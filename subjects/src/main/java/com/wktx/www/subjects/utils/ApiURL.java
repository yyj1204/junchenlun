package com.wktx.www.subjects.utils;

/**
 * Created by yyj on 2018/1/16.
 *君臣论-臣民 api 接口工具类
 *
 *http://cts.junchenlun.com/api/index.php?service=User_User_Login.Phone
 *
 */

public class ApiURL {
    /**
     *  API的全局URL
     */
//    public static final String GLOBAL_URL = "http://cts.jcl.55085.cn";
//    public static final String GLOBAL_URL_JZ = "http://api.jcl.55085.cn";
    //臣民端api
    public static final String GLOBAL_URL = "http://cts.junchenlun.com";
    //君主端api
    public static final String GLOBAL_URL_JZ = "http://api.junchenlun.com";

    /**
     *共同部分URL
     */
    public static final String COMMON_URL = "/api/index.php";

    /**
     * 共同请求参数名
     */
    public static final String PARAMS_KEY = "service";

    //协议说明展示接口
    public static final String URL_EXPLAIN ="http://cts.junchenlun.com/api/index.php?service=Exhibition.Index&ename=";

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
     * 职位
     */
    //参数（平台、风格、类目、职位、工作经验）
    public static final String PARAMS_POSITION_CONDITION = "Resume.GetJobList";
    //首页轮播图
    public static final String PARAMS_POSITION_BANNER = "Home.Index";
    //首页职位招聘列表
    public static final String PARAMS_POSITION_LIST = "Demand.GetDemandList";
    //搜索公司列表
    public static final String PARAMS_COMPANY_LIST = "Company.SearchCompany";
    //职位招聘详情
    public static final String PARAMS_POSITION_INFO = "Demand.GetDemandInfo";
    //收藏、取消收藏职位
    public static final String PARAMS_POSITION_COLLECT= "DemandCollect.DemandCollectChange";
    //投递简历
    public static final String PARAMS_POSITION_SENDRESUME= "Interview.AddInterview";
    //消息通知列表
    public static final String PARAMS_MESSAGE_LIST = "Center.MessageNotic";
    //消息通知内容
    public static final String PARAMS_MESSAGE_INFO = "Center.GetMessageInfo";

    /**
     * 管理
     */
    //我的工作列表
    public static final String PARAMS_WORK_LIST = "Work.GetMyWorkList";
    //我的工作详情
    public static final String PARAMS_WORK_INFO = "Work.GetMyWorkInfo";
    //请假
    public static final String PARAMS_WORK_LEAVE = "Work.Leave";
    //上传请假图片Base64
    public static final String PARAMS_LEAVE_BASE64 = "Upload.UploadleaveSales";
    //请假记录
    public static final String PARAMS_WORK_LEAVELIST = "Work.LeaveList";
    //我的绩效考核详情
    public static final String PARAMS_MANAGE_KPI = "Work.GetStaffInfo";
    //公司店铺列表
    public static final String PARAMS_COMPANY_STORELIST = "Work.CompanyShopList";
    //公司解雇详情
    public static final String PARAMS_FIRE_INFO = "Work.GetDismissalInfo";
    //接受公司解雇
    public static final String PARAMS_FIRE_ACCEPT = "Work.ReceiveDismissal";
    //拒绝公司解雇
    public static final String PARAMS_FIRE_REFUSE = "Work.RefuseDismissal";
    //申请介入公司解雇
    public static final String PARAMS_FIRE_APPLY = "Work.ServiceIntervene";
    //公司暂停工作详情
    public static final String PARAMS_PAUSE_INFO = "Work.SuspendWorkInfo";
    //公司投诉详情
    public static final String PARAMS_COMPLAINT_INFO = "Work.ComplaintInfo";
    //获取工作安排的工作报告列表
    public static final String PARAMS_REPORT_LIST = "Work.GetArrangementWorkReportList";
    //获取工作报告详情
    public static final String PARAMS_REPORT_INFO = "Work.GetWorkReportInfo";
    //上传工作报告数据图片Base64
    public static final String PARAMS_REPORT_BASE64 = "Upload.UploadWorkReport";
    //提交工作报告
    public static final String PARAMS_REPORT_RELEASE = "Work.EditWorkReport";
    //获取工作销售额详情
    public static final String PARAMS_SALEROOM_INFO = "Work.GetSalesVolumeInfo";
    //上传工作销售额数据图片Base64
    public static final String PARAMS_SALEROOM_BASE64 = "Upload.UploadWorkSales";
    //编辑工作销售额(增改)
    public static final String PARAMS_SALEROOM_EDITINFO = "Work.EditSalesVolume";
    //我的工资
    public static final String PARAMS_WORK_SALARY= "Work.GetPaySalary";
    //签到记录
    public static final String PARAMS_WORK_ATTENDANCE= "Work.AttendanceRecord";
    //签到
    public static final String PARAMS_WORK_SIGNIN= "Work.SignIn";

