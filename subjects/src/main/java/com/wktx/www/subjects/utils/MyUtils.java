package com.wktx.www.subjects.utils;
/**
 * 自定义工具类
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.widget.Toast;


import com.wktx.www.subjects.widget.MyToast;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MyUtils {

    /**
     * 自定义弹窗
     */
    public static void showToast(Context context, String toastString) {
        MyToast myToast = MyToast.makeText(context, toastString, Toast.LENGTH_SHORT);
        myToast.setGravity(Gravity.CENTER, 0, 0);
        myToast.show();
    }

    /**
     * 验证手机号码格式
     */
    public static boolean checkMobileNumber(String mobileNumber) {
        boolean flag = false;
        try {
            Pattern regex = Pattern.compile("^((1[0-9])\\d{9})$");
            Matcher matcher = regex.matcher(mobileNumber);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 验证邮箱格式
     */
    public static boolean isEmail(String email) {
        boolean flag = false;
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        try {
            Pattern regex = Pattern.compile(str);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
    /**
     * 设置手机号码中间4位*
     * 如（181****1675）
     */
    public static String setPhone(String mobileNumber) {
        StringBuilder sb  =new StringBuilder();
        if(!TextUtils.isEmpty(mobileNumber) && mobileNumber.length() > 6 ){
            for (int i = 0; i < mobileNumber.length(); i++) {
                char c = mobileNumber.charAt(i);
                if (i >= 3 && i <= 6) {
                    sb.append('*');
                } else {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }


    /**
     *防止按钮频繁点击
     */
    private static long lastClickTime;

    public static boolean isFastClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 2500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }


    /**
     * 订单号必须唯一。
     * @return
     */
    public static String getOrderSn(String orderSn) {
        StringBuffer sb = new StringBuffer();
        sb.append(orderSn);
        String ranString = "";
        Random r = new Random();
        for (int i = 0; i <4; i++) {
            ranString += r.nextInt(10);
        }
        sb.append(ranString);
        return sb.toString();
    }

    /**
     * 判断是否安装目标应用
     * @param packageName 目标应用安装后的包名
     * @return 是否已安装目标应用
     * 高德地图：com.autonavi.minimap
     * 百度地图：com.baidu.BaiduMap
     */
    public static boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }

    /**
     * 通过Base32将Bitmap转换成Base64字符串
     * @param bit
     * @return
     */
    public static String Bitmap2StrByBase64(Bitmap bit){
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG, 100, bos);//参数100表示不压缩
        byte[] bytes=bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    /**
     * 保存文件
     * @param bm
     * @throws IOException
     */
    public static File saveFile(Bitmap bm) throws IOException {
        String path = Environment.getExternalStorageDirectory().toString()+"/xpcw/icon_bitmap/";
        File dirFile = new File(path);
        if(!dirFile.exists()){
            dirFile.mkdirs();
        }
        File myIconFile= new File(path + "headicon.jpg");
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myIconFile));
//        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
        return myIconFile;
    }


    /**
     * 从本地获取图片
     * @param pathString 文件路径
     * @return 图片
     */
    public static Bitmap getDiskBitmap(String pathString) {
        Bitmap bitmap = null;
        try {
            File file = new File(pathString);
            if(file.exists()) {
                bitmap = BitmapFactory.decodeFile(pathString);
            }
        } catch (Exception e) {
        }
        return bitmap;
    }
    /**
     * 根据图片的url路径获得Bitmap对象
     * @param url
     * @return
     */
    public static Bitmap returnBitmap(String url) {
        URL fileUrl = null;
        Bitmap bitmap = null;
        try {
            fileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            HttpURLConnection conn = (HttpURLConnection) fileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
