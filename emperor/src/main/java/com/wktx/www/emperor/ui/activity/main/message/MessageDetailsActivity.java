package com.wktx.www.emperor.ui.activity.main.message;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.main.message.MessageDetailsInfoData;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.main.MessageDetailsPresenter;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.DateUtil;
import com.wktx.www.emperor.ui.view.IView;
import com.wktx.www.emperor.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 消息通知---详情
 */
public class MessageDetailsActivity extends ABaseActivity<IView,MessageDetailsPresenter> implements IView<MessageDetailsInfoData> {
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
        setContentView(R.layout.activity_message_details);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        initData();
    }

    @Override
    protected MessageDetailsPresenter createPresenter() {
        return new MessageDetailsPresenter();
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
