package com.wktx.www.subjects.apiresult.manage.companystore;

import java.util.List;

/**
 * Created by yyj on 2018/2/8.
 * 公司店铺列表
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":[{"id": "14","bgap": "天猫","bgat": "母婴玩具","shop_name": "20180629","shop_url": "wktx.cn",
 *         "shop_logo": "http://shop.jcl.55085.cn/public/upload/store_logo/2018/06-29/201806291018170927.jpg"}]
 *          }
 * }
 */

public class StoreListData {
    private int code;
    private String msg;
    private List<StoreListInfoData> info;

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
    public List<StoreListInfoData> getInfo() {
        return info;
    }
    public void setInfo(List<StoreListInfoData> info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "StoreListData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
