package com.wktx.www.emperor.ui.adapter.main;

import android.content.Context;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.main.message.MessageInfoData;
import com.wktx.www.emperor.utils.DateUtil;

/**
 * Created by yyj on 2018/1/26.
 * 消息通知---通告适配器
 */
public class MessageNoticeListAdapter extends BaseQuickAdapter<MessageInfoData, BaseViewHolder> {
    private Context mContext;

    public MessageNoticeListAdapter(Context context) {
        super(R.layout.item_rv_messagenotice, null);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageInfoData item) {
        helper.setText(R.id.tv_title,item.getTitle());
        helper.setText(R.id.tv_content,item.getMessage());
        helper.setText(R.id.tv_time, DateUtil.getTimestamp2CustomType(item.getSend_time(),"yyyy/MM/dd HH:mm:ss"));
        TextView tvState = helper.getView(R.id.tv_state);
        if (item.getStatus().equals("0")){//未查看
            tvState.setTextColor(mContext.getResources().getColor(R.color.color_fa716f));
        }else if (item.getStatus().equals("0")){//已查看
            tvState.setTextColor(mContext.getResources().getColor(R.color.color_999999));
        }
    }
}
