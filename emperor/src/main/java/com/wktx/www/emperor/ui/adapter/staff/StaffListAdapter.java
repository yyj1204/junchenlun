package com.wktx.www.emperor.ui.adapter.staff;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.staff.staff.StaffInfoData;
import com.wktx.www.emperor.utils.DateUtil;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by yyj on 2018/1/26.
 * 我的员工 \ 雇佣记录列表适配器
 */

public class StaffListAdapter extends BaseQuickAdapter<StaffInfoData, BaseViewHolder>{
    private Context mContext;

    public StaffListAdapter(Context context) {
        super(R.layout.item_rv_staff, null);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, StaffInfoData item) {
        helper.setText(R.id.tv_name,item.getName());
        helper.setText(R.id.tv_hireMoney,"¥"+item.getHire_price());
        //雇佣时间
        String beginTime = DateUtil.getTimestamp2CustomType(item.getProject_start_time(), "yyyy.MM.dd");
        String endTime = DateUtil.getTimestamp2CustomType(item.getProject_end_time(), "yyyy.MM.dd");
        helper.setText(R.id.tv_hireTime,beginTime+"-"+endTime);
        //支付状态
        if (item.getIs_pay().equals("0")){
            helper.setText(R.id.tv_payState,"未支付");
        }else {
            helper.setText(R.id.tv_payState,"托管中");
        }

        //头像
        CircleImageView ivHead = helper.getView(R.id.iv_head);
        if (!item.getPicture().equals("")){
            Glide.with(mContext).load(item.getPicture()).into(ivHead);
        }else {
            if (item.getSex().equals("1")){
                ivHead.setImageResource(R.drawable.img_head_man);
            }else if (item.getSex().equals("2")){
                ivHead.setImageResource(R.drawable.img_head_woman);
            }else {
            ivHead.setImageResource(R.drawable.img_mine_head);
            }
        }
        //性别 1:男 2:女
        if (item.getSex().equals("1")){
            helper.setImageResource(R.id.iv_sex,R.drawable.ic_sex_man);
        }else if (item.getSex().equals("2")){
            helper.setImageResource(R.id.iv_sex,R.drawable.ic_sex_woman);
        }
        //工作类型
        String tow = item.getTow();
        if (tow.equals("1")){
            helper.setText(R.id.tv_staffJob,"美工");
        }else if (tow.equals("2")){
            helper.setText(R.id.tv_staffJob,"客服");
        }else if (tow.equals("3")){
            helper.setText(R.id.tv_staffJob,"运营");
        }
        //雇佣状态
        if (item.getIs_pay().equals("0")){
            helper.setImageResource(R.id.iv_hireState,R.drawable.ic_hirerecord_state0);
        }else {
            if (item.getType().equals("1")){//合作中
                helper.setImageResource(R.id.iv_hireState,R.drawable.ic_hirerecord_state1);
            }else if (item.getType().equals("2")){//请假中
                helper.setImageResource(R.id.iv_hireState,R.drawable.ic_hirerecord_state2);
            }else if (item.getType().equals("3")){//暂停中
                helper.setImageResource(R.id.iv_hireState,R.drawable.ic_hirerecord_state3);
            }else if (item.getType().equals("4")){//投诉中
                helper.setImageResource(R.id.iv_hireState,R.drawable.ic_hirerecord_state4);
            }else if (item.getType().equals("5")){//被解雇
                helper.setImageResource(R.id.iv_hireState,R.drawable.ic_hirerecord_state5);
            }else if (item.getType().equals("6")){//完结
                helper.setImageResource(R.id.iv_hireState,R.drawable.ic_hirerecord_state6);
            }else if (item.getType().equals("7")){//退款
                helper.setImageResource(R.id.iv_hireState,R.drawable.ic_hirerecord_state7);
            }else if (item.getType().equals("8")){//未付款到期
                helper.setImageResource(R.id.iv_hireState,R.drawable.ic_hirerecord_state8);
            }else if (item.getType().equals("9")){//续约中
                helper.setImageResource(R.id.iv_hireState,R.drawable.ic_hirerecord_state9);
            }
        }
    }
}