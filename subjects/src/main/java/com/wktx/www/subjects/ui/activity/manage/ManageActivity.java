package com.wktx.www.subjects.ui.activity.manage;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.subjects.apiresult.manage.WorkDetailsInfoData;
import com.wktx.www.subjects.apiresult.manage.WorkListInfoData;
import com.wktx.www.subjects.presenter.manage.ManagePresenter;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.login.AccountInfoData;
import com.wktx.www.subjects.basemvp.ABaseActivity;
import com.wktx.www.subjects.ui.adapter.manage.WorkManageAdapter;
import com.wktx.www.subjects.ui.view.IView;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.DateUtil;
import com.wktx.www.subjects.utils.GlideUtil;
import com.wktx.www.subjects.utils.LoginUtil;
import com.wktx.www.subjects.utils.MyUtils;
import com.wktx.www.subjects.widget.MyLayoutManager;
import com.wktx.www.subjects.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 管理我的工作
 */
public class ManageActivity extends ABaseActivity<IView,ManagePresenter> implements IView<WorkDetailsInfoData>, View.OnClickListener{

    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    //雇主信息
    private CircleImageView civHead;
    private ImageView ivState;
    private TextView tvName;
    private TextView tvHireTime;
    //工资、报告数、签到数
    private TextView tvSalary;
    private TextView tvReport;
    private TextView tvAttendance;

    private WorkManageAdapter mAdapter;

    private String hireId;//雇佣id
    private String fireId;//解雇id
    private WorkDetailsInfoData workDetailsInfoData;//工作详情信息


