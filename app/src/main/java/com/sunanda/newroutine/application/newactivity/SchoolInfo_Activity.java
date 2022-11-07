package com.sunanda.newroutine.application.newactivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.database.DatabaseHandler;
import com.sunanda.newroutine.application.modal.CommonModel;
import com.sunanda.newroutine.application.util.CGlobal;
import com.sunanda.newroutine.application.util.Constants;

import java.util.ArrayList;

public class SchoolInfo_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.school_info_activity);
        init();
    }

    String sSourceSite = "", sStatusAccommodation = "", sElectricitySchool = "", sTubeWell = "";

    @Override
    protected void onResume() {
        super.onResume();

        sSourceSite = CGlobal.getInstance().getPersistentPreference(SchoolInfo_Activity.this)
                .getString(Constants.PREFS_SOURCE_SITE_SCHOOL, "");

        if (!TextUtils.isEmpty(sSourceSite)) {
            if (sSourceSite.equalsIgnoreCase("ANGANWADI")) {
                tvEdit1.setText(getString(R.string.no_of_students_in_the_anganwadi));
                tvEdit2.setText(getString(R.string.no_of_boys_in_the_anganwadi));
                tvEdit3.setText(getString(R.string.no_of_girls_in_the_anganwadi));
                tvEdit4.setText(getString(R.string.availability_of_electricity_in_anganwadi));
            } else {
                tvEdit1.setText(getString(R.string.no_of_students_in_the_school));
                tvEdit2.setText(getString(R.string.no_of_boys_in_the_school));
                tvEdit3.setText(getString(R.string.no_of_girls_in_the_school));
                tvEdit4.setText(getString(R.string.availability_of_electricity_in_school));
            }
        }
    }

    Toolbar toolbar;
    TextView tvSchoolManagement, tvSchoolCategory, tvSchoolType, tvEdit1, tvEdit2, tvEdit3, tvEdit4;
    EditText etNoOfStudentsInTheSchool, etNoOfBoysInTheSchool, etNoOfGirlsInTheSchool,
            etSchoolManagement, etSchoolCategory, etSchoolType;
    LinearLayout llNoOfBoysInTheSchool, llNoOfGirlsInTheSchool, llStatusAccommodation, llSchoolManagement,
            llSchoolCategory, llSchoolType, llNoOfStudentsInTheSchool;
    RadioButton rbElectricitySchoolYes, rbElectricitySchoolNo, rbTubeWellYes, rbTubeWellNo;
    Button btnSaveAndExit;
    Spinner spStatusAccommodation;
    String sSchoolManagement = "", sSchoolCategory = "", sSchoolType = "";

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("School/Anganwadi Info");

        tvSchoolManagement = findViewById(R.id.tvSchoolManagement);
        tvSchoolCategory = findViewById(R.id.tvSchoolCategory);
        tvSchoolType = findViewById(R.id.tvSchoolType);

        tvEdit1 = findViewById(R.id.tvEdit1);
        tvEdit2 = findViewById(R.id.tvEdit2);
        tvEdit3 = findViewById(R.id.tvEdit3);
        tvEdit4 = findViewById(R.id.tvEdit4);

        etSchoolManagement = findViewById(R.id.etSchoolManagement);
        etSchoolCategory = findViewById(R.id.etSchoolCategory);
        etSchoolType = findViewById(R.id.etSchoolType);

        etNoOfStudentsInTheSchool = findViewById(R.id.etNoOfStudentsInTheSchool);
        etNoOfBoysInTheSchool = findViewById(R.id.etNoOfBoysInTheSchool);
        etNoOfGirlsInTheSchool = findViewById(R.id.etNoOfGirlsInTheSchool);

        llNoOfBoysInTheSchool = findViewById(R.id.llNoOfBoysInTheSchool);
        llNoOfGirlsInTheSchool = findViewById(R.id.llNoOfGirlsInTheSchool);
        llStatusAccommodation = findViewById(R.id.llStatusAccommodation);
        llSchoolManagement = findViewById(R.id.llSchoolManagement);
        llSchoolCategory = findViewById(R.id.llSchoolCategory);
        llSchoolType = findViewById(R.id.llSchoolType);
        llNoOfStudentsInTheSchool = findViewById(R.id.llNoOfStudentsInTheSchool);

        rbElectricitySchoolYes = findViewById(R.id.rbElectricitySchoolYes);
        rbElectricitySchoolNo = findViewById(R.id.rbElectricitySchoolNo);
        rbTubeWellYes = findViewById(R.id.rbTubeWellYes);
        rbTubeWellNo = findViewById(R.id.rbTubeWellNo);

        btnSaveAndExit = findViewById(R.id.btnSaveAndExit);

        spStatusAccommodation = findViewById(R.id.spStatusAccommodation);

        sSourceSite = CGlobal.getInstance().getPersistentPreference(SchoolInfo_Activity.this)
                .getString(Constants.PREFS_SOURCE_SITE_SCHOOL, "");

        if (!TextUtils.isEmpty(sSourceSite)) {
            if (sSourceSite.equalsIgnoreCase("ANGANWADI")) {
                llStatusAccommodation.setVisibility(View.VISIBLE);
                getAnganwadiPremisses();

                llNoOfBoysInTheSchool.setVisibility(View.VISIBLE);
                llNoOfGirlsInTheSchool.setVisibility(View.VISIBLE);
                llSchoolManagement.setVisibility(View.GONE);
                llSchoolCategory.setVisibility(View.GONE);
                llSchoolType.setVisibility(View.GONE);
                llNoOfStudentsInTheSchool.setVisibility(View.VISIBLE);

            } else {
                llStatusAccommodation.setVisibility(View.GONE);
                showSchoolInfo();
            }
        } else {
            llStatusAccommodation.setVisibility(View.GONE);
        }

        rbElectricitySchoolYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbElectricitySchoolYes.setChecked(true);
                rbElectricitySchoolNo.setChecked(false);
                sElectricitySchool = "1";
            }
        });

        rbElectricitySchoolNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbElectricitySchoolNo.setChecked(true);
                rbElectricitySchoolYes.setChecked(false);
                sElectricitySchool = "0";
            }
        });

        rbTubeWellYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbTubeWellYes.setChecked(true);
                rbTubeWellNo.setChecked(false);
                sTubeWell = "1";
            }
        });

        rbTubeWellNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbTubeWellNo.setChecked(true);
                rbTubeWellYes.setChecked(false);
                sTubeWell = "0";
            }
        });

        btnSaveAndExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(SchoolInfo_Activity.this);
                builder1.setTitle("Water Quality");
                builder1.setMessage("Are you sure you want to save it?");
                builder1.setCancelable(true);
                builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (!TextUtils.isEmpty(sSourceSite)) {
                            if (sSourceSite.equalsIgnoreCase("ANGANWADI")) {
                                if (TextUtils.isEmpty(sStatusAccommodation) || sStatusAccommodation.equalsIgnoreCase("Choose")) {
                                    new AlertDialog.Builder(SchoolInfo_Activity.this)
                                            .setMessage("Please Select Anganwadi Accommodation")
                                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            }).show();
                                    return;
                                }
                            }
                        }

                        int id1 = CGlobal.getInstance().getPersistentPreference(SchoolInfo_Activity.this)
                                .getInt(Constants.PREFS_LAST_INDEX_SCHOOL_SAMPLE_COLLECTION_ID_SCHOOL, 0);

                        String udise_code = CGlobal.getInstance().getPersistentPreference(SchoolInfo_Activity.this)
                                .getString(Constants.PREFS_UDISE_CODE_SCHOOL, "");

                        if (udise_code.equalsIgnoreCase("OTHER")) {
                            sSchoolManagement = etSchoolManagement.getText().toString();
                            sSchoolCategory = etSchoolCategory.getText().toString();
                            sSchoolType = etSchoolType.getText().toString();
                        } else {
                            sSchoolManagement = tvSchoolManagement.getText().toString();
                            sSchoolCategory = tvSchoolCategory.getText().toString();
                            sSchoolType = tvSchoolType.getText().toString();
                        }

                        String sNoOfStudentsInTheSchool = etNoOfStudentsInTheSchool.getText().toString();
                        String sNoOfBoysInTheSchool = etNoOfBoysInTheSchool.getText().toString();
                        String sNoOfGirlsInTheSchool = etNoOfGirlsInTheSchool.getText().toString();

                        DatabaseHandler databaseHandler = new DatabaseHandler(SchoolInfo_Activity.this);
                        databaseHandler.updateSchoolInfoSchoolAppDataCollection(id1, sSchoolManagement, sSchoolCategory, sSchoolType,
                                sNoOfStudentsInTheSchool, sNoOfBoysInTheSchool, sNoOfGirlsInTheSchool, sElectricitySchool, sTubeWell,
                                sStatusAccommodation);

                        Intent intent = new Intent(SchoolInfo_Activity.this, Wash_Activity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                        dialog.cancel();
                    }
                });

                builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

    }

    CommonModel cmaSchoolData;

    private void showSchoolInfo() {

        cmaSchoolData = new CommonModel();
        String udise_code = CGlobal.getInstance().getPersistentPreference(SchoolInfo_Activity.this)
                .getString(Constants.PREFS_UDISE_CODE_SCHOOL, "");
        DatabaseHandler databaseHandler = new DatabaseHandler(SchoolInfo_Activity.this);
        cmaSchoolData = databaseHandler.getSchoolInfo(udise_code);

        if (udise_code.equalsIgnoreCase("OTHER")) {
            etSchoolManagement.setVisibility(View.VISIBLE);
            etSchoolCategory.setVisibility(View.VISIBLE);
            etSchoolType.setVisibility(View.VISIBLE);

            tvSchoolManagement.setVisibility(View.GONE);
            tvSchoolCategory.setVisibility(View.GONE);
            tvSchoolType.setVisibility(View.GONE);

            llSchoolManagement.setVisibility(View.VISIBLE);
            llSchoolCategory.setVisibility(View.VISIBLE);
            llSchoolType.setVisibility(View.VISIBLE);

            llNoOfBoysInTheSchool.setVisibility(View.VISIBLE);
            llNoOfGirlsInTheSchool.setVisibility(View.VISIBLE);
            llNoOfStudentsInTheSchool.setVisibility(View.VISIBLE);
        } else {
            tvSchoolManagement.setText(cmaSchoolData.getSchool_management());
            tvSchoolCategory.setText(cmaSchoolData.getSchool_category());
            tvSchoolType.setText(cmaSchoolData.getSchool_type());

            etSchoolManagement.setVisibility(View.GONE);
            etSchoolCategory.setVisibility(View.GONE);
            etSchoolType.setVisibility(View.GONE);

            tvSchoolManagement.setVisibility(View.VISIBLE);
            tvSchoolCategory.setVisibility(View.VISIBLE);
            tvSchoolType.setVisibility(View.VISIBLE);

            llSchoolManagement.setVisibility(View.VISIBLE);
            llSchoolCategory.setVisibility(View.VISIBLE);
            llSchoolType.setVisibility(View.VISIBLE);

            if (cmaSchoolData.getSchool_type().equalsIgnoreCase("GIRLS")) {
                llNoOfBoysInTheSchool.setVisibility(View.GONE);
                llNoOfGirlsInTheSchool.setVisibility(View.VISIBLE);
                llNoOfStudentsInTheSchool.setVisibility(View.VISIBLE);
            } else if (cmaSchoolData.getSchool_type().equalsIgnoreCase("BOYS")) {
                llNoOfBoysInTheSchool.setVisibility(View.VISIBLE);
                llNoOfGirlsInTheSchool.setVisibility(View.GONE);
                llNoOfStudentsInTheSchool.setVisibility(View.VISIBLE);
            } else {
                llNoOfBoysInTheSchool.setVisibility(View.VISIBLE);
                llNoOfGirlsInTheSchool.setVisibility(View.VISIBLE);
                llNoOfStudentsInTheSchool.setVisibility(View.VISIBLE);
            }
        }
    }

    private void getAnganwadiPremisses() {
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add("Choose");
        stringArrayList.add("OWN BUILDING");
        stringArrayList.add("RENTED BUILDING");
        stringArrayList.add("PRIMARY SCHOOL");
        stringArrayList.add("OTHER GOVERNMENT BUILDING");
        stringArrayList.add("OPEN SPACE");
        stringArrayList.add("PRIVATE BUILDING");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, stringArrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStatusAccommodation.setAdapter(adapter);

        spStatusAccommodation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sStatusAccommodation = spStatusAccommodation.getSelectedItem().toString();
                if (sStatusAccommodation.equalsIgnoreCase("Choose")) {
                    return;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(SchoolInfo_Activity.this)
                .setMessage("Please Click Next Button")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

}
