package com.example.chms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CattleProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cattle_profile);
    }
    public void insemination(View v)
    {
        Intent i = new Intent(this,Insemination.class);
        startActivity(i);
    }
    public void milkProduction(View v)
    {
        Intent i = new Intent(this,MilkProduction.class);
        startActivity(i);
    }
}
