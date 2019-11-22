package com.example.chms;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HeatDetails extends AppCompatActivity{

    public static String[] probableHeatDates = null;
    public static String[] actualHeatDates = null;
    public static String[] inseminations = null;
    private TextView txtCurrentProbableDate;
    private Button btnChange;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heat_details);

        txtCurrentProbableDate = findViewById(R.id.current_probable);
        btnChange = findViewById(R.id.btn_heat_today);

        Intent intent = getIntent();
        final String cattleId = intent.getStringExtra("cattleId");

        final CHMSDatabase dbHelper = new CHMSDatabase(this);
        final SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursorcatle = db.query("cattle_profile",new String[]{},"cuin=?",new String[]{cattleId},null,null,null);
        cursorcatle.moveToFirst();

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        final Cattle cattle = new Cattle();
        cattle.setUcin(cursorcatle.getInt(cursorcatle.getColumnIndex("cuin")));
        String lastHeatDate = cursorcatle.getString(cursorcatle.getColumnIndex("last_heat_on"));
        try {
            cattle.setLastHeatDate(format.parse(lastHeatDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cattle.setBreed(cursorcatle.getString(cursorcatle.getColumnIndex("breed")));
        cattle.setAge(cursorcatle.getInt(cursorcatle.getColumnIndex("age")));
        cattle.setStatus(cursorcatle.getString(cursorcatle.getColumnIndex("status")));
        cattle.setBreedingStatus(cursorcatle.getString(cursorcatle.getColumnIndex("breeding_status")));
        cattle.setCattleType(cursorcatle.getString(cursorcatle.getColumnIndex("cattle_type")));
        final Date nextHeatDate = HeatPrediction.nextHeatCalculate(this,cattle);
        txtCurrentProbableDate.setText(format.format(nextHeatDate));


        Cursor cursorHeatDetails = db.query("heat_table",new String[]{},"cuin=?",new String[]{cattleId},null,null,"h_id DESC");

        probableHeatDates = new String[cursorHeatDetails.getCount()];
        actualHeatDates = new String[cursorHeatDetails.getCount()];
        inseminations = new String[cursorHeatDetails.getCount()];
        cursorHeatDetails.moveToFirst();
        for (int i=0;i<cursorHeatDetails.getCount();i++)
        {
            if(i != 0)
            {
                probableHeatDates[i] = cursorHeatDetails.getString(cursorHeatDetails.getColumnIndex("predicted_next_heat_date"));
                actualHeatDates[i] = cursorHeatDetails.getString(cursorHeatDetails.getColumnIndex("actual_heat_date"));
                inseminations[i] = cursorHeatDetails.getString(cursorHeatDetails.getColumnIndex("insemination_status"));


            }
            cursorHeatDetails.moveToNext();
        }
        db.close();
        ListView heatDetailsList = (ListView)findViewById(R.id.heat_details);
        HeatDetailsAdapter heatDetailsAdapter = new HeatDetailsAdapter(this,probableHeatDates,actualHeatDates,inseminations);
        heatDetailsList.setAdapter(heatDetailsAdapter);

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                String actualHeatDate = format.format(new Date());
                values.put("actual_heat_date",actualHeatDate);
                values.put("insemination_status","No");

                long count = db.update("heat_table",values,"cuin=? AND predicted_next_heat_date = ?",new String[]{cattleId,txtCurrentProbableDate.getText().toString()});

                if(count > 0)
                {
                    ContentValues profileValues = new ContentValues();
                    profileValues.put("last_heat_on",actualHeatDate);
                    db.update("cattle_profile",profileValues,"cuin=?",new String[]{cattleId});

                    Toast.makeText(HeatDetails.this, "Changed", Toast.LENGTH_SHORT).show();
                    ContentValues predictedValues = new ContentValues();
                    predictedValues.put("cuin",cattleId);
                    predictedValues.put("last_heat_date",actualHeatDate);
                    try {
                        cattle.setLastHeatDate(format.parse(actualHeatDate));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Date nextPredictedHeatDate = HeatPrediction.nextHeatCalculate(HeatDetails.this,cattle);
                    predictedValues.put("predicted_next_heat_date",format.format(nextPredictedHeatDate));
                    predictedValues.put("insemination_status","");
                    predictedValues.put("actual_heat_date","");

                    count = db.insert("heat_table",null,predictedValues);

                    db.close();
                    if(count > 0){
                        Toast.makeText(HeatDetails.this, "Changed", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

    }
}
