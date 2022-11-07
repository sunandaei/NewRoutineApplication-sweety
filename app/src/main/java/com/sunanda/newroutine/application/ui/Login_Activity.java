package com.sunanda.newroutine.application.ui;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;
import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.somenath.pojo.LabWorkStatus;
import com.sunanda.newroutine.application.somenath.view.DashBoard_Activity;
import com.sunanda.newroutine.application.util.CGlobal;
import com.sunanda.newroutine.application.util.CommonURL;
import com.sunanda.newroutine.application.util.Constants;
import com.sunanda.newroutine.application.util.PostInterface;
import com.sunanda.newroutine.application.util.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Login_Activity extends AppCompatActivity {

    SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        init();
    }

    ProgressDialog progressdialog;

    @Override
    protected void onResume() {
        super.onResume();
        sessionManager = new SessionManager(this);
        progressdialog = new ProgressDialog(Login_Activity.this);
        progressdialog.setMessage("Please Wait....");
    }

    Toolbar toolbar;
    Button btnLogin;
    TextInputEditText tietEmail, tietPassword;
    private RadioGroup rLogin;
    private RadioButton rSelectId;
    String sSelectId = "";

    private void init() {
        tietEmail = findViewById(R.id.tietEmail);
        tietPassword = findViewById(R.id.tietPassword);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isConnected = CGlobal.getInstance().isConnected(Login_Activity.this);
                if (isConnected) {
                    saveLoginStatus();
                } else {
                    new AlertDialog.Builder(Login_Activity.this)
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
        });

        rLogin = findViewById(R.id.rLogin);

    }

    private void saveLoginStatus() {
        String sUserName = tietEmail.getText().toString();
        String spassword = tietPassword.getText().toString();

        if (TextUtils.isEmpty(sUserName)) {
            tietEmail.setError("Please Enter User Name");
            return;
        }

        if (TextUtils.isEmpty(spassword)) {
            tietPassword.setError("Please Enter Password");
            return;
        }

        int selectedId = rLogin.getCheckedRadioButtonId();
        rSelectId = findViewById(selectedId);
        if (selectedId == -1) {
            new AlertDialog.Builder(Login_Activity.this)
                    .setCancelable(false)
                    .setMessage("⚠ Please Select User Type")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        } else {
            sSelectId = rSelectId.getText().toString();
        }
        if (!TextUtils.isEmpty(sSelectId)) {
            if (sSelectId.equalsIgnoreCase("LABORATORY STAFF")) {
                CGlobal.getInstance().getPersistentPreferenceEditor(Login_Activity.this)
                        .putString(Constants.PREFS_ROUTINE_USER_TYPE, "lab").commit();
                saveLabPersonal(sUserName, spassword);
            } else if (sSelectId.equalsIgnoreCase("FACILITATOR")) {
                CGlobal.getInstance().getPersistentPreferenceEditor(Login_Activity.this)
                        .putString(Constants.PREFS_ROUTINE_USER_TYPE, "facilitator").commit();
                saveFacilitator(sUserName, spassword);
            }
        }
    }

    private void saveLabPersonal(final String sUserName, final String sPassword) {
        boolean isConnected = CGlobal.getInstance().isConnected(Login_Activity.this);
        if (isConnected) {
            try {
                progressdialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            String sUrl = CommonURL.LoginApp_URL + "?UserName=" + sUserName + "&Passw=" + sPassword + "&AppKey=Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3";

            StringRequest postRequest = new StringRequest(Request.Method.GET,
                    sUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    saveLabPersonalResponse(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressdialog.dismiss();
                    try {
                        new AlertDialog.Builder(Login_Activity.this)
                                .setMessage("Please Check Your Internet Connection. Please try again")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    //params.put("emailph", sEmail);
                    //params.put("password", sPassword);
                    return CGlobal.getInstance().checkParams(params);
                }
            };
            CGlobal.getInstance().addVolleyRequest(postRequest, false, Login_Activity.this);
        } else {
            new AlertDialog.Builder(Login_Activity.this)
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

    private void saveLabPersonalResponse(String response) {
        progressdialog.dismiss();
        try {
            JSONObject loginObject = new JSONObject(response);
            String sUSRUniqueID = CGlobal.getInstance().isNullNotDefined(loginObject, "USRUniqueID") ? "" : loginObject.getString("USRUniqueID");
            String sReturnMessage = CGlobal.getInstance().isNullNotDefined(loginObject, "ReturnMessage") ? "" : loginObject.getString("ReturnMessage");
            String sDistrictID = CGlobal.getInstance().isNullNotDefined(loginObject, "DistrictID") ? "" : loginObject.getString("DistrictID");
            String sCityID = CGlobal.getInstance().isNullNotDefined(loginObject, "CityID") ? "" : loginObject.getString("CityID");
            String sLabID = CGlobal.getInstance().isNullNotDefined(loginObject, "LabID") ? "" : loginObject.getString("LabID");
            String sLabCode = CGlobal.getInstance().isNullNotDefined(loginObject, "LabCode") ? "" : loginObject.getString("LabCode");
            String sLabName = CGlobal.getInstance().isNullNotDefined(loginObject, "LabName") ? "" : loginObject.getString("LabName");
            String sDistrictCode = CGlobal.getInstance().isNullNotDefined(loginObject, "DistrictCode") ? "" : loginObject.getString("DistrictCode");

            if (sUSRUniqueID.equalsIgnoreCase("0")) {
                new AlertDialog.Builder(Login_Activity.this)
                        .setCancelable(false)
                        .setMessage(sReturnMessage)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            } else {
                CGlobal.getInstance().getPersistentPreferenceEditor(Login_Activity.this)
                        .putString(Constants.PREFS_USER_USRUNIQUE_ID, sUSRUniqueID).commit();

                CGlobal.getInstance().getPersistentPreferenceEditor(Login_Activity.this)
                        .putString(Constants.PREFS_USER_DISTRICT_ID, sDistrictID).commit();

                CGlobal.getInstance().getPersistentPreferenceEditor(Login_Activity.this)
                        .putString(Constants.PREFS_USER_CITY_ID, sCityID).commit();

                CGlobal.getInstance().getPersistentPreferenceEditor(Login_Activity.this)
                        .putString(Constants.PREFS_USER_LAB_ID, sLabID).commit();

                CGlobal.getInstance().getPersistentPreferenceEditor(Login_Activity.this)
                        .putString(Constants.PREFS_USER_LAB_CODE, sLabCode).commit();

                CGlobal.getInstance().getPersistentPreferenceEditor(Login_Activity.this)
                        .putString(Constants.PREFS_USER_LAB_NAME, sLabName).commit();

                CGlobal.getInstance().getPersistentPreferenceEditor(Login_Activity.this)
                        .putString(Constants.PREFS_USER_DISTRICT_CODE, sDistrictCode).commit();

                new AlertDialog.Builder(Login_Activity.this)
                        .setCancelable(false)
                        .setMessage(sReturnMessage)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                FetchData();
                            }
                        })
                        .show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void FetchData() {

        Calendar c = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        final String formattedDate = df.format(c.getTime());

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
                    .connectTimeout(60 * 3, TimeUnit.SECONDS)
                    .readTimeout(60 * 3, TimeUnit.SECONDS)
                    .writeTimeout(60 * 3, TimeUnit.SECONDS)
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
            String districtCode = CGlobal.getInstance().getPersistentPreference(Login_Activity.this)
                    .getString(Constants.PREFS_USER_DISTRICT_CODE, "");

            Log.d("labCode!!", labCode + "-" + districtCode);

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
                                    sessionManager.setDate(formattedDate);
                                    sessionManager.saveArrayList(labWorkStatusArrayList, "LAB_STAT");

                                    CGlobal.getInstance().getPersistentPreferenceEditor(Login_Activity.this)
                                            .putBoolean(Constants.PREFS_LOGIN_FLAG, true).commit();
                                    Intent intent = new Intent(Login_Activity.this, DashBoard_Activity.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                                    finish();
                                } else {
                                    new AlertDialog.Builder(Login_Activity.this)
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
                            Toast.makeText(Login_Activity.this, "Unable to get data", Toast.LENGTH_SHORT).show();
                        }
                        progressdialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    //Log.i("onEmptyResponse", "Returned empty response");
                    progressdialog.dismiss();
                    Toast.makeText(Login_Activity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
                    //.setIcon(android.R.drawable.ic_dialog_alert)
                    .setCancelable(false)
                    .show();
        }
    }

    private void saveFacilitator(final String sUserName, final String sPassword) {
        boolean isConnected = CGlobal.getInstance().isConnected(Login_Activity.this);
        if (isConnected) {
            try {
                progressdialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            String sUrl = "http://test.sunandainternational.org/AppApi/FacilatorLogin"
                    + "?UserName=" + sUserName + "&Password=" + sPassword + "&AppKey=Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3";

            StringRequest postRequest = new StringRequest(Request.Method.GET,
                    sUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    saveFacilitatorResponse(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressdialog.dismiss();
                    try {
                        new AlertDialog.Builder(Login_Activity.this)
                                .setMessage("Please Check Your Internet Connection. Please try again")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    //params.put("emailph", sEmail);
                    //params.put("password", sPassword);
                    return CGlobal.getInstance().checkParams(params);
                }
            };
            CGlobal.getInstance().addVolleyRequest(postRequest, false, Login_Activity.this);
        } else {
            new AlertDialog.Builder(Login_Activity.this)
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

    private void saveFacilitatorResponse(String response) {
        progressdialog.dismiss();
        try {
            JSONObject loginObject = new JSONObject(response);
            String sFCID = CGlobal.getInstance().isNullNotDefined(loginObject, "FCID") ? "" : loginObject.getString("FCID");
            String sFCName = CGlobal.getInstance().isNullNotDefined(loginObject, "FCName") ? "" : loginObject.getString("FCName");
            String sUserName = CGlobal.getInstance().isNullNotDefined(loginObject, "UserName") ? "" : loginObject.getString("UserName");
            String sPassword = CGlobal.getInstance().isNullNotDefined(loginObject, "Password") ? "" : loginObject.getString("Password");
            String sUserType = CGlobal.getInstance().isNullNotDefined(loginObject, "UserType") ? "" : loginObject.getString("UserType");
            String sLabCode = CGlobal.getInstance().isNullNotDefined(loginObject, "LabCode") ? "" : loginObject.getString("LabCode");
            String sLabID = CGlobal.getInstance().isNullNotDefined(loginObject, "LabID") ? "" : loginObject.getString("LabID");
            String sDistrictId = CGlobal.getInstance().isNullNotDefined(loginObject, "DistrictId") ? "" : loginObject.getString("DistrictId");
            String LabName = CGlobal.getInstance().isNullNotDefined(loginObject, "LabName") ? "" : loginObject.getString("LabName");
            String IsActive = CGlobal.getInstance().isNullNotDefined(loginObject, "IsActive") ? "" : loginObject.getString("IsActive");
            String SampleCollectorId = CGlobal.getInstance().isNullNotDefined(loginObject, "SampleCollectorId") ? "" : loginObject.getString("SampleCollectorId");
            String Email = CGlobal.getInstance().isNullNotDefined(loginObject, "Email") ? "" : loginObject.getString("Email");
            String Mobile = CGlobal.getInstance().isNullNotDefined(loginObject, "Mobile") ? "" : loginObject.getString("Mobile");

            if (!sFCID.equalsIgnoreCase("0") && !TextUtils.isEmpty(sFCID)) {
                CGlobal.getInstance().getPersistentPreferenceEditor(Login_Activity.this)
                        .putString(Constants.PREFS_USER_USRUNIQUE_ID, SampleCollectorId).commit();

                CGlobal.getInstance().getPersistentPreferenceEditor(Login_Activity.this)
                        .putString(Constants.PREFS_USER_DISTRICT_ID, sDistrictId).commit();

                CGlobal.getInstance().getPersistentPreferenceEditor(Login_Activity.this)
                        .putString(Constants.PREFS_USER_LAB_ID, sLabID).commit();

                CGlobal.getInstance().getPersistentPreferenceEditor(Login_Activity.this)
                        .putString(Constants.PREFS_USER_FACILITATOR_NAME, sFCName).commit();

                CGlobal.getInstance().getPersistentPreferenceEditor(Login_Activity.this)
                        .putString(Constants.PREFS_USER_FACILITATOR_ID, sFCID).commit();

                CGlobal.getInstance().getPersistentPreferenceEditor(Login_Activity.this)
                        .putString(Constants.PREFS_USER_USER_NAME, sUserName).commit();

                CGlobal.getInstance().getPersistentPreferenceEditor(Login_Activity.this)
                        .putString(Constants.PREFS_USER_USER_TYPE, sUserType).commit();

                CGlobal.getInstance().getPersistentPreferenceEditor(Login_Activity.this)
                        .putString(Constants.PREFS_USER_LAB_CODE, sLabCode).commit();

                CGlobal.getInstance().getPersistentPreferenceEditor(Login_Activity.this)
                        .putString(Constants.PREFS_USER_LAB_NAME, LabName).commit();


                new AlertDialog.Builder(Login_Activity.this)
                        .setCancelable(false)
                        .setMessage("Successfully Login")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                CGlobal.getInstance().getPersistentPreferenceEditor(Login_Activity.this)
                                        .putBoolean(Constants.PREFS_LOGIN_FLAG, true).commit();
                                Intent intent = new Intent(Login_Activity.this, SyncOnlineData_Facilitator_Activity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                                finish();
                                dialog.dismiss();
                            }
                        })
                        .show();
            } else {
                new AlertDialog.Builder(Login_Activity.this)
                        .setCancelable(false)
                        .setMessage("Please enter correct User id and Password")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (progressdialog != null && progressdialog.isShowing()) {
            progressdialog.cancel();
        }
    }
}
