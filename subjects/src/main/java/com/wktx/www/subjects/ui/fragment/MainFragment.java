package com.wktx.www.subjects.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wktx.www.subjects.apiresult.main.condition.ConditionBean;
import com.wktx.www.subjects.apiresult.main.condition.ConditionInfoData;
import com.wktx.www.subjects.apiresult.main.position.BannerInfoData;
import com.wktx.www.subjects.apiresult.main.position.PositionListInfoData;
import com.wktx.www.subjects.ui.activity.main.ScreeningSalaryActivity;
import com.wktx.www.subjects.ui.activity.main.notification.NotificationActivity;
import com.wktx.www.subjects.ui.activity.main.SearchActivity;
import com.wktx.www.subjects.ui.activity.main.PositionDetailsActivity;
import com.wktx.www.subjects.apiresult.main.position.BannerBean;
import com.wktx.www.subjects.apiresult.login.AccountInfoData;
import com.wktx.www.subjects.basemvp.ALazyLoadBaseFragment;
import com.wktx.www.subjects.presenter.main.MainPresenter;
import com.wktx.www.subjects.ui.activity.login.LoginActivity;
import com.wktx.www.subjects.ui.adapter.DropDownListAdapter;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.ui.adapter.main.PositionAdapter;
import com.wktx.www.subjects.ui.view.main.IMainView;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.GlideImageLoader;
import com.wktx.www.subjects.utils.LoginUtil;
import com.wktx.www.subjects.utils.MyUtils;
import com.wktx.www.subjects.widget.DropDownMenu;
import com.wktx.www.subjects.widget.MyLayoutManager;
import com.wktx.www.subjects.utils.ToastUtil;
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
 * 职位片段
 */
public class MainFragment extends ALazyLoadBaseFragment<IMainView,MainPresenter> implements IMainView {
    @BindView(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private Banner banner;//轮播图

    //条件筛选适配器
    private DropDownListAdapter categoryAdapter;//类目
    private DropDownListAdapter platformAdapter;//平台
    private PositionAdapter mAdapter;//RecyclerView 适配器

    private String tabTexts[] = {"招聘类目", "招聘平台"};
    private List<ConditionBean> categoryBeans = new ArrayList<>();//类目集合
    private List<ConditionBean> platformBeans = new ArrayList<>();//平台集合
    private List<String> categoryStrs = new ArrayList<>();//类目名称
    private List<String> platformStrs = new ArrayList<>();//平台名称
    private String categoryId="0";//类目Id
    private String platformId="0";//平台Id
    private String minSalary="";//最低薪资
    private String maxSalary="";//最高薪资

    private List<View> popupViews = new ArrayList<>();//多条件筛选的弹窗集合
    private ListView categoryView;
    private ListView platformView;

    private int page = 1;
    private static final int PAGE_SIZE = 10;//请求每页的数据量
    private boolean isRefresh;//是下拉刷新还是加载更多  false：加载更多，true：下拉刷新

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
                isLoginStartActivity(NotificationActivity.class);
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
                initData();
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
            initData();
            refresh();
    }

    public MainFragment() {
    }
    public static MainFragment newInstance(String info) {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        initView();
        initRecycleView();
        initAdapter();
        initHeadView();
        initRefreshLayout();
        return view;
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }

    private void initData() {
        getPresenter().getConditionInfo();//获取检索条件
        getPresenter().getBannerInfo();//获取轮播图
    }

