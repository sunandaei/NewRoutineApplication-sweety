package com.sunanda.newroutine.application.somenath.myadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sunanda.newroutine.application.R;

import java.util.ArrayList;


public class MySpinnerAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> cmArrayMaster;
    LayoutInflater inflter;

    public MySpinnerAdapter(Context applicationContext, ArrayList<String> cmaMaster) {
        this.context = applicationContext;
        this.cmArrayMaster = cmaMaster;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return cmArrayMaster.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.spinner_item, null);
        TextView item = (TextView) view.findViewById(R.id.item);
        item.setText(cmArrayMaster.get(i));
        return view;
    }
}