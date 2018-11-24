package com.wktx.www.emperor.apiresult.mine.center;

import java.io.Serializable;

/**
 * Created by yyj on 2018/1/18.
 * 个人中心详情
 * "userinfo": {
 *      "nickname": "阿饭","sex": "0",
 *      "head_pic": "http://shop.jcl.55085.cn/public/upload/head_pic/2018/06-29/201806291130245725.jpg",
 *      "user_money": "400787.60","mobile": "18150961675","frozen_money": "0"
 *      },
 * "authent": "1","authent_status": "已认证","authent_type": 2, "alipay_authent_id": "1","alipay_authent_status": "认证失败"
 */

public class CenterInfoData implements Serializable{
    private UserInfoBean userinfo;//用户信息
    private String authent_type;//认证方式 0：未认证 1：个人认证 2：店铺认证
    private String authent_status;//已提交的认证状态 '未认证','审核中','认证失败','已认证'
    private String authent;//认证id
    private String alipay_authent_status;//支付宝认证状态 '未认证','审核中','认证失败','已认证'
    private String alipay_authent_id;//支付宝认证id

    public UserInfoBean getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserInfoBean userinfo) {
        this.userinfo = userinfo;
    }

    public String getAuthent_type() {
        return authent_type;
    }

    public void setAuthent_type(String authent_type) {
        this.authent_type = authent_type;
    }

    public String getAuthent_status() {
        return authent_status;
    }

    public void setAuthent_status(String authent_status) {
        this.authent_status = authent_status;
    }

    public String getAuthent() {
        return authent;
    }

    public void setAuthent(String authent) {
        this.authent = authent;
    }

    public String getAlipay_authent_status() {
        return alipay_authent_status;
    }

    public void setAlipay_authent_status(String alipay_authent_status) {
        this.alipay_authent_status = alipay_authent_status;
    }

    public String getAlipay_authent_id() {
        return alipay_authent_id;
    }

    public void setAlipay_authent_id(String alipay_authent_id) {
        this.alipay_authent_id = alipay_authent_id;
    }

    @Override
    public String toString() {
        return "CenterInfoData{" +
                "userinfo=" + userinfo +
                ", authent_type='" + authent_type + '\'' +
                ", authent_status='" + authent_status + '\'' +
                ", authent='" + authent + '\'' +
                ", alipay_authent_status='" + alipay_authent_status + '\'' +
                ", alipay_authent_id='" + alipay_authent_id + '\'' +
                '}';
    }
}
