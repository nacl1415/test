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
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
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
        final BitmapFactory.Options options = new BitmapFactory.Options();

        try {
            BitmapFactory.Options.class.getField("inNativeAlloc").setBoolean(options,true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        options.inJustDecodeBounds = true;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        BitmapFactory.decodeResource(res, resId, options);

        float imageRatio = options.outWidth / options.outHeight;
        int imageViewWidth = 480;
        int imageRealHeight = (int) (imageViewWidth / imageRatio);
        options.inSampleSize = calculateInSampleSize(options, imageViewWidth, imageRealHeight);
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