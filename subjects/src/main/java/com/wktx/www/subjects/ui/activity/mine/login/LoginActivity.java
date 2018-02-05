package com.wktx.www.subjects.ui.activity.mine.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.login.AccountInfoData;
import com.wktx.www.subjects.apiresult.login.login8register.RegisterData;
import com.wktx.www.subjects.basemvp.ABaseActivity;
import com.wktx.www.subjects.presenter.login.LoginPresenter;
import com.wktx.www.subjects.utils.LoginUtil;
import com.wktx.www.subjects.utils.MyUtils;
import com.wktx.www.subjects.view.login.ILoginView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 个人中心-登录界面
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


    @OnClick({R.id.tb_IvReturn, R.id.tv_forget_pwd, R.id.bt_login, R.id.tv_regiest})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn:
                finish();
                break;
            case R.id.tv_forget_pwd://忘记密码
                startActivity(new Intent(this, ForgetPwdActivity.class));
                break;
            case R.id.bt_login://登录
                if (MyUtils.isFastClick()){
                    return;
                }
                //判断输入框格式
                if (TextUtils.isEmpty(getPhoneStr())){
                    MyUtils.showToast(LoginActivity.this,"请输入手机号！");
                    etPhone.requestFocus();
                }else if (!MyUtils.checkMobileNumber(getPhoneStr())) {
                    MyUtils.showToast(LoginActivity.this, "手机号输入不正确！");
                    etPhone.requestFocus();
                }else if (TextUtils.isEmpty(getPwdStr())){
                    MyUtils.showToast(LoginActivity.this,"请输入密码！");
                    etPwd.requestFocus();
                }else if (getPwdStr().length()<6||getPwdStr().length()>12){
                    MyUtils.showToast(this,"密码输入不正确！");
                    etPwd.requestFocus();
                }else {//注册
                    btLogin.setEnabled(false);
                    getPresenter().onLogin();
                }
                break;
            case R.id.tv_regiest://没有账号，立即注册
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
        // 设置右滑动返回
        Slidr.attach(this);
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(LoginActivity.this);
    }

    /**
     * ILoginView
     */
    @Override
    public AccountInfoData getUserInfo() {
        return null;
    }
    @Override
    public String getPhoneStr() {
        return etPhone.getText().toString().trim();
    }
    @Override
    public String getPwdStr() {
        return etPwd.getText().toString().trim();
    }
    @Override
    public void onRequestSuccess(RegisterData tData) {
        //保存登录信息
        if (userInfo==null){
            userInfo = new AccountInfoData();
        }
        userInfo.setUser_id(tData.getInfo().getUser_id());
        userInfo.setToken(tData.getInfo().getToken());
        userInfo.setIs_new(tData.getInfo().getIs_new());
        LoginUtil.getinit().saveUserInfo(userInfo);

        MyUtils.showToast(LoginActivity.this,"登录成功！");
        finish();
    }
    @Override
    public void onRequestFailure(String result) {
        btLogin.setEnabled(true);
        MyUtils.showToast(LoginActivity.this,result);
    }
}