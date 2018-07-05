package com.wktx.www.subjects.ui.view.mine.resume;
import com.wktx.www.subjects.apiresult.mine.works.WorksDetailsInfoData;
import com.wktx.www.subjects.apiresult.mine.works.condition.WorksConditionInfoData;
import com.wktx.www.subjects.ui.view.IView;

import java.util.ArrayList;


/**
 * Created by yyj on 2018/1/15.
 * 我的作品 --- 作品详情(增删改)
 */

public interface IWorksDetailsView extends IView<WorksDetailsInfoData> {

    /**
     * 获取作品标题
     */
    String getWorksTitle();

    /**
     * 获取类目id
     */
    String getCategoryIds();

    /**
     * 获取设计类型id
     */
    String getDesignPatternId();

    /**
     * 获取作品封面图片url
     */
    String getCoverImgUrl();

    /**
     * 获取作品简介
     */
    String getIntro();

    /**
     * 获取多张作品图片
     */
    ArrayList<String> getWorksImgUrls();


    /**
     * 获取参数（类目、设计类型）成功回调
     */
    void onGetConditionSuccess(WorksConditionInfoData result);

    /**
     * 获取参数（类目、设计类型）失败回调
     */
    void onGetConditionFailure(String result);

    /**
     * 获取图片路径是否成功的回调
     */
    void onGetImgUrlResult(boolean isSuccess, String result);

    /**
     * 获取编辑作品是否成功的回调
     */
    void onChangeWorksResult(boolean isSuccess, String result);
}
