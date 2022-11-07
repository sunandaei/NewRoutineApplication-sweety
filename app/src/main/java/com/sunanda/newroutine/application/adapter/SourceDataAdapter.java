package com.sunanda.newroutine.application.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.fragmnet.AssignTask_NA;
import com.sunanda.newroutine.application.modal.ResponseSourceData;
import com.sunanda.newroutine.application.util.PostInterface;

import java.util.ArrayList;

public class SourceDataAdapter extends RecyclerView.Adapter<SourceDataAdapter.ViewHolder> {

    private Context _ctx;
    private boolean isSelectedAll = false;
    private ArrayList<ResponseSourceData> modelsArray;
    private AssignTask_NA.BtnCheck btnCheck;

    public SourceDataAdapter(Context context, ArrayList<ResponseSourceData> d, AssignTask_NA.BtnCheck listener) {
        this.modelsArray = d;
        this._ctx = context;
        this.btnCheck = listener;
    }

    @Override
    public SourceDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.source_data_item, null);

        SourceDataAdapter.ViewHolder viewHolder = new SourceDataAdapter.ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onBindViewHolder(@NonNull final SourceDataAdapter.ViewHolder viewHolder, final int position) {

        viewHolder.cbItem.setTag(position);

        viewHolder.tvName.setText(modelsArray.get(position).getSourceDetails());
        viewHolder.coll_date.setText("Collection Date : " + modelsArray.get(position).getCollectionDate());
        viewHolder.test_date.setText("Test Date : " + modelsArray.get(position).getTestDate());
        if (!TextUtils.isEmpty(modelsArray.get(position).getImgSource()))
            Glide.with(_ctx).load(PostInterface.IMAGEURL_ROUTINE + modelsArray.get(position)
                    .getImgSource() + ".jpg").into(viewHolder.imageView);
        else
            Glide.with(_ctx).load(R.drawable.noimage).into(viewHolder.imageView);

        viewHolder.lat.setText("Latitude : " + modelsArray.get(position).getLatitude());
        viewHolder.lon.setText("Longitude : " + modelsArray.get(position).getLongitude());
        viewHolder.sourceType.setText("Source Type : " + modelsArray.get(position).getWaterSourceType());

        if (position == 0) {
            viewHolder.distance.setText("ORIGIN");
            viewHolder.distance.setTextColor(Color.parseColor("#556B2F"));
        } else {
            viewHolder.distance.setText("Distance : " + String.format("%.2f", modelsArray.get(position).getDistance()));
            viewHolder.distance.setTextColor(Color.parseColor("#00008B"));
        }

        viewHolder.cbItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btnCheck != null)
                    btnCheck.onBtnCheckValue((Integer) viewHolder.cbItem.getTag(), viewHolder.cbItem);
                //notifyDataSetChanged();
            }
        });

        if (!isSelectedAll)
            viewHolder.cbItem.setChecked(false);
        else
            viewHolder.cbItem.setChecked(true);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, coll_date, test_date, lat, lon, sourceType, distance;
        CheckBox cbItem;
        ImageView imageView;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            tvName = itemLayoutView.findViewById(R.id.tvName);
            lat = itemLayoutView.findViewById(R.id.lat);
            lon = itemLayoutView.findViewById(R.id.lon);
            sourceType = itemLayoutView.findViewById(R.id.sourceType);
            distance = itemLayoutView.findViewById(R.id.distance);
            coll_date = itemLayoutView.findViewById(R.id.coll_date);
            test_date = itemLayoutView.findViewById(R.id.test_date);
            cbItem = itemLayoutView.findViewById(R.id.cbItem);
            imageView = itemLayoutView.findViewById(R.id.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return modelsArray.size();
    }

    public void selectAll() {
        isSelectedAll = true;
        notifyDataSetChanged();
    }

    public void unselectall() {
        isSelectedAll = false;
        notifyDataSetChanged();
    }
}
