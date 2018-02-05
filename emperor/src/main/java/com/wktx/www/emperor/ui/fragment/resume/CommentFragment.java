package com.wktx.www.emperor.ui.fragment.resume;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hedgehog.ratingbar.RatingBar;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.wktx.www.emperor.Activity.WorksDetailActivity;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.widget.MyLayoutManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentFragment extends Fragment {
    @BindView(R.id.recyclerView)
    RecyclerView mVtRecycleView;
    List<String> mDatas = new ArrayList<>();
    private BaseQuickAdapter<String, BaseViewHolder> mAdapter;

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


    public CommentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comment, container, false);
        ButterKnife.bind(this, view);
        initVtRvData();
        initVtRecycleView();
        return view;
    }


    private void initVtRvData() {
        for (int i = 0; i < 10; i++) {
            mDatas.add("胡图图" + i);
        }
    }

    private void initVtRecycleView() {
        mVtRecycleView.addItemDecoration(MyUtils.drawDivider(getActivity(), LinearLayout.VERTICAL, R.drawable.divider_f0f0f0_2));// 添加分割线。
        MyLayoutManager myLayoutManager = new MyLayoutManager(getActivity(), LinearLayout.VERTICAL, false);
        mVtRecycleView.setLayoutManager(myLayoutManager);
        mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_state_grid_style, mDatas) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                RatingBar ratingBar = helper.getView(R.id.ratingbar);
                RatingBar ratingBar1 = helper.getView(R.id.ratingbar1);
                RatingBar ratingBar2 = helper.getView(R.id.ratingbar2);
                ratingBar.setStar(4);
                ratingBar1.setStar(5);
                ratingBar2.setStar(4);
                Random random = new Random();
                ArrayList<Integer> imgList = new ArrayList<>();
                for (int j = 0; j < 3; j++) {
                    int i = random.nextInt(9);
                    imgList.add(image[i]);
                }
                NineGridImageView mNglContent = helper.getView(R.id.ngl_images);
                NineGridImageViewAdapter<Integer> adapter = new NineGridImageViewAdapter<Integer>() {
                    @Override
                    protected void onDisplayImage(Context context, ImageView imageView, Integer s) {
                        if (imageView != null && s != null) {
                            Drawable drawable = getResources().getDrawable(s);
                            imageView.setImageDrawable(drawable);
                        }
                    }

                    @Override
                    protected ImageView generateImageView(Context context) {
                        return super.generateImageView(context);
                    }

                    @Override
                    protected void onItemImageClick(Context context, ImageView imageView, int index, List<Integer> list) {
                        startActivity(new Intent(getActivity(), WorksDetailActivity.class));
                    }
                };
                mNglContent.setAdapter(adapter);
                mNglContent.setImagesData(imgList);
                mNglContent.setSingleImgSize(450);
            }
        };
        mVtRecycleView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mDatas.clear();
    }
}
