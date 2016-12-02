package com.fangzhi.app.main.adapter;

import android.view.ViewGroup;
import android.widget.TextView;

import com.fangzhi.app.R;
import com.fangzhi.app.bean.RoomProductType;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by smacr on 2016/9/1.
 */
public class ProductTypeViewHolder extends BaseViewHolder<RoomProductType> {
    TextView textView;

    public ProductTypeViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_room_product_type_view);
        AutoUtils.autoSize(itemView);
        textView = $(R.id.tv_type);
        textView.setTextColor(getContext().getResources().getColor(R.color.white));
    }

    @Override
    public void setData(RoomProductType data) {
        textView.setText(data.getType_name());
        if (data.isSelected()) {
            textView.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.shape_green_solid_white_stroke));
        } else {
            textView.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.shape_transparent_solid_white_stroke));
        }
    }
}
