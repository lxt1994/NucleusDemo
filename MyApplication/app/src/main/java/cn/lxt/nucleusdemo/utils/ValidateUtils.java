package cn.lxt.nucleusdemo.utils;

import android.content.Context;
import android.text.TextUtils;

public class ValidateUtils {

    //校验用户名
    public static boolean validateUserName(String username) {
        return username.matches("(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,8}");
    }

    //校验密码
    public static boolean isPassword(Context context, String password) {
        if (TextUtils.isEmpty(password)) {
            CommonUtils.showToast(context, "密码不能为空");
            return false;
        } else if (!password.matches("(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,18}")) {
            CommonUtils.showToast(context, "密码必须是6到18位的字母加数字");
            return false;
        }
        return true;
    }

    public static boolean isPhoneNum(Context context, String phoneNum) {
        if (TextUtils.isEmpty(phoneNum)) {
            CommonUtils.showToast(context, "手机号不能为空");
            return false;
        } else if (!phoneNum.matches("^1[3-5,7,8][0-9]{9}$")) {
            CommonUtils.showToast(context, "请输入正确的手机号");
            return false;
        }
        return true;
    }

    public static boolean isBankCard(Context context, String cardNo) {
        if (TextUtils.isEmpty(cardNo)) {
            CommonUtils.showToast(context, "银行卡号不能为空");
            return false;
        } else if (!cardNo.matches("^\\d{16,19}$|^\\d{6}[- ]\\d{10,13}$|^\\d{4}[- ]\\d{4}[- ]\\d{4}[- ]\\d{4,7}$")) {
            CommonUtils.showToast(context, "请输入正确的银行卡号");
            return false;
        }
        return true;
    }
}
