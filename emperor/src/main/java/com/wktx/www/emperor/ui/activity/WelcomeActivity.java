package com.wktx.www.emperor.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.wktx.www.emperor.R;
import com.wktx.www.emperor.utils.ToastUtil;
import com.zhy.autolayout.AutoLayoutActivity;
/**
 * 欢迎界面
 */
public class WelcomeActivity extends AutoLayoutActivity {
    private TextView tvClose;

    SharedPreferences preferences;
    private boolean isFirst;

    private int time = 3;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                tvClose.setText(time+"s");
            } else if (msg.what == 1) {
                startMainActivity();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //解决按home键，再次点击程序图标重启问题
        if((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome);
        //强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        preferences=getSharedPreferences("jclBoss", Context.MODE_PRIVATE);
        isFirst = preferences.getBoolean("isFirst",true);

        tvClose = findViewById(R.id.tv_close);
        tvClose.setText(time+"s");
        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMainActivity();
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (; time > 0; time--) {
                    handler.sendEmptyMessage(0);
                    if (time <= 0) {
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                handler.sendEmptyMessage(1);
            }
        }).start();
    }

    /**
     * 如果第一次安装---新手引导页
     * 如果已经安装过 --- 主界面
     */
    private void startMainActivity() {
        if(!isFirst){
            startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
        }else {
            startActivity(new Intent(WelcomeActivity.this,GuideActivity.class));
            SharedPreferences.Editor editor=preferences.edit();
            editor.putBoolean("isFirst", false);
            editor.commit();
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //终止消息队列，防止内存泄漏
        if (handler!=null){
            handler.removeCallbacksAndMessages(null);
        }
    }
}
