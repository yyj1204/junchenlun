package com.wktx.www.subjects.apiresult.mine;

import java.io.Serializable;

/**
 * Created by yyj on 2018/1/18.
 * 个人中心---认证信息内容
 * id : 9
 * real_name : 杨啊饭
 * id_card_no : 350303030303030303
 * id_card_front_pic : http://shop.jcl.55085.cn/public/upload/identity_authent/2018/06-08/201806081151478296.jpg
 * id_card_back_pic : http://shop.jcl.55085.cn/public/upload/identity_authent/2018/06-08/201806081151483442.jpg
 * create_time : 2018-06-08 11:51:48
 * err_remark :
 * statusInfo : {"status":"0","info":"审核中"}
 */

public class CertificationInfoData implements Serializable{
    private String id;//认证id
    private String real_name;//真实姓名
    private String id_card_no;//身份证号码
    private String id_card_front_pic;//身份证正面
    private String id_card_back_pic;//身份证背面
    private String create_time;//创建时间
    private String err_remark;//未通过原因
    private StatusInfoBean statusInfo;//认证状态

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getId_card_no() {
        return id_card_no;
    }

    public void setId_card_no(String id_card_no) {
        this.id_card_no = id_card_no;
    }

    public String getId_card_front_pic() {
        return id_card_front_pic;
    }

    public void setId_card_front_pic(String id_card_front_pic) {
        this.id_card_front_pic = id_card_front_pic;
    }

    public String getId_card_back_pic() {
        return id_card_back_pic;
    }

    public void setId_card_back_pic(String id_card_back_pic) {
        this.id_card_back_pic = id_card_back_pic;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getErr_remark() {
        return err_remark;
    }

    public void setErr_remark(String err_remark) {
        this.err_remark = err_remark;
    }

    public StatusInfoBean getStatusInfo() {
        return statusInfo;
    }

    public void setStatusInfo(StatusInfoBean statusInfo) {
        this.statusInfo = statusInfo;
    }

    @Override
    public String toString() {
        return "CertificationInfoData{" +
                "id='" + id + '\'' +
                ", real_name='" + real_name + '\'' +
                ", id_card_no='" + id_card_no + '\'' +
                ", id_card_front_pic='" + id_card_front_pic + '\'' +
                ", id_card_back_pic='" + id_card_back_pic + '\'' +
                ", create_time='" + create_time + '\'' +
                ", err_remark='" + err_remark + '\'' +
                ", statusInfo=" + statusInfo +
                '}';
    }

    public static class StatusInfoBean implements Serializable{
        private String status;//认证状态码 0:未审核 1:未通过 2:已通过
        private String info;//	认证详情

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        @Override
        public String toString() {
            return "StatusInfoBean{" +
                    "status='" + status + '\'' +
                    ", info='" + info + '\'' +
                    '}';
        }
    }
}
