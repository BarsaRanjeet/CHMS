package com.example.chms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

public class HeatDetails extends AppCompatActivity {

    public static String[] probableHeatDates = {"25/10/2019","25/10/2019","25/10/2019"};
    public static String[] actualHeatDates = {"25/10/2019","25/10/2019","25/10/2019"};
    public static String[] inseminations = {"yes","no","yes"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heat_details);

        Intent intent = getIntent();
        String cattleId = intent.getStringExtra("cattleId");

        ListView heatDetailsList = (ListView)findViewById(R.id.heat_details);
        HeatDetailsAdapter heatDetailsAdapter = new HeatDetailsAdapter(this,probableHeatDates,actualHeatDates,inseminations);
        heatDetailsList.setAdapter(heatDetailsAdapter);
    }
}
