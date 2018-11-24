package com.wktx.www.subjects.apiresult.message;

/**
 * Created by yyj on 2018/2/8.
 * 公司邀请列表内容
 * "hire_id": "392","nickname": "尚仁网络有限公司","address_from": "江苏省-常州市-武进区", "add_time": "2018-06-08 08:58:57",
 * "head_pic": "http://shop.jcl.55085.cn/public/upload/head_pic/2018/04-12/201804121827586819.jpg"
 *
 */

public class InviteListInfoData {
    private String hire_id;//雇佣订单id
    private String head_pic;//公司Logo
    private String nickname;//公司名称
    private String address_from;//公司地址
    private String add_time;//添加时间

    public String getHire_id() {
        return hire_id;
    }

    public void setHire_id(String hire_id) {
        this.hire_id = hire_id;
    }

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
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

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    @Override
    public String toString() {
        return "InviteListInfoData{" +
                "hire_id='" + hire_id + '\'' +
                ", head_pic='" + head_pic + '\'' +
                ", nickname='" + nickname + '\'' +
                ", address_from='" + address_from + '\'' +
                ", add_time='" + add_time + '\'' +
                '}';
    }
}
