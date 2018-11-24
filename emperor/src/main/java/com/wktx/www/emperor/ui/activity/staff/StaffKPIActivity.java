package com.wktx.www.emperor.ui.activity.staff;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.staff.kpi.StaffKPIInfoData;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.staff.StaffKPIPresenter;
import com.wktx.www.emperor.ui.view.IView;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.GlideUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.utils.ToastUtil;
import com.wktx.www.emperor.widget.MyScrollView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.wktx.www.emperor.utils.MyUtils.checkQQApkExist;

/**
 * 管理我的员工---考核指标界面
 */
public class StaffKPIActivity extends ABaseActivity<IView,StaffKPIPresenter> implements IView<StaffKPIInfoData> {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.scrollview)
    MyScrollView scrollview;
    @BindView(R.id.linear_top)
    LinearLayout llTop;
    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.tv_staffName)
    TextView tvName;
    @BindView(R.id.tv_hireTime)
    TextView tvHireTime;
    @BindView(R.id.tv_hireTimeCount)
    TextView tvHireTimeCount;
    @BindView(R.id.tv_chuqinCount)
    TextView tvChuqinCount;
    @BindView(R.id.tv_workCount)
    TextView tvWorkCount;
    @BindView(R.id.tv_workExperience)
    TextView tvWorkExperience;
    @BindView(R.id.tv_category)
    TextView tvCategory;
    @BindView(R.id.tv_phoneNumber)
    TextView tvPhoneNumber;
    @BindView(R.id.tv_QQNumber)
    TextView tvQQNumber;
    @BindView(R.id.tv_city)
    TextView tvCity;

    private String categoryStr="";//类目名称（多个）
    private String qqStr="";
    private String phoneStr="";

    @OnClick({R.id.tb_IvReturn,R.id.linear_phone,R.id.linear_qq})
    public void MyOnclick(View view) {
        if (MyUtils.isFastClick()){
            return;
        }
        switch (view.getId()) {
            case R.id.tb_IvReturn://返回
                finish();
                break;
            case R.id.linear_phone://联系方式
                if (TextUtils.isEmpty(phoneStr)||phoneStr.equals("0")){
                    ToastUtil.myToast("未留下手机号！");
                }else {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:" + phoneStr);
                    intent.setData(data);
                    startActivity(intent);
                }
                break;
            case R.id.linear_qq://QQ号码
                if (TextUtils.isEmpty(qqStr)){
                    ToastUtil.myToast("未留下QQ号！");
                }else {
                    if (checkQQApkExist(StaffKPIActivity.this, "com.tencent.mobileqq")){
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin="+qqStr+"&version=1")));
                    }else{
                        ToastUtil.myToast("本机未安装QQ应用！");
                    }
                }
                break;
            default:
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_kpi);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        initScrollListener();
        initData();
    }

    @Override
    protected StaffKPIPresenter createPresenter() {
        return new StaffKPIPresenter();
    }

    //实现标题栏透明度随之渐变
    private void initScrollListener() {
        tvTitle.setAlpha(0);//先设置看不见
        scrollview.setOnScrollViewListener(new MyScrollView.ScrollViewListener() {
            @Override
            public void onScrollChange(int height) {
                int titleHeight = tvTitle.getHeight()+llTop.getHeight();
                if (height <= titleHeight) {
                    //如果滑动高度小于标题控件的高度，透明度逐渐减弱
                    tvTitle.setAlpha((float)height/titleHeight);
                }else {
                    //如果滑动高度大于标题控件的高度，标题直接显示出来
                    tvTitle.setAlpha(1);
                }
            }
        });
    }

    /**
     * 接收 StaffManageActivity 传递过来的雇佣id
     */
    private void initData() {
        String hireId = getIntent().getStringExtra(ConstantUtil.KEY_POSITION);
        getPresenter().onGetKPIInfo(hireId);
    }

    /**
     * IView
     */
    @Override
    public void onRequestSuccess(StaffKPIInfoData tData) {
        //头像
        if (TextUtils.isEmpty(tData.getPicture())){
            if (tData.getSex().equals("1")){
                ivHead.setImageResource(R.drawable.img_head_man);
            }else if (tData.getSex().equals("2")){
                ivHead.setImageResource(R.drawable.img_head_woman);
            }else {
                ivHead.setImageResource(R.drawable.img_mine_head);
            }
        }else {
            GlideUtil.loadImage(tData.getPicture(),R.drawable.img_mine_head,ivHead);
        }
        //员工姓名
        tvName.setText("员工："+tData.getName());
        //雇佣结束时间
        tvHireTime.setText("雇佣至："+tData.getProject_end_time());
        tvHireTimeCount.setText(tData.getWork_days()+"天");
        tvChuqinCount.setText(tData.getAttendance_days()+"天");
        tvWorkCount.setText(tData.getArrangement_work_count()+"次");
        //所在城市
        if (TextUtils.isEmpty(tData.getResidential_city())){
            tvCity.setText("未设置");
        }else {
            tvCity.setText(tData.getResidential_city());
        }

        phoneStr = tData.getPhone();
        qqStr = tData.getQq();
        tvPhoneNumber.setText(phoneStr);
        tvQQNumber.setText(qqStr);

        //工作经验
        String workingYears = tData.getWorking_years();
        if (workingYears.equals("未设置")){
            workingYears="经验不限";
        }
        tvWorkExperience.setText(workingYears);

        //擅长类目(多个)
        List<StaffKPIInfoData.BgatBean> categoryList = tData.getBgat();
        if (categoryList.size()!=0){
            for (int i = 0; i <categoryList.size() ; i++) {
                if (i==0){
                    categoryStr = categoryList.get(i).getName();
                }else {
                    categoryStr = categoryStr +"/"+categoryList.get(i).getName();
                }
            }
        }
        tvCategory.setText(categoryStr);
    }
    @Override
    public void onRequestFailure(String result) {
        ToastUtil.myToast(result);
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtil.cancleMyToast();
    }
}
