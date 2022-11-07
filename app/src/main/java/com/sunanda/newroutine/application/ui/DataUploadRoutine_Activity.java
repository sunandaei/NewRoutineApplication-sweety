package com.sunanda.newroutine.application.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.adapter.DataUpload_Adapter;
import com.sunanda.newroutine.application.database.DatabaseHandler;
import com.sunanda.newroutine.application.modal.SampleModel_Routine;
import com.sunanda.newroutine.application.util.ApiConfig;
import com.sunanda.newroutine.application.util.AppConfig;
import com.sunanda.newroutine.application.util.CGlobal;
import com.sunanda.newroutine.application.util.CommonURL;
import com.sunanda.newroutine.application.util.Constants;
import com.sunanda.newroutine.application.util.ServerResponse;
import com.sunanda.newroutine.application.util.VolleyMultipartRequest;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
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

public class DataUploadRoutine_Activity extends AppCompatActivity {

    private String sUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_upload_activity);

        TextView tvHeaderName = findViewById(R.id.tvHeaderName);
        tvHeaderName.setText("Routine Upload Data");

        init();
    }

    RecyclerView rvUploadData;
    ArrayList<SampleModel_Routine> sampleModelsDataCollection;
    DataUpload_Adapter adapter;
    ProgressDialog progressdialog;

    private void init() {

        rvUploadData = findViewById(R.id.rvUploadData);

        DatabaseHandler databaseHandler = new DatabaseHandler(DataUploadRoutine_Activity.this);
        sampleModelsDataCollection = new ArrayList<>();
        String sFCID = CGlobal.getInstance().getPersistentPreference(DataUploadRoutine_Activity.this)
                .getString(Constants.PREFS_USER_FACILITATOR_ID, "");

        sampleModelsDataCollection = databaseHandler.getSampleCollectionRoutine(sFCID, "Routine");

        adapter = new DataUpload_Adapter(DataUploadRoutine_Activity.this, sampleModelsDataCollection, new BtnImage() {
            @Override
            public void onBtnImageValue(final int position) {

                final PrettyDialog prettyDialog = new PrettyDialog(DataUploadRoutine_Activity.this);
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
                                            if (!TextUtils.isEmpty(sampleModelsDataCollection.get(position).getImg_source())) {
                                                String spl[] = new String[0];
                                                try {
                                                    spl = sampleModelsDataCollection.get(position).getImg_source().split("Sunanda_EI/");
                                                } catch (Exception e) {
                                                    Log.e("Sample Error", "");
                                                }
                                                try {
                                                    uploadFileSource(spl[1], sampleModelsDataCollection.get(position).getImg_source(), sampleModelsDataCollection.get(position));
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

                new AlertDialog.Builder(DataUploadRoutine_Activity.this)
                        .setTitle("Delete")
                        .setMessage("Do you want to Delete?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                DatabaseHandler databaseHandler = new DatabaseHandler(DataUploadRoutine_Activity.this);
                                //databaseHandler.deleteSampleCollection(sampleModelsDataCollection.get(position).getID());
                                databaseHandler.UpdateRecycleBin(sampleModelsDataCollection.get(position).getID(), "1");
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

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(DataUploadRoutine_Activity.this);
        rvUploadData.setLayoutManager(mLayoutManager);
        rvUploadData.setItemAnimator(new DefaultItemAnimator());
        rvUploadData.setAdapter(adapter);
    }

    private void showRow() {

        DatabaseHandler databaseHandler = new DatabaseHandler(DataUploadRoutine_Activity.this);
        sampleModelsDataCollection = new ArrayList<>();
        String sFCID = CGlobal.getInstance().getPersistentPreference(DataUploadRoutine_Activity.this)
                .getString(Constants.PREFS_USER_FACILITATOR_ID, "");

        sampleModelsDataCollection = databaseHandler.getSampleCollectionRoutine(sFCID, "Routine");

        adapter = new DataUpload_Adapter(DataUploadRoutine_Activity.this, sampleModelsDataCollection, new BtnImage() {
            @Override
            public void onBtnImageValue(final int position1) {
                final PrettyDialog prettyDialog = new PrettyDialog(DataUploadRoutine_Activity.this);
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
                                            if (!TextUtils.isEmpty(sampleModelsDataCollection.get(position1).getImg_source())) {
                                                String spl[] = new String[0];
                                                try {
                                                    spl = sampleModelsDataCollection.get(position1).getImg_source().split("Sunanda_EI/");
                                                } catch (Exception e) {
                                                    Log.e("Sample Error", "");
                                                }
                                                try {
                                                    uploadFileSource(spl[1], sampleModelsDataCollection.get(position1).getImg_source(),
                                                            sampleModelsDataCollection.get(position1));
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
                new AlertDialog.Builder(DataUploadRoutine_Activity.this)
                        .setTitle("Delete")
                        .setMessage("Do you want to Delete?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                DatabaseHandler databaseHandler = new DatabaseHandler(DataUploadRoutine_Activity.this);
                                //databaseHandler.deleteSampleCollection(sampleModelsDataCollection.get(position).getID());
                                databaseHandler.UpdateRecycleBin(sampleModelsDataCollection.get(position).getID(), "1");
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

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(DataUploadRoutine_Activity.this);
        rvUploadData.setLayoutManager(mLayoutManager);
        rvUploadData.setItemAnimator(new DefaultItemAnimator());
        rvUploadData.setAdapter(adapter);

        /*if (sampleModelsDataCollection.size() > 0) {
            tvNoData.setVisibility(View.GONE);
        } else {
            tvNoData.setVisibility(View.VISIBLE);
        }*/
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
        progressdialog = new ProgressDialog(DataUploadRoutine_Activity.this);
        progressdialog.setMessage("Please Wait....");
    }

    String json = "";

    private void sendData(final SampleModel_Routine sampleModel) {
        try {
            progressdialog.setMessage("Send Data Please Wait....");
        } catch (Exception e) {
            Log.e("Sample Error", "");
        }
        try {
            json = new Gson().toJson(sampleModel);
        } catch (Exception e) {
            Log.e("Sample Error", "");
        }
        try {
            sUrl = CommonURL.New_postRutine_1_URL;

            StringRequest postRequest = new StringRequest(Request.Method.POST,
                    sUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("Sample Response", response);
                    try {
                        if (response.equalsIgnoreCase("Successfuly inserted data")) {
                            DatabaseHandler databaseHandler = new DatabaseHandler(DataUploadRoutine_Activity.this);
                            //databaseHandler.deleteSampleCollection(sampleModel.getID());
                            databaseHandler.UpdateRecycleBin(sampleModel.getID(), "1");
                            adapter.notifyDataSetChanged();

                            showRow();

                           /* AlertDialog.Builder builder1 = new AlertDialog.Builder(DataUploadRoutine_Activity.this);
                            builder1.setMessage("Successfully inserted data");
                            builder1.setCancelable(true);
                            builder1.setPositiveButton("Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.dismiss();
                                        }
                                    });
                            AlertDialog alert11 = builder1.create();
                            if (!DataUploadRoutine_Activity.this.isFinishing()) {
                                alert11.show();
                            }*/

                            final PrettyDialog prettyDialog = new PrettyDialog(DataUploadRoutine_Activity.this);
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
                            DatabaseHandler databaseHandler = new DatabaseHandler(DataUploadRoutine_Activity.this);
                            //databaseHandler.deleteSampleCollection(sampleModel.getID());
                            databaseHandler.UpdateRecycleBin(sampleModel.getID(), "1");
                            adapter.notifyDataSetChanged();

                            showRow();

                            final PrettyDialog prettyDialog = new PrettyDialog(DataUploadRoutine_Activity.this);
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
                           /* AlertDialog.Builder builder1 = new AlertDialog.Builder(DataUploadRoutine_Activity.this);
                            builder1.setMessage("Please try again");
                            builder1.setCancelable(true);
                            builder1.setPositiveButton("Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.dismiss();
                                        }
                                    });
                            AlertDialog alert11 = builder1.create();
                            if (!DataUploadRoutine_Activity.this.isFinishing()) {
                                alert11.show();
                            }*/

                            final PrettyDialog prettyDialog = new PrettyDialog(DataUploadRoutine_Activity.this);
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
                    } catch (Exception e) {
                        Log.e("Sample Error", "");
                    }
                    if (progressdialog != null && progressdialog.isShowing()) {
                        progressdialog.cancel();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Sample Error", "");
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
            CGlobal.getInstance().addVolleyRequest(postRequest, false, DataUploadRoutine_Activity.this);
        } catch (Exception e) {
            Log.e("Sample Error", "");
        }
    }

    private void uploadFileSource(final String imageName, String imageFile, final SampleModel_Routine sampleModel) {

        try {
            progressdialog.setMessage("Send Source Image Please Wait....");
            progressdialog.show();
        } catch (Exception e) {
            Log.e("Sample Error", "");
        }
        File file = new File(imageFile);
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), imageName);
        RequestBody key = RequestBody.create(MediaType.parse("text/plain"), "abc123456");
        RequestBody type = RequestBody.create(MediaType.parse("text/plain"), "Routine");
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
                            if (!TextUtils.isEmpty(sampleModel.getSanitary_W_S_Q_img())) {
                                String spl[] = new String[0];
                                try {
                                    spl = sampleModel.getSanitary_W_S_Q_img().split("Sunanda_EI/");
                                } catch (Exception e) {
                                    Log.e("Sample Error", "");
                                }
                                try {
                                    uploadFileSurvey(spl[1], sampleModel.getSanitary_W_S_Q_img(), sampleModel);
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
                            Log.e("Sample Error", "");
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    assert serverResponse != null;
                    Log.v("Response", serverResponse.toString());
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(DataUploadRoutine_Activity.this);
                    builder1.setMessage("Source Image Not Send. Please try again");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    if (!DataUploadRoutine_Activity.this.isFinishing()) {
                        alert11.show();
                    }
                }
                if (progressdialog != null && progressdialog.isShowing()) {
                    progressdialog.cancel();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.v("Response", "Error");
                if (progressdialog != null && progressdialog.isShowing()) {
                    progressdialog.cancel();
                }
            }
        });
    }

    private void uploadFileSurvey(String imageName, String imageFile, final SampleModel_Routine sampleModel) {

        try {
            progressdialog.setMessage("Send Survey Image Please Wait....");
        } catch (Exception e) {
            Log.e("Sample Error", "");
        }

        // Map is used to multipart the file using okhttp3.RequestBody
        File file = new File(imageFile);
        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), imageName);
        RequestBody key = RequestBody.create(MediaType.parse("text/plain"), "abc123456");
        RequestBody type = RequestBody.create(MediaType.parse("text/plain"), "Routine");
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
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(DataUploadRoutine_Activity.this);
                    builder1.setMessage("Survey Image Not Send. Please try again");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    if (!DataUploadRoutine_Activity.this.isFinishing()) {
                        alert11.show();
                    }
                }
                if (progressdialog != null && progressdialog.isShowing()) {
                    progressdialog.cancel();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.v("Response", "Error");
                if (progressdialog != null && progressdialog.isShowing()) {
                    progressdialog.cancel();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (progressdialog != null && progressdialog.isShowing()) {
            progressdialog.cancel();
        }
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

}
