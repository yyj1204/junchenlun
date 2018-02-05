package com.wktx.www.subjects.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wktx.www.subjects.Activity.MessageActivity;
import com.wktx.www.subjects.Activity.ScreeningActivity;
import com.wktx.www.subjects.Activity.SearchActivity;
import com.wktx.www.subjects.Activity.WorkDetailActivity;
import com.wktx.www.subjects.ui.adapter.ListDropDownAdapter;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.utils.GlideImageLoader;
import com.wktx.www.subjects.utils.MyDividerUtil;
import com.wktx.www.subjects.widget.DropDownMenu;
import com.wktx.www.subjects.widget.MyLayoutManager;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class JobsFragment extends Fragment {
    @BindView(R.id.dropDownMenu)
    DropDownMenu mDropDownMenu;
    private ImageView imgView;
    private MyLayoutManager myLayoutManager;
    private BaseQuickAdapter<String, BaseViewHolder> mAdapter;

    @OnClick({R.id.title_layout, R.id.tb_right_img})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_right_img://消息通知
                startActivity(new Intent(getActivity(), MessageActivity.class));
                break;
            case R.id.title_layout://搜索
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
            default:
                break;
        }
    }

    private ListDropDownAdapter ageAdapter1;
    private String headers[] = {"招聘类目", "招聘平台"};
    private List<View> popupViews = new ArrayList<>();
    private TextView textView;
    private ListDropDownAdapter ageAdapter;
    private RecyclerView mVtRecycleView;
    private String ages[] = {"不限", "淘宝", "天猫", "阿里巴巴", "京东", "苏宁"};
    private String citys[] = {"不限", "鞋服", "生活电器", "3c数码", "母婴", "食品"};
    List<String> mDatas = new ArrayList<>();

    private void initVtRvData() {
        for (int i = 0; i < 6; i++) {
            mDatas.add("店铺运营" + i);
        }
    }

    public JobsFragment() {
        // Required empty public constructor
    }

    public static JobsFragment newInstance(String info) {
        Bundle args = new Bundle();
        JobsFragment fragment = new JobsFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_jobs, container, false);
        ButterKnife.bind(this, view);
        initVtRvData();
        initView();
        initVtRecycleView();
        return view;
    }


    private void initView() {
        //测试tabView扩展功能
        @SuppressLint("RestrictedApi") final View view = getLayoutInflater(new Bundle()).inflate(R.layout.tab_text, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
        textView = (TextView) view.findViewById(R.id.text);
        imgView = (ImageView) view.findViewById(R.id.img);
        textView.setText("筛选");

        //init age menu
        final ListView ageView = new ListView(getActivity());
        ageView.setDividerHeight(0);
        ageAdapter = new ListDropDownAdapter(getActivity(), Arrays.asList(citys));
        ageView.setAdapter(ageAdapter);

        //init sex menu
        final ListView ageView1 = new ListView(getActivity());
        ageView1.setDividerHeight(0);
        ageAdapter1 = new ListDropDownAdapter(getActivity(), Arrays.asList(ages));
        ageView1.setAdapter(ageAdapter1);
        popupViews.add(ageView);
        popupViews.add(ageView1);
        ageView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ageAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[0] : citys[position]);
                mDropDownMenu.closeMenu();
                textView.setTextColor(getResources().getColor(R.color.title_color));
            }
        });

        ageView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ageAdapter1.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[1] : ages[position]);
                mDropDownMenu.closeMenu();
                textView.setTextColor(getResources().getColor(R.color.title_color));
            }
        });

        @SuppressLint("RestrictedApi") View view_rv = getLayoutInflater(new Bundle()).inflate(R.layout.include_recycleview, null);
        mVtRecycleView = (RecyclerView) view_rv.findViewById(R.id.recyclerView);
        view_rv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        //初始化 dropdownview
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, view_rv);
        mDropDownMenu.addTab(view, 2);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDropDownMenu.isActive()) {
                    mDropDownMenu.closeMenu();
                } else {
                    startActivity(new Intent(getActivity(), ScreeningActivity.class));
                    getActivity().overridePendingTransition(R.anim.move_in_left, R.anim.move_out_left);
                }
            }
        });
        mDropDownMenu.setOnItemMenuClickListener(new DropDownMenu.OnItemMenuClickListener() {
            @Override
            public void OnItemMenuClick(TextView tabView, int position) {
                textView.setTextColor(getResources().getColor(R.color.title_color));
                imgView.setImageDrawable(getResources().getDrawable(R.drawable.down_pull_normal));
            }
        });
    }

    private void initVtRecycleView() {
        mVtRecycleView.addItemDecoration(MyDividerUtil.drawDivider(getActivity(), LinearLayout.VERTICAL, R.drawable.divider_recycler_gray_three));// 添加分割线。
        MyLayoutManager myLayoutManager = new MyLayoutManager(getActivity(), LinearLayout.VERTICAL, false);
        mVtRecycleView.setLayoutManager(myLayoutManager);
        mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_vt_rv, mDatas) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.hzTvName, item);
            }
        };
        initHeaderAndFooter();
        mVtRecycleView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getActivity(), WorkDetailActivity.class));
            }
        });
    }

    private void initHeaderAndFooter() {
        View HvBanner = getActivity().getLayoutInflater().inflate(R.layout.hbanner, (ViewGroup) mVtRecycleView.getParent(), false);
        View HvTitle = getActivity().getLayoutInflater().inflate(R.layout.mine_hv_title, (ViewGroup) mVtRecycleView.getParent(), false);
        initBanner(HvBanner);
        mAdapter.setHeaderView(HvBanner, 0);
        mAdapter.setHeaderView(HvTitle, 1);
    }

    /**
     * 初始化广告轮播
     */
    private void initBanner(View view) {
        /*设置banner样式*/
        Banner banner = (Banner) view.findViewById(R.id.banner);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        List<String> images = new ArrayList<>();
        images.add("https://img11.static.yhbimg.com/yhb-img01/2017/05/22/11/018bf3b2c3dc4d4299da7351ded61167fa.jpg");
        images.add("https://img10.static.yhbimg.com/yhb-img01/2017/05/18/10/015afe6e5be1ba8f274cbcb2a697d92e4e.jpg");
        images.add("https://img10.static.yhbimg.com/yhb-img01/2017/05/22/10/01e0e2a25bfc1795412e2cb749292615eb.jpg");
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mDatas.clear();
        popupViews.clear();
    }
}
