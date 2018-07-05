package com.wktx.www.subjects.presenter.message;
import com.wktx.www.subjects.apiresult.CustomApiResult;
import com.wktx.www.subjects.apiresult.message.TaskListData;
import com.wktx.www.subjects.basemvp.ABasePresenter;
import com.wktx.www.subjects.ui.view.IView;
import com.wktx.www.subjects.utils.ApiURL;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.LogUtil;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/1/15.
 * 任务消息片段
 */

public class TaskPresenter extends ABasePresenter<IView> {

    public TaskPresenter() {
    }

    //获取任务消息列表
    public void getInfo(int page){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()));
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("page", page+"");
        httpParams.put("pageSize", "10");
        LogUtil.error("获取任务消息列表","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_TASK_LIST)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<TaskListData>, TaskListData>
                        (new SimpleCallBack<TaskListData>() {
                            @Override
                            public void onError(ApiException e) {
                                LogUtil.error("获取任务消息列表","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(TaskListData result) {
                                if (result != null) {
                                    LogUtil.error("获取任务消息列表","result=="+result.toString());

                                    if (result.getCode()==0){//请求成功
                                        if (result.getInfo().size()!=0){//获取任务消息列表 成功
                                            getmMvpView().onRequestSuccess(result.getInfo());
                                        }else {//获取任务消息列表 失败（没数据）
                                            getmMvpView().onRequestFailure("");
                                        }
                                    }else {//获取任务消息列表 失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
