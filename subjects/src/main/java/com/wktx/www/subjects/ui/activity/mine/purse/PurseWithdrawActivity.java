package com.wktx.www.subjects.ui.activity.mine.purse;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.mine.center.CenterInfoData;
import com.wktx.www.subjects.basemvp.ABaseActivity;
import com.wktx.www.subjects.presenter.mine.purse.PurseWithdrawPresenter;
import com.wktx.www.subjects.ui.activity.MainActivity;
import com.wktx.www.subjects.ui.view.mine.purse.IPurseWithdrawView;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.MyUtils;
import com.wktx.www.subjects.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的钱包---提现界面
 */
public class PurseWithdrawActivity extends ABaseActivity<IPurseWithdrawView,PurseWithdrawPresenter> implements IPurseWithdrawView {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;

    //提现成功
    @BindView(R.id.linear_withdraw_success)
    LinearLayout linearSuccess;

    //提现布局
    @BindView(R.id.linear_withdraw)
    LinearLayout linearWithdraw;
    @BindView(R.id.tv_getcode)
    TextView tvGetCode;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.et_payPwd)
    EditText etPayPwd;
    @BindView(R.id.et_alipayNume)
    EditText etAlipayNume;
    @BindView(R.id.et_idNum)
    EditText etIdNum;
    @BindView(R.id.et_withdrawal)
    EditText etWithdrawal;
    @BindView(R.id.tv_withdrawalMoney)
    TextView tvWithdrawalMoney;
    @BindView(R.id.tv_withdrawalAll)
    TextView tvWithdrawalAll;
    @BindView(R.id.bt_withdrawal)
    Button btWithdrawal;

    private String userMoney;//用户余额

    private int time = 60;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                tvGetCode.setText(time+"秒后重新获取");
            } else if (msg.what == 1) {
                tvGetCode.setText("重新获取验证码");
                tvGetCode.setEnabled(true);
                time = 60;
            }
        }
    };

    @OnClick({R.id.tb_IvReturn,R.id.tv_getcode,R.id.tv_withdrawalAll,R.id.bt_withdrawal,R.id.bt_sure})
    public void MyOnclick(View view) {
        //将输入法隐藏
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tvTitle.getWindowToken(), 0);
        switch (view.getId()) {
            case R.id.tb_IvReturn:
                finish();
                break;
            case R.id.tv_getcode://获取验证码
                if (MyUtils.isFastClick()){
                    return;
                }
                //获取验证码
                getPresenter().onGetCode();
                break;
            case R.id.tv_withdrawalAll://全部提现
                etWithdrawal.setText(userMoney);
                break;
            case R.id.bt_withdrawal://提现
                if (MyUtils.isFastClick()){
                    return;
                }
                //判断输入框格式
                if (TextUtils.isEmpty(getCodeStr())){
                    ToastUtil.myToast("请输入验证码！");
                    etCode.requestFocus();
                }else if (TextUtils.isEmpty(getPayPwdStr())){
                    ToastUtil.myToast("请输入支付密码！");
                    etPayPwd.requestFocus();
                }else if (getPayPwdStr().length()!=6){
                    ToastUtil.myToast("支付密码输入不正确！");
                    etPayPwd.requestFocus();
                }else if (TextUtils.isEmpty(getAlipayNumStr())){
                    ToastUtil.myToast("请输入认证的支付宝账号！");
                    etAlipayNume.requestFocus();
                }else if (TextUtils.isEmpty(getIdNumStr())){
                    ToastUtil.myToast("请输入认证的身份证号!");
                    etIdNum.requestFocus();
                }else if (TextUtils.isEmpty(getWithdrawalMoney())) {
                    ToastUtil.myToast("请输入提现金额！");
                    etWithdrawal.requestFocus();
                }else if (Double.parseDouble(getWithdrawalMoney())>Double.parseDouble(userMoney)){//判断提现金额与当前余额
                    ToastUtil.myToast("提现金额超过当前余额！");
                    etWithdrawal.requestFocus();
                }else if (Double.parseDouble(getWithdrawalMoney())<10){
                    ToastUtil.myToast("提现金额不得小于10元！");
                    etWithdrawal.requestFocus();
                }else {//提现
                    btWithdrawal.setEnabled(false);
                    getPresenter().onWithdraw();
                }
                break;
            case R.id.bt_sure://提现成功-确定按钮
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purse_withdraw);
        // 设置右滑动返回
        Slidr.attach(this);
        ButterKnife.bind(this);
        tvTitle.setText(R.string.title_purse_withdraw);
        tvPhone.setText(MyUtils.setPhone(getUserInfo().getPhone()));
        //提现金额输入框（小数点限制两位数）
        setEtListener(etWithdrawal);
        initData();
    }

    @Override
    protected PurseWithdrawPresenter createPresenter() {
        return new PurseWithdrawPresenter();
    }

    /**
     * 接收 MainFragment 传递的个人中心信息
     */
    private void initData() {
        CenterInfoData centerInfoData = (CenterInfoData) getIntent().getSerializableExtra(ConstantUtil.KEY_DATA);
        userMoney = centerInfoData.getUserinfo().getUser_money();
        tvWithdrawalMoney.setText("余额￥"+ userMoney +" ");
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
     * IPurseWithdrawView
     */
    @Override
    public String getPhoneStr() {
        return getUserInfo().getPhone();
    }
    @Override
    public String getCodeStr() {
        return etCode.getText().toString().trim();
    }
    @Override
    public String getPayPwdStr() {
        return etPayPwd.getText().toString().trim();
    }
    @Override
    public String getAlipayNumStr() {
        return etAlipayNume.getText().toString().trim();
    }
    @Override
    public String getIdNumStr() {
        return etIdNum.getText().toString().trim();
    }
    @Override
    public String getWithdrawalMoney() {
        return etWithdrawal.getText().toString().trim();
    }
    @Override
    public void onSendCodeResult(boolean isSuccess, String msg) {
        ToastUtil.myToast( msg);
        if (isSuccess){
            tvGetCode.setEnabled(false);
            tvGetCode.setText(time+"秒后重新获取");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (; time > 0; time--) {
                        handler.sendEmptyMessage(0);
                        if (time <= 0) {
                            break;
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    handler.sendEmptyMessage(1);
                }
            }).start();
        }
    }
    @Override
    public void onRequestSuccess(String tData) {
        ToastUtil.myToast(tData);
        linearWithdraw.setVisibility(View.GONE);
        linearSuccess.setVisibility(View.VISIBLE);
    }
    @Override
    public void onRequestFailure(String result) {
        btWithdrawal.setEnabled(true);
        ToastUtil.myToast(result);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //终止消息队列，防止内存泄漏
        if (handler!=null){
            handler.removeCallbacksAndMessages(null);
        }
        ToastUtil.cancleMyToast();
    }
}
