package com.mer.SqliteEx;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import static com.mer.SqliteEx.SqliteActivity.TAG;

public class SqliteHelper extends SQLiteOpenHelper {
    static String DB = "mer.db";
    String TABLE = "Contacts";
    String ID = "id";
    String NAME = "name";
    Context context;

    String CREATE_TABLE_QUERY = "create table " + TABLE + " (" + ID + " integer primary key," + NAME + " text)";
    String DELETE_TABLE_QUERY = "drop table if exists " + TABLE;
    String READ_TABLE_QUERY = "select name from " + TABLE;

    public SqliteHelper(Context context) {
        super(context, DB, null, 1);
        this.context = context;
        Log.d(TAG, "SqliteHelper");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_TABLE_QUERY);
        onCreate(db);
    }

    public void insert(String name) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, name);
        long result = db.insert(TABLE, null, values);
        Log.d(TAG, "insert: " + result);
    }

    public void update(String oldName, String newName) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = NAME + "=?";
        String[] whereArgs = {oldName};
        ContentValues values = new ContentValues();
        values.put(NAME, newName);
        int result = db.update(TABLE, values, whereClause, whereArgs);
        Log.d(TAG, "update: " + result);
    }

    public void delete(String name) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = NAME + "=?";
        String[] whereArgs = {name};
        int result = db.delete(TABLE, whereClause, whereArgs);
        Log.d(TAG, "delete: " + result);
    }

    public void display() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(READ_TABLE_QUERY, null);
        String records = "";
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            String name = cursor.getString(0);
            records = records + name + " , ";
            Log.d(TAG, "display: " + name);
            cursor.moveToNext();
        }
        Toast.makeText(context, records, Toast.LENGTH_SHORT).show();
    }
}