package com.wktx.www.subjects.basemvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.jaeger.library.StatusBarUtil;
import com.wktx.www.subjects.R;


/**
 * Created by yyj on 2018/1/13.
 * 指定子类具体的View必须继承自 IBaseView
 * 指定子类具体的Presenter必须继承自 ABasePresenter
 */

public abstract class ABaseFragment<V extends IBaseView,P extends ABasePresenter<V>> extends Fragment implements IBaseView{

    private P presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //沉浸式状态栏
        StatusBarUtil.setColor(getActivity(), getResources().getColor(R.color.color_ffb321),0);
        //初始化MVP
        initMVP();
    }

    /**
     * MVP绑定
     */
    public void initMVP(){
        //创建Presenter
        if (presenter == null) {
            presenter = createPresenter();
        }
        if (presenter == null) {
            throw new NullPointerException("presenter 不能为空!");
        }
        //绑定view
        presenter.attachMvpView((V) this,getContext());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //解除绑定
        if (presenter != null) {
            presenter.detachMvpView();
        }
    }

    /**
     * 创建Presenter
     * @return 子类自己需要的Presenter
     */
    protected abstract P createPresenter();

    /**
     * 获取Presenter
     * @return 返回子类创建的Presenter
     */
    public P getPresenter() {
        return presenter;
    }

}
