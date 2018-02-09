package com.wktx.www.emperor.apiresult.recruit.resume;

import java.util.List;

/**
 * Created by yyj on 2018/2/8.
 * 简历---评价列表
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":[{
 *                "id":"1","uid":"2648","type":"1","design_pattern":"0","service_attitude":"1",
 *                "ability":"2","response_speed":"3","evaluation_content":"123456","name":"8b******eb",
 *                "images":[]
 *                }]
 *          }
 * }
 */

public class EvaluateData {
    private int code;
    private String msg;
    private List<EvaluateInfoData> info;

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
    public List<EvaluateInfoData> getInfo() {
        return info;
    }
    public void setInfo(List<EvaluateInfoData> info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "EvaluateData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
