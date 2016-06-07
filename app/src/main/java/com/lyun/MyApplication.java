package com.lyun;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by sm on 2016/5/27.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initImageLoader(getApplicationContext());
    }

    public static void initImageLoader(Context context){
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2)
            .denyCacheImageMultipleSizesInMemory()
            .diskCacheFileNameGenerator(new Md5FileNameGenerator())
            .diskCacheSize(50 * 1024 * 1024)
            .tasksProcessingOrder(QueueProcessingType.LIFO);

        ImageLoader.getInstance().init(config.build());
    }
}
