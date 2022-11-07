package com.sunanda.newroutine.application.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.modal.CommonModel;

import java.util.ArrayList;

public class Master_Adapter extends BaseAdapter {
    Context context;
    ArrayList<CommonModel> cmArrayMaster;
    LayoutInflater inflter;

    public Master_Adapter(Context applicationContext, ArrayList<CommonModel> cmaMaster) {
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
        item.setText(cmArrayMaster.get(i).getName());
        return view;
    }
}