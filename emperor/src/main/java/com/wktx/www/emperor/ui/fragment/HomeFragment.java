package com.wktx.www.emperor.ui.fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wktx.www.emperor.apiresult.home.home.BannerBean;
import com.wktx.www.emperor.apiresult.home.home.HomeInfoData;
import com.wktx.www.emperor.apiresult.home.home.ResumeListBean;
import com.wktx.www.emperor.apiresult.login.AccountInfoData;
import com.wktx.www.emperor.basemvp.ABaseFragment;
import com.wktx.www.emperor.presenter.home.HomePresenter;
import com.wktx.www.emperor.ui.activity.recruit.demand.DemandActivity;
import com.wktx.www.emperor.ui.activity.recruit.resume.ArtistResumeActivity;
import com.wktx.www.emperor.Activity.InviteGuideActivity;
import com.wktx.www.emperor.Activity.MessageActivity;
import com.wktx.www.emperor.Activity.RecruitActivity;
import com.wktx.www.emperor.ui.activity.SearchActivity;
import com.wktx.www.emperor.Activity.WorksActivity;
import com.wktx.www.emperor.model.HomeHorizonalBean;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.ui.adapter.HomeListAdapter;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.GlideImageLoader;
import com.wktx.www.emperor.utils.LoginUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.view.IView;
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
 * 首页
 */
public class HomeFragment extends ABaseFragment<IView,HomePresenter> implements IView<HomeInfoData> {
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recycleView;
    private View hvBanner;//轮播图

    private int page = 1;
    private static final int PAGE_SIZE = 10;//请求每页的数据量
    private boolean isRefresh;//是下拉刷新还是加载更多  ：下拉刷新
    //RecyclerView 适配器
    private HomeListAdapter adapter;
    private BaseQuickAdapter<HomeHorizonalBean, BaseViewHolder> hzAdapter;

    List<String> mDatas = new ArrayList<>();

    private List<HomeHorizonalBean> horizonalBeans;
    int[] hzImages = {R.drawable.home_artist, R.drawable.home_service, R.drawable.home_operation,
            R.drawable.home_example, R.drawable.home_guide};
    String[] hzNames = {"招美工", "招客服", "招运营", "美工案例", "招聘指南"};

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

    public HomeFragment() {
    }

