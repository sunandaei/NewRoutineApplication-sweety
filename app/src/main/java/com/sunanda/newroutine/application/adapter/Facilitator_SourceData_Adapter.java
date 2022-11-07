package com.sunanda.newroutine.application.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.database.DatabaseHandler;
import com.sunanda.newroutine.application.modal.CommonModel;
import com.sunanda.newroutine.application.ui.OldSampleCollection_Activity;
import com.sunanda.newroutine.application.util.CGlobal;
import com.sunanda.newroutine.application.util.Constants;

import java.util.ArrayList;

public class Facilitator_SourceData_Adapter extends RecyclerView.Adapter<Facilitator_SourceData_Adapter.ViewHolder> implements Filterable {

    private Context _ctx;
    private ArrayList<CommonModel> modelsArray;
    private ArrayList<CommonModel> mData;
    String url = "", sLocationDName = "";
    ValueFilter valueFilter;
    String sLD = "";

    public Facilitator_SourceData_Adapter(Context context, ArrayList<CommonModel> d) {
        this.modelsArray = d;
        this.mData = d;
        this._ctx = context;
    }

    @Override
    public Facilitator_SourceData_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.facilitator_source_data_item, null);

        Facilitator_SourceData_Adapter.ViewHolder viewHolder = new Facilitator_SourceData_Adapter.ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onBindViewHolder(@NonNull final Facilitator_SourceData_Adapter.ViewHolder viewHolder, final int position) {

        if (TextUtils.isEmpty(modelsArray.get(position).getExisting_mid())) {
            if (modelsArray.get(position).getApp_name().equalsIgnoreCase("School")) {
                viewHolder.tvName.setText(modelsArray.get(position).getLocationDescription());
            } else {
                if (modelsArray.get(position).getIsnewlocation_School_UdiseCode().equalsIgnoreCase("No")) {
                    viewHolder.tvName.setText(modelsArray.get(position).getDescriptionofthelocation());
                } else {
                    viewHolder.tvName.setText(modelsArray.get(position).getLocationDescription());
                }
            }
        } else {
            if (modelsArray.get(position).getApp_name().equalsIgnoreCase("School")) {
                viewHolder.tvName.setText(modelsArray.get(position).getLocationDescription());
            } else {
                if (modelsArray.get(position).getIsnewlocation_School_UdiseCode().equalsIgnoreCase("No")) {
                    viewHolder.tvName.setText(modelsArray.get(position).getDescriptionofthelocation());
                } else {
                    viewHolder.tvName.setText(modelsArray.get(position).getLocationDescription());
                }
            }
        }

        viewHolder.tvNumber.setText("#" + String.valueOf(position + 1));

        viewHolder.tvAppName.setText(Html.fromHtml("Source: <b>" + modelsArray.get(position).getApp_name() + "</b>"));

        viewHolder.coll_date.setText(Html.fromHtml("Last Collection: <b>" + modelsArray.get(position).getCollection_date() + "</b>"));

        viewHolder.test_date.setText(Html.fromHtml("Last Test: <b>" + modelsArray.get(position).getCollection_date() + "</b>"));

        DatabaseHandler databaseHandler = new DatabaseHandler(_ctx);
        viewHolder.tvSampleBottleNo.setText(Html.fromHtml("Sample Bottle Number: <b>" + modelsArray.get(position).getSample_bott_num() + "</b>"));

        viewHolder.tvTypeOfSource.setText(Html.fromHtml("Type of Source: <b>" + modelsArray.get(position).getType_of_locality() + "</b>"));

        viewHolder.sourceType.setText(Html.fromHtml("Source Type: <b>" + modelsArray.get(position).getWater_source_type() + "</b>"));

        try {
            if (!TextUtils.isEmpty(modelsArray.get(position).getImg_source())) {
                if (modelsArray.get(position).getImg_source().contains(".")) {
                    if (modelsArray.get(position).getApp_name().equalsIgnoreCase("Routine")) {
                        url = "https://sunanda-images.s3.ap-south-1.amazonaws.com/Routine/" + modelsArray.get(position).getImg_source();
                    } else if (modelsArray.get(position).getApp_name().equalsIgnoreCase("School")) {
                        url = "https://sunanda-images.s3.ap-south-1.amazonaws.com/School/" + modelsArray.get(position).getImg_source();
                    } else {
                        url = "http://images.sunandainternational.org:8088/Omas/" + modelsArray.get(position).getImg_source();
                    }
                } else {
                    if (modelsArray.get(position).getApp_name().equalsIgnoreCase("Routine")) {
                        url = "https://sunanda-images.s3.ap-south-1.amazonaws.com/Routine/" + modelsArray.get(position).getImg_source() + "jpg";
                    } else if (modelsArray.get(position).getApp_name().equalsIgnoreCase("School")) {
                        url = "https://sunanda-images.s3.ap-south-1.amazonaws.com/School/" + modelsArray.get(position).getImg_source() + "jpg";
                    } else {
                        url = "http://images.sunandainternational.org:8088/Omas/" + modelsArray.get(position).getImg_source() + "jpg";
                    }
                }

                if (position < 30) {
                    Glide.with(_ctx).load(url).into(viewHolder.imageView);
                }
            } else {
                Glide.with(_ctx).load(R.drawable.no_image).into(viewHolder.imageView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        viewHolder.llSourceData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String json = new Gson().toJson(modelsArray.get(position));
                CGlobal.getInstance().getPersistentPreferenceEditor(_ctx).putString(Constants.PREFS_SOURCE_SAMPLE_DETAILS, json).commit();
                Intent intent = new Intent(_ctx, OldSampleCollection_Activity.class);
                _ctx.startActivity(intent);
            }
        });


        if (modelsArray.get(position).getSpecial_drive().equalsIgnoreCase("Yes")) {
            if (modelsArray.get(position).getName_of_special_drive().equalsIgnoreCase("ARSENIC TREND STATION")) {
                if (modelsArray.get(position).getIsnewlocation_School_UdiseCode().equalsIgnoreCase("No")) {
                    sLD = modelsArray.get(position).getDescriptionofthelocation();
                } else {
                    sLD = modelsArray.get(position).getLocationDescription();
                }
                String sATS = databaseHandler.getArsenicName(sLD);
                if (sATS.equalsIgnoreCase(sLD)) {
                    viewHolder.llName.setBackgroundColor(ContextCompat.getColor(_ctx, R.color.x));
                }
            } else if (modelsArray.get(position).getName_of_special_drive().equalsIgnoreCase("ROSTER")) {
                viewHolder.llName.setBackgroundColor(ContextCompat.getColor(_ctx, R.color.Y));
            } else if (modelsArray.get(position).getName_of_special_drive().equalsIgnoreCase("JE/AES ROSTER")) {
                viewHolder.llName.setBackgroundColor(ContextCompat.getColor(_ctx, R.color.Z));
            }
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, coll_date, test_date, tvSampleBottleNo, tvTypeOfSource, sourceType, tvAppName, tvNumber;
        ImageView imageView;
        LinearLayout llSourceData, llName;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            tvName = itemLayoutView.findViewById(R.id.tvName);
            tvSampleBottleNo = itemLayoutView.findViewById(R.id.tvSampleBottleNo);
            tvTypeOfSource = itemLayoutView.findViewById(R.id.tvTypeOfSource);
            sourceType = itemLayoutView.findViewById(R.id.sourceType);
            coll_date = itemLayoutView.findViewById(R.id.coll_date);
            test_date = itemLayoutView.findViewById(R.id.test_date);
            imageView = itemLayoutView.findViewById(R.id.imageView);
            llSourceData = itemLayoutView.findViewById(R.id.llSourceData);
            llName = itemLayoutView.findViewById(R.id.llName);
            tvAppName = itemLayoutView.findViewById(R.id.tvAppName);
            tvNumber = itemLayoutView.findViewById(R.id.tvNumber);
        }
    }

    @Override
    public int getItemCount() {
        return modelsArray.size();
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                ArrayList<CommonModel> filterList = new ArrayList<>();
                for (int i = 0; i < mData.size(); i++) {

                    if (TextUtils.isEmpty(mData.get(i).getExisting_mid())) {
                        if (mData.get(i).getApp_name().equalsIgnoreCase("School")) {
                            sLocationDName = mData.get(i).getLocationDescription();
                        } else {
                            if (mData.get(i).getIsnewlocation_School_UdiseCode().equalsIgnoreCase("No")) {
                                sLocationDName = mData.get(i).getDescriptionofthelocation();
                            } else {
                                sLocationDName = mData.get(i).getLocationDescription();
                            }
                        }
                    } else {
                        if (mData.get(i).getApp_name().equalsIgnoreCase("School")) {
                            sLocationDName = mData.get(i).getLocationDescription();
                        } else {
                            if (mData.get(i).getIsnewlocation_School_UdiseCode().equalsIgnoreCase("No")) {
                                sLocationDName = mData.get(i).getDescriptionofthelocation();
                            } else {
                                sLocationDName = mData.get(i).getLocationDescription();
                            }
                        }
                    }


                    if ((sLocationDName.toUpperCase()).contains(constraint.toString().toUpperCase())) {
                        filterList.add(mData.get(i));
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mData.size();
                results.values = mData;
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            modelsArray = (ArrayList<CommonModel>) results.values;
            notifyDataSetChanged();
        }

    }
}
