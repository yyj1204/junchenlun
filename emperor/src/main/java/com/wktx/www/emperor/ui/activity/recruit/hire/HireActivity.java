package com.wktx.www.emperor.ui.activity.recruit.hire;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

import com.bigkoo.pickerview.TimePickerView;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.recruit.hire.HireInfoData;
import com.wktx.www.emperor.apiresult.recruit.resume.ResumeInfoData;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.recruit.hire.HirePresenter;
import com.wktx.www.emperor.utils.ArithUtil;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.DateUtil;
import com.wktx.www.emperor.utils.GlideUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.ui.view.recruit.hire.IHireView;
import com.wktx.www.emperor.utils.ToastUtil;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 简历---雇佣
 */
public class HireActivity extends ABaseActivity<IHireView,HirePresenter> implements IHireView {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    //员工基本信息
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_staffName)
    TextView tvStaffName;
    @BindView(R.id.tv_staffJob)
    TextView tvStaffJob;
    @BindView(R.id.tv_staffSalary)
    TextView tvStaffSalary;
    //雇佣方式
    @BindView(R.id.linear_hireWay1)
    LinearLayout llhireWay1;
    @BindView(R.id.tv_wayMonth)
    TextView tvWayMonth;
    @BindView(R.id.tv_wayCustom)
    TextView tvWayCustom;
    @BindView(R.id.linear_hireWay2)
    LinearLayout llhireWay2;
    @BindView(R.id.tv_wayMorning)
    TextView tvWayMorning;
    @BindView(R.id.tv_wayEvening)
    TextView tvWayEvening;
    //项目开始结束时间
    @BindView(R.id.tv_beginTime)
    TextView tvBeginTime;
    @BindView(R.id.linear_endTime)
    LinearLayout llEndTime;
    @BindView(R.id.tv_endTime)
    TextView tvEndTime;
    //定制价格
    @BindView(R.id.linear_customPrice)
    LinearLayout llCustomPrice;
    @BindView(R.id.et_customPrice)
    EditText etCustomPrice;
    //包月雇佣时间
    @BindView(R.id.linear_hireTime)
    LinearLayout llHireTime;
    @BindView(R.id.tv_hireTimeCount)
    TextView tvHireTimeCount;
    //客服提成
    @BindView(R.id.linear_pushMoney)
    LinearLayout llPushMoney;
    @BindView(R.id.et_pushMoney)
    EditText etPushMoney
            ;
    @BindView(R.id.et_demand)
    EditText etDemand;
    @BindView(R.id.et_qqNumber)
    EditText etQQNumber;
    @BindView(R.id.et_wechatNumber)
    EditText etWechatNumber;
    //雇佣金额
    @BindView(R.id.linear_totalMoney)
    LinearLayout llTotalMoney;
    @BindView(R.id.tv_totalMoney)
    TextView tvTotalMoney;
    @BindView(R.id.tv_sureOrders)
    TextView tvSureOrders;//确认下单


    //系统日历控件
    private Calendar curCalendar = Calendar.getInstance();

    private ResumeInfoData resumeInfoData;//员工简历信息
    private boolean isService;//是否是客服类型
    private boolean isCustom=false;//雇佣方式  false：包月 ，true：定制
    private boolean isEvening=false;//雇佣方式  false:早班 true：晚班（客服雇佣单人必填)

    private int hireTime=1;//雇佣时间（月）
    private String salaryStr;//月薪，用于计算雇佣总金额=月薪*雇佣月数
    private String beginDateStr;//项目开始时间"yyyy-MM-dd"格式
    private String endDateStr;//项目结束时间"yyyy-MM-dd"格式
    private long curDateLong;//当前时间的时间戳
    private long beginDateLong;//项目开始时间的时间戳
    private long endDateLong;//项目结束时间的时间戳


    @OnClick({R.id.tb_IvReturn,R.id.tv_wayMonth,R.id.tv_wayCustom,R.id.tv_wayMorning,R.id.tv_wayEvening,
            R.id.linear_beginTime,R.id.linear_endTime, R.id.rela_hireTimeMinus,R.id.rela_hireTimeAdd,R.id.tv_sureOrders})
    public void MyOnclick(View view) {
        //将输入法隐藏
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tvTitle.getWindowToken(), 0);
        switch (view.getId()) {
            case R.id.tb_IvReturn://返回
                finish();
                break;
            case R.id.tv_wayMonth://包月方式雇佣
                isCustom=false;
                tvWayMonth.setSelected(!isCustom);
                tvWayMonth.setBackground(getResources().getDrawable(R.drawable.shape_buttom_ffb321_38));
                tvWayCustom.setSelected(isCustom);
                tvWayCustom.setBackground(null);
                llEndTime.setVisibility(View.GONE);
                llCustomPrice.setVisibility(View.GONE);
                llHireTime.setVisibility(View.VISIBLE);
                llTotalMoney.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_wayCustom://定制方式雇佣
                isCustom=true;
                tvWayMonth.setSelected(!isCustom);
                tvWayMonth.setBackground(null);
                tvWayCustom.setSelected(isCustom);
                tvWayCustom.setBackground(getResources().getDrawable(R.drawable.shape_buttom_ffb321_38));
                llEndTime.setVisibility(View.VISIBLE);
                llCustomPrice.setVisibility(View.VISIBLE);
                llHireTime.setVisibility(View.GONE);
                llTotalMoney.setVisibility(View.GONE);
                break;
            case R.id.tv_wayMorning://雇佣单人雇佣（早班）
                isEvening=false;
                tvWayMorning.setSelected(!isEvening);
                tvWayMorning.setBackground(getResources().getDrawable(R.drawable.shape_buttom_ffb321_38));
                tvWayEvening.setSelected(isEvening);
                tvWayEvening.setBackground(null);
                break;
            case R.id.tv_wayEvening://雇佣单人雇佣（晚班）
                isEvening=true;
                tvWayMorning.setSelected(!isEvening);
                tvWayMorning.setBackground(null);
                tvWayEvening.setSelected(isEvening);
                tvWayEvening.setBackground(getResources().getDrawable(R.drawable.shape_buttom_ffb321_38));
                break;
            case R.id.linear_beginTime://项目开始时间
                pickDate(tvBeginTime,true,"选择项目开始时间");
                break;
            case R.id.linear_endTime://项目结束时间
                pickDate(tvEndTime,false,"选择项目结束时间");
                break;
            case R.id.rela_hireTimeMinus://雇佣时间减
                if (hireTime>1){
                    hireTime--;
                    initTimeAndMoney();
                }else {
                    if (MyUtils.isFastClick()) {
                        return;
                    }
                    ToastUtil.myToast("包月的雇佣时间最短为1个月哦!");
                }
                break;
            case R.id.rela_hireTimeAdd://雇佣时间加
                if (hireTime<12){
                    hireTime++;
                    initTimeAndMoney();
                }else {
                    if (MyUtils.isFastClick()) {
                        return;
                    }
                    ToastUtil.myToast("包月的雇佣时间最长为12个月哦!");
                }
                break;
            case R.id.tv_sureOrders://确认下单
                if (MyUtils.isFastClick()) {
                    return;
                }

                //判断输入框格式
                if (isCustom){
                    //如果开始日期大于结束日期，需要重新选择开始日期
                    if (beginDateLong > endDateLong ){
                        ToastUtil.myToast("项目开始时间需在结束时间之前!");
                        pickDate(tvBeginTime,true,"选择项目开始时间");
                        return;
                    }else if (TextUtils.isEmpty(getcustomPrice())){
                        ToastUtil.myToast("请输入定制价格！");
                        etCustomPrice.requestFocus();
                        return;
                    }
                }

                if (TextUtils.isEmpty(getPushMoney())){
                    ToastUtil.myToast("请输入约定好的业绩分成！");
                    etPushMoney.requestFocus();
                }else if (TextUtils.isEmpty(getDemandContent())) {
                    ToastUtil.myToast("请输入需求内容！");
                    etDemand.requestFocus();
                }else if (TextUtils.isEmpty(getQQNumber())){
                    ToastUtil.myToast("请输入QQ号码！");
                    etQQNumber.requestFocus();
                }else if (TextUtils.isEmpty(getWeChatNumber())){
                    ToastUtil.myToast("请输入微信号码！");
                    etWechatNumber.requestFocus();
                }else {//确认下单获取雇佣信息
                    tvSureOrders.setEnabled(false);
                    getPresenter().onGetHireInfo(resumeInfoData.getId());
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hire);
        ButterKnife.bind(this);
        initData();
        initUI();
    }

    @Override
    protected HirePresenter createPresenter() {
        return new HirePresenter();
    }

    /**
     * 接收 ResumeActivity 传递过来的员工简历信息
     */
    private void initData() {
        resumeInfoData = (ResumeInfoData) getIntent().getSerializableExtra(ConstantUtil.KEY_DATA);
        //月薪
        salaryStr = resumeInfoData.getMonthly_money();
    }

    private void initUI() {
        //头像
        if (TextUtils.isEmpty(resumeInfoData.getPicture())){
            if (resumeInfoData.getSex().equals("1")){
                ivHead.setImageResource(R.drawable.img_head_man);
            }else if (resumeInfoData.getSex().equals("2")){
                ivHead.setImageResource(R.drawable.img_head_woman);
            }else {
                ivHead.setImageResource(R.drawable.img_mine_head);
            }
        }else {
            GlideUtil.loadImage(resumeInfoData.getPicture(),R.drawable.img_mine_head,ivHead);
        }
        //员工姓名
        tvStaffName.setText(resumeInfoData.getName());
        //月薪
        tvStaffSalary.setText(salaryStr+"元/月");
        //员工职业类型
        tvTitle.setText(resumeInfoData.getTow_name()+"雇佣");
        tvStaffJob.setText(resumeInfoData.getTow_name());
        //初始化客服以及其他职位类型的控件
        initServiceUI(resumeInfoData.getTow());
        //定制价格输入框（小数点限制两位数）
        setEtListener(etCustomPrice);
        //提成方案输入框（小数点限制两位数）
        setEtListener(etPushMoney);
        //初始化雇佣时间与雇佣总金额
        initTimeAndMoney();
        //初始化项目开始结束时间
        initTime();
    }

    /**
     * 根据职位类型，显示相应布局
     * 雇佣方式、提成方案
     */
    private void initServiceUI(String jobType) {
        if (jobType.equals("2")){//客服
            isService=true;
            tvWayMorning.setSelected(true);
            tvWayEvening.setSelected(false);
            llhireWay1.setVisibility(View.GONE);
            llhireWay2.setVisibility(View.VISIBLE);
            llEndTime.setVisibility(View.GONE);
            llCustomPrice.setVisibility(View.GONE);
        }else {//其他
            isService=false;
            tvWayMonth.setSelected(true);
            tvWayCustom.setSelected(false);
            llhireWay1.setVisibility(View.VISIBLE);
            llhireWay2.setVisibility(View.GONE);
            llEndTime.setVisibility(View.GONE);
            llCustomPrice.setVisibility(View.GONE);
        }
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
     * 初始化雇佣时间月份数与雇佣总金额
     */
    private void initTimeAndMoney() {
        //雇佣时间
        tvHireTimeCount.setText(hireTime+"");
        //先将月薪转为double，进行计算总金额
        double totalMoney = ArithUtil.mul(Double.parseDouble(salaryStr), hireTime);
        //保留两位小数点
        DecimalFormat df = new DecimalFormat("0.00");
        //雇佣总金额
        tvTotalMoney.setText("¥"+df.format(totalMoney));
    }

    /**
     * 初始化项目开始结束时间
     */
    private void initTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //开始时间
        Date beginDate = curCalendar.getTime();
        beginDateStr = sdf.format(beginDate);
        //将项目开始时间转为时间戳(只转日期不转时间，方便pickDate方法做判断)
        curDateLong = Long.parseLong(DateUtil.getCustomType2Timestamp(beginDateStr,"yyyy-MM-dd"))* 1000L;
        beginDateLong = curDateLong;
        tvBeginTime.setText(beginDateStr);

        //结束时间---推迟日历一个月
        curCalendar.add(Calendar.DAY_OF_MONTH,-1);
        curCalendar.add(Calendar.MONTH,1);
        Date endDate = curCalendar.getTime();
        endDateStr = sdf.format(endDate);
        //将项目结束时间转为时间戳(只转日期不转时间，方便pickDate方法做判断)
        endDateLong = Long.parseLong(DateUtil.getCustomType2Timestamp(endDateStr,"yyyy-MM-dd"))* 1000L;
        tvEndTime.setText(endDateStr);
    }

    //选择项目开始结束时间
    private void  pickDate(final TextView tvDate, final boolean isBegin, String titleStr) {
        TimePickerView pvTime = new TimePickerView.Builder(HireActivity.this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String selectDateStr = sdf.format(date);
                long selectDateLong = date.getTime();

                //如果是开始时间，则选中的日期不能在当前日期之前
                if (isBegin){
                    if(selectDateLong >= curDateLong && selectDateLong < curDateLong + (15*24*60*60*1000L)) {
                        //如果是定制雇佣方式，需再判断开始日期不能在结束日期之后
                        if (isCustom){
                            if (selectDateLong > endDateLong){
                                ToastUtil.myToast("项目开始时间需在结束时间之前!");
                            }else {
                                beginDateLong = selectDateLong;//赋值，用在结束日期做判断
                                tvDate.setText(selectDateStr);
                            }
                        }else {
                            beginDateLong = selectDateLong;//赋值，用在结束日期做判断
                            tvDate.setText(selectDateStr);
                        }
                    }else {
                        ToastUtil.myToast("项目开始时间需在今日及之后15天之内!");
                    }

                }else {//如果是结束时间，则选中的日期不能在开始日期之前
                    if(selectDateLong >= beginDateLong) {
                        if (selectDateLong > beginDateLong + (31*24*60*60*1000L)){
                            ToastUtil.myToast( "定制时间超过1个月，建议使用包月雇佣方式!");
                        }
                        endDateLong = selectDateLong;//赋值，用在开始日期做判断
                        tvDate.setText(selectDateStr);
                    }else {
                        ToastUtil.myToast("项目结束时间需在开始时间之后!");
                    }
                }
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})//默认全部显示,年月日时分秒
                .setTitleText(titleStr)//标题文字
                .setTitleSize(16)//标题文字大小
                .setContentSize(14)//滚轮文字大小
                .setSubmitColor(HireActivity.this.getResources().getColor(R.color.color_ffb321))
                .setCancelColor(Color.GRAY)
                .isCenterLabel(false)
                .setOutSideCancelable(false)
                .build();

        pvTime.setDate(DateUtil.getNYR2Calendar(tvDate.getText().toString()));
        pvTime.show();
    }

    /**
     * IHireView
     */
    //雇佣方式
    @Override
    public String getHireWay() {
        if (isService){//如果是客服，雇佣方式是雇佣单人
            return "3";
        }else {
            if (isCustom){//定制
                return "2";
            }else {//包月
                return "1";
            }
        }
    }
    //客服---上班班次
    @Override
    public String getServiceShifts() {
        if (isEvening){//晚班
            return "evening";
        }else {//早班
            return "morning";
        }
    }
    @Override
    public String getBeginTime() {
        return tvBeginTime.getText().toString().trim();
    }
    @Override
    public String getEndTime() {
        return tvEndTime.getText().toString().trim();
    }
    @Override
    public String getcustomPrice() {
        return etCustomPrice.getText().toString().trim();
    }
    @Override
    public String getHireTime() {
        return tvHireTimeCount.getText().toString().trim();
    }
    @Override
    public String getPushMoney() {
        return etPushMoney.getText().toString().trim();
    }
    @Override
    public String getDemandContent() {
        return etDemand.getText().toString().trim();
    }
    @Override
    public String getQQNumber() {
        return etQQNumber.getText().toString().trim();
    }
    @Override
    public String getWeChatNumber() {
        return etWechatNumber.getText().toString().trim();
    }
    @Override
    public void onRequestSuccess(HireInfoData tData) {
        tvSureOrders.setEnabled(true);
        //将雇佣信息传递给 OrdersInfoActivity
        Intent intent = new Intent(this, OrdersInfoActivity.class);
        intent.putExtra(ConstantUtil.KEY_INFO,resumeInfoData);
        intent.putExtra(ConstantUtil.KEY_DATA,tData);
        startActivity(intent);
    }
    @Override
    public void onRequestFailure(String result) {
        tvSureOrders.setEnabled(true);
        ToastUtil.myToast(result);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtil.cancleMyToast();
    }
}
