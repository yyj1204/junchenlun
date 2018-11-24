package com.wktx.www.emperor.ui.activity.recruit.resume;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.wktx.www.emperor.apiresult.recruit.demand.DemandListInfoData;
import com.wktx.www.emperor.apiresult.recruit.resume.ResumeInfoData;
import com.wktx.www.emperor.apiresult.recruit.retrievalcondition.Bean;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.recruit.resume.ResumePresenter;
import com.wktx.www.emperor.ui.activity.login.LoginActivity;
import com.wktx.www.emperor.ui.activity.mine.store.StoreInfoEditActivity;
import com.wktx.www.emperor.ui.activity.recruit.demand.DemandActivity;
import com.wktx.www.emperor.ui.activity.recruit.demand.DemandReleaseActivity;
import com.wktx.www.emperor.ui.activity.recruit.hire.HireActivity;
import com.wktx.www.emperor.ui.activity.recruit.hire.TrusteeshipSalaryActivity;
import com.wktx.www.emperor.ui.activity.staff.StaffManageActivity;
import com.wktx.www.emperor.ui.fragment.resume.ResumeEvaluateFragment;
import com.wktx.www.emperor.ui.fragment.resume.ResumeWorksFragment;
import com.wktx.www.emperor.ui.fragment.resume.ResumeFragment;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.ui.view.recruit.IResumeView;
import com.wktx.www.emperor.utils.ToastUtil;
import com.wktx.www.emperor.widget.CustomDialog;
import com.wktx.www.emperor.widget.PopupInterview;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.wktx.www.emperor.utils.MyUtils.checkQQApkExist;

/**
 * 简历
 */
