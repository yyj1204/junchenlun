package com.wktx.www.subjects.apiresult.message;

import java.util.List;

/**
 * Created by yyj on 2018/2/8.
 * 对我感兴趣的公司列表
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":[{
 *                    "id": "4","company_id": "2657","nickname": "劲氏软件", "add_time": "2018-04-16 17:10:52",
 *                    "head_pic": "http://shop.jcl.55085.cn/public/upload/head_pic/2018/04-18/201804182015280419.jpg"
 *                    }]
 *          }
 * }
 */

public class InterestedListData {
    private int code;
    private String msg;
    private List<InterestedListInfoData> info;

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
    public List<InterestedListInfoData> getInfo() {
        return info;
    }
    public void setInfo(List<InterestedListInfoData> info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "InterestedListData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
