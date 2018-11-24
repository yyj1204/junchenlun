package com.wktx.www.subjects.ui.activity.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.login.login8register.RegisterInfoData;
import com.wktx.www.subjects.basemvp.ABaseActivity;
import com.wktx.www.subjects.presenter.login.RegisterPresenter;
import com.wktx.www.subjects.ui.view.login.IRegisterView;
import com.wktx.www.subjects.utils.MyUtils;
import com.wktx.www.subjects.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人中心---注册界面
 */
public class RegisterActivity extends ABaseActivity<IRegisterView,RegisterPresenter> implements IRegisterView{

    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.tv_getcode)
    TextView tvGetCode;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.et_pwd1)
    EditText etPwd1;
    @BindView(R.id.et_pwd2)
    EditText etPwd2;
    @BindView(R.id.bt_register)//确认注册
    Button btRegister;

    private int time = 60;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                tvGetCode.setText(time+"秒后重新获取");
            } else if (msg.what == 1) {
                tvGetCode.setText("重新获取验证码");
                tvGetCode.setEnabled(true);
                time = 60;
            }
        }
    };

    @OnClick({R.id.tb_IvReturn, R.id.tv_getcode, R.id.bt_register,R.id.tv_login})
    public void MyOnclick(View view) {
        //将输入法隐藏
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tvTitle.getWindowToken(), 0);
        if (MyUtils.isFastClick()){
            return;
        }
        switch (view.getId()) {
            case R.id.tb_IvReturn:
                finish();
                break;
            case R.id.tv_getcode://获取验证码
                if (TextUtils.isEmpty(getPhoneStr())){
                     ToastUtil.myToast("请输入手机号！");
                    etPhone.requestFocus();
                }else if (!MyUtils.checkMobileNumber(getPhoneStr())){
                     ToastUtil.myToast("手机号输入不正确！");
                    etPhone.requestFocus();
                }else {//获取验证码
                    getPresenter().onGetCode();
                }
                break;
            case R.id.bt_register://确认注册
                //判断输入框格式
                if (TextUtils.isEmpty(getPhoneStr())){
                     ToastUtil.myToast("请输入手机号！");
                    etPhone.requestFocus();
                }else if (!MyUtils.checkMobileNumber(getPhoneStr())) {
                     ToastUtil.myToast( "手机号输入不正确！");
                    etPhone.requestFocus();
                }else if (TextUtils.isEmpty(getCodeStr())){
                     ToastUtil.myToast("请输入验证码！");
                    etCode.requestFocus();
                }else if (TextUtils.isEmpty(getPwd1Str())){
                     ToastUtil.myToast("请输入密码！");
                    etPwd1.requestFocus();
                }else if (getPwd1Str().length()<6||getPwd1Str().length()>20){
                   ToastUtil.myToast("密码输入不正确！");
                    etPwd1.requestFocus();
                }else if (TextUtils.isEmpty(getPwd2Str())){
                     ToastUtil.myToast("请输入确认密码！");
                    etPwd2.requestFocus();
                }else if (getPwd2Str().length()<6||getPwd2Str().length()>20||!getPwd1Str().equals(getPwd2Str())){
                   ToastUtil.myToast("确认密码输入不正确！");
                    etPwd2.requestFocus();
                }else {//注册
                    btRegister.setEnabled(false);
                    getPresenter().onRegister();
                }
                break;
            case R.id.tv_login://已有账号，立即登录
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        tvTitle.setText(R.string.title_register);
        //设置右滑动返回
        Slidr.attach(this);
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }

    /**
     * IRegisterView
     */
    @Override
    public String getPhoneStr() {
        return etPhone.getText().toString().trim();
    }
    @Override
    public String getCodeStr() {
        return etCode.getText().toString().trim();
    }
    @Override
    public String getPwd1Str() {
        return etPwd1.getText().toString().trim();
    }
    @Override
    public String getPwd2Str() {
        return etPwd2.getText().toString().trim();
    }
    @Override
    public void onSendCodeResult(boolean isSuccess,String msg) {
         ToastUtil.myToast( msg);
        if (isSuccess){
            tvGetCode.setEnabled(false);
            tvGetCode.setText(time+"秒后重新获取");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (; time > 0; time--) {
                        handler.sendEmptyMessage(0);
                        if (time <= 0) {
                            break;
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    handler.sendEmptyMessage(1);
                }
            }).start();
        }
    }
    @Override//注册成功，登录
    public void onRequestSuccess(RegisterInfoData tData) {
        ToastUtil.myToast("注册成功！可以登录啦~");
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
    @Override
    public void onRequestFailure(String result) {
        btRegister.setEnabled(true);
         ToastUtil.myToast(result);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //终止消息队列，防止内存泄漏
        if (handler!=null){
            handler.removeCallbacksAndMessages(null);
        }
        ToastUtil.cancleMyToast();
    }
}
