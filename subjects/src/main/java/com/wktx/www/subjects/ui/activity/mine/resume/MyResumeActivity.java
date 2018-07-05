package com.wktx.www.subjects.ui.activity.mine.resume;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.login.AccountInfoData;
import com.wktx.www.subjects.apiresult.main.condition.ConditionBean;
import com.wktx.www.subjects.apiresult.mine.resume.ResumeInfoData;
import com.wktx.www.subjects.basemvp.ABaseActivity;
import com.wktx.www.subjects.presenter.mine.resume.MyResumePresenter;
import com.wktx.www.subjects.ui.activity.mine.PersonInfoActivity;
import com.wktx.www.subjects.ui.activity.mine.works.MyWorksActivity;
import com.wktx.www.subjects.ui.adapter.mine.ResumeExperienceAdapter;
import com.wktx.www.subjects.ui.view.mine.resume.IMyResumeView;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.GlideUtil;
import com.wktx.www.subjects.utils.LoginUtil;
import com.wktx.www.subjects.utils.MyUtils;
import com.wktx.www.subjects.widget.MyLayoutManager;
import com.wktx.www.subjects.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 个人中心 --- 我的简历
 */
public class MyResumeActivity extends ABaseActivity<IMyResumeView,MyResumePresenter> implements IMyResumeView, View.OnClickListener {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    //个人信息
    private CircleImageView civHead;
    private TextView tvName;
    private ImageView ivSex;
    //应聘职位信息
    private LinearLayout llPositionAdd;
    private LinearLayout llPosition;
    private TextView tvPositionName;
    private TextView tvCategory;
    private TextView tvPlatform;
    private TextView tvExperience;
    private TextView tvSalary;
    //客服与美工（打字速度&擅长风格）
    private LinearLayout llSpeedTitle;
    private TextView tvSpeedTitle;
    private TextView tvSpeed;
    //是否找工作开关
    private TextView tvOpen;
    private TextView tvClose;

    private ResumeExperienceAdapter mAdapter;

    private boolean isOpen;//是否找工作
    //简历信息
    private ResumeInfoData resumeInfoData;

    @OnClick({R.id.tb_IvReturn, R.id.bt_previewResume})
    public void MyOnclick(View view) {
        if (MyUtils.isFastClick1()){
            return;
        }
        switch (view.getId()) {
            case R.id.tb_IvReturn:
                finish();
                break;
            case R.id.bt_previewResume://预览简历
                if (resumeInfoData.getTow()==null){
                     ToastUtil.myToast("暂无应聘职位，无法预览简历！");
                }else {
                    Intent intent = new Intent(MyResumeActivity.this, ResumePreviewActivity.class);
                    intent.putExtra(ConstantUtil.KEY_DATA,resumeInfoData.getId());
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linear_info://编辑个人信息
                if (MyUtils.isFastClick1()){
                    return;
                }
                startActivity(new Intent(MyResumeActivity.this,PersonInfoActivity.class));
                break;
            case R.id.linear_positionAdd://新增应聘职位
                if (MyUtils.isFastClick1()){
                    return;
                }
                startPositionActivity(true);
                break;
            case R.id.linear_position://编辑应聘职位
                if (MyUtils.isFastClick1()){
                    return;
                }
                if (isOpen){
                     ToastUtil.myToast("正在找工作，无法编辑该职位！");
                }else {
                    startPositionActivity(false);
                }
                break;
            case R.id.tv_open://打开
                changeHuntingState(true);
                break;
            case R.id.tv_close://关闭
                changeHuntingState(false);
                break;
            case R.id.linear_uploadResume://上传个性简历
                if (MyUtils.isFastClick1()){
                    return;
                }
                startSomeActivity(ResumeUploadActivity.class);
                break;
            case R.id.linear_uploadWorks://上传作品
                if (MyUtils.isFastClick1()){
                    return;
                }
                startSomeActivity(MyWorksActivity.class);
                break;
            case R.id.linear_experienceAdd://新增工作经历
                if (MyUtils.isFastClick1()){
                    return;
                }
                //打开工作经历详情界面
                startExperienceActivity(true,0);
                break;
            default:
                break;
        }
    }

    /**
     * 修改找工作状态按钮
     */
    private void changeHuntingState(boolean isHunting) {
        //如果找工作状态已打开，点击打开不做反应，已关闭，点击关机不做反应
        if (isOpen==isHunting){
            return;
        }
        if (MyUtils.isFastClick()){
            return;
        }
        tvOpen.setEnabled(false);
        tvClose.setEnabled(false);
        if (isHunting){//打开
            getPresenter().changeHuntingState(resumeInfoData.getId(),"1");
        }else {//关闭
            getPresenter().changeHuntingState(resumeInfoData.getId(),"0");
        }
    }

    /**
     * 打开上传简历、上传作品界面
     * clazz：界面
     */
    private void startSomeActivity(Class<?> clazz) {
        Intent intent = new Intent(MyResumeActivity.this, clazz);
        intent.putExtra(ConstantUtil.KEY_DATA,resumeInfoData);
        startActivity(intent);
    }

    /**
     * 打开工作经历详情界面
     * isAdd：是新增还是编辑
     * position：工作经历列表的位标
     */
    private void startExperienceActivity(boolean isAdd,int position) {
        Intent intent = new Intent(MyResumeActivity.this, WorkExperienceActivity.class);
        intent.putExtra(ConstantUtil.KEY_WHETHER, isAdd);
        intent.putExtra(ConstantUtil.KEY_POSITION, position);
        intent.putExtra(ConstantUtil.KEY_DATA, resumeInfoData);
        startActivity(intent);
    }

    /**
     * 打开图片职位详情界面
     * isAdd：是新增还是编辑
     */
    private void startPositionActivity(boolean isAdd) {
        Intent intent = new Intent(MyResumeActivity.this, ResumePositionActivity.class);
        intent.putExtra(ConstantUtil.KEY_WHETHER, isAdd);
        intent.putExtra(ConstantUtil.KEY_DATA, resumeInfoData);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_resume);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_my_resume);

