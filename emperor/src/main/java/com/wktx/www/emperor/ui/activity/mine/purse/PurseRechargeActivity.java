package com.wktx.www.emperor.ui.activity.mine.purse;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.r0adkll.slidr.Slidr;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wktx.www.emperor.ui.activity.recruit.hire.PayResultActivity;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.login.AccountInfoData;
import com.wktx.www.emperor.apiresult.mine.pay.WechatPayInfoData;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.payutil.IPayView;
import com.wktx.www.emperor.payutil.alipay.AuthResult;
import com.wktx.www.emperor.payutil.alipay.PayResult;
import com.wktx.www.emperor.presenter.mine.purse.PurseRechargePresenter;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.wktx.www.emperor.utils.LoginUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.view.mine.purse.IPurseRechargeView;
import com.wktx.www.emperor.wxapi.WXPayEntryActivity;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 我的钱包---充值界面
 */
public class PurseRechargeActivity extends ABaseActivity<IPurseRechargeView,PurseRechargePresenter> implements IPurseRechargeView, IPayView {

    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.et_recharge)
    EditText etRecharge;
    @BindView(R.id.iv_selectedAlipay)
    ImageView ivAlipay;
    @BindView(R.id.iv_selectedWechat)
    ImageView ivWechat;
    @BindView(R.id.bt_recharge)
    Button btRecharge;

    private boolean isSelectWechat = false;
    private IWXAPI wxApi;//微信支付

    @OnClick({R.id.tb_IvReturn,R.id.rela_alipay,R.id.rela_wechat,R.id.bt_recharge})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn:
                finish();
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
            case R.id.bt_recharge://充值
                if (MyUtils.isFastClick()){
                    return;
                }

                //判断输入框格式
                if (TextUtils.isEmpty(getMoneyStr())){
                    MyUtils.showToast(PurseRechargeActivity.this,"请输入充值金额！");
                    etRecharge.requestFocus();
                }else {//充值
                    btRecharge.setEnabled(false);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purse_recharge);
        // 设置右滑动返回
        Slidr.attach(this);
        ButterKnife.bind(this);
        tvTitle.setText(R.string.title_purse_recharge);
        //充值金额输入框（小数点限制两位数）
        setEtListener(etRecharge);
        //初始化默认支付宝支付
        ivAlipay.setSelected(!isSelectWechat);
        ivWechat.setSelected(isSelectWechat);
        initWxPay();
    }

    @Override
    protected PurseRechargePresenter createPresenter() {
        return new PurseRechargePresenter();
    }

    //输入框的监听事件
    private void setEtListener(EditText et) {
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
                        etRecharge.setText(s);
                        etRecharge.setSelection(s.length());
                    }
                }

                //判断字符串的第一位是小数点，则在小数点前面加个0
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    etRecharge.setText(s);
                    etRecharge.setSelection(2);
                }

                //判断字符串第一位是0
                if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                    //如果第二位不是小数点，限制不能输入
                    if (!s.toString().substring(1, 2).equals(".")) {
                        etRecharge.setText(s.subSequence(0, 1));
                        etRecharge.setSelection(1);
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
     * 初始化微信支付事件
     */
    private void initWxPay() {
        wxApi = WXAPIFactory.createWXAPI(this, ConstantUtil.WX_APP_ID);//向微信注册您的APPID
        new WXPayEntryActivity().setWxPayListener(this);//微信支付状态监听事件
    }

    /**
     * IPurseRechargeView
     */
    @Override
    public AccountInfoData getUserInfo() {
        AccountInfoData userInfo = LoginUtil.getinit().getUserInfo();
        return userInfo;
    }
    @Override
    public String getMoneyStr() {
        return etRecharge.getText().toString().trim();
    }
    @Override//支付宝支付
    public void onAlipaySuccessResult(boolean isSuccess, final String msg) {
        if (isSuccess){
            Runnable payRunnable = new Runnable() {
                @Override
                public void run() {
                    PayTask alipay = new PayTask(PurseRechargeActivity.this);
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
            btRecharge.setEnabled(true);
            MyUtils.showToast(PurseRechargeActivity.this,msg);
        }
    }
    @Override//微信支付
    public void onRequestSuccess(WechatPayInfoData tData) {
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
    public void onRequestFailure(String result) {
        btRecharge.setEnabled(true);
        MyUtils.showToast(PurseRechargeActivity.this,result);
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
        intent.putExtra(ConstantUtil.KEY_POSITION,ConstantUtil.ACTIVITY_QBCZ);
        intent.putExtra(ConstantUtil.KEY_ISOK,isOk);
        startActivity(intent);
        btRecharge.setEnabled(true);
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
                        MyUtils.showToast(PurseRechargeActivity.this, "支付成功");
                        alipaySuccess();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        if (TextUtils.equals(resultStatus, "6001")){
                            MyUtils.showToast(PurseRechargeActivity.this, "取消支付");
                        }else {
                            MyUtils.showToast(PurseRechargeActivity.this, "支付失败");
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
                        MyUtils.showToast(PurseRechargeActivity.this, "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()));
                    } else {
                        // 其他状态值则为授权失败
                        MyUtils.showToast(PurseRechargeActivity.this, "授权失败" + String.format("authCode:%s", authResult.getAuthCode()));
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //终止消息队列，防止内存泄漏
        if (mHandler!=null){
            mHandler.removeCallbacksAndMessages(null);
        }
    }
}
