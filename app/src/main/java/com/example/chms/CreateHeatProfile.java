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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
        Cattle cattle = (Cattle)intent.getSerializableExtra("cattle");
        int cuin = cattle.getUcin();

        radioGroup = findViewById(R.id.insemination);
        txtLastHeatDate = findViewById(R.id.last_heat_date);

        try {
            cattle.setLastHeatDate(new SimpleDateFormat("dd/MM/yyyy").parse(txtLastHeatDate.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int checked = radioGroup.getCheckedRadioButtonId();

        String insemination = (checked == R.id.insemination_done) ? "Yes" : "No";
        ContentValues values = new ContentValues();

        values.put("cuin",cuin);
        values.put("last_heat_date",txtLastHeatDate.getText().toString());
        values.put("actual_heat_date",txtLastHeatDate.getText().toString());
        values.put("insemination_status",insemination);
        values.put("predicted_next_heat_date","");

        CHMSDatabase dbHelper = new CHMSDatabase(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.insert("heat_table",null,values);


        ContentValues updateValues = new ContentValues();
        updateValues.put("last_heat_on",txtLastHeatDate.getText().toString());
        db.update("cattle_profile",updateValues,"cuin=?",new String[]{String.valueOf(cuin)});



        ContentValues predictedValue = new ContentValues();
        predictedValue.put("cuin",cuin);
        predictedValue.put("last_heat_date",txtLastHeatDate.getText().toString());

        try {
            cattle.setLastHeatDate(new SimpleDateFormat("dd/MM/yyyy").parse(txtLastHeatDate.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String predictedHeatDate = new SimpleDateFormat("dd/MM/yyyy").format(HeatPrediction.nextHeatCalculate(this,cattle));

        predictedHeatDate = (predictedHeatDate == null)?"":predictedHeatDate;

        predictedValue.put("predicted_next_heat_date",predictedHeatDate);
        predictedValue.put("insemination_status","");
        predictedValue.put("actual_heat_date","");
        long count = db.insert("heat_table",null,predictedValue);
        if(count>0){
            Toast.makeText(this, "Heat profile Successfully Created", Toast.LENGTH_SHORT).show();
            Intent intentCattleList = new Intent(this,CattleList.class);
            startActivity(intentCattleList);
        }else{
            Toast.makeText(this, "Failed to create Heat profile", Toast.LENGTH_SHORT).show();
        }
        db.close();

    }
//
//    public Date nextHeatCalculate(Cattle cattle)
//    {
//        if(!cattle.getBreedingStatus().equals("Normal"))
//            return null;
//        Date lastHeatDate = cattle.getLastHeatDate();
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(lastHeatDate);
//        List<String> breeds = null;
//        if(cattle.getCattleType().equals("Cow"))
//        {
//            breeds = Arrays.asList(getResources().getStringArray(R.array.cow_breed));
//        }
//        else if(cattle.getCattleType().equals("Buffalo"))
//        {
//            breeds = Arrays.asList(getResources().getStringArray(R.array.buffalo_breed));
//        }
//
//        int pos  = breeds.indexOf(cattle.getBreed());
//        int heatPeriod = 0;
//        if(cattle.getCattleType().equals("Cow"))
//        {
//            List<String> heatPeriods = Arrays.asList(getResources().getStringArray(R.array.cow_breed_avg_heat_period));
//            heatPeriod = Integer.parseInt(heatPeriods.get(pos));
//        }
//        else if(cattle.getCattleType().equals("Buffalo"))
//        {
//            List<String> heatPeriods = Arrays.asList(getResources().getStringArray(R.array.buffalo_breed_avg_heat_period));
//            heatPeriod = Integer.parseInt(heatPeriods.get(pos));
//        }
//        cal.add(Calendar.DATE,heatPeriod);
//       return cal.getTime();
//    }


}
