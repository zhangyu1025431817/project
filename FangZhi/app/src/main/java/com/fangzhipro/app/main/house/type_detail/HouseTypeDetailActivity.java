package com.fangzhipro.app.main.house.type_detail;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.fangzhipro.app.R;
import com.fangzhipro.app.base.BaseActivity;
import com.fangzhipro.app.bean.HouseTypeDetails;
import com.fangzhipro.app.config.SpKey;
import com.fangzhipro.app.login.LoginActivity;
import com.fangzhipro.app.main.MainActivity;
import com.fangzhipro.app.main.adapter.NoDoubleClickListener;
import com.fangzhipro.app.main.house.scene.SceneActivity;
import com.fangzhipro.app.tools.SPUtils;
import com.fangzhipro.app.tools.T;
import com.fangzhipro.app.view.DialogDelegate;
import com.fangzhipro.app.view.SweetAlertDialogDelegate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by smacr on 2016/9/21.
 */
public class HouseTypeDetailActivity extends BaseActivity<HouseTypeDetailPresenter, HouseTypeDetailModel>
        implements HouseTypeDetailContract.View {
    @Bind(R.id.iv_image)
    ImageView imageView;
    @Bind(R.id.layout_bg)
    RelativeLayout layoutBg;
    @Bind(R.id.tv_title)
    TextView tvName;
    //户型图id
    String mHouseTypeId;
    DialogDelegate delegate;
    ArrayList<HouseTypeDetails.HouseTypeDetail> mList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_house_type_detail;
    }

    @Override
    public void initView() {
        //加载背景户型图
        String imgUrl = getIntent().getStringExtra("imgUrl");
        mHouseTypeId = getIntent().getStringExtra("id");
        String name = getIntent().getStringExtra("name");
        if (name != null) {
            tvName.setText(name);
        }
        delegate = new SweetAlertDialogDelegate(this);
        Glide.with(this)
                .load(imgUrl)
                .fitCenter()
                .placeholder(R.drawable.bg_image_placeholder)
                .crossFade()
                .into(new GlideDrawableImageViewTarget(imageView) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);
                        delegate.showProgressDialog(true, "初始化热点区域...");
                        mPresenter.getHouseTypeDetails();
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        T.showShort(HouseTypeDetailActivity.this, "户型图加载失败,请检查网络!");
                    }
                });
    }

    @OnClick(R.id.iv_back)
    public void onFinish() {
        finish();
    }
    @OnClick(R.id.iv_home)
    public void onHome(){
        Intent intent = new Intent(HouseTypeDetailActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    @Override
    public String getToken() {
        return SPUtils.getString(this, SpKey.TOKEN, "");
    }

    @Override
    public String getHouseTypeId() {
        return mHouseTypeId;
    }

    private ArrayList<HouseTypeDetails.HouseTypeDetail> mHasSonList = new ArrayList<>();

    @Override
    public void showHouseTypeDetails(List<HouseTypeDetails.HouseTypeDetail> list) {
        HashMap<String, HouseTypeDetails.HouseTypeDetail> map = new HashMap<>();
        if (list == null || list.isEmpty()) {
            delegate.stopProgressWithWarning("该户型暂无场景", "该户型暂无场景", new DialogDelegate.OnDialogListener() {
                @Override
                public void onClick() {
                    delegate.clearDialog();
                    finish();
                }
            });

        } else {
            //添加热点
            delegate.clearDialog();
            for (HouseTypeDetails.HouseTypeDetail houseTypeDetail : list) {
                drawHotArea(houseTypeDetail);
                if (houseTypeDetail.getSonList() != null && !houseTypeDetail.getSonList().isEmpty()) {
                    //  mHasSonList.add(houseTypeDetail);
                    //去重
                    map.put(houseTypeDetail.getHot_type(), houseTypeDetail);
                }
            }
            mHasSonList.addAll(new ArrayList<>(map.values()));
            mList.addAll(list);
        }
    }


    private void drawHotArea(final HouseTypeDetails.HouseTypeDetail houseTypeDetail) {
        int bgWidth = layoutBg.getWidth();
        int bgHeight = layoutBg.getHeight();
        int marginTop = (int) (bgHeight * (Float.valueOf(houseTypeDetail.getHot_top()) / 100));
        int marginLeft = (int) (bgWidth * (Float.valueOf(houseTypeDetail.getHot_left()) / 100));
        int areaWidth = (int) (bgWidth * (Float.valueOf(houseTypeDetail.getHot_long()) / 100));
        int areaHeight = (int) (bgHeight * (Float.valueOf(houseTypeDetail.getHot_wide()) / 100));

        Log.e("drawHotArea", "背景宽:" + bgWidth
                + ",背景高:" + bgHeight
                + ",上间距:" + marginTop
                + ",左间距:" + marginLeft
                + ",区域宽:" + areaWidth
                + ",区域高:" + areaHeight);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(areaWidth, areaHeight);
        layoutParams.topMargin = marginTop;
        layoutParams.leftMargin = marginLeft;

        View view = LayoutInflater.from(this).inflate(R.layout.view_hot, null);
        view.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(int position) {
                Intent intent = new Intent();
                String type = houseTypeDetail.getHot_type();
                Bundle bundle = new Bundle();
                bundle.putString("type", type);
//                if (houseTypeDetail.getSonList() != null && houseTypeDetail.getSonList().size() > 0) {
//                    bundle.putSerializable("window_types", houseTypeDetail.getSonList());
//                    intent.setClass(HouseTypeDetailActivity.this, WindowTypeActivity.class);
//                } else {
//                    intent.setClass(HouseTypeDetailActivity.this, SceneActivity.class);
//                }
//                if (!mHasSonList.isEmpty()) {
//                    if (houseTypeDetail.getSonList() != null
//                            && !houseTypeDetail.getSonList().isEmpty()) {
//                        bundle.putSerializable("window_types", mHasSonList);
//                        intent.setClass(HouseTypeDetailActivity.this, WindowTypeActivity.class);
//                    } else {
//                        T.showShort(HouseTypeDetailActivity.this, "该房间暂无场景，请选择其他房间");
//                        return;
//                    }
//                } else {
                intent.setClass(HouseTypeDetailActivity.this, SceneActivity.class);
                //               }
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        layoutBg.addView(view, layoutParams);
    }

    @Override
    public void tokenInvalid(String msg) {
        delegate.showErrorDialog(msg, msg, new DialogDelegate.OnDialogListener() {
            @Override
            public void onClick() {
                Intent intent = new Intent(HouseTypeDetailActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onError(String msg) {
        delegate.stopProgressWithWarning(msg, msg, new DialogDelegate.OnDialogListener() {
            @Override
            public void onClick() {
                delegate.clearDialog();
                finish();
            }
        });
    }

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
//            View decorView = getWindow().getDecorView();
//            decorView.setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//        }
//    }
}
