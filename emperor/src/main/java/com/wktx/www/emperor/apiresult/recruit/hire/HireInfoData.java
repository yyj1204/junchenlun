package com.wktx.www.emperor.apiresult.recruit.hire;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yyj on 2018/2/8.
 * 简历---雇佣信息内容
 * "hire_id": "13","hire_type": "1","project_start_time": "1519660800","project_end_time": 1522339199,
 * "hire_price": "3500.00","user_money": "0.01",
 * "coupon_list": [{
 *          "id":"1","money":"4500", "condition":"使用条件，满足金额才能使用",
 *          "end_time":"结束时间","available":"优惠券是否可用 true:可用 false:不可用"}]
 */

public class HireInfoData implements Serializable{
    private String hire_id;//雇佣订单id
    private String hire_type;//雇佣方式 1:包月,2:定制 3:雇佣单人
    private String project_start_time;//项目开始时间
    private String project_end_time;//项目结束时间
    private String hire_price;//雇佣金额
    private String user_money;//用户余额
    private ArrayList<CouponListBean> coupon_list;//优惠券列表

    public String getHire_id() {
        return hire_id;
    }

    public void setHire_id(String hire_id) {
        this.hire_id = hire_id;
    }

    public String getHire_type() {
        return hire_type;
    }

    public void setHire_type(String hire_type) {
        this.hire_type = hire_type;
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

    public String getHire_price() {
        return hire_price;
    }

    public void setHire_price(String hire_price) {
        this.hire_price = hire_price;
    }

    public String getUser_money() {
        return user_money;
    }

    public void setUser_money(String user_money) {
        this.user_money = user_money;
    }

    public ArrayList<CouponListBean> getCoupon_list() {
        return coupon_list;
    }

    public void setCoupon_list(ArrayList<CouponListBean> coupon_list) {
        this.coupon_list = coupon_list;
    }


    @Override
    public String toString() {
        return "HireInfoData{" +
                "hire_id='" + hire_id + '\'' +
                ", hire_type='" + hire_type + '\'' +
                ", project_start_time='" + project_start_time + '\'' +
                ", project_end_time='" + project_end_time + '\'' +
                ", hire_price='" + hire_price + '\'' +
                ", user_money='" + user_money + '\'' +
                ", coupon_list=" + coupon_list +
                '}';
    }

    public static class CouponListBean implements Serializable{
        private String id;//优惠券id
        private String money;//优惠券金额
        private String condition;//使用条件，满足金额才能使用
        private String end_time;//结束时间
        private boolean available;//优惠券是否可用 true:可用 false:不可用

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getCondition() {
            return condition;
        }

        public void setCondition(String condition) {
            this.condition = condition;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public boolean isAvailable() {
            return available;
        }

        public void setAvailable(boolean available) {
            this.available = available;
        }


        @Override
        public String toString() {
            return "CouponListBean{" +
                    "id='" + id + '\'' +
                    ", money='" + money + '\'' +
                    ", condition='" + condition + '\'' +
                    ", end_time='" + end_time + '\'' +
                    ", available=" + available +
                    '}';
        }
    }
}
