package com.fangzhipro.app.main.adapter;

import android.view.ViewGroup;
import android.widget.TextView;

import com.fangzhipro.app.R;
import com.fangzhipro.app.bean.HouseTypeDetails;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by smacr on 2016/9/1.
 */
public class WindowHotTypeViewHolder extends BaseViewHolder<HouseTypeDetails.HouseTypeDetail> {
    TextView textView;

    public WindowHotTypeViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_text_view);
        AutoUtils.autoSize(itemView);
        textView = $(R.id.tv_type);
    }

    @Override
    public void setData(HouseTypeDetails.HouseTypeDetail data) {
        textView.setText(data.getCODE_DESC());
        if (data.isSelected()) {
            textView.setBackgroundColor(getContext().getResources().getColor(R.color.alpha_green));
        } else {
            textView.setBackgroundColor(getContext().getResources().getColor(R.color.shadow));
        }
    }
}
