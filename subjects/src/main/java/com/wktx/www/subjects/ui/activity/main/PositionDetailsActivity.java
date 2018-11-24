package com.wktx.www.subjects.ui.activity.main;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.main.demand.DemandDetailsInfoData;
import com.wktx.www.subjects.basemvp.ABaseActivity;
import com.wktx.www.subjects.presenter.main.PositionDetailsPresenter;
import com.wktx.www.subjects.ui.activity.login.LoginActivity;
import com.wktx.www.subjects.ui.activity.message.CompanyDemandActivity;
import com.wktx.www.subjects.ui.activity.mine.resume.MyResumeActivity;
import com.wktx.www.subjects.ui.view.main.IPositionDetailsView;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.GlideUtil;
import com.wktx.www.subjects.utils.MyUtils;
import com.wktx.www.subjects.utils.ToastUtil;
import com.wktx.www.subjects.widget.CustomDialog;
import com.wktx.www.subjects.widget.PopupInterview;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.wktx.www.subjects.utils.MyUtils.checkQQApkExist;

/**
 * 职位招聘需求详情
 */
public class PositionDetailsActivity extends ABaseActivity<IPositionDetailsView,PositionDetailsPresenter> implements IPositionDetailsView {
    public static PositionDetailsActivity instance = null;
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    //招聘条件
    @BindView(R.id.tv_position)
    TextView tvPosition;
    @BindView(R.id.tv_experience)
    TextView tvExperience;
    @BindView(R.id.tv_demandSalary)
    TextView tvDemandSalary;
    @BindView(R.id.tv_hireType)
    TextView tvHireType;
    @BindView(R.id.tv_category)
    TextView tvCategory;
    @BindView(R.id.tv_platform)
    TextView tvPlatform;
    @BindView(R.id.tv_patternTitle)
    TextView tvPatternTitle;
    @BindView(R.id.tv_pattern)
    TextView tvPattern;
    //公司信息
    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.tv_companyName)
    TextView tvCmpanyName;
    @BindView(R.id.tv_companySite)
    TextView tvCmpanySite;
    @BindView(R.id.tv_storeName)
    TextView tvStoreName;
    @BindView(R.id.tv_storeUrl)
    TextView tvStoreUrl;
    @BindView(R.id.tv_companyRemark)
    TextView tvCompanyRemark;
    //招聘内容
    @BindView(R.id.tv_demandTitle)
    TextView tvDemandTitle;
    @BindView(R.id.tv_demandContent)
    TextView tvDemandContent;

    //收藏
    @BindView(R.id.linear_collect)
    LinearLayout llCollect;
    @BindView(R.id.iv_collect)
    ImageView ivCollect;
    @BindView(R.id.tv_collect)
    TextView tvCollect;

    //发布时间、截止时间、过期状态
    @BindView(R.id.tv_addTime)
    TextView tvAddTime;
    @BindView(R.id.tv_endTime)
    TextView tvEndTime;
    @BindView(R.id.tv_overdueState)
    TextView tvOverdueState;

    @BindView(R.id.bt_interview)
    Button btInterview;

    private String isActivity;//是哪个界面进来的
    private boolean isCollect=false;//是否收藏 false:否 true:是


    private String demandId;//招聘id
    private String bossId;//雇主id
    private String isResumePosition;//我的简历里是否有添加应聘职位：0：没有，1：有

    private String wechatStr="";
    private String qqStr="";
    private String phoneStr="";

    private PopupInterview popupInterView;//应聘弹窗
    private CustomDialog customDialog;

    @OnClick({R.id.tb_IvReturn,R.id.linear_collect,R.id.linear_company,R.id.bt_interview})
    public void MyOnclick(View view) {
        if (MyUtils.isFastClick()){
            return;
        }

        switch (view.getId()) {
            case R.id.tb_IvReturn:
                finish();
                break;
            case R.id.linear_collect://收藏（取消收藏）职位招聘
                if (getUserInfo()!=null){//已登录
                    llCollect.setEnabled(false);
                    if (isCollect){//收藏---取消收藏
                        getPresenter().changeCollectState(demandId,"0");
                    }else {//未收藏---收藏
                        getPresenter().changeCollectState(demandId,"1");
                    }
                }else {//未登录---登录界面
                    startActivity(new Intent(PositionDetailsActivity.this, LoginActivity.class));
                }
                break;
            case R.id.linear_company://公司
                //关闭之前的招聘列表界面，重新创建
                if (isActivity!=null&&isActivity.equals(ConstantUtil.ACTIVITY_ZPLB)){
                    CompanyDemandActivity.instance.finish();
                }
                //打开公司招聘列表界面
                Intent intent = new Intent(PositionDetailsActivity.this, CompanyDemandActivity.class);
                intent.putExtra(ConstantUtil.KEY_WHETHER,ConstantUtil.ACTIVITY_ZPXQ);
                intent.putExtra(ConstantUtil.KEY_DATA,bossId);
                startActivity(intent);
                break;
            case R.id.bt_interview://立即应聘
                if (getUserInfo()!=null){//已登录---触发应聘popuwindow
                    showInterviewPopup();
                }else {//未登录---登录界面
                    startActivity(new Intent(PositionDetailsActivity.this, LoginActivity.class));
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
        popupInterView = new PopupInterview(PositionDetailsActivity.this, PositionDetailsActivity.this);
        popupInterView.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupInterView.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupInterView.setClippingEnabled(false);
        popupInterView.showPopupWindow(tvDemandTitle);
        popupInterView.setOnGetInterviewWayClckListener(new PopupInterview.onGetInterviewWayClckListener() {
            @Override
            public void getInterviewWay(ConstantUtil.InterviewWay way) {
                switch (way){
                    case PHONE://手机联系
                        if (TextUtils.isEmpty(phoneStr)||phoneStr.equals("0")){
                            ToastUtil.myToast("该招聘者未留下手机号！");
                        }else {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            Uri data = Uri.parse("tel:" + phoneStr);
                            intent.setData(data);
                            startActivity(intent);
                        }
                        break;
                    case QQ://QQ联系
                        if (TextUtils.isEmpty(qqStr)){
                            ToastUtil.myToast("该招聘者未留下QQ号！");
                        }else {
                            if (checkQQApkExist(PositionDetailsActivity.this, "com.tencent.mobileqq")){
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin="+qqStr+"&version=1")));
                            }else{
                                ToastUtil.myToast("本机未安装QQ应用！");
                            }
                        }
                        break;
                    case WX://微信联系
                        if (TextUtils.isEmpty(wechatStr)){
                            ToastUtil.myToast("该招聘者未留下微信号！");
                        }else {
                            //将微信号复制
                            ClipboardManager copy = (ClipboardManager) PositionDetailsActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                            //将文本内容放到系统剪贴板里
                            copy.setText(wechatStr);
                            ToastUtil.myToast("该招聘者微信号复制成功，快去添加好友吧！");
                        }
                        break;
                    case RESUME://投递简历
                        if (isResumePosition.equals("0")){//没有应聘职位---去添加再应聘
                            showAddPositionDialog();
                        }else {//有应聘职位---去应聘
                            btInterview.setEnabled(false);
                            getPresenter().sendResume(bossId,demandId);
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 应聘职位添加对话框
     */
    private void showAddPositionDialog() {
        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setTitle("系统提示");
        builder.setMessage("您的简历未添加应聘职位，添加后才可发送简历！");
        builder.setPositiveButton("去添加", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //打开我的简历界面
                startActivity(new Intent(PositionDetailsActivity.this, MyResumeActivity.class));
            }
        });

        builder.setNegativeButton("取消发送",
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_details);
        instance = this;
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_position_details);
        initData();
    }

    @Override
    protected PositionDetailsPresenter createPresenter() {
        return new PositionDetailsPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().getInfo(demandId);
    }

    /**
     * 接收首页、搜索、浏览记录、面试记录、收藏 、公司招聘列表 传递过来的招聘需求id
     */
    private void initData() {
        isActivity = getIntent().getStringExtra(ConstantUtil.KEY_WHETHER);
        demandId = getIntent().getStringExtra(ConstantUtil.KEY_DATA);

    }

    /**
     * IPositionDetailsView
     */
    @Override//收藏回调
    public void onCancelCollectResult(boolean isSuccess, String result) {
        llCollect.setEnabled(true);
        ToastUtil.myToast(result);
        if (isSuccess){
            isCollect=!isCollect;
            ivCollect.setSelected(isCollect);
        }
    }
    @Override//投递简历回调
    public void onSendResumeResult(boolean isSuccess, String result) {
        btInterview.setEnabled(true);
        ToastUtil.myToast(result);
    }
    @Override//获取职位招聘详情
    public void onRequestSuccess(DemandDetailsInfoData tData) {
        demandId = tData.getId();
        bossId = tData.getUser_id();
        isResumePosition = tData.getResume_tow();
        wechatStr = tData.getWeixin();
        qqStr = tData.getQq();
        phoneStr = tData.getPhone();

        //招聘条件
        tvPosition.setText(tData.getTow().getName());
        //工作经验
        String workingYears = tData.getWorking_years();
        if (workingYears.equals("未设置")){
            workingYears="经验不限";
        }
        tvExperience.setText(workingYears);
        tvDemandSalary.setText("¥"+tData.getBudget());
        tvHireType.setText("("+tData.getHire_type()+")");
        tvCategory.setText(tData.getBgat_name());
        tvPlatform.setText(tData.getBgap_name());
        tvPattern.setText(tData.getDesign_pattern());
        //设计模式
        String design_pattern = tData.getDesign_pattern();
        if (TextUtils.isEmpty(design_pattern)||design_pattern.equals("0")){
            tvPatternTitle.setVisibility(View.GONE);
            tvPattern.setVisibility(View.GONE);
        }else {
            tvPatternTitle.setVisibility(View.VISIBLE);
            tvPattern.setVisibility(View.VISIBLE);
        }
        //招聘内容
        tvDemandTitle.setText(tData.getTitle());
        tvDemandContent.setText(tData.getContent());
        //时间
        tvAddTime.setText("发布时间:"+tData.getAdd_time());
        tvEndTime.setText(tData.getEnd_time());
        //公司信息
        tvCmpanyName.setText(tData.getNickname());
        //公司地址
        if (TextUtils.isEmpty(tData.getAddress_from())){
            tvCmpanySite.setText("公司地址：未设置");
        }else {
            tvCmpanySite.setText(tData.getAddress_from());
        }
        tvStoreName.setText(tData.getShop_name());
        tvStoreUrl.setText("店铺网址："+tData.getShop_url());
        //公司介绍
        if (TextUtils.isEmpty(tData.getRemark())){
            tvCompanyRemark.setText("无");
        }else {
            tvCompanyRemark.setText(tData.getRemark());
        }
        //公司logo
        if (TextUtils.isEmpty(tData.getHead_pic())){
            ivHead.setImageResource(R.drawable.img_mine_head);
        }else {
            GlideUtil.loadImage(tData.getHead_pic(),R.drawable.img_mine_head,ivHead);
        }
        //收藏状态 	0:取消 1:收藏
        String isCollectStr = tData.getIs_collect();
        if (isCollectStr.equals("0")){
            isCollect=false;
            ivCollect.setSelected(isCollect);
        }else if (isCollectStr.equals("1")){
            isCollect=true;
            ivCollect.setSelected(isCollect);
        }
        //是否过期，0：未过期 1：已过期
        String is_dated = tData.getIs_dated();
        if (is_dated.equals("0")){
            tvOverdueState.setText("");
        }else if (is_dated.equals("1")){
            tvOverdueState.setText("已过期");
        }
    }
    @Override
    public void onRequestFailure(String result) {
        ToastUtil.myToast(result);
        finish();
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
