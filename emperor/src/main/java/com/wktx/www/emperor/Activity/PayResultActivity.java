package com.wktx.www.emperor.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 美工简历---美工雇佣---确认订单信息---托管工资---支付结果
 */
public class PayResultActivity extends AppCompatActivity {

    @BindView(R.id.tb_TvBarTitle)
    TextView mTvTitle;
    @BindView(R.id.linear_success)
    LinearLayout linear_success;
    @BindView(R.id.linear_failure)
    LinearLayout linear_failure;

    @OnClick({R.id.tb_IvReturn,R.id.tv_pay_again,R.id.tv_pay_cancel,R.id.tv_pay_finish})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn://返回
                finish();
                break;
            case R.id.tv_pay_again://重新支付
                linear_success.setVisibility(View.VISIBLE);
                linear_failure.setVisibility(View.GONE);
                break;
            case R.id.tv_pay_cancel://取消支付
                finish();
                break;
            case R.id.tv_pay_finish://支付完成
                finish();
                break;
            default:
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_result);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        mTvTitle.setText(R.string.title_pay_result);
        initUI();
    }

    private void initUI() {
        linear_success.setVisibility(View.GONE);
        linear_failure.setVisibility(View.VISIBLE);
    }
}
