package com.wktx.www.emperor.apiresult.recruit.demand;

/**
 * Created by yyj on 2018/1/26.
 * 需求发布需要的选择参数（店铺、平台、类目、需求模式）
 *{
 * "ret": 200,"msg": "",
 * "data": {
 *   "code": 0,"msg": "",
 *   "info": {
 *          "bgap": [{ "id": "1", "name": "淘宝" }],
 *          "bgat": [{"id": "1", "name": "服装内衣" }],
 *          "design_pattern": [{"id": "1",  "name": "其他设计"}]
 *               }
 *          }
 *}
 */

public class DemandReleaseConditionData {
    private int code;
    private String msg;
    private DemandReleaseConditionInfoData info;


    public int getCode()
    {
        return this.code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMsg()
    {
        return this.msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public DemandReleaseConditionInfoData getInfo() {
        return info;
    }

    public void setInfo(DemandReleaseConditionInfoData info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "DemandReleaseConditionData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
