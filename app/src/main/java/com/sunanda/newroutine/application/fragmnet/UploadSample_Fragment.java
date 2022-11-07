package com.sunanda.newroutine.application.fragmnet;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.database.DatabaseHandler;
import com.sunanda.newroutine.application.modal.SampleModel_OMAS;
import com.sunanda.newroutine.application.modal.SampleModel_Routine;
import com.sunanda.newroutine.application.modal.SampleModel_School;
import com.sunanda.newroutine.application.ui.DataUploadOMAS_Activity;
import com.sunanda.newroutine.application.ui.DataUploadRoutine_Activity;
import com.sunanda.newroutine.application.ui.DataUploadSchool_Activity;
import com.sunanda.newroutine.application.ui.schoolomas.DataUploadSchoolOMAS_Activity;
import com.sunanda.newroutine.application.util.CGlobal;
import com.sunanda.newroutine.application.util.Constants;

import java.util.ArrayList;

public class UploadSample_Fragment extends Fragment {

    public static UploadSample_Fragment newInstance() {
        UploadSample_Fragment fragment = new UploadSample_Fragment();
        return fragment;
    }

    public UploadSample_Fragment() {
        // Required empty public constructor
    }

    RelativeLayout llRoutine, llSchool, llOMAS, llSchoolOMAS;
    View myView;
    TextView tvCountDataRoutine, tvCountDataSchool, tvCountDataOMAS, tvCountDataSchoolOMAS;
    ArrayList<SampleModel_Routine> sampleModelsDataCollectionRoutine;
    ArrayList<SampleModel_School> sampleModelsDataCollectionSchool;
    ArrayList<SampleModel_School> sampleModelsDataCollectionSchoolOMAS;
    ArrayList<SampleModel_OMAS> sampleModelsDataCollectionOMAS;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.choose_app_dialog_upload, container, false);

        llRoutine = myView.findViewById(R.id.llRoutine);
        llSchool = myView.findViewById(R.id.llSchool);
        llOMAS = myView.findViewById(R.id.llOMAS);
        llSchoolOMAS = myView.findViewById(R.id.llSchoolOMAS);

        tvCountDataRoutine = myView.findViewById(R.id.tvCountDataRoutine);
        tvCountDataSchool = myView.findViewById(R.id.tvCountDataSchool);
        tvCountDataOMAS = myView.findViewById(R.id.tvCountDataOMAS);
        tvCountDataSchoolOMAS = myView.findViewById(R.id.tvCountDataSchoolOMAS);

        llRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DataUploadRoutine_Activity.class);
                startActivity(intent);
            }
        });

        llSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DataUploadSchool_Activity.class);
                startActivity(intent);
            }
        });

        llOMAS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DataUploadOMAS_Activity.class);
                startActivity(intent);
            }
        });

        llSchoolOMAS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DataUploadSchoolOMAS_Activity.class);
                startActivity(intent);
            }
        });

        return myView;
    }

    @Override
    public void onResume() {
        super.onResume();
        DatabaseHandler databaseHandler = new DatabaseHandler(getActivity());
        String sFCID = CGlobal.getInstance().getPersistentPreference(getActivity())
                .getString(Constants.PREFS_USER_FACILITATOR_ID, "");

        sampleModelsDataCollectionRoutine = new ArrayList<>();
        sampleModelsDataCollectionRoutine = databaseHandler.getSampleCollectionRoutine(sFCID, "Routine");

        sampleModelsDataCollectionSchool = new ArrayList<>();
        sampleModelsDataCollectionSchool = databaseHandler.getSchoolAppDataCollectionSchool(sFCID, "School");

        sampleModelsDataCollectionSchoolOMAS = new ArrayList<>();
        sampleModelsDataCollectionSchoolOMAS = databaseHandler.getSchoolAppDataCollectionSchool(sFCID, "SchoolOMAS");

        sampleModelsDataCollectionOMAS = new ArrayList<>();
        sampleModelsDataCollectionOMAS = databaseHandler.getSampleCollectionOMAS(sFCID, "OMAS");

        tvCountDataRoutine.setText(String.valueOf(sampleModelsDataCollectionRoutine.size()));
        tvCountDataSchool.setText(String.valueOf(sampleModelsDataCollectionSchool.size()));
        tvCountDataOMAS.setText(String.valueOf(sampleModelsDataCollectionOMAS.size()));
        tvCountDataSchoolOMAS.setText(String.valueOf(sampleModelsDataCollectionSchoolOMAS.size()));
    }
}
