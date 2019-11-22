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
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class CattleList extends AppCompatActivity {

    public static String [] cattleImages = null;
    public static String [] cattleIds = null;
    public static String [] cattleNextHeatDates = null;
    public static String[] cattleLastHeatDates = null;
    private TextView txtTitle;
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
        txtTitle = findViewById(R.id.txt_title);
        CHMSDatabase dbHelper = new CHMSDatabase(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursorCattles = db.rawQuery("SELECT * FROM cattle_profile WHERE last_heat_on != '' OR last_heat_on != null ",new String[]{});
        cattleImages = new String[cursorCattles.getCount()];
        cattleIds = new String[cursorCattles.getCount()];
        cattleNextHeatDates = new String[cursorCattles.getCount()];
        cattleLastHeatDates = new String[cursorCattles.getCount()];
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        if(cursorCattles.getCount() > 0)
        {
            cursorCattles.moveToFirst();
            for(int i=0;!cursorCattles.isAfterLast();i++)
            {
                cattleImages[i] = cursorCattles.getString(cursorCattles.getColumnIndex("cattle_image"));
                cattleIds[i] = "UCIN No: "+cursorCattles.getString(cursorCattles.getColumnIndex("cuin"));
                //cattleNextHeatDates[i] = "Next heat on: "+cursorCattles.getString(cursorCattles.getColumnIndex("last_heat_on"));
                String lastHeatOn = cursorCattles.getString(cursorCattles.getColumnIndex("last_heat_on"));
                cattleNextHeatDates[i] = "";
                if(lastHeatOn != null || !lastHeatOn.equals(""))
                {
                    try {
                        Calendar c = Calendar.getInstance();
                        Date date = simpleDateFormat.parse(lastHeatOn);
                        c.setTime(date);
                        c.add(Calendar.DATE, 21);
                        Date nextHeatDate = c.getTime();
                        String nextHeatOn = simpleDateFormat.format(nextHeatDate);
                        cattleNextHeatDates[i] = nextHeatOn;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    cattleLastHeatDates[i] = lastHeatOn;

                    cursorCattles.moveToNext();

                }

            }

            for(int i=0;i<cattleIds.length;i++)
            {

                String currentNextHeat = cattleNextHeatDates[i];
                Date currentNextHeatDate = null;

                long currentMills = 0;
                try {
                    currentNextHeatDate = simpleDateFormat.parse(currentNextHeat);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                currentMills = currentNextHeatDate.getTime();
                for(int j=i;j<cattleIds.length;j++)
                {
                    String checkNextHeat = cattleNextHeatDates[j];
                    Date checkNextHeatDate = null;
                    try {
                        checkNextHeatDate = simpleDateFormat.parse(checkNextHeat);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    long checkMills = checkNextHeatDate.getTime();
                    if(checkMills < currentMills)
                    {
                        String tmpCattleId = cattleIds[i];
                        cattleIds[i] = cattleIds[j];
                        cattleIds[j] = tmpCattleId;

                        String tmpNextHeatDate = cattleNextHeatDates[i];
                        cattleNextHeatDates[i] = cattleNextHeatDates[j];
                        cattleNextHeatDates[j] = tmpNextHeatDate;

                        String tmpCattleImage = cattleImages[i];
                        cattleImages[i] = cattleImages[j];
                        cattleImages[j] = tmpCattleImage;
                    }
                }



            }

            Cursor cursorCattlesNullDate = db.rawQuery("SELECT * FROM cattle_profile WHERE last_heat_on = '' OR last_heat_on = null ",new String[]{});
            int cursorLength = cursorCattlesNullDate.getCount();
            //Toast.makeText(this, ""+cursorLength, Toast.LENGTH_SHORT).show();
            String[] nCattleImages = new String[cursorLength+cattleIds.length];
            String[] nCattleIds = new String[cursorLength+cattleIds.length];
            String[] nCattleNextHeatDates = new String[cursorLength+cattleIds.length];
            int i = 0;
            for(i=0;i<cattleNextHeatDates.length;i++)
            {
                nCattleIds[i] = cattleIds[i];
                nCattleImages[i] = cattleImages[i];
                nCattleNextHeatDates[i] = "Next heat on: "+ cattleNextHeatDates[i];
            }
            cursorCattlesNullDate.moveToPosition(0);

            for(int j=0;j<cursorLength;j++,i++)
            {
                nCattleIds[i] = "UCIN No: "+cursorCattlesNullDate.getString(cursorCattlesNullDate.getColumnIndex("cuin"));
                nCattleImages[i] = cursorCattlesNullDate.getString(cursorCattlesNullDate.getColumnIndex("cattle_image"));
                String cattleType = cursorCattlesNullDate.getString(cursorCattlesNullDate.getColumnIndex("cattle_type"));
                if(cattleType.equals("Bull") || cattleType.equals("Buffalo Bull"))
                {
                    nCattleNextHeatDates[i] = cattleType;
                }
                else
                {
                    nCattleNextHeatDates[i] = "Calf in Immature state";
                }
                cursorCattlesNullDate.moveToNext();
            }
            if(nCattleIds.length >= 1)
                txtTitle.setText("Cattle List");
            db.close();
            CattleListAdapter adapter = new CattleListAdapter(this,nCattleImages,nCattleIds,nCattleNextHeatDates);
            final ListView listView = (ListView)findViewById(R.id.cattleList);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    String item = (String)listView.getItemAtPosition(position);
                    item = item.substring(9);
                    Intent i = new Intent(getApplicationContext(),CattleProfile.class);
                    i.putExtra("cattleId",item);
                    startActivity(i);
                }
            });

        }

    }
    public void addCattle(View v)
    {
        Intent i = new Intent(this,AddCattle.class);
        startActivity(i);
    }
}