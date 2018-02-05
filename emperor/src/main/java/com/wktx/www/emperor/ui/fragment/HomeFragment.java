package com.wktx.www.emperor.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wktx.www.emperor.ui.activity.recruit.resume.ArtistResumeActivity;
import com.wktx.www.emperor.Activity.InviteGuideActivity;
import com.wktx.www.emperor.Activity.MessageActivity;
import com.wktx.www.emperor.Activity.RecruitActivity;
import com.wktx.www.emperor.ui.activity.SearchActivity;
import com.wktx.www.emperor.Activity.WorksActivity;
import com.wktx.www.emperor.Model1.TempClass;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.utils.GlideImageLoader;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.widget.MyLayoutManager;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    @BindView(R.id.recyclerView)
    RecyclerView mVtRecycleView;

    @OnClick({R.id.linear_titleSearch, R.id.iv_titleRight})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.linear_titleSearch://搜索
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
            case R.id.iv_titleRight://消息通知
            startActivity(new Intent(getActivity(), MessageActivity.class));
            break;
            default:
                break;
        }
    }

    //    int[] imgRes = {R.drawable.e, R.drawable.b, R.drawable.d};
    List<String> mDatas = new ArrayList<>();
    private BaseQuickAdapter<String, BaseViewHolder> mAdapter;
    private BaseQuickAdapter<TempClass, BaseViewHolder> mAdapter1;
    private List<TempClass> temp;
    int[] image =
            {
                    R.drawable.home_artist,
                    R.drawable.home_service,
                    R.drawable.home_operation,
                    R.drawable.home_example,
                    R.drawable.home_guide,
                    R.drawable.home_artist,
                    R.drawable.home_service,
                    R.drawable.home_operation,
                    R.drawable.home_example,
                    R.drawable.home_guide,
            };
    String[] title =
            {
                    "招美工", "招客服", "招运营", "美工案例", "招聘指南"
            };


    private void initHzRvData() {
        temp = new ArrayList<>();
        int length = title.length;
        for (int i = 0; i < length; i++) {
            temp.add(new TempClass(title[i], image[i]));
        }
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String info) {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }

    private void initVtRvData() {
        for (int i = 0; i < 5; i++) {
            mDatas.add("胡图图" + i);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        initVtRvData();
        initHzRvData();
        initVtRecycleView();
        return view;
    }

    private void initVtRecycleView() {
        mVtRecycleView.addItemDecoration(MyUtils.drawDivider(getActivity(), LinearLayout.VERTICAL, R.drawable.divider_f0f0f0_2));// 添加分割线。
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
                startActivity(new Intent(getActivity(), ArtistResumeActivity.class));
            }
        });
    }

    private void initHeaderAndFooter() {
        View HvBanner = getActivity().getLayoutInflater().inflate(R.layout.hbanner, (ViewGroup) mVtRecycleView.getParent(), false);
        View HvHzRv = getActivity().getLayoutInflater().inflate(R.layout.include_recycleview_mainwhite, (ViewGroup) mVtRecycleView.getParent(), false);
        View HvTitle = getActivity().getLayoutInflater().inflate(R.layout.mine_hv_title, (ViewGroup) mVtRecycleView.getParent(), false);
        initBanner(HvBanner);
        initHzRv(HvHzRv);
        mAdapter.setHeaderView(HvBanner, 0);
        mAdapter.setHeaderView(HvHzRv, 1);
        mAdapter.setHeaderView(HvTitle, 2);
    }

    /**
     * 初始化广告轮播
     */
    private void initBanner(View view) {
        Banner banner = (Banner) view.findViewById(R.id.banner); /*设置banner样式*/
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

    /**
     * 初始化横向热品推荐
     */
    private void initHzRv(View view) {
        RecyclerView rv_horizontal = (RecyclerView) view.findViewById(R.id.recyclerView);
        MyLayoutManager myLayoutManager = new MyLayoutManager(getActivity(), LinearLayout.HORIZONTAL, false);
        rv_horizontal.setLayoutManager(myLayoutManager);
        mAdapter1 = new BaseQuickAdapter<TempClass, BaseViewHolder>(R.layout.item_hv_hzrv, temp) {
            @Override
            protected void convert(BaseViewHolder helper, TempClass tempClass) {
                helper.setText(R.id.item_text, tempClass.getTitle());
                helper.setImageResource(R.id.item_img, tempClass.getImg());
            }
        };
        rv_horizontal.setAdapter(mAdapter1);
        mAdapter1.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                if (position != 0 && position % 4 == 0) {
                    startActivity(new Intent(getActivity(), InviteGuideActivity.class));
                } else if
                        (position != 0 && position % 3 == 0) {
                    startActivity(new Intent(getActivity(), WorksActivity.class));
                } else {
                    Intent intent = new Intent();
                    Bundle mBundle = new Bundle();
                    mBundle.putInt("position", position);//压入数据
                    intent.setClass(getActivity(), RecruitActivity.class);
                    intent.putExtras(mBundle);
                    startActivity(intent);
                }

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        temp.clear();
        mDatas.clear();
    }
}
