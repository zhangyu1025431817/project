package com.fangzhi.app.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.fangzhi.app.R;
import com.fangzhi.app.base.BaseActivity;
import com.fangzhi.app.bean.County;
import com.fangzhi.app.bean.Houses;
import com.fangzhi.app.config.FactoryListInfo;
import com.fangzhi.app.config.SpKey;
import com.fangzhi.app.login.LoginActivity;
import com.fangzhi.app.main.adapter.HousesAdapter;
import com.fangzhi.app.main.adapter.NoDoubleClickListener;
import com.fangzhi.app.main.city.CityActivity;
import com.fangzhi.app.main.ddd.ThreeDimensionalActivity;
import com.fangzhi.app.main.house_type.HouseTypeActivity;
import com.fangzhi.app.main.parent.ParentActivity;
import com.fangzhi.app.main.sell_part.SellPartActivity;
import com.fangzhi.app.tools.ActivityTaskManager;
import com.fangzhi.app.tools.SPUtils;
import com.fangzhi.app.tools.ScreenUtils;
import com.fangzhi.app.tools.T;
import com.fangzhi.app.view.ClearEditText;
import com.fangzhi.app.view.DialogDelegate;
import com.fangzhi.app.view.SweetAlertDialogDelegate;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
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
    @Bind(R.id.sp_area)
    Spinner spinner;

    private HousesAdapter mAdapter;
    private int mPage = 0;
    String mKeyword = "";
    DialogDelegate dialogDelegate;


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
        //禁用滑动删除
        setSwipeBackEnable(false);
        recyclerView.setRefreshListener(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mAdapter = new HousesAdapter(this);
        mAdapter.setMore(R.layout.view_more, this);

        recyclerView.setAdapterWithProgress(mAdapter);
        mAdapter.setOnItemClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(int position) {
                String id = mAdapter.getItem(position).getId();
                String name = mAdapter.getItem(position).getPre_cname();
                Intent intent = new Intent();
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.setClass(MainActivity.this, HouseTypeActivity.class);
                startActivity(intent);
            }
        });

        if (!currentCity.isEmpty()) {
            tvLocation.setText(currentCity);
            onRefresh();
        }
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
            mCurrentCounty = null;
            //重新选择城市后需要重新设置区县
            spinner.setVisibility(View.GONE);
            mPresenter.changeShowCounty(true);
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
        mAdapter.addAll(houses);
        recyclerView.setRefreshing(false);
    }

    @Override
    public void showNoMoreHouseList(List<Houses.House> houseList) {
        mAdapter.addAll(houseList);
        recyclerView.setRefreshing(false);
        //这里又没分页了
        mAdapter.stopMore();
    }

    private boolean isFirstSelect = true;

    @Override
    public void showCountyList(final List<County> countyList) {
        if (countyList != null && !countyList.isEmpty()) {
            spinner.setVisibility(View.VISIBLE);
            List<String> list = new ArrayList<>();
            list.add("全城");
            for (County county : countyList) {
                list.add(county.getArea_cname());
            }

            final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_simple_spinner, list);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //第一次默认选中全城不做操作
                    if (isFirstSelect) {
                        isFirstSelect = false;
                        return;
                    }
                    if (position == 0) {//全城
                        mCurrentCounty = null;
                        recyclerView.setRefreshing(true);
                        mPage = 1;
                        mAdapter.clear();
                        //不需要再次设置区县
                        mPresenter.getHousesList();
                    } else {
                        mCurrentCounty = countyList.get(position - 1);
                        recyclerView.setRefreshing(true);
                        mAdapter.clear();
                        mPresenter.getCountyHousesList();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } else {
            spinner.setVisibility(View.GONE);
            mCurrentCounty = null;
        }
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

    private County mCurrentCounty;

    @Override
    public String getCountyId() {
        return mCurrentCounty.getId();
    }

    @Override
    public int getPageSize() {
        return 20;
    }

    @Override
    public int getCurrentPage() {
        return mPage;
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
            if (mCurrentCounty == null) {
                //加载更多不需要更新区县列表
                mPresenter.getHousesList();
            } else {
                mPresenter.getCountyHousesList();
            }
        }
        mPage++;
    }

    @Override
    public void onRefresh() {
        recyclerView.setRefreshing(true);
        mPage = 1;
        mAdapter.clear();
        onLoadMore();
    }

    private void onSearch() {
        recyclerView.setRefreshing(true);
        mPage = 1;
        mAdapter.clear();
        onLoadMore();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialogDelegate.clearDialog();
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
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
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }


    @Override
    public void onError(String msg) {
        T.showShort(this, msg);
        recyclerView.setRefreshing(false);
    }

    @OnClick(R.id.btn_more)
    public void onMore(View view) {
        showPopupWindow(view);
    }

    public void onExit() {
        dialogDelegate.showWarningDialog("退出登录", "确定退出当前账号？", new DialogDelegate.OnDialogListener() {
            @Override
            public void onClick() {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ActivityTaskManager.getActivityTaskManager().finishActivity();
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);

    }

    @OnClick(R.id.btn_home_material)
    public void onHomeMaterial() {
        startActivity(new Intent(this, SellPartActivity.class));
    }

    private PopupWindow popupWindow;

    private void showPopupWindow(View parent) {
        TextView btnLogout;
        TextView btnChangeParent = null;
        if (popupWindow == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.view_popup_window, null);
            btnLogout = (TextView) view.findViewById(R.id.btn_logout);
            btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onExit();
                }
            });

            btnChangeParent = (TextView) view.findViewById(R.id.btn_change_parent);
            btnChangeParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onChangeParent();
                }
            });
            if (FactoryListInfo.parentList.size() <= 1) {
              // btnChangeParent.setVisibility(View.GONE);
                btnChangeParent.setTextColor(getResources().getColor(R.color.gray));
                btnChangeParent.setClickable(false);
            } else {
              //  btnChangeParent.setVisibility(View.VISIBLE);
                btnChangeParent.setTextColor(getResources().getColor(R.color.black_semi_transparent));
                btnChangeParent.setClickable(true);
            }
            popupWindow = new PopupWindow(view, ScreenUtils.getScreenWidth(this) / 8, AutoLinearLayout.LayoutParams.WRAP_CONTENT);
        }
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        int xPos = -popupWindow.getWidth() / 2;

        popupWindow.showAsDropDown(parent, xPos, 4);

    }

    private void onChangeParent() {
        startActivity(new Intent(this, ParentActivity.class));
    }

    @OnClick(R.id.btn_3_d)
    public void on3DClick(){
        startActivity(new Intent(this, ThreeDimensionalActivity.class));
    }
}
