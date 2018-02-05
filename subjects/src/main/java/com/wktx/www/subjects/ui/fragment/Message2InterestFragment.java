package com.wktx.www.subjects.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.utils.MyDividerUtil;
import com.wktx.www.subjects.widget.MyLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * 消息---互动---对我感兴趣
 */
public class Message2InterestFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private BaseQuickAdapter<String, BaseViewHolder> mAdapter;
    private List<String> stringList = new ArrayList();

    public Message2InterestFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message_task, container, false);
        ButterKnife.bind(this, view);
        initData();
        initUI();
        return view;
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            stringList.add("化妆品旗舰店" + (i+1));
        }
    }

    private void initUI() {
        recyclerView.addItemDecoration(MyDividerUtil.drawDivider(getActivity(), LinearLayout.VERTICAL,R.drawable.divider_recycler_gray_three));
        MyLayoutManager myLayoutManager = new MyLayoutManager(getActivity(), LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(myLayoutManager);
        mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_rv_message2interest, stringList) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
            }
        };
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stringList.clear();
    }

}
