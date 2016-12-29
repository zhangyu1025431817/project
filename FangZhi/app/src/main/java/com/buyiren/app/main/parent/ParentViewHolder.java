package com.buyiren.app.main.parent;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.view.ViewGroup;
import android.widget.TextView;

import com.buyiren.app.R;
import com.buyiren.app.bean.LoginNewBean;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by smacr on 2016/9/1.
 */
public class ParentViewHolder extends BaseViewHolder<LoginNewBean.Parent> {
    TextView textView;
    Shader shaderWhite;
    Shader shaderBlue;
    public ParentViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_parent);
        AutoUtils.autoSize(itemView);
        textView = $(R.id.tv_brand);
        shaderWhite =new LinearGradient(0, 0, 0, 100, Color.WHITE, Color.WHITE, Shader.TileMode.CLAMP);
        shaderBlue =new LinearGradient(0, 0, 0, 100,
                0xFF0095d6, 0xFF005ead,
                Shader.TileMode.CLAMP);
    }

    @Override
    public void setData(LoginNewBean.Parent data) {
        textView.setText(data.getNAME());
        if (data.isSelected()) {
            textView.setBackground(getContext().getResources().getDrawable(R.drawable.btn_parent_selected));
            textView.getPaint().setShader(shaderWhite);
           // textView.setTextColor(getContext().getResources().getColor(R.color.colorWhite));
        } else {
            textView.setBackground(getContext().getResources().getDrawable(R.drawable.btn_parent_unselected));
            textView.getPaint().setShader(shaderBlue);
           // textView.setTextColor(getContext().getResources().getColor(R.color.black_3c3c3c));
        }
    }
}
