package com.wktx.www.subjects.apiresult.mine.personinfo;

/**
 * Created by yyj on 2018/1/18.
 * 个人中心---个人信息
 * {"ret": 200,"msg": "",
 * "data": {"code": 0,"msg": "",
 *          "info": {
 *            "name": "啊饭","picture": "","sex": "1","highest_education": "1","date_of_birth": "1994.01","residential_city": "莆田",
 *            "phone": "18150961675","qq": "123456789","wechat": "123456789","character_introduction": "好"
 *             }
 *          }
 * }
 */

public class PersonData {
    private int code;
    private String msg;
    private PersonInfoData info;

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

    public PersonInfoData getInfo() {
        return info;
    }

    public void setInfo(PersonInfoData info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "PersonData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
