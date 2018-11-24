package com.wktx.www.subjects.ui.activity.main.notification;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.main.notification.MessageDetailsInfoData;
import com.wktx.www.subjects.basemvp.ABaseActivity;
import com.wktx.www.subjects.presenter.main.NotificationDetailsPresenter;
import com.wktx.www.subjects.ui.view.IView;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.DateUtil;
import com.wktx.www.subjects.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 系统消息通知---详情
 */
public class NotificationDetailsActivity extends ABaseActivity<IView,NotificationDetailsPresenter> implements IView<MessageDetailsInfoData> {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.tv_title)
    TextView tvMessageTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_content)
    TextView tvContent;

    @OnClick({R.id.tb_IvReturn})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn://返回
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_details);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        initData();
    }

    @Override
    protected NotificationDetailsPresenter createPresenter() {
        return new NotificationDetailsPresenter();
    }


    /**
     * 接收 MessageNoticeFragment MessageRemindFragment 传递过来的消息Id
     */
    private void initData() {
        String isActivity = getIntent().getStringExtra(ConstantUtil.KEY_POSITION);
        String messageId = getIntent().getStringExtra(ConstantUtil.KEY_DATA);
        getPresenter().onMessageInfo(messageId);
        if (isActivity.equals(ConstantUtil.ACTIVITY_GG)){
            tvTitle.setText(R.string.title_notice_details);
        }else {
            tvTitle.setText(R.string.title_remind_details);
        }
    }

    /**
     * IView
     */
    @Override
    public void onRequestSuccess(MessageDetailsInfoData tData) {
        tvMessageTitle.setText(tData.getTitle());
        tvTime.setText(DateUtil.getTimestamp2CustomType(tData.getSend_time(),"yyyy/MM/dd HH:mm:ss"));
        tvContent.setText(tData.getMessage());
    }
    @Override
    public void onRequestFailure(String result) {
        finish();
        ToastUtil.myToast(result);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtil.cancleMyToast();
    }
}
