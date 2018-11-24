package com.wktx.www.emperor.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wktx.www.emperor.ui.activity.mine.AboutUsActivity;
import com.wktx.www.emperor.ui.activity.mine.MyCollectActivity;
import com.wktx.www.emperor.ui.activity.mine.TransactionRecordActivity;
import com.wktx.www.emperor.apiresult.mine.center.CenterInfoData;
import com.wktx.www.emperor.apiresult.mine.center.UserInfoBean;
import com.wktx.www.emperor.ui.activity.mine.BrowsingRecordActivity;
import com.wktx.www.emperor.ui.activity.mine.WebExplainActivity;
import com.wktx.www.emperor.ui.activity.mine.MyCouponActivity;
import com.wktx.www.emperor.ui.activity.mine.authent.AccountAuthentActivity;
import com.wktx.www.emperor.ui.activity.mine.CompanyInfoActivity;
import com.wktx.www.emperor.ui.activity.mine.ContactServiceActivity;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.basemvp.ABaseFragment;
import com.wktx.www.emperor.presenter.mine.MinePresenter;
import com.wktx.www.emperor.ui.activity.mine.authent.AuthentPersonalDetailsActivity;
import com.wktx.www.emperor.ui.activity.mine.authent.AuthentStoreDetailsActivity;
import com.wktx.www.emperor.ui.activity.mine.purse.WithdrawListActivity;
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
import com.wktx.www.emperor.widget.CustomDialog;
import com.wktx.www.emperor.widget.PopupLogout;
import com.wktx.www.emperor.widget.MyScrollView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

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
    CircleImageView ivHead;
    @BindView(R.id.tv_userName)
    TextView tvUserName;
    //认证布局
    @BindView(R.id.linear_certification)
    LinearLayout llCertification;
    @BindView(R.id.linear_certification1)
    LinearLayout llCertification1;
    @BindView(R.id.tv_certificationState)
    TextView tvCertificationState;
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.tv_frozenMoney)
    TextView tvFrozenMoney;
    @BindView(R.id.linear_account)
    LinearLayout llAccount;//已登录布局
    @BindView(R.id.linear_login)
    LinearLayout llLogin;//未登录布局
    @BindView(R.id.bt_logout)
    Button btLogout;//退出登录

    private PopupLogout logoutPopup;//退出登录弹窗
    private CustomDialog customDialog;

    private boolean isLogin;
    private CenterInfoData centerInfoData;//个人中心信息
    private String authentType;//认证方式：1：个人认证 2：店铺认证
    private String authentId;//认证Id
    private String authentState;//已提交的认证状态(字符串)

    @OnClick({R.id.iv_set,R.id.iv_message, R.id.tv_browsing, R.id.civ_head,R.id.tv_userName,
            R.id.linear_certification,R.id.linear_certification1, R.id.tv_login,R.id.tv_register,
            R.id.linear_balance,R.id.linear_frozenMoney, R.id.linear_storeInfo,R.id.linear_myCollect,
            R.id.linear_tradeRecord, R.id.linear_interviewRecord,R.id.linear_employRecord,R.id.linear_myRedpacket,
            R.id.linear_aboutApp, R.id.linear_accountSecurity, R.id.linear_contactService, R.id.bt_logout})
    public void MyOnclick(View view) {
        if (MyUtils.isFastClick1()){
            return;
        }
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.iv_set://设置
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.iv_message://消息通知
                isLoginStartActivity(MessageActivity.class);
                break;
            case R.id.tv_browsing://浏览记录
                isLoginStartActivity(BrowsingRecordActivity.class);
                break;
            case R.id.civ_head://用户头像
            case R.id.tv_userName://用户名
                isLoginStartActivity(CompanyInfoActivity.class);
                break;
            case R.id.linear_certification://申请认证
                startActivity(new Intent(getActivity(), AccountAuthentActivity.class));
                break;
            case R.id.linear_certification1://已提交认证
                //如果认证失败状态，则可以进入认证详情界面
                if (authentState.equals("认证失败")){
                    if (authentType.equals("1")){//个人认证详情
                        startAuthentActivity(AuthentPersonalDetailsActivity.class);
                    }else {//店铺认证详情
                        startAuthentActivity(AuthentStoreDetailsActivity.class);
                    }
                }
                break;
            case R.id.tv_login://登录
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.tv_register://注册
                startActivity(new Intent(getActivity(), RegisterActivity.class));
                break;
            case R.id.linear_balance://余额
                if (isLogin){
                    intent.setClass(getActivity(), MyPurseActivity.class);
                    intent.putExtra(ConstantUtil.KEY_DATA,centerInfoData);
                    startActivity(intent);
                }else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
            case R.id.linear_frozenMoney://冻结余额
                isLoginStartActivity(WithdrawListActivity.class);
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
//                intent.setClass(getActivity(), WebExplainActivity.class);
//                intent.putExtra(ConstantUtil.KEY_WEB_EXPLAIN,ConstantUtil.EX_ABOUT);
//                startActivity(intent);
                startActivity(new Intent(getActivity(), AboutUsActivity.class));
                break;
            case R.id.linear_accountSecurity://账户安全
                if (isLogin){
                    intent.setClass(getActivity(), AccountSecurityActivity.class);
                    intent.putExtra(ConstantUtil.KEY_DATA,centerInfoData);
                    startActivity(intent);
                }else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
            case R.id.linear_contactService://联系客服
                startActivity(new Intent(getActivity(), ContactServiceActivity.class));
                break;
            case R.id.bt_logout://退出登录
                showLogoutDialog();
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
     * 才能打开对应的认证详情界面（个人、店铺）
     * 传递认证id
     */
    private void startAuthentActivity(Class<?> clazz) {
        Intent intent = new Intent(getActivity(), clazz);
        intent.putExtra(ConstantUtil.KEY_DATA,authentId);
        startActivity(intent);
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

    /**
     * 退出登录对话框
     */
    private void showLogoutDialog() {
        CustomDialog.Builder builder = new CustomDialog.Builder(getContext());
        builder.setTitle("系统提示");
        builder.setMessage("您确定退出登录吗？");
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("退出登录",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        getPresenter().onLogout();
                    }
                });

        customDialog = builder.create();
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.show();
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
            tvFrozenMoney.setText("0.00");
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
    public void onRequestSuccess(CenterInfoData tData) {
        centerInfoData = tData;
        UserInfoBean userinfo = tData.getUserinfo();
        //昵称
        if (TextUtils.isEmpty(userinfo.getNickname())){
            tvUserName.setText(MyUtils.setPhone(userinfo.getMobile()));
        }else {
            tvUserName.setText(userinfo.getNickname());
        }
        //头像(根据性别显示对应头像图片)
        if (TextUtils.isEmpty(userinfo.getHead_pic())){
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
        //冻结金额
        tvFrozenMoney.setText(userinfo.getFrozen_money());

        //账户认证方式： 0：未认证  1：个人认证 2：店铺认证
        authentType = tData.getAuthent_type();
        //认证id(0:未提交，其他:已提交认证)
        authentId = tData.getAuthent();
        //已提交的认证状态 '未认证','审核中','认证失败','已认证'
        authentState = tData.getAuthent_status();
        if (TextUtils.isEmpty(authentType)||authentType.equals("0")){//未提交认证
            llCertification.setVisibility(View.VISIBLE);
            llCertification1.setVisibility(View.GONE);
        }else{//已提交认证 1：个人认证 2：店铺认证 ('审核中','认证失败','已认证')
            llCertification.setVisibility(View.GONE);
            llCertification1.setVisibility(View.VISIBLE);
            if (authentType.equals("1")){
                tvCertificationState.setText("个人"+authentState);
            }else {
                tvCertificationState.setText("店铺"+authentState);
            }
        }
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
