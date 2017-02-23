package com.fangzhipro.app.main.ddd;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fangzhipro.app.R;

/**
 * Created by zhangyu on 2016/8/1.
 */
public class ProductListFragment extends Fragment {

    public static ProductListFragment newInstance(String url) {
        ProductListFragment fragment = new ProductListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  LayoutInflater.from(getContext()).inflate(R.layout.item_2d_picture,null);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_image);
                    Glide.with(getContext())
                    .load(getArguments().getString("url"))
                    .asBitmap()
                    .placeholder(R.drawable.bg_image_placeholder)
                    .fitCenter()
                    .into(imageView);
        return view;
    }
}
