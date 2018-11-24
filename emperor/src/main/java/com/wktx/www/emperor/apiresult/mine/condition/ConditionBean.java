package com.wktx.www.emperor.apiresult.mine.condition;

import java.io.Serializable;

/**
 * Created by yyj on 2018/3/05.
 */

public class ConditionBean implements Serializable{
    private String id;
    private String name;

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
