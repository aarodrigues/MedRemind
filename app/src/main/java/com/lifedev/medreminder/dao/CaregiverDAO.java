package com.lifedev.medreminder.dao;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.lifedev.medreminder.model.Caregiver;

import com.lifedev.medreminder.db.MedReminderContract;
import com.lifedev.medreminder.db.MedReminderDbHelper;

/**
 * Created by alano on 4/21/16.
 */
public class CaregiverDAO {

    // Database fields
    private SQLiteDatabase database;
    private MedReminderDbHelper dbHelper;
    private String[] allColumns = {
            MedReminderContract.Caregiver.COLUMN_ID,
            MedReminderContract.Caregiver.COLUMN_NAME,
            MedReminderContract.Caregiver.COLUMN_MOBILE_PHONE,
    };

    private final String queryGetCareByIdMedicine =
            "SELECT c."+MedReminderContract.Caregiver.COLUMN_ID+", c."
                    +MedReminderContract.Caregiver.COLUMN_NAME+", c."
                    +MedReminderContract.Caregiver.COLUMN_MOBILE_PHONE
                    +" FROM "+MedReminderContract.TABLE_NAME_CAREGIVER+" c INNER JOIN "
                    +MedReminderContract.TABLE_NAME_MEDICINE_CAREGIVER+" dc ON c."
                    +MedReminderContract.Caregiver.COLUMN_ID+" = dc."
                    +MedReminderContract.MedicineToCaregiver.COLUMN_ID_CAREGIVER+" WHERE dc."
                    +MedReminderContract.MedicineToCaregiver.COLUMN_ID_MEDICINE+" = ?";

    private String sortOrder = MedReminderContract.Caregiver.COLUMN_NAME +" ASC";

    private String whereIdEqual = MedReminderContract.Caregiver.COLUMN_ID + " = ";


    public CaregiverDAO(Context context) {
        dbHelper = new MedReminderDbHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long createCaregiver(String name,String phone,long idMedicine) {
        ContentValues values = new ContentValues();


        values.put(MedReminderContract.Caregiver.COLUMN_NAME, name);
        values.put(MedReminderContract.Caregiver.COLUMN_MOBILE_PHONE, phone);

        /**
         * The second argument provides the name of a column in which
         * the framework can insert NULL in the event
         * that the ContentValues is empty

         */
        long insertId = database.insert(
                MedReminderContract.TABLE_NAME_CAREGIVER,
                null,
                values);

        return createMedicineCaregiver(idMedicine,insertId);

    }

    public long createMedicineCaregiver(long idMedicine, long idCaregiver){

        ContentValues values = new ContentValues();

        values.put(MedReminderContract.MedicineToCaregiver.COLUMN_ID_MEDICINE, idMedicine);
        values.put(MedReminderContract.MedicineToCaregiver.COLUMN_ID_CAREGIVER, idCaregiver);

        return database.insert(
                MedReminderContract.TABLE_NAME_MEDICINE_CAREGIVER,
                null,
                values);
    }

    public void deleteCaregiver(Caregiver caregiver) {
        long id = caregiver.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(MedReminderContract.TABLE_NAME_CAREGIVER, MedReminderContract.Medicine.COLUMN_ID
                + " = " + id, null);
    }

    public List<Caregiver> getAllCaregivers() {
        List<Caregiver> careList = new ArrayList<Caregiver>();

        Cursor cursor = database.query(MedReminderContract.TABLE_NAME_CAREGIVER,
                allColumns, null, null, null, null, sortOrder);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Caregiver care = cursorToCare(cursor);
            careList.add(care);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return careList;
    }

    public Caregiver getCaregiverById(long id) {

        String[] whereArg = new String[] {String.valueOf(id)};
        Cursor cursor = database.query(MedReminderContract.TABLE_NAME_CAREGIVER,
                allColumns, whereIdEqual + "?", whereArg, null, null, sortOrder);

        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            return cursorToCare(cursor);
        }
        return null;
    }

    public List<Caregiver> getCaregiverByMedicineId(long id){
        List<Caregiver> careList = new ArrayList<Caregiver>();

        String[] arg = new String[] {String.valueOf(id)};
        Cursor cursor = database.rawQuery(queryGetCareByIdMedicine, arg);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Caregiver care = cursorToCare(cursor);
            careList.add(care);
            cursor.moveToNext();
        }

        // make sure to close the cursor
        cursor.close();
        return careList;
    }

    @SuppressLint("SimpleDateFormat")
    private Caregiver cursorToCare(Cursor cursor) {
        Caregiver care = new Caregiver();
        care.setId(cursor.getInt(cursor.getColumnIndex(MedReminderContract.Caregiver.COLUMN_ID)));
        care.setName(cursor.getString(cursor.getColumnIndex(MedReminderContract.Caregiver.COLUMN_NAME)));
        care.setPhone(cursor.getString(cursor.getColumnIndex(MedReminderContract.Caregiver.COLUMN_MOBILE_PHONE)));

        return care;
    }

}
