package com.sunanda.newroutine.application.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.adapter.Block_Adapter;
import com.sunanda.newroutine.application.database.DatabaseHandler;
import com.sunanda.newroutine.application.modal.CommonModel;
import com.sunanda.newroutine.application.util.CGlobal;
import com.sunanda.newroutine.application.util.Constants;

import java.util.ArrayList;

public class SelectFacilitator_Activity extends AppCompatActivity {

    ArrayList<CommonModel> cmaBlock = new ArrayList<>();
    ArrayList<CommonModel> cmaPanchayat = new ArrayList<>();
    Spinner spBlock;
    DatabaseHandler databaseHandler;
    String sBlockName, sBlockCode, sPanchayatName, sPanchayatCode;
    TextView tvLabName, tvDistrictName, tvPanchayat;
    Button btnSubmit;
    AlertDialog.Builder alertdialogbuilder;
    boolean[] selectedtruefalse;
    String[] alertDialogItems;
    String name = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_facilitator_activity);

        spBlock = findViewById(R.id.spBlock);

        tvPanchayat = findViewById(R.id.tvPanchayat);

        tvLabName = findViewById(R.id.tvLabName);
        tvDistrictName = findViewById(R.id.tvDistrictName);

        databaseHandler = new DatabaseHandler(SelectFacilitator_Activity.this);

        tvPanchayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(sBlockCode)) {

                    selectedtruefalse = new boolean[]{};
                    alertDialogItems = new String[]{};
                    tvPanchayat.setText("");
                    alertdialogbuilder = new AlertDialog.Builder(SelectFacilitator_Activity.this);
                    //cmaPanchayat = databaseHandler.getPanchayatList(sBlockCode);
                    selectedtruefalse = new boolean[cmaPanchayat.size()];
                    alertDialogItems = new String[cmaPanchayat.size()];
                    for (int a = 0; a < cmaPanchayat.size(); a++) {
                        selectedtruefalse[a] = false;
                        alertDialogItems[a] = cmaPanchayat.get(a).getPanchayatname();
                    }
                    alertdialogbuilder.setMultiChoiceItems(alertDialogItems, selectedtruefalse, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                        }
                    });
                    alertdialogbuilder.setCancelable(false);
                    alertdialogbuilder.setTitle("Select Subjects Here");
                    alertdialogbuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int a = 0;
                            while (a < selectedtruefalse.length) {
                                boolean value = selectedtruefalse[a];
                                if (value) {
                                    String name1 = cmaPanchayat.get(a).getPanchayatname();
                                    if (!TextUtils.isEmpty(name)) {
                                        name = name + "," + name1;
                                    } else {
                                        name = name1;
                                    }
                                }
                                a++;
                            }
                            tvPanchayat.setText(name);
                        }
                    });

                    alertdialogbuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

                    AlertDialog dialog = alertdialogbuilder.create();

                    dialog.show();

                }
            }
        });

        getBlock();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String labid = CGlobal.getInstance().getPersistentPreference(SelectFacilitator_Activity.this)
                .getString(Constants.PREFS_USER_LAB_ID, "");
        DatabaseHandler databaseHandler1 = new DatabaseHandler(SelectFacilitator_Activity.this);
        //String labName = databaseHandler1.getLabName();
        //tvLabName.setText(labName);

        //String sDistrict = databaseHandler1.getDistrictName();
        //tvDistrictName.setText(sDistrict);
    }

    private void getBlock() {
        CommonModel commonModel = new CommonModel();
        //cmaBlock = databaseHandler.getBlockList();
        commonModel.setBlockname("Choose");
        cmaBlock.add(0, commonModel);
        Block_Adapter block_adapter = new Block_Adapter(SelectFacilitator_Activity.this, cmaBlock);
        spBlock.setAdapter(block_adapter);

        spBlock.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sBlockName = cmaBlock.get(position).getBlockname();
                if (sBlockName.equalsIgnoreCase("Choose")) {
                    return;
                }
                sBlockCode = cmaBlock.get(position).getBlockcode();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
