package com.wktx.www.emperor.apiresult.recruit.demand;

/**
 * Created by yyj on 2018/1/26.
 * 需求详情
 * {
 * "ret": 200,"msg": "",
 *   "data": {
 *        "code": 0,"msg": "",
 *        "info": {
 * id : 30
 * bgap : {"id":"4","name":"苏宁"}
 * bgat : {"id":"4","name":"生活百货"}
 * title : 装修房子
 * content : 给我揉揉孙洪涛VSOP瑞图你女聚聚提督这1838386881我可以盔子
 * design_pattern : {"id":"5","name":"海报BANNERE设计"}
 * budget : 2000.00
 * add_time : 1532577426
 * end_time : 1532966400
 * status_log : [{"remark":"您创建了需求","create_time":"1532577426","user_id":"2649"}]
 * status : 0
 * hire_type : 包月
 * tow : {"id":"1","name":"美工"}
 * working_years : 两年
 * store : {"id":"13","shop_name":"张公子茶叶店","shop_logo":"http://shop.junchenlun.com/public/upload/store_logo/2018/07-01/201807011803044062.jpg"}
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
