package com.sunanda.newroutine.application.newactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.adapter.AssignedHabitationList_Adapter;
import com.sunanda.newroutine.application.adapter.Head_PWSS_Source_Adapter;
import com.sunanda.newroutine.application.database.DatabaseHandler;
import com.sunanda.newroutine.application.modal.CommonModel;
import com.sunanda.newroutine.application.ui.DashBoard_Facilitator_Activity;
import com.sunanda.newroutine.application.util.CGlobal;
import com.sunanda.newroutine.application.util.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class Head_PWSS_Source_Activity extends AppCompatActivity {

    Button btnHeadSite, btnPWSSSource;
    RecyclerView rcvSourceList;
    ArrayList<CommonModel> modelArrayList;
    String sBlockCode = "", sPanCode = "", sVillCode = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.head_pwss_source_activity);

        btnHeadSite = findViewById(R.id.btnHeadSite);
        btnPWSSSource = findViewById(R.id.btnPWSSSource);
        rcvSourceList = findViewById(R.id.rcvSourceList);

        sBlockCode = CGlobal.getInstance().getPersistentPreference(Head_PWSS_Source_Activity.this)
                .getString("HEAD_PWSS_BLOCK", "");
        sPanCode = CGlobal.getInstance().getPersistentPreference(Head_PWSS_Source_Activity.this)
                .getString("HEAD_PWSS_PAN", "");
        sVillCode = CGlobal.getInstance().getPersistentPreference(Head_PWSS_Source_Activity.this)
                .getString("HEAD_PWSS_VILL", "");


        modelArrayList = new ArrayList<>();
        DatabaseHandler databaseHandler = new DatabaseHandler(Head_PWSS_Source_Activity.this);
        modelArrayList = databaseHandler.getAssignPWSSList(sBlockCode, sPanCode, sVillCode, "head_site");

        if(modelArrayList.size() == 0){
            Toast.makeText(Head_PWSS_Source_Activity.this,"No Source found from Head site",Toast.LENGTH_LONG).show();
        }

        Head_PWSS_Source_Adapter sourceDataAdapter = new Head_PWSS_Source_Adapter(modelArrayList,
                Head_PWSS_Source_Activity.this, "0", "head_site");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Head_PWSS_Source_Activity.this);
        rcvSourceList.setLayoutManager(linearLayoutManager);
        rcvSourceList.setItemAnimator(new DefaultItemAnimator());
        rcvSourceList.setAdapter(sourceDataAdapter);

        btnHeadSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modelArrayList = new ArrayList<>();
                DatabaseHandler databaseHandler = new DatabaseHandler(Head_PWSS_Source_Activity.this);
                modelArrayList = databaseHandler.getAssignPWSSList(sBlockCode, sPanCode, sVillCode, "head_site");

                if(modelArrayList.size() == 0){
                    Toast.makeText(Head_PWSS_Source_Activity.this,"No Source found from Head site",Toast.LENGTH_LONG).show();
                }

                Head_PWSS_Source_Adapter sourceDataAdapter = new Head_PWSS_Source_Adapter(modelArrayList,
                        Head_PWSS_Source_Activity.this, "0", "head_site");
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Head_PWSS_Source_Activity.this);
                rcvSourceList.setLayoutManager(linearLayoutManager);
                rcvSourceList.setItemAnimator(new DefaultItemAnimator());
                rcvSourceList.setAdapter(sourceDataAdapter);
            }
        });

        btnPWSSSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modelArrayList = new ArrayList<>();
                DatabaseHandler databaseHandler = new DatabaseHandler(Head_PWSS_Source_Activity.this);
                modelArrayList = databaseHandler.getAssignPWSSList(sBlockCode, sPanCode, sVillCode, "YES");

                if(modelArrayList.size() == 0){
                    Toast.makeText(Head_PWSS_Source_Activity.this,"No Source found from PWSS",Toast.LENGTH_LONG).show();
                }

                Head_PWSS_Source_Adapter sourceDataAdapter = new Head_PWSS_Source_Adapter(modelArrayList,
                        Head_PWSS_Source_Activity.this, "0", "YES");
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Head_PWSS_Source_Activity.this);
                rcvSourceList.setLayoutManager(linearLayoutManager);
                rcvSourceList.setItemAnimator(new DefaultItemAnimator());
                rcvSourceList.setAdapter(sourceDataAdapter);
            }
        });
    }
}
