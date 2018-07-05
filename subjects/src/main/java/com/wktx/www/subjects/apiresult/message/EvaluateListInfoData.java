package com.wktx.www.subjects.apiresult.message;
/**
 * Created by yyj on 2018/2/8.
 * 评价消息列表内容
 * id : 4
 * evaluation_content : 评价内容4评价内容4评价内容4
 * evaluate_time : 2018-03-14 10:47:05
 */

public class EvaluateListInfoData {
    private String id;//报告id
    private String evaluation_content;//评价内容
    private String evaluate_time;//评价时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEvaluation_content() {
        return evaluation_content;
    }

    public void setEvaluation_content(String evaluation_content) {
        this.evaluation_content = evaluation_content;
    }

    public String getEvaluate_time() {
        return evaluate_time;
    }

    public void setEvaluate_time(String evaluate_time) {
        this.evaluate_time = evaluate_time;
    }

    @Override
    public String toString() {
        return "EvaluateListInfoData{" +
                "id='" + id + '\'' +
                ", evaluation_content='" + evaluation_content + '\'' +
                ", evaluate_time='" + evaluate_time + '\'' +
                '}';
    }
}
