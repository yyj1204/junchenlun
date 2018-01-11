package com.wktx.www.subjects.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;
import com.wktx.www.subjects.Model.Login.LoginInfo;
import com.wktx.www.subjects.Model.Login.LoginInfoData;
import com.wktx.www.subjects.Model.Login.LoginInfoDataInfo;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.Utils.Contants;
import com.wktx.www.subjects.Utils.GsonUtils;
import com.wktx.www.subjects.Utils.Md5Util;
import com.wktx.www.subjects.Utils.PreventDoubleClickUtil;
import com.wktx.www.subjects.Utils.SaveObjectUtils;
import com.wktx.www.subjects.Utils.SharedPreferenceUtil;
import com.wktx.www.subjects.Utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import java.util.HashMap;
import java.util.Map;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 个人中心-登陆
 */
public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.tb_TvBarTitle)
    TextView mTvTitle;
    @BindView(R.id.loginBtnSubmit)
    Button btnLogin;
    @BindView(R.id.loginEtAccount)
    EditText etAccount;
    @BindView(R.id.loginEtPsw)
    EditText etPsw;
    private LoginInfo loginInfo;
    private String state;
    private String pageName;
    Activity mContext = LoginActivity.this;

    @OnClick({R.id.tb_IvReturn, R.id.tv_forget, R.id.loginBtnSubmit, R.id.lL_regiest})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.lL_regiest:
                startActivity(new Intent(this, RegiestActivity.class));
                break;
            case R.id.tv_forget:
                startActivity(new Intent(this, ForgetActivity.class));
                break;
            case R.id.tb_IvReturn:
                finish();
                break;
            case R.id.loginBtnSubmit:
                if (PreventDoubleClickUtil.noDoubleClick()) {
                    String account = etAccount.getText().toString();
                    String psw = etPsw.getText().toString();
                    loginSubmit(account, psw);
                }
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
        mTvTitle.setText(R.string.title_login);
        // 设置右滑动返回
        Slidr.attach(this);
        Intent intent = getIntent();
        if (intent != null) {
            pageName = intent.getStringExtra(Contants.PAGE);
        }
    }


    //提交登录信息到后台验证
    public void loginSubmit(String phone, String psw) {
        state = Contants.STATELOGINIPHONE;
        Map<String, String> params = new HashMap<>();
        params.put("phone", phone);
        params.put("password", Md5Util.md5(psw));
        params.put("ph_expires_in", "10");
        params.put("type", "1");
        OkHttpUtils.post()//
                .url(Contants.URL_SUBMIT_LOGIN)//
                .params(params)//
                .build()//
                .execute(new MyStringCallback());
    }

    public class MyStringCallback extends StringCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            Log.e("HomeDetailFragment", "null");
        }

        @Override
        public void onResponse(String response, int id) {
            if (response != null) {
                switch (state) {
                    case Contants.STATELOGINIPHONE:
                        loginInfo = GsonUtils.parseJSON(response, LoginInfo.class);
                        LoginInfoData data = loginInfo.getData();
                        int code = data.getCode();
                        LoginInfoDataInfo info = data.getInfo();
                        if (code == 0) {
                            //把登录信息对象写入内存
                            new SaveObjectUtils(LoginActivity.this, Contants.LOGININFO).setObject(Contants.LOGININFO_OBJECT, info);
                            //登录成功关闭页面返回页面跳转结果给登录显示界面
                            Intent intent = new Intent();
                            if (pageName != null && pageName.equals(Contants.PAGE_GOODSDETAIL)) {
                                setResult(Contants.RESULT_GOODSDETAIL);
                            } else {
                                intent.putExtra(Contants.LOGIN_RESULTCODE, info);
                                setResult(Contants.RESULT_LOGINCODE, intent);
                            }
                            ToastUtil.toast(LoginActivity.this, "登录成功");
                            SharedPreferenceUtil.saveData(LoginActivity.this, Contants.ISLOGIN, true, Contants.SP_ISLOGIN);
                            finish();
                        } else {
                            ToastUtil.toast(LoginActivity.this, data.getMsg());
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }
}