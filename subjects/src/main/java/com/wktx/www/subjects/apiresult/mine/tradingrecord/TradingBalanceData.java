package com.wktx.www.subjects.apiresult.mine.tradingrecord;


/**
 * Created by yyj on 2018/6/9.
 * 获取账户余额与托管资金
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info": {"balance": "0.00","trusteeship": "0.00"}
 *         }
 * }
 */

public class TradingBalanceData {
    private int code;
    private String msg;
    private TradingBalanceInfoData info;

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
    public TradingBalanceInfoData getInfo() {
        return info;
    }
    public void setInfo(TradingBalanceInfoData info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "TradingBalanceData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
