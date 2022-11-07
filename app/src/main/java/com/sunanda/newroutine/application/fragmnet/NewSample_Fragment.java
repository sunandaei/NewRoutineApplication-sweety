package com.sunanda.newroutine.application.fragmnet;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.newactivity.OMASNewSample_Activity;
import com.sunanda.newroutine.application.newactivity.RoutineNewSample_Activity;
import com.sunanda.newroutine.application.newactivity.SchoolNewSample_Activity;
import com.sunanda.newroutine.application.newactivity.SchoolOMASNewSample_Activity;

public class NewSample_Fragment extends Fragment {

    public static NewSample_Fragment newInstance() {
        NewSample_Fragment fragment = new NewSample_Fragment();
        return fragment;
    }

    public NewSample_Fragment() {
        // Required empty public constructor
    }

    LinearLayout llRoutine, llSchool, llOMAS, llSchoolOMAS;

    View myView;
    String sTaskIdx = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.choose_app_dialog, container, false);

        llRoutine = myView.findViewById(R.id.llRoutine);
        llSchool = myView.findViewById(R.id.llSchool);
        llOMAS = myView.findViewById(R.id.llOMAS);
        llSchoolOMAS = myView.findViewById(R.id.llSchoolOMAS);

        llRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RoutineNewSample_Activity.class);
                startActivity(intent);
            }
        });

        llSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SchoolNewSample_Activity.class);
                startActivity(intent);
            }
        });

        llOMAS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OMASNewSample_Activity.class);
                startActivity(intent);
            }
        });

        llSchoolOMAS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SchoolOMASNewSample_Activity.class);
                startActivity(intent);
            }
        });

        return myView;
    }
}
