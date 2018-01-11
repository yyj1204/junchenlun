package com.wktx.www.emperor.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 美工简历---美工雇佣---确认订单信息
 */
public class OrdersInfoActivity extends AppCompatActivity {

    @BindView(R.id.tb_TvBarTitle)
    TextView mTvTitle;

    @OnClick({R.id.tb_IvReturn,R.id.tv_order_sure})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn://返回
                finish();
                break;
            case R.id.tv_order_sure://确认下单
                startActivity(new Intent(this,SalaryTrusteeshipActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_info);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        mTvTitle.setText(R.string.title_orderinfo_confirm);
    }
}
