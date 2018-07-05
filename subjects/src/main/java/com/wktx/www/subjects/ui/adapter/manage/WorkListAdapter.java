package com.wktx.www.subjects.ui.adapter.manage;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.manage.WorkListInfoData;
import com.wktx.www.subjects.utils.GlideUtil;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by yyj on 2018/1/26.
 * 我的工作列表 适配器
 */
public class WorkListAdapter extends BaseQuickAdapter<WorkListInfoData, BaseViewHolder> {
    private Context context;
    public WorkListAdapter(Context context) {
        super(R.layout.item_rv_manage_worklist, null);
        this.context = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, WorkListInfoData item) {
        helper.setText(R.id.tv_companyName,item.getNickname());
        helper.setText(R.id.tv_address,item.getAddress_from());
        //头像
        CircleImageView ivHead = helper.getView(R.id.iv_head);
        if (item.getHead_pic()==null||item.getHead_pic().equals("")){
            ivHead.setImageResource(R.drawable.img_mine_head);
        }else {
            GlideUtil.loadImage(item.getHead_pic(),R.drawable.img_mine_head,ivHead);
        }

        //解雇详情
        String fireId = item.getDismissal_id();
        TextView tvFire = helper.getView(R.id.tv_fireDetails);
        //如果解雇id=0，说明没有解雇信息，不显示查看解雇详情按钮
        if (fireId.equals("0")){
            tvFire.setVisibility(View.GONE);
        }else {
            //如果雇佣状态=被解雇，不显示查看解雇详情按钮
            if (item.getStatus().equals("5")){
                tvFire.setVisibility(View.GONE);
            }else {
                tvFire.setVisibility(View.VISIBLE);
            }
        }
        tvFire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //5.确定监听的时机，并且确定要传递的参数
                if (listener != null){
                    //查看解雇详情
                    listener.onFireDetails(helper.getLayoutPosition());
                }
            }
        });

        //雇佣状态
        if (!fireId.equals("0")) {//被解雇或者解雇中
            if (item.getStatus().equals("5")){
                helper.setImageResource(R.id.iv_hireState,R.drawable.ic_manage_state5);
            }else {
                helper.setImageResource(R.id.iv_hireState,R.drawable.ic_manage_state5ing);
            }
        }else {
            if (item.getStatus().equals("1")){//合作中
                helper.setImageResource(R.id.iv_hireState,R.drawable.ic_manage_state1);
            }else if (item.getStatus().equals("2")){//请假中
                helper.setImageResource(R.id.iv_hireState,R.drawable.ic_manage_state2);
            }else if (item.getStatus().equals("3")){//暂停中
                helper.setImageResource(R.id.iv_hireState,R.drawable.ic_manage_state3);
            }else if (item.getStatus().equals("4")){//投诉中
                helper.setImageResource(R.id.iv_hireState,R.drawable.ic_manage_state4);
            }else if (item.getStatus().equals("5")){//被解雇
                helper.setImageResource(R.id.iv_hireState,R.drawable.ic_manage_state5);
            }else if (item.getStatus().equals("6")){//已完结
                helper.setImageResource(R.id.iv_hireState,R.drawable.ic_manage_state6);
            }else if (item.getStatus().equals("7")){//已退款
                helper.setImageResource(R.id.iv_hireState,R.drawable.ic_manage_state7);
            }else if (item.getStatus().equals("9")){//续约中
                helper.setImageResource(R.id.iv_hireState,R.drawable.ic_manage_state9);
            }
        }
    }

    //1.定义监听接口
    public interface OnFireDetailsListener
    {
        //4.定义抽象方法
        void onFireDetails(int position);
    }
    //2.定义接口变量
    private OnFireDetailsListener listener;
    //3.定义设置监听接口方法
    public void setOnFireDetailsListener(OnFireDetailsListener l)
    {
        this.listener = l;
    }
}
