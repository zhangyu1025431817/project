package com.buqi.app.main.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.buqi.app.R;
import com.buqi.app.bean.SceneStyleResponse;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by smacr on 2016/9/1.
 */
public class SceneStyleTypeViewHolder extends BaseViewHolder<SceneStyleResponse.SceneStyle> {
    TextView textView;
    View viewLine;
    public SceneStyleTypeViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_scene_stype_type);
        AutoUtils.autoSize(itemView);
        textView = $(R.id.tv_name);
        viewLine = $(R.id.view_line);
    }

    @Override
    public void setData(SceneStyleResponse.SceneStyle data) {
        textView.setText(data.getCode_desc());
        if (data.isSelected()) {
            textView.setTextColor(getContext().getResources().getColor(R.color.text_main_color));
            viewLine.setVisibility(View.VISIBLE);
        } else {
            textView.setTextColor(getContext().getResources().getColor(R.color.black_3c3c3c));
            viewLine.setVisibility(View.GONE);
        }
    }
}
