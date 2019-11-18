package com.example.chms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MilkNewEntry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milk_new_entry);

        Spinner milkTimeSpin = (Spinner)findViewById(R.id.milkTime);
        String[] milkTimes = {"Select milk time","Morning","Evening"};
        ArrayAdapter<String> milkAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,milkTimes);
        milkTimeSpin.setAdapter(milkAdapter);
    }
}
