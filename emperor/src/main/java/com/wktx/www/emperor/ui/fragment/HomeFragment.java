package com.wktx.www.emperor.ui.fragment;
import android.content.Context;
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
import com.wktx.www.emperor.apiresult.main.home.BannerBean;
import com.wktx.www.emperor.apiresult.main.home.HomeInfoData;
import com.wktx.www.emperor.apiresult.main.home.JobTypeInfoData;
import com.wktx.www.emperor.apiresult.main.home.ResumeListBean;
import com.wktx.www.emperor.basemvp.ALazyLoadFragment;
import com.wktx.www.emperor.presenter.main.HomePresenter;
import com.wktx.www.emperor.ui.activity.ImageActivity;
import com.wktx.www.emperor.ui.activity.login.LoginActivity;
import com.wktx.www.emperor.ui.activity.main.artistcase.CasesActivity;
import com.wktx.www.emperor.ui.activity.recruit.resume.ResumeActivity;
import com.wktx.www.emperor.ui.activity.main.RecruitGuideActivity;
import com.wktx.www.emperor.ui.activity.main.message.MessageActivity;
import com.wktx.www.emperor.ui.activity.main.SearchActivity;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.ui.adapter.main.HomeJobTypeAdapter;
import com.wktx.www.emperor.ui.adapter.main.HomeListAdapter;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.GlideImageLoader;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.ui.view.main.IHomeView;
import com.wktx.www.emperor.utils.ToastUtil;
import com.wktx.www.emperor.widget.MyLayoutManager;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 首页片段
 */
public class HomeFragment extends ALazyLoadFragment<IHomeView,HomePresenter> implements IHomeView {
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
    private HomeJobTypeAdapter hzAdapter;


    @OnClick({R.id.linear_titleSearch, R.id.iv_titleRight})
    public void MyOnclick(View view) {
        if (MyUtils.isFastClick1()){
            return;
        }
        switch (view.getId()) {
            case R.id.linear_titleSearch://搜索
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
            case R.id.iv_titleRight://消息通知
                isLoginStartActivity(MessageActivity.class);
                break;
            default:
                break;
        }
    }

    /**
     * 登录了才能打开对应的界面
     */
    private void isLoginStartActivity(Class<?> clazz) {
        if (getUserInfo()!=null){
            startActivity(new Intent(getActivity(), clazz));
        }else {
            startActivity(new Intent(getActivity(), LoginActivity.class));
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
        initRecycleView();
        initAdapter();
        initHeadView();
        initRefreshLayout();
        return view;
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    /**
     * 片段是否可见
     * @param isVisible falese 可见 -> 不可见
     *  此时 isRefresh=true，在片段不可见时候将 isRefresh = false
     * @param isVisible true  不可见 -> 可见
     *  如果 isRefresh=false 说明已经创建过，防止多次请求接口，加判断
     */
    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible){
            if (!isRefresh){
                refresh();
            }
        }else {
            if (isRefresh){
                isRefresh=false;
                swipeRefreshLayout.setRefreshing(false);
            }
        }
    }

    /**
     * 片段第一次被创建（可见）时才会执行到这个方法
     * 加载数据
     * isRefresh=true
     */
    @Override
    protected void onFragmentFirstVisible() {
        refresh();
    }


