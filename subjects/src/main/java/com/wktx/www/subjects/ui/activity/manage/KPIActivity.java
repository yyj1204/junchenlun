package com.wktx.www.subjects.ui.activity.manage;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.manage.kpi.StaffKPIInfoData;
import com.wktx.www.subjects.basemvp.ABaseActivity;
import com.wktx.www.subjects.presenter.manage.KPIPresenter;
import com.wktx.www.subjects.ui.view.IView;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.GlideUtil;
import com.wktx.www.subjects.utils.MyUtils;
import com.wktx.www.subjects.utils.ToastUtil;
import com.wktx.www.subjects.widget.MyScrollView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.wktx.www.subjects.utils.MyUtils.checkQQApkExist;

/**
 * 管理我的工作---考核指标界面
 */
public class KPIActivity extends ABaseActivity<IView,KPIPresenter> implements IView<StaffKPIInfoData> {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.scrollview)
    MyScrollView scrollview;
    @BindView(R.id.linear_top)
    LinearLayout llTop;
    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.tv_companyName)
    TextView tvName;
    @BindView(R.id.tv_hireTime)
    TextView tvHireTime;
    @BindView(R.id.tv_hireTimeCount)
    TextView tvHireTimeCount;
    @BindView(R.id.tv_chuqinCount)
    TextView tvChuqinCount;
    @BindView(R.id.tv_workCount)
    TextView tvWorkCount;
    @BindView(R.id.tv_evaluateCount)
    TextView tvEvaluateCount;
    @BindView(R.id.tv_phoneNumber)
    TextView tvPhoneNumber;
    @BindView(R.id.tv_QQNumber)
    TextView tvQQNumber;
    @BindView(R.id.tv_wechatNumber)
    TextView tvWechatNumber;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.tv_companyRemark)
    TextView tvRemark;

    private String companyId;//公司id
    private String qqStr="";
    private String phoneStr="";
    private String wechatStr="";

    @OnClick({R.id.tb_IvReturn,R.id.iv_head,R.id.linear_phone,R.id.linear_qq,R.id.linear_wechat})
    public void MyOnclick(View view) {
        if (MyUtils.isFastClick()){
            return;
        }
        switch (view.getId()) {
            case R.id.tb_IvReturn://返回
                finish();
                break;
            case R.id.iv_head://打开公司店铺列表
                Intent intent1 = new Intent(KPIActivity.this, CompanyStoreActivity.class);
                intent1.putExtra(ConstantUtil.KEY_DATA,companyId);
                startActivity(intent1);
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
                    if (checkQQApkExist(KPIActivity.this, "com.tencent.mobileqq")){
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin="+qqStr+"&version=1")));
                    }else{
                        ToastUtil.myToast("本机未安装QQ应用！");
                    }
                }
                break;
            case R.id.linear_wechat://微信号码
                if (TextUtils.isEmpty(wechatStr)){
                    ToastUtil.myToast("未留下微信号！");
                }else {
                    //将微信号复制
                    ClipboardManager copy = (ClipboardManager) KPIActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                    //将文本内容放到系统剪贴板里
                    copy.setText(wechatStr);
                    ToastUtil.myToast("微信号复制成功，快去添加好友吧！");
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kpi);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        initScrollListener();
        initData();
    }

    @Override
    protected KPIPresenter createPresenter() {
        return new KPIPresenter();
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
     * 接收 ManageActivity 传递过来的雇佣id
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
        companyId = tData.getUser_id();
        //头像
        if (TextUtils.isEmpty(tData.getHead_pic())){
            ivHead.setImageResource(R.drawable.img_mine_head);
        }else {
            GlideUtil.loadImage(tData.getHead_pic(),R.drawable.img_mine_head,ivHead);
        }
        //雇主姓名
        tvName.setText("雇主："+tData.getNickname());
        //雇佣结束时间
        tvHireTime.setText("合作至："+tData.getProject_end_time());
        tvHireTimeCount.setText(tData.getWork_days()+"天");
        tvChuqinCount.setText(tData.getAttendance_days()+"天");
        tvWorkCount.setText(tData.getArrangement_work_count()+"份");
        tvEvaluateCount.setText(tData.getReport_evaluate_count()+"份");
        if (TextUtils.isEmpty(tData.getAddress_from())){
            tvCity.setText("无");
        }else {
            tvCity.setText(tData.getAddress_from());
        }
        if (TextUtils.isEmpty(tData.getAddress_from())){
            tvRemark.setText("无");
        }else {
            tvRemark.setText(tData.getRemark());
        }

        phoneStr = tData.getPhone();
        qqStr = tData.getQq();
        wechatStr = tData.getWechat();
        if (TextUtils.isEmpty(phoneStr)){
            tvPhoneNumber.setText("无");
        }else {
            tvPhoneNumber.setText(phoneStr);
        }
        if (TextUtils.isEmpty(qqStr)){
            tvQQNumber.setText("无");
        }else {
            tvQQNumber.setText(qqStr);
        }
        if (TextUtils.isEmpty(wechatStr)){
            tvWechatNumber.setText("无");
        }else {
            tvWechatNumber.setText(wechatStr);
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
