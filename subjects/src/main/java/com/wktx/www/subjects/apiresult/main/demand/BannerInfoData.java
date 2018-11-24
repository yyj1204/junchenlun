package com.wktx.www.subjects.apiresult.main.demand;
import java.util.List;

/**
 * Created by yyj on 2018/2/12.
 * 职位片段 --- 轮播图
 * "top_ad": [{
 *      "ad_id": "73","ad_name": "1","ad_link": "",
 *      "ad_code": "http://shop.jcl.55085.cn/public/upload/ad/2018/02-06/202308a2572800c0a0662ea337dfdd44.jpg"}]
 */

public class BannerInfoData {
    private List<BannerBean> top_ad;

    public List<BannerBean> getTop_ad() {
        return top_ad;
    }
    public void setTop_ad(List<BannerBean> top_ad) {
        this.top_ad = top_ad;
    }


    @Override
    public String toString() {
        return "PositionInfoData{" +
                ", top_ad=" + top_ad +
                '}';
    }
}
