package com.wktx.www.subjects.apiresult.main;


/**
 * Created by yyj on 2018/2/8.
 * 公司列表 内容
 * "user_id": "2672","nickname": "尚仁网络旗舰店", "address_from": "福建省-泉州市-丰泽区",
 * "head_pic": "http://shop.jcl.55085.cn/public/upload/head_pic/2018/05-21/201805211507309664.jpeg"
 */

public class CompanyListInfoData {
    private String user_id;//公司id
    private String head_pic;//公司logo
    private String nickname;//公司名称
    private String address_from;//公司地址

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    @Override
    public String toString() {
        return "CompanyListInfoData{" +
                "user_id='" + user_id + '\'' +
                ", head_pic='" + head_pic + '\'' +
                ", nickname='" + nickname + '\'' +
                ", address_from='" + address_from + '\'' +
                '}';
    }
}
