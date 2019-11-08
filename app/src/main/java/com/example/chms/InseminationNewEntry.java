package com.example.chms;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class InseminationNewEntry extends AppCompatActivity {

    final Calendar myCalendar = Calendar.getInstance();
    final Calendar myCurrentTime = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insemination_new_entry);

        String[] type = {"Select insemination type","Naturally","Artificially"};
        Spinner spin = (Spinner)findViewById(R.id.type);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,type);
        spin.setAdapter(adapter);

        EditText datePicker= (EditText) findViewById(R.id.date);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }
        };

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(InseminationNewEntry.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        final EditText editTimePicker = (EditText)findViewById(R.id.time);

        editTimePicker.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int hr = myCurrentTime.get(Calendar.HOUR);
                int min = myCurrentTime.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        InseminationNewEntry.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        editTimePicker.setText(hourOfDay + " : "+ minute);
                    }
                },hr,min,true);
                timePickerDialog.setTitle("Select time");
                timePickerDialog.show();
            }
        });
    }
    private void updateDate()
    {
        EditText edittext= (EditText) findViewById(R.id.date);
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        edittext.setText(sdf.format(myCalendar.getTime()));
    }
    public void add(View v)
    {
        Intent i = new Intent(this,Insemination.class);
        startActivity(i);
    }
}
