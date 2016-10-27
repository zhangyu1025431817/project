package com.fangzhi.app.main.list;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.fangzhi.app.R;
import com.fangzhi.app.base.RxBus;
import com.fangzhi.app.bean.Order;
import com.fangzhi.app.main.adapter.OrderAdapter;
import com.fangzhi.app.main.adapter.OrderViewHolder;
import com.fangzhi.app.tools.BeanCloneUtil;
import com.fangzhi.app.view.DialogInput;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import rx.functions.Action1;

/**
 * Created by smacr on 2016/9/23.
 */
public class ListOrderActivity extends SwipeBackActivity {
    @Bind(R.id.recycler_view)
    EasyRecyclerView recyclerView;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    TextView tvTotalMoney;
    View viewFooter;
    private OrderAdapter mAdapter;
    private LayoutInflater mInflater;
    private Map<Integer, Float> moneyMap = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_order_list);
        ButterKnife.bind(this);
        mInflater = LayoutInflater.from(this);
        viewFooter = mInflater.inflate(R.layout.view_order_list_footer, null);
        tvTotalMoney = (TextView) viewFooter.findViewById(R.id.tv_total_money);
        tvTitle.setText("设计清单");
        Intent intent = getIntent();
        if (intent.hasExtra("list")) {
            List<Order> list = (List<Order>) intent.getSerializableExtra("list");
            List<Order> tempOrderList = new ArrayList<>();
            for(Order order : list){
                String[] images = order.getPart_img_short().split(";");
                for (String image : images){
                    Order tempOrder = BeanCloneUtil.cloneTo(order);
                    tempOrder.setPart_img_short(image);
                    tempOrderList.add(tempOrder);
                }
            }
            mAdapter = new OrderAdapter(this);
            recyclerView.setAdapter(mAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            mAdapter.addAll(tempOrderList);
            mAdapter.addHeader(new RecyclerArrayAdapter.ItemView() {

                @Override
                public View onCreateView(ViewGroup parent) {
                    return mInflater.inflate(R.layout.view_order_list_header, null);
                }

                @Override
                public void onBindView(View headerView) {

                }
            });
            mAdapter.addFooter(new RecyclerArrayAdapter.ItemView() {

                @Override
                public View onCreateView(ViewGroup parent) {
                    return viewFooter;
                }

                @Override
                public void onBindView(View headerView) {

                }
            });
            RxBus.$().register("price").
                    subscribe(new Action1<Object>() {
                                  @Override
                                  public void call(Object o) {
                                      final OrderViewHolder.Money money = (OrderViewHolder.Money) o;
                                      final Order order = mAdapter.getItem(money.position);
                                      new DialogInput(ListOrderActivity.this, Float.parseFloat(order.getPrice()), 0, new DialogInput.ClickListenerInterface() {

                                          @Override
                                          public void doConfirm(String priceStr) {
                                              int count = Integer.parseInt(order.getCount());
                                              float mf = Float.parseFloat(priceStr);
                                              float strP = count * mf;
                                              String strT = strP + "";
                                              order.setPrice(priceStr);
                                              order.setTotalMoney(strT);
                                              moneyMap.put(money.position, strP);
                                              mAdapter.notifyItemChanged(money.position+1);
                                              float totalMoney = 0;
                                              for (Integer key : moneyMap.keySet()) {
                                                  totalMoney += moneyMap.get(key);
                                              }
                                              tvTotalMoney.setText(String.valueOf(totalMoney));
                                          }
                                      }).show();

                                  }
                              }
                    );


            RxBus.$().register("count").
                    subscribe(new Action1<Object>() {
                                  @Override
                                  public void call(Object o) {
                                      final OrderViewHolder.Money money = (OrderViewHolder.Money) o;
                                      final Order order = mAdapter.getItem(money.position);
                                      new DialogInput(ListOrderActivity.this, Integer.parseInt(order.getCount()), 1, new DialogInput.ClickListenerInterface() {

                                          @Override
                                          public void doConfirm(String countStr) {
                                              float price = Float.parseFloat(order.getPrice());
                                              int count = Integer.parseInt(countStr);
                                              float strP = count * price;
                                              String strT = strP + "";
                                              order.setCount(countStr);
                                              order.setTotalMoney(strT);
                                              moneyMap.put(money.position, strP);
                                              mAdapter.notifyDataSetChanged();
                                              float totalMoney = 0;
                                              for (Integer key : moneyMap.keySet()) {
                                                  totalMoney += moneyMap.get(key);
                                              }
                                              tvTotalMoney.setText(totalMoney + "");
                                          }
                                      }).show();

                                  }
                              }
                    );

        }


    }

    @OnClick(R.id.iv_back)
    public void onReturn() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.$().unregister("price");
        RxBus.$().unregister("count");
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
}
