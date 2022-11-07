package com.sunanda.newroutine.application.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.modal.SampleModel;

import java.util.ArrayList;

public class CompleteFacilitator_SourceData_Adapter extends RecyclerView.Adapter<CompleteFacilitator_SourceData_Adapter.ViewHolder> {

    private Context _ctx;
    private ArrayList<SampleModel> modelsArray;
    String url = "";

    public CompleteFacilitator_SourceData_Adapter(Context context, ArrayList<SampleModel> d) {
        this.modelsArray = d;
        this._ctx = context;
    }

    @Override
    public CompleteFacilitator_SourceData_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.complete_facilitator_source_data_item, null);

        CompleteFacilitator_SourceData_Adapter.ViewHolder viewHolder = new CompleteFacilitator_SourceData_Adapter.ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onBindViewHolder(@NonNull final CompleteFacilitator_SourceData_Adapter.ViewHolder viewHolder, final int position) {

        if (TextUtils.isEmpty(modelsArray.get(position).getExisting_mid())) {
            if (modelsArray.get(position).getApp_name().equalsIgnoreCase("School")) {
                viewHolder.tvName.setText(modelsArray.get(position).getNew_location_q_14() + " (New)");
            } else {
                if (modelsArray.get(position).getThis_new_location_q_12().equalsIgnoreCase("No")) {
                    viewHolder.tvName.setText(modelsArray.get(position).getExisting_location_q_13() + " (New)");
                } else {
                    viewHolder.tvName.setText(modelsArray.get(position).getNew_location_q_14() + " (New)");
                }
            }
        } else {
            if (modelsArray.get(position).getApp_name().equalsIgnoreCase("School")) {
                viewHolder.tvName.setText(modelsArray.get(position).getNew_location_q_14() + " (Retest)");
            } else {
                if (modelsArray.get(position).getThis_new_location_q_12().equalsIgnoreCase("No")) {
                    viewHolder.tvName.setText(modelsArray.get(position).getExisting_location_q_13() + " (Retest)");
                } else {
                    viewHolder.tvName.setText(modelsArray.get(position).getNew_location_q_14() + " (Retest)");
                }
            }
        }

        viewHolder.tvAppName.setText(Html.fromHtml("Source: <b>" + modelsArray.get(position).getApp_name() + "</b>"));

        viewHolder.coll_date.setText(Html.fromHtml("Collection Date: <b>" + modelsArray.get(position).getCollection_date_q_4a() + "</b>"));

        viewHolder.tvSampleNumber.setText(Html.fromHtml("Sample Bottle Number: <b>" + modelsArray.get(position).getSample_bottle_number_q_16() + "</b>"));

        viewHolder.tvTypeOfSource.setText(Html.fromHtml("Type of Source: <b>" + modelsArray.get(position).getType_of_locality_q_5() + "</b>"));

        viewHolder.sourceType.setText(Html.fromHtml("Source Type : <b>" + modelsArray.get(position).getSource_type_q_6() + "</b>"));


        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(modelsArray.get(position).getSource_image_q_17(), bmOptions);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(modelsArray.get(position).getSource_image_q_17(), bmOptions);
        viewHolder.imageView.setImageBitmap(bitmap);

        if (modelsArray.get(position).getSpecial_drive_q_2().equalsIgnoreCase("Yes")) {
            if (modelsArray.get(position).getSpecial_drive_name_q_3().equalsIgnoreCase("ARSENIC TREND STATION")) {
                viewHolder.llName.setBackgroundColor(ContextCompat.getColor(_ctx, R.color.x));
            } else if (modelsArray.get(position).getSpecial_drive_name_q_3().equalsIgnoreCase("ROSTER")) {
                viewHolder.llName.setBackgroundColor(ContextCompat.getColor(_ctx, R.color.Y));
            } else if (modelsArray.get(position).getSpecial_drive_name_q_3().equalsIgnoreCase("JE/AES ROSTER")) {
                viewHolder.llName.setBackgroundColor(ContextCompat.getColor(_ctx, R.color.Z));
            }
        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, coll_date, tvSampleNumber, tvTypeOfSource, sourceType, tvAppName;
        ImageView imageView;
        LinearLayout llSourceData, llName;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            tvAppName = itemLayoutView.findViewById(R.id.tvAppName);
            tvName = itemLayoutView.findViewById(R.id.tvName);
            tvSampleNumber = itemLayoutView.findViewById(R.id.tvSampleNumber);
            tvTypeOfSource = itemLayoutView.findViewById(R.id.tvTypeOfSource);
            sourceType = itemLayoutView.findViewById(R.id.sourceType);
            coll_date = itemLayoutView.findViewById(R.id.coll_date);
            imageView = itemLayoutView.findViewById(R.id.imageView);
            llSourceData = itemLayoutView.findViewById(R.id.llSourceData);
            llName = itemLayoutView.findViewById(R.id.llName);
        }
    }

    @Override
    public int getItemCount() {
        return modelsArray.size();
    }
}
