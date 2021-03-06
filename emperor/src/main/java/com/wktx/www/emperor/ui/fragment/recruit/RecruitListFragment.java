package com.wktx.www.emperor.ui.fragment.recruit;

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
import com.wktx.www.emperor.apiresult.recruit.recruitlist.RecruitListInfoData;
import com.wktx.www.emperor.basemvp.ALazyLoadFragment;
import com.wktx.www.emperor.presenter.recruit.recruit.RecruitListPresenter;
import com.wktx.www.emperor.ui.activity.recruit.ScreeningResumeActivity;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.recruit.retrievalcondition.Bean;
import com.wktx.www.emperor.apiresult.recruit.retrievalcondition.RetrievalConditionInfoData;
import com.wktx.www.emperor.ui.activity.recruit.resume.ResumeActivity;
import com.wktx.www.emperor.ui.adapter.DropDownListAdapter;
import com.wktx.www.emperor.ui.adapter.recruit.RecruitListAdapter;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.ui.view.recruit.IRecruitListView;
import com.wktx.www.emperor.utils.ToastUtil;
import com.wktx.www.emperor.widget.DropDownMenu;
import com.wktx.www.emperor.widget.MyLayoutManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * 招聘片段---职位检索结果
 */
public class RecruitListFragment extends ALazyLoadFragment<IRecruitListView,RecruitListPresenter> implements IRecruitListView {
    @BindView(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private int jobTypePosition;//工作类型位标
    private boolean isServiceType;//是否是客服类型
    private RetrievalConditionInfoData conditionInfoData;//检索条件

    private String[] tabTexts1 = {"擅长类目", "擅长平台"};
//    private String tabTexts2[] = {"擅长类目", "客服类型"};
    private List<Bean> categoryBeans = new ArrayList<>();//类目集合
    private List<Bean> platformBeans = new ArrayList<>();//平台（客服类型）共用集合
    private List<String> categoryStrs = new ArrayList<>();//类目名称
    private List<String> platformStrs = new ArrayList<>();//平台（客服类型）共用名称

    private List<View> popupViews = new ArrayList<>();//多条件筛选的弹窗集合
    private String categoryId="0";//类目Id
    private String platformId="0";//平台（客服类型）共用Id
    private String experienceId="0";//工作经验Id
    private String sexId="0";//性别Id

    //条件筛选适配器
    private DropDownListAdapter categoryAdapter;//类目
    private DropDownListAdapter platformAdapter;//平台（客服类型）共用
    //RecyclerView 适配器
    private RecruitListAdapter mAdapter;

    private int page = 1;
    private static final int PAGE_SIZE = 10;//请求每页的数据量
    private boolean isRefresh;//是下拉刷新还是加载更多  false：加载更多，true：下拉刷新


    public RecruitListFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recruit_list, container, false);
        ButterKnife.bind(this, view);
        initData();
        initView();
        initRecycleView();
        initAdapter();
        initRefreshLayout();
        return view;
    }

    @Override
    protected RecruitListPresenter createPresenter() {
        return new RecruitListPresenter();
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
     *接收 RecruitFragment 传递过来的检索条件结果
     */
    private void initData() {
        Bundle bundle = getArguments();
        //工作类型的位标
        jobTypePosition = bundle.getInt(ConstantUtil.KEY_POSITION);
        //检索条件
        conditionInfoData = (RetrievalConditionInfoData) bundle.getSerializable(ConstantUtil.KEY_DATA);
        //类目
        categoryBeans = conditionInfoData.getBottom().getBgat();
        platformBeans = conditionInfoData.getBottom().getBgap();
        isServiceType = conditionInfoData.getTop().getTow().get(jobTypePosition).getId().equals("2");
//        if (isServiceType){//客服类型
//            platformBeans = conditionInfoData.getBottom().getCust_service_type();
//        }else {//平台
//            platformBeans = conditionInfoData.getBottom().getBgap();
//        }
        for (int c = 0; c < categoryBeans.size() ; c++) {
            categoryStrs.add(categoryBeans.get(c).getName());
        }
        for (int p = 0; p < platformBeans.size() ; p++) {
            platformStrs.add(platformBeans.get(p).getName());
        }
    }

    /**
     * 初始化条件筛选控件
     */
    private void initView() {
        //测试tabView扩展功能
        @SuppressLint("RestrictedApi")View screenView = getLayoutInflater(new Bundle()).inflate(R.layout.item_dropdown_screening, null);
        screenView.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));

        //擅长类目
        final ListView categoryView = new ListView(getContext());
        categoryView.setDividerHeight(0);
        categoryAdapter = new DropDownListAdapter(getContext(), categoryStrs);
        categoryView.setAdapter(categoryAdapter);
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
        //擅长平台（客服类型）共用
        final ListView platformView = new ListView(getContext());
        platformView.setDividerHeight(0);
        platformAdapter = new DropDownListAdapter(getContext(), platformStrs);
        platformView.setAdapter(platformAdapter);
        platformView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (MyUtils.isFastClick1()){
                    return;
                }
                platformId = platformBeans.get(position).getId();
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
        @SuppressLint("RestrictedApi") View contentView = getLayoutInflater(new Bundle()).inflate(R.layout.include_recyclerview_refresh, null);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        swipeRefreshLayout = (SwipeRefreshLayout) contentView.findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView) contentView.findViewById(R.id.recyclerView);
        //List<String> tabTexts 所有标题,
        // List<View> popupViews 所有菜单,
        // View contentView内容
        dropDownMenu.setDropDownMenu(Arrays.asList(tabTexts1), popupViews, contentView);
