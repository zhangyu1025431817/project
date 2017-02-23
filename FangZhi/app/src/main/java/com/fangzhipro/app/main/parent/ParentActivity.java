package com.fangzhipro.app.main.parent;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.fangzhipro.app.MyApplication;
import com.fangzhipro.app.R;
import com.fangzhipro.app.bean.LoginBean;
import com.fangzhipro.app.bean.LoginNewBean;
import com.fangzhipro.app.config.SpKey;
import com.fangzhipro.app.main.MainActivity;
import com.fangzhipro.app.main.welcome.CustomActivity;
import com.fangzhipro.app.manager.AccountManager;
import com.fangzhipro.app.network.MySubscriber;
import com.fangzhipro.app.network.Network;
import com.fangzhipro.app.network.http.api.ErrorCode;
import com.fangzhipro.app.tools.SPUtils;
import com.fangzhipro.app.view.DialogDelegate;
import com.fangzhipro.app.view.SweetAlertDialogDelegate;
import com.fangzhipro.app.view.VerticalViewPager;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by smacr on 2016/10/26.
 */
public class ParentActivity extends AppCompatActivity implements BrandFragment.MyListener{

    @Bind(R.id.view_pager)
    VerticalViewPager verticalViewPager;
    DialogDelegate dialogDelegate;
    static Shader shaderWhite;
    static Shader shaderBlue;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_parent);
        ButterKnife.bind(this);

        dialogDelegate = new SweetAlertDialogDelegate(this);
        initViewPager();
    }

    private void initViewPager(){
        shaderWhite =new LinearGradient(0, 0, 0, 100, Color.WHITE, Color.WHITE, Shader.TileMode.CLAMP);
        shaderBlue =new LinearGradient(0, 0, 0, 100,
                0xFF0095d6, 0xFF005ead,
                Shader.TileMode.CLAMP);
        verticalViewPager.setAdapter(new DummyAdapter(getSupportFragmentManager(),
                (ArrayList<LoginNewBean.Parent>) AccountManager.getInstance().getParentList()));
        verticalViewPager.setPageMarginDrawable(new ColorDrawable(getResources().getColor(android.R.color.holo_green_dark)));
    }

    private void loginParent(final String name, final String parentId, final String url) {
        String token = SPUtils.getString(this, SpKey.TOKEN, "");
        String userId = SPUtils.getString(this, SpKey.USER_ID, "");
        Network.getApiService().loginParent(token, parentId, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<LoginBean>() {
                    @Override
                    public void onNext(LoginBean loginBean) {
                        if (ErrorCode.SUCCEED.equals(loginBean.getError_code())) {
                            SPUtils.put(ParentActivity.this, SpKey.TOKEN, loginBean.getToken());
                            dialogDelegate.clearDialog();
                            Intent intent = new Intent();
                            if (loginBean.getImg() == null || loginBean.getImg().isEmpty()) {
                                intent.setClass(ParentActivity.this, MainActivity.class);
                            } else {
                                intent.putExtra("url", loginBean.getImg());
                                intent.setClass(ParentActivity.this, CustomActivity.class);
                            }
                            SPUtils.put(MyApplication.getContext(),
                                    SpKey.FACTORY_ADDRESS, url == null ? "" : url)
                            ;
                            AccountManager.getInstance().setCurrentSelectParentId(parentId);
                            AccountManager.getInstance().setCurrentParentName(name);
                            startActivity(intent);
                            ParentActivity.this.finish();
                        } else {
                            dialogDelegate.stopProgressWithFailed("提交失败", loginBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialogDelegate.stopProgressWithFailed("提交失败", "服务器连接失败！");
                    }
                });
    }

    @OnClick(R.id.iv_close)
    public void onFinish() {
        finish();
    }

    @OnClick(R.id.iv_up)
    public void onScrollUp(){
        int position = verticalViewPager.getCurrentItem();
        if(position > 0){
            verticalViewPager.setCurrentItem(position-1);
        }
    }
    @OnClick(R.id.iv_down)
    public void onScrollDown(){
        int position = verticalViewPager.getCurrentItem();
        int count = verticalViewPager.getChildCount();
        if(position < count -1){
            verticalViewPager.setCurrentItem(position+1);
        }

    }

    @Override
    public void chooseParent(LoginNewBean.Parent parent) {
        dialogDelegate.showProgressDialog(true, "正在提交...");
        loginParent(
                parent.getNAME(),
                parent.getID(),
                parent.getURL());
    }

    public class DummyAdapter extends FragmentPagerAdapter {
        private ArrayList<LoginNewBean.Parent> list;
        public DummyAdapter(FragmentManager fm,ArrayList<LoginNewBean.Parent> list ) {
            super(fm);
            this.list = list;
        }

        @Override
        public Fragment getItem(int position) {
            ArrayList<LoginNewBean.Parent> tempList = new ArrayList<>();
            try {
                LoginNewBean.Parent parent1 =  list.get(position*2);
                tempList.add(parent1);
                LoginNewBean.Parent parent2 =  list.get(position*2+1);
                tempList.add(parent2);
            }catch (Exception e){
                e.printStackTrace();
            }
            return BrandFragment.newInstance(tempList);
        }

        @Override
        public int getCount() {
            return list.size()%2 == 0? list.size()/2: list.size()/2 +1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }

    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
