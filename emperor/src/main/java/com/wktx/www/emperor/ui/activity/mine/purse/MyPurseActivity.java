package com.wktx.www.emperor.ui.activity.mine.purse;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.mine.center.CenterInfoData;
import com.wktx.www.emperor.ui.activity.mine.authent.AccountAuthentActivity;
import com.wktx.www.emperor.ui.activity.mine.authent.AuthentPersonalDetailsActivity;
import com.wktx.www.emperor.ui.activity.mine.authent.AuthentStoreDetailsActivity;
import com.wktx.www.emperor.ui.activity.mine.security.AuthentAlipayActivity;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.utils.ToastUtil;
import com.wktx.www.emperor.widget.CustomDialog;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 个人中心---我的钱包
 */
public class MyPurseActivity extends AutoLayoutActivity {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.tv_alipayState)
    TextView tvAlipayState;


    private CenterInfoData centerInfoData;//个人中心信息

    private CustomDialog customDialog;

    @OnClick({R.id.tb_IvReturn, R.id.linear_recharge, R.id.linear_withdrawal})
    public void MyOnclick(View view) {
        if (MyUtils.isFastClick()){
            return;
        }
        switch (view.getId()) {
            case R.id.tb_IvReturn:
                finish();
                break;
            case R.id.linear_recharge://充值
                startActivity(new Intent(this, PurseRechargeActivity.class));
                break;
            case R.id.linear_withdrawal://提现（账户、支付宝认证成功才可以提现）
                isAuthentStartActivity();
                break;
            default:
                break;
        }
    }

    /**
     * 判断账户认证状态，再打开对应界面
     */
    private void isAuthentStartActivity() {
        switch (centerInfoData.getAuthent_status()){
            case "未认证":
            case "认证失败"://打开账户认证对话框
                showAuthentDialog();
                break;
            case "审核中"://打开账户认证选择界面
                ToastUtil.myToast("您的账户认证还在审核中，无法提现！");
                break;
            case "已认证"://打开对应界面
                switch (centerInfoData.getAlipay_authent_status()){
                    case "未认证":
                    case "认证失败"://打开支付宝认证对话框
                        showAlipayAuthentDialog();
                        break;
                    case "审核中"://不做操作
                        ToastUtil.myToast("您的支付宝认证还在审核中，无法提现！");
                        break;
                    case "已认证"://提现
                        double userMoney = Double.parseDouble(centerInfoData.getUserinfo().getUser_money());
                        if (userMoney<=10){
                            ToastUtil.myToast("您的余额不足10元，无法提现！");
                        }else {
                            Intent intent = new Intent(this, PurseWithdrawActivity.class);
                            intent.putExtra(ConstantUtil.KEY_DATA,centerInfoData);
                            startActivity(intent);
                        }
                        break;
                    default:
                        break;
                }

                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_purse);
        //沉浸式状态栏
        StatusBarUtil.setColor(MyPurseActivity.this, getResources().getColor(R.color.color_ffb321),0);
        //强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 设置右滑动返回
        Slidr.attach(this);
        ButterKnife.bind(this);
        tvTitle.setText(R.string.title_my_purse);
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
        CustomDialog.Builder builder = new CustomDialog.Builder(MyPurseActivity.this);
        builder.setTitle("系统提示");
        builder.setMessage("您的账户"+centerInfoData.getAuthent_status()+"，前往认证？");
        builder.setPositiveButton("账户认证", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                switch (centerInfoData.getAuthent_status()){
                    case "未认证"://打开账户认证选择界面
                        startActivity(new Intent(MyPurseActivity.this, AccountAuthentActivity.class));
                        break;
                    case "认证失败":
                        if (centerInfoData.getAuthent_type().equals("1")){//个人认证详情
                            startAuthentActivity(AuthentPersonalDetailsActivity.class);
                        }else {//店铺认证详情
                            startAuthentActivity(AuthentStoreDetailsActivity.class);
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
     * 才能打开对应的认证详情界面（个人、店铺）
     * 传递认证id
     */
    private void startAuthentActivity(Class<?> clazz) {
        Intent intent = new Intent(MyPurseActivity.this, clazz);
        intent.putExtra(ConstantUtil.KEY_DATA,centerInfoData.getAuthent());
        startActivity(intent);
    }

    /**
     * 支付宝认证对话框
     */
    private void showAlipayAuthentDialog() {
        CustomDialog.Builder builder = new CustomDialog.Builder(MyPurseActivity.this);
        builder.setTitle("系统提示");
        builder.setMessage("您的支付宝"+centerInfoData.getAlipay_authent_status()+"，前往认证？");
        builder.setPositiveButton("支付宝认证", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                switch (centerInfoData.getAlipay_authent_status()){
                    case "未认证":
                    case "认证失败"://打开支付宝认证界面，进行认证（重新认证）
                        Intent intent = new Intent(MyPurseActivity.this, AuthentAlipayActivity.class);
                        intent.putExtra(ConstantUtil.KEY_ISOK,centerInfoData.getAuthent_type());
                        intent.putExtra(ConstantUtil.KEY_DATA,centerInfoData.getAlipay_authent_id());
                        startActivity(intent);
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
