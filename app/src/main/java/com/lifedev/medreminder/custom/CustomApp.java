package com.lifedev.medreminder.custom;


import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.lifedev.medreminder.R;

/**
 * Created by alano on 4/28/16.
 */
public class CustomApp {

    private static String REMACHINESCRIPT_FONT_PATH = "fonts/RemachineScript_Personal_Use.ttf";

    /**
     * Customize the actionbar
     * @param supportActionBar
     * @param context
     * @param title
     * @param assets
     */
    public static void customActionBar(ActionBar supportActionBar, Context context, int title, AssetManager assets){

        supportActionBar.setDisplayShowCustomEnabled(true);
        supportActionBar.setDisplayShowTitleEnabled(false);

        /* Inflate layout with current aplication context */
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View customView = layoutInflater.inflate(R.layout.custom_action_bar,null);

        /* get TextView from custom_action_bar.xml */
        TextView text = (TextView) customView.findViewById(R.id.title_action_bar);
        text.setText(title);

        /* Customize the title of action bar*/
        changeFont(text,assets);

        supportActionBar.setCustomView(customView);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
    }


    /**
     * Load a external font and change the text received by parameter
     * @param textView
     * @param assets
     * @return
     */
    private static TextView changeFont(TextView textView, AssetManager assets){
        Typeface remachineScriptTtf = Typeface.createFromAsset(assets,REMACHINESCRIPT_FONT_PATH);
        textView.setTypeface(remachineScriptTtf);
        return textView;
    }
}
