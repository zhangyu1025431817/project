package com.buqi.app.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.buqi.app.tools.TUtil;

import butterknife.ButterKnife;

/**
 * Created by zhangyu on 2016/7/25.
 */
public abstract class BaseFragment<T extends BasePresenter, M extends BaseModel> extends Fragment {
    protected T mPresenter;
    protected M mModel;
    /**
     * Fragment当前状态是否可见
     */
    protected boolean isVisible;
    protected boolean isPrepared;
    private boolean isFirst = true;

    public abstract View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    public abstract void init();

    private View rootView;
    private boolean isLazyLoad = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (this instanceof BaseView) mPresenter.setVM(this, mModel);
        rootView = getContentView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        Bundle bundle = getArguments();
        if(bundle != null){
            if(bundle.containsKey("lazyLoad")){
                isLazyLoad = bundle.getBoolean("lazyLoad");
            }
        }
        if (!isLazyLoad) {
            init();
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (isLazyLoad) {
            isPrepared = true;
            lazyLoad();
        }
    }


    /**
     * 懒加载
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isLazyLoad) {
            if (getUserVisibleHint()) {
                isVisible = true;
                lazyLoad();
            } else {
                isVisible = false;
            }
        }
    }

    protected void lazyLoad() {
        if (!isPrepared || !isVisible || !isFirst) {
            return;
        }
        init();
        isFirst = false;
    }


    @Override
    public void onDestroyView() {
        isPrepared = false;
        ButterKnife.unbind(this);

        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        super.onDestroyView();

    }

    public void closeLazyLoad(){
        isLazyLoad = false;
    }
}
