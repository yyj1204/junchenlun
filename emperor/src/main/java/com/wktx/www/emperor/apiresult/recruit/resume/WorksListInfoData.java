package com.wktx.www.emperor.apiresult.recruit.resume;

/**
 * Created by yyj on 2018/2/8.
 * 简历---评价详情内容
 * "id":"1","title":"大管家店铺装修",
 * "image":"http://shop.jcl.55085.cn/public/upload/goods/2016/04-22/5719843a87434.jpg"
 */

public class WorksListInfoData {
    private String id;//作品id
    private String title;//作品标题
    private String image;//作品预览图

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    @Override
    public String toString() {
        return "WorksListInfoData{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
