package com.example.chms;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MilkNewEntry extends AppCompatActivity {
    private String cattleId;
    private EditText milkDate,milkQty,milkNote;
    private Spinner milkTime;
    final Calendar myCalendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milk_new_entry);
        Intent i = getIntent();
        cattleId = i.getStringExtra("cattleId");

        milkDate = (EditText)findViewById(R.id.milk_date);
        milkDate.setText(getCurrentDate());
        milkTime = (Spinner)findViewById(R.id.milk_time);
        String[] milkTimes = {"Select milk time","Morning","Evening"};
        ArrayAdapter<String> milkAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,milkTimes);
        milkTime.setAdapter(milkAdapter);

        milkQty = (EditText)findViewById(R.id.milk_qty);
        milkNote = (EditText)findViewById(R.id.milk_note);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }
        };

        milkDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(MilkNewEntry.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
    private void updateDate() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        milkDate.setText(sdf.format(myCalendar.getTime()));
    }
    public void addMilkNewEntry(View v)
    {
        CHMSDatabase sqliteHelper = new CHMSDatabase(getApplicationContext());
        SQLiteDatabase db = sqliteHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("cuin",Integer.parseInt(cattleId));
        values.put("date",milkDate.getText().toString());
        values.put("time",milkTime.getSelectedItem().toString());
        values.put("milk_qty",Double.parseDouble(milkQty.getText().toString()));
        values.put("note",milkNote.getText().toString());
        db.insert("milk_detail",null,values);
        Toast.makeText(getApplicationContext(),"Successfully inserted detail",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,MilkProduction.class);
        intent.putExtra("cattleId",cattleId);
        startActivity(intent);
    }
    private String getCurrentDate()
    {
        String date=null;
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        date = day+"/"+month+"/"+year;
        return date;
    }
}
