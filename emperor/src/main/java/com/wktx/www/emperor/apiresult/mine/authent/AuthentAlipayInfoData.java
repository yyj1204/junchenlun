package com.wktx.www.emperor.apiresult.mine.authent;

/**
 * Created by yyj on 2018/1/18.
 * 个人中心---店铺认证信息
 * id : 1
 * name : 发射点发生
 * alipay : 123434556
 * status : 认证失败
 * err_remark :
 * add_time : 2018-07-13 11:08:25
 */

public class AuthentAlipayInfoData {
    private String id;//支付宝认证id
    private String name;//支付宝姓名
    private String alipay;//支付宝账户
    private String status;//认证状态
    private String err_remark;//备注
    private String add_time;//创建时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErr_remark() {
        return err_remark;
    }

    public void setErr_remark(String err_remark) {
        this.err_remark = err_remark;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    @Override
    public String toString() {
        return "AuthentAlipayInfoData{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", alipay='" + alipay + '\'' +
                ", status='" + status + '\'' +
                ", err_remark='" + err_remark + '\'' +
                ", add_time='" + add_time + '\'' +
                '}';
    }
}
