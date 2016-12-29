package com.buyiren.app.main.decoration.product;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.buyiren.app.R;
import com.buyiren.app.bean.CellGraphResponseBean;
import com.buyiren.app.config.SpKey;
import com.buyiren.app.main.adapter.PictureAdapter;
import com.buyiren.app.network.MySubscriber;
import com.buyiren.app.network.Network;
import com.buyiren.app.tools.SPUtils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by smacr on 2016/11/29.
 */
public class PictureActivity extends Activity {
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.recycler_view)
    EasyRecyclerView easyRecyclerView;
    @Bind(R.id.tv_empty)
    TextView tvEmpty;
    @Bind(R.id.layout_data)
    ViewGroup layoutData;
    String mPartId;
    Subscription subscription;
    PictureAdapter pictureAdapter;
    int mCurrentPosition = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        ButterKnife.bind(this);

//        BannerAdapter mBannerAdapter = new BannerAdapter(this, list);
//        rollPagerView.setAdapter(mBannerAdapter);
        Intent intent = getIntent();
        mPartId = intent.getStringExtra("partId");
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == mCurrentPosition){
                    return;
                }
                onSelect(position);
                easyRecyclerView.scrollToPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        easyRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.HORIZONTAL));
        pictureAdapter = new PictureAdapter(PictureActivity.this);
        pictureAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.e("onItemClick",position+"--"+mCurrentPosition);
                if(position == mCurrentPosition){
                    return;
                }
                onSelect(position);
                viewPager.setCurrentItem(position);
            }
        });
        easyRecyclerView.setAdapter(pictureAdapter);
        requestData();
    }

    private void onSelect(int position) {
        if (mCurrentPosition < 0 ||
                mCurrentPosition > pictureAdapter.getAllData().size() - 1)
            return;

        CellGraphResponseBean.CellGraph oldCellGraph = pictureAdapter.getItem(mCurrentPosition);
        oldCellGraph.setSelected(false);
        pictureAdapter.notifyItemChanged(mCurrentPosition);

        CellGraphResponseBean.CellGraph cellGraph = pictureAdapter.getItem(position);
        cellGraph.setSelected(true);
        mCurrentPosition = position;
        pictureAdapter.notifyItemChanged(position);
    }

    private void requestData() {
        subscription = Network.getApiService().queryCellGraph(SPUtils.getString(this, SpKey.TOKEN, ""), mPartId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<CellGraphResponseBean>() {
                    @Override
                    public void onNext(CellGraphResponseBean cellGraphResponseBean) {

                         final ArrayList<CellGraphResponseBean.CellGraph> list = cellGraphResponseBean.getCellList();
                            if (list != null && !list.isEmpty()) {
//                        final ArrayList<CellGraphResponseBean.CellGraph> list = new ArrayList<>();
//                        for (int i = 0; i < 5; i++) {
//                            list.add(new CellGraphResponseBean.CellGraph());
//                        }
                        tvEmpty.setVisibility(View.GONE);
                        layoutData.setVisibility(View.VISIBLE);
                        viewPager.setAdapter(new PagerAdapter() {
                            @Override
                            public int getCount() {
                                return list.size();
                            }

                            @Override
                            public boolean isViewFromObject(View view, Object object) {
                                return view == object;
                            }

                            @Override
                            public void destroyItem(ViewGroup container, int position, Object object) {
                                //    container.removeViewAt(position);
                            }

                            @Override
                            public Object instantiateItem(ViewGroup container, int position) {
                                ImageView im = new ImageView(PictureActivity.this);
                                im.setScaleType(ImageView.ScaleType.FIT_XY);
                                //加载图片
                                Glide.with(PictureActivity.this)
                                        .load(list.get(position).getIMG_URL())
                                        .placeholder(R.drawable.bg_image_placeholder)
                                        .into(im);
                                container.addView(im);
                                return im;
                            }
                        });
                        list.get(0).setSelected(true);
                        pictureAdapter.addAll(list);
                           } else {
                               tvEmpty.setVisibility(View.VISIBLE);
                                layoutData.setVisibility(View.GONE);
                              }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    @OnClick(R.id.iv_close)
    public void onFinish() {
        finish();
    }

    @OnClick(R.id.btn_left)
    public void onLeft() {
        if (mCurrentPosition > 0) {
            viewPager.setCurrentItem(mCurrentPosition-1);
        }
    }

    @OnClick(R.id.btn_right)
    public void onRight() {
        if (mCurrentPosition < pictureAdapter.getAllData().size() - 1) {
            viewPager.setCurrentItem(mCurrentPosition+1);
        }
    }

    @Override
    protected void onDestroy() {
        subscription.unsubscribe();
        super.onDestroy();
    }
}
