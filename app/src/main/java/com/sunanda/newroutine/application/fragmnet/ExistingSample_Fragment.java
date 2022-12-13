package com.sunanda.newroutine.application.fragmnet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.adapter.AssignedHabitationList_Adapter;
import com.sunanda.newroutine.application.adapter.CompleteHabitationList_Adapter;
import com.sunanda.newroutine.application.database.DatabaseHandler;
import com.sunanda.newroutine.application.modal.CommonModel;
import com.sunanda.newroutine.application.modal.SampleModel;
import com.sunanda.newroutine.application.ui.DashBoard_Facilitator_Activity;
import com.sunanda.newroutine.application.util.CGlobal;
import com.sunanda.newroutine.application.util.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ExistingSample_Fragment extends Fragment {

    public static ExistingSample_Fragment newInstance() {
        ExistingSample_Fragment fragment = new ExistingSample_Fragment();
        return fragment;
    }

    public ExistingSample_Fragment() {
        // Required empty public constructor
    }

    TextView location, labDetail;
    RecyclerView sourceRecycler;
    View myView;
    private String jsonresponse = "";
    ArrayList<CommonModel> modelArrayList;
    ArrayList<SampleModel> modelArrayListComplete, modelArrayListCompleteRoutine, modelArrayListCompleteOmas, modelArrayListCompleteSchool;
    Button btnCompleteSample, btnInCompleteSample, btnPWSSVillageSource, btnNonPWSSVillageSource;
    String sTaskId = "";
    LinearLayout llPWSSStatus;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.existing_sample_fragment, container, false);

        sourceRecycler = myView.findViewById(R.id.sourceRecycler);
        location = myView.findViewById(R.id.location);
        labDetail = myView.findViewById(R.id.labDetail);

        llPWSSStatus = myView.findViewById(R.id.llPWSSStatus);

        btnInCompleteSample = myView.findViewById(R.id.btnInCompleteSample);
        btnCompleteSample = myView.findViewById(R.id.btnCompleteSample);
        btnPWSSVillageSource = myView.findViewById(R.id.btnPWSSVillageSource);
        btnNonPWSSVillageSource = myView.findViewById(R.id.btnNonPWSSVillageSource);

        btnInCompleteSample.setCompoundDrawablesWithIntrinsicBounds(R.drawable.checked, 0, 0, 0);
        btnCompleteSample.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        btnPWSSVillageSource.setCompoundDrawablesWithIntrinsicBounds(R.drawable.checked, 0, 0, 0);
        btnNonPWSSVillageSource.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

        btnCompleteSample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llPWSSStatus.setVisibility(View.GONE);
                btnCompleteSample.setCompoundDrawablesWithIntrinsicBounds(R.drawable.checked, 0, 0, 0);
                btnInCompleteSample.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                modelArrayListCompleteRoutine = new ArrayList<>();
                modelArrayListCompleteOmas = new ArrayList<>();
                modelArrayListCompleteSchool = new ArrayList<>();
                modelArrayListComplete = new ArrayList<>();
                DatabaseHandler databaseHandler = new DatabaseHandler(getActivity());
                String sFCID = CGlobal.getInstance().getPersistentPreference(getActivity()).getString(Constants.PREFS_USER_FACILITATOR_ID, "");
                modelArrayListCompleteRoutine = databaseHandler.getSampleCollection(sFCID, "Routine");
                modelArrayListCompleteOmas = databaseHandler.getSampleCollection(sFCID, "OMAS");
                modelArrayListCompleteSchool = databaseHandler.getSchoolAppDataCollection(sFCID, "School");

                modelArrayListComplete.addAll(modelArrayListCompleteRoutine);
                modelArrayListComplete.addAll(modelArrayListCompleteSchool);
                modelArrayListComplete.addAll(modelArrayListCompleteOmas);

                Collections.sort(modelArrayListComplete, new Comparator<SampleModel>() {

                    @Override
                    public int compare(SampleModel arg0, SampleModel arg1) {
                        return arg0.getCollection_date_q_4a().compareTo(arg1.getCollection_date_q_4a());
                    }
                });

                CompleteHabitationList_Adapter sourceDataAdapter = new CompleteHabitationList_Adapter(modelArrayListComplete, getActivity());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                sourceRecycler.setLayoutManager(linearLayoutManager);
                sourceRecycler.setItemAnimator(new DefaultItemAnimator());
                sourceRecycler.setAdapter(sourceDataAdapter);

                String labName = CGlobal.getInstance().getPersistentPreference(getActivity())
                        .getString(Constants.PREFS_USER_LAB_NAME, "");
                labDetail.setText(labName);
            }
        });

        btnInCompleteSample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llPWSSStatus.setVisibility(View.VISIBLE);
                btnInCompleteSample.setCompoundDrawablesWithIntrinsicBounds(R.drawable.checked, 0, 0, 0);
                btnCompleteSample.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                if (DashBoard_Facilitator_Activity.mCurrentLocation != null) {
                    modelArrayList = new ArrayList<>();
                    DatabaseHandler databaseHandler = new DatabaseHandler(getActivity());
                    modelArrayList = databaseHandler.getAssignHabitationList("YES");

                    Collections.sort(modelArrayList, new Comparator<CommonModel>() {

                        @Override
                        public int compare(CommonModel arg0, CommonModel arg1) {
                            return arg0.getCreatedDate().compareTo(arg1.getCreatedDate());
                        }
                    });

                    AssignedHabitationList_Adapter sourceDataAdapter = new AssignedHabitationList_Adapter(modelArrayList, getActivity(), "0",
                            DashBoard_Facilitator_Activity.mCurrentLocation.getLatitude(),
                            DashBoard_Facilitator_Activity.mCurrentLocation.getLongitude(), "YES");
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    sourceRecycler.setLayoutManager(linearLayoutManager);
                    sourceRecycler.setItemAnimator(new DefaultItemAnimator());
                    sourceRecycler.setAdapter(sourceDataAdapter);

                    String labName = CGlobal.getInstance().getPersistentPreference(getActivity())
                            .getString(Constants.PREFS_USER_LAB_NAME, "");
                    labDetail.setText(labName);
                }
            }
        });

        btnPWSSVillageSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llPWSSStatus.setVisibility(View.VISIBLE);
                btnPWSSVillageSource.setCompoundDrawablesWithIntrinsicBounds(R.drawable.checked, 0, 0, 0);
                btnNonPWSSVillageSource.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                if (DashBoard_Facilitator_Activity.mCurrentLocation != null) {
                    modelArrayList = new ArrayList<>();
                    DatabaseHandler databaseHandler = new DatabaseHandler(getActivity());
                    modelArrayList = databaseHandler.getAssignHabitationList("YES");

                    Collections.sort(modelArrayList, new Comparator<CommonModel>() {

                        @Override
                        public int compare(CommonModel arg0, CommonModel arg1) {
                            return arg0.getCreatedDate().compareTo(arg1.getCreatedDate());
                        }
                    });

                    AssignedHabitationList_Adapter sourceDataAdapter = new AssignedHabitationList_Adapter(modelArrayList, getActivity(), "0",
                            DashBoard_Facilitator_Activity.mCurrentLocation.getLatitude(),
                            DashBoard_Facilitator_Activity.mCurrentLocation.getLongitude(), "YES");
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    sourceRecycler.setLayoutManager(linearLayoutManager);
                    sourceRecycler.setItemAnimator(new DefaultItemAnimator());
                    sourceRecycler.setAdapter(sourceDataAdapter);

                    String labName = CGlobal.getInstance().getPersistentPreference(getActivity())
                            .getString(Constants.PREFS_USER_LAB_NAME, "");
                    labDetail.setText(labName);
                }
            }
        });

        btnNonPWSSVillageSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llPWSSStatus.setVisibility(View.VISIBLE);
                btnNonPWSSVillageSource.setCompoundDrawablesWithIntrinsicBounds(R.drawable.checked, 0, 0, 0);
                btnPWSSVillageSource.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                if (DashBoard_Facilitator_Activity.mCurrentLocation != null) {
                    modelArrayList = new ArrayList<>();
                    DatabaseHandler databaseHandler = new DatabaseHandler(getActivity());
                    modelArrayList = databaseHandler.getAssignHabitationList("");

                    Collections.sort(modelArrayList, new Comparator<CommonModel>() {

                        @Override
                        public int compare(CommonModel arg0, CommonModel arg1) {
                            return arg0.getCreatedDate().compareTo(arg1.getCreatedDate());
                        }
                    });

                    AssignedHabitationList_Adapter sourceDataAdapter = new AssignedHabitationList_Adapter(modelArrayList, getActivity(), "0",
                            DashBoard_Facilitator_Activity.mCurrentLocation.getLatitude(),
                            DashBoard_Facilitator_Activity.mCurrentLocation.getLongitude(), "NO");
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    sourceRecycler.setLayoutManager(linearLayoutManager);
                    sourceRecycler.setItemAnimator(new DefaultItemAnimator());
                    sourceRecycler.setAdapter(sourceDataAdapter);

                    String labName = CGlobal.getInstance().getPersistentPreference(getActivity())
                            .getString(Constants.PREFS_USER_LAB_NAME, "");
                    labDetail.setText(labName);
                }
            }
        });

        if (DashBoard_Facilitator_Activity.mCurrentLocation != null) {
            modelArrayList = new ArrayList<>();
            DatabaseHandler databaseHandler = new DatabaseHandler(getActivity());
            modelArrayList = databaseHandler.getAssignHabitationList("YES");

            Collections.sort(modelArrayList, new Comparator<CommonModel>() {

                @Override
                public int compare(CommonModel arg0, CommonModel arg1) {
                    return arg0.getCreatedDate().compareTo(arg1.getCreatedDate());
                }
            });

            AssignedHabitationList_Adapter sourceDataAdapter = new AssignedHabitationList_Adapter(modelArrayList, getActivity(), "0",
                    DashBoard_Facilitator_Activity.mCurrentLocation.getLatitude(), DashBoard_Facilitator_Activity.mCurrentLocation.getLongitude(), "YES");
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            sourceRecycler.setLayoutManager(linearLayoutManager);
            sourceRecycler.setItemAnimator(new DefaultItemAnimator());
            sourceRecycler.setAdapter(sourceDataAdapter);

            String labName = CGlobal.getInstance().getPersistentPreference(getActivity())
                    .getString(Constants.PREFS_USER_LAB_NAME, "");
            labDetail.setText(labName);
        }

        return myView;
    }

    ArrayList<CommonModel> checkedLists = new ArrayList<>();

    public class DistanceSorter implements Comparator<CommonModel> {
        @Override
        public int compare(CommonModel o1, CommonModel o2) {
            return String.valueOf(o1.getDistance()).compareToIgnoreCase(String.valueOf(o2.getDistance()));
        }
    }
}
