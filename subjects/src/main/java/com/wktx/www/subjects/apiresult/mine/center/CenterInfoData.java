package com.wktx.www.subjects.apiresult.mine.center;

/**
 * Created by yyj on 2018/1/18.
 * 个人中心详情
 */

public class CenterInfoData {
    private UserInfoBean userinfo;//用户信息

    public UserInfoBean getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserInfoBean userinfo) {
        this.userinfo = userinfo;
    }

    @Override
    public String toString() {
        return "CenterInfoData{" +
                "userinfo=" + userinfo +
                '}';
    }
}
