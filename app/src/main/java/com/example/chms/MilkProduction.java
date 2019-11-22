package com.example.chms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class MilkProduction extends AppCompatActivity {
    private  static String[] dates = null;
    private static String[] milkQuantities = null;
    private String cattleId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milk_production);
        Intent i = getIntent();
        cattleId = i.getStringExtra("cattle_id");

        CHMSDatabase sqliteHelper = new CHMSDatabase(getApplicationContext());
        SQLiteDatabase db = sqliteHelper.getReadableDatabase();

        ListView listV = (ListView)findViewById(R.id.milkDetail);
        MilkProductionAdapter adapter = new MilkProductionAdapter(this,dates,milkQuantities);
        listV.setAdapter(adapter);
    }
    public void newEntry(View v)
    {
        Intent i = new Intent(this,MilkNewEntry.class);
        startActivity(i);
    }
}
