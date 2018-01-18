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

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "UserManager.db";

    // User table name
    private static final String TABLE_USER = "user";

    // User Table Columns names
    //private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_USERNAME = "user_username";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_NOREK = "user_norek";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    private static final String COLUMN_USER_NOHP = "user_nohp";
    private static final String COLUMN_USER_SALDO = "user_saldo";
    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "(" + COLUMN_USER_USERNAME + " TEXT,"
            + COLUMN_USER_NAME + " TEXT," + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_NOREK + " TEXT PRIMARY KEY,"
            + COLUMN_USER_PASSWORD + " TEXT," + COLUMN_USER_NOHP + " TEXT,"+ COLUMN_USER_SALDO + " TEXT"+ ")";

    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
    /**
     * Constructor
     *
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);

        // Create tables again
        onCreate(db);
    }
    /**
     * This method to update user saldo
     *
     * @param user
     */
    public void updateSaldo(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_SALDO, user.getSaldo());
        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_NOREK + " = ?", new String[]{user.getNoRek()});
        db.close();
        UserArray();
    }
    /**
     * This method to update user PIN
     *
     * @param user
     */
    public void updatePIN(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_NOREK + " = ?", new String[]{user.getNoRek()});
        db.close();
        UserArray();
    }
    /**
     * This method to update user profile
     *
     * @param user
     */
    public void updateProfile(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_NOHP, user.getNohp());
        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_NOREK + " = ?", new String[]{user.getNoRek()});
        db.close();
        UserArray();
    }
    /**
     * This method to check no rekening user exist or not
     *
     * @paramnorek
     * @return true/false
     */
    public boolean checkNoRek(String norek) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_NOREK
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_NOREK + " = ?";

        // selection argument
        String[] selectionArgs = {norek};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }
    /**
     * This method to check no rekening and PIN user valid or not
     *
     * @paramnorek
     * @return true/false
     */
    public boolean checkNoRek(String norek, String pin) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_NOREK
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_NOREK + " = ?"+ " AND " + COLUMN_USER_PASSWORD + " = ?";

        // selection argument
        String[] selectionArgs = {norek,pin};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }
    public boolean checkUsername(String username, String pin) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_NOREK
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_USERNAME + " = ?"+ " AND " + COLUMN_USER_PASSWORD + " = ?";

        // selection argument
        String[] selectionArgs = {username,pin};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }
    //menyimpan data user dari database ke array user
    public void UserArray(){

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_USERNAME,
                COLUMN_USER_EMAIL,
                COLUMN_USER_NAME,
                COLUMN_USER_NOREK,
                COLUMN_USER_PASSWORD,
                COLUMN_USER_NOHP,
                COLUMN_USER_SALDO
        };

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null); //The sort order


        if(cursor.moveToFirst()) {
            User user;
            User.users.clear();
            do {
                user=new User();
                user.setUsername(cursor.getString(cursor.getColumnIndex(COLUMN_USER_USERNAME)));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setNoRek(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NOREK)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                user.setNohp(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NOHP)));
                user.setSaldo(cursor.getString(cursor.getColumnIndex(COLUMN_USER_SALDO)));
                // Adding user record to list
                user.users.add(user);
            } while (cursor.moveToNext());
        }


        cursor.close();
        db.close();
    }

    //mengisi data user pada database
    public void Userutama(){// array of columns to fetch
        String[] columns = {
                COLUMN_USER_NOREK
        };
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                null,                       //columns for the WHERE clause
                null,                       //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();
        List<User> userList = new ArrayList<User>();
        cursor.close();
        db.close();
        userList.add(new User("vin_22","8000885577","Vincent", "limvin_39@yahoo.com", "392297", "081286037147","Rp100.000.000"));
        userList.add(new User("stevensw11","8305911611","Steven Willington", "stevenwillington11@gmail.com", "115197", "081286037147","Rp200.000.000"));
        userList.add(new User("jefri12","8305353535","Jefri", "jefri@email.com", "120697", "081286037147","Rp250.000.000"));
        userList.add(new User("riandy07","4000567890","Riandy", "riandy7@hotmail.com", "072597", "081286037147","Rp100.000.000"));
        userList.add(new User("danny01","4000575788","Danny", "danny21@yahoo.com", "210197", "081286037147","Rp200.000.000"));
        userList.add(new User("vinson05","6000556612","Vinson", "vinson17@gmail.com", "170597", "081286037147","Rp250.000.000"));
        userList.add(new User("jimmy02","7000123456","Jimmy Lohil", "jimmylo15@hotmail.com", "150297", "081286037147","Rp100.000.000"));
        userList.add(new User("howard03","7000445566","Howard", "howard_3@yahoo.com", "270397", "081286037147","Rp200.000.000"));
        userList.add(new User("raymond10","7000223344","Raymond", "raymond20@gmail.com", "301097", "081286037147","Rp250.000.000"));
        userList.add(new User("erick11","8000200777","Erick Kwantan", "erickk13@email.com", "111197", "081286037147","Rp100.000.000"));
        //userList.add(new User("8000300888","Tommy Aditya", "tommy.aditya@mobile.dasar.mikroskil", "mobiledasar", "081286037147","200000000"));
        //userList.add(new User("9000353535","Erwin", "erwin@mobile.dasar.mikroskil", "mobiledasar", "081286037147","250000000"));

        if (cursorCount == 0) {
            SQLiteDatabase db1 = this.getWritableDatabase();
            for (int i = 0; i < userList.size(); i++) {
                ContentValues values = new ContentValues();
                values.put(COLUMN_USER_USERNAME, userList.get(i).username);
                values.put(COLUMN_USER_NAME, userList.get(i).name);
                values.put(COLUMN_USER_EMAIL, userList.get(i).email);
                values.put(COLUMN_USER_NOREK, userList.get(i).norek);
                values.put(COLUMN_USER_PASSWORD, userList.get(i).password);
                values.put(COLUMN_USER_NOHP, userList.get(i).nohp);
                values.put(COLUMN_USER_SALDO, userList.get(i).saldo);
                // Inserting Row
                db1.insert(TABLE_USER, null, values);
            }
            db1.close();
        }
    }
}
