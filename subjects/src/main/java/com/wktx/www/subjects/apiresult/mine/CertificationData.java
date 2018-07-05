package com.wktx.www.subjects.apiresult.mine;

/**
 * Created by yyj on 2018/1/18.
 * 个人中心---认证信息
 * {"ret": 200,"msg": "",
 * "data": {"code": 0,"msg": "",
 *          "info": {
 *                "id": "9","real_name": "杨啊饭","id_card_no": "350303030303030303",
 *                "id_card_front_pic": "http://shop.jcl.55085.cn/public/upload/identity_authent/2018/06-08/201806081151478296.jpg",
 *                "id_card_back_pic": "http://shop.jcl.55085.cn/public/upload/identity_authent/2018/06-08/201806081151483442.jpg",
 *                "create_time": "2018-06-08 11:51:48","err_remark": "",
 *                "statusInfo": {"status": "0","info": "审核中"}
 *             }
 *          }
 * }
 */

public class CertificationData {
    private int code;
    private String msg;
    private CertificationInfoData info;

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

    public CertificationInfoData getInfo() {
        return info;
    }

    public void setInfo(CertificationInfoData info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "CertificationData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
