package com.sunanda.newroutine.application.adapter.schoolomas;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.database.DatabaseHandler;
import com.sunanda.newroutine.application.modal.SampleModel;
import com.sunanda.newroutine.application.ui.schoolomas.RecycleBinSchoolOMAS_Activity;

import java.util.ArrayList;

public class RecycleBinSchoolOMAS_Adapter extends RecyclerView.Adapter<RecycleBinSchoolOMAS_Adapter.ViewHolder> {

    private Context _ctx;
    private ArrayList<SampleModel> product_SampleModelsArray;
    private RecycleBinSchoolOMAS_Activity.BtnRestore btnImage = null;

    public RecycleBinSchoolOMAS_Adapter(Context context, ArrayList<SampleModel> d, RecycleBinSchoolOMAS_Activity.BtnRestore listener) {
        this.product_SampleModelsArray = d;
        this._ctx = context;
        this.btnImage = listener;
    }

    @Override
    public RecycleBinSchoolOMAS_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_bin_item, null);

        RecycleBinSchoolOMAS_Adapter.ViewHolder viewHolder = new RecycleBinSchoolOMAS_Adapter.ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecycleBinSchoolOMAS_Adapter.ViewHolder viewHolder, final int position) {

        viewHolder.tvSearchTime.setText(product_SampleModelsArray.get(position).getCollection_date_q_4a() + " ("
                + product_SampleModelsArray.get(position).getApp_name()
                + " - " + product_SampleModelsArray.get(position).getApp_version()+ ")");

        viewHolder.cbRestore.setTag(position);

        DatabaseHandler databaseHandler = new DatabaseHandler(_ctx);
        if (product_SampleModelsArray.get(position).getType_of_locality_q_5().equalsIgnoreCase("RURAL")) {
            String block = databaseHandler.getBlockSingle(product_SampleModelsArray.get(position).getBlock_q_8());
            String panchayat = databaseHandler.getPanchayatSingle(product_SampleModelsArray.get(position).getBlock_q_8(), product_SampleModelsArray.get(position).getPanchayat_q_9());
            if (!TextUtils.isEmpty(product_SampleModelsArray.get(position).getNew_location_q_14())) {
                viewHolder.tvdata.setText(product_SampleModelsArray.get(position).getType_of_locality_q_5() + "/"
                        + product_SampleModelsArray.get(position).getSample_bottle_number_q_16() + "/"
                        + product_SampleModelsArray.get(position).getCollection_date_q_4a() + "/" + block + "/" + panchayat + "/"
                        + product_SampleModelsArray.get(position).getVillage_name_q_10() + "/" + product_SampleModelsArray.get(position).getHabitation_q_11()
                        + "/" + product_SampleModelsArray.get(position).getNew_location_q_14());
            } else {
                if (product_SampleModelsArray.get(position).getSpecial_drive_name_q_3().equalsIgnoreCase("ROSTER")
                        || product_SampleModelsArray.get(position).getSpecial_drive_name_q_3().equalsIgnoreCase("JE/AES ROSTER")) {
                    viewHolder.tvdata.setText(product_SampleModelsArray.get(position).getType_of_locality_q_5() + "/"
                            + product_SampleModelsArray.get(position).getSample_bottle_number_q_16() + "/"
                            + product_SampleModelsArray.get(position).getCollection_date_q_4a() + "/" + block + "/" + panchayat + "/"
                            + product_SampleModelsArray.get(position).getVillage_name_q_10()
                            + "/" + product_SampleModelsArray.get(position).getHabitation_q_11()
                            + "/" + product_SampleModelsArray.get(position).getExisting_location_q_13());
                } else {
                    viewHolder.tvdata.setText(product_SampleModelsArray.get(position).getType_of_locality_q_5() + "/"
                            + product_SampleModelsArray.get(position).getSample_bottle_number_q_16() + "/"
                            + product_SampleModelsArray.get(position).getCollection_date_q_4a() + "/" + block + "/" + panchayat + "/"
                            + product_SampleModelsArray.get(position).getVillage_name_q_10() + "/"
                            + product_SampleModelsArray.get(position).getHabitation_q_11() + "/"
                            + product_SampleModelsArray.get(position).getExisting_location_q_13());
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

        viewHolder.cbRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnImage != null)
                    btnImage.onBtnRestoreValue((Integer) viewHolder.cbRestore.getTag(), product_SampleModelsArray.get(position).getID(), viewHolder.cbRestore);
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvdata, tvSearchTime;
        CheckBox cbRestore;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            tvdata = itemLayoutView.findViewById(R.id.tvdata);
            tvSearchTime = itemLayoutView.findViewById(R.id.tvSearchTime);

            cbRestore = itemLayoutView.findViewById(R.id.cbRestore);
        }
    }

    @Override
    public int getItemCount() {
        return product_SampleModelsArray.size();
    }
}