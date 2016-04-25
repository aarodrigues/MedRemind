package com.lifedev.medreminder.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.lifedev.medreminder.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        ActionBar actionBar =  getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.icon);
        */
    }

    public void callMedicine(View view){
        Intent intent = new Intent(this, MedicineActivity.class);
        startActivity(intent);
    }

    public void callMedicineList(View view){
        Intent intent = new Intent(this, MedListViewActivity.class);
        startActivity(intent);
    }

    public void callCaregiverList(View view){
        Intent intent = new Intent(this, CareListViewActivity.class);
        startActivity(intent);
    }
}
