package com.sunanda.newroutine.application.adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.database.DatabaseHandler;
import com.sunanda.newroutine.application.modal.CommonModel;
import com.sunanda.newroutine.application.newactivity.Head_PWSS_Source_Activity;
import com.sunanda.newroutine.application.ui.DashBoard_Facilitator_Activity;
import com.sunanda.newroutine.application.util.CGlobal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class Head_PWSS_Source_Adapter extends RecyclerView.Adapter<Head_PWSS_Source_Adapter.MyView_Holder> {

    private String type;
    private ArrayList<CommonModel> commonModelArrayList, modelArrayList, sourceCountArrayTotal, sourceCountArrayRemaining, sourceCountArrayComplete;
    private Context context;
    ProgressDialog progressdialog;
    String sPWSS_Status = "";

    public Head_PWSS_Source_Adapter(ArrayList<CommonModel> commonModelArray,
                                    Context context, String type, String pWSS_Status) {
        this.commonModelArrayList = commonModelArray;
        this.context = context;
        this.type = type;
        this.sPWSS_Status = pWSS_Status;
    }

    @Override
    public Head_PWSS_Source_Adapter.MyView_Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.head_pwss_source_adapter, parent, false);
        Head_PWSS_Source_Adapter.MyView_Holder holder = new Head_PWSS_Source_Adapter.MyView_Holder(v);
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final Head_PWSS_Source_Adapter.MyView_Holder holder, final int position) {

        holder.tvScheme.setText(commonModelArrayList.get(position).getScheme());

        holder.rlExpanse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commonModelArrayList.get(position).isFlag()) {
                    commonModelArrayList.get(position).setFlag(false);
                    holder.llExpanse.setVisibility(View.GONE);
                } else {
                    commonModelArrayList.get(position).setFlag(true);
                    holder.llExpanse.setVisibility(View.VISIBLE);
                    showSourceList(context, holder.rvSourceList, type, commonModelArrayList.get(position).getBlockcode(),
                            commonModelArrayList.get(position).getPancode(), commonModelArrayList.get(position).getVillagecode(),
                            commonModelArrayList.get(position).getHabecode(), holder.search, sPWSS_Status,
                            commonModelArrayList.get(position).getScheme_code());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return commonModelArrayList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class MyView_Holder extends RecyclerView.ViewHolder {

        private TextView tvScheme;
        RelativeLayout rlExpanse;
        RecyclerView rvSourceList;
        SearchView search;
        LinearLayout llExpanse;

        public MyView_Holder(View itemView) {
            super(itemView);
            tvScheme = itemView.findViewById(R.id.tvScheme);
            rlExpanse = itemView.findViewById(R.id.rlExpanse);
            rvSourceList = itemView.findViewById(R.id.rvSourceList);
            rlExpanse = itemView.findViewById(R.id.rlExpanse);
            search = itemView.findViewById(R.id.search);
            llExpanse = itemView.findViewById(R.id.llExpanse);
        }
    }

    Facilitator_SourceData_Adapter sourceDataAdapter;

    public void showSourceList(Context context, RecyclerView rvSourceList, String type, String blockCode,
                               String panCode, String villageCode, String habitationCode,
                               SearchView search, String sPWSS_Status, String schemeCode) {
        DataLoadAsync dataLoadAsync = new DataLoadAsync(context, rvSourceList, type, blockCode,
                panCode, villageCode, habitationCode, search, sPWSS_Status, schemeCode);
        dataLoadAsync.execute();

    }

    private final class DataLoadAsync extends AsyncTask<Void, Integer, String> {
        Context context;
        RecyclerView rvSourceList;
        String type, blockCode, panCode, villageCode, habitationCode, sPWSS_Status, sSchemeCode;
        SearchView search;

        public DataLoadAsync(Context context1, RecyclerView rvSourceList1, String type1,
                             String blockCode1, String panCode1, String villageCode1,
                             String habitationCode1, SearchView search1, String pWSS_Status,
                             String schemeCode) {
            context = context1;
            rvSourceList = rvSourceList1;
            type = type1;
            blockCode = blockCode1;
            panCode = panCode1;
            villageCode = villageCode1;
            habitationCode = habitationCode1;
            search = search1;
            sPWSS_Status = pWSS_Status;
            sSchemeCode = schemeCode;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                progressdialog = new ProgressDialog(context);
                progressdialog.setMessage("Please Wait....");
                progressdialog.setCancelable(false);
                progressdialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        protected String doInBackground(Void... arg0) {
            modelArrayList = new ArrayList<>();
            DatabaseHandler databaseHandler = new DatabaseHandler(context);
            modelArrayList = databaseHandler.getSourceForHeadPWSS(type, blockCode, panCode,
                    villageCode, sPWSS_Status, sSchemeCode);
            Collections.sort(modelArrayList, new DistanceSorter());
            return null;
        }

        protected void onProgressUpdate(Integer... a) {
            super.onProgressUpdate(a);
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            search.setActivated(true);
            search.setQueryHint("Type your Location Description");
            search.onActionViewExpanded();
            search.setIconified(false);
            search.clearFocus();

            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {

                    sourceDataAdapter.getFilter().filter(newText);
                    return false;
                }
            });

            sourceDataAdapter = new Facilitator_SourceData_Adapter(context, modelArrayList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            rvSourceList.setLayoutManager(linearLayoutManager);
            rvSourceList.setItemAnimator(new DefaultItemAnimator());
            rvSourceList.setAdapter(sourceDataAdapter);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressdialog.dismiss();
                }
            }, 10000);
        }
    }

    public class DistanceSorter implements Comparator<CommonModel> {
        @Override
        public int compare(CommonModel o1, CommonModel o2) {
            return String.valueOf(o1.getDistance()).compareToIgnoreCase(String.valueOf(o2.getDistance()));
        }
    }
}