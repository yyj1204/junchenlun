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
 * 管理我的员工
 */
public class StaffManageActivity extends AppCompatActivity {

    @BindView(R.id.tb_TvBarTitle)
    TextView mTvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_manage);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        mTvTitle.setText(R.string.title_staff_manage);
    }


    @OnClick({R.id.tb_IvReturn,R.id.tv_staff_renew,R.id.linear_staff_salary,R.id.linear_staff_report,
            R.id.linear_staff_chuqin,R.id.linear_staff_queqin,R.id.linear_arrange_work,R.id.linear_pause_work,
            R.id.linear_launch_complaint,R.id.linear_launch_fire})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn://返回
                finish();
                break;
            case R.id.tv_staff_renew://续签
                startActivity(new Intent(this,StaffRenewActivity.class));
                break;
            case R.id.linear_staff_salary://工资
                startActivity(new Intent(this,StaffSalaryActivity.class));
                break;
            case R.id.linear_staff_report://报告
                startActivity(new Intent(this,StaffReportActivity.class));
                break;
            case R.id.linear_staff_chuqin://出勤
                startActivity(new Intent(this,StaffAttendanceActivity.class));
                break;
            case R.id.linear_staff_queqin://缺勤
                startActivity(new Intent(this,StaffAttendanceActivity.class));
                break;
            case R.id.linear_arrange_work://安排工作
                startActivity(new Intent(this,StaffArrangeWorkActivity.class));
                break;
            case R.id.linear_pause_work://暂停工作
                startActivity(new Intent(this,StaffPauseWorkActivity.class));
                break;
            case R.id.linear_launch_complaint://发起投诉
                startActivity(new Intent(this,StaffComplaintActivity.class));
                break;
            case R.id.linear_launch_fire://发起解雇
                startActivity(new Intent(this,StaffFireActivity.class));
                break;
            default:
                break;
        }
    }

}
