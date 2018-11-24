package com.wktx.www.subjects.ui.activity.manage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.manage.WorkDetailsInfoData;
import com.wktx.www.subjects.apiresult.message.TaskListInfoData;
import com.wktx.www.subjects.ui.activity.manage.report.ReportListActivity;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.GlideUtil;
import com.wktx.www.subjects.utils.MyUtils;
import com.wktx.www.subjects.widget.MyLayoutManager;
import com.wktx.www.subjects.utils.ToastUtil;
import com.zhy.autolayout.AutoLayoutActivity;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 管理我的工作---报告(工作安排列表)
 */
public class WorkArrangeActivity extends AutoLayoutActivity {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private BaseQuickAdapter<TaskListInfoData, BaseViewHolder> mAdapter;
    private List<TaskListInfoData> workList;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_arrange);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_work_arrange);
        initData();
        initRecycleView();
        initAdapter();
    }

    /**
     * 接收ManageActivity 传递过来的 工作详情（工作安排列表）
     */
    private void initData() {
        WorkDetailsInfoData workDetailsInfoData = (WorkDetailsInfoData) getIntent().getSerializableExtra(ConstantUtil.KEY_DATA);
        workList = workDetailsInfoData.getArrangementWork();
    }

    /**
     * 初始化 RecycleView
     */
    private void initRecycleView() {
        //设置分割线与垂直方向布局
        recyclerView.addItemDecoration(MyUtils.drawDivider(this, LinearLayout.VERTICAL, R.drawable.divider_f0f0f0_14));
        MyLayoutManager myLayoutManager = new MyLayoutManager(this, LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(myLayoutManager);
        if (workList.size()==0){
            recyclerView.setBackgroundResource(R.drawable.img_nothing);
            ToastUtil.myToast("暂无任何工作安排！");
        }else {
            recyclerView.setBackgroundResource(R.color.color_f0f0f0);
        }
    }

    /**
     * 为 RecyclerView 加载 Adapter
     */
    private void initAdapter() {
        mAdapter = new BaseQuickAdapter<TaskListInfoData, BaseViewHolder>(R.layout.item_rv_manage_report, null) {
            @Override
            protected void convert(BaseViewHolder helper, TaskListInfoData item) {
                String endTime = item.getEnd_time();
                helper.setText(R.id.tv_demandTitle,item.getDemand_title());
                helper.setText(R.id.tv_demandContent,item.getDemand_content());
                helper.setText(R.id.tv_storeName,item.getShop_name());
                helper.setText(R.id.tv_endTime,endTime+"截止");
                //头像
                CircleImageView ivHead = helper.getView(R.id.iv_head);
                if (TextUtils.isEmpty(item.getShop_logo())){
                    ivHead.setImageResource(R.drawable.img_mine_head);
                }else {
                    GlideUtil.loadImage(item.getShop_logo(),R.drawable.img_mine_head,ivHead);
                }
            }
        };
        recyclerView.setAdapter(mAdapter);
        mAdapter.setNewData(workList);
        //子控件点击事件
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (MyUtils.isFastClick1()){
                    return;
                }
                //将安排工作id 传递给工作报告列表
                Intent intent = new Intent(WorkArrangeActivity.this, ReportListActivity.class);
                intent.putExtra(ConstantUtil.KEY_DATA,mAdapter.getData().get(position).getId());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtil.cancleMyToast();
    }
}
