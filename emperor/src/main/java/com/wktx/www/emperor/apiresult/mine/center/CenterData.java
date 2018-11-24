package com.wktx.www.emperor.apiresult.mine.center;

/**
 * Created by yyj on 2018/1/18.
 * 个人中心
 * "userinfo": {
 *      "nickname": "阿饭","sex": "0",
 *      "head_pic": "http://shop.jcl.55085.cn/public/upload/head_pic/2018/06-29/201806291130245725.jpg",
 *      "user_money": "400787.60","mobile": "18150961675","frozen_money": "0"
 *      },
 * "authent": "1","authent_status": "已认证","authent_type": 2, "alipay_authent_id": "1","alipay_authent_status": "认证失败"
 */

public class CenterData {
    private int code;
    private String msg;
    private CenterInfoData info;

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

    public CenterInfoData getInfo() {
        return info;
    }

    public void setInfo(CenterInfoData info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "CenterData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
