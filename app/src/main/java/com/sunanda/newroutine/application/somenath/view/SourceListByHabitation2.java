package com.sunanda.newroutine.application.somenath.view;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.somenath.myadapter.Habitation_SourceData_Adapter2;
import com.sunanda.newroutine.application.somenath.pojo.SourceByHabPojo2;
import com.sunanda.newroutine.application.ui.SyncOnlineData_Facilitator_Activity;
import com.sunanda.newroutine.application.util.CGlobal;
import com.sunanda.newroutine.application.util.CommonURL;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SourceListByHabitation2 extends AppCompatActivity {

    ProgressDialog progressdialog;
    ArrayList<SourceByHabPojo2> sourceByHabPojoArrayList;
    ArrayList<SourceByHabPojo2> sourceByHabPojoArrayList1;
    ArrayList<SourceByHabPojo2> sourceByHabPojoArrayList2;
    RecyclerView sourceRecycler;
    TextView allName;
    String sUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source_list_by_habitation2);

        progressdialog = new ProgressDialog(this);
        progressdialog.setMessage("Please Wait....");
        progressdialog.setCancelable(false);

        sourceRecycler = findViewById(R.id.sourceRecycler);
        allName = findViewById(R.id.allName);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        final Drawable upArrow = getResources().getDrawable(R.drawable.leftarrow);
        upArrow.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.leftarrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((TextView) findViewById(R.id.title)).setText("Source List By Habitation");

        if (getIntent().getStringExtra("TYPE").equalsIgnoreCase("1")) {//COLLECT
            sUrl = CommonURL.GetSourceByHabitation_URL3 + "?FCID=" + getIntent().getStringExtra("FCID");
            getSourceForFacilitator(sUrl);
        } else if (getIntent().getStringExtra("TYPE").equalsIgnoreCase("2") ||
                getIntent().getStringExtra("TYPE").equalsIgnoreCase("3")) {//ACCEPT || COMPLETE
            sUrl = CommonURL.GetSourceByHabitation_URL4 + "?Task_Id=" + getIntent().getStringExtra("TID");
            getSourceForFacilitator(sUrl);
        } else if (getIntent().getStringExtra("TYPE").equalsIgnoreCase("5")) {
            get_horizon_source_by_vill(getIntent().getStringExtra("DC"), getIntent().getStringExtra("BC"),
                    getIntent().getStringExtra("PC"), getIntent().getStringExtra("VC"));
        } else {
            sUrl = CommonURL.GetSourceByHabitation_URL2 + "?FCID=" + getIntent().getStringExtra("FCID");
            getSourceForFacilitator(sUrl);
        }
    }

    private void getSourceForFacilitator(String url) {

        sourceByHabPojoArrayList = new ArrayList<>();
        sourceByHabPojoArrayList1 = new ArrayList<>();
        sourceByHabPojoArrayList2 = new ArrayList<>();
        try {
            progressdialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Log.d("URL!!", sUrl);
        JsonArrayRequest postRequest = new JsonArrayRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONArray>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(JSONArray response) {
                progressdialog.dismiss();
                sourceByHabPojoArrayList = new Gson().fromJson(response.toString(), new TypeToken<List<SourceByHabPojo2>>() {
                }.getType());
                //Log.d("TEST!!", sourceByHabPojoArrayList.size() + "#" + sUrl);

                ArrayList<SourceByHabPojo2> tempList = new ArrayList<>();
                for (int i = 0; i < sourceByHabPojoArrayList.size(); i++) {
                    if (getIntent().getStringExtra("HN").equalsIgnoreCase(sourceByHabPojoArrayList.get(i).getHabitation()))
                        tempList.add(sourceByHabPojoArrayList.get(i));
                }

                if (tempList.size() != 0) {
                    allName.setText(getIntent().getStringExtra("DN") + "/" +
                            getIntent().getStringExtra("BN") + "/" +
                            getIntent().getStringExtra("PN") + "/" +
                            tempList.get(0).getVillageName() + "/" +
                            tempList.get(0).getHabitation());

                    Habitation_SourceData_Adapter2 sourceDataAdapter =
                            new Habitation_SourceData_Adapter2(SourceListByHabitation2.this, tempList);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SourceListByHabitation2.this);
                    sourceRecycler.setLayoutManager(linearLayoutManager);
                    sourceRecycler.setItemAnimator(new DefaultItemAnimator());
                    sourceRecycler.setAdapter(sourceDataAdapter);

                } else {
                    Toast.makeText(SourceListByHabitation2.this, "No Source Found", Toast.LENGTH_SHORT).show();
                    overridePendingTransition(R.anim.trans_right_in, R.anim.trans_left_out);
                    finish();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressdialog.dismiss();
                try {
                    new AlertDialog.Builder(SourceListByHabitation2.this)
                            .setMessage("Unable to fetch information.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    finish();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                progressdialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return CGlobal.getInstance().checkParams(params);
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
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

    public boolean isNullNotDefined(JSONObject jo, String jkey) {
        if (!jo.has(jkey)) {
            return true;
        }
        return jo.isNull(jkey);
    }

    private void get_horizon_source_by_vill(String dist_code, String block_code,
                                            String pan_code, String vill_code) {
        String sUrl = "https://phed.sunandainternational.org/api/get-horizon-source-by-vill?dist_code="
                + dist_code + "&block_code=" + block_code + "&pan_code=" + pan_code
                + "&vill_code=" + vill_code + "";

        StringRequest postRequest = new StringRequest(Request.Method.GET,
                sUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                get_horizon_source_by_villResponse(response, dist_code, block_code, pan_code, vill_code);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    new AlertDialog.Builder(SourceListByHabitation2.this)
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
        CGlobal.getInstance().addVolleyRequest(postRequest, false, SourceListByHabitation2.this);
    }

    private void get_horizon_source_by_villResponse(String response, String sDist_code, String sBlock_code,
                                                    String sPan_code, String sVill_code) {
        try {
            sourceByHabPojoArrayList = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(response);
            String resCode = jsonObject.getString("resCode");
            String message = jsonObject.getString("message");
            String error = jsonObject.getString("error");
            JSONObject jsonObjectdata = jsonObject.getJSONObject("data");
            String current_page = isNullNotDefined(jsonObjectdata, "current_page") ? "" : jsonObjectdata.getString("current_page");
            if (jsonObjectdata.has("data")) {
                JSONArray dataJSONArray = jsonObjectdata.getJSONArray("data");
                if (dataJSONArray.length() > 0) {
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

                        SourceByHabPojo2 sourceByHabPojo2 = new SourceByHabPojo2();
                        sourceByHabPojo2.setLocationDescription(Nameofthefamilyhead);
                        sourceByHabPojo2.setDateofDataCollection(created_at);
                        sourceByHabPojo2.setWaterSourceType("PIPED WATER SUPPLY SCHEME");
                        sourceByHabPojo2.setImg_source("");
                        sourceByHabPojo2.setSampleBottleNumber("");
                        sourceByHabPojoArrayList.add(sourceByHabPojo2);
                    }
                }

                Habitation_SourceData_Adapter2 sourceDataAdapter =
                        new Habitation_SourceData_Adapter2(SourceListByHabitation2.this, sourceByHabPojoArrayList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SourceListByHabitation2.this);
                sourceRecycler.setLayoutManager(linearLayoutManager);
                sourceRecycler.setItemAnimator(new DefaultItemAnimator());
                sourceRecycler.setAdapter(sourceDataAdapter);
            }
        } catch (Exception e) {
            Log.e("SyncOnlineData_", e.getMessage());
        }
    }
}
