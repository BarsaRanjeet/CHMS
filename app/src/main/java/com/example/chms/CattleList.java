package com.example.chms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class CattleList extends AppCompatActivity {

    public static String [] cattleImages = null;
    public static String [] cattleIds = null;
    public static String [] cattleNextHeatDates = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cattle_list);

        SharedPreferences sharedPreferences = getSharedPreferences("CHMS",MODE_PRIVATE);
        boolean isRegistered = sharedPreferences.getBoolean("isRegistered",false);
        if(!isRegistered)
        {
            Intent intent = new Intent(this, OwnerRegistration.class);
            startActivity(intent);
        }

        CHMSDatabase dbHelper = new CHMSDatabase(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursorCattles = db.rawQuery("SELECT * FROM cattle_profile",new String[]{});
        cattleImages = new String[cursorCattles.getCount()];
        cattleIds = new String[cursorCattles.getCount()];
        cattleNextHeatDates = new String[cursorCattles.getCount()];

        cursorCattles.moveToFirst();
        for(int i=0;!cursorCattles.isLast();i++)
        {
            cursorCattles.moveToNext();
            cattleImages[i] = cursorCattles.getString(cursorCattles.getColumnIndex("cattle_image"));
            cattleIds[i] = "Cattle id: "+cursorCattles.getString(cursorCattles.getColumnIndex("cattle_name"));
            cattleNextHeatDates[i] = "Next heat on: "+cursorCattles.getString(cursorCattles.getColumnIndex("last_heat_on"));

        }

        CattleListAdapter adapter = new CattleListAdapter(this,cattleImages,cattleIds,cattleNextHeatDates);
        ListView listView = (ListView)findViewById(R.id.cattleList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(),CattleProfile.class);
                startActivity(i);
            }
        });
    }
    public void addCattle(View v)
    {
        Intent i = new Intent(this,AddCattle.class);
        startActivity(i);
    }
}