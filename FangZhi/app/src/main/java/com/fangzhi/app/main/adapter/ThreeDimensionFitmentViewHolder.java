package com.fangzhi.app.main.adapter;

import android.view.ViewGroup;
import android.widget.TextView;

import com.fangzhi.app.R;
import com.fangzhi.app.bean.FitmentTypeResponseBean;
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
            textView.setBackgroundColor(getContext().getResources().getColor(R.color.alpha_green));
            //    textView.setTextColor(getContext().getResources().getColor(R.color.white));
        } else {
            textView.setBackgroundColor(getContext().getResources().getColor(R.color.shadow));
            //    textView.setTextColor(getContext().getResources().getColor(R.color.black_semi_transparent));
        }
    }
}
