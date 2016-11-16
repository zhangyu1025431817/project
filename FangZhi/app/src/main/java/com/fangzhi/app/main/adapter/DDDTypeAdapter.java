package com.fangzhi.app.main.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.fangzhi.app.bean.DDDTypeResponseBean;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by smacr on 2016/9/1.
 */
public class DDDTypeAdapter extends RecyclerArrayAdapter<DDDTypeResponseBean.DDDType> {
    public DDDTypeAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new DDDTypeViewHolder(parent);
    }
}