    private void initView() {
        //测试tabView扩展功能
        @SuppressLint("RestrictedApi") View screenView = getLayoutInflater(new Bundle()).inflate(R.layout.item_dropdown_screening, null);
        screenView.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));

        //招聘类目
        categoryView = new ListView(getContext());
        categoryView.setDividerHeight(0);
        categoryView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (MyUtils.isFastClick1()){
                    return;
                }
                categoryId = categoryBeans.get(position).getId();
                categoryAdapter.setCheckItem(position);
                dropDownMenu.setTabText(categoryStrs.get(position));
                dropDownMenu.closeMenu();
                refresh();
            }
        });

        //招聘平台
        platformView = new ListView(getContext());
        platformView.setDividerHeight(0);
        platformView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (MyUtils.isFastClick1()){
                    return;
                }
                platformId =  platformBeans.get(position).getId();
                platformAdapter.setCheckItem(position);
                dropDownMenu.setTabText(platformStrs.get(position));
                dropDownMenu.closeMenu();
                refresh();
            }
        });


        //添加
        popupViews.add(categoryView);
        popupViews.add(platformView);
        //展示条件筛选结果的内容控件
        @SuppressLint("RestrictedApi") View contentView =  getLayoutInflater(new Bundle()).inflate(R.layout.include_recyclerview_refresh, null);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        swipeRefreshLayout = (SwipeRefreshLayout) contentView.findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView) contentView.findViewById(R.id.recyclerView);
        dropDownMenu.setDropDownMenu(Arrays.asList(tabTexts), popupViews, contentView);
        //添加自定义的tabView(筛选)
        dropDownMenu.addTab(screenView, 2);
        screenView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyUtils.isFastClick1()){
                    return;
                }
                if (dropDownMenu.isActive()) {
                    dropDownMenu.closeMenu();
                }else {
                    //打开筛选界面（期望薪资）
                    Intent intent = new Intent(getActivity(), ScreeningSalaryActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(ConstantUtil.KEY_DATA, minSalary);//最低薪资
                    bundle.putString(ConstantUtil.KEY_POSITION,maxSalary);//最高薪资
                    intent.putExtras(bundle);
                    startActivityForResult(intent,ConstantUtil.REQUESTCODE_SCREENING);
                    getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                }
            }
        });
    }

    /**
     * 初始化检索结果 RecycleView
     */
    private void initRecycleView() {
        //设置刷新圈圈的颜色
        swipeRefreshLayout.setColorSchemeColors(Color.rgb(255, 179, 33));
        swipeRefreshLayout.setRefreshing(true);
        //设置分割线与垂直方向布局
        recyclerView.addItemDecoration(MyUtils.drawDivider(getContext(), LinearLayout.VERTICAL, R.drawable.divider_f0f0f0_14));
        MyLayoutManager manager = new MyLayoutManager(getContext(), LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
    }

    /**
     * 为 RecyclerView 加载 Adapter
     */
    private void initAdapter() {
        mAdapter = new PositionAdapter(getContext());
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        });
        recyclerView.setAdapter(mAdapter);
        //子控件点击事件
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (MyUtils.isFastClick1()){
                    return;
                }
                //打开招聘详情界面（传递招聘需求id）
                Intent intent = new Intent(getActivity(), PositionDetailsActivity.class);
                intent.putExtra(ConstantUtil.KEY_DATA,mAdapter.getData().get(position).getId());
                startActivity(intent);
            }
        });
    }

    /**
     * 为 RecyclerView 添加头部控件（轮播图）
     */
    private void initHeadView() {
        View hvBanner = getActivity().getLayoutInflater().inflate(R.layout.item_hv_main_banner, (ViewGroup) recyclerView.getParent(), false);
        banner = (Banner) hvBanner.findViewById(R.id.banner);
        mAdapter.addHeaderView(hvBanner);
    }

    /**
     * 下拉刷新 ---  下拉加载更多
     */
    private void initRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
                refresh();
            }
        });
    }

    //刷新
    private void refresh() {
        page = 1;
        isRefresh=true;
        //这里的作用是防止下拉刷新的时候还可以上拉加载
        mAdapter.setEnableLoadMore(false);
        getPresenter().getInfo(page);
    }
    //加载更多
    private void loadMore() {
        isRefresh=false;
        getPresenter().getInfo(page);
    }

    /**
     * IMainView
     */
    @Override
    public AccountInfoData getUserInfo() {
        AccountInfoData userInfo = LoginUtil.getinit().getUserInfo();
        return userInfo;
    }
    @Override
    public String getCategoryId() {
        return categoryId;
    }
    @Override
    public String getPlatformId() {
        return platformId;
    }
    @Override
    public String getMinSalary() {
        return minSalary;
    }
    @Override
    public String getMaxSalary() {
        return maxSalary;
    }
    @Override//获取职位检索条件
    public void onGetConditionSuccessResult(ConditionInfoData result) {
        ArrayList<ConditionBean> categoryList = result.getBgatList();
        ArrayList<ConditionBean> platformList = result.getBgapList();
        categoryList.add(0,new ConditionBean("0","全部"));
        platformList.add(0,new ConditionBean("0","全部"));
        categoryBeans = categoryList;
        platformBeans = platformList;
        categoryStrs.clear();
        platformStrs.clear();
        for (int i = 0; i < categoryBeans.size() ; i++) {
            categoryStrs.add(categoryBeans.get(i).getName());
        }
        for (int i = 0; i < platformBeans.size() ; i++) {
            platformStrs.add(platformBeans.get(i).getName());
        }
        categoryAdapter = new DropDownListAdapter(getContext(), categoryStrs);
        platformAdapter = new DropDownListAdapter(getContext(), platformStrs);
        categoryView.setAdapter(categoryAdapter);
        platformView.setAdapter(platformAdapter);
    }
    @Override
    public void onGetConditionFailureResult(String result) {
         ToastUtil.myToast(result);
    }
    @Override//获取轮播图
    public void onGetBannerSuccessResult(BannerInfoData result) {
        banner.setVisibility(View.VISIBLE);
        initBanner(result.getTop_ad());
    }
    @Override
    public void onGetBannerFailureResult(String result) {
        banner.setVisibility(View.GONE);
        if (!result.equals("")){
             ToastUtil.myToast(result);
        }
    }
    @Override//获取职位招聘列表
    public void onRequestSuccess(List<PositionListInfoData> tData) {
        setData(tData);
        if (isRefresh){//停止刷新
            mAdapter.setEnableLoadMore(true);
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onRequestFailure(String result) {
        String toastStr=result;
        setData(null);
        //如果是刷新，没数据代表全部没数据
        if (isRefresh){
            if (result.equals("")){
                toastStr="暂无任何职位招聘！";
            }
            mAdapter.setEnableLoadMore(true);
            swipeRefreshLayout.setRefreshing(false);
        }else {//如果是加载，没数据代表加载完毕
            if (result.equals("")){//没数据
                toastStr="已经到底了哦！";
                mAdapter.loadMoreEnd();
            }else {//请求出错
                mAdapter.loadMoreFail();
            }
        }
         ToastUtil.myToast(toastStr);
    }

    /**
     * 初始化广告轮播
     */
    private void initBanner(List<BannerBean> bannerBeans) {
        /*设置banner样式*/
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
            mAdapter.setNewData(data);
        } else {
            if (size > 0) {
                mAdapter.addData(data);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            mAdapter.loadMoreEnd(isRefresh);
        } else {
            mAdapter.loadMoreComplete();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case ConstantUtil.REQUESTCODE_SCREENING:
                    if (resultCode==ConstantUtil.RESULTCODE_SCREENING){
                        //接收薪资筛选界面输入的的最低、最高薪资
                        String[] screeningIdStr = data.getStringArrayExtra(ConstantUtil.KEY_POSITION);
                        minSalary = screeningIdStr[0];
                        maxSalary = screeningIdStr[1];
                        refresh();
                    }
                    break;
                default:
                    break;
            }
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        categoryStrs.clear();
        platformStrs.clear();
        popupViews.clear();

        categoryId="0";//类目Id
        platformId="0";//平台Id
        minSalary="";//最低薪资
        maxSalary="";//最高薪资
    }
}
