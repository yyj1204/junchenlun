package com.wktx.www.emperor.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.xlhratingbar_lib.XLHRatingBar;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 管理我的员工---工作报告---发布报告
 */
public class StaffReportDetaisActivity extends AppCompatActivity {

    @BindView(R.id.tb_TvBarTitle)
    TextView mTvTitle;
    @BindView(R.id.rb_star_serve)
    XLHRatingBar rb_serve;
    @BindView(R.id.rb_star_work)
    XLHRatingBar rb_work;
    @BindView(R.id.rb_star_respond)
    XLHRatingBar rb_respond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_report_detais);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        mTvTitle.setText(R.string.title_staff_report_details);
        initRatingBar();
    }

    /**
     * 工作评分
     */
    private void initRatingBar() {
        rb_serve.setOnRatingChangeListener(new XLHRatingBar.OnRatingChangeListener() {
            @Override
            public void onChange(int countSelected) {
            }
        });
        rb_work.setOnRatingChangeListener(new XLHRatingBar.OnRatingChangeListener() {
            @Override
            public void onChange(int countSelected) {
            }
        });
        rb_respond.setOnRatingChangeListener(new XLHRatingBar.OnRatingChangeListener() {
            @Override
            public void onChange(int countSelected) {
            }
        });
    }

    @OnClick({R.id.tb_IvReturn,R.id.tv_report_sure})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn://返回
                finish();
                break;
            case R.id.tv_report_sure://确认发布
                break;
            default:
                break;
        }
    }
}
