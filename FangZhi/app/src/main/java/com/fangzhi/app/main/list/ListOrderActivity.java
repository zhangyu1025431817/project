package com.fangzhi.app.main.list;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fangzhi.app.MyApplication;
import com.fangzhi.app.R;
import com.fangzhi.app.bean.AttachOrderResponseBean;
import com.fangzhi.app.bean.Order;
import com.fangzhi.app.config.SpKey;
import com.fangzhi.app.network.MySubscriber;
import com.fangzhi.app.network.Network;
import com.fangzhi.app.network.http.api.ErrorCode;
import com.fangzhi.app.tools.KeyboardChangeListener;
import com.fangzhi.app.tools.SPUtils;
import com.fangzhi.app.tools.T;
import com.fangzhi.app.view.XEditText;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by smacr on 2016/9/23.
 */
public class ListOrderActivity extends AppCompatActivity implements KeyboardChangeListener.KeyBoardListener{
    @Bind(R.id.recycler_view)
    ListView listView;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_contact_factory)
    TextView tvAddress;
    @Bind(R.id.tv_total_money)
    TextView tvTotalMoney;
    List<Order> mList = new ArrayList<>();
    LayoutInflater mInflater;
    Map<String, String> priceMap = new HashMap<>();
    MyAdapter mAdapter;
    private KeyboardChangeListener mKeyboardChangeListener;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
