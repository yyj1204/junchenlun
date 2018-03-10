package com.wktx.www.emperor.apiresult.mine.browsingrecord;


import java.util.List;

/**
 * Created by yyj on 2018/2/8.
 * 浏览记录 \ 我的收藏
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":[{
 *                  "rid": "2","tow": "2","name": "啊西歌","picture": "","working_years": "5","typing_speed": "0",
 *                  "sex": "1","residential_city": "江西-南昌","monthly_money": "0.01","bgat": "母婴玩具/精品鞋包/生活百货"
 *                }]
 *          }
 * }
 */

public class BrowsingRecordData {
    private int code;
    private String msg;
    private List<BrowsingRecordInfoData> info;

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
    public List<BrowsingRecordInfoData> getInfo() {
        return info;
    }
    public void setInfo(List<BrowsingRecordInfoData> info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "BrowsingRecordData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
