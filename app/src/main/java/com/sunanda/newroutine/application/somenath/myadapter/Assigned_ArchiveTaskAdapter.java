package com.sunanda.newroutine.application.somenath.myadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.somenath.pojo.AssignedArchiveTaskPojo;
import com.sunanda.newroutine.application.somenath.view.SourceListByHabitation2;
import com.sunanda.newroutine.application.util.CGlobal;
import com.sunanda.newroutine.application.util.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Assigned_ArchiveTaskAdapter extends RecyclerView.Adapter<Assigned_ArchiveTaskAdapter.MyView_Holder> {

    private String type;
    String villageCode;
    private ArrayList<AssignedArchiveTaskPojo> commonModelArrayList;
    private Context context;
    private String labname = CGlobal.getInstance().getPersistentPreference(context)
            .getString(Constants.PREFS_USER_LAB_NAME, "");
    private String labcode = CGlobal.getInstance().getPersistentPreference(context)
            .getString(Constants.PREFS_USER_LAB_CODE, "");

    public Assigned_ArchiveTaskAdapter(ArrayList<AssignedArchiveTaskPojo> commonModelArray, Context context, String type) {
        this.commonModelArrayList = commonModelArray;
        this.context = context;
        this.type = type;
    }

    @Override
    public MyView_Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.facilitator_assigned_list_item, parent, false);
        MyView_Holder holder = new MyView_Holder(v);
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyView_Holder holder, final int position) {

        if (!commonModelArrayList.get(position).getVillageCode().equalsIgnoreCase(villageCode)) {
            villageCode = commonModelArrayList.get(position).getVillageCode();
            if (!TextUtils.isEmpty(commonModelArrayList.get(position).getVillageName())) {
                if (type.equalsIgnoreCase("CURRENT")) {
                    if (!TextUtils.isEmpty(commonModelArrayList.get(position).getPws_status())) {
                        holder.title.setText("Village : " + commonModelArrayList.get(position).getVillageName());
                        //holder.title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.circle, 0, 0, 0);

                        holder.title2.setText(labname + "/" + commonModelArrayList.get(position).getDistrictName() +
                                "/" + commonModelArrayList.get(position).getBlockName() + "/" +
                                commonModelArrayList.get(position).getPanName() + "/" +
                                commonModelArrayList.get(position).getVillageName());

                        @SuppressLint("SimpleDateFormat")
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = new Date();
                        try {
                            String newDate = commonModelArrayList.get(position).getCreatedDate().split("T")[0] + " " +
                                    commonModelArrayList.get(position).getCreatedDate().split("T")[1].substring(0, 8);

                            Date date1 = simpleDateFormat.parse(simpleDateFormat.format(date));
                            Date date2 = simpleDateFormat.parse(newDate);
                            holder.timeElapsed.setText("⌚ Time Elapsed : " + printDifference(date2, date1));

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (type.equalsIgnoreCase("COLLECT")) {

                    holder.title.setText("Village : " + commonModelArrayList.get(position).getVillageName());
                    //holder.title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tick, 0, 0, 0);

                    holder.title2.setText(labname + "/" + commonModelArrayList.get(position).getDistrictName() +
                            "/" + commonModelArrayList.get(position).getBlockName() + "/" +
                            commonModelArrayList.get(position).getPanName() + "/" +
                            commonModelArrayList.get(position).getVillageName() + "\n\n\uD83D\uDCC5 Submission Date (To Lab) : " +
                            commonModelArrayList.get(position).getFormSubmissionDate().split("T")[0].split("-")[2] + "/" +
                            commonModelArrayList.get(position).getFormSubmissionDate().split("T")[0].split("-")[1] + "/" +
                            commonModelArrayList.get(position).getFormSubmissionDate().split("T")[0].split("-")[0]);

                    @SuppressLint("SimpleDateFormat")
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                        String newDate = commonModelArrayList.get(position).getCreatedDate().split("T")[0] + " " +
                                commonModelArrayList.get(position).getCreatedDate().split("T")[1].substring(0, 8);

                        String submitDate = commonModelArrayList.get(position).getFormSubmissionDate().split("T")[0] + " " +
                                commonModelArrayList.get(position).getFormSubmissionDate().split("T")[1].substring(0, 8);

                        Date date1 = simpleDateFormat.parse(submitDate);
                        Date date2 = simpleDateFormat.parse(newDate);
                        holder.timeElapsed.setText("⌚ Time Elapsed : " + printDifference(date2, date1));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (commonModelArrayList.get(position).getNoOfCollection().equalsIgnoreCase("0")) {
                        holder.title.setTextColor(context.getResources().getColor(R.color.Red));
                    } else if (commonModelArrayList.get(position).getNoOfSource().
                            equalsIgnoreCase(commonModelArrayList.get(position).getNoOfCollection())) {
                        holder.title.setTextColor(context.getResources().getColor(R.color.Green));
                    } else {
                        holder.title.setTextColor(context.getResources().getColor(R.color.DarkOrange));
                    }
                } else if (type.equalsIgnoreCase("ACCEPT")) {

                    holder.title.setText("Village : " + commonModelArrayList.get(position).getVillageName());
                    //holder.title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tick, 0, 0, 0);

                    holder.title2.setText(labname + "/" + commonModelArrayList.get(position).getDistrictName() +
                            "/" + commonModelArrayList.get(position).getBlockName() + "/" +
                            commonModelArrayList.get(position).getPanName() + "/" +
                            commonModelArrayList.get(position).getVillageName() + "\n\n\uD83D\uDCC5 Accepted Date (By Lab) : " +
                            commonModelArrayList.get(position).getFecilatorCompletedDate().split("T")[0].split("-")[2] + "/" +
                            commonModelArrayList.get(position).getFecilatorCompletedDate().split("T")[0].split("-")[1] + "/" +
                            commonModelArrayList.get(position).getFecilatorCompletedDate().split("T")[0].split("-")[0]);

                    @SuppressLint("SimpleDateFormat")
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                        String newDate = commonModelArrayList.get(position).getCreatedDate().split("T")[0] + " " +
                                commonModelArrayList.get(position).getCreatedDate().split("T")[1].substring(0, 8);

                        String submitDate = commonModelArrayList.get(position).getFecilatorCompletedDate().split("T")[0] + " " +
                                commonModelArrayList.get(position).getFecilatorCompletedDate().split("T")[1].substring(0, 8);

                        Date date1 = simpleDateFormat.parse(submitDate);
                        Date date2 = simpleDateFormat.parse(newDate);
                        holder.timeElapsed.setText("⌚ Time Elapsed : " + printDifference(date2, date1));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (commonModelArrayList.get(position).getNoOfCollection().equalsIgnoreCase("0")) {
                        holder.title.setTextColor(context.getResources().getColor(R.color.Red));
                    } else if (commonModelArrayList.get(position).getNoOfSource().
                            equalsIgnoreCase(commonModelArrayList.get(position).getNoOfCollection())) {
                        holder.title.setTextColor(context.getResources().getColor(R.color.Green));
                    } else {
                        holder.title.setTextColor(context.getResources().getColor(R.color.DarkOrange));
                    }
                } else {
                    holder.title.setText("Habitation : " + commonModelArrayList.get(position).getVillageName());
                    //holder.title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tick, 0, 0, 0);

                    holder.title2.setText(labname + "/" + commonModelArrayList.get(position).getDistrictName() +
                            "/" + commonModelArrayList.get(position).getBlockName() + "/" +
                            commonModelArrayList.get(position).getPanName() + "/" +
                            commonModelArrayList.get(position).getVillageName() + "\n\n\uD83D\uDCC5 Completion Date : " +
                            commonModelArrayList.get(position).getTestCompletedDate().split("T")[0].split("-")[2] + "/" +
                            commonModelArrayList.get(position).getTestCompletedDate().split("T")[0].split("-")[1] + "/" +
                            commonModelArrayList.get(position).getTestCompletedDate().split("T")[0].split("-")[0]);

                    @SuppressLint("SimpleDateFormat")
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                        String newDate = commonModelArrayList.get(position).getCreatedDate().split("T")[0] + " " +
                                commonModelArrayList.get(position).getCreatedDate().split("T")[1].substring(0, 8);

                        String submitDate = commonModelArrayList.get(position).getTestCompletedDate().split("T")[0] + " " +
                                commonModelArrayList.get(position).getTestCompletedDate().split("T")[1].substring(0, 8);

                        Date date1 = simpleDateFormat.parse(submitDate);
                        Date date2 = simpleDateFormat.parse(newDate);
                        holder.timeElapsed.setText("⌚ Time Elapsed : " + printDifference(date2, date1));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (commonModelArrayList.get(position).getNoOfCollection().equalsIgnoreCase("0")) {
                        holder.title.setTextColor(context.getResources().getColor(R.color.Red));
                    } else if (commonModelArrayList.get(position).getNoOfSource().
                            equalsIgnoreCase(commonModelArrayList.get(position).getNoOfCollection())) {
                        holder.title.setTextColor(context.getResources().getColor(R.color.Green));
                    } else {
                        holder.title.setTextColor(context.getResources().getColor(R.color.DarkOrange));
                    }
                }

                holder.noofsource.setText("✔ No of Sources : " + commonModelArrayList.get(position).getNoOfSource());
                holder.noofcollection.setText("✔ No of Collection : " + commonModelArrayList.get(position).getNoOfCollection());

                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (commonModelArrayList.get(position).isFlag()) {
                            commonModelArrayList.get(position).setFlag(false);
                            holder.llview.setVisibility(View.GONE);
                            holder.imageView.setImageResource(R.drawable.more);
                        } else {
                            commonModelArrayList.get(position).setFlag(true);
                            holder.llview.setVisibility(View.VISIBLE);
                            holder.imageView.setImageResource(R.drawable.less);
                        }
                    }
                });

                holder.llview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(context, SourceListByHabitation2.class);
                        i.putExtra("FCID", commonModelArrayList.get(position).getfCID());
                        i.putExtra("HC", commonModelArrayList.get(position).getHabCode());
                        i.putExtra("HN", commonModelArrayList.get(position).getHabName());
                        i.putExtra("DC", commonModelArrayList.get(position).getDistCode());
                        i.putExtra("DN", commonModelArrayList.get(position).getDistrictName());
                        i.putExtra("BC", commonModelArrayList.get(position).getBlockCode());
                        i.putExtra("BN", commonModelArrayList.get(position).getBlockName());
                        i.putExtra("PC", commonModelArrayList.get(position).getPanCode());
                        i.putExtra("PN", commonModelArrayList.get(position).getPanName());
                        i.putExtra("VC", commonModelArrayList.get(position).getVillageCode());
                        i.putExtra("VN", commonModelArrayList.get(position).getVillageName());
                        i.putExtra("LC", commonModelArrayList.get(position).getLabCode());
                        i.putExtra("TID", commonModelArrayList.get(position).getTask_Id());
                        if (type.equalsIgnoreCase("COLLECT"))
                            i.putExtra("TYPE", "1");
                        else if (type.equalsIgnoreCase("ACCEPT"))
                            i.putExtra("TYPE", "2");
                        else if (type.equalsIgnoreCase("CURRENT"))
                            if (commonModelArrayList.get(position).getPws_status().equalsIgnoreCase("YES")) {
                                i.putExtra("TYPE", "5");
                            } else {
                                i.putExtra("TYPE", "0");
                            }
                        else
                            i.putExtra("TYPE", "3");
                        context.startActivity(i);
                    }
                });

        /*String time = commonModelArrayList.get(position).getCreatedDate().split("T")[0].split("-")[2] + "/" +
                commonModelArrayList.get(position).getCreatedDate().split("T")[0].split("-")[1] + "/" +
                commonModelArrayList.get(position).getCreatedDate().split("T")[0].split("-")[0];*/
                if (position != 0) {
                    if (commonModelArrayList.get(position - 1).getCreatedDate().split("T")[0].
                            equalsIgnoreCase(commonModelArrayList.get(position).getCreatedDate().split("T")[0]))
                        holder.date.setVisibility(View.GONE);
                    else {
                        holder.date.setText("\uD83D\uDCC5 Assigned Date : " + commonModelArrayList.get(position).getCreatedDate().split("T")[0].split("-")[2] + "/" +
                                commonModelArrayList.get(position).getCreatedDate().split("T")[0].split("-")[1] + "/" +
                                commonModelArrayList.get(position).getCreatedDate().split("T")[0].split("-")[0]);
                        holder.date.setVisibility(View.VISIBLE);
                    }
                } else {
                    holder.date.setText("\uD83D\uDCC5 Assigned Date : " + commonModelArrayList.get(position).getCreatedDate().split("T")[0].split("-")[2] + "/" +
                            commonModelArrayList.get(position).getCreatedDate().split("T")[0].split("-")[1] + "/" +
                            commonModelArrayList.get(position).getCreatedDate().split("T")[0].split("-")[0]);
                    holder.date.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return commonModelArrayList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    class MyView_Holder extends RecyclerView.ViewHolder {

        private TextView title, title2, date, noofsource, noofcollection, timeElapsed;
        private ImageView imageView;
        private LinearLayout llview;
        private CardView cardView;

        public MyView_Holder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            title2 = (TextView) itemView.findViewById(R.id.title2);
            noofsource = (TextView) itemView.findViewById(R.id.noofsource);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            llview = (LinearLayout) itemView.findViewById(R.id.llview);
            date = (TextView) itemView.findViewById(R.id.date);
            noofcollection = (TextView) itemView.findViewById(R.id.noofcollection);
            timeElapsed = (TextView) itemView.findViewById(R.id.timeElapsed);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
        }
    }

    private String printDifference(Date startDate, Date endDate) {
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : " + endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);

        String timeElapsed = elapsedDays + " days " + elapsedHours + " hours " + elapsedMinutes + " minutes";
        return timeElapsed;
    }
}
