package ru.spicedevelopers.igorpavlov.kioskmode;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class Applicant {
    public int Id;
    public String firstName;
    public String secondName;
    public String email;
    public String phone;
    public int male;

    public Applicant(int id, String firstName, String secondName, String email, String phone, int male) {
        this.Id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.phone = phone;
        this.male = male;
    }

    public void Save() {
        SQLiteDatabase db = DBHelper.getInstance().getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("first_name", firstName);
        cv.put("second_name", secondName);
        cv.put("email", email);
        cv.put("phone", phone);
        cv.put("male", male);

        long rowID = db.insert("mytable", null, cv);
        Log.d("w", "row inserted, ID = " + rowID);

        DBHelper.getInstance().close();
    }

    public static ArrayList<Applicant> FindUnsent() {
        SQLiteDatabase db = DBHelper.getInstance().getWritableDatabase();
        Cursor c = db.query("applicants", null, null, null, null, null, null);

        ArrayList<Applicant> result = new ArrayList<>();
        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex("id");
            int firstNameColIndex = c.getColumnIndex("first_name");
            int secondNameColIndex = c.getColumnIndex("second_name");
            int emailColIndex = c.getColumnIndex("email");
            int phoneColIndex = c.getColumnIndex("phone");
            int maleColIndex = c.getColumnIndex("male");

            do {
                result.add(new Applicant(
                        c.getInt(idColIndex),
                        c.getString(firstNameColIndex),
                        c.getString(secondNameColIndex),
                        c.getString(emailColIndex),
                        c.getString(phoneColIndex),
                        c.getInt(maleColIndex)));

            } while (c.moveToNext());

        }
        c.close();
        db.close();

        return result;
    }

    public void Delete() {
        SQLiteDatabase db = DBHelper.getInstance().getWritableDatabase();
        db.delete("applicants", "id = ?", new String[]{String.valueOf(Id)});
        db.close();

    }


    public String toString() {
        return String.format("first_name={0}&second_name={1}&email={2}&phone={3}&male={4}", firstName, secondName, email, phone, male);
    }

}
