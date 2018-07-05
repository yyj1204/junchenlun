package com.wktx.www.emperor.ui.activity.recruit.hire;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.login.AccountInfoData;
import com.wktx.www.emperor.apiresult.recruit.hire.CouponBalanceInfoData;
import com.wktx.www.emperor.apiresult.recruit.hire.HireInfoData;
import com.wktx.www.emperor.apiresult.recruit.resume.ResumeInfoData;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.recruit.hire.OrdersInfoPresenter;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.DateUtil;
import com.wktx.www.emperor.utils.GlideUtil;
import com.wktx.www.emperor.utils.LoginUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.ui.view.recruit.hire.IOrdersInfoView;
import com.wktx.www.emperor.utils.ToastUtil;
import com.wktx.www.emperor.widget.CustomDialog;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 简历---雇佣---确认订单信息
 */
public class OrdersInfoActivity extends ABaseActivity<IOrdersInfoView,OrdersInfoPresenter> implements IOrdersInfoView {
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
    //可用余额
    @BindView(R.id.linear_usableBalance)
    LinearLayout llUsableBalance;
    @BindView(R.id.tv_usableBalance)
    TextView tvUsableBalance;
    @BindView(R.id.iv_usableBalance)
    ImageView ivUsableBalance;

    @BindView(R.id.tv_hireWay)
    TextView tvHireWay;
    @BindView(R.id.tv_itemTime)
    TextView tvItemTime;
    @BindView(R.id.tv_hireMoney)
    TextView tvHireMoney;
    @BindView(R.id.tv_usableDiscounts)
    TextView tvUsableDiscounts;
    @BindView(R.id.tv_payMoney)
    TextView tvPayMoney;
    @BindView(R.id.tv_sureOrders)
    TextView tvSureOrders;//确认下单

    private ResumeInfoData resumeInfoData;//员工简历信息
    private HireInfoData hireInfoData;//确认订单信息
    private ArrayList<HireInfoData.CouponListBean> usableCouponList = new ArrayList<>();//可用优惠券集合

    //优惠券
    private OptionsPickerView pvCustomOptions;//自义定选择器
    private ArrayList<String> optionsItemStrs = new ArrayList<>();//选择器字符串集合
    private String couponId="0";//优惠券id
    //余额
    private boolean isUseBalance=false;//是否使用余额
    private String balanceStr;//可用余额字符串
    private double balanceDou;//可用余额
    private double couponMoney=0;//选中的优惠券金额,没选中则为0
    private double hireMoney;//雇佣总金额
    private double payMoney;//待支付金额

    private CustomDialog customDialog;
    @OnClick({R.id.tb_IvReturn,R.id.linear_usableBalance,R.id.linear_usableDiscounts,R.id.tv_sureOrders})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn://返回
                showCancelOrdersDialog();
                break;
            case R.id.linear_usableBalance://可用余额
                isUseBalance=!isUseBalance;
                ivUsableBalance.setSelected(isUseBalance);
                balanceDou = Double.parseDouble(balanceStr);
                if (isUseBalance){//使用余额
                    if (balanceDou > payMoney){//可用余额>待支付金额,待支付金额=0
                        payMoney = 0;
                    }else {//否则,待支付金额=雇佣总金额-余额-优惠券
                        payMoney = hireMoney-balanceDou-couponMoney;
                    }
                }else {//不使用余额,待支付金额=雇佣总金额-优惠券
                    balanceDou=0;
                    payMoney = hireMoney-couponMoney;
                }
                tvPayMoney.setText("¥" + getPayMoneyStr(payMoney));
                break;
            case R.id.linear_usableDiscounts://优惠券
                if (usableCouponList.size() != 0){
                    initCustomOptionPicker();
                }
                break;
            case R.id.tv_sureOrders://确认下单
                if (MyUtils.isFastClick()){
                    return;
                }

