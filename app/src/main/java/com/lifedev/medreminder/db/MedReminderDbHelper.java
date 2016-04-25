package com.lifedev.medreminder.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by alano on 4/21/16.
 */
public class MedReminderDbHelper extends SQLiteOpenHelper{

    public MedReminderDbHelper(Context context) {
        super(context, MedReminderContract.DATABASE_NAME, null, MedReminderContract.DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MedReminderContract.Medicine.CREATE_TABLE);
        db.execSQL(MedReminderContract.Caregiver.CREATE_TABLE);
        db.execSQL(MedReminderContract.MedicineToCaregiver.CREATE_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(MedReminderContract.Medicine.DELETE_TABLE);
        db.execSQL(MedReminderContract.Caregiver.DELETE_TABLE);
        db.execSQL(MedReminderContract.MedicineToCaregiver.DELETE_TABLE);
        onCreate(db);
    }

}
