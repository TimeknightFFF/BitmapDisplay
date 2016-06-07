package com.lyun.view;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;

import com.lyun.bitmapdisplay.R;

/**
 * Created by sm on 2016/6/2.
 */
public class DialogView {

    public void showDialog(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_view,null);
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setView(view);
        dialog.show();
    }

    public void showPoppup(Context context, Button btn){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_view,null);
        PopupWindow popupWindow = new PopupWindow(context);
        popupWindow.setContentView(view);
        popupWindow.showAtLocation(btn, Gravity.CENTER,0,0);
    }
}
