package com.wktx.www.emperor.apiresult.mine.withdraw;

import java.util.List;

/**
 * Created by yyj on 2018/2/8.
 * 提现记录
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":[{"id": "262","money": "1.00", "status": "审核中","remark": "","add_time": "2018-07-16 16:20:32","update_time": "0"}]
 *          }
 * }
 */

public class WithdrawListData {
    private int code;
    private String msg;
    private List<WithdrawListInfoData> info;

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
    public List<WithdrawListInfoData> getInfo() {
        return info;
    }
    public void setInfo(List<WithdrawListInfoData> info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "WithdrawListData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
