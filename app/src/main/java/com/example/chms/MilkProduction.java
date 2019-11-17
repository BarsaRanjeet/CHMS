package com.example.chms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class MilkProduction extends AppCompatActivity {
    public  static String[] dates ={
            "8/11/2019",
            "8/11/2019",
            "7/11/2019",
            "7/11/2019"
    };
    public static String[] milkQuantities = {
            "2 liter",
            "2.5 liter",
            "3 liter",
            "2.5 liter"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milk_production);
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
