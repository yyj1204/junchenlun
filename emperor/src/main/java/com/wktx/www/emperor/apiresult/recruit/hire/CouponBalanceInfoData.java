package com.wktx.www.emperor.apiresult.recruit.hire;

import java.io.Serializable;

/**
 * Created by yyj on 2018/2/8.
 * 简历---雇佣---使用优惠券或者余额内容
 * "hire_id": "13","project_start_time": "1519660800","project_end_time": 1522339199,"hire_money": "3500.00","user_money_pay_all":0
 */

public class CouponBalanceInfoData implements Serializable{
    private String hire_id;//雇佣订单id
    private String project_start_time;//项目开始时间
    private String project_end_time;//项目结束时间
    private String hire_money;//待支付金额
    private int user_money_pay_all;//0:不是全款余额支付 1:全款余额支付

    public String getHire_id() {
        return hire_id;
    }

    public void setHire_id(String hire_id) {
        this.hire_id = hire_id;
    }

    public String getProject_start_time() {
        return project_start_time;
    }

    public void setProject_start_time(String project_start_time) {
        this.project_start_time = project_start_time;
    }

    public String getProject_end_time() {
        return project_end_time;
    }

    public void setProject_end_time(String project_end_time) {
        this.project_end_time = project_end_time;
    }

    public String getHire_money() {
        return hire_money;
    }

    public void setHire_money(String hire_money) {
        this.hire_money = hire_money;
    }


    public int getUser_money_pay_all() {
        return user_money_pay_all;
    }

    public void setUser_money_pay_all(int user_money_pay_all) {
        this.user_money_pay_all = user_money_pay_all;
    }

    @Override
    public String toString() {
        return "CouponBalanceInfoData{" +
                "hire_id='" + hire_id + '\'' +
                ", project_start_time='" + project_start_time + '\'' +
                ", project_end_time='" + project_end_time + '\'' +
                ", hire_money='" + hire_money + '\'' +
                ", user_money_pay_all='" + user_money_pay_all + '\'' +
                '}';
    }
}
