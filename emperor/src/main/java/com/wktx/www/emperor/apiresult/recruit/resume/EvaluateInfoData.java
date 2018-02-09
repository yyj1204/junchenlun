package com.wktx.www.emperor.apiresult.recruit.resume;

import java.util.List;

/**
 * Created by yyj on 2018/2/8.
 * 简历---评价列表内容
 * "id":"1","uid":"2648","type":"1","design_pattern":"0","service_attitude":"1",
 * "ability":"2","response_speed":"3","evaluation_content":"123456","name":"8b******eb",
 * "images":[]
 */

public class EvaluateInfoData {
    private String id;//评价id
    private String uid;//雇主id
    private String type;//1:包月服务 2:定制服务
    //设计模式 0:其他设计 1:详情页模板设计 2:首页设计 3:直通车设计
    // 4:营销活动设计 5:海报BANNERE设计 6:钻石展位设计 7:爆款策划设计
    private String design_pattern;
    private String service_attitude;//服务态度星级
    private String ability;//能力星级
    private String response_speed;//响应速度星级
    private String evaluation_content;//评价内容
    private String name;//雇主昵称
    private List<String> images;//评价图片

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesign_pattern() {
        return design_pattern;
    }

    public void setDesign_pattern(String design_pattern) {
        this.design_pattern = design_pattern;
    }

    public String getService_attitude() {
        return service_attitude;
    }

    public void setService_attitude(String service_attitude) {
        this.service_attitude = service_attitude;
    }

    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    public String getResponse_speed() {
        return response_speed;
    }

    public void setResponse_speed(String response_speed) {
        this.response_speed = response_speed;
    }

    public String getEvaluation_content() {
        return evaluation_content;
    }

    public void setEvaluation_content(String evaluation_content) {
        this.evaluation_content = evaluation_content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }


    @Override
    public String toString() {
        return "EvaluateInfoData{" +
                "id='" + id + '\'' +
                ", uid='" + uid + '\'' +
                ", type='" + type + '\'' +
                ", design_pattern='" + design_pattern + '\'' +
                ", service_attitude='" + service_attitude + '\'' +
                ", ability='" + ability + '\'' +
                ", response_speed='" + response_speed + '\'' +
                ", evaluation_content='" + evaluation_content + '\'' +
                ", name='" + name + '\'' +
                ", images=" + images +
                '}';
    }
}
