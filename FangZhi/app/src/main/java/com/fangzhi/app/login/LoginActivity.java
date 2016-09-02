package com.fangzhi.app.login;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.CountDownTimer;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.fangzhi.app.R;
import com.fangzhi.app.base.BaseActivity;
import com.fangzhi.app.main.MainActivity;
import com.fangzhi.app.tools.T;
import com.fangzhi.app.view.DialogDelegate;
import com.fangzhi.app.view.StateEditText;
import com.fangzhi.app.view.SweetAlertDialogDelegate;
import com.zhy.autolayout.AutoLinearLayout;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by smacr on 2016/8/29.
 */
public class LoginActivity extends BaseActivity<LoginPresenter, LoginModel> implements LoginContract.View {
    private static final String TAG = "LoginActivity";
    @Bind(R.id.layout_indicator)
    AutoLinearLayout layoutIndicator;
    @Bind(R.id.view_indicator)
    View viewIndicator;
    @Bind(R.id.radio_group)
    RadioGroup radioGroup;
    @Bind(R.id.rb_request)
    RadioButton rbRequest;
    @Bind(R.id.rb_verification)
    RadioButton rbVerification;
    @Bind(R.id.layout_main)
    AutoLinearLayout layoutMain;
    @Bind(R.id.et_phone)
    StateEditText etPhone;
    @Bind(R.id.et_password)
    StateEditText etPassword;
    @Bind(R.id.layout_login)
    View layoutLogin;
    @Bind(R.id.layout_reset_password)
    View layoutResetPassword;
    @Bind(R.id.layout_set_password)
    View layoutSetPassword;
    @Bind(R.id.et_phone_reset_password)
    StateEditText etResetPasswordPhone;
    @Bind(R.id.et_message_code)
    StateEditText etMessageCode;
    @Bind(R.id.et_new_password)
    StateEditText etNewPassword;
    @Bind(R.id.et_set_password)
    StateEditText etSetPassword;
    @Bind(R.id.btn_get_code)
    Button btnGetCode;
    private boolean hasMeasured;

    private boolean isPasswordLogin = false;

