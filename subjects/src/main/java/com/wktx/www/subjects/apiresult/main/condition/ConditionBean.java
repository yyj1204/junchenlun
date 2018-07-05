package com.wktx.www.subjects.apiresult.main.condition;

import java.io.Serializable;

/**
 * Created by 369 on 2018/6/1.
 * 职位类型、擅长类目、平台、风格、工作经验
 * "id":"2","name":"客服"
 */

public class ConditionBean implements Serializable{
    private String id;
    private String name;

    public ConditionBean(String id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return "ConditionBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
