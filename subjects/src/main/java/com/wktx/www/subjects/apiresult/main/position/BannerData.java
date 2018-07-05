package com.wktx.www.subjects.apiresult.main.position;

/**
 * Created by yyj on 2018/5/8.
 * 职位片段 --- 轮播图列表
 * {
 *  "ret": 200,"msg": "",
 *  "data": {
 *    "code": 0,"msg": "",
 *    "info": {
 *    "top_ad": [{
 *          "ad_id": "73","ad_name": "1","ad_link": "",
 *          "ad_code": "http://shop.jcl.55085.cn/public/upload/ad/2018/02-06/202308a2572800c0a0662ea337dfdd44.jpg"}]
 *           }
 *        }
 * }
 */

public class BannerData {
    private int code;
    private String msg;
    private BannerInfoData info;

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

    public BannerInfoData getInfo() {
        return info;
    }

    public void setInfo(BannerInfoData info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "BannerData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
