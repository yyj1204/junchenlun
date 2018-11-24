package com.wktx.www.subjects.apiresult.main.demand;

/**
 * Created by yyj on 2018/5/8.
 * 职位招聘需求详情
 * {
 *   "ret": 200,"msg": "",
 *   "data": {"code": 0,"msg": "",
 *         "info": {
 *                  "id": "12","user_id": "2649","title": "sr4002","content": "sr","tow": {"id": "1","name": "美工"},
 *                  "design_pattern": "直通车设计","working_years": "两年","bgap_name": "京东","bgat_name": "3C数码","hire_type": "不限",
 *                  "budget": "1888.00","shop_name": "永蛙田鸡","nickname": "阿饭","address_from": "江苏省-常州市-武进区",
 *                  "head_pic": "http://shop.jcl.55085.cn/public/upload/head_pic/2018/06-29/201806291130245725.jpg",
 *                  "shop_logo": "http://shop.jcl.55085.cn/public/upload/store_logo/2018/06-27/201806271524474142.jpg",
 *                  "shop_url": "www.taobao.com", "remark": "了122",
 *                  "qq": "12347","weixin": "22","phone": "12345678907",
 *                  "end_time": "2020-09-13","add_time": "2018-05-15 10:30:58",
 *                  "is_dated": "0","is_collect": 0}
 *              }
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
