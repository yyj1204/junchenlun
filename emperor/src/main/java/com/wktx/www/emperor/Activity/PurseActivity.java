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

public class PurseActivity extends AppCompatActivity {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;

    @OnClick({R.id.tb_IvReturn, R.id.pureLlCash, R.id.pureLlRecharge})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.pureLlCash:
                startActivity(new Intent(this, CashActivity.class));
                break;
            case R.id.pureLlRecharge:
                startActivity(new Intent(this, RechargeActivity.class));
                break;
            case R.id.tb_IvReturn:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purse);
        // 设置右滑动返回
        Slidr.attach(this);
        ButterKnife.bind(this);
        tvTitle.setText(R.string.title_purse);

    }
}
