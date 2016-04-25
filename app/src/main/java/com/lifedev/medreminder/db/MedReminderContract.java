package com.lifedev.medreminder.db;

import android.provider.BaseColumns;
/**
 * Created by alano on 4/21/16.
 */
public class MedReminderContract {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MedReminder.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP  = ",";
    private static final String DATETIME = " DATETIME";

    /* Tables */
    public static final String TABLE_NAME_MEDICINE = "medicine";
    public static final String TABLE_NAME_CAREGIVER = "caregiver";
    public static final String TABLE_NAME_PATIENT = "patient";
    public static final String TABLE_NAME_MEDICINE_CAREGIVER = "medicine_caregiver";


    public MedReminderContract(){

    }

    /* Inner class that defines the table medicine */
    public static abstract class Medicine implements BaseColumns {

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_LABORATORY = "laboratory";
        public static final String COLUMN_DATE_CADASTRE = "date_cadastre";
        public static final String COLUMN_BEGIN_HOUR = "begin_hour";
        public static final String COLUMN_MEDICINE_INTERVAL = "interval";


        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME_MEDICINE + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                COLUMN_LABORATORY + TEXT_TYPE + COMMA_SEP +
                COLUMN_DATE_CADASTRE + DATETIME + COMMA_SEP +
                COLUMN_BEGIN_HOUR + DATETIME + COMMA_SEP +
                COLUMN_MEDICINE_INTERVAL + DATETIME + " )";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME_MEDICINE;

    }

    /* Inner class that defines the table receiver */
    public static abstract class Caregiver implements BaseColumns {

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_MOBILE_PHONE = "phone";
        public static final String COLUMN_EMAIL = "email";


        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME_CAREGIVER + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                COLUMN_EMAIL + TEXT_TYPE + COMMA_SEP +
                COLUMN_MOBILE_PHONE + TEXT_TYPE + " )";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME_CAREGIVER;

    }


    /* Inner class that defines the table patient */
    /*
    public static abstract class Patient implements BaseColumns {

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_MOBILE_PHONE = "phone";
        public static final String COLUMN_GENDER = "gender";


        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME_PATIENT + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                COLUMN_MOBILE_PHONE + TEXT_TYPE + COMMA_SEP +
                COLUMN_GENDER + " )";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME_PATIENT;

    }
    */

    /* Inner class that defines the relationship between table medicine and caregiver */
    public static abstract class MedicineToCaregiver implements BaseColumns {

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_ID_MEDICINE = "id_medicine";
        public static final String COLUMN_ID_CAREGIVER = "id_caregiver";


        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME_MEDICINE_CAREGIVER + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_ID_MEDICINE + " INTEGER " + COMMA_SEP +
                COLUMN_ID_CAREGIVER + " INTEGER " + COMMA_SEP + //" )";
                "FOREIGN KEY(" + COLUMN_ID_MEDICINE + ") REFERENCES " + TABLE_NAME_MEDICINE + "("+ COLUMN_ID +")" + COMMA_SEP +
                "FOREIGN KEY(" + COLUMN_ID_CAREGIVER + ") REFERENCES " + TABLE_NAME_CAREGIVER + "("+ COLUMN_ID +")" + " )";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME_MEDICINE_CAREGIVER;

    }



}
