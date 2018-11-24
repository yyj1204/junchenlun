package com.wktx.www.emperor.ui.activity.mine.security;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.mine.security.EditPayPwdPresenter;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.ui.view.login.IForgetPwdView;
import com.wktx.www.emperor.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 账户安全---修改支付密码
 */
public class EditPayPwdActivity extends ABaseActivity<IForgetPwdView,EditPayPwdPresenter> implements IForgetPwdView {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.tv_getcode)
    TextView tvGetCode;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.et_pwd1)
    EditText etPwd1;
    @BindView(R.id.et_pwd2)
    EditText etPwd2;
    @BindView(R.id.bt_sure)//修改支付密码
            Button btSure;

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

    @OnClick({R.id.tb_IvReturn, R.id.tv_getcode, R.id.bt_sure})
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
                //获取验证码
                getPresenter().onGetCode();
                break;
            case R.id.bt_sure://修改支付密码
                //判断输入框格式
                if (TextUtils.isEmpty(getCodeStr())){
                    ToastUtil.myToast("请输入验证码！");
                    etCode.requestFocus();
                }else if (TextUtils.isEmpty(getPwd1Str())){
                    ToastUtil.myToast("请输入支付密码！");
                    etPwd1.requestFocus();
                }else if (getPwd1Str().length()!=6){
                    ToastUtil.myToast("支付密码输入不正确！");
                    etPwd1.requestFocus();
                }else if (TextUtils.isEmpty(getPwd2Str())){
                    ToastUtil.myToast("请输入确认密码！");
                    etPwd2.requestFocus();
                }else if (getPwd2Str().length()!=6||!getPwd1Str().equals(getPwd2Str())){
                    ToastUtil.myToast("确认密码输入不正确！");
                    etPwd2.requestFocus();
                }else {//修改支付密码
                    btSure.setEnabled(false);
                    getPresenter().onSetPayPassword();
                }
                break;
            default:
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_paypwd);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_edit_paypwd);
        tvPhone.setText(MyUtils.setPhone(getUserInfo().getPhone()));
    }

    @Override
    protected EditPayPwdPresenter createPresenter() {
        return new EditPayPwdPresenter();
    }


    /**
     * IForgetPwdView
     */
    @Override
    public String getPhoneStr() {
        return getUserInfo().getPhone();
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
    @Override
    public void onRequestSuccess(String tData) {
        ToastUtil.myToast("支付密码已修改！");
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
