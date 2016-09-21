package com.fangzhi.app.main;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.fangzhi.app.R;
import com.fangzhi.app.base.BaseActivity;
import com.fangzhi.app.bean.Houses;
import com.fangzhi.app.location.LocationManager;
import com.fangzhi.app.main.adapter.HousesAdapter;
import com.fangzhi.app.main.city.CityActivity;
import com.fangzhi.app.main.house_type.HouseTypeActivity;
import com.fangzhi.app.tools.SPUtils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<MainPresenter, MainModel> implements MainContract.View, RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.recycler_view)
    EasyRecyclerView recyclerView;
    @Bind(R.id.et_keyword)
    EditText etKeyword;
    @Bind(R.id.tv_location)
    TextView tvLocation;
    @Bind(R.id.cb_all)
    CheckBox cbAll;

    private HousesAdapter adapter;
    private int page = 0;
    boolean isSearch = false;
    String mKeyword = "";
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
                    SPUtils.putString(MainActivity.this, "city_name", name);
                    SPUtils.putString(MainActivity.this, "city_code", name);
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
                intent.putExtra("id",id);
                intent.putExtra("name",name);
                intent.setClass(MainActivity.this, HouseTypeActivity.class);
                startActivity(intent);
            }
        });

        //开始定位
        String currentCity = SPUtils.getString(this, "city_name", null);
        if (currentCity == null) {
            tvLocation.setText("正在定位");
            LocationManager.getInstance().startLocation(locationListener);
        } else {
            tvLocation.setText(currentCity);
            onRefresh();
        }

    }

    @OnCheckedChanged(R.id.cb_all)
    public void onChanged(boolean isChecked){
        if(isChecked){
            onRefresh();
        }else{
            mKeyword = etKeyword.getText().toString().trim();
            if (!mKeyword.isEmpty()) {
                onSearch();
            }
        }
    }

    @OnClick(R.id.tv_location)
    public void pickCity() {
        startActivityForResult(new Intent(this, CityActivity.class), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String city = (String) SPUtils.get(this, "city_name", "定位失败");
        tvLocation.setText(city);
        if (resultCode == RESULT_OK) {
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
                if(!cbAll.isChecked()) {
                    onSearch();
                }else{
                    cbAll.setChecked(false);
                }
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
        return SPUtils.getString(this, "token", "");
    }

    @Override
    public String getAreaCode() {

        return SPUtils.getString(this, "city_code", "");
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

    @Override
    public void onLoadMore() {
        if (!cbAll.isChecked()) {
            if (!mKeyword.isEmpty()) {
                mPresenter.searchHouseList();
            }else{
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
        LocationManager.getInstance().removeListener(locationListener);
    }
    public void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(getCurrentFocus()
                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
