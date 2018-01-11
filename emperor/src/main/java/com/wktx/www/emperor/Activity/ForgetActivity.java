package com.wktx.www.emperor.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.Utils.Contants;
import com.wktx.www.emperor.Model.ForgetPsw.ForgetPswInfo;
import com.wktx.www.emperor.Model.ForgetPsw.ForgetPswInfoData;
import com.wktx.www.emperor.Model.SendCode.SendCodeInfo;
import com.wktx.www.emperor.Model.SendCode.SendCodeInfoData;
import com.wktx.www.emperor.Utils.CheckPhoneUtil;
import com.wktx.www.emperor.Utils.GsonUtils;
import com.wktx.www.emperor.Utils.Md5Util;
import com.wktx.www.emperor.Utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
/**
 * 个人中心-登陆-忘记密码
 */
public class ForgetActivity extends AppCompatActivity {
    private MyHandler myHandler = new MyHandler(this);
    private String state;
    String account;
    @BindView(R.id.tb_TvBarTitle)
    TextView mTvTitle;
    @BindView(R.id.forgetEtAccount)
    EditText etAccount;
    @BindView(R.id.forgetEtYzm)
    EditText etCode;
    @BindView(R.id.forgetEtPswOne)
    EditText etPswOne;
    @BindView(R.id.forgetEtPswTwo)
    EditText etPswTwo;
    @BindView(R.id.tVSend)
    TextView tVSend;

    @OnClick({R.id.tb_IvReturn, R.id.tVSend, R.id.forgetBtnSubmit})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn:
                finish();
                break;
            case R.id.tVSend:
                account = etAccount.getText().toString();
                if (CheckPhoneUtil.isMobileNO(account)) {
                    sendMessageClick();
                    sendCode();
                } else if (account.equals("")) {
                    ToastUtil.toast(this, "请输入手机号码");
                } else {
                    ToastUtil.toast(this, "手机号码格式有误");
                }
                break;
            case R.id.forgetBtnSubmit:
                account = etAccount.getText().toString();
                String pswOne = etPswOne.getText().toString();
                String pswTwo = etPswTwo.getText().toString();
                String code = etCode.getText().toString();
                if (code.length() != 6) {
                    ToastUtil.toast(this, "请输入六位验证码");
                    break;
                }
                if (!pswOne.equals("") && !pswTwo.equals("")) {
                    if (pswOne.equals(pswTwo)) {
                        UpdatePsw(account, code, pswOne);
                    } else {
                        ToastUtil.toast(ForgetActivity.this, "两次输入密码不一致");
                        break;
                    }
                } else {
                    ToastUtil.toast(ForgetActivity.this, "密码不能为空");
                }
                break;
            default:
                break;
        }
    }

    /*
   *提交请求修改密码的方法
    */
    public void UpdatePsw(String phone, String code, String psw) {
        state = Contants.SUBMITREGIEST;
        Map<String, String> params = new HashMap<>();
        params.put("phone", phone);
        params.put("code", code);
        params.put("new_password", Md5Util.md5(psw));
        OkHttpUtils.post()
                .url(Contants.URL_USER_USER_INFO_FORGETPASSWORD)
                .params(params)
                .build()
                .execute(new MyStringCallback());
    }


    private void sendCode() {
        state = Contants.SENDCODE;
        String account = etAccount.getText().toString();
        Map<String, String> params = new HashMap<>();
        params.put("phone", account);
        OkHttpUtils.post()
                .url(Contants.URL_SENDCODE)
                .params(params)
                .build()
                .execute(new MyStringCallback());
    }

    public class MyStringCallback extends StringCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            Log.e("regiesetActivity", "null");
        }

        @Override
        public void onResponse(String response, int id) {

            switch (state) {
                case Contants.SENDCODE:
                    SendCodeInfo sendCodeInfo = GsonUtils.parseJSON(response, SendCodeInfo.class);
                    SendCodeInfoData data = sendCodeInfo.getData();
                    ToastUtil.toast(ForgetActivity.this, data.getMsg());
                    break;
                case Contants.SUBMITREGIEST:
                    ForgetPswInfo forgetPswInfo = GsonUtils.parseJSON(response, ForgetPswInfo.class);
                    ForgetPswInfoData forgetData = forgetPswInfo.getData();
                    if (forgetData.getCode() == 0) {
                        ToastUtil.toast(ForgetActivity.this, forgetData.getMsg());
                        finish();
                    } else {
                        ToastUtil.toast(ForgetActivity.this, forgetData.getMsg());
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        ButterKnife.bind(this);
        mTvTitle.setText(R.string.title_forget);
        // 设置右滑动返回
        Slidr.attach(this);
    }

    /**
     * 方式：使用Handler
     * <p>
     * 静态内部类：避免内存泄漏
     */
    private static class MyHandler extends android.os.Handler {
        private final WeakReference<ForgetActivity> weakReference;

        public MyHandler(ForgetActivity activity) {
            weakReference = new WeakReference<ForgetActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ForgetActivity activity = weakReference.get();
            TextView tvSend = activity.tVSend;
            if (activity != null) {
                switch (msg.what) {
                    case 0:
                        if (msg.arg1 == 0) {
                            tvSend.setText("重新获取");
                            tvSend.setBackgroundColor(Color.parseColor("#ffffff"));
                            tvSend.setClickable(true);
                        } else {
                            tvSend.setText(msg.arg1 + "秒");
                            tvSend.setBackgroundColor(Color.parseColor("#ffffff"));
                            tvSend.setClickable(false);
                        }
                        break;
                }
            }
        }
    }

    /**
     * 监听按钮下直接调用即可
     */
    private void sendMessageClick() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 59; i >= 0; i--) {
                    Message msg = myHandler.obtainMessage();
                    msg.arg1 = i;
                    myHandler.sendMessage(msg);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
