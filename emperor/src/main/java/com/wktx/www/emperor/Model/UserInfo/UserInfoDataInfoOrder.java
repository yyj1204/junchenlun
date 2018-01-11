package com.wktx.www.emperor.Model.UserInfo;

import java.io.Serializable;

public class UserInfoDataInfoOrder implements Serializable{
    private String waitComment;
    private String waitSend;
    private String waitReceive;
    private String waitPay;
    private String return_count;

    public String getWaitComment() {
        return this.waitComment;
    }

    public void setWaitComment(String waitComment) {
        this.waitComment = waitComment;
    }

    public String getWaitSend() {
        return this.waitSend;
    }

    public void setWaitSend(String waitSend) {
        this.waitSend = waitSend;
    }

    public String getWaitReceive() {
        return this.waitReceive;
    }

    public void setWaitReceive(String waitReceive) {
        this.waitReceive = waitReceive;
    }

    public String getWaitPay() {
        return this.waitPay;
    }

    public void setWaitPay(String waitPay) {
        this.waitPay = waitPay;
    }

    public String getReturn_count() {
        return this.return_count;
    }

    public void setReturn_count(String return_count) {
        this.return_count = return_count;
    }
}
