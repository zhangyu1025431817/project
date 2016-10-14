package com.fangzhi.app.main.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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
    TextView tv_price;
    TextView tv_count;
    TextView tv_total_money;

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
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .crossFade()
                .centerCrop()
                .into(iv_image);
        tv_brand.setText(data.getPart_brand());
        tv_type.setText(data.getType());
        tv_number.setText(data.getPart_code());
        tv_size.setText(data.getPart_unit());
        tv_price.setText(data.getPrice());
        tv_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Money money = new Money();
                money.position = getLayoutPosition() -1;
                money.price = Float.parseFloat(data.getPrice());
                money.count = Integer.parseInt(data.getCount());
                RxBus.$().post("price",money);
            }
        });

        tv_count.setText(data.getCount());
        tv_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Money money = new Money();
                money.position = getLayoutPosition() -1;
                money.price = Float.parseFloat(data.getPrice());
                money.count = Integer.parseInt(data.getCount());
                RxBus.$().post("count",money);
            }
        });

        tv_total_money.setText(data.getTotalMoney());
    }
//    public class Price{
//        public int position;
//        public float price;
//
//        public Price(int position, float price) {
//            this.position = position;
//            this.price = price;
//        }
//    }
//    public class Count{
//        public int position;
//        public int count;
//
//        public Count(int position, int count) {
//            this.position = position;
//            this.count = count;
//        }
//    }
    public class Money{
        public int position;
        public int count;
        public float price;
    }

}
