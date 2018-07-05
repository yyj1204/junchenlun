package com.wktx.www.emperor.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.wktx.www.emperor.ui.activity.mine.MyCollectActivity;
import com.wktx.www.emperor.ui.activity.mine.TransactionRecordActivity;
import com.wktx.www.emperor.apiresult.mine.center.CenterInfoData;
import com.wktx.www.emperor.apiresult.mine.center.UserInfoBean;
import com.wktx.www.emperor.ui.activity.mine.BrowsingRecordActivity;
import com.wktx.www.emperor.ui.activity.mine.WebExplainActivity;
import com.wktx.www.emperor.ui.activity.mine.MyCouponActivity;
import com.wktx.www.emperor.ui.activity.mine.certification.AccountCertificationActivity;
import com.wktx.www.emperor.ui.activity.mine.CompanyInfoActivity;
import com.wktx.www.emperor.ui.activity.mine.ContactServiceActivity;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.login.AccountInfoData;
import com.wktx.www.emperor.basemvp.ABaseFragment;
import com.wktx.www.emperor.presenter.mine.MinePresenter;
import com.wktx.www.emperor.ui.activity.mine.security.AccountSecurityActivity;
import com.wktx.www.emperor.ui.activity.mine.purse.MyPurseActivity;
import com.wktx.www.emperor.ui.activity.mine.SettingActivity;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.ui.activity.mine.HireRecordActivity;
import com.wktx.www.emperor.ui.activity.mine.InterviewRecordActivity;
import com.wktx.www.emperor.ui.activity.login.LoginActivity;
import com.wktx.www.emperor.ui.activity.main.message.MessageActivity;
import com.wktx.www.emperor.ui.activity.login.RegisterActivity;
import com.wktx.www.emperor.ui.activity.mine.store.StoreInfoActivity;
import com.wktx.www.emperor.utils.GlideUtil;
import com.wktx.www.emperor.utils.LoginUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.ui.view.mine.IMineView;
import com.wktx.www.emperor.utils.ToastUtil;
import com.wktx.www.emperor.widget.PopupLogout;
import com.wktx.www.emperor.widget.MyScrollView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的账户片段
 */
