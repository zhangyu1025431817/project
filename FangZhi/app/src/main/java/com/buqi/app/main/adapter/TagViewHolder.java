package com.buqi.app.main.adapter;

import android.view.ViewGroup;
import android.widget.TextView;

import com.buqi.app.R;
import com.buqi.app.bean.SceneLabel;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by smacr on 2016/9/1.
 */
public class TagViewHolder extends BaseViewHolder<SceneLabel> {
    TextView textView;

    public TagViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_text_view);
        AutoUtils.autoSize(itemView);
        textView = $(R.id.tv_type);
    }

    @Override
    public void setData(SceneLabel data) {
        textView.setText(data.getLabel_name());
        if (data.isSelected()) {
            textView.setTextColor(getContext().getResources().getColor(R.color.text_main_color));
        } else {
            textView.setTextColor(getContext().getResources().getColor(R.color.black_3c3c3c));
        }
    }
}
