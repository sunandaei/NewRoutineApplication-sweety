package com.sunanda.newroutine.application.somenath.myadapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.modal.CommonModel;
import com.sunanda.newroutine.application.somenath.pojo.AssignedArchiveTaskPojo;
import com.sunanda.newroutine.application.somenath.pojo.HabCovUncovPojo;
import com.sunanda.newroutine.application.somenath.view.CreateFacilitator_Activity;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class HabitationC_UN_Adapter extends RecyclerView.Adapter<HabitationC_UN_Adapter.MultiViewHolder> {

    private Context context;
    private ArrayList<HabCovUncovPojo> habCovUncovPojoArrayList;
    private ProgressDialog progressdialog;
    private CommonModel commonModel;
    private ArrayList<AssignedArchiveTaskPojo> allTaskPojoArrayList;
    private ArrayList<AssignedArchiveTaskPojo> currentTaskPojoArrayList;
    private String current_task_id = "";
    private SessionManager sessionManager;
    private String habId = "", villageCode = "";
    private ArrayList<String> FCName;
    private String facilitatorName;

    public HabitationC_UN_Adapter(Context context, ArrayList<HabCovUncovPojo> labWorkStatusArrayList) {
        this.context = context;
        this.habCovUncovPojoArrayList = labWorkStatusArrayList;
        progressdialog = new ProgressDialog(context);
        sessionManager = new SessionManager(context);
    }

    @NonNull
    @Override
    public MultiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_covered_uncovered_hab, viewGroup, false);
        return new MultiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MultiViewHolder multiViewHolder, int position) {
        multiViewHolder.bind(habCovUncovPojoArrayList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return habCovUncovPojoArrayList.size();
    }

    class MultiViewHolder extends RecyclerView.ViewHolder {

        private TextView name, no;
        private TextView last_previous_touched_date, last_touched_date, no_source_tested_in_FY;
        private TextView no_source_tested_in_previous_FY, touched, touchedPrevious;
        private LinearLayout newLayout;
        private ImageView less, more;
        private Button assign;

        MultiViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            no = itemView.findViewById(R.id.done_dialog);
            last_previous_touched_date = itemView.findViewById(R.id.last_previous_touched_date);
            last_touched_date = itemView.findViewById(R.id.last_touched_date);
            no_source_tested_in_FY = itemView.findViewById(R.id.no_source_tested_in_FY);
            no_source_tested_in_previous_FY = itemView.findViewById(R.id.no_source_tested_in_previous_FY);
            touched = itemView.findViewById(R.id.touched);
            touchedPrevious = itemView.findViewById(R.id.touchedPrevious);
            newLayout = itemView.findViewById(R.id.newLayout);
            less = itemView.findViewById(R.id.less);
            more = itemView.findViewById(R.id.more);
            assign = itemView.findViewById(R.id.assign);
        }

        @SuppressLint("SetTextI18n")
        void bind(final HabCovUncovPojo habCovUncovPojo, final int position) {

            if (habCovUncovPojoArrayList.get(position).isExpanded()) {
                newLayout.setVisibility(View.VISIBLE);
                more.setVisibility(View.GONE);
                less.setVisibility(View.VISIBLE);
            } else {
                newLayout.setVisibility(View.GONE);
                more.setVisibility(View.VISIBLE);
                less.setVisibility(View.GONE);
            }

            no.setText(String.valueOf(position + 1));
            name.setText("Habitation Name : " + habCovUncovPojo.getHabitationName());
            last_previous_touched_date.setText("Last Previous Touched Date : " +
                    (habCovUncovPojo.getLastPreviousTouchedDate().equalsIgnoreCase("01-01-1970") ?
                            "NA" : habCovUncovPojo.getLastPreviousTouchedDate()));
            last_touched_date.setText("Last Touched Date : " +
                    (habCovUncovPojo.getLastTouchedDate().equalsIgnoreCase("01-01-1970") ?
                            "NA" : habCovUncovPojo.getLastTouchedDate()));
            no_source_tested_in_FY.setText("No. of Source Tested in CFY : " +
                    (TextUtils.isEmpty(habCovUncovPojo.getNoSourceTestedInFY()) ? "0" : habCovUncovPojo.getNoSourceTestedInFY()));
            no_source_tested_in_previous_FY.setText("No. of Source Tested in PFY : " +
                    (TextUtils.isEmpty(habCovUncovPojo.getNoSourceTestedInPreviousFY()) ? "0" : habCovUncovPojo.getNoSourceTestedInPreviousFY()));

            /*touched.setText("Touched Current : " + (TextUtils.isEmpty(habCovUncovPojo.getTouched()) ?
                    "0" : habCovUncovPojo.getTouched()));
            touchedPrevious.setText("Touched Previous : " + (habCovUncovPojo.getTouchedPrevious().equalsIgnoreCase("01-01-1970") ?
                    "NA" : habCovUncovPojo.getTouchedPrevious()));*/

            touched.setVisibility(View.GONE);
            touchedPrevious.setVisibility(View.GONE);
            if (habCovUncovPojo.isAlredyAssign()) {
                int sdk = android.os.Build.VERSION.SDK_INT;
                assign.setVisibility(View.GONE);
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    itemView.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.rectangle4));
                } else {
                    itemView.setBackground(ContextCompat.getDrawable(context, R.drawable.rectangle4));
                }
            } else if (TextUtils.isEmpty(habCovUncovPojo.getTouched()) || habCovUncovPojo.getTouched().equalsIgnoreCase("0")) {
                int sdk = android.os.Build.VERSION.SDK_INT;
                assign.setVisibility(View.VISIBLE);
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    itemView.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.rectangle6));
                } else {
                    itemView.setBackground(ContextCompat.getDrawable(context, R.drawable.rectangle6));
                }
            } else {
                int sdk = android.os.Build.VERSION.SDK_INT;
                assign.setVisibility(View.GONE);
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    itemView.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.rectangle5));
                } else {
                    itemView.setBackground(ContextCompat.getDrawable(context, R.drawable.rectangle5));
                }
            }

            more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newLayout.setVisibility(View.VISIBLE);
                    more.setVisibility(View.GONE);
                    less.setVisibility(View.VISIBLE);
                    habCovUncovPojoArrayList.get(position).setExpanded(true);
                }
            });
            less.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newLayout.setVisibility(View.GONE);
                    more.setVisibility(View.VISIBLE);
                    less.setVisibility(View.GONE);
                    habCovUncovPojoArrayList.get(position).setExpanded(false);
                }
            });

            assign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    habId = habCovUncovPojo.getHabitationCode();
                    villageCode = habCovUncovPojo.getVillCode();
                    getFacilitatorList();
                    /*Intent intent = new Intent(context, HabitationDetails.class);
                    intent.putExtra("HABITATION", habitation);
                    intent.putExtra("VILLNAME", villageCovUnCovPojo.getVillName());
                    ((Activity) context).overridePendingTransition(R.anim.trans_left_in, R.anim.trans_right_out);
                    context.startActivity(intent);*/
                }
            });
        }
    }

    public void updateList(ArrayList<HabCovUncovPojo> list){
        habCovUncovPojoArrayList = list;
        notifyDataSetChanged();
    }

    private void getFacilitatorList() {

        progressdialog.setMessage("Please Wait....");
        progressdialog.setCancelable(false);

        boolean isConnected = CGlobal.getInstance().isConnected(context);
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
                            okhttp3.Request.Builder ongoing = chain.request().newBuilder();
                            return chain.proceed(ongoing.build());
                        }
                    })
                    .build();

            String sLabCode = CGlobal.getInstance().getPersistentPreference(context)
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
                    //Log.i("Responsestring", response.body());
                    progressdialog.dismiss();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            getFacilitatorListResponse(response.body());
                        } else {
                            //Log.i("onEmptyResponse", "Returned empty response");
                            Toast.makeText(context, "Unable to get data", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    //Log.i("onEmptyResponse", "Returned empty response");
                    progressdialog.dismiss();
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            new AlertDialog.Builder(context)
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

    private void getFacilitatorListResponse(String response) {

        FCName = new ArrayList<>();

        SessionManager sessionManager = new SessionManager(context);
        progressdialog.dismiss();
        try {
            JSONArray aFacilitator = new JSONArray(response);
            boolean isFound = false;
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

                commonModel = new CommonModel();
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

                String[] newPan_Codes = Pan_Codes.split(",");
                // Convert String Array to List
                List<String> list = Arrays.asList(newPan_Codes);
                if (oFacilitator.getString("IsActive").equalsIgnoreCase("true")) {
                    if (sessionManager.getKeyDistCode().equalsIgnoreCase(Dist_Code) &&
                            list.contains(sessionManager.getKeyPanCode())
                            && sessionManager.getKeyBlockCode().equalsIgnoreCase(Block_Code)) {
                        isFound = true;
                        //Toast.makeText(context, name, Toast.LENGTH_SHORT).show();
                        facilitatorName = name;
                        break;
                        //FCName.add(FCID);
                    }
                }
            }
            if (isFound) {
                getFCRecords();
            } else {
                new AlertDialog.Builder(context)
                        //.setTitle("No")
                        .setMessage("No Facilitator found in this Panchayat. Please Create new Facilitator to assign the task. Do you want to create new Facilitator?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                ((Activity) context).startActivity(new Intent(context,
                                        CreateFacilitator_Activity.class));
                                ((Activity) context).overridePendingTransition(R.anim.trans_right_in, R.anim.trans_left_out);
                            }
                        })
                        //.setNegativeButton(android.R.string.no, null)
                        .setNeutralButton(android.R.string.no, null)
                        .setCancelable(false)
                        //.setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getFCRecords() {

        allTaskPojoArrayList = new ArrayList<>();
        currentTaskPojoArrayList = new ArrayList<>();

        final LoadingDialog loadingDialog = new LoadingDialog(context);

        boolean isConnected = CGlobal.getInstance().isConnected(context);
        if (isConnected) {

            loadingDialog.showDialog();

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
                    //.addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            PostInterface api = retrofit.create(PostInterface.class);

            final Map<String, String> mapdata = new HashMap<>();
            mapdata.put("FCID", commonModel.getFCID());
            mapdata.put("AppKey", "Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3");

            Call<List<AssignedArchiveTaskPojo>> call = api.GetAssignHabitationListFCWiseForLab(mapdata);

            call.enqueue(new Callback<List<AssignedArchiveTaskPojo>>() {
                @Override
                public void onResponse(Call<List<AssignedArchiveTaskPojo>> call, Response<List<AssignedArchiveTaskPojo>> response) {
                    //Log.i("ResponsestringAS!!", response.body().size() + "");
                    loadingDialog.hideDialog();

                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            allTaskPojoArrayList.addAll(response.body());

                            for (int i = 0; i < allTaskPojoArrayList.size(); i++) {

                                if (!TextUtils.isEmpty(allTaskPojoArrayList.get(i).getCreatedDate())) {
                                    currentTaskPojoArrayList.add(allTaskPojoArrayList.get(i));
                                }
                            }
                            /*if (currentTaskPojoArrayList.size() == 0) {
                                String current_task_id = currentTaskPojoArrayList.get(0).getTask_Id();
                                Intent intent = new Intent(context, AddnewTaskToFC.class);
                                intent.putExtra("FCNAME", commonModel.getName());
                                intent.putExtra("FCID", commonModel.getFCID());
                                intent.putExtra("ALLDATA", commonModel);
                                intent.putExtra("TASKID", current_task_id);
                                ((Activity) context).overridePendingTransition(R.anim.trans_left_in, R.anim.trans_right_out);
                                ((Activity) context).startActivity(intent);
                            } else {
                                new AlertDialog.Builder(context)
                                        .setTitle("Add New Task")
                                        .setMessage("Already an active assigned task to Facilitator. You can't assign new Task.")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        })
                                        //.setNegativeButton(android.R.string.no, null)
                                        .setCancelable(false)
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
                            }*/
                            if (currentTaskPojoArrayList.size() == 0) {
                                submitData();
                            } else {
                                current_task_id = currentTaskPojoArrayList.get(0).getTask_Id();
                                new AlertDialog.Builder(context)
                                        .setTitle("Add New Task")
                                        .setMessage("Already an active assigned task to Facilitator `" + facilitatorName +
                                                "`. Do you want to assign another?")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                submitData();
                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, null)
                                        .setCancelable(false)
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
                            }
                        } else {
                            Log.i("onEmptyResponse", "Returned empty response");
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<AssignedArchiveTaskPojo>> call, Throwable t) {
                    loadingDialog.hideDialog();
                    Log.i("onEmptyResponse", "Returned empty response");
                }
            });
        } else {
            new androidx.appcompat.app.AlertDialog.Builder(context)
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

    private void submitData() {
        new AlertDialog.Builder(context)
                .setTitle("Confirm to Submit New Task")
                .setMessage("Do you assigned task to Facilitator `" + facilitatorName + "`?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        confirmToSubmit();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setCancelable(false)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void confirmToSubmit() {

        progressdialog = new ProgressDialog(context);

        boolean isConnected = CGlobal.getInstance().isConnected(context);
        if (isConnected) {

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

            progressdialog.setMessage("Please Wait....");
            progressdialog.setCancelable(false);
            progressdialog.show();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(PostInterface.JSONURL)
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            PostInterface api = retrofit.create(PostInterface.class);
            String sLabId = CGlobal.getInstance().getPersistentPreference(context)
                    .getString(Constants.PREFS_USER_LAB_ID, "");

            long unixTime = System.currentTimeMillis() / 1000L;

            Call<ResponseBody> call;
            if (TextUtils.isEmpty(current_task_id))
                call = api.AssignTaskToFacilitator(habId, sessionManager.getKeyDistCode(), sessionManager.getKeyBlockCode(),
                        sessionManager.getKeyPanCode(), villageCode, sLabId, commonModel.getFCID()/*, "Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3"*/);
            else
                call = api.AddMoreHabitationInSameTask(habId, sessionManager.getKeyDistCode(),
                        sessionManager.getKeyBlockCode(), sessionManager.getKeyPanCode(), villageCode, sLabId,
                        commonModel.getFCID(), current_task_id, "Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3");

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    progressdialog.dismiss();
                    if (response.body() != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string().trim());
                            if (jsonObject.getBoolean("response")) {
                                new androidx.appcompat.app.AlertDialog.Builder(context)
                                        .setMessage("Task assigned successfully")
                                        .setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                //easyFlipView.flipTheView();
                                                ((Activity) context).overridePendingTransition(R.anim.trans_right_in, R.anim.trans_left_out);
                                                ((Activity) context).finish();
                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_info)
                                        .setCancelable(false)
                                        .show();
                            } else {
                                showMessage("Already assigned");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        showMessage("Unable to add task.");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    progressdialog.dismiss();
                    //Log.i("onEmptyResponse", "Returned empty response");
                    showMessage("Something went wrong.");
                }
            });
        } else {
            new androidx.appcompat.app.AlertDialog.Builder(context)
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

    private void showMessage(String msg) {
        Snackbar.make(((Activity) context).findViewById(android.R.id.content),
                "⚠ " + msg, Snackbar.LENGTH_LONG).show();
    }
}

