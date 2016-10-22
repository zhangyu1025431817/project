package com.fangzhi.app.login.regist;

import com.fangzhi.app.base.BaseActivity;

/**
 * Created by smacr on 2016/10/22.
 */
public class RegisterActivity extends BaseActivity<RegisterPresenter,RegisterModel> implements RegisterContract.View{
    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initView() {

    }

    @Override
    public String getPhone() {
        return null;
    }

    @Override
    public String getCode() {
        return null;
    }

    @Override
    public void tokenInvalid(String msg) {

    }

    @Override
    public void onError(String msg) {

    }
}
