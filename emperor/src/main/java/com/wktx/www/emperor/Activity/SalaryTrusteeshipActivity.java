package com.wktx.www.emperor.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 美工简历---美工雇佣---确认订单信息---托管工资
 */
public class SalaryTrusteeshipActivity extends AppCompatActivity {

    @BindView(R.id.tb_TvBarTitle)
    TextView mTvTitle;

    @BindView(R.id.img_balance)
    ImageView mIvBalance;
    @BindView(R.id.img_wechat)
    ImageView mIvWechart;
    @BindView(R.id.img_Ali)
    ImageView mIvAli;

    private int payType=0;//支付方式---0:余额，1：支付宝， 2：微信

    @OnClick({R.id.tb_IvReturn,R.id.img_balance,R.id.paywayRlBalance,R.id.img_Ali,R.id.paywayRlAliPay,
            R.id.img_wechat,R.id.paywayRlWxPay,R.id.tv_pay_sure})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn://返回
                finish();
                break;
            case R.id.img_balance:
            case R.id.paywayRlBalance://余额支付
                payType=0;
                mIvBalance.setSelected(true);
                mIvWechart.setSelected(false);
                mIvAli.setSelected(false);
                break;
            case R.id.img_Ali:
            case R.id.paywayRlAliPay://支付宝支付
                payType=1;
                mIvBalance.setSelected(false);
                mIvWechart.setSelected(false);
                mIvAli.setSelected(true);
                break;
            case R.id.img_wechat:
            case R.id.paywayRlWxPay://微信支付
                payType=2;
                mIvBalance.setSelected(false);
                mIvWechart.setSelected(true);
                mIvAli.setSelected(false);
                break;
            case R.id.tv_pay_sure://确认支付
                startActivity(new Intent(this,PayResultActivity.class));
                break;
            default:
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary_trusteeship);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        mTvTitle.setText(R.string.title_salary_trusteeship);
    }
}
