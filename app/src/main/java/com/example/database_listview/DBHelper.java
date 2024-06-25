package com.example.database_listview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "users.db";
    private static final String DB_TABLE = "users_table";

    private static final String ID = "ID";
    private static final String NAME = "NAME";

    SQLiteDatabase db;

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String qry = "CREATE TABLE " + DB_TABLE +
                " ( " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                 NAME + " TEXT " + ")";

        db.execSQL(qry);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);

        onCreate(db);
    }

    public boolean insertdata(String name)
    {
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NAME, name);

        long result = db.insert(DB_TABLE , null , cv);

        return result != -1;
    }

    public Cursor viewdata()
    {
        db = this.getReadableDatabase();
        String qry = "SELECT * FROM " + DB_TABLE;
        Cursor cursor = db.rawQuery(qry,null);

        return cursor;
    }

}
