package com.wktx.www.subjects.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.wktx.www.subjects.ui.fragment.JobsFragment;
import com.wktx.www.subjects.ui.fragment.ManagerFragment;
import com.wktx.www.subjects.ui.fragment.MessageFragment;
import com.wktx.www.subjects.ui.fragment.MineFragment;
import com.wktx.www.subjects.R;
import com.yinglan.alphatabs.AlphaTabsIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.mViewPager)
    ViewPager mViewPger;
    @BindView(R.id.alphaIndicator)
    AlphaTabsIndicator alphaTabsIndicator;
    static ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initUI();
        viewPager = mViewPger;
    }

    /**
     * 初始化控件
     */
    private void initUI() {
        MainAdapter mainAdapter = new MainAdapter(getSupportFragmentManager());
        mViewPger.setAdapter(mainAdapter);
        mViewPger.addOnPageChangeListener(mainAdapter);
        alphaTabsIndicator.setViewPager(mViewPger);
        /**
         * 消息通知条数
         */
//        alphaTabsIndicator.getTabView(0).showNumber(6);
//        alphaTabsIndicator.getTabView(1).showNumber(888);
//        alphaTabsIndicator.getTabView(2).showNumber(88);
//        alphaTabsIndicator.getTabView(3).showPoint();
    }

    /**
     * viewpager适配器
     */
    private class MainAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {
        private List<Fragment> fragments = new ArrayList<>();
        private String[] titles = {"首页", "招聘", "员工", "我的"};

        public MainAdapter(FragmentManager fm) {
            super(fm);
            fragments.add(JobsFragment.newInstance(titles[0]));
            fragments.add(ManagerFragment.newInstance(titles[1]));
            fragments.add(MessageFragment.newInstance(titles[2]));
            fragments.add(MineFragment.newInstance(titles[3]));
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

//点击tab触发消息数目变化
//            if (0 == position) {
//                alphaTabsIndicator.getTabView(0).showNumber(alphaTabsIndicator.getTabView(0).getBadgeNumber() - 1);
//            } else if (2 == position) {
//                alphaTabsIndicator.getCurrentItemView().removeShow();
//            } else if (3 == position) {
//                alphaTabsIndicator.removeAllBadge();
//            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}