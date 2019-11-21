package com.example.chms;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateHeatProfile extends AppCompatActivity {

    private RadioGroup radioGroup;
    private EditText txtLastHeatDate;
    Calendar myCalendar =Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_heat_profile);

        EditText datePicker= (EditText) findViewById(R.id.last_heat_date);
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
                new DatePickerDialog(CreateHeatProfile.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CreateHeatProfile.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    }

    private void updateDate()
    {
        EditText edittext= (EditText) findViewById(R.id.last_heat_date);
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        edittext.setText(sdf.format(myCalendar.getTime()));
    }

    public void createHeatProfile(View view) {
        Intent intent = getIntent();
        String cuin = intent.getStringExtra("ucin");
        radioGroup = findViewById(R.id.insemination);
        txtLastHeatDate = findViewById(R.id.last_heat_date);
        int checked = radioGroup.getCheckedRadioButtonId();
        String insemination = (checked == R.id.insemination_done) ? "Yes" : "No";
        ContentValues values = new ContentValues();
        values.put("cuin",cuin);
        values.put("last_heat_date",txtLastHeatDate.getText().toString());
        values.put("insemination_status",insemination);
        values.put("predicted_next_heat_date","");
        CHMSDatabase dbHelper = new CHMSDatabase(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long count = db.insert("heat_table",null,values);
        if(count>0){
            Toast.makeText(this, "Heat profile Successfully Created", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Failed to create Heat profile", Toast.LENGTH_SHORT).show();
        }
        ContentValues updateValues = new ContentValues();
        updateValues.put("last_heat_on",txtLastHeatDate.getText().toString());
        db.update("cattle_profile",updateValues,"cuin=?",new String[]{cuin});
        db.close();
    }
}
