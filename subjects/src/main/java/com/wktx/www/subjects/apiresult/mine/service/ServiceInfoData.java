package com.wktx.www.subjects.apiresult.mine.service;
/**
 * Created by yyj on 2018/2/8.
 * 联系客服信息
 * "info":{"phone": "123456789","qq": "123456789","service_time": "9:00-20:00"}
 */

public class ServiceInfoData {
    private String phone;
    private String qq;
    private String service_time;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getService_time() {
        return service_time;
    }

    public void setService_time(String service_time) {
        this.service_time = service_time;
    }


    @Override
    public String toString() {
        return "ServiceInfoData{" +
                "phone='" + phone + '\'' +
                ", qq='" + qq + '\'' +
                ", service_time='" + service_time + '\'' +
                '}';
    }
}
