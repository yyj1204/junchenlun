package com.wktx.www.emperor.ui.activity.recruit.hire;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.r0adkll.slidr.Slidr;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.login.AccountInfoData;
import com.wktx.www.emperor.apiresult.mine.pay.WechatPayInfoData;
import com.wktx.www.emperor.apiresult.recruit.hire.TrusteeshipSalaryInfoData;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.payutil.IPayView;
import com.wktx.www.emperor.payutil.alipay.AuthResult;
import com.wktx.www.emperor.payutil.alipay.PayResult;
import com.wktx.www.emperor.presenter.recruit.hire.TrusteeshipSalaryPresenter;
import com.wktx.www.emperor.ui.activity.main.MainActivity;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.DateUtil;
import com.wktx.www.emperor.utils.GlideUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.wktx.www.emperor.utils.LoginUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.ui.view.recruit.hire.ITrusteeshipSalaryView;
import com.wktx.www.emperor.utils.ToastUtil;
import com.wktx.www.emperor.widget.CustomDialog;
import com.wktx.www.emperor.wxapi.WXPayEntryActivity;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 简历---雇佣---确认订单信息---托管工资
 */
public class TrusteeshipSalaryActivity extends ABaseActivity<ITrusteeshipSalaryView,TrusteeshipSalaryPresenter> implements ITrusteeshipSalaryView,IPayView {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    //员工基本信息
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_staffName)
    TextView tvStaffName;
    @BindView(R.id.tv_workYears)
    TextView tvWorkYears;
    @BindView(R.id.tv_category)
    TextView tvCategory;
    @BindView(R.id.tv_hireType)
    TextView tvHireType;

    @BindView(R.id.tv_hireTime)
    TextView tvHireTime;
    @BindView(R.id.tv_beginTime)
    TextView tvBeginTime;
    @BindView(R.id.tv_endTime)
    TextView tvEndTime;
    //相关金额
    @BindView(R.id.tv_hireMoney)
    TextView tvHireMoney;
    @BindView(R.id.linear_balanceMoney)
    LinearLayout llBalanceMoney;
    @BindView(R.id.tv_balanceMoney)
    TextView tvBalanceMoney;
    @BindView(R.id.linear_couponMoney)
    LinearLayout llCouponMoney;
    @BindView(R.id.tv_couponMoney)
    TextView tvCouponMoney;
    @BindView(R.id.tv_payMoney)
    TextView tvPayMoney;

    //支付布局
    @BindView(R.id.linear_payWay)
    LinearLayout llPayWay;
    @BindView(R.id.iv_selectedAlipay)
    ImageView ivAlipay;
    @BindView(R.id.iv_selectedWechat)
    ImageView ivWechat;
    @BindView(R.id.bt_surePay)
    Button btSurePay;

    private String hireId;//雇佣订单id
    private String isActivity;//是哪个界面进来的

    private boolean isBalancePay;//是否余额全款支付
    private boolean isSelectWechat = false;//是否选择微信支付
    private IWXAPI wxApi;//微信支付
    private CustomDialog customDialog;
    private String balanceMoney;//使用余额支付金额
    private String balancePayPwd="";//余额支付密码（MD5加密过的）


    @OnClick({R.id.tb_IvReturn,R.id.rela_alipay,R.id.rela_wechat,R.id.bt_surePay})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn://返回
                showCancelOrdersDialog();
                break;
            //支付宝支付
            case R.id.rela_alipay:
                isSelectWechat = false;
                ivAlipay.setSelected(!isSelectWechat);
                ivWechat.setSelected(isSelectWechat);
                break;
            //微信支付
            case R.id.rela_wechat:
                isSelectWechat = true;
                ivAlipay.setSelected(!isSelectWechat);
                ivWechat.setSelected(isSelectWechat);
                break;
            case R.id.bt_surePay://确认支付
                if (MyUtils.isFastClick()){
                    return;
                }

                btSurePay.setEnabled(false);
                if (isBalancePay){//余额支付
                    //打开输入支付密码界面
                    Intent intent = new Intent(TrusteeshipSalaryActivity.this, InputPayPwdActivity.class);
                    intent.putExtra(ConstantUtil.KEY_DATA,balanceMoney);
                    startActivityForResult(intent,ConstantUtil.REQUESTCODE_BALANCEPAY);
                }else {
                    if (isSelectWechat){//微信支付
                        getPresenter().getWeChatPayInfo();
                    }else {//支付宝支付
                        getPresenter().getAliPayInfo();
                    }
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
                startMainActivity();
            }
        });

        builder.setNegativeButton("确认取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        getPresenter().onCancelOrders();
                    }
                });

        customDialog = builder.create();
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trusteeship_salary);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_trusteeship_salary);
        initData();
        btSurePay.setEnabled(false);
        initWxPay();
    }

    @Override
    protected TrusteeshipSalaryPresenter createPresenter() {
        return new TrusteeshipSalaryPresenter();
    }

    private void initData() {
        //接收 ResumeActivity OrdersInfoActivity HireRecordActivity 传递过来的雇佣订单id
        hireId = getIntent().getStringExtra(ConstantUtil.KEY_DATA);
        isActivity = getIntent().getStringExtra(ConstantUtil.KEY_POSITION);
        getPresenter().getHireOrdersInfo();
    }
    /**
     * 初始化微信支付事件
     */
    private void initWxPay() {
        wxApi = WXAPIFactory.createWXAPI(this, ConstantUtil.WX_APP_ID);//向微信注册您的APPID
        new WXPayEntryActivity().setWxPayListener(this);//微信支付状态监听事件
    }

    /**
     * ITrusteeshipSalaryView
     */
    @Override
    public AccountInfoData getUserInfo() {
        AccountInfoData userInfo = LoginUtil.getinit().getUserInfo();
        return userInfo;
    }
    @Override
    public String getHireId() {
        return hireId;
    }
    @Override
    public String getPayPwd() {
        return balancePayPwd;
    }
    @Override
    public void onCancelOrdersResult(boolean isSuccess, String result) {
        ToastUtil.myToast(result);
        if (isSuccess){
            startMainActivity();
        }
    }
    @Override
    public void onBanlancePayResult(boolean isSuccess, String result) {
        if (isSuccess){
            startPayResultActivity(true);
        }else {
            btSurePay.setEnabled(true);
            ToastUtil.myToast(result);
        }
    }
    @Override
    public void onAlipayResult(boolean isSuccess, final String msg) {
        if (isSuccess){
            Runnable payRunnable = new Runnable() {
                @Override
                public void run() {
                    PayTask alipay = new PayTask(TrusteeshipSalaryActivity.this);
                    Map<String, String> result = alipay.payV2(msg, true);
                    LogUtil.error("支付宝订单", result.toString());
                    Message msg = new Message();
                    msg.what = ConstantUtil.SDK_PAY_FLAG;
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                }
            };
            // 必须异步调用
            Thread payThread = new Thread(payRunnable);
            payThread.start();
        }else {
            btSurePay.setEnabled(true);
            ToastUtil.myToast(msg);
        }
    }
    @Override
    public void onWechatSuccessResult(WechatPayInfoData tData) {
        //微信后台下单成功--->支付
        PayReq req = new PayReq();
        req.appId = tData.getAppid();
        req.partnerId = tData.getPartnerid();
        req.prepayId= tData.getPrepayid();
        req.packageValue = tData.getPay_package();
        req.nonceStr= tData.getNoncestr();
        req.timeStamp= tData.getTimestamp();
        req.sign= tData.getSign();
        //将该app注册到微信（在支付前注册）
        wxApi.registerApp(ConstantUtil.WX_APP_ID);
        //发起支付
        wxApi.sendReq(req);
    }
    @Override
    public void onWechatFailureResult(String result) {
        btSurePay.setEnabled(true);
        ToastUtil.myToast(result);
    }
    @Override
    public void onRequestSuccess(TrusteeshipSalaryInfoData tData) {
        btSurePay.setEnabled(true);
        //初始化默认支付宝支付
        ivAlipay.setSelected(!isSelectWechat);
        ivWechat.setSelected(isSelectWechat);
        if (tData.getUser_money_pay_all()==0){//不是全款余额支付
            isBalancePay = false;
            llPayWay.setVisibility(View.VISIBLE);
            btSurePay.setText("确认支付");
        }else if (tData.getUser_money_pay_all()==1){//全款余额支付
            isBalancePay = true;
            llPayWay.setVisibility(View.GONE);
            btSurePay.setText("确认余额支付");
        }
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
        //姓名
        tvStaffName.setText(tData.getName());
        //擅长类目
        tvCategory.setText(tData.getBgat());
        //工作经验
        String working_years = tData.getWorking_years();
        if (working_years.equals("0")){
            tvWorkYears.setText("无经验");
            tvWorkYears.setVisibility(View.GONE);
        }else if (working_years.equals("1")){
            tvWorkYears.setText("一年以内");
        }else if (working_years.equals("2")){
            tvWorkYears.setText("一年");
        }else if (working_years.equals("3")){
            tvWorkYears.setText("两年");
        }else if (working_years.equals("4")){
            tvWorkYears.setText("三年");
        }else if (working_years.equals("5")){
            tvWorkYears.setText("四年");
        }else if (working_years.equals("6")){
            tvWorkYears.setText("五年");
        }else if (working_years.equals("7")){
            tvWorkYears.setText("五年以上");
        }
        //雇佣方式 1:包月,2:定制 3:雇佣单人
        String hireWay = tData.getHire_type();
        if (hireWay.equals("1")){
            tvHireType.setText("包月");
        }else if (hireWay.equals("2")){
            tvHireType.setText("定制");
        }else if (hireWay.equals("3")){
            tvHireType.setText("雇佣单人");
        }
        if (!hireWay.equals("2")){
            tvHireTime.setText(tData.getHire_time()+"个月");
            //雇佣金额(月薪、定制金额)
            tvHireMoney.setText(tData.getHire_price()+"元/月");
        }else {
            tvHireTime.setText("");
            //雇佣金额(月薪、定制金额)
            tvHireMoney.setText(tData.getHire_price()+"元");
        }
        //雇佣周期
        tvBeginTime.setText(DateUtil.getTimestamp2CustomType(tData.getProject_start_time(),"yyyy.MM.dd"));
        tvEndTime.setText(DateUtil.getTimestamp2CustomType(tData.getProject_end_time(),"yyyy.MM.dd"));
        //使用余额
        balanceMoney = tData.getUser_money();
        tvBalanceMoney.setText(balanceMoney +"元");
        if (balanceMoney.equals("0.00")){
            llBalanceMoney.setVisibility(View.GONE);
        }else {
            llBalanceMoney.setVisibility(View.VISIBLE);
        }
        //优惠券金额
        tvCouponMoney.setText(tData.getCoupon_price()+"元");
        if (tData.getCoupon_price().equals("0.00")){
            llCouponMoney.setVisibility(View.GONE);
        }else {
            llCouponMoney.setVisibility(View.VISIBLE);
        }
        //支付金额
        tvPayMoney.setText(tData.getTotal_amount()+"元");
    }
    @Override
    public void onRequestFailure(String result) {
        finish();
        btSurePay.setEnabled(false);
        ToastUtil.myToast(result);
    }

    /**
     * IPayView 四个方法
     */
    @Override
    public void alipaySuccess() {
        startPayResultActivity(true);
    }
    @Override
    public void alipayFailed() {
        startPayResultActivity(false);
    }
    @Override
    public void wxSuccess() {
        startPayResultActivity(true);
    }
    @Override
    public void wxFailed() {
        startPayResultActivity(false);
    }

    /**
     * 打开支付结果界面
     */
    private void startPayResultActivity(boolean isOk) {
        Intent intent = new Intent(this, PayResultActivity.class);
        intent.putExtra(ConstantUtil.KEY_POSITION,ConstantUtil.ACTIVITY_TGGZ);
        intent.putExtra(ConstantUtil.KEY_ISOK,isOk);
        intent.putExtra(ConstantUtil.KEY_DATA,hireId);
        startActivity(intent);
        btSurePay.setEnabled(true);
        if (isOk){
            finish();
        }
    }
    /**
     * 如果是雇佣流程进来的，则回到首页
     * 如果是雇佣记录进来的，则关闭即可
     */
    private void startMainActivity() {
        if (isActivity.equals(ConstantUtil.ACTIVITY_QRDD)){
            Intent intent = new Intent(this, MainActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        finish();
    }

    /**
     * 支付宝支付结果通知
     */
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ConstantUtil.SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        ToastUtil.myToast( "支付成功");
                        alipaySuccess();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        if (TextUtils.equals(resultStatus, "6001")){
                            ToastUtil.myToast( "取消支付");
                        }else {
                            ToastUtil.myToast( "支付失败");
                        }
                        alipayFailed();
                    }
                    break;
                }
                case ConstantUtil.SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        ToastUtil.myToast( "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()));
                    } else {
                        // 其他状态值则为授权失败
                        ToastUtil.myToast( "授权失败" + String.format("authCode:%s", authResult.getAuthCode()));
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };


    /**
     * 余额全款支付点击支付时需要的密码回调
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null){
            if (requestCode==ConstantUtil.REQUESTCODE_BALANCEPAY){
                switch (resultCode){
                    //取消密码输入
                    case ConstantUtil.RESULTCODE_BALANCEPAY_CANCELE:
                        btSurePay.setEnabled(true);
                        break;
                        //密码输入完成 --- 请求余额支付接口
                    case ConstantUtil.RESULTCODE_BALANCEPAY_OK:
                        balancePayPwd = data.getStringExtra(ConstantUtil.KEY_INFO);
                        getPresenter().onBalancePay();
                        break;
                    default:
                        break;
                }
            }
        }
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
        //终止消息队列，防止内存泄漏
        if (mHandler!=null){
            mHandler.removeCallbacksAndMessages(null);
        }
        if (customDialog!=null){
            customDialog.dismiss();
            customDialog=null;
        }

        ToastUtil.cancleMyToast();
    }
}
