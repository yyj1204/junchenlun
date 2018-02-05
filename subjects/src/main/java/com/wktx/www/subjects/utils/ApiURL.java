package com.wktx.www.subjects.utils;

/**
 * Created by yyj on 2018/1/16.
 *君臣论 api 接口工具类
 *
 *http://api.ykt.55085.cn/api/index.php?service=User_User_Login.Phone
 *
 */

public class ApiURL {

    /**
     *  API的全局URL
     */
    public static final String GLOBAL_URL = "http://api.ykt.55085.cn";

    /**
     *共同部分URL
     */
    public static final String COMMON_URL = "/api/index.php?service=";

    /**
     * 共同请求参数名
     */
    public static final String PARAMS_KEY = "service";

    /**
     * 用户管理
     */
    //发送验证码
    public static final String PARAMS_SENCODE = "SendSMS.SendCodeSMS";
    //验证验证码
    public static final String PARAMS_CHECKCODE = "SendSMS.CheckCode";
    //手机登录&注册
    public static final String PARAMS_LOGIN = "User_User_Login.Phone";
    //退出登录
    public static final String PARAMS_LOGOUT = "User_User_Info.Logout";
    //忘记密码
    public static final String PARAMS_FORGET_PWD = "User_User_Info.ForgetPassword";
    //获取用户信息
    public static final String PARAMS_USER_INFO = "User_User_Info.GetUserInfo";

}
