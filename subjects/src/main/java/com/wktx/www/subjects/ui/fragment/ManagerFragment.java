package com.wktx.www.subjects.ui.fragment;


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
import com.wktx.www.subjects.Activity.StaffManageActivity;
import com.wktx.www.subjects.ui.adapter.ListDropDownAdapter;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.utils.MyDividerUtil;
import com.wktx.www.subjects.widget.DropDownMenu;
import com.wktx.www.subjects.widget.MyLayoutManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManagerFragment extends Fragment {
    @BindView(R.id.dropDownMenu)
    DropDownMenu mDropDownMenu;
    private BaseQuickAdapter<String, BaseViewHolder> mAdapter;
    List<String> mDatas = new ArrayList<>();

    @OnClick({})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            default:
                break;
        }
    }

    private String headers[] = {"全部岗位"};
    private List<View> popupViews = new ArrayList<>();
    private ListDropDownAdapter ageAdapter;
    private RecyclerView mVtRecycleView;
    private String posts[] = {"不限", "美工", "客服", "运营", "店长"};


    public ManagerFragment() {
        // Required empty public constructor
    }
    public static ManagerFragment newInstance(String info) {
        Bundle args = new Bundle();
        ManagerFragment fragment = new ManagerFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manager, container, false);
        ButterKnife.bind(this,view);
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
        popupViews.add(ageView);

        ageView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ageAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[0] : posts[position]);
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
        mVtRecycleView.addItemDecoration(MyDividerUtil.drawDivider(getActivity(), LinearLayout.VERTICAL, R.drawable.divider_recycler_gray_three));
        MyLayoutManager myLayoutManager = new MyLayoutManager(getActivity(), LinearLayout.VERTICAL, false);
        mVtRecycleView.setLayoutManager(myLayoutManager);
        mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_vt_rv, mDatas) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.hzTvName, item);
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
        for (int i = 0; i < 6; i++) {
            mDatas.add("胡图图" + i);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        popupViews.clear();
        mDatas.clear();
    }
}
