package com.buqi.app.main.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.buqi.app.bean.SellType;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by smacr on 2016/9/1.
 */
public class HomeCategoryAdapter extends RecyclerArrayAdapter<SellType.Category> {
    public HomeCategoryAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new HomeCategoryViewHolder(parent);
    }
}
