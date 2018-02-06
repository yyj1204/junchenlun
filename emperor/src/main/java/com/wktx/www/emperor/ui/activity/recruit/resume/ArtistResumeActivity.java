package com.wktx.www.emperor.ui.activity.recruit.resume;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.wktx.www.emperor.Activity.ArtistEmployActivity;
import com.wktx.www.emperor.apiresult.login.AccountInfoData;
import com.wktx.www.emperor.apiresult.recruit.resume.ResumeInfoData;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.recruit.resume.ResumePresenter;
import com.wktx.www.emperor.ui.fragment.resume.WorksFragment;
import com.wktx.www.emperor.ui.fragment.resume.CommentFragment;
import com.wktx.www.emperor.ui.fragment.resume.ResumeFragment;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.view.IView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 美工简历---简历
 */
public class ArtistResumeActivity extends ABaseActivity<IView,ResumePresenter> implements IView<ResumeInfoData> {
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.layout_smartTab)
    SmartTabLayout smartTab;
    @BindView(R.id.tv_employ)
    TextView tvEmploy;
    private String wechatStr;
    private String qqStr;
    private String phoneStr;

    @OnClick({ R.id.iv_return,R.id.linear_weixin,R.id.linear_qq,R.id.linear_phone,R.id.tv_employ})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                finish();
                break;
            case R.id.linear_weixin:
                if (!wechatStr.equals("")){

                }else {
                    MyUtils.showToast(ArtistResumeActivity.this,"该同学未留下微信号！");
                }
                break;
            case R.id.linear_qq:
                if (!phoneStr.equals("")){

                }else {
                    MyUtils.showToast(ArtistResumeActivity.this,"该同学未留下QQ号！");
                }
                break;
            case R.id.linear_phone://调用拨号界面
                phoneStr="18150961675";
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
                startActivity(new Intent(this,ArtistEmployActivity.class));
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
        String resumeId = getIntent().getStringExtra(ConstantUtil.KEY_POSITION);
        getPresenter().onGetResumeInfo(resumeId);
    }

    /**
     * IResumeView
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
        initSmartVp(tData);
    }
    @Override
    public void onRequestFailure(String result) {
        MyUtils.showToast(ArtistResumeActivity.this,result);
    }

    private void initSmartVp(ResumeInfoData tData) {
        FragmentPagerItems pages = new FragmentPagerItems(this);
        pages.add(FragmentPagerItem.of("简历",ResumeFragment.class));
        pages.add(FragmentPagerItem.of("评价", CommentFragment.class));
        pages.add(FragmentPagerItem.of("作品", WorksFragment.class));
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
