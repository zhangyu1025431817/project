package com.buyiren.app.main.ddd;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.buyiren.app.R;
import com.buyiren.app.tools.DensityUtils;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by smacr on 2016/11/16.
 */
public class DDView extends AppCompatActivity {
    @Bind(R.id.roll_pager_view)
    RollPagerView rollPagerView;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2d);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        final ArrayList<Map<String,String>> mapArrayList = (ArrayList<Map<String, String>>) intent.getSerializableExtra("url");
        setName(mapArrayList,0);
      //  rollPagerView.setHintView(new ColorPointHintView(this, Color.GREEN, Color.GRAY));
        rollPagerView.setHintView(new ColorPointHintView(this, Color.TRANSPARENT, Color.TRANSPARENT));
        rollPagerView.setHintPadding(0, 0, 0, DensityUtils.dp2px(this, 8));
      //  rollPagerView.setPlayDelay(10000);
        List<ImageView> list = new ArrayList<>();
        for(Map<String,String> map : mapArrayList){
            View view =  LayoutInflater.from(this).inflate(R.layout.item_2d_picture,null);
            ImageView imageView = (ImageView) view.findViewById(R.id.iv_image);
            list.add(imageView);
        }
        PictureAdapter mBannerAdapter = new PictureAdapter(this, mapArrayList,list);
        rollPagerView.setAdapter(mBannerAdapter);
        rollPagerView.getViewPager().addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                setName(mapArrayList,position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }



    private void setName(ArrayList<Map<String,String>> mapArrayList,int position){
        Map<String ,String> map = mapArrayList.get(position);
        tvName.setText(map.get("name")+"("+map.get("position")+"/"+map.get("count")+")");
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
