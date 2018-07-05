package com.wktx.www.emperor.ui.activity.staff;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.login.AccountInfoData;
import com.wktx.www.emperor.apiresult.recruit.hire.HireInfoData;
import com.wktx.www.emperor.apiresult.recruit.resume.ResumeInfoData;
import com.wktx.www.emperor.apiresult.staff.manage.StaffManageInfoData;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.staff.StaffRenewalPresenter;
import com.wktx.www.emperor.ui.activity.recruit.hire.OrdersInfoActivity;
import com.wktx.www.emperor.utils.ArithUtil;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.DateUtil;
import com.wktx.www.emperor.utils.GlideUtil;
import com.wktx.www.emperor.utils.LoginUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.ui.view.staff.IStaffRenewalView;
import com.wktx.www.emperor.utils.ToastUtil;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 管理我的员工---员工续签
 */
public class StaffRenewalActivity extends ABaseActivity<IStaffRenewalView,StaffRenewalPresenter> implements IStaffRenewalView {

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
    //续签月数
    @BindView(R.id.tv_renewalTimeCount)
    TextView tvRenewalTimeCount;
    //月薪要求
    @BindView(R.id.tv_staffSalary)
    TextView tvStaffSalary;
    //提成
    @BindView(R.id.linear_pushMoney)
    LinearLayout llPushMoney;
    @BindView(R.id.et_pushMoney)
    EditText etPushMoney;
    //续签总金额
    @BindView(R.id.tv_totalMoney)
    TextView tvTotalMoney;
    @BindView(R.id.tv_sureRenewal)
    TextView tvSureRenewal;//确认续签

    private StaffManageInfoData manageInfoData;//员工管理的基本信息
    private boolean isService;

    private int renewalTime=1;//续签时间（月）
    private String salaryStr;//月薪，用于计算续签总金额=月薪*续签月数

