package com.wktx.www.emperor.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.Utils.Contants;
import com.wktx.www.emperor.Model.Regiest.RegiestInfo;
import com.wktx.www.emperor.Model.Regiest.RegiestInfoData;
import com.wktx.www.emperor.Model.SendCode.SendCodeInfo;
import com.wktx.www.emperor.Utils.CheckPhoneUtil;
import com.wktx.www.emperor.Utils.GsonUtils;
import com.wktx.www.emperor.Utils.Md5Util;
import com.wktx.www.emperor.Utils.PreventDoubleClickUtil;
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

public class RegiestActivity extends AppCompatActivity {
    MyHandler myHandler = new MyHandler(this);
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.tVSend)
    TextView tVSend;
    @BindView(R.id.regiestEtAccount)
    EditText etAccount;
    @BindView(R.id.regiestEtPswOne)
    EditText etPswOne;
    @BindView(R.id.regiestEtPswTwo)
    EditText etPswTwo;
    @BindView(R.id.regiestEtYzm)
    EditText etPswYzm;
    String state;
    String account;

    @OnClick({R.id.tb_IvReturn, R.id.tVSend, R.id.regiestBtnSubmit})
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
            case R.id.regiestBtnSubmit:
                if (PreventDoubleClickUtil.noDoubleClick()) {
                    account = etAccount.getText().toString();
                    String pswYzm = etPswYzm.getText().toString();
                    if (pswYzm.length() != 6) {
                        ToastUtil.toast(this, "请输入六位验证码");
                        break;
                    } else {
                        checkYzm(account, pswYzm);
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regiest);
        ButterKnife.bind(this);
        tvTitle.setText(R.string.title_regiest);
        // 设置右滑动返回
        Slidr.attach(this);
    }

    /*
    *提交请求注册的方法
     */
    public void regiestSubmit(String phone, String psw) {
        state = Contants.SUBMITREGIEST;
        Map<String, String> params = new HashMap<>();
        params.put("phone", phone);
        params.put("password", Md5Util.md5(psw));
        params.put("ph_expires_in", "5");
        params.put("type", "0");
        OkHttpUtils.post()
                .url(Contants.URL_SUBMIT_REGIEST)
                .params(params)
                .build()
                .execute(new MyStringCallback());
    }

    /*
    *提交请求获取验证码的方法
    */
    void sendCode() {
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


    /*
    *提交请求检测验证码的方法
     */
    public void checkYzm(String phone, String code) {
        state = Contants.CHECKCODE;
        Map<String, String> params = new HashMap<>();
        params.put("phone", phone);
        params.put("code", code);
        OkHttpUtils.post()
                .url(Contants.URL_CHECKCODE)
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
                    String msg = sendCodeInfo.getData().getMsg();
                    ToastUtil.toast(RegiestActivity.this, msg);
                    break;
                case Contants.CHECKCODE:
                    String account = etAccount.getText().toString();
                    String pswOne = etPswOne.getText().toString();
                    String pswTwo = etPswTwo.getText().toString();
                    if (pswOne.equals(pswTwo)) {
                        if (!pswOne.equals("") && !pswTwo.equals("")) {
                            if (pswOne.equals(pswTwo)) {
                                regiestSubmit(account, pswOne);
                            } else {
                                ToastUtil.toast(RegiestActivity.this, "两次输入密码不一致");
                                break;
                            }
                        } else {
                            ToastUtil.toast(RegiestActivity.this, "密码不能为空");
                        }

                    } else {
                        ToastUtil.toast(RegiestActivity.this, "两次输入密码不同，请重新输入");
                    }
                    break;
                case Contants.SUBMITREGIEST:
                    String remarks;
                    if (response != null) {
                        RegiestInfo regiestInfo = GsonUtils.parseJSON(response, RegiestInfo.class);
                        RegiestInfoData data = regiestInfo.getData();
                        int code = data.getCode();
                        if (code == 0) {
                            remarks = "注册成功";
                        } else {
                            remarks = data.getMsg();
                            Log.e("regiestInfo", new Gson().toJson(regiestInfo));
                        }
                        Toast.makeText(RegiestActivity.this, remarks, Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 方式：使用Handler
     * <p>
     * 静态内部类：避免内存泄漏
     */
    static class MyHandler extends android.os.Handler {

        final WeakReference<RegiestActivity> weakReference;

        public MyHandler(RegiestActivity activity) {
            weakReference = new WeakReference<RegiestActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            RegiestActivity activity = weakReference.get();
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
    void sendMessageClick() {
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