public class MineFragment extends ABaseFragment<IMineView,MinePresenter> implements IMineView{
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.scrollview)
    MyScrollView scrollview;
    @BindView(R.id.rela_top)
    RelativeLayout relaTop;
    @BindView(R.id.civ_head)
    ImageView ivHead;
    @BindView(R.id.tv_userName)
    TextView tvUserName;
    //认证布局
    @BindView(R.id.linear_certification)
    LinearLayout llCertification;
    @BindView(R.id.linear_certification1)
    LinearLayout llCertification1;
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

    private PopupLogout logoutPopup;//退出登录弹窗

    private boolean isLogin;

    @OnClick({R.id.iv_set,R.id.iv_message, R.id.tv_browsing, R.id.civ_head,R.id.tv_userName,
            R.id.linear_certification,R.id.linear_certification1, R.id.tv_login,R.id.tv_register,
            R.id.linear_balance,R.id.linear_usableBalance, R.id.linear_storeInfo,R.id.linear_myCollect,
            R.id.linear_tradeRecord, R.id.linear_interviewRecord,R.id.linear_employRecord,R.id.linear_myRedpacket,
            R.id.linear_aboutApp, R.id.linear_accountSecurity, R.id.linear_contactService, R.id.bt_logout})
    public void MyOnclick(View view) {
        if (MyUtils.isFastClick1()){
            return;
        }
        switch (view.getId()) {
            case R.id.iv_set://设置
                isLoginStartActivity(SettingActivity.class);
                break;
            case R.id.iv_message://消息通知
                isLoginStartActivity(MessageActivity.class);
                break;
            case R.id.tv_browsing://浏览记录
                isLoginStartActivity(BrowsingRecordActivity.class);
                break;
            case R.id.civ_head://用户头像
            case R.id.tv_userName://用户名
                if (isLogin){//已登录
                    startActivity(new Intent(getActivity(), CompanyInfoActivity.class));
                }else {//未登录
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
            case R.id.linear_certification://申请认证
                startActivity(new Intent(getActivity(), AccountCertificationActivity.class));
                break;
            case R.id.linear_certification1://已认证
//                startActivity(new Intent(getActivity(), AccountCertificationActivity.class));
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
            case R.id.linear_storeInfo://店铺信息
                isLoginStartActivity(StoreInfoActivity.class);
                break;
            case R.id.linear_myCollect://我的收藏
                isLoginStartActivity(MyCollectActivity.class);
                break;
            case R.id.linear_tradeRecord://交易记录
                isLoginStartActivity(TransactionRecordActivity.class);
                break;
            case R.id.linear_interviewRecord://面试记录
                isLoginStartActivity(InterviewRecordActivity.class);
                break;
            case R.id.linear_employRecord://雇佣记录
                isLoginStartActivity(HireRecordActivity.class);
                break;
            case R.id.linear_myRedpacket://我的红包
                isLoginStartActivity(MyCouponActivity.class);
                break;
            case R.id.linear_aboutApp://关于我们
                Intent intent = new Intent(getActivity(), WebExplainActivity.class);
                intent.putExtra(ConstantUtil.KEY_WEB_EXPLAIN,ConstantUtil.EX_ABOUT);
                startActivity(intent);
                break;
            case R.id.linear_accountSecurity://账号安全
                isLoginStartActivity(AccountSecurityActivity.class);
                break;
            case R.id.linear_contactService://联系客服
                startActivity(new Intent(getActivity(), ContactServiceActivity.class));
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
        logoutPopup = new PopupLogout(getActivity(), getActivity());
        logoutPopup.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        logoutPopup.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        logoutPopup.setClippingEnabled(false);
        logoutPopup.showPopupWindow(ivHead);
        logoutPopup.setOnGetTypeClckListener(new PopupLogout.onGetTypeClckListener() {
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

    //根据登录状态获取用户信息，更新界面
    @Override
    public void onResume() {
        super.onResume();
        initUI();
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
        initScrollListener();
        return view;
    }

    @Override
    protected MinePresenter createPresenter() {
        return new MinePresenter();
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
            ivHead.setImageResource(R.drawable.img_mine_head);
            tvBalance.setText("0.00");
            tvUsableBalance.setText("0.00");
            llAccount.setVisibility(View.GONE);
            llLogin.setVisibility(View.VISIBLE);
            btLogout.setVisibility(View.GONE);
        }
    }

    //实现标题栏透明度随之渐变
    private void initScrollListener() {
        tvTitle.setAlpha(0);//先设置看不见
        scrollview.setOnScrollViewListener(new MyScrollView.ScrollViewListener() {
            @Override
            public void onScrollChange(int height) {
                int titleHeight = tvTitle.getHeight()+relaTop.getHeight();
                if (height <= titleHeight) {
                    //如果滑动高度小于标题控件的高度，透明度逐渐减弱
                    tvTitle.setAlpha((float)height/titleHeight);
                }else {
                    //如果滑动高度大于标题控件的高度，标题直接显示出来
                    tvTitle.setAlpha(1);
                }
            }
        });
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
            tvUserName.setText(getUserInfo().getPhone());
        }
        //头像(根据性别显示对应头像图片)
        if (userinfo.getHead_pic()==null|userinfo.getHead_pic().equals("")){
            if (userinfo.getSex().equals("1")){
                ivHead.setImageResource(R.drawable.img_head_man);
            }else if (userinfo.getSex().equals("2")){
                ivHead.setImageResource(R.drawable.img_head_woman);
            }else {
                ivHead.setImageResource(R.drawable.img_mine_head);
            }
        }else {
            GlideUtil.loadImage(userinfo.getHead_pic(),R.drawable.img_mine_head,ivHead);
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
        ToastUtil.myToast(msg);
        if (isSuccess){
            LoginUtil.getinit().logout();//将本地登录信息清除
            initUI();
        }
    }
}
