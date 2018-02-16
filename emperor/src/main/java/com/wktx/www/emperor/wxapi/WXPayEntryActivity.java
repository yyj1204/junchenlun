package com.wktx.www.emperor.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wktx.www.emperor.pay.IPayView;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.wktx.www.emperor.utils.MyUtils;


/**
 * Created by yyj on 2017/3/27 0027.
 * 微信支付
 */

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, ConstantUtil.WX_APP_ID);
        api.handleIntent(getIntent(), this);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    /**
     * 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
     * arg0。errCode  0成功 -1支付失败 -2取消
     */
    @Override
    public void onResp(BaseResp resp) {
        LogUtil.error("微信支付结结果码=", resp.errCode+"");

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            int code = resp.errCode;
            switch (code) {
                case 0:
                    Intent intent = new Intent();
                    intent.setAction("fbPayAction");
                    //          intent.setAction("goodsPayAction");
                    //          intent.setAction("integraPayAction");
                    sendBroadcast(intent);
                    MyUtils.showToast(getApplicationContext(), "支付成功");
                    if (listener!=null){
                        listener.wxSuccess();
                    }
                    break;
                case -1:
                    MyUtils.showToast(getApplicationContext(), "支付失败");
                    if (listener!=null){
                        listener.wxFailed();
                    }
                    break;
                case -2:
                    MyUtils.showToast(getApplicationContext(), "支付取消");
                    if (listener!=null){
                        listener.wxFailed();
                    }
                    break;
                default:
                    MyUtils.showToast(getApplicationContext(), "支付取消");
                    if (listener!=null){
                        listener.wxFailed();
                    }
                    break;
            }
            finish();
        }
    }


    //2.声明一个该接口的变量
    private static IPayView listener;

    //3.定义设置监听的方法
    public void setWxPayListener(IPayView l) {
        listener = l;
    }
}
