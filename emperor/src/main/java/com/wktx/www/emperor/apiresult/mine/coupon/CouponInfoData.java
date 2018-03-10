package com.wktx.www.emperor.apiresult.mine.coupon;
/**
 * Created by yyj on 2018/2/8.
 * 我的红包内容
 * "id": "262","money": "1.00","condition": "100.00","end_time": "1520611199","status": "0","name": "满100减1","from": "活动"
 */

public class CouponInfoData {
    private String id;//优惠券id
    private String money;//优惠券金额
    private String condition;//使用条件，满足金额才能使用
    private String end_time;//结束时间
    private String status;//0:未使用 1:已使用 2:已过期
    private String name;//优惠券名称
    private String from;//来源

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public String toString() {
        return "CouponInfoData{" +
                "id='" + id + '\'' +
                ", money='" + money + '\'' +
                ", condition='" + condition + '\'' +
                ", end_time='" + end_time + '\'' +
                ", status='" + status + '\'' +
                ", name='" + name + '\'' +
                ", from='" + from + '\'' +
                '}';
    }
}
