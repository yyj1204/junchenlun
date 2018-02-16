package com.wktx.www.emperor.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wktx.www.emperor.apiresult.mine.center.CenterInfoData;
import com.wktx.www.emperor.apiresult.mine.center.UserInfoBean;
import com.wktx.www.emperor.ui.activity.mine.certification.AccountCertificationActivity;
import com.wktx.www.emperor.ui.activity.mine.CompanyInfoActivity;
import com.wktx.www.emperor.Activity.PacketActivity;
import com.wktx.www.emperor.Activity.ServiceActivity;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.login.AccountInfoData;
import com.wktx.www.emperor.basemvp.ABaseFragment;
import com.wktx.www.emperor.presenter.mine.MinePresenter;
import com.wktx.www.emperor.ui.activity.mine.purse.MyPurseActivity;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.Activity.AboutUsActivity;
import com.wktx.www.emperor.ui.activity.recruit.BrowsingHistoryActivity;
import com.wktx.www.emperor.Activity.EmployActivity;
import com.wktx.www.emperor.Activity.FavorActivity;
import com.wktx.www.emperor.Activity.InterviewActivity;
import com.wktx.www.emperor.ui.activity.login.LoginActivity;
import com.wktx.www.emperor.Activity.MessageActivity;
import com.wktx.www.emperor.Activity.PayPswctivity;
import com.wktx.www.emperor.ui.activity.login.RegisterActivity;
import com.wktx.www.emperor.Activity.StoreInfoActivity;
import com.wktx.www.emperor.Activity.TrasactActivity;
import com.wktx.www.emperor.utils.LoginUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.view.mine.IMineView;
import com.wktx.www.emperor.widget.LogoutPopup;

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
    @BindView(R.id.bt_logout)
    Button btLogout;//退出登录

    private LogoutPopup logoutPopup;//退出登录弹窗

    private boolean isLogin;

    @OnClick({R.id.iv_message, R.id.tv_browsing, R.id.civ_head,R.id.tv_userName,R.id.linear_certification,
            R.id.tv_login,R.id.tv_register,R.id.linear_balance,R.id.linear_usableBalance,
            R.id.linear_storeMeassage,R.id.linear_myCollect,R.id.linear_tradeRecord,
            R.id.linear_interviewRecord,R.id.linear_employRecord,R.id.linear_myRedpacket,
            R.id.linear_aboutApp, R.id.linear_payPwd, R.id.linear_contactService, R.id.bt_logout})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.iv_message://消息通知
                isLoginStartActivity(MessageActivity.class);
                break;
            case R.id.tv_browsing://浏览记录
                isLoginStartActivity(BrowsingHistoryActivity.class);
                break;
            case R.id.civ_head://用户头像
            case R.id.tv_userName://用户名
                if (isLogin){//已登录
                    Intent intent = new Intent();
//                intent.putExtra(Contants.INTENT_IDTOKEN, info);
                    intent.setClass(getActivity(), CompanyInfoActivity.class);
                    startActivityForResult(intent, 0);
                }else {//未登录
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
            case R.id.linear_certification://申请认证
                startActivity(new Intent(getActivity(), AccountCertificationActivity.class));
                break;
            case R.id.tv_login://登录
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.tv_register://注册
                startActivity(new Intent(getActivity(), RegisterActivity.class));
                break;
            case R.id.linear_balance://余额
            case R.id.linear_usableBalance://可用余额
                isLoginStartActivity(MyPurseActivity.class);
                break;
            case R.id.linear_storeMeassage://店铺信息
                isLoginStartActivity(StoreInfoActivity.class);
                break;
            case R.id.linear_myCollect://我的收藏
                isLoginStartActivity(FavorActivity.class);
                break;
            case R.id.linear_tradeRecord://交易记录
                isLoginStartActivity(TrasactActivity.class);
                break;
            case R.id.linear_interviewRecord://面试记录
                isLoginStartActivity(InterviewActivity.class);
                break;
            case R.id.linear_employRecord://雇佣记录
                isLoginStartActivity(EmployActivity.class);
                break;
            case R.id.linear_myRedpacket://我的红包
                isLoginStartActivity(PacketActivity.class);
                break;
            case R.id.linear_aboutApp://关于君臣论
                startActivity(new Intent(getActivity(), AboutUsActivity.class));
                break;
            case R.id.linear_payPwd://支付密码
                isLoginStartActivity(PayPswctivity.class);
                break;
            case R.id.linear_contactService://联系客服
                startActivity(new Intent(getActivity(), ServiceActivity.class));
                break;
            case R.id.bt_logout://退出登录
                showLogoutPopup();
                break;
            default:
                break;
        }
    }

    /**
     * 登录了才能打开对应的界面
     */
    private void isLoginStartActivity(Class<?> clazz) {
        if (isLogin){
            startActivity(new Intent(getActivity(), clazz));
        }else {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }

    /**
     * 退出登录弹窗
     */
    private void showLogoutPopup() {
        logoutPopup = new LogoutPopup(getActivity(), getActivity());
        logoutPopup.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        logoutPopup.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        logoutPopup.setClippingEnabled(false);
        logoutPopup.showPopupWindow(ivHead);
        logoutPopup.setOnGetTypeClckListener(new LogoutPopup.onGetTypeClckListener() {
            @Override
            public void getText(String sure) {
                switch (sure) {
                    case ConstantUtil.LOGOUT:
                        getPresenter().onLogout();
                        break;
                    default:
                        break;
                }
            }
        });
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    //根据登录状态获取用户信息，更新界面
    @Override
    public void onResume() {
        super.onResume();
        initUI();
    }

    private void initUI() {
        if (getUserInfo()!=null){//已登录
            isLogin=true;
            llAccount.setVisibility(View.VISIBLE);
            llLogin.setVisibility(View.GONE);
            btLogout.setVisibility(View.VISIBLE);
            getPresenter().onGetCenterInfo();//请求个人中心信息
        }else {//未登录
            isLogin=false;
            ivHead.setImageResource(R.drawable.mine_head);
            tvBalance.setText("0.00");
            tvUsableBalance.setText("0.00");
            llAccount.setVisibility(View.GONE);
            llLogin.setVisibility(View.VISIBLE);
            btLogout.setVisibility(View.GONE);
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
    public void onRequestSuccess(CenterInfoData tData) {
        UserInfoBean userinfo = tData.getUserinfo();
        //昵称
        if (!userinfo.getNickname().equals("")){
            tvUserName.setText(userinfo.getNickname());
        }else {
            tvUserName.setText(MyUtils.setPhone(userinfo.getMobile()));
        }
        //头像(根据性别显示对应头像图片)
        if (!userinfo.getHead_pic().equals("")){
            Glide.with(getContext()).load(userinfo.getHead_pic()).into(ivHead);
        }else {
            if (userinfo.getSex().equals("1")){
                ivHead.setImageResource(R.drawable.icon_head_man);
            }else if (userinfo.getSex().equals("2")){
                ivHead.setImageResource(R.drawable.icon_head_woman);
            }
        }
        //余额
        tvBalance.setText(userinfo.getUser_money());
        tvUsableBalance.setText(userinfo.getAvailable_balance());
    }
    @Override
    public void onRequestFailure(String result) {
        MyUtils.showToast(getContext(),result);
    }
    @Override
    public void onLogout(boolean isSuccess, String msg) {
        MyUtils.showToast(getContext(), msg);
        if (isSuccess){
            LoginUtil.getinit().logout();//将本地登录信息清除
            initUI();
        }
    }
}
