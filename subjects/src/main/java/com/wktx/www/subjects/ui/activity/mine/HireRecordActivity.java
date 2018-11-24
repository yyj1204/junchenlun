package com.wktx.www.subjects.ui.activity.mine;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.main.condition.ConditionBean;
import com.wktx.www.subjects.apiresult.manage.WorkListInfoData;
import com.wktx.www.subjects.basemvp.ABaseActivity;
import com.wktx.www.subjects.presenter.mine.HireRecordPresenter;
import com.wktx.www.subjects.ui.activity.manage.FireDetailsActivity;
import com.wktx.www.subjects.ui.activity.manage.ManageActivity;
import com.wktx.www.subjects.ui.activity.message.InviteDetailsActivity;
import com.wktx.www.subjects.ui.adapter.DropDownListAdapter;
import com.wktx.www.subjects.ui.adapter.manage.WorkListAdapter;
import com.wktx.www.subjects.ui.view.IView;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.MyUtils;
import com.wktx.www.subjects.utils.ToastUtil;
import com.wktx.www.subjects.widget.DropDownMenu;
import com.wktx.www.subjects.widget.MyLayoutManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人中心-雇佣记录
 */

public class HireRecordActivity extends ABaseActivity<IView,HireRecordPresenter> implements IView<List<WorkListInfoData>> {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private DropDownListAdapter workStateAdapter;//工作状态筛选适配器
    private WorkListAdapter mAdapter;//RecyclerView 适配器

    private String tabTexts[] = {"工作状态"};
    private List<ConditionBean> workStateBeans = new ArrayList<>();//工作状态集合
    private List<String> workStateStrs = new ArrayList<>();//工作状态名称
    private String workStateId="0";//工作状态  0：全部 1：工作中 2：已完结 3.未正常完结
    private List<View> popupViews = new ArrayList<>();//多条件筛选的弹窗集合
    private ListView workStateView;

    private int page = 1;
    private static final int PAGE_SIZE = 10;//请求每页的数据量
    private boolean isRefresh;//是下拉刷新还是加载更多  false：加载更多，true：下拉刷新

    @OnClick({R.id.tb_IvReturn})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hire_record);
        ButterKnife.bind(this);
        //设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_hire_record);
        initData();
        initView();
        initRecycleView();
        initAdapter();
        initRefreshLayout();
    }

    @Override
    protected HireRecordPresenter createPresenter() {
        return new HireRecordPresenter();
    }

    private void initData() {
        workStateBeans.add(new ConditionBean("0","全部"));
        workStateBeans.add(new ConditionBean("1","担保中的工作"));
        workStateBeans.add(new ConditionBean("2","已完结的工作"));
        workStateBeans.add(new ConditionBean("3","未正常完结的工作"));
        for (int i = 0; i < 3 ; i++) {
            workStateStrs.add(workStateBeans.get(i).getName());
        }
    }

    private void initView() {
        //工作状态
        workStateView = new ListView(HireRecordActivity.this);
        workStateView.setDividerHeight(0);
        workStateView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (MyUtils.isFastClick1()){
                    return;
                }
                workStateId = workStateBeans.get(position).getId();
                workStateAdapter.setCheckItem(position);
                dropDownMenu.setTabText(workStateStrs.get(position));
                dropDownMenu.closeMenu();
                refresh();
            }
        });
        workStateAdapter = new DropDownListAdapter(HireRecordActivity.this, workStateStrs);
        workStateView.setAdapter(workStateAdapter);
        //添加
        popupViews.add(workStateView);
        //展示条件筛选结果的内容控件
        View contentView =  getLayoutInflater().inflate(R.layout.include_recyclerview_refresh, null);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        swipeRefreshLayout = (SwipeRefreshLayout) contentView.findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView) contentView.findViewById(R.id.recyclerView);
        dropDownMenu.setDropDownMenu(Arrays.asList(tabTexts), popupViews, contentView);
    }

    /**
     * 初始化 RecycleView
     */
    private void initRecycleView() {
        //设置刷新圈圈的颜色
        swipeRefreshLayout.setColorSchemeColors(Color.rgb(255, 179, 33));
        swipeRefreshLayout.setRefreshing(true);
        //设置分割线与垂直方向布局
        recyclerView.addItemDecoration(MyUtils.drawDivider(HireRecordActivity.this, LinearLayout.VERTICAL, R.drawable.divider_f0f0f0_14));
        MyLayoutManager manager = new MyLayoutManager(HireRecordActivity.this, LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
    }

    /**
     * 为 RecyclerView 加载 Adapter
     */
    private void initAdapter() {
        mAdapter = new WorkListAdapter(HireRecordActivity.this);
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

                //雇佣状态 0:未开始 1:合作中 2:请假中 3:暂停中 4:投诉中 5:被解雇 6:已完结 7:已退款 8:已取消 9:续约中 10:待入职
                String hireStatus = mAdapter.getData().get(position).getStatus();
                switch (hireStatus){
                    case "1":
                    case "2":
                    case "3":
                    case "4":
                    case "5":
                    case "6":
                    case "7":
                    case "9"://雇佣id 传递给 ManageActivity
                        toActivity(ManageActivity.class,mAdapter.getData().get(position).getHire_id());
                        break;
                    case "8"://已取消（支付超时、取消、拒绝）
                        switch (mAdapter.getData().get(position).getStatus_desc()){
                            case ConstantUtil.HIRESTATE_OVERTIME:
                                ToastUtil.myToast("该订单超时未支付已取消！");
                                break;
                            case ConstantUtil.HIRESTATE_CANCEL:
                                ToastUtil.myToast("该雇主已取消该订单！");
                                break;
                            case ConstantUtil.HIRESTATE_REFUSED:
                                ToastUtil.myToast("您已拒绝该雇主的入职邀请！");
                                break;
                            default:
                                break;
                        }
                        break;
                    case "10"://打开邀请详情界面
                        toActivity(InviteDetailsActivity.class,mAdapter.getData().get(position).getHire_id());
                        break;
                    default:
                        break;
                }
            }
        });

        //查看解雇详情点击事件
        mAdapter.setOnFireDetailsListener(new WorkListAdapter.OnFireDetailsListener() {
            @Override
            public void onFireDetails(int position) {
                //如果解雇id不为0，解雇id 传递给 FireDetailsActivity
                toActivity(FireDetailsActivity.class,mAdapter.getData().get(position).getDismissal_id());
            }
        });
    }

    /**
     * 传递 雇佣id(解雇id) 到相关界面
     */
    private void toActivity(Class<?> clazz,String id) {
        Intent intent = new Intent(HireRecordActivity.this,clazz );
        intent.putExtra(ConstantUtil.KEY_DATA,id);
        startActivity(intent);
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
        getPresenter().getInfo(workStateId,page);
    }
    //加载更多
    private void loadMore() {
        isRefresh=false;
        getPresenter().getInfo(workStateId,page);
    }


    /**
     * IView
     */
    @Override
    public void onRequestSuccess(List<WorkListInfoData> tData) {
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
                toastStr="暂无任何雇佣记录！";
                recyclerView.setBackgroundResource(R.drawable.img_nothing_hire);
            }else {
                recyclerView.setBackgroundResource(R.drawable.img_nothing_net);
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
    public void onDestroy() {
        super.onDestroy();
        ToastUtil.cancleMyToast();
    }
}
