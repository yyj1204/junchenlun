package com.wktx.www.emperor.utils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.wktx.www.emperor.application.MyApplication;

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
    public static void loadImage (String imageUrls, int loadImg , ImageView mImageView) {
        RequestOptions options = new RequestOptions();
        options.centerCrop()
                .placeholder(loadImg)//加载中显示的图片
                .error(loadImg) //加载失败时显示的图片
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);//只缓存转换过后的图片

        Glide.with(MyApplication.getContext())
                .load(imageUrls)
                .apply(options)
                .into(mImageView);
    }
}
