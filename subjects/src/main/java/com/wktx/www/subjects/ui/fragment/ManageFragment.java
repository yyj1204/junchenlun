package com.wktx.www.subjects.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wktx.www.subjects.apiresult.manage.WorkListInfoData;
import com.wktx.www.subjects.ui.activity.MainActivity;
import com.wktx.www.subjects.ui.activity.main.notification.NotificationActivity;
import com.wktx.www.subjects.apiresult.login.AccountInfoData;
import com.wktx.www.subjects.basemvp.ALazyLoadBaseFragment;
import com.wktx.www.subjects.presenter.manage.ManagePresenter;
import com.wktx.www.subjects.ui.activity.manage.FireDetailsActivity;
import com.wktx.www.subjects.ui.activity.manage.ManageActivity;
import com.wktx.www.subjects.ui.activity.login.LoginActivity;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.ui.activity.message.InviteDetailsActivity;
import com.wktx.www.subjects.ui.adapter.manage.WorkListAdapter;
import com.wktx.www.subjects.ui.view.IView;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.LoginUtil;
import com.wktx.www.subjects.utils.MyUtils;
import com.wktx.www.subjects.widget.MyLayoutManager;
import com.wktx.www.subjects.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 管理片段
 */
public class ManageFragment extends ALazyLoadBaseFragment<IView,ManagePresenter> implements IView<List<WorkListInfoData>> {
    @BindView(R.id.linear_nothing)
    LinearLayout llNothing;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_onlineExperience)
    TextView tvExperience;

    private WorkListAdapter mAdapter;//RecyclerView 适配器

    private int page = 1;
    private static final int PAGE_SIZE = 10;//请求每页的数据量
    private boolean isRefresh;//是下拉刷新还是加载更多  false：加载更多，true：下拉刷新


    @OnClick({R.id.iv_message,R.id.tv_onlineExperience,R.id.tv_selectWork})
    public void MyOnclick(View view) {
        if (MyUtils.isFastClick1()){
            return;
        }
        switch (view.getId()) {
            case R.id.iv_message://消息通知
                isLoginStartActivity(NotificationActivity.class);
                break;
            case R.id.tv_onlineExperience://体验在线办公
                if (getUserInfo()==null){
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }else {
                    //定位到 MainFragment
                    MainActivity activity = (MainActivity) getActivity();
                    activity.setCurrenPager(0);
                }
                break;
            case R.id.tv_selectWork://挑合适的工作
                //定位到 MainFragment
                MainActivity activity = (MainActivity) getActivity();
                activity.setCurrenPager(0);
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
            if (getUserInfo()==null){
                tvExperience.setText("未登录，去登录");
                llNothing.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setVisibility(View.GONE);
            }else {
                if (!isRefresh){
                    llNothing.setVisibility(View.GONE);
                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                    refresh();
                }
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
        if (getUserInfo()==null){
            tvExperience.setText("未登录，去登录");
            llNothing.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setVisibility(View.GONE);
        }else {
            llNothing.setVisibility(View.GONE);
            swipeRefreshLayout.setVisibility(View.VISIBLE);
            refresh();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserInfo()==null){
            tvExperience.setText("未登录，去登录");
            llNothing.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setVisibility(View.GONE);
        }else {
            llNothing.setVisibility(View.GONE);
            swipeRefreshLayout.setVisibility(View.VISIBLE);
            refresh();
        }
    }

    public ManageFragment() {
    }
    public static ManageFragment newInstance(String info) {
        Bundle args = new Bundle();
        ManageFragment fragment = new ManageFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage, container, false);
        ButterKnife.bind(this,view);
        initRecycleView();
        initAdapter();
        initRefreshLayout();
        return view;
    }

    @Override
    protected ManagePresenter createPresenter() {
        return new ManagePresenter();
    }

    /**
     * 初始化 RecycleView
     */
    private void initRecycleView() {
        //设置刷新圈圈的颜色
        swipeRefreshLayout.setColorSchemeColors(Color.rgb(255, 179, 33));
        swipeRefreshLayout.setRefreshing(true);
        //设置分割线与垂直方向布局
        recyclerView.addItemDecoration(MyUtils.drawDivider(getContext(), LinearLayout.VERTICAL, R.drawable.divider_f0f0f0_14));
        MyLayoutManager myLayoutManager = new MyLayoutManager(getContext(), LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(myLayoutManager);
    }

    /**
     * 为 RecyclerView 加载 Adapter
     */
    private void initAdapter() {
        mAdapter = new WorkListAdapter(getContext());
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
        Intent intent = new Intent(getActivity(),clazz );
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
        getPresenter().getInfo(page);
    }
    //加载更多
    private void loadMore() {
        isRefresh=false;
        getPresenter().getInfo(page);
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
    public void onRequestSuccess(List<WorkListInfoData> tData) {
        setData(tData);
        llNothing.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        recyclerView.setBackgroundResource(R.color.color_f0f0f0);
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
                toastStr="暂无任何工作！";
                tvExperience.setText("暂无工作，在线体验办公");
                llNothing.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setVisibility(View.GONE);
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
}
