package com.sunanda.newroutine.application.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.database.DatabaseHandler;
import com.sunanda.newroutine.application.modal.SampleModel;
import com.sunanda.newroutine.application.ui.UserSelection_Activity;
import com.sunanda.newroutine.application.util.CGlobal;
import com.sunanda.newroutine.application.util.Constants;

import java.util.ArrayList;

public class Search_Adapter extends RecyclerView.Adapter<Search_Adapter.ViewHolder> {

    private Context _ctx;
    private ArrayList<SampleModel> product_commonModelsArray;
    String sAppName = "";

    public Search_Adapter(Context context, ArrayList<SampleModel> d, String appName) {
        this.product_commonModelsArray = d;
        this._ctx = context;
        this.sAppName = appName;
    }

    @Override
    public Search_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, null);

        Search_Adapter.ViewHolder viewHolder = new Search_Adapter.ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Search_Adapter.ViewHolder viewHolder, final int position) {

        viewHolder.tvSearchTime.setText(product_commonModelsArray.get(position).getCollection_date_q_4a());

        DatabaseHandler databaseHandler = new DatabaseHandler(_ctx);
        if (product_commonModelsArray.get(position).getType_of_locality_q_5().equalsIgnoreCase("RURAL")) {
            String block = databaseHandler.getBlockSingle(product_commonModelsArray.get(position).getBlock_q_8());
            String panchayat = databaseHandler.getPanchayatSingle(product_commonModelsArray.get(position).getBlock_q_8(), product_commonModelsArray.get(position).getPanchayat_q_9());
            if (!TextUtils.isEmpty(product_commonModelsArray.get(position).getNew_location_q_14())) {

                viewHolder.tvSearchItem.setText(product_commonModelsArray.get(position).getType_of_locality_q_5() + "/"
                        + product_commonModelsArray.get(position).getSample_bottle_number_q_16() + "/"
                        + product_commonModelsArray.get(position).getCollection_date_q_4a() + "/" + block + "/" + panchayat + "/"
                        + product_commonModelsArray.get(position).getVillage_name_q_10() + "/"
                        + product_commonModelsArray.get(position).getHabitation_q_11() + "/"
                        + product_commonModelsArray.get(position).getNew_location_q_14());
            } else {
                if (product_commonModelsArray.get(position).getSpecial_drive_name_q_3().equalsIgnoreCase("ROSTER")
                        || product_commonModelsArray.get(position).getSpecial_drive_name_q_3().equalsIgnoreCase("JE/AES ROSTER")) {

                    viewHolder.tvSearchItem.setText(product_commonModelsArray.get(position).getType_of_locality_q_5() + "/"
                            + product_commonModelsArray.get(position).getSample_bottle_number_q_16() + "/"
                            + product_commonModelsArray.get(position).getCollection_date_q_4a() + "/" + block + "/" + panchayat + "/"
                            + product_commonModelsArray.get(position).getVillage_name_q_10()
                            + "/" + product_commonModelsArray.get(position).getHabitation_q_11() + "/"
                            + product_commonModelsArray.get(position).getExisting_location_q_13());
                } else {
                    viewHolder.tvSearchItem.setText(product_commonModelsArray.get(position).getType_of_locality_q_5() + "/"
                            + product_commonModelsArray.get(position).getSample_bottle_number_q_16() + "/"
                            + product_commonModelsArray.get(position).getCollection_date_q_4a() + "/" + block + "/" + panchayat + "/"
                            + product_commonModelsArray.get(position).getVillage_name_q_10() + "/"
                            + product_commonModelsArray.get(position).getHabitation_q_11() + "/"
                            + product_commonModelsArray.get(position).getExisting_location_q_13());
                }
            }
        } else {
            String town = databaseHandler.getTownSingle(product_commonModelsArray.get(position).getTown_q_7a());
            viewHolder.tvSearchItem.setText(product_commonModelsArray.get(position).getType_of_locality_q_5() + "/"
                    + product_commonModelsArray.get(position).getSample_bottle_number_q_16() + "/"
                    + product_commonModelsArray.get(position).getCollection_date_q_4a() + "/" + town + "/"
                    + product_commonModelsArray.get(position).getWard_q_7b()
                    + "/" + product_commonModelsArray.get(position).getNew_location_q_14());
        }
        viewHolder.tvSearchItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CGlobal.getInstance().getPersistentPreferenceEditor(_ctx)
                        .putInt(Constants.PREFS_SEARCH_CLICK_ID, product_commonModelsArray.get(position).getID()).commit();

                CGlobal.getInstance().getPersistentPreferenceEditor(_ctx).putString(Constants.PREFS_APP_NAME, sAppName).commit();

                Intent intent = new Intent(_ctx, UserSelection_Activity.class);
                _ctx.startActivity(intent);

            }
        });


    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvSearchItem, tvSearchTime;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            tvSearchItem = itemLayoutView.findViewById(R.id.tvSearchItem);
            tvSearchTime = itemLayoutView.findViewById(R.id.tvSearchTime);
        }
    }

    @Override
    public int getItemCount() {
        return product_commonModelsArray.size();
    }
}