package com.lyun.bitmapdisplay;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.lyun.utils.DrawBitmapWithIntent;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private File sdcard = null;
    private DrawBitmapWithIntent draw;

    private Button btn,album;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        draw = new DrawBitmapWithIntent(this);
        sdcard = Environment.getExternalStorageDirectory();
        initView();
    }

    private void initView() {
        btn = (Button) findViewById(R.id.btn);
        album = (Button) findViewById(R.id.album);
        image = (ImageView) findViewById(R.id.image);

        btn.setOnClickListener(this);
        album.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode){
            case DrawBitmapWithIntent.BITMAP_FROM_CAMERA:
                File f = new File(sdcard, DrawBitmapWithIntent.fileName);
                draw.startPhotoZoom(Uri.fromFile(new File(f.getAbsolutePath())));
                break;
            case DrawBitmapWithIntent.CROP_BITMAP:
                if (data != null) {
                    draw.setPicToView(data, image);
                }
                break;
            case DrawBitmapWithIntent.BITMAP_FROM_PHOTO_ALBUM:
                if (data != null) {
                    draw.startPhotoZoom(data.getData());
                }
                break;
            default:
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn:
                draw.getBitmapFromCamera();
                break;
            case R.id.album:
                draw.getBitmapFromPhotoAlbum();
                break;
        }
    }
}
