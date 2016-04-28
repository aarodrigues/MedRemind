package com.lifedev.medreminder.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.lifedev.medreminder.R;
import com.lifedev.medreminder.custom.CustomApp;
import com.lifedev.medreminder.dao.CaregiverDAO;
import com.lifedev.medreminder.model.Caregiver;

public class CareGiverActivity extends AppCompatActivity {

    private long idMedicine;
    private String nameMedicine;
    private static String nameCaregiver;
    private static String phoneCaregiver;
    private static String emailCaregiver;

    private CaregiverDAO careDao;

    public final static String CAREGIVER_ID_INTEND = "com.lifedev.medreminder.id.caregiver";
    public final static String MEDICINE_ID_INTEND = MedicineActivity.MEDICINE_ID;
    public final static String MEDICINE_NAME_INTEND = MedicineActivity.MEDICINE_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_giver);

        /* Customize the title of action bar */
        CustomApp.customActionBar(getSupportActionBar(),
                getApplicationContext(),R.string.title_reg_care,getAssets());
        //getSupportActionBar().setTitle(R.string.title_reg_care);

        Intent intent = getIntent();
        this.idMedicine = intent.getLongExtra(MedicineActivity.MEDICINE_ID, 0L);
        this.nameMedicine = intent.getStringExtra(MedicineActivity.MEDICINE_NAME);

        long idCaregiver = intent.getLongExtra(CAREGIVER_ID_INTEND, -1L);
        // Verify whether is edition
        if (idCaregiver != -1L) {
            openConnection();
            Caregiver care = careDao.getCaregiverById(idCaregiver);
            nameCaregiver = care.getName();
            phoneCaregiver = care.getPhone();
            emailCaregiver = care.getEmail();
            fillFields();
        }
    }

    private void openConnection(){
        careDao = new CaregiverDAO(this);
        careDao.open();
    }

    public void fillFields(){
        EditText name =  (EditText) findViewById(R.id.care_name);
        EditText lab = (EditText) findViewById(R.id.phone_number);
        EditText email = (EditText) findViewById(R.id.email);
        name.setText(nameCaregiver);
        lab.setText(phoneCaregiver);
        email.setText(emailCaregiver);
        nameCaregiver = "";
        phoneCaregiver = "";
        emailCaregiver = "";
    }

    public void saveCaregiver(View view){

            openConnection();

            EditText editTextName = (EditText) findViewById(R.id.care_name);
            EditText editTextLab = (EditText) findViewById(R.id.phone_number);
            String name = editTextName.getText().toString();
            String phone = editTextLab.getText().toString();

            if (this.idMedicine !=0) {
                careDao.createCaregiver(name, phone,this.idMedicine);
            }

            System.out.println("The caregiver was saved with sucess!!");
            editTextName.setText("");
            editTextLab.setText("");

            Toast.makeText(this, "salvo com sucesso!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, AlarmActivity.class);
            intent.putExtra(MEDICINE_ID_INTEND, this.idMedicine);
            intent.putExtra(MEDICINE_NAME_INTEND, this.nameMedicine);
            startActivity(intent);

    }


    public void callPatient(View view){
        Intent intent = new Intent(this, PatientActivity.class);
        startActivity(intent);
    }
}
