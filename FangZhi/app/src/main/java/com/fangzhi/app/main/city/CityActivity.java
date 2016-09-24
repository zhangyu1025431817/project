package com.fangzhi.app.main.city;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.fangzhi.app.R;
import com.fangzhi.app.base.BaseActivity;
import com.fangzhi.app.bean.City;
import com.fangzhi.app.config.SpKey;
import com.fangzhi.app.db.DBManager;
import com.fangzhi.app.location.LocationManager;
import com.fangzhi.app.login.LoginActivityNew;
import com.fangzhi.app.tools.SPUtils;
import com.fangzhi.app.view.DialogDelegate;
import com.fangzhi.app.view.SideLetterBar;
import com.fangzhi.app.view.SweetAlertDialogDelegate;

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
    EditText etSearch;
    //输入框清除
    @Bind(R.id.iv_search_clear)
    ImageView ivClear;
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
                mCityAdapter.updateLocateState(LocateState.SUCCESS, name);
                SPUtils.putString(CityActivity.this, "city_code", code);
                SPUtils.putString(CityActivity.this, "city_name", name);
                LocationManager.getInstance().stopLocation();
            } else {
                mCityAdapter.updateLocateState(LocateState.FAILED, "");
            }
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
                SPUtils.putString(CityActivity.this, "city_code", code);
                SPUtils.putString(CityActivity.this, "city_name", mResultAdapter.getItem(position).getArea_cname());
                back(RESULT_OK);
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
                    ivClear.setVisibility(View.GONE);
                    viewEmpty.setVisibility(View.GONE);
                    lvResultCity.setVisibility(View.GONE);
                } else {
                    ivClear.setVisibility(View.VISIBLE);
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
        back(RESULT_CANCELED);
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
                SPUtils.putString(CityActivity.this, "city_code", city.getId());
                SPUtils.putString(CityActivity.this, "city_name", city.getArea_cname());
                back(RESULT_OK);
            }

            @Override
            public void onLocateClick() {
                mCityAdapter.updateLocateState(LocateState.LOCATING, null);
                LocationManager.getInstance().startLocation(locationListener);
            }
        });
        lvAllCity.setAdapter(mCityAdapter);
        String name = SPUtils.getString(CityActivity.this, "city_name", "定位失败");
        mCityAdapter.updateLocateState(LocateState.SUCCESS, name);
        dialogDelegate.clearDialog();
    }

    @Override
    public String getToken() {
        return SPUtils.getString(this,SpKey.TOKEN,"");
    }

    @Override
    public void tokenInvalid(String msg) {
        dialogDelegate.showErrorDialog(msg, msg, new DialogDelegate.OnDialogListener() {
            @Override
            public void onClick() {
                Intent intent = new Intent(CityActivity.this, LoginActivityNew.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(new Intent(CityActivity.this, LoginActivityNew.class));
            }
        });
    }

    @Override
    public void onError(String msg) {
        dialogDelegate.stopProgressWithFailed(msg,msg);
    }

    public void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(getCurrentFocus()
                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
