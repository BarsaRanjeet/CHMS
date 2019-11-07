package com.example.chms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AddCattle extends AppCompatActivity {
    String[] breed = {"Select breed","caw type 2","caw type 3"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cattle);
        ArrayAdapter<String> breedAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,breed);
        Spinner spinner = (Spinner)findViewById(R.id.breed);
        breedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(breedAdapter);
    }
}
