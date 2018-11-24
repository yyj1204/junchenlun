package com.wktx.www.subjects.apiresult.main.notification;

/**
 * Created by yyj on 2018/2/12.
 * 系统消息通知内容
 * "rec_id": "6","status": "1","title": "阿饭给您安排了新工作","message": "阿饭给您安排了新工作",
 * "action": "2003","id": "496","send_time": "1531991183"
 */

public class MessageListInfoData {
    private String rec_id;//消息id
    private String status;//查看状态：0:未查看 1:已查看
    private String title;//消息标题
    private String message;//消息内容
    private String send_time;//发送时间
    //暂不用
    private String action;//消息分类：1000：君主，2000：臣民，1：君主或臣民详情 2：订单  3：工作 4：评价
    private String id;//

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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSend_time() {
        return send_time;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }

    @Override
    public String toString() {
        return "MessageListInfoData{" +
                "rec_id='" + rec_id + '\'' +
                ", status='" + status + '\'' +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", action='" + action + '\'' +
                ", id='" + id + '\'' +
                ", send_time='" + send_time + '\'' +
                '}';
    }
}
