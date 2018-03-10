package com.wktx.www.emperor.ui.adapter.recruit;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hedgehog.ratingbar.RatingBar;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.recruit.resume.EvaluateInfoData;
import com.wktx.www.emperor.utils.ApiURL;

import java.util.List;

/**
 * Created by yyj on 2018/1/26.
 * 简历---评价列表适配器
 */

public class ResumeEvaluateAdapter extends BaseQuickAdapter<EvaluateInfoData, BaseViewHolder>{
    private Context mContext;

    public ResumeEvaluateAdapter(Context context) {
        super(R.layout.item_rv_resume_evaluate, null);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, EvaluateInfoData item) {
        helper.setText(R.id.tv_employerName,"雇主: "+item.getName());
        //服务类型 1:包月服务 2:定制服务
        if (item.getType().equals("1")){
            helper.setText(R.id.tv_serviceType,"包月服务");
        }else if (item.getType().equals("2")){
            helper.setText(R.id.tv_serviceType,"定制服务");

        }
        //设计模式  0:其他设计 1:详情页模板设计 2:首页设计 3:直通车设计
        // 4:营销活动设计 5:海报BANNERE设计 6:钻石展位设计 7:爆款策划设计
        String design_pattern = item.getDesign_pattern();
        if (design_pattern.equals("0")){
            helper.setText(R.id.tv_designPattern,"其他设计");
        }else if (design_pattern.equals("1")){
            helper.setText(R.id.tv_designPattern,"详情页模板设计");
        }else if (design_pattern.equals("2")){
            helper.setText(R.id.tv_designPattern,"首页设计");
        }else if (design_pattern.equals("3")){
            helper.setText(R.id.tv_designPattern,"直通车设计");
        }else if (design_pattern.equals("4")){
            helper.setText(R.id.tv_designPattern,"营销活动设计");
        }else if (design_pattern.equals("5")){
            helper.setText(R.id.tv_designPattern,"海报BANNERE设计");
        }else if (design_pattern.equals("6")){
            helper.setText(R.id.tv_designPattern,"钻石展位设计");
        }else if (design_pattern.equals("7")){
            helper.setText(R.id.tv_designPattern,"爆款策划设计");
        }
        //评价等级
        RatingBar rbServiceAttitude = helper.getView(R.id.rb_serviceAttitude);
        RatingBar rbDesignAbility = helper.getView(R.id.rb_designAbility);
        RatingBar rbResponseSpeed = helper.getView(R.id.rb_responseSpeed);
        rbServiceAttitude.setStar(Float.parseFloat(item.getService_attitude()));
        rbDesignAbility.setStar(Float.parseFloat(item.getAbility()));
        rbResponseSpeed.setStar(Float.parseFloat(item.getResponse_speed()));
        //评价内容
        if (item.getEvaluation_content().equals("")){
            helper.setText(R.id.tv_evaluateContent,"没有留下任何评价！");
        }else {
            helper.setText(R.id.tv_evaluateContent,item.getEvaluation_content());
        }
        //评价图片
        List<String> imageBeans = item.getImages();
        if (imageBeans.size()!=0){
            NineGridImageView nglWorks = helper.getView(R.id.ngl_evaluateImages);
            NineGridImageViewAdapter<String> adapter = new NineGridImageViewAdapter<String>() {
                @Override
                protected void onDisplayImage(Context context, ImageView imageView, String s) {
                    if (imageView != null && s != null) {
                        Glide.with(mContext).load(ApiURL.GLOBAL_IMG_URL+s).into(imageView);
                    }
                }
                @Override
                protected ImageView generateImageView(Context context) {
                    return super.generateImageView(context);
                }
                @Override
                protected void onItemImageClick(Context context, ImageView imageView, int index, List<String> list) {
                }
            };
            nglWorks.setAdapter(adapter);
            nglWorks.setImagesData(imageBeans);
            nglWorks.setSingleImgSize(450);
        }
    }
}
