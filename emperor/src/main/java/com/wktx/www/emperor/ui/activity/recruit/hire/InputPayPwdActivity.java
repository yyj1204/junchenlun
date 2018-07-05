package com.wktx.www.emperor.ui.activity.recruit.hire;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wktx.www.emperor.R;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.wktx.www.emperor.utils.Md5Util;
import com.wktx.www.emperor.widget.CustomKeyboardView;
import com.wktx.www.emperor.widget.CustomPwdEditView;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 简历---雇佣---确认订单信息---托管工资---输入余额支付密码
 */
public class InputPayPwdActivity extends AutoLayoutActivity {

    @BindView(R.id.tv_balanceMoney)
    TextView tvBalance;
    @BindView(R.id.pwdEditView)
    CustomPwdEditView pwdEditView;
    @BindView(R.id.keyboardView)
    CustomKeyboardView keyboardView;

    private String balanceMoney;//余额支付金额

    @OnClick({R.id.tb_IvReturn})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn://返回
                setResult(ConstantUtil.RESULTCODE_BALANCEPAY_CANCELE,new Intent(InputPayPwdActivity.this, TrusteeshipSalaryActivity.class));
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_paypwd);
        //强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);
        //接收 TrusteeshipSalaryActivity 传递过来的余额支付金额
        balanceMoney = getIntent().getStringExtra(ConstantUtil.KEY_DATA);
        initUI();
    }

    private void initUI() {
        //支付金额
        tvBalance.setText("¥"+balanceMoney);
        keyboardView.setEditView(pwdEditView);
        //密码输入框事件
        pwdEditView.setListener(new CustomPwdEditView.Listener() {
            @Override
            public void onValueChanged(String value) {
                LogUtil.error("内容发生变化==",value);
            }
            @Override
            public void onComplete(String value) {
                LogUtil.error("输入完成==",value);
                //将支付密码MD5加密后传回 TrusteeshipSalaryActivity
                Intent intent = new Intent(InputPayPwdActivity.this, TrusteeshipSalaryActivity.class);
                intent.putExtra(ConstantUtil.KEY_INFO, Md5Util.md5(value));
                setResult(ConstantUtil.RESULTCODE_BALANCEPAY_OK,intent);
                finish();
            }
        });
    }


}
