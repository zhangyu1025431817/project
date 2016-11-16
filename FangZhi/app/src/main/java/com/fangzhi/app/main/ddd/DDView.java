package com.fangzhi.app.main.ddd;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.fangzhi.app.R;
import com.fangzhi.app.tools.DensityUtils;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by smacr on 2016/11/16.
 */
public class DDView extends AppCompatActivity {
    @Bind(R.id.roll_pager_view)
    RollPagerView rollPagerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2d);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String image = intent.getStringExtra("url");
        String[] images = image.split(";");
        List<String> list = Arrays.asList(images);

        rollPagerView.setHintView(new ColorPointHintView(this, Color.GREEN, Color.GRAY));
        rollPagerView.setHintPadding(0, 0, 0, DensityUtils.dp2px(this, 8));
      //  rollPagerView.setPlayDelay(10000);
        BannerAdapter mBannerAdapter = new BannerAdapter(this, list);
        rollPagerView.setAdapter(mBannerAdapter);
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

    @OnClick(R.id.iv_close)
    public void onFinish() {
        finish();
    }
}
