package com.example.chms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

public class CattleList extends AppCompatActivity {

    public static Integer [] cattleImages = {
            R.drawable.ic_launcher_background,
            R.color.colorPrimary,
            R.color.colorPrimaryDark
    };
    public static String [] cattleNames = {
            "Mamu ki Pyari",
            "Vadil ki pyari",
            "Gun name(Unknown)"
    };
    public static String [] cattleStates ={
            "Walking",
            "Eating",
            "Resting"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cattle_list);

        CattleListAdpter adapter = new CattleListAdpter(this,cattleImages,cattleNames,cattleStates);
        ListView listView = (ListView)findViewById(R.id.cattleList);
        listView.setAdapter(adapter);
    }
}