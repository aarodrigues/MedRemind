package com.lifedev.medreminder.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.lifedev.medreminder.R;
import com.lifedev.medreminder.adapter.ListAdapter;
import com.lifedev.medreminder.custom.CustomApp;
import com.lifedev.medreminder.custom.CustomDialogRow;
import com.lifedev.medreminder.custom.CustomRowList;
import com.lifedev.medreminder.dao.CaregiverDAO;
import com.lifedev.medreminder.interfaces.CustomRow;
import com.lifedev.medreminder.model.Caregiver;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;

import android.widget.AdapterView;


public class CareListViewActivity extends AppCompatActivity {

    private CaregiverDAO careDao;
    private ListView listViewCustom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Customize the title of action bar */
        CustomApp.customActionBar(getSupportActionBar(),
                getApplicationContext(),R.string.title_list_care,getAssets());

        careDao = new CaregiverDAO(this);
        careDao.open();

        Intent intent = getIntent();
        long id = intent.getLongExtra(CareGiverActivity.MEDICINE_ID_INTEND, -1L);
        List<Caregiver> values;

        if (id!=-1L) {
            values = careDao.getCaregiverByMedicineId(id);
        }else{
            values = careDao.getAllCaregivers();
        }

        final ArrayList<String> list = new ArrayList<String>();

        if(values.isEmpty()){
            Caregiver caregiver = new Caregiver();
            caregiver.setName("Lista Vazia");
            values.add(caregiver);
        }

        setContentView(R.layout.activity_care_list_view);

        listViewCustom = (ListView) findViewById(R.id.care_list_view);

        ListAdapter adapter = new ListAdapter(this,1,createItemList(values));

        listViewCustom.setAdapter(adapter);

        onClickItem();
    }

    private List<CustomRow> createItemList(List<Caregiver> valuesList){

        List<CustomRow> items = new ArrayList<CustomRow>();

        for (Caregiver caregiver : valuesList) {
            items.add(new CustomRowList(caregiver.getName(),caregiver.getId(),R.drawable.care_icon));
        }
        return items;
    }

    private void onClickItem(){

        listViewCustom.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parentAdapter, View view, int position, long id) {

                CustomRowList customRow = (CustomRowList) parentAdapter.getAdapter().getItem(position);

                Intent intent = new Intent();
                // get item's id and add to intent
                intent.putExtra(CareGiverActivity.CAREGIVER_ID_INTEND, (long) customRow.getId());
                Dialog dialogChoose = CustomApp.onCreateDialog(intent,CareListViewActivity.this,getResources(),createLabelListViewDialog());
                dialogChoose.show();

            }
        });
    }


    private List<CustomDialogRow> createLabelListViewDialog(){
        ArrayList<CustomDialogRow> dialogRowsArrayList = new ArrayList<CustomDialogRow>();

        dialogRowsArrayList.add(new CustomDialogRow(getResources().getString(R.string.edit_care_dialog),R.drawable.med_icon,"CareGiverActivity"));
        dialogRowsArrayList.add(new CustomDialogRow(getResources().getString(R.string.view_med_list_dialog),R.drawable.med_icon,"MedListViewActivity"));

        return dialogRowsArrayList;
    }

}
