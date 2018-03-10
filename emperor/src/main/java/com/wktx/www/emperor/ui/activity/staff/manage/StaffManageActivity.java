package com.wktx.www.emperor.ui.activity.staff.manage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.Activity.StaffArrangeWorkActivity;
import com.wktx.www.emperor.Activity.StaffAttendanceActivity;
import com.wktx.www.emperor.Activity.StaffComplaintActivity;
import com.wktx.www.emperor.Activity.StaffFireActivity;
import com.wktx.www.emperor.Activity.StaffPauseWorkActivity;
import com.wktx.www.emperor.Activity.StaffRenewActivity;
import com.wktx.www.emperor.Activity.StaffReportActivity;
import com.wktx.www.emperor.Activity.StaffSalaryActivity;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.login.AccountInfoData;
import com.wktx.www.emperor.apiresult.staff.manage.StaffManageInfoData;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.staff.manage.StaffManagePresenter;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.DateUtil;
import com.wktx.www.emperor.utils.LoginUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.view.IView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 管理我的员工
 */
public class StaffManageActivity extends ABaseActivity<IView,StaffManagePresenter> implements IView<StaffManageInfoData> {

    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    //员工信息
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.iv_hireState)
    ImageView ivHireState;
    @BindView(R.id.iv_sex)
    ImageView ivSex;
    @BindView(R.id.tv_staffName)
    TextView tvStaffName;
    @BindView(R.id.tv_staffJob)
    TextView tvStaffJob;
    @BindView(R.id.tv_hireTime)
    TextView tvHireTime;
    //工资报告出勤缺勤
    @BindView(R.id.tv_staff_salary)
    TextView tvSalary;
    @BindView(R.id.tv_staff_report)
    TextView tvReport;
    @BindView(R.id.tv_staff_chuqin)
    TextView tvChuqin;
    @BindView(R.id.tv_staff_queqin)
    TextView tvQueqin;

    private String hireId;

    @OnClick({R.id.tb_IvReturn,R.id.tv_renewal,R.id.linear_staff_salary,R.id.linear_staff_report,
            R.id.linear_staff_chuqin,R.id.linear_staff_queqin,R.id.linear_arrangeWork,R.id.linear_pauseWork,
            R.id.linear_launchComplaint,R.id.linear_launchFire})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn://返回
                finish();
                break;
            case R.id.tv_renewal://续签
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
            case R.id.linear_arrangeWork://安排工作
                startActivity(new Intent(this,StaffArrangeWorkActivity.class));
                break;
            case R.id.linear_pauseWork://暂停工作
                startActivity(new Intent(this,StaffPauseWorkActivity.class));
                break;
            case R.id.linear_launchComplaint://发起投诉
                startActivity(new Intent(this,StaffComplaintActivity.class));
                break;
            case R.id.linear_launchFire://发起解雇
                startActivity(new Intent(this,StaffFireActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_manage);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_staff_manage);
        initData();
    }

    @Override
    protected StaffManagePresenter createPresenter() {
        return new StaffManagePresenter();
    }

    private void initData() {
        hireId = getIntent().getStringExtra(ConstantUtil.KEY_DATA);
        getPresenter().onGetStaffInfo(hireId);
    }


    /**
     * IView
     */
    @Override
    public AccountInfoData getUserInfo() {
        AccountInfoData userInfo = LoginUtil.getinit().getUserInfo();
        return userInfo;
    }
    @Override
    public void onRequestSuccess(StaffManageInfoData tData) {
        //头像
        if (!tData.getPicture().equals("")){
            Glide.with(StaffManageActivity.this).load(tData.getPicture()).into(ivHead);
        }else {
            if (tData.getSex().equals("1")){
                ivHead.setImageResource(R.drawable.img_head_man);
            }else if (tData.getSex().equals("2")){
                ivHead.setImageResource(R.drawable.img_head_woman);
            }else {
                ivHead.setImageResource(R.drawable.img_mine_head);
            }
        }
        //性别 1:男 2:女
        if (tData.getSex().equals("1")){
            ivSex.setImageResource(R.drawable.ic_sex_man);
        }else if (tData.getSex().equals("2")){
            ivSex.setImageResource(R.drawable.ic_sex_woman);
        }
        //员工姓名
        tvStaffName.setText(tData.getName());
        //员工职业类型
        String jobTow = tData.getTow();
        if (jobTow.equals("1")){
            tvStaffJob.setText("美工");
        }else if (jobTow.equals("2")){
            tvStaffJob.setText("客服");
        }else if (jobTow.equals("3")){
            tvStaffJob.setText("运营");
        }
        //雇佣时间
        String beginTime = DateUtil.getTimestamp2CustomType(tData.getProject_start_time(), "yyyy.MM.dd");
        String endTime = DateUtil.getTimestamp2CustomType(tData.getProject_end_time(), "yyyy.MM.dd");
        tvHireTime.setText(beginTime+"-"+endTime);
        //雇佣状态
        if (tData.getType().equals("1")){//合作中
            ivHireState.setImageResource(R.drawable.ic_hirerecord_state1);
        }else if (tData.getType().equals("2")){//请假中
            ivHireState.setImageResource(R.drawable.ic_hirerecord_state2);
        }else if (tData.getType().equals("3")){//暂停中
            ivHireState.setImageResource(R.drawable.ic_hirerecord_state3);
        }else if (tData.getType().equals("4")){//投诉中
            ivHireState.setImageResource(R.drawable.ic_hirerecord_state4);
        }else if (tData.getType().equals("5")){//被解雇
            ivHireState.setImageResource(R.drawable.ic_hirerecord_state5);
        }else if (tData.getType().equals("6")){//完结
            ivHireState.setImageResource(R.drawable.ic_hirerecord_state6);
        }else if (tData.getType().equals("7")){//退款
            ivHireState.setImageResource(R.drawable.ic_hirerecord_state7);
        }else if (tData.getType().equals("8")){//未付款到期
            ivHireState.setImageResource(R.drawable.ic_hirerecord_state8);
        }else if (tData.getType().equals("9")){//续约中
            ivHireState.setImageResource(R.drawable.ic_hirerecord_state9);
        }
        //工资报告出勤缺勤
        tvSalary.setText(tData.getHire_price());
        tvReport.setText(tData.getReport_count()+"");
        tvChuqin.setText(tData.getAttendance_count()+"");
        tvQueqin.setText(tData.getAbsence_from_duty_count()+"");
    }
    @Override
    public void onRequestFailure(String result) {
        finish();
        MyUtils.showToast(StaffManageActivity.this,result);
    }
}
