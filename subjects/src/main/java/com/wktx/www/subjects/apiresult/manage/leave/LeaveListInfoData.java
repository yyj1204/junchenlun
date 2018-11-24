package com.wktx.www.subjects.apiresult.manage.leave;


import java.io.Serializable;

/**
 * Created by yyj on 2018/2/8.
 * 请假记录内容
 * id : 2
 * uid : 2681
 * hire_id : 560
 * reason : 打萨芬
 * image :
 * start_time : 2018-08-13 15:35:35
 * end_time : 2018-08-14 15:35:35
 * status : 已拒绝
 * add_time : 2018-08-13 15:48:48
 */

public class LeaveListInfoData implements Serializable{
    private String id;//请假id
    private String uid;//臣民id
    private String hire_id;//雇佣订单id
    private String reason;//请假原因
    private String image;//请假图片
    private String start_time;//开始时间
    private String end_time;//结束时间
    private String status;//处理状态 0：申请中，1：已同意，2：已拒绝
    private String add_time;//申请时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getHire_id() {
        return hire_id;
    }

    public void setHire_id(String hire_id) {
        this.hire_id = hire_id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
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

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    @Override
    public String toString() {
        return "LeaveListInfoData{" +
                "id='" + id + '\'' +
                ", uid='" + uid + '\'' +
                ", hire_id='" + hire_id + '\'' +
                ", reason='" + reason + '\'' +
                ", image='" + image + '\'' +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", status='" + status + '\'' +
                ", add_time='" + add_time + '\'' +
                '}';
    }
}
