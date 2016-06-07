package com.lyun.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by sm on 2016/4/22.
 */
public class BitmapUtils {

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 只测图片大小，不实际分配内存
        BitmapFactory.decodeResource(res, resId, options);

        // 计算缩放比例
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = true;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;
        int inSampleSize = 1;

        if (outHeight > reqHeight || outWidth > reqWidth) {
            int halfHeight = outHeight / 2;
            int halfWidth = outWidth / 2;

            while ((halfHeight / inSampleSize) > reqHeight &&
                    (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
