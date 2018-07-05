package com.wktx.www.emperor.ui.activity.recruit.resume;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.wktx.www.emperor.apiresult.login.AccountInfoData;
import com.wktx.www.emperor.apiresult.recruit.resume.ResumeInfoData;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.recruit.resume.ResumePresenter;
import com.wktx.www.emperor.ui.activity.login.LoginActivity;
import com.wktx.www.emperor.ui.activity.recruit.hire.HireActivity;
import com.wktx.www.emperor.ui.activity.recruit.hire.TrusteeshipSalaryActivity;
import com.wktx.www.emperor.ui.fragment.resume.ResumeEvaluateFragment;
import com.wktx.www.emperor.ui.fragment.resume.ResumeWorksFragment;
import com.wktx.www.emperor.ui.fragment.resume.ResumeFragment;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LoginUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.ui.view.recruit.IResumeView;
import com.wktx.www.emperor.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.wktx.www.emperor.utils.MyUtils.checkQQApkExist;

/**
 * 简历
 */
public class ResumeActivity extends ABaseActivity<IResumeView,ResumePresenter> implements IResumeView {
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.layout_smartTab)
    SmartTabLayout smartTab;
    @BindView(R.id.tv_employ)
    TextView tvEmploy;

    private ResumeInfoData resumeInfoData;//简历信息
    private String resumeId;//简历ID

    private String interviewStr;//面试方式：微信、QQ、手机号
    private String wechatStr="";
    private String qqStr="";
    private String phoneStr="";


    @OnClick({ R.id.iv_return,R.id.linear_weixin,R.id.linear_qq,R.id.linear_phone,R.id.tv_employ})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                finish();
                break;
            case R.id.linear_weixin://微信
                if (MyUtils.isFastClick()) {
                    return;
                }
                if (getUserInfo()!=null) {
                    if (wechatStr.equals("")){
                        ToastUtil.myToast("该求职者未留下微信号！");
                    }else {
                        interviewStr="wx";
                        getPresenter().onInterview(resumeId);
                    }
                }else {
                    startActivity(new Intent(ResumeActivity.this, LoginActivity.class));
                }
                break;
            case R.id.linear_qq://调用QQ界面
                if (MyUtils.isFastClick()) {
                    return;
                }
                if (getUserInfo()!=null) {
                    if (qqStr.equals("")) {
                        ToastUtil.myToast( "该求职者未留下QQ号！");
                    } else {
                        if (checkQQApkExist(this, "com.tencent.mobileqq")) {
                            interviewStr = "qq";
                            getPresenter().onInterview(resumeId);
                        } else {
                            ToastUtil.myToast( "本机未安装QQ应用！");
                        }
                    }
                }else {
                    startActivity(new Intent(ResumeActivity.this, LoginActivity.class));
                }
                break;
            case R.id.linear_phone://调用拨号界面
                if (MyUtils.isFastClick()) {
                    return;
                }

                if (getUserInfo()!=null) {
                    if (phoneStr.equals("")||phoneStr.equals("0")){
                        ToastUtil.myToast("该求职者未留下手机号！");
                    }else {
                        interviewStr="phone";
                        getPresenter().onInterview(resumeId);
                    }
                }else {
                    startActivity(new Intent(ResumeActivity.this, LoginActivity.class));
                }
                break;
            case R.id.tv_employ://立即雇佣
                if (getUserInfo()!=null){
                    //有未支付的雇佣订单
                    if (resumeInfoData.getNo_pay_order().equals("1")){
                        //将雇佣Id传递给 TrusteeshipSalaryActivity
                        Intent intent = new Intent(this, TrusteeshipSalaryActivity.class);
                        intent.putExtra(ConstantUtil.KEY_DATA,resumeInfoData.getHire_id());
                        intent.putExtra(ConstantUtil.KEY_POSITION,ConstantUtil.ACTIVITY_JL);
                        startActivity(intent);
                    }else if (resumeInfoData.getNo_pay_order().equals("0")){//没有未支付的雇佣订单
                        //将简历信息传递给 HireActivity
                        Intent intent = new Intent(this, HireActivity.class);
                        intent.putExtra(ConstantUtil.KEY_DATA,resumeInfoData);
                        startActivity(intent);
                    }
                }else {
                    startActivity(new Intent(ResumeActivity.this, LoginActivity.class));
                }
                break;
            default:
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);
        ButterKnife.bind(this);
        initData();
    }

    @Override
    protected ResumePresenter createPresenter() {
        return new ResumePresenter();
    }

    private void initData() {
        //接收 HomeFragment RecruitListFragment StaffFragment、StaffFragment、HireRecordActivity、WorksDetailsActivity 传递过来的简历ID
        resumeId = getIntent().getStringExtra(ConstantUtil.KEY_POSITION);
        getPresenter().onGetResumeInfo(resumeId);
    }

    /**
     * IResumeView
     */
    @Override
    public AccountInfoData getUserInfo() {
        AccountInfoData userInfo = LoginUtil.getinit().getUserInfo();
        return userInfo;
    }
    @Override//面试员工
    public void onInterviewResult(boolean isSuccess, String msg) {
        if (isSuccess){
            switch (interviewStr){
                case "wx"://微信联系
                    //将微信号复制
                    ClipboardManager copy = (ClipboardManager) ResumeActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                    //将文本内容放到系统剪贴板里
                    copy.setText(wechatStr);
                    ToastUtil.myToast("该求职者微信号复制成功，快去添加好友吧！");
                    break;
                case "qq"://QQ联系
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin="+qqStr+"&version=1")));
                    break;
                case "phone"://手机联系
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:" + phoneStr);
                    intent.setData(data);
                    startActivity(intent);
                    break;
                default:
                    break;
            }

        }else {
            ToastUtil.myToast(msg);
        }
    }
    @Override
    public void onRequestSuccess(ResumeInfoData tData) {
        resumeInfoData=tData;
        String is_hiring = tData.getIs_hiring();
        if (tData.getIs_job_hunting().equals("0")){//不在找工作
            tvEmploy.setEnabled(false);
            tvEmploy.setText("不可雇佣");
            tvEmploy.setBackgroundResource(R.color.color_888888);
        }else if (tData.getIs_job_hunting().equals("1")){//找工作中
            tvEmploy.setText("立即雇佣(¥"+tData.getMonthly_money()+"/月)");
            tvEmploy.setBackgroundResource(R.drawable.selector_submit_selected);

            //以下判断是一个员工只能接受一个雇主
//            if (is_hiring.equals("0")){
//                tvEmploy.setEnabled(true);
//                tvEmploy.setText("立即雇佣(¥"+tData.getMonthly_money()+"/月)");
//                tvEmploy.setBackgroundResource(R.drawable.selector_submit_selected);
//            }else if (is_hiring.equals("1")){
//                tvEmploy.setEnabled(false);
//                tvEmploy.setText("已被雇佣");
//                tvEmploy.setBackgroundResource(R.color.color_888888);
//            }
        }
        wechatStr = tData.getWechat();
        qqStr = tData.getQq();
        phoneStr = tData.getPhone();
        initSmartVp(tData);
    }
    @Override
    public void onRequestFailure(String result) {
        ToastUtil.myToast(result);
        finish();
    }


    /**
     * 初始化
     * @param tData
     */
    private void initSmartVp(ResumeInfoData tData) {
        FragmentPagerItems pages = new FragmentPagerItems(this);
        //绑定简历数据
        Bundle bundle1 = new Bundle();
        bundle1.putSerializable(ConstantUtil.KEY_DATA, tData);//简历内容
        Bundle bundle2 = new Bundle();
        bundle2.putSerializable(ConstantUtil.KEY_POSITION, resumeId);//简历ID
        pages.add(FragmentPagerItem.of("简历",ResumeFragment.class, bundle1));
        pages.add(FragmentPagerItem.of("评价("+tData.getEvaluate_num()+")", ResumeEvaluateFragment.class,bundle2));
        if (tData.getTow().equals("1")){//如果是美工类型，就多一个作品界面
            pages.add(FragmentPagerItem.of("作品", ResumeWorksFragment.class,bundle2));
        }
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), pages);
        smartTab.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
                CharSequence pageTitle = adapter.getPageTitle(position);
                View v = LayoutInflater.from(ResumeActivity.this).inflate(R.layout.item_smarttab_resume, container, false);
                if (v != null) {
                    TextView textView = (TextView) v.findViewById(R.id.textView);
                    textView.setText(pageTitle);
                }
                return v;
            }
        });
        viewPager.setAdapter(adapter);
        smartTab.setViewPager(viewPager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtil.cancleMyToast();
    }
}
