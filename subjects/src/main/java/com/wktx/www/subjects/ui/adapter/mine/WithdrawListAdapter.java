package com.wktx.www.subjects.ui.adapter.mine;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.mine.withdraw.WithdrawListInfoData;

/**
 * Created by yyj on 2018/1/26.
 * 提现记录列表适配器
 */
public class WithdrawListAdapter extends BaseQuickAdapter<WithdrawListInfoData, BaseViewHolder> {
    public WithdrawListAdapter() {
        super(R.layout.item_rv_withdrawlist, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, WithdrawListInfoData item) {
        helper.setText(R.id.tv_title,"支付宝提现");
        helper.setText(R.id.tv_time, item.getAdd_time());
        helper.setText(R.id.tv_state, item.getStatus());
        //提现状态：审核中、未通过、已到账
        if (item.getStatus().equals("未通过")){
            helper.setGone(R.id.linear_remark,true);
        }else {
            helper.setGone(R.id.linear_remark,false);
        }
        if (TextUtils.isEmpty(item.getRemark())){
            helper.setText(R.id.tv_remark,"无");
        }else {
            helper.setText(R.id.tv_remark,item.getRemark());
        }
        //判断金额格式，小数点后为"00"，直接取整
        String money = item.getMoney();
        String[] mSplit = money.split("[.]");
        String moneyStr="";
        if (mSplit[1].equals("00")){
            moneyStr=mSplit[0];
        }else {
            moneyStr=money;
        }
        helper.setText(R.id.tv_money,moneyStr+"元");
    }
}
