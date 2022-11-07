package com.sunanda.newroutine.application.ui.schoolomas;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.adapter.schoolomas.DataUploadSchoolOMAS_Adapter;
import com.sunanda.newroutine.application.database.DatabaseHandler;
import com.sunanda.newroutine.application.modal.SampleModel_SchoolOMAS;
import com.sunanda.newroutine.application.util.ApiConfig;
import com.sunanda.newroutine.application.util.AppConfig;
import com.sunanda.newroutine.application.util.CGlobal;
import com.sunanda.newroutine.application.util.CommonURL;
import com.sunanda.newroutine.application.util.Constants;
import com.sunanda.newroutine.application.util.ServerResponse;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class DataUploadSchoolOMAS_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_upload_activity);

        TextView tvHeaderName = findViewById(R.id.tvHeaderName);
        tvHeaderName.setText("School OMAS Upload Data");

        init();
    }

    RecyclerView rvUploadData;
    ArrayList<SampleModel_SchoolOMAS> sampleModelsDataCollection;
    DataUploadSchoolOMAS_Adapter adapter;
    ProgressDialog progressdialog;

    private void init() {

        rvUploadData = findViewById(R.id.rvUploadData);

        DatabaseHandler databaseHandler = new DatabaseHandler(DataUploadSchoolOMAS_Activity.this);
        sampleModelsDataCollection = new ArrayList<>();

        String sFCID = CGlobal.getInstance().getPersistentPreference(DataUploadSchoolOMAS_Activity.this)
                .getString(Constants.PREFS_USER_FACILITATOR_ID, "");

        sampleModelsDataCollection = databaseHandler.getSchoolAppDataCollectionSchoolOMAS(sFCID, "SchoolOMAS");

        adapter = new DataUploadSchoolOMAS_Adapter(DataUploadSchoolOMAS_Activity.this, sampleModelsDataCollection, new BtnImage() {
            @Override
            public void onBtnImageValue(final int position) {

                final PrettyDialog prettyDialog = new PrettyDialog(DataUploadSchoolOMAS_Activity.this);
                prettyDialog.setTitle("Send")
                        .setTitleColor(R.color.colorPrimaryDark)
                        .setIcon(R.drawable.pdlg_icon_success)
                        .setMessage("Do you want to Send it?")
                        .setIconTint(R.color.pdlg_color_green)
                        .addButton(
                                "Send",
                                R.color.pdlg_color_white,
                                R.color.pdlg_color_green,
                                new PrettyDialogCallback() {
                                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                    @Override
                                    public void onClick() {
                                        try {
                                            if (!TextUtils.isEmpty(sampleModelsDataCollection.get(position).getSourceImageFile_q_17())) {
                                                String spl[] = new String[0];
                                                try {
                                                    spl = sampleModelsDataCollection.get(position).getSourceImageFile_q_17().split("Sunanda_EI/");
                                                } catch (Exception e) {
                                                    Log.e("Sample Error", "");
                                                }
                                                try {
                                                    uploadFileSource(spl[1], sampleModelsDataCollection.get(position).getSourceImageFile_q_17(), sampleModelsDataCollection.get(position));
                                                } catch (Exception e) {
                                                    Log.e("Sample Error", "");
                                                }
                                            }
                                        } catch (Exception e) {
                                            Log.e("Sample Error", "");
                                        }
                                        prettyDialog.dismiss();
                                    }
                                }
                        )
                        .addButton(
                                "Cancel",
                                R.color.pdlg_color_white,
                                R.color.pdlg_color_red,
                                new PrettyDialogCallback() {
                                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                    @Override
                                    public void onClick() {
                                        prettyDialog.dismiss();
                                    }
                                }
                        )
                        .show();
                prettyDialog.setCancelable(false);


            }
        }, new BtnImageDelete() {
            @Override
            public void onBtnImageDeleteValue(final int position) {

                new AlertDialog.Builder(DataUploadSchoolOMAS_Activity.this)
                        .setTitle("Delete")
                        .setMessage("Do you want to Delete?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                DatabaseHandler databaseHandler = new DatabaseHandler(DataUploadSchoolOMAS_Activity.this);
                                //databaseHandler.deleteSampleCollection(sampleModelsDataCollection.get(position).getID());
                                databaseHandler.UpdateRecycleBinSchool(sampleModelsDataCollection.get(position).getID(), "1");
                                if (!TextUtils.isEmpty(sampleModelsDataCollection.get(position).getExisting_mid())) {
                                    databaseHandler.updateSourceForFacilitator("0", sampleModelsDataCollection.get(position).getExisting_mid());
                                }
                                showRow();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(DataUploadSchoolOMAS_Activity.this);
        rvUploadData.setLayoutManager(mLayoutManager);
        rvUploadData.setItemAnimator(new DefaultItemAnimator());
        rvUploadData.setAdapter(adapter);
    }

    public interface BtnImage {
        public abstract void onBtnImageValue(int position);
    }

    public interface BtnImageDelete {
        public abstract void onBtnImageDeleteValue(int position);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressdialog = new ProgressDialog(DataUploadSchoolOMAS_Activity.this);
        progressdialog.setMessage("Please Wait....");
    }

    private void showRow() {
        DatabaseHandler databaseHandler = new DatabaseHandler(DataUploadSchoolOMAS_Activity.this);
        sampleModelsDataCollection = new ArrayList<>();

        String sFCID = CGlobal.getInstance().getPersistentPreference(DataUploadSchoolOMAS_Activity.this)
                .getString(Constants.PREFS_USER_FACILITATOR_ID, "");

        sampleModelsDataCollection = databaseHandler.getSchoolAppDataCollectionSchoolOMAS(sFCID, "SchoolOMAS");

        adapter = new DataUploadSchoolOMAS_Adapter(DataUploadSchoolOMAS_Activity.this, sampleModelsDataCollection, new BtnImage() {
            @Override
            public void onBtnImageValue(final int position1) {

                final PrettyDialog prettyDialog = new PrettyDialog(DataUploadSchoolOMAS_Activity.this);
                prettyDialog.setTitle("Send")
                        .setTitleColor(R.color.colorPrimaryDark)
                        .setIcon(R.drawable.pdlg_icon_success)
                        .setMessage("Do you want to Send it?")
                        .setIconTint(R.color.pdlg_color_green)
                        .addButton(
                                "Send",
                                R.color.pdlg_color_white,
                                R.color.pdlg_color_green,
                                new PrettyDialogCallback() {
                                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                    @Override
                                    public void onClick() {
                                        try {
                                            if (!TextUtils.isEmpty(sampleModelsDataCollection.get(position1).getSourceImageFile_q_17())) {
                                                String spl[] = new String[0];
                                                try {
                                                    spl = sampleModelsDataCollection.get(position1).getSourceImageFile_q_17().split("Sunanda_EI/");
                                                } catch (Exception e) {
                                                    Log.e("Sample Error", "");
                                                }
                                                try {
                                                    uploadFileSource(spl[1], sampleModelsDataCollection.get(position1).getSourceImageFile_q_17(), sampleModelsDataCollection.get(position1));
                                                } catch (Exception e) {
                                                    Log.e("Sample Error", "");
                                                }
                                            }
                                        } catch (Exception e) {
                                            Log.e("Sample Error", "");
                                        }
                                        prettyDialog.dismiss();
                                    }
                                }
                        )
                        .addButton(
                                "Cancel",
                                R.color.pdlg_color_white,
                                R.color.pdlg_color_red,
                                new PrettyDialogCallback() {
                                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                    @Override
                                    public void onClick() {
                                        prettyDialog.dismiss();
                                    }
                                }
                        )
                        .show();
                prettyDialog.setCancelable(false);

            }
        }, new BtnImageDelete() {
            @Override
            public void onBtnImageDeleteValue(final int position) {
                new AlertDialog.Builder(DataUploadSchoolOMAS_Activity.this)
                        .setTitle("Delete")
                        .setMessage("Do you want to Delete?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                DatabaseHandler databaseHandler = new DatabaseHandler(DataUploadSchoolOMAS_Activity.this);
                                //databaseHandler.deleteSampleCollection(sampleModelsDataCollection.get(position).getID());
                                databaseHandler.UpdateRecycleBinSchool(sampleModelsDataCollection.get(position).getID(), "1");
                                if (!TextUtils.isEmpty(sampleModelsDataCollection.get(position).getExisting_mid())) {
                                    databaseHandler.updateSourceForFacilitator("0", sampleModelsDataCollection.get(position).getExisting_mid());
                                }
                                showRow();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(DataUploadSchoolOMAS_Activity.this);
        rvUploadData.setLayoutManager(mLayoutManager);
        rvUploadData.setItemAnimator(new DefaultItemAnimator());
        rvUploadData.setAdapter(adapter);
    }

    String json = "";

    private void sendData(final SampleModel_SchoolOMAS sampleModel) {
        try {
            progressdialog.setMessage("Send Data Please Wait....");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            json = new Gson().toJson(sampleModel);
        } catch (Exception e) {
            Log.e("Sample Error", "");
        }
        try {
            String sUrl = CommonURL.postRutine_1_SchoolOMASApp_URL;

            StringRequest postRequest = new StringRequest(Request.Method.POST,
                    sUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("Sample Response", response);
                    try {
                        if (response.equalsIgnoreCase("Successfuly inserted data")) {
                            DatabaseHandler databaseHandler = new DatabaseHandler(DataUploadSchoolOMAS_Activity.this);
                            //databaseHandler.deleteSampleCollection(sampleModel.getID());
                            databaseHandler.UpdateRecycleBinSchool(sampleModel.getID(), "1");

                            adapter.notifyDataSetChanged();

                            showRow();

                            final PrettyDialog prettyDialog = new PrettyDialog(DataUploadSchoolOMAS_Activity.this);
                            prettyDialog.setTitle(getString(R.string.app_name))
                                    .setTitleColor(R.color.colorPrimaryDark)
                                    .setIcon(R.drawable.pdlg_icon_success)
                                    .setMessage("Successfully inserted data")
                                    .setIconTint(R.color.pdlg_color_green)
                                    .addButton(
                                            "OK",
                                            R.color.pdlg_color_white,
                                            R.color.pdlg_color_green,
                                            new PrettyDialogCallback() {
                                                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                                @Override
                                                public void onClick() {
                                                    prettyDialog.dismiss();
                                                }
                                            }
                                    )
                                    .show();
                            prettyDialog.setCancelable(false);

                        } else if (response.equalsIgnoreCase("Data already exists")) {
                            DatabaseHandler databaseHandler = new DatabaseHandler(DataUploadSchoolOMAS_Activity.this);
                            //databaseHandler.deleteSampleCollection(sampleModel.getID());
                            databaseHandler.UpdateRecycleBinSchool(sampleModel.getID(), "1");

                            adapter.notifyDataSetChanged();

                            showRow();

                            final PrettyDialog prettyDialog = new PrettyDialog(DataUploadSchoolOMAS_Activity.this);
                            prettyDialog.setTitle(getString(R.string.app_name))
                                    .setTitleColor(R.color.colorPrimaryDark)
                                    .setIcon(R.drawable.pdlg_icon_success)
                                    .setMessage("Successfully inserted data")
                                    .setIconTint(R.color.pdlg_color_green)
                                    .addButton(
                                            "OK",
                                            R.color.pdlg_color_white,
                                            R.color.pdlg_color_green,
                                            new PrettyDialogCallback() {
                                                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                                @Override
                                                public void onClick() {
                                                    prettyDialog.dismiss();
                                                }
                                            }
                                    )
                                    .show();
                            prettyDialog.setCancelable(false);
                        } else {
                            final PrettyDialog prettyDialog = new PrettyDialog(DataUploadSchoolOMAS_Activity.this);
                            prettyDialog.setTitle(getString(R.string.app_name))
                                    .setTitleColor(R.color.colorPrimaryDark)
                                    .setIcon(R.drawable.pdlg_icon_close)
                                    .setMessage("Please try again")
                                    .setIconTint(R.color.pdlg_color_red)
                                    .addButton(
                                            "OK",
                                            R.color.pdlg_color_white,
                                            R.color.pdlg_color_red,
                                            new PrettyDialogCallback() {
                                                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                                @Override
                                                public void onClick() {
                                                    prettyDialog.dismiss();
                                                }
                                            }
                                    )
                                    .show();
                            prettyDialog.setCancelable(false);
                        }

                        if (progressdialog != null && progressdialog.isShowing()) {
                            progressdialog.cancel();
                        }
                    } catch (Exception e) {
                        Log.e("Sample Error", "");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Sample Error", "");
                    Toast.makeText(getApplicationContext(), "Please Check your Internet Connection.Please try again.", Toast.LENGTH_LONG).show();
                    if (progressdialog != null && progressdialog.isShowing()) {
                        progressdialog.cancel();
                    }
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("val", json);
                    return CGlobal.getInstance().checkParams(params);
                }
            };
            CGlobal.getInstance().addVolleyRequest(postRequest, false, DataUploadSchoolOMAS_Activity.this);
        } catch (Exception e) {
            Log.e("Sample Error", "");
        }
    }

    private void uploadFileSource(String str, String str2, final SampleModel_SchoolOMAS sampleModel) {

        try {
            progressdialog.setMessage("Send Source Image Please Wait....");
            progressdialog.show();
        } catch (Exception e) {
            Log.e("Sample Error", "");
        }

        // Map is used to multipart the file using okhttp3.RequestBody
        File file = new File(str2);
        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), str);
        RequestBody key = RequestBody.create(MediaType.parse("text/plain"), "abc123456");
        RequestBody type = RequestBody.create(MediaType.parse("text/plain"), "SchoolOmas");
        ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);
        Call<ServerResponse> call = getResponse.uploadFile(fileToUpload, filename, key, type);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {
                ServerResponse serverResponse = response.body();
                if (serverResponse != null) {
                    if (serverResponse.getSuccess()) {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        try {
                            if (!TextUtils.isEmpty(sampleModel.getImg_sanitary())) {
                                String spl[] = new String[0];
                                try {
                                    spl = sampleModel.getImg_sanitary().split("Sunanda_EI/");
                                } catch (Exception e) {
                                    Log.e("Sample Error", "");
                                }
                                try {
                                    uploadFileSurvey(spl[1], sampleModel.getImg_sanitary(), sampleModel);
                                } catch (Exception e) {
                                    Log.e("Sample Error", "");
                                }
                            } else {
                                try {
                                    if (!TextUtils.isEmpty(sampleModel.getImageofToilet_q_w_2d())) {
                                        String spl[] = new String[0];
                                        try {
                                            spl = sampleModel.getImageofToilet_q_w_2d().split("Sunanda_EI/");
                                        } catch (Exception e) {
                                            Log.e("Sample Error", "");
                                        }
                                        try {
                                            uploadFileToilet(spl[1], sampleModel.getImageofToilet_q_w_2d(), sampleModel);
                                        } catch (Exception e) {
                                            Log.e("Sample Error", "");
                                        }
                                    } else {
                                        try {
                                            if (!TextUtils.isEmpty(sampleModel.getImageofWashBasin_q_w_3c())) {
                                                String spl[] = new String[0];
                                                try {
                                                    spl = sampleModel.getImageofWashBasin_q_w_3c().split("Sunanda_EI/");
                                                } catch (Exception e) {
                                                    Log.e("Sample Error", "");
                                                }
                                                try {
                                                    uploadFileWashBasin(spl[1], sampleModel.getImageofWashBasin_q_w_3c(), sampleModel);
                                                } catch (Exception e) {
                                                    Log.e("Sample Error", "");
                                                }
                                            } else {
                                                try {
                                                    sendData(sampleModel);
                                                } catch (Exception e) {
                                                    Log.e("Sample Error", "");
                                                }
                                            }
                                        } catch (Exception e) {
                                            if (progressdialog != null && progressdialog.isShowing()) {
                                                progressdialog.cancel();
                                            }
                                            Log.e("Sample Error", "");
                                        }
                                    }
                                } catch (Exception e) {
                                    if (progressdialog != null && progressdialog.isShowing()) {
                                        progressdialog.cancel();
                                    }
                                    Log.e("Sample Error", "");
                                }
                            }
                        } catch (Exception e) {
                            Log.e("Sample Error", "");
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    assert serverResponse != null;
                    Log.v("Response", serverResponse.toString());
                }
                if (progressdialog != null && progressdialog.isShowing()) {
                    progressdialog.cancel();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.v("Response", "Error");
                Toast.makeText(getApplicationContext(), "Please Check your Source Image in Gallery Folder/Internet Connection.Please try again.", Toast.LENGTH_LONG).show();
                if (progressdialog != null && progressdialog.isShowing()) {
                    progressdialog.cancel();
                }
            }
        });
    }

    private void uploadFileSurvey(String str, String str2, final SampleModel_SchoolOMAS sampleModel) {

        try {
            progressdialog.setMessage("Send Survey Image Please Wait....");
        } catch (Exception e) {
            Log.e("Sample Error", "");
        }

        // Map is used to multipart the file using okhttp3.RequestBody
        File file = new File(str2);
        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), str);
        RequestBody key = RequestBody.create(MediaType.parse("text/plain"), "abc123456");
        RequestBody type = RequestBody.create(MediaType.parse("text/plain"), "SchoolOmas");
        ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);
        Call<ServerResponse> call = getResponse.uploadFile(fileToUpload, filename, key, type);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {
                ServerResponse serverResponse = response.body();
                if (serverResponse != null) {
                    if (serverResponse.getSuccess()) {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        try {
                            if (!TextUtils.isEmpty(sampleModel.getImageofToilet_q_w_2d())) {
                                String spl[] = new String[0];
                                try {
                                    spl = sampleModel.getImageofToilet_q_w_2d().split("Sunanda_EI/");
                                } catch (Exception e) {
                                    Log.e("Sample Error", "");
                                }
                                try {
                                    uploadFileToilet(spl[1], sampleModel.getImageofToilet_q_w_2d(), sampleModel);
                                } catch (Exception e) {
                                    Log.e("Sample Error", "");
                                }
                            } else {
                                try {
                                    if (!TextUtils.isEmpty(sampleModel.getImageofWashBasin_q_w_3c())) {
                                        String spl[] = new String[0];
                                        try {
                                            spl = sampleModel.getImageofWashBasin_q_w_3c().split("Sunanda_EI/");
                                        } catch (Exception e) {
                                            Log.e("Sample Error", "");
                                        }
                                        try {
                                            uploadFileWashBasin(spl[1], sampleModel.getImageofWashBasin_q_w_3c(), sampleModel);
                                        } catch (Exception e) {
                                            Log.e("Sample Error", "");
                                        }
                                    } else {
                                        try {
                                            sendData(sampleModel);
                                        } catch (Exception e) {
                                            Log.e("Sample Error", "");
                                        }
                                    }
                                } catch (Exception e) {
                                    if (progressdialog != null && progressdialog.isShowing()) {
                                        progressdialog.cancel();
                                    }
                                    Log.e("Sample Error", "");
                                }
                            }
                        } catch (Exception e) {
                            if (progressdialog != null && progressdialog.isShowing()) {
                                progressdialog.cancel();
                            }
                            Log.e("Sample Error", "");
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    assert serverResponse != null;
                    Log.v("Response", serverResponse.toString());
                }
                if (progressdialog != null && progressdialog.isShowing()) {
                    progressdialog.cancel();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.v("Response", "Error");
                Toast.makeText(getApplicationContext(), "Please Check your Sanitary Image in Gallery Folder/Internet Connection.Please try again.", Toast.LENGTH_LONG).show();
                if (progressdialog != null && progressdialog.isShowing()) {
                    progressdialog.cancel();
                }
            }
        });
    }

    private void uploadFileToilet(String str, String str2, final SampleModel_SchoolOMAS sampleModel) {

        try {
            progressdialog.setMessage("Please Wait....");
            progressdialog.show();
        } catch (Exception e) {
            Log.e("Sample Error", "");
        }

        // Map is used to multipart the file using okhttp3.RequestBody
        File file = new File(str2);
        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), str);
        RequestBody key = RequestBody.create(MediaType.parse("text/plain"), "abc123456");
        RequestBody type = RequestBody.create(MediaType.parse("text/plain"), "SchoolOmas");
        ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);
        Call<ServerResponse> call = getResponse.uploadFile(fileToUpload, filename, key, type);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {
                ServerResponse serverResponse = response.body();
                if (serverResponse != null) {
                    if (serverResponse.getSuccess()) {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        try {
                            if (!TextUtils.isEmpty(sampleModel.getImageofWashBasin_q_w_3c())) {
                                String spl[] = new String[0];
                                try {
                                    spl = sampleModel.getImageofWashBasin_q_w_3c().split("Sunanda_EI/");
                                } catch (Exception e) {
                                    Log.e("Sample Error", "");
                                }
                                try {
                                    uploadFileWashBasin(spl[1], sampleModel.getImageofWashBasin_q_w_3c(), sampleModel);
                                } catch (Exception e) {
                                    Log.e("Sample Error", "");
                                }
                            } else {
                                try {
                                    sendData(sampleModel);
                                } catch (Exception e) {
                                    Log.e("Sample Error", "");
                                }
                            }
                        } catch (Exception e) {
                            if (progressdialog != null && progressdialog.isShowing()) {
                                progressdialog.cancel();
                            }
                            Log.e("Sample Error", "");
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    assert serverResponse != null;
                    Log.v("Response", serverResponse.toString());
                }
                if (progressdialog != null && progressdialog.isShowing()) {
                    progressdialog.cancel();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.v("Response", "Error");
                Toast.makeText(getApplicationContext(), "Please Check your Toilet Image in Gallery Folder/Internet Connection.Please try again.", Toast.LENGTH_LONG).show();
                if (progressdialog != null && progressdialog.isShowing()) {
                    progressdialog.cancel();
                }
                try {
                    if (!TextUtils.isEmpty(sampleModel.getImageofToilet_q_w_2d())) {
                        String spl[] = new String[0];
                        try {
                            spl = sampleModel.getImageofToilet_q_w_2d().split("Sunanda_EI/");
                        } catch (Exception e) {
                            Log.e("Sample Error", "");
                        }
                        try {
                            uploadFileToilet(spl[1], sampleModel.getImageofToilet_q_w_2d(), sampleModel);
                        } catch (Exception e) {
                            Log.e("Sample Error", "");
                        }
                    }
                } catch (Exception e) {
                    Log.e("Sample Error", "");
                }
            }
        });
    }

    private void uploadFileWashBasin(String str, String str2, final SampleModel_SchoolOMAS sampleModel) {
        try {
            progressdialog.setMessage("Please Wait....");
            progressdialog.show();
        } catch (Exception e) {
            Log.e("Sample Error", "");
        }
        // Map is used to multipart the file using okhttp3.RequestBody
        File file = new File(str2);
        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), str);
        RequestBody key = RequestBody.create(MediaType.parse("text/plain"), "abc123456");
        RequestBody type = RequestBody.create(MediaType.parse("text/plain"), "SchoolOmas");
        ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);
        Call<ServerResponse> call = getResponse.uploadFile(fileToUpload, filename, key, type);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {
                ServerResponse serverResponse = response.body();
                if (serverResponse != null) {
                    if (serverResponse.getSuccess()) {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        try {
                            sendData(sampleModel);
                        } catch (Exception e) {
                            Log.e("Sample Error", "");
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    assert serverResponse != null;
                    Log.v("Response", serverResponse.toString());
                }
                if (progressdialog != null && progressdialog.isShowing()) {
                    progressdialog.cancel();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.v("Response", "Error");
                Toast.makeText(getApplicationContext(), "Please Check your WashBasin Image in Gallery Folder/Internet Connection.Please try again.", Toast.LENGTH_LONG).show();
                if (progressdialog != null && progressdialog.isShowing()) {
                    progressdialog.cancel();
                }

                try {
                    if (!TextUtils.isEmpty(sampleModel.getImageofWashBasin_q_w_3c())) {
                        String spl[] = new String[0];
                        try {
                            spl = sampleModel.getImageofWashBasin_q_w_3c().split("Sunanda_EI/");
                        } catch (Exception e) {
                            Log.e("Sample Error", "");
                        }
                        try {
                            uploadFileWashBasin(spl[1], sampleModel.getImageofWashBasin_q_w_3c(), sampleModel);
                        } catch (Exception e) {
                            Log.e("Sample Error", "");
                        }
                    }
                } catch (Exception e) {
                    Log.e("Sample Error", "");
                }
            }
        });
    }

}
