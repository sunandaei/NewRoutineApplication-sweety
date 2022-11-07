package com.sunanda.newroutine.application.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.modal.SampleModel;
import com.sunanda.newroutine.application.ui.RecycleViewApp_Activity;
import com.sunanda.newroutine.application.ui.SyncOnlineData_Facilitator_Activity;

import java.util.ArrayList;

public class MenuFacilitrator_Adapter extends RecyclerView.Adapter<MenuFacilitrator_Adapter.ViewHolder> {

    private Activity activity;
    private String[] itemName;
    private Integer[] Imgids;
    private DrawerLayout drawerLayout;
    ArrayList<SampleModel> sampleModelsDataCollection;

    public MenuFacilitrator_Adapter(Activity activity, String[] strings, Integer[] Imgids, DrawerLayout drawerLayout) {
        this.activity = activity;
        this.itemName = strings;
        this.Imgids = Imgids;
        this.drawerLayout = drawerLayout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.navigationlist, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {

        viewHolder.textView.setText(itemName[position]);
        Glide.with(activity).load(Imgids[position]).into(viewHolder.navigationimage);
        viewHolder.textView.setTextColor(Color.BLACK);

        viewHolder.staticLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawers();
                switch (position) {
                    case 0:
                        Intent intent = new Intent(activity, SyncOnlineData_Facilitator_Activity.class);
                        activity.startActivity(intent);
                        activity.overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                        break;
                    case 1:
                        Intent intentRecycle_bin = new Intent(activity, RecycleViewApp_Activity.class);
                        activity.startActivity(intentRecycle_bin);
                        activity.overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                        break;
                    /*case 2: // Synch
                        Intent intent = new Intent(activity, ExistingLocation_Activity.class);
                        activity.startActivity(intent);
                        activity.overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                        break;
                    case 3: // Search
                        Intent intent2 = new Intent(activity, Search_Activity.class);
                        activity.startActivity(intent2);
                        activity.overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                        break;
                    case 4: // Upload
                        DatabaseHandler databaseHandler = new DatabaseHandler(activity);
                        sampleModelsDataCollection = new ArrayList<>();
                        sampleModelsDataCollection = databaseHandler.getSampleCollection();
                        if (sampleModelsDataCollection.size() > 0) {
                            Intent intent4 = new Intent(activity, DataUploadRoutine_Activity.class);
                            activity.startActivity(intent4);
                            activity.overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                        } else {
                            new AlertDialog.Builder(activity)
                                    .setMessage("No New Record to Sync")
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).show();
                        }
                        break;
                    case 5: // Settings
                        Intent intent4 = new Intent(activity, MyAccountActivity.class);*//*LandingPage*//*
                        activity.startActivity(intent4);
                        activity.overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                        break;
                    case 6:
                        Toast.makeText(activity, "Sign-In", Toast.LENGTH_SHORT).show();
                        break;*/
                    /*case 6: // Logout
                        //Toast.makeText(activity, viewHolder.textView.getText().toString(), Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
                        alertDialog.setTitle("DealMart");
                        alertDialog.setMessage("Are you sure you want to Exit?");
                        alertDialog.setIcon(R.mipmap.ic_launcher_round);
                        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Logout();
                            }
                        });
                        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        alertDialog.show();
                        break;*/
                    /*case 7: // Sign-Up
                        //Toast.makeText(activity, viewHolder.textView.getText().toString(), Toast.LENGTH_SHORT).show();
                        Intent intent7 = new Intent(activity, LoginActivity.class);
                        activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                        activity.startActivity(intent7);
                        break;*/
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemName.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView navigationimage;
        private RelativeLayout staticLayout;

        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.navigationtext);
            navigationimage = (ImageView) view.findViewById(R.id.navigationimage);
            staticLayout = view.findViewById(R.id.staticLayout);
        }
    }
}