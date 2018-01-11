package com.wktx.www.emperor.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.Utils.MyDividerUtil;
import com.wktx.www.emperor.Activity.StaffManageActivity;
import com.wktx.www.emperor.Activity.MessageActivity;
import com.wktx.www.emperor.Adapter.ListDropDownAdapter;
import com.wktx.www.emperor.Widget.DropDownMenu;
import com.wktx.www.emperor.Widget.MyLayoutManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class StaffFragment extends Fragment {
    @BindView(R.id.tb_tvBarTitle)
    TextView tvTitle;
    @BindView(R.id.dropDownMenu)
    DropDownMenu mDropDownMenu;
    List<String> mDatas = new ArrayList<>();
    private String headers[] = {"全部岗位", "全部状态"};
    private List<View> popupViews = new ArrayList<>();
    private BaseQuickAdapter<String, BaseViewHolder> mAdapter;
    private ListDropDownAdapter ageAdapter;
    private RecyclerView mVtRecycleView;
    private String posts[] = {"不限", "美工", "客服", "运营", "店长"};
    private String states[] = {"不限", "空闲中", "雇佣中", "已完成"};
    private ListDropDownAdapter ageAdapter1;


    public StaffFragment() {
        // Required empty public constructor
    }

    public static StaffFragment newInstance(String info) {
        Bundle args = new Bundle();
        StaffFragment fragment = new StaffFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_staff, container, false);
        ButterKnife.bind(this, view);
        tvTitle.setText(R.string.title_tab_staff);
        initView();
        initVtRvData();
        initVtRecycleView();
        return view;
    }

    private void initView() {
        //init age menu
        final ListView ageView = new ListView(getActivity());
        ageView.setDividerHeight(0);
        ageAdapter = new ListDropDownAdapter(getActivity(), Arrays.asList(posts));
        ageView.setAdapter(ageAdapter);
        //init sex menu
        final ListView ageView1 = new ListView(getActivity());
        ageView1.setDividerHeight(0);
        ageAdapter1 = new ListDropDownAdapter(getActivity(), Arrays.asList(states));
        ageView1.setAdapter(ageAdapter1);

        popupViews.add(ageView);
        popupViews.add(ageView1);

        ageView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ageAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[0] : posts[position]);
                mDropDownMenu.closeMenu();
            }
        });

        ageView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ageAdapter1.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[1] : states[position]);
                mDropDownMenu.closeMenu();
            }
        });

        final View view_rv = getLayoutInflater(new Bundle()).inflate(R.layout.include_recycleview, null);
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
        mVtRecycleView.addItemDecoration(MyDividerUtil.drawDivider(getActivity(), LinearLayout.VERTICAL, R.drawable.divider_recycler_gray_seven));
        MyLayoutManager myLayoutManager = new MyLayoutManager(getActivity(), LinearLayout.VERTICAL, false);
        mVtRecycleView.setLayoutManager(myLayoutManager);
        mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_vt_rv_employ, mDatas) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.hzTvName, item);
//                helper.getView(R.id.employIvState).setVisibility(View.VISIBLE);
            }
        };
        mVtRecycleView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getActivity(), StaffManageActivity.class));
            }
        });
    }

    private void initVtRvData() {
        for (int i = 0; i < 5; i++) {
            mDatas.add("胡图图" + i);
        }
    }

    @OnClick({R.id.tb_ivRight})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_ivRight://消息通知
                startActivity(new Intent(getActivity(), MessageActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mDatas.clear();
        popupViews.clear();
    }
}
