package com.example.quizapp_saasjoy.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.example.quizapp_saasjoy.R;

public class ResultDialog {

    TextView txtTotal;
    TextView txtAttempted;
    TextView txtCorrect;
    TextView txtWrong;
    TextView btnClose;

    public void openDialog(Context context, int Total, int Correct, int Wrong) {

        Dialog dialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationTopBottom;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_result);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        txtTotal = dialog.findViewById(R.id.txtTotal);
        txtAttempted = dialog.findViewById(R.id.txtAttempted);
        txtCorrect = dialog.findViewById(R.id.txtCorrect);
        txtWrong = dialog.findViewById(R.id.txtWrong);
        btnClose = dialog.findViewById(R.id.btnClose);

        txtTotal.setText(Total + "");
        txtCorrect.setText(Correct + "");
        txtWrong.setText(Wrong + "");
        txtAttempted.setText((Correct + Wrong) + "");

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }
}
