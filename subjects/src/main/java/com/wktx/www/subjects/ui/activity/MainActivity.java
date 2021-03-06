package com.wktx.www.subjects.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;

import com.jaeger.library.StatusBarUtil;
import com.wktx.www.subjects.apiresult.login.AccountInfoData;
import com.wktx.www.subjects.ui.activity.login.LoginActivity;
import com.wktx.www.subjects.ui.fragment.ManageFragment;
import com.wktx.www.subjects.ui.fragment.MainFragment;
import com.wktx.www.subjects.ui.fragment.MessageFragment;
import com.wktx.www.subjects.ui.fragment.MineFragment;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.utils.LogUtil;
import com.wktx.www.subjects.utils.LoginUtil;
import com.wktx.www.subjects.utils.MyUtils;
import com.wktx.www.subjects.utils.ToastUtil;
import com.yinglan.alphatabs.AlphaTabsIndicator;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * 主界面
 */
public class MainActivity extends AutoLayoutActivity {
    @BindView(R.id.mViewPager)
    ViewPager mViewPger;
    @BindView(R.id.alphaIndicator)
    AlphaTabsIndicator alphaTabsIndicator;
    static ViewPager viewPager;

    private Handler handler = new Handler();
    private boolean isExit;//是否退出

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //沉浸式状态栏
        StatusBarUtil.setColor(MainActivity.this, getResources().getColor(R.color.color_ffb321),0);
        //强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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
     * 定位到固定片段
     */
    public void setCurrenPager(int item) {
        viewPager.setCurrentItem(item);
    }

    /**
     * viewpager适配器
     */
    private class MainAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {
        private List<Fragment> fragments = new ArrayList<>();
        private String[] titles = {"职位", "管理", "消息", "我的"};

        public MainAdapter(FragmentManager fm) {
            super(fm);
            fragments.add(MainFragment.newInstance(titles[0]));
            fragments.add(ManageFragment.newInstance(titles[1]));
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
            if (position==1||position==2){
                if (getUserInfo()==null){
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            }
            LogUtil.error("首页onPageSelected===","position=="+position);
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
        public void onPageScrollStateChanged(int state) {}
    }

    /**
     * 获取用户的token与user_id
     */
    private AccountInfoData getUserInfo() {
        AccountInfoData userInfo = LoginUtil.getinit().getUserInfo();
        return userInfo;
    }

    /**
     * 系统返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isExit) {
                finish();
            } else {//第一次点击返回
                isExit = true;
                ToastUtil.myToast("再按一次退出应用！");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //判断2秒之内是否有再次点击返回
                        isExit = false;
                    }
                }, 2000);
            }
        }
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtil.cancleMyToast();
    }
}