package com.fangzhi.app.login;

import android.content.Intent;
import android.widget.ImageView;

import com.fangzhi.app.R;
import com.fangzhi.app.base.BaseActivity;
import com.fangzhi.app.main.MainActivity;
import com.fangzhi.app.tools.AppUtils;
import com.fangzhi.app.tools.RandomCode;
import com.fangzhi.app.view.StateEditText;

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
    /**
     * 随机码
     */
    @Bind(R.id.iv_random_code)
    ImageView ivRandomCode;
    private String mRealRandomCode;

    /**
     * 登录
     */
    @OnClick(R.id.btn_login)
    public void onLogin() {
        startActivity(new Intent(this, MainActivity.class));
//
//        if (verification(etPhone, getString(R.string.phone_number_not_be_null))
//                && verification(etPassword, getString(R.string.password_not_be_null))) {
//            if (!etMessageCode.getText().toString().trim().equals(mRealRandomCode)) {
//                etMessageCode.changeState(getString(R.string.verification_code_error));
//            } else {
//                mPresenter.login();
//            }
//        }
    }

    @OnClick(R.id.iv_random_code)
    public void changeRandomCode() {
        ivRandomCode.setImageBitmap(RandomCode.getInstance().createBitmap());
        mRealRandomCode = RandomCode.getInstance().getCode().toLowerCase();
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
        //将验证码用图片的形式显示出来
        changeRandomCode();
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
    public String getRandomCode() {
        return etMessageCode.getText().toString().trim();
    }

    @Override
    public void loginSucceed() {

    }

    @Override
    public void loginFailed(String msg) {

    }

}
