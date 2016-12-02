package com.fangzhi.app.main.parent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.WindowManager;

import com.fangzhi.app.MyApplication;
import com.fangzhi.app.R;
import com.fangzhi.app.bean.LoginBean;
import com.fangzhi.app.bean.LoginNewBean;
import com.fangzhi.app.config.SpKey;
import com.fangzhi.app.main.MainActivity;
import com.fangzhi.app.main.welcome.CustomActivity;
import com.fangzhi.app.manager.AccountManager;
import com.fangzhi.app.network.MySubscriber;
import com.fangzhi.app.network.Network;
import com.fangzhi.app.network.http.api.ErrorCode;
import com.fangzhi.app.tools.SPUtils;
import com.fangzhi.app.view.DialogDelegate;
import com.fangzhi.app.view.SweetAlertDialogDelegate;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by smacr on 2016/10/26.
 */
public class ParentActivity extends AppCompatActivity {

    @Bind(R.id.recycler_view)
    EasyRecyclerView easyRecyclerView;
    ParentAdapter mAdapter;
    DialogDelegate dialogDelegate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_parent);
        ButterKnife.bind(this);
        Log.e("ParentActivity","ParentActivity--create");
        mAdapter = new ParentAdapter(this);
        mAdapter.addAll(AccountManager.getInstance().getParentList());
        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                LoginNewBean.Parent parent = mAdapter.getItem(position);
                if (parent.isSelected()) {
                    finish();
                    return;
                }
                for (LoginNewBean.Parent bean : mAdapter.getAllData()) {
                    bean.setSelected(false);
                }
                parent.setSelected(true);
                mAdapter.notifyDataSetChanged();
                dialogDelegate.showProgressDialog(true, "正在提交...");
                loginParent(
                        parent.getNAME(),
                        parent.getID(),
                        parent.getURL());
            }
        });
        easyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        easyRecyclerView.setAdapter(mAdapter);
        dialogDelegate = new SweetAlertDialogDelegate(this);
    }

    private void loginParent(final String name, final String parentId, final String url) {
        String token = SPUtils.getString(this, SpKey.TOKEN, "");
        String userId = SPUtils.getString(this, SpKey.USER_ID, "");
        Network.getApiService().loginParent(token, parentId, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<LoginBean>() {
                    @Override
                    public void onNext(LoginBean loginBean) {
                        if (ErrorCode.SUCCEED.equals(loginBean.getError_code())) {
                            SPUtils.put(ParentActivity.this, SpKey.TOKEN, loginBean.getToken());
                            dialogDelegate.clearDialog();
                            Intent intent = new Intent();
                            if (loginBean.getImg() == null || loginBean.getImg().isEmpty()) {
                                intent.setClass(ParentActivity.this, MainActivity.class);
                            } else {
                                intent.putExtra("url", loginBean.getImg());
                                intent.setClass(ParentActivity.this, CustomActivity.class);
                            }
                            SPUtils.put(MyApplication.getContext(),
                                    SpKey.FACTORY_ADDRESS, url == null ? "" : url)
                            ;
                            AccountManager.getInstance().setCurrentSelectParentId(parentId);
                            AccountManager.getInstance().setCurrentParentName(name);
                            startActivity(intent);
                            ParentActivity.this.finish();
                        } else {
                            dialogDelegate.stopProgressWithFailed("提交失败", loginBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialogDelegate.stopProgressWithFailed("提交失败", "服务器连接失败！");
                    }
                });
    }

    //    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
//            View decorView = getWindow().getDecorView();
//            decorView.setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//        }
//    }
    @OnClick(R.id.iv_close)
    public void onFinish() {
        finish();
    }
}
