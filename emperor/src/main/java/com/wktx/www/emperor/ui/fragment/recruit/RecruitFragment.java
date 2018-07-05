package com.wktx.www.emperor.ui.fragment.recruit;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.wktx.www.emperor.basemvp.ABaseFragment;
import com.wktx.www.emperor.ui.activity.main.SearchActivity;
import com.wktx.www.emperor.ui.activity.login.LoginActivity;
import com.wktx.www.emperor.ui.activity.mine.BrowsingRecordActivity;
import com.wktx.www.emperor.ui.activity.recruit.demand.DemandActivity;
import com.wktx.www.emperor.ui.activity.main.message.MessageActivity;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.login.AccountInfoData;
import com.wktx.www.emperor.apiresult.recruit.retrievalcondition.Bean;
import com.wktx.www.emperor.apiresult.recruit.retrievalcondition.RetrievalConditionInfoData;
import com.wktx.www.emperor.presenter.recruit.recruit.RecruitPresenter;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LoginUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.ui.view.IView;
import com.wktx.www.emperor.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 招聘片段
 */
public class RecruitFragment extends ABaseFragment<IView,RecruitPresenter> implements IView<RetrievalConditionInfoData> {
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.layout_smartTab)
    SmartTabLayout smartTab;
    @BindView(R.id.iv_browsingHistory)
    ImageView ivBrowsingHistory;

    private boolean isFirstVisible;//是否第一次创建可见

    private List<Bean> jobTypeList;//职业类型集合


    @OnClick({R.id.linear_titleSearch, R.id.iv_titleRight,R.id.tv_releaseDemand,R.id.iv_browsingHistory})
    public void MyOnclick(View view) {
        if (MyUtils.isFastClick1()){
            return;
        }
        switch (view.getId()) {
            case R.id.linear_titleSearch://搜索
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
            case R.id.iv_titleRight://消息通知
                isLoginStartActivity(MessageActivity.class);
                break;
            case R.id.tv_releaseDemand://发布需求
                isLoginStartActivity(DemandActivity.class);
                break;
            case R.id.iv_browsingHistory://浏览记录
                isLoginStartActivity(BrowsingRecordActivity.class);
                break;
            default:
                break;
        }
    }

    /**
     * 登录了才能打开对应的界面
     */
    private void isLoginStartActivity(Class<?> clazz) {
        if (getUserInfo()!=null){
            startActivity(new Intent(getActivity(), clazz));
        }else {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }

    public RecruitFragment() {
    }

    public static RecruitFragment newInstance(String info) {
        Bundle args = new Bundle();
        RecruitFragment fragment = new RecruitFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recruit, container, false);
        ButterKnife.bind(this, view);
        initFloat();//浏览记录
        initData();
        return view;
    }

    @Override
    protected RecruitPresenter createPresenter() {
        return new RecruitPresenter();
    }

    /**
     * 片段是否可见
     * @param isVisible falese 可见 -> 不可见
     *  此时 isFirstVisible=true，在片段不可见时候将 isFirstVisible = false
     * @param isVisible true  不可见 -> 可见
     *  如果 isFirstVisible=false 说明已经创建过，防止多次请求接口，加判断
     */
//    @Override
//    protected void onFragmentVisibleChange(boolean isVisible) {
//        if (isVisible){
//            if (!isFirstVisible){
//                initData();
//            }
//        }else {
//            if (isFirstVisible){
//                isFirstVisible=false;
//            }
//        }
//    }

    /**
     * 片段第一次被创建（可见）时才会执行到这个方法
     * 加载数据
     * isFirstVisible=true
     */
//    @Override
//    protected void onFragmentFirstVisible() {
//        initData();
//        isFirstVisible=true;
//    }

    private void initData() {
        getPresenter().onGetRetrievalConditionInfo();//获取招聘检索条件
    }

    /**
     * 浏览记录---按压动画效果
     */
    @SuppressLint("ClickableViewAccessibility")
    private void initFloat() {
        ivBrowsingHistory.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ObjectAnimator ofFloatX = null;
                ObjectAnimator ofFloatY = null;
                AnimatorSet animatorSet = null;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN://按下
                        ofFloatX = ObjectAnimator.ofFloat(ivBrowsingHistory, "scaleX", 1, 0.7f);
                        ofFloatX.setDuration(500).start();
                        ofFloatY = ObjectAnimator.ofFloat(ivBrowsingHistory, "scaleY", 1, 0.7f);
                        ofFloatY.setDuration(500).start();
                        animatorSet = new AnimatorSet();
                        animatorSet.playTogether(ofFloatX, ofFloatY);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP://放开
                        ofFloatX = ObjectAnimator.ofFloat(ivBrowsingHistory, "scaleX", 0.7f, 1);
                        ofFloatX.setDuration(500).start();
                        ofFloatY = ObjectAnimator.ofFloat(ivBrowsingHistory, "scaleY", 0.7f, 1);
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

    /**
     * IRecruitListView
     */
    @Override
    public AccountInfoData getUserInfo() {
        AccountInfoData userInfo = LoginUtil.getinit().getUserInfo();
        return userInfo;
    }
    @Override
    public void onRequestSuccess(RetrievalConditionInfoData tData) {
        initSmartVp(tData);
    }
    @Override
    public void onRequestFailure(String result) {
        ToastUtil.myToast( result);
    }

    /**
     * 初始化SmartTabLayout
     */
    private void initSmartVp(RetrievalConditionInfoData tData) {
        //招聘职位类型集合
        jobTypeList = tData.getTop().getTow();
        //子片段数量
        FragmentPagerItems pages = new FragmentPagerItems(getActivity());
        for (int i = 0; i < jobTypeList.size(); i++) {
            //绑定检索条件数据
            Bundle bundle = new Bundle();
            bundle.putInt(ConstantUtil.KEY_POSITION, i);//工作类型的ID
            bundle.putSerializable(ConstantUtil.KEY_DATA, tData);//检索条件
            pages.add(FragmentPagerItem.of(jobTypeList.get(i).getName(), RecruitListFragment.class, bundle));
        }

        //适配器
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getChildFragmentManager(), pages);
        //给SmartTabLayout子控件赋值
        smartTab.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
                CharSequence pageTitle = adapter.getPageTitle(position);
                View v = LayoutInflater.from(getActivity()).inflate(R.layout.item_smarttab_recruit, container, false);
                if (v != null) {
                    TextView textView = (TextView) v.findViewById(R.id.textView);
                    textView.setText("招"+pageTitle);
                }
                return v;
            }
        });
        viewPager.setAdapter(adapter);
        smartTab.setViewPager(viewPager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    //接收 HomeFragment 寄存在 MainActivity 里的被选中的职业类型id
    public void SetCurrentPager(String jobTypeId){
        for (int i = 0; i < jobTypeList.size() ; i++) {
            if (jobTypeList.get(i).getId().equals(jobTypeId)){
                viewPager.setCurrentItem(i);
                return;
            }
        }
    }
}
