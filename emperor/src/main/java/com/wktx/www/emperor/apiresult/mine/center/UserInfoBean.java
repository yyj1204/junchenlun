package com.wktx.www.emperor.apiresult.mine.center;

import java.io.Serializable;

/**
 * Created by yyj on 2018/1/18.
 * 用户信息内容
 */

public class UserInfoBean implements Serializable{
    private String nickname;//昵称
    private String head_pic;//头像
    private String sex;//性别 1:男 2:女
    private String user_money;//余额
    private String frozen_money;//冻结余额
    private String mobile;//手机号

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUser_money() {
        return user_money;
    }

    public void setUser_money(String user_money) {
        this.user_money = user_money;
    }

    public String getFrozen_money() {
        return frozen_money;
    }

    public void setFrozen_money(String frozen_money) {
        this.frozen_money = frozen_money;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    @Override
    public String toString() {
        return "UserInfoBean{" +
                "nickname='" + nickname + '\'' +
                ", head_pic='" + head_pic + '\'' +
                ", sex=" + sex +
                ", user_money='" + user_money + '\'' +
                ", frozen_money='" + frozen_money + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}
