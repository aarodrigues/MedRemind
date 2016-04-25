package com.lifedev.medreminder.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.lifedev.medreminder.R;
import com.lifedev.medreminder.dao.MedicineDAO;
import com.lifedev.medreminder.model.Medicine;


import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class MedicineActivity extends AppCompatActivity {

    public final static String MEDICINE_ID = "com.lifedev.medreminder.id";
    public final static String MEDICINE_NAME = "com.lifedev.medreminder.medicineName";
    public final static String MEDICINE_LAB = "com.lifedev.medreminder.medicineLab";

    private MedicineDAO medicineDao;
    private long idMedicine = -1L;
    private static String nameMedicine;
    private static String labMedicine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_reg_med);

        Intent intent = getIntent();
        this.idMedicine = intent.getLongExtra(MEDICINE_ID,-1L);
        // Verify whether is edition
        if (idMedicine != -1L) {
            openConnection();
            Medicine med = medicineDao.getMedicineById(idMedicine);
            nameMedicine = med.getName();
            labMedicine = med.getLaboratory();
            medicineDao.close();
            fillFields();
        }

    }

    private void openConnection(){
        medicineDao = new MedicineDAO(this);
        medicineDao.open();
    }

    public void callCaregiver(long id, String name){
        Intent intent = new Intent(this, CareGiverActivity.class);
        intent.putExtra(MEDICINE_ID, id);
        intent.putExtra(MEDICINE_NAME, name);
        startActivity(intent);
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cad_drug, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    */

    private void fillFields(){
        EditText name =  (EditText) findViewById(R.id.med_name);
        EditText lab = (EditText) findViewById(R.id.lab_name);
        name.setText(nameMedicine);
        lab.setText(labMedicine);
        labMedicine = "";
        nameMedicine = "";
    }


    public void saveMedicine(View view){

        openConnection();

        EditText editTextName = (EditText) findViewById(R.id.med_name);
        EditText editTextLab = (EditText) findViewById(R.id.lab_name);
        String name = editTextName.getText().toString();
        String lab = editTextLab.getText().toString();

        if (idMedicine ==-1L) {

            idMedicine = medicineDao.createMedicine(name,lab,"14:00","6");
            System.out.println("The drug was saved with sucess!!");

            Toast.makeText(this, "salvo com sucesso!", Toast.LENGTH_SHORT).show();

            callCaregiver(idMedicine,name);
        }else {
            medicineDao.updateMedicine(idMedicine, name, lab);
            Toast.makeText(this, "Editado com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        }

        editTextName.setText("");
        editTextLab.setText("");

    }

    /*
    public void finishSaveDrug(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    */
}
