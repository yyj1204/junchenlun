package com.wktx.www.emperor.apiresult.staff.complaint;

/**
 * Created by yyj on 2018/3/6.
 * 我的员工---管理员工---发起投诉信息内容
 * "id": "16","complaint_title": "投诉问题","complaint_content": "投诉内容"
 */

public class ComplaintInfoData {
    private String id;//投诉id
    private String complaint_title;//投诉问题
    private String complaint_content;//投诉内容

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "ComplaintInfoData{" +
                "id='" + id + '\'' +
                ", complaint_title='" + complaint_title + '\'' +
                ", complaint_content='" + complaint_content + '\'' +
                '}';
    }
}
