package com.fangzhi.app.login;

import android.content.Intent;

import com.fangzhi.app.MyApplication;
import com.fangzhi.app.R;
import com.fangzhi.app.base.BaseActivity;
import com.fangzhi.app.config.SpKey;
import com.fangzhi.app.main.MainActivity;
import com.fangzhi.app.tools.AppUtils;
import com.fangzhi.app.tools.SPUtils;
import com.fangzhi.app.view.DialogDelegate;
import com.fangzhi.app.view.StateEditText;
import com.fangzhi.app.view.SweetAlertDialogDelegate;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by smacr on 2016/9/19.
 */
public class LoginActivityNew extends BaseActivity<LoginPresenter, LoginModel> implements LoginContract.View {
    /**
     * 手机
     */
    @Bind(R.id.et_phone)
    StateEditText etPhone;
    /**
     * 密码
     */
    @Bind(R.id.et_password)
    StateEditText etPassword;
    /**
     * 验证码
     */
    @Bind(R.id.et_message_code)
    StateEditText etMessageCode;
    DialogDelegate dialogDelegate ;
    /**
     * 登录
     */
    @OnClick(R.id.btn_login)
    public void onLogin() {
        if (verification(etPhone, getString(R.string.phone_number_not_be_null))
                && verification(etPassword, getString(R.string.password_not_be_null))) {
            dialogDelegate.showProgressDialog(true,"正在登录...");
            mPresenter.login();
        }
    }

    private boolean verification(StateEditText et, String errorMsg) {
        if (et.getText().toString().trim().isEmpty()) {
            et.changeState(errorMsg);
            return false;
        }
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login_new;
    }

    @Override
    public void initView() {
        dialogDelegate = new SweetAlertDialogDelegate(this);
        etPhone.setText(SPUtils.getString(MyApplication.getContext(), SpKey.USER_NAME,""));
        etPassword.setText(SPUtils.getString(MyApplication.getContext(),SpKey.PASSWORD,""));
    }

    @Override
    public String getDeviceId() {
        return AppUtils.getDeviceId(this);
    }

    @Override
    public String getPhoneNumber() {
        return etPhone.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return etPassword.getText().toString().trim();
    }

    @Override
    public void loginSucceed() {
        dialogDelegate.clearDialog();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void loginFailed(String msg) {
        dialogDelegate.stopProgressWithFailed(msg,msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialogDelegate.clearDialog();
    }

    @Override
    public void tokenInvalid(String msg) {

    }

    @Override
    public void onError(String msg) {

    }
}
