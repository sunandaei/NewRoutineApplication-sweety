package com.sunanda.newroutine.application.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.modal.CommonModel;
import com.sunanda.newroutine.application.ui.OldSampleCollection_Activity;

import java.util.ArrayList;

public class ExistingLocation_Adapter extends RecyclerView.Adapter<ExistingLocation_Adapter.ViewHolder> {

    private Context _ctx;
    private ArrayList<CommonModel> product_SampleModelsArray;

    public ExistingLocation_Adapter(Context context, ArrayList<CommonModel> d) {
        this.product_SampleModelsArray = d;
        this._ctx = context;

    }

    @Override
    public ExistingLocation_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.existing_location_item, null);

        ExistingLocation_Adapter.ViewHolder viewHolder = new ExistingLocation_Adapter.ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ExistingLocation_Adapter.ViewHolder viewHolder, final int position) {

        viewHolder.item.setText(product_SampleModelsArray.get(position).getSourcelocalityname());

        viewHolder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_ctx, OldSampleCollection_Activity.class);
                _ctx.startActivity(intent);
            }
        });

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView item;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            item = itemLayoutView.findViewById(R.id.item);
        }
    }

    @Override
    public int getItemCount() {
        return product_SampleModelsArray.size();
    }

    public void filterList(ArrayList<CommonModel> filterdNames) {
        this.product_SampleModelsArray = filterdNames;
        notifyDataSetChanged();
    }
}