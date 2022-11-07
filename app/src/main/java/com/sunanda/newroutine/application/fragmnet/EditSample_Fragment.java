package com.sunanda.newroutine.application.fragmnet;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class EditSample_Fragment extends Fragment {

    public static EditSample_Fragment newInstance() {
        EditSample_Fragment fragment = new EditSample_Fragment();
        return fragment;
    }

    public EditSample_Fragment() {
        // Required empty public constructor
    }

    TextView tvFromDate, tvToDate;
    Button btnSearch;
    RecyclerView rvSearchList;
    ArrayList<SampleModel> commonModelArrayList;
    View myView;
    //String sTaskIdx = "";
    private RadioGroup rAppName;
    private RadioButton rSelectId;
    int selectedId;
    String sSelectId = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.edit_sample_fragment, container, false);

        tvFromDate = myView.findViewById(R.id.tvFromDate);
        tvToDate = myView.findViewById(R.id.tvToDate);

        rvSearchList = myView.findViewById(R.id.rvSearchList);

        btnSearch = myView.findViewById(R.id.btnSearch);

        tvFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        tvToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSearchResult();
            }
        });

        return myView;
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

    private void getSearchResult() {
        commonModelArrayList = new ArrayList<>();

        rAppName = myView.findViewById(R.id.rAppName);
        selectedId = rAppName.getCheckedRadioButtonId();
        rSelectId = myView.findViewById(selectedId);

        if (selectedId == -1) {
            new AlertDialog.Builder(getActivity())
                    .setCancelable(false)
                    .setMessage("⚠ Please Select Type")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();

            return;
        } else {
            sSelectId = rSelectId.getText().toString();
        }

        if (!TextUtils.isEmpty(sSelectId)) {
            if (sSelectId.equalsIgnoreCase("Routine")) {

                String fromDate = tvFromDate.getText().toString();
                String toDate = tvToDate.getText().toString();

                DatabaseHandler databaseHandler = new DatabaseHandler(getActivity());
                /*try {
                    sTaskIdx = databaseHandler.getTaskIdOne();
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
                String sFCID = CGlobal.getInstance().getPersistentPreference(getActivity())
                        .getString(Constants.PREFS_USER_FACILITATOR_ID, "");

                commonModelArrayList = databaseHandler.getSearch(fromDate, toDate, sFCID, "Routine");

            } else if (sSelectId.equalsIgnoreCase("School")) {

                String fromDate = tvFromDate.getText().toString();
                String toDate = tvToDate.getText().toString();

                DatabaseHandler databaseHandler = new DatabaseHandler(getActivity());
                /*try {
                    sTaskIdx = databaseHandler.getTaskIdOne();
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
                String sFCID = CGlobal.getInstance().getPersistentPreference(getActivity())
                        .getString(Constants.PREFS_USER_FACILITATOR_ID, "");

                commonModelArrayList = databaseHandler.getSearchSchool(fromDate, toDate, sFCID, "School");

            } else if (sSelectId.equalsIgnoreCase("SchoolOMAS")) {

                String fromDate = tvFromDate.getText().toString();
                String toDate = tvToDate.getText().toString();

                DatabaseHandler databaseHandler = new DatabaseHandler(getActivity());
                /*try {
                    sTaskIdx = databaseHandler.getTaskIdOne();
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
                String sFCID = CGlobal.getInstance().getPersistentPreference(getActivity())
                        .getString(Constants.PREFS_USER_FACILITATOR_ID, "");

                commonModelArrayList = databaseHandler.getSearchSchool(fromDate, toDate, sFCID, "SchoolOMAS");

            } else if (sSelectId.equalsIgnoreCase("OMAS")) {

                String fromDate = tvFromDate.getText().toString();
                String toDate = tvToDate.getText().toString();

                DatabaseHandler databaseHandler = new DatabaseHandler(getActivity());
                /*try {
                    sTaskIdx = databaseHandler.getTaskIdOne();
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
                String sFCID = CGlobal.getInstance().getPersistentPreference(getActivity())
                        .getString(Constants.PREFS_USER_FACILITATOR_ID, "");

                commonModelArrayList = databaseHandler.getSearch(fromDate, toDate, sFCID, "OMAS");

            } else {
                new AlertDialog.Builder(getActivity())
                        .setCancelable(false)
                        .setMessage("⚠ Please Select User Type")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        } else {
            new AlertDialog.Builder(getActivity())
                    .setCancelable(false)
                    .setMessage("⚠ Please Select User Type")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        }


        if (commonModelArrayList.size() > 0) {
            Search_Adapter adapter = new Search_Adapter(getActivity(), commonModelArrayList, sSelectId);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            rvSearchList.setLayoutManager(mLayoutManager);
            rvSearchList.setItemAnimator(new DefaultItemAnimator());
            rvSearchList.setAdapter(adapter);
        } else {
            Search_Adapter adapter = new Search_Adapter(getActivity(), commonModelArrayList, sSelectId);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            rvSearchList.setLayoutManager(mLayoutManager);
            rvSearchList.setItemAnimator(new DefaultItemAnimator());
            rvSearchList.setAdapter(adapter);
            new AlertDialog.Builder(getActivity())
                    .setMessage("No Data Found")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }
    }
}