        initRecycleView();
        initAdapter();
        initHeadView();
        initRefreshLayout();
    }

    @Override
    protected MyResumePresenter createPresenter() {
        return new MyResumePresenter();
    }

    /**
     * 初始化 RecycleView
     */
    private void initRecycleView() {
        //设置刷新圈圈的颜色
        swipeRefreshLayout.setColorSchemeColors(Color.rgb(255, 179, 33));
        swipeRefreshLayout.setRefreshing(true);
        //设置分割线与垂直方向布局
        recyclerView.addItemDecoration(MyUtils.drawDivider(this, LinearLayout.VERTICAL, R.drawable.divider_f0f0f0_2));
        MyLayoutManager myLayoutManager = new MyLayoutManager(this, LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(myLayoutManager);
    }


    /**
     * 为 RecyclerView 加载 Adapter
     */
    private void initAdapter() {
        mAdapter = new ResumeExperienceAdapter();
        recyclerView.setAdapter(mAdapter);
        //子控件点击事件
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (MyUtils.isFastClick1()){
                    return;
                }
                //打开工作经历详情界面
                startExperienceActivity(false,position);
            }
        });
    }

    /**
     * 为 RecyclerView 添加头部控件
     */
    private void initHeadView() {
        View hv = getLayoutInflater().inflate(R.layout.item_hv_resume, null);
        mAdapter.addHeaderView(hv);

        //个人信息
        civHead = hv.findViewById(R.id.civ_head);
        tvName = hv.findViewById(R.id.tv_name);
        ivSex = hv.findViewById(R.id.iv_sex);
        //应聘职位信息
        llPositionAdd = hv.findViewById(R.id.linear_positionAdd);
        llPosition = hv.findViewById(R.id.linear_position);
        tvPositionName = hv.findViewById(R.id.tv_positionName);
        tvCategory = hv.findViewById(R.id.tv_category);
        tvPlatform = hv.findViewById(R.id.tv_platform);
        tvExperience = hv.findViewById(R.id.tv_experience);
        tvSalary = hv.findViewById(R.id.tv_salary);
        //客服与美工（打字速度&擅长风格）
        llSpeedTitle = hv.findViewById(R.id.linear_speedTitle);
        tvSpeedTitle = hv.findViewById(R.id.tv_speedTitle);
        tvSpeed = hv.findViewById(R.id.tv_speed);
        //是否找工作开关
        tvOpen = hv.findViewById(R.id.tv_open);
        tvClose = hv.findViewById(R.id.tv_close);

        //点击事件
        llPositionAdd.setOnClickListener(this);
        llPosition.setOnClickListener(this);
        tvOpen.setOnClickListener(this);
        tvClose.setOnClickListener(this);
        hv.findViewById(R.id.linear_info).setOnClickListener(this);
        hv.findViewById(R.id.linear_uploadResume).setOnClickListener(this);
        hv.findViewById(R.id.linear_uploadWorks).setOnClickListener(this);
        hv.findViewById(R.id.linear_experienceAdd).setOnClickListener(this);
    }

    /**
     * 下拉刷新 ---  下拉加载更多
     */
    private void initRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    //刷新
    private void refresh() {
        getPresenter().getInfo();
    }

    /**
     * IMyResumeView
     */
    @Override
    public AccountInfoData getUserInfo() {
        AccountInfoData userInfo = LoginUtil.getinit().getUserInfo();
        return userInfo;
    }
    @Override
    public void onRequestSuccess(ResumeInfoData tData) {
        resumeInfoData = tData;
        //头像(根据性别显示对应头像图片)
        if (tData.getPicture()==null||tData.getPicture().equals("")){
            if (tData.getSex().equals("1")){
                civHead.setImageResource(R.drawable.img_head_man);
            }else if (tData.getSex().equals("2")){
                civHead.setImageResource(R.drawable.img_head_women);
            }else {
                civHead.setImageResource(R.drawable.img_mine_head);
            }
        }else {
            GlideUtil.loadImage(tData.getPicture(),R.drawable.img_mine_head,civHead);
        }
        //名字
        if (tData.getName()==null||tData.getName().equals("")){
            tvName.setText(getUserInfo().getPhone());
        }else {
            tvName.setText(tData.getName());
        }
        //性别
        if (tData.getSex().equals("1")){
            ivSex.setImageResource(R.drawable.ic_sex_man);
        }else if (tData.getSex().equals("2")){
            ivSex.setImageResource(R.drawable.ic_sex_women);
        }else {
            ivSex.setVisibility(View.GONE);
        }
        //职位
        ConditionBean tow = tData.getTow();
        if (tow==null){
            llPositionAdd.setVisibility(View.VISIBLE);
            llPosition.setVisibility(View.GONE);
        }else {
            llPositionAdd.setVisibility(View.GONE);
            llPosition.setVisibility(View.VISIBLE);
            //职位名称
            tvPositionName.setText(tow.getName());
            //是否找工作 0: 否 1:是
            if (tData.getIs_job_hunting().equals("1")){
                setHuntingState(true);
            }else {
                setHuntingState(false);
            }
            //类目
            List<ConditionBean> bgatList = tData.getBgatList();
            String categoryStr = "";
            for (int i = 0; i <bgatList.size() ; i++) {
                if (i==0){
                    categoryStr = categoryStr + bgatList.get(i).getName();
                }else {
                    categoryStr = categoryStr + "/"+bgatList.get(i).getName();
                }
            }
            tvCategory.setText(categoryStr);
            //平台
            tvPlatform.setText(tData.getBgap().getName());
            //工作经验
            tvExperience.setText(tData.getWorking_years().getName());
            //薪资
            tvSalary.setText(tData.getMonthly_money()+"元/月");
            //美工-风格、客服-打字速度
            if (tow.getId().equals("1")||tow.getId().equals("2")){
                tvSpeedTitle.setVisibility(View.VISIBLE);
                tvSpeed.setVisibility(View.VISIBLE);
                if (tow.getId().equals("1")){
                    tvSpeedTitle.setText("擅长风格：");
                    List<ConditionBean> bgasList = tData.getBgasList();
                    String styleStr = "";
                    for (int i = 0; i <bgasList.size() ; i++) {
                        if (i==0){
                            styleStr = styleStr + bgasList.get(i).getName();
                        }else {
                            styleStr =styleStr + "/"+ bgasList.get(i).getName();
                        }
                    }
                    tvSpeed.setText(styleStr);
                }else {
                    tvSpeedTitle.setText("打字速度：");
                    tvSpeed.setText(tData.getTyping_speed());
                }
            }else {
                llSpeedTitle.setVisibility(View.GONE);
            }
        }
        //工作经历
        mAdapter.setNewData(tData.getWork_experience());
        swipeRefreshLayout.setRefreshing(false);
    }
    @Override
    public void onRequestFailure(String result) {
         ToastUtil.myToast(result);
        finish();
    }
    @Override//修改找工作状态
    public void onChangeHuntingResult(boolean isSuccess, String result) {
        tvOpen.setEnabled(true);
        tvClose.setEnabled(true);
         ToastUtil.myToast(result);
        if (isSuccess){
            isOpen = !isOpen;
            setHuntingState(isOpen);
        }
    }

    /**
     * 设置找工作按钮转态
     */
    private void setHuntingState(boolean isHunting) {
        isOpen=isHunting;
        tvOpen.setSelected(isHunting);
        tvClose.setSelected(!isHunting);
        if (isHunting){
            tvOpen.setBackground(getResources().getDrawable(R.drawable.shape_buttom_ffb321_38));
            tvClose.setBackground(null);
        }else {
            tvOpen.setBackground(null);
            tvClose.setBackground(getResources().getDrawable(R.drawable.shape_buttom_ffb321_38));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtil.cancleMyToast();
    }
}
