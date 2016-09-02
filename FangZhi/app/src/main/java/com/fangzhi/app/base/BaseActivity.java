package com.fangzhi.app.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.fangzhi.app.tools.TUtil;

import butterknife.ButterKnife;

/**
 * Created by zhangyu on 2016/3/11.
 */
public abstract class BaseActivity<T extends BasePresenter,M extends BaseModel> extends AppCompatActivity {

    protected T mPresenter;
    protected M mModel;
    public abstract int getLayoutId();
    public abstract void initView();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        //反射拿到Presenter实例
        mPresenter = TUtil.getT(this,0);
        mModel = TUtil.getT(this,1);
        if (this instanceof BaseView) mPresenter.setVM(this, mModel);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPresenter != null) {
            mPresenter.onDestroy();
        }
        ButterKnife.unbind(this);
    }
}
