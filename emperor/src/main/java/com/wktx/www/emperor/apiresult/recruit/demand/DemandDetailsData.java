package com.wktx.www.emperor.apiresult.recruit.demand;

/**
 * Created by yyj on 2018/1/26.
 * 需求详情
 * {
 * "ret": 200,"msg": "",
 *   "data": {
 *        "code": 0,"msg": "",
 *        "info": {
 *            "id": "1","title": "淘宝首页设计","content": "淘宝首页设计淘宝首页设计淘宝首页设计",
 *             "design_pattern": "2","budget": "200.00","add_time": "1516930645","end_time": "0",
 *             "status_log": [{"remark": "您创建了需求","create_time": "1516930645","user_id": "2649"}],
 *             "status": "0"
 *                }
 *          }
 * }
 */

public class DemandDetailsData {
    private int code;
    private String msg;
    private DemandDetailsInfoData info;

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

    public DemandDetailsInfoData getInfo() {
        return info;
    }

    public void setInfo(DemandDetailsInfoData info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "DemandDetailsData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
