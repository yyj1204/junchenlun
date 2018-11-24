package com.wktx.www.subjects.apiresult.manage;

/**
 * Created by yyj on 2018/2/8.
 * 我的工作---工作管理---投诉详情内容
 * id : 28
 * hire_id : 560
 * uid : 2649
 * complaint_title : 具体
 * complaint_content : 屠龙记里
 * add_time : 2018-08-10 17:32:03
 * update_time : 0
 * status : 0
 * remark : null
 */

public class ComplaintDetailsInfoData {
    private String id;//投诉id
    private String hire_id;//雇佣id
    private String uid;//雇主id
    private String complaint_title;//投诉问题
    private String complaint_content;//投诉描述
    private String add_time;//	创建时间
    private String update_time;//修改时间
    private String status;//	0:等待审核 1:已审核 2:已撤回
    private String remark;//	处理结果

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHire_id() {
        return hire_id;
    }

    public void setHire_id(String hire_id) {
        this.hire_id = hire_id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getComplaint_title() {
        return complaint_title;
    }

    public void setComplaint_title(String complaint_title) {
        this.complaint_title = complaint_title;
    }

    public String getComplaint_content() {
        return complaint_content;
    }

    public void setComplaint_content(String complaint_content) {
        this.complaint_content = complaint_content;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "ComplaintDetailsInfoData{" +
                "id='" + id + '\'' +
                ", hire_id='" + hire_id + '\'' +
                ", uid='" + uid + '\'' +
                ", complaint_title='" + complaint_title + '\'' +
                ", complaint_content='" + complaint_content + '\'' +
                ", add_time='" + add_time + '\'' +
                ", update_time='" + update_time + '\'' +
                ", status='" + status + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
