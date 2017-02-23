package com.fangzhipro.app.main.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.fangzhipro.app.bean.HouseTypeDetails;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by smacr on 2016/9/1.
 */
public class WindowHotTypeAdapter extends RecyclerArrayAdapter<HouseTypeDetails.HouseTypeDetail> {
    public WindowHotTypeAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new WindowHotTypeViewHolder(parent);
    }
}
