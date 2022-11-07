package com.sunanda.newroutine.application.adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.database.DatabaseHandler;
import com.sunanda.newroutine.application.modal.CommonModel;
import com.sunanda.newroutine.application.ui.DashBoard_Facilitator_Activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class AssignedHabitationList_Adapter extends RecyclerView.Adapter<AssignedHabitationList_Adapter.MyView_Holder> {

    private String type;
    private ArrayList<CommonModel> commonModelArrayList, modelArrayList, sourceCountArrayTotal, sourceCountArrayRemaining, sourceCountArrayComplete;
    private Context context;
    double lat, lng;
    ProgressDialog progressdialog;

    public AssignedHabitationList_Adapter(ArrayList<CommonModel> commonModelArray, Context context, String type, double latitude, double longitude) {
        this.commonModelArrayList = commonModelArray;
        this.context = context;
        this.type = type;
        this.lat = latitude;
        this.lng = longitude;
    }

    @Override
    public MyView_Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.assigne_habitation_list_item, parent, false);
        MyView_Holder holder = new MyView_Holder(v);
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyView_Holder holder, final int position) {

        if (position != 0) {
            if (commonModelArrayList.get(position - 1).getCreatedDate().split("T")[0].
                    equalsIgnoreCase(commonModelArrayList.get(position).getCreatedDate().split("T")[0])) {
                holder.tvAssignedDate.setVisibility(View.GONE);
                holder.tvAssignedDateDifferent.setVisibility(View.GONE);
            } else {
                holder.tvAssignedDate.setText("\uD83D\uDCC5 Assigned Date : \n" + commonModelArrayList.get(position).getCreatedDate().split("T")[0]);
                holder.tvAssignedDateDifferent.setText("Days Passed After Assigning Task: \n" + dateDifferent(commonModelArrayList.get(position).getCreatedDate().split("T")[0]));
                holder.tvAssignedDate.setVisibility(View.VISIBLE);
                holder.tvAssignedDateDifferent.setVisibility(View.VISIBLE);
            }
        } else {
            holder.tvAssignedDateDifferent.setText("Days Passed After Assigning Task: \n" + dateDifferent(commonModelArrayList.get(position).getCreatedDate().split("T")[0]));
            holder.tvAssignedDate.setText("\uD83D\uDCC5 Assigned Date : \n" + commonModelArrayList.get(position).getCreatedDate().split("T")[0]);
            holder.tvAssignedDate.setVisibility(View.VISIBLE);
            holder.tvAssignedDateDifferent.setVisibility(View.VISIBLE);
        }

        holder.tvPanName.setText(commonModelArrayList.get(position).getPanchayatname());

        holder.tvVillName.setText(commonModelArrayList.get(position).getVillagename());

        holder.tvHabitationName.setText(commonModelArrayList.get(position).getHabitationname());

        sourceCountArrayTotal = new ArrayList<>();
        sourceCountArrayRemaining = new ArrayList<>();
        sourceCountArrayComplete = new ArrayList<>();

        DatabaseHandler databaseHandler = new DatabaseHandler(context);

        sourceCountArrayTotal = databaseHandler.getHabitationSourceCountTotal();
        sourceCountArrayRemaining = databaseHandler.getHabitationSourceCountRemaining();
        sourceCountArrayComplete = databaseHandler.getHabitationSourceCountComplete();

        for (int x = 0; x < sourceCountArrayTotal.size(); x++) {
            if (sourceCountArrayTotal.get(x).getDistrictcode().equalsIgnoreCase(commonModelArrayList.get(position).getDistrictcode())) {
                if (sourceCountArrayTotal.get(x).getBlockcode().equalsIgnoreCase(commonModelArrayList.get(position).getBlockcode())) {
                    if (sourceCountArrayTotal.get(x).getPancode().equalsIgnoreCase(commonModelArrayList.get(position).getPancode())) {
                        if (sourceCountArrayTotal.get(x).getVillagename().equalsIgnoreCase(commonModelArrayList.get(position).getVillagename())) {
                            if (sourceCountArrayTotal.get(x).getHabitationname().equalsIgnoreCase(commonModelArrayList.get(position).getHabitationname())) {
                                holder.tvSourceCountTotal.setText(sourceCountArrayTotal.get(x).getSourceCount());

                            }
                        }
                    }
                }
            }
        }
        if (sourceCountArrayRemaining.size() > 0) {
            for (int x = 0; x < sourceCountArrayRemaining.size(); x++) {
                if (sourceCountArrayRemaining.get(x).getDistrictcode().equalsIgnoreCase(commonModelArrayList.get(position).getDistrictcode())) {
                    if (sourceCountArrayRemaining.get(x).getBlockcode().equalsIgnoreCase(commonModelArrayList.get(position).getBlockcode())) {
                        if (sourceCountArrayRemaining.get(x).getPancode().equalsIgnoreCase(commonModelArrayList.get(position).getPancode())) {
                            if (sourceCountArrayRemaining.get(x).getVillagename().equalsIgnoreCase(commonModelArrayList.get(position).getVillagename())) {
                                if (sourceCountArrayRemaining.get(x).getHabitationname().equalsIgnoreCase(commonModelArrayList.get(position).getHabitationname())) {
                                    if (!sourceCountArrayRemaining.get(x).getSourceCount().equalsIgnoreCase("0")) {
                                        holder.tvSourceCountRemaining.setText(sourceCountArrayRemaining.get(x).getSourceCount());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (sourceCountArrayComplete.size() > 0) {
            for (int x = 0; x < sourceCountArrayComplete.size(); x++) {
                if (sourceCountArrayComplete.get(x).getDistrictcode().equalsIgnoreCase(commonModelArrayList.get(position).getDistrictcode())) {
                    if (sourceCountArrayComplete.get(x).getBlockcode().equalsIgnoreCase(commonModelArrayList.get(position).getBlockcode())) {
                        if (sourceCountArrayComplete.get(x).getPancode().equalsIgnoreCase(commonModelArrayList.get(position).getPancode())) {
                            if (sourceCountArrayComplete.get(x).getVillagename().equalsIgnoreCase(commonModelArrayList.get(position).getVillagename())) {
                                if (sourceCountArrayComplete.get(x).getHabitationname().equalsIgnoreCase(commonModelArrayList.get(position).getHabitationname())) {
                                    if (!sourceCountArrayComplete.get(x).getSourceCount().equalsIgnoreCase("0")) {
                                        holder.tvSourceCountComplete.setText(sourceCountArrayComplete.get(x).getSourceCount());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        holder.rlExpanse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commonModelArrayList.get(position).isFlag()) {
                    commonModelArrayList.get(position).setFlag(false);
                    holder.llExpanse.setVisibility(View.GONE);
                } else {
                    commonModelArrayList.get(position).setFlag(true);
                    holder.llExpanse.setVisibility(View.VISIBLE);
                    showSourceList(context, holder.rvSourceList, lat, lng, type, commonModelArrayList.get(position).getBlockcode(),
                            commonModelArrayList.get(position).getPancode(), commonModelArrayList.get(position).getVillagecode(),
                            commonModelArrayList.get(position).getHabecode(), holder.search);
                }

                if (holder.tvSourceCountRemaining.getText().toString().equalsIgnoreCase("0")) {
                    ((DashBoard_Facilitator_Activity) context).setRefersh2();
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

        private TextView tvPanName, tvVillName, tvHabitationName, tvAssignedDate, tvSourceCountTotal, tvSourceCountRemaining, tvSourceCountComplete, tvAssignedDateDifferent;
        private LinearLayout llExpanse, llShow;
        RecyclerView rvSourceList;
        RelativeLayout rlExpanse;
        SearchView search;

        public MyView_Holder(View itemView) {
            super(itemView);
            tvPanName = itemView.findViewById(R.id.tvPanName);
            tvVillName = itemView.findViewById(R.id.tvVillName);
            tvHabitationName = itemView.findViewById(R.id.tvHabitationName);
            tvSourceCountTotal = itemView.findViewById(R.id.tvSourceCountTotal);
            tvSourceCountRemaining = itemView.findViewById(R.id.tvSourceCountRemaining);
            tvSourceCountComplete = itemView.findViewById(R.id.tvSourceCountComplete);
            llExpanse = itemView.findViewById(R.id.llExpanse);
            llShow = itemView.findViewById(R.id.llShow);
            rvSourceList = itemView.findViewById(R.id.rvSourceList);
            rlExpanse = itemView.findViewById(R.id.rlExpanse);
            tvAssignedDate = itemView.findViewById(R.id.tvAssignedDate);
            tvAssignedDateDifferent = itemView.findViewById(R.id.tvAssignedDateDifferent);
            search = itemView.findViewById(R.id.search);
        }
    }

    Facilitator_SourceData_Adapter sourceDataAdapter;

    public void showSourceList(Context context, RecyclerView rvSourceList, double dLat, double dLng, String type, String blockCode,
                               String panCode, String villageCode, String habitationCode, SearchView search) {

        if (dLat != 0.0 && dLng != 0.0) {
            DataLoadAsync dataLoadAsync = new DataLoadAsync(context, rvSourceList, dLat, dLng, type, blockCode,
                    panCode, villageCode, habitationCode, search);
            dataLoadAsync.execute();
        }

    }

    private final class DataLoadAsync extends AsyncTask<Void, Integer, String> {
        Context context;
        RecyclerView rvSourceList;
        double dLat, dLng;
        String type, blockCode, panCode, villageCode, habitationCode;
        SearchView search;

        public DataLoadAsync(Context context1, RecyclerView rvSourceList1, double dLat1, double dLng1, String type1,
                             String blockCode1, String panCode1, String villageCode1, String habitationCode1, SearchView search1) {
            context = context1;
            rvSourceList = rvSourceList1;
            dLat = dLat1;
            dLng = dLng1;
            type = type1;
            blockCode = blockCode1;
            panCode = panCode1;
            villageCode = villageCode1;
            habitationCode = habitationCode1;
            search = search1;
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
            if (dLat != 0.0 && dLng != 0.0) {
                modelArrayList = new ArrayList<>();
                DatabaseHandler databaseHandler = new DatabaseHandler(context);
                modelArrayList = databaseHandler.getSourceForFacilitator(dLat, dLng, type, blockCode, panCode, villageCode, habitationCode);
                Collections.sort(modelArrayList, new DistanceSorter());
            }
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

    private String dateDifferent(String startDate1) {
        String dateDifferentValue = "";
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String formattedDate = df.format(c.getTime());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            Date startDate = simpleDateFormat.parse(startDate1 + " 12:00");
            Date endDate = simpleDateFormat.parse(formattedDate);

            long different = endDate.getTime() - startDate.getTime();

            System.out.println("different : " + different);

            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            long elapsedDays = different / daysInMilli;
            different = different % daysInMilli;

            long elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;

            if (elapsedDays == 0) {
                dateDifferentValue = elapsedDays + " day";
            } else if (elapsedDays == 1) {
                dateDifferentValue = elapsedDays + " day ";
            } else {
                dateDifferentValue = elapsedDays + " days ";
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateDifferentValue;
    }
}
