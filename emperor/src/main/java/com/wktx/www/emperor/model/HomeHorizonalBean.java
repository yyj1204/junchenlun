package com.wktx.www.emperor.model;

/**
 * Created by yyj on 2018/2/13.
 * 首页横向推荐
 */

public class HomeHorizonalBean {
    private String name;
    private int img;

    public HomeHorizonalBean(String name, int img) {
        this.name = name;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

}
