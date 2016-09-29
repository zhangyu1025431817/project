package com.fangzhi.app.main.custom;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fangzhi.app.R;
import com.fangzhi.app.main.MainActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by smacr on 2016/9/24.
 */
public class CustomActivity extends AppCompatActivity {
    @Bind(R.id.iv_bg)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_custom);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent.hasExtra("url")) {
            String url = intent.getStringExtra("url");
            Glide.with(this)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .crossFade()
                    .centerCrop()
                    .into(imageView);
        }
    }

    @OnClick(R.id.iv_close)
    public void onClose() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
