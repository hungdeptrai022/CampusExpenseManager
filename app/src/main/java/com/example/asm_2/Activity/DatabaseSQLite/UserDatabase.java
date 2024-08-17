package com.example.asm_2.Activity.DatabaseSQLite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.asm_2.Activity.Models.User;

public class UserDatabase extends SQLiteOpenHelper {
    public static final String DB_NAME = "campusmanager";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "users";
    public static final String ID_COL= "id";
    public static final String USERNAME_COL = "username";
    public static final String PASSWORD_COL = "password";
    public static final String EMAIL_COL = "email";
    public static final String PHONE_COL = "phone";

    public UserDatabase( Context context) {
        super(context, DB_NAME, null,DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+ TABLE_NAME
                        +" ( " +ID_COL+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + USERNAME_COL + " VARCHAR(60), "
                        + EMAIL_COL + " VARCHAR(100), "
                        + PHONE_COL + " VARCHAR(30), "
                        + PASSWORD_COL + " VARCHAR(200))";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
            onCreate(db);
    }

    public long addNewUser (String username, String email, String phone, String password){
        SQLiteDatabase db  = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERNAME_COL, username);
        values.put(EMAIL_COL, email);
        values.put(PHONE_COL, phone);
        values.put(PASSWORD_COL, password);
        long insert =db.insert(TABLE_NAME, null, values);
        db.close();
        return  insert;
    }

        @SuppressLint("Range")
        public User getInfoUser(String username, String password){
            Cursor cursor = null;
            User user =new User();
            try{
                SQLiteDatabase db = this.getWritableDatabase();

                String[] columns = {ID_COL,USERNAME_COL,EMAIL_COL,PHONE_COL};
                String condition = USERNAME_COL + " = ? " +" AND "+PASSWORD_COL+" = ?";
                String[] params ={username,password};
                db.query(
                  TABLE_NAME,
                  columns,
                  condition,
                  params,
                  null,
                  null,
                   null
                );
                if(cursor.getCount() > 0){
                    cursor.moveToFirst();
                    user.setId(cursor.getInt(cursor.getColumnIndex(ID_COL)));
                    user.setUsername(cursor.getString(cursor.getColumnIndex(USERNAME_COL)));
                    user.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL_COL)));
                    user.setPhone(cursor.getString(cursor.getColumnIndex(PHONE_COL)));
                }
                db.close();
            } finally {
                    assert cursor !=null;
                    cursor.close();
            }
            return user;
    }
    public Cursor getUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, new String[]{USERNAME_COL, EMAIL_COL,PHONE_COL },
                EMAIL_COL + "=? AND " + PASSWORD_COL + "=?",
                new String[]{email, password}, null, null, null);
    }

}