//            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
//            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
//        }
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        try{
            ActionBar actionBar = getSupportActionBar();
            actionBar.hide();
        }catch (Exception e){
            e.printStackTrace();
        }

        setContentView(R.layout.activity_order_list);
        ButterKnife.bind(this);
        mInflater = LayoutInflater.from(this);
        tvTitle.setText("设计清单");
        Intent intent = getIntent();

        if (intent.hasExtra("list")) {
            List<Order> tempList = (List<Order>) intent.getSerializableExtra("list");
            HashMap<String, String> tempMap = (HashMap<String, String>) SPUtils.getObject(this, "price");
            if (tempList != null) {
                if (tempMap != null) {
                    priceMap = tempMap;
                    for (Order order : tempList) {
                        if (tempMap.containsKey(order.getPart_name())) {
                            order.setPrice(priceMap.get(order.getPart_name()));
                        }
                    }
                }
                mList.addAll(tempList);
            }
//            for (Order order : tempList) {
//                String[] images = order.getPart_img_short().split(";");
//                for (String image : images) {
//                    Order tempOrder = BeanCloneUtil.cloneTo(order);
//                    tempOrder.setPart_img_short(image);
//                    mList.add(tempOrder);
//                }
//            }
        }

        listView.setAdapter(mAdapter = new MyAdapter());

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:    //当停止滚动时
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:    //滚动时
                        //没错，下面这一坨就是隐藏软键盘的代码
                        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                                .hideSoftInputFromWindow(ListOrderActivity.this.getCurrentFocus().getWindowToken()
                                        , InputMethodManager.HIDE_NOT_ALWAYS);
                        onWindowFocusChanged(true);
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:   //手指抬起，但是屏幕还在滚动状态
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });

        getAttachPart();
        mKeyboardChangeListener = new KeyboardChangeListener(this);
        mKeyboardChangeListener.setKeyBoardListener(this);

    }

    @OnClick(R.id.iv_back)
    public void onReturn() {
        SPUtils.putObject(this, "price", priceMap);
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

    @OnClick(R.id.tv_contact_factory)
    public void onGoto() {
        //联系厂家那个按钮显示与否
        String url = SPUtils.getString(MyApplication.getContext(),
                SpKey.FACTORY_ADDRESS, "");
        if (url.isEmpty()) {
            T.showShort(this, "暂未提供");
            return;
        }
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        startActivity(intent);
    }

    private float mTotalMoney;

    @Override
    public void onKeyboardChange(boolean isShow, int keyboardHeight) {
        Log.e("onKeyboardChange", "onKeyboardChange() called with: " + "isShow = [" + isShow + "], keyboardHeight = [" + keyboardHeight + "]");
    }

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
                        float price;
                        try {
                            price = s.toString().isEmpty() ? 0f : Float.parseFloat(s.toString());
                        } catch (Exception e) {
                            price = 0f;
                        }
                        int count;
                        try {
                            count = Integer.parseInt(bean.getCountNumber().isEmpty() ? "0" : bean.getCountNumber());
                        }catch (Exception e){
                            count = 0;
                        }
                        float total = price * count;
                        if (price == 0f) {
                            bean.setPrice("");
                        } else {
                            bean.setPrice(price + "");
                        }
                        bean.setTotalMoney(total + "");
                        holder.tv_total_money.setText(total + "");
                        mTotalMoney = mTotalMoney + (total - oldMoney);
                        tvTotalMoney.setText("总价:¥" + mTotalMoney);
                        if (price > 0f) {
                            priceMap.put(bean.getPart_name(), price + "");
                        } else {
                            if (priceMap.containsKey(bean.getPart_name())) {
                                priceMap.remove(bean.getPart_name());
                            }
                        }
                    }
                });
                holder.tv_count = (XEditText) convertView.findViewById(R.id.tv_count);

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
                        float price = Float.parseFloat(bean.getPrice().isEmpty() ? "0" : bean.getPrice());
                        int count = s.toString().isEmpty() ? 0 : Integer.parseInt(s.toString());
                        float total = price * count;

                        bean.setCountNumber(count == 0? "":count+"");
                        bean.setTotalMoney(total + "");
                        holder.tv_total_money.setText(total + "");
                        mTotalMoney = mTotalMoney + (total - oldMoney);
                        tvTotalMoney.setText("总价:¥" + mTotalMoney);
                    }
                });
                holder.tv_count.setDrawableLeftListener(new XEditText.DrawableLeftListener() {
                    @Override
                    public void onDrawableLeftClick(View view) {
                        int number;
                        try{
                            number = Integer.parseInt(holder.tv_count.getText().toString());
                        }catch (Exception e){
                            number = 0;
                        }
                        number = number - 1;
                        if (number > 0) {
                            holder.tv_count.setText(number + "");
                        } else {
                            holder.tv_count.setText("");
                        }
                    }
                });
                holder.tv_count.setDrawableRightListener(new XEditText.DrawableRightListener() {
                    @Override
                    public void onDrawableRightClick(View view) {
                        int number;
                        try{
                            number = Integer.parseInt(holder.tv_count.getText().toString());
                        }catch (Exception e){
                            number = 0;
                        }
                        number = number + 1;
                        holder.tv_count.setText(number + "");
                    }
                });
                holder.tv_total_money = (TextView) convertView.findViewById(R.id.tv_total_money);
                convertView.setTag(holder);
                AutoUtils.autoSize(convertView);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Glide.with(ListOrderActivity.this)
                    .load(order.getPart_img_short())
                    .crossFade()
                    .into(holder.iv_image);
            holder.tv_brand.setText(order.getPart_brand());
            holder.tv_type.setText(order.getType());
            holder.tv_number.setText(order.getPart_name());
            holder.tv_size.setText(order.getPart_unit());

            holder.tv_price.setTag(order);
            holder.tv_price.setText(order.getPrice());
            holder.tv_price.clearFocus();


            holder.tv_count.setTag(order);
            holder.tv_count.setText(order.getCountNumber());
            holder.tv_count.clearFocus();

            holder.tv_total_money.setText(order.getTotalMoney());
            if (position % 2 == 0) {
                convertView.setBackgroundColor(getResources().getColor(R.color.white_pressed));
            } else {
                convertView.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            }
            return convertView;
        }

        class ViewHolder {
            ImageView iv_image;
            TextView tv_brand;
            TextView tv_type;
            TextView tv_number;
            TextView tv_size;
            EditText tv_price;
            XEditText tv_count;
            TextView tv_total_money;
        }
    }

    private void getAttachPart() {
        String token = SPUtils.getString(this, SpKey.TOKEN, "");
        Network.getApiService().getAttachOrder(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<AttachOrderResponseBean>() {
                    @Override
                    public void onNext(AttachOrderResponseBean bean) {
                        if (ErrorCode.SUCCEED.equals(bean.getError_code())) {
                            List<AttachOrderResponseBean.AttachOrder> list = bean.getAttachPartList();
                            if (list != null && !list.isEmpty()) {
                                for (AttachOrderResponseBean.AttachOrder attachOrder : list) {
                                    Order order = new Order();
                                    order.setId(attachOrder.getId());
                                    order.setPart_name(attachOrder.getPart_name());
                                    order.setPart_img_short(attachOrder.getPart_img_short());
                                    order.setPart_brand(attachOrder.getPart_brand());
                                    order.setType(attachOrder.getType_name());
                                    order.setPart_code(attachOrder.getPart_name());
                                    if (priceMap.containsKey(order.getPart_name())) {
                                        order.setPrice(priceMap.get(order.getPart_name()));
                                    } else {
                                        order.setPrice("");
                                    }
                                    order.setCountNumber("");
                                    order.setTotalMoney("0.0");
                                    order.setPart_unit(attachOrder.getPart_unit());
                                    mList.add(order);
                                }
                                mAdapter.notifyDataSetChanged();
                            }

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }
//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//       // super.onWindowFocusChanged(hasFocus);
//        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
//            View decorView = getWindow().getDecorView();
//            decorView.setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//        }
//    }
}
