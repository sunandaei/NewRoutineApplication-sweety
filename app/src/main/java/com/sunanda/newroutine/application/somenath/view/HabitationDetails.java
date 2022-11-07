package com.sunanda.newroutine.application.somenath.view;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.somenath.myadapter.HabitationC_UN_Adapter;
import com.sunanda.newroutine.application.somenath.pojo.HabCovUncovPojo;
import com.sunanda.newroutine.application.somenath.pojo.HabitationPojo;
import com.sunanda.newroutine.application.somenath.pojo.SourceForLaboratoryPOJO;
import com.sunanda.newroutine.application.util.CGlobal;
import com.sunanda.newroutine.application.util.Constants;
import com.sunanda.newroutine.application.util.LoadingDialog;
import com.sunanda.newroutine.application.util.PostInterface;
import com.sunanda.newroutine.application.util.SessionManager;

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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HabitationDetails extends AppCompatActivity {

    RecyclerView recyclerView;
    LoadingDialog loadingDialog;
    TextView title, tmpTxt;
    ImageView info;
    EditText editTextSearch;
    ArrayList<HabCovUncovPojo> habAllArrayList, habUnCoverArrayList, habCoverArrayList;
    HabitationC_UN_Adapter habAdapter_c_un;
    SessionManager sessionManager;
    String vill_code;

    ArrayList<String> cmaVillageName = new ArrayList<>();
    ArrayList<String> cmaVillageCode = new ArrayList<>();
    ArrayList<SourceForLaboratoryPOJO> sourceForLaboratoryPOJOArrayList = new ArrayList<>();

    ArrayList<String> cmaHabitationId = new ArrayList<>();
    ArrayList<String> cmaHabitationName = new ArrayList<>();
    ArrayList<String> cmaHabitationCode = new ArrayList<>();

    ArrayList<HabitationPojo> habitationPojoArrayList = new ArrayList<>();
    ArrayList<HabitationPojo> habitationPojoArrayListIsNotTestHab = new ArrayList<>();
    ArrayList<HabitationPojo> habitationPojoArrayListAlredyAssign = new ArrayList<>();
    ArrayList<HabitationPojo> habitationPojoArrayListIsPreviousFinancialYearsSource = new ArrayList<>();
    ArrayList<HabitationPojo> habitationPojoArrayListIsCurrentFinancialYearsSource = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habitation_details);

        this.recyclerView = (RecyclerView) findViewById(R.id.statusHab);

        loadingDialog = new LoadingDialog(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        sessionManager = new SessionManager(this);
        info = findViewById(R.id.info);

        title = findViewById(R.id.title);
        title.setText("Village Name : " + getIntent().getStringExtra("VILLNAME"));
        tmpTxt = findViewById(R.id.tmpTxt);
        editTextSearch = findViewById(R.id.editTextSearch);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        final Drawable upArrow = getResources().getDrawable(R.drawable.leftarrow);
        upArrow.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.leftarrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /*if (getIntent().getStringExtra("TYPE").equalsIgnoreCase("0")) {
            tmpTxt.setText("UNTOUCHED HABITATION DETAILS");
            tmpTxt.setTextColor(Color.parseColor("#FF0000"));
        } else {
            tmpTxt.setText("COVERAGE HABITATION DETAILS");
            tmpTxt.setTextColor(Color.parseColor("#228B22"));
        }*/

        habAllArrayList = new ArrayList<>();
        habUnCoverArrayList = new ArrayList<>();
        habCoverArrayList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(getIntent().getStringExtra("HABITATION"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                HabCovUncovPojo habCovUncovPojo = new HabCovUncovPojo();
                habCovUncovPojo.setId(jsonObject.getString("_id"));
                habCovUncovPojo.setVillCode(jsonObject.getString("vill_code"));
                habCovUncovPojo.setVillCodeUnique(jsonObject.getString("vill_code_unique"));
                habCovUncovPojo.setHabitationCode(jsonObject.getString("habitation_code"));
                habCovUncovPojo.setHabitationName(jsonObject.getString("habitation_name"));
                habCovUncovPojo.setTouched(jsonObject.getString("touched"));
                habCovUncovPojo.setNoSourceTestedInFY(jsonObject.getString("no_source_tested_in_FY"));
                habCovUncovPojo.setLastTouchedDate(jsonObject.getString("last_touched_date"));
                habCovUncovPojo.setTouchedPrevious(jsonObject.getString("touchedPrevious"));
                habCovUncovPojo.setNoSourceTestedInFY(jsonObject.getString("no_source_tested_in_FY"));
                habCovUncovPojo.setLastPreviousTouchedDate(jsonObject.getString("last_previous_touched_date"));

                vill_code = jsonObject.getString("vill_code");

                if (TextUtils.isEmpty(habCovUncovPojo.getTouched()) || habCovUncovPojo.getTouched().equalsIgnoreCase("0"))
                    habUnCoverArrayList.add(habCovUncovPojo);
                else
                    habCoverArrayList.add(habCovUncovPojo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        habAllArrayList.clear();
        habAllArrayList.addAll(habUnCoverArrayList);
        habAllArrayList.addAll(habCoverArrayList);

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

        getHabitationDetails();

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMyDialog();
            }
        });
    }

    private void showMyDialog() {
        final Dialog dialog = new Dialog(this, R.style.AppTheme_NoActionBar);

        // Setting dialogview
        Window window = dialog.getWindow();
        window.setGravity(Gravity.TOP | Gravity.CENTER);

        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.mydialog2);
        dialog.show();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
    }

    private void filter(String text) {
        try {
            //new array list that will hold the filtered data
            ArrayList<HabCovUncovPojo> filterdNames = new ArrayList<>();
            ArrayList<String> names = new ArrayList<>();
            for (int i = 0; i < habAllArrayList.size(); i++) {
                names.add(habAllArrayList.get(i).getHabitationName());
            }

            //looping through existing elements
            for (String s : names) {
                //if the existing elements contains the search input
                if (s.toLowerCase().contains(text.toLowerCase())) {
                    //adding the element to filtered list
                    filterdNames.add(habAllArrayList.get(names.indexOf(s)));
                }
            }
            //calling a method of the adapter class and passing the filtered list
            habAdapter_c_un.updateList(filterdNames);
        } catch (Exception ignored) {
        }
    }

    private void getHabitationDetails() {

        final ProgressDialog progressdialog;
        progressdialog = new ProgressDialog(this);
        progressdialog.setMessage("Please Wait....");
        progressdialog.setCancelable(false);

        boolean isConnected = CGlobal.getInstance().isConnected(this);
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
                    .baseUrl(PostInterface.JSONURL)
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            PostInterface api = retrofit.create(PostInterface.class);

            String sLabId = CGlobal.getInstance().getPersistentPreference(this)
                    .getString(Constants.PREFS_USER_LAB_ID, "");

            Call<List<SourceForLaboratoryPOJO>> call = api.getSourceForLaboratoryNew(sLabId, sessionManager.getKeyDistCode(),
                    sessionManager.getKeyBlockCode(), sessionManager.getKeyPanCode(), vill_code, "Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3");

            call.enqueue(new Callback<List<SourceForLaboratoryPOJO>>() {
                @Override
                public void onResponse(Call<List<SourceForLaboratoryPOJO>> call, Response<List<SourceForLaboratoryPOJO>> response) {
                    //Log.i("Responsestring!!", response.body().size() + "");
                    //loadingDialog.hideDialog();
                    if (response.body() != null) {

                        sourceForLaboratoryPOJOArrayList.addAll(response.body());

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
                                        habitationPojo.setAlredyAssign(sourceForLaboratoryPOJOArrayList.get(i).getAlredyAssign());

                                        habitationPojo.setBlock_code(sourceForLaboratoryPOJOArrayList.get(i).getBlock_code());
                                        habitationPojo.setVillage_Code(sourceForLaboratoryPOJOArrayList.get(i).getVillage_Code());
                                        habitationPojo.setHabitation_Code(sourceForLaboratoryPOJOArrayList.get(i).getHabitation_Code());
                                        habitationPojo.setDist_code(sourceForLaboratoryPOJOArrayList.get(i).getDist_code());
                                        habitationPojo.setPan_code(sourceForLaboratoryPOJOArrayList.get(i).getPan_code());

                                            /*if (sourceForLaboratoryPOJOArrayList.get(i).getIsNotTestHab().equalsIgnoreCase("yes")) {
                                                habitationPojoArrayListIsNotTestHab.add(habitationPojo);
                                            } else if (sourceForLaboratoryPOJOArrayList.get(i).getIsPreviousFinancialYearsSource().
                                                    equalsIgnoreCase("yes") &&
                                                    sourceForLaboratoryPOJOArrayList.get(i).getAlredyAssign().equalsIgnoreCase("0")) {
                                                habitationPojoArrayListIsPreviousFinancialYearsSource.add(habitationPojo);
                                            } else if (!sourceForLaboratoryPOJOArrayList.get(i).getAlredyAssign().equalsIgnoreCase("0"))
                                                habitationPojoArrayListAlredyAssign.add(habitationPojo);
                                            else {
                                                habitationPojoArrayListIsCurrentFinancialYearsSource.add(habitationPojo);
                                            }*/
                                        if (sourceForLaboratoryPOJOArrayList.get(i).getIsNotTestHab().equalsIgnoreCase("yes")) {
                                            habitationPojoArrayListIsNotTestHab.add(habitationPojo);
                                        } else if (sourceForLaboratoryPOJOArrayList.get(i).getIsCurrentFinancialYearsSource().
                                                equalsIgnoreCase("yes") &&
                                                sourceForLaboratoryPOJOArrayList.get(i).getAlredyAssign().equalsIgnoreCase("0")) {
                                            habitationPojoArrayListIsCurrentFinancialYearsSource.add(habitationPojo);
                                        } else if (!sourceForLaboratoryPOJOArrayList.get(i).getAlredyAssign().equalsIgnoreCase("0"))
                                            habitationPojoArrayListAlredyAssign.add(habitationPojo);
                                        else {
                                            habitationPojoArrayListIsPreviousFinancialYearsSource.add(habitationPojo);
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
                                    habitationPojo.setAlredyAssign(sourceForLaboratoryPOJOArrayList.get(i).getAlredyAssign());

                                    habitationPojo.setBlock_code(sourceForLaboratoryPOJOArrayList.get(i).getBlock_code());
                                    habitationPojo.setVillage_Code(sourceForLaboratoryPOJOArrayList.get(i).getVillage_Code());
                                    habitationPojo.setHabitation_Code(sourceForLaboratoryPOJOArrayList.get(i).getHabitation_Code());
                                    habitationPojo.setDist_code(sourceForLaboratoryPOJOArrayList.get(i).getDist_code());
                                    habitationPojo.setPan_code(sourceForLaboratoryPOJOArrayList.get(i).getPan_code());

                                        /*if (sourceForLaboratoryPOJOArrayList.get(i).getIsNotTestHab().equalsIgnoreCase("yes")) {
                                            habitationPojoArrayListIsNotTestHab.add(habitationPojo);
                                        } if (sourceForLaboratoryPOJOArrayList.get(i).getIsPreviousFinancialYearsSource().
                                                equalsIgnoreCase("yes") &&
                                                sourceForLaboratoryPOJOArrayList.get(i).getAlredyAssign().equalsIgnoreCase("0")) {
                                            habitationPojoArrayListIsPreviousFinancialYearsSource.add(habitationPojo);
                                        } else if (!sourceForLaboratoryPOJOArrayList.get(i).getAlredyAssign().equalsIgnoreCase("0"))
                                            habitationPojoArrayListAlredyAssign.add(habitationPojo);
                                        else {
                                            habitationPojoArrayListIsCurrentFinancialYearsSource.add(habitationPojo);
                                        }*/
                                    if (sourceForLaboratoryPOJOArrayList.get(i).getIsNotTestHab().equalsIgnoreCase("yes")) {
                                        habitationPojoArrayListIsNotTestHab.add(habitationPojo);
                                    } else if (sourceForLaboratoryPOJOArrayList.get(i).getIsCurrentFinancialYearsSource().
                                            equalsIgnoreCase("yes") &&
                                            sourceForLaboratoryPOJOArrayList.get(i).getAlredyAssign().equalsIgnoreCase("0")) {
                                        habitationPojoArrayListIsCurrentFinancialYearsSource.add(habitationPojo);
                                    } else if (!sourceForLaboratoryPOJOArrayList.get(i).getAlredyAssign().equalsIgnoreCase("0"))
                                        habitationPojoArrayListAlredyAssign.add(habitationPojo);
                                    else {
                                        habitationPojoArrayListIsPreviousFinancialYearsSource.add(habitationPojo);
                                    }
                                    //habitationPojoArrayList.add(habitationPojo);
                                }
                            }
                        }
                        // habitationPojoArrayList -> By KAJAL
                        habitationPojoArrayList = new ArrayList<>();
                        habitationPojoArrayList.addAll(habitationPojoArrayListIsNotTestHab);
                        habitationPojoArrayList.addAll(habitationPojoArrayListIsPreviousFinancialYearsSource);
                        habitationPojoArrayList.addAll(habitationPojoArrayListIsCurrentFinancialYearsSource);
                        habitationPojoArrayList.addAll(habitationPojoArrayListAlredyAssign);

                        int count = 0;
                        for (int i = 0; i < habAllArrayList.size(); i++) {
                            for (int j = 0; j < habitationPojoArrayList.size(); j++) {
                                if (habAllArrayList.get(i).getHabitationCode().equalsIgnoreCase(habitationPojoArrayList.get(j).getHabitation_Code())
                                        && habAllArrayList.get(i).getVillCode().equalsIgnoreCase(habitationPojoArrayList.get(j).getVillage_Code())
                                        && Integer.parseInt(habitationPojoArrayList.get(j).getAlredyAssign()) > 0) {
                                    habAllArrayList.get(i).setAlredyAssign(true);
                                    /*Log.d("CNT", (++count) + "#" + habitationPojoArrayList.get(i).getVillage_Code() +
                                            "#" + habitationPojoArrayList.get(i).getHabitation_Code());*/
                                }
                            }
                        }

                        habAdapter_c_un = new HabitationC_UN_Adapter(HabitationDetails.this, habAllArrayList);
                        recyclerView.setAdapter(habAdapter_c_un);

                    } else {
                        //Log.i("onEmptyResponse", "Returned empty response");
                        Toast.makeText(HabitationDetails.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                    progressdialog.dismiss();
                }

                @Override
                public void onFailure(Call<List<SourceForLaboratoryPOJO>> call, Throwable t) {
                    //loadingDialog.hideDialog();
                    progressdialog.dismiss();
                    //Log.i("onEmptyResponse", "Returned empty response");
                }
            });
        } else {
            new androidx.appcompat.app.AlertDialog.Builder(HabitationDetails.this)
                    .setMessage("Please check your Internet connection. Please try again")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_left_out);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            overridePendingTransition(R.anim.trans_right_in, R.anim.trans_left_out);
            finish();
        }
        return (super.onOptionsItemSelected(menuItem));
    }
}
