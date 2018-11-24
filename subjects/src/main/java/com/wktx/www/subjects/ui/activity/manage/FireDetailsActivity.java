package com.wktx.www.subjects.ui.activity.manage;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.manage.FireDetailsInfoData;
import com.wktx.www.subjects.basemvp.ABaseActivity;
import com.wktx.www.subjects.presenter.manage.FireDetailsPresenter;
import com.wktx.www.subjects.ui.view.manage.IFireDetailsView;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.GlideUtil;
import com.wktx.www.subjects.utils.MyUtils;
import com.wktx.www.subjects.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 解雇详情
 */
public class FireDetailsActivity extends ABaseActivity<IFireDetailsView,FireDetailsPresenter> implements IFireDetailsView  {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.tv_companyName)
    TextView tvCompanyName;
    //解雇信息
    @BindView(R.id.tv_fireState)
    TextView tvFireState;
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
    //解雇同意（拒绝、申请介入）
    @BindView(R.id.linear_fire)
    LinearLayout llFire;
    @BindView(R.id.tv_accept)
    TextView tvAccept;
    @BindView(R.id.tv_refuse)
    TextView tvRefuse;
    @BindView(R.id.tv_apply)
    TextView tvApply;

    private String fireId;//雇佣id

    @OnClick({R.id.tb_IvReturn,R.id.tv_accept,R.id.tv_refuse,R.id.tv_apply})
    public void MyOnclick(View view) {
        if (MyUtils.isFastClick()){
            return;
        }
        switch (view.getId()) {
            case R.id.tb_IvReturn:
                finish();
                break;
            case R.id.tv_accept://同意解雇
                setButtom(false);
                getPresenter().acceptFire(fireId);
                break;
            case R.id.tv_refuse://拒绝解雇
                setButtom(false);
                getPresenter().refuseFire(fireId);
                break;
            case R.id.tv_apply://申请介入
                setButtom(false);
                getPresenter().applyFire(fireId);
                break;
            default:
                break;
        }
    }

    private void setButtom(boolean isEnabled) {
        tvAccept.setEnabled(isEnabled);
        tvRefuse.setEnabled(isEnabled);
        tvApply.setEnabled(isEnabled);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_details);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_fire_details);
        initData();
    }

    @Override
    protected FireDetailsPresenter createPresenter() {
        return new FireDetailsPresenter();
    }

    /**
     * 接收 ManageFragment ManageActivity Message2InviteFragment 传递过来的 解雇id
     */
    private void initData() {
        fireId = getIntent().getStringExtra(ConstantUtil.KEY_DATA);
        getPresenter().getInfo(fireId);
    }

    /**
     * IFireDetailsView
     */
    @Override
    public void onFireResult(boolean isSuccess, String result) {
        setButtom(true);
        ToastUtil.myToast(result);
        if (isSuccess){
            finish();
        }
    }
    @Override
    public void onRequestSuccess(FireDetailsInfoData tData) {
        //解雇状态  0:等待臣民确认 1:臣民已确认 2:臣民已拒绝 3:君主放弃解雇 4:君主申请后台审核 5:后台同意解雇 6:后台不同意解雇
        String status = tData.getStatus();
        switch (status){
            case "0":
                llFire.setVisibility(View.VISIBLE);
                tvFireState.setText("等待确认");
                break;
            case "1":
                llFire.setVisibility(View.GONE);
                tvFireState.setText("同意解雇");
                break;
            case "2":
                llFire.setVisibility(View.GONE);
                tvFireState.setText("拒绝解雇");
                break;
            case "3":
                llFire.setVisibility(View.GONE);
                tvFireState.setText("君主放弃解雇");
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

        //公司头像
        if (TextUtils.isEmpty(tData.getHead_pic())){
            ivHead.setImageResource(R.drawable.img_mine_head);
        }else {
            GlideUtil.loadImage(tData.getHead_pic(),R.drawable.img_mine_head,ivHead);
        }
        tvCompanyName.setText(tData.getNickname());
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
