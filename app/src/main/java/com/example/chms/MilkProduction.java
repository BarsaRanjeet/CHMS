package com.example.chms;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MilkProduction extends AppCompatActivity {
    private  static String[] dates = null;
    private static String[] milkQuantities = null;
    private static String[] milkIds = null;
    private static String[] milkTimes = null;

    private String cattleId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milk_production);
        Intent i = getIntent();
        cattleId = i.getStringExtra("cattleId");

        CHMSDatabase sqliteHelper = new CHMSDatabase(getApplicationContext());
        final SQLiteDatabase db = sqliteHelper.getReadableDatabase();
        final Cursor cursor = db.query("milk_detail",new String[]{},null,null,null,null,"milk_id DESC");
        if(cursor.getCount()>0) {

            dates = new String[cursor.getCount()];
            milkQuantities = new String[cursor.getCount()];
            milkIds = new String[cursor.getCount()];
            milkTimes = new String[cursor.getCount()];
            cursor.moveToFirst();
            for (int j=0;cursor.isAfterLast()==false;j++)
            {
                dates[j] = cursor.getString(cursor.getColumnIndex("date"));
                milkQuantities[j] = cursor.getString(cursor.getColumnIndex("milk_qty"));
                milkIds[j] = cursor.getString(cursor.getColumnIndex("milk_id"));
                milkTimes[j] = cursor.getString(cursor.getColumnIndex("time"));
                cursor.moveToNext();
            }
            ListView listV = (ListView) findViewById(R.id.milkDetail);
            MilkProductionAdapter adapter = new MilkProductionAdapter(this, milkIds, dates, milkQuantities,milkTimes);
            listV.setAdapter(adapter);
        }
        final ListView milkList = (ListView)findViewById(R.id.milkDetail);
        milkList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = (String)milkList.getItemAtPosition(i);
                Cursor cursor1 = db.query("milk_detail",new String[]{},"milk_id=?",new String[]{item},null,null,null);
                if(cursor1.getCount()>0)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MilkProduction.this);
                    builder.setTitle("Milk details");

                    cursor1.moveToFirst();
                    while (cursor1.isAfterLast()==false)
                    {
                        String[] milkDetails = {
                                "Cattle id : "+cattleId,
                                "Date : "+cursor1.getString(cursor1.getColumnIndex("date")),
                                "Time : "+cursor1.getString(cursor1.getColumnIndex("time")),
                                "Quantity : "+cursor1.getString(cursor1.getColumnIndex("milk_qty")),
                                "Note : "+cursor1.getString(cursor1.getColumnIndex("note"))
                        };
                        builder.setItems(milkDetails,null);
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        cursor1.moveToNext();
                    }
                }
            }
        });
    }
    public void newEntry(View v)
    {
        Intent i = new Intent(this,MilkNewEntry.class);
        i.putExtra("cattleId",cattleId);
        startActivity(i);
    }
}
