package com.lyun.bitmapdisplay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ActListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView list_activity;
    private List<String> activity_names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initData();
    }

    void initData() {
        list_activity = (ListView) findViewById(R.id.list_activity);
        activity_names = new ArrayList<>();

        activity_names.add(0,"MainActivity");
        activity_names.add(1,"LoadBigBitmapActivity");
        activity_names.add(2,"ShowImageAct");
        activity_names.add(3,"ListImageAct");
        activity_names.add(4,"ShowDialogAct");


        list_activity.setAdapter(new ArrayAdapter<String>(ActListActivity.this, android.R.layout.simple_list_item_1, activity_names));
        list_activity.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent;
        switch (position){
            case 0:
                intent = new Intent(ActListActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case 1:
                intent = new Intent(ActListActivity.this, LoadBigBitmapActivity.class);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(ActListActivity.this,ShowImageAct.class);
                startActivity(intent);
                break;
            case 3:
                intent = new Intent(ActListActivity.this,ListImageAct.class);
                startActivity(intent);
                break;
            case 4:
                intent = new Intent(ActListActivity.this, ShowDialogAct.class);
                startActivity(intent);
                break;
        }
    }
}
