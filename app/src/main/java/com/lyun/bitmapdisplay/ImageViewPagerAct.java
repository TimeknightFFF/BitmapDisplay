package com.lyun.bitmapdisplay;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.lyun.supplement.Urls;

public class ImageViewPagerAct extends BaseActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view_pager);

        initData();
    }

    @Override
    void initData() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
    }

    private static class ImageAdapter extends PagerAdapter{

        private static final String[] IMAGE_URLS = Urls.imageUrls;

        @Override
        public int getCount() {
            return IMAGE_URLS.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return false;
        }
    }
}