                //如果没有使用余额和优惠券，则直接进入 TrusteeshipSalaryActivity 进行支付
                if (!isUseBalance&&couponId.equals("0")){
                    Intent intent = new Intent(this, TrusteeshipSalaryActivity.class);
                    intent.putExtra(ConstantUtil.KEY_DATA,hireInfoData.getHire_id());
                    intent.putExtra(ConstantUtil.KEY_POSITION,ConstantUtil.ACTIVITY_QRDD);
                    startActivity(intent);
                }else {
                    tvSureOrders.setEnabled(false);
                    getPresenter().onUseCounpon(hireInfoData.getHire_id());
                }
                break;
            default:
                break;
        }
    }

    /**
     * 取消雇佣订单对话框
     */
    private void showCancelOrdersDialog() {
        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setTitle("系统提示");
        builder.setMessage("您是否要取消这笔雇佣订单？");
        builder.setPositiveButton("不，再等等", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("确认取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        getPresenter().onCancelOrders(hireInfoData.getHire_id());
                    }
                });

        customDialog = builder.create();
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_info);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_sureorders_info);
        initData();
        initUI();
    }

    @Override
    protected OrdersInfoPresenter createPresenter() {
        return new OrdersInfoPresenter();
    }

    /**
     * 接收 HireActivity StaffRenewalActivity 传递过来的员工简历（管理）信息、确认订单信息
     */
    private void initData() {
        resumeInfoData = (ResumeInfoData) getIntent().getSerializableExtra(ConstantUtil.KEY_INFO);
        hireInfoData = (HireInfoData) getIntent().getSerializableExtra(ConstantUtil.KEY_DATA);
        //遍历优惠券可用才添加到集合器
        ArrayList<HireInfoData.CouponListBean> couponList = hireInfoData.getCoupon_list();
        for (int i = 0; i < couponList.size() ; i++) {
            HireInfoData.CouponListBean couponListBean = couponList.get(i);
            if (couponListBean.isAvailable()){
                usableCouponList.add(couponListBean);
                String endTime = DateUtil.getTimestamp2CustomType(couponList.get(i).getEnd_time(),"yyyy-MM-dd");
                //判断金额格式，小数点后为"00"，直接取整
                String condition = couponList.get(i).getCondition();
                String money = couponList.get(i).getMoney();
                String[] cSplit = condition.split("[.]");
                String[] mSplit = money.split("[.]");
                String conditionStr="";
                String moneyStr="";
                if (cSplit[1].equals("00")){
                    conditionStr=cSplit[0];
                }else {
                    conditionStr=condition;
                }
                if (mSplit[1].equals("00")){
                    moneyStr=mSplit[0];
                }else {
                    moneyStr=money;
                }
                optionsItemStrs.add("满"+conditionStr+"元减"+moneyStr+"元("+endTime+")到期");
            }
        }
    }

    private void initUI() {
        //头像
        if (resumeInfoData.getPicture()==null||resumeInfoData.getPicture().equals("")){
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
        tvStaffSalary.setText(resumeInfoData.getMonthly_money()+"元/月");
        //员工职业类型
        tvStaffJob.setText(resumeInfoData.getTow_name());
        //雇佣方式
        String hireWay = hireInfoData.getHire_type();
        if (hireWay.equals("1")){
            tvHireWay.setText("包月");
        }else if (hireWay.equals("2")){
            tvHireWay.setText("定制");
        }else if (hireWay.equals("3")){
            tvHireWay.setText("雇佣单人");
        }
        //项目时间
        String beginTime = DateUtil.getTimestamp2CustomType(hireInfoData.getProject_start_time(), "yyyy.MM.dd");
        String endTime = DateUtil.getTimestamp2CustomType(hireInfoData.getProject_end_time(), "yyyy.MM.dd");
        tvItemTime.setText(beginTime+"-"+endTime);
        //可用余额
        balanceStr = hireInfoData.getUser_money();
        tvUsableBalance.setText(balanceStr +"元");
        if (balanceStr.equals("0.00")){
            llUsableBalance.setEnabled(false);
            ivUsableBalance.setVisibility(View.GONE);
        }else {
            llUsableBalance.setEnabled(true);
            ivUsableBalance.setVisibility(View.VISIBLE);
        }
        //可用优惠
        if (usableCouponList.size() == 0){
            tvUsableDiscounts.setText("暂无优惠可用");
        }else {
            tvUsableDiscounts.setText("点击选择优惠");
        }
        //雇佣总金额
        hireMoney = Double.parseDouble(hireInfoData.getHire_price());
        payMoney = hireMoney;//初始化待付款金额
        tvHireMoney.setText(getPayMoneyStr(payMoney)+"元");
        //待付款金额
        tvPayMoney.setText("¥"+getPayMoneyStr(payMoney));
    }

    /**
     * 自定义选择器
     */
    private void initCustomOptionPicker() {//条件选择器初始化，自定义布局
        /**
         * 注意事项：
         * 自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针。
         * 具体可参考demo 里面的两个自定义layout布局。
         */
        pvCustomOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                couponId = usableCouponList.get(options1).getId();
                //判断金额格式，小数点后为"00"，直接取整
                String condition = usableCouponList.get(options1).getCondition();
                String money = usableCouponList.get(options1).getMoney();
                String[] cSplit = condition.split("[.]");
                String[] mSplit = money.split("[.]");
                String conditionStr="";
                String moneyStr= "";
                if (cSplit[1].equals("00")){
                    conditionStr=cSplit[0];
                }else {
                    conditionStr=condition;
                }
                if (mSplit[1].equals("00")){
                    moneyStr=mSplit[0];
                }else {
                    moneyStr=money;
                }
                tvUsableDiscounts.setText("满"+conditionStr+"元减"+moneyStr+"元");
                //优惠券金额
                couponMoney = Double.parseDouble(moneyStr);
                if (isUseBalance){//使用余额
                    if (balanceDou > payMoney){//余额>待支付金额,待支付金额=0
                        payMoney = 0;
                    }else {//否则,待支付金额=雇佣总金额-余额-优惠券
                        payMoney = hireMoney-balanceDou-couponMoney;
                    }
                }else {//不使用余额,待支付金额=雇佣总金额-优惠券
                    payMoney = hireMoney-couponMoney;
                }
                tvPayMoney.setText("¥" +getPayMoneyStr(payMoney));
            }
        })
                .setLayoutRes(R.layout.widget_custom_pickerview, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        TextView tvCancel = (TextView) v.findViewById(R.id.tv_cancel);
                        TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        TextView tvTitle = (TextView) v.findViewById(R.id.tv_title);
                        tvCancel.setText("不使用");
                        tvSubmit.setText("使用");
                        tvTitle.setText("优惠选择");
                        tvCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {//不使用优惠券
                                couponId="0";
                                couponMoney=0;
                                tvUsableDiscounts.setText("不使用优惠券");
                                if (isUseBalance){//使用余额
                                    if (balanceDou > payMoney){//余额>待支付金额,待支付金额=0
                                        payMoney = 0;
                                    }else {//否则,待支付金额=雇佣总金额-余额
                                        payMoney = hireMoney-balanceDou;
                                    }
                                }else {//不使用余额,待支付金额=雇佣总金额
                                    payMoney = hireMoney;
                                }
                                tvPayMoney.setText("¥" +getPayMoneyStr(payMoney));
                                pvCustomOptions.dismiss();
                            }
                        });
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.returnData();
                                pvCustomOptions.dismiss();
                            }
                        });
                    }
                })
                .isDialog(false)
                .setContentTextSize(14)
                .setTextColorCenter(OrdersInfoActivity.this.getResources().getColor(R.color.color_fa716f))
                .build();
        pvCustomOptions.setPicker(optionsItemStrs);//添加数据
        pvCustomOptions.show();
    }

    //保留两位小数点
    private String getPayMoneyStr(double money){
        DecimalFormat df = new DecimalFormat("0.00");
        String moneyStr = df.format(money);
        return moneyStr;
    }


    /**
     * IOrdersView
     */
    @Override
    public AccountInfoData getUserInfo() {
        AccountInfoData userInfo = LoginUtil.getinit().getUserInfo();
        return userInfo;
    }
    @Override
    public String getCouponId() {
        return couponId;
    }
    @Override
    public String getUsableBalance() {
        if (isUseBalance){
            return balanceStr;
        }else {
            return "0";
        }
    }
    @Override
    public void onCancelOrdersResult(boolean isSuccess, String result) {
        ToastUtil.myToast(result);
        if (isSuccess){
            finish();
        }
    }
    @Override
    public void onRequestSuccess(CouponBalanceInfoData tData) {
        tvSureOrders.setEnabled(true);
        Intent intent = new Intent(this, TrusteeshipSalaryActivity.class);
        intent.putExtra(ConstantUtil.KEY_DATA,hireInfoData.getHire_id());
        intent.putExtra(ConstantUtil.KEY_POSITION,ConstantUtil.ACTIVITY_QRDD);
        startActivity(intent);
    }
    @Override
    public void onRequestFailure(String result) {
        tvSureOrders.setEnabled(true);
        ToastUtil.myToast(result);
    }


    /**
     * 系统返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (customDialog==null) {
                showCancelOrdersDialog();
            } else {
                customDialog.dismiss();
                customDialog=null;
            }
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pvCustomOptions!=null){
            pvCustomOptions.dismiss();
        }

        if (customDialog!=null){
            customDialog.dismiss();
            customDialog=null;
        }
        ToastUtil.cancleMyToast();
    }
}
