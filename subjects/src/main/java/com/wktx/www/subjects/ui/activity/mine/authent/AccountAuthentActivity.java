package com.wktx.www.subjects.ui.activity.mine.authent;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.utils.MyUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人中心---账户认证界面
 */
public class AccountAuthentActivity extends AutoLayoutActivity {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;

    @OnClick({R.id.tb_IvReturn,R.id.linear_personal,R.id.linear_studio})
    public void MyOnclick(View view) {
        if (MyUtils.isFastClick1()){
            return;
        }
        switch (view.getId()){
            case R.id.tb_IvReturn:
                finish();
                break;
            case R.id.linear_personal://个人认证
                startActivity(new Intent(this,AuthentPersonalActivity.class));
                break;
            case R.id.linear_studio://工作室认证
                startActivity(new Intent(this,AuthentStudioActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_authent);
        //沉浸式状态栏
        StatusBarUtil.setColor(AccountAuthentActivity.this, getResources().getColor(R.color.color_ffb321),0);
        //强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_account_authent);
    }
}
