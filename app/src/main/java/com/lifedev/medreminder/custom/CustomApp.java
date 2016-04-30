package com.lifedev.medreminder.custom;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.lifedev.medreminder.R;
import com.lifedev.medreminder.adapter.ListAdapter;
import com.lifedev.medreminder.interfaces.CustomRow;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alano on 4/28/16.
 */
public class CustomApp {

    private static String REMACHINESCRIPT_FONT_PATH = "fonts/RemachineScript_Personal_Use.ttf";

    private static ArrayList<String> redirectActivities;

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

    /**
     * Create dialog rows with specific color
     * @param dialogRows
     * @return
     */
    public static List<CustomRow> createListViewDialog(List<CustomDialogRow> dialogRows){
        List<CustomRow> items = new ArrayList<CustomRow>();
        redirectActivities = new ArrayList<String>();
        String textColor = "#A401D3";

        for (int i = 0; i < dialogRows.size(); i++){
            items.add(new CustomRowList(dialogRows.get(i).getLabel(),i,dialogRows.get(i).getIcon(),textColor));
            redirectActivities.add(dialogRows.get(i).getActivitytarget());
        }

        return items;
    }

    /**
     * Create dialog with params from Activity
     * @param intent
     * @param context
     * @param resources
     * @param dialogRow
     * @return
     */
    public static Dialog onCreateDialog(final Intent intent,final Context context, Resources resources,List<CustomDialogRow> dialogRow) {

        /* Create a custom adapter to show icon and text at same time */
        ListAdapter adapter = new ListAdapter(context,1,createListViewDialog(dialogRow));
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.AppCompatAlertDialogStyle);

        builder.setTitle(resources.getString(R.string.choose_dialog))
                .setAdapter(adapter, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which) {
                            case 0:
                                launchNewActivity(redirectActivities.get(0));
                                break;

                            case 1:
                                launchNewActivity(redirectActivities.get(1));
                                break;

                            case 2:
                                launchNewActivity(redirectActivities.get(2));
                                break;

                            case 3:
                                launchNewActivity(redirectActivities.get(3));
                                break;

                            default:
                                break;
                        }
                    }

                    private void launchNewActivity(String className){
                        String packageContext = "com.lifedev.medreminder";
                        intent.setClassName(packageContext, packageContext+".activity."+className);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });
        return builder.create();
    }

}
