package com.example.chms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    public Integer[] listImage = {
            R.drawable.logo,
            R.drawable.logo,
            R.drawable.logo,
            R.drawable.logo,
            R.drawable.logo,
            R.drawable.logo
    };
    public String[] names = {
            "Ranjit",
            "Mehul",
            "Satish",
            "Govind",
            "Vipul",
            "Rahul"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ManageList adapter = new ManageList(this,names,listImage);
        listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }

}
