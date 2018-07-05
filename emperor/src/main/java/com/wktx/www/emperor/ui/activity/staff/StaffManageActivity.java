package com.wktx.www.emperor.ui.activity.staff;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.login.AccountInfoData;
import com.wktx.www.emperor.apiresult.staff.manage.StaffManageInfoData;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.staff.StaffManagePresenter;
import com.wktx.www.emperor.ui.activity.staff.report.StaffReportActivity;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.DateUtil;
import com.wktx.www.emperor.utils.GlideUtil;
import com.wktx.www.emperor.utils.LoginUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.ui.view.IView;
import com.wktx.www.emperor.utils.ToastUtil;

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
    @BindView(R.id.tv_renewal)
    TextView tvRenewal;
    //工资报告出勤缺勤
    @BindView(R.id.tv_staff_salary)
    TextView tvSalary;
    @BindView(R.id.tv_staff_report)
    TextView tvReport;
    @BindView(R.id.tv_staff_chuqin)
    TextView tvChuqin;
    @BindView(R.id.tv_staff_queqin)
    TextView tvQueqin;

    @BindView(R.id.tv_pauseWork)
    TextView tvPauseWork;
    @BindView(R.id.tv_launchComplaint)
    TextView tvLaunchComplaint;

    private String hireId;//雇佣id
    private StaffManageInfoData manageInfoData;//员工管理的基本信息
    private String hireState;//雇佣状态

    @OnClick({R.id.tb_IvReturn,R.id.tv_renewal,R.id.linear_staff_salary,R.id.linear_staff_report,
            R.id.linear_staff_chuqin,R.id.linear_staff_queqin,R.id.linear_arrangeWork,R.id.linear_pauseWork,
            R.id.linear_launchComplaint,R.id.linear_launchFire})
    public void MyOnclick(View view) {
        if (MyUtils.isFastClick1()){
            return;
        }
        switch (view.getId()) {
            case R.id.tb_IvReturn://返回
                finish();
                break;
            case R.id.tv_renewal://续签
                //将员工基本信息传递给 StaffRenewalActivity
                Intent intent = new Intent(this, StaffRenewalActivity.class);
                intent.putExtra(ConstantUtil.KEY_DATA,manageInfoData);
                startActivity(intent);
                break;
            case R.id.linear_staff_salary://工资
                toActivity(StaffSalaryActivity.class);
                break;
            case R.id.linear_staff_report://报告
                toActivity(StaffReportActivity.class);
                break;
            case R.id.linear_staff_chuqin://出勤
            case R.id.linear_staff_queqin://缺勤
                toActivity(StaffAttendanceActivity.class);
                break;
            case R.id.linear_arrangeWork://安排工作
                toActivity(StaffArrangeWorkActivity.class);
                break;
            case R.id.linear_pauseWork://暂停工作
                Intent intent1 = new Intent(this, StaffPauseWorkActivity.class);
                intent1.putExtra(ConstantUtil.KEY_POSITION,hireId);
                if (hireState.equals("3")){//如果暂停中，传递true
                    intent1.putExtra(ConstantUtil.KEY_ISOK,true);
                }else {//如果未暂停，false
                    intent1.putExtra(ConstantUtil.KEY_ISOK,false);
                }
                startActivity(intent1);
                break;
            case R.id.linear_launchComplaint://发起投诉
                Intent intent2 = new Intent(this, StaffComplaintActivity.class);
                intent2.putExtra(ConstantUtil.KEY_POSITION,hireId);
                if (hireState.equals("4")){//如果投诉中，传递true
                    intent2.putExtra(ConstantUtil.KEY_ISOK,true);
                }else {//如果未投诉，false
                    intent2.putExtra(ConstantUtil.KEY_ISOK,false);
                }
                startActivity(intent2);
                break;
            case R.id.linear_launchFire://发起解雇
                toActivity(StaffFireActivity.class);
                break;
            default:
                break;
        }
    }

    private void toActivity(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra(ConstantUtil.KEY_POSITION,hireId);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().onGetStaffInfo(hireId);
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
        manageInfoData = tData;
        //头像
        if (tData.getPicture()==null||tData.getPicture().equals("")){
            if (tData.getSex().equals("1")){
                ivHead.setImageResource(R.drawable.img_head_man);
            }else if (tData.getSex().equals("2")){
                ivHead.setImageResource(R.drawable.img_head_woman);
            }else {
                ivHead.setImageResource(R.drawable.img_mine_head);
            }
        }else {
            GlideUtil.loadImage(tData.getPicture(),R.drawable.img_mine_head,ivHead);
        }
        //性别 1:男 2:女
        if (tData.getSex().equals("1")){
            ivSex.setImageResource(R.drawable.ic_sex_man);
        }else if (tData.getSex().equals("2")){
            ivSex.setImageResource(R.drawable.ic_sex_woman);
        }else {
            ivSex.setVisibility(View.GONE);
        }
        //员工姓名
        tvStaffName.setText(tData.getName());
        //员工职业类型
        tvStaffJob.setText(tData.getTow_name());
        //雇佣时间
        String beginTime = DateUtil.getTimestamp2CustomType(tData.getProject_start_time(), "yyyy.MM.dd");
        String endTime = DateUtil.getTimestamp2CustomType(tData.getProject_end_time(), "yyyy.MM.dd");
        tvHireTime.setText(beginTime+"-"+endTime);
        //雇佣状态
        hireState = tData.getType();
        if (hireState.equals("1")){//合作中
            ivHireState.setImageResource(R.drawable.ic_hirerecord_state1);
        }else if (hireState.equals("2")){//请假中
            ivHireState.setImageResource(R.drawable.ic_hirerecord_state2);
        }else if (hireState.equals("3")){//暂停中
            ivHireState.setImageResource(R.drawable.ic_hirerecord_state3);
        }else if (hireState.equals("4")){//投诉中
            ivHireState.setImageResource(R.drawable.ic_hirerecord_state4);
        }else if (hireState.equals("5")){//被解雇
            ivHireState.setImageResource(R.drawable.ic_hirerecord_state5);
        }else if (hireState.equals("6")){//完结
            ivHireState.setImageResource(R.drawable.ic_hirerecord_state6);
        }else if (hireState.equals("7")){//退款
            ivHireState.setImageResource(R.drawable.ic_hirerecord_state7);
        }else if (hireState.equals("8")){//未付款到期
            ivHireState.setImageResource(R.drawable.ic_hirerecord_state8);
        }else if (hireState.equals("9")){//续约中
            ivHireState.setImageResource(R.drawable.ic_hirerecord_state9);
        }else if (hireState.equals("10")){//待入职
            ivHireState.setImageResource(R.drawable.ic_hirerecord_state10);
        }

        //工资报告出勤缺勤
        tvSalary.setText(tData.getHire_price());
        tvReport.setText(tData.getReport_count()+"");
        tvChuqin.setText(tData.getAttendance_count()+"");
        tvQueqin.setText(tData.getAbsence_from_duty_count()+"");

        //暂停中
        if (hireState.equals("3")){
            tvPauseWork.setText("恢复工作");

        }else {
            tvPauseWork.setText("暂停工作");
        }

        //投诉中
        if (hireState.equals("4")){
            tvLaunchComplaint.setText("撤销投诉");
        }else {
            tvLaunchComplaint.setText("发起投诉");
        }
    }
    @Override
    public void onRequestFailure(String result) {
        ToastUtil.myToast(result);
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtil.cancleMyToast();
    }
}
