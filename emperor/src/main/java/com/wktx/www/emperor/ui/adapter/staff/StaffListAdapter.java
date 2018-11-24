package com.wktx.www.emperor.ui.adapter.staff;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.staff.staff.StaffInfoData;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.DateUtil;
import com.wktx.www.emperor.utils.GlideUtil;

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
        if (TextUtils.isEmpty(item.getName())){
            helper.setText(R.id.tv_name,"匿名用户");
        }else {
            helper.setText(R.id.tv_name,item.getName());
        }
        helper.setText(R.id.tv_hireMoney,"¥"+item.getHire_price());
        //雇佣时间
        String beginTime = DateUtil.getTimestamp2CustomType(item.getProject_start_time(), "yyyy.MM.dd");
        String endTime = DateUtil.getTimestamp2CustomType(item.getProject_end_time(), "yyyy.MM.dd");
        helper.setText(R.id.tv_hireTime,beginTime+"-"+endTime);

        //头像
        CircleImageView ivHead = helper.getView(R.id.iv_head);
        if (TextUtils.isEmpty(item.getPicture())){
            if (item.getSex().equals("1")){
                ivHead.setImageResource(R.drawable.img_head_man);
            }else if (item.getSex().equals("2")){
                ivHead.setImageResource(R.drawable.img_head_woman);
            }else {
                ivHead.setImageResource(R.drawable.img_mine_head);
            }
        }else {
            GlideUtil.loadImage(item.getPicture(),R.drawable.img_mine_head,ivHead);
        }
        //性别 1:男 2:女
        ImageView ivSex = helper.getView(R.id.iv_sex);
        if (item.getSex().equals("1")){
            ivSex.setVisibility(View.VISIBLE);
            ivSex.setImageResource(R.drawable.ic_sex_man);
        }else if (item.getSex().equals("2")){
            ivSex.setVisibility(View.VISIBLE);
            ivSex.setImageResource(R.drawable.ic_sex_woman);
        }else {
            ivSex.setVisibility(View.GONE);
        }
        //工作类型
        helper.setText(R.id.tv_staffJob,item.getTow_name());
        //支付状态
        if (item.getIs_pay().equals("0")){
            helper.setText(R.id.tv_payState,"未支付");
        }else {
            helper.setText(R.id.tv_payState,"托管中");
        }


        //雇佣状态 1:合作中(雇佣成功) 2:请假中 3:暂停中 4:投诉中 5:被解雇 6:完结 7:退款 8:已取消 9:续约中 10:待入职
        if (item.getIs_pay().equals("0")&&item.getType().equals("0")) {//未付款
            helper.setImageResource(R.id.iv_hireState, R.drawable.ic_hirerecord_state0);
        }else if (!item.getDismissal_id().equals("0")){//被解雇或者解雇中
            if (item.getType().equals("5")){
                helper.setImageResource(R.id.iv_hireState,R.drawable.ic_hirerecord_state5);
            }else {
                helper.setImageResource(R.id.iv_hireState,R.drawable.ic_hirerecord_state5ing);
            }
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
            }else if (item.getType().equals("8")){//已取消（支付超时、取消、拒绝）
                switch (item.getStatus_desc()){
                    case ConstantUtil.HIRESTATE_OVERTIME:
                        helper.setImageResource(R.id.iv_hireState,R.drawable.ic_hirerecord_state8_1);
                        break;
                    case ConstantUtil.HIRESTATE_CANCEL:
                        helper.setImageResource(R.id.iv_hireState,R.drawable.ic_hirerecord_state8_2);
                        break;
                    case ConstantUtil.HIRESTATE_REFUSED:
                        helper.setImageResource(R.id.iv_hireState,R.drawable.ic_hirerecord_state8_3);
                        break;
                    default:
                        break;
                }
            }else if (item.getType().equals("9")){//续约中
                helper.setImageResource(R.id.iv_hireState,R.drawable.ic_hirerecord_state9);
            }else if (item.getType().equals("10")){//待入职
                helper.setImageResource(R.id.iv_hireState,R.drawable.ic_hirerecord_state10);
            }
        }
    }
}
