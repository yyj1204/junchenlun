package com.wktx.www.emperor.apiresult.main.message;

/**
 * Created by yyj on 2018/2/12.
 * 系统消息通知详情
 * {
 *  "ret": 200,"msg": ""，
 *  "data": {
 *    "code": 0,"msg": "",
 *     "info": {"status": "1","title": "测试消息1","message": "11111111111111111111111113333333","send_time": "1520321981"}
 *        }
 * }
 */

public class MessageDetailsData {
    private int code;
    private String msg;
    private MessageDetailsInfoData info;

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

    public MessageDetailsInfoData getInfo() {
        return info;
    }

    public void setInfo(MessageDetailsInfoData info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "MessageDetailsData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
