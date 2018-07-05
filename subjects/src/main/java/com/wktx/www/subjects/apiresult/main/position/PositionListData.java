package com.wktx.www.subjects.apiresult.main.position;


import java.util.List;

/**
 * Created by yyj on 2018/2/8.
 * 职位招聘列表 \ 我的收藏 \ 面试记录
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":[{
 *                   "id": "17","demand_id": "10","title": "Add","content": "Add","bgap_name": "天猫","bgat_name": "汽车配件",
 *                   "budget": "123.00","shop_name": "555","shop_logo": null,"is_dated": "0"("time": "2018-06-06 14:21:07")
 *                }]
 *          }
 * }
 */

public class PositionListData {
    private int code;
    private String msg;
    private List<PositionListInfoData> info;

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
    public List<PositionListInfoData> getInfo() {
        return info;
    }
    public void setInfo(List<PositionListInfoData> info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "TaskListData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
