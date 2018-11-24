package com.wktx.www.emperor.ui.activity.mine.security;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.mine.authent.AuthentAlipayInfoData;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.mine.authent.AuthentAlipayPresenter;
import com.wktx.www.emperor.ui.activity.MainActivity;
import com.wktx.www.emperor.ui.view.mine.authent.IAuthentAlipayView;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 账户安全---支付宝认证
 */
public class AuthentAlipayActivity extends ABaseActivity<IAuthentAlipayView,AuthentAlipayPresenter> implements IAuthentAlipayView {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;

    //未通过原因布局
    @BindView(R.id.linear_cause)
    LinearLayout llCause;
    @BindView(R.id.tv_cause)
    TextView tvCause;

    @BindView(R.id.et_alipayNum)
    EditText etNum;
    @BindView(R.id.et_alipayName)
    EditText etName;
    @BindView(R.id.bt_sure)
    Button btSure;//提交认证
    private String alipayAuthentId;

    @OnClick({R.id.tb_IvReturn,R.id.bt_sure})
    public void MyOnclick(View view) {
        //将输入法隐藏
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tvTitle.getWindowToken(), 0);

        switch (view.getId()) {
            case R.id.tb_IvReturn:
                finish();
                break;
            case R.id.bt_sure://认证
                if (MyUtils.isFastClick()){
                    return;
                }
                //判断输入框格式
                if (TextUtils.isEmpty(getAlipayNum())){
                    ToastUtil.myToast("请输入支付宝账户！");
                    etNum.requestFocus();
                }else if (TextUtils.isEmpty(getAlipayName())){
                    ToastUtil.myToast("请输入支付宝真实姓名！");
                    etName.requestFocus();
                }else {//认证
                    btSure.setEnabled(false);
                    getPresenter().onCertification(alipayAuthentId);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authent_alipay);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_authent_alipay);
        initData();
    }

    @Override
    protected AuthentAlipayPresenter createPresenter() {
        return new AuthentAlipayPresenter();
    }

    /**
     * 接收 账户安全 传递的  账户认证类型（个人、店铺）&支付宝认证id
     */
    private void initData() {
        String authentType = getIntent().getStringExtra(ConstantUtil.KEY_ISOK);
        alipayAuthentId = getIntent().getStringExtra(ConstantUtil.KEY_DATA);
        if (alipayAuthentId.equals("0")){
            btSure.setText("提交认证");
            llCause.setVisibility(View.GONE);
        }else {
            llCause.setVisibility(View.VISIBLE);
            btSure.setText("重新认证");
            getPresenter().getCertificationInfo(alipayAuthentId);
        }
    }


    /**
     * IAuthentAlipayView
     */
    @Override
    public String getAlipayNum() {
        return etNum.getText().toString().trim();
    }
    @Override
    public String getAlipayName() {
        return etName.getText().toString().trim();
    }
    @Override//获取支付宝认证详情回调
    public void onGetAuthentInfoSuccessResult(AuthentAlipayInfoData result) {
        etNum.setText(result.getAlipay());
        etName.setText(result.getName());
        tvCause.setText(result.getErr_remark());
    }
    @Override
    public void onGetAuthentInfoFailureResult(String result) {
        ToastUtil.myToast(result);
        finish();
    }
    @Override
    public void onRequestSuccess(String tData) {
        ToastUtil.myToast("提交成功！请等待审核...");
        //回到首页
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onRequestFailure(String result) {
        btSure.setEnabled(true);
        ToastUtil.myToast(result);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtil.cancleMyToast();
    }
}
