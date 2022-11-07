package com.sunanda.newroutine.application.fragmnet;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.somenath.myadapter.MySpinnerAdapter;
import com.sunanda.newroutine.application.somenath.pojo.ResponsePanchyat;
import com.sunanda.newroutine.application.ui.Login_Activity;
import com.sunanda.newroutine.application.util.CGlobal;
import com.sunanda.newroutine.application.util.Constants;
import com.sunanda.newroutine.application.util.PostInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class CreateFacilitator extends Fragment {

    EditText name, email, mobile, pass;
    View myView;
    TextView uname;
    Button btnSubmit;
    ProgressDialog progressdialog;

    ArrayList<ResponsePanchyat> responsePanArrayList;

    ArrayList<String> blockName = new ArrayList<>();
    ArrayList<String> blockCode = new ArrayList<>();

    ArrayList<String> cmaPanchayatName = new ArrayList<>();
    ArrayList<String> cmaPanchayatCode = new ArrayList<>();

    Spinner spBlock;
    String sBlockName = "", sBlockCode = "", newBlockCode = "";
    TextView tvPanchayat;
    android.app.AlertDialog.Builder alertdialogbuilder1;
    boolean[] selectedtruefalse;
    String[] alertDialogItems1;
    String pan_name = "", pan_code = "";
    String districtCode = "", distName = "";

    public static CreateFacilitator newInstance() {
        return new CreateFacilitator();
    }


    public CreateFacilitator() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_fragment2, container, false);

        progressdialog = new ProgressDialog(getActivity());
        progressdialog.setMessage("Please Wait....");

        name = myView.findViewById(R.id.name);
        email = myView.findViewById(R.id.email);
        uname = myView.findViewById(R.id.uname);
        mobile = myView.findViewById(R.id.mobile);
        pass = myView.findViewById(R.id.pass);
        btnSubmit = myView.findViewById(R.id.btnSubmit);
        tvPanchayat = myView.findViewById(R.id.tvPanchayat);
        spBlock = myView.findViewById(R.id.spBlock);

        name.addTextChangedListener(watch);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(name.getText().toString())) {
                    name.setError("Enter Name");
                    name.requestFocus();
                } else if (!TextUtils.isEmpty(email.getText().toString()) && !isValidEmail(email.getText().toString())) {
                    email.setError("Enter Valid Email");
                    email.requestFocus();
                } else if (!TextUtils.isEmpty(mobile.getText().toString()) && mobile.getText().toString().length() != 10) {
                    mobile.setError("Enter Valid Mobile");
                    mobile.requestFocus();
                } else if (TextUtils.isEmpty(pass.getText().toString())) {
                    pass.setError("Enter Password");
                    pass.requestFocus();
                } else if (TextUtils.isEmpty(sBlockCode)) {
                    //Toast.makeText(getActivity(), "Please Select Block", Toast.LENGTH_SHORT).show();
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content),
                            "Please Select Block", Snackbar.LENGTH_LONG);
                    /*View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    TextView textView = snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.WHITE);*/
                    snackbar.show();
                } else if (TextUtils.isEmpty(pan_code)) {
                    //Toast.makeText(getActivity(), "Please Select Panchayat", Toast.LENGTH_SHORT).show();
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content),
                            "Please Select Panchayat", Snackbar.LENGTH_LONG);
                    /*View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    TextView textView = snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.WHITE);*/
                    snackbar.show();
                } else {
                    /*AlertDialog dialog = new AlertDialog.Builder(getActivity()).setMessage("").show();
                    TextView textView = (TextView) dialog.findViewById(android.R.id.message);
                    assert textView != null;
                    textView.setText("Name : " + name.getText().toString() + "\n" +
                            "Email : " + (TextUtils.isEmpty(email.getText().toString()) ? "---" : email.getText().toString()) + "\n" +
                            "Mobile : " + (TextUtils.isEmpty(mobile.getText().toString()) ? "---" : mobile.getText().toString()) + "\n" +
                            "User Name : " + uname.getText().toString() + "\n" +
                            "Password : " + pass.getText().toString());
                    Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "proxima_nova_light.ttf");
                    textView.setTypeface(face);
                    textView.setTextColor(Color.RED);
                    textView.setTextSize(16);*/
                    saveRegister();
                }
            }
        });

        tvPanchayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getpanchayat();
            }
        });

        loadSourceData();

        return myView;
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    TextWatcher watch = new TextWatcher() {

        String sLabCode = CGlobal.getInstance().getPersistentPreference(getContext())
                .getString(Constants.PREFS_USER_LAB_CODE, "");

        @Override
        public void afterTextChanged(Editable arg0) {

        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onTextChanged(CharSequence s, int a, int b, int c) {

            if (s.length() != 0)
                uname.setText(s.toString().replace(" ", "") + sLabCode.substring(sLabCode.length() - 3));
            else
                uname.setText("");
        }
    };


    private void saveRegister() {
        boolean isConnected = CGlobal.getInstance().isConnected(getActivity());
        if (isConnected) {
            try {
                progressdialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

            String labid = CGlobal.getInstance().getPersistentPreference(getContext())
                    .getString(Constants.PREFS_USER_LAB_ID, "");
            String PREFS_USER_USRUNIQUE_ID = CGlobal.getInstance().getPersistentPreference(getContext())
                    .getString(Constants.PREFS_USER_USRUNIQUE_ID, "");

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(PostInterface.JSONURL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();

            PostInterface api = retrofit.create(PostInterface.class);

            Call<String> call = api.getRegister(name.getText().toString().trim(), mobile.getText().toString().trim(),
                    uname.getText().toString(), email.getText().toString().trim(),
                    pass.getText().toString().trim(), "facilitator", labid, pan_code, pan_name,
                    sBlockCode, sBlockName, districtCode, distName, "true",
                    "Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3", PREFS_USER_USRUNIQUE_ID);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    //Log.i("Responsestring", response.body());
                    progressdialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().trim());
                        if (jsonObject.getBoolean("response")) {
                            if (response.body() != null) {
                                //Log.i("onSuccess", response.body());
                                saveRegisterResponse();
                            } else {
                                //Toast.makeText(getContext(), "Unable to create Facilitator", Toast.LENGTH_SHORT).show();
                                //Log.i("onEmptyResponse", "Returned empty jsonResponse");
                                new AlertDialog.Builder(getContext())
                                        .setTitle("Unable to create Facilitator")
                                        .setMessage("Please use alternate name of the Facilitator")
                                        // Specifying a listener allows you to take an action before dismissing the dialog.
                                        // The dialog is automatically dismissed when a dialog button is clicked.
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        })
                                        //.setNegativeButton(android.R.string.no, null)
                                        .setCancelable(false)
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
                            }
                        }else{
                            new AlertDialog.Builder(getContext())
                                    .setTitle("Unable to create Facilitator")
                                    .setMessage("Facilitator's UserName already exists, please use alternate name of the Facilitator.")
                                    // Specifying a listener allows you to take an action before dismissing the dialog.
                                    // The dialog is automatically dismissed when a dialog button is clicked.
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    //.setNegativeButton(android.R.string.no, null)
                                    .setCancelable(false)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.i("onEmptyResponse", "Returned empty jsonResponse");
                    progressdialog.dismiss();
                }
            });
        } else {
            new AlertDialog.Builder(getContext())
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
                        districtCode = responsePanArrayList.get(0).getDistCode();
                        distName = responsePanArrayList.get(0).getDistName();
                        //tvDistrictName.setText(responsePanArrayList.get(0).getDistName());

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

        MySpinnerAdapter block_adapter = new MySpinnerAdapter(getContext(), blockName);
        spBlock.setAdapter(block_adapter);
        spBlock.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                newBlockCode = sBlockCode;
                sBlockName = blockName.get(position);
                sBlockCode = blockCode.get(position);
                pan_name = "";
                pan_code = "";
                tvPanchayat.setText("");

                selectedtruefalse = new boolean[0];
                //Arrays.fill( example, null );

                for (int a = 0; a < responsePanArrayList.size(); a++) {
                    if (sBlockCode.equalsIgnoreCase(responsePanArrayList.get(a).getBlockCode())) {
                        cmaPanchayatName.add(responsePanArrayList.get(a).getPanchayatName());
                        cmaPanchayatCode.add(responsePanArrayList.get(a).getPanCode());
                    }
                }

                selectedtruefalse = new boolean[cmaPanchayatCode.size()];
                for (int i = 0; i < selectedtruefalse.length; i++) {
                    selectedtruefalse[i] = false;
                }
                //Toast.makeText(getContext(), "" + selectedtruefalse.length, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getpanchayat() {

        cmaPanchayatName = new ArrayList<>();
        cmaPanchayatCode = new ArrayList<>();

        if (!TextUtils.isEmpty(sBlockCode)) {

            alertDialogItems1 = new String[]{};
            alertdialogbuilder1 = new android.app.AlertDialog.Builder(getContext());

            for (int a = 0; a < responsePanArrayList.size(); a++) {
                if (sBlockCode.equalsIgnoreCase(responsePanArrayList.get(a).getBlockCode())) {
                    cmaPanchayatName.add(responsePanArrayList.get(a).getPanchayatName());
                    cmaPanchayatCode.add(responsePanArrayList.get(a).getPanCode());
                }
            }

            alertDialogItems1 = new String[cmaPanchayatCode.size()];
            for (int b = 0; b < cmaPanchayatCode.size(); b++) {
                alertDialogItems1[b] = cmaPanchayatName.get(b);
                //selectedtruefalse[b] = false;
            }

            alertdialogbuilder1.setMultiChoiceItems(alertDialogItems1, selectedtruefalse, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                    if (isChecked) {
                        selectedtruefalse[which] = true;
                    }
                }
            });

            alertdialogbuilder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int a = 0;
                    pan_name = "";
                    pan_code = "";
                    while (a < selectedtruefalse.length) {
                        boolean value = selectedtruefalse[a];
                        if (value) {
                            String name1 = cmaPanchayatName.get(a);
                            String panCode = cmaPanchayatCode.get(a);
                            if (!TextUtils.isEmpty(pan_name)) {
                                pan_name = pan_name + "," + name1;
                                pan_code = pan_code + "," + panCode;
                            } else {
                                pan_name = name1;
                                pan_code = panCode;
                            }
                        }
                        a++;
                    }
                    tvPanchayat.setText(pan_name);
                    //Toast.makeText(getContext(), pan_code, Toast.LENGTH_SHORT).show();
                }
            });

           /* alertdialogbuilder1.setSingleChoiceItems(alertDialogItems1, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Toast.makeText(getContext(), alertDialogItems[which], Toast.LENGTH_SHORT).show();
                    pan_name = alertDialogItems1[which];
                    pan_code = cmaPanchayatCode.get(which);
                }
            });*/
            alertdialogbuilder1.setCancelable(false);
            alertdialogbuilder1.setTitle("Select Panchayat");

            /*alertdialogbuilder1.setPositiveButton("Choose", new DialogInterface.OnClickListener() {
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
            });*/

            alertdialogbuilder1.setNeutralButton("Discard", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    pan_name = "";
                    pan_code = "";
                }
            });

            android.app.AlertDialog dialog = alertdialogbuilder1.create();
            dialog.show();
        } else {
            //Toast.makeText(getActivity(), "Select Block", Toast.LENGTH_SHORT).show();
            showMessage("Select Block");
        }
    }

    private void showMessage(String msg) {
        Snackbar.make(getActivity().findViewById(android.R.id.content),
                "⚠ " + msg, Snackbar.LENGTH_LONG).show();
    }

    private void saveRegisterResponse() {
        progressdialog.dismiss();
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage("Facilitator Created Successfully");
        builder1.setCancelable(false);
        builder1.setPositiveButton("DONE",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        name.setText("");
                        email.setText("");
                        mobile.setText("");
                        uname.setText("");
                        pass.setText("");
                        // restart-activity again
                        getActivity().recreate();
                    }
                });
        AlertDialog alert11 = builder1.create();
        if (!getActivity().isFinishing()) {
            alert11.show();
        }
    }
}
