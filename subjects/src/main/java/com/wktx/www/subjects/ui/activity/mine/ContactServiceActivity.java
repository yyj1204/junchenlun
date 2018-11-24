package com.wktx.www.subjects.ui.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.mine.service.ServiceInfoData;
import com.wktx.www.subjects.basemvp.ABaseActivity;
import com.wktx.www.subjects.presenter.mine.ContactServicePresenter;
import com.wktx.www.subjects.ui.activity.login.LoginActivity;
import com.wktx.www.subjects.ui.view.mine.IContactServiceView;
import com.wktx.www.subjects.utils.MyUtils;
import com.wktx.www.subjects.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.wktx.www.subjects.utils.MyUtils.checkQQApkExist;

/**
 * 个人中心---联系客服
 */
public class ContactServiceActivity extends ABaseActivity<IContactServiceView,ContactServicePresenter> implements IContactServiceView{
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
    @BindView(R.id.et_messageContent)
    EditText etMessageContent;
    @BindView(R.id.et_contactWay)
    EditText etContactWay;
    @BindView(R.id.bt_sure)
    Button btSure;

    private String qqNumber="";
    private String phoneNumber="";


    @OnClick({R.id.tb_IvReturn,R.id.linear_QQ,R.id.linear_phone,R.id.bt_sure})
    public void MyOnclick(View view) {
        if (MyUtils.isFastClick()) {
            return;
        }
        //将输入法隐藏
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tvTitle.getWindowToken(), 0);
        switch (view.getId()) {
            case R.id.tb_IvReturn:
                finish();
                break;
            case R.id.linear_QQ:
                if (TextUtils.isEmpty(qqNumber)){
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
                if (TextUtils.isEmpty(phoneNumber)||phoneNumber.equals("0")){
                    ToastUtil.myToast("未获取到电话客服手机号！");
                }else {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:" + phoneNumber);
                    intent.setData(data);
                    startActivity(intent);
                }
                break;
            case R.id.bt_sure:
                //未登录
                if (getUserInfo()==null){
                    startActivity(new Intent(ContactServiceActivity.this, LoginActivity.class));
                    return;
                }

                if (TextUtils.isEmpty(getMessageContent())){
                    ToastUtil.myToast("请输入留言内容！");
                    etMessageContent.requestFocus();
                }else if (TextUtils.isEmpty(getContactWay())){
                    ToastUtil.myToast("请输入联系方式！");
                    etContactWay.requestFocus();
                }else {
                    btSure.setEnabled(false);
                    getPresenter().onMessage();
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
     * IContactServiceView
     */
    @Override
    public String getMessageContent() {
        return etMessageContent.getText().toString().trim();
    }
    @Override
    public String getContactWay() {
        return etContactWay.getText().toString().trim();
    }
    @Override
    public void onMessageResult(boolean isSuccess, String msg) {
        ToastUtil.myToast(msg);
        btSure.setEnabled(true);
        if (isSuccess){
            etMessageContent.requestFocus();
            etMessageContent.setText("");
            etContactWay.setText("");
        }
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
