package com.wktx.www.emperor.application;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.wktx.www.emperor.utils.ApiURL;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.cache.converter.SerializableDiskConverter;
import com.zhouyou.http.utils.HttpLog;
import com.zhy.autolayout.config.AutoLayoutConifg;

import java.io.File;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;


/**
 * @ClassName: MyApplication
 * @Description: TODO(这里用一句话描述这个类的作用)
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
        //设备的物理高度进行百分比化
        AutoLayoutConifg.getInstance().useDeviceSize();

        //开启debug模式，方便定位错误，具体错误检查方式可以查看http://dev.umeng.com/social/android/quick-integration的报错必看，正式发布，请关闭该模式
//        Config.DEBUG = false;
//        QueuedWork.isUseThreadPool = false;
//        UMShareAPI.get(this);
//        // PushService 为第三方自定义推送服务
//        PushManager.getInstance().initialize(this.getApplicationContext(), PushService.class);
//        //IntentService 为第三方自定义的推送服务事件接收类
//        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), IntentService.class);

        /**
         * RxJava2+Retrofit2
         * 网络请求框架
         */
        EasyHttp.init(this);//默认初始化,必须调用


        //以下设置的所有参数是全局参数,同样的参数可以在请求的时候再设置一遍,那么对于该请求来讲,请求中的参数会覆盖全局参数
        EasyHttp.getInstance()
                .setBaseUrl(ApiURL.GLOBAL_URL)//可以全局统一设置全局URL

                // 打开该调试开关并设置TAG,不需要就不要加入该行
                // 最后的true表示是否打印okgo的内部异常，一般打开方便调试错误
                .debug("RxEasyHttp", true)

                //如果使用默认的60秒,以下三行也不需要设置
//                .setReadTimeOut(60 * 1000)
//                .setWriteTimeOut(60 * 1000)
//                .setConnectTimeout(60 * 1000)

                //可以全局统一设置超时重连次数,默认为3次,那么最差的情况会请求4次(一次原始请求,三次重连请求),
                //不需要可以设置为0
                .setRetryCount(3)//默认网络不好自动重试3次
                //可以全局统一设置超时重试间隔时间,默认为500ms,不需要可以设置为0
                .setRetryDelay(500)//每次延时500ms重试
                //可以全局统一设置超时重试间隔叠加时间,默认为0ms不叠加
                .setRetryIncreaseDelay(500)//每次延时叠加500ms

                //全局设置自定义缓存保存转换器，主要针对自定义RxCache缓存
                .setCacheDiskConverter(new SerializableDiskConverter())//默认缓存使用序列化转化
                //全局设置自定义缓存大小，默认50M
                .setCacheMaxSize(50 * 1024 * 1024)//设置缓存大小为50M
                //设置缓存版本，如果缓存有变化，修改版本后，缓存就不会被加载。特别是用于版本重大升级时缓存不能使用的情况
                .setCacheVersion(1)//缓存版本为1
                .setHostnameVerifier(new UnSafeHostnameVerifier(ApiURL.GLOBAL_URL))//全局访问规则

                //可以设置https的证书,以下几种方案根据需要自己设置
                .setCertificates();//信任所有证书
//                .addConverterFactory(GsonConverterFactory.create(gson))//本框架没有采用Retrofit的Gson转化，所以不用配置
//                .addCommonHeaders(headers)//设置全局公共头
//                .addCommonParams(params)//设置全局公共参数
//                .addInterceptor(new CustomSignInterceptor());//添加参数签名拦截器
//                .addInterceptor(new HeTInterceptor());//处理自己业务的拦截器
    }

    public class UnSafeHostnameVerifier implements HostnameVerifier {
        private String host;

        public UnSafeHostnameVerifier(String host) {
            this.host = host;
            HttpLog.i("###############　UnSafeHostnameVerifier " + host);
        }

        @Override
        public boolean verify(String hostname, SSLSession session) {
            HttpLog.i("############### verify " + hostname + " " + this.host);
            if (this.host == null || "".equals(this.host) || !this.host.contains(hostname)){
                return false;
            }else {
                return true;
            }
        }
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
