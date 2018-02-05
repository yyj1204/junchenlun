package com.wktx.www.subjects.basemvp;


/**
 * Created by yyj on 2018/1/12.
 * 将重复操作抽取出来，解决MVP代码冗余、内存泄漏
 */

public abstract class ABasePresenter<V extends IBaseView> {
    private V mMvpView;
    /**
     * 绑定V层
     * @param view
     */
    public void attachMvpView(V view){
        this.mMvpView = view;
    }

    /**
     * 解除绑定V层
     */
    public void detachMvpView(){
        mMvpView = null;
    }

    /**
     * 获取V层
     * @return
     */
    public V getmMvpView() {
        return mMvpView;
    }

    /**
     * 是否与View建立连接
     * 每次调用业务请求的时候都要出先调用方法检查是否与View建立连接
     */
    public boolean isViewAttached(){
        return mMvpView != null;
    }

}
