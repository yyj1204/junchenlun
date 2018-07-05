package com.wktx.www.subjects.apiresult.mine.resume;

import java.io.Serializable;

/**
 * Created by 369 on 2018/6/1.
 * 工作经验
 * id : 2,years : 一年
 */

public class WorkingYearsBean implements Serializable{
    private String id;
    private String years;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    @Override
    public String toString() {
        return "WorkingYearsBean{" +
                "id='" + id + '\'' +
                ", years='" + years + '\'' +
                '}';
    }
}
