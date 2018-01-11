package com.wktx.www.emperor.Activity;

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

public class RechargeActivity extends AppCompatActivity {

    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.img_wechat)
    ImageView mIvWechart;
    @BindView(R.id.img_Ali)
    ImageView mIvAli;
    private boolean isSelectWechat = false;

    @OnClick({R.id.tb_IvReturn,R.id.img_wechat,R.id.img_Ali,R.id.paywayRlAliPay,R.id.paywayRlWxPay})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn:
                finish();
                break;
            case R.id.img_wechat:
            case R.id.paywayRlWxPay:
                isSelectWechat = true;
                mIvWechart.setSelected(isSelectWechat);
                mIvAli.setSelected(!isSelectWechat);
                break;
            case R.id.img_Ali:
            case R.id.paywayRlAliPay:
                isSelectWechat = false;
                mIvAli.setSelected(!isSelectWechat);
                mIvWechart.setSelected(isSelectWechat);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        // 设置右滑动返回
        Slidr.attach(this);
        ButterKnife.bind(this);
        tvTitle.setText(R.string.title_recharge);
    }
}
