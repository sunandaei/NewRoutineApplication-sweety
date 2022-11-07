package com.sunanda.newroutine.application.fragmnet;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.somenath.pojo.LabWorkStatus;
import com.sunanda.newroutine.application.somenath.view.Cover_UncoverDetails;
import com.sunanda.newroutine.application.ui.Login_Activity;
import com.sunanda.newroutine.application.util.CGlobal;
import com.sunanda.newroutine.application.util.Constants;
import com.sunanda.newroutine.application.util.PostInterface;
import com.sunanda.newroutine.application.util.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
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

public class WorkStatusFragment extends Fragment {

    ProgressBar progressBar1;
    View myView;
    TextView covered, uncovered, total, reload;
    SessionManager sessionManager;
    ArrayList<LabWorkStatus> labWorkStatusArrayList;
    int count_touched = 0, count_untouched = 0;

    public WorkStatusFragment() {
        // Required empty public constructor
    }

    public static WorkStatusFragment newInstance() {
        return new WorkStatusFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_work_status, container, false);
        progressBar1 = myView.findViewById(R.id.progressbar1);
        covered = myView.findViewById(R.id.covered);
        uncovered = myView.findViewById(R.id.uncovered);
        total = myView.findViewById(R.id.total);
        sessionManager = new SessionManager(getContext());

        //getWorkStatus();

        labWorkStatusArrayList = sessionManager.getArrayList("LAB_STAT");

