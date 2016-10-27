package com.fangzhi.app.main.parent;

import android.view.ViewGroup;
import android.widget.TextView;

import com.fangzhi.app.R;
import com.fangzhi.app.bean.LoginNewBean;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by smacr on 2016/9/1.
 */
public class ParentViewHolder extends BaseViewHolder<LoginNewBean.Parent> {
    TextView textView;

    public ParentViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_parent);
        AutoUtils.autoSize(itemView);
        textView = $(R.id.tv_name);
    }

    @Override
    public void setData(LoginNewBean.Parent data) {
        textView.setText(data.getNAME());
        if (data.isSelected()) {
            textView.setBackgroundColor(getContext().getResources().getColor(R.color.alpha_green));
        } else {
            textView.setBackgroundColor(getContext().getResources().getColor(R.color.white));
        }
    }
}
