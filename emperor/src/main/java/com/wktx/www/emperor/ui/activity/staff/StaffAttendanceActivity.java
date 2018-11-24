package com.wktx.www.emperor.ui.activity.staff;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.staff.attendance.AttendanceRecordInfoData;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.staff.StaffAttendancePresenter;
import com.wktx.www.emperor.ui.activity.staff.report.StaffWorkListActivity;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.ui.view.IView;
import com.wktx.www.emperor.utils.ToastUtil;
import com.wktx.www.emperor.widget.CustomCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 管理我的员工---考勤记录
 */
public class StaffAttendanceActivity extends ABaseActivity<IView,StaffAttendancePresenter> implements IView<AttendanceRecordInfoData>,CustomCalendar.CustomCalendarListener{
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.tv_chuqinDay)
    TextView tvChuqin;
    @BindView(R.id.tv_queqinDay)
    TextView tvQueqin;
    @BindView(R.id.customCalendar)
    CustomCalendar customCalendar;

    private String hireId;

    @OnClick({R.id.tb_IvReturn,R.id.bt_viewReport})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn://返回
                finish();
                break;
            case R.id.bt_viewReport://查看报告
                if (MyUtils.isFastClick1()){
                    return;
                }
                Intent intent = new Intent(this, StaffWorkListActivity.class);
                intent.putExtra(ConstantUtil.KEY_POSITION,hireId);
                intent.putExtra(ConstantUtil.KEY_ISOK,false);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_attendance);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_staff_attendance);
        customCalendar.listener = this;
        initData();
    }

    /**
     * 接收 StaffManageActivity 传递过来的雇佣id
     */
    private void initData() {
        hireId = getIntent().getStringExtra(ConstantUtil.KEY_POSITION);
        getPresenter().onGetAttendanceInfo(hireId,"0");
    }

    @Override
    protected StaffAttendancePresenter createPresenter() {
        return new StaffAttendancePresenter();
    }

    /**
     * 自定义日历的点击事件
     */
    @Override
    public void onClick(long titleTime) {
        getPresenter().onGetAttendanceInfo(hireId,titleTime+"");
    }


    /**
     * IView
     */
    @Override
    public void onRequestSuccess(AttendanceRecordInfoData tData) {
        tvChuqin.setText(tData.getAttendance_count());
        tvQueqin.setText(tData.getAbsence_from_duty_count());
        customCalendar.setData(tData.getAttendance_record());
    }
    @Override
    public void onRequestFailure(String result) {
        ToastUtil.myToast(result);
        customCalendar.setData(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtil.cancleMyToast();
    }
}
