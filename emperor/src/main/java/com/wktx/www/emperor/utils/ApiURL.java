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
    //消息通知
    public static final String PARAMS_MESSAGE_LIST = "Center.MessageNotic";
    public static final String PARAMS_MESSAGE_INFO = "Center.GetMessageInfo";

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
    //收藏、取消收藏简历
    public static final String PARAMS_RESUME_COLLECT= "Resume.ResumeCollect";
    //简历评价列表
    public static final String PARAMS_RESUME_EVALUATE= "Resume.GetRevaluateList";
    //简历作品列表
    public static final String PARAMS_RESUME_WORKS= "Resume.GetResumeWorksList";
    //简历作品详情
    public static final String PARAMS_RESUME_WORKS_INFO= "Resume.GetResumeWorksInfo";
    //雇佣第一步：获取雇佣信息
    public static final String PARAMS_HIRE_HIREINFO= "Hire.GetDesignerHireInfo";
    //雇佣第二步：使用优惠券或者余额
    public static final String PARAMS_HIRE_USEDISCOUNT= "Hire.UseCouponOrUserMoney";
    //雇佣第三步：获取支付信息
    public static final String PARAMS_HIRE_PAYINFO= "Hire.GetHirePayInfo";
    //取消雇佣订单
    public static final String PARAMS_HIRE_CANCELORDERS= "Hire.CancelOrder";

    /**
     * 员工
     */
    //

    /**
     * 我的
     */
    //个人中心
    public static final String PARAMS_CENTER = "Center.Index";
    //列表检索条件
    public static final String PARAMS_LIST_CONDITION = "Center.GetHireCondition";
    //账户认证（个人&店铺）
    public static final String PARAMS_CERTIFICATION = "Center.IdentityAuthent";
    //钱包充值
    public static final String PARAMS_PURSE_RECHARGE = "Center.WalletRecharge";
    //店铺列表
    public static final String PARAMS_STORE_LIST = "Center.GetOnlineStoreList";
    //店铺信息
    public static final String PARAMS_STORE_INFO = "Center.GetOnlineStoreInfo";
    //编辑店铺信息需要的选择参数 （平台、类目）
    public static final String PARAMS_STORE_EDIT_CONDITION = "Center.GetEditStoreCondition";
    //编辑（添加）店铺信息
    public static final String PARAMS_STORE_EDIT = "Center.AddEditOnlineStore";
    //浏览记录
    public static final String PARAMS_BROWSING_RECORD = "Center.GetMyBrowseRecords";
    //我的收藏
    public static final String PARAMS_COLLECT_LIST = "Center.GetMyCollectList";
    //交易记录
    public static final String PARAMS_TRANSACTION_RECORD = "Center.TransactionRecord";
    //雇佣记录列表
    public static final String PARAMS_HIRE_RECORD = "Center.HireRecord";
    //雇佣记录信息
    public static final String PARAMS_HIRE_RECORD_INFO = "Center.HireRecordInfo";
    //我的红包
    public static final String PARAMS_COUPON_LIST = "Center.MyCoupon";
    //联系客服信息
    public static final String PARAMS_CONTACT_SERVICE = "Center.GetCustomerServiceInfo";
}
