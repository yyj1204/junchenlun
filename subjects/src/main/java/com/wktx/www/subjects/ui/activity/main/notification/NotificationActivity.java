package com.wktx.www.subjects.ui.activity.main.notification;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.subjects.ui.fragment.notification.NotificationNoticeFragment;
import com.wktx.www.subjects.ui.fragment.notification.NotificationRemindFragment;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.utils.ToastUtil;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 消息通知
 */
public class NotificationActivity extends AutoLayoutActivity {

    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.layout_smartTab)
    SmartTabLayout smartTab;

    String[] title = {"公告", "提醒"};

    @OnClick({R.id.tb_IvReturn})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn://返回
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        //沉浸式状态栏
        StatusBarUtil.setColor(NotificationActivity.this, getResources().getColor(R.color.color_ffb321),0);
        //强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_message);
        initSmartVp();
    }


    /**
     * 初始化SmartTabLayout
     */
    private void initSmartVp() {
        FragmentPagerItems pages = new FragmentPagerItems(this);
        pages.add(FragmentPagerItem.of(title[0], NotificationNoticeFragment.class));
        pages.add(FragmentPagerItem.of(title[1], NotificationRemindFragment.class));
        //适配器
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), pages);
        //给SmartTabLayout子控件赋值
        smartTab.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
                CharSequence pageTitle = adapter.getPageTitle(position);
                View v = getLayoutInflater().inflate(R.layout.item_smarttab_message, container, false);
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
