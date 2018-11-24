package com.wktx.www.subjects.apiresult.mine.authent;

/**
 * Created by yyj on 2018/1/18.
 * 个人中心---工作室认证信息
 * {"ret": 200,"msg": "",
 * "data": {"code": 0,"msg": "",
 *          "info": {
 *                "id": "2","corporate_name": "尚仁0629","credit_code": "125487896352489658",
 *                "legal_pers_id_card_front_pic": "http://shop.jcl.55085.cn/public/upload/identity_authent/2018/06-29/201806290954595256.jpeg",
 *                "business_license_pic": "http://shop.jcl.55085.cn/public/upload/identity_authent/2018/06-29/201806290954597032.jpeg",
 *                "shop_name": "尚仁0629","shop_url": "www.junchenlun.com","taobao_asccount_id": "151515",
 *                "crate_time": "2018-06-29 09:54:59","update_time": "2018-07-03 15:08:17","status": "已认证","err_remark": ""
 *             }
 *          }
 * }
 */

public class AuthentStudioData {
    private int code;
    private String msg;
    private AuthentStudioInfoData info;

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

    public AuthentStudioInfoData getInfo() {
        return info;
    }

    public void setInfo(AuthentStudioInfoData info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "AuthentStudioData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
