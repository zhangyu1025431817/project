package com.fangzhi.app.main.type_detail;

import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import com.fangzhi.app.R;
import com.fangzhi.app.base.BaseActivity;
import com.fangzhi.app.bean.HouseTypeDetails;
import com.fangzhi.app.config.SpKey;
import com.fangzhi.app.login.LoginActivityNew;
import com.fangzhi.app.main.scene.SceneActivity;
import com.fangzhi.app.tools.SPUtils;
import com.fangzhi.app.tools.T;
import com.fangzhi.app.view.DialogDelegate;
import com.fangzhi.app.view.SweetAlertDialogDelegate;

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
    @Bind(R.id.tv_name)
    TextView tvName;
    //户型图id
    String mHouseTypeId;
    DialogDelegate delegate;

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

    @Override
    public String getToken() {
        return SPUtils.getString(this, SpKey.TOKEN, "");
    }

    @Override
    public String getHouseTypeId() {
        return mHouseTypeId;
    }

    @Override
    public void showHouseTypeDetails(List<HouseTypeDetails.HouseTypeDetail> list) {
        if (list == null || list.isEmpty()) {
            delegate.stopProgressWithWarning("该户型暂无场景", "该户型暂无场景", new DialogDelegate.OnDialogListener() {
                @Override
                public void onClick() {
                    finish();
                }
            });

        }else {
            //添加热点
            delegate.clearDialog();
            for (HouseTypeDetails.HouseTypeDetail houseTypeDetail : list) {
                drawHotArea(houseTypeDetail);
            }
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
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                String type = houseTypeDetail.getHot_type();
                intent.putExtra("type", type);
                intent.setClass(HouseTypeDetailActivity.this, SceneActivity.class);
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
                Intent intent = new Intent(HouseTypeDetailActivity.this, LoginActivityNew.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(new Intent(HouseTypeDetailActivity.this, LoginActivityNew.class));
                finish();
            }
        });
    }

    @Override
    public void onError(String msg) {
        T.showShort(this, msg);
    }
}
