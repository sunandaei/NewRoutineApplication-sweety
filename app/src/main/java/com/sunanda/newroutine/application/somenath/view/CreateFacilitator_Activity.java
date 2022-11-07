package com.sunanda.newroutine.application.somenath.view;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.util.CGlobal;
import com.sunanda.newroutine.application.util.Constants;
import com.sunanda.newroutine.application.util.PostInterface;
import com.sunanda.newroutine.application.util.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class CreateFacilitator_Activity extends AppCompatActivity {

    ProgressDialog progressdialog;
    EditText name, email, mobile, pass;
    TextView uname, tvPanchayat, tvBLock;
    Button btnSubmit;
    SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_facilitator_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        final Drawable upArrow = getResources().getDrawable(R.drawable.leftarrow);
        upArrow.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.leftarrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        progressdialog = new ProgressDialog(this);
        progressdialog.setMessage("Please Wait....");
        sessionManager = new SessionManager(this);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        uname = findViewById(R.id.uname);
        mobile = findViewById(R.id.mobile);
        pass = findViewById(R.id.pass);
        btnSubmit = findViewById(R.id.btnSubmit);
        tvPanchayat = findViewById(R.id.tvPanchayat);
        tvBLock = findViewById(R.id.tvBlock);
        tvBLock.setText(sessionManager.getKeyBlockName());
        tvPanchayat.setText(sessionManager.getKeyPanName());

        name.addTextChangedListener(watch);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(name.getText().toString())) {
                    name.setError("Enter Name");
                    name.requestFocus();
                } else if (!TextUtils.isEmpty(email.getText().toString()) && !isValidEmail(email.getText().toString())) {
                    email.setError("Enter Valid Email");
                    email.requestFocus();
                } else if (!TextUtils.isEmpty(mobile.getText().toString()) && mobile.getText().toString().length() != 10) {
                    mobile.setError("Enter Valid Mobile");
                    mobile.requestFocus();
                } else if (TextUtils.isEmpty(pass.getText().toString())) {
                    pass.setError("Enter Password");
                    pass.requestFocus();
                } else {
                    saveRegister();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    TextWatcher watch = new TextWatcher() {

        String sLabCode = CGlobal.getInstance().getPersistentPreference(CreateFacilitator_Activity.this)
                .getString(Constants.PREFS_USER_LAB_CODE, "");

        @Override
        public void afterTextChanged(Editable arg0) {

        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onTextChanged(CharSequence s, int a, int b, int c) {

            if (s.length() != 0)
                uname.setText(s.toString().replace(" ", "") + sLabCode.substring(sLabCode.length() - 3));
            else
                uname.setText("");
        }
    };

    private void saveRegister() {
        boolean isConnected = CGlobal.getInstance().isConnected(this);
        if (isConnected) {
            try {
                progressdialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

            String labid = CGlobal.getInstance().getPersistentPreference(this)
                    .getString(Constants.PREFS_USER_LAB_ID, "");
            String PREFS_USER_USRUNIQUE_ID = CGlobal.getInstance().getPersistentPreference(this)
                    .getString(Constants.PREFS_USER_USRUNIQUE_ID, "");

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(PostInterface.JSONURL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();

            PostInterface api = retrofit.create(PostInterface.class);

            Call<String> call = api.getRegister(name.getText().toString().trim(), mobile.getText().toString().trim(),
                    uname.getText().toString(), email.getText().toString().trim(),
                    pass.getText().toString().trim(), "facilitator", labid, sessionManager.getKeyPanCode(),
                    sessionManager.getKeyPanName(), sessionManager.getKeyBlockCode(), sessionManager.getKeyBlockName(),
                    sessionManager.getKeyDistCode(), sessionManager.getKeyDistCode(), "true",
                    "Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3", PREFS_USER_USRUNIQUE_ID);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    //Log.i("Responsestring", response.body());
                    progressdialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().trim());
                        if (jsonObject.getBoolean("response")) {
                            if (response.body() != null) {
                                //Log.i("onSuccess", response.body());
                                saveRegisterResponse();
                            } else {
                                //Toast.makeText(getContext(), "Unable to create Facilitator", Toast.LENGTH_SHORT).show();
                                //Log.i("onEmptyResponse", "Returned empty jsonResponse");
                                new AlertDialog.Builder(CreateFacilitator_Activity.this)
                                        .setTitle("Unable to create Facilitator")
                                        .setMessage("Please use alternate name of the Facilitator")
                                        // Specifying a listener allows you to take an action before dismissing the dialog.
                                        // The dialog is automatically dismissed when a dialog button is clicked.
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        })
                                        //.setNegativeButton(android.R.string.no, null)
                                        .setCancelable(false)
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.i("onEmptyResponse", "Returned empty jsonResponse");
                    progressdialog.dismiss();
                }
            });
        } else {
            new AlertDialog.Builder(this)
                    .setMessage("Please check your Internet connection. Please try again")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    private void saveRegisterResponse() {
        progressdialog.dismiss();
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Facilitator Created Successfully");
        builder1.setCancelable(false);
        builder1.setPositiveButton("DONE",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_left_out);
                        finish();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_left_out);
        finish();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            overridePendingTransition(R.anim.trans_right_in, R.anim.trans_left_out);
            finish();
        }
        return (super.onOptionsItemSelected(menuItem));
    }
}
