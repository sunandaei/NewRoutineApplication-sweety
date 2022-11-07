package com.sunanda.newroutine.application.somenath.myadapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.somenath.pojo.SourceByHabPojo;
import com.sunanda.newroutine.application.util.PostInterface;

import java.util.ArrayList;

public class Habitation_SourceData_Adapter extends RecyclerView.Adapter<Habitation_SourceData_Adapter.ViewHolder> {

    private Context _ctx;
    private ArrayList<SourceByHabPojo> modelsArray;

    public Habitation_SourceData_Adapter(Context context, ArrayList<SourceByHabPojo> d) {
        this.modelsArray = d;
        this._ctx = context;
    }

    @Override
    public Habitation_SourceData_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.habitation_source_data_item, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {

        if (modelsArray.get(position).getIsnewlocation().equalsIgnoreCase("NO")) {
            viewHolder.tvName.setText(modelsArray.get(position).getDescriptionofthelocation());
        } else {
            viewHolder.tvName.setText(modelsArray.get(position).getLocationDescription());
        }

        viewHolder.number.setText(String.valueOf(position + 1));

        viewHolder.test_date.setText("Last Test : " + modelsArray.get(position).getTimeofDataCollection());
        /*if (!TextUtils.isEmpty(modelsArray.get(position).getImg_source())) {
            Glide.with(_ctx).load(PostInterface.IMAGEURL + modelsArray.get(position)
                    .getImg_source() + ".jpg").into(viewHolder.imageView);
        } else {
            Glide.with(_ctx).load(R.drawable.no_image).into(viewHolder.imageView);
        }*/

        viewHolder.tvDistBlock.setText("Location : " + modelsArray.get(position).getDistrict() + " -> "
                + modelsArray.get(position).getBlock());
        viewHolder.tvPanVillHab.setText(("-> " + modelsArray.get(position).getPanchayat()) + " -> "
                + modelsArray.get(position).getVillageName() + " -> " + modelsArray.get(position).getHabitation());
        viewHolder.sourceType.setText("Source Type : " + modelsArray.get(position).getWaterSourceType());

        if (!TextUtils.isEmpty(modelsArray.get(position).getImgSource())) {
            String url = PostInterface.IMAGEURL_ROUTINE + modelsArray.get(position).getImgSource();
            Glide.with(_ctx).load(url).placeholder(R.drawable.no_image).into(viewHolder.imageView);
        } else {
            Glide.with((Activity) _ctx).load(R.drawable.no_image).into(viewHolder.imageView);
        }

        /*viewHolder.llSourceData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String json = new Gson().toJson(modelsArray.get(position));
                CGlobal.getInstance().getPersistentPreferenceEditor(_ctx).putString(Constants.PREFS_SOURCE_SAMPLE_DETAILS, json).commit();
                Intent intent = new Intent(_ctx, OldSampleCollection_Activity.class);
                _ctx.startActivity(intent);
            }
        });*/

        viewHolder.ivMap.setVisibility(View.GONE);

        if (modelsArray.get(position).getIsnewlocation().equalsIgnoreCase("yes"))
            viewHolder.upperLayout.setBackgroundColor(_ctx.getResources().getColor(R.color.Green));

        if (modelsArray.get(position).isTested()) {
            viewHolder.coll_date.setText("New Collection : " +
                    modelsArray.get(position).getDateofDataCollection().replace("T", " ").split(".")[0]);
            viewHolder.upperLayout.setBackgroundColor(_ctx.getResources().getColor(R.color.Green));
            String url = PostInterface.IMAGEURL_ROUTINE + modelsArray.get(position).getImgSource();
            Glide.with(_ctx).load(url).placeholder(R.drawable.no_image).into(viewHolder.imageView);
        } else {
            //viewHolder.upperLayout.setBackgroundColor(_ctx.getResources().getColor(R.color.Red));
            viewHolder.coll_date.setText("Last Collected : " +
                    modelsArray.get(position).getDateofDataCollection().replace("T", " At ")
                            .split("\\.")[0]);
        }

        viewHolder.bottleNum.setText("Sample Bottle No. : " + modelsArray.get(position).getSampleBottleNumber());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, coll_date, test_date, tvDistBlock, tvPanVillHab, sourceType, distance, bottleNum, number;
        ImageView imageView, ivMap;
        LinearLayout llSourceData, upperLayout;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            tvName = itemLayoutView.findViewById(R.id.tvName);
            number = itemLayoutView.findViewById(R.id.number);
            bottleNum = itemLayoutView.findViewById(R.id.bottleNum);
            tvDistBlock = itemLayoutView.findViewById(R.id.tvDistBlock);
            tvPanVillHab = itemLayoutView.findViewById(R.id.tvPanVillHab);
            sourceType = itemLayoutView.findViewById(R.id.sourceType);
            distance = itemLayoutView.findViewById(R.id.distance);
            coll_date = itemLayoutView.findViewById(R.id.coll_date);
            test_date = itemLayoutView.findViewById(R.id.test_date);
            imageView = itemLayoutView.findViewById(R.id.imageView);
            ivMap = itemLayoutView.findViewById(R.id.ivMap);
            llSourceData = itemLayoutView.findViewById(R.id.llSourceData);
            upperLayout = itemLayoutView.findViewById(R.id.upperLayout);
        }
    }

    @Override
    public int getItemCount() {
        return modelsArray.size();
    }

}

