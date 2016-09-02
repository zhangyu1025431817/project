package com.fangzhi.app.network;

import com.fangzhi.app.bean.BaseResponseBean;

/**
 * Created by smacr on 2016/8/30.
 */
public class RequestCodeMessage {
    public static final String ACCOUNT_NOT_FOND = "该手机号码不存在";
    public static final String ACCOUNT_PAST = "该用户已过期";
    public static final String PASSWORD_ERROR ="用户密码错误";
    public static final String MESSAGE_CODE_PAST="验证码已经过期";
    public static final String OTHER_ERROR = "服务器异常";
    public static final String SYSTEM_ERROR = "系统异常";
    public static final String ILLEGAL_OPERATION = "操作异常";

    public static boolean verificationResponseCode(BaseResponseBean bean){
        switch (bean.getCode()){
            case 1000:
                return true;
            case 1001:
                bean.setMsg(PASSWORD_ERROR);
                break;
            case 1002:
                bean.setMsg(ACCOUNT_PAST);
                break;
            case 1003:
                bean.setMsg(ACCOUNT_NOT_FOND);
                break;
            case 1004:
                bean.setMsg(MESSAGE_CODE_PAST);
                break;
            case 9998:
                bean.setMsg( SYSTEM_ERROR);
                break;
            case 9999:
                bean.setMsg(ILLEGAL_OPERATION);
                break;
        }
        return false;
    }
}
