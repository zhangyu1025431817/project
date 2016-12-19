package com.fangzhi.app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.amap.api.location.AMapLocationClient;
import com.fangzhi.app.bean.UpdateVersion;
import com.fangzhi.app.login.LoginActivity;
import com.fangzhi.app.network.MySubscriber;
import com.fangzhi.app.network.Network;
import com.fangzhi.app.network.http.api.ErrorCode;
import com.fangzhi.app.tools.SDCardUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import java.io.File;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Call;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by smacr on 2016/8/29.
 */
public class LoadingActivity extends AppCompatActivity {
    private static final String AMapApiKey = "9ecad00ea95543d902c3820868a03d03";
    private Subscription mSubscription;
    private static final int REQUEST_CODE_SDCARD = 2;
    private static final int REQUEST_LOG = 3;
    private Subscription mDownloadSp;
    @Bind(R.id.iv_bg)
    ImageView ivBackground;
    SweetAlertDialog pDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化高德地图
        AMapLocationClient.setApiKey(AMapApiKey);
        setContentView(R.layout.activity_loading);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        /**
         * 延迟1秒页面跳转
         * 使用了RxAndroid
         */
        mSubscription = Observable
                .just("")
                .delay(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        //请求读写sd卡权限
                        if (!MPermissions.shouldShowRequestPermissionRationale(LoadingActivity.this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUEST_CODE_SDCARD)) {
                            MPermissions.requestPermissions(LoadingActivity.this, REQUEST_CODE_SDCARD,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        }
                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscription.unsubscribe();
        if (mDownloadSp != null) {
            mDownloadSp.unsubscribe();
        }
    }

    /**
     * 请求版本号
     */
    private void requestVersionCode() {
        mDownloadSp = Network.getApiService().updateVersion("A").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new MySubscriber<UpdateVersion>() {
                    @Override
                    public void onNext(UpdateVersion updateVersion) {
                        if (ErrorCode.SUCCEED.equals(updateVersion.getError_code())) {
                            //请求成功
                            final UpdateVersion.Version version = updateVersion.getEditionMap();
                            String code = version.getEDITION_NO();
                            final String url = version.getURL();
                            if (Float.parseFloat(code) > getVersion() && url != null && !url.isEmpty()) {
                                pDialog = new SweetAlertDialog(LoadingActivity.this, SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText("版本更新")
                                        .setContentText("当前程序需要更新后才能运行!")
                                        .setConfirmText("立即更新")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                download(url);
                                            }
                                        })
                                        .setCancelText("暂不更新")
                                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                if (!"10041001".equals(version.getEDITION_STATUS())) {
                                                    //非强制更新
                                                    startActivity(new Intent(LoadingActivity.this, LoginActivity.class));
                                                }
                                                pDialog.dismiss();
                                                finish();
                                            }
                                        });

                                pDialog.setCancelable(false);
                                pDialog.show();

                            } else {
                                startActivity(new Intent(LoadingActivity.this, LoginActivity.class));
                                finish();
                            }
                        } else {
                            startActivity(new Intent(LoadingActivity.this, LoginActivity.class));
                            finish();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        startActivity(new Intent(LoadingActivity.this, LoginActivity.class));
                        finish();
                    }
                });
    }

    private void download(String url) {
        if (SDCardUtils.isSDCardEnable()) {
            downloadFile(url);
        } else {
            pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
            pDialog.showCancelButton(false);
            pDialog.setConfirmText("确定");
            pDialog.setTitleText("下载失败");
            pDialog.setContentText("请插入sd卡!");
            pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    pDialog.dismiss();
                    finish();
                }
            });
        }
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public Float getVersion() {
        try {
            PackageManager manager = getPackageManager();
            PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
            return Float.parseFloat(info.versionName);
        } catch (Exception e) {
            e.printStackTrace();
            return 0f;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @PermissionGrant(REQUEST_CODE_SDCARD)
    public void requestSdcardSuccess() {
        requestVersionCode();
    }

    @PermissionDenied(REQUEST_CODE_SDCARD)
    public void requestSdcardFailed() {
        startActivity(new Intent(LoadingActivity.this, LoginActivity.class));
        finish();
    }

    private static final String apkName = "/fangzhi.apk";

    /**
     * 下载apk文件
     *
     * @param url
     */
    private void downloadFile(String url) {
        pDialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
        pDialog.showCancelButton(false);
        pDialog.showContentText(false);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.getProgressHelper().stopSpinning();
        OkHttpUtils.get().url(url).build().execute(new FileCallBack(SDCardUtils.getPublicDirectory(), apkName) {
            @Override
            public void onError(Call call, Exception e, int id) {
                pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                pDialog.setTitleText("下载失败");
                pDialog.showCancelButton(false);
                pDialog.setConfirmText("确定");
                pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        pDialog.dismiss();
                        finish();
                    }
                });
            }

            @Override
            public void onResponse(File response, int id) {
                File file = new File(SDCardUtils.getPublicDirectory() + apkName);
                final Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(Uri.parse("file://" + file.getAbsolutePath()), "application/vnd.android.package-archive");
                pDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                pDialog.setTitleText("下载完成!");
                pDialog.setConfirmText("安装");
                pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                pDialog.setTitleText("已下载" + (int) (progress * 100) + "%");
                pDialog.getProgressHelper().setProgress(progress);
            }
        });


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
