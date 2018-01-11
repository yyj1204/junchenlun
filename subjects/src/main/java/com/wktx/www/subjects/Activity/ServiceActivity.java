package com.wktx.www.subjects.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.subjects.Model.Service.ServiceInfo;
import com.wktx.www.subjects.Model.Service.ServiceInfoData;
import com.wktx.www.subjects.Model.Service.ServiceInfoDataInfo;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.Utils.Contants;
import com.wktx.www.subjects.Utils.GsonUtils;
import com.wktx.www.subjects.Utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class ServiceActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RC_CALL_PHONE = 1001;
    private TextView tvPhone;
    private TextView tvQQ;
    private String QQ;
    private String Phone;
    private ServiceInfoDataInfo info;
    private String service_time;
    private TextView tvTimeOne;
    private TextView tvTimeTwo;


    //提交登录信息到后台验证
    public void getServiceInfo() {
        Map<String, String> params = new HashMap<>();
        OkHttpUtils.post()//
                .url(Contants.URL_CENTER_GETCUSTOMERSERVICEINFO)//
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
                ServiceInfo serviceInfo = GsonUtils.parseJSON(response, ServiceInfo.class);
                ServiceInfoData data = serviceInfo.getData();
                if (data.getCode() == 0) {
                    info = data.getInfo();
                    if (info != null) {
                        Phone = info.getPhone();
                        QQ = info.getQq();
                        service_time = info.getService_time();
                        tvPhone.setText("Mobile:" + Phone);
                        tvQQ.setText("QQ:" + QQ);
                        tvTimeOne.setText("服务时间：" + service_time);
                        tvTimeTwo.setText("服务时间：" + service_time);
                    }
                }
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        // 设置右滑动返回
        Slidr.attach(this);
        initUI();
        initData();
    }

    private void initData() {
        getServiceInfo();
    }

    private void initUI() {
        findViewById(R.id.tb_IvReturn).setOnClickListener(this);
        findViewById(R.id.tuiHuanHuoLlPhone).setOnClickListener(this);
        findViewById(R.id.tuiHuanHuoLlQQ).setOnClickListener(this);
        tvPhone = (TextView) findViewById(R.id.returnTvPhone);
        tvQQ = (TextView) findViewById(R.id.returnTvQQ);
        tvTimeOne = (TextView) findViewById(R.id.returnTvTime);
        tvTimeTwo = (TextView) findViewById(R.id.returnTvTime);
    }

    /**
     * 判断qq是否可用
     *
     * @param context
     * @return
     */
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(RC_CALL_PHONE)
    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.CALL_PHONE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + Phone));
            //开启系统拨号器
            startActivity(intent);
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, Manifest.permission.CALL_PHONE,
                    RC_CALL_PHONE, perms);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn:
                finish();
                break;
            case R.id.tuiHuanHuoLlPhone:
                methodRequiresTwoPermission();
                break;
            case R.id.tuiHuanHuoLlQQ:
                if (isQQClientAvailable(this)) {
                    String url = "mqqwpa://im/chat?chat_type=wpa&uin=" + QQ;
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                } else {
                    ToastUtil.toast(this, "请安装QQ之后再重试");
                }
                break;
            default:
                break;
        }
    }
}
