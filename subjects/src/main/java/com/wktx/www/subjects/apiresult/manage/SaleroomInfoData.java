package com.wktx.www.subjects.apiresult.manage;

/**
 * Created by yyj on 2018/3/6.
 * 我的工作---管理工作---销售额内容
 * "total_sales": "1266.00","last_month_sales": "1266.00",
 * "last_sales_data": "http://shop.jcl.55085.cn/public/upload/logo/2017/06-29/222e2f75798b8476e06aafe7c838f41b.png"
 */

public class SaleroomInfoData {
    private String total_sales;//累计销售总额
    private String last_month_sales;//上个月销售额
    private String last_sales_data;//上个月销售额数据

    public String getTotal_sales() {
        return total_sales;
    }

    public void setTotal_sales(String total_sales) {
        this.total_sales = total_sales;
    }

    public String getLast_month_sales() {
        return last_month_sales;
    }

    public void setLast_month_sales(String last_month_sales) {
        this.last_month_sales = last_month_sales;
    }

    public String getLast_sales_data() {
        return last_sales_data;
    }

    public void setLast_sales_data(String last_sales_data) {
        this.last_sales_data = last_sales_data;
    }
    @Override
    public String toString() {
        return "SaleroomInfoData{" +
                "total_sales='" + total_sales + '\'' +
                ", last_month_sales='" + last_month_sales + '\'' +
                ", last_sales_data='" + last_sales_data + '\'' +
                '}';
    }
}

