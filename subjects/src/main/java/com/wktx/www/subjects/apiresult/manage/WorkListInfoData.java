package com.wktx.www.subjects.apiresult.manage;

import java.io.Serializable;

/**
 * Created by yyj on 2018/2/8.
 * 我的工作(公司邀请)列表内容
 * "hire_id": "392","nickname": "尚仁网络有限公司","address_from": "江苏省-常州市-武进区", "add_time": "2018-06-08 08:58:57",
 * "status": "1", "dismissal_id": "0","head_pic": "http://shop.jcl.55085.cn/public/upload/head_pic/2018/04-12/201804121827586819.jpg"
 *
 */

public class WorkListInfoData implements Serializable{
    private String hire_id;//雇佣订单id
    private String nickname;//公司名称
    private String address_from;//公司地址
    private String dismissal_id;//解雇id，如果有值则跳到解雇详情（我的工作）
    private String status;//状态（我的工作）0:未开始 1:合作中 2:请假中 3:暂停中 4:投诉中 5:被解雇 6:已完结 7:已退款 8:已取消 9:续约中;
    private String add_time;//添加时间（公司邀请）
    private String head_pic;//公司Logo

    public String getHire_id() {
        return hire_id;
    }

    public void setHire_id(String hire_id) {
        this.hire_id = hire_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAddress_from() {
        return address_from;
    }

    public void setAddress_from(String address_from) {
        this.address_from = address_from;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDismissal_id() {
        return dismissal_id;
    }

    public void setDismissal_id(String dismissal_id) {
        this.dismissal_id = dismissal_id;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    @Override
    public String toString() {
        return "WorkListInfoData{" +
                "hire_id='" + hire_id + '\'' +
                ", nickname='" + nickname + '\'' +
                ", address_from='" + address_from + '\'' +
                ", status='" + status + '\'' +
                ", dismissal_id='" + dismissal_id + '\'' +
                ", add_time='" + add_time + '\'' +
                ", head_pic='" + head_pic + '\'' +
                '}';
    }
}