    public static HomeFragment newInstance(String info) {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        initHorizonalData();
        initRecycleView();
        initAdapter();
        initHeadView();
        initRefreshLayout();
        refresh();
        return view;
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    /**
     * 初始化 横向推荐
     */
    private void initHorizonalData() {
        horizonalBeans = new ArrayList<>();
        int length = hzNames.length;
        for (int i = 0; i < length; i++) {
            horizonalBeans.add(new HomeHorizonalBean(hzNames[i], hzImages[i]));
        }
    }

    /**
     * 初始化 RecycleView
     */
    private void initRecycleView() {
        //设置刷新圈圈的颜色
        swipeRefreshLayout.setColorSchemeColors(Color.rgb(255, 179, 33));
        swipeRefreshLayout.setRefreshing(true);
        //设置分割线与垂直方向布局
        recycleView.addItemDecoration(MyUtils.drawDivider(getActivity(), LinearLayout.VERTICAL, R.drawable.divider_f0f0f0_2));
        MyLayoutManager myLayoutManager = new MyLayoutManager(getActivity(), LinearLayout.VERTICAL, false);
        recycleView.setLayoutManager(myLayoutManager);
    }

    /**
     * 为 RecyclerView 加载 Adapter
     */
    private void initAdapter() {
        adapter = new HomeListAdapter(getContext());
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        });
        recycleView.setAdapter(adapter);
        //子控件点击事件
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //将简历ID 传递给 ArtistResumeActivity
                ResumeListBean info = (ResumeListBean) adapter.getData().get(position);
                Intent intent = new Intent(getActivity(), ArtistResumeActivity.class);
                intent.putExtra(ConstantUtil.KEY_POSITION,info.getId());
                startActivity(intent);
            }
        });
    }

    /**
     * 为 RecyclerView 添加头部控件（轮播图、横向推荐）
     */
    private void initHeadView() {
        hvBanner = getActivity().getLayoutInflater().inflate(R.layout.item_hv_banner, (ViewGroup) recycleView.getParent(), false);
        View hvHorizontalRv = getActivity().getLayoutInflater().inflate(R.layout.item_hv_home, (ViewGroup) recycleView.getParent(), false);
        initHzRv(hvHorizontalRv);
        adapter.addHeaderView(hvBanner);
        adapter.addHeaderView(hvHorizontalRv);
    }
    /**
     * 初始化横向热品推荐
     */
    private void initHzRv(View view) {
        RecyclerView rv_horizontal = (RecyclerView) view.findViewById(R.id.recyclerView);
        MyLayoutManager myLayoutManager = new MyLayoutManager(getActivity(), LinearLayout.HORIZONTAL, false);
        rv_horizontal.setLayoutManager(myLayoutManager);
        hzAdapter = new BaseQuickAdapter<HomeHorizonalBean, BaseViewHolder>(R.layout.item_rv_home_horizontal, horizonalBeans) {
            @Override
            protected void convert(BaseViewHolder helper, HomeHorizonalBean item) {
                helper.setText(R.id.tv_name, item.getName());
                helper.setImageResource(R.id.iv_img, item.getImg());
            }
        };
        rv_horizontal.setAdapter(hzAdapter);
        hzAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position != 0 && position % 4 == 0) {//招聘指南
                    startActivity(new Intent(getActivity(), InviteGuideActivity.class));
                } else if (position != 0 && position % 3 == 0) {
                    startActivity(new Intent(getActivity(), WorksActivity.class));
                } else {
                    //TODO 招聘界面还没处理
                    Intent intent = new Intent(getActivity(), RecruitActivity.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putInt("position", position);//压入数据
                    intent.putExtras(mBundle);
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * 下拉刷新 ---  下拉加载更多
     */
    private void initRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    //刷新
    private void refresh() {
        page = 1;
        isRefresh=true;
        //这里的作用是防止下拉刷新的时候还可以上拉加载
        adapter.setEnableLoadMore(false);
        getPresenter().onGetHomeInfo(page);
    }

    //加载更多
    private void loadMore() {
        isRefresh=false;
        getPresenter().onGetHomeInfo(page);
    }

    /**
     * IView
     */
    @Override
    public AccountInfoData getUserInfo() {
        AccountInfoData userInfo = LoginUtil.getinit().getUserInfo();
        return userInfo;
    }
    @Override
    public void onRequestSuccess(HomeInfoData tData) {
        initBanner(tData.getTop_ad());
        setData(tData.getResume_list());
        if (isRefresh){//停止刷新
            adapter.setEnableLoadMore(true);
            swipeRefreshLayout.setRefreshing(false);
        }else {
            if (tData.getResume_list()==null||tData.getResume_list().size()==0){
                adapter.loadMoreEnd();
            }
        }
    }
    @Override
    public void onRequestFailure(String result) {
        if (isRefresh){
            adapter.setEnableLoadMore(true);
            swipeRefreshLayout.setRefreshing(false);
        }else {
                adapter.loadMoreFail();
        }
    }

    /**
     * 初始化广告轮播
     */
    private void initBanner(List<BannerBean> bannerBeans) {
        Banner banner = (Banner) hvBanner.findViewById(R.id.banner); /*设置banner样式*/
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //遍历图片
        List<String> images = new ArrayList<>();
        for (int i = 0; i < bannerBeans.size(); i++) {
            images.add(bannerBeans.get(i).getAd_code());
        }
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
     * 设置数据
     */
    private void setData(List data) {
        page++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            adapter.setNewData(data);
        } else {
            if (size > 0) {
                adapter.addData(data);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            adapter.loadMoreEnd(isRefresh);
        } else {
            adapter.loadMoreComplete();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        horizonalBeans.clear();
    }
}
