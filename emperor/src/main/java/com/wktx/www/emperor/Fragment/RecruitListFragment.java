package com.wktx.www.emperor.Fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.wktx.www.emperor.Activity.ArtistResumeActivity;
import com.wktx.www.emperor.Activity.CheckListActivity;
import com.wktx.www.emperor.Activity.ScreeningActivity;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.Utils.MyDividerUtil;
import com.wktx.www.emperor.Widget.DropDownMenu;
import com.wktx.www.emperor.Activity.WorksDetailActivity;
import com.wktx.www.emperor.Adapter.ListDropDownAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecruitListFragment extends Fragment {
    @BindView(R.id.dropDownMenu)
    DropDownMenu mDropDownMenu;
    @BindView(R.id.hotIvFloat)
    ImageView ivFloat;
    private String title;
    private ImageView imgView;
    private ListDropDownAdapter ageAdapter1;

    @OnClick({R.id.hotIvFloat})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.hotIvFloat:
                startActivity(new Intent(getActivity(), CheckListActivity.class));
                break;
            default:
                break;
        }
    }

    private String headers[] = {"全部", "设计类型"};
    private List<View> popupViews = new ArrayList<>();
    private TextView textView;
    private ListDropDownAdapter ageAdapter;
    private RecyclerView mVtRecycleView;
    private String citys[] = {"不限", "淘宝", "天猫", "阿里巴巴", "京东", "苏宁"};
    private String ages[] = {"不限", "鞋服", "生活电器", "3c数码", "母婴", "食品"};
    private List<String> strings = new ArrayList();

    public RecruitListFragment() {
        // Required empty public constructor
    }

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recruit_list, container, false);
        ButterKnife.bind(this, view);
        Bundle arguments = getArguments();
        title = arguments.getString("title");
        initFloat();
        initData();
        initView();
        initRv();
        return view;
    }

    private void initView() {
        //测试tabView扩展功能
        final View view = getLayoutInflater(new Bundle()).inflate(R.layout.tab_text, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
        textView = (TextView) view.findViewById(R.id.text);
        imgView = (ImageView) view.findViewById(R.id.img);
        textView.setText("筛选");

        //init age menu
        final ListView ageView = new ListView(getActivity());
        ageView.setDividerHeight(0);
        ageAdapter = new ListDropDownAdapter(getActivity(), Arrays.asList(citys));
        ageView.setAdapter(ageAdapter);

        //init sex menu
        final ListView ageView1 = new ListView(getActivity());
        ageView1.setDividerHeight(0);
        ageAdapter1 = new ListDropDownAdapter(getActivity(), Arrays.asList(ages));
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

        View view_rv = getLayoutInflater(new Bundle()).inflate(R.layout.include_recycleview, null);
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
                } else {
                    startActivity(new Intent(getActivity(), ScreeningActivity.class));
                    getActivity().overridePendingTransition(R.anim.move_in_left,R.anim.move_out_left);
                }
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


    private void initRv() {
        mVtRecycleView.addItemDecoration(MyDividerUtil.drawDivider(getActivity(), LinearLayout.VERTICAL, R.drawable.divider_recycler_gray_three));// 添加分割线。
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false);
        mVtRecycleView.setLayoutManager(manager);
        BaseQuickAdapter<String, BaseViewHolder> adapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_vt_rv_img, strings) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
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
        mVtRecycleView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getActivity(), ArtistResumeActivity.class));
            }
        });
    }

    private void initData() {
        for (int i = 0; i < 6; i++) {
            strings.add("胡图图" + 1);
        }
//        for (int j = 0; j < 3; j++) {
//            imgList.add(image[j]);
//        }
    }

    private void initFloat() {
        ivFloat.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ObjectAnimator ofFloatX = null;
                ObjectAnimator ofFloatY = null;
                AnimatorSet animatorSet = null;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ofFloatX = ObjectAnimator.ofFloat(ivFloat, "scaleX", 1, 0.7f);
                        ofFloatX.setDuration(500).start();
                        ofFloatY = ObjectAnimator.ofFloat(ivFloat, "scaleY", 1, 0.7f);
                        ofFloatY.setDuration(500).start();
                        animatorSet = new AnimatorSet();
                        animatorSet.playTogether(ofFloatX, ofFloatY);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        ofFloatX = ObjectAnimator.ofFloat(ivFloat, "scaleX", 0.7f, 1);
                        ofFloatX.setDuration(500).start();
                        ofFloatY = ObjectAnimator.ofFloat(ivFloat, "scaleY", 0.7f, 1);
                        ofFloatY.setDuration(500).start();
                        animatorSet = new AnimatorSet();
                        animatorSet.playTogether(ofFloatX, ofFloatY);
                        break;

                    default:
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        strings.clear();
        popupViews.clear();
    }
}