package com.lifedev.medreminder.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lifedev.medreminder.R;
import com.lifedev.medreminder.adapter.ListAdapter;
import com.lifedev.medreminder.custom.CustomApp;
import com.lifedev.medreminder.custom.CustomRowList;
import com.lifedev.medreminder.dao.MedicineDAO;
import com.lifedev.medreminder.interfaces.CustomRow;
import com.lifedev.medreminder.model.Medicine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import android.support.v7.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.TextView;

public class MedListViewActivity extends AppCompatActivity {

    private MedicineDAO medicineDao;
    private ListView listViewCustom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Customize the title of action bar */
        CustomApp.customActionBar(getSupportActionBar(),
                getApplicationContext(),R.string.title_list_med,getAssets());

        //getSupportActionBar().setTitle(R.string.title_list_med);

        medicineDao = new MedicineDAO(this);
        medicineDao.open();

        List<Medicine> values = medicineDao.getAllMedicines();
        final ArrayList<String> list = new ArrayList<String>();

        if(values.isEmpty()){
            Medicine medicine = new Medicine();
            medicine.setName("Lista Vazia");
            values.add(medicine);
        }

        setContentView(R.layout.activity_med_list_view);

        listViewCustom = (ListView) findViewById(R.id.med_list_view);

        ListAdapter adapter = new ListAdapter(this,1,createItemList(values));

        listViewCustom.setAdapter(adapter);

        onClickItem();

    }


    private List<CustomRow> createItemList(List<Medicine> valuesList){

        List<CustomRow> items = new ArrayList<CustomRow>();

        for (Medicine medicine : valuesList) {
            items.add(new CustomRowList(medicine.getName(),medicine.getId(),R.drawable.med_icon));
        }
        return items;
    }


    private void onClickItem(){

        listViewCustom.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parentAdapter, View view, int position, long id) {

                CustomRowList customRow = (CustomRowList) parentAdapter.getAdapter().getItem(position);

                Intent intent = new Intent();
                // get item's id and add to intent
                intent.putExtra(MedicineActivity.MEDICINE_ID, (long) customRow.getId());
                // get item's name and add to intent
                intent.putExtra(MedicineActivity.MEDICINE_NAME, customRow.getText());
                Dialog dialogChoose = onCreateDialog(intent);
                dialogChoose.show();

            }
        });
    }

    public List<CustomRow> createListViewDialog(LinkedHashMap<String, Integer> labels){
        List<CustomRow> items = new ArrayList<CustomRow>();
        int count = 0;

        for (Map.Entry<String,Integer> label:labels.entrySet()) {
            items.add(new CustomRowList(label.getKey(),count,label.getValue()));
            count++;
        }

        return items;
    }

    public LinkedHashMap<String,Integer> createLabelListViewDialog(){

        LinkedHashMap<String,Integer> labelMap = new LinkedHashMap();

        labelMap.put(getResources().getString(R.string.edit_med_dialog),R.drawable.med_icon);
        labelMap.put(getResources().getString(R.string.set_alarm_dialog),R.drawable.med_icon);
        labelMap.put(getResources().getString(R.string.view_care_list_dialog),R.drawable.med_icon);
        labelMap.put(getResources().getString(R.string.add_care_dialog),R.drawable.med_icon);
        return labelMap;
    }

    public Dialog onCreateDialog(final Intent intent) {;
        /* Create a Map with label and icon to show on Dialog */
        LinkedHashMap labelMap = createLabelListViewDialog();
        /* Create a custom adapter to show icon and text at same time */
        ListAdapter adapter = new ListAdapter(this,1,createListViewDialog(labelMap));
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.AppCompatAlertDialogStyle);

        builder.setTitle(getResources().getString(R.string.choose_dialog))
                .setAdapter(adapter, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which) {
                            case 0:

                                intent.setClassName("com.lifedev.medreminder", "com.lifedev.medreminder.activity.MedicineActivity");
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                break;

                            case 1:

                                intent.setClassName("com.lifedev.medreminder", "com.lifedev.medreminder.activity.AlarmActivity");
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                break;

                            case 2:

                                intent.setClassName("com.lifedev.medreminder", "com.lifedev.medreminder.activity.CareListViewActivity");
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                break;

                            case 3:

                                intent.setClassName("com.lifedev.medreminder", "com.lifedev.medreminder.activity.CareGiverActivity");
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                break;

                            default:
                                break;
                        }
                    }
                });
        return builder.create();
    }

}
