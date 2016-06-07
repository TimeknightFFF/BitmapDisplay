package com.lyun.bitmapdisplay;

import android.os.Bundle;
import android.widget.ListView;

import com.lyun.adapter.ImageOneAdapter;

/**
 * 根据控件大小设置图片
 */
public class LoadBigBitmapActivity extends BaseActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_big_bitmap);

        initData();
    }

    @Override
    void initData() {
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new ImageOneAdapter(LoadBigBitmapActivity.this));
    }


}
