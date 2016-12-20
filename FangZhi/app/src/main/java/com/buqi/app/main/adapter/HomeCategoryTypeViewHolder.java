package com.buqi.app.main.adapter;

import android.view.ViewGroup;
import android.widget.TextView;

import com.buqi.app.R;
import com.buqi.app.bean.CategoryPart;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by smacr on 2016/9/1.
 */
public class HomeCategoryTypeViewHolder extends BaseViewHolder<CategoryPart.HotType> {
    TextView textView;

    public HomeCategoryTypeViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_text_view);
        AutoUtils.autoSize(itemView);
        textView = $(R.id.tv_type);
    }

    @Override
    public void setData(CategoryPart.HotType data) {
        textView.setText(data.getCode_desc());
        if (data.isSelected()) {
            textView.setBackgroundColor(getContext().getResources().getColor(R.color.alpha_green));
        } else {
            textView.setBackgroundColor(getContext().getResources().getColor(R.color.shadow));
        }
    }
}
