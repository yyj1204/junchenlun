package com.wktx.www.subjects.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.wktx.www.subjects.Activity.AboutUsActivity;
import com.wktx.www.subjects.Activity.CashActivity;
import com.wktx.www.subjects.Activity.FavorActivity;
import com.wktx.www.subjects.Activity.InterviewActivity;
import com.wktx.www.subjects.Activity.PersonalInfoActivity;
import com.wktx.www.subjects.apiresult.login.AccountInfoData;
import com.wktx.www.subjects.apiresult.mine.UserData;
import com.wktx.www.subjects.basemvp.ABaseFragment;
import com.wktx.www.subjects.presenter.mine.MinePresenter;
import com.wktx.www.subjects.ui.activity.mine.login.LoginActivity;
import com.wktx.www.subjects.Activity.MessageActivity;
import com.wktx.www.subjects.ui.activity.mine.login.RegisterActivity;
import com.wktx.www.subjects.Activity.ResumeActivity;
import com.wktx.www.subjects.Activity.ServiceActivity;
import com.wktx.www.subjects.Activity.TrasactActivity;
import com.wktx.www.subjects.Model.UserInfo.UserInfo;
import com.wktx.www.subjects.Model.UserInfo.UserInfoDataInfoUserinfo;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.LoginUtil;
import com.wktx.www.subjects.utils.MyUtils;
import com.wktx.www.subjects.view.mine.IMineView;
import com.wktx.www.subjects.widget.LogoutPopup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的账户
 */
public class MineFragment extends ABaseFragment<IMineView,MinePresenter> implements IMineView {
    @BindView(R.id.civ_head)
    ImageView ivHead;
    @BindView(R.id.tv_userName)
    TextView tvUserName;
    @BindView(R.id.tv_userJob)
    TextView tvUserJob;
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.tv_logout)
    TextView tv_Logout;
    @BindView(R.id.linear_account)
    LinearLayout llAccount;//已登录布局
    @BindView(R.id.linear_login)
    LinearLayout llLogin;//未登录布局

    private LogoutPopup logoutPopup;//退出登录弹窗


    private boolean isLogin;
    private String token;
    private int user_id;
    private UserInfoDataInfoUserinfo info;
    private UserInfo userInfo;
    private String state;


    @OnClick({R.id.iv_message,R.id.tv_logout,R.id.civ_head,R.id.tv_userName,R.id.linear_balance,R.id.tv_login,R.id.tv_register
            ,R.id.linear_myResume,R.id.linear_myCollect,R.id.linear_tradeRecord,R.id.linear_interviewRecord
            ,R.id.linear_contactService,R.id.linear_aboutApp})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.iv_message://消息通知
                startActivity(new Intent(getActivity(), MessageActivity.class));
                break;
            case R.id.tv_logout://退出登录
                showLogoutPopup();
                break;
            case R.id.civ_head:
            case R.id.tv_userName://用户名
                if (getUserInfo()!=null){//已登录
                    Intent intent = new Intent();
//                intent.putExtra(Contants.INTENT_IDTOKEN, info);
                    intent.setClass(getActivity(), PersonalInfoActivity.class);
                    startActivityForResult(intent, 0);
                }else {//未登录
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), ConstantUtil.REQUESTCODE_LOGIN);
                }
                break;
            case R.id.linear_balance:
                startActivity(new Intent(getActivity(), CashActivity.class));
                break;
            case R.id.tv_login://登录
                startActivityForResult(new Intent(getActivity(), LoginActivity.class), ConstantUtil.REQUESTCODE_LOGIN);
                break;
            case R.id.tv_register://注册
                startActivity(new Intent(getActivity(), RegisterActivity.class));
                break;
            case R.id.linear_myResume://我的简历
                startActivity(new Intent(getActivity(), ResumeActivity.class));
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
            case R.id.linear_contactService://联系客服
                startActivity(new Intent(getActivity(), ServiceActivity.class));
                break;
            case R.id.linear_aboutApp://关于君臣论
                startActivity(new Intent(getActivity(), AboutUsActivity.class));
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
            tv_Logout.setVisibility(View.VISIBLE);
            llAccount.setVisibility(View.VISIBLE);
            llLogin.setVisibility(View.GONE);
        }else {//未登录
            tv_Logout.setVisibility(View.GONE);
            llAccount.setVisibility(View.GONE);
            llLogin.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected MinePresenter createPresenter() {
        return new MinePresenter(getContext());
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
    public void onRequestSuccess(UserData tData) {

    }
    @Override
    public void onRequestFailure(String result) {

    }
    @Override
    public void onLogout(boolean isSuccess, String msg) {
        MyUtils.showToast(getActivity(), msg);
        if (isSuccess){
            LoginUtil.getinit().logout();//将本地登录信息清除
            tv_Logout.setVisibility(View.GONE);
            llAccount.setVisibility(View.GONE);
            llLogin.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 退出登录弹窗
     */
    private void showLogoutPopup() {
        logoutPopup = new LogoutPopup(getContext(), getActivity());
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
