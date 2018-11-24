package com.wktx.www.emperor.apiresult.recruit.retrievalcondition;

import java.io.Serializable;
/**
 * Created by yyj on 2018/1/24.
 */

public class Bean implements Serializable{
    private String id;
    private String name;

    public Bean() {
    }

    public Bean(String id, String name) {
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
        return "StoreConditionBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
