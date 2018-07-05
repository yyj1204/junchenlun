package com.wktx.www.subjects.ui.fragment.notification;


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
import com.wktx.www.subjects.apiresult.main.message.MessageListInfoData;
import com.wktx.www.subjects.ui.activity.main.notification.NotificationDetailsActivity;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.login.AccountInfoData;
import com.wktx.www.subjects.basemvp.ALazyLoadBaseFragment;
import com.wktx.www.subjects.presenter.main.NotificationPresenter;
import com.wktx.www.subjects.ui.adapter.main.NotificationRemindListAdapter;
import com.wktx.www.subjects.ui.view.IView;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.LoginUtil;
import com.wktx.www.subjects.utils.MyUtils;
import com.wktx.www.subjects.widget.MyLayoutManager;
import com.wktx.www.subjects.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 消息通知---提醒
 */
public class NotificationRemindFragment extends ALazyLoadBaseFragment<IView,NotificationPresenter> implements IView<List<MessageListInfoData>> {

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    //RecyclerView 适配器
    private NotificationRemindListAdapter adapter;

    private boolean isFirstVisible;//是否第一次创建可见

    /**
     * 片段是否可见
     * @param isVisible falese 可见 -> 不可见
     *  此时 isFirstVisible=true，在片段不可见时候将 isFirstVisible = false
     * @param isVisible true  不可见 -> 可见
     *  如果 isFirstVisible=false 说明已经创建过，防止多次请求接口，加判断
     */
    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible){
            if (!isFirstVisible){
                refresh();
            }
        }else {
            if (isFirstVisible){
                isFirstVisible=false;
            }
        }
    }

    /**
     * 片段第一次被创建（可见）时才会执行到这个方法
     * 加载数据
     * isFirstVisible=true
     */
    @Override
    protected void onFragmentFirstVisible() {
        refresh();
        isFirstVisible=true;
    }


    public NotificationRemindFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification_common, container, false);
        ButterKnife.bind(this, view);
        initRecycleView();
        initAdapter();
        initRefreshLayout();
        return view;
    }

    @Override
    protected NotificationPresenter createPresenter() {
        return new NotificationPresenter();
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
        MyLayoutManager manager = new MyLayoutManager(getContext(), LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
    }

    /**
     * 为 RecyclerView 加载 Adapter
     */
    private void initAdapter() {
        adapter = new NotificationRemindListAdapter(getContext());
        recyclerView.setAdapter(adapter);
        //子控件点击事件
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (MyUtils.isFastClick1()){
                    return;
                }
                //将提醒ID 传递给 NotificationDetailsActivity
                MessageListInfoData info = (MessageListInfoData) adapter.getData().get(position);
                Intent intent = new Intent(getActivity(), NotificationDetailsActivity.class);
                intent.putExtra(ConstantUtil.KEY_POSITION,ConstantUtil.ACTIVITY_TX);
                intent.putExtra(ConstantUtil.KEY_DATA,info.getRec_id());
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
        getPresenter().onMessageList("2");
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
    public void onRequestSuccess(List<MessageListInfoData> tData) {
        recyclerView.setBackgroundResource(R.color.color_f0f0f0);
        adapter.setNewData(tData);
        swipeRefreshLayout.setRefreshing(false);
    }
    @Override
    public void onRequestFailure(String result) {
        if (result.equals("")){//没数据
            recyclerView.setBackgroundResource(R.drawable.img_nothing);
            ToastUtil.myToast("暂无任何提醒消息！");
        }else {
            recyclerView.setBackgroundResource(R.drawable.img_nothing_net);
             ToastUtil.myToast(result);
        }
        adapter.setNewData(null);
        swipeRefreshLayout.setRefreshing(false);
    }
}
