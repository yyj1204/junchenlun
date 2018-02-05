package com.wktx.www.subjects.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.utils.MyDividerUtil;
import com.wktx.www.subjects.widget.MyLayoutManager;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.wktx.www.subjects.R.id.linear_staff_salary;

/**
 * 管理我的员工
 */
public class StaffManageActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.tb_TvBarTitle)
    TextView mTvTitle;
    @BindView(R.id.recyclerView)
    RecyclerView mVtRecycleView;
    List<String> mDatas = new ArrayList<>();
    private BaseQuickAdapter<String, BaseViewHolder> mAdapter;

    private void initVtRvData() {
        for (int i = 0; i < 6; i++) {
            mDatas.add("母婴玩具专营店" + i);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_manager);
        ButterKnife.bind(this);
        // 设置右滑动返回r
        Slidr.attach(this);
        mTvTitle.setText(R.string.title_staff_manager);
        initVtRvData();
        initVtRecycleView();
    }

    private void initVtRecycleView() {
        mVtRecycleView.addItemDecoration(MyDividerUtil.drawDivider(this, LinearLayout.VERTICAL, R.drawable.divider_recycler_gray));// 添加分割线。
        MyLayoutManager myLayoutManager = new MyLayoutManager(this, LinearLayout.VERTICAL, false);
        mVtRecycleView.setLayoutManager(myLayoutManager);
        mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_staffmanager, mDatas) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.textview, item);
            }
        };
        View hv = getLayoutInflater().inflate(R.layout.item_staffmanager_hv, null);
        mAdapter.setHeaderView(hv);
        inithv(hv);
        mVtRecycleView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(StaffManageActivity.this, StaffReportDetaisActivity.class));
            }
        });
    }

    private void inithv(View view) {
        view.findViewById(R.id.tv_staff_renew).setOnClickListener(this);
        view.findViewById(linear_staff_salary).setOnClickListener(this);
        view.findViewById(R.id.linear_staff_chuqin).setOnClickListener(this);
        view.findViewById(R.id.linear_arrange_work).setOnClickListener(this);
    }

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
        switch (view.getId()) {
            case R.id.tv_staff_renew:
            startActivity(new Intent(StaffManageActivity.this, StaffRenewActivity.class));
                break;
            case R.id.linear_staff_salary://工资
                startActivity(new Intent(StaffManageActivity.this, StaffSalaryActivity.class));
                break;
            case R.id.linear_staff_chuqin://出勤
                startActivity(new Intent(StaffManageActivity.this, StaffAttendanceActivity.class));
                break;
            case R.id.linear_arrange_work://销售额
               startActivity(new Intent(StaffManageActivity.this,WorkReportActivity.class));
                break;
            default:
                break;
        }
    }
}
