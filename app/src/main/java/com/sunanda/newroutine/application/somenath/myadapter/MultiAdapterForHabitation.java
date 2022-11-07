package com.sunanda.newroutine.application.somenath.myadapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.somenath.pojo.HabitationPojo;

import java.util.ArrayList;

public class MultiAdapterForHabitation extends RecyclerView.Adapter<MultiAdapterForHabitation.MultiViewHolder> {

    private Context context;
    private ArrayList<HabitationPojo> habitaions;

    public MultiAdapterForHabitation(Context context, ArrayList<HabitationPojo> employees) {
        this.context = context;
        this.habitaions = employees;
    }

   /* public void setHabitation(ArrayList<HabitationPojo> habitaions) {
        this.habitaions = new ArrayList<>();
        this.habitaions = habitaions;
        notifyDataSetChanged();
    }*/

    @NonNull
    @Override
    public MultiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_habitation, viewGroup, false);
        return new MultiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MultiViewHolder multiViewHolder, int position) {
        multiViewHolder.bind(habitaions.get(position), position);
    }

    @Override
    public int getItemCount() {
        return habitaions.size();
    }

    class MultiViewHolder extends RecyclerView.ViewHolder {

        private TextView textView, no;
        private ImageView imageView;
        private LinearLayout mainItem;

        MultiViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            no = itemView.findViewById(R.id.done_dialog);
            imageView = itemView.findViewById(R.id.imageView);
            mainItem = itemView.findViewById(R.id.mainItem);
        }

        @SuppressLint("SetTextI18n")
        void bind(final HabitationPojo habitationPojo, final int position) {

            no.setText(String.valueOf(position + 1));
            imageView.setVisibility(habitationPojo.isChecked() ? View.VISIBLE : View.GONE);
            textView.setText("Hab Name : " + habitationPojo.getName() + "\n(Total Sources: " + habitationPojo.getXcount() + ")");

            if (habitaions.get(position).getIsCurrentFinancialYearsSource().equalsIgnoreCase("yes")) {
                mainItem.setBackgroundResource(R.drawable.rectangle4);
            } else if (!habitaions.get(position).getAlredyAssign().equalsIgnoreCase("0"))
                mainItem.setBackgroundResource(R.drawable.rectangle5);
            else {
                mainItem.setBackgroundResource(R.drawable.rectangle2);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!habitaions.get(position).getIsCurrentFinancialYearsSource().equalsIgnoreCase("yes")) {
                        habitationPojo.setChecked(!habitationPojo.isChecked());
                        imageView.setVisibility(habitationPojo.isChecked() ? View.VISIBLE : View.GONE);
                    } else {
                        //Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                        //showMessage();
                        if (!habitationPojo.isChecked()) {
                            /*new AlertDialog.Builder(context)
                                    .setTitle("Unable to assign Habitation")
                                    //.setMessage("Are you sure to assign entry in which Water test already completed?")
                                    .setMessage("Water test already completed in the Current Financial Year.....")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            //habitationPojo.setChecked(!habitationPojo.isChecked());
                                            //imageView.setVisibility(habitationPojo.isChecked() ? View.VISIBLE : View.GONE);
                                        }
                                    })
                                    //.setNegativeButton(android.R.string.no, null)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setCancelable(false)
                                    .show();*/
                            new AlertDialog.Builder(context)
                                    .setTitle("Do you want to re-assign Habitation?")
                                    //.setMessage("Are you sure to assign entry in which Water test already completed?")
                                    .setMessage("Water test already completed in the Current Financial Year. " +
                                            "Do you want to re-assign?")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            habitationPojo.setChecked(!habitationPojo.isChecked());
                                            imageView.setVisibility(habitationPojo.isChecked() ? View.VISIBLE : View.GONE);
                                        }
                                    })
                                    .setNegativeButton(android.R.string.no, null)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setCancelable(false)
                                    .show();
                        }else{
                            habitationPojo.setChecked(!habitationPojo.isChecked());
                            imageView.setVisibility(habitationPojo.isChecked() ? View.VISIBLE : View.GONE);
                        }
                    }
                }
            });

            /*itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //showLongMessage();
                    if (!habitaions.get(position).getIsCurrentFinancialYearsSource().equalsIgnoreCase("yes")) {
                        habitationPojo.setChecked(!habitationPojo.isChecked());
                        imageView.setVisibility(habitationPojo.isChecked() ? View.VISIBLE : View.GONE);
                    } else {
                        //Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                        //showMessage();
                        if (!habitationPojo.isChecked()) {
                            new AlertDialog.Builder(context)
                                    .setTitle("Do you want to re-assign Habitation?")
                                    //.setMessage("Are you sure to assign entry in which Water test already completed?")
                                    .setMessage("Water test already completed in the Current Financial Year. " +
                                            "Do you want to re-assign?")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            habitationPojo.setChecked(!habitationPojo.isChecked());
                                            imageView.setVisibility(habitationPojo.isChecked() ? View.VISIBLE : View.GONE);
                                        }
                                    })
                                    .setNegativeButton(android.R.string.no, null)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setCancelable(false)
                                    .show();
                        } else {
                            habitationPojo.setChecked(!habitationPojo.isChecked());
                            imageView.setVisibility(habitationPojo.isChecked() ? View.VISIBLE : View.GONE);
                        }
                    }
                    return true;
                }
            });*/
        }
    }

    private void showMessage() {
        Snackbar.make(((Activity) context).findViewById(android.R.id.content),
                "\uD83D\uDE0A Water test already completed..... \uD83D\uDC4D", Snackbar.LENGTH_SHORT).show();
    }

    public ArrayList<HabitationPojo> getAll() {
        return habitaions;
    }

    public ArrayList<HabitationPojo> getSelected() {
        ArrayList<HabitationPojo> selected = new ArrayList<>();
        for (int i = 0; i < habitaions.size(); i++) {
            if (habitaions.get(i).isChecked()) {
                selected.add(habitaions.get(i));
            }
        }
        return selected;
    }
    // https://gist.github.com/SalaSuresh/5173023eb1c18688652a4768ad047850

    /*
    private void createList() {
        habitaions = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Employee employee = new Employee();
            employee.setName("Employee " + (i + 1));
            habitaions.add(employee);
        }
        adapter.setEmployees(habitaions);
    }
    */
}
