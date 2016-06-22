package com.lyun.bitmapdisplay;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;

import com.lyun.pullrefreshlistview.PullToRefreshBase;
import com.lyun.pullrefreshlistview.PullToRefreshScrollView;
import com.lyun.view.DialogView;

public class ShowDialogAct extends AppCompatActivity {

    private PullToRefreshScrollView scrollView;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            scrollView.onRefreshComplete();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_dialog);

        scrollView = (PullToRefreshScrollView) findViewById(R.id.pull_scroll);
        scrollView.setMode(PullToRefreshBase.Mode.BOTH);
        scrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2() {
            @Override
            public void onPullDownToRefresh() {
                finishPull();
            }

            @Override
            public void onPullUpToRefresh() {
                finishPull();
            }
        });
        ScrollView scrollView1 = scrollView.getRefreshableView();
        View view = LayoutInflater.from(ShowDialogAct.this).inflate(R.layout.show_dialog_layout, null);
        scrollView1.addView(view);
        final DialogView dialogView = new DialogView();

        view.findViewById(R.id.dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogView.showDialog(ShowDialogAct.this);
            }
        });
        final Button pop = (Button) view.findViewById(R.id.popup);
        pop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogView.showPoppup(ShowDialogAct.this, pop);
            }
        });
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
}
