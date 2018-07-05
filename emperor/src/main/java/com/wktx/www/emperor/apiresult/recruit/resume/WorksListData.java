package com.wktx.www.emperor.apiresult.recruit.resume;

import java.util.List;

/**
 * Created by yyj on 2018/2/8.
 * 简历---作品列表
 * 美工案例列表
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":[{
 *         "id":"1","title":"大管家店铺装修",
 *         "image":"http://shop.jcl.55085.cn/public/upload/goods/2016/04-22/5719843a87434.jpg"
 *                }]
 *          }
 * }
 */

public class WorksListData {
    private int code;
    private String msg;
    private List<WorksListInfoData> info;

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
    public List<WorksListInfoData> getInfo() {
        return info;
    }
    public void setInfo(List<WorksListInfoData> info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "WorksListData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
