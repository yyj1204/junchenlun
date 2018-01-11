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

/**
 * 管理我的员工---考勤记录
 */
public class StaffAttendanceActivity extends AppCompatActivity {

    @BindView(R.id.tb_TvBarTitle)
    TextView mTvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_attendance);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        mTvTitle.setText(R.string.title_staff_attendance);
    }

    @OnClick({R.id.tb_IvReturn,R.id.tv_view_report})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn://返回
                finish();
                break;
            case R.id.tv_view_report://查看报告
                startActivity(new Intent(this,StaffReportActivity.class));
                break;
            default:
                break;
        }
    }
}
