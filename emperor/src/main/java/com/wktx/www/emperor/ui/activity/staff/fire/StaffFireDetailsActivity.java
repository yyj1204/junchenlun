package com.wktx.www.emperor.ui.activity.staff.fire;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.staff.fire.StaffFireDetailsInfoData;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.staff.fire.StaffFireDetailsPresenter;
import com.wktx.www.emperor.ui.view.staff.fire.IStaffFireDetailsView;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.GlideUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 管理我的员工---解雇详情
 */
public class StaffFireDetailsActivity extends ABaseActivity<IStaffFireDetailsView,StaffFireDetailsPresenter> implements IStaffFireDetailsView {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    //员工信息
    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.iv_sex)
    ImageView ivSex;
    @BindView(R.id.tv_staffName)
    TextView tvStaffName;
    @BindView(R.id.tv_staffJob)
    TextView tvStaffJob;
    @BindView(R.id.tv_fireState)
    TextView tvFireState;
    //解雇信息
    @BindView(R.id.tv_fireTime)
    TextView tvFireTime;
    @BindView(R.id.tv_workDay)
    TextView tvWorkDay;
    @BindView(R.id.tv_salary)
    TextView tvSalary;
    @BindView(R.id.tv_salaryPay)
    TextView tvSalaryPay;
    @BindView(R.id.tv_fireCause)
    TextView tvFireCause;
    @BindView(R.id.tv_fireResult)
    TextView tvFireResult;
    //解雇取消（申请介入）
    @BindView(R.id.linear_fire)
    LinearLayout llFire;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_apply)
    TextView tvApply;

    private String fireId;//雇佣id

    @OnClick({R.id.tb_IvReturn,R.id.tv_cancel,R.id.tv_apply})
    public void MyOnclick(View view) {
        if (MyUtils.isFastClick()){
            return;
        }
        switch (view.getId()) {
            case R.id.tb_IvReturn:
                finish();
                break;
            case R.id.tv_cancel://撤销解雇
                setButtom(false);
                getPresenter().onCancelFire(fireId);
                break;
            case R.id.tv_apply://申请介入
                setButtom(false);
                getPresenter().onApplyFire(fireId);
                break;
            default:
                break;
        }
    }

    private void setButtom(boolean isEnabled) {
        tvCancel.setEnabled(isEnabled);
        tvApply.setEnabled(isEnabled);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_fire_details);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_staff_fireinfo);
        initData();
    }

    @Override
    protected StaffFireDetailsPresenter createPresenter() {
        return new StaffFireDetailsPresenter();
    }

    /**
     * 接收 StaffManageActivity 解雇id
     */
    private void initData() {
        fireId = getIntent().getStringExtra(ConstantUtil.KEY_DATA);
        getPresenter().onGetFireInfo(fireId);
    }

    /**
     * IStaffFireDetailsView
     */
    @Override
    public void onFireResult(boolean isSuccess, String msg) {
        setButtom(true);
        ToastUtil.myToast(msg);
        if (isSuccess){
            finish();
        }
    }
    @Override
    public void onRequestSuccess(StaffFireDetailsInfoData tData) {
        //解雇状态  0:等待臣民确认 1:臣民已确认 2:臣民已拒绝 3:君主放弃解雇 4:君主申请后台审核 5:后台同意解雇 6:后台不同意解雇
        String status = tData.getStatus();
        switch (status){
            case "0":
                llFire.setVisibility(View.VISIBLE);
                tvFireState.setText("等待臣子确认");
                break;
            case "1":
                llFire.setVisibility(View.GONE);
                tvFireState.setText("臣子同意解雇");
                break;
            case "2":
                llFire.setVisibility(View.VISIBLE);
                tvFireState.setText("臣子拒绝解雇");
                break;
            case "3":
                llFire.setVisibility(View.GONE);
                tvFireState.setText("放弃解雇");
                break;
            case "4":
                llFire.setVisibility(View.GONE);
                tvFireState.setText("申请后台审核");
                break;
            case "5":
                llFire.setVisibility(View.GONE);
                tvFireState.setText("后台同意解雇");
                break;
            case "6":
                llFire.setVisibility(View.GONE);
                tvFireState.setText("后台不同意解雇");
                break;
            default:
                break;
        }

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
        tvStaffJob.setText(tData.getTow());
        //解雇时间
        tvFireTime.setText(tData.getAdd_time());
        //已工作天数
        tvWorkDay.setText(tData.getWork_days()+"天");
        //应支付工资
        tvSalary.setText(tData.getShould_paid_wages()+"元");
        //愿意支付工资
        tvSalaryPay.setText(tData.getBe_willing_paid_wages()+"元");
        //解雇原因
        if (TextUtils.isEmpty(tData.getReason())){
            tvFireCause.setText("无");
        }else {
            tvFireCause.setText(tData.getReason());
        }
        //后台处理结果
        if (TextUtils.isEmpty(tData.getRemark())){
            tvFireResult.setText("无");
        }else {
            tvFireResult.setText(tData.getRemark());
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
