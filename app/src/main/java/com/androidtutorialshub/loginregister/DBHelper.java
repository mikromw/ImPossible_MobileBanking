package com.androidtutorialshub.loginregister;

/**
 * Created by hp on 11/16/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.androidtutorialshub.loginregister.User;

public class DBHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "HistoryManager.db";

    // User table name
    private static final String TABLE_HISTORY = "history";

    // User Table Columns names
    //private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_HISTORY_KODE = "history_kode";
    private static final String COLUMN_HISTORY_NOREKASAL = "history_norekasal";
    private static final String COLUMN_HISTORY_NOREKTUJUAN = "history_norektujuan";
    private static final String COLUMN_HISTORY_JUMLAH = "history_jumlah";
    private static final String COLUMN_HISTORY_DATETIME="history_datetime";
    // create table sql query
    private String CREATE_HISTORY_TABLE = "CREATE TABLE " + TABLE_HISTORY + "("
            + COLUMN_HISTORY_KODE + " TEXT PRIMARY KEY," + COLUMN_HISTORY_NOREKASAL + " TEXT,"
            + COLUMN_HISTORY_NOREKTUJUAN + " TEXT," + COLUMN_HISTORY_JUMLAH + " TEXT," + COLUMN_HISTORY_DATETIME + " TEXT" +")";

    // drop table sql query
    private String DROP_HISTORY_TABLE = "DROP TABLE IF EXISTS " + TABLE_HISTORY;
    /**
     * Constructor
     *
     * @param context
     */
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_HISTORY_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop User Table if exist
        db.execSQL(DROP_HISTORY_TABLE);

        // Create tables again
        onCreate(db);
    }
    /**
     * This method is to create history record
     *
     * @paramuser
     */
    public void addHistory(History history) {
        SQLiteDatabase db = this.getWritableDatabase();
        //String tglwktskrg = getCurrentDate();
        ContentValues values = new ContentValues();
        values.put(COLUMN_HISTORY_KODE, history.getKode());
        values.put(COLUMN_HISTORY_NOREKASAL, history.getNoRekAsal());
        values.put(COLUMN_HISTORY_NOREKTUJUAN, history.getNoRekTujuan());
        values.put(COLUMN_HISTORY_JUMLAH, history.getJumlah());
        values.put(COLUMN_HISTORY_DATETIME, history.getDateTime());
        // Inserting Row
        db.insert(TABLE_HISTORY, null, values);
        db.close();
        HistoryArray();
    }
    //mengembalikan string kode history
    public String KodeHistory(){
        String[] columns = {
                COLUMN_HISTORY_KODE
        };
        SQLiteDatabase db = this.getReadableDatabase();
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_HISTORY, //Table to query
                columns,    //columns to return4
                null,                  //columns for the WHERE clause
                null,              //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null); //The sort order
        Integer cursorCount = cursor.getCount();
        String banyak=cursorCount.toString();
        cursor.close();
        db.close();
        return banyak;
    }
    //menyimpan data history dari database ke array history
    public void HistoryArray(){

        // array of columns to fetch
        String[] columns = {
                COLUMN_HISTORY_KODE,
                COLUMN_HISTORY_NOREKASAL,
                COLUMN_HISTORY_NOREKTUJUAN,
                COLUMN_HISTORY_JUMLAH,
                COLUMN_HISTORY_DATETIME
        };

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_HISTORY, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null); //The sort order


        if(cursor.moveToFirst()) {
            History history;
            History.historys.clear();
            do {
                history=new History();
                history.setKode(cursor.getString(cursor.getColumnIndex(COLUMN_HISTORY_KODE)));
                history.setNoRekAsal(cursor.getString(cursor.getColumnIndex(COLUMN_HISTORY_NOREKASAL)));
                history.setNoRekTujuan(cursor.getString(cursor.getColumnIndex(COLUMN_HISTORY_NOREKTUJUAN)));
                history.setJumlah(cursor.getString(cursor.getColumnIndex(COLUMN_HISTORY_JUMLAH)));
                history.setDateTime(cursor.getString(cursor.getColumnIndex(COLUMN_HISTORY_DATETIME)));
                // Adding user record to list
                history.historys.add(history);
            } while (cursor.moveToNext());
        }


        cursor.close();
        db.close();
    }

}