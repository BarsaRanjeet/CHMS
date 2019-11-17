package com.example.chms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class CattleList extends AppCompatActivity {

    public static Integer [] cattleImages = {
            R.drawable.cow1,
            R.drawable.cow2,
            R.drawable.cow3,
            R.drawable.cow4,
            R.drawable.cow5
    };
    public static String [] cattleNames = {
            " Cattle id : 1234 ",
            " Cattle id : 5678 ",
            " Cattle id : 9012 ",
            " Cattle id : 3456 ",
            " Cattle id : 7890 "
    };
    public static String [] cattleStates ={
            " Next heat date : 15/11/2019 ",
            " Next heat date : 20/11/2019 ",
            " Next heat date : 25/11/2019 ",
            " Next heat date : 27/11/2019 ",
            " Next heat date : 30/11/2019 ",
    };

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

        CattleListAdapter adapter = new CattleListAdapter(this,cattleImages,cattleNames,cattleStates);
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