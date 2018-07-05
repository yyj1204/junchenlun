package com.wktx.www.emperor.ui.activity.main;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.jaeger.library.StatusBarUtil;
import com.wktx.www.emperor.R;
import com.zhy.autolayout.AutoLayoutActivity;
/**
 * 欢迎界面
 */
public class WelcomeActivity extends AutoLayoutActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //解决按home键，再次点击程序图标重启问题
        if((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0)
        {
            finish();
            return;
        }
        setContentView(R.layout.activity_welcome);
        //沉浸式状态栏
        StatusBarUtil.setColor(WelcomeActivity.this, getResources().getColor(R.color.color_ffb321),0);
        //强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
