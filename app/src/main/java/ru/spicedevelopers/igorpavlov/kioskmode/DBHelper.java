package ru.spicedevelopers.igorpavlov.kioskmode;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by igorpavlov on 23.07.16.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper instance;

    public static DBHelper getInstance() {
        return instance;
    }

    public DBHelper(Context context) {
        super(context, "myDB", null, 1);
        instance = this;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.w("w", "--- onCreate database ---");
        // создаем таблицу с полями
        db.execSQL("create table applicants ("
                + "id integer primary key autoincrement,"
                + "first_name text,"
                + "second_name text,"
                + "phone text,"
                + "email text,"
                + "male integer"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

