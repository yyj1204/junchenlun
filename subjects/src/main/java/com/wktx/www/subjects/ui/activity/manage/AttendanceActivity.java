package com.wktx.www.subjects.ui.activity.manage;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.manage.attendance.AttendanceRecordInfoData;
import com.wktx.www.subjects.basemvp.ABaseActivity;
import com.wktx.www.subjects.presenter.manage.AttendancePresenter;
import com.wktx.www.subjects.ui.view.manage.IAttendanceView;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.MyUtils;
import com.wktx.www.subjects.widget.CustomCalendar;
import com.wktx.www.subjects.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 管理我的工作---签到
 */
public class AttendanceActivity extends ABaseActivity<IAttendanceView,AttendancePresenter> implements IAttendanceView,CustomCalendar.CustomCalendarListener{
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.tv_chuqinDay)
    TextView tvChuqin;
    @BindView(R.id.tv_queqinDay)
    TextView tvQueqin;
    @BindView(R.id.customCalendar)
    CustomCalendar customCalendar;
    @BindView(R.id.bt_qiandao)
    Button btQiandao;

    private String hireId;

    @OnClick({R.id.tb_IvReturn,R.id.bt_qiandao})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn://返回
                finish();
                break;
            case R.id.bt_qiandao://签到
                if (MyUtils.isFastClick()){
                    return;
                }
                btQiandao.setEnabled(false);
                getPresenter().onSignIn(hireId);
                break;
            default:
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_manage_attendance);
        customCalendar.listener = this;
        initData();
    }

    /**
     * 接收 ManageActivity 传递过来的雇佣id
     */
    private void initData() {
        hireId = getIntent().getStringExtra(ConstantUtil.KEY_POSITION);
        //雇佣状态 0:未开始 1:合作中 2:请假中 3:暂停中 4:投诉中 5:被解雇 6:已完结 7:已退款 8:已取消 9:续约中
        String hireStatus = getIntent().getStringExtra(ConstantUtil.KEY_WHETHER);
        if (hireStatus.equals("1")||hireStatus.equals("9")){
            btQiandao.setVisibility(View.VISIBLE);
        }else {
            btQiandao.setVisibility(View.GONE);
        }
        getPresenter().getInfo(hireId,"0");
    }

    @Override
    protected AttendancePresenter createPresenter() {
        return new AttendancePresenter();
    }


    /**
     * 自定义日历的点击事件
     */
    @Override
    public void onClick(long titleTime) {
        getPresenter().getInfo(hireId,titleTime+"");
    }


    /**
     * IAttendanceView
     */
    @Override//签到回调
    public void onSingInResult(boolean isSuccess, String result) {
        btQiandao.setEnabled(true);
         ToastUtil.myToast(result);
        if (isSuccess){
            finish();
        }
    }
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
