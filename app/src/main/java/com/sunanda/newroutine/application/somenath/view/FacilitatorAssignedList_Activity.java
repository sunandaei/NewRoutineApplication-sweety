package com.sunanda.newroutine.application.somenath.view;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.snackbar.Snackbar;
import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.modal.CommonModel;
import com.sunanda.newroutine.application.somenath.myadapter.Assigned_ArchiveTaskAdapter;
import com.sunanda.newroutine.application.somenath.myadapter.MySpinnerAdapter;
import com.sunanda.newroutine.application.somenath.pojo.AssignedArchiveTaskPojo;
import com.sunanda.newroutine.application.somenath.pojo.ResponsePanchyat;
import com.sunanda.newroutine.application.util.CGlobal;
import com.sunanda.newroutine.application.util.Constants;
import com.sunanda.newroutine.application.util.LoadingDialog;
import com.sunanda.newroutine.application.util.PostInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FacilitatorAssignedList_Activity extends AppCompatActivity {

    Switch statusSwitch;
    String strStatus;
    TextView name, email, pass, mobile, uname;
    boolean isVisible = false, isAssigned = true, isArchive = false, isAccepted = false, isCollected = false, isAction = false;
    String current_task_id = "", villageCode = "";

    CommonModel commonModel;
    ImageView show_hide_pass;
    ArrayList<AssignedArchiveTaskPojo> allTaskPojoArrayList;
    ArrayList<AssignedArchiveTaskPojo> currentTaskPojoArrayList;
    ArrayList<AssignedArchiveTaskPojo> archiveTaskPojoArrayList;
    ArrayList<AssignedArchiveTaskPojo> acceptedTaskPojoArrayList;
    ArrayList<AssignedArchiveTaskPojo> collectedTaskPojoArrayList;
    RecyclerView rvAssignList, rvArchiveList;
    Assigned_ArchiveTaskAdapter mAdapter;
    TextView nodata, nodata2;

    ArrayList<ResponsePanchyat> responsePanArrayList;
    ArrayList<String> blockName = new ArrayList<>();
    ArrayList<String> blockCode = new ArrayList<>();
    ArrayList<String> cmaPanchayatName = new ArrayList<>();
    ArrayList<String> cmaPanchayatCode = new ArrayList<>();
    Spinner spBlock;
    String sBlockName = "", sBlockCode = "";
    String[] selectedPanCodes;
    String selectedBlockCode = "";
    String selectedBlockName = "";
    int selected_pos = 0;
    TextView tvPanchayat, pannames;
    android.app.AlertDialog.Builder alertdialogbuilder1;
    boolean[] selectedtruefalse;
    String[] alertDialogItems1;
    String pan_name = "", pan_code = "";
    String districtCode = "", distName = "";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facilitator_assign_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        final Drawable upArrow = getResources().getDrawable(R.drawable.leftarrow);
        upArrow.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.leftarrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((TextView) findViewById(R.id.title)).setText("Facilitator Details");

        rvAssignList = findViewById(R.id.rvAssignList);
        nodata = findViewById(R.id.nodata);
        rvArchiveList = findViewById(R.id.rvArchiveList);
        nodata2 = findViewById(R.id.nodata2);

        commonModel = (CommonModel) getIntent().getSerializableExtra("ALLDATA");
        selectedBlockCode = commonModel.getFCBlock_Code();
        selectedBlockName = commonModel.getFCBlockName();
        try {
            selectedPanCodes = commonModel.getFCPan_Codes().split("\\s*,\\s*");
        } catch (Exception e) {
            selectedPanCodes[0] = commonModel.getFCPan_Codes();
        }
        //Toast.makeText(this, commonModel.getName(), Toast.LENGTH_SHORT).show();

        name = findViewById(R.id.name);
        uname = findViewById(R.id.uname);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        mobile = findViewById(R.id.mobile);

        name.setText("\uD83D\uDC64 Name : " + commonModel.getName());
        uname.setText("\uD83D\uDC64 User Name : " + commonModel.getUser_name());
        email.setText(" ✉ Email : " + commonModel.getEmail());
        pass.setText("\uD83D\uDD10 Password : " + commonModel.getPassword().replaceAll(".", "x"));
        //mobile.setText("\uD83D\uDCF1 Mobile : " + "+91 ----------");
        mobile.setText("\uD83D\uDCF1 Mobile : " + commonModel.getMobile());

        statusSwitch = (Switch) findViewById(R.id.statusSwitch);
        if (commonModel.is_active.equalsIgnoreCase("1")) {
            statusSwitch.setChecked(true);
            findViewById(R.id.addNewTask).setVisibility(View.VISIBLE);
        } else {
            statusSwitch.setChecked(false);
            findViewById(R.id.addNewTask).setVisibility(View.GONE);
        }

        if (statusSwitch.isChecked()) {
            strStatus = statusSwitch.getTextOn().toString();
            statusSwitch.setText(strStatus);
            statusSwitch.setBackgroundColor(Color.parseColor("#006400"));
        } else {
            strStatus = statusSwitch.getTextOff().toString();
            statusSwitch.setText(strStatus);
            statusSwitch.setBackgroundColor(Color.parseColor("#FF0000"));
        }
        statusSwitch.setText(strStatus);
        statusSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (statusSwitch.isChecked()) {
                    strStatus = statusSwitch.getTextOn().toString();
                    statusSwitch.setText(strStatus);
                    statusSwitch.setBackgroundColor(Color.parseColor("#006400"));
                    //showMyDialog("1");
                    ActiveInactivefacilitator("A");
                } else {
                    strStatus = statusSwitch.getTextOff().toString();
                    statusSwitch.setText(strStatus);
                    statusSwitch.setBackgroundColor(Color.parseColor("#FF0000"));
                    //Toast.makeText(FacilitatorAssignedList_Activity.this, "www", Toast.LENGTH_SHORT).show();
                    //showMyDialog("0");
                    ActiveInactivefacilitator("I");
                }
            }
        });

        show_hide_pass = findViewById(R.id.show_hide_pass);
        show_hide_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVisible) {
                    isVisible = false;
                    pass.setText("\uD83D\uDD10 Password : " + commonModel.getPassword().replaceAll("(?s).", "x"));
                    show_hide_pass.setImageDrawable(getResources().getDrawable(R.drawable.hide));
                } else {
                    isVisible = true;
                    pass.setText("\uD83D\uDD10 Password : " + commonModel.getPassword());
                    show_hide_pass.setImageDrawable(getResources().getDrawable(R.drawable.view));
                }
            }
        });

        findViewById(R.id.assigned).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssignTask();
            }
        });
        findViewById(R.id.collected).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollectedTask();
            }
        });
        findViewById(R.id.accepted).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AcceptedTask();
            }
        });
        findViewById(R.id.archive).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArchiveTask();
            }
        });

        findViewById(R.id.actionLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAction) {
                    findViewById(R.id.action).setVisibility(View.VISIBLE);
                    findViewById(R.id.action2).setVisibility(View.GONE);
                    findViewById(R.id.lowerlayout).setVisibility(View.GONE);
                    isAction = false;
                } else {
                    findViewById(R.id.action2).setVisibility(View.VISIBLE);
                    findViewById(R.id.action).setVisibility(View.GONE);
                    findViewById(R.id.lowerlayout).setVisibility(View.VISIBLE);
                    isAction = true;
                }
            }
        });

        findViewById(R.id.edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadSourceData();

                AlertDialog.Builder builder = new AlertDialog.Builder(FacilitatorAssignedList_Activity.this);
                // Get the layout inflater
                LayoutInflater inflater = getLayoutInflater();
                View mView = inflater.inflate(R.layout.dialog_edit_facilitator, null);
                final EditText username = (EditText) mView.findViewById(R.id.username);
                final EditText newmobile = (EditText) mView.findViewById(R.id.mobile);
                final EditText newemail = (EditText) mView.findViewById(R.id.email);
                final EditText newpassword = (EditText) mView.findViewById(R.id.password);
                tvPanchayat = mView.findViewById(R.id.tvPanchayat);
                pannames = mView.findViewById(R.id.pannames);
                spBlock = mView.findViewById(R.id.spBlock);
                username.setText(commonModel.getName());
                username.setSelection(commonModel.getName().length());
                newmobile.setText(commonModel.getMobile().trim());
                newemail.setText(commonModel.getEmail().trim());
                newpassword.setText(commonModel.getPassword().trim());

                if (TextUtils.isEmpty(commonModel.getFCPan_Codes()))
                    pannames.setVisibility(View.GONE);
                else
                    pannames.setText("• Selected Panchayats : " + commonModel.getFCPanNames());

                final String labid = CGlobal.getInstance().getPersistentPreference(FacilitatorAssignedList_Activity.this)
                        .getString(Constants.PREFS_USER_LAB_ID, "");
                final String PREFS_USER_USRUNIQUE_ID = CGlobal.getInstance().getPersistentPreference(FacilitatorAssignedList_Activity.this)
                        .getString(Constants.PREFS_USER_USRUNIQUE_ID, "");

                builder.setView(mView)
                        // Add action buttons
                        .setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                if (TextUtils.isEmpty(pan_code)) {
                                    pan_code = commonModel.getFCPan_Codes();
                                    pan_name = commonModel.getFCPanNames();
                                    sBlockCode = commonModel.getFCBlock_Code();
                                    districtCode = commonModel.getFCDist_Code();
                                    distName = commonModel.getFCDistNam();
                                    sBlockName = commonModel.getFCBlockName();
                                }
                                if (TextUtils.isEmpty(username.getText().toString())) {
                                    Toast.makeText(FacilitatorAssignedList_Activity.this,
                                            "Name can't be empty", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (TextUtils.isEmpty(newpassword.getText().toString())) {
                                    Toast.makeText(FacilitatorAssignedList_Activity.this,
                                            "Password can't be empty", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (!TextUtils.isEmpty(newmobile.getText().toString())) {
                                    if (newmobile.getText().toString().length() != 10) {
                                        Toast.makeText(FacilitatorAssignedList_Activity.this,
                                                "Please Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();
                                    } else if (!isValidMobile(newmobile.getText().toString())) {
                                        Toast.makeText(FacilitatorAssignedList_Activity.this,
                                                "Please Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();
                                    } else if (TextUtils.isEmpty(pan_name)) {
                                        Toast.makeText(FacilitatorAssignedList_Activity.this,
                                                "Panchayat name(s) can't be elank", Toast.LENGTH_SHORT).show();

                                        /*dialog.dismiss();

                                        pan_code = commonModel.getFCPan_Codes();
                                        pan_name = commonModel.getFCPanNames();
                                        sBlockCode = commonModel.getFCBlock_Code();
                                        districtCode = commonModel.getFCDist_Code();
                                        distName = commonModel.getFCDistNam();
                                        sBlockName = commonModel.getFCBlockName();

                                        EditMyFacilator(username.getText().toString().trim(), newmobile.getText().toString().trim(),
                                                newemail.getText().toString().trim(), newpassword.getText().toString().trim(),
                                                commonModel.getFCPan_Codes(), commonModel.getFCBlock_Code(), labid,
                                                PREFS_USER_USRUNIQUE_ID, commonModel.getFCID());*/
                                    } else {

                                        dialog.dismiss();
                                        EditMyFacilator(username.getText().toString().trim(), newmobile.getText().toString().trim(),
                                                newemail.getText().toString().trim(), newpassword.getText().toString().trim(),
                                                pan_code, sBlockCode, labid, PREFS_USER_USRUNIQUE_ID, commonModel.getFCID());
                                    }
                                    return;
                                }
                                if (!TextUtils.isEmpty(newemail.getText().toString().trim())) {
                                    if (!isInputDataValid(newemail.getText().toString())) {
                                        Toast.makeText(FacilitatorAssignedList_Activity.this,
                                                "Please enter valid Email-Id", Toast.LENGTH_SHORT).show();
                                    } else if (TextUtils.isEmpty(pan_name)) {
                                        Toast.makeText(FacilitatorAssignedList_Activity.this,
                                                "Panchayat Name(s) Can't be Blank", Toast.LENGTH_SHORT).show();

                                        /*dialog.dismiss();

                                        pan_code = commonModel.getFCPan_Codes();
                                        pan_name = commonModel.getFCPanNames();
                                        sBlockCode = commonModel.getFCBlock_Code();
                                        districtCode = commonModel.getFCDist_Code();
                                        distName = commonModel.getFCDistNam();
                                        sBlockName = commonModel.getFCBlockName();

                                        EditMyFacilator(username.getText().toString().trim(), newmobile.getText().toString().trim(),
                                                newemail.getText().toString().trim(), newpassword.getText().toString().trim(),
                                                commonModel.getFCPan_Codes(), commonModel.getFCBlock_Code(), labid,
                                                PREFS_USER_USRUNIQUE_ID, commonModel.getFCID());*/
                                    } else {

                                        /*pan_code =  commonModel.getFCPan_Codes();
                                        pan_name=  commonModel.getFCPanNames();
                                        sBlockCode=  commonModel.getFCBlock_Code();
                                        districtCode=  commonModel.getFCDist_Code();
                                        distName=  commonModel.getFCDistNam();
                                        sBlockName=  commonModel.getFCBlockName();*/


                                        dialog.dismiss();
                                        EditMyFacilator(username.getText().toString().trim(), newmobile.getText().toString().trim(),
                                                newemail.getText().toString().trim(), newpassword.getText().toString().trim(),
                                                pan_code, sBlockCode, labid, PREFS_USER_USRUNIQUE_ID, commonModel.getFCID());
                                    }
                                    return;
                                }
                                if (TextUtils.isEmpty(pan_name)) {
                                    Toast.makeText(FacilitatorAssignedList_Activity.this,
                                            "Panchayat Name(s) Can't be Blank", Toast.LENGTH_SHORT).show();

                                    dialog.dismiss();

                                    /*pan_code =  commonModel.getFCPan_Codes();
                                    pan_name=  commonModel.getFCPanNames();
                                    sBlockCode=  commonModel.getFCBlock_Code();
                                    districtCode=  commonModel.getFCDist_Code();
                                    distName=  commonModel.getFCDistNam();
                                    sBlockName=  commonModel.getFCBlockName();

                                    EditMyFacilator(username.getText().toString().trim(), newmobile.getText().toString().trim(),
                                            newemail.getText().toString().trim(), newpassword.getText().toString().trim(),
                                            commonModel.getFCPan_Codes(), commonModel.getFCBlock_Code(), labid,
                                            PREFS_USER_USRUNIQUE_ID, commonModel.getFCID());*/
                                } else {

                                    dialog.dismiss();
                                    EditMyFacilator(username.getText().toString().trim(), newmobile.getText().toString().trim(),
                                            newemail.getText().toString().trim(), newpassword.getText().toString().trim(),
                                            pan_code, sBlockCode, labid, PREFS_USER_USRUNIQUE_ID, commonModel.getFCID());
                                }
                            }
                        })
                        /*.setNegativeButton("DISCARD", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        })*/
                        .setNeutralButton("DISCARD", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create();
                builder.show();
                builder.setCancelable(false);

                // In Dialog, onclick panchhayat
                tvPanchayat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getpanchayat();
                    }
                });
            }
        });

        findViewById(R.id.addNewTask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentTaskPojoArrayList.size() == 0) {
                    Intent intent = new Intent(FacilitatorAssignedList_Activity.this, AddnewTaskToFC.class);
                    intent.putExtra("FCNAME", commonModel.getName());
                    intent.putExtra("FCID", commonModel.getFCID());
                    intent.putExtra("ALLDATA", commonModel);
                    intent.putExtra("TASKID", current_task_id);
                    intent.putExtra("CURRENTLIST", currentTaskPojoArrayList);
                    overridePendingTransition(R.anim.trans_left_in, R.anim.trans_right_out);
                    startActivity(intent);
                } else {
                    new AlertDialog.Builder(FacilitatorAssignedList_Activity.this)
                            .setTitle("Add New Task")
                            .setMessage("Already an active assigned task to Facilitator. Do you want to assign another?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    Intent intent = new Intent(FacilitatorAssignedList_Activity.this, AddnewTaskToFC.class);
                                    intent.putExtra("FCNAME", commonModel.getName());
                                    intent.putExtra("FCID", commonModel.getFCID());
                                    intent.putExtra("ALLDATA", commonModel);
                                    intent.putExtra("TASKID", current_task_id);
                                    intent.putExtra("CURRENTLIST", currentTaskPojoArrayList);
                                    overridePendingTransition(R.anim.trans_left_in, R.anim.trans_right_out);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .setCancelable(false)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        });
        //getArchiveRecords();

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllRecords();
            }
        });

        //getAllRecords();
    }

    private boolean isValidMobile(String s) {
        // 1) Begins with 0 or 91
        // 2) Then contains 7 or 8 or 9.
        // 3) Then contains 9 digits
        // Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}");
        Pattern p = Pattern.compile("[6-9][0-9]{9}");

        // Pattern class contains matcher() method
        // to find matching between given number
        // and regular expression
        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
    }

    private void CollectedTask() {
        findViewById(R.id.assigned).setBackgroundDrawable(getResources().getDrawable(R.drawable.shape88));
        findViewById(R.id.collected).setBackgroundDrawable(getResources().getDrawable(R.drawable.shape101));
        findViewById(R.id.accepted).setBackgroundDrawable(getResources().getDrawable(R.drawable.shape10));
        findViewById(R.id.archive).setBackgroundDrawable(getResources().getDrawable(R.drawable.shape77));
        isAssigned = false;
        isCollected = true;
        isAccepted = false;
        isArchive = false;
        ((TextView) findViewById(R.id.collected)).setTextColor(Color.WHITE);
        ((TextView) findViewById(R.id.accepted)).setTextColor(Color.parseColor("#0452a2"));
        ((TextView) findViewById(R.id.archive)).setTextColor(Color.parseColor("#0452a2"));
        ((TextView) findViewById(R.id.assigned)).setTextColor(Color.parseColor("#0452a2"));

        if (collectedTaskPojoArrayList.size() != 0) {
            rvAssignList.setVisibility(View.VISIBLE);
            nodata.setVisibility(View.GONE);

            mAdapter = new Assigned_ArchiveTaskAdapter(collectedTaskPojoArrayList,
                    FacilitatorAssignedList_Activity.this, "COLLECT");
            rvAssignList.setAdapter(mAdapter);
            rvAssignList.setLayoutManager(new LinearLayoutManager(FacilitatorAssignedList_Activity.this));
        } else {
            rvAssignList.setVisibility(View.GONE);
            nodata.setVisibility(View.VISIBLE);
        }
    }


    private void AcceptedTask() {
        findViewById(R.id.assigned).setBackgroundDrawable(getResources().getDrawable(R.drawable.shape88));
        findViewById(R.id.collected).setBackgroundDrawable(getResources().getDrawable(R.drawable.shape10));
        findViewById(R.id.accepted).setBackgroundDrawable(getResources().getDrawable(R.drawable.shape101));
        findViewById(R.id.archive).setBackgroundDrawable(getResources().getDrawable(R.drawable.shape77));
        isAssigned = false;
        isCollected = false;
        isAccepted = true;
        isArchive = false;
        ((TextView) findViewById(R.id.collected)).setTextColor(Color.parseColor("#0452a2"));
        ((TextView) findViewById(R.id.accepted)).setTextColor(Color.WHITE);
        ((TextView) findViewById(R.id.archive)).setTextColor(Color.parseColor("#0452a2"));
        ((TextView) findViewById(R.id.assigned)).setTextColor(Color.parseColor("#0452a2"));

        if (acceptedTaskPojoArrayList.size() != 0) {
            rvAssignList.setVisibility(View.VISIBLE);
            nodata.setVisibility(View.GONE);

            mAdapter = new Assigned_ArchiveTaskAdapter(acceptedTaskPojoArrayList,
                    FacilitatorAssignedList_Activity.this, "ACCEPT");
            rvAssignList.setAdapter(mAdapter);
            rvAssignList.setLayoutManager(new LinearLayoutManager(FacilitatorAssignedList_Activity.this));
        } else {
            rvAssignList.setVisibility(View.GONE);
            nodata.setVisibility(View.VISIBLE);
        }
    }


    private void ArchiveTask() {
        findViewById(R.id.assigned).setBackgroundDrawable(getResources().getDrawable(R.drawable.shape88));
        findViewById(R.id.collected).setBackgroundDrawable(getResources().getDrawable(R.drawable.shape10));
        findViewById(R.id.accepted).setBackgroundDrawable(getResources().getDrawable(R.drawable.shape10));
        findViewById(R.id.archive).setBackgroundDrawable(getResources().getDrawable(R.drawable.shape7));
        isAssigned = false;
        isArchive = true;
        isAccepted = false;
        isCollected = false;
        ((TextView) findViewById(R.id.archive)).setTextColor(Color.WHITE);
        ((TextView) findViewById(R.id.accepted)).setTextColor(Color.parseColor("#0452a2"));
        ((TextView) findViewById(R.id.assigned)).setTextColor(Color.parseColor("#0452a2"));
        ((TextView) findViewById(R.id.collected)).setTextColor(Color.parseColor("#0452a2"));

        if (archiveTaskPojoArrayList.size() != 0) {
            rvAssignList.setVisibility(View.VISIBLE);
            nodata.setVisibility(View.GONE);

            mAdapter = new Assigned_ArchiveTaskAdapter(archiveTaskPojoArrayList,
                    FacilitatorAssignedList_Activity.this, "ARCHIVE");
            rvAssignList.setAdapter(mAdapter);
            rvAssignList.setLayoutManager(new LinearLayoutManager(FacilitatorAssignedList_Activity.this));
        } else {
            rvAssignList.setVisibility(View.GONE);
            nodata.setVisibility(View.VISIBLE);
        }
    }

    private void AssignTask() {
        findViewById(R.id.assigned).setBackgroundDrawable(getResources().getDrawable(R.drawable.shape8));
        findViewById(R.id.collected).setBackgroundDrawable(getResources().getDrawable(R.drawable.shape10));
        findViewById(R.id.accepted).setBackgroundDrawable(getResources().getDrawable(R.drawable.shape10));
        findViewById(R.id.archive).setBackgroundDrawable(getResources().getDrawable(R.drawable.shape77));
        isAssigned = true;
        isAccepted = false;
        isArchive = false;
        isCollected = false;
        ((TextView) findViewById(R.id.assigned)).setTextColor(Color.WHITE);
        ((TextView) findViewById(R.id.accepted)).setTextColor(Color.parseColor("#0452a2"));
        ((TextView) findViewById(R.id.archive)).setTextColor(Color.parseColor("#0452a2"));
        ((TextView) findViewById(R.id.collected)).setTextColor(Color.parseColor("#0452a2"));
        //findViewById(R.id.assignLayout).setVisibility(View.VISIBLE);
        //findViewById(R.id.archiveLayout).setVisibility(View.GONE);

        if (currentTaskPojoArrayList.size() != 0) {
            current_task_id = currentTaskPojoArrayList.get(0).getTask_Id();
            rvAssignList.setVisibility(View.VISIBLE);
            nodata.setVisibility(View.GONE);
            mAdapter = new Assigned_ArchiveTaskAdapter(currentTaskPojoArrayList,
                    FacilitatorAssignedList_Activity.this, "CURRENT");
            rvAssignList.setAdapter(mAdapter);
            rvAssignList.setLayoutManager(new LinearLayoutManager(FacilitatorAssignedList_Activity.this));
        } else {
            rvAssignList.setVisibility(View.GONE);
            nodata.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllRecords();
    }

    private void showMyDialog(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        if (s.equalsIgnoreCase("1"))
            dialog.setMessage("Do you want to Deactivate the facilitator?");
        else
            dialog.setMessage("Do you want to Activate the facilitator?");
        dialog.show();
        builder.setCancelable(false);
    }

    private void getAllRecords() {

        allTaskPojoArrayList = new ArrayList<>();
        currentTaskPojoArrayList = new ArrayList<>();
        archiveTaskPojoArrayList = new ArrayList<>();
        acceptedTaskPojoArrayList = new ArrayList<>();
        collectedTaskPojoArrayList = new ArrayList<>();

        final LoadingDialog loadingDialog = new LoadingDialog(this);

        boolean isConnected = CGlobal.getInstance().isConnected(this);
        if (isConnected) {

            loadingDialog.showDialog();

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
                    //.addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            PostInterface api = retrofit.create(PostInterface.class);

            final Map<String, String> mapdata = new HashMap<>();
            mapdata.put("FCID", commonModel.getFCID());
            mapdata.put("AppKey", "Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3");
            //Log.d("TEST", commonModel.getFCID());

            Call<List<AssignedArchiveTaskPojo>> call = api.GetAssignHabitationListFCWiseForLab(mapdata);

            call.enqueue(new Callback<List<AssignedArchiveTaskPojo>>() {
                @Override
                public void onResponse(Call<List<AssignedArchiveTaskPojo>> call, Response<List<AssignedArchiveTaskPojo>> response) {
                    //Log.i("ResponsestringAS!!", response.body().size() + "");
                    loadingDialog.hideDialog();

                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            allTaskPojoArrayList.addAll(response.body());

                            for (int i = 0; i < allTaskPojoArrayList.size(); i++) {

                                if (!TextUtils.isEmpty(allTaskPojoArrayList.get(i).getTestCompletedDate())) {
                                    archiveTaskPojoArrayList.add(allTaskPojoArrayList.get(i));// Test COmpleted
                                    if (currentTaskPojoArrayList.size() != 0) {
                                        isAssigned = true;
                                        isArchive = false;
                                        isAccepted = false;
                                        isCollected = false;
                                    } else if (collectedTaskPojoArrayList.size() != 0) {
                                        isAssigned = false;
                                        isArchive = false;
                                        isAccepted = false;
                                        isCollected = true;
                                    } else if (acceptedTaskPojoArrayList.size() != 0) {
                                        isAssigned = false;
                                        isArchive = false;
                                        isAccepted = true;
                                        isCollected = false;
                                    } else {
                                        isAssigned = false;
                                        isArchive = true;
                                        isAccepted = false;
                                        isCollected = false;
                                    }

                                    if (isArchive) {
                                        ArchiveTask();
                                    }

                                    if (allTaskPojoArrayList.size() == 0) {
                                        isAssigned = true;
                                        isArchive = false;
                                        isAccepted = false;
                                        isCollected = false;
                                        AssignTask();
                                    }
                                } else if (!TextUtils.isEmpty(allTaskPojoArrayList.get(i).getFecilatorCompletedDate())) {
                                    acceptedTaskPojoArrayList.add(allTaskPojoArrayList.get(i));// FecilatorCompletedDate
                                    if (currentTaskPojoArrayList.size() != 0) {
                                        isAssigned = true;
                                        isArchive = false;
                                        isAccepted = false;
                                        isCollected = false;
                                    } else if (collectedTaskPojoArrayList.size() != 0) {
                                        isAssigned = false;
                                        isArchive = false;
                                        isAccepted = false;
                                        isCollected = true;
                                    } else if (acceptedTaskPojoArrayList.size() != 0) {
                                        isAssigned = false;
                                        isArchive = false;
                                        isAccepted = true;
                                        isCollected = false;
                                    } else {
                                        isAssigned = false;
                                        isArchive = true;
                                        isAccepted = false;
                                        isCollected = false;
                                    }

                                    if (isAccepted) {
                                        AcceptedTask();
                                    }

                                    if (allTaskPojoArrayList.size() == 0) {
                                        isAssigned = true;
                                        isArchive = false;
                                        isAccepted = false;
                                        isCollected = false;
                                        AssignTask();
                                    }
                                } else if (!TextUtils.isEmpty(allTaskPojoArrayList.get(i).getFormSubmissionDate())) {
                                    collectedTaskPojoArrayList.add(allTaskPojoArrayList.get(i)); // FormSubmissionDate
                                    if (currentTaskPojoArrayList.size() != 0) {
                                        isAssigned = true;
                                        isArchive = false;
                                        isAccepted = false;
                                        isCollected = false;
                                    } else if (collectedTaskPojoArrayList.size() != 0) {
                                        isAssigned = false;
                                        isArchive = false;
                                        isAccepted = false;
                                        isCollected = true;
                                    } else if (acceptedTaskPojoArrayList.size() != 0) {
                                        isAssigned = false;
                                        isArchive = false;
                                        isAccepted = true;
                                        isCollected = false;
                                    } else {
                                        isAssigned = false;
                                        isArchive = true;
                                        isAccepted = false;
                                        isCollected = false;
                                    }

                                    if (isCollected) {
                                        CollectedTask();
                                    }

                                    if (allTaskPojoArrayList.size() == 0) {
                                        isAssigned = true;
                                        isArchive = false;
                                        isAccepted = false;
                                        isCollected = false;
                                        AssignTask();
                                    }
                                } else {
                                    Collections.sort(allTaskPojoArrayList, new Comparator<AssignedArchiveTaskPojo>() {
                                        @Override
                                        public int compare(AssignedArchiveTaskPojo lhs, AssignedArchiveTaskPojo rhs) {
                                            return lhs.getVillageCode().compareTo(rhs.getVillageCode());
                                        }
                                    });
                                    if (!allTaskPojoArrayList.get(i).getVillageCode().equalsIgnoreCase(villageCode)) {
                                        villageCode = allTaskPojoArrayList.get(i).getVillageCode();
                                        if(!TextUtils.isEmpty(allTaskPojoArrayList.get(i).getVillageName())) {
                                            get_horizon_head_site(allTaskPojoArrayList.get(i).getDistCode(),
                                                    allTaskPojoArrayList.get(i).getBlockCode(),
                                                    allTaskPojoArrayList.get(i).getPanCode(),
                                                    allTaskPojoArrayList.get(i).getVillageCode(),
                                                    allTaskPojoArrayList.get(i));
                                        }
                                    }
                                }
                            }
                        } else {
                            Log.i("onEmptyResponse", "Returned empty response");
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<AssignedArchiveTaskPojo>> call, Throwable t) {
                    loadingDialog.hideDialog();
                    Log.i("onEmptyResponse", "Returned empty response");
                }
            });
        } else {
            new androidx.appcompat.app.AlertDialog.Builder(FacilitatorAssignedList_Activity.this)
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

    private void ActiveInactivefacilitator(final String status) {

        final LoadingDialog loadingDialog = new LoadingDialog(this);

        boolean isConnected = CGlobal.getInstance().isConnected(this);
        if (isConnected) {

            loadingDialog.showDialog();

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
                    //.addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            PostInterface api = retrofit.create(PostInterface.class);

            final Map<String, String> mapdata = new HashMap<>();
            mapdata.put("FCID", commonModel.getFCID());
            mapdata.put("AppKey", "Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3");

            Call<ResponseBody> call = api.ActiveInactivefacilitator(mapdata);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        //JSONObject jsonObject = new JSONObject(response.body().string().replaceAll("\r\n", ""));
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        //Log.i("ResponsestringSWITCH!!", jsonObject.toString().trim());
                        if (jsonObject.getBoolean("response")) {
                            if (status.equalsIgnoreCase("I")) {
                                //finish();
                                Toast.makeText(FacilitatorAssignedList_Activity.this, "Deactivated Successfully", Toast.LENGTH_SHORT).show();
                                findViewById(R.id.addNewTask).setVisibility(View.GONE);
                            } else {
                                Toast.makeText(FacilitatorAssignedList_Activity.this, "Activated Successfully", Toast.LENGTH_SHORT).show();
                                findViewById(R.id.addNewTask).setVisibility(View.VISIBLE);
                            }
                        } else {
                            Log.i("onEmptyResponse", "Returned empty response");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    loadingDialog.hideDialog();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    loadingDialog.hideDialog();
                    Log.i("onEmptyResponse", "Returned empty response");
                }
            });
        } else {
            new androidx.appcompat.app.AlertDialog.Builder(FacilitatorAssignedList_Activity.this)
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

    private void EditMyFacilator(
            final String usrname, final String umobile, final String uemail, final String upassword, final String pan_code,
            final String sBlockCode, final String labid, final String SampleCollectorId, final String fcid) {

        final LoadingDialog loadingDialog = new LoadingDialog(this);

        boolean isConnected = CGlobal.getInstance().isConnected(this);
        if (isConnected) {

            loadingDialog.showDialog();

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
                    //.addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            PostInterface api = retrofit.create(PostInterface.class);

            final Map<String, String> mapdata = new HashMap<>();
            mapdata.put("FCName", usrname);
            mapdata.put("Mobile", umobile);
            mapdata.put("Email", uemail);
            mapdata.put("Password", upassword);
            mapdata.put("Pan_Codes", pan_code);
            mapdata.put("PanNames", pan_name);
            mapdata.put("Block_Code", sBlockCode);
            mapdata.put("Dist_Code", districtCode);
            mapdata.put("DistName", distName);
            mapdata.put("BlockName", sBlockName);
            mapdata.put("LabID", labid);
            mapdata.put("SampleCollectorId", SampleCollectorId);
            mapdata.put("FCID", fcid);
            mapdata.put("IsActive", "true");
            mapdata.put("UserType", "facilitator");
            mapdata.put("AppKey", "Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3");

            Call<ResponseBody> call = api.EditMyFacilator(mapdata);

            call.enqueue(new Callback<ResponseBody>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        //JSONObject jsonObject = new JSONObject(response.body().string().replaceAll("\r\n\t", ""));
                        JSONObject jsonObject = new JSONObject(response.body().string().trim());
                        //Log.i("ResponsestringUpdate!!2", jsonObject.toString());
                        if (jsonObject.getBoolean("response")) {
                            /*name.setText("\uD83D\uDC64 Name : " + uname);
                            email.setText("✉ Email : " + uemail);
                            pass.setText("\uD83D\uDD10 Password : " + upassword.replaceAll(".", "x"));
                            //mobile.setText("\uD83D\uDCF1 Mobile : " + "+91 ----------");
                            mobile.setText("\uD83D\uDCF1 Mobile : " + umobile);*/

                            name.setText("\uD83D\uDC64 Name : " + usrname);
                            email.setText("✉ Email : " + uemail);
                            pass.setText(" \uD83D\uDD10 Password : " + upassword.replaceAll(".", "x"));
                            //mobile.setText("\uD83D\uDCF1 Mobile : " + "+91 ----------");
                            mobile.setText("\uD83D\uDCF1 Mobile : " + umobile);

                            // Update current record
                            commonModel.setFCPanNames(pan_name);
                            commonModel.setFCPan_Codes(pan_code);
                            commonModel.setFCBlockName(sBlockName);
                            commonModel.setFCBlock_Code(sBlockCode);
                            commonModel.setName(usrname);
                            commonModel.setEmail(uemail);
                            commonModel.setPassword(upassword);
                            commonModel.setMobile(umobile);

                            selectedBlockCode = commonModel.getFCBlock_Code();
                            selectedBlockName = commonModel.getFCBlockName();
                            try {
                                selectedPanCodes = commonModel.getFCPan_Codes().split("\\s*,\\s*");
                            } catch (Exception e) {
                                selectedPanCodes[0] = commonModel.getFCPan_Codes();
                            }
                            //loadSourceData();

                            Toast.makeText(FacilitatorAssignedList_Activity.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                            //finish();
                            //startActivity(getIntent());
                        } else {
                            Toast.makeText(FacilitatorAssignedList_Activity.this, "Unable to update!", Toast.LENGTH_SHORT).show();
                            //Log.i("onEmptyResponse", "Returned empty response");
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    loadingDialog.hideDialog();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    loadingDialog.hideDialog();
                    Toast.makeText(FacilitatorAssignedList_Activity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    //Log.i("onEmptyResponse", "Returned empty response");
                }
            });
        } else {
            new androidx.appcompat.app.AlertDialog.Builder(FacilitatorAssignedList_Activity.this)
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

    private void loadSourceData() {

        responsePanArrayList = new ArrayList<>();
        blockCode = new ArrayList<>();
        blockName = new ArrayList<>();
        cmaPanchayatCode = new ArrayList<>();
        cmaPanchayatName = new ArrayList<>();

        final ProgressDialog progressdialog;
        progressdialog = new ProgressDialog(this);
        progressdialog.setMessage("Please Wait....");
        progressdialog.setCancelable(false);

        boolean isConnected = CGlobal.getInstance().isConnected(this);
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
                    .baseUrl(PostInterface.URL_RNJ)
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            String labcode = CGlobal.getInstance().getPersistentPreference(this)
                    .getString(Constants.PREFS_USER_LAB_CODE, "");
            String sdistrictCode = CGlobal.getInstance().getPersistentPreference(this)
                    .getString(Constants.PREFS_USER_DISTRICT_CODE, "");

            PostInterface api = retrofit.create(PostInterface.class);

            Call<List<ResponsePanchyat>> call = api.lab_relation(labcode, sdistrictCode);

            call.enqueue(new Callback<List<ResponsePanchyat>>() {
                @Override
                public void onResponse(Call<List<ResponsePanchyat>> call, retrofit2.Response<List<ResponsePanchyat>> response) {
                    //Log.i("Responsestring", response.message());

                    if (response.body() != null) {
                        responsePanArrayList.addAll(response.body());
                        districtCode = responsePanArrayList.get(0).getDistCode();
                        distName = responsePanArrayList.get(0).getDistName();
                        //tvDistrictName.setText(responsePanArrayList.get(0).getDistName());

                        for (int i = 0; i < responsePanArrayList.size(); i++) {
                            if (!blockCode.contains(responsePanArrayList.get(i).getBlockCode())) {
                                blockCode.add(responsePanArrayList.get(i).getBlockCode());
                                blockName.add(responsePanArrayList.get(i).getBlockName());
                            }
                            if (!cmaPanchayatCode.contains(responsePanArrayList.get(i).getPanCode())) {
                                cmaPanchayatCode.add(responsePanArrayList.get(i).getPanCode());
                                cmaPanchayatName.add(responsePanArrayList.get(i).getPanchayatName());
                            }
                        }

                        getBlock();
                    } else {
                        //Log.i("onEmptyResponse", "Returned empty jsonResponse");
                        Toast.makeText(FacilitatorAssignedList_Activity.this, "Unable to get data", Toast.LENGTH_SHORT).show();
                    }
                    progressdialog.dismiss();
                }

                @Override
                public void onFailure(Call<List<ResponsePanchyat>> call, Throwable t) {
                    //Log.i("onEmptyResponse", "Returned empty jsonResponse");
                    progressdialog.dismiss();
                    Toast.makeText(FacilitatorAssignedList_Activity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            new androidx.appcompat.app.AlertDialog.Builder(FacilitatorAssignedList_Activity.this)
                    .setMessage("Please check your Internet connection. Please try again")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    private void getBlock() {

        MySpinnerAdapter block_adapter = new MySpinnerAdapter(this, blockName);
        spBlock.setAdapter(block_adapter);
        spBlock.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sBlockName = blockName.get(position);
                sBlockCode = blockCode.get(position);
                pan_name = "";
                pan_code = "";
                tvPanchayat.setText("");

                selectedtruefalse = new boolean[0];
                //Arrays.fill( example, null );

                for (int a = 0; a < responsePanArrayList.size(); a++) {
                    if (sBlockCode.equalsIgnoreCase(responsePanArrayList.get(a).getBlockCode())) {
                        cmaPanchayatName.add(responsePanArrayList.get(a).getPanchayatName());
                        cmaPanchayatCode.add(responsePanArrayList.get(a).getPanCode());
                    }
                }

                selectedtruefalse = new boolean[cmaPanchayatCode.size()];
                for (int i = 0; i < selectedtruefalse.length; i++) {
                    selectedtruefalse[i] = false;
                }
                //Toast.makeText(getContext(), "" + selectedtruefalse.length, Toast.LENGTH_SHORT).show();
                // selectedPanCodes
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // default selection of previously saved block
        spBlock.setSelection(blockCode.indexOf(selectedBlockCode));
    }

    private void getpanchayat() {

        cmaPanchayatName = new ArrayList<>();
        cmaPanchayatCode = new ArrayList<>();

        if (!TextUtils.isEmpty(sBlockCode)) {

            alertDialogItems1 = new String[]{};
            alertdialogbuilder1 = new android.app.AlertDialog.Builder(this);

            for (int a = 0; a < responsePanArrayList.size(); a++) {
                if (sBlockCode.equalsIgnoreCase(responsePanArrayList.get(a).getBlockCode())) {
                    cmaPanchayatName.add(responsePanArrayList.get(a).getPanchayatName());
                    cmaPanchayatCode.add(responsePanArrayList.get(a).getPanCode());
                }
            }

            alertDialogItems1 = new String[cmaPanchayatCode.size()];
            for (int b = 0; b < cmaPanchayatCode.size(); b++) {
                alertDialogItems1[b] = cmaPanchayatName.get(b);
                //selectedtruefalse[b] = false;
            }

            alertdialogbuilder1.setMultiChoiceItems(alertDialogItems1, selectedtruefalse, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                    if (isChecked) {
                        selectedtruefalse[which] = true;
                    }
                }
            });

            alertdialogbuilder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int a = 0;
                    pan_name = "";
                    pan_code = "";
                    while (a < selectedtruefalse.length) {
                        boolean value = selectedtruefalse[a];
                        if (value) {
                            String name1 = cmaPanchayatName.get(a);
                            String panCode = cmaPanchayatCode.get(a);
                            if (!TextUtils.isEmpty(pan_name)) {
                                pan_name = pan_name + "," + name1;
                                pan_code = pan_code + "," + panCode;
                            } else {
                                pan_name = name1;
                                pan_code = panCode;
                            }
                        }
                        a++;
                    }
                    tvPanchayat.setText(pan_name);
                    //Toast.makeText(getContext(), pan_code, Toast.LENGTH_SHORT).show();
                }
            });

           /* alertdialogbuilder1.setSingleChoiceItems(alertDialogItems1, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Toast.makeText(getContext(), alertDialogItems[which], Toast.LENGTH_SHORT).show();
                    pan_name = alertDialogItems1[which];
                    pan_code = cmaPanchayatCode.get(which);
                }
            });*/
            alertdialogbuilder1.setCancelable(false);
            alertdialogbuilder1.setTitle("Select Panchayat");

            /*alertdialogbuilder1.setPositiveButton("Choose", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    if (!tvPanchayat.getText().toString().equalsIgnoreCase(pan_name)) {
                        vill_code = "";
                        vill_name = "";
                        tvVillage.setText("");

                        hab_code = "";
                        hab_name = "";
                        tvHabitation.setText("");
                    }
                    tvPanchayat.setText(pan_name);
                    //pan_code = cmaPanchayatCode.get(which);
                    Log.d("pan_code", pan_code);
                }
            });*/

            alertdialogbuilder1.setNeutralButton("Discard", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    pan_name = "";
                    pan_code = "";
                }
            });

            android.app.AlertDialog dialog = alertdialogbuilder1.create();
            dialog.show();
        } else {
            //Toast.makeText(getActivity(), "Select Block", Toast.LENGTH_SHORT).show();
            showMessage("Please Select Block");
        }
    }

    private void showMessage(String msg) {
        Snackbar.make(findViewById(android.R.id.content),
                "⚠ " + msg, Snackbar.LENGTH_LONG).show();
    }

    public boolean isInputDataValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent();
        intent.putExtra("MESSAGE", "1");
        setResult(2, intent);
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_left_out);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            Intent intent = new Intent();
            intent.putExtra("MESSAGE", "1");
            setResult(2, intent);
            overridePendingTransition(R.anim.trans_right_in, R.anim.trans_left_out);
            finish();
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    int sCount1 = 0, sCount2 = 0;

    private void get_horizon_head_site(String dist_code, String block_code,
                                       String pan_code, String vill_code,
                                       AssignedArchiveTaskPojo allTaskPojoArrayList1) {
        String sUrl = "https://phed.sunandainternational.org/api/get-horizon-head-site?dist_code="
                + dist_code + "&block_code=" + block_code + "&pan_code=" + pan_code
                + "&vill_code=" + vill_code + "";

        StringRequest postRequest = new StringRequest(com.android.volley.Request.Method.GET,
                sUrl, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String resCode = jsonObject.getString("resCode");
                    String message = jsonObject.getString("message");
                    String error = jsonObject.getString("error");
                    if (jsonObject.has("data")) {
                        JSONArray dataJSONArray = jsonObject.getJSONArray("data");
                        sCount1 = dataJSONArray.length();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                get_horizon_source_by_vill(dist_code, block_code, pan_code,
                        vill_code, sCount1, allTaskPojoArrayList1);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                get_horizon_source_by_vill(dist_code, block_code, pan_code,
                        vill_code, 0, allTaskPojoArrayList1);
                try {
                    new AlertDialog.Builder(FacilitatorAssignedList_Activity.this)
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
        CGlobal.getInstance().addVolleyRequest(postRequest, false, FacilitatorAssignedList_Activity.this);
    }

    private void get_horizon_source_by_vill(String dist_code, String block_code,
                                            String pan_code, String vill_code,
                                            int count, AssignedArchiveTaskPojo allTaskPojoArrayList1) {
        String sUrl = "https://phed.sunandainternational.org/api/get-horizon-source-by-vill?dist_code="
                + dist_code + "&block_code=" + block_code + "&pan_code=" + pan_code
                + "&vill_code=" + vill_code + "";

        StringRequest postRequest = new StringRequest(com.android.volley.Request.Method.GET,
                sUrl, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String resCode = jsonObject.getString("resCode");
                    String message = jsonObject.getString("message");
                    String error = jsonObject.getString("error");
                    JSONObject jsonObjectdata = jsonObject.getJSONObject("data");
                    if (jsonObjectdata.has("data")) {
                        JSONArray dataJSONArray = jsonObjectdata.getJSONArray("data");
                        sCount2 = dataJSONArray.length();
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }

                if (allTaskPojoArrayList1.getDistCode().equalsIgnoreCase(dist_code)) {
                    if (allTaskPojoArrayList1.getBlockCode().equalsIgnoreCase(block_code)) {
                        if (allTaskPojoArrayList1.getPanCode().equalsIgnoreCase(pan_code)) {
                            if (allTaskPojoArrayList1.getVillageCode().equalsIgnoreCase(vill_code)) {
                                int xCount = 0;
                                if (!TextUtils.isEmpty(allTaskPojoArrayList1.getNoOfSource())) {
                                    if (!TextUtils.isEmpty(allTaskPojoArrayList1.getPws_status())) {
                                        if (allTaskPojoArrayList1.getPws_status().equalsIgnoreCase("YES")) {
                                            xCount = sCount2 + count;
                                        } else {
                                            xCount = Integer.parseInt(allTaskPojoArrayList1.getNoOfSource());
                                        }
                                    }
                                } else {
                                    if (!TextUtils.isEmpty(allTaskPojoArrayList1.getPws_status())) {
                                        if (allTaskPojoArrayList1.getPws_status().equalsIgnoreCase("YES")) {
                                            xCount = sCount2 + count;
                                        } else {
                                            xCount = 0;
                                        }
                                    }
                                }
                                allTaskPojoArrayList1.setNoOfSource(String.valueOf(xCount));
                            }
                        }
                    }
                }
                currentTaskPojoArrayList.add(allTaskPojoArrayList1);// CreatedDate
                if (currentTaskPojoArrayList.size() != 0) {
                    isAssigned = true;
                    isArchive = false;
                    isAccepted = false;
                    isCollected = false;
                } else if (collectedTaskPojoArrayList.size() != 0) {
                    isAssigned = false;
                    isArchive = false;
                    isAccepted = false;
                    isCollected = true;
                } else if (acceptedTaskPojoArrayList.size() != 0) {
                    isAssigned = false;
                    isArchive = false;
                    isAccepted = true;
                    isCollected = false;
                } else {
                    isAssigned = false;
                    isArchive = true;
                    isAccepted = false;
                    isCollected = false;
                }

                if (isAssigned) {
                    AssignTask();
                }

                if (allTaskPojoArrayList.size() == 0) {
                    isAssigned = true;
                    isArchive = false;
                    isAccepted = false;
                    isCollected = false;
                    AssignTask();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (currentTaskPojoArrayList.size() != 0) {
                    isAssigned = true;
                    isArchive = false;
                    isAccepted = false;
                    isCollected = false;
                } else if (collectedTaskPojoArrayList.size() != 0) {
                    isAssigned = false;
                    isArchive = false;
                    isAccepted = false;
                    isCollected = true;
                } else if (acceptedTaskPojoArrayList.size() != 0) {
                    isAssigned = false;
                    isArchive = false;
                    isAccepted = true;
                    isCollected = false;
                } else {
                    isAssigned = false;
                    isArchive = true;
                    isAccepted = false;
                    isCollected = false;
                }

                if (isAssigned) {
                    AssignTask();
                }

                if (allTaskPojoArrayList.size() == 0) {
                    isAssigned = true;
                    isArchive = false;
                    isAccepted = false;
                    isCollected = false;
                    AssignTask();
                }
                try {
                    new AlertDialog.Builder(FacilitatorAssignedList_Activity.this)
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
        CGlobal.getInstance().addVolleyRequest(postRequest, false, FacilitatorAssignedList_Activity.this);
    }
}
