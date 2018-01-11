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
import com.wktx.www.emperor.Fragment.WorksFragment;
import com.wktx.www.emperor.Fragment.CommentFragment;
import com.wktx.www.emperor.Fragment.ResumeFragment;
import com.wktx.www.emperor.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 美工简历---简历
 */
public class ArtistResumeActivity extends AppCompatActivity {
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.viewpagertab)
    SmartTabLayout viewPagerTab;

    @OnClick({ R.id.tb_IvReturn,R.id.tv_artist_employ})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn:
                finish();
                break;
            case R.id.tv_artist_employ://立即雇佣
                startActivity(new Intent(this,ArtistEmployActivity.class));
                break;
            default:
                break;
        }
    }

    String[] title =
            {
                    "简历", "评价","作品"
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_resume);
        ButterKnife.bind(this);
        initSmartVp();
    }

    private void initSmartVp() {
        FragmentPagerItems pages = new FragmentPagerItems(this);
//        for (int i = 0; i < title.length; i++) {
//            pages.add(FragmentPagerItem.of(title[i], ResumeFragment.class));
//        }

        pages.add(FragmentPagerItem.of("简历",ResumeFragment.class));
        pages.add(FragmentPagerItem.of("评价", CommentFragment.class));
        pages.add(FragmentPagerItem.of("作品", WorksFragment.class));
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), pages);
        viewPagerTab.setCustomTabView(new SmartTabLayout.TabProvider() {
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
        viewPagerTab.setViewPager(viewPager);
    }
}
