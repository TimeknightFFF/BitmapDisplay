package com.lyun.bitmapdisplay;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.lyun.view.DialogView;

public class ShowDialogAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_dialog);

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
}