//        if (isServiceType){//客服类型
//            dropDownMenu.setDropDownMenu(Arrays.asList(tabTexts2), popupViews, contentView);
//        }else {//平台
//            dropDownMenu.setDropDownMenu(Arrays.asList(tabTexts1), popupViews, contentView);
//        }
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
                    //打开筛选界面（工作经验、性别）
                    Intent intent = new Intent(getActivity(), ScreeningResumeActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt(ConstantUtil.KEY_POSITION,jobTypePosition);//工作类型的位标
                    bundle.putString("experience",experienceId);//工作经验ID
                    bundle.putString("sex",sexId);//性别ID
                    bundle.putSerializable(ConstantUtil.KEY_DATA, conditionInfoData);//检索条件
                    intent.putExtras(bundle);
                    startActivityForResult(intent,ConstantUtil.REQUESTCODE_SCREENING);
                    getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                }
            }
        });
    }

    /**
     * 初始化筛选结果 RecycleView
     */
    private void initRecycleView() {
        //设置刷新圈圈的颜色
        swipeRefreshLayout.setColorSchemeColors(Color.rgb(255, 179, 33));
        swipeRefreshLayout.setRefreshing(true);
        //设置分割线与垂直方向布局
        recyclerView.addItemDecoration(MyUtils.drawDivider(getContext(), LinearLayout.VERTICAL, R.drawable.divider_f0f0f0_6));
        MyLayoutManager manager = new MyLayoutManager(getContext(), LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
    }

    /**
     * 为 RecyclerView 加载 Adapter
     */
    private void initAdapter() {
        mAdapter = new RecruitListAdapter(getContext());
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
                //将简历ID 传递给 ResumeActivity
                RecruitListInfoData info = (RecruitListInfoData) mAdapter.getData().get(position);
                Intent intent = new Intent(getActivity(), ResumeActivity.class);
                intent.putExtra(ConstantUtil.KEY_POSITION,info.getId());
                startActivity(intent);
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
        mAdapter.setEnableLoadMore(false);
        getPresenter().onGetRecruitList(page);
    }
    //加载更多
    private void loadMore() {
        isRefresh=false;
        getPresenter().onGetRecruitList(page);
    }

    /**
     * IRecruitListView
     */
    @Override
    public String getJobTypeId() {
        return conditionInfoData.getTop().getTow().get(jobTypePosition).getId();
    }
    @Override
    public String getCategoryId() {
        return categoryId;
    }
    @Override
    public String getPlatformId() {
        return platformId;
//        if (isServiceType){
//            return "0";
//        }else {
//            //平台&客服类型共用一个参数
//            return platformId;
//        }
    }
    @Override
    public String getServiceId() {
        if (isServiceType){
            //平台&客服类型共用一个参数
            return platformId;
        }else {
            return "0";
        }
    }
    @Override
    public String getExperienceId() {
        return experienceId;
    }
    @Override
    public String getSexId() {
        return sexId;
    }
    @Override
    public void onRequestSuccess(List<RecruitListInfoData> tData) {
        recyclerView.setBackgroundResource(R.color.color_f0f0f0);
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
//                toastStr="暂无任何简历！";
                recyclerView.setBackgroundResource(R.drawable.img_nothing);
            }else {
                recyclerView.setBackgroundResource(R.drawable.img_nothing_net);
                ToastUtil.myToast(toastStr);
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
            ToastUtil.myToast(toastStr);
        }
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
                        //接收经验、性别筛选界面选中的值
                        String[] screeningIdStr = data.getStringArrayExtra(ConstantUtil.KEY_POSITION);
                        experienceId = screeningIdStr[0];
                        sexId = screeningIdStr[1];
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
        platformId="0";//平台（客服类型）Id
        experienceId="0";//工作经验Id
        sexId="0";//性别Id
    }
}