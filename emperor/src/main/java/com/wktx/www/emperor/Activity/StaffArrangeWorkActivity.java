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
 * 管理我的员工---安排工作
 */
public class StaffArrangeWorkActivity extends AppCompatActivity {

    @BindView(R.id.tb_TvBarTitle)
    TextView mTvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_arrange_work);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        mTvTitle.setText(R.string.title_staff_arrangework);
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
