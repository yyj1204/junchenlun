package com.wktx.www.emperor.utils;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by 369 on 2018/9/28.
 * 异步图片类
 */

public class ImageDownloadTask extends AsyncTask<Object,Object,Bitmap> {

    private ImageView imageView = null;

    private int _displaywidth = 720;
    private int _displayheight = 1280;
    private int _displaypixels = _displaywidth * _displayheight;
    /***
     * 这里获取到手机的分辨率大小
     * */
    public void setDisplayWidth(int width) {
        _displaywidth = width;
    }
    public int getDisplayWidth() {
        return _displaywidth;
    }
    public void setDisplayHeight(int height) {
        _displayheight = height;
    }
    public int getDisplayHeight() {
        return _displayheight;
    }
    public int getDisplayPixels() {
        return _displaypixels;
    }

    @Override
    protected Bitmap doInBackground(Object... params) {
        Bitmap bmp = null;
        imageView = (ImageView) params[1];
        try {
            String url = (String) params[0];
            bmp = getBitmap(url, _displaypixels,true);
        } catch (Exception e) {
            return null;
        }
        return bmp;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        if (imageView != null&&result!=null) {
            //获取图片的宽高与屏幕的宽高
//            int sWidth = result.getWidth();
//            int sHeight = result.getHeight();
//            //如果图片高度大于屏幕高度，且图片高度大于图片宽度的3倍
//            if (sHeight >= getDisplayHeight() && sHeight / sWidth >=3) {
//                imageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP);
//                // 将保存的图片地址给SubsamplingScaleImageView,这里注意设置ImageViewState设置初始显示比例
//                imageView.setImage(ImageSource.bitmap(result), new ImageViewState(0F, new PointF(0, 0), 0));
//            }else {
//                imageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM);
//                imageView.setImage(ImageSource.bitmap(result));
//                imageView.setDoubleTapZoomStyle(ZOOM_FOCUS_CENTER_IMMEDIATE);
//            }

//            imageView.setImage(ImageSource.bitmap(result));
            imageView.setImageBitmap(result);
            if (null != result && result.isRecycled() == false) {
                System.gc();
            }
        }
    }

    /**
     * 通过URL获得网上图片。如:http://www.xxxxxx.com/xx.jpg
     * */
    public Bitmap getBitmap(String url, int displaypixels, Boolean isBig) throws MalformedURLException, IOException {
        Bitmap bmp = null;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        InputStream stream = new URL(url).openStream();
        byte[] bytes = getBytes(stream);

        //这3句是处理图片溢出的begin( 如果不需要处理溢出直接 opts.inSampleSize=1;)
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
        opts.inSampleSize = computeSampleSize(opts, -1, displaypixels);
        //end
        opts.inJustDecodeBounds = false;
        bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
        return bmp;
    }

    /**
     * 数据流转成btyle[]数组
     * */
    private byte[] getBytes(InputStream is) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] b = new byte[2048];
        int len = 0;
        try {
            while ((len = is.read(b, 0, 2048)) != -1) {
                baos.write(b, 0, len);
                baos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = baos.toByteArray();
        return bytes;
    }

    /****
     * 处理图片bitmap size exceeds VM budget （Out Of Memory 内存溢出）
     */
    private int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
        LogUtil.error("大图2===","initialSize="+initialSize);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        LogUtil.error("大图3===","roundedSize="+roundedSize);
        return roundedSize;
    }

    private int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));
        LogUtil.error("大图1===","minSideLength="+minSideLength+",maxNumOfPixels="+maxNumOfPixels+",w="+w+",h="+h+",lowerBound="+lowerBound+",upperBound="+upperBound);
        if (upperBound < lowerBound) {
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }
}
