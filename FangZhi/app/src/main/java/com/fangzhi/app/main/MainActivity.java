package com.fangzhi.app.main;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fangzhi.app.R;
import com.fangzhi.app.base.BaseActivity;
import com.fangzhi.app.bean.HousesResponseBean;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zhy.autolayout.AutoFrameLayout;

import java.util.List;

import butterknife.Bind;

public class MainActivity extends BaseActivity<MainPresenter, MainModel> implements MainContract.View, RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    //    @Bind(R.id.recycler_view)
//    EasyRecyclerView recyclerView;
//
//    private HousesAdapter adapter;
//    private int page = 0;
    @Bind(R.id.frame)
    AutoFrameLayout frameLayout;
    String[] list = new String[]{"http://room-image.91fzz.com/image/20160830/74926eee-00e1-4250-b822-d7f668447fcb.webp"
            , "http://room-image.91fzz.com/image/20160830/2a6aa62a-95a6-4bab-871e-db04e47cb460.webp"
            , "http://room-image.91fzz.com/image/20160830/0a5dc0d1-b9e2-42ff-974f-c2352b7397ad.webp"
            , "http://room-image.91fzz.com/image/20160830/c0ad4efb-65c8-48e0-9195-02e319102b1b.webp"
            , "http://room-image.91fzz.com/image/20160830/a3bdaf89-7366-4711-abaf-7f5937f6ed74.webp"
            , "http://room-image.91fzz.com/image/20160830/00cc3575-d9c8-435e-a094-9dc2c793118d.webp"
            , "http://room-image.91fzz.com/image/20160830/3237f6fc-d776-4ea3-b72e-2104ada688fa.webp"};

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
//        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
//        recyclerView.setRefreshListener(this);
//
//        adapter = new HousesAdapter(this);
//        adapter.setMore(R.layout.view_more, this);
//        adapter.setNoMore(R.layout.view_nomore);
//        recyclerView.setAdapterWithProgress(adapter);
//        onRefresh();
        for (int i = 0; i < list.length; i++) {
            ImageView imageView = new ImageView(this);
            Glide.with(this)
                    .load(list[i])
                    .placeholder(R.drawable.bg_default)
                    .into(imageView);
            frameLayout.addView(imageView, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        }

    }

    @Override
    public void showHousesList(List<HousesResponseBean.Houses> housingEstateList) {
//        adapter.addAll(housingEstateList);
//        recyclerView.setRefreshing(false);
    }

    @Override
    public void onLoadMore() {
//        mPresenter.getHousesList("500000",10,page);
//        page++;
    }

    @Override
    public void onRefresh() {
//        recyclerView.setRefreshing(true);
//        page = 0;
//        adapter.clear();
//        onLoadMore();
    }
}
