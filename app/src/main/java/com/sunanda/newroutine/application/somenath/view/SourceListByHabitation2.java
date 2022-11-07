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
import com.sunanda.newroutine.application.somenath.myadapter.Habitation_SourceData_Adapter2;
import com.sunanda.newroutine.application.somenath.pojo.SourceByHabPojo2;
import com.sunanda.newroutine.application.util.CGlobal;
import com.sunanda.newroutine.application.util.CommonURL;

import org.json.JSONArray;

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

        final String sUrl;
        if (getIntent().getStringExtra("TYPE").equalsIgnoreCase("1"))//COLLECT
            sUrl = CommonURL.GetSourceByHabitation_URL3 + "?FCID=" + getIntent().getStringExtra("FCID");
        else if (getIntent().getStringExtra("TYPE").equalsIgnoreCase("2") ||
                getIntent().getStringExtra("TYPE").equalsIgnoreCase("3"))//ACCEPT || COMPLETE
            sUrl = CommonURL.GetSourceByHabitation_URL4 + "?Task_Id=" + getIntent().getStringExtra("TID");
        else
            sUrl = CommonURL.GetSourceByHabitation_URL2 + "?FCID=" + getIntent().getStringExtra("FCID");

        //Log.d("URL!!", sUrl);

        JsonArrayRequest postRequest = new JsonArrayRequest(Request.Method.GET,
                sUrl, null, new Response.Listener<JSONArray>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(JSONArray response) {
                progressdialog.dismiss();
                sourceByHabPojoArrayList = new Gson().fromJson(response.toString(), new TypeToken<List<SourceByHabPojo2>>() {
                }.getType());
                //Log.d("TEST!!", sourceByHabPojoArrayList.size() + "#" + sUrl);

                ArrayList<SourceByHabPojo2> tempList = new ArrayList<>();
                for (int i = 0; i < sourceByHabPojoArrayList.size(); i++) {
                    if(getIntent().getStringExtra("HN").equalsIgnoreCase(sourceByHabPojoArrayList.get(i).getHabitation()))
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
}
