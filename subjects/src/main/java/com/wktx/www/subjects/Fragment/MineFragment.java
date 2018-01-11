package com.wktx.www.subjects.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.wktx.www.subjects.Activity.AboutUsActivity;
import com.wktx.www.subjects.Activity.AccountProfileActivity;
import com.wktx.www.subjects.Activity.CashActivity;
import com.wktx.www.subjects.Activity.FavorActivity;
import com.wktx.www.subjects.Activity.InterviewActivity;
import com.wktx.www.subjects.Activity.LoginActivity;
import com.wktx.www.subjects.Activity.MessageActivity;
import com.wktx.www.subjects.Activity.RegiestActivity;
import com.wktx.www.subjects.Activity.ResumeActivity;
import com.wktx.www.subjects.Activity.ServiceActivity;
import com.wktx.www.subjects.Activity.TrasactActivity;
import com.wktx.www.subjects.Model.Login.LoginInfoDataInfo;
import com.wktx.www.subjects.Model.UserInfo.UserInfo;
import com.wktx.www.subjects.Model.UserInfo.UserInfoData;
import com.wktx.www.subjects.Model.UserInfo.UserInfoDataInfo;
import com.wktx.www.subjects.Model.UserInfo.UserInfoDataInfoUserinfo;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.Utils.Contants;
import com.wktx.www.subjects.Utils.GsonUtils;
import com.wktx.www.subjects.Utils.SaveObjectUtils;
import com.wktx.www.subjects.Utils.ToastUtil;
import com.wktx.www.subjects.Utils.getSaveInfoUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import java.util.HashMap;
import java.util.Map;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment {
    @BindView(R.id.mineIvHead)
    ImageView mIvHead;
    @BindView(R.id.minLlAccount)
    LinearLayout mLlAccount;
    @BindView(R.id.minLlAccount1)
    LinearLayout mLlAccount1;
    @BindView(R.id.mineTvAccount)
    TextView mTvAccount;
    @BindView(R.id.mineLlCount)
    LinearLayout mLlCount;
    private boolean isLogin;
    private String token;
    private int user_id;
    private UserInfoDataInfoUserinfo info;
    private UserInfo userInfo;
    private String state;

    @OnClick({R.id.tb_ivRight, R.id.mineIvHead, R.id.minLlAccount, R.id.mineLlAbout, R.id.mineLlService, R.id.mineLlFavor,
            R.id.mineLlResume, R.id.mineLlTransact, R.id.mineLlInterview, R.id.mineLlCount, R.id.mineTvLogin, R.id.mineTvRegiest, R.id.mineTvAccount})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_ivRight://消息通知
                startActivity(new Intent(getActivity(), MessageActivity.class));
                break;
            case R.id.mineIvHead:
            case R.id.mineTvLogin:
            case R.id.mineTvAccount:
                //根据是否登录状态设置跳转的界面
                if (isLogin) {
                    Intent intent = new Intent();
                    intent.putExtra(Contants.INTENT_IDTOKEN, info);
                    intent.setClass(getActivity(), AccountProfileActivity.class);
                    startActivityForResult(intent, 0);
                } else {
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), Contants.REQUESTCODE_LOGIN);
                }
                break;
            case R.id.mineTvRegiest:
                startActivity(new Intent(getActivity(), RegiestActivity.class));
                break;
            case R.id.mineLlAbout:
                startActivity(new Intent(getActivity(), AboutUsActivity.class));
                break;
            case R.id.mineLlResume:
