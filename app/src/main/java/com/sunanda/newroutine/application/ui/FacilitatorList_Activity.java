package com.sunanda.newroutine.application.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.adapter.FacilitatorList_Adapter;
import com.sunanda.newroutine.application.modal.CommonModel;
import com.sunanda.newroutine.application.util.CGlobal;
import com.sunanda.newroutine.application.util.Constants;
import com.sunanda.newroutine.application.util.PostInterface;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class FacilitatorList_Activity extends AppCompatActivity {

    RecyclerView rvFacilitatorList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facilitator_list);

        rvFacilitatorList = findViewById(R.id.rvFacilitatorList);

        getFacilitatorList();
    }

    ProgressDialog progressdialog;

    @Override
    protected void onResume() {
        super.onResume();
        progressdialog = new ProgressDialog(FacilitatorList_Activity.this);
        progressdialog.setMessage("Please Wait....");
    }

    private void getFacilitatorList() {
        boolean isConnected = CGlobal.getInstance().isConnected(FacilitatorList_Activity.this);
        if (isConnected) {
            try {
                progressdialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

            String sLabCode = CGlobal.getInstance().getPersistentPreference(FacilitatorList_Activity.this)
                    .getString(Constants.PREFS_USER_LAB_ID, "");

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(PostInterface.JSONURL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();

            PostInterface api = retrofit.create(PostInterface.class);

            Call<String> call = api.getFacilitatorListOnLab(sLabCode, "Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3");

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.i("Responsestring", response.body().toString());
                    progressdialog.dismiss();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            Log.i("onSuccess", response.body().toString());

                            String jsonresponse = response.body().toString();
                            getFacilitatorListResponse(jsonresponse);

                        } else {
                            Log.i("onEmptyResponse", "Returned empty response");
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.i("onEmptyResponse", "Returned empty response");
                    progressdialog.dismiss();
                }
            });
        } else {
            new AlertDialog.Builder(FacilitatorList_Activity.this)
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

    ArrayList<CommonModel> commonModelArrayList;

    private void getFacilitatorListResponse(String response) {
        progressdialog.dismiss();
        try {
            commonModelArrayList = new ArrayList<>();
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
            }

            FacilitatorList_Adapter adapter = new FacilitatorList_Adapter(commonModelArrayList, FacilitatorList_Activity.this);
            rvFacilitatorList.setAdapter(adapter);
            rvFacilitatorList.setLayoutManager(new LinearLayoutManager(FacilitatorList_Activity.this));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
