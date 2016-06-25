package com.lyun.bitmapdisplay;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.lyun.bean.MeetingData;
import com.lyun.pullrefreshlistview.PullToRefreshBase;
import com.lyun.pullrefreshlistview.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

public class ActListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private PullToRefreshListView listView;
    private ListView list_activity;
    private List<String> activity_names;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            listView.onRefreshComplete();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initData();
    }

    void initData() {
        listView = (PullToRefreshListView) findViewById(R.id.listView);
        list_activity = listView.getRefreshableView();
        activity_names = new ArrayList<>();
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2() {
            @Override
            public void onPullDownToRefresh() {
                finishPull();
            }

            @Override
            public void onPullUpToRefresh() {
                finishPull();
            }
        });
        activity_names.add(0, "MainActivity");
        activity_names.add(1,"LoadBigBitmapActivity");
        activity_names.add(2,"ShowImageAct");
        activity_names.add(3,"ListImageAct");
        activity_names.add(4, "ShowDialogAct");


        list_activity.setAdapter(new ArrayAdapter<String>(ActListActivity.this, android.R.layout.simple_list_item_1, activity_names));
        list_activity.setOnItemClickListener(this);

        gsonFormatTest();
    }

    private void gsonFormatTest() {
        String jsonStr = "{\n" +
                "  \"code\": 0,\n" +
                "  \"msg\": \"轮播会议获取成功\",\n" +
                "  \"records\": [\n" +
                "    {\n" +
                "      \"joinNumber\": 3,\n" +
                "      \"id\": 10,\n" +
                "      \"startDateStr\": \"2016-10-26 09:00 星期三\",\n" +
                "      \"theme\": \"2016年度海外高层次人群聚会\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"joinNumber\": 3,\n" +
                "      \"id\": 1,\n" +
                "      \"startDateStr\": \"2016-10-24 08:00 星期一\",\n" +
                "      \"theme\": \"2016年度苏州医疗会议\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        Gson gson = new Gson();
        MeetingData meetingData = gson.fromJson(jsonStr, MeetingData.class);
        List<MeetingData.RecordsEntity> records = meetingData.getRecords();
        for (MeetingData.RecordsEntity entity: records){
            System.out.println("entity: " + entity.getJoinNumber() + " theme: " + entity.getTheme());
        }
    }

    private void finishPull() {
        new Thread(){
            @Override
            public void run() {
                try {
                    sleep(4000);
                    handler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent;
        switch (position){
            case 1:
                intent = new Intent(ActListActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(ActListActivity.this, LoadBigBitmapActivity.class);
                startActivity(intent);
                break;
            case 3:
                intent = new Intent(ActListActivity.this,ShowImageAct.class);
                startActivity(intent);
                break;
            case 4:
                intent = new Intent(ActListActivity.this,ListImageAct.class);
                startActivity(intent);
                break;
            case 5:
                intent = new Intent(ActListActivity.this, ShowDialogAct.class);
                startActivity(intent);
                break;
        }
    }
}
