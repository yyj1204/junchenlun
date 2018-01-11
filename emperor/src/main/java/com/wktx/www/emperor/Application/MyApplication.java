package com.wktx.www.emperor.Application;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import java.io.File;


/**
 * @author yiw
 * @ClassName: MyApplication
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2015-12-28 下午4:21:08
 */
public class MyApplication extends Application {
    // 默认存放图片的路径
    public final static String DEFAULT_SAVE_IMAGE_PATH = Environment.getExternalStorageDirectory() + File.separator + "CircleDemo" + File.separator + "Images"
            + File.separator;
    private static Context mContext;
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        if (instance == null) {
            instance = this;
        }
        //开启debug模式，方便定位错误，具体错误检查方式可以查看http://dev.umeng.com/social/android/quick-integration的报错必看，正式发布，请关闭该模式
//        Config.DEBUG = false;
//        QueuedWork.isUseThreadPool = false;
//        UMShareAPI.get(this);
//        // PushService 为第三方自定义推送服务
////        PushManager.getInstance().initialize(this.getApplicationContext(), PushService.class);
////        //IntentService 为第三方自定义的推送服务事件接收类
////        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), IntentService.class);
    }
//
//    //各个平台的配置，建议放在全局Application或者程序入口
//    {
//        PlatformConfig.setWeixin("wx46c0c2a1b9188bf6", "d7e233e973771374882e53f18ec6582d");
//        PlatformConfig.setSinaWeibo("187207382", "4798e0024a8c2c93fae8adff9ec3ec6b", "https://api.weibo.com/oauth2/default.html");
//        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
//    }

    public static Context getContext() {
        return mContext;
    }

    public static MyApplication getInstance() {
        return instance;
    }

}
