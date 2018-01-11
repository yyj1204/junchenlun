package com.wktx.www.subjects.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.subjects.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人中心---关于我们
 */
public class AboutUsActivity extends AppCompatActivity{
    @BindView(R.id.tb_TvBarTitle)
    TextView mTvTitle;
    @OnClick(R.id.tb_IvReturn)
    public void MyOnclick(View view) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        mTvTitle.setText(R.string.title_About_us);
    }
}
