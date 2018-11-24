package com.wktx.www.subjects.ui.activity.message;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.message.InviteDetailsInfoData;
import com.wktx.www.subjects.basemvp.ABaseActivity;
import com.wktx.www.subjects.presenter.message.InviteDetailsPresenter;
import com.wktx.www.subjects.ui.view.message.IInviteDetailsView;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.DateUtil;
import com.wktx.www.subjects.utils.GlideUtil;
import com.wktx.www.subjects.utils.MyUtils;
import com.wktx.www.subjects.utils.ToastUtil;
import com.wktx.www.subjects.widget.PopupContact;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.wktx.www.subjects.utils.MyUtils.checkQQApkExist;

/**
 * 消息 --- 邀请详情
 */
public class InviteDetailsActivity extends ABaseActivity<IInviteDetailsView,InviteDetailsPresenter> implements IInviteDetailsView {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.tv_companyName)
    TextView tvCompanyName;
    @BindView(R.id.tv_positionName)
    TextView tvPositionName;
    @BindView(R.id.tv_hireTime)
    TextView tvHireTime;
    @BindView(R.id.tv_companySite)
    TextView tvCompanySite;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_demandContent)
    TextView tvDemandContent;

    @BindView(R.id.tv_demandSalary)
    TextView tvDemandSalary;
    @BindView(R.id.tv_pushMoney)
    TextView tvPushMoney;

    @BindView(R.id.tv_contact)
    TextView tvContact;
    @BindView(R.id.tv_accept)
    TextView tvAccept;
    @BindView(R.id.tv_refuse)
    TextView tvRefuse;

    private String hireId;//雇佣id

    private String wechatStr="";
    private String qqStr="";
    private String phoneStr="";

    private PopupContact popupContact;//联系弹窗

    @OnClick({R.id.tb_IvReturn,R.id.tv_contact,R.id.tv_accept,R.id.tv_refuse})
    public void MyOnclick(View view) {
        if (MyUtils.isFastClick()){
            return;
        }
        switch (view.getId()) {
            case R.id.tb_IvReturn:
                finish();
                break;
            case R.id.tv_contact://联系
                showContactPopup();
                break;
            case R.id.tv_accept://接受邀请
                tvContact.setEnabled(false);
                tvAccept.setEnabled(false);
                tvRefuse.setEnabled(false);
                getPresenter().acceptInvite(hireId);
                break;
            case R.id.tv_refuse://拒绝
                tvContact.setEnabled(false);
                tvAccept.setEnabled(false);
                tvRefuse.setEnabled(false);
                getPresenter().refuseInvite(hireId);
                break;
            default:
                break;
        }
    }

    /**
     * 联系弹窗
     */
    private void showContactPopup() {
        popupContact = new PopupContact(InviteDetailsActivity.this, InviteDetailsActivity.this);
        popupContact.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupContact.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupContact.setClippingEnabled(false);
        popupContact.showPopupWindow(tvPositionName);
        popupContact.setOnGetInterviewWayClckListener(new PopupContact.onGetInterviewWayClckListener() {
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
                            if (checkQQApkExist(InviteDetailsActivity.this, "com.tencent.mobileqq")){
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
                            ClipboardManager copy = (ClipboardManager) InviteDetailsActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                            //将文本内容放到系统剪贴板里
                            copy.setText(wechatStr);
                            ToastUtil.myToast("该招聘者微信号复制成功，快去添加好友吧！");
                        }
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
        setContentView(R.layout.activity_invite_details);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_invite_details);
        initData();
    }

    @Override
    protected InviteDetailsPresenter createPresenter() {
        return new InviteDetailsPresenter();
    }

    /**
     * 接收 Message2InviteFragment、ManageFragment、HireRecordActivity、TradingRecordActivity 传递过来的 雇佣id
     */
    private void initData() {
        hireId = getIntent().getStringExtra(ConstantUtil.KEY_DATA);
        getPresenter().getInfo(hireId);
    }

    /**
     * IInviteDetailsView
     */
    @Override//接受（拒绝）邀请回调
    public void onInviteResult(boolean isSuccess, String result) {
        tvContact.setEnabled(true);
        tvAccept.setEnabled(true);
        tvRefuse.setEnabled(true);
        ToastUtil.myToast(result);
        if (isSuccess){
            finish();
        }
    }
    @Override
    public void onRequestSuccess(InviteDetailsInfoData tData) {
        wechatStr = tData.getWechat();
        qqStr = tData.getQq();
        phoneStr = tData.getPhone();
        String pushMoney = tData.getCommission();
        tvPositionName.setText(tData.getName());
        tvDemandSalary.setText("¥"+tData.getHire_price());
        tvPushMoney.setText("(提成:"+pushMoney+"%)");
        tvDemandContent.setText(tData.getDemand());
        tvCompanyName.setText(tData.getNickname());
        tvCompanySite.setText(tData.getAddress_from());
        //将时间格式yyyy-MM-dd HH:mm:ss转为yyyy.MM.dd
        String startTime = tData.getProject_start_time();
        String endTime = tData.getProject_end_time();
        String startTimeLong = DateUtil.getCustomType2Timestamp(startTime, "yyyy-MM-dd HH:mm:ss");
        String endTimeLong = DateUtil.getCustomType2Timestamp(endTime, "yyyy-MM-dd HH:mm:ss");
        String startTimeStr = DateUtil.getTimestamp2CustomType(startTimeLong, "yyyy.MM.dd");
        String endTimeStr = DateUtil.getTimestamp2CustomType(endTimeLong, "yyyy.MM.dd");
        tvHireTime.setText(startTimeStr+"-"+endTimeStr);
        tvTime.setText(tData.getAdd_time());
        //公司头像
        if (TextUtils.isEmpty(tData.getHead_pic())){
            ivHead.setImageResource(R.drawable.img_mine_head);
        }else {
            GlideUtil.loadImage(tData.getHead_pic(),R.drawable.img_mine_head,ivHead);
        }
        //提成
        if (TextUtils.isEmpty(pushMoney)||pushMoney.equals("0")){
            tvPushMoney.setVisibility(View.GONE);
        }else {
            tvPushMoney.setVisibility(View.VISIBLE);
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
