package com.fangzhipro.app.login;

import android.content.Intent;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.fangzhipro.app.MyApplication;
import com.fangzhipro.app.R;
import com.fangzhipro.app.base.BaseActivity;
import com.fangzhipro.app.bean.LocationArea;
import com.fangzhipro.app.config.SpKey;
import com.fangzhipro.app.main.MainActivity;
import com.fangzhipro.app.main.parent.ParentActivity;
import com.fangzhipro.app.main.welcome.CustomActivity;
import com.fangzhipro.app.tools.ActivityTaskManager;
import com.fangzhipro.app.tools.AppUtils;
import com.fangzhipro.app.tools.MD5Util;
import com.fangzhipro.app.tools.SPUtils;
import com.fangzhipro.app.tools.ScreenUtils;
import com.fangzhipro.app.tools.StringUtils;
import com.fangzhipro.app.tools.T;
import com.fangzhipro.app.view.ClearEditText;
import com.fangzhipro.app.view.DialogDelegate;
import com.fangzhipro.app.view.SweetAlertDialogDelegate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnItemSelected;

/**
 * Created by smacr on 2016/9/19.
 */
public class LoginActivity extends BaseActivity<LoginPresenter, LoginModel> implements LoginContract.View {

    //登录布局------->
    @Bind(R.id.layout_login)
    ViewGroup layoutLogin;
    //手机
    @Bind(R.id.et_phone)
    ClearEditText etPhone;
    //密码
    @Bind(R.id.et_password)
    ClearEditText etPassword;
    //获取验证码布局----->
    @Bind(R.id.layout_get_code)
    ViewGroup layoutGetCode;
    //号码
    @Bind(R.id.et_phone_code)
    ClearEditText etCodePhone;
    //验证码
    @Bind(R.id.et_message_code)
    ClearEditText etMessageCode;
    //获取验证码
    @Bind(R.id.tv_get_code)
    TextView tvGetCode;
    //填写注册信息布局------>
    @Bind(R.id.layout_register)
    ViewGroup layoutRegister;
    @Bind(R.id.et_user_name)
    EditText etUserName;
    @Bind(R.id.et_register_psd)
    EditText etRegisterPwd;
    @Bind(R.id.et_register_psd_confirm)
    EditText etRegisterPwdConfirm;
    @Bind(R.id.rb_sex)
    RadioGroup rbSex;
    @Bind(R.id.sp_province)
    Spinner spProvince;
    @Bind(R.id.sp_city)
    Spinner spCity;
    @Bind(R.id.sp_county)
    Spinner spCounty;
    @Bind(R.id.et_company)
    EditText etCompany;
    @Bind(R.id.et_address)
    EditText etAddress;
    @Bind(R.id.et_business_area)
    EditText etBusinessArea;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.et_register_v_code)
    EditText etRegisterVCode;
    //找回密码布局------->
    @Bind(R.id.layout_find_password)
    ViewGroup layoutFindPassword;
    //新密码
    @Bind(R.id.et_new_password)
    ClearEditText etNewPwd;
    //再次输入
    @Bind(R.id.et_new_password_confirm)
    ClearEditText etNewPwdConfirm;
    DialogDelegate dialogDelegate;

    private CountDownTimer mTimer;
    private int mCount = 60;
    List<String> mProvinceNames, mCityNames, mAreaNames;
    List<String> mProvinceIds, mCityIds, mAreaIds;
    private String mProvinceId, mCityId, mAreaId;

    //登录
    @OnClick(R.id.btn_login)
    public void onLogin() {
        if (verificationEmpty(etPhone, getString(R.string.phone_number_not_be_null))
                && verificationEmpty(etPassword, getString(R.string.password_not_be_null))) {
            dialogDelegate.showProgressDialog(true, "正在登录...");
            mPresenter.loginNew();
        }
    }
    @OnClick(R.id.btn_exit)
    public void onExit(){
        finish();
    }
    private boolean verificationEmpty(EditText et, String errorMsg) {
        if (et.getText().toString().trim().isEmpty()) {
            T.showShort(this, errorMsg);
            return false;
        }
        return true;
    }

    private boolean verificationPhone(EditText et, String errorMsg) {
        if (!StringUtils.checkPhone(et.getText().toString().trim())) {
            T.showShort(this, errorMsg);
            return false;
        }
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        Log.e("LoginActivity", ScreenUtils.getScreenWidth(this) + "--" + ScreenUtils.getScreenHeight(this));
        setSwipeBackEnable(false);
        Log.e("LoginActivity", AppUtils.getDeviceId(this));
        dialogDelegate = new SweetAlertDialogDelegate(this);
        etPhone.setText(SPUtils.getString(MyApplication.getContext(), SpKey.USER_NAME, ""));
        etPassword.setText(SPUtils.getString(MyApplication.getContext(), SpKey.PASSWORD, ""));

        mTimer = new CountDownTimer(1000 * 60, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvGetCode.setEnabled(false);
                tvGetCode.setText(mCount + getString(R.string.get_code_again));
                mCount--;
            }

            @Override
            public void onFinish() {
                tvGetCode.setText(getString(R.string.get_msg_code));
                tvGetCode.setEnabled(true);
                mCount = 60;
            }
        };
        ActivityTaskManager.getActivityTaskManager().addActivity(this);
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
    public String getCodePhone() {
        return etCodePhone.getText().toString().trim();
    }

    @Override
    public String getKey() {
        String md5Key = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime()) + "fangzhi";
        return MD5Util.getInstance().getMD5(md5Key);
    }

    @Override
    public String getCode() {
        return etMessageCode.getText().toString().trim();
    }

    @Override
    public int getType() {
        return mCodeType;
    }

    @Override
    public String getLoginPwd() {
        return etRegisterPwd.getText().toString().trim();
    }

    @Override
    public String getUserName() {
        return etUserName.getText().toString().trim();
    }

    @Override
    public int getSex() {
        if (rbSex.getCheckedRadioButtonId() == R.id.rb_man) {
            return 1;
        }
        return 0;
    }

    @Override
    public String getCompanyName() {
        return etCompany.getText().toString().trim();
    }

    @Override
    public String getAddress() {
        return etAddress.getText().toString().trim();
    }

    @Override
    public String getProvinceCode() {
        return mProvinceId;
    }

    @Override
    public String getCityCode() {
        return mCityId;
    }

    @Override
    public String getCountyCode() {
        return mAreaId;
    }

    @Override
    public String getScope() {
        return etBusinessArea.getText().toString().trim();
    }

    @Override
    public String getRegisterVcode() {
        return etRegisterVCode.getText().toString().trim();
    }

    @Override
    public String getNewPassword() {
        return etNewPwd.getText().toString().trim();
    }

    @Override
    public String getDeviceRealSize() {
        return ScreenUtils.getScreenSizeOfDevice(this)+"";
    }

    @Override
    public String getScreenWidth() {
        return ScreenUtils.getScreenWidth(this)+"";
    }

    @Override
    public String getScreenHeight() {
        return ScreenUtils.getScreenHeight(this)+"";
    }

    @Override
    public void loginSucceed(String img) {
        dialogDelegate.clearDialog();
        if (img == null || img.isEmpty()) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            Intent intent = new Intent();
            intent.putExtra("url", img);
            intent.setClass(this, CustomActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void loginSucceedMultiple() {
        dialogDelegate.clearDialog();
        startActivity(new Intent(this, ParentActivity.class));
    }

    @Override
    public void loginFailed(String msg) {
        dialogDelegate.stopProgressWithFailed(msg, msg);
    }

    @Override
    public void checkMsgCodeSucceed() {
        dialogDelegate.clearDialog();
        if (mCodeType == 0) {//注册
            layoutRegister.setVisibility(View.VISIBLE);
            layoutRegister.setAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
            tvPhone.setText(etCodePhone.getText().toString());
        } else {//忘记密码
            layoutFindPassword.setVisibility(View.VISIBLE);
            layoutFindPassword.setAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
        }
        layoutGetCode.setAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
        layoutGetCode.setVisibility(View.GONE);
    }

    @Override
    public void checkMsgCodeFailed(String msg) {
        dialogDelegate.stopProgressWithFailed(msg, msg);
    }

    @Override
    public void registerSucceed() {
        SPUtils.put(this, SpKey.USER_NAME, tvPhone.getText());
        SPUtils.put(this, SpKey.PASSWORD, etRegisterPwd.getText().toString());

        etPhone.setText(tvPhone.getText());
        etPassword.setText(etRegisterPwd.getText().toString());
        dialogDelegate.clearDialog();
        onRegisterClose();
        onLogin();
    }

    @Override
    public void registerFailed(String msg) {
        dialogDelegate.stopProgressWithFailed(msg, msg);
    }

    @Override
    public void modificationPasswordFailed(String msg) {
        dialogDelegate.stopProgressWithFailed(msg, msg);
    }

    @Override
    public void modificationPasswordSucceed() {
        SPUtils.put(this, SpKey.USER_NAME, etCodePhone.getText().toString());
        SPUtils.put(this, SpKey.PASSWORD, etNewPwd.getText().toString());

        etPhone.setText(etCodePhone.getText().toString());
        etPassword.setText(etNewPwd.getText().toString());

        dialogDelegate.clearDialog();
        onFindPwdClose();
        onLogin();
    }

    @Override
    public void showProvince(List<LocationArea.Location> list) {
        if (list == null) {
            return;
        }
        mProvinceNames = new ArrayList<>();
        mProvinceIds = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            mProvinceIds.add(list.get(i).getID());
            mProvinceNames.add(list.get(i).getAREA_CNAME());
        }
        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.item_simple_spinner, mProvinceNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// 下拉列表中每个条目的样式
        spProvince.setAdapter(adapter);
    }

    @Override
    public void showCity(List<LocationArea.Location> list) {
        mCityNames = new ArrayList<>();
        mCityIds = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            mCityIds.add(list.get(i).getID());
            mCityNames.add(list.get(i).getAREA_CNAME());
        }
        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.item_simple_spinner, mCityNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// 下拉列表中每个条目的样式
        spCity.setAdapter(adapter);
    }

    @Override
    public void showCounty(List<LocationArea.Location> list) {
        mAreaNames = new ArrayList<>();
        mAreaIds = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            mAreaIds.add(list.get(i).getID());
            mAreaNames.add(list.get(i).getAREA_CNAME());
        }
        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.item_simple_spinner, mAreaNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// 下拉列表中每个条目的样式
        spCounty.setAdapter(adapter);
    }

    @OnItemSelected(R.id.sp_province)
    void onProvinceItemSelected(int position) {
        mProvinceId = mProvinceIds.get(position);
        mPresenter.getCity();
    }

    @OnItemSelected(R.id.sp_city)
    void onCityItemSelected(int position) {
        mCityId = mCityIds.get(position);
        mPresenter.getCounty();
    }

    @OnItemSelected(R.id.sp_county)
    void onAreaItemSelected(int position) {
        mAreaId = mAreaIds.get(position);
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
        T.showShort(this, msg);
    }

    @OnClick(R.id.tv_protocol)
    public void onProtocol() {
        startActivity(new Intent(this, ProtocolActivity.class));
    }

    private int mCodeType = 0;

    @OnClick(R.id.tv_register)
    public void onRegister() {
        mCodeType = 0;
        showGetCodeView();
    }

    @OnClick(R.id.tv_forget_password)
    public void onForgetPassword() {
        mCodeType = 1;
        showGetCodeView();
    }

    @OnClick(R.id.tv_get_code)
    public void onGetCode() {
        if (verificationPhone(etCodePhone, getString(R.string.input_right_phone_number))) {
            mTimer.start();
            mPresenter.getMsgCode();
        }
    }

    private void showGetCodeView() {
        layoutGetCode.setVisibility(View.VISIBLE);
        clearCodeLayout();
        layoutGetCode.setAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
        layoutLogin.setAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
        layoutLogin.setVisibility(View.GONE);
    }

    @OnClick(R.id.iv_close_code)
    public void onCodeClose() {

        layoutGetCode.setVisibility(View.GONE);
        layoutGetCode.setAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
        layoutLogin.setAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
        layoutLogin.setVisibility(View.VISIBLE);
        clearCodeLayout();
    }

    private void clearCodeLayout() {
        etCodePhone.setText("");
        etMessageCode.setText("");
//        mTimer.cancel();
//        mTimer.onFinish();
//        mCount = 60;
    }

    @OnClick(R.id.btn_register_cancel)
    public void onRegisterClose() {
        layoutRegister.setVisibility(View.GONE);
        layoutRegister.setAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
        layoutLogin.setAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
        layoutLogin.setVisibility(View.VISIBLE);
        clearRegisterInfo();
    }

    private void clearRegisterInfo() {
        tvPhone.setText("");
        etUserName.setText("");
        etRegisterPwd.setText("");
        etRegisterPwdConfirm.setText("");
        etCompany.setText("");
        etAddress.setText("");
        etBusinessArea.setText("");

    }

    @OnClick(R.id.btn_register)
    public void onRegisterUser() {

        if (etUserName.getText().toString().isEmpty() ||
                etRegisterPwd.getText().toString().isEmpty() ||
                etCompany.getText().toString().isEmpty()) {
            T.showShort(this, "信息填写不完整！");
            return;
        }
        if (!etRegisterPwd.getText().toString().equals(etRegisterPwdConfirm.getText().toString())) {
            T.showShort(this, "两次密码输入不相同！");
            return;
        }
        dialogDelegate.showProgressDialog(true, "正在注册...");
        mPresenter.register();
    }

    @OnClick(R.id.btn_next)
    public void onNext() {

        if (!verificationPhone(etCodePhone, getString(R.string.input_right_phone_number))) {
            return;
        }
        if (!verificationEmpty(etMessageCode, getString(R.string.request_code_not_be_null))) {
            return;
        }
        dialogDelegate.showProgressDialog(true, "正在验证...");
        mPresenter.checkCode();
    }

    @OnClick(R.id.btn_change_password_cancel)
    public void onFindPwdClose() {
        layoutFindPassword.setVisibility(View.GONE);
        layoutFindPassword.setAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
        layoutLogin.setAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
        layoutLogin.setVisibility(View.VISIBLE);
        clearModificationPassword();
    }

    private void clearModificationPassword() {
        etNewPwd.setText("");
        etNewPwdConfirm.setText("");
    }

    @OnClick(R.id.btn_commit_change_password)
    public void onModificationPassword() {
        if (etNewPwd.getText().toString().isEmpty()) {
            T.showShort(this, "密码不能为空！");
            return;
        }
        if (!etNewPwd.getText().toString().equals(etNewPwdConfirm.getText().toString())) {
            T.showShort(this, "两次密码不相同！");
            return;
        }
        dialogDelegate.showProgressDialog(true, "正在修改...");
        mPresenter.modificationPassword();
    }
}
