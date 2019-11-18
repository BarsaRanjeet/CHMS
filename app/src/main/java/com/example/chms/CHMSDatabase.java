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
        db.execSQL("CREATE TABLE cattle_profile(cuin int PRIMARY KEY,cattle_image varchar(30),cattle_name varchar(20),cattle_policy varchar(20),age int(3),weight double,no_of_child int(3),mother_id int,father_id int,breed varchar(20),status varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS cow_profile");
        onCreate(db);
    }
}
