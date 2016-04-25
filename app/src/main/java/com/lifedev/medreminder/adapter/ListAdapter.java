package com.lifedev.medreminder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.lifedev.medreminder.interfaces.CustomRow;

import java.util.List;

/**
 * Created by alano on 4/23/16.
 */
public class ListAdapter extends ArrayAdapter<CustomRow> {

    private LayoutInflater layoutInflater;

    public ListAdapter(Context context, int resource, List<CustomRow> objects) {
        super(context, resource, objects);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getItem(position).getView(layoutInflater, convertView);
    }

    /*
    @Override
    public int getViewTypeCount() {
        return RowType.values().length;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getViewType();
    }
    */

}
