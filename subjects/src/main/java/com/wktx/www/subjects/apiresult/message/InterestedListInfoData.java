package com.wktx.www.subjects.apiresult.message;
/**
 * Created by yyj on 2018/2/8.
 * 对我感兴趣的公司列表内容
 * id : 4
 * company_id : 2657
 * head_pic : http://shop.jcl.55085.cn/public/upload/head_pic/2018/04-18/201804182015280419.jpg
 * nickname : 劲氏软件
 * add_time : 2018-04-16 17:10:52
 */

public class InterestedListInfoData {
    private String id;//收藏id
    private String company_id;//公司id
    private String head_pic;//公司logo
    private String nickname;//公司名称
    private String add_time;//收藏时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
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

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    @Override
    public String toString() {
        return "InterestedListInfoData{" +
                "id='" + id + '\'' +
                ", company_id='" + company_id + '\'' +
                ", head_pic='" + head_pic + '\'' +
                ", nickname='" + nickname + '\'' +
                ", add_time='" + add_time + '\'' +
                '}';
    }
}
