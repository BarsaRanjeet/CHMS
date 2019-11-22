package com.example.chms;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class InseminationNewEntry extends AppCompatActivity {

    final Calendar myCalendar = Calendar.getInstance();
    final Calendar myCurrentTime = Calendar.getInstance();
    String date,time,cattleId,heatId;
    EditText note,cuin;
    Spinner spin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insemination_new_entry);

        Intent i = getIntent();
        cattleId = i.getStringExtra("cattleId");
        heatId = i.getStringExtra("hId");
        cuin = (EditText)findViewById(R.id.cattleId);
        cuin.setText(cattleId);
        String[] type = {"Select insemination type","Naturally","Artificially"};
        spin = (Spinner)findViewById(R.id.type);
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
                        editTimePicker.setText(hourOfDay + ":"+ minute);
                        time = ""+(hourOfDay + ":"+ minute);
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
        date = ""+sdf.format(myCalendar.getTime());
    }
    public void add(View v)
    {
        note = (EditText)findViewById(R.id.note);
        CHMSDatabase sqliteDatabase = new CHMSDatabase(this);
        SQLiteDatabase db = sqliteDatabase.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("cuin",cattleId);
        values.put("h_id",heatId);
        values.put("date",date);
        values.put("time",time);
        values.put("type",spin.getSelectedItem().toString());
        values.put("note",note.getText().toString());
        db.insert("insemination",null,values);

        ContentValues updateValues = new ContentValues();
        updateValues.put("insemination_status","Yes");
        db.update("heat_table",updateValues,"h_id=?",new String[]{heatId});

        Toast.makeText(getApplicationContext(),"Successfully Updated insemination detail",Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this,Insemination.class);
        i.putExtra("cattleId",cattleId);
        startActivity(i);
    }
}
