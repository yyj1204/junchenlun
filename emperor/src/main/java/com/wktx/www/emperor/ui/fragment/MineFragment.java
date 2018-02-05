package com.wktx.www.emperor.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wktx.www.emperor.apiresult.mine.UserInfoData;
import com.wktx.www.emperor.ui.activity.mine.CompanyInfoActivity;
import com.wktx.www.emperor.Activity.PacketActivity;
import com.wktx.www.emperor.Activity.ServiceActivity;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.login.AccountInfoData;
import com.wktx.www.emperor.basemvp.ABaseFragment;
import com.wktx.www.emperor.presenter.mine.MinePresenter;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.Activity.AboutUsActivity;
import com.wktx.www.emperor.Activity.CheckAccountActivity;
import com.wktx.www.emperor.ui.activity.recruit.BrowsingHistoryActivity;
import com.wktx.www.emperor.Activity.EmployActivity;
import com.wktx.www.emperor.Activity.FavorActivity;
import com.wktx.www.emperor.Activity.InterviewActivity;
import com.wktx.www.emperor.ui.activity.login.LoginActivity;
import com.wktx.www.emperor.Activity.MessageActivity;
import com.wktx.www.emperor.Activity.PayPswctivity;
import com.wktx.www.emperor.Activity.PurseActivity;
import com.wktx.www.emperor.ui.activity.login.RegisterActivity;
import com.wktx.www.emperor.Activity.StoreInfoActivity;
import com.wktx.www.emperor.Activity.TrasactActivity;
import com.wktx.www.emperor.utils.LoginUtil;
import com.wktx.www.emperor.view.mine.IMineView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的账户片段
 */
public class MineFragment extends ABaseFragment<IMineView,MinePresenter> implements IMineView{
    @BindView(R.id.civ_head)
    ImageView ivHead;
    @BindView(R.id.tv_userName)
    TextView tvUserName;
    @BindView(R.id.linear_account)
    LinearLayout llAccount;//已登录布局
    @BindView(R.id.linear_login)
    LinearLayout llLogin;//未登录布局
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.tv_usableBalance)
    TextView tvUsableBalance;

    private boolean isLogin;


    @OnClick({R.id.iv_message, R.id.tv_browsing, R.id.civ_head,R.id.tv_userName,R.id.linear_certification,
            R.id.tv_login,R.id.tv_register,R.id.linear_balance,R.id.linear_usableBalance,
            R.id.linear_storeMeassage,R.id.linear_myCollect,R.id.linear_tradeRecord,
            R.id.linear_interviewRecord,R.id.linear_employRecord,R.id.linear_myRedpacket,
            R.id.linear_aboutApp, R.id.linear_payPwd, R.id.linear_contactService})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.iv_message://消息通知
                startActivity(new Intent(getActivity(), MessageActivity.class));
                break;
            case R.id.tv_browsing://浏览记录
                startActivity(new Intent(getActivity(), BrowsingHistoryActivity.class));
                break;
            case R.id.civ_head://用户头像
            case R.id.tv_userName://用户名
                if (getUserInfo()!=null){//已登录
                    Intent intent = new Intent();
//                intent.putExtra(Contants.INTENT_IDTOKEN, info);
                    intent.setClass(getActivity(), CompanyInfoActivity.class);
                    startActivityForResult(intent, 0);
                }else {//未登录
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), ConstantUtil.REQUESTCODE_LOGIN);
                }
                break;
            case R.id.linear_certification://申请认证
                startActivity(new Intent(getActivity(), CheckAccountActivity.class));
                break;
            case R.id.tv_login://登录
                startActivityForResult(new Intent(getActivity(), LoginActivity.class), ConstantUtil.REQUESTCODE_LOGIN);
                break;
            case R.id.tv_register://注册
                startActivity(new Intent(getActivity(), RegisterActivity.class));
                break;
            case R.id.linear_balance://余额
            case R.id.linear_usableBalance://可用余额
                startActivity(new Intent(getActivity(), PurseActivity.class));
                break;
            case R.id.linear_storeMeassage://店铺信息
                startActivity(new Intent(getActivity(), StoreInfoActivity.class));
                break;
            case R.id.linear_myCollect://我的收藏
                startActivity(new Intent(getActivity(), FavorActivity.class));
                break;
            case R.id.linear_tradeRecord://交易记录
                startActivity(new Intent(getActivity(), TrasactActivity.class));
                break;
            case R.id.linear_interviewRecord://面试记录
                startActivity(new Intent(getActivity(), InterviewActivity.class));
                break;
            case R.id.linear_employRecord://雇佣记录
                startActivity(new Intent(getActivity(), EmployActivity.class));
                break;
            case R.id.linear_myRedpacket://我的红包
                startActivity(new Intent(getActivity(), PacketActivity.class));
                break;
            case R.id.linear_aboutApp://关于君臣论
                startActivity(new Intent(getActivity(), AboutUsActivity.class));
                break;
            case R.id.linear_payPwd://支付密码
                startActivity(new Intent(getActivity(), PayPswctivity.class));
                break;
            case R.id.linear_contactService://联系客服
                startActivity(new Intent(getActivity(), ServiceActivity.class));
                break;
            default:
                break;
        }
    }

    public MineFragment() {
    }

    public static MineFragment newInstance(String info) {
        Bundle args = new Bundle();
        MineFragment fragment = new MineFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, view);
        initUI();
        return view;
    }

    private void initUI() {
        if (getUserInfo()!=null){//已登录
            llAccount.setVisibility(View.VISIBLE);
            llLogin.setVisibility(View.GONE);
        }else {//未登录
            llAccount.setVisibility(View.GONE);
            llLogin.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected MinePresenter createPresenter() {
        return new MinePresenter();
    }

    /**
     * IMineView
     */
    @Override
    public AccountInfoData getUserInfo() {
        AccountInfoData userInfo = LoginUtil.getinit().getUserInfo();
        return userInfo;
    }
    @Override
    public void onRequestSuccess(UserInfoData tData) {

    }
    @Override
    public void onRequestFailure(String result) {

    }

    //根据登录状态获取用户信息，更新界面
    @Override
    public void onResume() {
        super.onResume();
        initUI();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case ConstantUtil.REQUESTCODE_LOGIN:
                    llAccount.setVisibility(View.VISIBLE);
                    llLogin.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
            switch (resultCode) {
                case ConstantUtil.RESULT_OUTLOGINCODE:
                    tvUserName.setText("登录/注册");
                    ivHead.setImageResource(R.drawable.mine_head);
                    llLogin.setVisibility(View.VISIBLE);
                    llAccount.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }
    }

}
