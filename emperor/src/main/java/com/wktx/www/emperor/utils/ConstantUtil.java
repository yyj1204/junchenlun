package com.wktx.www.emperor.utils;

import android.Manifest;

/**
 * Created by yyj on 2018/01/18 .
 * 常量工具类
 */

public class ConstantUtil {

    public static final String TOAST_NONET= "请检查网络!";
    public static final String TOAST_LOGOUT= "请先登录！";
    public static final String TOAST_ERROR= "请求出错了~";
    //退出登录弹窗
    public static final String LOGOUT = "退出";
    //修改公司logo弹窗
    public static enum Type {
        PHONE, CAMERA
    }
    //SharedPreferenceUtil
    public static final String HEADBASE64_NAME = "headBase64_Name";
    public static final String HEADBASE64STR_KEY = "headBase64Str";
    //请求码
    public static final int REQUESTCODE_SCREENING = 0;//招聘检索条件筛选
    public static final int REQUESTCODE_BALANCEPAY = 0;//余额支付
    //拍照、本地选择、裁剪
    public static final int REQUEST_TAKE_PICTURE = 3;
    public static final int REQUEST_CHOOSE_PICTURE = 4;
    public static final int REQUEST_CROP_SMALL_PICTURE = 5;

    //结果码
    public static final int RESULTCODE_SCREENING = 0;//招聘检索条件筛选
    public static final int RESULTCODE_BALANCEPAY_CANCELE = 0;//取消输入余额支付密码
    public static final int RESULTCODE_BALANCEPAY_OK = 1;//余额支付

    //intent
    public static final String  KEY_POSITION= "position";//序号
    public static final String  KEY_DATA= "data";//数据
    public static final String  KEY_INFO= "info";//信息
    public static final String  KEY_ISOK= "isOk";//是否成功、正确
    //相关说明 --- WebView界面
    public static final String KEY_WEB_EXPLAIN= "web_explain";
    /**
     * 相关使用说明
     */
    public static final String EX_ABOUT = "junchenlun";//关于君臣论
    public static final String EX_COUPONRULE = "usage_rules";//红包使用规则


    //界面（区分是哪个界面支付后进入支付结果界面的）
    public static final String  ACTIVITY_TGGZ= "TrusteeshipSalary";//托管工资
    public static final String  ACTIVITY_QBCZ= "PurseRecharge";//钱包充值
    //（区分是哪个界面进入托管工资界面的）
    public static final String  ACTIVITY_JL= "ResumeActivity";//简历信息
    public static final String  ACTIVITY_QRDD= "OrdersInfoActivity";//确认订单信息
    public static final String  ACTIVITY_GYJL= "HireRecordActivity";//雇佣记录
    //（区分是哪个片段进入消息详情界面的）
    public static final String  ACTIVITY_GG= "MessageNoticeFragment";//公告
    public static final String  ACTIVITY_TX= "MessageRemindFragment";//提醒
    //（区分是哪个界面进入作品详情界面的）
    public static final String  ACTIVITY_SS= "SearchActivity";//搜索
    public static final String  ACTIVITY_ZP= "RecruitListFragment";//招聘
    public static final String  ACTIVITY_AL= "ArtistCaseActivity";//美工案例

    /**
     * 支付宝支付结果
     */
    public static final int SDK_PAY_FLAG = 1;
    public static final int SDK_AUTH_FLAG = 2;
    /**
     * 微信支付
     */
    //微信APPId
    public static final String WX_APP_ID = "wxdfb137b9c34a5a28";


    /**
     * 权限---需要的权限参数
     */
    //修改头像&账户认证权限--拍照
    public static final String[] PERMS_CAMERA = {Manifest.permission.CAMERA};
    /**
     * 权限---唯一标识码
     */
    public static final int PERMS_CODE_CAMERA=1;
}
