package com.wktx.www.emperor.ui.adapter.main;

import android.content.Context;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.main.message.MessageListInfoData;
import com.wktx.www.emperor.utils.DateUtil;

/**
 * Created by yyj on 2018/1/26.
 * 消息通知---提醒适配器
 */
public class MessageRemindListAdapter extends BaseQuickAdapter<MessageListInfoData, BaseViewHolder> {
    private Context mContext;

    public MessageRemindListAdapter(Context context) {
        super(R.layout.item_rv_messageremind, null);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageListInfoData item) {
        helper.setText(R.id.tv_content,"【 "+item.getTitle()+" 】 "+item.getMessage());
        helper.setText(R.id.tv_time, DateUtil.getTimestamp2CustomType(item.getSend_time(),"yyyy/MM/dd HH:mm:ss"));
        TextView tvState = helper.getView(R.id.tv_state);
        if (item.getStatus().equals("0")){//未查看
            tvState.setTextColor(mContext.getResources().getColor(R.color.color_ffb321));
        }else if (item.getStatus().equals("1")){//已查看
            tvState.setTextColor(mContext.getResources().getColor(R.color.color_999999));
        }
    }
}