    /**
     * 消息
     */
    //我的任务消息列表
    public static final String PARAMS_TASK_LIST = "Work.GetArrangementWorkList";
    //我的评价消息列表
    public static final String PARAMS_EVALUATE_LIST = "Work.WorkEvaluateList";
    //我的交易消息列表
    public static final String PARAMS_DEAL_LIST = "Work.GetDealList";
    //对我感兴趣公司列表
    public static final String PARAMS_INTERESTED_LIST = "Company.GetBeInterestedInMeList";
    //公司招聘列表
    public static final String PARAMS_COMPANY_DEMANDLIST = "Company.GetDemandList";
    //公司邀请列表
    public static final String PARAMS_INVITE_LIST = "Work.GetCompanyInvite";
    //公司邀请详情
    public static final String PARAMS_INVITE_INFO = "Work.GetCompanyInviteInfo";
    //接受公司邀请
    public static final String PARAMS_INVITE_ACCEPT = "Work.ReceiveInvite";
    //拒绝公司邀请
    public static final String PARAMS_INVITE_REFUSE = "Work.RefuseInvite";



    /**
     * 我的
     */
    //个人中心
    public static final String PARAMS_CENTER = "Center.Index";
    //个人信息
    public static final String PARAMS_PERSON_INFO = "Resume.GetPersonalInfo";
    //编辑个人信息
    public static final String PARAMS_PERSON_EDITINFO = "Resume.EditPersonalInfo";
    //个人信息参数(学历)
    public static final String PARAMS_PERSON_CONDITION = "Center.GetEditUserCondition";
    //账户认证（个人&工作室）
    public static final String PARAMS_CERTIFICATION = "Center.IdentityAuthent";
    //账户认证详情（个人&工作室）
    public static final String PARAMS_CERTIFICATION_INFO = "Center.AuthentInfo";
    //上传简历相关图片（作品，个性简历）Base64
    public static final String PARAMS_RESUME_BASE64 = "Upload.UploadResume";
    //上传头像图片Base64
    public static final String PARAMS_HEAD_BASE64 = "Resume.UploadHeadPic";
    //我的简历
    public static final String PARAMS_RESUME = "Resume.GetMyResume";
    //编辑个性简历
    public static final String PARAMS_RESUME_RESUMEIMG = "Resume.EditResumeContent";
    //预览简历
    public static final String PARAMS_RESUME_PREVIEW = "Resume.GetResumeInfo";
    //编辑应聘职位(增改)
    public static final String PARAMS_RESUME_POSITION = "Resume.EditResumeJob";
    //删除应聘职位
    public static final String PARAMS_RESUME_POSITION_DELETE = "Resume.DeleteResumeJob";
    //修改找工作状态
    public static final String PARAMS_RESUME_JOBHUNTING = "Resume.IsJobHunting";
    //编辑工作经历(增删改)
    public static final String PARAMS_RESUME_EXPERIENCE = "Resume.EditWorkExp";
    //添加标签
    public static final String PARAMS_RESUME_TAGS = "Resume.EditTags";
    //我的作品列表
    public static final String PARAMS_WORKS_LIST = "Works.GetWorksList";
    //我的作品参数（类目、设计模式）
    public static final String PARAMS_WORKS_CONDITION = "Works.GetWorksInfoList";
    //作品详情
    public static final String PARAMS_WORKS_INFO = "Works.GetWorksInfo";
    //编辑作品(增改)
    public static final String PARAMS_WORKS_EDITINFO = "Works.EditWorks";
    //删除作品
    public static final String PARAMS_WORKS_DELETE = "Works.DeleteWorks";
    //浏览记录
    public static final String PARAMS_BROWSING_RECORD = "Center.GetMyBrowseRecords";
    //我的收藏
    public static final String PARAMS_COLLECT_LIST = "DemandCollect.GetDemandCollectList";
    //面试记录
    public static final String PARAMS_INTERVIEW_LIST = "Interview.GetInterviewList";
    //雇佣记录
    public static final String PARAMS_HIRE_LIST = "Center.GetHireRecord";
    //交易记录
    public static final String PARAMS_TRADING_LIST = "TradingRecord.TransactionRecord";
    //修改登录密码
    public static final String PARAMS_EDIT_PWD = "User_User_Info.EditUserPassword";
    //修改支付密码
    public static final String PARAMS_EDIT_PAYPWD = "User_User_Info.EditPayPassword";
    //修改手机号码
    public static final String PARAMS_EDIT_PHONE = "Center.EditPhone";
    //给客服留言
    public static final String PARAMS_CONTACT_MESSAGE = "Center.LeaveWord";


    /**
     * 使用君主端API接口
     */
    //支付宝认证
    public static final String PARAMS_ALIPAY_AUTHENT = "Center.AlipayAuthent";
    //支付宝认证详情
    public static final String PARAMS_ALIPAY_AUTHENT_INFO = "Center.AlipayAuthentInfo";
    //钱包提现
    public static final String PARAMS_PURSE_WITHDRAW = "Center.Withdraw";
    //钱包提现记录
    public static final String PARAMS_WITHDRAW_LIST = "Center.WithdrawList";
    //钱包充值
    public static final String PARAMS_PURSE_RECHARGE = "Center.WalletRecharge";
    //联系客服信息
    public static final String PARAMS_CONTACT_SERVICE = "Center.GetCustomerServiceInfo";


    //获取文章、查看任务、修改用户、店铺信息、用户信息（5个）不需要

}
