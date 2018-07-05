package com.wktx.www.subjects.ui.activity.main;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.login.AccountInfoData;
import com.wktx.www.subjects.apiresult.main.PositionDetailsInfoData;
import com.wktx.www.subjects.basemvp.ABaseActivity;
import com.wktx.www.subjects.presenter.main.PositionDetailsPresenter;
import com.wktx.www.subjects.ui.activity.login.LoginActivity;
import com.wktx.www.subjects.ui.activity.message.CompanyRecruitActivity;
import com.wktx.www.subjects.ui.view.main.IPositionDetailsView;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.GlideUtil;
import com.wktx.www.subjects.utils.LoginUtil;
import com.wktx.www.subjects.utils.MyUtils;
import com.wktx.www.subjects.utils.ToastUtil;
import com.wktx.www.subjects.widget.PopupInterview;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.wktx.www.subjects.utils.MyUtils.checkQQApkExist;

/**
 * 职位招聘详情
 */
public class PositionDetailsActivity extends ABaseActivity<IPositionDetailsView,PositionDetailsPresenter> implements IPositionDetailsView {
    public static PositionDetailsActivity instance = null;
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.tv_demandTitle)
    TextView tvDemandTitle;
    @BindView(R.id.tv_demandSalary)
    TextView tvDemandSalary;
    @BindView(R.id.tv_category)
    TextView tvCategory;
    @BindView(R.id.tv_platform)
    TextView tvPlatform;
    @BindView(R.id.tv_companyName)
    TextView tvCmpanyName;
    @BindView(R.id.tv_companySite)
    TextView tvCmpanySite;
    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.tv_storeName)
    TextView tvStoreName;
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

    private String wechatStr="";
    private String qqStr="";
    private String phoneStr="";

    private PopupInterview popupInterView;//应聘弹窗

    @OnClick({R.id.tb_IvReturn,R.id.linear_collect,R.id.linear_company,R.id.bt_interview})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn:
                finish();
                break;
            case R.id.linear_collect://收藏（取消收藏）职位招聘
                if (MyUtils.isFastClick()){
                    return;
                }

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
                    CompanyRecruitActivity.instance.finish();
                }
                //打开公司招聘列表界面
                Intent intent = new Intent(PositionDetailsActivity.this, CompanyRecruitActivity.class);
                intent.putExtra(ConstantUtil.KEY_WHETHER,ConstantUtil.ACTIVITY_ZPXQ);
                intent.putExtra(ConstantUtil.KEY_DATA,bossId);
                startActivity(intent);
                break;
            case R.id.bt_interview://立即应聘
                if (MyUtils.isFastClick()){
                    return;
                }

                if (getUserInfo()!=null){//已登录---触发应聘popuwindow
                    showIntervPopup();
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
    private void showIntervPopup() {
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
                        if (phoneStr.equals("")||phoneStr.equals("0")){
                            ToastUtil.myToast("该招聘者未留下手机号！");
                        }else {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            Uri data = Uri.parse("tel:" + phoneStr);
                            intent.setData(data);
                            startActivity(intent);
                        }
                        break;
                    case QQ://QQ联系
                        if (qqStr.equals("")){
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
                        if (wechatStr.equals("")){
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
                        btInterview.setEnabled(false);
                        getPresenter().sendResume(bossId,demandId);
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

    /**
     * 接收首页、搜索、面试记录、收藏 、公司招聘列表 传递过来的招聘需求id
     */
    private void initData() {
        isActivity = getIntent().getStringExtra(ConstantUtil.KEY_WHETHER);
        String demandId = getIntent().getStringExtra(ConstantUtil.KEY_DATA);
        getPresenter().getInfo(demandId);
    }

    /**
     * IPositionDetailsView
     */
    @Override
    public AccountInfoData getUserInfo() {
        AccountInfoData userInfo = LoginUtil.getinit().getUserInfo();
        return userInfo;
    }
    @Override//收藏回调
    public void onCancelCollectResult(boolean isSuccess, String result) {
        llCollect.setEnabled(true);
        ToastUtil.myToast(result);
        if (isSuccess){
            isCollect=!isCollect;
            ivCollect.setSelected(isCollect);
            if (isCollect){
                tvCollect.setText("已收藏");
            }else {
                tvCollect.setText("收藏");
            }
        }
    }
    @Override//投递简历回调
    public void onSendResumeResult(boolean isSuccess, String result) {
        btInterview.setEnabled(true);
        ToastUtil.myToast(result);
    }
    @Override//获取职位招聘详情
    public void onRequestSuccess(PositionDetailsInfoData tData) {
        demandId = tData.getId();
        bossId = tData.getUser_id();
        wechatStr = tData.getWeixin();
        qqStr = tData.getQq();
        phoneStr = tData.getPhone();
        tvDemandTitle.setText(tData.getTitle());
        tvDemandSalary.setText("¥"+tData.getBudget());
        tvDemandContent.setText(tData.getContent());
        tvCategory.setText(tData.getBgat_name());
        tvPlatform.setText(tData.getBgap_name());
        tvCmpanyName.setText(tData.getNickname());
        tvCmpanySite.setText(tData.getAddress_from());
        tvStoreName.setText(tData.getShop_name());
        tvAddTime.setText(tData.getAdd_time());
        tvEndTime.setText(tData.getEnd_time());
        //店铺头像
        if (tData.getShop_logo()==null||tData.getShop_logo().equals("")){
            ivHead.setImageResource(R.drawable.img_mine_head);
        }else {
            GlideUtil.loadImage(tData.getShop_logo(),R.drawable.img_mine_head,ivHead);
        }
        //收藏状态 	0:取消 1:收藏
        String isCollectStr = tData.getIs_collect();
        if (isCollectStr.equals("0")){
            isCollect=false;
            ivCollect.setSelected(isCollect);
            tvCollect.setText("收藏");
        }else if (isCollectStr.equals("1")){
            isCollect=true;
            ivCollect.setSelected(isCollect);
            tvCollect.setText("已收藏");
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
        ToastUtil.cancleMyToast();
    }
}
