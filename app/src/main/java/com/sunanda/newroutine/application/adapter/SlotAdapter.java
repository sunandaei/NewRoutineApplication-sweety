package com.sunanda.newroutine.application.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.ui.MapsActivity;

import java.util.ArrayList;


public class SlotAdapter extends RecyclerView.Adapter<SlotAdapter.ViewHolder> {

    private Activity activity;
    private ArrayList itemName;
    private int row_index = 0;
    private TextView textView;
    private MapsActivity.BtnSelect btnSelect;

    public SlotAdapter(ArrayList strings, Activity activity, TextView textView, MapsActivity.BtnSelect listner) {
        this.activity = activity;
        this.itemName = strings;
        btnSelect = listner;
        this.textView = textView;
    }

    @NonNull
    @Override
    public SlotAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.slotitem, parent, false);

        return new SlotAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SlotAdapter.ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {

        String string = itemName.get(position).toString();
        viewHolder.cardView.setTag(position);

        viewHolder.textViewName.setText(string);

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnSelect != null) {
                    btnSelect.onBtnSelectValue((Integer) viewHolder.cardView.getTag());
                    row_index = position;
                    textView.setText(itemName.get(position).toString());
                    notifyDataSetChanged();
                    //Toast.makeText(activity, itemName.get(position).toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (row_index == position) {
            viewHolder.textViewName.setTextColor(Color.parseColor("#FF98DA4A"));
        } else {
            viewHolder.textViewName.setTextColor(Color.parseColor("#000000"));
        }
    }

    @Override
    public int getItemCount() {
        return itemName.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;
        private CardView cardView;

        public ViewHolder(View view) {
            super(view);
            textViewName = itemView.findViewById(R.id.textViewName);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }
}