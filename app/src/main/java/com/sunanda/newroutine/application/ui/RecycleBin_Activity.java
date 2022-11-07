package com.sunanda.newroutine.application.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.adapter.RecycleBin_Adapter;
import com.sunanda.newroutine.application.database.DatabaseHandler;
import com.sunanda.newroutine.application.modal.SampleModel;
import com.sunanda.newroutine.application.util.CGlobal;
import com.sunanda.newroutine.application.util.Constants;

import java.util.ArrayList;

public class RecycleBin_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_bin_activity);
        init();
    }

    RecyclerView rvRecycleBin;
    ArrayList<SampleModel> sampleModelsDataCollection;
    ArrayList<String> storeCheckbox = new ArrayList<>();
    Button btnRestore, btnDeleteData;
    RecycleBin_Adapter adapter;

    private void init() {

        rvRecycleBin = findViewById(R.id.rvRecycleBin);

        btnRestore = findViewById(R.id.btnRestore);
        btnDeleteData = findViewById(R.id.btnDeleteData);

        DatabaseHandler databaseHandler = new DatabaseHandler(RecycleBin_Activity.this);
        sampleModelsDataCollection = new ArrayList<>();

        String sFCID = CGlobal.getInstance().getPersistentPreference(RecycleBin_Activity.this)
                .getString(Constants.PREFS_USER_FACILITATOR_ID, "");

        sampleModelsDataCollection = databaseHandler.getRecycleBin(sFCID, "Routine");

        adapter = new RecycleBin_Adapter(RecycleBin_Activity.this, sampleModelsDataCollection, new BtnRestore() {
            @Override
            public void onBtnRestoreValue(final int position, int id, CheckBox cbRestore) {
                if (cbRestore.isChecked()) {
                    storeCheckbox.add(String.valueOf(id));
                } else {
                    storeCheckbox.remove(String.valueOf(id));
                }
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(RecycleBin_Activity.this);
        rvRecycleBin.setLayoutManager(mLayoutManager);
        rvRecycleBin.setItemAnimator(new DefaultItemAnimator());
        rvRecycleBin.setAdapter(adapter);

        btnRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < storeCheckbox.size(); i++) {
                    DatabaseHandler databaseHandler = new DatabaseHandler(RecycleBin_Activity.this);
                    databaseHandler.UpdateRecycleBin(Integer.parseInt(storeCheckbox.get(i)), "0");
                }
                adapter.notifyDataSetChanged();
                showData();
                new AlertDialog.Builder(RecycleBin_Activity.this)
                        .setMessage("Successfully Restore")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        });

        btnDeleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(RecycleBin_Activity.this)
                        .setTitle("Delete")
                        .setMessage("Do you want to Delete permanently?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                for (int i = 0; i < storeCheckbox.size(); i++) {
                                    DatabaseHandler databaseHandler = new DatabaseHandler(RecycleBin_Activity.this);
                                    databaseHandler.deleteSampleCollection(Integer.parseInt(storeCheckbox.get(i)));
                                }
                                adapter.notifyDataSetChanged();
                                showData();
                                new AlertDialog.Builder(RecycleBin_Activity.this)
                                        .setMessage("Successfully Delete")
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        }).show();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();

            }
        });

    }

    private void showData() {
        DatabaseHandler databaseHandler = new DatabaseHandler(RecycleBin_Activity.this);
        sampleModelsDataCollection = new ArrayList<>();

        String sFCID = CGlobal.getInstance().getPersistentPreference(RecycleBin_Activity.this)
                .getString(Constants.PREFS_USER_FACILITATOR_ID, "");
        sampleModelsDataCollection = databaseHandler.getRecycleBin(sFCID, "Routine");

        adapter = new RecycleBin_Adapter(RecycleBin_Activity.this, sampleModelsDataCollection, new BtnRestore() {
            @Override
            public void onBtnRestoreValue(final int position, int id, CheckBox cbRestore) {
                if (cbRestore.isChecked()) {
                    storeCheckbox.add(String.valueOf(id));
                } else {
                    storeCheckbox.remove(String.valueOf(id));
                }
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(RecycleBin_Activity.this);
        rvRecycleBin.setLayoutManager(mLayoutManager);
        rvRecycleBin.setItemAnimator(new DefaultItemAnimator());
        rvRecycleBin.setAdapter(adapter);
    }

    public interface BtnRestore {
        public abstract void onBtnRestoreValue(int position, int id, CheckBox cbRestore);
    }
}
