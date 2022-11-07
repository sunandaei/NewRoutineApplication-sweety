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
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.somenath.myadapter.Habitation_SourceData_Adapter;
import com.sunanda.newroutine.application.somenath.pojo.SourceByHabPojo;
import com.sunanda.newroutine.application.util.CGlobal;
import com.sunanda.newroutine.application.util.CommonURL;
import com.sunanda.newroutine.application.util.Constants;
import com.sunanda.newroutine.application.util.PostInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SourceListByHabitation extends AppCompatActivity {

    ProgressDialog progressdialog;
    ArrayList<SourceByHabPojo> sourceByHabPojoArrayList;
    ArrayList<SourceByHabPojo> sourceByHabPojoArrayList1;
    ArrayList<SourceByHabPojo> sourceByHabPojoArrayList2;
    RecyclerView sourceRecycler;
    TextView allName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source_list_by_habitation);

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

        getSourceForFacilitator();
    }

    private void getSourceForFacilitator() {

        sourceByHabPojoArrayList = new ArrayList<>();
        sourceByHabPojoArrayList1 = new ArrayList<>();
        sourceByHabPojoArrayList2 = new ArrayList<>();
        try {
            progressdialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String unique_id = CGlobal.getInstance().getPersistentPreference(this)
                .getString(Constants.PREFS_USER_USRUNIQUE_ID, "");

        /*String sUrl = CommonURL.GetSourceByHabitation_URL + "?HabCodes=" + getIntent().getStringExtra("HC") +
                "&dist_code=" + getIntent().getStringExtra("DC") +
                "&block_code=" + getIntent().getStringExtra("BC") +
                "&pan_code=" + getIntent().getStringExtra("PC") +
                "&village_code=" + getIntent().getStringExtra("VC") +
                "&samplecollectorid=" + unique_id +
                "&AppKey=Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3";*/

        String sUrl = CommonURL.GetSourceByHabitation_URL_NEW + "?HabCodes=" + getIntent().getStringExtra("HC") +
                "&dist_code=" + getIntent().getStringExtra("DC") +
                "&block_code=" + getIntent().getStringExtra("BC") +
                "&pan_code=" + getIntent().getStringExtra("PC") +
                "&village_code=" + getIntent().getStringExtra("VC") +
                "&samplecollectorid=" + unique_id /*+
                "&AppKey=Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3"*/;

        //Log.d("URL!!", sUrl);

        JsonArrayRequest postRequest = new JsonArrayRequest(Request.Method.GET,
                sUrl, null, new Response.Listener<JSONArray>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(JSONArray response) {
                progressdialog.dismiss();
                sourceByHabPojoArrayList = new Gson().fromJson(response.toString(), new TypeToken<List<SourceByHabPojo>>() {
                }.getType());
                //Log.d("TEST!!", String.valueOf(sourceByHabPojoArrayList.size()));

                if (sourceByHabPojoArrayList.size() != 0) {
                    allName.setText(getIntent().getStringExtra("DN") + "/" +
                            getIntent().getStringExtra("BN") + "/" +
                            getIntent().getStringExtra("PN") + "/" +
                            sourceByHabPojoArrayList.get(0).getVillageName() + "/" +
                            sourceByHabPojoArrayList.get(0).getHabitation());

                    //getSourceByTask();

                    Habitation_SourceData_Adapter sourceDataAdapter =
                            new Habitation_SourceData_Adapter(SourceListByHabitation.this, sourceByHabPojoArrayList);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SourceListByHabitation.this);
                    sourceRecycler.setLayoutManager(linearLayoutManager);
                    sourceRecycler.setItemAnimator(new DefaultItemAnimator());
                    sourceRecycler.setAdapter(sourceDataAdapter);

                } else {
                    Toast.makeText(SourceListByHabitation.this, "No Source Found", Toast.LENGTH_SHORT).show();
                    overridePendingTransition(R.anim.trans_right_in, R.anim.trans_left_out);
                    finish();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressdialog.dismiss();
                try {
                    new AlertDialog.Builder(SourceListByHabitation.this)
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

    public void getSourceByTask() {

        sourceByHabPojoArrayList1 = new ArrayList<>();
        sourceByHabPojoArrayList2 = new ArrayList<>();

        try {
            progressdialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sUrl = PostInterface.URL_RNJ + "source_by_taskID.php" + "?Task_Id=" + getIntent().getStringExtra("TID");

        //Log.d("URL!!", sUrl);

        JsonArrayRequest postRequest = new JsonArrayRequest(Request.Method.GET,
                sUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressdialog.dismiss();

                try {
                    JSONArray jsonArray = new JSONArray(response.toString());
                    for (int i = 0; i < sourceByHabPojoArrayList.size(); i++) {
                        for (int j = 0; j < jsonArray.length(); j++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(j);
                            if (sourceByHabPojoArrayList.get(i).getMiD().equalsIgnoreCase(jsonObject.getString("existing_mid"))) {
                                sourceByHabPojoArrayList.get(i).setCollected_date(jsonObject.getString("collected_date"));
                                sourceByHabPojoArrayList.get(i).setTested(true);
                                sourceByHabPojoArrayList.get(i).setImgSource(jsonObject.getString("img_source"));
                                sourceByHabPojoArrayList.get(i).setSampleBottleNumber(jsonObject.getString("sample_bottle_no"));
                                //sourceByHabPojoArrayList1.add(sourceByHabPojoArrayList.get(i));
                                break;
                            } /*else {
                                sourceByHabPojoArrayList2.add(sourceByHabPojoArrayList.get(i));
                            }*/
                        }
                    }
                    //sourceByHabPojoArrayList.clear();
                    //sourceByHabPojoArrayList.addAll(sourceByHabPojoArrayList1);
                    //sourceByHabPojoArrayList.addAll(sourceByHabPojoArrayList2);
                    Habitation_SourceData_Adapter sourceDataAdapter =
                            new Habitation_SourceData_Adapter(SourceListByHabitation.this, sourceByHabPojoArrayList);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SourceListByHabitation.this);
                    sourceRecycler.setLayoutManager(linearLayoutManager);
                    sourceRecycler.setItemAnimator(new DefaultItemAnimator());
                    sourceRecycler.setAdapter(sourceDataAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressdialog.dismiss();
                try {
                    /*new AlertDialog.Builder(SourceListByHabitation.this)
                            .setMessage("Unable to fetch information.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    finish();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();*/
                    Habitation_SourceData_Adapter sourceDataAdapter =
                            new Habitation_SourceData_Adapter(SourceListByHabitation.this, sourceByHabPojoArrayList);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SourceListByHabitation.this);
                    sourceRecycler.setLayoutManager(linearLayoutManager);
                    sourceRecycler.setItemAnimator(new DefaultItemAnimator());
                    sourceRecycler.setAdapter(sourceDataAdapter);

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
}
