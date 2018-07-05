package com.wktx.www.subjects.apiresult.message;


import java.util.List;

/**
 * Created by yyj on 2018/6/9.
 * 对我感兴趣公司---公司招聘列表
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "id": "15","title": "1234455664","content": "whxhxhxhxb","bgap_name": "京东",
 *         "bgat_name": "服装内衣","budget": "1111111.00","shop_name": "测试2",
 *         "shop_logo": "http://shop.jcl.55085.cn/public/upload/logo/2017/06-29/222e2f75798b8476e06aafe7c838f41b.png"
 *         }
 * }
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
