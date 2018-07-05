package com.wktx.www.subjects.apiresult.mine.works;


/**
 * Created by yyj on 2018/2/8.
 * 简历---作品详情
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":{
 *         "id": "7","rid": "13","title": "打火机",
 *         "design_pattern": {"id": "7","name": "爆款策划设计"},
 *         "brief_intro": "放大恢复肌肤哈师大九分",
 *         "content": ["http://shop.jcl.55085.cn/public/upload/goods/2016/04-22/5719843a87434.jpg"],
 *         "image": "http://shop.jcl.55085.cn/ooo",
 *         "add_time": "0",
 *         "bgatList": [{"id": "1","name": "服装内衣"}]}
 *          }
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
