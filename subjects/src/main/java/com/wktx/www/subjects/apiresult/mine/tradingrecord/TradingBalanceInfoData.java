package com.wktx.www.subjects.apiresult.mine.tradingrecord;

/**
 * Created by yyj on 2018/3/8.
 * 获取账户余额与托管资金 内容
 * "balance": "0.00","trusteeship": "0.00"
 */

public class TradingBalanceInfoData {
    private String balance;//余额
    private String trusteeship;//托管金额

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getTrusteeship() {
        return trusteeship;
    }

    public void setTrusteeship(String trusteeship) {
        this.trusteeship = trusteeship;
    }

    @Override
    public String toString() {
        return "TradingBalanceInfoData{" +
                "balance='" + balance + '\'' +
                ", trusteeship='" + trusteeship + '\'' +
                '}';
    }
}
