package com.wktx.www.emperor.ui.adapter.mine;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.mine.coupon.CouponInfoData;
import com.wktx.www.emperor.utils.DateUtil;

/**
 * Created by yyj on 2018/1/26.
 * 我的红包列表适配器
 */
public class CouponListAdapter extends BaseQuickAdapter<CouponInfoData, BaseViewHolder> {
    private Context mContext;

    public CouponListAdapter(Context context) {
        super(R.layout.item_rv_mycoupon, null);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, CouponInfoData item) {
        //判断金额格式，小数点后为"00"，直接取整
        String money = item.getMoney();
        String condition = item.getCondition();
        String[] mSplit = money.split("[.]");
        String[] cSplit = condition.split("[.]");
        String conditionStr="";
        String moneyStr="";
        if (cSplit[1].equals("00")){
            conditionStr=cSplit[0];
        }else {
            conditionStr=condition;
        }
        if (mSplit[1].equals("00")){
            moneyStr=mSplit[0];
        }else {
            moneyStr=money;
        }
        helper.setText(R.id.tv_money,moneyStr+"元");
        helper.setText(R.id.tv_condition,"支付满"+conditionStr+"元可用");
        helper.setText(R.id.tv_from,"来源："+item.getFrom());
        helper.setText(R.id.tv_endTime, DateUtil.getTimestamp2CustomType(item.getEnd_time(),"yyyy-MM-dd")+"到期");
        if (item.getStatus().equals("0")){//未使用
            helper.setBackgroundRes(R.id.linear_coupon,R.drawable.img_mycoupon_unused);
            helper.setImageResource(R.id.iv_useState,R.drawable.ic_mycoupon_unused);
        }else if (item.getStatus().equals("1")){//已使用
            helper.setBackgroundRes(R.id.linear_coupon,R.drawable.img_mycoupon_used);
            helper.setImageResource(R.id.iv_useState,R.drawable.ic_mycoupon_used);
        }else if (item.getStatus().equals("2")){//已过期
            helper.setBackgroundRes(R.id.linear_coupon,R.drawable.img_mycoupon_used);
            helper.setImageResource(R.id.iv_useState,R.drawable.ic_mycoupon_pastdue);
        }
    }
}
