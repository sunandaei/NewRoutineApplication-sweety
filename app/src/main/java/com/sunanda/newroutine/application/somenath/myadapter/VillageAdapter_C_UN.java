package com.sunanda.newroutine.application.somenath.myadapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.somenath.pojo.VillageCovUnCovPojo;
import com.sunanda.newroutine.application.somenath.view.HabitationDetails;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


public class VillageAdapter_C_UN extends RecyclerView.Adapter<VillageAdapter_C_UN.MultiViewHolder> {

    private Context context;
    private ArrayList<VillageCovUnCovPojo> villageCovUnCovPojoArrayList;
    private JSONArray jsonArray = null;

    public VillageAdapter_C_UN(Context context, ArrayList<VillageCovUnCovPojo> labWorkStatusArrayList) {
        this.context = context;
        this.villageCovUnCovPojoArrayList = labWorkStatusArrayList;
    }

    @NonNull
    @Override
    public MultiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_covered_uncovered, viewGroup, false);
        return new MultiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MultiViewHolder multiViewHolder, int position) {
        multiViewHolder.bind(villageCovUnCovPojoArrayList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return villageCovUnCovPojoArrayList.size();
    }

    class MultiViewHolder extends RecyclerView.ViewHolder {

        private TextView panname, totvill, no, touched, untouched, total;

        MultiViewHolder(@NonNull View itemView) {
            super(itemView);
            panname = itemView.findViewById(R.id.panname);
            totvill = itemView.findViewById(R.id.totvill);
            no = itemView.findViewById(R.id.done_dialog);
            touched = itemView.findViewById(R.id.touched);
            untouched = itemView.findViewById(R.id.untouched);
            total = itemView.findViewById(R.id.total);
        }

        @SuppressLint("SetTextI18n")
        void bind(final VillageCovUnCovPojo villageCovUnCovPojo, final int position) {

            final String habitation = villageCovUnCovPojo.getHabitation();
            try {
                jsonArray = new JSONArray(habitation);
                no.setText(String.valueOf(position + 1));
                panname.setText("Village Name : " + villageCovUnCovPojo.getVillName());
                totvill.setText("No. Of Habitation : " + jsonArray.length());
                totvill.setVisibility(View.GONE);

                int count_touched = 0, count_untouched = 0, c, t, uc;
                for (int k = 0; k < jsonArray.length(); k++) {
                    if (jsonArray.getJSONObject(k).getString("touched").equalsIgnoreCase("1"))
                        count_touched++;
                    else
                        count_untouched++;
                }
                t = count_touched + count_untouched;
                c = count_touched;
                uc = count_untouched;
                total.setText("Total Habitations : " + t);
                if (t == 0) {
                    touched.setText("Coverage Habitations : NA");
                    untouched.setText("Untouched Habitations : NA");
                } else {
                    touched.setText("Coverage Habitations : " + c + " (" + String.format("%.2f", (float) 100 * c / t) + "%)");
                    untouched.setText("Untouched Habitations : " + uc + " (" + String.format("%.2f", (float) 100 * uc / t) + "%)");
                }

                if (c >= 1) {
                    panname.setTextColor(Color.parseColor("#228B22"));
                    //panname.setText("✓ Village Name : " + villageCovUnCovPojo.getVillName());
                    final int sdk = android.os.Build.VERSION.SDK_INT;
                    if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        itemView.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.rectangle5));
                    } else {
                        itemView.setBackground(ContextCompat.getDrawable(context, R.drawable.rectangle5));
                    }
                } else {
                    panname.setTextColor(Color.parseColor("#000000"));
                    final int sdk = android.os.Build.VERSION.SDK_INT;
                    if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        itemView.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.rectangle2));
                    } else {
                        itemView.setBackground(ContextCompat.getDrawable(context, R.drawable.rectangle2));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, HabitationDetails.class);
                    intent.putExtra("HABITATION", habitation);
                    intent.putExtra("VILLNAME", villageCovUnCovPojo.getVillName());
                    intent.putExtra("TYPE", ((Activity) context).getIntent().getStringExtra("TYPE"));
                    ((Activity) context).overridePendingTransition(R.anim.trans_left_in, R.anim.trans_right_out);
                    context.startActivity(intent);
                }
            });
        }
    }

    public void updateList(ArrayList<VillageCovUnCovPojo> list) {
        villageCovUnCovPojoArrayList = list;
        notifyDataSetChanged();
    }

    private void showMessage() {
        Snackbar.make(((Activity) context).findViewById(android.R.id.content),
                "\uD83D\uDE0A Water test already completed..... \uD83D\uDC4D", Snackbar.LENGTH_SHORT).show();
    }
}

