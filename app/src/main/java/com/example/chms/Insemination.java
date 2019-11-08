package com.example.chms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class Insemination extends AppCompatActivity {

    public  static Integer[] cattleIds ={5043,5612};
    public static String[] inseminationDates = {"9/10/2019","30/10/2019"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insemination);

        ListView listV = (ListView)findViewById(R.id.inseminations);
        InseminationAdapter adapter = new InseminationAdapter(this,cattleIds,inseminationDates);
        listV.setAdapter(adapter);
    }
    public void newEntry(View v)
    {
        Intent i = new Intent(this,InseminationNewEntry.class);
        startActivity(i);
    }
}
