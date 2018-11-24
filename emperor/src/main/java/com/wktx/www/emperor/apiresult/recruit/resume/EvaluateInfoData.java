package com.wktx.www.emperor.apiresult.recruit.resume;

import java.util.List;

/**
 * Created by yyj on 2018/2/8.
 * 简历---评价列表内容
 * "username": "ph_da92e307ae9ce43843c563119cdd00e7","nickname": "阿饭","id": "11","uid": "2649",
 * "service_attitude": "5","ability": "4","response_speed": "3","images": [],"evaluation_content": "很后悔","add_time": "1531813392"
 */

public class EvaluateInfoData {
    private String id;//评价id
    private String uid;//雇主id
    private String username;//雇主名
    private String nickname;//雇主昵称
    private String service_attitude;//工作态度星级
    private String ability;//工作能力星级
    private String response_speed;//工作效率星级
    private String evaluation_content;//评价内容
    private String add_time;//评价时间
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
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
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", service_attitude='" + service_attitude + '\'' +
                ", ability='" + ability + '\'' +
                ", response_speed='" + response_speed + '\'' +
                ", evaluation_content='" + evaluation_content + '\'' +
                ", add_time='" + add_time + '\'' +
                ", images=" + images +
                '}';
    }
}
