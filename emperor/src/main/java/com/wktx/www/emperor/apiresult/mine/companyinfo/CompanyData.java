package com.wktx.www.emperor.apiresult.mine.companyinfo;

/**
 * Created by yyj on 2018/1/18.
 * 个人中心---公司信息
 * {"ret": 200,"msg": "",
 * "data": {"code": 0,"msg": "",
 *          "info": {
 *             "user_id": "2649","username": "ph_da92e307ae9ce43843c563119cdd00e7",
 *             "nickname": "啊饭","head_pic": "","sex": "0","address_from": "","phone": ""，"qq": "0","weixin": "","remark": ""
 *             }
 *          }
 * }
 */

public class CompanyData {
    private int code;
    private String msg;
    private CompanyInfoData info;

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

    public CompanyInfoData getInfo() {
        return info;
    }

    public void setInfo(CompanyInfoData info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "CompanyData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
