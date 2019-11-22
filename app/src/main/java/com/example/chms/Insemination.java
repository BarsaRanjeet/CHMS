package com.example.chms;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Insemination extends AppCompatActivity {

    public static Integer[] ids = null;
    public static Integer[] cattleIds = null;
    public static String[] inseminationDates = null;
    String cattleId;
    TextView txtTitle;
    ListView listV;
    InseminationAdapter adapter;
    String[] columns = {};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insemination);

        Intent i = getIntent();
        cattleId = i.getStringExtra("cattleId");
        listV = (ListView) findViewById(R.id.inseminations);
        txtTitle = findViewById(R.id.txt_title);

        CHMSDatabase sqlDatabase = new  CHMSDatabase(this);
        SQLiteDatabase db = sqlDatabase.getReadableDatabase();

        Cursor cursor = db.query("insemination",columns,"cuin=?",new String[]{cattleId},null,null,"ins_id DESC");
        cursor.moveToFirst();
        cattleIds = new Integer[cursor.getCount()];
        inseminationDates = new String[cursor.getCount()];
        ids = new Integer[cursor.getCount()];
        if(cursor.getCount()>0) {
            txtTitle.setText("Insemination Details");
            for (int j = 0; cursor.isAfterLast() == false; j++) {
                cattleIds[j] = Integer.parseInt(cattleId.toString());
                inseminationDates[j] = cursor.getString(cursor.getColumnIndex("date")).toString();
                ids[j] = cursor.getInt(cursor.getColumnIndex("ins_id"));
                cursor.moveToNext();
            }
            adapter = new InseminationAdapter(this, ids,cattleIds, inseminationDates);
            listV.setAdapter(adapter);
        }
        listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                int item = (Integer) listV.getItemAtPosition(i);
                CHMSDatabase sqliteHelper = new CHMSDatabase(getApplicationContext());
                SQLiteDatabase db = sqliteHelper.getReadableDatabase();
                Cursor cursor = db.query("insemination",new String[]{},"ins_id=?",new String[]{""+item},null,null,null);
                cursor.moveToFirst();
                AlertDialog.Builder builder = new AlertDialog.Builder(Insemination.this);
                builder.setTitle("Insemination details");
                while(cursor.isAfterLast()==false)
                {
                    String[] items = {
                            ("Cattle id : "+cursor.getString(1)),
                            "Date : "+cursor.getString(2),
                            "Time : "+cursor.getString(3),
                            "Type : "+cursor.getString(4),
                            "Note : "+cursor.getString(5)};
                    builder.setItems(items,null);
                    cursor.moveToNext();
                }
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }
    public void newEntry(View v)
    {
        Intent i = new Intent(this,InseminationNewEntry.class);
        i.putExtra("cattleId",cattleId);
        startActivity(i);
    }
}
