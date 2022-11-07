package com.sunanda.newroutine.application.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.Window;

import com.sunanda.newroutine.application.R;

public class LoadingDialog {
    Dialog dialog;
    Context context;

    public LoadingDialog(Context context) {
        this.context = context;
        dialog = new Dialog(this.context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progress_layout);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(false);
    }

    public void showDialog() {

        dialog.show();
    }

    public void hideDialog() {
        dialog.dismiss();
    }
}