    @OnClick({R.id.tb_IvReturn})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn://返回
                finish();
                break;
            default:
                break;
        }
    }
    @Override
    public void onClick(View view) {
        if (MyUtils.isFastClick1()){
            return;
        }
        switch (view.getId()) {
            case R.id.tv_leave://请假
                //TODO
                break;
            case R.id.linear_manage_salary://工资
                toActivity(ManageSalaryActivity.class);
                break;
            case R.id.linear_manage_report://报告
                Intent intent = new Intent(this, ManageReportActivity.class);
                intent.putExtra(ConstantUtil.KEY_DATA,workDetailsInfoData);
                startActivity(intent);
                break;
            case R.id.linear_manage_attendance://签到
                toActivity(ManageAttendanceActivity.class);
                break;
            case R.id.linear_manage_saleroom://销售额
                Intent intent1 = new Intent(this, ManageSaleroomActivity.class);
                intent1.putExtra(ConstantUtil.KEY_DATA,workDetailsInfoData);
                startActivity(intent1);
                break;
            default:
                break;
        }
    }

    private void toActivity(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra(ConstantUtil.KEY_POSITION,hireId);
        startActivity(intent);
    }
    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_manage);

        initData();
        initRecycleView();
        initAdapter();
        initHeadView();
        initRefreshLayout();
    }

    /**
     * 接收 ManageFragment 传递过来的 WorkListInfoData(雇佣id,解雇id)
     */
    private void initData() {
        WorkListInfoData workListInfoData = (WorkListInfoData) getIntent().getSerializableExtra(ConstantUtil.KEY_DATA);
        hireId = workListInfoData.getHire_id();
        fireId = workListInfoData.getDismissal_id();
        getPresenter().getWorkInfo(hireId);
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
        recyclerView.addItemDecoration(MyUtils.drawDivider(this, LinearLayout.VERTICAL, R.drawable.divider_f0f0f0_14));
        MyLayoutManager myLayoutManager = new MyLayoutManager(this, LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(myLayoutManager);
    }

    /**
     * 为 RecyclerView 加载 Adapter
     */
    private void initAdapter() {
        mAdapter = new WorkManageAdapter(ManageActivity.this);
        recyclerView.setAdapter(mAdapter);
        //子控件点击事件
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (MyUtils.isFastClick1()){
                    return;
                }

                Intent intent = new Intent();
                //截止日期跟当前日期比较
                String endTime = mAdapter.getData().get(position).getEnd_time();
                String currentTime = DateUtil.getCurrentDateNYRSFM2();
                boolean isBigger = DateUtil.compareDate2Bigger3(endTime, currentTime, "yyyy-MM-dd HH:mm:ss");
                if (isBigger){//如果isBigger=true，说明截止日期未到
                    String reportId = mAdapter.getData().get(position).getReport_id();
                    if (reportId==null||reportId.equals("0")){//打开报告提交界面
                        intent.putExtra(ConstantUtil.KEY_DATA,mAdapter.getData().get(position));
                        intent.setClass(ManageActivity.this, ReportReleaseActivity.class);
                    }else {//打开工作报告列表
                        intent.putExtra(ConstantUtil.KEY_DATA,mAdapter.getData().get(position).getId());
                        intent.setClass(ManageActivity.this, ReportListActivity.class);
                    }
                }else {//打开工作报告列表
                    intent.putExtra(ConstantUtil.KEY_DATA,mAdapter.getData().get(position).getId());
                    intent.setClass(ManageActivity.this, ReportListActivity.class);
                }
                startActivity(intent);
            }
        });
    }

    /**
     * 为 RecyclerView 添加头部控件（管理）
     */
    private void initHeadView() {
        View hv = getLayoutInflater().inflate(R.layout.item_hv_manage, null);
        mAdapter.addHeaderView(hv);

        //雇主信息
        civHead = hv.findViewById(R.id.iv_head);
        ivState = hv.findViewById(R.id.iv_manage_state);
        tvName = hv.findViewById(R.id.tv_bossName);
        tvHireTime = hv.findViewById(R.id.tv_hireTime);
        //工资、报告数、签到数
        tvSalary = hv.findViewById(R.id.tv_manage_salary);
        tvReport = hv.findViewById(R.id.tv_manage_report);
        tvAttendance = hv.findViewById(R.id.tv_manage_attendance);

        //点击事件
        hv.findViewById(R.id.tv_leave).setOnClickListener(this);
        hv.findViewById(R.id.linear_manage_salary).setOnClickListener(this);
        hv.findViewById(R.id.linear_manage_report).setOnClickListener(this);
        hv.findViewById(R.id.linear_manage_attendance).setOnClickListener(this);
        hv.findViewById(R.id.linear_manage_saleroom).setOnClickListener(this);
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
        getPresenter().getWorkInfo(hireId);
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
    public void onRequestSuccess(WorkDetailsInfoData tData) {
        workDetailsInfoData = tData;
        //头像
        if (tData.getHead_pic()==null||tData.getHead_pic().equals("")){
            civHead.setImageResource(R.drawable.img_mine_head);
        }else {
            GlideUtil.loadImage(tData.getHead_pic(),R.drawable.img_mine_head,civHead);
        }
        //名字
        tvName.setText(tData.getNickname());
        //雇佣时间
        String startTime = tData.getProject_start_time();
        String endTime = tData.getProject_end_time();
        String startTimeLong = DateUtil.getCustomType2Timestamp(startTime, "yyyy-MM-dd HH:mm:ss");
        String startTimeStr = DateUtil.getTimestamp2CustomType(startTimeLong, "yyyy.MM.dd");
        String endTimeLong = DateUtil.getCustomType2Timestamp(endTime, "yyyy-MM-dd HH:mm:ss");
        String endTimeStr = DateUtil.getTimestamp2CustomType(endTimeLong, "yyyy.MM.dd");
        tvHireTime.setText(startTimeStr+"-"+endTimeStr);

        //雇佣状态
        String status = tData.getStatus();
        //雇佣状态
        if (!fireId.equals("0")) {//解雇中
            ivState.setImageResource(R.drawable.ic_manage_state5ing);
        }else {
            if (status.equals("1")) {//合作中
                ivState.setImageResource(R.drawable.ic_manage_state1);
            } else if (status.equals("2")) {//请假中
                ivState.setImageResource(R.drawable.ic_manage_state2);
            } else if (status.equals("3")) {//暂停中
                ivState.setImageResource(R.drawable.ic_manage_state3);
            } else if (status.equals("4")) {//投诉中
                ivState.setImageResource(R.drawable.ic_manage_state4);
            } else if (status.equals("5")) {//被解雇
                ivState.setImageResource(R.drawable.ic_manage_state5);
            } else if (status.equals("6")) {//已完结
                ivState.setImageResource(R.drawable.ic_manage_state6);
            } else if (status.equals("7")) {//已退款
                ivState.setImageResource(R.drawable.ic_manage_state7);
            } else if (status.equals("9")) {//续约中
                ivState.setImageResource(R.drawable.ic_manage_state9);
            }
        }

        //工资、报告数、签到数
        tvSalary.setText(tData.getPaid_wages());
        tvReport.setText(tData.getReportNum());
        tvAttendance.setText(tData.getSignIn());

        //安排工作列表
        mAdapter.setNewData(tData.getArrangementWork());
        swipeRefreshLayout.setRefreshing(false);
    }
    @Override
    public void onRequestFailure(String result) {
        ToastUtil.myToast(result);
        finish();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtil.cancleMyToast();
    }
}
