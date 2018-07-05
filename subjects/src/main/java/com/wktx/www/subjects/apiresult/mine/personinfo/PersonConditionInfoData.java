package com.wktx.www.subjects.apiresult.mine.personinfo;

import com.wktx.www.subjects.apiresult.main.condition.ConditionBean;

import java.util.ArrayList;

/**
 * Created by yyj on 2018/5/29.
 * 个人中心---个人信息参数内容
 * "education": [{"id": "0","name": "未设置"}]
 */

public class PersonConditionInfoData {
   private ArrayList<ConditionBean> education;//学历列表

    public ArrayList<ConditionBean> getEducation() {
        return education;
    }

    public void setEducation(ArrayList<ConditionBean> education) {
        this.education = education;
    }

    @Override
    public String toString() {
        return "PersonConditionInfoData{" +
                "education=" + education +
                '}';
    }
}
