package com.wktx.www.emperor.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.wktx.www.emperor.Adapter.ListDropDownAdapter;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.Utils.MyDividerUtil;
import com.wktx.www.emperor.Widget.DropDownMenu;
import com.wktx.www.emperor.Widget.MyLayoutManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人中心-我的收藏
 */
public class FavorActivity extends AppCompatActivity {
    @BindView(R.id.tb_TvBarTitle)
    TextView mTvTitle;
    @BindView(R.id.dropDownMenu)
    DropDownMenu mDropDownMenu;
    @OnClick(R.id.tb_IvReturn)
    public void MyOnclick(View view) {
        finish();
    }
    List<String> mDatas = new ArrayList<>();
    private String headers[] = {"全部岗位"};
    private List<View> popupViews = new ArrayList<>();
    private BaseQuickAdapter<String, BaseViewHolder> mAdapter;
    private ListDropDownAdapter ageAdapter;
    private RecyclerView mVtRecycleView;
    private String posts[] = {"不限", "美工", "客服", "运营", "店长"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favor);
        // 设置右滑动返回
        Slidr.attach(this);
        ButterKnife.bind(this);
        initView();
        initVtRvData();
        mTvTitle.setText(R.string.title_favor);
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


        final View view_rv = getLayoutInflater().inflate(R.layout.include_recycleview, null);
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
        mVtRecycleView.addItemDecoration(MyDividerUtil.drawDivider(this, LinearLayout.VERTICAL, R.drawable.divider_recycler_gray_seven));
        MyLayoutManager myLayoutManager = new MyLayoutManager(this, LinearLayout.VERTICAL, false);
        mVtRecycleView.setLayoutManager(myLayoutManager);
        mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_vt_rv_adress, mDatas) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.hzTvName, item);
            }
        };
        mVtRecycleView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(FavorActivity.this,ArtistResumeActivity.class));
            }
        });
    }

    private void initVtRvData() {
        for (int i = 0; i < 5; i++) {
            mDatas.add("胡图图" + i);
        }
    }
}