package com.sunanda.newroutine.application.somenath.myadapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.fragmnet.FCWOrkAreaFragment;
import com.sunanda.newroutine.application.somenath.pojo.ResponsePanchyat;

import java.util.ArrayList;


public class NewBlockAdapter extends RecyclerView.Adapter<NewBlockAdapter.ViewHolder> {

    private Activity activity;
    private ArrayList itemName;
    private ArrayList stringsCodes;
    private TextView txtPan;
    private ArrayList<ResponsePanchyat> responsePanArrayList;
    private int row_index = 0;
    private FCWOrkAreaFragment.BtnSelect btnSelect;

    public NewBlockAdapter(ArrayList strings, ArrayList stringsCodes, ArrayList<ResponsePanchyat> responsePanArrayList,
                           Activity activity, TextView txtPan, FCWOrkAreaFragment.BtnSelect listner) {
        this.activity = activity;
        this.itemName = strings;
        this.stringsCodes = stringsCodes;
        this.txtPan = txtPan;
        this.responsePanArrayList = responsePanArrayList;
        btnSelect = listner;
    }

    @NonNull
    @Override
    public NewBlockAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.slotitem, parent, false);

        return new NewBlockAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final NewBlockAdapter.ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {

        String string = itemName.get(position).toString();
        viewHolder.cardView.setTag(position);

        viewHolder.textViewName.setText((position + 1) + ". " + string);

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnSelect != null) {
                    btnSelect.onBtnSelectValue((Integer) viewHolder.cardView.getTag());
                    row_index = position;
                    notifyDataSetChanged();
                    //Toast.makeText(activity, itemName.get(position).toString(), Toast.LENGTH_SHORT).show();
                    getpanchayat(position);
                }
            }
        });

        if (row_index == 0)
            getpanchayat(0);

        if (row_index == position) {
            viewHolder.textViewName.setTextColor(Color.parseColor("#006400"));
            viewHolder.textViewName.setTypeface(null, Typeface.BOLD);
        } else {
            viewHolder.textViewName.setTextColor(Color.parseColor("#000000"));
            viewHolder.textViewName.setTypeface(null, Typeface.NORMAL);
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

    @SuppressLint("SetTextI18n")
    private void getpanchayat(int position) {

        //Log.d("TEST", stringsCodes.get(position).toString());
        if (!TextUtils.isEmpty(stringsCodes.get(position).toString())) {
            String tmp = "";
            txtPan.setText("");
            for (int a = 0, b = 1; a < responsePanArrayList.size(); a++) {
                if (stringsCodes.get(position).toString().equalsIgnoreCase(responsePanArrayList.get(a).getBlockCode())) {
                    //Log.d("TEST", responsePanArrayList.get(a).getPanchayatName());
                    tmp += (b++) + "• " + responsePanArrayList.get(a).getPanchayatName() + "\n";
                    txtPan.setText(tmp);
                }
            }
        }
    }
}
