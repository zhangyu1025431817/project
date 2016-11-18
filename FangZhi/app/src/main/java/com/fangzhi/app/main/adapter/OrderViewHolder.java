package com.fangzhi.app.main.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fangzhi.app.R;
import com.fangzhi.app.base.RxBus;
import com.fangzhi.app.bean.Order;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by smacr on 2016/9/1.
 */
public class OrderViewHolder extends BaseViewHolder<Order> {
    ImageView iv_image;
    TextView tv_brand;
    TextView tv_type;
    TextView tv_number;
    TextView tv_size;
    EditText tv_price;
    EditText tv_count;
    TextView tv_total_money;
    int index;
    MyWatcher mWatcher;
    public OrderViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_order_list);
        AutoUtils.autoSize(itemView);
        iv_image = $(R.id.iv_image);
        tv_brand = $(R.id.tv_brand);
        tv_type = $(R.id.tv_type);
        tv_number = $(R.id.tv_number);
        tv_size = $(R.id.tv_size);
        tv_price = $(R.id.tv_price);
        tv_count = $(R.id.tv_count);
        tv_total_money = $(R.id.tv_total_money);
    }

    @Override
    public void setData(final Order data) {
        Glide.with(getContext())
                .load(data.getPart_img_short())
                .crossFade()
                .into(iv_image);
        tv_brand.setText(data.getPart_brand());
        tv_type.setText(data.getType());
        tv_number.setText(data.getPart_code());
        tv_size.setText(data.getPart_unit());
//        tv_count.clearFocus();
//        tv_price.clearFocus();
        tv_price.setText(data.getPrice());
        tv_price.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    index = getLayoutPosition() - 1;
                }
                return false;
            }
        });
        tv_price.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText et = (EditText) v;
                if (mWatcher == null) {
                    mWatcher = new MyWatcher(0);
                }
                if (hasFocus) {
                    tv_count.clearFocus();
                    et.addTextChangedListener(mWatcher);
                } else {
                    et.removeTextChangedListener(mWatcher);
                }
            }
        });
        tv_count.setText(data.getCount());
        tv_count.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    index = getLayoutPosition() - 1;
                }
                return false;
            }
        });
        tv_count.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText et = (EditText) v;
                if (mWatcher == null) {
                    mWatcher = new MyWatcher(0);
                }
                if (hasFocus) {
                    tv_price.clearFocus();
                    et.addTextChangedListener(mWatcher);
                } else {
                    et.removeTextChangedListener(mWatcher);
                }
            }
        });

        tv_total_money.setText(data.getTotalMoney());
//        if (priceMap.containsKey(getLayoutPosition() - 1)) {
//            tv_price.setText(priceMap.get(getLayoutPosition() - 1) + "");
//        }
//        tv_price.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Money money = new Money();
//                money.position = getLayoutPosition() -1;
//                money.price = Float.parseFloat(data.getPrice());
//                money.count = Integer.parseInt(data.getCount());
//                RxBus.$().post("price",money);
//            }
//        });
//        if (countMap.containsKey(getLayoutPosition() - 1)) {
//            tv_count.setText(countMap.get(getLayoutPosition() - 1) + "");
//        }
//        tv_count.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Money money = new Money();
//                money.position = getLayoutPosition() - 1;
//                money.price = Float.parseFloat(data.getPrice());
//                money.count = Integer.parseInt(data.getCount());
//                RxBus.$().post("count", money);
//            }
//        });
//        if (totalMap.containsKey(getLayoutPosition() - 1))
//            tv_total_money.setText(totalMap.get(getLayoutPosition() - 1) + "");
    }

    public class Money {
        public int position;
        public int count;
        public float price;
    }

    class MyWatcher implements TextWatcher {
        int type;
        public MyWatcher(int type){
            this.type = type;
        }


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(type == 0){
                float price = s.toString().isEmpty() ? 0f : Float.parseFloat(s.toString());
                Money money = new Money();
                money.position = getLayoutPosition() - 1;
                money.price = price;
                RxBus.$().post("price", money);
            }else{
                int count = s.toString().isEmpty() ? 0 : Integer.parseInt(s.toString());
                Money money = new Money();
                money.position = getLayoutPosition() - 1;
                money.count = count;
                RxBus.$().post("count", money);
            }
        }

    }
}
