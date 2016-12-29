package com.buyiren.app.main.ddd;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.buyiren.app.R;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by smacr on 2016/11/16.
 */
public class DDView2 extends AppCompatActivity {
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.tv_name)
    TextView tvName;
    String mSize;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2d2);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        final ArrayList<Map<String,String>> mapArrayList = (ArrayList<Map<String, String>>) intent.getSerializableExtra("url");
        mSize = mapArrayList.size()+"";
        setName(mapArrayList,0);
        List<Fragment> fragments = new ArrayList<>();
        for(Map<String,String> map : mapArrayList){
            fragments.add(ProductListFragment.newInstance(map.get("url")));
        }
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(),fragments,mapArrayList));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
     //   recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));
        PictureAdapter adapter = new PictureAdapter(this);
     //   recyclerView.setAdapter(adapter);
        adapter.addAll(mapArrayList);

    }

    public class FragmentAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> mFragments;
        private List<Map<String,String>> mCategoryList;

        public FragmentAdapter(FragmentManager fm, List<Fragment> fragments, List<Map<String,String>> categoryList) {
            super(fm);
            mFragments = fragments;
            mCategoryList = categoryList;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }
    }
    private void setName(ArrayList<Map<String,String>> mapArrayList,int position){
        Map<String ,String> map = mapArrayList.get(position);
        tvName.setText(map.get("name")+"("+(position+1)+"/"+mSize+")");
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

    public class PictureAdapter extends RecyclerArrayAdapter<Map<String,String>> {
        public PictureAdapter(Context context) {
            super(context);
        }

        @Override
        public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
            return new PictureViewHolder(parent);
        }
    }

    public class PictureViewHolder extends BaseViewHolder<Map<String,String>> {
        ImageView imageView;

        public PictureViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_2d_picture);
            AutoUtils.autoSize(itemView);
            imageView = $(R.id.iv_image);
        }

        @Override
        public void setData(Map<String, String> data) {
            Glide.with(getContext())
                    .load(data.get("url"))
                    .placeholder(R.drawable.bg_image_placeholder)
                    .crossFade()
                    .into(imageView);
        }
    }

    }