    //属性动画
    ValueAnimator animLeft;
    ValueAnimator animRight;
    private DialogDelegate mDialogDelegate;
    private CountDownTimer mTimer;
    private int mCount = 60;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                etPassword.setText("");
                if (checkedId == R.id.rb_request) {//邀请码登录
                    if (animRight != null) {
                        animRight.start();
                    }
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    etPassword.setDefaultHint(getString(R.string.input_request_code));
                    isPasswordLogin = false;
                } else {//账号登录
                    if (animLeft != null) {
                        animLeft.start();
                    }
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    etPassword.setDefaultHint(getString(R.string.please_input_password));
                    isPasswordLogin = true;
                }
            }
        });
        //等待xml文件加载完才能获取控件宽度
        ViewTreeObserver vto = viewIndicator.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                if (!hasMeasured) {
                    int rbWidth = rbRequest.getWidth();
                    int indicatorWidth = viewIndicator.getWidth();
                    int offset = rbWidth / 2 - indicatorWidth / 2;
                    layoutIndicator.scrollTo(-offset, 0);
                    //属性动画，从左滑到右
                    animLeft = ValueAnimator.ofFloat(-offset, -rbWidth - offset);
                    animLeft.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float currentValue = (float) animation.getAnimatedValue();
                            layoutIndicator.scrollTo((int) currentValue, 0);
                        }
                    });
                    animLeft.setDuration(300);
                    //属性动画，从左滑到右
                    animRight = ValueAnimator.ofFloat(-rbWidth - offset, -offset);
                    animRight.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float currentValue = (float) animation.getAnimatedValue();
                            layoutIndicator.scrollTo((int) currentValue, 0);
                        }
                    });
                    animRight.setDuration(300);
                    hasMeasured = true;
                }
                return true;
            }
        });
        mDialogDelegate = new SweetAlertDialogDelegate(this);
        mTimer = new CountDownTimer(1000 * 60, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                btnGetCode.setEnabled(false);
                btnGetCode.setText(mCount + "s");
                mCount--;
            }

            @Override
            public void onFinish() {
                btnGetCode.setText(getString(R.string.get_msg_code));
                btnGetCode.setEnabled(true);
                mCount = 60;
            }
        };

    }

    //登录
    @OnClick(R.id.btn_login)
    public void onLogin() {
        String phone = etPhone.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (phone.isEmpty()) {
            etPhone.changeState(getString(R.string.phone_number_not_be_null));
            return;
        }
        if (password.isEmpty()) {
            if(isPasswordLogin) {
                etPassword.changeState(getString(R.string.password_not_null));
            }else {
                etPassword.changeState(getString(R.string.request_code_not_be_null));
            }
            return;
        }
        mDialogDelegate.showProgressDialog(true, getString(R.string.logining));
        mPresenter.login(phone, password);
    }

    @OnClick(R.id.tv_forget_password)
    public void onForgetPassword() {
        showResetPasswordView();
    }

    //重置密码
    @OnClick(R.id.btn_sure)
    public void onResetPassword() {
        String phone = etResetPasswordPhone.getText().toString().trim();
        String code = etMessageCode.getText().toString().trim();
        String pwd = etNewPassword.getText().toString().trim();
        if (phone.isEmpty()) {
            etResetPasswordPhone.changeState(getResources().getString(R.string.input_right_phone_number));
            return;
        }
        if (code.isEmpty()) {
            etMessageCode.changeState(getResources().getString( R.string.input_verification_code));
            return;
        }
        if (pwd.isEmpty()) {
            etNewPassword.changeState(getResources().getString(R.string.password_not_null));
            return;
        }
        mDialogDelegate.showProgressDialog(true, getString(R.string.commit_ing));
        mPresenter.resetPwd(phone, code, pwd);
    }

    //设置密码
    @OnClick(R.id.btn_finish)
    public void onSetPassword() {
        String pwd = etSetPassword.getText().toString().trim();
        if (pwd.isEmpty()) {
            T.showShort(this, getString(R.string.password_not_null));
        } else {
            mPresenter.setPassword(pwd);
        }
    }

    //获取验证码
    @OnClick(R.id.btn_get_code)
    public void onGetMessageCode() {
        String phone = etResetPasswordPhone.getText().toString().trim();
        if (phone.isEmpty()) {
            etResetPasswordPhone.changeState(getResources().getString(R.string.input_right_phone_number));
        } else {
            mPresenter.getMessageCode(phone);
        }
    }

    @OnClick(R.id.iv_close)
    public void onRestViewClose() {
        etResetPasswordPhone.setText("");
        etMessageCode.setText("");
        etNewPassword.setText("");
        showLoginView();
    }

    @Override
    public void loginSucceed() {
        mDialogDelegate.clearDialog();
        if (!isPasswordLogin) {
            showSetPasswordView();
        } else {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    //显示登录界面
    private void showLoginView() {
        layoutLogin.setVisibility(View.VISIBLE);
        layoutSetPassword.setVisibility(View.GONE);
        layoutResetPassword.setVisibility(View.GONE);
    }

    //显示设置密码界面
    private void showSetPasswordView() {
        layoutLogin.setVisibility(View.GONE);
        layoutSetPassword.setVisibility(View.VISIBLE);
        layoutResetPassword.setVisibility(View.GONE);
    }

    //显示重新设置密码界面
    private void showResetPasswordView() {
        layoutLogin.setVisibility(View.GONE);
        layoutSetPassword.setVisibility(View.GONE);
        layoutResetPassword.setVisibility(View.VISIBLE);
    }

    @Override
    public void loginFailed(String msg) {
        mDialogDelegate.stopProgressWithFailed(msg, msg);
    }

    @Override
    public void getCodeSucceed() {
        mTimer.start();
    }

    @Override
    public void getCodeFailed(String msg) {
        T.showShort(this, msg);
    }

    @Override
    public void setPwdSucceed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void setPwdFailed(String msg) {
        T.showShort(this, msg);
    }

    @Override
    public void resetPwdSucceed() {
        mDialogDelegate.clearDialog();
        showLoginView();
    }

    @Override
    public void resetPwdFailed(String msg) {
        mDialogDelegate.stopProgressWithFailed(msg,msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
        mDialogDelegate.clearDialog();
    }
}
