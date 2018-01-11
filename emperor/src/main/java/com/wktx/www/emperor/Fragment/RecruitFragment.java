package com.wktx.www.emperor.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.wktx.www.emperor.Activity.SearchActivity;
import com.wktx.www.emperor.Activity.DemandActivity;
import com.wktx.www.emperor.Activity.MessageActivity;
import com.wktx.www.emperor.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecruitFragment extends Fragment {
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.viewpagertab)
    SmartTabLayout viewPagerTab;

    @OnClick({R.id.recruitTvDemand, R.id.title_layout, R.id.tb_right_img})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.recruitTvDemand:
                startActivity(new Intent(getActivity(), DemandActivity.class));
                break;
            case R.id.title_layout:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
            case R.id.tb_right_img://消息通知
                startActivity(new Intent(getActivity(), MessageActivity.class));
                break;
            default:
                break;
        }
    }

    public RecruitFragment() {
        // Required empty public constructor
    }

    public static RecruitFragment newInstance(String info) {
        Bundle args = new Bundle();
        RecruitFragment fragment = new RecruitFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }

    String[] title =
            {
                    "招美工", "招客服", "招运营", "招美工", "招客服", "招运营"
            };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recruit, container, false);
        ButterKnife.bind(this, view);
        initSmartVp();
        return view;
    }

    private void initSmartVp() {
        FragmentPagerItems pages = new FragmentPagerItems(getActivity());
        for (int i = 0; i < title.length; i++) {
            Bundle bundle = new Bundle();
            bundle.putString("title", title[i]);
            pages.add(FragmentPagerItem.of(title[i], RecruitListFragment.class, bundle));
        }

//        pages.add(FragmentPagerItem.of(getString(R.string.demo_tab_1), SortListPagesFragment.class));
//        pages.add(FragmentPagerItem.of(getString(R.string.demo_tab_2), SortListPagesFragment.class));
//        pages.add(FragmentPagerItem.of(getString(R.string.demo_tab_3), SortListPagesFragment.class));
//        pages.add(FragmentPagerItem.of(getString(R.string.demo_tab_4), SortListPagesFragment.class));
//        pages.add(FragmentPagerItem.of(getString(R.string.demo_tab_5), SortListPagesFragment.class));
//        pages.add(FragmentPagerItem.of(getString(R.string.demo_tab_6), SortListPagesFragment.class));
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getChildFragmentManager(), pages);
        viewPagerTab.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
                CharSequence pageTitle = adapter.getPageTitle(position);
                View v = LayoutInflater.from(getActivity()).inflate(R.layout.sortlist_title_view, container, false);
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
