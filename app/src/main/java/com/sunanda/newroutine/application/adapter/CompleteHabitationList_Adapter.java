package com.sunanda.newroutine.application.adapter;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.database.DatabaseHandler;
import com.sunanda.newroutine.application.modal.CommonModel;
import com.sunanda.newroutine.application.modal.SampleModel;
import com.sunanda.newroutine.application.util.CGlobal;
import com.sunanda.newroutine.application.util.Constants;

import java.util.ArrayList;
import java.util.Comparator;

public class CompleteHabitationList_Adapter extends RecyclerView.Adapter<CompleteHabitationList_Adapter.MyView_Holder> {

    private ArrayList<SampleModel> sampleModelArrayListComplete, modelArrayListComplete, modelArrayListCompleteRoutine,
            modelArrayListCompleteOmas, modelArrayListCompleteSchool;
    private Context context;
    double lat, lng;
    String panName = "";

    public CompleteHabitationList_Adapter(ArrayList<SampleModel> sampleModelArrayListComplete, Context context) {
        this.sampleModelArrayListComplete = sampleModelArrayListComplete;
        this.context = context;
    }

    @Override
    public MyView_Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.complete_habitation_list_item, parent, false);
        MyView_Holder holder = new MyView_Holder(v);
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyView_Holder holder, final int position) {
        DatabaseHandler databaseHandler = new DatabaseHandler(context);
        panName = databaseHandler.getPanchayatSingle(sampleModelArrayListComplete.get(position).getBlock_q_8(), sampleModelArrayListComplete.get(position).getPanchayat_q_9());
        if (TextUtils.isEmpty(panName)) {
            panName = databaseHandler.getPanchayatRosterSingel(sampleModelArrayListComplete.get(position).getBlock_q_8(), sampleModelArrayListComplete.get(position).getPanchayat_q_9());
            if (TextUtils.isEmpty(panName)) {
                panName = databaseHandler.getPanchayatArsenicSingel(sampleModelArrayListComplete.get(position).getBlock_q_8(), sampleModelArrayListComplete.get(position).getPanchayat_q_9());
            }
        }
        if (position != 0) {
            if (sampleModelArrayListComplete.get(position - 1).getCollection_date_q_4a().
                    equalsIgnoreCase(sampleModelArrayListComplete.get(position).getCollection_date_q_4a())) {
                if (sampleModelArrayListComplete.get(position - 1).getHabitation_q_11()
                        .equalsIgnoreCase(sampleModelArrayListComplete.get(position).getHabitation_q_11())) {
                    holder.cardView.setVisibility(View.GONE);
                } else {
                    holder.tvPanName.setText(panName);
                    holder.tvVillName.setText(sampleModelArrayListComplete.get(position).getVillage_name_q_10());
                    holder.tvHabitationName.setText(sampleModelArrayListComplete.get(position).getHabitation_q_11());
                    holder.cardView.setVisibility(View.VISIBLE);
                }
                holder.tvAssignedDate.setVisibility(View.GONE);
            } else {
                holder.tvAssignedDate.setText("\uD83D\uDCC5 Collection Date : " + sampleModelArrayListComplete.get(position).getCollection_date_q_4a());
                holder.tvAssignedDate.setVisibility(View.VISIBLE);
                if (sampleModelArrayListComplete.get(position - 1).getHabitation_q_11()
                        .equalsIgnoreCase(sampleModelArrayListComplete.get(position).getHabitation_q_11())) {
                    holder.cardView.setVisibility(View.GONE);
                } else {
                    holder.tvPanName.setText(panName);
                    holder.tvVillName.setText(sampleModelArrayListComplete.get(position).getVillage_name_q_10());
                    holder.tvHabitationName.setText(sampleModelArrayListComplete.get(position).getHabitation_q_11());
                    holder.cardView.setVisibility(View.VISIBLE);
                }
            }
        } else {
            holder.tvAssignedDate.setText("\uD83D\uDCC5 Collection Date : " + sampleModelArrayListComplete.get(position).getCollection_date_q_4a());
            holder.tvAssignedDate.setVisibility(View.VISIBLE);
            holder.tvPanName.setText(panName);
            holder.tvVillName.setText(sampleModelArrayListComplete.get(position).getVillage_name_q_10());
            holder.tvHabitationName.setText(sampleModelArrayListComplete.get(position).getHabitation_q_11());
            holder.cardView.setVisibility(View.VISIBLE);
        }

        holder.rlExpanse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sampleModelArrayListComplete.get(position).isFlag()) {
                    sampleModelArrayListComplete.get(position).setFlag(false);
                    holder.llExpanse.setVisibility(View.GONE);
                } else {
                    sampleModelArrayListComplete.get(position).setFlag(true);
                    holder.llExpanse.setVisibility(View.VISIBLE);
                    showCompleteSourceList(context, holder.rvSourceList,
                            sampleModelArrayListComplete.get(position).getBlock_q_8(),
                            sampleModelArrayListComplete.get(position).getPanchayat_q_9(),
                            sampleModelArrayListComplete.get(position).getVillage_code(),
                            sampleModelArrayListComplete.get(position).getHanitation_code());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return sampleModelArrayListComplete.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class MyView_Holder extends RecyclerView.ViewHolder {

        private TextView tvPanName, tvVillName, tvHabitationName, tvAssignedDate;
        private LinearLayout llExpanse, llShow;
        RecyclerView rvSourceList;
        RelativeLayout rlExpanse;
        CardView cardView;

        public MyView_Holder(View itemView) {
            super(itemView);
            tvPanName = itemView.findViewById(R.id.tvPanName);
            tvVillName = itemView.findViewById(R.id.tvVillName);
            tvHabitationName = itemView.findViewById(R.id.tvHabitationName);
            llExpanse = itemView.findViewById(R.id.llExpanse);
            rvSourceList = itemView.findViewById(R.id.rvSourceList);
            rlExpanse = itemView.findViewById(R.id.rlExpanse);
            tvAssignedDate = itemView.findViewById(R.id.tvAssignedDate);
            llShow = itemView.findViewById(R.id.llShow);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }

    String sTaskIdx = "";

    public void showCompleteSourceList(Context context, RecyclerView rvSourceList, String blockCode,
                                       String panCode, String villageCode, String habitationCode) {
        modelArrayListComplete = new ArrayList<>();
        DatabaseHandler databaseHandler = new DatabaseHandler(context);
        try {
            sTaskIdx = databaseHandler.getTaskId(blockCode, panCode, villageCode, habitationCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sFCID = CGlobal.getInstance().getPersistentPreference(context)
                .getString(Constants.PREFS_USER_FACILITATOR_ID, "");

        modelArrayListCompleteRoutine = databaseHandler.getSampleCollectionComplete(blockCode, panCode, villageCode, habitationCode,
                sTaskIdx, sFCID, "0", "Routine");
        modelArrayListCompleteOmas = databaseHandler.getSampleCollectionComplete(blockCode, panCode, villageCode, habitationCode,
                sTaskIdx, sFCID, "0", "OMAS");
        modelArrayListCompleteSchool = databaseHandler.getSchoolAppDataCollectionComplete(blockCode, panCode, villageCode, habitationCode,
                sTaskIdx, sFCID, "0", "School");

        modelArrayListComplete.addAll(modelArrayListCompleteRoutine);
        modelArrayListComplete.addAll(modelArrayListCompleteSchool);
        modelArrayListComplete.addAll(modelArrayListCompleteOmas);

        CompleteFacilitator_SourceData_Adapter sourceDataAdapter = new CompleteFacilitator_SourceData_Adapter(context, modelArrayListComplete);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        rvSourceList.setLayoutManager(linearLayoutManager);
        rvSourceList.setItemAnimator(new DefaultItemAnimator());
        rvSourceList.setAdapter(sourceDataAdapter);
    }

    public class DistanceSorter implements Comparator<CommonModel> {
        @Override
        public int compare(CommonModel o1, CommonModel o2) {
            return String.valueOf(o1.getDistance()).compareToIgnoreCase(String.valueOf(o2.getDistance()));
        }
    }
}
