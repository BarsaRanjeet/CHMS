package com.example.chms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

public class Insemination extends AppCompatActivity {

    public  static Integer[] cattleIds ={123456,78910};
    public static String[] inseminationDates = {"10/12/2019","20/12/2019"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insemination);

        ListView listV = (ListView)findViewById(R.id.inseminations);
        InseminationAdapter adapter = new InseminationAdapter(this,cattleIds,inseminationDates);
        listV.setAdapter(adapter);
    }
}
