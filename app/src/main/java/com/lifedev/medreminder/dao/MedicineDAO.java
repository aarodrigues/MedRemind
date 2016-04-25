package com.lifedev.medreminder.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lifedev.medreminder.db.MedReminderContract;
import com.lifedev.medreminder.db.MedReminderDbHelper;
import com.lifedev.medreminder.model.Medicine;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by alano on 4/21/16.
 */
public class MedicineDAO {

    // Database fields
    private SQLiteDatabase database;
    private MedReminderDbHelper dbHelper;
    private String[] allColumns = {
            MedReminderContract.Medicine.COLUMN_ID,
            MedReminderContract.Medicine.COLUMN_NAME,
            MedReminderContract.Medicine.COLUMN_LABORATORY,
            MedReminderContract.Medicine.COLUMN_DATE_CADASTRE,
            MedReminderContract.Medicine.COLUMN_BEGIN_HOUR,
            MedReminderContract.Medicine.COLUMN_MEDICINE_INTERVAL
    };
    private String sortOrder = MedReminderContract.Medicine.COLUMN_NAME +" ASC";

    private String whereIdEqual = MedReminderContract.Medicine.COLUMN_ID + " = ";


    public MedicineDAO(Context context) {
        dbHelper = new MedReminderDbHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long createMedicine(String name,String laboratory,String beginHour, String interval) {
        ContentValues values = new ContentValues();
        Date dateCadastre = new Date(System.currentTimeMillis());
//		    values.put(CareHealthContract.Drug.COLUMN_ID, drug.getId());
        values.put(MedReminderContract.Medicine.COLUMN_NAME, name);
        values.put(MedReminderContract.Medicine.COLUMN_LABORATORY, laboratory);
        values.put(MedReminderContract.Medicine.COLUMN_DATE_CADASTRE, dateCadastre.toString());
        values.put(MedReminderContract.Medicine.COLUMN_BEGIN_HOUR, beginHour);
        values.put(MedReminderContract.Medicine.COLUMN_MEDICINE_INTERVAL, interval);

        /**
         * The second argument provides the name of a column in which
         * the framework can insert NULL in the event
         * that the ContentValues is empty
         */
        long insertId = database.insert(
                MedReminderContract.TABLE_NAME_MEDICINE,
                null,
                values);

        return insertId;
    }

    public void updateMedicine(long id, String name, String laboratory){
        ContentValues values = new ContentValues();
        values.put(MedReminderContract.Medicine.COLUMN_NAME, name);
        values.put(MedReminderContract.Medicine.COLUMN_LABORATORY, laboratory);

        database.update(MedReminderContract.TABLE_NAME_MEDICINE, values, whereIdEqual + id, null);
    }

    public void deleteMedicine(long id) {

        System.out.println("Comment deleted with id: " + id);
        database.delete(MedReminderContract.TABLE_NAME_MEDICINE, MedReminderContract.Medicine.COLUMN_ID
                + " = " + id, null);
    }

    public List<Medicine> getAllMedicines() {
        List<Medicine> drugList = new ArrayList<Medicine>();

        Cursor cursor = database.query(MedReminderContract.TABLE_NAME_MEDICINE,
                allColumns, null, null, null, null, sortOrder);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Medicine medicine = cursorToMedicine(cursor);
            drugList.add(medicine);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return drugList;
    }


    public Medicine getMedicineById(long id){

        String[] whereArg = new String[] {String.valueOf(id)};
        Cursor cursor = database.query(MedReminderContract.TABLE_NAME_MEDICINE,
                allColumns, whereIdEqual + "?", whereArg, null, null, sortOrder);

        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            return cursorToMedicine(cursor);
        }
        return null;
    }

    @SuppressLint("SimpleDateFormat")
    private Medicine cursorToMedicine(Cursor cursor) {
        Medicine drug = new Medicine();
        drug.setId(cursor.getInt(0));
        drug.setName(cursor.getString(1));
        drug.setLaboratory(cursor.getString(2));

        drug.setDateCadastre(stringToDate(cursor.getString(3)));
        drug.setBeginHour(stringToDate(cursor.getString(4)));
        drug.setInterval(stringToDate(cursor.getString(5)));

        return drug;
    }

    @SuppressLint("SimpleDateFormat")
    private Date stringToDate(String stringDate){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        try {
            date =  formatter.parse(stringDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return date;
    }

    public void addTimeMedicine(long id, String hour, String interval){

        ContentValues values = new ContentValues();
        values.put(MedReminderContract.Medicine.COLUMN_BEGIN_HOUR, hour);
        values.put(MedReminderContract.Medicine.COLUMN_MEDICINE_INTERVAL, interval);

        database.update(MedReminderContract.TABLE_NAME_MEDICINE, values, whereIdEqual + id, null);
    }

}
