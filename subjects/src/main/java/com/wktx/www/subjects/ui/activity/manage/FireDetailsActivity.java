package com.wktx.www.subjects.ui.activity.manage;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.login.AccountInfoData;
import com.wktx.www.subjects.apiresult.manage.FireDetailsInfoData;
import com.wktx.www.subjects.apiresult.manage.WorkListInfoData;
import com.wktx.www.subjects.basemvp.ABaseActivity;
import com.wktx.www.subjects.presenter.manage.FireDetailsPresenter;
import com.wktx.www.subjects.ui.view.manage.IFireDetailsView;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.GlideUtil;
import com.wktx.www.subjects.utils.LoginUtil;
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
    @BindView(R.id.tv_fireState)
    TextView tvFireState;
    @BindView(R.id.tv_fireTime)
    TextView tvFireTime;
    @BindView(R.id.tv_salary)
    TextView tvSalary;
    @BindView(R.id.tv_salaryPay)
    TextView tvSalaryPay;
    @BindView(R.id.tv_fireCause)
    TextView tvFireCause;
    @BindView(R.id.tv_fireResult)
    TextView tvFireResult;
    //解雇同意（拒绝）
    @BindView(R.id.linear_fire)
    LinearLayout llFire;
    @BindView(R.id.tv_accept)
    TextView tvAccept;
    @BindView(R.id.tv_refuse)
    TextView tvRefuse;

    private WorkListInfoData workListInfoData;//工作信息
    private String fireId;//雇佣id

    @OnClick({R.id.tb_IvReturn,R.id.tv_accept,R.id.tv_refuse})
    public void MyOnclick(View view) {
        if (MyUtils.isFastClick()){
            return;
        }
        switch (view.getId()) {
            case R.id.tb_IvReturn:
                finish();
                break;
            case R.id.tv_accept://同意解雇
                tvAccept.setEnabled(false);
                tvRefuse.setEnabled(false);
                getPresenter().acceptFire(fireId);
                break;
            case R.id.tv_refuse://拒绝解雇
                tvAccept.setEnabled(false);
                tvRefuse.setEnabled(false);
                getPresenter().refuseFire(fireId);
                break;
            default:
                break;
        }
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
        initUI();
    }

    @Override
    protected FireDetailsPresenter createPresenter() {
        return new FireDetailsPresenter();
    }

    /**
     * 接收 Message2InviteFragment 传递过来的 雇佣id
     */
    private void initData() {
        workListInfoData = (WorkListInfoData) getIntent().getSerializableExtra(ConstantUtil.KEY_DATA);
        fireId = workListInfoData.getDismissal_id();
        getPresenter().getInfo(fireId);
    }

    /**
     * 初始化控件
     */
    private void initUI() {
        //公司头像
        if (workListInfoData.getHead_pic()==null||workListInfoData.getHead_pic().equals("")){
            ivHead.setImageResource(R.drawable.img_mine_head);
        }else {
            GlideUtil.loadImage(workListInfoData.getHead_pic(),R.drawable.img_mine_head,ivHead);
        }
        tvCompanyName.setText(workListInfoData.getNickname());
    }

    /**
     * IFireDetailsView
     */
    @Override
    public AccountInfoData getUserInfo() {
        AccountInfoData userInfo = LoginUtil.getinit().getUserInfo();
        return userInfo;
    }
    @Override
    public void onFireResult(boolean isSuccess, String result) {
        tvAccept.setEnabled(true);
        tvRefuse.setEnabled(true);
         ToastUtil.myToast(result);
        if (isSuccess){
            finish();
        }
    }
    @Override
    public void onRequestSuccess(FireDetailsInfoData tData) {
        //解雇状态
        String status = tData.getStatus();
        switch (status){
            case "0":
                tvFireState.setText("等待确认");
                break;
            case "1":
                tvFireState.setText("同意解雇");
                break;
            case "2":
                tvFireState.setText("拒绝解雇");
                break;
            case "3":
                tvFireState.setText("君主放弃解雇");
                break;
            case "4":
                tvFireState.setText("君主申请后台审核");
                break;
            case "5":
                tvFireState.setText("后台同意解雇");
                break;
            case "6":
                tvFireState.setText("后台不同意解雇");
                break;
            default:
                break;
        }
        if (status.equals("0")){//解雇状态=等待臣民确认，显示解雇同意（拒绝）按钮
            llFire.setVisibility(View.VISIBLE);
        }else {//否则隐藏
            llFire.setVisibility(View.GONE);
        }

        //解雇时间
        tvFireTime.setText(tData.getAdd_time());
        //应支付工资
        tvSalary.setText(tData.getShould_paid_wages()+"元");
        //愿意支付工资
        tvSalaryPay.setText(tData.getBe_willing_paid_wages()+"元");
        //解雇原因
        if (tData.getReason()==null||tData.getReason().equals("")){
            tvFireCause.setText("无");
        }else {
            tvFireCause.setText(tData.getReason());
        }
        //后台处理结果
        if (tData.getRemark()==null||tData.getRemark().equals("")){
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
