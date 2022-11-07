package com.sunanda.newroutine.application.ui;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sunanda.newroutine.application.R;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    public ProgressDialog prsDlg;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
    }

    public void showProgressDailog() {
        prsDlg = new ProgressDialog(this);
        prsDlg.setMessage(Html.fromHtml("<font color=\"##ff8c00\">Please Wait.....</font>"));
        prsDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        prsDlg.setIndeterminate(true);
        prsDlg.setCancelable(false);
        prsDlg.show();
    }

    public void dismissProgressDialog() {
        if (prsDlg.isShowing()) {
            prsDlg.dismiss();
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        boolean f =  activeNetworkInfo != null && activeNetworkInfo.isConnected() &&
                    connectivityManager.getActiveNetworkInfo() != null;
        if(f){
            return true;
        }else{
            //Toast.makeText(getApplicationContext(), "You Device Doesn’t Have Internet Connectivity, Please Connect To Internet And Try Accessing The App",Toast.LENGTH_LONG).show();
            /*Toast toast = Toast.makeText(getApplicationContext(),
                    "You Device Doesn’t Have Internet Connectivity, Please Connect To Internet And Try Accessing The App", Toast.LENGTH_LONG);
            TextView toastMessage = toast.getView().findViewById(android.R.id.message);
            toastMessage.setTextColor(Color.RED);
            toast.show();
            finishAffinity();
            System.exit(0);*/
            startMyActivity();
            return false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void startMyActivity() {
        final Dialog dialog = new Dialog(this);

        dialog.setTitle("SUNANDA");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        TextView tvbtn_msg_refresh = (TextView) dialog.findViewById(R.id.tvbtn_msg_refresh);
        tvbtn_msg_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isNetworkAvailable()) {
                    startMyActivity();
                } else {
                    isNetworkAvailable();
                    dialog.dismiss();
                }
            }
        });

        dialog.setCancelable(false);
        dialog.show();
    }

    public Typeface getFontNormal(){
        return Typeface.createFromAsset(getAssets(), "Raleway.ttf");
    }

    public Typeface getFontHeavy(){
        return Typeface.createFromAsset(getAssets(), "Raleway-Heavy.ttf");
    }
}
