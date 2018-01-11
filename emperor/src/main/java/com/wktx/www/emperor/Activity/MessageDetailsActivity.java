package com.wktx.www.emperor.Activity;

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
 * 消息通知---公告详情
 */
public class MessageDetailsActivity extends AppCompatActivity {
    @BindView(R.id.tb_TvBarTitle)
    TextView mTvTitle;
    @BindView(R.id.tv_notice_title)
    TextView tv_title;
    @BindView(R.id.tv_notice_time)
    TextView tv_time;
    @BindView(R.id.tv_notice_content)
    TextView tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        initData();
        initUI();
    }
    private void initData() {
    }

    private void initUI() {
        mTvTitle.setText(R.string.title_message_notice);
        tv_title.setText("君臣论最新版本上线");
        tv_time.setText("2017/10/30  15:17:50");
        tv_content.setText("本周三君臣论APP最新版本隆重上线");
    }

    @OnClick({R.id.tb_IvReturn})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn://返回
                finish();
                break;
            default:
                break;
        }
    }
}
