package com.wktx.www.emperor.ui.activity.recruit.hire;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.login.AccountInfoData;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.recruit.hire.PayResultPresenter;
import com.wktx.www.emperor.ui.activity.main.MainActivity;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LoginUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.ui.view.IView;
import com.wktx.www.emperor.utils.ToastUtil;
import com.wktx.www.emperor.widget.CustomDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 简历---雇佣---确认订单信息---托管工资---支付结果
 */
public class PayResultActivity extends ABaseActivity<IView,PayResultPresenter> implements IView<String> {

    @BindView(R.id.tb_IvReturn)
    ImageView ivReturn;
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.linear_success)
    LinearLayout linear_success;
    @BindView(R.id.linear_failure)
    LinearLayout linear_failure;

    private CustomDialog customDialog;
    private boolean isSuccess;//是否支付成功
    private String isActivity;//是哪个界面进来的
    private String hireId;

    @OnClick({R.id.bt_payAgain,R.id.bt_payCancel,R.id.bt_paySuccess})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.bt_payAgain://重新支付
               ToastUtil.myToast("请重新支付！");
                finish();
                break;
            case R.id.bt_payCancel://取消支付
                if (MyUtils.isFastClick()){
                    return;
                }
                //如果是托管工资，取消订单
                if (isActivity.equals(ConstantUtil.ACTIVITY_TGGZ)){
                    showCancelOrdersDialog();
                }else if (isActivity.equals(ConstantUtil.ACTIVITY_QBCZ)){//如果是钱包充值，返回个人中心
                   ToastUtil.myToast("取消支付");
                    startMainActivity();
                }
                break;
            case R.id.bt_paySuccess://支付完成
                if (MyUtils.isFastClick1()){
                    return;
                }
                startMainActivity();
                break;
            default:
                break;
        }
    }

    /**
     * 回到首页
     */
    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    /**
     * 取消雇佣订单
     */
    private void showCancelOrdersDialog() {
        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setTitle("系统提示");
        builder.setMessage("您是否要取消这笔雇佣订单？");
        builder.setPositiveButton("不，再等等", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                startMainActivity();
            }
        });

        builder.setNegativeButton("确认取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        getPresenter().onCancelOrders(hireId);
                    }
                });

        customDialog = builder.create();
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_result);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_pay_result);
        initData();
        initUI();
    }

    @Override
    protected PayResultPresenter createPresenter() {
        return new PayResultPresenter();
    }

    //接收 TrusteeshipSalaryActivity 传递过来的支付结果
    private void initData() {
        isSuccess = getIntent().getBooleanExtra(ConstantUtil.KEY_ISOK, false);
        isActivity = getIntent().getStringExtra(ConstantUtil.KEY_POSITION);
        //托管工资进来的话，需要hireId
        if (isActivity.equals(ConstantUtil.ACTIVITY_TGGZ)){
            hireId = getIntent().getStringExtra(ConstantUtil.KEY_DATA);
        }
    }

    private void initUI() {
        ivReturn.setVisibility(View.GONE);
        if (isSuccess){
            linear_success.setVisibility(View.VISIBLE);
            linear_failure.setVisibility(View.GONE);
        }else {
            linear_success.setVisibility(View.GONE);
            linear_failure.setVisibility(View.VISIBLE);
        }
    }

    /**
     * IView
     */
    @Override
    public AccountInfoData getUserInfo() {
        AccountInfoData userInfo = LoginUtil.getinit().getUserInfo();
        return userInfo;
    }
    @Override
    public void onRequestSuccess(String tData) {
       ToastUtil.myToast(tData);
        startMainActivity();
    }
    @Override
    public void onRequestFailure(String result) {
       ToastUtil.myToast(result);
    }



    /**
     * 系统返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            //如果是托管工资，取消订单
            if (isActivity.equals(ConstantUtil.ACTIVITY_TGGZ)){
                if (isSuccess){
                    startMainActivity();
                }else {
                    if (customDialog==null){
                        showCancelOrdersDialog();
                    }else {
                        customDialog.dismiss();
                        customDialog=null;
                    }
                }
            }else if (isActivity.equals(ConstantUtil.ACTIVITY_QBCZ)){//如果是钱包充值，返回个人中心
               ToastUtil.myToast("取消支付");
                startMainActivity();
            }
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (customDialog!=null){
            customDialog.dismiss();
            customDialog=null;
        }

        ToastUtil.cancleMyToast();
    }
}
