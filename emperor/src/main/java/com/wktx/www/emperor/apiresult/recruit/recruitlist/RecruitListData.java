package com.wktx.www.emperor.apiresult.recruit.recruitlist;

import java.util.List;

/**
 * Created by yyj on 2018/1/29.
 * 招聘片段---职位检索结果列表
 *{
 * "ret": 200,"msg": "",
 * "data": {
 *   "code": 0,"msg": "",
 *   "info":[{"id": "2","name": "啊西歌","picture": "","working_years": "5",
 *   "sex": "1","monthly_money": "4800.00","is_job_hunting": "1",
 *   "bgat": "母婴玩具/精品鞋包/生活百货","bgas": "中国风/时尚简约/质感炫酷","resume_works": []}]
 *  }
 *}
 */

public class RecruitListData {
    private int code;
    private String msg;
    private List<RecruitListInfoData> info;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<RecruitListInfoData> getInfo() {
        return info;
    }

    public void setInfo(List<RecruitListInfoData> info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "RecruitListData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
