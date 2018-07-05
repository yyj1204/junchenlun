package com.wktx.www.emperor.apiresult.main.home;

/**
 * Created by yyj on 2018/3/24.
 * 职业类型内容
 * "id": "1","name": "美工",
 * "picture": "http://shop.jcl.55085.cn/public/upload/logo/2018/03-24/ef6065111eddefc2f06374aadb0784eb.png"
 */

public class JobTypeInfoData {
    private String id;//类型id
    private String name;//类型名称
    private String picture;//类型图标

    public JobTypeInfoData(String id, String name, String picture) {
        this.id = id;
        this.name = name;
        this.picture = picture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "JobTypeInfoData{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }
}
