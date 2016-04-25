package com.lifedev.medreminder.interfaces;

import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by alano on 4/24/16.
 */
public interface CustomRow {
    //public int getViewType();
    public View getView(LayoutInflater inflater, View convertView);
}
