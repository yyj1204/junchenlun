package com.wktx.www.emperor.apiresult.recruit.demand;

import java.util.List;

/**
 * Created by yyj on 2018/1/26.
 * 需求列表
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *   "code": 0,"msg": "",
 *   "info": [{"id":"1","title":"淘宝首页设计","status":"0"}]
 *   }
 *}
 */

public class DemandListData {
    private int code;
    private String msg;
    private List<DemandListInfoData> info;

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

    public List<DemandListInfoData> getInfo() {
        return info;
    }

    public void setInfo(List<DemandListInfoData> info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "DemandListData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
