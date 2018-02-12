package com.wktx.www.emperor.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.ui.activity.recruit.resume.ArtistResumeActivity;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.widget.DropDownMenu;
import com.wktx.www.emperor.ui.adapter.ListDropDownAdapter;
import com.wktx.www.emperor.widget.MyLayoutManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人中心-面试记录
 */
public class InterviewActivity extends AppCompatActivity {

    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.dropDownMenu)
    DropDownMenu mDropDownMenu;
    private BaseQuickAdapter<String, BaseViewHolder> mAdapter;
    List<String> mDatas = new ArrayList<>();

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

    private String headers[] = {"全部岗位"};
    private List<View> popupViews = new ArrayList<>();
    private ListDropDownAdapter ageAdapter;
    private RecyclerView mVtRecycleView;
    private String posts[] = {"不限", "美工", "客服", "运营", "店长"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview);
        // 设置右滑动返回
        Slidr.attach(this);
        ButterKnife.bind(this);
        tvTitle.setText(R.string.title_interview);
        initView();
        initVtRvData();
        initVtRecycleView();

    }

    private void initView() {
        //init age menu
        final ListView ageView = new ListView(this);
        ageView.setDividerHeight(0);
        ageAdapter = new ListDropDownAdapter(this, Arrays.asList(posts));
        ageView.setAdapter(ageAdapter);
        popupViews.add(ageView);

        ageView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ageAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[0] : posts[position]);
                mDropDownMenu.closeMenu();
            }
        });

        final View view_rv = getLayoutInflater().inflate(R.layout.include_recycleview_f0f0f0, null);
        mVtRecycleView = (RecyclerView) view_rv.findViewById(R.id.recyclerView);
        view_rv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        //初始化 dropdownview
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, view_rv);

        mDropDownMenu.setOnItemMenuClickListener(new DropDownMenu.OnItemMenuClickListener() {
            @Override
            public void OnItemMenuClick(TextView tabView, int position) {
            }
        });
    }

    private void initVtRecycleView() {
        mVtRecycleView.addItemDecoration(MyUtils.drawDivider(this, LinearLayout.VERTICAL, R.drawable.divider_f0f0f0_14));
        MyLayoutManager myLayoutManager = new MyLayoutManager(this, LinearLayout.VERTICAL, false);
        mVtRecycleView.setLayoutManager(myLayoutManager);
        mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_vt_rv_employ, mDatas) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.hzTvName, item);
            }
        };
        mVtRecycleView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(InterviewActivity.this,ArtistResumeActivity.class));
            }
        });
    }

    private void initVtRvData() {
        for (int i = 0; i < 5; i++) {
            mDatas.add("胡图图" + i);
        }
    }
}