public class ResumeActivity extends ABaseActivity<IResumeView,ResumePresenter> implements IResumeView {
    public static ResumeActivity instance = null;

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.layout_smartTab)
    SmartTabLayout smartTab;
    @BindView(R.id.tv_employ)
    TextView tvEmploy;

    private ResumeInfoData resumeInfoData;//简历信息
    private String resumeId;//简历ID

    private String wechatStr="";
    private String qqStr="";
    private String phoneStr="";

    private ArrayList<Bean> demandBeans = new ArrayList<>();//需求标题
    private String demandId;//需求Id

    private PopupInterview popupInterView;//应聘弹窗
    private CustomDialog customDialog;
    private OptionsPickerView pvCustomOptions;//自义定选择器
    private ArrayList<String> optionsItemStrs = new ArrayList<>();//选择器字符串集合


    @OnClick({ R.id.iv_return,R.id.tv_employ,R.id.tv_interview})
    public void MyOnclick(View view) {
        if (MyUtils.isFastClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.iv_return:
                finish();
                break;
            case R.id.tv_employ://立即雇佣
                if (getUserInfo()!=null){
                    Intent intent = new Intent();
                    if (!resumeInfoData.getHire_id().equals("0")){//不为0:正在被我雇佣,前往我的员工界面
                        intent.setClass(ResumeActivity.this, StaffManageActivity.class);
                        intent.putExtra(ConstantUtil.KEY_DATA,resumeInfoData.getHire_id());
                        startActivity(intent);
                    }else {//0:未被我雇佣
                        //1:有未支付的雇佣订单,未支付订单雇佣Id传递给 TrusteeshipSalaryActivity
                        if (resumeInfoData.getNo_pay_order().equals("1")){
                            intent.setClass(ResumeActivity.this, TrusteeshipSalaryActivity.class);
                            intent.putExtra(ConstantUtil.KEY_DATA,resumeInfoData.getNo_pay_order_id());
                            intent.putExtra(ConstantUtil.KEY_POSITION,ConstantUtil.ACTIVITY_JL);
                            startActivity(intent);
                        }else {//0:没有未支付的雇佣订单,将简历信息传递给 HireActivity
                            intent.setClass(ResumeActivity.this, HireActivity.class);
                            intent.putExtra(ConstantUtil.KEY_DATA,resumeInfoData);
                            startActivity(intent);
                        }
                    }
                }else {
                    startActivity(new Intent(ResumeActivity.this, LoginActivity.class));
                }
                break;
            case R.id.tv_interview://邀请面试
                if (getUserInfo()!=null){//已登录---触发应聘popuwindow
                    showInterviewPopup();
                }else {//未登录---登录界面
                    startActivity(new Intent(ResumeActivity.this, LoginActivity.class));
                }
                break;
            default:
                break;
        }
    }

    /**
     * 应聘弹窗
     */
    private void showInterviewPopup() {
        popupInterView = new PopupInterview(ResumeActivity.this, ResumeActivity.this);
        popupInterView.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupInterView.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupInterView.setClippingEnabled(false);
        popupInterView.showPopupWindow(tvEmploy);
        popupInterView.setOnGetInterviewWayClckListener(new PopupInterview.onGetInterviewWayClckListener() {
            @Override
            public void getInterviewWay(ConstantUtil.InterviewWay way) {
                switch (way){
                    case PHONE://手机联系
                        if (TextUtils.isEmpty(phoneStr)||phoneStr.equals("0")){
                            ToastUtil.myToast("该求职者未留下手机号！");
                        }else {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            Uri data = Uri.parse("tel:" + phoneStr);
                            intent.setData(data);
                            startActivity(intent);
                        }
                        break;
                    case QQ://QQ联系
                        if (TextUtils.isEmpty(qqStr)){
                            ToastUtil.myToast("该求职者未留下QQ号！");
                        }else {
                            if (checkQQApkExist(ResumeActivity.this, "com.tencent.mobileqq")){
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin="+qqStr+"&version=1")));
                            }else{
                                ToastUtil.myToast("本机未安装QQ应用！");
                            }
                        }
                        break;
                    case WX://微信联系
                        if (TextUtils.isEmpty(wechatStr)){
                            ToastUtil.myToast("该求职者未留下微信号！");
                        }else {
                            //将微信号复制
                            ClipboardManager copy = (ClipboardManager) ResumeActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                            //将文本内容放到系统剪贴板里
                            copy.setText(wechatStr);
                            ToastUtil.myToast("该求职者微信号复制成功，快去添加好友吧！");
                        }
                        break;
                    case INTERVIEW://邀请面试
                        getPresenter().onGetDemandList();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);
        instance = this;
        ButterKnife.bind(this);
        initData();
    }

    @Override
    protected ResumePresenter createPresenter() {
        return new ResumePresenter();
    }

    /**
     * 接收 HomeFragment、RecruitListFragment 、WorksDetailsActivity 、StaffManageActivity传递过来的简历ID
     */
    private void initData() {
        resumeId = getIntent().getStringExtra(ConstantUtil.KEY_POSITION);
        getPresenter().onGetResumeInfo(resumeId);
    }

    /**
     * IResumeView
     */
    @Override//面试员工
    public void onInterviewResult(boolean isSuccess, String msg) {
        if (isSuccess){
            msg="邀请面试成功！";
        }
        ToastUtil.myToast(msg);
    }

    @Override
    public void onDemandListSuccessResult(List<DemandListInfoData> result) {
        demandBeans.clear();
        for (int i = 0; i <result.size() ; i++) {
            Bean bean = new Bean();
            bean.setId(result.get(i).getId());
            bean.setName(result.get(i).getTitle());
            demandBeans.add(bean);
        }

        if (demandBeans.size()!=0){
            initCustomOptionPicker(demandBeans);
        }
    }
    @Override
    public void onDemandListFailureResult(String msg) {
        if (msg.equals("")){//没有需求数据
            showAddDemandDialog();
        }else {
            ToastUtil.myToast(msg);
        }
    }

    @Override
    public void onRequestSuccess(ResumeInfoData tData) {
        resumeInfoData=tData;
        String is_hiring = tData.getIs_hiring();
        if (tData.getIs_job_hunting().equals("0")){//不在找工作
            tvEmploy.setEnabled(false);
            tvEmploy.setText("不可雇佣");
            tvEmploy.setBackgroundResource(R.color.color_888888);
        }else if (tData.getIs_job_hunting().equals("1")){//找工作中
            if (!tData.getHire_id().equals("0")){//不为0:正在被我雇佣
                tvEmploy.setText("已雇佣");
                tvEmploy.setBackgroundResource(R.color.color_888888);
            }else {//0:未被我雇佣
                if (tData.getNo_pay_order().equals("1")){//1：有待支付订单
                    tvEmploy.setText("有未支付订单");
                    tvEmploy.setBackgroundResource(R.color.color_888888);
                }else {//0:没有待支付订单
                    tvEmploy.setText("立即雇佣(¥"+tData.getMonthly_money()+"/月)");
                    tvEmploy.setBackgroundResource(R.drawable.selector_submit_selected);
                }
            }

            //以下判断是一个员工只能接受一个雇主
//            if (is_hiring.equals("0")){
//                tvEmploy.setEnabled(true);
//                tvEmploy.setText("立即雇佣(¥"+tData.getMonthly_money()+"/月)");
//                tvEmploy.setBackgroundResource(R.drawable.selector_submit_selected);
//            }else if (is_hiring.equals("1")){
//                tvEmploy.setEnabled(false);
//                tvEmploy.setText("已被雇佣");
//                tvEmploy.setBackgroundResource(R.color.color_888888);
//            }
        }
        wechatStr = tData.getWechat();
        qqStr = tData.getQq();
        phoneStr = tData.getPhone();
        initSmartVp(tData);
    }
    @Override
    public void onRequestFailure(String result) {
        ToastUtil.myToast(result);
        finish();
    }


    /**
     * 初始化
     * @param tData
     */
    private void initSmartVp(ResumeInfoData tData) {
        FragmentPagerItems pages = new FragmentPagerItems(this);
        //绑定简历数据
        Bundle bundle1 = new Bundle();
        bundle1.putSerializable(ConstantUtil.KEY_DATA, tData);//简历内容
        Bundle bundle2 = new Bundle();
        bundle2.putSerializable(ConstantUtil.KEY_POSITION, resumeId);//简历ID
        pages.add(FragmentPagerItem.of("简历",ResumeFragment.class, bundle1));
        pages.add(FragmentPagerItem.of("评价("+tData.getEvaluate_num()+")", ResumeEvaluateFragment.class,bundle2));
        pages.add(FragmentPagerItem.of("案例", ResumeWorksFragment.class,bundle2));
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), pages);
        smartTab.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
                CharSequence pageTitle = adapter.getPageTitle(position);
                View v = LayoutInflater.from(ResumeActivity.this).inflate(R.layout.item_smarttab_resume, container, false);
                if (v != null) {
                    TextView textView = (TextView) v.findViewById(R.id.textView);
                    textView.setText(pageTitle);
                }
                return v;
            }
        });
        viewPager.setAdapter(adapter);
        smartTab.setViewPager(viewPager);
    }

    /**
     * 发布需求对话框
     */
    private void showAddDemandDialog() {
        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setTitle("系统提示");
        builder.setMessage("您还未发布任何需求，发布后才可邀请面试！");
        builder.setPositiveButton("去发布", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //打开发布需求界面
                startActivity(new Intent(ResumeActivity.this, DemandReleaseActivity.class));
            }
        });

        builder.setNegativeButton("取消邀请",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        customDialog = builder.create();
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.show();
    }

    /**
     * 自定义选择器
     */
    private void initCustomOptionPicker(final ArrayList<Bean> list) {//条件选择器初始化，自定义布局
        /**
         * 注意事项：
         * 自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针。
         * 具体可参考demo 里面的两个自定义layout布局。
         */
        pvCustomOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String name = list.get(options1).getName();
                demandId = list.get(options1).getId();
                //邀请面试
                getPresenter().onInterview(resumeId,demandId);
            }
        })
                .setLayoutRes(R.layout.widget_custom_pickerview, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        TextView tvTitle = (TextView) v.findViewById(R.id.tv_title);
                        TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        TextView tvCancel = (TextView) v.findViewById(R.id.tv_cancel);

                        tvTitle.setText("职位需求选择");
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.returnData();
                                pvCustomOptions.dismiss();
                            }
                        });
                        tvCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.dismiss();
                            }
                        });
                    }
                })
                .isDialog(false)
                .setOutSideCancelable(false)
                .build();
        optionsItemStrs.clear();
        for (int i = 0; i <list.size() ; i++) {
            optionsItemStrs.add(list.get(i).getName());
        }
        pvCustomOptions.setPicker(optionsItemStrs);//添加数据
        pvCustomOptions.show();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (customDialog!=null){
            customDialog.dismiss();
            customDialog=null;
        }
        ToastUtil.cancleMyToast();
    }
}
