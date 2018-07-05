package com.wktx.www.emperor.ui.activity.staff;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.login.AccountInfoData;
import com.wktx.www.emperor.apiresult.staff.fire.StaffFireInfoData;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.staff.StaffFirePresenter;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.DateUtil;
import com.wktx.www.emperor.utils.GlideUtil;
import com.wktx.www.emperor.utils.LoginUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.ui.view.staff.IStaffFireView;
import com.wktx.www.emperor.utils.ToastUtil;
import com.wktx.www.emperor.widget.CustomDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 管理我的员工---发起解雇
 */
public class StaffFireActivity extends ABaseActivity<IStaffFireView,StaffFirePresenter> implements IStaffFireView {

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
    //雇佣信息
    @BindView(R.id.tv_fireTime)
    TextView tvFireTime;
    @BindView(R.id.tv_cooperationTime)
    TextView tvCooperationTime;
    @BindView(R.id.tv_maxSalary)
    TextView tvMaxSalary;
    @BindView(R.id.tv_salary)
    TextView tvSalary;
    @BindView(R.id.et_salaryPay)
    EditText etAmount;
    @BindView(R.id.et_fireCause)
    EditText etFireCause;

    private String hireId;//雇佣id

    //系统日历控件
    private Calendar curCalendar = Calendar.getInstance();
    private CustomDialog customDialog;


    @OnClick({R.id.tb_IvReturn,R.id.bt_sureFire,R.id.bt_cancelFire})
    public void MyOnclick(View view) {
        //将输入法隐藏
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tvTitle.getWindowToken(), 0);
        switch (view.getId()) {
            case R.id.tb_IvReturn://返回
                finish();
                break;
            case R.id.bt_sureFire://确定解雇
                if (MyUtils.isFastClick()){
                    return;
                }
                //判断输入框格式
                if (TextUtils.isEmpty(getAmount())) {
                    ToastUtil.myToast("请输入愿意支付的金额！");
                    etAmount.requestFocus();
                }else if (TextUtils.isEmpty(getFireCause())){
                    ToastUtil.myToast("请输入解雇原因！");
                    etAmount.requestFocus();
                }else {//解雇弹窗
                    showFireDialog();
                }
                break;
            case R.id.bt_cancelFire://取消
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_fire);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_staff_fire);
        initData();
        //定制价格输入框（小数点限制两位数）
        setEtListener(etAmount);
    }

    @Override
    protected StaffFirePresenter createPresenter() {
        return new StaffFirePresenter();
    }

    /**
     * 接收 StaffManageActivity 传递过来的雇佣id
     */
    private void initData() {
        hireId = getIntent().getStringExtra(ConstantUtil.KEY_POSITION);
        getPresenter().onGetFireInfo(hireId);//获取解雇信息
    }

    /**
     * 解雇
     */
    private void showFireDialog() {
        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setTitle("发起解雇");
        builder.setMessage("您是否要解雇该员工？");
        builder.setPositiveButton("不，再等等", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        builder.setNegativeButton("确认解雇",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        getPresenter().onFire(hireId);
                    }
                });

        customDialog = builder.create();
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.show();
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
     * IStaffFireView
     */
    @Override
    public AccountInfoData getUserInfo() {
        AccountInfoData userInfo = LoginUtil.getinit().getUserInfo();
        return userInfo;
    }
    @Override
    public String getAmount() {
        return etAmount.getText().toString().trim();
    }
    @Override
    public String getFireCause() {
        return etFireCause.getText().toString().trim();
    }
    @Override//发起解雇是否成功的回调
    public void onFireResult(boolean isSuccess, String msg) {
        ToastUtil.myToast(msg);
        if (isSuccess){
            finish();
        }
    }
    @Override
    public void onRequestSuccess(StaffFireInfoData tData) {
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
        String beginTime = DateUtil.getTimestamp2CustomType(tData.getStart_time(), "yyyy.MM.dd");
        String endTime = DateUtil.getTimestamp2CustomType(tData.getEnd_time(), "yyyy.MM.dd");
        tvHireTime.setText(beginTime+"-"+endTime);
        //雇佣状态
        String hireState = tData.getStatus();
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
        //解雇时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        Date fireDate = curCalendar.getTime();
        tvFireTime.setText(sdf.format(fireDate));
        //合作时间
        tvCooperationTime.setText(tData.getHire_days()+"天");
        //托管工资
        tvMaxSalary.setText(tData.getMax_should_paid_wages()+"元");
        //应发工资
        tvSalary.setText(tData.getShould_paid_wages()+"元");
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
