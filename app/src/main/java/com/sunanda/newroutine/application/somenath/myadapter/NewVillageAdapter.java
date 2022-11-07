package com.sunanda.newroutine.application.somenath.myadapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.somenath.pojo.ResponseVillage;

import java.util.ArrayList;

public class NewVillageAdapter extends RecyclerView.Adapter<NewVillageAdapter.ListViewHolder> {

    private Context context;
    private ArrayList<ResponseVillage> mDataset;
    private int checkedPosition = -1;
    private NewVillageAdapter.RecyclerViewItemClickListener recyclerViewItemClickListener;

    public NewVillageAdapter(Context context, ArrayList<ResponseVillage> myDataset,
                             NewVillageAdapter.RecyclerViewItemClickListener listener) {
        this.context = context;
        mDataset = myDataset;
        this.recyclerViewItemClickListener = listener;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new ListViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull NewVillageAdapter.ListViewHolder myViewHolder, int i) {

        if (checkedPosition == -1) {
            myViewHolder.imageView.setVisibility(View.GONE);
        } else {
            if (checkedPosition == i) {
                if (mDataset.get(i).isSelected()) {
                    myViewHolder.imageView.setVisibility(View.GONE);
                    mDataset.get(i).setSelected(false);
                } else {
                    myViewHolder.imageView.setVisibility(View.VISIBLE);
                    mDataset.get(i).setSelected(true);
                }
            } else {
                myViewHolder.imageView.setVisibility(View.GONE);
                mDataset.get(i).setSelected(false);
            }
        }

        //if (mDataset.get(i).getTotalHab().equalsIgnoreCase(mDataset.get(i).getTouchedCurrentYear()))
        if (!TextUtils.isEmpty(mDataset.get(i).getTouchedCurrentYear())) {
            int iTouchedCurrentYear = Integer.parseInt(mDataset.get(i).getTouchedCurrentYear());
            if (iTouchedCurrentYear >= 1)
                myViewHolder.card_view.setBackgroundResource(R.drawable.rectangle5);
            else
                myViewHolder.card_view.setBackgroundResource(R.drawable.rectangle2);
        }

        myViewHolder.mTextView.setText(mDataset.get(i).getVillName());
        myViewHolder.textView2.setText("Total Hab : " + mDataset.get(i).getTotalHab() +
                "  Covered Hab : " + mDataset.get(i).getTouchedCurrentYear());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTextView, textView2;
        ImageView imageView;
        LinearLayout card_view;

        ListViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.textView);
            textView2 = v.findViewById(R.id.textView2);
            imageView = v.findViewById(R.id.imageView);
            card_view = v.findViewById(R.id.card_view);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mDataset.get(getAdapterPosition()).getTotalHab()
                    .equalsIgnoreCase(mDataset.get(getAdapterPosition()).getTouchedCurrentYear())) {
                new AlertDialog.Builder(context)
                        //.setTitle("Unable to assign")
                        /*.setMessage("Total Habitation in this Village is already covered in current financial year. " +
                                "Please choose different Village to assign.")*/
                        .setMessage("Total Habitation in this Village is already covered in current financial year. " +
                                "Do you still want to proceed?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                recyclerViewItemClickListener.clickOnItem(mDataset.get(getAdapterPosition()));
                                imageView.setVisibility(View.VISIBLE);
                                if (checkedPosition != getAdapterPosition()) {
                                    notifyItemChanged(checkedPosition);
                                    checkedPosition = getAdapterPosition();
                                }
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setCancelable(false)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            } else {
                recyclerViewItemClickListener.clickOnItem(mDataset.get(getAdapterPosition()));
                imageView.setVisibility(View.VISIBLE);
                if (checkedPosition != getAdapterPosition()) {
                    notifyItemChanged(checkedPosition);
                    checkedPosition = getAdapterPosition();
                }
            }
        }
    }

    public interface RecyclerViewItemClickListener {
        void clickOnItem(ResponseVillage data);
    }
}
