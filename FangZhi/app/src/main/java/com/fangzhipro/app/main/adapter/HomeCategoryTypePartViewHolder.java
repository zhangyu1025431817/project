package com.fangzhipro.app.main.adapter;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fangzhipro.app.R;
import com.fangzhipro.app.base.RxBus;
import com.fangzhipro.app.bean.CategoryPart;
import com.fangzhipro.app.main.decoration.product.PictureActivity;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by smacr on 2016/9/1.
 */
public class HomeCategoryTypePartViewHolder extends BaseViewHolder<CategoryPart.Part> {
    TextView textView;
    ImageView imageView;
    RelativeLayout layoutPart;
    Button btnDiy;
    Button btnStyle;
    View space;

    public HomeCategoryTypePartViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_home_category_part_product);
        AutoUtils.autoSize(itemView);
        layoutPart = $(R.id.layout_part);
        btnDiy = $(R.id.btn_diy);
        btnStyle = $(R.id.btn_style);
        textView = $(R.id.tv_name);
        imageView = $(R.id.iv_image);
        space = $(R.id.space);
    }

    @Override
    public void setData(final CategoryPart.Part data) {
        textView.setText(data.getPart_name());
        Glide.with(getContext())
                .load(data.getPart_img_short())
                .placeholder(R.drawable.bg_image_placeholder)
                .crossFade()
                .into(imageView);
//        btnDiy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                RxBus.$().post("partId",data.getId());
//            }
//        });
        btnDiy.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    pressUp(v);
                    RxBus.$().post("partId",data.getId());
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    pressDown(v);
                }
                return true;
            }
        });
//        btnStyle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), PictureActivity.class);
//                intent.putExtra("partId",data.getId());
//                getContext().startActivity(intent);
//            }
//        });
        btnStyle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    pressUp(v);
                    Intent intent = new Intent(getContext(), PictureActivity.class);
                    intent.putExtra("partId",data.getId());
                    getContext().startActivity(intent);
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    pressDown(v);
                }
                return true;
            }
        });
        if(data.getCount() == 0){
            btnStyle.setVisibility(View.GONE);
            space.setVisibility(View.GONE);
        }else{
            btnStyle.setVisibility(View.VISIBLE);
            space.setVisibility(View.VISIBLE);
        }
//        if (data.isSelected()) {
//            btnDiy.setBackground(getContext().getResources().getDrawable(R.drawable.btn_blue));
//            btnDiy.setTextColor(getContext().getResources().getColor(R.color.colorWhite));
//            btnStyle.setBackground(getContext().getResources().getDrawable(R.drawable.btn_blue));
//            btnStyle.setTextColor(getContext().getResources().getColor(R.color.colorWhite));
//            btnStyle.setClickable(true);
//            btnDiy.setClickable(true);
//        } else {
//            btnDiy.setBackground(getContext().getResources().getDrawable(R.drawable.btn_corner_transpant_black));
//            btnDiy.setTextColor(getContext().getResources().getColor(R.color.black_3c3c3c));
//            btnStyle.setBackground(getContext().getResources().getDrawable(R.drawable.btn_corner_transpant_black));
//            btnStyle.setTextColor(getContext().getResources().getColor(R.color.black_3c3c3c));
//            btnStyle.setClickable(false);
//            btnDiy.setClickable(false);
//        }
    }

    public void pressDown(final View view) {
        ObjectAnimator anim = ObjectAnimator//
                .ofFloat(view, "shrink", 1.0F, 1.05F)//
                .setDuration(500);//
        anim.start();
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();
                view.setAlpha(cVal);
                view.setScaleX(cVal);
                view.setScaleY(cVal);
            }
        });
    }

    public void pressUp(final View view) {
        ObjectAnimator anim = ObjectAnimator//
                .ofFloat(view, "zoom", 1.05F, 1.0F)//
                .setDuration(500);//
        anim.start();
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();
                view.setAlpha(cVal);
                view.setScaleX(cVal);
                view.setScaleY(cVal);
            }
        });
    }
}
