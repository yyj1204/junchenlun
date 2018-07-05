package com.wktx.www.subjects.apiresult.message;


import java.util.List;

/**
 * Created by yyj on 2018/2/8.
 * 评价消息列表
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":[{"id": "4","evaluation_content": "评价内容4评价内容4评价内容4","evaluate_time": "2018-03-14 10:47:05"}]
 *          }
 * }
 */

public class EvaluateListData {
    private int code;
    private String msg;
    private List<EvaluateListInfoData> info;

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
    public List<EvaluateListInfoData> getInfo() {
        return info;
    }
    public void setInfo(List<EvaluateListInfoData> info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "EvaluateListData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
