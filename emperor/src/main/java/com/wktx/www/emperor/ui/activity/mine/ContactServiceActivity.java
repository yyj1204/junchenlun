package com.wktx.www.emperor.ui.activity.mine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.login.AccountInfoData;
import com.wktx.www.emperor.apiresult.mine.service.ServiceInfoData;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.mine.ContactServicePresenter;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.ui.view.IView;
import com.wktx.www.emperor.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.wktx.www.emperor.utils.MyUtils.checkQQApkExist;
/**
 * 个人中心---联系客服
 */
public class ContactServiceActivity extends ABaseActivity<IView,ContactServicePresenter> implements IView<ServiceInfoData> {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.tv_QQNumber)
    TextView tvQQNumber;
    @BindView(R.id.tv_serviceTime1)
    TextView tvServiceTime1;
    @BindView(R.id.tv_phoneNumber)
    TextView tvPhoneNumber;
    @BindView(R.id.tv_serviceTime2)
    TextView tvServiceTime2;

    private String qqNumber="";
    private String phoneNumber="";

    @OnClick({R.id.tb_IvReturn,R.id.linear_QQ,R.id.linear_phone})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn:
                finish();
                break;
            case R.id.linear_QQ:
                if (MyUtils.isFastClick()) {
                    return;
                }
                if (qqNumber.equals("")){
                    ToastUtil.myToast("未获取到在线客服QQ号！");
                }else {
                    if (checkQQApkExist(this, "com.tencent.mobileqq")){
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin="+qqNumber+"&version=1")));
                    }else{
                        ToastUtil.myToast("本机未安装QQ应用！");
                    }
                }
                break;
            case R.id.linear_phone:
                if (MyUtils.isFastClick()) {
                    return;
                }
                if (phoneNumber.equals("")||phoneNumber.equals("0")){
                    ToastUtil.myToast("未获取到电话客服手机号！");
                }else {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:" + phoneNumber);
                    intent.setData(data);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_service);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_contact_service);
        initData();
    }

    @Override
    protected ContactServicePresenter createPresenter() {
        return new ContactServicePresenter();
    }

    private void initData() {
        getPresenter().onGetServiceInfo();
    }

    /**
     * IView
     */
    @Override
    public AccountInfoData getUserInfo() {
        return null;
    }
    @Override
    public void onRequestSuccess(ServiceInfoData tData) {
        qqNumber = tData.getQq();
        phoneNumber = tData.getPhone();
        tvQQNumber.setText("QQ:"+ qqNumber);
        tvServiceTime1.setText("服务时间:"+tData.getService_time());
        tvPhoneNumber.setText(phoneNumber);
        tvServiceTime2.setText("服务时间:"+tData.getService_time());
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