    @OnClick({R.id.tb_IvReturn, R.id.rela_renewalTimeMinus,R.id.rela_renewalTimeAdd,R.id.tv_sureRenewal})
    public void MyOnclick(View view) {
        //将输入法隐藏
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tvTitle.getWindowToken(), 0);
        switch (view.getId()) {
            case R.id.tb_IvReturn://返回
                finish();
                break;
            case R.id.rela_renewalTimeMinus://续签时间减号
                if (renewalTime>1){
                    renewalTime--;
                    initTimeAndMoney();
                }else {
                    if (MyUtils.isFastClick()) {
                        return;
                    }
                    ToastUtil.myToast("续签时间最短为1个月哦!");
                }
                break;
            case R.id.rela_renewalTimeAdd://续签时间加号
                if (renewalTime<12){
                    renewalTime++;
                    initTimeAndMoney();
                }else {
                    if (MyUtils.isFastClick()) {
                        return;
                    }
                    ToastUtil.myToast("续签时间最长为12个月哦!");
                }
                break;
            case R.id.tv_sureRenewal://确认续签
                if (MyUtils.isFastClick()){
                    return;
                }


                //判断输入框格式
                if (isService){
                    if (TextUtils.isEmpty(getPushMoney())){
                        ToastUtil.myToast("请输入约定好的业绩分成！");
                        etPushMoney.requestFocus();
                        return;
                    }
                }
                tvSureRenewal.setEnabled(false);
                getPresenter().onGetHireInfo(manageInfoData.getHire_id(),isService);

                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_renewal);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_staff_renewal);
        initData();
        initUI();
    }

    @Override
    protected StaffRenewalPresenter createPresenter() {
        return new StaffRenewalPresenter();
    }

    /**
     * 接收 StaffManageActivity 传递过来的员工信息
     */
    private void initData() {
        manageInfoData = (StaffManageInfoData) getIntent().getSerializableExtra(ConstantUtil.KEY_DATA);
        //月薪
        salaryStr = manageInfoData.getHire_price();
    }

    private void initUI() {
        //头像
        if (manageInfoData.getPicture()==null||manageInfoData.getPicture().equals("")){
            if (manageInfoData.getSex().equals("1")){
                ivHead.setImageResource(R.drawable.img_head_man);
            }else if (manageInfoData.getSex().equals("2")){
                ivHead.setImageResource(R.drawable.img_head_woman);
            }else {
                ivHead.setImageResource(R.drawable.img_mine_head);
            }
        }else {
            GlideUtil.loadImage(manageInfoData.getPicture(),R.drawable.img_mine_head,ivHead);
        }
        //性别 1:男 2:女
        if (manageInfoData.getSex().equals("1")){
            ivSex.setImageResource(R.drawable.ic_sex_man);
        }else if (manageInfoData.getSex().equals("2")){
            ivSex.setImageResource(R.drawable.ic_sex_woman);
        }else {
            ivSex.setVisibility(View.GONE);
        }
        //员工姓名
        tvStaffName.setText(manageInfoData.getName());
        //员工职业类型
        tvStaffJob.setText(manageInfoData.getTow_name());
        //雇佣时间
        String beginTime = DateUtil.getTimestamp2CustomType(manageInfoData.getProject_start_time(), "yyyy.MM.dd");
        String endTime = DateUtil.getTimestamp2CustomType(manageInfoData.getProject_end_time(), "yyyy.MM.dd");
        tvHireTime.setText(beginTime+"-"+endTime);
        //雇佣状态
        String hireState = manageInfoData.getType();
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
        //月薪要求
        tvStaffSalary.setText(salaryStr+"元/月");
        //提成方案输入框（小数点限制两位数）
        setEtListener(etPushMoney);
        //如果是客服，显示提成
        if (manageInfoData.getTow().equals("2")){
            isService=true;
            llPushMoney.setVisibility(View.VISIBLE);
        }else {
            isService=false;
            llPushMoney.setVisibility(View.GONE);
        }
        //初始化雇佣时间与雇佣总金额
        initTimeAndMoney();
    }

    /**
     * 初始化续签时间月份数与续签总金额
     */
    private void initTimeAndMoney() {
        //续签时间
        tvRenewalTimeCount.setText(renewalTime+"");
        //先将月薪转为double，进行计算总金额
        double totalMoney = ArithUtil.mul(Double.parseDouble(salaryStr), renewalTime);
        //保留两位小数点
        DecimalFormat df = new DecimalFormat("0.00");
        //雇佣总金额
        tvTotalMoney.setText("¥"+df.format(totalMoney));
    }

    //输入框的监听事件
    private void setEtListener(final EditText et) {
        et.addTextChangedListener(new TextWatcher() {
            @Override// 输入文本之前的状态
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override// 输入文字中的状态
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //限制金额输入框格式
                if (s.toString().contains(".")) {
                    //判断小数点的位置大于倒3，将输入框的字符串截取到小数点后两位数
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
                        et.setText(s);
                        et.setSelection(s.length());
                    }
                }

                //判断字符串的第一位是小数点，则在小数点前面加个0
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    et.setText(s);
                    et.setSelection(2);
                }

                //判断字符串第一位是0
                if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                    //如果第二位不是小数点，限制不能输入
                    if (!s.toString().substring(1, 2).equals(".")) {
                        et.setText(s.subSequence(0, 1));
                        et.setSelection(1);
                        return;
                    }
                }
            }
            @Override// 输入文字后的状态
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * IStaffRenewalView
     */
    @Override
    public AccountInfoData getUserInfo() {
        AccountInfoData userInfo = LoginUtil.getinit().getUserInfo();
        return userInfo;
    }
    @Override
    public String getRenewalTime() {
        return tvRenewalTimeCount.getText().toString().trim();
    }
    @Override
    public String getPushMoney() {
        return etPushMoney.getText().toString().trim();
    }
    @Override
    public void onRequestSuccess(HireInfoData tData) {
        tvSureRenewal.setEnabled(true);
        //将 OrdersInfoActivity 需要的员工信息赋值给 ResumeInfoData 传过去（为了跟雇佣流程保持一致）
        ResumeInfoData resumeInfoData = new ResumeInfoData();
        resumeInfoData.setHire_id(manageInfoData.getHire_id());
        resumeInfoData.setPicture(manageInfoData.getPicture());
        resumeInfoData.setName(manageInfoData.getName());
        resumeInfoData.setSex(manageInfoData.getSex());
        resumeInfoData.setMonthly_money(manageInfoData.getHire_price());
        resumeInfoData.setTow(manageInfoData.getTow());
        resumeInfoData.setTow(manageInfoData.getTow_name());
        //将雇佣信息传递给 OrdersInfoActivity
        Intent intent = new Intent(this, OrdersInfoActivity.class);
        intent.putExtra(ConstantUtil.KEY_INFO,resumeInfoData);
        intent.putExtra(ConstantUtil.KEY_DATA,tData);
        startActivity(intent);
    }
    @Override
    public void onRequestFailure(String result) {
        tvSureRenewal.setEnabled(true);
        ToastUtil.myToast(result);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtil.cancleMyToast();
    }
}
