package com.fangzhi.app.main;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.fangzhi.app.R;
import com.fangzhi.app.base.BaseActivity;
import com.fangzhi.app.bean.City;
import com.fangzhi.app.bean.HousesResponseBean;
import com.fangzhi.app.main.adapter.HousesAdapter;
import com.fangzhi.app.tools.T;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<MainPresenter, MainModel> implements MainContract.View, RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.recycler_view)
    EasyRecyclerView recyclerView;
    @Bind(R.id.et_keyword)
    EditText etKeyword;
    @Bind(R.id.tv_location)
    TextView tvLocation;

    private HousesAdapter adapter;
    private int page = 0;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = new AMapLocationClientOption();

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }
    @Override
    public void initView() {
        initLocation();
        startLocation();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setRefreshListener(this);

        adapter = new HousesAdapter(this);
        adapter.setMore(R.layout.view_more, this);
        adapter.setNoMore(R.layout.view_nomore);
        recyclerView.setAdapterWithProgress(adapter);
        onRefresh();

    }
    @OnClick(R.id.tv_location)
    public void pickCity(){
        startActivityForResult(new Intent(this, City.class),1);
    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        //这里注意要作判断处理，ActionDown、ActionUp都会回调到这里，不作处理的话就会调用两次
        if (KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction()) {
            T.showShort(this,"回车");
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
    @Override
    public void showHousesList(List<HousesResponseBean.Houses> housingEstateList) {
        adapter.addAll(housingEstateList);
        recyclerView.setRefreshing(false);
    }

    @Override
    public void onLoadMore() {
        mPresenter.getHousesList("500000", 10, page);
        page++;
    }

    @Override
    public void onRefresh() {
        recyclerView.setRefreshing(true);
        page = 0;
        adapter.clear();
        onLoadMore();
    }
    /**
     * 初始化定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void initLocation(){
        //初始化client
        locationClient = new AMapLocationClient(this.getApplicationContext());
        //设置定位参数
        locationClient.setLocationOption(getDefaultOption());
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
    }
    /**
     * 默认的定位参数
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private AMapLocationClientOption getDefaultOption(){
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(1000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是ture

        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        return mOption;
    }
    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation loc) {
            Log.e("AMapLocationListener","已经回调");
            if (null != loc) {
                //解析定位结果
                //      String result = Utils.getLocationStr(loc);
                tvLocation.setText(loc.getCity());
                //      Log.e("AMapLocationListener",result);
                locationClient.stopLocation();
            } else {
                tvLocation.setText("定位失败");
                Log.e("AMapLocationListener","定位失败loc is null");
            }
        }
    };
    /**
     * 开始定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void startLocation(){
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }


}
