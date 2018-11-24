package com.wktx.www.subjects.ui.activity.mine.security;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.mine.center.CenterInfoData;
import com.wktx.www.subjects.apiresult.mine.center.UserInfoBean;
import com.wktx.www.subjects.ui.activity.mine.authent.AccountAuthentActivity;
import com.wktx.www.subjects.ui.activity.mine.authent.AuthentPersonalActivity;
import com.wktx.www.subjects.ui.activity.mine.authent.AuthentPersonalDetailsActivity;
import com.wktx.www.subjects.ui.activity.mine.authent.AuthentStudioDetailsActivity;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.MyUtils;
import com.wktx.www.subjects.utils.ToastUtil;
import com.wktx.www.subjects.widget.CustomDialog;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人中心 --- 账户安全
 */
public class AccountSecurityActivity extends AutoLayoutActivity {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.tv_alipayState)
    TextView tvAlipayState;

    private CenterInfoData centerInfoData;//个人中心信息

    private CustomDialog customDialog;

    @OnClick({R.id.tb_IvReturn,R.id.linear_loginPwd,R.id.linear_payPwd,R.id.linear_loginPhone,R.id.linear_alipay})
    public void MyOnclick(View view) {
        if (MyUtils.isFastClick1()){
            return;
        }
        switch (view.getId()) {
            case R.id.tb_IvReturn://返回
                finish();
                break;
            case R.id.linear_loginPwd://修改登录密码
                startActivity(new Intent(this,EditLoginPwdActivity.class));
                break;
            case R.id.linear_payPwd://修改支付密码
                startActivity(new Intent(this,EditPayPwdActivity.class));
                break;
            case R.id.linear_loginPhone://修改手机号码
                isAuthentStartActivity(true);
                break;
            case R.id.linear_alipay://支付宝账户
                isAuthentStartActivity(false);
                break;
            default:
                break;
        }
    }

    /**
     * 判断账户认证状态，再打开对应界面
     */
    private void isAuthentStartActivity(boolean isEditPhone) {
        switch (centerInfoData.getAuthent_status()){
            case "未认证":
            case "认证失败"://打开账户认证对话框
                showAuthentDialog();
                break;
            case "审核中"://打开账户认证选择界面
                ToastUtil.myToast("您的账户认证还在审核中，无法操作！");
                break;
            case "已认证"://打开对应界面
                if (isEditPhone){//如果是修改手机号按钮，打开修改手机号界面
                    startActivity(new Intent(AccountSecurityActivity.this, EditPhoneActivity.class));
                }else {//如果是支付宝认证按钮，判断支付宝认证状态
                    switch (centerInfoData.getAlipay_authent_status()){
                        case "未认证":
                        case "认证失败"://打开支付宝认证界面，进行认证（重新认证）
                            Intent intent = new Intent(AccountSecurityActivity.this, AuthentAlipayActivity.class);
                            intent.putExtra(ConstantUtil.KEY_DATA,centerInfoData.getAlipay_authent_id());
                            startActivity(intent);
                            break;
                        case "审核中"://不做操作
                            ToastUtil.myToast("您的支付宝认证正在审核中！");
                            break;
                        case "已认证":
                            ToastUtil.myToast("您的支付宝认证已认证成功！");
                            break;
                        default:
                            break;
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
        setContentView(R.layout.activity_account_security);
        //沉浸式状态栏
        StatusBarUtil.setColor(AccountSecurityActivity.this, getResources().getColor(R.color.color_ffb321),0);
        //强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_account_security);
        initData();
    }

    /**
     * 接收 MainFragment 传递的个人中心信息
     */
    private void initData() {
        centerInfoData = (CenterInfoData) getIntent().getSerializableExtra(ConstantUtil.KEY_DATA);
        //支付宝认证状态
        tvAlipayState.setText(centerInfoData.getAlipay_authent_status());
        switch (centerInfoData.getAlipay_authent_status()){
            case "未认证":
            case "认证失败":
                tvAlipayState.setBackgroundResource(R.drawable.shape_solid_cccccc_10);
                break;
            case "审核中":
            case "已认证":
                tvAlipayState.setBackgroundResource(R.drawable.shape_solid_ffb321_10);
                break;
            default:
                break;
        }
    }

    /**
     * 账户认证对话框
     */
    private void showAuthentDialog() {
        CustomDialog.Builder builder = new CustomDialog.Builder(AccountSecurityActivity.this);
        builder.setTitle("系统提示");
        builder.setMessage("您的账户"+centerInfoData.getAuthent_status()+"，前往认证？");
        builder.setPositiveButton("账户认证", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                switch (centerInfoData.getAuthent_status()){
                    case "未认证"://打开账户认证选择界面
                        startActivity(new Intent(AccountSecurityActivity.this, AccountAuthentActivity.class));
                        break;
                    case "认证失败":
                        if (centerInfoData.getAuthent_type().equals("1")){//个人认证详情
                            startAuthentActivity(AuthentPersonalDetailsActivity.class);
                        }else {//工作室认证详情
                            startAuthentActivity(AuthentStudioDetailsActivity.class);
                        }
                        break;
                    default:
                        break;
                }
            }
        });
        builder.setNegativeButton("不，再等等",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        customDialog = builder.create();
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.show();
    }

    /**
     * 才能打开对应的认证详情界面（个人、工作室）
     * 传递认证id
     */
    private void startAuthentActivity(Class<?> clazz) {
        Intent intent = new Intent(AccountSecurityActivity.this, clazz);
        intent.putExtra(ConstantUtil.KEY_DATA,centerInfoData.getAuthent());
        startActivity(intent);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        if (customDialog!=null){
            customDialog.dismiss();
            customDialog=null;
        }
        ToastUtil.cancleMyToast();
    }
}
