package com.sai.sailib.toutiao;

import android.support.annotation.Nullable;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by dianxiaoer on 2018/11/14.
 */

public class VerificationUtils {

    /**
     * 验证字符串是否为空(包括长度为0)
     *
     * @param
     * @return true 为空, false不为空
     */
    public static boolean isEmptyString(@Nullable String str) {
        return str == null || str.length() == 0 || "null".equals(str);
    }

    public static boolean isEmptyString(String text, String text2) {
        if (text == null || text.length() < 1 || text2 == null || text2.length() < 1) {
            return true;
        }
        return false;
    }

    /**
     * 验证集合是否为空(包括长度为0)
     *
     * @param list
     * @return true 为空, false不为空
     */
    public static boolean isEmptyList(List list) {
        if (list == null || list.size() < 1) {
            return true;
        }
        return false;
    }

    /**
     * 邮箱验证
     */
    public static boolean isEmail(String email) {
        String regex       = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
        return Pattern.matches(regex, email);
    }

    /**
     * 身份证验证
     */
    public static boolean isIdCode(String idcode){
        String REGEX_IDCARD = "(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$|^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|x|X)$)";
        String regex = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)";
        return Pattern.matches(REGEX_IDCARD,idcode);
    }

    /**
     * 手机号验证
     */
    public static boolean isPhone(String phone){
        String regex = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";
        return Pattern.matches(regex,phone);
    }
    /**
     * 隐藏手机中间4位号码
     * 130****0000
     *
     * @param mobile_phone 手机号码
     * @return 130****0000
     */
    public static String hideMobilePhone4(String mobile_phone) {
        if (mobile_phone.length() != 11 || !isPhone(mobile_phone)) {
            return "手机号码不正确";
        }
        return mobile_phone.substring(0, 3) + "****" + mobile_phone.substring(7, 11);
    }
    /**
     * 数字字母验证
     */
    public static boolean isStrAndNum(String strNam){
        String regex = "^[A-Za-z0-9]+$";
        return Pattern.matches(regex,strNam);
    }
}
