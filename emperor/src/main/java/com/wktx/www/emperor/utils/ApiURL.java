package com.wktx.www.emperor.utils;

/**
 * Created by yyj on 2018/1/16.
 *君臣论 api 接口工具类
 *
 *http://api.jcl.55085.cn/api/index.php?service=User_User_Login.Phone
 *
 */

public class ApiURL {
    /**
     *  API的全局URL
     */
    public static final String GLOBAL_URL = "http://api.jcl.55085.cn";

    /**
     *  图片的全局URL
     */
    public static final String GLOBAL_IMG_URL = "http://shop.jcl.55085.cn";

    /**
     *共同部分URL
     */
    public static final String COMMON_URL = "/api/index.php";

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

    /**
     * 首页
     */
    //首页
    public static final String PARAMS_HOME = "Home.Index";

    /**
     * 招聘
     */
    //招聘检索条件
    public static final String PARAMS_RETRIEVAL_CONDITION = "Recruit.GetRetrievalCondition";
    //招聘人才列表
    public static final String PARAMS_RECRUIT_LIST = "Recruit.GetResumeList";
    //需求列表
    public static final String PARAMS_DEMAND_LIST = "Hire.GetDemandList";
    //需求列表信息
    public static final String PARAMS_DEMAND_INFO = "Hire.GetDemandInfo";
    //需求发布需要的选择参数 （店铺、平台、类目、需求模式）
    public static final String PARAMS_DEMAND_RELEASE_CONDITION = "Hire.GetReleaseDemandConditionInfo";
    //需求发布
    public static final String PARAMS_DEMAND_RELEASE= "Hire.ReleaseDemand";
    //简历详情
    public static final String PARAMS_RESUME_INFO= "Resume.GetResumeInfo";
    //简历评价列表
    public static final String PARAMS_RESUME_EVALUATE= "Resume.GetRevaluateList";
    //简历作品列表
    public static final String PARAMS_RESUME_WORKS= "Resume.GetResumeWorksList";
    //简历作品详情
    public static final String PARAMS_RESUME_WORKS_INFO= "Resume.GetResumeWorksInfo";

    /**
     * 我的
     */
    //个人中心
    public static final String PARAMS_CENTER = "Center.Index";
    //账户认证（个人&店铺）
    public static final String PARAMS_CERTIFICATION = "Center.IdentityAuthent";
    //钱包充值
    public static final String PARAMS_PURSE_RECHARGE = "Center.WalletRecharge";
}
