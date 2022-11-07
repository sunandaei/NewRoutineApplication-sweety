package com.sunanda.newroutine.application.fragmnet;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.somenath.myadapter.NewBlockAdapter;
import com.sunanda.newroutine.application.somenath.pojo.ResponsePanchyat;
import com.sunanda.newroutine.application.ui.Login_Activity;
import com.sunanda.newroutine.application.util.CGlobal;
import com.sunanda.newroutine.application.util.Constants;
import com.sunanda.newroutine.application.util.PostInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FCWOrkAreaFragment extends Fragment {

    TextView tvLabName, tvDistrictName, txtPan;
    ArrayList<ResponsePanchyat> responsePanArrayList;
    ArrayList<String> blockName = new ArrayList<>();
    ArrayList<String> blockCode = new ArrayList<>();
    ArrayList<String> cmaPanchayatName = new ArrayList<>();
    ArrayList<String> cmaPanchayatCode = new ArrayList<>();

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    public static FCWOrkAreaFragment newInstance() {
        FCWOrkAreaFragment fragment = new FCWOrkAreaFragment();
        return fragment;
    }

    View myView;

    public FCWOrkAreaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (container != null) {
            container.removeAllViews();
        }
        // OR container.clearDisappearingChildren();


        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_fcwork_area, container, false);
        tvDistrictName = myView.findViewById(R.id.tvDistrictName);
        tvLabName = myView.findViewById(R.id.tvLabName);
        txtPan = myView.findViewById(R.id.txtPan);

        tvLabName.setText(CGlobal.getInstance().getPersistentPreference(getContext())
                .getString(Constants.PREFS_USER_LAB_NAME, ""));

        recyclerView = myView.findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        return myView;
    }

    @Override
    public void onResume(){
        super.onResume();
        loadSourceData();
    }

    private void loadSourceData() {

        responsePanArrayList = new ArrayList<>();
        blockCode = new ArrayList<>();
        blockName = new ArrayList<>();
        cmaPanchayatCode = new ArrayList<>();
        cmaPanchayatName = new ArrayList<>();

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

            String labcode = CGlobal.getInstance().getPersistentPreference(getContext())
                    .getString(Constants.PREFS_USER_LAB_CODE, "");
            String sdistrictCode = CGlobal.getInstance().getPersistentPreference(getContext())
                    .getString(Constants.PREFS_USER_DISTRICT_CODE, "");

            PostInterface api = retrofit.create(PostInterface.class);

            Call<List<ResponsePanchyat>> call = api.lab_relation(labcode, sdistrictCode);

            call.enqueue(new Callback<List<ResponsePanchyat>>() {
                @Override
                public void onResponse(Call<List<ResponsePanchyat>> call, retrofit2.Response<List<ResponsePanchyat>> response) {
                    //Log.i("Responsestring", response.message());

                    if (response.body() != null) {
                        responsePanArrayList.addAll(response.body());
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
                        Toast.makeText(getActivity(), "Unable to get data", Toast.LENGTH_SHORT).show();
                    }
                    progressdialog.dismiss();
                }

                @Override
                public void onFailure(Call<List<ResponsePanchyat>> call, Throwable t) {
                    //Log.i("onEmptyResponse", "Returned empty jsonResponse");
                    progressdialog.dismiss();
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            new androidx.appcompat.app.AlertDialog.Builder(getActivity())
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

        NewBlockAdapter slotAdapter = new NewBlockAdapter(blockName, blockCode, responsePanArrayList,
                getActivity(), txtPan, new BtnSelect() {
            @Override
            public void onBtnSelectValue(int position) {
            }
        });
        recyclerView.setAdapter(slotAdapter);

    }

    public interface BtnSelect {
        void onBtnSelectValue(int position);
    }
}
