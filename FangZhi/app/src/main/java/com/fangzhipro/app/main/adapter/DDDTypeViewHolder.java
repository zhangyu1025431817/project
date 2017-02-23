package com.fangzhipro.app.main.adapter;

import android.view.ViewGroup;
import android.widget.TextView;

import com.fangzhipro.app.R;
import com.fangzhipro.app.bean.DDDTypeResponseBean;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by smacr on 2016/9/1.
 */
public class DDDTypeViewHolder extends BaseViewHolder<DDDTypeResponseBean.DDDType> {
    TextView textView;

    public DDDTypeViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_text_view);
        AutoUtils.autoSize(itemView);
        textView = $(R.id.tv_type);
    }

    @Override
    public void setData(DDDTypeResponseBean.DDDType data) {
        textView.setText(data.getImage_type_name());
        if (data.isSelected()) {
            textView.setTextColor(getContext().getResources().getColor(R.color.text_main_color));
        } else {
            textView.setTextColor(getContext().getResources().getColor(R.color.black_3c3c3c));
        }
    }
}
