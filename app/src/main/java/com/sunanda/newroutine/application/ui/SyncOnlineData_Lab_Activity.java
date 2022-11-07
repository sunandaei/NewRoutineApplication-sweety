package com.sunanda.newroutine.application.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.database.DatabaseHandler;
import com.sunanda.newroutine.application.util.CGlobal;
import com.sunanda.newroutine.application.util.Constants;

import org.json.JSONObject;

public class SyncOnlineData_Lab_Activity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sync_online_data_activity);
    }

    ProgressDialog progressdialog;
    String sDistrictId = "", sLabId = "";
    boolean isTrue = false;
    DatabaseHandler databaseHandler;

    @Override
    protected void onResume() {
        super.onResume();

        sLabId = CGlobal.getInstance().getPersistentPreference(SyncOnlineData_Lab_Activity.this)
                .getString(Constants.PREFS_USER_LAB_ID, "");

        sDistrictId = CGlobal.getInstance().getPersistentPreference(SyncOnlineData_Lab_Activity.this)
                .getString(Constants.PREFS_USER_DISTRICT_ID, "");
        databaseHandler = new DatabaseHandler(SyncOnlineData_Lab_Activity.this);
        progressdialog = new ProgressDialog(SyncOnlineData_Lab_Activity.this);
        progressdialog.setMessage("Please Wait....");
        progressdialog.setCancelable(false);
        boolean isConnected = CGlobal.getInstance().isConnected(SyncOnlineData_Lab_Activity.this);
        if (isConnected) {
            if (!isTrue) {
                //getLabData();
                isTrue = true;
            }
        } else {
            new AlertDialog.Builder(SyncOnlineData_Lab_Activity.this)
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

/*    private void getLabData() {
        boolean isConnected = CGlobal.getInstance().isConnected(SyncOnlineData_Lab_Activity.this);
        if (isConnected) {
            try {
                progressdialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

            String sLabCode = CGlobal.getInstance().getPersistentPreference(SyncOnlineData_Lab_Activity.this)
                    .getString(Constants.PREFS_USER_LAB_CODE, "");

            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {
                            Request.Builder ongoing = chain.request().newBuilder();
                            return chain.proceed(ongoing.build());
                        }
                    })
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(PostInterface.JSONURL)
                    .client(httpClient)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();

            PostInterface api = retrofit.create(PostInterface.class);

            Call<String> call = api.getLabRelation(sLabCode);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                    Log.i("Responsestring", response.body().toString());
                    progressdialog.dismiss();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            Log.i("onSuccess", response.body().toString());

                            String jsonresponse = response.body().toString();
                            getLabDataResponse(jsonresponse);

                        } else {
                            Log.i("onEmptyResponse", "Returned empty response");
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.i("onEmptyResponse", "Returned empty response");
                    progressdialog.dismiss();
                }
            });
        } else {
            new AlertDialog.Builder(SyncOnlineData_Lab_Activity.this)
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

    String oId = "", state_code = "", dist_code = "", dist_name = "", block_code = "", block_name = "", oId_pan_unique = "",
            pan_code = "", panchayat_name = "", sLabCode = "", lab_name = "", isSpecialLab = "", isSpecialStateLab = "",
            oId_VillageHab = "", vill_code = "", vill_name = "", isActive_VillageHab = "", oId_Habitation = "",
            habitation_code = "", habitation_name = "", isActive_habitation = "";
    double latitude = 0.0, longitude = 0.0;

    private void getLabDataResponse(String response) {
        try {

            JSONArray arrayLabData = new JSONArray(response);
            for (int i = 0; i < arrayLabData.length(); i++) {
                JSONObject objectLabData = arrayLabData.getJSONObject(i);

                JSONObject object_id = objectLabData.getJSONObject("_id");
                oId = CGlobal.getInstance().isNullNotDefined(object_id, "$oid") ? "" : object_id.getString("$oid");

                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             state_code = CGlobal.getInstance().isNullNotDefined(objectLabData, "state_code") ? "" : objectLabData.getString("state_code");
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        dist_code = CGlobal.getInstance().isNullNotDefined(objectLabData, "dist_code") ? "" : objectLabData.getString("dist_code");
                dist_name = CGlobal.getInstance().isNullNotDefined(objectLabData, "dist_name") ? "" : objectLabData.getString("dist_name");
                block_code = CGlobal.getInstance().isNullNotDefined(objectLabData, "block_code") ? "" : objectLabData.getString("block_code");
                block_name = CGlobal.getInstance().isNullNotDefined(objectLabData, "block_name") ? "" : objectLabData.getString("block_name");

                JSONObject object_pan_code_unique = objectLabData.getJSONObject("pan_code_unique");
                oId_pan_unique = CGlobal.getInstance().isNullNotDefined(object_pan_code_unique, "$oid") ? "" : object_pan_code_unique.getString("$oid");

                pan_code = CGlobal.getInstance().isNullNotDefined(objectLabData, "pan_code") ? "" : objectLabData.getString("pan_code");
                panchayat_name = CGlobal.getInstance().isNullNotDefined(objectLabData, "panchayat_name") ? "" : objectLabData.getString("panchayat_name");
                sLabCode = CGlobal.getInstance().isNullNotDefined(objectLabData, "LabCode") ? "" : objectLabData.getString("LabCode");
                lab_name = CGlobal.getInstance().isNullNotDefined(objectLabData, "lab_name") ? "" : objectLabData.getString("lab_name");
                isSpecialLab = CGlobal.getInstance().isNullNotDefined(objectLabData, "isSpecialLab") ? "" : objectLabData.getString("isSpecialLab");
                isSpecialStateLab = CGlobal.getInstance().isNullNotDefined(objectLabData, "isSpecialStateLab") ? "" : objectLabData.getString("isSpecialStateLab");
                latitude = CGlobal.getInstance().isNullNotDefined(objectLabData, "latitude") ? 0.0 : objectLabData.getDouble("latitude");
                longitude = CGlobal.getInstance().isNullNotDefined(objectLabData, "longitude") ? 0.0 : objectLabData.getDouble("longitude");

                JSONArray arrayVillageHab = objectLabData.getJSONArray("villageHab");
                for (int j = 0; j < arrayVillageHab.length(); j++) {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                            JSONObject objectVillageHab = arrayVillageHab.getJSONObject(j);

                    JSONObject objectVillageHab_id = objectVillageHab.getJSONObject("_id");
                    oId_VillageHab = CGlobal.getInstance().isNullNotDefined(objectVillageHab_id, "$oid") ? "" : objectVillageHab_id.getString("$oid");

                    vill_code = CGlobal.getInstance().isNullNotDefined(objectVillageHab, "vill_code") ? "" : objectVillageHab.getString("vill_code");
                    vill_name = CGlobal.getInstance().isNullNotDefined(objectVillageHab, "vill_name") ? "" : objectVillageHab.getString("vill_name");
                    isActive_VillageHab = CGlobal.getInstance().isNullNotDefined(objectVillageHab, "isActive") ? "" : objectVillageHab.getString("isActive");

                    JSONArray arrayHabitation = objectVillageHab.getJSONArray("habitation");
                    for (int k = 0; k < arrayHabitation.length(); k++) {
                        JSONObject objectHabitation = arrayHabitation.getJSONObject(k);

                        JSONObject objectHabitation_id = objectHabitation.getJSONObject("_id");
                        oId_Habitation = CGlobal.getInstance().isNullNotDefined(objectHabitation_id, "$oid") ? "" : objectHabitation_id.getString("$oid");

                        habitation_code = CGlobal.getInstance().isNullNotDefined(objectHabitation, "habitation_code") ? "" : objectHabitation.getString("habitation_code");
                        habitation_name = CGlobal.getInstance().isNullNotDefined(objectHabitation, "habitation_name") ? "" : objectHabitation.getString("habitation_name");
                        isActive_habitation = CGlobal.getInstance().isNullNotDefined(objectHabitation, "isActive") ? "" : objectHabitation.getString("isActive");

                        databaseHandler.addLabRelation(oId, state_code, dist_code, dist_name, block_code, block_name,
                                oId_pan_unique, pan_code, panchayat_name, sLabCode, lab_name, isSpecialLab, isSpecialStateLab,
                                latitude, longitude, oId_VillageHab, vill_code, vill_name, isActive_VillageHab, oId_Habitation,
                                habitation_code, habitation_name, isActive_habitation);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        progressdialog.dismiss();

        getExistingSource();
    }

    private void getExistingSource() {
        boolean isConnected = CGlobal.getInstance().isConnected(SyncOnlineData_Lab_Activity.this);
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
                            Request.Builder ongoing = chain.request().newBuilder();
                            return chain.proceed(ongoing.build());
                        }
                    })
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(PostInterface.JSONURL)
                    .client(httpClient)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();

            PostInterface api = retrofit.create(PostInterface.class);

            Call<String> call = api.getSource("5d63a6b1ae9c901c7c4f4453");

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                    Log.i("Responsestring", response.body().toString());
                    progressdialog.dismiss();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            Log.i("onSuccess", response.body().toString());

                            String jsonresponse = response.body().toString();
                            getExistingSourceResponse(jsonresponse);

                        } else {
                            Log.i("onEmptyResponse", "Returned empty response");
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.i("onEmptyResponse", "Returned empty response");
                    progressdialog.dismiss();
                }
            });
        } else {
            new AlertDialog.Builder(SyncOnlineData_Lab_Activity.this)
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

    double lat, lng;

    private void getExistingSourceResponse(String response) {
        try {
            databaseHandler.deleteExistingSource();
            JSONArray arrayExistingSource = new JSONArray(response);
            for (int i = 0; i < arrayExistingSource.length(); i++) {
                JSONObject objectExisting = arrayExistingSource.getJSONObject(i);

                JSONObject object_id = objectExisting.getJSONObject("_id");
                String oId = CGlobal.getInstance().isNullNotDefined(object_id, "$oid") ? "" : object_id.getString("$oid");

                String app_version = isNullNotDefined(objectExisting, "app_version") ? "" : objectExisting.getString("app_version");
                String block_code = isNullNotDefined(objectExisting, "block_code") ? "" : objectExisting.getString("block_code");
                String block_name = isNullNotDefined(objectExisting, "block_name") ? "" : objectExisting.getString("block_name");
                String collection_date = isNullNotDefined(objectExisting, "collection_date") ? "" : objectExisting.getString("collection_date");
                String district_name = isNullNotDefined(objectExisting, "district_name") ? "" : objectExisting.getString("district_name");
                String dist_code = isNullNotDefined(objectExisting, "dist_code") ? "" : objectExisting.getString("dist_code");
                String existing_loc = isNullNotDefined(objectExisting, "existing_loc") ? "" : objectExisting.getString("existing_loc");
                String ExistingLocationID = isNullNotDefined(objectExisting, "ExistingLocationID") ? "" : objectExisting.getString("ExistingLocationID");
                String habitation_name = isNullNotDefined(objectExisting, "habitation_name") ? "" : objectExisting.getString("habitation_name");
                String habitation_code = isNullNotDefined(objectExisting, "habitation_code") ? "" : objectExisting.getString("habitation_code");
                String health_facility = isNullNotDefined(objectExisting, "health_facility") ? "" : objectExisting.getString("health_facility");
                String imei = isNullNotDefined(objectExisting, "imei") ? "" : objectExisting.getString("imei");
                String img_source = isNullNotDefined(objectExisting, "img_source") ? "" : objectExisting.getString("img_source");
                String interview_id = isNullNotDefined(objectExisting, "interview_id") ? "" : objectExisting.getString("interview_id");
                String LabCode = isNullNotDefined(objectExisting, "LabCode") ? "" : objectExisting.getString("LabCode");
                String lab_Name = isNullNotDefined(objectExisting, "lab_Name") ? "" : objectExisting.getString("lab_Name");
                String latitude = isNullNotDefined(objectExisting, "latitude") ? "" : objectExisting.getString("latitude");
                String longitude = isNullNotDefined(objectExisting, "longitude") ? "" : objectExisting.getString("longitude");
                String new_loc = isNullNotDefined(objectExisting, "new_loc") ? "" : objectExisting.getString("new_loc");
                String name_of_special_drive = isNullNotDefined(objectExisting, "name_of_special_drive") ? "" : objectExisting.getString("name_of_special_drive");
                String panchayat_name = isNullNotDefined(objectExisting, "panchayat_name") ? "" : objectExisting.getString("panchayat_name");
                String pan_code = isNullNotDefined(objectExisting, "pan_code") ? "" : objectExisting.getString("pan_code");
                String sampleCollectorId = isNullNotDefined(objectExisting, "sampleCollectorId") ? "" : objectExisting.getString("sampleCollectorId");
                String sample_bott_num = isNullNotDefined(objectExisting, "sample_bott_num") ? "" : objectExisting.getString("sample_bott_num");
                String sample_id = isNullNotDefined(objectExisting, "sample_id") ? "" : objectExisting.getString("sample_id");
                String source_code = isNullNotDefined(objectExisting, "source_code") ? "" : objectExisting.getString("source_code");
                String source_details = isNullNotDefined(objectExisting, "source_details") ? "" : objectExisting.getString("source_details");
                String source_site = isNullNotDefined(objectExisting, "source_site") ? "" : objectExisting.getString("source_site");
                String source_unique = isNullNotDefined(objectExisting, "source_unique") ? "" : objectExisting.getString("source_unique");
                String special_drive = isNullNotDefined(objectExisting, "special_drive") ? "" : objectExisting.getString("special_drive");
                String sub_source_type = isNullNotDefined(objectExisting, "sub_source_type") ? "" : objectExisting.getString("sub_source_type");
                String sub_scheme_name = isNullNotDefined(objectExisting, "sub_scheme_name") ? "" : objectExisting.getString("sub_scheme_name");
                String town_code = isNullNotDefined(objectExisting, "town_code") ? "" : objectExisting.getString("town_code");
                String town_name = isNullNotDefined(objectExisting, "town_name") ? "" : objectExisting.getString("town_name");
                String type_of_locality = isNullNotDefined(objectExisting, "type_of_locality") ? "" : objectExisting.getString("type_of_locality");
                String village_name = isNullNotDefined(objectExisting, "village_name") ? "" : objectExisting.getString("village_name");
                String vill_code = isNullNotDefined(objectExisting, "vill_code") ? "" : objectExisting.getString("vill_code");
                String ward_number = isNullNotDefined(objectExisting, "ward_number") ? "" : objectExisting.getString("ward_number");
                String water_source_type = isNullNotDefined(objectExisting, "water_source_type") ? "" : objectExisting.getString("water_source_type");

                if (TextUtils.isEmpty(latitude)) {
                    lat = 0.0;
                } else {
                    lat = Double.parseDouble(latitude);
                }

                if (TextUtils.isEmpty(longitude)) {
                    lng = 0.0;
                } else {
                    lng = Double.parseDouble(longitude);
                }

                databaseHandler.addExistingSource(oId, app_version, block_code, block_name, collection_date,
                        district_name, dist_code, existing_loc, ExistingLocationID, habitation_name,
                        habitation_code, health_facility, imei, img_source, interview_id,
                        LabCode, lab_Name, lat, lng, new_loc,
                        panchayat_name, pan_code, sampleCollectorId, sample_bott_num, sample_id,
                        source_code, source_details, source_site, source_unique, special_drive,
                        name_of_special_drive, sub_source_type, sub_scheme_name, town_code, town_name,
                        type_of_locality, village_name, vill_code, ward_number, water_source_type);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        progressdialog.dismiss();

        CGlobal.getInstance().getPersistentPreferenceEditor(SyncOnlineData_Lab_Activity.this)
                .putBoolean(Constants.PREFS_SYNC_ONLINE_DATA_FLAG, true).commit();
        Intent intent = new Intent(SyncOnlineData_Lab_Activity.this, DashBoard_Activity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
        finish();
    }*/

    /*private void getSourceSite() {
        try {
            progressdialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sUrl = CommonURL.getSourceSiteMaster_URL + "?&AppKey=Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3";

        StringRequest postRequest = new StringRequest(Request.Method.GET,
                sUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                getSourceSiteResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressdialog.dismiss();
                try {
                    new AlertDialog.Builder(SyncOnlineData_Lab_Activity.this)
                            .setMessage("Downloading error. Please try again")
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
                return CGlobal.getInstance().checkParams(params);
            }
        };
        CGlobal.getInstance().addVolleyRequest(postRequest, false, SyncOnlineData_Lab_Activity.this);
    }

    private void getSourceSiteResponse(String response) {
        try {
            databaseHandler.deleteSourceSite();
            response = response.replaceAll("\r\n", "");
            JSONArray sourceLocalityArray = new JSONArray(response);
            for (int i = 0; i < sourceLocalityArray.length(); i++) {
                JSONObject sLocality_obj = sourceLocalityArray.getJSONObject(i);
                String sID = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "ID") ? "" : sLocality_obj.getString("ID");
                String sName = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "Name") ? "" : sLocality_obj.getString("Name");

                databaseHandler.addSourceSite(sID, sName);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        progressdialog.dismiss();
        getSourceType();
    }

    private void getSourceType() {
        try {
            progressdialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sUrl = CommonURL.GetSourceTypeMaster_URL + "?&AppKey=Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3&xtype=R";

        StringRequest postRequest = new StringRequest(Request.Method.GET,
                sUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                getSourceTypeResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressdialog.dismiss();
                try {
                    new AlertDialog.Builder(SyncOnlineData_Lab_Activity.this)
                            .setMessage("Downloading error. Please try again")
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
                return CGlobal.getInstance().checkParams(params);
            }
        };
        CGlobal.getInstance().addVolleyRequest(postRequest, false, SyncOnlineData_Lab_Activity.this);
    }

    private void getSourceTypeResponse(String response) {
        try {
            databaseHandler.deleteSourceType();
            response = response.replaceAll("\r\n", "");
            JSONArray sourceLocalityArray = new JSONArray(response);
            for (int i = 0; i < sourceLocalityArray.length(); i++) {
                JSONObject sLocality_obj = sourceLocalityArray.getJSONObject(i);
                String sID = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "ID") ? "" : sLocality_obj.getString("ID");
                String sName = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "Name") ? "" : sLocality_obj.getString("Name");

                databaseHandler.addSourceType(sID, sName);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        progressdialog.dismiss();
        getChildSourceType();
    }

    private void getChildSourceType() {
        try {
            progressdialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sUrl = CommonURL.GetChildSourceTypeMaster_URL + "?&AppKey=Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3";

        StringRequest postRequest = new StringRequest(Request.Method.GET,
                sUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                getChildSourceTypeResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressdialog.dismiss();
                try {
                    new AlertDialog.Builder(SyncOnlineData_Lab_Activity.this)
                            .setMessage("Downloading error. Please try again")
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
                return CGlobal.getInstance().checkParams(params);
            }
        };
        CGlobal.getInstance().addVolleyRequest(postRequest, false, SyncOnlineData_Lab_Activity.this);
    }

    private void getChildSourceTypeResponse(String response) {
        try {
            databaseHandler.deleteChildSourceType();
            response = response.replaceAll("\r\n", "");
            JSONArray sourceLocalityArray = new JSONArray(response);
            for (int i = 0; i < sourceLocalityArray.length(); i++) {
                JSONObject sLocality_obj = sourceLocalityArray.getJSONObject(i);
                String sID = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "ID") ? "" : sLocality_obj.getString("ID");
                String sName = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "Name") ? "" : sLocality_obj.getString("Name");
                String parentId = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "parentId") ? "" : sLocality_obj.getString("parentId");

                databaseHandler.addChildSourceType(sID, sName, parentId);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        progressdialog.dismiss();
        getSpecialDrive();
    }

    private void getSpecialDrive() {
        try {
            progressdialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sUrl = CommonURL.GetSpecialDriveMaster_URL + "?appname=r&AppKey=Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3";

        StringRequest postRequest = new StringRequest(Request.Method.GET,
                sUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                getSpecialDriveResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressdialog.dismiss();
                try {
                    new AlertDialog.Builder(SyncOnlineData_Lab_Activity.this)
                            .setMessage("Downloading error. Please try again")
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
                return CGlobal.getInstance().checkParams(params);
            }
        };
        CGlobal.getInstance().addVolleyRequest(postRequest, false, SyncOnlineData_Lab_Activity.this);
    }

    private void getSpecialDriveResponse(String response) {
        try {
            databaseHandler.deleteSpecialDrive();
            response = response.replaceAll("\r\n", "");
            JSONArray sourceLocalityArray = new JSONArray(response);
            for (int i = 0; i < sourceLocalityArray.length(); i++) {
                JSONObject sLocality_obj = sourceLocalityArray.getJSONObject(i);
                String sID = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "ID") ? "" : sLocality_obj.getString("ID");
                String sName = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "Name") ? "" : sLocality_obj.getString("Name");

                databaseHandler.addSpecialDrive(sID, sName);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        progressdialog.dismiss();
        getTown();
    }

    private void getTown() {
        try {
            progressdialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sUrl = CommonURL.GetTownMaster_URL + "?DistrictID=" + sDistrictId + "&AppKey=Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3";

        StringRequest postRequest = new StringRequest(Request.Method.GET,
                sUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                getTownResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressdialog.dismiss();
                try {
                    new AlertDialog.Builder(SyncOnlineData_Lab_Activity.this)
                            .setMessage("Downloading error. Please try again")
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
                return CGlobal.getInstance().checkParams(params);
            }
        };
        CGlobal.getInstance().addVolleyRequest(postRequest, false, SyncOnlineData_Lab_Activity.this);
    }

    private void getTownResponse(String response) {
        try {
            databaseHandler.deleteTown();
            response = response.replaceAll("\r\n", "");
            JSONArray sourceLocalityArray = new JSONArray(response);
            for (int i = 0; i < sourceLocalityArray.length(); i++) {
                JSONObject sLocality_obj = sourceLocalityArray.getJSONObject(i);
                String sID = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "ID") ? "" : sLocality_obj.getString("ID");
                String sDistrictName = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "DistrictName") ? "" : sLocality_obj.getString("DistrictName");
                String town_name = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "town_name") ? "" : sLocality_obj.getString("town_name");
                String ward_no = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "ward_no") ? "" : sLocality_obj.getString("ward_no");

                databaseHandler.addTown(sID, sDistrictName, town_name, ward_no);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        progressdialog.dismiss();
        getHealthFacility();
    }

    private void getHealthFacility() {
        try {
            progressdialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sUrl = CommonURL.GetHealthFacilityMaster_URL + "?DistrictID=" + sDistrictId + "&AppKey=Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3";

        StringRequest postRequest = new StringRequest(Request.Method.GET,
                sUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                getHealthFacilityResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressdialog.dismiss();
                try {
                    new AlertDialog.Builder(SyncOnlineData_Lab_Activity.this)
                            .setMessage("Downloading error. Please try again")
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
                return CGlobal.getInstance().checkParams(params);
            }
        };
        CGlobal.getInstance().addVolleyRequest(postRequest, false, SyncOnlineData_Lab_Activity.this);
    }

    private void getHealthFacilityResponse(String response) {
        try {
            databaseHandler.deleteHealthFacility();
            response = response.replaceAll("\r\n", "");
            JSONArray sourceLocalityArray = new JSONArray(response);
            for (int i = 0; i < sourceLocalityArray.length(); i++) {
                JSONObject sLocality_obj = sourceLocalityArray.getJSONObject(i);
                String sID = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "ID") ? "" : sLocality_obj.getString("ID");
                String sDistrictName = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "DistrictName") ? "" : sLocality_obj.getString("DistrictName");
                String health_facility_name = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "health_facility_name") ? "" : sLocality_obj.getString("health_facility_name");

                databaseHandler.addHealthFacility(sID, sDistrictName, health_facility_name);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        progressdialog.dismiss();
        getPipedWaterSupplyScheme();
    }

    private void getPipedWaterSupplyScheme() {
        try {
            progressdialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sUrl = CommonURL.GetPipedWaterSupplyScheme_URL + "?DistrictID=" + sDistrictId + "&AppKey=Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3";

        StringRequest postRequest = new StringRequest(Request.Method.GET,
                sUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                getPipedWaterSupplySchemeResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressdialog.dismiss();
                try {
                    new AlertDialog.Builder(SyncOnlineData_Lab_Activity.this)
                            .setMessage("Downloading error. Please try again")
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
                return CGlobal.getInstance().checkParams(params);
            }
        };
        CGlobal.getInstance().addVolleyRequest(postRequest, false, SyncOnlineData_Lab_Activity.this);
    }

    private void getPipedWaterSupplySchemeResponse(String response) {
        try {
            databaseHandler.deletePipedWaterSupplyScheme();
            response = response.replaceAll("\r\n", "");
            JSONArray sourceLocalityArray = new JSONArray(response);
            for (int i = 0; i < sourceLocalityArray.length(); i++) {
                JSONObject sLocality_obj = sourceLocalityArray.getJSONObject(i);
                String sID = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "ID") ? "" : sLocality_obj.getString("ID");
                String sDistrictName = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "DistrictName") ? "" : sLocality_obj.getString("DistrictName");
                String sCityName = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "CityName") ? "" : sLocality_obj.getString("CityName");
                String pwss_name = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "pwss_name") ? "" : sLocality_obj.getString("pwss_name");
                String sm_code = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "sm_code") ? "" : sLocality_obj.getString("sm_code");
                String zone = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "zone") ? "" : sLocality_obj.getString("zone");
                String big_dia_tube_well_code = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "big_dia_tube_well_code") ? "" : sLocality_obj.getString("big_dia_tube_well_code");
                String big_dia_tube_well_no = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "big_dia_tube_well_no") ? "" : sLocality_obj.getString("big_dia_tube_well_no");

                pwss_name = pwss_name.replaceAll("'", "");
                zone = zone.replaceAll("'", "");

                databaseHandler.addPipedWaterSupplyScheme(sID, sDistrictName, sCityName, pwss_name, sm_code,
                        zone, big_dia_tube_well_code, big_dia_tube_well_no);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        progressdialog.dismiss();
        getLab();
    }

    private void getLab() {
        try {
            progressdialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sUrl = CommonURL.GetLabMaster_URL + "?DistrictID=" + sDistrictId + "&AppKey=Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3";

        StringRequest postRequest = new StringRequest(Request.Method.GET,
                sUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                getLabResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressdialog.dismiss();
                try {
                    new AlertDialog.Builder(SyncOnlineData_Lab_Activity.this)
                            .setMessage("Downloading error. Please try again")
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
                return CGlobal.getInstance().checkParams(params);
            }
        };
        CGlobal.getInstance().addVolleyRequest(postRequest, false, SyncOnlineData_Lab_Activity.this);
    }

    private void getLabResponse(String response) {
        try {
            databaseHandler.deleteLab();
            response = response.replaceAll("\r\n", "");
            JSONArray sourceLocalityArray = new JSONArray(response);
            for (int i = 0; i < sourceLocalityArray.length(); i++) {
                JSONObject sLocality_obj = sourceLocalityArray.getJSONObject(i);
                String sID = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "ID") ? "" : sLocality_obj.getString("ID");
                String sDistrictName = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "DistrictName") ? "" : sLocality_obj.getString("DistrictName");
                String sCityName = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "CityName") ? "" : sLocality_obj.getString("CityName");
                String LabCode = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "LabCode") ? "" : sLocality_obj.getString("LabCode");
                String LabName = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "LabName") ? "" : sLocality_obj.getString("LabName");

                databaseHandler.addLab(sID, sDistrictName, sCityName, LabCode, LabName);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        progressdialog.dismiss();
        getRoster();
    }

    private void getRoster() {
        try {
            progressdialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sUrl = CommonURL.GetRosterData_URL + "?LabID=" + sLabId + "&AppKey=Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3";

        StringRequest postRequest = new StringRequest(Request.Method.GET,
                sUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                getRosterResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressdialog.dismiss();
                try {
                    new AlertDialog.Builder(SyncOnlineData_Lab_Activity.this)
                            .setMessage("Downloading error. Please try again")
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
                return CGlobal.getInstance().checkParams(params);
            }
        };
        CGlobal.getInstance().addVolleyRequest(postRequest, false, SyncOnlineData_Lab_Activity.this);
    }

    private void getRosterResponse(String response) {
        try {
            databaseHandler.deleteRoster();
            response = response.replaceAll("\r\n", "");
            JSONArray sourceLocalityArray = new JSONArray(response);
            for (int i = 0; i < sourceLocalityArray.length(); i++) {
                JSONObject sLocality_obj = sourceLocalityArray.getJSONObject(i);
                String sStateName = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "StateName") ? "" : sLocality_obj.getString("StateName");
                String sDistrictName = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "DistrictName") ? "" : sLocality_obj.getString("DistrictName");
                String sBlocKName = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "BlocKName") ? "" : sLocality_obj.getString("BlocKName");
                String sPanchayatName = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "PanchayatName") ? "" : sLocality_obj.getString("PanchayatName");
                String sVillageName = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "VillageName") ? "" : sLocality_obj.getString("VillageName");
                String sHabitationName = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "HabitationName") ? "" : sLocality_obj.getString("HabitationName");
                String sSource = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "source") ? "" : sLocality_obj.getString("source");
                String sSourceName = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "SourceName") ? "" : sLocality_obj.getString("SourceName");
                String sDistrictcode = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "districtcode") ? "" : sLocality_obj.getString("districtcode");
                String sBlockcode = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "blockcode") ? "" : sLocality_obj.getString("blockcode");
                String sPancode = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "Pancode") ? "" : sLocality_obj.getString("Pancode");
                String sVillagecode = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "villagecode") ? "" : sLocality_obj.getString("villagecode");
                String sHabecode = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "Habecode") ? "" : sLocality_obj.getString("Habecode");
                String sDistrictid = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "districtid") ? "" : sLocality_obj.getString("districtid");
                String sBlockid = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "blockid") ? "" : sLocality_obj.getString("blockid");
                String sPanid = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "panid") ? "" : sLocality_obj.getString("panid");
                String sVillageid = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "villageid") ? "" : sLocality_obj.getString("villageid");
                String sHabitationid = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "habitationid") ? "" : sLocality_obj.getString("habitationid");
                String stype = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "stype") ? "" : sLocality_obj.getString("stype");

                databaseHandler.addRoster(sStateName.trim(), sDistrictName.trim(), sBlocKName.trim(), sPanchayatName.trim(), sVillageName.trim(),
                        sHabitationName.trim(), sSource, sSourceName.trim(), sDistrictcode, sBlockcode,
                        sPancode, sVillagecode, sHabecode, sDistrictid, sBlockid,
                        sPanid, sVillageid, sHabitationid, stype.trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        progressdialog.dismiss();
        getArsenic();
    }

    private void getArsenic() {
        try {
            progressdialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sUrl = CommonURL.GetArsenicTrendStationSourceData_URL + "?LabID=" + sLabId + "&AppKey=Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3";

        StringRequest postRequest = new StringRequest(Request.Method.GET,
                sUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                getArsenicResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressdialog.dismiss();
                try {
                    new AlertDialog.Builder(SyncOnlineData_Lab_Activity.this)
                            .setMessage("Downloading error. Please try again")
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
                return CGlobal.getInstance().checkParams(params);
            }
        };
        CGlobal.getInstance().addVolleyRequest(postRequest, false, SyncOnlineData_Lab_Activity.this);
    }

    private void getArsenicResponse(String response) {
        try {
            databaseHandler.deleteArsenic();
            response = response.replaceAll("\r\n", "");
            JSONArray sourceLocalityArray = new JSONArray(response);
            for (int i = 0; i < sourceLocalityArray.length(); i++) {
                JSONObject sLocality_obj = sourceLocalityArray.getJSONObject(i);
                String sDistrict = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "District") ? "" : sLocality_obj.getString("District");
                String sLaboratory = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "Laboratory") ? "" : sLocality_obj.getString("Laboratory");
                String sBlock = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "Block") ? "" : sLocality_obj.getString("Block");
                String sPanchayat = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "Panchayat") ? "" : sLocality_obj.getString("Panchayat");
                String sVillage = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "Village") ? "" : sLocality_obj.getString("Village");
                String sHabitation = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "Habitation") ? "" : sLocality_obj.getString("Habitation");
                String sLocation = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "Location") ? "" : sLocality_obj.getString("Location");
                String districtcode = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "districtcode") ? "" : sLocality_obj.getString("districtcode");
                String blockcode = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "blockcode") ? "" : sLocality_obj.getString("blockcode");
                String pancode = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "pancode") ? "" : sLocality_obj.getString("pancode");
                String districtid = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "districtid") ? "" : sLocality_obj.getString("districtid");
                String blockid = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "blockid") ? "" : sLocality_obj.getString("blockid");
                String panid = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "panid") ? "" : sLocality_obj.getString("panid");

                databaseHandler.addArsenic(sDistrict, sLaboratory, sBlock, sPanchayat, sVillage, sHabitation, sLocation,
                        districtcode, blockcode, pancode, districtid, blockid, panid);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        progressdialog.dismiss();
        getSurveyQuestion();
    }

    private void getSurveyQuestion() {
        try {
            progressdialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sUrl = CommonURL.GetSurveyQuestion_URL + "?AppKey=Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3";

        StringRequest postRequest = new StringRequest(Request.Method.GET,
                sUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                getSurveyQuestionResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressdialog.dismiss();
                try {
                    new AlertDialog.Builder(SyncOnlineData_Lab_Activity.this)
                            .setMessage("Downloading error. Please try again")
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
                return CGlobal.getInstance().checkParams(params);
            }
        };
        CGlobal.getInstance().addVolleyRequest(postRequest, false, SyncOnlineData_Lab_Activity.this);
    }

    private void getSurveyQuestionResponse(String response) {
        try {
            databaseHandler.deleteSurveyQuestion();
            response = response.replaceAll("\r\n", "");
            JSONArray sourceLocalityArray = new JSONArray(response);
            for (int i = 0; i < sourceLocalityArray.length(); i++) {
                JSONObject sLocality_obj = sourceLocalityArray.getJSONObject(i);
                String sQuestionID = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "QuestionID") ? "" : sLocality_obj.getString("QuestionID");
                String sQuestions = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "Questions") ? "" : sLocality_obj.getString("Questions");
                String sSourceTypeID = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "SourceTypeID") ? "" : sLocality_obj.getString("SourceTypeID");

                sQuestions = sQuestions.replaceAll("'", "");

                databaseHandler.addSurveyQuestion(sQuestionID, sQuestions, sSourceTypeID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        progressdialog.dismiss();

    }*/

    public boolean isNullNotDefined(JSONObject jo, String jkey) {
        if (!jo.has(jkey)) {
            return true;
        }
        return jo.isNull(jkey);
    }
}
