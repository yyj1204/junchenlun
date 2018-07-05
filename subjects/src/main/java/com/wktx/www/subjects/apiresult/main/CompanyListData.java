package com.wktx.www.subjects.apiresult.main;

import java.util.List;

/**
 * Created by yyj on 2018/2/8.
 * 公司列表
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":[{
 *                    "user_id": "2672","nickname": "尚仁网络旗舰店", "address_from": "福建省-泉州市-丰泽区",
 *                    "head_pic": "http://shop.jcl.55085.cn/public/upload/head_pic/2018/05-21/201805211507309664.jpeg"}]
 *          }
 * }
 */

public class CompanyListData {
    private int code;
    private String msg;
    private List<CompanyListInfoData> info;

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
    public List<CompanyListInfoData> getInfo() {
        return info;
    }
    public void setInfo(List<CompanyListInfoData> info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "CompanyListData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
