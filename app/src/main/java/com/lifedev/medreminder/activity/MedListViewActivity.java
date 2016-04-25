package com.lifedev.medreminder.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lifedev.medreminder.R;
import com.lifedev.medreminder.adapter.ListAdapter;
import com.lifedev.medreminder.custom.CustomRowList;
import com.lifedev.medreminder.dao.MedicineDAO;
import com.lifedev.medreminder.interfaces.CustomRow;
import com.lifedev.medreminder.model.Medicine;

import java.util.ArrayList;
import java.util.List;


import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;

public class MedListViewActivity extends AppCompatActivity {

    private MedicineDAO medicineDao;
    private ListView listViewCustom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_list_med);
        //setContentView(R.layout.activity_med_list_view);

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


    public Dialog onCreateDialog(final Intent intent) {
        String[] array = {"Edit Drug","Set Alarm","View Caregiver","Add New Caregiver"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Escolha")
                .setItems(array, new DialogInterface.OnClickListener() {
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