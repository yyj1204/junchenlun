package com.wktx.www.emperor.Fragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hedgehog.ratingbar.RatingBar;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.Utils.MyDividerUtil;
import com.wktx.www.emperor.Widget.MyLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResumeFragment extends Fragment {
    @BindView(R.id.recyclerView)
    RecyclerView mVtRecycleView;
    List<String> mDatas = new ArrayList<>();
    private BaseQuickAdapter<String, BaseViewHolder> mAdapter;

    public ResumeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_resume, container, false);
        ButterKnife.bind(this, view);
        initVtRvData();
        initVtRecycleView();
        return view;
    }
    private void initVtRvData() {
        for (int i = 0; i < 5; i++) {
            mDatas.add("胡图图" + i);
        }
    }


    private void initVtRecycleView() {
        mVtRecycleView.addItemDecoration(MyDividerUtil.drawDivider(getActivity(), LinearLayout.VERTICAL, R.drawable.divider_recycler_gray));// 添加分割线。
        MyLayoutManager myLayoutManager = new MyLayoutManager(getActivity(), LinearLayout.VERTICAL, false);
        mVtRecycleView.setLayoutManager(myLayoutManager);
        mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_vt_resume, mDatas) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
            }
        };
        View hvResume = getActivity().getLayoutInflater().inflate(R.layout.hv_vt_resume, (ViewGroup) mVtRecycleView.getParent(), false);
        mAdapter.setHeaderView(hvResume);
        initHVResume(hvResume);
        mVtRecycleView.setAdapter(mAdapter);
    }

    private void initHVResume(View hvResume) {
        RatingBar ratingBar = (RatingBar) hvResume.findViewById(R.id.ratingbar);
        RatingBar ratingBar1 = (RatingBar) hvResume.findViewById(R.id.ratingbar1);
        RatingBar ratingBar2 = (RatingBar) hvResume.findViewById(R.id.ratingbar2);
        ratingBar.setStar(4);
        ratingBar1.setStar(5);
        ratingBar2.setStar(4);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mDatas.clear();
    }
}
