package com.example.autocompletetxtview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends ArrayAdapter<ModelClass> {

    Context context;
    int resource, textViewResourceId;
    List<ModelClass> items, tempItems, suggestions;

    public Adapter(Context context, int resource, int textViewResourceId, List<ModelClass> items) {
        super(context, resource, textViewResourceId, items);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = items;
        tempItems = new ArrayList<ModelClass>(items);
        suggestions = new ArrayList<ModelClass>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        }
        ModelClass model = items.get(position);
        if (model != null) {
            TextView item_name = (TextView) view.findViewById(android.R.id.text1);
            if (item_name != null)
                item_name.setText(model.getName());
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((ModelClass) resultValue).getName();
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (charSequence != null && charSequence.length()>=3) {
                suggestions.clear();
                for (ModelClass model : tempItems) {
                    if (model.getName().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        suggestions.add(model);
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
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            List<ModelClass> filterList = (ArrayList<ModelClass>) filterResults.values;
            if (filterResults != null && filterResults.count > 0) {
                clear();
                for (ModelClass model : filterList) {
                    add(model);
                    notifyDataSetChanged();
                }
            }
        }
    };

}
