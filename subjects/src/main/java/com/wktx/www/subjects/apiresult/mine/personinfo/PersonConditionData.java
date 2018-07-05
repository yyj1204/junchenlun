package com.wktx.www.subjects.apiresult.mine.personinfo;

/**
 * Created by yyj on 2018/5/29.
 * 个人中心---个人信息参数
 * {"ret": 200,"msg": "",
 * "data": {"code": 0,"msg": "",
 *          "info": {
 *             "education": [{"id": "0","name": "未设置"}]
 *             }
 *          }
 * }
 */

public class PersonConditionData {
    private int code;
    private String msg;
    private PersonConditionInfoData info;

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

    public PersonConditionInfoData getInfo() {
        return info;
    }

    public void setInfo(PersonConditionInfoData info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "PersonConditionData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
