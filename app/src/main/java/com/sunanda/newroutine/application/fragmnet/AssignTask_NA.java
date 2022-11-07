package com.sunanda.newroutine.application.fragmnet;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.adapter.FacilitatorAdapter;
import com.sunanda.newroutine.application.adapter.SourceDataAdapter;
import com.sunanda.newroutine.application.database.DatabaseHandler;
import com.sunanda.newroutine.application.modal.CommonModel;
import com.sunanda.newroutine.application.modal.ResponseSourceData;
import com.sunanda.newroutine.application.somenath.helper.CustomListViewDialog;
import com.sunanda.newroutine.application.somenath.pojo.HabitationPojo;
import com.sunanda.newroutine.application.somenath.myadapter.MultiAdapterForHabitation;
import com.sunanda.newroutine.application.somenath.myadapter.MySpinnerAdapter;
import com.sunanda.newroutine.application.somenath.pojo.NewVillagePojo;
import com.sunanda.newroutine.application.somenath.pojo.SourceForLaboratoryPOJO;
import com.sunanda.newroutine.application.somenath.myadapter.VillageAdapter;
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
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class AssignTask_NA extends Fragment implements VillageAdapter.RecyclerViewItemClickListener {

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
    ArrayList<HabitationPojo> habitationPojoArrayListIsPreviousFinancialYearsSource = new ArrayList<>();
    ArrayList<HabitationPojo> habitationPojoArrayListIsCurrentFinancialYearsSource = new ArrayList<>();

    ArrayList<ResponseSourceData> checkedLists = new ArrayList<>();
    ArrayList<ResponseSourceData> arrayList, tempList;
    SourceDataAdapter sourceDataAdapter;

    Spinner spBlock, facilitatorSpinner;
    LoadingDialog loadingDialog;
    String sBlockName, sBlockCode;
    TextView tvLabName, tvDistrictName, tvPanchayat, tvVillage, tvHabitation, location, labDetail;
    AlertDialog.Builder alertdialogbuilder1, alertdialogbuilder2, alertdialogbuilder3;
    boolean[] selectedtruefalse;
    String[] alertDialogItems1, alertDialogItems2, alertDialogItems3;
    String pan_name = "", pan_code = "", vill_name = "";
    String districtCode = "", vill_code = "", hab_code = "", hab_name = "", facilitatorName = "", facilitatorId = "";

    View myView;
    private EasyFlipView easyFlipView;
    private RecyclerView sourceRecycler;
    ProgressDialog progressdialog;
    Switch simpleSwitch;
    String statusSwitch;
    String selectedHabName = "";
    ArrayList<NewVillagePojo> newVillagePojoArrayList;

    CustomListViewDialog customDialog;

    RecyclerView recyclerView;
    MultiAdapterForHabitation adapter;

    ArrayList<SourceForLaboratoryPOJO> sourceForLaboratoryPOJOArrayList;
    DatabaseHandler databaseHandler;

    static String response;
    private String jsonresponse = "";

    private boolean fragmentResume = false;
    private boolean fragmentVisible = false;
    private boolean fragmentOnCreated = false;

    public static AssignTask_NA newInstance(String res) {
        response = res;
        return new AssignTask_NA();
    }

    public AssignTask_NA() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            addFacilatorEvent = (AddFacilatorEvent) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement AddFacilatorEvent");
        }
    }

    /*@Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible) {
            Log.d("TESTSR", "Working2");
            getFacilitatorList();
        }
    }

    @Override
    public void setUserVisibleHint(boolean visible) {
        super.setUserVisibleHint(visible);
        Log.d("TESTSR", "Working1");
        if (visible && isResumed()) {   // only at fragment screen is resumed
            fragmentResume = true;
            fragmentVisible = false;
            fragmentOnCreated = true;
            getFacilitatorList();
        } else if (visible) {        // only at fragment onCreated
            fragmentResume = false;
            fragmentVisible = true;
            fragmentOnCreated = true;
        } else if (!visible && fragmentOnCreated) {// only when you go out of fragment screen
            fragmentVisible = false;
            fragmentResume = false;
        }
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_fragment4, container, false);

        spBlock = myView.findViewById(R.id.spBlock);
        facilitatorSpinner = myView.findViewById(R.id.facilitatorSpinner);

        this.recyclerView = (RecyclerView) myView.findViewById(R.id.recyclerView);

        loadingDialog = new LoadingDialog(getContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        tvPanchayat = myView.findViewById(R.id.tvPanchayat);
        tvVillage = myView.findViewById(R.id.tvVillage);
        tvLabName = myView.findViewById(R.id.tvLabName);
        tvDistrictName = myView.findViewById(R.id.tvDistrictName);
        tvHabitation = myView.findViewById(R.id.tvHabitation);
        sourceRecycler = myView.findViewById(R.id.sourceRecycler);
        location = myView.findViewById(R.id.location);
        labDetail = myView.findViewById(R.id.labDetail);

        simpleSwitch = (Switch) myView.findViewById(R.id.simpleSwitch);
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


        tvLabName.setText(CGlobal.getInstance().getPersistentPreference(getActivity())
                .getString(Constants.PREFS_USER_LAB_NAME, ""));


        myView.findViewById(R.id.btnNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (facilitatorName.contains("Choose")) {
                    //Toast.makeText(getActivity(), "Please Select Facilitator", Toast.LENGTH_SHORT).show();
                    showMessage("Please Select Facilitator");
                } else if (TextUtils.isEmpty(vill_code)) {
                    //Toast.makeText(getActivity(), "Please Select Village", Toast.LENGTH_SHORT).show();
                    showMessage("Please Select Village");
                } else {
                    submitData("");
                   /* getHabitation();
                    habitationPojoArrayList = new ArrayList<>();
                    habitationPojoArrayList.addAll(habitationPojoArrayListIsNotTestHab);
                    habitationPojoArrayList.addAll(habitationPojoArrayListIsPreviousFinancialYearsSource);
                    habitationPojoArrayList.addAll(habitationPojoArrayListIsCurrentFinancialYearsSource);
                    adapter = new MultiAdapterForHabitation(getActivity(), habitationPojoArrayList);
                    recyclerView.setAdapter(adapter);
                    easyFlipView.flipTheView();*/
                }
            }
        });

        myView.findViewById(R.id.btnNext2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        myView.findViewById(R.id.btnPrev).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                easyFlipView.flipTheView();
            }
        });

        myView.findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "SUBMIT", Toast.LENGTH_SHORT).show();
                //submitData();
                new AlertDialog.Builder(getActivity())
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
                                        stringBuilder.append(adapter.getSelected().get(i).getCode());
                                        if (i < adapter.getSelected().size() - 1)
                                            stringBuilder.append(",");
                                    }
                                    //showToast(stringBuilder.toString().trim());
                                    submitData(String.valueOf(stringBuilder));
                                } else {
                                    //showToast("Please select Habitation to assign");
                                    showMessage("Please select Habitation to assign");
                                }
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setCancelable(false)
                        //.setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        myView.findViewById(R.id.addNewUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFacilatorEvent.addNewOne(CreateFacilitator.newInstance());
            }
        });

        easyFlipView = (EasyFlipView) myView.findViewById(R.id.easyFlipView);
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
                getVillage();
            }
        });
        tvHabitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHabitation();
            }
        });

        /*myView.findViewById(R.id.reload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getFacilitatorListResponse(response);
                getFacilitatorList();
                getRecords();
            }
        });*/

        //getFacilitatorListResponse(response);
        getFacilitatorList();
        //getRecords();

        /*if (!fragmentResume && fragmentVisible) {   //only when first time fragment is created
            getFacilitatorList();
            getRecords();
        }*/

        return myView;
    }

    private void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void getHabitation() {

        cmaHabitationId = new ArrayList<>();
        cmaHabitationName = new ArrayList<>();
        cmaHabitationCode = new ArrayList<>();
        //habitationPojoArrayList = new ArrayList<>();
        habitationPojoArrayListIsNotTestHab = new ArrayList<>();
        habitationPojoArrayListIsPreviousFinancialYearsSource = new ArrayList<>();
        habitationPojoArrayListIsCurrentFinancialYearsSource = new ArrayList<>();

        if (!TextUtils.isEmpty(vill_code)) {

            //tvHabitation.setText("");
            //alertdialogbuilder3 = new AlertDialog.Builder(getActivity());

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

                            if (sourceForLaboratoryPOJOArrayList.get(i).getIsNotTestHab().equalsIgnoreCase("yes")) {
                                habitationPojoArrayListIsNotTestHab.add(habitationPojo);
                            } else if (sourceForLaboratoryPOJOArrayList.get(i).getIsPreviousFinancialYearsSource().equalsIgnoreCase("yes")) {
                                habitationPojoArrayListIsPreviousFinancialYearsSource.add(habitationPojo);
                            } else {
                                habitationPojoArrayListIsCurrentFinancialYearsSource.add(habitationPojo);
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

                        if (sourceForLaboratoryPOJOArrayList.get(i).getIsNotTestHab().equalsIgnoreCase("yes")) {
                            habitationPojoArrayListIsNotTestHab.add(habitationPojo);
                        } else if (sourceForLaboratoryPOJOArrayList.get(i).getIsPreviousFinancialYearsSource().equalsIgnoreCase("yes")) {
                            habitationPojoArrayListIsPreviousFinancialYearsSource.add(habitationPojo);
                        } else {
                            habitationPojoArrayListIsCurrentFinancialYearsSource.add(habitationPojo);
                        }
                        //habitationPojoArrayList.add(habitationPojo);
                    }
                }
            }

            /*selectedtruefalse = new boolean[]{};
            selectedtruefalse = new boolean[cmaHabitationCode.size()];

            alertDialogItems3 = new String[cmaHabitationName.size()];
            for (int a = 0; a < cmaHabitationName.size(); a++) {
                selectedtruefalse[a] = false;
                alertDialogItems3[a] = cmaHabitationName.get(a);
            }

            *//*alertdialogbuilder3.setSingleChoiceItems(alertDialogItems3, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Toast.makeText(getContext(), alertDialogItems[which], Toast.LENGTH_SHORT).show();
                    hab_name = alertDialogItems3[which];
                    hab_code = cmaHabitationCode.get(which);
                }
            });*//*
            alertdialogbuilder3.setMultiChoiceItems(alertDialogItems3, selectedtruefalse, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                    selectedtruefalse[which] = isChecked;
                }
            });
            alertdialogbuilder3.setCancelable(false);
            alertdialogbuilder3.setTitle("Select Habitation");

            *//*alertdialogbuilder3.setPositiveButton("Choose", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    if (!tvHabitation.getText().toString().equalsIgnoreCase(hab_name))
                        tvHabitation.setText("");
                    else
                        tvHabitation.setText(hab_name);
                    //hab_code = databaseHandler.getHabCode(hab_name);
                    Log.d("hab_code", hab_code);
                }
            });*//*
            alertdialogbuilder3.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int a = 0;
                    while (a < selectedtruefalse.length) {
                        boolean value = selectedtruefalse[a];
                        if (value) {
                            String name1 = cmaHabitationName.get(a);
                            if (!TextUtils.isEmpty(selectedHabName)) {
                                selectedHabName = selectedHabName + "," + name1;
                            } else {
                                selectedHabName = name1;
                            }
                        }
                        a++;
                    }
                    tvHabitation.setText(selectedHabName);
                }
            }).setNeutralButton("Discard", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog dialog = alertdialogbuilder3.create();
            dialog.show();*/
        } else {
            //Toast.makeText(getActivity(), "Select Village", Toast.LENGTH_SHORT).show();
            showMessage("Select Village");
        }
    }

    private void getVillage() {

        cmaVillageId = new ArrayList<>();
        cmaVillageName = new ArrayList<>();
        cmaVillageCode = new ArrayList<>();

        if (!TextUtils.isEmpty(pan_code)) {

            //tvVillage.setText("");
            alertdialogbuilder2 = new AlertDialog.Builder(getActivity());

            for (int i = 0; i < sourceForLaboratoryPOJOArrayList.size(); i++) {
                if (sourceForLaboratoryPOJOArrayList.get(i).getPan_code().equalsIgnoreCase(pan_code)) {
                    if (cmaVillageId.size() > 0) {
                        if (!cmaVillageId.contains(sourceForLaboratoryPOJOArrayList.get(i).getVillageID())) {
                            cmaVillageId.add(sourceForLaboratoryPOJOArrayList.get(i).getVillageID());
                            cmaVillageCode.add(sourceForLaboratoryPOJOArrayList.get(i).getVillage_Code());
                            cmaVillageName.add(sourceForLaboratoryPOJOArrayList.get(i).getVillageName());
                        }
                    } else {
                        cmaVillageId.add(sourceForLaboratoryPOJOArrayList.get(i).getVillageID());
                        cmaVillageCode.add(sourceForLaboratoryPOJOArrayList.get(i).getVillage_Code());
                        cmaVillageName.add(sourceForLaboratoryPOJOArrayList.get(i).getVillageName());
                    }
                }
            }

            /*alertDialogItems2 = new String[cmaVillageId.size()];
            for (int a = 0; a < cmaVillageName.size(); a++) {
                alertDialogItems2[a] = cmaVillageName.get(a);
            }*/

            newVillagePojoArrayList = new ArrayList<>();
            for (int i = 0; i < cmaVillageCode.size(); i++) {
                int totCnt = databaseHandler.getTotalCountByVillage(cmaVillageCode.get(i));
                NewVillagePojo newVillagePojo = databaseHandler.getDataByVillage(cmaVillageCode.get(i), String.valueOf(totCnt));
                if (!newVillagePojo.getTestedHab().equalsIgnoreCase("0"))
                    newVillagePojoArrayList.add(newVillagePojo);
            }

            VillageAdapter dataAdapter = new VillageAdapter(newVillagePojoArrayList, this);
            //customDialog = new CustomListViewDialog(getActivity(), dataAdapter);
            customDialog.show();
            customDialog.setCanceledOnTouchOutside(false);

        } else {
            //Toast.makeText(getActivity(), "Select Panchayat", Toast.LENGTH_SHORT).show();
            showMessage("Select Panchayat");
        }
    }

    private void getpanchayat() {

        cmaPanchayatId = new ArrayList<>();
        cmaPanchayatName = new ArrayList<>();
        cmaPanchayatCode = new ArrayList<>();

        if (!TextUtils.isEmpty(sBlockCode)) {

            alertDialogItems1 = new String[]{};
            //tvPanchayat.setText("");
            alertdialogbuilder1 = new AlertDialog.Builder(getActivity());

            cmaPanchayatId = new ArrayList<>();

            for (int i = 0; i < sourceForLaboratoryPOJOArrayList.size(); i++) {

                if (sourceForLaboratoryPOJOArrayList.get(i).getBlock_code().equalsIgnoreCase(sBlockCode)) {

                    if (cmaPanchayatId.size() > 0) {
                        if (!cmaPanchayatId.contains(sourceForLaboratoryPOJOArrayList.get(i).getPanchayatID())) {
                            cmaPanchayatId.add(sourceForLaboratoryPOJOArrayList.get(i).getPanchayatID());
                            cmaPanchayatName.add(sourceForLaboratoryPOJOArrayList.get(i).getPanchayatName());
                            cmaPanchayatCode.add(sourceForLaboratoryPOJOArrayList.get(i).getPan_code());
                        }
                    } else {
                        cmaPanchayatId.add(sourceForLaboratoryPOJOArrayList.get(i).getPanchayatID());
                        cmaPanchayatName.add(sourceForLaboratoryPOJOArrayList.get(i).getPanchayatName());
                        cmaPanchayatCode.add(sourceForLaboratoryPOJOArrayList.get(i).getPan_code());
                    }
                }
            }

            alertDialogItems1 = new String[cmaPanchayatId.size()];
            for (int a = 0; a < cmaPanchayatId.size(); a++) {
                alertDialogItems1[a] = cmaPanchayatName.get(a);
            }

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
                }
            });

            AlertDialog dialog = alertdialogbuilder1.create();
            dialog.show();
        } else {
            //Toast.makeText(getActivity(), "Select Block", Toast.LENGTH_SHORT).show();
            showMessage("Select Block");
        }
    }

    private void getRecords() {

        databaseHandler = new DatabaseHandler(getContext());
        databaseHandler.deleteAssignHabitationList();
        //loadingDialog.showDialog();
        progressdialog = new ProgressDialog(getActivity());

        sourceForLaboratoryPOJOArrayList = new ArrayList<>();
        cmaBlockId = new ArrayList<>();
        blockName = new ArrayList<>();
        blockCode = new ArrayList<>();

        boolean isConnected = CGlobal.getInstance().isConnected(getActivity());
        if (isConnected) {

            progressdialog.setMessage("Please Hold-On....");
            progressdialog.setCancelable(false);
            progressdialog.show();

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

            String sLabId = CGlobal.getInstance().getPersistentPreference(getActivity())
                    .getString(Constants.PREFS_USER_LAB_ID, "");
            //Log.d("LABID", sLabId);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(PostInterface.JSONURL)
                    .client(httpClient)
                    //.addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            PostInterface api = retrofit.create(PostInterface.class);

           /* Map<String, String> mapdata = new HashMap<>();
            mapdata.put("LabID", "922");
            mapdata.put("AppKey", "Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3");*/

            Call<List<SourceForLaboratoryPOJO>> call = api.getSourceForLaboratory(sLabId, "Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3");

            call.enqueue(new Callback<List<SourceForLaboratoryPOJO>>() {
                @Override
                public void onResponse(Call<List<SourceForLaboratoryPOJO>> call, Response<List<SourceForLaboratoryPOJO>> response) {
                    //Log.i("Responsestring!!", response.body().size() + "");
                    //loadingDialog.hideDialog();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            sourceForLaboratoryPOJOArrayList.addAll(response.body());

                            cmaBlockId.add("0");
                            blockName.add("Choose");
                            blockCode.add("0");

                            tvDistrictName.setText(sourceForLaboratoryPOJOArrayList.get(0).getDistrictName());
                            districtCode = sourceForLaboratoryPOJOArrayList.get(0).getDist_code();

                            if (databaseHandler.getSourceForLaboratoryCount() != 0) {
                                databaseHandler.deleteSOURCE_FOR_LAB();
                            }

                            for (int i = 0; i < sourceForLaboratoryPOJOArrayList.size(); i++) {

                                if (cmaBlockId.size() > 1) {
                                    if (!blockCode.contains(sourceForLaboratoryPOJOArrayList.get(i).getBlock_code())) {
                                        cmaBlockId.add(sourceForLaboratoryPOJOArrayList.get(i).getBlockId());
                                        blockName.add(sourceForLaboratoryPOJOArrayList.get(i).getBlockName());
                                        blockCode.add(sourceForLaboratoryPOJOArrayList.get(i).getBlock_code());
                                    }
                                } else {
                                    cmaBlockId.add(sourceForLaboratoryPOJOArrayList.get(i).getBlockId());
                                    blockName.add(sourceForLaboratoryPOJOArrayList.get(i).getBlockName());
                                    blockCode.add(sourceForLaboratoryPOJOArrayList.get(i).getBlock_code());
                                }

                                String NoOfSourceCollect = sourceForLaboratoryPOJOArrayList.get(i).getNoOfSourceCollect();
                                String DistrictID = sourceForLaboratoryPOJOArrayList.get(i).getDistrictID();
                                String dist_code = sourceForLaboratoryPOJOArrayList.get(i).getDist_code();
                                String DistrictName = sourceForLaboratoryPOJOArrayList.get(i).getDistrictName();
                                String BlockId = sourceForLaboratoryPOJOArrayList.get(i).getBlockId();
                                String block_code = sourceForLaboratoryPOJOArrayList.get(i).getBlock_code();
                                String BlockName = sourceForLaboratoryPOJOArrayList.get(i).getBlockName();
                                String PanchayatID = sourceForLaboratoryPOJOArrayList.get(i).getPanchayatID();
                                String pan_code = sourceForLaboratoryPOJOArrayList.get(i).getPan_code();
                                String PanchayatName = sourceForLaboratoryPOJOArrayList.get(i).getPanchayatName();
                                String VillageID = sourceForLaboratoryPOJOArrayList.get(i).getVillageID();
                                String Village_Code = sourceForLaboratoryPOJOArrayList.get(i).getVillage_Code();
                                String VillageName = sourceForLaboratoryPOJOArrayList.get(i).getVillageName();
                                String HabId = sourceForLaboratoryPOJOArrayList.get(i).getHabId();
                                String Habitation_Code = sourceForLaboratoryPOJOArrayList.get(i).getHabitation_Code();
                                String IsNotTestHab = sourceForLaboratoryPOJOArrayList.get(i).getIsNotTestHab();
                                String IsCurrentFinancialYearsSource = sourceForLaboratoryPOJOArrayList.get(i).getIsCurrentFinancialYearsSource();
                                String IsPreviousFinancialYearsSource = sourceForLaboratoryPOJOArrayList.get(i).getIsPreviousFinancialYearsSource();
                                String xcount = sourceForLaboratoryPOJOArrayList.get(i).getXcount();
                                String allocation_date = "2019-11-10";
                                String finished_date = "2019-11-17";

                                databaseHandler.insertSourceForLaboratory(String.valueOf(i + 1), NoOfSourceCollect, DistrictID,
                                        dist_code, DistrictName, BlockId, block_code, BlockName, PanchayatID, pan_code, PanchayatName,
                                        VillageID, Village_Code, VillageName, HabId, Habitation_Code, IsNotTestHab,
                                        IsCurrentFinancialYearsSource, IsPreviousFinancialYearsSource, xcount, allocation_date, finished_date);
                            }

                            MySpinnerAdapter block_adapter = new MySpinnerAdapter(getActivity(), blockName);
                            spBlock.setAdapter(block_adapter);
                            spBlock.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    sBlockName = blockName.get(position);
                                    if (sBlockName.equalsIgnoreCase("Choose")) {
                                        return;
                                    }
                                    sBlockCode = blockCode.get(position);
                                    // Reset all drill down values
                                    pan_name = "";
                                    pan_code = "";
                                    tvPanchayat.setText("");

                                    vill_code = "";
                                    vill_name = "";
                                    tvVillage.setText("");

                                    hab_code = "";
                                    hab_name = "";
                                    tvHabitation.setText("");
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } else {
                            //Log.i("onEmptyResponse", "Returned empty response");
                            reloadData();
                        }
                        progressdialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<List<SourceForLaboratoryPOJO>> call, Throwable t) {
                    //loadingDialog.hideDialog();
                    progressdialog.dismiss();
                    //Log.i("onEmptyResponse", "Returned empty response");
                    reloadData();
                }
            });
        } else {
            new androidx.appcompat.app.AlertDialog.Builder(getActivity())
                    .setMessage("Please check your Internet connection. Please try again")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

        /*// Calling method of activity from fragment
        ((DashBoard_Activity)getActivity()).Test();*/
    }

    private void reloadData() {
        Snackbar.make(getActivity().findViewById(android.R.id.content), "Unable to fetch data", 6000)
                .setAction("Try Again", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getRecords();
                    }
                })
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                .show();
    }

    private void getFacilitatorList() {

        boolean isConnected = CGlobal.getInstance().isConnected(getActivity());
        if (isConnected) {

            loadingDialog.showDialog();

            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {
                            okhttp3.Request.Builder ongoing = chain.request().newBuilder();
                            return chain.proceed(ongoing.build());
                        }
                    })
                    .build();

            String sLabCode = CGlobal.getInstance().getPersistentPreference(getActivity())
                    .getString(Constants.PREFS_USER_LAB_ID, "");

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(PostInterface.JSONURL)
                    .client(httpClient)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();

            PostInterface api = retrofit.create(PostInterface.class);

            Call<String> call = api.getFacilitatorListOnLab(sLabCode, "Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3");

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                    Log.i("Response_string", response.body());
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            loadingDialog.hideDialog();
                            String jsonResponse = response.body();
                            getFacilitatorListResponse(jsonResponse);
                        } else {
                            //Log.i("onEmptyResponse", "Returned empty jsonResponse");
                            //Toast.makeText(getActivity(), "Unable to get data", Toast.LENGTH_SHORT).show();
                            showMessage("Unable to fetch data");
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    //Log.i("onEmptyResponse", "Returned empty jsonResponse");
                    //Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    showMessage("Something went wrong");
                    loadingDialog.hideDialog();
                }
            });
        } else {
            new androidx.appcompat.app.AlertDialog.Builder(getActivity())
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


    private void getFacilitatorListResponse(String response) {

        final ArrayList<CommonModel> commonModelArrayList = new ArrayList<>();
        try {

            JSONArray aFacilitator = new JSONArray(response);
            for (int i = 0; i < aFacilitator.length(); i++) {
                JSONObject oFacilitator = aFacilitator.getJSONObject(i);

                String name = CGlobal.getInstance().isNullNotDefined(oFacilitator, "FCName") ? "" : oFacilitator.getString("FCName");
                String FCID = CGlobal.getInstance().isNullNotDefined(oFacilitator, "FCID") ? "" : oFacilitator.getString("FCID");
                String email = CGlobal.getInstance().isNullNotDefined(oFacilitator, "Email") ? "" : oFacilitator.getString("Email");
                String password = CGlobal.getInstance().isNullNotDefined(oFacilitator, "Password") ? "" : oFacilitator.getString("Password");
                String Mobile = CGlobal.getInstance().isNullNotDefined(oFacilitator, "Mobile") ? "" : oFacilitator.getString("Mobile");
                String UserName = CGlobal.getInstance().isNullNotDefined(oFacilitator, "UserName") ? "" : oFacilitator.getString("UserName");
                boolean is_active = !CGlobal.getInstance().isNullNotDefined(oFacilitator, "IsActive") && oFacilitator.getBoolean("IsActive");
                String user_type = CGlobal.getInstance().isNullNotDefined(oFacilitator, "UserType") ? "" : oFacilitator.getString("UserType");
                String LabCode = CGlobal.getInstance().isNullNotDefined(oFacilitator, "LabCode") ? "" : oFacilitator.getString("LabCode");
                String LabID = CGlobal.getInstance().isNullNotDefined(oFacilitator, "LabID") ? "" : oFacilitator.getString("LabID");
                String SampleCollectorId = CGlobal.getInstance().isNullNotDefined(oFacilitator, "SampleCollectorId") ? "" : oFacilitator.getString("SampleCollectorId");

                if (is_active) {
                    CommonModel commonModel = new CommonModel();
                    commonModel.setFCID(FCID);
                    commonModel.setName(name);
                    commonModel.setEmail(email);
                    commonModel.setPassword(password);
                    commonModel.setMobile(Mobile);
                    commonModel.setUser_type(user_type);
                    commonModel.setUser_name(UserName);
                    commonModel.setIs_active("1");
                    commonModel.setLabCode(LabCode);
                    commonModel.setLabId(LabID);

                    commonModelArrayList.add(commonModel);
                }
            }

            CommonModel commonModel = new CommonModel();
            commonModel.setName("Choose Facilitator");
            commonModelArrayList.add(0, commonModel);

        } catch (Exception e) {
            e.printStackTrace();
        }

        FacilitatorAdapter temp = new FacilitatorAdapter(getActivity(), commonModelArrayList);
        facilitatorSpinner.setAdapter(temp);

        facilitatorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                facilitatorName = commonModelArrayList.get(position).getName();
                if (facilitatorName.contains("Choose")) {
                    return;
                }
                facilitatorId = commonModelArrayList.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void submitData(String HabIds) {

        progressdialog = new ProgressDialog(getActivity());

        boolean isConnected = CGlobal.getInstance().isConnected(getActivity());
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
            String sLabId = CGlobal.getInstance().getPersistentPreference(getActivity())
                    .getString(Constants.PREFS_USER_LAB_ID, "");

            long unixTime = System.currentTimeMillis() / 1000L;

            Call<ResponseBody> call = api.AssignTaskToFacilitator(HabIds, districtCode, sBlockCode, pan_code,
                    vill_code, sLabId, facilitatorId/*, "Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3"*/);

            /*Log.d("TEST", sLabId + "#" + facilitatorId + "#" + HabIds + "#" + districtCode
                    + "#" + sBlockCode + "#" + pan_code + "#" + vill_code);*/

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    progressdialog.dismiss();
                    if (response.body() != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            if (jsonObject.getBoolean("response")) {
                                new androidx.appcompat.app.AlertDialog.Builder(getActivity())
                                        .setMessage("Task assigned successfully")
                                        .setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                facilitatorName = "";
                                                dialog.dismiss();
                                                easyFlipView.flipTheView();
                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_info)
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
            new androidx.appcompat.app.AlertDialog.Builder(getContext())
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

    @Override
    public void clickOnItem(NewVillagePojo data) {
        //Toast.makeText(getContext(), data.getVillageName() + " #" + data.getVillageCode(), Toast.LENGTH_SHORT).show();
        vill_code = data.getVillageCode();
        tvVillage.setText(data.getVillageName());
    }

    public interface BtnCheck {
        void onBtnCheckValue(int position, CheckBox cbInstrumentItem);
    }

    public interface AddFacilatorEvent {
        void addNewOne(Fragment s);
    }

    private void showMessage(String msg) {
        Snackbar.make(getActivity().findViewById(android.R.id.content),
                "⚠ " + msg, Snackbar.LENGTH_LONG).show();
    }

    AddFacilatorEvent addFacilatorEvent;

}
