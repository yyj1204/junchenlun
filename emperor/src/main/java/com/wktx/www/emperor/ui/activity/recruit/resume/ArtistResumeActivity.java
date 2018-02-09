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
import com.wktx.www.emperor.ui.fragment.resume.ResumeEvaluateFragment;
import com.wktx.www.emperor.ui.fragment.resume.ResumeWorksFragment;
import com.wktx.www.emperor.ui.fragment.resume.ResumeFragment;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.view.IView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.wktx.www.emperor.utils.MyUtils.checkQQApkExist;

/**
 * 简历
 */
public class ArtistResumeActivity extends ABaseActivity<IView,ResumePresenter> implements IView<ResumeInfoData> {
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.layout_smartTab)
    SmartTabLayout smartTab;
    @BindView(R.id.tv_employ)
    TextView tvEmploy;

    private String wechatStr="";
    private String qqStr="";
    private String phoneStr="";
    private String resumeId;//简历ID
    private boolean isServiceType;//是否是客服类型
    private ResumeInfoData resumeInfoData;//简历信息

    @OnClick({ R.id.iv_return,R.id.linear_weixin,R.id.linear_qq,R.id.linear_phone,R.id.tv_employ})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                finish();
                break;
            case R.id.linear_weixin://微信
                if (wechatStr.equals("")){
                    MyUtils.showToast(ArtistResumeActivity.this,"该同学未留下微信号！");
                }else {
                    //将微信号复制
                    ClipboardManager copy = (ClipboardManager) ArtistResumeActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                    //将文本内容放到系统剪贴板里
                    copy.setText(wechatStr);
                    MyUtils.showToast(ArtistResumeActivity.this,"该同学微信号复制成功，快去添加好友吧！");
                }
                break;
            case R.id.linear_qq://调用QQ界面
                if (qqStr.equals("")){
                    MyUtils.showToast(ArtistResumeActivity.this,"该同学未留下QQ号！");
                }else {
                    if (checkQQApkExist(this, "com.tencent.mobileqq")){
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin="+qqStr+"&version=1")));
                    }else{
                        MyUtils.showToast(ArtistResumeActivity.this,"本机未安装QQ应用！");
                    }
                }
                break;
            case R.id.linear_phone://调用拨号界面
                if (phoneStr.equals("")||phoneStr.equals("0")){
                    MyUtils.showToast(ArtistResumeActivity.this,"该同学未留下手机号！");
                }else {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:" + phoneStr);
                    intent.setData(data);
                    startActivity(intent);
                }
                break;
            case R.id.tv_employ://立即雇佣
                if (isServiceType){

                }else {
                    Intent intent = new Intent(this, ArtistEmployActivity.class);
                    intent.putExtra(ConstantUtil.KEY_DATA,resumeInfoData);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_resume);
        ButterKnife.bind(this);
        initData();
    }

    @Override
    protected ResumePresenter createPresenter() {
        return new ResumePresenter();
    }

    private void initData() {
        resumeId = getIntent().getStringExtra(ConstantUtil.KEY_POSITION);
        getPresenter().onGetResumeInfo(resumeId);
    }

    /**
     * IEmployView
     */
    @Override
    public AccountInfoData getUserInfo() {
        return null;
    }
    @Override
    public void onRequestSuccess(ResumeInfoData tData) {
        tvEmploy.setText("立即雇佣(¥"+tData.getMonthly_money()+"/月)");
        wechatStr = tData.getWechat();
        qqStr = tData.getQq();
        phoneStr = tData.getPhone();
        isServiceType = tData.getTow().equals("2");
        initSmartVp(tData);
    }
    @Override
    public void onRequestFailure(String result) {
        MyUtils.showToast(ArtistResumeActivity.this,result);
        finish();
    }

    private void initSmartVp(ResumeInfoData tData) {
        FragmentPagerItems pages = new FragmentPagerItems(this);
        //绑定简历数据
        Bundle bundle1 = new Bundle();
        bundle1.putSerializable(ConstantUtil.KEY_DATA, tData);//简历内容
        Bundle bundle2 = new Bundle();
        bundle2.putSerializable(ConstantUtil.KEY_POSITION, resumeId);//简历ID
        pages.add(FragmentPagerItem.of("简历",ResumeFragment.class, bundle1));
        pages.add(FragmentPagerItem.of("评价("+tData.getEvaluate_num()+")", ResumeEvaluateFragment.class,bundle2));
        if (!isServiceType){//如果不是客服类型，就多一个作品界面
            pages.add(FragmentPagerItem.of("作品", ResumeWorksFragment.class,bundle2));
        }
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), pages);
        smartTab.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
                CharSequence pageTitle = adapter.getPageTitle(position);
                View v = LayoutInflater.from(ArtistResumeActivity.this).inflate(R.layout.resme_title_view, container, false);
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
}
