package com.fangzhi.app.main.list;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fangzhi.app.MyApplication;
import com.fangzhi.app.R;
import com.fangzhi.app.bean.Order;
import com.fangzhi.app.config.SpKey;
import com.fangzhi.app.tools.SPUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by smacr on 2016/9/23.
 */
public class ListOrderActivity extends SwipeBackActivity {
    @Bind(R.id.recycler_view)
    ListView listView;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_contact_factory)
    TextView tvAddress;
    TextView tvTotalMoney;
    View viewFooter;
    View viewHeader;
    List<Order> mList = new ArrayList<>();
    LayoutInflater mInflater;
    Map<String, String> priceMap = new HashMap<>();
    Map<String, String> countMap = new HashMap<>();
    Map<String, String> totalMap = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        ButterKnife.bind(this);
        mInflater = LayoutInflater.from(this);
        viewFooter = mInflater.inflate(R.layout.view_order_list_footer, null);
        viewHeader = mInflater.inflate(R.layout.view_order_list_header, null);
        tvTotalMoney = (TextView) viewFooter.findViewById(R.id.tv_total_money);
        tvTitle.setText("设计清单");
        Intent intent = getIntent();
        if (intent.hasExtra("list")) {
            List<Order> tempList = (List<Order>) intent.getSerializableExtra("list");
            mList.addAll(tempList);
//            for (Order order : tempList) {
//                String[] images = order.getPart_img_short().split(";");
//                for (String image : images) {
//                    Order tempOrder = BeanCloneUtil.cloneTo(order);
//                    tempOrder.setPart_img_short(image);
//                    mList.add(tempOrder);
//                }
//            }
        }
        //联系厂家那个按钮显示与否
        String url = SPUtils.getString(MyApplication.getContext(),
                SpKey.FACTORY_ADDRESS, "");
        if (url.isEmpty()) {
            tvAddress.setVisibility(View.GONE);
        } else {
            tvAddress.setVisibility(View.VISIBLE);
        }
        listView.setAdapter(new MyAdapter());
        listView.addHeaderView(viewHeader);
        listView.addFooterView(viewFooter);


    }

    @OnClick(R.id.iv_back)
    public void onReturn() {
        finish();
    }

    @Override
    protected void onDestroy() {
        closeKeyboard();
        super.onDestroy();
    }

    public void closeKeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isActive()) {

                imm.hideSoftInputFromWindow(getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @OnClick(R.id.tv_contact_factory)
    public void onGoto() {
        String url = SPUtils.getString(MyApplication.getContext(),
                SpKey.FACTORY_ADDRESS, "");
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        startActivity(intent);
    }

    private float mTotalMoney;
    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final Order order = mList.get(position);
            final ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_order_list, parent, false);
                holder = new ViewHolder();
                holder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);
                holder.tv_brand = (TextView) convertView.findViewById(R.id.tv_brand);
                holder.tv_type = (TextView) convertView.findViewById(R.id.tv_type);
                holder.tv_number = (TextView) convertView.findViewById(R.id.tv_number);
                holder.tv_size = (TextView) convertView.findViewById(R.id.tv_size);
                holder.tv_price = (EditText) convertView.findViewById(R.id.tv_price);
                holder.tv_price.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        Order bean = (Order) holder.tv_price.getTag();
                        float oldMoney = Float.parseFloat(bean.getTotalMoney());
                        float price = s.toString().isEmpty() ? 0f : Float.parseFloat(s.toString());
                        int count = Integer.parseInt(bean.getCount());
                        float total = price * count;
                        bean.setPrice(price + "");
                        bean.setTotalMoney(total + "");
                        holder.tv_total_money.setText(total + "");
                        mTotalMoney = mTotalMoney+(total - oldMoney);
                        tvTotalMoney.setText(mTotalMoney+"");
                        //    notifyDataSetChanged();
                    }
                });
                holder.tv_count = (EditText) convertView.findViewById(R.id.tv_count);
                holder.tv_count.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        Order bean = (Order) holder.tv_count.getTag();
                        float oldMoney = Float.parseFloat(bean.getTotalMoney());
                        float price = Float.parseFloat(bean.getPrice());
                        int count = s.toString().isEmpty() ? 0 : Integer.parseInt(s.toString());
                        float total = price * count;

                        bean.setCount(count + "");
                        bean.setTotalMoney(total + "");
                        holder.tv_total_money.setText(total + "");
                        mTotalMoney = mTotalMoney+(total - oldMoney);
                        tvTotalMoney.setText(mTotalMoney+"");
                        //   notifyDataSetChanged();
                    }
                });
                holder.tv_total_money = (TextView) convertView.findViewById(R.id.tv_total_money);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Glide.with(ListOrderActivity.this)
                    .load(order.getPart_img_short())
                    .crossFade()
                    .into(holder.iv_image);
            holder.tv_brand.setText(order.getPart_brand());
            holder.tv_type.setText(order.getType());
            holder.tv_number.setText(order.getPart_code());
            holder.tv_size.setText(order.getPart_unit());

            holder.tv_price.setTag(order);
            holder.tv_price.setText(order.getPrice());
            holder.tv_price.clearFocus();


            holder.tv_count.setTag(order);
            holder.tv_count.setText(order.getCount());
            holder.tv_count.clearFocus();

            holder.tv_total_money.setText(order.getTotalMoney());
            return convertView;
        }

        class ViewHolder {
            ImageView iv_image;
            TextView tv_brand;
            TextView tv_type;
            TextView tv_number;
            TextView tv_size;
            EditText tv_price;
            EditText tv_count;
            TextView tv_total_money;
        }
    }
