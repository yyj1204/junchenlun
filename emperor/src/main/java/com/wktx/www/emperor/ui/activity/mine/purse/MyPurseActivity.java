package com.wktx.www.emperor.ui.activity.mine.purse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 我的钱包界面
 */
public class MyPurseActivity extends AutoLayoutActivity {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;

    @OnClick({R.id.tb_IvReturn, R.id.linear_recharge, R.id.linear_withdrawal})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn:
                finish();
                break;
            case R.id.linear_recharge://充值
                startActivity(new Intent(this, PurseRechargeActivity.class));
                break;
            case R.id.linear_withdrawal://提现
                startActivity(new Intent(this, PurseWithdrawalActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_purse);
        // 设置右滑动返回
        Slidr.attach(this);
        ButterKnife.bind(this);
        tvTitle.setText(R.string.title_my_purse);
    }
}
