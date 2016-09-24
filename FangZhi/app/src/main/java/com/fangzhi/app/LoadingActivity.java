package com.fangzhi.app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.amap.api.location.AMapLocationClient;
import com.fangzhi.app.config.SpKey;
import com.fangzhi.app.login.LoginActivityNew;
import com.fangzhi.app.tools.SPUtils;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by smacr on 2016/8/29.
 */
public class LoadingActivity extends AppCompatActivity {
    private Subscription mSubscription;
    @Bind(R.id.iv_bg)
    ImageView ivBackground;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AMapLocationClient.setApiKey("9ecad00ea95543d902c3820868a03d03");
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
         * 延迟两秒页面跳转
         * 使用了RxAndroid
         */
        mSubscription = Observable
                .just((String) SPUtils.get(MyApplication.getContext(), SpKey.TOKEN, ""))
                .delay(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        startActivity(new Intent(LoadingActivity.this, LoginActivityNew.class));
                        finish();
                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscription.unsubscribe();
    }
}
