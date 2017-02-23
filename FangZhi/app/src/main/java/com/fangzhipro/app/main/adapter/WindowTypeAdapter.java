package com.fangzhipro.app.main.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.fangzhipro.app.bean.WindowType;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by smacr on 2016/9/1.
 */
public class WindowTypeAdapter extends RecyclerArrayAdapter<WindowType> {
    public WindowTypeAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new WindowTypeViewHolder(parent);
    }
}
