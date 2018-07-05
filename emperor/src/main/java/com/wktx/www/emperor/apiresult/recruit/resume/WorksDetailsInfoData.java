package com.wktx.www.emperor.apiresult.recruit.resume;

import java.util.List;

/**
 * Created by yyj on 2018/2/9.
 * 简历---作品列表---作品详情内容
 * "rid":"1","id":"1","title":"亚麻拖鞋详情","brief_intro":"亚麻拖鞋详情","bgat":"服装内衣/母婴玩具/精品鞋包"
 * "content":["http://shop.jcl.55085.cn/public/upload/goods/2016/04-22/5719843a87434.jpg"]
 */

public class WorksDetailsInfoData {
    private String rid;//简历id
    private String id;
    private String title;//标题
    private String brief_intro;//简介
    private String bgat;//类目
    private List<String> content;//作品内容

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

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

    public String getBrief_intro() {
        return brief_intro;
    }

    public void setBrief_intro(String brief_intro) {
        this.brief_intro = brief_intro;
    }

    public String getBgat() {
        return bgat;
    }

    public void setBgat(String bgat) {
        this.bgat = bgat;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }


    @Override
    public String toString() {
        return "WorksDetailsInfoData{" +
                "rid='" + rid + '\'' +
                ",id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", brief_intro='" + brief_intro + '\'' +
                ", bgat='" + bgat + '\'' +
                ", content=" + content +
                '}';
    }
}
