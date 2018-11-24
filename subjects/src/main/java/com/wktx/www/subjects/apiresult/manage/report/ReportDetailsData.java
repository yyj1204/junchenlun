package com.wktx.www.subjects.apiresult.manage.report;

/**
 * Created by yyj on 2018/1/18.
 * 工作报告详情
 * {"ret": 200,"msg": "",
 * "data": {"code": 0,"msg": "",
 *          "info": {
 *                "id": "1","shop_name": "安时代的","today_work_content": "今日工作内容1","store_current_situation": "店铺目前现状1",
 *                "future_operation_plan": "往后运营计划1","need_help": "需要帮助1",
 *                "data_representation": ["http://shop.jcl.55085.cn/public/upload/logo/2017/06-29/222e2f75798b8476e06aafe7c838f41b.png"],
 *                "add_time": "2018-03-13 11:07:33","evaluate_time": "2018-03-15 15:13:25",
 *                "service_attitude": "2","working_ability": "5","response_speed": "4","evaluation_content": "很好哦"
 *             }
 *          }
 * }
 */

public class ReportDetailsData {
    private int code;
    private String msg;
    private ReportDetailsInfoData info;

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

    public ReportDetailsInfoData getInfo() {
        return info;
    }

    public void setInfo(ReportDetailsInfoData info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "ReportDetailsData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
