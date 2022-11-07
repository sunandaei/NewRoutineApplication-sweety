package com.sunanda.newroutine.application.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.modal.CommonModel;

import java.util.ArrayList;

public class AutoText_Adapter extends ArrayAdapter<CommonModel> {

    Context context;
    int resource, textViewResourceId;
    ArrayList<CommonModel> items, tempItems, suggestions;

    public AutoText_Adapter(Context context, int resource, int textViewResourceId, ArrayList<CommonModel> items) {
        super(context, resource, textViewResourceId, items);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = items;
        tempItems = new ArrayList<CommonModel>(items); // this makes the difference.
        suggestions = new ArrayList<CommonModel>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.spinner_item, parent, false);
        }
        CommonModel people = items.get(position);
        if (people != null) {
            TextView lblName = (TextView) view.findViewById(R.id.item);
            if (lblName != null)
                lblName.setText(people.getChlorine_Value());
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((CommonModel) resultValue).getChlorine_Value();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (CommonModel people : tempItems) {
                    if (people.getChlorine_Value().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(people);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<CommonModel> filterList = (ArrayList<CommonModel>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (CommonModel people : filterList) {
                    add(people);
                    notifyDataSetChanged();
                }
            }
        }
    };
}