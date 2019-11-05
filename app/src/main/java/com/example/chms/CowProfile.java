package com.example.chms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class CowProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cow_profile);



    }
    public void createProfile(View v)
    {

        CHMSDatabase chms = new CHMSDatabase(this);
        SQLiteDatabase db = chms.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("");
    }
}
