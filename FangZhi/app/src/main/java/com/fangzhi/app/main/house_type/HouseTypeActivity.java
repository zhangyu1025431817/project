package com.fangzhi.app.main.house_type;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.widget.TextView;

import com.fangzhi.app.R;
import com.fangzhi.app.base.BaseActivity;
import com.fangzhi.app.bean.HouseTypes;
import com.fangzhi.app.main.adapter.HouseTypeAdapter;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by smacr on 2016/9/21.
 */
public class HouseTypeActivity extends BaseActivity<HouseTypePresenter,HouseTypeModel> implements HouseTypeContract.View
,RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener{
    @Bind(R.id.recycler_view)
    EasyRecyclerView recyclerView;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    private String mHouseId;
    private HouseTypeAdapter mAdapter;
    private int page;

    @Override
    public int getLayoutId() {
        return R.layout.activity_house_type;
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        //获取楼盘id
        mHouseId = intent.getStringExtra("id");
        String name = intent.getStringExtra("name");
        //设置标题
        tvTitle.setText(name);

        recyclerView.setRefreshListener(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new HouseTypeAdapter(this);
        mAdapter.setMore(R.layout.view_more, this);
        recyclerView.setAdapterWithProgress(mAdapter);
        onRefresh();
    }

    @Override
    public String getToken() {
        return "";
    }

    @Override
    public String getHouseId() {
        return mHouseId;
    }

    @Override
    public void showHouseTypes(List<HouseTypes.HouseType> list) {
        mAdapter.addAll(list);
        recyclerView.setRefreshing(false);
    }
    @OnClick(R.id.iv_back)
    public void onFinish(){
        finish();
    }

    @Override
    public void onLoadMore() {
        mPresenter.getHouseTypes();
        page++;
    }

    @Override
    public void onRefresh() {
        recyclerView.setRefreshing(true);
        page = 1;
        mAdapter.clear();
        onLoadMore();
    }
}
