package com.sunanda.newroutine.application.fragmnet;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.adapter.FacilitatorList_Adapter;
import com.sunanda.newroutine.application.modal.CommonModel;
import com.sunanda.newroutine.application.util.CGlobal;
import com.sunanda.newroutine.application.util.Constants;
import com.sunanda.newroutine.application.util.PostInterface;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class FacilitatorLists extends Fragment {

    LinearLayout llLab, llFacilitator;
    RelativeLayout rlAssignFacilitator, rlCreateFacilitator, rlFacilitatorList;
    TextView tvCountData, tvLabName;
    View myView;

    RecyclerView rvFacilitatorList;
    ProgressDialog progressdialog;
    ArrayList<CommonModel> commonModelArrayList, activeList, inactiveList, tempList;
    EditText editTextSearch;
    FacilitatorList_Adapter adapter;
    static String jsonResponse;
    SwipeRefreshLayout mSwipeRefreshLayout;
    Boolean isActive = true;
    Button active, inactive;

    public static FacilitatorLists newInstance(String res) {
        jsonResponse = res;
        return new FacilitatorLists();
    }

    public FacilitatorLists() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView =  inflater.inflate(R.layout.fragment_fragment3, container, false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) myView.findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        rlAssignFacilitator = myView.findViewById(R.id.rlAssignFacilitator);
        rlCreateFacilitator = myView.findViewById(R.id.rlCreateFacilitator);
        rlFacilitatorList = myView.findViewById(R.id.rlFacilitatorList);
        llLab = myView.findViewById(R.id.llLab);
        llFacilitator = myView.findViewById(R.id.llFacilitator);

        tvCountData = myView.findViewById(R.id.tvCountData);
        tvLabName = myView.findViewById(R.id.tvLabName);

        inactive = myView.findViewById(R.id.inactive);
        active = myView.findViewById(R.id.active);

        /*String userType = CGlobal.getInstance().getPersistentPreference(getActivity())
                .getString(Constants.PREFS_ROUTINE_USER_TYPE, "");

        if (userType.equalsIgnoreCase("facilitator")) {
            llLab.setVisibility(View.GONE);
            llFacilitator.setVisibility(View.VISIBLE);
        } else if (userType.equalsIgnoreCase("lab")) {
            llLab.setVisibility(View.VISIBLE);
            llFacilitator.setVisibility(View.GONE);
        } else {
            llLab.setVisibility(View.GONE);
            llFacilitator.setVisibility(View.GONE);
        }*/

        CGlobal.getInstance().getPersistentPreferenceEditor(getActivity())
                .putInt(Constants.PREFS_ASSIGN_FACILITATOR_LIST, 2).commit();
        rvFacilitatorList = myView.findViewById(R.id.rvFacilitatorList);
        progressdialog = new ProgressDialog(getActivity());
        progressdialog.setMessage("Please Wait....");
        editTextSearch = myView.findViewById(R.id.editTextSearch);

        getFacilitatorListResponse(jsonResponse);

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //after the change calling the method and passing the search input
                filter(editable.toString());
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                active.setCompoundDrawablesWithIntrinsicBounds(R.drawable.checked, 0, 0, 0);
                inactive.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                getFacilitatorList();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                active.setCompoundDrawablesWithIntrinsicBounds(R.drawable.checked, 0, 0, 0);
                inactive.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                adapter = new FacilitatorList_Adapter(activeList, getActivity());
                rvFacilitatorList.setAdapter(adapter);
                rvFacilitatorList.setLayoutManager(new LinearLayoutManager(getActivity()));
                adapter.notifyDataSetChanged();
                isActive = true;
            }
        });
        inactive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inactive.setCompoundDrawablesWithIntrinsicBounds(R.drawable.checked, 0, 0, 0);
                active.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                adapter = new FacilitatorList_Adapter(inactiveList, getActivity());
                rvFacilitatorList.setAdapter(adapter);
                rvFacilitatorList.setLayoutManager(new LinearLayoutManager(getActivity()));
                adapter.notifyDataSetChanged();
                isActive = false;
            }
        });

        return myView;
    }


    private void filter(String text) {
        if (isActive)
            tempList = activeList;
        else
            tempList = inactiveList;
        try {
            //new array list that will hold the filtered data
            ArrayList<CommonModel> filterdNames = new ArrayList<>();
            ArrayList<String> names = new ArrayList<>();
            for (int i = 0; i < tempList.size(); i++) {
                names.add(tempList.get(i).user_name);
            }

            //looping through existing elements
            for (String s : names) {
                //if the existing elements contains the search input
                if (s.toLowerCase().contains(text.toLowerCase())) {
                    //adding the element to filtered list
                    filterdNames.add(tempList.get(names.indexOf(s)));
                }
            }
            //calling a method of the adapter class and passing the filtered list
            adapter.updateList(filterdNames);
        } catch (Exception ignored) {
        }
    }


    private void getFacilitatorListResponse(String response) {
        progressdialog.dismiss();
        try {
            commonModelArrayList = new ArrayList<>();
            activeList = new ArrayList<>();
            inactiveList = new ArrayList<>();
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
                String Pan_Codes = CGlobal.getInstance().isNullNotDefined(oFacilitator, "Pan_Codes") ? "" : oFacilitator.getString("Pan_Codes");
                String Block_Code = CGlobal.getInstance().isNullNotDefined(oFacilitator, "Block_Code") ? "" : oFacilitator.getString("Block_Code");
                String PanNames = CGlobal.getInstance().isNullNotDefined(oFacilitator, "PanNames") ? "" : oFacilitator.getString("PanNames");
                String BlockName = CGlobal.getInstance().isNullNotDefined(oFacilitator, "BlockName") ? "" : oFacilitator.getString("BlockName");
                String Dist_Code = CGlobal.getInstance().isNullNotDefined(oFacilitator, "Dist_Code") ? "" : oFacilitator.getString("Dist_Code");
                String DistName = CGlobal.getInstance().isNullNotDefined(oFacilitator, "DistName") ? "" : oFacilitator.getString("DistName");

                CommonModel commonModel = new CommonModel();
                commonModel.setFCID(FCID);
                commonModel.setName(name);
                commonModel.setEmail(email);
                commonModel.setPassword(password);
                commonModel.setMobile(Mobile);
                commonModel.setUser_type(user_type);
                commonModel.setUser_name(UserName);
                commonModel.setIs_active(is_active ? "1" : "0");
                commonModel.setLabCode(LabCode);
                commonModel.setLabId(LabID);
                commonModel.setFCPanNames(PanNames);
                commonModel.setFCPan_Codes(Pan_Codes);
                commonModel.setFCBlock_Code(Block_Code);
                commonModel.setFCBlockName(BlockName);
                commonModel.setFCDistNam(DistName);
                commonModel.setFCDist_Code(Dist_Code);

                commonModelArrayList.add(commonModel);

                if (oFacilitator.getString("IsActive").equalsIgnoreCase("true"))
                    activeList.add(commonModel);
                else
                    inactiveList.add(commonModel);
            }
            adapter = new FacilitatorList_Adapter(activeList, getActivity());
            rvFacilitatorList.setAdapter(adapter);
            rvFacilitatorList.setLayoutManager(new LinearLayoutManager(getActivity()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getFacilitatorList() {

        boolean isConnected = CGlobal.getInstance().isConnected(getActivity());
        if (isConnected) {

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
                    Log.i("Responsestring", response.body());
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            jsonResponse = response.body();
                            getFacilitatorListResponse(jsonResponse);
                        } else {
                            //Log.i("onEmptyResponse", "Returned empty jsonResponse");
                            Toast.makeText(getActivity(), "Unable to get data", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    //Log.i("onEmptyResponse", "Returned empty jsonResponse");
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            new AlertDialog.Builder(getActivity())
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
}
