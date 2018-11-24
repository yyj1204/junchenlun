package com.wktx.www.emperor.ui.adapter.staff;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.utils.GlideUtil;

/**
 * Created by yyj on 2018/6/7.
 * 评价员工---多张评价图片列表适配器
 */

public class StaffEvaluateAdapter extends BaseQuickAdapter<String, BaseViewHolder>{
    private Context mContext;

    public StaffEvaluateAdapter(Context context) {
        super(R.layout.item_rv_staffevaluate, null);
        this.mContext = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, String item) {
        ImageView ivWorksImage = helper.getView(R.id.iv_worksImage);
        GlideUtil.loadImage(item,R.drawable.img_loading,ivWorksImage);
        helper.getView(R.id.iv_deleteImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //5.确定监听的时机，并且确定要传递的参数
                if (listener != null){
                    //删除图片
                    listener.onDeleteImg(helper.getLayoutPosition());
                }
            }
        });
    }


    //1.定义监听接口
    public interface OnDeleteImgListener
    {
        //4.定义抽象方法
        void onDeleteImg(int position);
    }
    //2.定义接口变量
    private OnDeleteImgListener listener;
    //3.定义设置监听接口方法
    public void setOnDeleteImgListener(OnDeleteImgListener l)
    {
        this.listener = l;
    }
}