        myView.findViewById(R.id.btnCover).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "Under Development", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), Cover_UncoverDetails.class);
                //intent.putExtra("LIST", labWorkStatusArrayList);
                intent.putExtra("TYPE", "1");
                getActivity().overridePendingTransition(R.anim.trans_left_in, R.anim.trans_right_out);
                startActivity(intent);
            }
        });
        myView.findViewById(R.id.btnunCover).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "Under Development", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), Cover_UncoverDetails.class);
                //intent.putExtra("LIST", labWorkStatusArrayList);
                intent.putExtra("TYPE", "0");
                getActivity().overridePendingTransition(R.anim.trans_left_in, R.anim.trans_right_out);
                startActivity(intent);
            }
        });

        //Toast.makeText(getContext(), "" + labWorkStatusArrayList.size(), Toast.LENGTH_SHORT).show();
        try {
            for (int i = 0; i < labWorkStatusArrayList.size(); i++) {

                JSONArray villageHab = new JSONArray(labWorkStatusArrayList.get(i).getVillageHab());
                for (int j = 0; j < villageHab.length(); j++) {
                    JSONArray habitation = new JSONArray(villageHab.getJSONObject(j).getJSONArray("habitation").toString());
                    for (int k = 0; k < habitation.length(); k++) {
                        if (habitation.getJSONObject(k).getString("touched").equalsIgnoreCase("1"))
                            count_touched++;
                        else
                            count_untouched++;
                    }
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
            FetchData();
        }
        int c = count_touched;
        int t = count_touched + count_untouched;
        int uc = count_untouched;
        covered.setText("■ Coverage Habitations: " + c + " (" + String.format("%.2f", (float) 100 * c / t) + "%)");
        uncovered.setText("■ Untouched Habitations: " + uc + " (" + String.format("%.2f", (float) 100 * uc / t) + "%)");
        total.setText("■ Total Habitations: " + t);
        progressBar1.setMax(t);
        progressBar1.setProgress(c);

        reload = myView.findViewById(R.id.btnunCover);
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setMessage("Are you sure you want to reload information?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                FetchData();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setCancelable(false)
                        .show();
            }
        });

        return myView;
    }

    private void FetchData() {

        count_touched = 0;
        count_untouched = 0;

        final ProgressDialog progressdialog;
        progressdialog = new ProgressDialog(getContext());
        progressdialog.setMessage("Please Wait! We are fetching necessary information....");
        progressdialog.setCancelable(false);

        boolean isConnected = CGlobal.getInstance().isConnected(getContext());
        if (isConnected) {
            try {
                progressdialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .connectTimeout(60 * 3, TimeUnit.SECONDS)
                    .readTimeout(60 * 3, TimeUnit.SECONDS)
                    .writeTimeout(60 * 3, TimeUnit.SECONDS)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {
                            okhttp3.Request.Builder ongoing = chain.request().newBuilder();
                            return chain.proceed(ongoing.build());
                        }
                    })
                    .build();

            String labCode = CGlobal.getInstance().getPersistentPreference(getContext())
                    .getString(Constants.PREFS_USER_LAB_CODE, "");

            String districtCode = CGlobal.getInstance().getPersistentPreference(getContext())
                    .getString(Constants.PREFS_USER_DISTRICT_CODE, "");
            //Log.d("labCode!!", labCode);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(PostInterface.URL_RNJ)
                    .client(httpClient)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();

            PostInterface api = retrofit.create(PostInterface.class);

            Call<String> call = api.lab_work_status_details(labCode, districtCode);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                    //Log.i("Responsestring", response.body());

                    ArrayList<LabWorkStatus> labWorkStatusList = new ArrayList<>();

                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            //Log.i("onSuccess", response.body());
                            String jsonresponse = response.body();
                            try {
                                JSONArray jsonArray = new JSONArray(jsonresponse);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    LabWorkStatus labWorkStatus = new LabWorkStatus();
                                    labWorkStatus.set_id(jsonObject.getJSONObject("_id").getString("$oid"));
                                    labWorkStatus.setStateCode(jsonObject.getString("state_code"));
                                    labWorkStatus.setDistCode(jsonObject.getString("dist_code"));
                                    labWorkStatus.setDistName(jsonObject.getString("dist_name"));
                                    labWorkStatus.setBlockCode(jsonObject.getString("block_code"));
                                    labWorkStatus.setBlockName(jsonObject.getString("block_name"));
                                    labWorkStatus.setPanCodeUnique(jsonObject.getString("pan_code_unique"));
                                    labWorkStatus.setPanCode(jsonObject.getString("pan_code"));
                                    labWorkStatus.setPanchayatName(jsonObject.getString("panchayat_name"));
                                    labWorkStatus.setLabCode(jsonObject.getString("LabCode"));
                                    labWorkStatus.setLabName(jsonObject.getString("lab_name"));
                                    labWorkStatus.setVillageHab(jsonObject.getJSONArray("villageHab").toString());

                                    labWorkStatusList.add(labWorkStatus);
                                }

                                if (labWorkStatusList.size() != 0) {
                                    sessionManager.saveArrayList(labWorkStatusList, "LAB_STAT");
                                    labWorkStatusArrayList = labWorkStatusList;
                                    for (int i = 0; i < labWorkStatusArrayList.size(); i++) {

                                        JSONArray villageHab = new JSONArray(labWorkStatusArrayList.get(i).getVillageHab());
                                        for (int j = 0; j < villageHab.length(); j++) {
                                            JSONArray habitation = new JSONArray(villageHab.getJSONObject(j).getJSONArray("habitation").toString());
                                            for (int k = 0; k < habitation.length(); k++) {
                                                if (habitation.getJSONObject(k).getString("touched").equalsIgnoreCase("1"))
                                                    count_touched++;
                                                else
                                                    count_untouched++;
                                            }
                                        }
                                    }
                                    int c = count_touched;
                                    int t = count_touched + count_untouched;
                                    int uc = count_untouched;
                                    covered.setText("■ Coverage Habitations: " + c + " (" + String.format("%.2f", (float) 100 * c / t) + "%)");
                                    uncovered.setText("■ Untouched Habitations: " + uc + " (" + String.format("%.2f", (float) 100 * uc / t) + "%)");
                                    total.setText("■ Total Habitations: " + t);
                                    progressBar1.setMax(t);
                                    progressBar1.setProgress(c);
                                } else {
                                    new AlertDialog.Builder(getContext())
                                            .setMessage("Please try again")
                                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                    FetchData();
                                                }
                                            })
                                            //.setIcon(android.R.drawable.ic_dialog_alert)
                                            .setCancelable(false)
                                            .show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            //Log.i("onEmptyResponse", "Returned empty response");
                            Toast.makeText(getContext(), "Unable to get data", Toast.LENGTH_SHORT).show();
                        }
                        progressdialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    //Log.i("onEmptyResponse", "Returned empty response");
                    progressdialog.dismiss();
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            new AlertDialog.Builder(getContext())
                    .setMessage("Please check your Internet connection. Please try again")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            getActivity().finish();
                        }
                    })
                    //.setIcon(android.R.drawable.ic_dialog_alert)
                    .setCancelable(false)
                    .show();
        }
    }



   /* private void getWorkStatus() {

        final ProgressDialog progressdialog;
        progressdialog = new ProgressDialog(getContext());
        progressdialog.setMessage("Please Wait....");
        progressdialog.setCancelable(false);

        boolean isConnected = CGlobal.getInstance().isConnected(getContext());
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

            PostInterface api = retrofit.create(PostInterface.class);

            Call<ResponseBody> call = api.lab_work_status(CGlobal.getInstance().getPersistentPreference(getContext())
                    .getString(Constants.PREFS_USER_LAB_CODE, ""));

            call.enqueue(new Callback<ResponseBody>() {
                @SuppressLint({"DefaultLocale", "SetTextI18n"})
                @Override
                public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                    //Log.i("Responsestring", response.message());
                    progressdialog.dismiss();

                    if (response.body() != null) {
                        try {
                            JSONArray jsonArray = new JSONArray(response.body().string());
                            progressBar1.setProgress(Integer.parseInt(jsonArray.getJSONObject(0).getString("covered_hab")));
                            progressBar1.setMax(Integer.parseInt(jsonArray.getJSONObject(0).getString("total_hab")));
                            int c = Integer.parseInt(jsonArray.getJSONObject(0).getString("covered_hab"));
                            int t = Integer.parseInt(jsonArray.getJSONObject(0).getString("total_hab"));
                            int uc = Integer.parseInt(jsonArray.getJSONObject(0).getString("total_hab")) -
                                    Integer.parseInt(jsonArray.getJSONObject(0).getString("covered_hab"));
                            covered.setText("■ Coverage Habitation: " + c + " (" + String.format("%.2f", (float) 100 * c / t) + "%)");
                            uncovered.setText("■ Untouched Habitation: " + uc + " (" + String.format("%.2f", (float) 100 * uc / t) + "%)");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        //Log.i("onEmptyResponse", "Returned empty jsonResponse");
                        Toast.makeText(getActivity(), "Unable to get data", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    //Log.i("onEmptyResponse", "Returned empty jsonResponse");
                    progressdialog.dismiss();
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            new android.support.v7.app.AlertDialog.Builder(getActivity())
                    .setMessage("Please check your Internet connection. Please try again")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }*/
}
