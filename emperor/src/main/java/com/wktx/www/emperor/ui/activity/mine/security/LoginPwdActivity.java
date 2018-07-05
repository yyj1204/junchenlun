package com.wktx.www.emperor.ui.activity.mine.security;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.login.AccountInfoData;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.mine.security.LoginPwdPresenter;
import com.wktx.www.emperor.ui.activity.main.MainActivity;
import com.wktx.www.emperor.utils.LoginUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.ui.view.login.IForgetPwdView;
import com.wktx.www.emperor.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置---修改密码
 */
public class LoginPwdActivity extends ABaseActivity<IForgetPwdView,LoginPwdPresenter> implements IForgetPwdView {

    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.et_pwdOld)
    EditText etPwdOld;
    @BindView(R.id.et_pwdNew1)
    EditText etPwdNew1;
    @BindView(R.id.et_pwdNew2)
    EditText etPwdNew2;
    @BindView(R.id.bt_editPwd)
    Button btEditPwd;//修改密码


    @OnClick({R.id.tb_IvReturn, R.id.bt_editPwd})
    public void MyOnclick(View view) {
        //将输入法隐藏
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tvTitle.getWindowToken(), 0);

        switch (view.getId()) {
            case R.id.tb_IvReturn:
                finish();
                break;
            case R.id.bt_editPwd://修改密码
                if (MyUtils.isFastClick()){
                    return;
                }
                //判断输入框格式
                if (TextUtils.isEmpty(getPhoneStr())){
                    ToastUtil.myToast("请输入旧密码！");
                    etPwdOld.requestFocus();
                }else if (getPhoneStr().length()<6||getPhoneStr().length()>12){
                    ToastUtil.myToast("旧密码输入不正确！");
                    etPwdOld.requestFocus();
                }else if (TextUtils.isEmpty(getPwd1Str())){
                    ToastUtil.myToast("请输入新密码！");
                    etPwdNew1.requestFocus();
                }else if (getPwd1Str().length()<6||getPwd1Str().length()>12){
                    ToastUtil.myToast("新密码输入不正确！");
                    etPwdNew1.requestFocus();
                }else if (TextUtils.isEmpty(getPwd2Str())){
                    ToastUtil.myToast("请输入确认密码！");
                    etPwdNew2.requestFocus();
                }else if (getPwd2Str().length()<6||getPwd2Str().length()>12||!getPwd1Str().equals(getPwd2Str())){
                    ToastUtil.myToast("确认密码输入不正确！");
                    etPwdNew2.requestFocus();
                }else {//注册
                    btEditPwd.setEnabled(false);
                    getPresenter().onEditPwd();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pwd);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_pwd_edit);
    }



    @Override
    protected LoginPwdPresenter createPresenter() {
        return new LoginPwdPresenter();
    }

    /**
     * IForgetPwdView
     */
    @Override
    public AccountInfoData getUserInfo() {
        AccountInfoData userInfo = LoginUtil.getinit().getUserInfo();
        return userInfo;
    }
    @Override//旧密码
    public String getPhoneStr() {
        return etPwdOld.getText().toString().trim();
    }
    @Override
    public String getCodeStr() {
        return null;
    }
    @Override//新密码
    public String getPwd1Str() {
        return etPwdNew1.getText().toString().trim();
    }
    @Override//确认密码
    public String getPwd2Str() {
        return etPwdNew2.getText().toString().trim();
    }
    @Override
    public void onSendCodeResult(boolean isSuccess, String msg) {
    }
    @Override
    public void onRequestSuccess(String tData) {
        LoginUtil.getinit().logout();
        ToastUtil.myToast("登录密码已修改！请重新登录~");
        //回到首页
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onRequestFailure(String result) {
        btEditPwd.setEnabled(true);
        ToastUtil.myToast(result);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtil.cancleMyToast();
    }
}
