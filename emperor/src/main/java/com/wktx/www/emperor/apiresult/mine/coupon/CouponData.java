package com.wktx.www.emperor.apiresult.mine.coupon;
import java.util.List;

/**
 * Created by yyj on 2018/2/8.
 * 我的红包
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":[{"id": "262","money": "1.00","condition": "100.00","end_time": "1520611199","status": "0","name": "满100减1","from": "活动"}]
 *          }
 * }
 */

public class CouponData {
    private int code;
    private String msg;
    private List<CouponInfoData> info;

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
    public List<CouponInfoData> getInfo() {
        return info;
    }
    public void setInfo(List<CouponInfoData> info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "CouponData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