//               ToastUtil.toast(getActivity(),"我的简历");
                startActivity(new Intent(getActivity(), ResumeActivity.class));
                break;
            case R.id.mineLlService:
                startActivity(new Intent(getActivity(), ServiceActivity.class));
                break;
            case R.id.mineLlFavor:
                startActivity(new Intent(getActivity(), FavorActivity.class));
                break;
            case R.id.mineLlTransact:
                startActivity(new Intent(getActivity(), TrasactActivity.class));
                break;
            case R.id.mineLlInterview:
                startActivity(new Intent(getActivity(), InterviewActivity.class));
                break;
            case R.id.mineLlCount:
                startActivity(new Intent(getActivity(), CashActivity.class));
                break;
            default:
                break;
        }
    }


    //提交获取用户信息请求
    public void getUserInfo(int user_id, String token) {
        state = Contants.GETUSERINFO;
        Map<String, String> params = new HashMap<>();
        params.put("user_id", "" + user_id);
        params.put("token", token);
        OkHttpUtils.post()//
                .url(Contants.URL_GETUSERINFO)//
                .params(params)//
                .build()//
                .execute(new MyStringCallback1());
    }

    public class MyStringCallback1 extends StringCallback {

        @Override
        public void onError(okhttp3.Call call, Exception e, int id) {
            ToastUtil.toast(getActivity(), "网络不给力哟");
        }

        @Override
        public void onResponse(String response, int id) {
            if (response != null) {
                userInfo = GsonUtils.parseJSON(response, UserInfo.class);
                UserInfoData data = userInfo.getData();
                if (data.getCode() == 0) {
                    UserInfoDataInfo dataInfo = data.getInfo();
                    info = dataInfo.getUserinfo();
                    String nickname = info.getNickname();
                    String head_pic = info.getHead_pic();
                    if (nickname != null) {
                        mTvAccount.setText(nickname);
                    }
                    if (head_pic != null) {
                        Glide.with(getActivity()).load(head_pic).into(mIvHead);
                    } else {
                        mIvHead.setImageResource(R.drawable.mine_head);
                    }
                    new SaveObjectUtils(getActivity(), Contants.USERINFO_NAME).setObject(token, info);
                } else {
                    ToastUtil.toast(getActivity(), data.getMsg());
                }
            }
        }
    }

    public MineFragment() {
        // Required empty public constructor
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, view);
        initSaveLoacl();
        return view;
    }

    private void initSaveLoacl() {
        isLogin = getSaveInfoUtil.getIsLogin(getActivity());
        if (isLogin) {
            LoginInfoDataInfo saveObject = getSaveInfoUtil.getSaveObject(getActivity());
            if (saveObject != null) {
                token = getSaveInfoUtil.getToken(getActivity());
                user_id = getSaveInfoUtil.getUser_id(getActivity());
            }
        }
    }

    //根据登录状态获取用户信息，更新界面
    @Override
    public void onResume() {
        super.onResume();
        initHeadNickName();
    }

    //初始化头像和昵称
    private void initHeadNickName() {
        initSaveLoacl();
        if (isLogin) {
            mLlAccount.setVisibility(View.VISIBLE);
            mLlAccount1.setVisibility(View.GONE);
            mLlCount.setVisibility(View.VISIBLE);
            info = new SaveObjectUtils(getActivity(), Contants.USERINFO_NAME).getObject(token, UserInfoDataInfoUserinfo.class);
            if (info != null) {
                String nickname = info.getNickname();
                if (nickname != null) {
                    mTvAccount.setText(nickname);
                }
                String url = info.getHead_pic();
                if (url != null) {
                    Glide.with(getActivity()).load(url).into(mIvHead);
                } else {
                    mIvHead.setImageResource(R.drawable.mine_head);
                }
                if (token != null && user_id != 0) {
                    getUserInfo(user_id, token);
                }
            } else {
                if (token != null && user_id != 0) {
                    getUserInfo(user_id, token);
                }
            }
        } else {
            mLlAccount1.setVisibility(View.VISIBLE);
            mLlAccount.setVisibility(View.GONE);
            mLlCount.setVisibility(View.GONE);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case Contants.REQUESTCODE_LOGIN:
                    mLlAccount.setVisibility(View.VISIBLE);
                    mLlAccount1.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
            switch (resultCode) {
                case Contants.RESULT_OUTLOGINCODE:
                    mTvAccount.setText("登录/注册");
                    mIvHead.setImageResource(R.drawable.mine_head);
                    mLlAccount1.setVisibility(View.VISIBLE);
                    mLlAccount.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }
    }

}