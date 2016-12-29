package com.buyiren.app.main.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.buyiren.app.bean.CategoryPart;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by smacr on 2016/9/1.
 */
public class HomeCategoryTypeAdapter extends RecyclerArrayAdapter<CategoryPart.HotType> {
    public HomeCategoryTypeAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new HomeCategoryTypeViewHolder(parent);
    }
}
