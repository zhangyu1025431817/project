package com.fangzhipro.app.main.parent;

import android.content.Context;
import android.view.ViewGroup;

import com.fangzhipro.app.bean.LoginNewBean;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by smacr on 2016/9/1.
 */
public class ParentAdapter extends RecyclerArrayAdapter<LoginNewBean.Parent> {
    public ParentAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ParentViewHolder(parent);
    }
}
