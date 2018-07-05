package com.wktx.www.emperor.ui.adapter.mine;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.mine.transactionrecord.TransactionBean;
import com.wktx.www.emperor.utils.DateUtil;

/**
 * Created by yyj on 2018/1/26.
 * 交易记录列表适配器
 */
public class TransactionListAdapter extends BaseQuickAdapter<TransactionBean, BaseViewHolder> {
    public TransactionListAdapter() {
        super(R.layout.item_rv_transactionrecord, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, TransactionBean item) {
        if (item.getRemark()==null||item.getRemark().equals("")){
            helper.setText(R.id.tv_remark,"无备注");
        }else {
            helper.setText(R.id.tv_remark,item.getRemark());
        }
        helper.setText(R.id.tv_time, DateUtil.getTimestamp2CustomType(item.getAdd_time(),"yyyy.MM.dd hh:mm:ss"));
        //判断金额格式，小数点后为"00"，直接取整
        String money = item.getAmount();
        String[] mSplit = money.split("[.]");
        String moneyStr="";
        if (mSplit[1].equals("00")){
            moneyStr=mSplit[0];
        }else {
            moneyStr=money;
        }
        helper.setText(R.id.tv_money,"-"+moneyStr);
    }
}
