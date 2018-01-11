package com.wktx.www.emperor.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.Utils.MyDividerUtil;
import com.wktx.www.emperor.Widget.MyLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 管理我的员工---员工工资
 */
public class StaffSalaryActivity extends AppCompatActivity {

    @BindView(R.id.tb_TvBarTitle)
    TextView mTvTitle;
    @BindView(R.id.tv_underline1)
    TextView tv_underline1;
    @BindView(R.id.tv_underline2)
    TextView tv_underline2;

    @BindView(R.id.recyclerView)
    RecyclerView mVtRecycleView;

    private BaseQuickAdapter<String, BaseViewHolder> mAdapter;
    List<String> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_salary);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        mTvTitle.setText(R.string.title_staff_salary);
        initVtRvData();
        initVtRecycleView();
    }

    private void initVtRecycleView() {
        mVtRecycleView.addItemDecoration(MyDividerUtil.drawDivider(this, LinearLayout.VERTICAL, R.drawable.divider_recycler_gray));
        MyLayoutManager myLayoutManager = new MyLayoutManager(this, LinearLayout.VERTICAL, false);
        mVtRecycleView.setLayoutManager(myLayoutManager);
        mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_rv_staffsalary, mDatas) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.tv_salary_money, item);
            }
        };
        mVtRecycleView.setAdapter(mAdapter);
    }

    private void initVtRvData() {
        for (int i = 0; i < 5; i++) {
            mDatas.add(500 * i + 2000 + "");
        }
    }

    @OnClick({R.id.tb_IvReturn,R.id.linear_salary_trusteeship,R.id.linear_salary_pay})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn://返回
                finish();
                break;
            case R.id.linear_salary_trusteeship://托管中工资
                tv_underline1.setVisibility(View.VISIBLE);
                tv_underline2.setVisibility(View.INVISIBLE);
                break;
            case R.id.linear_salary_pay://已付工资
                tv_underline1.setVisibility(View.INVISIBLE);
                tv_underline2.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }
}
