package com.wktx.www.emperor.ui.activity.staff;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.staff.manage.StaffManageInfoData;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.staff.StaffManagePresenter;
import com.wktx.www.emperor.ui.activity.recruit.resume.ResumeActivity;
import com.wktx.www.emperor.ui.activity.staff.fire.StaffFireActivity;
import com.wktx.www.emperor.ui.activity.staff.fire.StaffFireDetailsActivity;
import com.wktx.www.emperor.ui.activity.staff.leave.LeaveListActivity;
import com.wktx.www.emperor.ui.activity.staff.report.StaffWorkListActivity;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.DateUtil;
import com.wktx.www.emperor.utils.GlideUtil;
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

    @BindView(R.id.linear_normal)
    LinearLayout llNormal;
    @BindView(R.id.linear_evaluate)
    LinearLayout llEvaluate;
    @BindView(R.id.linear_fireDetails)
    LinearLayout llFireDetails;
    @BindView(R.id.tv_pauseWork)
    TextView tvPauseWork;
    @BindView(R.id.tv_launchComplaint)
    TextView tvLaunchComplaint;
    @BindView(R.id.tv_launchFire)
    TextView tvLaunchFire;
    @BindView(R.id.tv_leave)
    TextView tvLeave;

    private String hireId;//雇佣id
    private StaffManageInfoData manageInfoData;//员工管理的基本信息
    private String fireId;//解雇id
    private String hireState;//雇佣状态

    @OnClick({R.id.tb_IvReturn,R.id.iv_head,R.id.tv_renewal,R.id.linear_staff_salary,R.id.linear_staff_report,
            R.id.linear_staff_chuqin,R.id.linear_staff_queqin,R.id.linear_arrangeWork,R.id.linear_pauseWork,
            R.id.linear_launchComplaint,R.id.linear_launchFire,R.id.linear_evaluate,R.id.linear_fireDetails,
            R.id.linear_leavelist})
    public void MyOnclick(View view) {
        if (MyUtils.isFastClick1()){
            return;
        }
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.tb_IvReturn://返回
                finish();
                break;
            case R.id.iv_head://考核指标
                toActivity(StaffKPIActivity.class);
                break;
            case R.id.tv_renewal://续签
                if (tvRenewal.getText().toString().trim().equals("续签")){
                    //将员工基本信息传递给 StaffRenewalActivity
                    intent.setClass(this, StaffRenewalActivity.class);
                    intent.putExtra(ConstantUtil.KEY_DATA,manageInfoData);
                    startActivity(intent);
                }else if (tvRenewal.getText().toString().trim().equals("再次雇佣")){
                    //将员工简历id传递给 ResumeActivity
                    intent.setClass(this, ResumeActivity.class);
                    intent.putExtra(ConstantUtil.KEY_POSITION,manageInfoData.getRid());
                    startActivity(intent);
                }
                break;
            case R.id.linear_staff_salary://工资
                toActivity(StaffSalaryActivity.class);
                break;
            case R.id.linear_staff_report://报告
                toWorklistActivity(false);
                break;
            case R.id.linear_staff_chuqin://出勤
            case R.id.linear_staff_queqin://缺勤
                toActivity(StaffAttendanceActivity.class);
                break;
            case R.id.linear_arrangeWork://安排工作
                toWorklistActivity(true);
                break;
            case R.id.linear_pauseWork://暂停工作
                intent.setClass(this, StaffPauseWorkActivity.class);
                intent.putExtra(ConstantUtil.KEY_POSITION,hireId);
                if (hireState.equals("3")){//如果暂停中，传递true
                    intent.putExtra(ConstantUtil.KEY_ISOK,true);
                }else {//如果未暂停，false
                    intent.putExtra(ConstantUtil.KEY_ISOK,false);
                }
                startActivity(intent);
                break;
            case R.id.linear_launchComplaint://发起投诉
                intent.setClass(this, StaffComplaintActivity.class);
                intent.putExtra(ConstantUtil.KEY_POSITION,hireId);
                if (hireState.equals("4")){//如果投诉中，传递true
                    intent.putExtra(ConstantUtil.KEY_ISOK,true);
                }else {//如果未投诉，false
                    intent.putExtra(ConstantUtil.KEY_ISOK,false);
                }
                startActivity(intent);
                break;
            case R.id.linear_launchFire://发起解雇
                if (fireId.equals("0")){
                    toActivity(StaffFireActivity.class);
                }else {
                    //将员工解雇id传递给 StaffFireDetailsActivity
                    intent.setClass(this, StaffFireDetailsActivity.class);
                    intent.putExtra(ConstantUtil.KEY_DATA,fireId);
                    startActivity(intent);
                }
                break;
            case R.id.linear_evaluate://订单评价
                //将员工雇佣id传递给 StaffEvaluateActivity
                intent.setClass(this, StaffEvaluateActivity.class);
                intent.putExtra(ConstantUtil.KEY_POSITION,manageInfoData.getHire_id());
                startActivity(intent);
                break;
            case R.id.linear_fireDetails://解雇详情
                //将员工解雇id传递给 StaffFireDetailsActivity
                intent.setClass(this, StaffFireDetailsActivity.class);
                intent.putExtra(ConstantUtil.KEY_DATA,fireId);
                startActivity(intent);
                break;
            case R.id.linear_leavelist://请假记录
                toActivity(LeaveListActivity.class);
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

    /**
     * 打开工作安排列表
     * @param isAdd
     * true:安排工作
     * false：工作报告
     */
    private void toWorklistActivity(boolean isAdd) {
        Intent intent = new Intent(this, StaffWorkListActivity.class);
        intent.putExtra(ConstantUtil.KEY_POSITION,hireId);
        intent.putExtra(ConstantUtil.KEY_ISOK,isAdd);
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

    /**
     * 接收 StaffFragment、HireRecordActivity、TransactionRecordActivity 传递的雇佣id
     */
    private void initData() {
        hireId = getIntent().getStringExtra(ConstantUtil.KEY_DATA);
    }


    /**
     * IView
     */
    @Override
    public void onRequestSuccess(StaffManageInfoData tData) {
        manageInfoData = tData;
        //头像
        if (TextUtils.isEmpty(tData.getPicture())){
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
        //工资报告出勤缺勤
        tvSalary.setText(tData.getHire_price());
        tvReport.setText(tData.getReport_count()+"");
        tvChuqin.setText(tData.getAttendance_count()+"");
        tvQueqin.setText(tData.getAbsence_from_duty_count()+"");

        //解雇id
        fireId = tData.getDismissal_id();
        //雇佣状态 0:全部状态 1:合作中(雇佣成功) 2:请假中 3:暂停中 4:投诉中 5:被解雇 6:完结 7:退款 8:已取消 9:续约中 10:待入职
        hireState = tData.getType();
        if (!fireId.equals("0")){//被解雇或者解雇中
            if (hireState.equals("5")){
                setHireState("再次雇佣",View.VISIBLE,View.GONE,R.drawable.ic_hirerecord_state5);
            }else {
                setHireState("续签",View.GONE,View.VISIBLE,R.drawable.ic_hirerecord_state5ing);
            }
        }else {
            switch (hireState){
                case "1"://合作中
                    setHireState("续签",View.VISIBLE,View.VISIBLE,R.drawable.ic_hirerecord_state1);
                    break;
                case "2"://请假中
                    setHireState("续签",View.VISIBLE,View.VISIBLE,R.drawable.ic_hirerecord_state2);
                    break;
                case "3"://暂停中
                    setHireState("续签",View.VISIBLE,View.VISIBLE,R.drawable.ic_hirerecord_state3);
                    break;
                case "4"://投诉中
                    setHireState("续签",View.VISIBLE,View.VISIBLE,R.drawable.ic_hirerecord_state4);
                    break;
                case "5"://被解雇
                    setHireState("再次雇佣",View.VISIBLE,View.GONE,R.drawable.ic_hirerecord_state5);
                    break;
                case "6"://完结
                    setHireState("再次雇佣",View.VISIBLE,View.GONE,R.drawable.ic_hirerecord_state6);
                    break;
                case "7"://退款
                    setHireState("再次雇佣",View.VISIBLE,View.GONE,R.drawable.ic_hirerecord_state7);
                    break;
                case "8"://未付款到期
                    setHireState("续签",View.GONE,View.GONE,R.drawable.ic_hirerecord_state8_1);
                    break;
                case "9"://续约中
                    setHireState("续签",View.GONE,View.VISIBLE,R.drawable.ic_hirerecord_state9);
                    break;
                case "10"://待入职
                    setHireState("续签",View.GONE,View.GONE,R.drawable.ic_hirerecord_state10);
                    break;
                default:
                    break;
            }
        }

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

        //解雇
        if (fireId.equals("0")){
            tvLaunchFire.setText("发起解雇");
        }else {
            tvLaunchFire.setText("解雇详情");
        }
    }

    @Override
    public void onRequestFailure(String result) {
        ToastUtil.myToast(result);
        finish();
    }

    /**
     * 设置雇佣状态相关布局
     * @param tvRenewalStr 续签按钮
     * @param tvRenewalVisibility
     * @param llNormalVisibility
     * @param hireStateImageResource  雇佣状态图标
     */
    private void setHireState(String tvRenewalStr,int tvRenewalVisibility,int llNormalVisibility,int hireStateImageResource) {
        tvRenewal.setText(tvRenewalStr);
        tvRenewal.setVisibility(tvRenewalVisibility);
        llNormal.setVisibility(llNormalVisibility);
        //如果 llNormal布局是隐藏的，说明当前订单是结束的订单，判断是否需要评价按钮
        if (llNormal.getVisibility()==View.GONE){
            //订单评价id=0，显示订单评价按钮，!=0，隐藏订单评价按钮
            if (manageInfoData.getEvaluate_id().equals("0")){
                llEvaluate.setVisibility(View.VISIBLE);
            }else {
                llEvaluate.setVisibility(View.GONE);
            }
            //解雇id!=0，显示解雇详情按钮，=0，隐藏解雇详情按钮
            if (!fireId.equals("0")){
                llFireDetails.setVisibility(View.VISIBLE);
            }else {
                llFireDetails.setVisibility(View.GONE);
            }
        }else {
            llEvaluate.setVisibility(View.GONE);
            llFireDetails.setVisibility(View.GONE);
        }
        ivHireState.setImageResource(hireStateImageResource);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtil.cancleMyToast();
    }
}
