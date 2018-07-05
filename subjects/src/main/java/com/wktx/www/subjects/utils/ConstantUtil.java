package com.wktx.www.subjects.utils;

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

    /**
     *  intent
     */
    public static final String  KEY_POSITION= "position";//序号
    public static final String  KEY_DATA= "data";//数据
    public static final String  KEY_WHETHER= "whether";//是否
    //相关说明 --- WebView界面
    public static final String KEY_WEB_EXPLAIN= "web_explain";

    /**
     * 相关使用说明
     */
    public static final String EX_ABOUT = "junchenlun";//关于君臣论

    /**
     * 区分是哪个片段进入消息详情界面的
     */
    public static final String  ACTIVITY_ZPXQ= "PositionDetailsActivity";//招聘详情
    public static final String  ACTIVITY_ZPLB= "CompanyRecruitActivity";//招聘列表
    public static final String  ACTIVITY_GG= "NotificationNoticeFragment";//公告
    public static final String  ACTIVITY_TX= "NotificationRemindFragment";//提醒

    /**
     *  请求码
     */
    public static final int REQUESTCODE_CATEGORY = 0;//擅长类目
    public static final int REQUESTCODE_STYLE = 1;//擅长风格
    public static final int REQUESTCODE_SCREENING = 0;//职位检索条件筛选

    /**
     * 结果码
     */
    public static final int RESULTCODE_PICK = 0;//多选界面
    public static final int RESULTCODE_SCREENING = 0;//职位检索条件筛选


    //职位详情应聘弹窗(电话、QQ、微信、简历)
    public static enum InterviewWay {
        PHONE, QQ,WX,RESUME;
    }

    //照片选择弹窗(相册、相机)
    public static enum Type {
        PHONE, CAMERA
    }


    /**
     * 权限---需要的权限参数
     */
    //修改头像&销售额--拍照
    public static final String[] PERMS_CAMERA = {Manifest.permission.CAMERA};
    /**
     * 权限---唯一标识码
     */
    public static final int PERMS_CODE_CAMERA=1;
}
