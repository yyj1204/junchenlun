package com.wktx.www.subjects.apiresult.mine.tradingrecord;

/**
 * Created by yyj on 2018/6/9.
 * 交易记录列表
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info": {"transa_amount": "7000.00","trusteeship_amount": "63351.89",
 *                  "list": [{"id": "151", "hire_id": "410","add_time": "1520049697",
 *                  "amount": "0.01","remark": "" ,"name": "","status": "5","tow": "客服"}]
 *                  }
 *         }
 * }
 */

public class TradingRecordData {
    private int code;
    private String msg;
    private TradingRecordInfoData info;

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

    public TradingRecordInfoData getInfo() {
        return info;
    }

    public void setInfo(TradingRecordInfoData info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "TradingRecordData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