//    private class MyAdapter extends CommonAdapter<Order> {
//        ImageView iv_image;
//        TextView tv_brand;
//        TextView tv_type;
//        TextView tv_number;
//        TextView tv_size;
//        EditText tv_price;
//        EditText tv_count;
//        TextView tv_total_money;
//        Context mContext;
//        int index;
//        MyWatcher mPriceWatcher;
//        MyWatcher mCountWatcher;
//
//        public MyAdapter(Context context, List<Order> datas, int layoutId) {
//            super(context, datas, layoutId);
//            mContext = context;
//        }
//
//        @Override
//        public void convert(ViewHolder holder, final Order data, final int position) {
//            final String id = data.getId();
//            iv_image = holder.getView(R.id.iv_image);
//            tv_brand = holder.getView(R.id.tv_brand);
//            tv_type = holder.getView(R.id.tv_type);
//            tv_number = holder.getView(R.id.tv_number);
//            tv_size = holder.getView(R.id.tv_size);
//            tv_price = holder.getView(R.id.tv_price);
//            tv_count = holder.getView(R.id.tv_count);
//            tv_total_money = holder.getView(R.id.tv_total_money);
//
//
//
//            Glide.with(mContext)
//                    .load(data.getPart_img_short())
//                    .crossFade()
//                    .into(iv_image);
//            tv_brand.setText(data.getPart_brand());
//            tv_type.setText(data.getType());
//
//            tv_number.setText(data.getPart_code());
//            tv_size.setText(data.getPart_unit());
//
//            if (priceMap.containsKey(id)) {
//                tv_price.setText(priceMap.get(id));
//            }else{
//                tv_price.setText("");
//            }
//            tv_price.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    if (event.getAction() == MotionEvent.ACTION_UP) {
//                        index = position;
//                    }
//                    return false;
//                }
//            });
//            tv_price.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                @Override
//                public void onFocusChange(View v, boolean hasFocus) {
//                    EditText et = (EditText) v;
//                    if (mPriceWatcher == null) {
//                        mPriceWatcher = new MyWatcher(0, id);
//                    }
//                    if (hasFocus) {
//                        et.addTextChangedListener(mPriceWatcher);
//                    } else {
//                        et.removeTextChangedListener(mPriceWatcher);
//                        mPriceWatcher = null;
//                    }
//                }
//            });
//
//            if (countMap.containsKey(id)) {
//                tv_count.setText(countMap.get(id));
//            }else{
//                tv_count.setText("");
//            }
//
//            tv_count.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    if (event.getAction() == MotionEvent.ACTION_UP) {
//                        index = position;
//                    }
//                    return false;
//                }
//            });
//            tv_count.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                @Override
//                public void onFocusChange(View v, boolean hasFocus) {
//                    EditText et = (EditText) v;
//                    if (mCountWatcher == null) {
//                        mCountWatcher = new MyWatcher(1,id);
//                    }
//                    if (hasFocus) {
//                        et.addTextChangedListener(mCountWatcher);
//                    } else {
//                        et.removeTextChangedListener(mCountWatcher);
//                        mCountWatcher = null;
//                    }
//                }
//            });
//            if (totalMap.containsKey(id)) {
//                tv_total_money.setText(totalMap.get(id));
//            }else{
//                tv_total_money.setText("");
//            }
//        }
//
//        class MyWatcher implements TextWatcher {
//            int type;
//            String id;
//            public MyWatcher(int type, String id) {
//                this.type = type;
//                this.id = id;
//            }
//
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count,
//                                          int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before,
//                                      int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (type == 0) {
//                    float price = s.toString().isEmpty() ? 0f : Float.parseFloat(s.toString());
//                    int count = tv_count.getText().toString().trim().isEmpty() ?
//                            0 : Integer.parseInt(tv_count.getText().toString().trim());
//                    float total = price * count;
//                    priceMap.put(id, price + "");
//                    totalMap.put(id, total + "");
//                  //  tv_total_money.setText(total + "");
//                } else {
//                    float price = tv_price.getText().toString().trim().isEmpty() ?
//                            0f : Integer.parseInt(tv_price.getText().toString().trim());
//                    int count = s.toString().isEmpty() ? 0 : Integer.parseInt(s.toString());
//                    float total = price * count;
//                    countMap.put(id, count + "");
//                    totalMap.put(id, total + "");
//                //    tv_total_money.setText(total + "");
//
//                }
//                notifyDataSetChanged();
//            }
//
//        }
//       }
}
