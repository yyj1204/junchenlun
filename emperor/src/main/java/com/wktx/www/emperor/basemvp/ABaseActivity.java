package com.wktx.www.emperor.basemvp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jaeger.library.StatusBarUtil;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.login.AccountInfoData;
import com.wktx.www.emperor.ui.activity.login.LoginActivity;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LoginUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.zhy.autolayout.AutoLayoutActivity;

/**
 * Created by yyj on 2018/1/13.
 * 指定子类具体的View必须继承自 IBaseView
 * 指定子类具体的Presenter必须继承自 ABasePresenter
 */

public abstract class ABaseActivity<V extends IBaseView,P extends ABasePresenter<V>>
        extends AutoLayoutActivity implements IBaseView{

    private P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //沉浸式状态栏
        StatusBarUtil.setColor(ABaseActivity.this, getResources().getColor(R.color.color_ffb321),0);
        //强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //初始化MVP
        initMVP();
    }

    /**
     * MVP绑定
     */
    private void initMVP() {
        //创建Presenter
        if (presenter == null) {
            presenter = createPresenter();
        }
        if (presenter == null) {
            throw new NullPointerException("presenter 不能为空!");
        }
        //绑定view
        presenter.attachMvpView((V) this,this);
    }

    @Override
    protected void onDestroy() {
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



    @Override
    public AccountInfoData getUserInfo() {
        AccountInfoData userInfo = LoginUtil.getinit().getUserInfo();
        return userInfo;
    }
    @Override
    public void onLoginFailure(String msg) {
        MyUtils.showToast(this,msg);
        if (getUserInfo()!=null){
            LoginUtil.getinit().logout();//将本地登录信息清除
        }
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(ConstantUtil.KEY_ISOK,true);
        startActivity(intent);
    }
}
