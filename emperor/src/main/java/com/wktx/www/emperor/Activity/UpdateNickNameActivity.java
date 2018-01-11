package com.wktx.www.emperor.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.Model.Login.LoginInfoDataInfo;
import com.wktx.www.emperor.Model.UpdateInfo.UpdateNickNameInfo;
import com.wktx.www.emperor.Model.UpdateInfo.UpdateNickNameInfoData;
import com.wktx.www.emperor.Model.UserInfo.UserInfoDataInfoUserinfo;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.Utils.Contants;
import com.wktx.www.emperor.Utils.GsonUtils;
import com.wktx.www.emperor.Utils.SaveObjectUtils;
import com.wktx.www.emperor.Utils.ToastUtil;
import com.wktx.www.emperor.Utils.getSaveInfoUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class UpdateNickNameActivity extends AppCompatActivity {

    @BindView(R.id.updataNickNameEtNickName)
    EditText etNickName;
    private boolean isLogin;
    private String token;
    private int user_id;
    private UserInfoDataInfoUserinfo info;

    @OnClick({R.id.tb_IvReturn, R.id.updataNickNameTVSure})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn:
                finish();
                break;
            //修改昵称确认按钮
            case R.id.updataNickNameTVSure:
                String nickName = etNickName.getText().toString();
                if (!nickName.equals("")) {
                    if (user_id != 0 && token != null) {
                        updateNickName(user_id, token, nickName);
                    }
                } else {
                    ToastUtil.toast(this, "请输入修改的昵称");
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_nick_name);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        initSaveLoacl();
        if (token != null) {
            info = new SaveObjectUtils(this, Contants.USERINFO_NAME).getObject(token, UserInfoDataInfoUserinfo.class);
        }
    }

    private void initSaveLoacl() {
        isLogin = getSaveInfoUtil.getIsLogin(this);
        if (isLogin) {
            LoginInfoDataInfo saveObject = getSaveInfoUtil.getSaveObject(this);
            if (saveObject != null) {
                token = getSaveInfoUtil.getToken(this);
                user_id = getSaveInfoUtil.getUser_id(this);
            }
        }
    }

    public class MyStringCallback extends StringCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            Log.e("HomeDetailFragment", "null");
        }

        @Override
        public void onResponse(String response, int id) {
            UpdateNickNameInfo updateNickNameInfo = GsonUtils.parseJSON(response, UpdateNickNameInfo.class);
            UpdateNickNameInfoData data = updateNickNameInfo.getData();
            String msg = data.getMsg();
            if (!msg.equals("")) {
                ToastUtil.toast(UpdateNickNameActivity.this, msg);
                if (msg.equals("修改成功")) {
                    String s = etNickName.getText().toString();
                    Intent intent = new Intent();
                    intent.putExtra(Contants.NICKTEXT, s);
                    info.setNickname(s);
                    new SaveObjectUtils(UpdateNickNameActivity.this, Contants.USERINFO_NAME).setObject(token, info);
                    setResult(Contants.RESULTCODE_UPDATENICK, intent);
                    finish();
                }
            }
        }
    }

    //提交修改昵称请求
    private void updateNickName(int user_id, String token, String nickName) {
        Map<String, String> params = new HashMap<>();
        params.put("user_id", "" + user_id);
        params.put("token", token);
        params.put("nickname", nickName);
        OkHttpUtils.post()//
                .url(Contants.URL_EDITUSERINFO)//
                .params(params)//
                .build()//
                .execute(new MyStringCallback());
    }

}
