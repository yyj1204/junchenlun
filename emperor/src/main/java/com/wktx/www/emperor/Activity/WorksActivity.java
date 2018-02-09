package com.wktx.www.emperor.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.application.MyApplication;
import com.wktx.www.emperor.ui.activity.recruit.resume.WorksDetailsActivity;
import com.wktx.www.emperor.utils.SpaceItemDecoration;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.utils.ToastUtil;
import com.wktx.www.emperor.widget.DropDownMenu;
import com.wktx.www.emperor.ui.adapter.ListDropDownAdapter;
import com.wktx.www.emperor.Model1.HomeItem;
import com.wktx.www.emperor.utils.Dip2pxUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WorksActivity extends AppCompatActivity {
    @BindView(R.id.dropDownMenu)
    DropDownMenu mDropDownMenu;
    @BindView(R.id.tb_TvBarTitle)
    TextView mTvTitle;
    private ArrayList<HomeItem> mDataList;
    private ImageView imgView;
    private ListDropDownAdapter ageAdapter1;

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
    private String headers[] = {"擅长平台", "擅长类目"};
    private List<View> popupViews = new ArrayList<>();
    private TextView textView;
    private ListDropDownAdapter ageAdapter;
    private RecyclerView mVtRecycleView;
    List<String> mDatas = new ArrayList<>();
    private String citys[] = {"不限", "淘宝", "天猫", "阿里巴巴", "京东", "苏宁"};
    private String ages[] = {"不限", "鞋服", "生活电器", "3c数码", "母婴", "食品"};
    private List<String> strings = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_works);
        ButterKnife.bind(this);
        Slidr.attach(this);
        mTvTitle.setText(R.string.title_works);
        initData();
        initView();
        initVtRecycleView();
    }

    private void initView() {
        //测试tabView扩展功能
        final View view = getLayoutInflater().inflate(R.layout.item_recruit_retrieve, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
        textView = (TextView) view.findViewById(R.id.text);
        imgView = (ImageView) view.findViewById(R.id.img);
        textView.setText("筛选");

        //init age menu
        final ListView ageView = new ListView(this);
        ageView.setDividerHeight(0);
        ageAdapter = new ListDropDownAdapter(this, Arrays.asList(citys));
        ageView.setAdapter(ageAdapter);

        //init sex menu
        final ListView ageView1 = new ListView(this);
        ageView1.setDividerHeight(0);
        ageAdapter1 = new ListDropDownAdapter(this, Arrays.asList(ages));
        ageView1.setAdapter(ageAdapter1);
        popupViews.add(ageView);
        popupViews.add(ageView1);

        ageView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ageAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[0] : citys[position]);
                mDropDownMenu.closeMenu();
                textView.setTextColor(getResources().getColor(R.color.title_color));
            }
        });

        ageView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ageAdapter1.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[1] : ages[position]);
                mDropDownMenu.closeMenu();
                textView.setTextColor(getResources().getColor(R.color.title_color));
            }
        });

        View view_rv = getLayoutInflater().inflate(R.layout.include_recycleview, null);
        mVtRecycleView = (RecyclerView) view_rv.findViewById(R.id.recyclerView);
        view_rv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        //初始化 dropdownview
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, view_rv);
        mDropDownMenu.addTab(view, 2);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDropDownMenu.isActive()) {
                    mDropDownMenu.closeMenu();
                }
                ToastUtil.toast(WorksActivity.this, "侧滑菜单");
            }
        });
        mDropDownMenu.setOnItemMenuClickListener(new DropDownMenu.OnItemMenuClickListener() {
            @Override
            public void OnItemMenuClick(TextView tabView, int position) {
                textView.setTextColor(getResources().getColor(R.color.title_color));
                imgView.setImageDrawable(getResources().getDrawable(R.drawable.down_pull_normal));
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void initVtRecycleView() {
        mVtRecycleView.setLayoutManager(new GridLayoutManager(this, 2));
        mVtRecycleView.addItemDecoration(new SpaceItemDecoration(Dip2pxUtil.dip2px(MyApplication.getContext(), 10),2));
        BaseQuickAdapter<HomeItem, BaseViewHolder> homeAdapter = new BaseQuickAdapter<HomeItem, BaseViewHolder>(R.layout.item_rv_resume_works, mDataList) {
            @Override
            protected void convert(BaseViewHolder helper, HomeItem item) {

            }
        };
        homeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(WorksActivity.this, WorksDetailsActivity.class);
                startActivity(intent);

            }
        });
        mVtRecycleView.setAdapter(homeAdapter);
    }

    private void initData() {
        mDataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            HomeItem item = new HomeItem();
            mDataList.add(item);
        }
    }


}
