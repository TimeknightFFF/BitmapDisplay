package com.lyun.bitmapdisplay;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lyun.supplement.Urls;
import com.lyun.utils.ImageLoaderHelper;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ListImageAct extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView image_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_image);

        initData();
    }

    private void initData() {
        image_list = (ListView) findViewById(R.id.image_list);
        image_list.setAdapter(new ImageAdapter());
        image_list.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    class ImageAdapter extends BaseAdapter{

        private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

        @Override
        public int getCount() {
            return Urls.imageUrls.length;
        }

        @Override
        public Object getItem(int position) {
            return Urls.imageUrls[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null){
                convertView = LayoutInflater.from(ListImageAct.this).inflate(R.layout.item_list_image,null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.text.setText("Item "+position+1);
            ImageLoader.getInstance().displayImage(
                    Urls.imageUrls[position],holder.image, ImageLoaderHelper.getInstance().getDisplayImageOptions()
                    ,animateFirstListener
            );

            return convertView;
        }
    }

    class ViewHolder{
        private ImageView image;
        private TextView text;

        public ViewHolder(View itemView) {
            image = (ImageView) itemView.findViewById(R.id.image);
            text = (TextView) itemView.findViewById(R.id.text);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AnimateFirstDisplayListener.displayedImages.clear();
    }

    private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }
}
