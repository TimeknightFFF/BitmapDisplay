package com.lyun.utils;

import android.graphics.Color;

import com.lyun.bitmapdisplay.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;

/**
 * Created by sm on 2016/5/27.
 */
public class ImageLoaderHelper {

    private static final ImageLoaderHelper imageLoaderHelper = new ImageLoaderHelper();

    public static ImageLoaderHelper getInstance(){
        return imageLoaderHelper;
    }

    public DisplayImageOptions getDisplayImageOptions(){

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.mipmap.ic_empty)
                .showImageOnLoading(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_error)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new CircleBitmapDisplayer(Color.WHITE))
                .build();
        return options;
    }
}
