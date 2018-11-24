package com.wktx.www.subjects.apiresult.message;


import java.util.List;

/**
 * Created by yyj on 2018/2/8.
 * 公司邀请列表
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":[{
 *                    "hire_id": "392","nickname": "尚仁网络有限公司","address_from": "江苏省-常州市-武进区", "add_time": "2018-06-08 08:58:57",
 *                    "head_pic": "http://shop.jcl.55085.cn/public/upload/head_pic/2018/04-12/201804121827586819.jpg"
 *                }]
 *          }
 * }
 */

public class InviteListData {
    private int code;
    private String msg;
    private List<InviteListInfoData> info;

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
    public List<InviteListInfoData> getInfo() {
        return info;
    }
    public void setInfo(List<InviteListInfoData> info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "InviteListData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
