package com.wktx.www.subjects.ui.activity.mine.security;

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
import com.wktx.www.subjects.basemvp.ABaseActivity;
import com.wktx.www.subjects.presenter.mine.security.EditLoginPhonePresenter;
import com.wktx.www.subjects.ui.activity.MainActivity;
import com.wktx.www.subjects.ui.view.login.IForgetPwdView;
import com.wktx.www.subjects.utils.LoginUtil;
import com.wktx.www.subjects.utils.MyUtils;
import com.wktx.www.subjects.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 账户安全---修改手机号码
 */
public class EditPhoneActivity extends ABaseActivity<IForgetPwdView,EditLoginPhonePresenter> implements IForgetPwdView {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.tv_getcode)
    TextView tvGetCode;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.bt_sure)
    Button btSure;//修改手机号

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

    @OnClick({R.id.tb_IvReturn, R.id.tv_getcode,R.id.bt_sure})
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
            case R.id.bt_sure://修改手机号
                //判断输入框格式
                if (TextUtils.isEmpty(getPhoneStr())){
                    ToastUtil.myToast("请输入新手机号！");
                    etPhone.requestFocus();
                }else if (!MyUtils.checkMobileNumber(getPhoneStr())) {
                    ToastUtil.myToast( "手机号输入不正确！");
                    etPhone.requestFocus();
                }else if (TextUtils.isEmpty(getCodeStr())){
                    ToastUtil.myToast("请输入验证码！");
                    etCode.requestFocus();
                }else if (TextUtils.isEmpty(getPwd1Str())){
                    ToastUtil.myToast("请输入登录密码！");
                    etPwd.requestFocus();
                }else if (getPwd1Str().length()<6||getPwd1Str().length()>20){
                    ToastUtil.myToast("登录密码输入不正确！");
                    etPwd.requestFocus();
                }else {//修改手机号
                    btSure.setEnabled(false);
                    getPresenter().onEditPhone();
                }
                break;
            default:
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_phone);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_edit_loginphone);
    }

    @Override
    protected EditLoginPhonePresenter createPresenter() {
        return new EditLoginPhonePresenter();
    }


    /**
     * IForgetPwdView
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
        return etPwd.getText().toString().trim();
    }
    @Override
    public String getPwd2Str() {
        return null;
    }
    @Override
    public void onSendCodeResult(boolean isSuccess, String msg) {
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
    @Override
    public void onRequestSuccess(String tData) {
        ToastUtil.myToast("手机号已修改！请重新登录~");
        LoginUtil.getinit().logout();
        //回到首页
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRequestFailure(String result) {
        btSure.setEnabled(true);
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
