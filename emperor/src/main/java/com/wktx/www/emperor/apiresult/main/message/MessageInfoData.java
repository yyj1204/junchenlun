package com.wktx.www.emperor.apiresult.main.message;

/**
 * Created by yyj on 2018/2/12.
 * 消息通知内容
 * "rec_id": "35","status": "1","title": "测试消息1",
 * "message": "11111111111111111111111113333333","send_time": "1520321981"
 */

public class MessageInfoData {
    private String rec_id;//消息id
    private String status;//查看状态：0:未查看 1:已查看
    private String title;//消息标题
    private String message;//消息内容
    private String send_time;//发送时间

    public String getRec_id() {
        return rec_id;
    }

    public void setRec_id(String rec_id) {
        this.rec_id = rec_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSend_time() {
        return send_time;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }

    @Override
    public String toString() {
        return "MessageDetailsInfoData{" +
                "rec_id='" + rec_id + '\'' +
                ", status='" + status + '\'' +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", send_time='" + send_time + '\'' +
                '}';
    }
}
