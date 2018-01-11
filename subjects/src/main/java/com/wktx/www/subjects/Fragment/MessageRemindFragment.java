package com.wktx.www.subjects.Fragment;


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
import com.wktx.www.subjects.Utils.MyDividerUtil;
import com.wktx.www.subjects.Widget.MyLayoutManager;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * 消息通知---提醒
 */
public class MessageRemindFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private BaseQuickAdapter<String, BaseViewHolder> mAdapter;
    private List<String> stringList = new ArrayList();

    public MessageRemindFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message_remind, container, false);
        ButterKnife.bind(this, view);
        initData();
        initUI();
        return view;
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            stringList.add("【面试通知】" + (i+1));
        }
    }

    private void initUI() {
        recyclerView.addItemDecoration(MyDividerUtil.drawDivider(getActivity(), LinearLayout.VERTICAL,R.drawable.divider_recycler_gray_seven));
        MyLayoutManager myLayoutManager = new MyLayoutManager(getActivity(), LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(myLayoutManager);
        mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_rv_messageremind, stringList) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.tv_content, item);
                helper.setText(R.id.tv_time, "2017/10/30  15:17:50");
            }
        };
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stringList.clear();
    }
}
