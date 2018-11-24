package com.wktx.www.emperor.utils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.application.MyApplication;

import java.lang.ref.WeakReference;

/**
 * Glide图片加载缓存
 *
 * 依赖:
 * compile 'com.github.bumptech.glide:glide:3.7.0'
 * compile 'com.android.support:support-v4:25.3.0'
 * 如需将图片进行裁剪、模糊、滤镜等处理请依赖：
 * compile 'jp.wasabeef:glide-transformations:2.0.0'
 *
 * 权限:
 * <uses-permission android:name="android.permission.INTERNET" />
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 *
 * 混淆:
 * -keep public class * implements com.bumptech.glide.module.GlideModule
 * -keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
 * **[] $VALUES;
 * public *;
 * }
 */

public class GlideUtil {

    /**
     * 普通加载
     * @param imageUrls 图片路径
     * @param mImageView ImageView
     */
    public static void loadImage (String imageUrls,int loadImg,ImageView mImageView) {
        RequestOptions options = new RequestOptions();
        options.centerCrop()
                .dontAnimate()//禁用过渡动画
                .placeholder(loadImg)//加载中显示的图片
                .error(loadImg)//加载失败时显示的图片
                ;
//                .diskCacheStrategy(DiskCacheStrategy.NONE);//只缓存转换过后的图片

        //ImageView的资源回收问题
        final WeakReference<ImageView> imageViewWeakReference = new WeakReference<>(mImageView);
        ImageView target = imageViewWeakReference.get();
        if (target != null) {
            Glide.with(MyApplication.getContext())
                    .load(imageUrls)
                    .apply(options)
                    .into(target);
        }
    }

    /**
     * 普通加载---指定图片大小
     * @param imageUrls 图片路径
     * @param mImageView ImageView
     */
    public static void loadImageSize (String imageUrls, int loadImg, int errorImg,ImageView mImageView) {
        RequestOptions options = new RequestOptions();
        options.centerCrop()
                .dontAnimate()//禁用过渡动画
                .placeholder(loadImg)//加载中显示的图片
                .error(errorImg) //加载失败时显示的图片
                .override(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL)
                .fitCenter();
//                .diskCacheStrategy(DiskCacheStrategy.NONE);//只缓存转换过后的图片

        //ImageView的资源回收问题
        final WeakReference<ImageView> imageViewWeakReference = new WeakReference<>(mImageView);
        ImageView target = imageViewWeakReference.get();
        if (target != null) {
            Glide.with(MyApplication.getContext())
                    .load(imageUrls)
                    .apply(options)
                    .into(target);
        }
    }
}
