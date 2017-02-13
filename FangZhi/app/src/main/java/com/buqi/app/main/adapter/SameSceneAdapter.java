package com.buqi.app.main.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.buqi.app.bean.Scene;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by smacr on 2016/9/1.
 */
public class SameSceneAdapter extends RecyclerArrayAdapter<Scene> {
    public SameSceneAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new SameSceneViewHolder(parent);
    }
}
