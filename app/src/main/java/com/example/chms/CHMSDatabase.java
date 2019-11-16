package com.example.chms;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CHMSDatabase extends SQLiteOpenHelper
{
    public CHMSDatabase(Context context) {
        super(context, "CHMS", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE owner_profile(id int PRIMARY KEY AUTOINCREMENT,name varchar(20),address varchar(40),contact_no varchar(11),adhar_no varchar(17),pincode varchar(6),child_no int(3),latitude double,longitude double)");
        db.execSQL("CREATE TABLE cow_profile(cuin int PRIMARY KEY,name varchar(20),policy varchar(20),location varchar(20),age int(3),weight float,child_no int(3),gotra varchar(10),breed varchar(15),owner varchar(20),aadhar int(16))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS cow_profile");
        onCreate(db);
    }
}
