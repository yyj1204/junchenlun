package com.wktx.www.emperor.ui.activity.mine;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.utils.VersionUtil;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *关于我们
 */
public class AboutUsActivity extends AutoLayoutActivity {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.tvVersion)
    TextView tvVersion;

    @OnClick(R.id.tb_IvReturn)
    public void MyOnclick(View view) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);//沉浸式状态栏
        StatusBarUtil.setColor(AboutUsActivity.this, getResources().getColor(R.color.color_ffb321),0);
        //强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 设置右滑动返回
        Slidr.attach(this);
        ButterKnife.bind(this);
        tvTitle.setText(R.string.title_about_us);
        tvVersion.setText("版本名称："  + VersionUtil.getAppVersionName(AboutUsActivity.this));

    }
}
