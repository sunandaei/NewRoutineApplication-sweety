package com.sunanda.newroutine.application.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.database.DatabaseHandler;
import com.sunanda.newroutine.application.modal.CommonModel;
import com.sunanda.newroutine.application.util.CGlobal;
import com.sunanda.newroutine.application.util.CommonURL;
import com.sunanda.newroutine.application.util.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SyncOnlineData_Facilitator_Activity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sync_online_data_activity);
    }

    ProgressDialog progressdialog;
    String sDistrictId = "", sLabId = "", fcID = "";
    boolean isTrue = false;
    DatabaseHandler databaseHandler;
    ArrayList<CommonModel> commonModelArrayList;

    @Override
    protected void onResume() {
        super.onResume();

        sLabId = CGlobal.getInstance().getPersistentPreference(SyncOnlineData_Facilitator_Activity.this)
                .getString(Constants.PREFS_USER_LAB_ID, "");

        fcID = CGlobal.getInstance().getPersistentPreference(SyncOnlineData_Facilitator_Activity.this)
                .getString(Constants.PREFS_USER_FACILITATOR_ID, "");

        sDistrictId = CGlobal.getInstance().getPersistentPreference(SyncOnlineData_Facilitator_Activity.this)
                .getString(Constants.PREFS_USER_DISTRICT_ID, "");
        databaseHandler = new DatabaseHandler(SyncOnlineData_Facilitator_Activity.this);
        progressdialog = new ProgressDialog(SyncOnlineData_Facilitator_Activity.this);
        progressdialog.setMessage("Please Wait....");
        progressdialog.setCancelable(false);
        boolean isConnected = CGlobal.getInstance().isConnected(SyncOnlineData_Facilitator_Activity.this);
        if (isConnected) {
            if (!isTrue) {
                getAssignHabitationListFCWise();
                isTrue = true;
            }
        } else {
            new AlertDialog.Builder(SyncOnlineData_Facilitator_Activity.this)
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

    private void getAssignHabitationListFCWise() {
        try {
            progressdialog.show();
        } catch (Exception e) {
            Log.e("SyncOnlineData_", e.getMessage());
        }
        String sUrl = CommonURL.GetAssignHabitationListFCWise_URL + "?FCID=" + fcID + "&AppKey=Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3";

        StringRequest postRequest = new StringRequest(Request.Method.GET,
                sUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                getAssignHabitationListFCWiseResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressdialog.dismiss();
                try {
                    Log.e("SyncOnlineDataError ", error.getMessage());
                    new AlertDialog.Builder(SyncOnlineData_Facilitator_Activity.this)
                            .setMessage("Downloading error. Please try again")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                } catch (Exception e) {
                    Log.e("SyncOnlineDataError ", e.getMessage());
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return CGlobal.getInstance().checkParams(params);
            }
        };
        CGlobal.getInstance().addVolleyRequest(postRequest, false, SyncOnlineData_Facilitator_Activity.this);
    }

    private void getAssignHabitationListFCWiseResponse(String response) {
        Log.d("TAG", "getAssignHabitationListFCWiseResponse: " + response);
        try {
            databaseHandler.deleteAssignHabitationList();
            response = response.replaceAll("\r\n", "");
            JSONArray sourceLocalityArray = new JSONArray(response);
            for (int i = 0; i < sourceLocalityArray.length(); i++) {
                JSONObject sLocality_obj = sourceLocalityArray.getJSONObject(i);
                String sBlockName = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "BlockName") ? "" : sLocality_obj.getString("BlockName");
                String sBlock_code = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "block_code") ? "" : sLocality_obj.getString("block_code");
                String sDistrictName = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "DistrictName") ? "" : sLocality_obj.getString("DistrictName");
                String sDist_code = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "dist_code") ? "" : sLocality_obj.getString("dist_code");
                String sFCID = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "FCID") ? "" : sLocality_obj.getString("FCID");
                String sHabName = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "HabName") ? "" : sLocality_obj.getString("HabName");
                String sHab_code = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "Hab_code") ? "" : sLocality_obj.getString("Hab_code");
                String sIsDone = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "IsDone") ? "" : sLocality_obj.getString("IsDone");
                String sLabCode = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "LabCode") ? "" : sLocality_obj.getString("LabCode");
                String sLabID = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "LabID") ? "" : sLocality_obj.getString("LabID");
                String sLogID = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "LogID") ? "" : sLocality_obj.getString("LogID");
                String sPanName = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "PanName") ? "" : sLocality_obj.getString("PanName");
                String sPan_code = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "pan_code") ? "" : sLocality_obj.getString("pan_code");
                String sVillageName = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "VillageName") ? "" : sLocality_obj.getString("VillageName");
                String sVillage_code = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "village_code") ? "" : sLocality_obj.getString("village_code");
                String sCreatedDate = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "CreatedDate") ? "" : sLocality_obj.getString("CreatedDate");
                String sFinishedDate = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "FinishedDate") ? "" : sLocality_obj.getString("FinishedDate");
                String sTask_Id = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "Task_Id") ? "" : sLocality_obj.getString("Task_Id");
                String sFormSubmissionDate = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "FormSubmissionDate") ? "" : sLocality_obj.getString("FormSubmissionDate");
                String pws_status = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "pws_status") ? "" : sLocality_obj.getString("pws_status");
                if (TextUtils.isEmpty(sFormSubmissionDate)) {
                    databaseHandler.addAssignHabitationList(sDist_code, sDistrictName.toUpperCase(), sBlock_code, sBlockName.toUpperCase(),
                            sPan_code, sPanName.toUpperCase(),
                            sVillageName.toUpperCase(), sVillage_code, sHabName.toUpperCase(), sHab_code, sLabCode, sLabID,
                            sFCID, sLogID, sIsDone, sTask_Id,
                            sCreatedDate, sFinishedDate, pws_status, "0");
                } else {
                    Log.d("SyncExc", "Form Submission Date is Empty" + sFormSubmissionDate);
                }
            }
        } catch (Exception e) {
            Log.e("SyncError", e.getMessage());
        }
        progressdialog.dismiss();

        commonModelArrayList = new ArrayList<>();
        commonModelArrayList = databaseHandler.getVillageCodeList();
        databaseHandler.deleteSourceForFacilitator();
        if (commonModelArrayList.size() > 0) {
            for (int k = 0; k < commonModelArrayList.size(); k++) {
                get_horizon_source_by_vill(commonModelArrayList.get(k).getDistrictcode(),
                        commonModelArrayList.get(k).getBlockcode(),
                        commonModelArrayList.get(k).getPancode(),
                        commonModelArrayList.get(k).getVillagecode(),
                        commonModelArrayList.get(k).getHabecode(),
                        commonModelArrayList.get(k).getTask_Id());

                get_horizon_head_site(commonModelArrayList.get(k).getDistrictcode(),
                        commonModelArrayList.get(k).getBlockcode(),
                        commonModelArrayList.get(k).getPancode(),
                        commonModelArrayList.get(k).getVillagecode(),
                        commonModelArrayList.get(k).getHabecode(),
                        commonModelArrayList.get(k).getTask_Id());
            }
            getSourceForFacilitator();
        } else {
            getSourceForFacilitator();
        }
    }

    private void get_horizon_head_site(String dist_code, String block_code,
                                       String pan_code, String vill_code,
                                       String hab_code, String sTaskId) {
        String sUrl = "https://phed.sunandainternational.org/api/get-horizon-head-site?dist_code="
                + dist_code + "&block_code=" + block_code + "&pan_code=" + pan_code
                + "&vill_code=" + vill_code + "";

        StringRequest postRequest = new StringRequest(Request.Method.GET,
                sUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                get_horizon_head_siteResponse(response, hab_code, sTaskId);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    new AlertDialog.Builder(SyncOnlineData_Facilitator_Activity.this)
                            .setMessage("Downloading error. Please try again")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                } catch (Exception e) {
                    Log.e("SyncOnlineData_", e.getMessage());
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                /*params.put("dist_code", dist_code);
                params.put("block_code", block_code);
                params.put("pan_code", pan_code);
                params.put("vill_code", vill_code);*/
                return CGlobal.getInstance().checkParams(params);
            }
        };
        CGlobal.getInstance().addVolleyRequest(postRequest, false, SyncOnlineData_Facilitator_Activity.this);
    }

    private void get_horizon_head_siteResponse(String response, String sHabCode, String sTaskId) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String resCode = jsonObject.getString("resCode");
            String message = jsonObject.getString("message");
            String error = jsonObject.getString("error");
            if (jsonObject.has("data")) {
                JSONArray dataJSONArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < dataJSONArray.length(); i++) {
                    JSONObject dataJsonObject = dataJSONArray.getJSONObject(i);
                    String _id = isNullNotDefined(dataJsonObject, "_id") ? "" : dataJsonObject.getString("_id");
                    String slNo = isNullNotDefined(dataJsonObject, "slNo") ? "" : dataJsonObject.getString("slNo");
                    String dist_code = isNullNotDefined(dataJsonObject, "dist_code") ? "" : dataJsonObject.getString("dist_code");
                    String dist_name = isNullNotDefined(dataJsonObject, "dist_name") ? "" : dataJsonObject.getString("dist_name");
                    String block_code = isNullNotDefined(dataJsonObject, "block_code") ? "" : dataJsonObject.getString("block_code");
                    String block_name = isNullNotDefined(dataJsonObject, "block_name") ? "" : dataJsonObject.getString("block_name");
                    String pan_code = isNullNotDefined(dataJsonObject, "pan_code") ? "" : dataJsonObject.getString("pan_code");
                    String pan_name = isNullNotDefined(dataJsonObject, "pan_name") ? "" : dataJsonObject.getString("pan_name");
                    String vill_code = isNullNotDefined(dataJsonObject, "vill_code") ? "" : dataJsonObject.getString("vill_code");
                    String vill_name = isNullNotDefined(dataJsonObject, "vill_name") ? "" : dataJsonObject.getString("vill_name");
                    String hab_name = isNullNotDefined(dataJsonObject, "hab_name") ? "" : dataJsonObject.getString("hab_name");
                    String scheme_code = isNullNotDefined(dataJsonObject, "scheme_code") ? "" : dataJsonObject.getString("scheme_code");
                    String Scheme = isNullNotDefined(dataJsonObject, "scheme") ? "" : dataJsonObject.getString("scheme");
                    String latitude = isNullNotDefined(dataJsonObject, "latitude") ? "" : dataJsonObject.getString("latitude");
                    String longitude = isNullNotDefined(dataJsonObject, "longitude") ? "" : dataJsonObject.getString("longitude");
                    String tubewell_site = isNullNotDefined(dataJsonObject, "tubewell_site") ? "" : dataJsonObject.getString("tubewell_site");

                    databaseHandler.addSourceForFacilitator("Routine", "", "",
                            block_code, "", "", tubewell_site, dist_code,
                            hab_name.toUpperCase(), "", "", "",
                            "", slNo, "", "", latitude,
                            tubewell_site, longitude, _id, "", pan_code, "",
                            "", "", Scheme, scheme_code, "", "FHTC",
                            "No", "", "", "",
                            "10:00", "", "RURAL", vill_name.toUpperCase(),
                            "", "PIPED WATER SUPPLY", "",
                            "", "", vill_code, "", "", "",
                            "", "", "", "", "", "", "",
                            "", "", "", "", "",
                            "", fcID, "",
                            "", "", "", sLabId, "",
                            "", "", "",
                            "", "", "",
                            "", "", "",
                            "", "",
                            "", "",
                            "", "",
                            "", "",
                            "", "",
                            "", "",
                            "", "",
                            "", "",
                            "", "", "",
                            "", "", "",
                            "", "", sTaskId, "", "",
                            "", "", "head_site", "0");

                }
            }
        } catch (Exception e) {
            Log.e("SyncOnlineData_", e.getMessage());
        }
    }

    private void get_horizon_source_by_vill(String dist_code, String block_code,
                                            String pan_code, String vill_code,
                                            String hab_code, String sTaskId) {
        String sUrl = "https://phed.sunandainternational.org/api/get-horizon-source-by-vill?dist_code="
                + dist_code + "&block_code=" + block_code + "&pan_code=" + pan_code
                + "&vill_code=" + vill_code + "";

        StringRequest postRequest = new StringRequest(Request.Method.GET,
                sUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                get_horizon_source_by_villResponse(response, hab_code, sTaskId);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    new AlertDialog.Builder(SyncOnlineData_Facilitator_Activity.this)
                            .setMessage("Downloading error. Please try again")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                } catch (Exception e) {
                    Log.e("SyncOnlineData_", e.getMessage());
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return CGlobal.getInstance().checkParams(params);
            }
        };
        CGlobal.getInstance().addVolleyRequest(postRequest, false, SyncOnlineData_Facilitator_Activity.this);
    }

    private void get_horizon_source_by_villResponse(String response, String sHabCode, String sTaskId) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String resCode = jsonObject.getString("resCode");
            String message = jsonObject.getString("message");
            String error = jsonObject.getString("error");
            JSONObject jsonObjectdata = jsonObject.getJSONObject("data");
            String current_page = isNullNotDefined(jsonObjectdata, "current_page") ? "" : jsonObjectdata.getString("current_page");
            if (jsonObjectdata.has("data")) {
                JSONArray dataJSONArray = jsonObjectdata.getJSONArray("data");
                for (int i = 0; i < dataJSONArray.length(); i++) {
                    JSONObject dataJsonObject = dataJSONArray.getJSONObject(i);
                    String _id = isNullNotDefined(dataJsonObject, "_id") ? "" : dataJsonObject.getString("_id");
                    String Unique_Id = isNullNotDefined(dataJsonObject, "Unique_Id") ? "" : dataJsonObject.getString("Unique_Id");
                    String ID = isNullNotDefined(dataJsonObject, "ID") ? "" : dataJsonObject.getString("ID");
                    String ExistingNewhousehold = isNullNotDefined(dataJsonObject, "ExistingNewhousehold") ? "" : dataJsonObject.getString("ExistingNewhousehold");
                    String ExistingNewhouseholdType = isNullNotDefined(dataJsonObject, "ExistingNewhouseholdType") ? "" : dataJsonObject.getString("ExistingNewhouseholdType");
                    String dist_code = isNullNotDefined(dataJsonObject, "dist_code") ? "" : dataJsonObject.getString("dist_code");
                    String dist_name = isNullNotDefined(dataJsonObject, "dist_name") ? "" : dataJsonObject.getString("dist_name");
                    String block_code = isNullNotDefined(dataJsonObject, "block_code") ? "" : dataJsonObject.getString("block_code");
                    String block_name = isNullNotDefined(dataJsonObject, "block_name") ? "" : dataJsonObject.getString("block_name");
                    String pan_code = isNullNotDefined(dataJsonObject, "pan_code") ? "" : dataJsonObject.getString("pan_code");
                    String pan_name = isNullNotDefined(dataJsonObject, "pan_name") ? "" : dataJsonObject.getString("pan_name");
                    String vill_code = isNullNotDefined(dataJsonObject, "vill_code") ? "" : dataJsonObject.getString("vill_code");
                    String vill_name = isNullNotDefined(dataJsonObject, "vill_name") ? "" : dataJsonObject.getString("vill_name");
                    String hab_code = isNullNotDefined(dataJsonObject, "hab_code") ? "" : dataJsonObject.getString("hab_code");
                    String IMIShabCode = isNullNotDefined(dataJsonObject, "IMIShabCode") ? "" : dataJsonObject.getString("IMIShabCode");
                    String hab_name = isNullNotDefined(dataJsonObject, "hab_name") ? "" : dataJsonObject.getString("hab_name");
                    String scheme_code = isNullNotDefined(dataJsonObject, "scheme_code") ? "" : dataJsonObject.getString("scheme_code");
                    String IMISschemeCode = isNullNotDefined(dataJsonObject, "IMISschemeCode") ? "" : dataJsonObject.getString("IMISschemeCode");
                    String Scheme = isNullNotDefined(dataJsonObject, "Scheme") ? "" : dataJsonObject.getString("Scheme");
                    String Latitude = isNullNotDefined(dataJsonObject, "Latitude") ? "" : dataJsonObject.getString("Latitude");
                    String Longitude = isNullNotDefined(dataJsonObject, "Longitude") ? "" : dataJsonObject.getString("Longitude");
                    String Nameofthefamilyhead = isNullNotDefined(dataJsonObject, "Nameofthefamilyhead") ? "" : dataJsonObject.getString("Nameofthefamilyhead");
                    String WaterSourceType = isNullNotDefined(dataJsonObject, "WaterSourceType") ? "" : dataJsonObject.getString("WaterSourceType");
                    String WaterSourceTypeName = isNullNotDefined(dataJsonObject, "WaterSourceTypeName") ? "" : dataJsonObject.getString("WaterSourceTypeName");
                    String Locality = isNullNotDefined(dataJsonObject, "Locality") ? "" : dataJsonObject.getString("Locality");
                    String created_at = isNullNotDefined(dataJsonObject, "created_at") ? "" : dataJsonObject.getString("created_at");
                    String updated_at = isNullNotDefined(dataJsonObject, "updated_at") ? "" : dataJsonObject.getString("updated_at");

                    databaseHandler.addSourceForFacilitator("Routine", "", "",
                            block_code, "", updated_at, Nameofthefamilyhead, dist_code,
                            hab_name.toUpperCase(), "", "", "",
                            "", ID, "", "", Latitude,
                            Nameofthefamilyhead, Longitude, _id, "", pan_code, "",
                            "", "", Scheme, scheme_code, "", "FHTC",
                            "No", "", "", "",
                            "10:00", "", "RURAL", vill_name.toUpperCase(),
                            "", "PIPED WATER SUPPLY", "",
                            "", "", vill_code, hab_code, "", "",
                            "", "", "", "", "", "", "",
                            "", "", "", "", created_at,
                            "", fcID, "",
                            "", "", "", sLabId, "",
                            "", "", "",
                            "", "", "",
                            "", "", "",
                            "", "",
                            "", "",
                            "", "",
                            "", "",
                            "", "",
                            "", "",
                            "", "",
                            "", "",
                            "", "", "",
                            "", "", "",
                            "", "", sTaskId, "", "",
                            "", "", "YES", "0");

                }
            }
        } catch (Exception e) {
            Log.e("SyncOnlineData_", e.getMessage());
        }
    }

    private void getSourceForFacilitator() {
        try {
            progressdialog.show();
        } catch (Exception e) {
            Log.e("SyncOnlineData_", e.getMessage());
        }
        String sUrl = CommonURL.GetSourceForFacilator_URL + "?FCID=" + fcID + "&AppKey=Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3";

        StringRequest postRequest = new StringRequest(Request.Method.GET,
                sUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                getSourceForFacilitatorResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressdialog.dismiss();
                try {
                    new AlertDialog.Builder(SyncOnlineData_Facilitator_Activity.this)
                            .setMessage("Downloading error. Please try again")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                } catch (Exception e) {
                    Log.e("SyncOnlineData_", e.getMessage());
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return CGlobal.getInstance().checkParams(params);
            }
        };
        CGlobal.getInstance().addVolleyRequest(postRequest, false, SyncOnlineData_Facilitator_Activity.this);
    }

    private void getSourceForFacilitatorResponse(String response) {
        try {
            response = response.replaceAll("\r\n", "");
            JSONArray sourceLocalityArray = new JSONArray(response);
            for (int i = 0; i < sourceLocalityArray.length(); i++) {
                JSONObject sLocality_obj = sourceLocalityArray.getJSONObject(i);
                String Accuracy = isNullNotDefined(sLocality_obj, "Accuracy") ? "" : sLocality_obj.getString("Accuracy");
                String AppName = isNullNotDefined(sLocality_obj, "AppName") ? "" : sLocality_obj.getString("AppName");
                String BidDiaTubWellCode = isNullNotDefined(sLocality_obj, "BidDiaTubWellCode") ? "" : sLocality_obj.getString("BidDiaTubWellCode");
                String Block = isNullNotDefined(sLocality_obj, "Block") ? "" : sLocality_obj.getString("Block");
                String ConditionOfSource = isNullNotDefined(sLocality_obj, "ConditionOfSource") ? "" : sLocality_obj.getString("ConditionOfSource");
                String DateofDataCollection = isNullNotDefined(sLocality_obj, "DateofDataCollection") ? "" : sLocality_obj.getString("DateofDataCollection");
                String Descriptionofthelocation = isNullNotDefined(sLocality_obj, "Descriptionofthelocation") ? "" : sLocality_obj.getString("Descriptionofthelocation");
                String District = isNullNotDefined(sLocality_obj, "District") ? "" : sLocality_obj.getString("District");
                String Habitation = isNullNotDefined(sLocality_obj, "Habitation") ? "" : sLocality_obj.getString("Habitation");
                String HandPumpCategory = isNullNotDefined(sLocality_obj, "HandPumpCategory") ? "" : sLocality_obj.getString("HandPumpCategory");
                String HealthFacility = isNullNotDefined(sLocality_obj, "HealthFacility") ? "" : sLocality_obj.getString("HealthFacility");
                String Howmanypipes = isNullNotDefined(sLocality_obj, "Howmanypipes") ? "" : sLocality_obj.getString("Howmanypipes");
                String img_source = isNullNotDefined(sLocality_obj, "img_source") ? "" : sLocality_obj.getString("img_source");
                String interview_id = isNullNotDefined(sLocality_obj, "interview_id") ? "" : sLocality_obj.getString("interview_id");
                String isnewlocation_School_UdiseCode = isNullNotDefined(sLocality_obj, "isnewlocation") ? "" : sLocality_obj.getString("isnewlocation");
                String LabCode = isNullNotDefined(sLocality_obj, "LabCode") ? "" : sLocality_obj.getString("LabCode");
                String Lat = isNullNotDefined(sLocality_obj, "Lat") ? "" : sLocality_obj.getString("Lat");
                String LocationDescription = isNullNotDefined(sLocality_obj, "LocationDescription") ? "" : sLocality_obj.getString("LocationDescription");
                String Long = isNullNotDefined(sLocality_obj, "Long") ? "" : sLocality_obj.getString("Long");
                String MiD = isNullNotDefined(sLocality_obj, "MiD") ? "" : sLocality_obj.getString("MiD");
                String NameofTown = isNullNotDefined(sLocality_obj, "NameofTown") ? "" : sLocality_obj.getString("NameofTown");
                String Panchayat = isNullNotDefined(sLocality_obj, "Panchayat") ? "" : sLocality_obj.getString("Panchayat");
                String Pictureofthesource = isNullNotDefined(sLocality_obj, "Pictureofthesource") ? "" : sLocality_obj.getString("Pictureofthesource");
                String q_18C = isNullNotDefined(sLocality_obj, "q_18C") ? "" : sLocality_obj.getString("q_18C");
                String SampleBottleNumber = isNullNotDefined(sLocality_obj, "SampleBottleNumber") ? "" : sLocality_obj.getString("SampleBottleNumber");
                String Scheme = isNullNotDefined(sLocality_obj, "Scheme") ? "" : sLocality_obj.getString("Scheme");
                String Scheme_Code = isNullNotDefined(sLocality_obj, "Scheme_Code") ? "" : sLocality_obj.getString("Scheme_Code");
                String Sourceselect = isNullNotDefined(sLocality_obj, "Sourceselect") ? "" : sLocality_obj.getString("Sourceselect");
                String SourceSite = isNullNotDefined(sLocality_obj, "SourceSite") ? "" : sLocality_obj.getString("SourceSite");
                String specialdrive = isNullNotDefined(sLocality_obj, "specialdrive") ? "" : sLocality_obj.getString("specialdrive");
                String SpecialdriveName = isNullNotDefined(sLocality_obj, "SpecialdriveName") ? "" : sLocality_obj.getString("SpecialdriveName");
                String sub_scheme_name = isNullNotDefined(sLocality_obj, "sub_scheme_name") ? "" : sLocality_obj.getString("sub_scheme_name");
                String sub_source_type = isNullNotDefined(sLocality_obj, "sub_source_type") ? "" : sLocality_obj.getString("sub_source_type");
                String TimeofDataCollection = isNullNotDefined(sLocality_obj, "TimeofDataCollection") ? "" : sLocality_obj.getString("TimeofDataCollection");
                String TotalDepth = isNullNotDefined(sLocality_obj, "TotalDepth") ? "" : sLocality_obj.getString("TotalDepth");
                String TypeofLocality = isNullNotDefined(sLocality_obj, "TypeofLocality") ? "" : sLocality_obj.getString("TypeofLocality");
                String VillageName = isNullNotDefined(sLocality_obj, "VillageName") ? "" : sLocality_obj.getString("VillageName");
                String WardNumber = isNullNotDefined(sLocality_obj, "WardNumber") ? "" : sLocality_obj.getString("WardNumber");
                String WaterSourceType = isNullNotDefined(sLocality_obj, "WaterSourceType") ? "" : sLocality_obj.getString("WaterSourceType");
                String WhoCollectingSample = isNullNotDefined(sLocality_obj, "WhoCollectingSample") ? "" : sLocality_obj.getString("WhoCollectingSample");
                String ZoneCategory = isNullNotDefined(sLocality_obj, "ZoneCategory") ? "" : sLocality_obj.getString("ZoneCategory");
                String ZoneNumber = isNullNotDefined(sLocality_obj, "ZoneNumber") ? "" : sLocality_obj.getString("ZoneNumber");
                String Village_Code = isNullNotDefined(sLocality_obj, "Village_Code") ? "" : sLocality_obj.getString("Village_Code");
                String Hab_Code = isNullNotDefined(sLocality_obj, "Hab_Code") ? "" : sLocality_obj.getString("Hab_Code");
                String ans_1 = isNullNotDefined(sLocality_obj, "w_s_q_1") ? "" : sLocality_obj.getString("w_s_q_1");
                String ans_2 = isNullNotDefined(sLocality_obj, "w_s_q_2") ? "" : sLocality_obj.getString("w_s_q_2");
                String ans_3 = isNullNotDefined(sLocality_obj, "w_s_q_3") ? "" : sLocality_obj.getString("w_s_q_3");
                String ans_4 = isNullNotDefined(sLocality_obj, "w_s_q_4") ? "" : sLocality_obj.getString("w_s_q_4");
                String ans_5 = isNullNotDefined(sLocality_obj, "w_s_q_5") ? "" : sLocality_obj.getString("w_s_q_5");
                String ans_6 = isNullNotDefined(sLocality_obj, "w_s_q_6") ? "" : sLocality_obj.getString("w_s_q_6");
                String ans_7 = isNullNotDefined(sLocality_obj, "w_s_q_7") ? "" : sLocality_obj.getString("w_s_q_7");
                String ans_8 = isNullNotDefined(sLocality_obj, "w_s_q_8") ? "" : sLocality_obj.getString("w_s_q_8");
                String ans_9 = isNullNotDefined(sLocality_obj, "w_s_q_9") ? "" : sLocality_obj.getString("w_s_q_9");
                String ans_10 = isNullNotDefined(sLocality_obj, "w_s_q_10") ? "" : sLocality_obj.getString("w_s_q_10");
                String ans_11 = isNullNotDefined(sLocality_obj, "w_s_q_11") ? "" : sLocality_obj.getString("w_s_q_11");
                String w_s_q_img = isNullNotDefined(sLocality_obj, "w_s_q_img") ? "" : sLocality_obj.getString("w_s_q_img");
                String img_sanitary = isNullNotDefined(sLocality_obj, "img_sanitary") ? "" : sLocality_obj.getString("img_sanitary");

                String CreatedDate = isNullNotDefined(sLocality_obj, "CreatedDate") ? "" : sLocality_obj.getString("CreatedDate");
                String existing_mid = isNullNotDefined(sLocality_obj, "existing_mid") ? "" : sLocality_obj.getString("existing_mid");
                String FCID = isNullNotDefined(sLocality_obj, "FCID") ? "" : sLocality_obj.getString("FCID");
                String FecilatorCompletedDate = isNullNotDefined(sLocality_obj, "FecilatorCompletedDate") ? "" : sLocality_obj.getString("FecilatorCompletedDate");
                String FormSubmissionDate = isNullNotDefined(sLocality_obj, "FormSubmissionDate") ? "" : sLocality_obj.getString("FormSubmissionDate");
                String HeaderLogID = isNullNotDefined(sLocality_obj, "HeaderLogID") ? "" : sLocality_obj.getString("HeaderLogID");
                String IsDone = isNullNotDefined(sLocality_obj, "IsDone") ? "" : sLocality_obj.getString("IsDone");
                String LabID = isNullNotDefined(sLocality_obj, "LabID") ? "" : sLocality_obj.getString("LabID");
                String LogID = isNullNotDefined(sLocality_obj, "LogID") ? "" : sLocality_obj.getString("LogID");

                String anganwadi_name_q_12b = isNullNotDefined(sLocality_obj, "q_12b") ? "" : sLocality_obj.getString("q_12b");
                String anganwadi_code_q_12c = isNullNotDefined(sLocality_obj, "q_12c") ? "" : sLocality_obj.getString("q_12c");
                String anganwadi_sectorcode_q_12d = isNullNotDefined(sLocality_obj, "q_12d") ? "" : sLocality_obj.getString("q_12d");
                String Standpostsituated_q_13e = isNullNotDefined(sLocality_obj, "q_13e") ? "" : sLocality_obj.getString("q_13e");
                String SchoolManagement_q_si_1 = isNullNotDefined(sLocality_obj, "q_si_1") ? "" : sLocality_obj.getString("q_si_1");
                String SchoolCategory_q_si_2 = isNullNotDefined(sLocality_obj, "q_si_2") ? "" : sLocality_obj.getString("q_si_2");
                String SchoolType_q_si_3 = isNullNotDefined(sLocality_obj, "q_si_3") ? "" : sLocality_obj.getString("q_si_3");
                String Noofstudentsintheschool_q_si_4 = isNullNotDefined(sLocality_obj, "q_si_4") ? "" : sLocality_obj.getString("q_si_4");
                String Noofboysintheschool_q_si_5 = isNullNotDefined(sLocality_obj, "q_si_5") ? "" : sLocality_obj.getString("q_si_5");
                String Noofgirlsintheschool_q_si_6 = isNullNotDefined(sLocality_obj, "q_si_6") ? "" : sLocality_obj.getString("q_si_6");
                String AvailabilityofElectricityinSchool_q_si_7 = isNullNotDefined(sLocality_obj, "q_si_7") ? "" : sLocality_obj.getString("q_si_7");
                String Isdistributionofwaterbeing_q_si_8 = isNullNotDefined(sLocality_obj, "q_si_8") ? "" : sLocality_obj.getString("q_si_8");
                String Anganwadiaccomodation_q_si_9 = isNullNotDefined(sLocality_obj, "q_si_9") ? "" : sLocality_obj.getString("q_si_9");

                String watersourcebeentestedbefore_q_w_1 = isNullNotDefined(sLocality_obj, "q_w_1") ? "" : sLocality_obj.getString("q_w_1");
                String Whenwaterlasttested_q_w_1a = isNullNotDefined(sLocality_obj, "q_w_1a") ? "" : sLocality_obj.getString("q_w_1a");
                String Istestreportsharedschoolauthority_q_w_1b = isNullNotDefined(sLocality_obj, "q_w_1b") ? "" : sLocality_obj.getString("q_w_1b");
                String foundtobebacteriologically_q_w_1c = isNullNotDefined(sLocality_obj, "q_w_1c") ? "" : sLocality_obj.getString("q_w_1c");
                String Istoiletfacilityavailable_q_w_2 = isNullNotDefined(sLocality_obj, "q_w_2") ? "" : sLocality_obj.getString("q_w_2");
                String Isrunningwateravailable_q_w_2a = isNullNotDefined(sLocality_obj, "q_w_2a") ? "" : sLocality_obj.getString("q_w_2a");
                String separatetoiletsforboysandgirls_q_w_2b = isNullNotDefined(sLocality_obj, "q_w_2b") ? "" : sLocality_obj.getString("q_w_2b");
                String NumberoftoiletforBoys_q_w_2b_a = isNullNotDefined(sLocality_obj, "q_w_2b_a") ? "" : sLocality_obj.getString("q_w_2b_a");
                String NumberoftoiletforGirl_q_w_2b_b = isNullNotDefined(sLocality_obj, "q_w_2b_b") ? "" : sLocality_obj.getString("q_w_2b_b");
                String Numberofgeneraltoilet_q_w_2b_c = isNullNotDefined(sLocality_obj, "q_w_2b_c") ? "" : sLocality_obj.getString("q_w_2b_c");
                String Isseparatetoiletforteachers_q_w_2c = isNullNotDefined(sLocality_obj, "q_w_2c") ? "" : sLocality_obj.getString("q_w_2c");
                String Numberoftoiletforteachers_q_w_2c_a = isNullNotDefined(sLocality_obj, "q_w_2c_a") ? "" : sLocality_obj.getString("q_w_2c_a");
                String ImageofToilet_q_w_2d = isNullNotDefined(sLocality_obj, "q_w_2d") ? "" : sLocality_obj.getString("q_w_2d");
                String Ishandwashingfacility_q_w_3 = isNullNotDefined(sLocality_obj, "q_w_3") ? "" : sLocality_obj.getString("q_w_3");
                String Isrunningwateravailable_q_w_3a = isNullNotDefined(sLocality_obj, "q_w_3a") ? "" : sLocality_obj.getString("q_w_3a");
                String Isthewashbasinwithin_q_w_3b = isNullNotDefined(sLocality_obj, "q_w_3b") ? "" : sLocality_obj.getString("q_w_3b");
                String ImageofWashBasin_q_w_3c = isNullNotDefined(sLocality_obj, "q_w_3c") ? "" : sLocality_obj.getString("q_w_3c");
                String IswaterinKitchen_q_w_4 = isNullNotDefined(sLocality_obj, "q_w_4") ? "" : sLocality_obj.getString("q_w_4");
                String Remarks = isNullNotDefined(sLocality_obj, "Remarks") ? "" : sLocality_obj.getString("Remarks");
                String sampleCollectorId = isNullNotDefined(sLocality_obj, "sampleCollectorId") ? "" : sLocality_obj.getString("sampleCollectorId");
                String Task_Id = isNullNotDefined(sLocality_obj, "Task_Id") ? "" : sLocality_obj.getString("Task_Id");
                String TestCompletedDate = isNullNotDefined(sLocality_obj, "TestCompletedDate") ? "" : sLocality_obj.getString("TestCompletedDate");
                String TestTime = isNullNotDefined(sLocality_obj, "TestTime") ? "" : sLocality_obj.getString("TestTime");
                String OtherSchoolName = isNullNotDefined(sLocality_obj, "OtherSchoolName") ? "" : sLocality_obj.getString("OtherSchoolName");
                String OtherAnganwadiName = isNullNotDefined(sLocality_obj, "OtherAnganwadiName") ? "" : sLocality_obj.getString("OtherAnganwadiName");

                if (AppName.equalsIgnoreCase("RT")) {

                    databaseHandler.addSourceForFacilitator("Routine", Accuracy, BidDiaTubWellCode, Block,
                            ConditionOfSource, DateofDataCollection,
                            Descriptionofthelocation, District, Habitation.toUpperCase(), HandPumpCategory, HealthFacility,
                            Howmanypipes, img_source, interview_id, isnewlocation_School_UdiseCode, LabCode,
                            Lat, LocationDescription, Long, MiD, NameofTown, Panchayat,
                            Pictureofthesource, q_18C, SampleBottleNumber, Scheme, Scheme_Code, Sourceselect,
                            SourceSite, specialdrive, SpecialdriveName, sub_scheme_name, sub_source_type,
                            TimeofDataCollection, TotalDepth, TypeofLocality, VillageName.toUpperCase(), WardNumber,
                            WaterSourceType, WhoCollectingSample, ZoneCategory, ZoneNumber, Village_Code,
                            Hab_Code, ans_1, ans_2, ans_3, ans_4, ans_5, ans_6, ans_7,
                            ans_8, ans_9, ans_10, ans_11, w_s_q_img, img_sanitary,
                            CreatedDate, existing_mid, FCID, FecilatorCompletedDate,
                            FormSubmissionDate, HeaderLogID, IsDone, LabID, LogID,
                            anganwadi_name_q_12b, anganwadi_code_q_12c, anganwadi_sectorcode_q_12d,
                            Standpostsituated_q_13e, SchoolManagement_q_si_1, SchoolCategory_q_si_2,
                            SchoolType_q_si_3, Noofstudentsintheschool_q_si_4, Noofboysintheschool_q_si_5,
                            Noofgirlsintheschool_q_si_6, AvailabilityofElectricityinSchool_q_si_7,
                            Isdistributionofwaterbeing_q_si_8, Anganwadiaccomodation_q_si_9,
                            watersourcebeentestedbefore_q_w_1, Whenwaterlasttested_q_w_1a,
                            Istestreportsharedschoolauthority_q_w_1b, foundtobebacteriologically_q_w_1c,
                            Istoiletfacilityavailable_q_w_2, Isrunningwateravailable_q_w_2a,
                            separatetoiletsforboysandgirls_q_w_2b, NumberoftoiletforBoys_q_w_2b_a,
                            NumberoftoiletforGirl_q_w_2b_b, Numberofgeneraltoilet_q_w_2b_c,
                            Isseparatetoiletforteachers_q_w_2c, Numberoftoiletforteachers_q_w_2c_a,
                            ImageofToilet_q_w_2d, Ishandwashingfacility_q_w_3, Isrunningwateravailable_q_w_3a,
                            Isthewashbasinwithin_q_w_3b, ImageofWashBasin_q_w_3c, IswaterinKitchen_q_w_4,
                            Remarks, sampleCollectorId, Task_Id, TestCompletedDate, TestTime,
                            OtherSchoolName, OtherAnganwadiName, "NO", "0");

                } else if (AppName.equalsIgnoreCase("SC")) {

                    databaseHandler.addSourceForFacilitator("School", Accuracy, BidDiaTubWellCode, Block, ConditionOfSource, DateofDataCollection,
                            Descriptionofthelocation, District, Habitation.toUpperCase(), HandPumpCategory, HealthFacility,
                            Howmanypipes, img_source, interview_id, isnewlocation_School_UdiseCode, LabCode,
                            Lat, LocationDescription, Long, MiD, NameofTown, Panchayat,
                            Pictureofthesource, q_18C, SampleBottleNumber, Scheme, Scheme_Code, Sourceselect,
                            SourceSite, specialdrive, SpecialdriveName, sub_scheme_name, sub_source_type,
                            TimeofDataCollection, TotalDepth, TypeofLocality, VillageName.toUpperCase(), WardNumber,
                            WaterSourceType, WhoCollectingSample, ZoneCategory, ZoneNumber, Village_Code,
                            Hab_Code, ans_1, ans_2, ans_3, ans_4, ans_5, ans_6, ans_7,
                            ans_8, ans_9, ans_10, ans_11, w_s_q_img, img_sanitary,
                            CreatedDate, existing_mid, FCID, FecilatorCompletedDate,
                            FormSubmissionDate, HeaderLogID, IsDone, LabID, LogID,
                            anganwadi_name_q_12b, anganwadi_code_q_12c, anganwadi_sectorcode_q_12d,
                            Standpostsituated_q_13e, SchoolManagement_q_si_1, SchoolCategory_q_si_2,
                            SchoolType_q_si_3, Noofstudentsintheschool_q_si_4, Noofboysintheschool_q_si_5,
                            Noofgirlsintheschool_q_si_6, AvailabilityofElectricityinSchool_q_si_7,
                            Isdistributionofwaterbeing_q_si_8, Anganwadiaccomodation_q_si_9,
                            watersourcebeentestedbefore_q_w_1, Whenwaterlasttested_q_w_1a,
                            Istestreportsharedschoolauthority_q_w_1b, foundtobebacteriologically_q_w_1c,
                            Istoiletfacilityavailable_q_w_2, Isrunningwateravailable_q_w_2a,
                            separatetoiletsforboysandgirls_q_w_2b, NumberoftoiletforBoys_q_w_2b_a,
                            NumberoftoiletforGirl_q_w_2b_b, Numberofgeneraltoilet_q_w_2b_c,
                            Isseparatetoiletforteachers_q_w_2c, Numberoftoiletforteachers_q_w_2c_a,
                            ImageofToilet_q_w_2d, Ishandwashingfacility_q_w_3, Isrunningwateravailable_q_w_3a,
                            Isthewashbasinwithin_q_w_3b, ImageofWashBasin_q_w_3c, IswaterinKitchen_q_w_4,
                            Remarks, sampleCollectorId, Task_Id, TestCompletedDate, TestTime,
                            OtherSchoolName, OtherAnganwadiName, "NO", "0");

                } else if (AppName.equalsIgnoreCase("OM")) {

                    databaseHandler.addSourceForFacilitator("OMAS", Accuracy, BidDiaTubWellCode, Block, ConditionOfSource, DateofDataCollection,
                            Descriptionofthelocation, District, Habitation.toUpperCase(), HandPumpCategory, HealthFacility,
                            Howmanypipes, img_source, interview_id, isnewlocation_School_UdiseCode, LabCode,
                            Lat, LocationDescription, Long, MiD, NameofTown, Panchayat,
                            Pictureofthesource, q_18C, SampleBottleNumber, Scheme, Scheme_Code, Sourceselect,
                            SourceSite, specialdrive, SpecialdriveName, sub_scheme_name, sub_source_type,
                            TimeofDataCollection, TotalDepth, TypeofLocality, VillageName.toUpperCase(), WardNumber,
                            WaterSourceType, WhoCollectingSample, ZoneCategory, ZoneNumber, Village_Code,
                            Hab_Code, ans_1, ans_2, ans_3, ans_4, ans_5, ans_6, ans_7,
                            ans_8, ans_9, ans_10, ans_11, w_s_q_img, img_sanitary,
                            CreatedDate, existing_mid, FCID, FecilatorCompletedDate,
                            FormSubmissionDate, HeaderLogID, IsDone, LabID, LogID,
                            anganwadi_name_q_12b, anganwadi_code_q_12c, anganwadi_sectorcode_q_12d,
                            Standpostsituated_q_13e, SchoolManagement_q_si_1, SchoolCategory_q_si_2,
                            SchoolType_q_si_3, Noofstudentsintheschool_q_si_4, Noofboysintheschool_q_si_5,
                            Noofgirlsintheschool_q_si_6, AvailabilityofElectricityinSchool_q_si_7,
                            Isdistributionofwaterbeing_q_si_8, Anganwadiaccomodation_q_si_9,
                            watersourcebeentestedbefore_q_w_1, Whenwaterlasttested_q_w_1a,
                            Istestreportsharedschoolauthority_q_w_1b, foundtobebacteriologically_q_w_1c,
                            Istoiletfacilityavailable_q_w_2, Isrunningwateravailable_q_w_2a,
                            separatetoiletsforboysandgirls_q_w_2b, NumberoftoiletforBoys_q_w_2b_a,
                            NumberoftoiletforGirl_q_w_2b_b, Numberofgeneraltoilet_q_w_2b_c,
                            Isseparatetoiletforteachers_q_w_2c, Numberoftoiletforteachers_q_w_2c_a,
                            ImageofToilet_q_w_2d, Ishandwashingfacility_q_w_3, Isrunningwateravailable_q_w_3a,
                            Isthewashbasinwithin_q_w_3b, ImageofWashBasin_q_w_3c, IswaterinKitchen_q_w_4,
                            Remarks, sampleCollectorId, Task_Id, TestCompletedDate, TestTime,
                            OtherSchoolName, OtherAnganwadiName, "NO", "0");

                }

            }
        } catch (Exception e) {
            Log.e("SyncOnlineData_", e.getMessage());
        }
        progressdialog.dismiss();
        getSourceSite();
    }

    private void getSourceSite() {
        try {
            progressdialog.show();
        } catch (Exception e) {
            Log.e("SyncOnlineData_", e.getMessage());
        }
        String sUrl = CommonURL.getSourceSiteMaster_URL + "?FCID=" + fcID + "&AppKey=Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3";

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
                    new AlertDialog.Builder(SyncOnlineData_Facilitator_Activity.this)
                            .setMessage("Downloading error. Please try again")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                } catch (Exception e) {
                    Log.e("SyncOnlineData_", e.getMessage());
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return CGlobal.getInstance().checkParams(params);
            }
        };
        CGlobal.getInstance().addVolleyRequest(postRequest, false, SyncOnlineData_Facilitator_Activity.this);
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

                databaseHandler.addSourceSite(sID, sName, "R");

            }
        } catch (Exception e) {
            Log.e("SyncOnlineData_", e.getMessage());
        }
        progressdialog.dismiss();
        getSourceType();
    }

    private void getSourceType() {
        try {
            progressdialog.show();
        } catch (Exception e) {
            Log.e("SyncOnlineData_", e.getMessage());
        }
        String sUrl = CommonURL.GetSourceTypeMaster_URL + "?FCID=" + fcID + "&AppKey=Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3&xtype=R";

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
                    new AlertDialog.Builder(SyncOnlineData_Facilitator_Activity.this)
                            .setMessage("Downloading error. Please try again")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                } catch (Exception e) {
                    Log.e("SyncOnlineData_", e.getMessage());
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return CGlobal.getInstance().checkParams(params);
            }
        };
        CGlobal.getInstance().addVolleyRequest(postRequest, false, SyncOnlineData_Facilitator_Activity.this);
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
            Log.e("SyncOnlineData_", e.getMessage());
        }
        progressdialog.dismiss();
        getChildSourceType();
    }

    private void getChildSourceType() {
        try {
            progressdialog.show();
        } catch (Exception e) {
            Log.e("SyncOnlineData_", e.getMessage());
        }
        String sUrl = CommonURL.GetChildSourceTypeMaster_URL + "?FCID=" + fcID + "&AppKey=Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3";

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
                    new AlertDialog.Builder(SyncOnlineData_Facilitator_Activity.this)
                            .setMessage("Downloading error. Please try again")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                } catch (Exception e) {
                    Log.e("SyncOnlineData_", e.getMessage());
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return CGlobal.getInstance().checkParams(params);
            }
        };
        CGlobal.getInstance().addVolleyRequest(postRequest, false, SyncOnlineData_Facilitator_Activity.this);
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
            Log.e("SyncOnlineData_", e.getMessage());
        }
        progressdialog.dismiss();
        getSpecialDrive();
    }

    private void getSpecialDrive() {
        try {
            progressdialog.show();
        } catch (Exception e) {
            Log.e("SyncOnlineData_", e.getMessage());
        }
        String sUrl = CommonURL.GetSpecialDriveMaster_URL + "?appname=r&FCID=" + fcID + "&AppKey=Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3";

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
                    new AlertDialog.Builder(SyncOnlineData_Facilitator_Activity.this)
                            .setMessage("Downloading error. Please try again")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                } catch (Exception e) {
                    Log.e("SyncOnlineData_", e.getMessage());
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return CGlobal.getInstance().checkParams(params);
            }
        };
        CGlobal.getInstance().addVolleyRequest(postRequest, false, SyncOnlineData_Facilitator_Activity.this);
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
            Log.e("SyncOnlineData_", e.getMessage());
        }
        progressdialog.dismiss();
        getTown();
    }

    private void getTown() {
        try {
            progressdialog.show();
        } catch (Exception e) {
            Log.e("SyncOnlineData_", e.getMessage());
        }
        String sUrl = CommonURL.GetTownMaster_URL + "?DistrictID=" + sDistrictId + "&FCID=" + fcID + "&AppKey=Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3";

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
                    new AlertDialog.Builder(SyncOnlineData_Facilitator_Activity.this)
                            .setMessage("Downloading error. Please try again")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                } catch (Exception e) {
                    Log.e("SyncOnlineData_", e.getMessage());
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return CGlobal.getInstance().checkParams(params);
            }
        };
        CGlobal.getInstance().addVolleyRequest(postRequest, false, SyncOnlineData_Facilitator_Activity.this);
    }

    private void getTownResponse(String response) {
        try {
            Log.d("TOWN", response);
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
            Log.e("SyncOnlineData_", e.getMessage());
        }
        progressdialog.dismiss();
        getHealthFacility();
    }

    private void getHealthFacility() {
        try {
            progressdialog.show();
        } catch (Exception e) {
            Log.e("SyncOnlineDataError", e.getMessage());
        }
        String sUrl = CommonURL.GetHealthFacilityMaster_URL + "?DistrictID=" + sDistrictId + "&FCID=" + fcID + "&AppKey=Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3";

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
                    Log.e("SyncOnlineDataError", error.getMessage());
                    new AlertDialog.Builder(SyncOnlineData_Facilitator_Activity.this)
                            .setMessage("Downloading error. Please try again")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                } catch (Exception e) {
                    Log.e("SyncOnlineDataError", e.getMessage());
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return CGlobal.getInstance().checkParams(params);
            }
        };
        CGlobal.getInstance().addVolleyRequest(postRequest, false, SyncOnlineData_Facilitator_Activity.this);
    }

    private void getHealthFacilityResponse(String response) {
        try {
            databaseHandler.deleteHealthFacility();
            response = response.replaceAll("\r\n", "");
            JSONArray sourceLocalityArray = new JSONArray(response);
            for (int i = 0; i < sourceLocalityArray.length(); i++) {
                JSONObject sLocality_obj = sourceLocalityArray.getJSONObject(i);
                String sID = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "ID") ? "" : sLocality_obj.getString("ID");
                String sDistrictID = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "DistrictID") ? "" : sLocality_obj.getString("DistrictID");
                String sDistrictCode = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "DistrictCode") ? "" : sLocality_obj.getString("DistrictCode");
                String sDistrictName = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "DistrictName") ? "" : sLocality_obj.getString("DistrictName");
                String sBlockId = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "BlockId") ? "" : sLocality_obj.getString("BlockId");
                String sBlockCode = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "BlockCode") ? "" : sLocality_obj.getString("BlockCode");
                String sBlockName = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "BlockName") ? "" : sLocality_obj.getString("BlockName");
                String health_facility_name = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "health_facility_name") ? "" : sLocality_obj.getString("health_facility_name");

                databaseHandler.addHealthFacility(sID, sDistrictName, sDistrictCode, sDistrictID, sBlockName,
                        sBlockCode, sBlockId, "", "", "", health_facility_name);
            }
        } catch (Exception e) {
            Log.e("SyncOnlineData_Error", e.getMessage());
        }
        progressdialog.dismiss();
        getPipedWaterSupplyScheme();
    }

    private void getPipedWaterSupplyScheme() {
        try {
            progressdialog.show();
        } catch (Exception e) {
            Log.e("SyncOnlineData_", e.getMessage());
        }
        String sUrl = CommonURL.GetPipedWaterSupplyScheme_URL + "?DistrictID=" + sDistrictId + "&FCID=" + fcID + "&AppKey=Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3";

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
                    new AlertDialog.Builder(SyncOnlineData_Facilitator_Activity.this)
                            .setMessage("Downloading error. Please try again")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                } catch (Exception e) {
                    Log.e("SyncOnlineData_", e.getMessage());
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return CGlobal.getInstance().checkParams(params);
            }
        };
        CGlobal.getInstance().addVolleyRequest(postRequest, false, SyncOnlineData_Facilitator_Activity.this);
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
            Log.e("SyncOnlineData_", e.getMessage());
        }
        progressdialog.dismiss();
        getLab();
    }

    private void getLab() {
        try {
            progressdialog.show();
        } catch (Exception e) {
            Log.e("SyncOnlineData_", e.getMessage());
        }
        String sUrl = CommonURL.GetLabMaster_URL + "?DistrictID=" + sDistrictId + "&FCID=" + fcID + "&AppKey=Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3";

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
                    new AlertDialog.Builder(SyncOnlineData_Facilitator_Activity.this)
                            .setMessage("Downloading error. Please try again")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                } catch (Exception e) {
                    Log.e("SyncOnlineData_", e.getMessage());
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return CGlobal.getInstance().checkParams(params);
            }
        };
        CGlobal.getInstance().addVolleyRequest(postRequest, false, SyncOnlineData_Facilitator_Activity.this);
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
            Log.e("SyncOnlineData_", e.getMessage());
        }
        progressdialog.dismiss();
        //getRoster();
        getArsenic();
    }

    private void getRoster() {
        try {
            progressdialog.show();
        } catch (Exception e) {
            Log.e("SyncOnlineData_", e.getMessage());
        }
        String sUrl = CommonURL.GetRosterData_URL + "?LabID=" + sLabId + "&FCID=" + fcID + "&AppKey=Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3";

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
                    new AlertDialog.Builder(SyncOnlineData_Facilitator_Activity.this)
                            .setMessage("Downloading error. Please try again")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                } catch (Exception e) {
                    Log.e("SyncOnlineData_", e.getMessage());
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return CGlobal.getInstance().checkParams(params);
            }
        };
        CGlobal.getInstance().addVolleyRequest(postRequest, false, SyncOnlineData_Facilitator_Activity.this);
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
            Log.e("SyncOnlineData_", e.getMessage());
        }
        progressdialog.dismiss();
        getArsenic();
    }

    private void getArsenic() {
        try {
            progressdialog.show();
        } catch (Exception e) {
            Log.e("SyncOnlineData_", e.getMessage());
        }
        String sUrl = CommonURL.GetArsenicTrendStationSourceData_URL + "?LabID=" + sLabId + "&FCID=" + fcID + "&AppKey=Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3";

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
                    new AlertDialog.Builder(SyncOnlineData_Facilitator_Activity.this)
                            .setMessage("Downloading error. Please try again")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                } catch (Exception e) {
                    Log.e("SyncOnlineData_", e.getMessage());
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return CGlobal.getInstance().checkParams(params);
            }
        };
        CGlobal.getInstance().addVolleyRequest(postRequest, false, SyncOnlineData_Facilitator_Activity.this);
    }

    private void getArsenicResponse(String response) {
        try {
            databaseHandler.deleteArsenic();
            response = response.replaceAll("\r\n", "");
            JSONArray sourceLocalityArray = new JSONArray(response);
            for (int i = 0; i < sourceLocalityArray.length(); i++) {
                JSONObject sLocality_obj = sourceLocalityArray.getJSONObject(i);
                String sID = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "ID") ? "" : sLocality_obj.getString("ID");
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
                String villcode = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "villcode") ? "" : sLocality_obj.getString("villcode");
                String habcode = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "habcode") ? "" : sLocality_obj.getString("habcode");

                databaseHandler.addArsenic(sID, sDistrict, sLaboratory, sBlock, sPanchayat, sVillage, sHabitation, sLocation,
                        districtcode, blockcode, pancode, districtid, blockid, panid, villcode, habcode);
            }
        } catch (Exception e) {
            Log.e("SyncOnlineData_", e.getMessage());
        }
        progressdialog.dismiss();
        getSourceSiteSchool();
    }


    private void getSourceSiteSchool() {
        try {
            progressdialog.show();
        } catch (Exception e) {
            Log.e("SyncOnlineData_", e.getMessage());
        }
        String sUrl = CommonURL.getSourceSiteMasterForSchool_URL + "?FCID=" + fcID + "&AppKey=Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3";

        StringRequest postRequest = new StringRequest(Request.Method.GET,
                sUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                getSourceSiteSchoolResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressdialog.dismiss();
                try {
                    new AlertDialog.Builder(SyncOnlineData_Facilitator_Activity.this)
                            .setMessage("Downloading error. Please try again")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                } catch (Exception e) {
                    Log.e("SyncOnlineData_", e.getMessage());
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return CGlobal.getInstance().checkParams(params);
            }
        };
        CGlobal.getInstance().addVolleyRequest(postRequest, false, SyncOnlineData_Facilitator_Activity.this);
    }

    private void getSourceSiteSchoolResponse(String response) {
        try {
            response = response.replaceAll("\r\n", "");
            JSONArray sourceLocalityArray = new JSONArray(response);
            for (int i = 0; i < sourceLocalityArray.length(); i++) {
                JSONObject sLocality_obj = sourceLocalityArray.getJSONObject(i);
                String sID = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "ID") ? "" : sLocality_obj.getString("ID");
                String sName = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "Name") ? "" : sLocality_obj.getString("Name");

                databaseHandler.addSourceSite(sID, sName, "S");

            }
        } catch (Exception e) {
            Log.e("SyncOnlineData_", e.getMessage());
        }
        progressdialog.dismiss();
        getSchoolDataSheet();
    }

    private void getSchoolDataSheet() {
        try {
            progressdialog.show();
        } catch (Exception e) {
            Log.e("SyncOnlineData_", e.getMessage());
        }

        String sUrl = CommonURL.GetSchoolDataSheet_URL + "?DistrictID=" + sDistrictId + "&LabID=" + sLabId + "&FCID=" + fcID + "&AppKey=Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3";

        StringRequest postRequest = new StringRequest(Request.Method.GET,
                sUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                getSchoolDataSheetResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressdialog.dismiss();
                try {
                    new AlertDialog.Builder(SyncOnlineData_Facilitator_Activity.this)
                            .setMessage("Downloading error. Please try again")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                } catch (Exception e) {
                    Log.e("SyncOnlineData_", e.getMessage());
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return CGlobal.getInstance().checkParams(params);
            }
        };
        CGlobal.getInstance().addVolleyRequest(postRequest, false, SyncOnlineData_Facilitator_Activity.this);
    }

    private void getSchoolDataSheetResponse(String response) {
        try {
            databaseHandler.deleteSchoolDataSheet();
            response = response.replaceAll("\r\n", "");
            JSONArray sourceLocalityArray = new JSONArray(response);
            for (int i = 0; i < sourceLocalityArray.length(); i++) {
                JSONObject sLocality_obj = sourceLocalityArray.getJSONObject(i);
                String id = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "id") ? "" : sLocality_obj.getString("id");
                String dist_name = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "dist_name") ? "" : sLocality_obj.getString("dist_name");
                String dist_code = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "dist_code") ? "" : sLocality_obj.getString("dist_code");
                String locality = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "locality") ? "" : sLocality_obj.getString("locality");
                String block_name = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "block_name") ? "" : sLocality_obj.getString("block_name");
                String block_code = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "block_code") ? "" : sLocality_obj.getString("block_code");
                String pan_name = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "pan_name") ? "" : sLocality_obj.getString("pan_name");
                String pan_code = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "pan_code") ? "" : sLocality_obj.getString("pan_code");
                String school_mamangement_code = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "school_mamangement_code") ? "" : sLocality_obj.getString("school_mamangement_code");
                String school_management = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "school_management") ? "" : sLocality_obj.getString("school_management");
                String school_category_code = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "school_category_code") ? "" : sLocality_obj.getString("school_category_code");
                String school_category = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "school_category") ? "" : sLocality_obj.getString("school_category");
                String udise_code = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "udise_code") ? "" : sLocality_obj.getString("udise_code");
                String school_name = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "school_name") ? "" : sLocality_obj.getString("school_name");
                String school_type_code = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "school_type_code") ? "" : sLocality_obj.getString("school_type_code");
                String school_type = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "school_type") ? "" : sLocality_obj.getString("school_type");
                String sDistrictID = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "DistrictID") ? "" : sLocality_obj.getString("DistrictID");
                String sCityID = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "CityID") ? "" : sLocality_obj.getString("CityID");
                String sPanchayatid = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "panchayatid") ? "" : sLocality_obj.getString("panchayatid");

                databaseHandler.addSchoolDataSheet(id, dist_name, dist_code, locality, block_name, block_code, pan_name,
                        pan_code, school_mamangement_code, school_management, school_category_code,
                        school_category, udise_code, school_name, school_type_code, school_type,
                        sDistrictID, sCityID, sPanchayatid);

            }
        } catch (Exception e) {
            Log.e("SyncOnlineData_", e.getMessage());
        }
        progressdialog.dismiss();
        getAwsDataSourceMaster();
    }

    private void getAwsDataSourceMaster() {
        try {
            progressdialog.show();
        } catch (Exception e) {
            Log.e("SyncOnlineData_", e.getMessage());
        }

        String sUrl = CommonURL.GetAWSDataSourceMaster_1_URL + "?DistrictID=" + sDistrictId + "&LabID=" + sLabId + "&FCID=" + fcID + "&AppKey=Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3";

        StringRequest postRequest = new StringRequest(Request.Method.GET,
                sUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                getAwsDataSourceMasterResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressdialog.dismiss();
                try {
                    new AlertDialog.Builder(SyncOnlineData_Facilitator_Activity.this)
                            .setMessage("Downloading error. Please try again")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                } catch (Exception e) {
                    Log.e("SyncOnlineData_", e.getMessage());
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return CGlobal.getInstance().checkParams(params);
            }
        };
        CGlobal.getInstance().addVolleyRequest(postRequest, false, SyncOnlineData_Facilitator_Activity.this);
    }

    private void getAwsDataSourceMasterResponse(String response) {
        try {
            databaseHandler.deleteAwsDataSourceMaster();
            response = response.replaceAll("\r\n", "");
            JSONArray sourceLocalityArray = new JSONArray(response);
            for (int i = 0; i < sourceLocalityArray.length(); i++) {
                JSONObject sLocality_obj = sourceLocalityArray.getJSONObject(i);
                String sDistrictCode = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "DistrictCode") ? "" : sLocality_obj.getString("DistrictCode");
                String sDistrictName = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "DistrictName") ? "" : sLocality_obj.getString("DistrictName");
                String sLocality = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "Locality") ? "" : sLocality_obj.getString("Locality");
                String sBlockCode = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "BlockCode") ? "" : sLocality_obj.getString("BlockCode");
                String sBlockName = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "BlockName") ? "" : sLocality_obj.getString("BlockName");
                String sPanCode = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "PanCode") ? "" : sLocality_obj.getString("PanCode");
                String sPanName = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "PanName") ? "" : sLocality_obj.getString("PanName");
                String sTownName = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "Town") ? "" : sLocality_obj.getString("Town");
                String sTownCode = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "TownCode") ? "" : sLocality_obj.getString("TownCode");
                String sICDSProjectCode = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "ICDSProjectCode") ? "" : sLocality_obj.getString("ICDSProjectCode");
                String sICDSProjectName = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "ICDSProjectName") ? "" : sLocality_obj.getString("ICDSProjectName");
                String sSectorCode = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "SectorCode") ? "" : sLocality_obj.getString("SectorCode");
                String sSectorName = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "SectorName") ? "" : sLocality_obj.getString("SectorName");
                String sAWCCode = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "AWCCode") ? "" : sLocality_obj.getString("AWCCode");
                String sAWCName = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "AWCName") ? "" : sLocality_obj.getString("AWCName");
                String sLatitude = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "Latitude") ? "" : sLocality_obj.getString("Latitude");
                String sLongitude = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "Longitude") ? "" : sLocality_obj.getString("Longitude");
                String sLocationStatus = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "LocationStatus") ? "" : sLocality_obj.getString("LocationStatus");
                String sSchemeName = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "SchemeName") ? "" : sLocality_obj.getString("SchemeName");
                String sMouzaName = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "MouzaName") ? "" : sLocality_obj.getString("MouzaName");
                //String sGPName = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "GPName") ? "" : sLocality_obj.getString("GPName");
                String sDistrictID = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "DistrictID") ? "" : sLocality_obj.getString("DistrictID");
                String sBlockID = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "BlockID") ? "" : sLocality_obj.getString("BlockID");
                String sPanchayetID = CGlobal.getInstance().isNullNotDefined(sLocality_obj, "PanchayetID") ? "" : sLocality_obj.getString("PanchayetID");

                sAWCName = sAWCName.replace("'", "");

                databaseHandler.addAwsDataSourceMaster(sDistrictCode, sDistrictName, sLocality, sBlockCode, sBlockName, sPanCode, sPanName, sTownName, sTownCode,
                        sICDSProjectCode, sICDSProjectName, sSectorCode, sSectorName, sAWCCode, sAWCName, sLatitude, sLongitude, sLocationStatus,
                        sSchemeName, sMouzaName, "", sDistrictID, sBlockID, sPanchayetID);

            }
        } catch (Exception e) {
            Log.e("SyncOnlineData_", e.getMessage());
        }
        progressdialog.dismiss();
        getResidualChlorineResult();
    }

    private void getResidualChlorineResult() {
        try {
            databaseHandler.deleteResidualChlorineResult();
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray m_jArry = obj.getJSONArray("ResidualChlorine");

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                String sChlorine_Value = jo_inside.getString("Chlorine_Value");
                String sCombined_Chlorine_Value = jo_inside.getString("Combined_Chlorine_Value");
                String sResult = jo_inside.getString("Result");
                String sResultDescription = jo_inside.getString("ResultDescription");
                int ID = jo_inside.getInt("ID");

                databaseHandler.addResidualChlorineResult(ID, sChlorine_Value, sCombined_Chlorine_Value,
                        sResult, sResultDescription);
            }
        } catch (Exception e) {
            Log.e("SyncOnlineData_", e.getMessage());
        }
        getSurveyQuestion();
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("ResidualChlorine.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void getSurveyQuestion() {
        try {
            progressdialog.show();
        } catch (Exception e) {
            Log.e("SyncOnlineData_", e.getMessage());
        }
        String sUrl = CommonURL.GetSurveyQuestion_URL + "?FCID=" + fcID + "AppKey=Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3";

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
                    new AlertDialog.Builder(SyncOnlineData_Facilitator_Activity.this)
                            .setMessage("Downloading error. Please try again")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                } catch (Exception e) {
                    Log.e("SyncOnlineData_", e.getMessage());
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return CGlobal.getInstance().checkParams(params);
            }
        };
        CGlobal.getInstance().addVolleyRequest(postRequest, false, SyncOnlineData_Facilitator_Activity.this);
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
            Log.e("SyncOnlineData_", e.getMessage());
        }
        progressdialog.dismiss();
        CGlobal.getInstance().getPersistentPreferenceEditor(SyncOnlineData_Facilitator_Activity.this)
                .putBoolean(Constants.PREFS_SYNC_ONLINE_DATA_FLAG, true).commit();
        CGlobal.getInstance().getPersistentPreferenceEditor(SyncOnlineData_Facilitator_Activity.this)
                .putBoolean(Constants.PREFS_RESTART_FRAGMENT, true).commit();
        Intent intent = new Intent(SyncOnlineData_Facilitator_Activity.this, DashBoard_Facilitator_Activity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
        finish();
    }

    public boolean isNullNotDefined(JSONObject jo, String jkey) {
        if (!jo.has(jkey)) {
            return true;
        }
        return jo.isNull(jkey);
    }
}
