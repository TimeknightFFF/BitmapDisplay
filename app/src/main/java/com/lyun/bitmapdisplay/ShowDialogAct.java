package com.lyun.bitmapdisplay;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

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
        int childCount = scrollView.getChildCount();
        for (int i=0; i< childCount; i++){
            Log.d("--DEBUG--","childId: "+scrollView.getChildAt(i).getId());
        }
        Log.d("--DEBUG--"," dialog: "+R.id.dialog+" popup: "+R.id.popup+" linear: "+R.id.pull_scroll+" - "+R.id.view_parent+" scroll: "+R.id.pull_scroll);
        final DialogView dialogView = new DialogView();

        findViewById(R.id.dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogView.showDialog(ShowDialogAct.this);
            }
        });
        final Button pop = (Button) findViewById(R.id.popup);
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
                    sleep(1000);
                    handler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d("--DEBUG--","ShowDialog action_down: "+MotionEvent.ACTION_DOWN);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("--DEBUG--","ShowDialog action_down: "+MotionEvent.ACTION_MOVE);
                break;
            case MotionEvent.ACTION_UP:
                Log.d("--DEBUG--","ShowDialog action_down: "+MotionEvent.ACTION_UP);
                break;
        }
        return super.onTouchEvent(event);
    }
}
