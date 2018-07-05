package com.wktx.www.emperor.apiresult.main.home;

/**
 * Created by yyj on 2018/2/12.
 * 首页片段---职位列表
 * {
 *  "ret": 200,"msg": "",
 *  "data": {
 *    "code": 0,"msg": "",
 *    "info": {
 *    "resume_list": [{
 *          "id": "2","name": "啊西歌","tow": "1","tow_name": "美工","picture": "","working_years": "5",
 *          "typing_speed": "0字/分","sex": "1","monthly_money": "4800.00","is_job_hunting": "1",
 *          "bgat": "母婴玩具/精品鞋包/生活百货","bgas": "中国风/时尚简约/质感炫酷"}],
 *    "top_ad": [{
 *          "ad_id": "73","ad_name": "1","ad_link": "",
 *          "ad_code": "http://shop.jcl.55085.cn/public/upload/ad/2018/02-06/202308a2572800c0a0662ea337dfdd44.jpg"}]
 *           }
 *        }
 * }
 */

public class HomeData {
    private int code;
    private String msg;
    private HomeInfoData info;

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

    public HomeInfoData getInfo() {
        return info;
    }

    public void setInfo(HomeInfoData info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "HomeInfoData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
