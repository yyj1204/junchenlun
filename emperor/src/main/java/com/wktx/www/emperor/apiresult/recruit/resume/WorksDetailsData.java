package com.wktx.www.emperor.apiresult.recruit.resume;

/**
 * Created by yyj on 2018/2/9.
 * 简历---作品列表---作品详情
 *{
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":{
 *                 "rid":"1","id":"1","title":"亚麻拖鞋详情","brief_intro":"亚麻拖鞋详情","bgat":"服装内衣/母婴玩具/精品鞋包"
 *                 "content":["http://shop.jcl.55085.cn/public/upload/goods/2016/04-22/5719843a87434.jpg"]
 *                 }
 *         }
 * }
 */

public class WorksDetailsData {
    private int code;
    private String msg;
    private WorksDetailsInfoData info;

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

    public WorksDetailsInfoData getInfo() {
        return info;
    }

    public void setInfo(WorksDetailsInfoData info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "WorksDetailsData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
