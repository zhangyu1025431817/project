package com.buqi.app.main.ddd;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.buqi.app.R;

import org.xwalk.core.XWalkNavigationHistory;
import org.xwalk.core.XWalkView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by smacr on 2016/11/10.
 */
public class DDDWebView extends AppCompatActivity {
    @Bind(R.id.web_view)
    XWalkView xWalkWebView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");

//        WebSettings settings = xWalkWebView.getSettings();
//        settings.setJavaScriptEnabled(true);
        xWalkWebView.load(url, null);
    }

    /**
     * 通过Activity管理XWalkWebView的生命周期
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (xWalkWebView != null) {
            xWalkWebView.pauseTimers();
            xWalkWebView.onHide();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (xWalkWebView != null) {
            xWalkWebView.resumeTimers();
            xWalkWebView.onShow();
        }
    }

    @Override
    protected void onDestroy() {
        if (xWalkWebView != null) {
            xWalkWebView.clearCache(true);
            XWalkNavigationHistory history = xWalkWebView.getNavigationHistory();
            if (history != null) {
                history.clear();
            }
            xWalkWebView.onDestroy();
        }
        super.onDestroy();
    }

    @OnClick(R.id.iv_close)
    public void onFinish() {
        finish();
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (xWalkWebView  != null) {
            xWalkWebView .onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (xWalkWebView != null) {
            xWalkWebView.onNewIntent(intent);
        }
    }
}
