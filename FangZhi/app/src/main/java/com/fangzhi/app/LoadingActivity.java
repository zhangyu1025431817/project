package com.fangzhi.app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.fangzhi.app.bean.LoginBean;
import com.fangzhi.app.config.SpKey;
import com.fangzhi.app.login.LoginActivity;
import com.fangzhi.app.main.MainActivity;
import com.fangzhi.app.network.MySubscriber;
import com.fangzhi.app.network.NetWorkRequest;
import com.fangzhi.app.network.RequestCodeMessage;
import com.fangzhi.app.tools.SPUtils;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
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
                .flatMap(new Func1<String, Observable<LoginBean>>() {
                    @Override
                    public Observable<LoginBean> call(String token) {
                        return token.isEmpty()
                                ? Observable.<LoginBean>error(new NullPointerException("Token is null!"))
                                : NetWorkRequest.login(token);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<LoginBean>() {
                    @Override
                    public void onNext(LoginBean loginBean) {
                        if (RequestCodeMessage.verificationResponseCode(loginBean)) {
                            startActivity(new Intent(LoadingActivity.this,MainActivity.class));
                        } else {
                            startActivity(new Intent(LoadingActivity.this, LoginActivity.class));
                        }
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        startActivity(new Intent(LoadingActivity.this, LoginActivity.class));
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
