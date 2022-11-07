package com.sunanda.newroutine.application.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.modal.CommonModel;
import com.sunanda.newroutine.application.somenath.view.FacilitatorAssignedList_Activity;
import com.sunanda.newroutine.application.util.View_Holder;

import java.util.ArrayList;

public class FacilitatorList_Adapter extends RecyclerView.Adapter<View_Holder> {

    ArrayList<CommonModel> commonModelArrayList;
    Context context;

    public FacilitatorList_Adapter(ArrayList<CommonModel> commonModelArray, Context context) {
        this.commonModelArrayList = commonModelArray;
        this.context = context;
    }

    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.facilitator_list_item, parent, false);
        View_Holder holder = new View_Holder(v);
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(View_Holder holder, final int position) {
        holder.title.setText(commonModelArrayList.get(position).getName());

        holder.tvShow.setText("VIEW");
        holder.description.setText(commonModelArrayList.get(position).getEmail()
                + "\n\nPassword : " + commonModelArrayList.get(position).getPassword());

        holder.tvShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FacilitatorAssignedList_Activity.class);
                intent.putExtra("ALLDATA", commonModelArrayList.get(position));
                //context.startActivity(intent);
                ((Activity)context).startActivityForResult(intent, 2);
            }
        });

        //animate(holder);
    }

    @Override
    public int getItemCount() {
        return commonModelArrayList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Insert a new item to the RecyclerView
    public void insert(int position, CommonModel commonModel) {
        commonModelArrayList.add(position, commonModel);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing the Data object
    public void remove(CommonModel commonModel) {
        int position = commonModelArrayList.indexOf(commonModel);
        commonModelArrayList.remove(position);
        notifyItemRemoved(position);
    }

    public void animate(RecyclerView.ViewHolder viewHolder) {
        final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(context, R.anim.anticipate_overshoot_interpolator);
        viewHolder.itemView.setAnimation(animAnticipateOvershoot);
    }

    public void updateList(ArrayList<CommonModel> list){
        commonModelArrayList = list;
        notifyDataSetChanged();
    }
}