package com.wktx.www.emperor.apiresult.mine.authent;

import java.io.Serializable;

/**
 * Created by yyj on 2018/1/18.
 * 个人中心---个人认证信息内容
 * id : 9
 * real_name : 杨啊饭
 * id_card_no : 350303030303030303
 * id_card_front_pic : http://shop.jcl.55085.cn/public/upload/identity_authent/2018/06-08/201806081151478296.jpg
 * id_card_back_pic : http://shop.jcl.55085.cn/public/upload/identity_authent/2018/06-08/201806081151483442.jpg
 * "shop_name": "尚仁0629",
 * "shop_url": "www.junchenlun.com",
 * "taobao_asccount_id": "151515",
 * "create_time": "2018-06-08 11:51:48",
 * "update_time": "2018-07-03 15:08:17",
 * "status": "已认证",
 * "err_remark": ""
 */

public class AuthentPersonalInfoData implements Serializable{
    private String id;//认证id
    private String real_name;//真实姓名
    private String id_card_no;//身份证号码
    private String id_card_front_pic;//身份证正面
    private String id_card_back_pic;//身份证背面
    private String shop_name;//	网店名称
    private String shop_url;//店铺地址
    private String taobao_asccount_id;//淘宝主账号id
    private String create_time;//创建时间
    private String update_time;//通过时间
    private String status;//认证状态
    private String err_remark;//备注

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

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShop_url() {
        return shop_url;
    }

    public void setShop_url(String shop_url) {
        this.shop_url = shop_url;
    }

    public String getTaobao_asccount_id() {
        return taobao_asccount_id;
    }

    public void setTaobao_asccount_id(String taobao_asccount_id) {
        this.taobao_asccount_id = taobao_asccount_id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
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

    public String getErr_remark() {
        return err_remark;
    }

    public void setErr_remark(String err_remark) {
        this.err_remark = err_remark;
    }

    @Override
    public String toString() {
        return "CertificationPersonalInfoData{" +
                "id='" + id + '\'' +
                ", real_name='" + real_name + '\'' +
                ", id_card_no='" + id_card_no + '\'' +
                ", id_card_front_pic='" + id_card_front_pic + '\'' +
                ", id_card_back_pic='" + id_card_back_pic + '\'' +
                ", shop_name='" + shop_name + '\'' +
                ", shop_url='" + shop_url + '\'' +
                ", taobao_asccount_id='" + taobao_asccount_id + '\'' +
                ", create_time='" + create_time + '\'' +
                ", update_time='" + update_time + '\'' +
                ", status='" + status + '\'' +
                ", err_remark='" + err_remark + '\'' +
                '}';
    }
}
