package com.lifedev.medreminder.custom;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lifedev.medreminder.interfaces.CustomRow;
import com.lifedev.medreminder.R;

/**
 * Created by alano on 4/24/16.
 */
public class CustomRowList implements CustomRow {

    private final String str;
    private final int id;
    private final int imageResource;

    public CustomRowList(String text, int id, int imageName){
        this.str = text;
        this.id = id;
        this.imageResource = imageName;
    }

    /*
    @Override
    public int getViewType() {
        return RowType.LIST_ITEM.ordinal();
    }
    */

    @Override
    public View getView(LayoutInflater inflater, View convertView) {

        View view;
        if (convertView == null) {
            view = (View) inflater.inflate(R.layout.custom_list, null);
            // Do some initialization
        } else {
            view = convertView;
        }

        ImageView imageView = (ImageView) view.findViewById(R.id.icon);
        imageView.setImageResource(imageResource);
        TextView text = (TextView) view.findViewById(R.id.text_list);
        text.setText(str);

        return view;
    }

    public String getText(){
        return str;
    }

    public int getId(){
        return id;
    }
}
