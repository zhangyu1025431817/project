package com.fangzhi.app.main.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fangzhi.app.R;
import com.fangzhi.app.bean.DDDTypeResponseBean;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by smacr on 2016/9/1.
 */
public class DDDImageViewHolder extends BaseViewHolder<DDDTypeResponseBean.DDDType.DDD> {
    ImageView imageView;
    TextView tvName;

    public DDDImageViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_3d);
        AutoUtils.autoSize(itemView);
        imageView = $(R.id.iv_image);
        tvName = $(R.id.tv_name);
    }

    @Override
    public void setData(DDDTypeResponseBean.DDDType.DDD data) {
        tvName.setText(data.getCase_name());
        Glide.with(getContext())
                .load(data.getCase_img())
                .placeholder(R.drawable.bg_image_placeholder)
                .crossFade()
                .into(imageView);
    }

}
