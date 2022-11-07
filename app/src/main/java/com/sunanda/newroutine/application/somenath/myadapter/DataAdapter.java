package com.sunanda.newroutine.application.somenath.myadapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sunanda.newroutine.application.R;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ListViewHolder> {

    private final String[] mDataset;
    private final RecyclerViewItemClickListener recyclerViewItemClickListener;

    public DataAdapter(String[] myDataset, RecyclerViewItemClickListener listener) {
        mDataset = myDataset;
        this.recyclerViewItemClickListener = listener;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new ListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder fruitViewHolder, int i) {
        fruitViewHolder.mTextView.setText(mDataset[i]);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTextView;

        ListViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.textView);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            recyclerViewItemClickListener.clickOnItem(mDataset[this.getAdapterPosition()]);
        }
    }

    public interface RecyclerViewItemClickListener {
        void clickOnItem(String data);
    }
}
