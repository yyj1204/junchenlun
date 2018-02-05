package com.wktx.www.emperor.apiresult.recruit.retrievalcondition;

/**
 * Created by yyj on 2018/1/24.
 * 检索条件信息
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *   "code": 0,"msg": "",
 *   "info": {
 *     "top": { "tow": [{"id": "1","name": "美工" }]},
 *     "bottom": {
 *          "bgat": [{"id": "1", "name": "服装内衣" }],
 *          "bgap": [{ "id": "1", "name": "淘宝" }],
 *          "cust_service_type": [{ "id": "1", "name": "售前客服" }],
 *          "working_years": [{"id": "1",  "name": "一年以内"}],
 *          "sex": [{"id": "1", "name": "男"}]
 *               }
 *          }
 *        }
 * }
 */

public class RetrievalConditionData {
    private int code;
    private String msg;
    private RetrievalConditionInfoData info;


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

    public RetrievalConditionInfoData getInfo() {
        return info;
    }

    public void setInfo(RetrievalConditionInfoData info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "RetrievalConditionData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
