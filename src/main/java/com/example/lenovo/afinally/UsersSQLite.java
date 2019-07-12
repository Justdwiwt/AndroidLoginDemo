package com.example.lenovo.afinally;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class UsersSQLite extends SQLiteOpenHelper {

    final String CREATE_USERS = "create table User (_id integer primary key autoincrement,username text,phone text,password text)";

    UsersSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    void insert(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", user.getName());
        values.put("phone", user.getPhone());
        values.put("password", user.getPassword());
        db.insert("User", null, values);
        values.clear();
    }

    Boolean find(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("select * from User where username=? and password=?", new String[]{user.getName(), user.getPassword()});
        boolean flag;
        if (cursor.moveToFirst()) {
            flag = true;
            int i = cursor.getColumnIndex("phone");
            String p = cursor.getString(i);
            user.setPhone(p);
        } else {
            flag = false;
        }
        return flag;
    }

    List<User> findAll() {
        List<User> userList = new ArrayList<User>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query("User", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setName(cursor.getString(cursor.getColumnIndex("username")));
                user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                user.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
                userList.add(user);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return userList;
    }

    void delete() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from User");
    }

    void deleteUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from User where username=?", new Object[]{username});
    }

    void updatePassword(User user, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update User set password=? where password=?", new Object[]{password, user.getPassword()});
    }

}
