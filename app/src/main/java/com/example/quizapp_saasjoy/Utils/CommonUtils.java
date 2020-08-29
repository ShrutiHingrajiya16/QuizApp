package com.example.quizapp_saasjoy.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapp_saasjoy.R;
import com.google.android.material.snackbar.Snackbar;

public class CommonUtils {

    public static void showToast(Context context, String Message) {
        Toast.makeText(context, Message, Toast.LENGTH_SHORT).show();
    }

    public static void showSnackBar(Activity ctx,/* View v,*/ String text) {

        Snackbar s = Snackbar.make(ctx.findViewById(android.R.id.content), text, Snackbar.LENGTH_SHORT);
        View color = s.getView();
        color.setBackgroundColor(ctx.getResources().getColor(R.color.colorAccent));
        TextView tv = (TextView) color.findViewById(com.google.android.material.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        s.show();
    }

    public static boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            return conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}