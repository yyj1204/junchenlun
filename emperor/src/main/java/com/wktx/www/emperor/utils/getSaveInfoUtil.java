package com.wktx.www.emperor.utils;

import android.content.Context;

import com.wktx.www.emperor.Model1.Login.LoginInfoDataInfo;
import com.wktx.www.emperor.Model1.UserInfo.UserInfoDataInfo;

/**
 * Created by Administrator on 2017/8/24.
 */

public class getSaveInfoUtil {


    private static LoginInfoDataInfo getSaveInfo;
    private static UserInfoDataInfo savaInfo;
    private static UserInfoDataInfo userInfoDataInfo;

    public static boolean getIsLogin(Context context) {

        boolean islogin = (boolean) SharedPreferenceUtil.getData(context, ConstantUtil.ISLOGIN, false, ConstantUtil.SP_ISLOGIN);
        return islogin;
    }

    public static LoginInfoDataInfo getSaveObject(Context context) {

        getSaveInfo = new SaveObjectUtil(context, ConstantUtil.LOGININFO).getObject(ConstantUtil.LOGININFO_OBJECT, LoginInfoDataInfo.class);
        return getSaveInfo;
    }

    public static int getUser_id(Context context) {

        int user_id = getSaveInfo.getUser_id();
        return user_id;
    }

    public static String getToken(Context context) {
        String token = getSaveInfo.getToken();
        return token;
    }

    public static UserInfoDataInfo getSaveUserInfo(Context context, String token) {

        userInfoDataInfo = new SaveObjectUtil(context, ConstantUtil.USERINFO_NAME).getObject(token, UserInfoDataInfo.class);
        return userInfoDataInfo;
    }

}
