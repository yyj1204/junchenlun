package com.wktx.www.subjects.ui.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.login.AccountInfoData;
import com.wktx.www.subjects.basemvp.ALazyLoadFragment;
import com.wktx.www.subjects.ui.activity.MainActivity;
import com.wktx.www.subjects.ui.activity.login.LoginActivity;
import com.wktx.www.subjects.ui.fragment.message.Message1DealFragment;
import com.wktx.www.subjects.ui.fragment.message.Message1EvaluateFragment;
import com.wktx.www.subjects.ui.fragment.message.Message1TaskFragment;
import com.wktx.www.subjects.ui.fragment.message.Message2InterestedFragment;
import com.wktx.www.subjects.ui.fragment.message.Message2InviteFragment;
import com.wktx.www.subjects.utils.LoginUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 消息片段
 */
public class MessageFragment extends ALazyLoadFragment{
    @BindView(R.id.tb_TvBarTitle1)
    TextView tv_title1;
    @BindView(R.id.tb_TvBarTitle2)
    TextView tv_title2;
    @BindView(R.id.linear_nothing)
    LinearLayout llNothing;
    //工作
    @BindView(R.id.linear_tab1)
    LinearLayout linear_tab1;
    @BindView(R.id.layout_smartTab1)
    SmartTabLayout smartTab1;
    @BindView(R.id.viewpager1)
    ViewPager viewPager1;
    //互动
    @BindView(R.id.linear_tab2)
    LinearLayout linear_tab2;
    @BindView(R.id.layout_smartTab2)
    SmartTabLayout smartTab2;
    @BindView(R.id.viewpager2)
    ViewPager viewPager2;

    private boolean isFirstVisible=true;//是否第一次创建可见

    private boolean isWork=true;//是否工作消息
    private String[] tabTitle1 = {"任务消息","评价消息","交易消息"};
    private String[] tabTitle2 = {"对我感兴趣","邀请函"};
    private FragmentPagerItemAdapter adapter1;
    private FragmentPagerItemAdapter adapter2;

    @OnClick({R.id.tb_TvBarTitle1,R.id.tb_TvBarTitle2,R.id.tv_login,R.id.tv_selectWork})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_TvBarTitle1://工作
                isWork=true;
                setTitle(isWork);
                break;
            case R.id.tb_TvBarTitle2://互动
                isWork=false;
                setTitle(isWork);
                break;
            case R.id.tv_login://登录
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.tv_selectWork://挑合适的工作
                //定位到 MainFragment
                MainActivity activity = (MainActivity) getActivity();
                activity.setCurrenPager(0);
                break;
            default:
                break;
        }
    }

    //顶部点击
    private void setTitle(boolean selected) {
        tv_title1.setSelected(selected);
        tv_title2.setSelected(!selected);
        //如果未登录，显示未登录布局
        if (getUserInfo()==null){
            llNothing.setVisibility(View.VISIBLE);
            linear_tab1.setVisibility(View.GONE);
            linear_tab2.setVisibility(View.GONE);
        }else {
            llNothing.setVisibility(View.GONE);
            if(selected){
                linear_tab1.setVisibility(View.VISIBLE);
                linear_tab2.setVisibility(View.GONE);
            }else {
                linear_tab1.setVisibility(View.GONE);
                linear_tab2.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle(isWork);
        if (getUserInfo()!=null){
            initSmartVp();
        }
    }

    /**
     * 片段是否可见
     * @param isVisible falese 可见 -> 不可见
     *  此时 isFirstVisible = true，在片段不可见时候将 isFirstVisible = false
     * @param isVisible true  不可见 -> 可见
     *  如果 isFirstVisible = false 说明已经创建过，防止多次请求接口，加判断
     */
    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible){
            if (!isFirstVisible){
                setTitle(isWork);
            }
        }else {
            isFirstVisible=false;
        }
    }

    /**
     * 片段第一次被创建（可见）时才会执行到这个方法
     * 加载数据
     *  isFirstVisible=true;
     */
    @Override
    protected void onFragmentFirstVisible() {
        isFirstVisible=true;
        setTitle(isWork);
    }


    public MessageFragment() {
    }
    public static MessageFragment newInstance(String info) {
        Bundle args = new Bundle();
        MessageFragment fragment = new MessageFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    private void initSmartVp() {
        //工作
        FragmentPagerItems pages1 = new FragmentPagerItems(getContext());
        pages1.add(FragmentPagerItem.of(tabTitle1[0], Message1TaskFragment.class));
        pages1.add(FragmentPagerItem.of(tabTitle1[1], Message1EvaluateFragment.class));
        pages1.add(FragmentPagerItem.of(tabTitle1[2], Message1DealFragment.class));
        adapter1 = new FragmentPagerItemAdapter(getChildFragmentManager(), pages1);
        smartTab1.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
                CharSequence pageTitle = adapter.getPageTitle(position);
                View v = getActivity().getLayoutInflater().inflate(R.layout.item_smarttab_message, container, false);
                if (v != null) {
                    TextView textView = (TextView) v.findViewById(R.id.textView);
                    textView.setText(pageTitle);
                }
                return v;
            }
        });
        viewPager1.setAdapter(adapter1);
        smartTab1.setViewPager(viewPager1);

        //互动
        FragmentPagerItems pages2 = new FragmentPagerItems(getContext());
        pages2.add(FragmentPagerItem.of(tabTitle2[0], Message2InterestedFragment.class));
        pages2.add(FragmentPagerItem.of(tabTitle2[1], Message2InviteFragment.class));
        adapter2 = new FragmentPagerItemAdapter(getChildFragmentManager(), pages2);
        smartTab2.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
                CharSequence pageTitle = adapter.getPageTitle(position);
                View v = getActivity().getLayoutInflater().inflate(R.layout.item_smarttab_message, container, false);
                if (v != null) {
                    TextView textView = (TextView) v.findViewById(R.id.textView);
                    textView.setText(pageTitle);
                }
                return v;
            }
        });
        viewPager2.setAdapter(adapter2);
        smartTab2.setViewPager(viewPager2);
    }

    /**
     * 获取用户的token与user_id
     */
    private AccountInfoData getUserInfo() {
        AccountInfoData userInfo = LoginUtil.getinit().getUserInfo();
        return userInfo;
    }
}
