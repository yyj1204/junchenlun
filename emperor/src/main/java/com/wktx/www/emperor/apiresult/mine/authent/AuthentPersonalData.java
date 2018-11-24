package com.wktx.www.emperor.apiresult.mine.authent;

/**
 * Created by yyj on 2018/1/18.
 * 个人中心---个人认证信息
 * {"ret": 200,"msg": "",
 * "data": {"code": 0,"msg": "",
 *          "info": {
 *                "id": "9","real_name": "杨啊饭","id_card_no": "350303030303030303",
 *                "id_card_front_pic": "http://shop.jcl.55085.cn/public/upload/identity_authent/2018/06-08/201806081151478296.jpg",
 *                "id_card_back_pic": "http://shop.jcl.55085.cn/public/upload/identity_authent/2018/06-08/201806081151483442.jpg",
 *                "shop_name": "尚仁0629","shop_url": "www.junchenlun.com","taobao_asccount_id": "151515",
 *                "create_time": "2018-06-08 11:51:48","update_time": "2018-07-03 15:08:17","status": "已认证","err_remark": ""
 *             }
 *          }
 * }
 */

public class AuthentPersonalData {
    private int code;
    private String msg;
    private AuthentPersonalInfoData info;

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

    public AuthentPersonalInfoData getInfo() {
        return info;
    }

    public void setInfo(AuthentPersonalInfoData info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "AuthentPersonalData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
