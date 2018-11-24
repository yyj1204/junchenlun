package com.wktx.www.emperor.apiresult.main.message;

import java.util.List;

/**
 * Created by yyj on 2018/2/12.
 * 系统消息通知
 * {
 *  "ret": 200,"msg": ""，
 *  "data": {
 *    "code": 0,"msg": "",
 *     "info": [{
 *     "rec_id": "6","status": "1","title": "阿饭给您安排了新工作","message": "阿饭给您安排了新工作",
 *     "action": "2003","id": "496","send_time": "1531991183"}]
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
