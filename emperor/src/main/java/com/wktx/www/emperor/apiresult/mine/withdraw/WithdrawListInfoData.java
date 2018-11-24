package com.wktx.www.emperor.apiresult.mine.withdraw;

/**
 * Created by yyj on 2018/2/8.
 * 提现记录内容
 * "id": "1",
 * "money": "100.00",
 * "status": "审核中",
 * "remark": "",
 * "add_time": "2018-07-16 16:20:32",
 * "update_time": "0"
 */

public class WithdrawListInfoData {
    private String id;
    private String money;//提现金额
    private String status;//提现状态
    private String remark;//备注
    private String add_time;
    private String update_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }


    @Override
    public String toString() {
        return "WithdrawListInfoData{" +
                "id='" + id + '\'' +
                ", money='" + money + '\'' +
                ", status='" + status + '\'' +
                ", remark='" + remark + '\'' +
                ", add_time='" + add_time + '\'' +
                ", update_time='" + update_time + '\'' +
                '}';
    }
}
