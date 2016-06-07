package com.lyun.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.lyun.bitmapdisplay.R;

/**
 * Created by sm on 2016/4/22. 只是一张图片的Adapter
 */
public class ImageOneAdapter extends BaseAdapter{

    private LayoutInflater inflater;
    private Context mContext;

    public ImageOneAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 15;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.one_image_item,null);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.image);
        int width = imageView.getDrawable().getBounds().width();
        Log.w("ImageView","width: "+width);
        return convertView;
    }
}