    /**
     * 初始化 RecycleView
     */
    private void initRecycleView() {
        //设置刷新圈圈的颜色
        swipeRefreshLayout.setColorSchemeColors(Color.rgb(255, 179, 33));
        swipeRefreshLayout.setRefreshing(true);
        //设置分割线与垂直方向布局
        recycleView.addItemDecoration(MyUtils.drawDivider(getActivity(), LinearLayout.VERTICAL, R.drawable.divider_f0f0f0_6));
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
                if (MyUtils.isFastClick1()){
                    return;
                }
                //将简历ID 传递给 ResumeActivity
                ResumeListBean info = (ResumeListBean) adapter.getData().get(position);
                Intent intent = new Intent(getActivity(), ResumeActivity.class);
                intent.putExtra(ConstantUtil.KEY_POSITION,info.getId());
                startActivity(intent);
            }
        });
    }

    /**
     * 为 RecyclerView 添加头部控件（轮播图、横向推荐）
     */
    private void initHeadView() {
        hvBanner = getActivity().getLayoutInflater().inflate(R.layout.item_hv_home_banner, (ViewGroup) recycleView.getParent(), false);
        View hvHorizontalRv = getActivity().getLayoutInflater().inflate(R.layout.item_hv_home_horizontal, (ViewGroup) recycleView.getParent(), false);
        initHzRv(hvHorizontalRv);
        adapter.addHeaderView(hvBanner);
        adapter.addHeaderView(hvHorizontalRv);
    }
    /**
     * 初始化横向职业类型推荐
     */
    private void initHzRv(View view) {
        RecyclerView rv_horizontal = (RecyclerView) view.findViewById(R.id.recyclerView);
        MyLayoutManager myLayoutManager = new MyLayoutManager(getActivity(), LinearLayout.HORIZONTAL, false);
        rv_horizontal.setLayoutManager(myLayoutManager);
        hzAdapter = new HomeJobTypeAdapter(getContext());
        rv_horizontal.setAdapter(hzAdapter);
        hzAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (MyUtils.isFastClick1()){
                    return;
                }

                if (position==adapter.getData().size()-1){//招聘指南
                    startActivity(new Intent(getActivity(), RecruitGuideActivity.class));
                }else  if (position==adapter.getData().size()-2){//案例库
                    startActivity(new Intent(getActivity(), CasesActivity.class));
                } else {
                    //将选中的职业类型id传给 MainActivity，MainActivity 再传给 RecruitFragment
                    JobTypeInfoData jobTypeInfoData = (JobTypeInfoData) adapter.getData().get(position);
                    if (listener!=null){
                        listener.onDataChanged(jobTypeInfoData.getId());
                    }
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
        //顶部横向的职位类型
        getPresenter().onGetTypeOfWorkList();
        //轮播图、简历列表
        getPresenter().onGetHomeInfo(page);
    }

    //加载更多
    private void loadMore() {
        isRefresh=false;
        getPresenter().onGetHomeInfo(page);
    }


    /**
     * IHomeView
     */
    @Override//获取职业类型
    public void onGetJobTypeSuccessResult(List<JobTypeInfoData> result) {
        //添加案例、招聘指南两个类型
        result.add(new JobTypeInfoData("","案例",""));
        result.add(new JobTypeInfoData("","招聘指南",""));
        hzAdapter.setNewData(result);
    }
    @Override
    public void onGetJobTypeFailureResult(String result) {
        ToastUtil.myToast(result);
    }
    @Override//获取轮播图、简历列表
    public void onRequestSuccess(HomeInfoData tData) {
        setData(tData.getResume_list());
        if (isRefresh){//停止刷新
            initBanner(tData.getTop_ad());
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
        ToastUtil.myToast(result);
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
        final List<String> images = new ArrayList<>();
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

        banner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                if (MyUtils.isFastClick()){
                    return;
                }
                //查看大图
                String[] imageUrls = images.toArray(new String[images.size()]);
                Intent intent = new Intent(getContext(), ImageActivity.class);
                intent.putExtra(ConstantUtil.KEY_DATA, imageUrls);
                //这里的position是从1开始的，所以位标要-1
                intent.putExtra(ConstantUtil.KEY_POSITION, position-1);
                startActivity(intent);
            }
        });
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


    /**
     * 接口
     * 点击横向布局的职业类型时，需定位到 RecruitFragment 对应职业位置
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // 这是为了保证Activity容器实现了用以回调的接口。如果没有，它会抛出一个异常。
        try {
            listener = (onChangedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + "must implement onChangedListener");
        }
    }

    private onChangedListener listener;

    public interface onChangedListener {
        void onDataChanged(String jobTypeId);//参数为用户需要传递的数据内容,这里我用的是一个横向布局的被选中职业类型id
    }
}
