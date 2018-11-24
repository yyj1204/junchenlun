package com.wktx.www.emperor.ui.fragment.message;
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

import com.bigkoo.pickerview.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.main.message.MessageListInfoData;
import com.wktx.www.emperor.basemvp.ALazyLoadFragment;
import com.wktx.www.emperor.presenter.main.MessagePresenter;
import com.wktx.www.emperor.ui.activity.main.message.MessageDetailsActivity;
import com.wktx.www.emperor.ui.adapter.main.MessageNoticeListAdapter;
import com.wktx.www.emperor.ui.view.main.IMessageView;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.DateUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.utils.ToastUtil;
import com.wktx.www.emperor.widget.MyLayoutManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 消息通知---公告
 */
public class MessageNoticeFragment extends ALazyLoadFragment<IMessageView,MessagePresenter> implements IMessageView {
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_beginTime)
    TextView tvBeginTime;
    @BindView(R.id.tv_endTime)
    TextView tvEndTime;
    @BindView(R.id.tv_query)
    TextView tvQuery;

    //RecyclerView 适配器
    private MessageNoticeListAdapter adapter;

    private boolean isFirstVisible;//是否第一次创建可见

    //系统日历控件
    private Calendar curCalendar = Calendar.getInstance();
    private String beginDateStr;//项目开始时间"yyyy-MM-dd"格式
    private String endDateStr;//项目结束时间"yyyy-MM-dd"格式
    private long beginDateLong;//项目开始时间的时间戳
    private long endDateLong;//项目结束时间的时间戳


    @OnClick({R.id.tv_beginTime,R.id.tv_endTime,R.id.tv_query})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tv_beginTime://开始时间
                pickDate(tvBeginTime,true,"选择开始时间");
                break;
            case R.id.tv_endTime://结束时间
                pickDate(tvEndTime,false,"选择结束时间");
                break;
            case R.id.tv_query://查询
                refresh();
                break;
            default:
                break;
        }
    }


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

    public MessageNoticeFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_notice, container, false);
        ButterKnife.bind(this, view);
        //初始化开始结束时间
        initTime();
        initRecycleView();
        initAdapter();
        initRefreshLayout();
        return view;
    }

    @Override
    protected MessagePresenter createPresenter() {
        return new MessagePresenter();
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
        adapter = new MessageNoticeListAdapter(getContext());
        recyclerView.setAdapter(adapter);
        //子控件点击事件
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (MyUtils.isFastClick1()){
                    return;
                }
                //将公告ID 传递给 MessageDetailsActivity
                MessageListInfoData info = (MessageListInfoData) adapter.getData().get(position);
                Intent intent = new Intent(getActivity(), MessageDetailsActivity.class);
                intent.putExtra(ConstantUtil.KEY_POSITION,ConstantUtil.ACTIVITY_GG);
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
        getPresenter().onMessageList("0");
    }

    /**
     * 初始化项目开始结束时间
     */
    private void initTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //结束时间---推迟日历一天
        curCalendar.add(Calendar.DAY_OF_MONTH,+1);
        Date endDate = curCalendar.getTime();
        endDateStr = sdf.format(endDate);
        //将结束时间转为时间戳(只转日期不转时间，方便pickDate方法做判断)
        endDateLong = Long.parseLong(DateUtil.getCustomType2Timestamp(endDateStr,"yyyy-MM-dd"))* 1000L;
        tvEndTime.setText(endDateStr);

        //开始时间---往前日历一个月
        curCalendar.add(Calendar.MONTH,-1);
        Date beginDate = curCalendar.getTime();
        beginDateStr = sdf.format(beginDate);
        //将开始时间转为时间戳(只转日期不转时间，方便pickDate方法做判断)
        beginDateLong = Long.parseLong(DateUtil.getCustomType2Timestamp(beginDateStr,"yyyy-MM-dd"))* 1000L;
        tvBeginTime.setText(beginDateStr);
    }

    //选择项目开始结束时间
    private void  pickDate(final TextView tvDate, final boolean isBegin, String titleStr) {
        TimePickerView pvTime = new TimePickerView.Builder(getContext(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String selectDateStr = sdf.format(date);
                long selectDateLong = date.getTime();

                //如果是开始时间，则选中的日期不能在结束时间之后
                if (isBegin){
                    if (selectDateLong > endDateLong){
                        ToastUtil.myToast("开始时间需在结束时间之前!");
                    }else {
                        beginDateLong = selectDateLong;//赋值，用在结束时间做判断
                        tvDate.setText(selectDateStr);
                    }
                }else {//如果是结束时间，则选中的日期不能在开始时间之前
                    if(selectDateLong >= beginDateLong) {
                        endDateLong = selectDateLong;//赋值，用在开始日期做判断
                        tvDate.setText(selectDateStr);
                    }else {
                        ToastUtil.myToast("结束时间需在开始时间之后!");
                    }
                }
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})//默认全部显示,年月日时分秒
                .setTitleText(titleStr)//标题文字
                .setTitleSize(16)//标题文字大小
                .setContentSize(14)//滚轮文字大小
                .setSubmitColor(getContext().getResources().getColor(R.color.color_ffb321))
                .setCancelColor(Color.GRAY)
                .isCenterLabel(false)
                .setOutSideCancelable(false)
                .build();
        pvTime.setDate(DateUtil.getNYR2Calendar(tvDate.getText().toString()));
        pvTime.show();
    }

    /**
     * IMessageView
     */
    @Override
    public String getBeginTime() {
        return tvBeginTime.getText().toString().trim();
    }
    @Override
    public String getEndTime() {
        return tvEndTime.getText().toString().trim();
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
            ToastUtil.myToast("暂无任何公告消息！");
        }else {
            recyclerView.setBackgroundResource(R.drawable.img_nothing_net);
            ToastUtil.myToast(result);
        }
        adapter.setNewData(null);
        swipeRefreshLayout.setRefreshing(false);
    }
}
