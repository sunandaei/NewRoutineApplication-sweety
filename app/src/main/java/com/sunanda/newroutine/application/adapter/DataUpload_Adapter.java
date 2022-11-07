package com.sunanda.newroutine.application.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.database.DatabaseHandler;
import com.sunanda.newroutine.application.modal.SampleModel_Routine;
import com.sunanda.newroutine.application.ui.DataUploadRoutine_Activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DataUpload_Adapter extends RecyclerView.Adapter<DataUpload_Adapter.ViewHolder> {

    private Context _ctx;
    private ArrayList<SampleModel_Routine> product_SampleModelsArray;
    private ArrayList<SampleModel_Routine> sampleModelsArray;
    private DataUploadRoutine_Activity.BtnImage btnImage = null;
    private DataUploadRoutine_Activity.BtnImageDelete btnImageDelete = null;

    public DataUpload_Adapter(Context context, ArrayList<SampleModel_Routine> d, DataUploadRoutine_Activity.BtnImage listener,
                              DataUploadRoutine_Activity.BtnImageDelete listenerDelete) {
        this.product_SampleModelsArray = d;
        this._ctx = context;
        this.btnImage = listener;
        this.btnImageDelete = listenerDelete;

    }

    @Override
    public DataUpload_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_upload_item, null);

        DataUpload_Adapter.ViewHolder viewHolder = new DataUpload_Adapter.ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final DataUpload_Adapter.ViewHolder viewHolder, final int position) {

        viewHolder.tvSearchTime.setText(product_SampleModelsArray.get(position).getCollection_date_q_4a()
                + " - " + product_SampleModelsArray.get(position).getApp_version());

        viewHolder.btnSendData.setTag(position);
        viewHolder.btnDeleteData.setTag(position);

        DatabaseHandler databaseHandler = new DatabaseHandler(_ctx);
        if (product_SampleModelsArray.get(position).getType_of_locality_q_5().equalsIgnoreCase("RURAL")) {
            String block = databaseHandler.getBlockSingle(product_SampleModelsArray.get(position).getBlock_q_8());
            String village = databaseHandler.getVillageSingle(product_SampleModelsArray.get(position).getBlock_q_8(),
                    product_SampleModelsArray.get(position).getPanchayat_q_9(), product_SampleModelsArray.get(position).getVillage_code());
            String panchayat = databaseHandler.getPanchayatSingle(product_SampleModelsArray.get(position).getBlock_q_8(),
                    product_SampleModelsArray.get(position).getPanchayat_q_9());
            String habitation = databaseHandler.getHabitationSingle(product_SampleModelsArray.get(position).getBlock_q_8(),
                    product_SampleModelsArray.get(position).getPanchayat_q_9(),
                    product_SampleModelsArray.get(position).getVillage_code(),
                    product_SampleModelsArray.get(position).getHanitation_code());
            if (!TextUtils.isEmpty(product_SampleModelsArray.get(position).getNew_location_q_14())) {
                viewHolder.tvdata.setText(product_SampleModelsArray.get(position).getType_of_locality_q_5() + "/"
                        + product_SampleModelsArray.get(position).getSample_bottle_number_q_16() + "/"
                        + product_SampleModelsArray.get(position).getCollection_date_q_4a() + "/" + block + "/" + panchayat + "/"
                        + village + "/" + habitation + "/" + product_SampleModelsArray.get(position).getNew_location_q_14());
            } else {
                if (product_SampleModelsArray.get(position).getSpecial_drive_name_q_3().equalsIgnoreCase("ROSTER")
                        || product_SampleModelsArray.get(position).getSpecial_drive_name_q_3().equalsIgnoreCase("JE/AES ROSTER")) {
                    viewHolder.tvdata.setText(product_SampleModelsArray.get(position).getType_of_locality_q_5() + "/"
                            + product_SampleModelsArray.get(position).getSample_bottle_number_q_16() + "/"
                            + product_SampleModelsArray.get(position).getCollection_date_q_4a() + "/" + block + "/" + panchayat + "/"
                            + product_SampleModelsArray.get(position).getVillage_name_q_10()
                            + "/" + product_SampleModelsArray.get(position).getHabitation_q_11() + "/"
                            + product_SampleModelsArray.get(position).getExisting_location_q_13());
                } else {
                    viewHolder.tvdata.setText(product_SampleModelsArray.get(position).getType_of_locality_q_5() + "/"
                            + product_SampleModelsArray.get(position).getSample_bottle_number_q_16() + "/"
                            + product_SampleModelsArray.get(position).getCollection_date_q_4a() + "/" + block + "/" + panchayat + "/"
                            + village + "/" + habitation + "/" + product_SampleModelsArray.get(position).getExisting_location_q_13());
                }
            }
        } else {
            String town = databaseHandler.getTownSingle(product_SampleModelsArray.get(position).getTown_q_7a());
            viewHolder.tvdata.setText(product_SampleModelsArray.get(position).getType_of_locality_q_5() + "/"
                    + product_SampleModelsArray.get(position).getSample_bottle_number_q_16() + "/"
                    + product_SampleModelsArray.get(position).getCollection_date_q_4a() + "/" + town + "/"
                    + product_SampleModelsArray.get(position).getWard_q_7b()
                    + "/" + product_SampleModelsArray.get(position).getNew_location_q_14());
        }

        viewHolder.btnSendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnImage != null)
                    btnImage.onBtnImageValue((Integer) viewHolder.btnSendData.getTag());
                notifyDataSetChanged();
            }
        });

        viewHolder.btnDeleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnImageDelete != null)
                    btnImageDelete.onBtnImageDeleteValue((Integer) viewHolder.btnDeleteData.getTag());
            }
        });


        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String formattedDate = df.format(c.getTime());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            Date startDate = simpleDateFormat.parse(product_SampleModelsArray.get(position).getCollection_date_q_4a() + " "
                    + product_SampleModelsArray.get(position).getTime_q_4b());
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

            viewHolder.tvTimeDefrent.setText(elapsedDays + " days " + elapsedHours + " hours");
            if (elapsedDays >= 2) {
                final int sdk = android.os.Build.VERSION.SDK_INT;
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    viewHolder.tvTimeDefrent.setBackgroundDrawable(ContextCompat.getDrawable(_ctx, R.drawable.button_backgroung_red));
                } else {
                    viewHolder.tvTimeDefrent.setBackground(ContextCompat.getDrawable(_ctx, R.drawable.button_backgroung_red));
                }
            } else {
                final int sdk = android.os.Build.VERSION.SDK_INT;
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    viewHolder.tvTimeDefrent.setBackgroundDrawable(ContextCompat.getDrawable(_ctx, R.drawable.button_backgroung_green));
                } else {
                    viewHolder.tvTimeDefrent.setBackground(ContextCompat.getDrawable(_ctx, R.drawable.button_backgroung_green));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvdata, tvSearchTime, tvTimeDefrent;
        Button btnSendData, btnDeleteData;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            tvdata = itemLayoutView.findViewById(R.id.tvdata);
            tvSearchTime = itemLayoutView.findViewById(R.id.tvSearchTime);
            tvTimeDefrent = itemLayoutView.findViewById(R.id.tvTimeDefrent);

            btnSendData = itemLayoutView.findViewById(R.id.btnSendData);
            btnDeleteData = itemLayoutView.findViewById(R.id.btnDeleteData);
        }
    }

    @Override
    public int getItemCount() {
        return product_SampleModelsArray.size();
    }
}