package com.sunanda.newroutine.application.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.database.DatabaseHandler;
import com.sunanda.newroutine.application.modal.SampleModel;
import com.sunanda.newroutine.application.newactivity.EditSanitarySurveyQuesAnsSchool_Activity;
import com.sunanda.newroutine.application.newactivity.EditSchoolInfo_Activity;
import com.sunanda.newroutine.application.newactivity.EditSchoolNewSample_Activity;
import com.sunanda.newroutine.application.newactivity.EditSchoolOMASNewSample_Activity;
import com.sunanda.newroutine.application.newactivity.EditWash_Activity;
import com.sunanda.newroutine.application.util.CGlobal;
import com.sunanda.newroutine.application.util.Constants;

public class UserSelection_Activity extends AppCompatActivity {

    TextView tvSampleCollection, tvSanitarySurvey, tvSchoolInfo, tvWashPage;
    SampleModel sampleModel;
    String sTaskIdx = "";
    DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_selection_activity);

        tvSampleCollection = findViewById(R.id.tvSampleCollection);
        tvSanitarySurvey = findViewById(R.id.tvSanitarySurvey);
        tvSchoolInfo = findViewById(R.id.tvSchoolInfo);
        tvWashPage = findViewById(R.id.tvWashPage);

        String appName = CGlobal.getInstance().getPersistentPreference(UserSelection_Activity.this)
                .getString(Constants.PREFS_APP_NAME, "");

        if (appName.equalsIgnoreCase("School")) {
            tvSampleCollection.setVisibility(View.VISIBLE);
            tvSanitarySurvey.setVisibility(View.VISIBLE);
            tvSchoolInfo.setVisibility(View.VISIBLE);
            tvWashPage.setVisibility(View.VISIBLE);
        } else if (appName.equalsIgnoreCase("SchoolOMAS")) {
            tvSampleCollection.setVisibility(View.VISIBLE);
            tvSanitarySurvey.setVisibility(View.VISIBLE);
            tvSchoolInfo.setVisibility(View.VISIBLE);
            tvWashPage.setVisibility(View.VISIBLE);
        } else if (appName.equalsIgnoreCase("Routine")) {
            tvSampleCollection.setVisibility(View.VISIBLE);
            tvSanitarySurvey.setVisibility(View.VISIBLE);
            tvSchoolInfo.setVisibility(View.GONE);
            tvWashPage.setVisibility(View.GONE);
        } else if (appName.equalsIgnoreCase("OMAS")) {
            tvSampleCollection.setVisibility(View.VISIBLE);
            tvSanitarySurvey.setVisibility(View.VISIBLE);
            tvSchoolInfo.setVisibility(View.GONE);
            tvWashPage.setVisibility(View.GONE);
        } else {
            tvSampleCollection.setVisibility(View.GONE);
            tvSanitarySurvey.setVisibility(View.GONE);
            tvSchoolInfo.setVisibility(View.GONE);
            tvWashPage.setVisibility(View.GONE);
        }

        tvSampleCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String appName = CGlobal.getInstance().getPersistentPreference(UserSelection_Activity.this)
                        .getString(Constants.PREFS_APP_NAME, "");
                if (appName.equalsIgnoreCase("School")) {
                    Intent intent = new Intent(UserSelection_Activity.this, EditSchoolNewSample_Activity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                } else if (appName.equalsIgnoreCase("SchoolOMAS")) {
                    Intent intent = new Intent(UserSelection_Activity.this, EditSchoolOMASNewSample_Activity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                } else {
                    Intent intent = new Intent(UserSelection_Activity.this, EditSampleCollection_Activity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                }
            }
        });

        tvSanitarySurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String appName = CGlobal.getInstance().getPersistentPreference(UserSelection_Activity.this)
                        .getString(Constants.PREFS_APP_NAME, "");
                if (appName.equalsIgnoreCase("School")) {

                    sampleModel = new SampleModel();
                    databaseHandler = new DatabaseHandler(UserSelection_Activity.this);
                    int iId = CGlobal.getInstance().getPersistentPreference(UserSelection_Activity.this)
                            .getInt(Constants.PREFS_SEARCH_CLICK_ID, 0);
                    /*try {
                        sTaskIdx = databaseHandler.getTaskIdOne();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/
                    String sFCID = CGlobal.getInstance().getPersistentPreference(UserSelection_Activity.this)
                            .getString(Constants.PREFS_USER_FACILITATOR_ID, "");
                    sampleModel = databaseHandler.getSanitarySurveyEditSchool(iId, sFCID);
                    if (sampleModel.getSource_type_q_6().equalsIgnoreCase("TUBE WELL MARK II")
                            || sampleModel.getSource_type_q_6().equalsIgnoreCase("TUBE WELL ORDINARY")) {
                        if (sampleModel.getCondition_of_source().equalsIgnoreCase("FUNCTIONAL")) {
                            Intent intent = new Intent(UserSelection_Activity.this, EditSanitarySurveyQuesAnsSchool_Activity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                        } else {
                            new AlertDialog.Builder(UserSelection_Activity.this)
                                    .setMessage("You have selected the Source as 'DEFUNCT', you can't proceed further!!")
                                    .setCancelable(false)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).show();
                        }
                    } else {
                        Intent intent = new Intent(UserSelection_Activity.this, EditSanitarySurveyQuesAnsSchool_Activity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                    }

                } if (appName.equalsIgnoreCase("SchoolOMAS")) {

                    sampleModel = new SampleModel();
                    databaseHandler = new DatabaseHandler(UserSelection_Activity.this);
                    int iId = CGlobal.getInstance().getPersistentPreference(UserSelection_Activity.this)
                            .getInt(Constants.PREFS_SEARCH_CLICK_ID, 0);
                    /*try {
                        sTaskIdx = databaseHandler.getTaskIdOne();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/
                    String sFCID = CGlobal.getInstance().getPersistentPreference(UserSelection_Activity.this)
                            .getString(Constants.PREFS_USER_FACILITATOR_ID, "");
                    sampleModel = databaseHandler.getSanitarySurveyEditSchool(iId, sFCID);
                    if (sampleModel.getSource_type_q_6().equalsIgnoreCase("TUBE WELL MARK II")
                            || sampleModel.getSource_type_q_6().equalsIgnoreCase("TUBE WELL ORDINARY")) {
                        if (sampleModel.getCondition_of_source().equalsIgnoreCase("FUNCTIONAL")) {
                            Intent intent = new Intent(UserSelection_Activity.this, EditSanitarySurveyQuesAnsSchool_Activity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                        } else {
                            new AlertDialog.Builder(UserSelection_Activity.this)
                                    .setMessage("You have selected the Source as 'DEFUNCT', you can't proceed further!!")
                                    .setCancelable(false)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).show();
                        }
                    } else {
                        Intent intent = new Intent(UserSelection_Activity.this, EditSanitarySurveyQuesAnsSchool_Activity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                    }

                } else {
                    sampleModel = new SampleModel();
                    databaseHandler = new DatabaseHandler(UserSelection_Activity.this);
                    int iId = CGlobal.getInstance().getPersistentPreference(UserSelection_Activity.this)
                            .getInt(Constants.PREFS_SEARCH_CLICK_ID, 0);
                    /*try {
                        sTaskIdx = databaseHandler.getTaskIdOne();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/
                    String sFCID = CGlobal.getInstance().getPersistentPreference(UserSelection_Activity.this)
                            .getString(Constants.PREFS_USER_FACILITATOR_ID, "");
                    sampleModel = databaseHandler.getSampleEdit(iId, sFCID, appName);
                    if (sampleModel.getSource_type_q_6().equalsIgnoreCase("TUBE WELL MARK II")
                            || sampleModel.getSource_type_q_6().equalsIgnoreCase("TUBE WELL ORDINARY")) {
                        if (sampleModel.getCondition_of_source().equalsIgnoreCase("FUNCTIONAL")) {
                            Intent intent = new Intent(UserSelection_Activity.this, EditSanitarySurveyQuesAns_Activity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                        } else {
                            new AlertDialog.Builder(UserSelection_Activity.this)
                                    .setMessage("You have selected the Source as 'DEFUNCT', you can't proceed further!!")
                                    .setCancelable(false)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).show();
                        }
                    } else {
                        Intent intent = new Intent(UserSelection_Activity.this, EditSanitarySurveyQuesAns_Activity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                    }
                }
            }
        });

        tvSchoolInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserSelection_Activity.this, EditSchoolInfo_Activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
            }
        });

        tvWashPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserSelection_Activity.this, EditWash_Activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
            }
        });
    }
}
