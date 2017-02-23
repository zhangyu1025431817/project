package com.fangzhipro.app.main.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.fangzhipro.app.bean.CooperativeResponseBean;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by smacr on 2016/9/1.
 */
public class CooperativeAdapter extends RecyclerArrayAdapter<CooperativeResponseBean.Cooperative> {
    public CooperativeAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new CooperativeViewHolder(parent);
    }
}
