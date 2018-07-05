package com.wktx.www.subjects.apiresult.main.message;

import java.util.List;

/**
 * Created by yyj on 2018/2/12.
 * 消息通知
 * {
 *  "ret": 200,"msg": ""，
 *  "data": {
 *    "code": 0,"msg": "",
 *     "info": [{
 *     "rec_id": "35","status": "1","title": "测试消息1",
 *     "message": "11111111111111111111111113333333","send_time": "1520321981"}]
 *        }
 * }
 */

public class MessageListData {
    private int code;
    private String msg;
    private List<MessageListInfoData> info;

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

    public List<MessageListInfoData> getInfo() {
        return info;
    }

    public void setInfo(List<MessageListInfoData> info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "MessageListData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
