package com.wktx.www.subjects.apiresult.message;


import java.util.List;

/**
 * Created by yyj on 2018/6/9.
 * 对我感兴趣公司---公司招聘需求列表
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "id": "15","title": "1234455664","content": "whxhxhxhxb","tow": "美工","design_pattern": "海报BANNERE设计","end_time": "2020-09-13",
 *         "hire_type": "不限","working_years": "四年","bgap_name": "京东","bgat_name": "服装内衣","budget": "1111111.00","shop_name": "测试2",
 *         "shop_logo": "http://shop.jcl.55085.cn/public/upload/logo/2017/06-29/222e2f75798b8476e06aafe7c838f41b.png"
 *         }
 * }
 */

public class CompanyDemandListData {
    private int code;
    private String msg;
    private List<CompanyDemandListInfoData> info;

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
    public List<CompanyDemandListInfoData> getInfo() {
        return info;
    }
    public void setInfo(List<CompanyDemandListInfoData> info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "CompanyDemandListData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
