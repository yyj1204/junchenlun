package com.wktx.www.emperor.ui.activity.mine.purse;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 我的钱包---提现界面
 */
public class PurseWithdrawalActivity extends AutoLayoutActivity {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.et_withdrawal)
    EditText etWithdrawal;
    @BindView(R.id.tv_withdrawalMoney)
    TextView tvWithdrawalMoney;
    @BindView(R.id.tv_withdrawalAll)
    TextView tvWithdrawalAll;
    @BindView(R.id.iv_selectedAlipay)
    ImageView ivAlipay;
    @BindView(R.id.iv_selectedWechat)
    ImageView ivWechat;
    @BindView(R.id.bt_withdrawal)
    Button btWithdrawal;

    private boolean isSelectWechat = false;

    @OnClick({R.id.tb_IvReturn,R.id.tv_withdrawalAll,R.id.iv_selectedAlipay,R.id.iv_selectedWechat,R.id.rela_alipay,R.id.rela_wechat,R.id.bt_withdrawal})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn:
                finish();
                break;
            case R.id.tv_withdrawalAll://全部提现
                break;
            //支付宝支付
            case R.id.iv_selectedAlipay:
            case R.id.rela_alipay:
                isSelectWechat = false;
                ivAlipay.setSelected(!isSelectWechat);
                ivWechat.setSelected(isSelectWechat);
                break;
            //微信支付
            case R.id.iv_selectedWechat:
            case R.id.rela_wechat:
                isSelectWechat = true;
                ivAlipay.setSelected(!isSelectWechat);
                ivWechat.setSelected(isSelectWechat);
                break;
            case R.id.bt_withdrawal://提现
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purse_withdrawal);
        // 设置右滑动返回
        Slidr.attach(this);
        ButterKnife.bind(this);
        tvTitle.setText(R.string.title_purse_withdrawal);
    }
}
