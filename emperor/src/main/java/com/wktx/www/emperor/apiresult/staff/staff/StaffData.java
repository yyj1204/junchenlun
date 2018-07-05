package com.wktx.www.emperor.apiresult.staff.staff;


import java.util.List;

/**
 * Created by yyj on 2018/2/8.
 * 我的员工 \ 雇佣记录
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":[{
 *                   "hire_id": "151","rid": "1","tow": "1","tow_name": "美工","project_start_time": "1520006400",
 *                   "project_end_time": "1522684799","hire_price": "0.01","is_pay": "1","name": "胡图图","picture": "","type": "1"}]
 *          }
 * }
 */

public class StaffData {
    private int code;
    private String msg;
    private List<StaffInfoData> info;

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public List<StaffInfoData> getInfo() {
        return info;
    }
    public void setInfo(List<StaffInfoData> info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "StaffData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }

    public static class DataBean {
        public static class InfoBean {
            /**
             * hire_id : 151
             * rid : 1
             * tow : 1
             * project_start_time : 1520006400
             * project_end_time : 1522684799
             * hire_price : 0.01
             * is_pay : 1
             * name : 胡图图
             * picture :
             * type : 1
             * status_str : 合作中
             */

            private String hire_id;
            private String rid;
            private String tow;
            private String project_start_time;
            private String project_end_time;
            private String hire_price;
            private String is_pay;
            private String name;
            private String picture;
            private String type;
            private String status_str;

            public String getHire_id() {
                return hire_id;
            }

            public void setHire_id(String hire_id) {
                this.hire_id = hire_id;
            }

            public String getRid() {
                return rid;
            }

            public void setRid(String rid) {
                this.rid = rid;
            }

            public String getTow() {
                return tow;
            }

            public void setTow(String tow) {
                this.tow = tow;
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

            public String getIs_pay() {
                return is_pay;
            }

            public void setIs_pay(String is_pay) {
                this.is_pay = is_pay;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getStatus_str() {
                return status_str;
            }

            public void setStatus_str(String status_str) {
                this.status_str = status_str;
            }
        }
    }
}
