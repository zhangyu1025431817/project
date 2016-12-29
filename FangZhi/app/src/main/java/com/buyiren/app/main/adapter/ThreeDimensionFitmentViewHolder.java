package com.buyiren.app.main.adapter;

import android.view.ViewGroup;
import android.widget.TextView;

import com.buyiren.app.R;
import com.buyiren.app.bean.FitmentTypeResponseBean;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by smacr on 2016/9/1.
 */
public class ThreeDimensionFitmentViewHolder extends BaseViewHolder<FitmentTypeResponseBean.FitmentType> {
    TextView textView;

    public ThreeDimensionFitmentViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_text_view);
        AutoUtils.autoSize(itemView);
        textView = $(R.id.tv_type);
    }

    @Override
    public void setData(FitmentTypeResponseBean.FitmentType data) {
        textView.setText(data.getType_name());
        if (data.isSelected()) {
            textView.setTextColor(getContext().getResources().getColor(R.color.text_main_color));
        } else {
            textView.setTextColor(getContext().getResources().getColor(R.color.black_3c3c3c));
        }
    }
}
