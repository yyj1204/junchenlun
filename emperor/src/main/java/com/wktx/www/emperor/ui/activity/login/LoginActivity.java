package com.wktx.www.emperor.ui.activity.login;

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
import com.wktx.www.emperor.apiresult.login.login8register.RegisterInfoData;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.login.LoginPresenter;
import com.wktx.www.emperor.ui.activity.MainActivity;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LoginUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.ui.view.login.ILoginView;
import com.wktx.www.emperor.utils.ToastUtil;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 个人中心---登录界面
 */
public class LoginActivity extends ABaseActivity<ILoginView,LoginPresenter> implements ILoginView {

    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.bt_login)
    Button btLogin;

    private AccountInfoData userInfo;
    private boolean isTokenExpire;//登录token是否过期

    @OnClick({R.id.tb_IvReturn, R.id.tv_forget_pwd, R.id.bt_login, R.id.tv_regiest})
    public void MyOnclick(View view) {
        //将输入法隐藏
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tvTitle.getWindowToken(), 0);
        switch (view.getId()) {
            case R.id.tb_IvReturn:
                //如果是token过期，回到首页
                if (isTokenExpire){
                    Intent intent = new Intent(this, MainActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                finish();
                break;
            case R.id.tv_forget_pwd://忘记密码
                if (MyUtils.isFastClick1()){
                    return;
                }
                startActivity(new Intent(this, ForgetPwdActivity.class));
                break;
            case R.id.bt_login://登录
                if (MyUtils.isFastClick()){
                    return;
                }
                //判断输入框格式
                if (TextUtils.isEmpty(getPhoneStr())){
                    ToastUtil.myToast("请输入手机号！");
                    etPhone.requestFocus();
                }else if (!MyUtils.checkMobileNumber(getPhoneStr())) {
                    ToastUtil.myToast( "手机号输入不正确！");
                    etPhone.requestFocus();
                }else if (TextUtils.isEmpty(getPwdStr())){
                    ToastUtil.myToast("请输入密码！");
                    etPwd.requestFocus();
                }else if (getPwdStr().length()<6||getPwdStr().length()>20){
                    ToastUtil.myToast("密码输入不正确！");
                    etPwd.requestFocus();
                }else {//登录
                    btLogin.setEnabled(false);
                    getPresenter().onLogin();
                }
                break;
            case R.id.tv_regiest://没有账号，立即注册
                if (MyUtils.isFastClick1()){
                    return;
                }
                startActivity(new Intent(this, RegisterActivity.class));
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        tvTitle.setText(R.string.title_login);
        //设置右滑动返回
        Slidr.attach(this);
        isTokenExpire = getIntent().getBooleanExtra(ConstantUtil.KEY_ISOK, false);
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    /**
     * ILoginView
     */
    @Override
    public String getPhoneStr() {
        return etPhone.getText().toString().trim();
    }
    @Override
    public String getPwdStr() {
        return etPwd.getText().toString().trim();
    }
    @Override
    public void onRequestSuccess(RegisterInfoData tData) {
        //保存登录信息
        if (userInfo==null){
            userInfo = new AccountInfoData();
        }
        userInfo.setUser_id(tData.getUser_id());
        userInfo.setToken(tData.getToken());
        userInfo.setPhone(getPhoneStr());
        userInfo.setIs_new(tData.getIs_new());
        LoginUtil.getinit().saveUserInfo(userInfo);

        ToastUtil.myToast("登录成功！");
        finish();
    }
    @Override
    public void onRequestFailure(String result) {
        btLogin.setEnabled(true);
        ToastUtil.myToast(result);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtil.cancleMyToast();
    }
}