package cis350.upenn.edu.cathealthapp.Persistence;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;

import cis350.upenn.edu.cathealthapp.Core.Database;
import cis350.upenn.edu.cathealthapp.Main.LoadingScreenActivity;

public class Persistence extends SQLiteOpenHelper {

    Context context;

    public Persistence(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    public void save() {
        SQLiteDatabase db = getWritableDatabase();
    }

    public void createDB() {

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS objs(obj_name nvarchar(100) NOT NULL, obj_bytes blob, PRIMARY KEY(obj_name));";
        db.execSQL(createTableSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
