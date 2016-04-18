package com.lyun.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by sm on 2016/4/18.
 * 调用手机相机相册
 */
public class DrawBitmapWithIntent {

    private Activity mContext;

    public final static int BITMAP_FROM_CAMERA = 1;   // 拍照
    public final static int BITMAP_FROM_PHOTO_ALBUM = 2; // 相册
    public final static int CROP_BITMAP = 3;  // 截图

    private File sdcard = null;
    private Intent intent;

    // 文件名，和上传文件有关。定义截取后的文件的名称
    public static String fileName = "icon.png";

    public DrawBitmapWithIntent(Activity context) {
        mContext = context;
        if (hasExtraSDCard()){
            sdcard = Environment.getExternalStorageDirectory();
        }else {
            Toast.makeText(mContext, "手机没有sdcard", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 使用手机相机拍摄图片
     */
    public void getBitmapFromCamera() {
        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(sdcard, fileName);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        mContext.startActivityForResult(intent, BITMAP_FROM_CAMERA);
    }

    /**
     * 获取手机相册中的图片
     */
    public void getBitmapFromPhotoAlbum() {
        intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        mContext.startActivityForResult(intent, BITMAP_FROM_PHOTO_ALBUM);
    }

    private boolean hasExtraSDCard() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/png");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        mContext.startActivityForResult(intent, CROP_BITMAP);
    }

    public void setPicToView(Intent picdata, ImageView image) {
        Bundle extras = picdata.getExtras();
        // 将图片放在/sdcard下
        Bitmap photo = extras.getParcelable("data");
        if (photo != null) {
            try {
                photo.compress(Bitmap.CompressFormat.PNG, 0,
                        new FileOutputStream(new File(sdcard,
                                fileName)));

                image.setImageBitmap(photo);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }


}
