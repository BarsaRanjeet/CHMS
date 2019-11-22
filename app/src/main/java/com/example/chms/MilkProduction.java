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
        cattleId = i.getStringExtra("cattleId");

        CHMSDatabase sqliteHelper = new CHMSDatabase(getApplicationContext());
        SQLiteDatabase db = sqliteHelper.getReadableDatabase();
        Cursor cursor = db.query("milk_detail",new String[]{},null,null,null,null,"milk_id DESC");
        if(cursor.getCount()>0) {

            dates = new String[cursor.getCount()];
            milkQuantities = new String[cursor.getCount()];
            cursor.moveToFirst();
            for (int j=0;cursor.isAfterLast()==false;j++)
            {
                dates[j] = cursor.getString(2);
                milkQuantities[j] = cursor.getString(4);
                cursor.moveToNext();
            }
            ListView listV = (ListView) findViewById(R.id.milkDetail);
            MilkProductionAdapter adapter = new MilkProductionAdapter(this, dates, milkQuantities);
            listV.setAdapter(adapter);
        }
    }
    public void newEntry(View v)
    {
        Intent i = new Intent(this,MilkNewEntry.class);
        i.putExtra("cattleId",cattleId);
        startActivity(i);
    }
}
