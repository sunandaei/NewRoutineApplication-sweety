package com.sunanda.newroutine.application.somenath.myadapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.somenath.pojo.LabWorkStatus;
import com.sunanda.newroutine.application.somenath.view.VillageDetails;
import com.sunanda.newroutine.application.util.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


public class CoverUncoverAdapter extends RecyclerView.Adapter<CoverUncoverAdapter.MultiViewHolder> {

    private Context context;
    private ArrayList<LabWorkStatus> labWorkStatusArrayList;
    private JSONArray jsonArray = null;

    public CoverUncoverAdapter(Context context, ArrayList<LabWorkStatus> labWorkStatusArrayList) {
        this.context = context;
        this.labWorkStatusArrayList = labWorkStatusArrayList;
    }

    @NonNull
    @Override
    public MultiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_covered_uncovered, viewGroup, false);
        return new MultiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MultiViewHolder multiViewHolder, int position) {
        multiViewHolder.bind(labWorkStatusArrayList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return labWorkStatusArrayList.size();
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
        void bind(final LabWorkStatus labWorkStatus, final int position) {

            final String villageHab = labWorkStatus.getVillageHab();
            try {
                jsonArray = new JSONArray(villageHab);
                no.setText(String.valueOf(position+1));
                panname.setText("Panchayat : " + labWorkStatus.getPanchayatName() + " (Block-" + labWorkStatus.getBlockName()+ ")");
                totvill.setText("No. Of Villages : " + jsonArray.length());

                int count_touched = 0, count_untouched = 0, c, t, uc;
                for (int j = 0; j < jsonArray.length(); j++) {
                    JSONArray habitation = new JSONArray(jsonArray.getJSONObject(j).getJSONArray("habitation").toString());
                    for (int k = 0; k < habitation.length(); k++) {
                        if (habitation.getJSONObject(k).getString("touched").equalsIgnoreCase("1"))
                            count_touched++;
                        else
                            count_untouched++;
                    }
                }
                t = count_touched + count_untouched;
                c = count_touched;
                uc = count_untouched;
                total.setText("Total Habitations : " + t);
                touched.setText("Coverage Habitations : " + c + " (" + String.format("%.2f", (float) 100 * c / t) + "%)");
                untouched.setText("Untouched Habitations : " + uc + " (" + String.format("%.2f", (float) 100 * uc / t) + "%)");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SessionManager sessionManager = new SessionManager(context);
                    sessionManager.setAllValues(labWorkStatus.getDistCode(), labWorkStatus.getDistName(),
                            labWorkStatus.getBlockCode(), labWorkStatus.getBlockName(),
                            labWorkStatus.getPanCode(), labWorkStatus.getPanchayatName(),
                            labWorkStatus.getLabCode(), labWorkStatus.getLabName());
                    Intent intent = new Intent(context, VillageDetails.class);
                    intent.putExtra("VILLAGE", villageHab);
                    intent.putExtra("PANNAME", labWorkStatus.getPanchayatName());
                    intent.putExtra("TYPE", ((Activity)context).getIntent().getStringExtra("TYPE"));
                    ((Activity)context).overridePendingTransition(R.anim.trans_left_in, R.anim.trans_right_out);
                    context.startActivity(intent);
                }
            });
        }
    }

    public void updateList(ArrayList<LabWorkStatus> list) {
        labWorkStatusArrayList = list;
        notifyDataSetChanged();
    }

    private void showMessage() {
        Snackbar.make(((Activity) context).findViewById(android.R.id.content),
                "\uD83D\uDE0A Water test already completed..... \uD83D\uDC4D", Snackbar.LENGTH_SHORT).show();
    }
}
