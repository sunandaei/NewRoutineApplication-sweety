package com.sunanda.newroutine.application.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.somenath.pojo.LabWorkStatus;
import com.sunanda.newroutine.application.somenath.view.DashBoard_Activity;
import com.sunanda.newroutine.application.util.CGlobal;
import com.sunanda.newroutine.application.util.Constants;
import com.sunanda.newroutine.application.util.PermissionUtil;
import com.sunanda.newroutine.application.util.PostInterface;
import com.sunanda.newroutine.application.util.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SplashScreen_Activity extends AppCompatActivity {

    SessionManager sessionManager;

    private static final String[] INITIAL_PERMS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private static final int INITIAL_REQUEST = 1514;

    private void requestAllPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen_Activity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                || ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen_Activity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                || ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen_Activity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                || ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen_Activity.this,
                Manifest.permission.CAMERA)
                || ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen_Activity.this,
                Manifest.permission.READ_PHONE_STATE)
                || ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen_Activity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(SplashScreen_Activity.this);
            builder1.setMessage("This app cannot work without the following permissions:Storage and Location");
            builder1.setCancelable(true);
            builder1.setPositiveButton("Grant permission",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    });
            AlertDialog alert11 = builder1.create();
            if (!SplashScreen_Activity.this.isFinishing()) {
                alert11.show();
            }
        } else {
            ActivityCompat.requestPermissions(SplashScreen_Activity.this, INITIAL_PERMS, INITIAL_REQUEST);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);

        sessionManager = new SessionManager(this);

        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(SplashScreen_Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(SplashScreen_Activity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(SplashScreen_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(SplashScreen_Activity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(SplashScreen_Activity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(SplashScreen_Activity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(SplashScreen_Activity.this);
                builder1.setMessage("This app cannot work without the following permissions:Storage and Location");
                builder1.setCancelable(true);
                builder1.setPositiveButton("Grant permission",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                requestAllPermission();
                                dialog.dismiss();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                if (!SplashScreen_Activity.this.isFinishing()) {
                    alert11.show();
                }
            } else {
                init();
            }
        } else {
            init();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        // If request is cancelled, the result arrays are empty.
        if (requestCode == INITIAL_REQUEST) {
            if (PermissionUtil.verifyPermissions(grantResults)) {
                init();
            } else {
                Toast.makeText(SplashScreen_Activity.this, "Please allow permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void init() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean isLoginFlag = CGlobal.getInstance().getPersistentPreference(SplashScreen_Activity.this)
                        .getBoolean(Constants.PREFS_LOGIN_FLAG, false);
                boolean isSyncOnlineDataFlag = CGlobal.getInstance().getPersistentPreference(SplashScreen_Activity.this)
                        .getBoolean(Constants.PREFS_SYNC_ONLINE_DATA_FLAG, false);
                String userType = CGlobal.getInstance().getPersistentPreference(SplashScreen_Activity.this)
                        .getString(Constants.PREFS_ROUTINE_USER_TYPE, "");

                if (userType.equalsIgnoreCase("facilitator")) {
                    if (isLoginFlag) {
                        if (isSyncOnlineDataFlag) {
                            Intent mainIntent = new Intent(SplashScreen_Activity.this, DashBoard_Facilitator_Activity.class);
                            startActivity(mainIntent);
                            overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                            finish();
                        } else {
                            Intent mainIntent = new Intent(SplashScreen_Activity.this, SyncOnlineData_Facilitator_Activity.class);
                            startActivity(mainIntent);
                            overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                            finish();
                        }
                    } else {
                        Intent intent = new Intent(SplashScreen_Activity.this, Login_Activity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                        finish();
                    }
                } else if (userType.equalsIgnoreCase("lab")) {
                    if (isLoginFlag) {

                        String getDate = sessionManager.getDate();
                        Calendar c = Calendar.getInstance();
                        @SuppressLint("SimpleDateFormat")
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        String formattedDate = df.format(c.getTime());

                        Date todayDate = null;
                        try {
                            todayDate = df.parse(formattedDate);
                            Date fDate = df.parse(getDate);
                            if (todayDate.equals(fDate)) {
                                Intent mainIntent = new Intent(SplashScreen_Activity.this, DashBoard_Activity.class);
                                startActivity(mainIntent);
                                overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                                finish();
                            }else{
                                sessionManager.setDate(formattedDate);
                                //sessionManager.setIsFIrst(true);
                                FetchData();
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Intent intent = new Intent(SplashScreen_Activity.this, Login_Activity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                        finish();
                    }
                } else {
                    Intent intent = new Intent(SplashScreen_Activity.this, Login_Activity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                    finish();
                }
            }
        }, 4000);
    }

    private void FetchData() {

        final ProgressDialog progressdialog;
        progressdialog = new ProgressDialog(this);
        progressdialog.setMessage("Please Wait! We are fetching necessary information....");
        progressdialog.setCancelable(false);

        boolean isConnected = CGlobal.getInstance().isConnected(this);
        if (isConnected) {
            try {
                progressdialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {
                            okhttp3.Request.Builder ongoing = chain.request().newBuilder();
                            return chain.proceed(ongoing.build());
                        }
                    })
                    .build();

            String labCode = CGlobal.getInstance().getPersistentPreference(this)
                    .getString(Constants.PREFS_USER_LAB_CODE, "");
            String districtCode = CGlobal.getInstance().getPersistentPreference(this)
                    .getString(Constants.PREFS_USER_DISTRICT_CODE, "");
            Log.d("labCode!!", labCode);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(PostInterface.URL_RNJ)
                    .client(httpClient)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();

            PostInterface api = retrofit.create(PostInterface.class);

            Call<String> call = api.lab_work_status_details(labCode, districtCode);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                    //Log.i("Responsestring", response.body());

                    ArrayList<LabWorkStatus> labWorkStatusArrayList = new ArrayList<>();

                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            //Log.i("onSuccess", response.body());
                            String jsonresponse = response.body();
                            try {
                                JSONArray jsonArray = new JSONArray(jsonresponse);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    LabWorkStatus labWorkStatus = new LabWorkStatus();
                                    labWorkStatus.set_id(jsonObject.getJSONObject("_id").getString("$oid"));
                                    labWorkStatus.setStateCode(jsonObject.getString("state_code"));
                                    labWorkStatus.setDistCode(jsonObject.getString("dist_code"));
                                    labWorkStatus.setDistName(jsonObject.getString("dist_name"));
                                    labWorkStatus.setBlockCode(jsonObject.getString("block_code"));
                                    labWorkStatus.setBlockName(jsonObject.getString("block_name"));
                                    labWorkStatus.setPanCodeUnique(jsonObject.getString("pan_code_unique"));
                                    labWorkStatus.setPanCode(jsonObject.getString("pan_code"));
                                    labWorkStatus.setPanchayatName(jsonObject.getString("panchayat_name"));
                                    labWorkStatus.setLabCode(jsonObject.getString("LabCode"));
                                    labWorkStatus.setLabName(jsonObject.getString("lab_name"));
                                    labWorkStatus.setVillageHab(jsonObject.getJSONArray("villageHab").toString());

                                    labWorkStatusArrayList.add(labWorkStatus);
                                }

                                if (labWorkStatusArrayList.size() != 0) {
                                    sessionManager.saveArrayList(labWorkStatusArrayList, "LAB_STAT");

                                    Intent mainIntent = new Intent(SplashScreen_Activity.this, DashBoard_Activity.class);
                                    startActivity(mainIntent);
                                    overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                                    finish();
                                } else {
                                    new AlertDialog.Builder(SplashScreen_Activity.this)
                                            .setMessage("Please try again")
                                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                    FetchData();
                                                }
                                            })
                                            //.setIcon(android.R.drawable.ic_dialog_alert)
                                            .setCancelable(false)
                                            .show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            //Log.i("onEmptyResponse", "Returned empty response");
                            Toast.makeText(SplashScreen_Activity.this, "Unable to get data", Toast.LENGTH_SHORT).show();
                        }
                        progressdialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    //Log.i("onEmptyResponse", "Returned empty response");
                    progressdialog.dismiss();
                    Toast.makeText(SplashScreen_Activity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            new AlertDialog.Builder(this)
                    .setMessage("Please check your Internet connection. Please try again")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    })
                    //.setIcon(android.R.drawable.ic_dialog_alert)
                    .setCancelable(false)
                    .show();
        }
    }
}
