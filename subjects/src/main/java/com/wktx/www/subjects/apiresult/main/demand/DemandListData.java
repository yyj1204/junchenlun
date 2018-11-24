package com.wktx.www.subjects.apiresult.main.demand;


import java.util.List;

/**
 * Created by yyj on 2018/2/8.
 * 职位招聘需求列表 \ 浏览记录 \ 我的收藏 \ 面试记录
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":[{
 *          "id": "17","demand_id": "10","title": "Add","content": "Add","bgap_name": "天猫",
 *          "bgat_name": "汽车配件","budget": "123.00","shop_name": "555","head_pic": null,
 *          "design_pattern": "0","end_time": "2018-08-07 00:00:00","hire_type": "定制",
 *          "is_dated": "0", "tow":"美工","working_years": "四年",
 *          "nickname": "尚仁网络旗舰店6.29","address_from": "福建省-泉州市-丰泽区",
 *          ("time": "2018-06-06 14:21:07")
 *                }]
 *          }
 * }
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
