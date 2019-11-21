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
        db.execSQL("CREATE TABLE owner_profile(id integer PRIMARY KEY AUTOINCREMENT,name varchar(20),address varchar(40),contact_no varchar(11),adhar_no varchar(17),pincode varchar(6),child_no integer(3),latitude double,longitude double)");
        db.execSQL("CREATE TABLE cattle_profile(cuin integer PRIMARY KEY,cattle_image varchar(30),cattle_name varchar(20),cattle_type varchar(20),date_of_birth varchar(12),cattle_policy varchar(20),age integer(3),weight double,no_of_child integer(3),mother_id integer,father_id integer,breed varchar(20),status varchar(20),last_heat_on varchar(12))");
        db.execSQL("CREATE TABLE heat_table(h_id  integer PRIMARY KEY AUTOINCREMENT,cuin integer, last_heat_date varchar(12), predicted_next_heat_date varchar(12),insemination_status  varchar(10), actual_next_heat_date varchar(12))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS owner_profile");
        db.execSQL("DROP TABLE IF EXISTS cattle_profile");
        db.execSQL("DROP TABLE IF EXISTS heat_table");
        onCreate(db);
    }
}
