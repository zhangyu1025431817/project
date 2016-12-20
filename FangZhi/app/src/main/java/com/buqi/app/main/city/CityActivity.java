package com.buqi.app.main.city;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.buqi.app.R;
import com.buqi.app.base.BaseActivity;
import com.buqi.app.bean.City;
import com.buqi.app.config.SpKey;
import com.buqi.app.db.DBManager;
import com.buqi.app.location.LocationManager;
import com.buqi.app.login.LoginActivity;
import com.buqi.app.tools.SPUtils;
import com.buqi.app.view.ClearEditText;
import com.buqi.app.view.DialogDelegate;
import com.buqi.app.view.SideLetterBar;
import com.buqi.app.view.SweetAlertDialogDelegate;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by smacr on 2016/9/19.
 */
public class CityActivity extends BaseActivity<CityPresenter, CityModel> implements CityContract.View {
    //所有城市
    @Bind(R.id.listview_all_city)
    ListView lvAllCity;
    //查询结果
    @Bind(R.id.listview_search_result)
    ListView lvResultCity;
    //右侧滑动选择
    @Bind(R.id.side_letter_bar)
    SideLetterBar mLetterBar;
    //搜索框
    @Bind(R.id.et_search)
    ClearEditText etSearch;

    //表层显示字母
    @Bind(R.id.tv_letter_overlay)
    TextView tvOverlay;
    //搜索结果空
    @Bind(R.id.empty_view)
    ViewGroup viewEmpty;
    private DBManager dbManager;
    private CityListAdapter mCityAdapter;
    private ResultListAdapter mResultAdapter;
    private List<City> cityList = new ArrayList<>();
    DialogDelegate dialogDelegate;
    private static final int REQUECT_CODE_SDCARD = 2;
    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation loc) {
            if (null != loc) {
                //解析定位结果
                String name = loc.getCity();
                String code = loc.getAdCode();
                if (name.isEmpty() || code.isEmpty()) {
                    mCityAdapter.updateLocateState(LocateState.FAILED, "");
                } else {
                    mCityAdapter.updateLocateState(LocateState.SUCCESS, name);
                    SPUtils.putString(CityActivity.this, SpKey.CITY_CODE, code);
                    SPUtils.putString(CityActivity.this, SpKey.CITY_NAME, name);
                    back(1);
                }
            } else {
                mCityAdapter.updateLocateState(LocateState.FAILED, "");
            }
            LocationManager.getInstance().stopLocation();
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_city_list;
    }

    @Override
    public void initView() {
        initData();
    }

    private void initData() {
        dbManager = new DBManager(this);
        dialogDelegate = new SweetAlertDialogDelegate(this);
        mResultAdapter = new ResultListAdapter(this, null);
        lvResultCity.setAdapter(mResultAdapter);
        lvResultCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String code = mResultAdapter.getItem(position).getId();
                SPUtils.putString(CityActivity.this, SpKey.CITY_CODE, code);
                SPUtils.putString(CityActivity.this, SpKey.CITY_NAME, mResultAdapter.getItem(position).getArea_cname());
                back(1);
            }
        });
        mLetterBar.setOverlay(tvOverlay);
        mLetterBar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                int position = mCityAdapter.getLetterPosition(letter);
                lvAllCity.setSelection(position);
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String keyword = s.toString();
                if (TextUtils.isEmpty(keyword)) {
                    viewEmpty.setVisibility(View.GONE);
                    lvResultCity.setVisibility(View.GONE);
                } else {
                    lvResultCity.setVisibility(View.VISIBLE);
                    List<City> result = dbManager.searchCity(keyword);
                    if (result == null || result.size() == 0) {
                        viewEmpty.setVisibility(View.VISIBLE);
                    } else {
                        viewEmpty.setVisibility(View.GONE);
                        mResultAdapter.changeData(result);
                    }
                }
            }
        });
        dialogDelegate.showProgressDialog(true, "正在加载城市信息...");
        mPresenter.getCityList();

    }

    private void back(int code) {
        Intent data = new Intent();
        setResult(code, data);
        closeKeyboard();
        finish();
    }

    @OnClick(R.id.iv_back)
    public void onFinish() {
        back(2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.close();
    }

    @Override
    public void setCities(List<City> list) {
        if (list == null || list.isEmpty()) {
            List<City> tempList = dbManager.getAllCities();
            if (tempList != null && !tempList.isEmpty()) {
                cityList.addAll(tempList);
            }
        } else {
            dbManager.clearTable();
            if (dbManager.insertBySql(list)) {
                cityList.clear();
                cityList.addAll(dbManager.getAllCities());
            }
        }
        mCityAdapter = new CityListAdapter(this, cityList);
        mCityAdapter.setOnCityClickListener(new CityListAdapter.OnCityClickListener() {
            @Override
            public void onCityClick(City city) {
                SPUtils.putString(CityActivity.this, SpKey.CITY_CODE, city.getId());
                SPUtils.putString(CityActivity.this, SpKey.CITY_NAME, city.getArea_cname());
                back(1);
            }

            @Override
            public void onLocateClick() {
                mCityAdapter.updateLocateState(LocateState.LOCATING, null);
                //请求权限
                if(!MPermissions.shouldShowRequestPermissionRationale(CityActivity.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION,REQUECT_CODE_SDCARD))
                {
                    MPermissions.requestPermissions(CityActivity.this, REQUECT_CODE_SDCARD,
                            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);
                }
            //    LocationManager.getInstance().startLocation(locationListener);
            }
        });
        lvAllCity.setAdapter(mCityAdapter);
        String name = SPUtils.getString(CityActivity.this, SpKey.CITY_NAME, "");
        if (name.isEmpty()) {
           // mCityAdapter.updateLocateState(LocateState.FAILED, name);
            //请求权限
            MPermissions.requestPermissions(this,REQUECT_CODE_SDCARD,
                    Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION);
            //定位
            mCityAdapter.updateLocateState(LocateState.LOCATING, null);

        } else {
            mCityAdapter.updateLocateState(LocateState.SUCCESS, name);
        }
        dialogDelegate.clearDialog();
    }

    @Override
    public String getToken() {
        return SPUtils.getString(this, SpKey.TOKEN, "");
    }

    @Override
    public void tokenInvalid(String msg) {
        dialogDelegate.showErrorDialog(msg, msg, new DialogDelegate.OnDialogListener() {
            @Override
            public void onClick() {
                Intent intent = new Intent(CityActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(new Intent(CityActivity.this, LoginActivity.class));
            }
        });
    }

    @Override
    public void onError(String msg) {
        dialogDelegate.stopProgressWithFailed(msg, msg);
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @PermissionGrant(REQUECT_CODE_SDCARD)
    public void requestSdcardSuccess()
    {
      //  Toast.makeText(this, "GRANT ACCESS SDCARD!", Toast.LENGTH_SHORT).show();
        LocationManager.getInstance().startLocation(locationListener);
    }

    @PermissionDenied(REQUECT_CODE_SDCARD)
    public void requestSdcardFailed()
    {
      //  Toast.makeText(this, "DENY ACCESS SDCARD!", Toast.LENGTH_SHORT).show();
    }
}
