package com.wktx.www.subjects.apiresult.mine.resume;

/**
 * Created by yyj on 2018/2/2.
 * 预览简历
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":{
 *             "id":"1","tow":"1","name":"胡图图","picture":"","highest_education":"本科","working_years":"一年","sex":"2",
 *             "date_of_birth":"1992.05","residential_city":"浙江-杭州","character_introduction":"脾气好、只做一家","resume_content":"",
 *             "monthly_money":"3500.00","wechat":"","qq":"","phone":"0","typing_speed": "0字/分","bgat":"服装内衣/母婴玩具/精品鞋包",
 *             "bgas":"日韩小清新/中国风/时尚简约","tow_name": "美工",,"is_job_hunting":"1"
 *             "work_experience":[{"work_date":{"start_date":"2014/07","end_date":"2016/08"},"company":"福建网客天下科技有限公司",
 *                                  "bgat":"服装内衣","position":"视觉传达设计师","introduction":"负责整个店铺页面装修，活动页面制作，商品详情"}]
 *             }
 *          }
 * }
 */

public class ResumePreviewData {
    private int code;
    private String msg;
    private ResumePreviewInfoData info;

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
    public ResumePreviewInfoData getInfo() {
        return info;
    }
    public void setInfo(ResumePreviewInfoData info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "ResumeData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
