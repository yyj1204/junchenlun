package com.wktx.www.emperor.Fragment;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wktx.www.emperor.Activity.WorksDetailActivity;
import com.wktx.www.emperor.Decoration.SpaceItemDecoration;
import com.wktx.www.emperor.Model.HomeItem;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.Utils.Dip2pxUtil;
import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorksFragment extends Fragment {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private ArrayList<HomeItem> mDataList;
    int[] image =
            {
                    R.drawable.recruit0,
                    R.drawable.recruit1,
                    R.drawable.recruit2,
                    R.drawable.recruit3,
                    R.drawable.recruit4,
                    R.drawable.recruit5,
                    R.drawable.recruit6,
                    R.drawable.recruit7,
                    R.drawable.recruit8,
            };
    public WorksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_works, container, false);
        ButterKnife.bind(this, view);
        initData();
        initAdapter();
        return view;
    }

    @SuppressWarnings("unchecked")
    private void initAdapter() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(Dip2pxUtil.dip2px(getContext(), 10), 3));
        BaseQuickAdapter<HomeItem, BaseViewHolder> homeAdapter = new BaseQuickAdapter<HomeItem, BaseViewHolder>(R.layout.home_item_view, mDataList) {
            @Override
            protected void convert(BaseViewHolder helper, HomeItem item) {
                ImageView icon = helper.getView(R.id.icon);
                Drawable drawable = getResources().getDrawable(item.getImageResource());
                icon.setImageDrawable(drawable);
            }
        };
        homeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), WorksDetailActivity.class);
                startActivity(intent);

            }
        });
        mRecyclerView.setAdapter(homeAdapter);
    }

    private void initData() {
        mDataList = new ArrayList<>();
        for (int i = 0; i < 18; i++) {
            Random random = new Random();
            HomeItem item = new HomeItem();
            int j= random.nextInt(9);
            item.setImageResource(image[j]);
            mDataList.add(item);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mDataList.clear();
    }
}
