package com.wktx.www.emperor.apiresult.main.artistcase;

/**
 * Created by yyj on 2018/3/30.
 */

public class WorksConditionBean{
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
        return "WorksConditionBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
