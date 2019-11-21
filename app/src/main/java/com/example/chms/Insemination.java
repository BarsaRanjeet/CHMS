package com.example.chms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class Insemination extends AppCompatActivity {

    public static Integer[] ids = null;
    public static Integer[] cattleIds = null;
    public static String[] inseminationDates = null;
    String cattleId;
    ListView listV;
    InseminationAdapter adapter;
    String[] columns = {};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insemination);

        Intent i = getIntent();
        cattleId = i.getStringExtra("cattleId");

        CHMSDatabase sqlDatabase = new  CHMSDatabase(this);
        SQLiteDatabase db = sqlDatabase.getReadableDatabase();

        Cursor cursor = db.query("insemination",columns,"cuin=?",new String[]{cattleId},null,null,null);
        cursor.moveToFirst();
        cattleIds = new Integer[cursor.getCount()];
        inseminationDates = new String[cursor.getCount()];
        ids = new Integer[cursor.getCount()];
        if(cursor.getCount()>0) {
            for (int j = 0; cursor.isAfterLast() == false; j++) {
                cattleIds[j] = Integer.parseInt(cattleId.toString());
                inseminationDates[j] = cursor.getString(cursor.getColumnIndex("date")).toString();
                ids[j] = cursor.getInt(cursor.getColumnIndex("ins_id"));
                cursor.moveToNext();
            }

            listV = (ListView) findViewById(R.id.inseminations);
            adapter = new InseminationAdapter(this, ids,cattleIds, inseminationDates);
            listV.setAdapter(adapter);
        }
    }
    public void newEntry(View v)
    {
        Intent i = new Intent(this,InseminationNewEntry.class);
        i.putExtra("cattleId",cattleId);
        startActivity(i);
    }
}
