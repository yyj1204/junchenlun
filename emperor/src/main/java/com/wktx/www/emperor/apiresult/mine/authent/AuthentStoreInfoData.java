package com.wktx.www.emperor.apiresult.mine.authent;

import java.io.Serializable;

/**
 * Created by yyj on 2018/1/18.
 * 个人中心---店铺认证信息内容
 * id : 2
 * corporate_name : 尚仁0629
 * credit_code : 125487896352489658
 * legal_pers_id_card_front_pic : http://shop.jcl.55085.cn/public/upload/identity_authent/2018/06-29/201806290954595256.jpeg
 * business_license_pic : http://shop.jcl.55085.cn/public/upload/identity_authent/2018/06-29/201806290954597032.jpeg
 * shop_name : 尚仁0629
 * shop_url : www.junchenlun.com
 * taobao_asccount_id : 151515
 * crate_time : 2018-06-29 09:54:59
 * update_time : 2018-07-03 15:08:17
 * status : 已认证
 * err_remark : null
 */

public class AuthentStoreInfoData implements Serializable{
    private String id;//认证id
    private String corporate_name;//公司名称
    private String credit_code;//信用代码
    private String legal_pers_id_card_front_pic;//手持身份证
    private String business_license_pic;//营业执照
    private String shop_name;//	网店名称
    private String shop_url;//店铺地址
    private String taobao_asccount_id;//淘宝主账号id
    private String crate_time;//创建时间
    private String update_time;//通过时间
    private String status;//认证状态
    private String err_remark;//备注

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCorporate_name() {
        return corporate_name;
    }

    public void setCorporate_name(String corporate_name) {
        this.corporate_name = corporate_name;
    }

    public String getCredit_code() {
        return credit_code;
    }

    public void setCredit_code(String credit_code) {
        this.credit_code = credit_code;
    }

    public String getLegal_pers_id_card_front_pic() {
        return legal_pers_id_card_front_pic;
    }

    public void setLegal_pers_id_card_front_pic(String legal_pers_id_card_front_pic) {
        this.legal_pers_id_card_front_pic = legal_pers_id_card_front_pic;
    }

    public String getBusiness_license_pic() {
        return business_license_pic;
    }

    public void setBusiness_license_pic(String business_license_pic) {
        this.business_license_pic = business_license_pic;
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

    public String getCrate_time() {
        return crate_time;
    }

    public void setCrate_time(String crate_time) {
        this.crate_time = crate_time;
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
        return "AuthentStoreInfoData{" +
                "id='" + id + '\'' +
                ", corporate_name='" + corporate_name + '\'' +
                ", credit_code='" + credit_code + '\'' +
                ", legal_pers_id_card_front_pic='" + legal_pers_id_card_front_pic + '\'' +
                ", business_license_pic='" + business_license_pic + '\'' +
                ", shop_name='" + shop_name + '\'' +
                ", shop_url='" + shop_url + '\'' +
                ", taobao_asccount_id='" + taobao_asccount_id + '\'' +
                ", crate_time='" + crate_time + '\'' +
                ", update_time='" + update_time + '\'' +
                ", status='" + status + '\'' +
                ", err_remark=" + err_remark +
                '}';
    }
}
