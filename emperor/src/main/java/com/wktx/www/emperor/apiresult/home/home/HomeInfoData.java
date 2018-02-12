package com.wktx.www.emperor.apiresult.home.home;

import java.util.List;

/**
 * Created by yyj on 2018/2/12.
 * 首页片段---职位列表（轮播图+职位）
 * "resume_list": [{
 *      "id": "2","name": "啊西歌","picture": "","working_years": "5","typing_speed": "0字/分",
 *      "sex": "1","monthly_money": "4800.00","is_job_hunting": "1","bgat": "母婴玩具/精品鞋包/生活百货",
 *      "bgas": "中国风/时尚简约/质感炫酷"}],
 * "top_ad": [{
 *      "ad_id": "73","ad_name": "1","ad_link": "",
 *      "ad_code": "http://shop.jcl.55085.cn/public/upload/ad/2018/02-06/202308a2572800c0a0662ea337dfdd44.jpg"}]
 */

public class HomeInfoData {
    private List<ResumeListBean> resume_list;
    private List<BannerBean> top_ad;

    public List<ResumeListBean> getResume_list() {
        return resume_list;
    }

    public void setResume_list(List<ResumeListBean> resume_list) {
        this.resume_list = resume_list;
    }

    public List<BannerBean> getTop_ad() {
        return top_ad;
    }

    public void setTop_ad(List<BannerBean> top_ad) {
        this.top_ad = top_ad;
    }


    @Override
    public String toString() {
        return "HomeInfoData{" +
                "resume_list=" + resume_list +
                ", top_ad=" + top_ad +
                '}';
    }
}
