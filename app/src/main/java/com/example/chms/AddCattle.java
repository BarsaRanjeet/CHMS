package com.example.chms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AddCattle extends AppCompatActivity {
    String[] breed = {"Select breed",
            "Gir",
            "Jersey",
            "Surti",
            "Jaffrabadi",
            "Mahesana"
    };
    String[] status = {"Select status"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cattle);
        ArrayAdapter<String> breedAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,breed);
        Spinner breedSpinner = (Spinner)findViewById(R.id.breed);
        breedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        breedSpinner.setAdapter(breedAdapter);

        ArrayAdapter<String> statusAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,status);
        Spinner statusSpinner = (Spinner)findViewById(R.id.status);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(statusAdapter);
    }
    public void addCattle(View v)
    {
        Intent i = new Intent(this,CattleList.class);
        startActivity(i);
    }
}
