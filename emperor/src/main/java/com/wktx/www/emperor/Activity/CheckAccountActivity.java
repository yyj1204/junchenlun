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

public class CheckAccountActivity extends AppCompatActivity {
    @BindView(R.id.tb_TvBarTitle)
    TextView mTvTitle;
    @OnClick({R.id.tb_IvReturn,R.id.linear_account_individual,R.id.linear_account_store})
    public void MyOnclick(View view) {
        switch (view.getId()){
            case R.id.tb_IvReturn:
                finish();
                break;
            case R.id.linear_account_individual://个人认证
                startActivity(new Intent(this,CertificateIndividualActivity.class));
                break;
            case R.id.linear_account_store://店铺认证
                startActivity(new Intent(this,CertificateStoreActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_account);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        mTvTitle.setText(R.string.title_check_account);
    }
}
