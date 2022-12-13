package com.sunanda.newroutine.application.somenath.view;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.adapter.SourceDataAdapter;
import com.sunanda.newroutine.application.modal.CommonModel;
import com.sunanda.newroutine.application.somenath.myadapter.MultiAdapterForHabitation;
import com.sunanda.newroutine.application.somenath.myadapter.NewVillageAdapter;
import com.sunanda.newroutine.application.somenath.pojo.AssignedArchiveTaskPojo;
import com.sunanda.newroutine.application.somenath.pojo.HabitationPojo;
import com.sunanda.newroutine.application.somenath.pojo.ResponsePanchyat;
import com.sunanda.newroutine.application.somenath.pojo.ResponseVillage;
import com.sunanda.newroutine.application.somenath.pojo.SourceForLaboratoryPOJO;
import com.sunanda.newroutine.application.util.CGlobal;
import com.sunanda.newroutine.application.util.Constants;
import com.sunanda.newroutine.application.util.LoadingDialog;
import com.sunanda.newroutine.application.util.PostInterface;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddnewTaskToFC extends AppCompatActivity {

    boolean backAction = false;

    ResponseVillage responseVillage = new ResponseVillage();

    ArrayList<ResponsePanchyat> responsePanArrayList;
    CommonModel commonModel;
    ImageView info;

    ArrayList<String> cmaBlockId = new ArrayList<>();
    ArrayList<String> blockName = new ArrayList<>();
    ArrayList<String> blockCode = new ArrayList<>();

    ArrayList<String> cmaPanchayatId = new ArrayList<>();
    ArrayList<String> cmaPanchayatName = new ArrayList<>();
    ArrayList<String> cmaPanchayatCode = new ArrayList<>();

    ArrayList<String> cmaVillageId = new ArrayList<>();
    ArrayList<String> cmaVillageName = new ArrayList<>();
    ArrayList<String> cmaVillageCode = new ArrayList<>();

    ArrayList<String> cmaHabitationId = new ArrayList<>();
    ArrayList<String> cmaHabitationName = new ArrayList<>();
    ArrayList<String> cmaHabitationCode = new ArrayList<>();

    ArrayList<HabitationPojo> habitationPojoArrayList = new ArrayList<>();
    ArrayList<HabitationPojo> habitationPojoArrayListIsNotTestHab = new ArrayList<>();
    ArrayList<HabitationPojo> habitationPojoArrayListAlredyAssign = new ArrayList<>();
    ArrayList<HabitationPojo> habitationPojoArrayListIsPreviousFinancialYearsSource = new ArrayList<>();
    ArrayList<HabitationPojo> habitationPojoArrayListIsCurrentFinancialYearsSource = new ArrayList<>();
    ArrayList<AssignedArchiveTaskPojo> currentTaskPojoArrayList;

    SourceDataAdapter sourceDataAdapter;

    Spinner spBlock;
    LoadingDialog loadingDialog;
    TextView tvLabName, tvDistrictName, tvPanchayat, tvVillage, tvHabitation, location, labDetail, tvBlock;
    CheckBox cbPwssVillage, cbNonPwssVillage;
    Button btnNext;
    AlertDialog.Builder alertdialogbuilder1;
    String[] alertDialogItems1;
    String pan_name = "", pan_code = "", vill_name = "";
    String districtCode = "", vill_code = "", hab_code = "", hab_name = "", facilitatorName = "", facilitatorId = "";
    String sVillage = "";
    String selectedPanCodes[], selectedBlockCode = "", selectedBlockName = "";

    private EasyFlipView easyFlipView;
    private RecyclerView sourceRecycler;
    ProgressDialog progressdialog;
    Switch simpleSwitch;
    String statusSwitch;
    TextView title;
    CustomListViewDialog customDialog;

    RecyclerView recyclerView;
    MultiAdapterForHabitation adapter;

    ArrayList<SourceForLaboratoryPOJO> sourceForLaboratoryPOJOArrayList;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew_task_to_fc);

        info = findViewById(R.id.info);
        info.setVisibility(View.GONE);
        spBlock = findViewById(R.id.spBlock);
        commonModel = (CommonModel) getIntent().getSerializableExtra("ALLDATA");
        currentTaskPojoArrayList = (ArrayList<AssignedArchiveTaskPojo>) getIntent().getSerializableExtra("CURRENTLIST");

        selectedBlockCode = commonModel.getFCBlock_Code();
        selectedBlockName = commonModel.getFCBlockName();
        try {
            selectedPanCodes = commonModel.getFCPan_Codes().split("\\s*,\\s*");
        } catch (Exception e) {
            selectedPanCodes[0] = commonModel.getFCPan_Codes();
        }
        tvBlock = findViewById(R.id.tvBlock);

        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        loadingDialog = new LoadingDialog(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        title = findViewById(R.id.title);
        facilitatorName = getIntent().getStringExtra("FCNAME");
        facilitatorId = getIntent().getStringExtra("FCID");
        title.setText("Assign Task to : " + getIntent().getStringExtra("FCNAME"));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        final Drawable upArrow = getResources().getDrawable(R.drawable.leftarrow);
        upArrow.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.leftarrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        findViewById(R.id.facilitatorSpinner).setVisibility(View.GONE);

        tvPanchayat = findViewById(R.id.tvPanchayat);
        tvVillage = findViewById(R.id.tvVillage);
        tvLabName = findViewById(R.id.tvLabName);
        tvDistrictName = findViewById(R.id.tvDistrictName);
        tvHabitation = findViewById(R.id.tvHabitation);
        sourceRecycler = findViewById(R.id.sourceRecycler);
        location = findViewById(R.id.location);
        labDetail = findViewById(R.id.labDetail);

        cbPwssVillage = findViewById(R.id.cbPwssVillage);
        cbNonPwssVillage = findViewById(R.id.cbNonPwssVillage);
        btnNext = findViewById(R.id.btnNext);

        simpleSwitch = (Switch) findViewById(R.id.simpleSwitch);
        if (simpleSwitch.isChecked())
            statusSwitch = simpleSwitch.getTextOn().toString();
        else
            statusSwitch = simpleSwitch.getTextOff().toString();
        simpleSwitch.setText(statusSwitch);
        simpleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (simpleSwitch.isChecked()) {
                    statusSwitch = simpleSwitch.getTextOn().toString();
                    simpleSwitch.setText(statusSwitch);
                    sourceDataAdapter.selectAll();
                } else {
                    statusSwitch = simpleSwitch.getTextOff().toString();
                    simpleSwitch.setText(statusSwitch);
                    sourceDataAdapter.unselectall();
                }
            }
        });


        tvLabName.setText(CGlobal.getInstance().getPersistentPreference(this)
                .getString(Constants.PREFS_USER_LAB_NAME, ""));

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (facilitatorName.contains("Choose")) {
                    //Toast.makeText(getActivity(), "Please Select Facilitator", Toast.LENGTH_SHORT).show();
                    showMessage("Please Select Facilitator");
                } else if (TextUtils.isEmpty(vill_code)) {
                    //Toast.makeText(getActivity(), "Please Select Village", Toast.LENGTH_SHORT).show();
                    showMessage("Please Select Village");
                } else {
                    backAction = false;
                    //getHabitation();
                    submitData("");
                }
            }
        });

        cbNonPwssVillage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cbNonPwssVillage.isChecked()) {
                    sVillage = "NO";
                    cbPwssVillage.setChecked(false);
                }
            }
        });

        cbPwssVillage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cbPwssVillage.isChecked()) {
                    sVillage = "YES";
                    cbNonPwssVillage.setChecked(false);
                }
            }
        });


        findViewById(R.id.btnPrev).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backAction = false;
                info.setVisibility(View.GONE);
                easyFlipView.flipTheView();
            }
        });


        findViewById(R.id.btnPrev).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backAction = false;
                info.setVisibility(View.GONE);
                easyFlipView.flipTheView();
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMyDialog();
            }
        });

        findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "SUBMIT", Toast.LENGTH_SHORT).show();
                //submitData();
                new AlertDialog.Builder(AddnewTaskToFC.this)
                        //.setTitle("Do you want to submit?")
                        .setMessage("Are you sure you want to submit this entry?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (adapter.getSelected().size() > 0) {
                                    StringBuilder stringBuilder = new StringBuilder();
                                    for (int i = 0; i < adapter.getSelected().size(); i++) {
                                        /*stringBuilder.append(adapter.getSelected().get(i).getName());
                                        stringBuilder.append("-");
                                        stringBuilder.append(adapter.getSelected().get(i).getId());
                                        stringBuilder.append("-");
                                        stringBuilder.append(adapter.getSelected().get(i).getCode());
                                        stringBuilder.append("\n");*/
                                        for (int j = 0; j < currentTaskPojoArrayList.size(); j++) {
                                            if (adapter.getSelected().get(i).getCode().
                                                    equalsIgnoreCase(currentTaskPojoArrayList.get(j).getHabCode()) &&
                                                    adapter.getSelected().get(i).getVillage_Code()
                                                            .equalsIgnoreCase(currentTaskPojoArrayList.get(j).getVillageCode())) {

                                                new AlertDialog.Builder(AddnewTaskToFC.this)
                                                        .setMessage("Please select different Habitation to assign. " +
                                                                adapter.getSelected().get(i).getName() +
                                                                " already assigned to same Facilitator.")
                                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                dialog.dismiss();
                                                            }
                                                        })
                                                        .setCancelable(false)
                                                        .show();

                                                return;
                                            }
                                        }

                                        stringBuilder.append(adapter.getSelected().get(i).getCode());
                                        if (i < adapter.getSelected().size() - 1)
                                            stringBuilder.append(",");
                                    }
                                    //showToast(stringBuilder.toString().trim());
                                    /*Log.d("TEST!!", "sLabId" + "#" + facilitatorId + "#" + stringBuilder
                                            + "#" + districtCode + "#" + selectedBlockCode + "#" + pan_code + "#" + vill_code
                                            + "#" + getIntent().getStringExtra("TASKID"));*/

                                    //submitData(String.valueOf(stringBuilder));
                                } else {
                                    //showToast("Please select Habitation to assign");
                                    showMessage("Please Select Habitation to assign");
                                }
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setCancelable(false)
                        //.setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });


        easyFlipView = (EasyFlipView) findViewById(R.id.easyFlipView);
        easyFlipView.setFlipDuration(1000);
        //easyFlipView.setFlipEnabled(true);

        // Smooth scrolling recyclerview
        sourceRecycler.setNestedScrollingEnabled(false);

        tvPanchayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getpanchayat();
            }
        });
        tvVillage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getVillage(sVillage);
            }
        });

        loadSourceData();
    }

    private void showMyDialog() {
        final Dialog dialog = new Dialog(this, R.style.AppTheme_NoActionBar);

        // Setting dialogview
        Window window = dialog.getWindow();
        window.setGravity(Gravity.TOP | Gravity.CENTER);

        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.mydialog);
        dialog.show();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
    }

    private void getHabitation() {

        //Log.d("LABID", sLabId + " " + vill_code);

        cmaVillageName = new ArrayList<>();
        cmaVillageCode = new ArrayList<>();
        sourceForLaboratoryPOJOArrayList = new ArrayList<>();

        cmaHabitationId = new ArrayList<>();
        cmaHabitationName = new ArrayList<>();
        cmaHabitationCode = new ArrayList<>();

        habitationPojoArrayList = new ArrayList<>();
        habitationPojoArrayListIsNotTestHab = new ArrayList<>();
        habitationPojoArrayListAlredyAssign = new ArrayList<>();
        habitationPojoArrayListIsPreviousFinancialYearsSource = new ArrayList<>();
        habitationPojoArrayListIsCurrentFinancialYearsSource = new ArrayList<>();

        if (!TextUtils.isEmpty(pan_code)) {

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
                        .baseUrl(PostInterface.JSONURL)
                        .client(httpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                PostInterface api = retrofit.create(PostInterface.class);

                String sLabId = CGlobal.getInstance().getPersistentPreference(this)
                        .getString(Constants.PREFS_USER_LAB_ID, "");
                Log.d("OLA", "Dist Code" + districtCode + "Lab IS" + sLabId + "BlockCode" + selectedBlockCode + "pan code" + pan_code + "vill_code" + vill_code);
                Call<List<SourceForLaboratoryPOJO>> call = api.getSourceForLaboratoryNew(sLabId, districtCode,
                        (Objects.equals(selectedBlockCode, "9001") ? "2176" : selectedBlockCode), pan_code, vill_code, "Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3");

                call.enqueue(new Callback<List<SourceForLaboratoryPOJO>>() {
                    @Override
                    public void onResponse(Call<List<SourceForLaboratoryPOJO>> call, Response<List<SourceForLaboratoryPOJO>> response) {
                        Log.i("Responsestring!!", response.body().size() + "");
                        //loadingDialog.hideDialog();
                        if (response.body() != null) {

                            sourceForLaboratoryPOJOArrayList.addAll(response.body());

                            for (int i = 0; i < sourceForLaboratoryPOJOArrayList.size(); i++) {

                                if (sourceForLaboratoryPOJOArrayList.get(i).getVillage_Code().equalsIgnoreCase(vill_code)) {

                                    if (cmaHabitationCode.size() > 0) {
                                        if (!cmaHabitationId.contains(sourceForLaboratoryPOJOArrayList.get(i).getHabId())) {
                                            cmaHabitationId.add(sourceForLaboratoryPOJOArrayList.get(i).getHabId());
                                            cmaHabitationName.add(sourceForLaboratoryPOJOArrayList.get(i).getHabitationName());
                                            cmaHabitationCode.add(sourceForLaboratoryPOJOArrayList.get(i).getHabitation_Code());

                                            HabitationPojo habitationPojo = new HabitationPojo();
                                            habitationPojo.setCode(sourceForLaboratoryPOJOArrayList.get(i).getHabitation_Code());
                                            habitationPojo.setId(sourceForLaboratoryPOJOArrayList.get(i).getHabId());
                                            habitationPojo.setName(sourceForLaboratoryPOJOArrayList.get(i).getHabitationName());
                                            habitationPojo.setIsCurrentFinancialYearsSource(sourceForLaboratoryPOJOArrayList.get(i).getIsCurrentFinancialYearsSource());
                                            habitationPojo.setIsNotTestHab(sourceForLaboratoryPOJOArrayList.get(i).getIsNotTestHab());
                                            habitationPojo.setIsPreviousFinancialYearsSource(sourceForLaboratoryPOJOArrayList.get(i).getIsPreviousFinancialYearsSource());
                                            habitationPojo.setChecked(false);
                                            habitationPojo.setXcount(sourceForLaboratoryPOJOArrayList.get(i).getXcount());
                                            habitationPojo.setAlredyAssign(sourceForLaboratoryPOJOArrayList.get(i).getAlredyAssign());
                                            habitationPojo.setVillage_Code(sourceForLaboratoryPOJOArrayList.get(i).getVillage_Code());

                                            /*if (sourceForLaboratoryPOJOArrayList.get(i).getIsNotTestHab().equalsIgnoreCase("yes")) {
                                                habitationPojoArrayListIsNotTestHab.add(habitationPojo);
                                            } else if (sourceForLaboratoryPOJOArrayList.get(i).getIsPreviousFinancialYearsSource().
                                                    equalsIgnoreCase("yes") &&
                                                    sourceForLaboratoryPOJOArrayList.get(i).getAlredyAssign().equalsIgnoreCase("0")) {
                                                habitationPojoArrayListIsPreviousFinancialYearsSource.add(habitationPojo);
                                            } else if (!sourceForLaboratoryPOJOArrayList.get(i).getAlredyAssign().equalsIgnoreCase("0"))
                                                habitationPojoArrayListAlredyAssign.add(habitationPojo);
                                            else {
                                                habitationPojoArrayListIsCurrentFinancialYearsSource.add(habitationPojo);
                                            }*/
                                            if (sourceForLaboratoryPOJOArrayList.get(i).getIsNotTestHab().equalsIgnoreCase("yes")) {
                                                habitationPojoArrayListIsNotTestHab.add(habitationPojo);
                                            } else if (sourceForLaboratoryPOJOArrayList.get(i).getIsCurrentFinancialYearsSource().
                                                    equalsIgnoreCase("yes") &&
                                                    sourceForLaboratoryPOJOArrayList.get(i).getAlredyAssign().equalsIgnoreCase("0")) {
                                                habitationPojoArrayListIsCurrentFinancialYearsSource.add(habitationPojo);
                                            } else if (!sourceForLaboratoryPOJOArrayList.get(i).getAlredyAssign().equalsIgnoreCase("0"))
                                                habitationPojoArrayListAlredyAssign.add(habitationPojo);
                                            else {
                                                habitationPojoArrayListIsPreviousFinancialYearsSource.add(habitationPojo);
                                            }
                                            //habitationPojoArrayList.add(habitationPojo);
                                        }
                                    } else {
                                        cmaHabitationId.add(sourceForLaboratoryPOJOArrayList.get(i).getHabId());
                                        cmaHabitationName.add(sourceForLaboratoryPOJOArrayList.get(i).getHabitationName());
                                        cmaHabitationCode.add(sourceForLaboratoryPOJOArrayList.get(i).getHabitation_Code());

                                        HabitationPojo habitationPojo = new HabitationPojo();
                                        habitationPojo.setCode(sourceForLaboratoryPOJOArrayList.get(i).getHabitation_Code());
                                        habitationPojo.setId(sourceForLaboratoryPOJOArrayList.get(i).getHabId());
                                        habitationPojo.setName(sourceForLaboratoryPOJOArrayList.get(i).getHabitationName());
                                        habitationPojo.setIsCurrentFinancialYearsSource(sourceForLaboratoryPOJOArrayList.get(i).getIsCurrentFinancialYearsSource());
                                        habitationPojo.setIsNotTestHab(sourceForLaboratoryPOJOArrayList.get(i).getIsNotTestHab());
                                        habitationPojo.setIsPreviousFinancialYearsSource(sourceForLaboratoryPOJOArrayList.get(i).getIsPreviousFinancialYearsSource());
                                        habitationPojo.setChecked(false);
                                        habitationPojo.setXcount(sourceForLaboratoryPOJOArrayList.get(i).getXcount());
                                        habitationPojo.setAlredyAssign(sourceForLaboratoryPOJOArrayList.get(i).getAlredyAssign());
                                        habitationPojo.setVillage_Code(sourceForLaboratoryPOJOArrayList.get(i).getVillage_Code());

                                        /*if (sourceForLaboratoryPOJOArrayList.get(i).getIsNotTestHab().equalsIgnoreCase("yes")) {
                                            habitationPojoArrayListIsNotTestHab.add(habitationPojo);
                                        } if (sourceForLaboratoryPOJOArrayList.get(i).getIsPreviousFinancialYearsSource().
                                                equalsIgnoreCase("yes") &&
                                                sourceForLaboratoryPOJOArrayList.get(i).getAlredyAssign().equalsIgnoreCase("0")) {
                                            habitationPojoArrayListIsPreviousFinancialYearsSource.add(habitationPojo);
                                        } else if (!sourceForLaboratoryPOJOArrayList.get(i).getAlredyAssign().equalsIgnoreCase("0"))
                                            habitationPojoArrayListAlredyAssign.add(habitationPojo);
                                        else {
                                            habitationPojoArrayListIsCurrentFinancialYearsSource.add(habitationPojo);
                                        }*/
                                        if (sourceForLaboratoryPOJOArrayList.get(i).getIsNotTestHab().equalsIgnoreCase("yes")) {
                                            habitationPojoArrayListIsNotTestHab.add(habitationPojo);
                                        } else if (sourceForLaboratoryPOJOArrayList.get(i).getIsCurrentFinancialYearsSource().
                                                equalsIgnoreCase("yes") &&
                                                sourceForLaboratoryPOJOArrayList.get(i).getAlredyAssign().equalsIgnoreCase("0")) {
                                            habitationPojoArrayListIsCurrentFinancialYearsSource.add(habitationPojo);
                                        } else if (!sourceForLaboratoryPOJOArrayList.get(i).getAlredyAssign().equalsIgnoreCase("0"))
                                            habitationPojoArrayListAlredyAssign.add(habitationPojo);
                                        else {
                                            habitationPojoArrayListIsPreviousFinancialYearsSource.add(habitationPojo);
                                        }
                                        //habitationPojoArrayList.add(habitationPojo);
                                    }
                                }
                            }
                            habitationPojoArrayList = new ArrayList<>();
                            habitationPojoArrayList.addAll(habitationPojoArrayListIsNotTestHab);
                            habitationPojoArrayList.addAll(habitationPojoArrayListIsPreviousFinancialYearsSource);
                            habitationPojoArrayList.addAll(habitationPojoArrayListIsCurrentFinancialYearsSource);
                            habitationPojoArrayList.addAll(habitationPojoArrayListAlredyAssign);
                            adapter = new MultiAdapterForHabitation(AddnewTaskToFC.this, habitationPojoArrayList);
                            recyclerView.setAdapter(adapter);
                            info.setVisibility(View.VISIBLE);
                            easyFlipView.flipTheView();

                        } else {
                            //Log.i("onEmptyResponse", "Returned empty response");
                            Toast.makeText(AddnewTaskToFC.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                        progressdialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<List<SourceForLaboratoryPOJO>> call, Throwable t) {
                        //loadingDialog.hideDialog();
                        progressdialog.dismiss();
                        //Log.i("onEmptyResponse", "Returned empty response");
                    }
                });
            } else {
                new androidx.appcompat.app.AlertDialog.Builder(AddnewTaskToFC.this)
                        .setMessage("Please check your Internet connection. Please try again")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        } else {
            //Toast.makeText(getActivity(), "Select Panchayat", Toast.LENGTH_SHORT).show();
            showMessage("Please Select Panchayat");
        }
    }


    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void getpanchayat() {

        cmaPanchayatName = new ArrayList<>();
        cmaPanchayatCode = new ArrayList<>();

        if (!TextUtils.isEmpty(selectedBlockCode)) {

            alertDialogItems1 = new String[]{};
            //tvPanchayat.setText("");
            alertdialogbuilder1 = new AlertDialog.Builder(this);

            Log.d("Bal", responsePanArrayList.toString());
            for (int a = 0; a < responsePanArrayList.size(); a++) {
                if (selectedBlockCode.equalsIgnoreCase(responsePanArrayList.get(a).getBlockCode())) {
                    for (String selectedPanCode : selectedPanCodes) {
                        if (selectedPanCode.equalsIgnoreCase(responsePanArrayList.get(a).getPanCode())) {
                            cmaPanchayatName.add(responsePanArrayList.get(a).getPanchayatName());
                            cmaPanchayatCode.add(responsePanArrayList.get(a).getPanCode());
                        }
                    }
                }
            }

            alertDialogItems1 = new String[cmaPanchayatCode.size()];
            for (int b = 0; b < cmaPanchayatCode.size(); b++)
                alertDialogItems1[b] = cmaPanchayatName.get(b);

            alertdialogbuilder1.setSingleChoiceItems(alertDialogItems1, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Toast.makeText(getContext(), alertDialogItems[which], Toast.LENGTH_SHORT).show();
                    pan_name = alertDialogItems1[which];
                    pan_code = cmaPanchayatCode.get(which);
                }
            });
            alertdialogbuilder1.setCancelable(false);
            alertdialogbuilder1.setTitle("Select Panchayat");

            alertdialogbuilder1.setPositiveButton("Choose", new DialogInterface.OnClickListener() {
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
            });

            alertdialogbuilder1.setNeutralButton("Discard", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    pan_code = "";
                }
            });

            AlertDialog dialog = alertdialogbuilder1.create();
            dialog.show();
        } else {
            //Toast.makeText(getActivity(), "Select Block", Toast.LENGTH_SHORT).show();
            showMessage("Please Select Block");
        }
    }

    private void getVillage(String sVillage) {

        if (TextUtils.isEmpty(sVillage) || sVillage.equalsIgnoreCase("")) {
            showMessage("Please Select PWSS/Non PWSS Village");
            return;
        }

        cmaVillageName = new ArrayList<>();
        cmaVillageCode = new ArrayList<>();
        final ArrayList<ResponseVillage> responseVillageArrayList = new ArrayList<>();
        final ArrayList<ResponseVillage> firstList = new ArrayList<>();
        final ArrayList<ResponseVillage> otherList = new ArrayList<>();

        if (!TextUtils.isEmpty(pan_code)) {

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
                        .baseUrl(PostInterface.URL_RNJ_NEW)
                        .client(httpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                PostInterface api = retrofit.create(PostInterface.class);

                Call<ResponseBody> call = api.get_airp_app_village(districtCode, selectedBlockCode, pan_code);
                //Log.d("Responsestring", districtCode + " " + selectedBlockCode + " " + pan_code);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                        //Log.i("Responsestring", response.message());
                        progressdialog.dismiss();

                        if (response.body() != null) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                String resCode = jsonObject.getString("resCode");
                                String message = jsonObject.getString("message");
                                String error = jsonObject.getString("error");
                                if (jsonObject.has("data")) {
                                    JSONArray dataJSONArray = jsonObject.getJSONArray("data");
                                    for (int i = 0; i < dataJSONArray.length(); i++) {
                                        JSONObject dataJsonObject = dataJSONArray.getJSONObject(i);
                                        cmaVillageName.add(dataJsonObject.getString("vill_name"));
                                        cmaVillageCode.add(dataJsonObject.getString("vill_code"));
                                        ResponseVillage responseVillage = new ResponseVillage();
                                        responseVillage.setVillCode(dataJsonObject.getString("vill_code"));
                                        responseVillage.setVillName(dataJsonObject.getString("vill_name"));
                                        responseVillage.setTotal_test(dataJsonObject.getString("2022-23_total_test"));
                                        responseVillage.setPws_status(dataJsonObject.getString("pws_status"));
                                        if (sVillage.equalsIgnoreCase(dataJsonObject.getString("pws_status"))) {
                                            if (dataJsonObject.getString("2022-23_total_test")
                                                    .equalsIgnoreCase("0"))
                                                firstList.add(responseVillage);
                                        }
                                    }
                                }

                                responseVillageArrayList.clear();
                                responseVillageArrayList.addAll(otherList);
                                responseVillageArrayList.addAll(firstList);

                                if(responseVillageArrayList.size() == 0){
                                    showMessage("No Village found");
                                    return;
                                }

                                NewVillageAdapter dataAdapter = new NewVillageAdapter(AddnewTaskToFC.this, responseVillageArrayList,
                                        new NewVillageAdapter.RecyclerViewItemClickListener() {
                                            @Override
                                            public void clickOnItem(ResponseVillage data) {
                                                //Toast.makeText(getContext(), data.getVillageName() + " #" + data.getVillageCode(), Toast.LENGTH_SHORT).show();
                                                //vill_code = data.getVillCode();
                                                //tvVillage.setText(data.getVillName());
                                                responseVillage = data;
                                            }
                                        });
                                customDialog = new CustomListViewDialog(AddnewTaskToFC.this, dataAdapter);
                                customDialog.show();
                                customDialog.setCanceledOnTouchOutside(false);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            //Log.i("onEmptyResponse", "Returned empty jsonResponse");
                            Toast.makeText(AddnewTaskToFC.this, "Unable to get data", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        //Log.i("onEmptyResponse", "Returned empty jsonResponse");
                        progressdialog.dismiss();
                        Toast.makeText(AddnewTaskToFC.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                new androidx.appcompat.app.AlertDialog.Builder(AddnewTaskToFC.this)
                        .setMessage("Please check your Internet connection. Please try again")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        } else {
            //Toast.makeText(getActivity(), "Select Panchayat", Toast.LENGTH_SHORT).show();
            showMessage("Please Select Panchayat");
        }
    }

    private void getBlock() {

        tvBlock.setText(selectedBlockName);
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
                        tvDistrictName.setText(responsePanArrayList.get(0).getDistName());
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
                        Toast.makeText(AddnewTaskToFC.this, "Unable to get data", Toast.LENGTH_SHORT).show();
                    }
                    progressdialog.dismiss();
                }

                @Override
                public void onFailure(Call<List<ResponsePanchyat>> call, Throwable t) {
                    //Log.i("onEmptyResponse", "Returned empty jsonResponse");
                    progressdialog.dismiss();
                    Toast.makeText(AddnewTaskToFC.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            new androidx.appcompat.app.AlertDialog.Builder(AddnewTaskToFC.this)
                    .setMessage("Please check your Internet connection. Please try again")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    private void submitData(String HabIds) {

        progressdialog = new ProgressDialog(AddnewTaskToFC.this);

        boolean isConnected = CGlobal.getInstance().isConnected(AddnewTaskToFC.this);
        if (isConnected) {

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

            progressdialog.setMessage("Please Wait....");
            progressdialog.setCancelable(false);
            progressdialog.show();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(PostInterface.JSONURL)
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            PostInterface api = retrofit.create(PostInterface.class);
            String sLabId = CGlobal.getInstance().getPersistentPreference(AddnewTaskToFC.this)
                    .getString(Constants.PREFS_USER_LAB_ID, "");

            long unixTime = System.currentTimeMillis() / 1000L;

            Call<ResponseBody> call;
            if (TextUtils.isEmpty(getIntent().getStringExtra("TASKID")))
                call = api.New_AssignTaskToFacilitor("", districtCode, selectedBlockCode, pan_code,
                        vill_code, sLabId, facilitatorId/*, "Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3"*/);
            else
                call = api.New_AddMoreHabitationInSameTask("", districtCode, selectedBlockCode, pan_code, vill_code, sLabId,
                        facilitatorId, getIntent().getStringExtra("TASKID"), "Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3");

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    progressdialog.dismiss();
                    if (response.body() != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string().trim());
                            if (jsonObject.getBoolean("response")) {
                                new androidx.appcompat.app.AlertDialog.Builder(AddnewTaskToFC.this)
                                        .setMessage("Task assigned successfully")
                                        .setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                facilitatorName = "";
                                                dialog.dismiss();
                                                overridePendingTransition(R.anim.trans_right_in, R.anim.trans_left_out);
                                                finish();
                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_info)
                                        .setCancelable(false)
                                        .show();
                            } else {
                                showMessage("Already assigned");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        showMessage("Unable to add task.");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    progressdialog.dismiss();
                    //Log.i("onEmptyResponse", "Returned empty response");
                    showMessage("Something went wrong.");
                }
            });
        } else {
            new androidx.appcompat.app.AlertDialog.Builder(this)
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

    private void showMessage(String msg) {
        Snackbar.make(findViewById(android.R.id.content),
                "⚠ " + msg, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if (backAction) {
            backAction = false;
            info.setVisibility(View.GONE);
            easyFlipView.flipTheView();
        } else {
            overridePendingTransition(R.anim.trans_right_in, R.anim.trans_left_out);
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            if (backAction) {
                backAction = false;
                info.setVisibility(View.GONE);
                easyFlipView.flipTheView();
            } else {
                overridePendingTransition(R.anim.trans_right_in, R.anim.trans_left_out);
                finish();
            }
        }
        return (super.onOptionsItemSelected(menuItem));
    }


    /* ############################################################################## */
    public class CustomListViewDialog extends Dialog implements View.OnClickListener {

        public CustomListViewDialog(Context context, int themeResId) {
            super(context, themeResId);
        }

        public CustomListViewDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
            super(context, cancelable, cancelListener);
        }

        public Activity activity;
        public Dialog dialog;
        private RecyclerView.Adapter adapter;

        public CustomListViewDialog(Activity a, NewVillageAdapter dataAdapter) {
            super(a);
            this.adapter = dataAdapter;
            setupLayout();
        }

        private void setupLayout() {

        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.custom_dialog_layout);
            Button cancel_dialog = (Button) findViewById(R.id.cancel_dialog);
            Button done_dialog = (Button) findViewById(R.id.done_dialog);
            //TextView title = findViewById(R.id.title);
            RecyclerView recyclerView = findViewById(R.id.recycler_view);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
            recyclerView.setLayoutManager(mLayoutManager);

            recyclerView.setAdapter(adapter);
            cancel_dialog.setOnClickListener(this);
            done_dialog.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cancel_dialog:
                    dismiss();
                    break;
                case R.id.done_dialog:
                    vill_code = responseVillage.getVillCode();
                    tvVillage.setText(responseVillage.getVillName());
                    Log.d("TESTS", responseVillage.getVillName() + vill_code);
                    dismiss();
                    break;
                default:
                    dismiss();
            }
            dismiss();
        }
    }
}
