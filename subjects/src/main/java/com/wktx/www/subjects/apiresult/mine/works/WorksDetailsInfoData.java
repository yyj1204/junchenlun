package com.wktx.www.subjects.apiresult.mine.works;

import com.wktx.www.subjects.apiresult.main.condition.ConditionBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyj on 2018/2/8.
 * 简历---作品详情内容
 *"id": "7","rid": "13","title": "打火机",
 *"design_pattern": {"id": "7","name": "爆款策划设计"},
 *"brief_intro": "放大恢复肌肤哈师大九分",
 *"content": ["http://shop.jcl.55085.cn/public/upload/goods/2016/04-22/5719843a87434.jpg"],
 *"image": "http://shop.jcl.55085.cn/ooo",
 *"add_time": "0",
 *"bgatList": [{"id": "1","name": "服装内衣"}]
 */

public class WorksDetailsInfoData {
    private String id;//作品id
    private String rid;//简历id
    private String title;//作品标题
    private String brief_intro;//作品简介
    private ConditionBean design_pattern;//设计类型
    private ArrayList<ConditionBean> bgatList;//多个类目
    private String image;//封面图片
    private ArrayList<String> content;//多张作品图片
    private String add_time;//添加时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
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

    public ConditionBean getDesign_pattern() {
        return design_pattern;
    }

    public void setDesign_pattern(ConditionBean design_pattern) {
        this.design_pattern = design_pattern;
    }

    public ArrayList<ConditionBean> getBgatList() {
        return bgatList;
    }

    public void setBgatList(ArrayList<ConditionBean> bgatList) {
        this.bgatList = bgatList;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<String> getContent() {
        return content;
    }

    public void setContent(ArrayList<String> content) {
        this.content = content;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }


    @Override
    public String toString() {
        return "WorksDetailsInfoData{" +
                "id='" + id + '\'' +
                ", rid='" + rid + '\'' +
                ", title='" + title + '\'' +
                ", brief_intro='" + brief_intro + '\'' +
                ", design_pattern=" + design_pattern +
                ", bgatList=" + bgatList +
                ", image='" + image + '\'' +
                ", content=" + content +
                ", add_time='" + add_time + '\'' +
                '}';
    }
}
