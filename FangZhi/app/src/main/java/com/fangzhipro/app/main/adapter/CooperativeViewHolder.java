package com.fangzhipro.app.main.adapter;

import android.view.ViewGroup;
import android.widget.TextView;

import com.fangzhipro.app.R;
import com.fangzhipro.app.bean.CooperativeResponseBean;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by smacr on 2016/9/1.
 */
public class CooperativeViewHolder extends BaseViewHolder<CooperativeResponseBean.Cooperative> {
    TextView textView;

    public CooperativeViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_text_view);
        AutoUtils.autoSize(itemView);
        textView = $(R.id.tv_type);
    }

    @Override
    public void setData(CooperativeResponseBean.Cooperative data) {
        textView.setText(data.getCooperative_name());
        if (data.isSelected()) {
            textView.setTextColor(getContext().getResources().getColor(R.color.text_main_color));
        } else {
            textView.setTextColor(getContext().getResources().getColor(R.color.black_3c3c3c));
        }
    }
}
