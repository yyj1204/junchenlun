package com.wktx.www.subjects.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
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

public class ResumeActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.tb_TvBarTitle)
    TextView mTvTitle;

    @OnClick({R.id.tb_IvReturn, R.id.resumeTvPreview})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn:
                finish();
                break;
            case R.id.resumeTvPreview:
                startActivity(new Intent(ResumeActivity.this,PreviewResumeActivity.class));
                break;
            default:
                break;
        }
    }

    @BindView(R.id.recyclerView)
    RecyclerView mVtRecycleView;
    List<String> mDatas = new ArrayList<>();
    private BaseQuickAdapter<String, BaseViewHolder> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        mTvTitle.setText(R.string.title_resume);
        initVtRvData();
        initVtRecycleView();
    }

    private void initVtRvData() {
        for (int i = 0; i < 2; i++) {
            mDatas.add("胡图图" + i);
        }
    }

    private void initVtRecycleView() {
        mVtRecycleView.addItemDecoration(MyDividerUtil.drawDivider(this, LinearLayout.VERTICAL, R.drawable.divider_recycler_gray));// 添加分割线。
        MyLayoutManager myLayoutManager = new MyLayoutManager(this, LinearLayout.VERTICAL, false);
        mVtRecycleView.setLayoutManager(myLayoutManager);
        mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_vt_resume, mDatas) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
            }
        };
        View hvResume = this.getLayoutInflater().inflate(R.layout.hv_vt_resume, (ViewGroup) mVtRecycleView.getParent(), false);
        hvResume.findViewById(R.id.linear_edit_info).setOnClickListener(this);
        hvResume.findViewById(R.id.resumeLlSubmitResume).setOnClickListener(this);
        hvResume.findViewById(R.id.goodLlStyle).setOnClickListener(this);
        hvResume.findViewById(R.id.goodLlCategory).setOnClickListener(this);
        mAdapter.setHeaderView(hvResume);
        mVtRecycleView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linear_edit_info://编辑个人信息
                startActivity(new Intent(ResumeActivity.this,PersonalInfoActivity.class));
                break;
            case R.id.resumeLlSubmitResume:
                startActivity(new Intent(ResumeActivity.this, SubmitResumeActivity.class));
                break;
            case R.id.goodLlStyle:
                startActivity(new Intent(ResumeActivity.this, GoodPickActivity.class));
                break;
            case R.id.goodLlCategory:
                startActivity(new Intent(ResumeActivity.this, GoodPickActivity.class));
                break;
            default:
                break;
        }
    }
}
