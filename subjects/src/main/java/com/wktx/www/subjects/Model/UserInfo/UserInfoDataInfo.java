package com.wktx.www.subjects.Model.UserInfo;

import java.io.Serializable;

public class UserInfoDataInfo implements Serializable {
    private UserInfoDataInfoUserinfo userinfo;
    private UserInfoDataInfoOrder order;

    public UserInfoDataInfoUserinfo getUserinfo() {
        return this.userinfo;
    }

    public void setUserinfo(UserInfoDataInfoUserinfo userinfo) {
        this.userinfo = userinfo;
    }

    public UserInfoDataInfoOrder getOrder() {
        return this.order;
    }

    public void setOrder(UserInfoDataInfoOrder order) {
        this.order = order;
    }
}
