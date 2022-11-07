package com.sunanda.newroutine.application.ui;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.adapter.Search_Adapter;
import com.sunanda.newroutine.application.database.DatabaseHandler;
import com.sunanda.newroutine.application.modal.SampleModel;
import com.sunanda.newroutine.application.util.CGlobal;
import com.sunanda.newroutine.application.util.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Search_Activity extends NavigationBar_Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        init();
    }

    TextView tvFromDate, tvToDate;
    Button btnSearch;
    RecyclerView rvSearchList;
    ArrayList<SampleModel> commonModelArrayList;
    String sTaskIdx = "";

    private void init() {
        create("Search");

        tvFromDate = findViewById(R.id.tvFromDate);
        tvToDate = findViewById(R.id.tvToDate);

        rvSearchList = findViewById(R.id.rvSearchList);

        btnSearch = findViewById(R.id.btnSearch);

        tvFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Search_Activity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        tvToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Search_Activity.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commonModelArrayList = new ArrayList<>();
                String fromDate = tvFromDate.getText().toString();
                String toDate = tvToDate.getText().toString();
                DatabaseHandler databaseHandler = new DatabaseHandler(Search_Activity.this);
                /*try {
                    sTaskIdx = databaseHandler.getTaskIdOne();
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
                String sFCID = CGlobal.getInstance().getPersistentPreference(Search_Activity.this)
                        .getString(Constants.PREFS_USER_FACILITATOR_ID, "");
                commonModelArrayList = databaseHandler.getSearch(fromDate, toDate, sFCID, "");
                if (commonModelArrayList.size() > 0) {
                    Search_Adapter adapter = new Search_Adapter(Search_Activity.this, commonModelArrayList, "");
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(Search_Activity.this);
                    rvSearchList.setLayoutManager(mLayoutManager);
                    rvSearchList.setItemAnimator(new DefaultItemAnimator());
                    rvSearchList.setAdapter(adapter);
                } else {
                    new AlertDialog.Builder(Search_Activity.this)
                            .setMessage("No Data Found")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                }
            }
        });
    }

    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    private void updateLabel() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        tvFromDate.setText(df.format(myCalendar.getTime()));
    }

    DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel1();
        }
    };

    private void updateLabel1() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        tvToDate.setText(df.format(myCalendar.getTime()));
    }
}
