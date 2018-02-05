package com.wktx.www.emperor.apiresult.recruit.demand;

/**
 * Created by yyj on 2018/1/26.
 * 需求列表详情
 * {"id":"1","title":"淘宝首页设计","status":"0"}
 */

public class DemandListInfoData {
    private String id;
    private String title;
    private String status;//响应状态 0：未响应，1：已响应

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "DemandListInfoData{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
