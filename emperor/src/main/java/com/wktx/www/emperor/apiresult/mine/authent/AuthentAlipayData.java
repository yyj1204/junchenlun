package com.wktx.www.emperor.apiresult.mine.authent;

/**
 * Created by yyj on 2018/1/18.
 * 个人中心---支付宝认证信息
 * {"ret": 200,"msg": "",
 * "data": {"code": 0,"msg": "",
 *          "info": {
 *                "id": "1","name": "发射点发生","alipay": "123434556","status": "认证失败","err_remark": "","add_time": "2018-07-13 11:08:25"
 *             }
 *          }
 * }
 */

public class AuthentAlipayData {
    private int code;
    private String msg;
    private AuthentAlipayInfoData info;

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

    public AuthentAlipayInfoData getInfo() {
        return info;
    }

    public void setInfo(AuthentAlipayInfoData info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "AuthentAlipayData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
