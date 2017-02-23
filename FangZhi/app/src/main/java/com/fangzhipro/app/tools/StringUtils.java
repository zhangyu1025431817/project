package com.fangzhipro.app.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhangyu on 2016/5/12.
 */
public class StringUtils {
    /**
     * 验证手机号
     */
    public static boolean checkPhone(String phone) {
        if (phone.matches("[1][34578]\\d{9}")) {
            return true;
        } else {
            return false;
        }
    }
    /** 验证用户名 */
    public static boolean userNameValidation(String username) {
        String str = "^(?:\\w|\\-|[\u4e00-\u9fa5])+$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(username);
        return m.matches();
    }
    /** 验证中文名 */
    public static boolean chinesename(String username) {
        String str = "^[\u4e00-\u9fa5]{2,50}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(username);
        return m.matches();
    }
    /**
     * 判断身份证
     */
    public static boolean personIdValidation(String text) {
        String regx = "[0-9]{17}x";
        String reg1 = "[0-9]{15}";
        String regex = "[0-9]{18}";
        boolean flag = text.matches(regx) || text.matches(reg1) || text.matches(regex);
        return flag;
    }

}
