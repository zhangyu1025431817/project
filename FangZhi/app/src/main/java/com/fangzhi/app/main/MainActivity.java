package com.fangzhi.app.main;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.fangzhi.app.R;
import com.fangzhi.app.base.BaseActivity;
import com.fangzhi.app.bean.Houses;
import com.fangzhi.app.config.SpKey;
import com.fangzhi.app.location.LocationManager;
import com.fangzhi.app.login.LoginActivityNew;
import com.fangzhi.app.main.adapter.HousesAdapter;
import com.fangzhi.app.main.city.CityActivity;
import com.fangzhi.app.main.house_type.HouseTypeActivity;
import com.fangzhi.app.tools.SPUtils;
import com.fangzhi.app.tools.T;
import com.fangzhi.app.view.ClearEditText;
import com.fangzhi.app.view.DialogDelegate;
import com.fangzhi.app.view.SweetAlertDialogDelegate;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<MainPresenter, MainModel> implements MainContract.View, RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.recycler_view)
    EasyRecyclerView recyclerView;
    @Bind(R.id.et_keyword)
    ClearEditText etKeyword;
    @Bind(R.id.tv_location)
    TextView tvLocation;

    private HousesAdapter adapter;
    private int page = 0;
    String mKeyword = "";
    DialogDelegate dialogDelegate;
    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation loc) {
            if (null != loc) {
                //解析定位结果
                String code = loc.getAdCode();
                String name = loc.getCity();
                if (code.isEmpty() || name.isEmpty()) {
                    tvLocation.setText("定位失败");
                } else {
                    tvLocation.setText(name);
                    SPUtils.putString(MainActivity.this, SpKey.CITY_NAME, name);
                    SPUtils.putString(MainActivity.this, SpKey.CITY_CODE, code);
                    onRefresh();
                }
                LocationManager.getInstance().stopLocation();
            } else {
                tvLocation.setText("定位失败");
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        String currentCity = SPUtils.getString(this, SpKey.CITY_NAME, "");
        if (currentCity.isEmpty()) {
            startActivityForResult(new Intent(this, CityActivity.class), 1);
        }

        setSwipeBackEnable(false);
        recyclerView.setRefreshListener(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new HousesAdapter(this);
        adapter.setMore(R.layout.view_more, this);

        recyclerView.setAdapterWithProgress(adapter);
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String id = adapter.getItem(position).getId();
                String name = adapter.getItem(position).getPre_cname();
                Intent intent = new Intent();
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.setClass(MainActivity.this, HouseTypeActivity.class);
                startActivity(intent);
            }
        });

//        //开始定位
//        String currentCity = SPUtils.getString(this, SpKey.CITY_NAME, "");
//        if (currentCity.isEmpty()) {
//            tvLocation.setText("正在定位");
//            LocationManager.getInstance().startLocation(locationListener);
//        } else {
        if (!currentCity.isEmpty()) {
            tvLocation.setText(currentCity);
            onRefresh();
        }
        //     }
        dialogDelegate = new SweetAlertDialogDelegate(this);
        etKeyword.addOnClearListener(new ClearEditText.OnClearListener() {
            @Override
            public void onClear() {
                mKeyword = "";
                isSearch = false;
                onRefresh();
                closeKeyboard();
            }
        });
    }


    @OnClick(R.id.tv_location)
    public void pickCity() {
        startActivityForResult(new Intent(this, CityActivity.class), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String city = (String) SPUtils.get(this, SpKey.CITY_NAME, "定位失败");
        tvLocation.setText(city);
        if (resultCode == 1) {
            mKeyword = "";
            etKeyword.setText(mKeyword);
            isSearch = false;
            recyclerView.setRefreshing(false);
            onRefresh();
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        //这里注意要作判断处理，ActionDown、ActionUp都会回调到这里，不作处理的话就会调用两次
        if (KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction()) {
            mKeyword = etKeyword.getText().toString().trim();
            if (!mKeyword.isEmpty()) {
                isSearch = true;
                onSearch();
                closeKeyboard();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void showHousesList(List<Houses.House> houses) {
        adapter.addAll(houses);
        recyclerView.setRefreshing(false);
    }

    @Override
    public String getToken() {
        return SPUtils.getString(this, SpKey.TOKEN, "");
    }

    @Override
    public String getAreaCode() {

        return SPUtils.getString(this, SpKey.CITY_CODE, "");
    }

    @Override
    public String getKey() {
        return mKeyword;
    }

    @Override
    public int getPageSize() {
        return 20;
    }

    @Override
    public int getCurrentPage() {
        return page;
    }

    private boolean isSearch = false;
    @Override
    public void onLoadMore() {
        if (isSearch) {
            if (!mKeyword.isEmpty()) {
                mPresenter.searchHouseList();
            } else {
                recyclerView.setRefreshing(false);
            }
        } else {
            mPresenter.getHousesList();
        }
        page++;
    }

    @Override
    public void onRefresh() {
        recyclerView.setRefreshing(true);
        page = 1;
        adapter.clear();
        onLoadMore();
    }

    private void onSearch() {
        recyclerView.setRefreshing(true);
        page = 1;
        adapter.clear();
        onLoadMore();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialogDelegate.clearDialog();
        LocationManager.getInstance().removeListener(locationListener);
    }

    public void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(getCurrentFocus()
                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void tokenInvalid(String msg) {
        dialogDelegate.showErrorDialog(msg, msg, new DialogDelegate.OnDialogListener() {
            @Override
            public void onClick() {
                Intent intent = new Intent(MainActivity.this, LoginActivityNew.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(new Intent(MainActivity.this, LoginActivityNew.class));
            }
        });
    }


    @Override
    public void onError(String msg) {
        T.showShort(this, msg);
        recyclerView.setRefreshing(false);
    }

}
