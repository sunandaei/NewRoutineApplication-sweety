package com.sunanda.newroutine.application.somenath.helper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.somenath.pojo.ResponseVillage;

public class CustomListViewDialog extends Dialog implements View.OnClickListener {

    public CustomListViewDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public CustomListViewDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public Activity activity;
    public Dialog dialog;
    private RecyclerView.Adapter adapter;
    private TextView tvVillage;
    private ResponseVillage responseVillageData;
    private String vill_code;

    public CustomListViewDialog(Activity a, RecyclerView.Adapter adapter, ResponseVillage responseVillage,
                                String vill_code, TextView tvVillage) {
        super(a);
        this.activity = a;
        this.adapter = adapter;
        this.vill_code = vill_code;
        this.tvVillage = tvVillage;
        this.responseVillageData = responseVillage;
        setupLayout();
    }

    private void setupLayout() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_layout);
        Button cancel_dialog = (Button) findViewById(R.id.cancel_dialog);
        Button done_dialog = (Button) findViewById(R.id.done_dialog);
        //TextView title = findViewById(R.id.title);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setAdapter(adapter);
        cancel_dialog.setOnClickListener(this);
        done_dialog.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_dialog:
                dismiss();
                break;
            case R.id.done_dialog:
                vill_code = responseVillageData.getVillCode();
                tvVillage.setText(responseVillageData.getVillName());
                Log.d("TESTS", responseVillageData.getVillName() + vill_code);
                dismiss();
                break;
            default:
                dismiss();
        }
        dismiss();
    }
}