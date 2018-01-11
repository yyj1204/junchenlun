package com.wktx.www.subjects.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment {

    @BindView(R.id.tb_TvBarTitle1)
    TextView tv_title1;
    @BindView(R.id.tb_TvBarTitle2)
    TextView tv_title2;
    //工作
    @BindView(R.id.linear_tab1)
    LinearLayout linear_tab1;
    @BindView(R.id.viewpagertab1)
    SmartTabLayout viewPagerTab1;
    @BindView(R.id.viewpager1)
    ViewPager viewPager1;
    //互动
    @BindView(R.id.linear_tab2)
    LinearLayout linear_tab2;
    @BindView(R.id.viewpagertab2)
    SmartTabLayout viewPagerTab2;
    @BindView(R.id.viewpager2)
    ViewPager viewPager2;


    private boolean isWork=true;
    private String[] tabTitle1 = {"任务消息", "评价消息","交易消息"};
    private String[] tabTitle2 = {"对我感兴趣", "看过我","邀请面试"};
    private FragmentPagerItemAdapter adapter1;
    private FragmentPagerItemAdapter adapter2;

    @OnClick({R.id.tb_TvBarTitle1,R.id.tb_TvBarTitle2})
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
            default:
                break;
        }
    }

    //顶部点击
    private void setTitle(boolean selected) {
        tv_title1.setSelected(selected);
        tv_title2.setSelected(!selected);
        if(selected){
            linear_tab1.setVisibility(View.VISIBLE);
            linear_tab2.setVisibility(View.GONE);
        }else {
            linear_tab1.setVisibility(View.GONE);
            linear_tab2.setVisibility(View.VISIBLE);
        }

    }

    public MessageFragment() {
        // Required empty public constructor
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
        initSmartVp();
        setTitle(isWork);
        return view;
    }

    private void initSmartVp() {
        //工作
        FragmentPagerItems pages1 = new FragmentPagerItems(getContext());
        pages1.add(FragmentPagerItem.of(tabTitle1[0], Message1TaskFragment.class));
        pages1.add(FragmentPagerItem.of(tabTitle1[1], Message1EvaluateFragment.class));
        pages1.add(FragmentPagerItem.of(tabTitle1[2], Message1DealFragment.class));
        adapter1 = new FragmentPagerItemAdapter(getChildFragmentManager(), pages1);
        viewPagerTab1.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
                CharSequence pageTitle = adapter.getPageTitle(position);
                View v = getActivity().getLayoutInflater().inflate(R.layout.item_tab_message, container, false);
                if (v != null) {
                    TextView textView = (TextView) v.findViewById(R.id.textView);
                    textView.setText(pageTitle);
                }
                return v;
            }
        });
        viewPager1.setAdapter(adapter1);
        viewPagerTab1.setViewPager(viewPager1);

        //互动
        FragmentPagerItems pages2 = new FragmentPagerItems(getContext());
        pages2.add(FragmentPagerItem.of(tabTitle2[0], Message2InterestFragment.class));
        pages2.add(FragmentPagerItem.of(tabTitle2[1], Message2LookFragment.class));
        pages2.add(FragmentPagerItem.of(tabTitle2[2], Message2InterviewFragment.class));
        adapter2 = new FragmentPagerItemAdapter(getChildFragmentManager(), pages2);
        viewPagerTab2.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
                CharSequence pageTitle = adapter.getPageTitle(position);
                View v = getActivity().getLayoutInflater().inflate(R.layout.item_tab_message, container, false);
                if (v != null) {
                    TextView textView = (TextView) v.findViewById(R.id.textView);
                    textView.setText(pageTitle);
                }
                return v;
            }
        });
        viewPager2.setAdapter(adapter2);
        viewPagerTab2.setViewPager(viewPager2);
    }
}
