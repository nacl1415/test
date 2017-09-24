package com.app.myapplication;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.lang.reflect.Field;

/**
 * Created by nacl on 2017/9/22.
 */

public class ImgCreater {

    private static ImgCreater instance;

    public static ImgCreater getInstance()
    {
        if (instance == null) {
            instance = new ImgCreater();
        }
        return instance;
    }

    private ImgCreater()
    {
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    //, int reqWidth, int reqHeight
    public static Bitmap decodeSampledBitmapFromResource(
            Resources res, int resId) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();

        try {
            BitmapFactory.Options.class.getField("inNativeAlloc").setBoolean(options,true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        options.inJustDecodeBounds = true;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inPurgeable = true;
        options.inInputShareable = true;
        BitmapFactory.decodeResource(res, resId, options);
        // 调用上面定义的方法计算inSampleSize值
        float imageRatio = options.outWidth / options.outHeight;

        int imageViewWidth = 480;
        int imageRealHeight = (int) (imageViewWidth / imageRatio);
        options.inSampleSize = calculateInSampleSize(options, imageViewWidth, imageRealHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public int getImgID(Context context, String imgName)
    {
        try
        {
            Class res = R.drawable.class;
            Field field = res.getField(imgName);
            int drawableId = field.getInt(null);
            return drawableId;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
    }
}