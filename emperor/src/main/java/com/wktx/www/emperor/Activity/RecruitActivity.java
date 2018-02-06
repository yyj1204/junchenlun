package com.wktx.www.emperor.Activity;

import android.content.Intent;
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
import com.wktx.www.emperor.ui.activity.SearchActivity;
import com.wktx.www.emperor.ui.activity.recruit.demand.DemandActivity;
import com.wktx.www.emperor.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecruitActivity extends AppCompatActivity {
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.viewpagertab)
    SmartTabLayout viewPagerTab;

    @OnClick({R.id.recruitTvDemand, R.id.title_layout, R.id.tb_right_img, R.id.tb_IvReturn})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.recruitTvDemand:
                startActivity(new Intent(this, DemandActivity.class));
                break;
            case R.id.title_layout:
                startActivity(new Intent(this, SearchActivity.class));
                break;
            case R.id.tb_right_img://消息通知
                startActivity(new Intent(this, MessageActivity.class));
                break;
            case R.id.tb_IvReturn:
                finish();
                break;
            default:
                break;
        }
    }

    public RecruitActivity() {
        // Required empty public constructor
    }

    String[] title =
            {
                    "招美工", "招客服", "招运营", "招美工", "招客服", "招运营"
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruit);
        ButterKnife.bind(this);
        initSmartVp();
    }

    private void initSmartVp() {
        FragmentPagerItems pages = new FragmentPagerItems(this);
        for (int i = 0; i < title.length; i++) {
            pages.add(FragmentPagerItem.of(title[i], RecruitListFragment.class));
        }
//        pages.add(FragmentPagerItem.of(getString(R.string.demo_tab_1), SortListPagesFragment.class));
//        pages.add(FragmentPagerItem.of(getString(R.string.demo_tab_2), SortListPagesFragment.class));
//        pages.add(FragmentPagerItem.of(getString(R.string.demo_tab_3), SortListPagesFragment.class));
//        pages.add(FragmentPagerItem.of(getString(R.string.demo_tab_4), SortListPagesFragment.class));
//        pages.add(FragmentPagerItem.of(getString(R.string.demo_tab_5), SortListPagesFragment.class));
//        pages.add(FragmentPagerItem.of(getString(R.string.demo_tab_6), SortListPagesFragment.class));
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), pages);
        viewPagerTab.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
                CharSequence pageTitle = adapter.getPageTitle(position);
                View v = LayoutInflater.from(RecruitActivity.this).inflate(R.layout.item_recruit_smarttab, container, false);
                if (v != null) {
                    TextView textView = (TextView) v.findViewById(R.id.textView);
                    textView.setText(pageTitle);
                }
                return v;
            }
        });
        viewPager.setAdapter(adapter);
        viewPagerTab.setViewPager(viewPager);
        Bundle bundle = getIntent().getExtras();
        int position = bundle.getInt("position");
        switch (position) {
            case 0:
                viewPager.setCurrentItem(0);
                break;
            case 1:
                viewPager.setCurrentItem(1);
                break;
            case 2:
                viewPager.setCurrentItem(2);
                break;
            case 5:
                viewPager.setCurrentItem(3);
                break;
            case 6:
                viewPager.setCurrentItem(4);
                break;
            case 7:
                viewPager.setCurrentItem(5);
                break;
            default:
                break;
        }
    }
}
