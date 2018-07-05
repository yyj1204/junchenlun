package com.wktx.www.emperor.apiresult.mine.transactionrecord;


/**
 * Created by yyj on 2018/3/8.
 * 交易记录
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info": {"transa_amount": "7000.00","trusteeship_amount": "63351.89",
 *         "list": [{"id": "151","add_time": "1520049697","amount": "0.01","remark": ""}]}
 *         }
 * }
 */

public class TransactionRecordData {
    private int code;
    private String msg;
    private TransactionRecordInfoData info;

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
    public TransactionRecordInfoData getInfo() {
        return info;
    }
    public void setInfo(TransactionRecordInfoData info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "TransactionRecordData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
