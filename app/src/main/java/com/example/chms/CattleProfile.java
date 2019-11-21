package com.example.chms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CattleProfile extends AppCompatActivity {
    TextView c_id,c_name,c_policy,age,weight,noOfChild,motherId,fatherId,breed,status,nextHeat,txtCattleType,txtBreedingStatus;
    Button btnInseminationDetails,btnHeatDetails,btnMilkProduction;

    ImageView c_image;
    CollapsingToolbarLayout c_title;
    private String cattle_id,c_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cattle_profile);

        btnHeatDetails = findViewById(R.id.heat_details);
        btnInseminationDetails = findViewById(R.id.insemination_details);
        btnMilkProduction = findViewById(R.id.milk_production);



        Intent i = getIntent();
        String id = i.getStringExtra("cattleId");
        CHMSDatabase chmsHelper = new CHMSDatabase(this);
        SQLiteDatabase db = chmsHelper.getReadableDatabase();
        String[] columns = {};
        Cursor cursor = db.query("cattle_profile",columns,"cuin=?",new String[]{id},null,null,null);

        c_id = (TextView)findViewById(R.id.cattle_cuin);
        c_name = (TextView)findViewById(R.id.cattle_name);
        c_policy = (TextView)findViewById(R.id.cattle_policy);
        age = (TextView)findViewById(R.id.cattle_age);
        weight = (TextView)findViewById(R.id.cattle_weight);
        noOfChild = (TextView)findViewById(R.id.cattle_child);
       /* motherId = (TextView)findViewById(R.id.mother_id);
        fatherId = (TextView)findViewById(R.id.father_id);*/
        breed = (TextView)findViewById(R.id.cattle_breed);
        status = (TextView)findViewById(R.id.cattle_status);
        nextHeat = (TextView)findViewById(R.id.cattle_next_heat);
        c_title = (CollapsingToolbarLayout)findViewById(R.id.cattle_profile_title);
        c_image = (ImageView)findViewById(R.id.cattle_image);
        txtCattleType = findViewById(R.id.cattle_type);
        txtBreedingStatus = findViewById(R.id.breeding_status);
        BitmapFactory.Options bitmapFactory = new BitmapFactory.Options();
        bitmapFactory.inSampleSize = 10;
        Bitmap bitmap;
        cursor.moveToFirst();
        while(cursor.isAfterLast()==false) {
            bitmap = BitmapFactory.decodeFile(cursor.getString(cursor.getColumnIndex("cattle_image")));
            c_image.setImageBitmap(bitmap);
           // c_title.setTitle("Cattle id : "+cursor.getString(cursor.getColumnIndex("cuin")));
            int ucin = cursor.getInt(cursor.getColumnIndex("cuin"));
            c_id.setText("UCIN No : "+ucin);
            String cattleType = cursor.getString(cursor.getColumnIndex("cattle_type"));
            txtCattleType.setText("Cattle : "+cattleType);
            c_name.setText("Name : "+cursor.getString(cursor.getColumnIndex("cattle_name")));
            String policyNumber =  cursor.getString(cursor.getColumnIndex("cattle_policy")).equals("0") ? "No policy" : cursor.getString(cursor.getColumnIndex("cattle_policy"));
            c_policy.setText("Policy No : "+policyNumber);
            int ageInt = cursor.getInt(cursor.getColumnIndex("age") );
            age.setText("Age : "+ageInt+" Years");
            weight.setText("Weight : "+cursor.getString(cursor.getColumnIndex("weight"))+" KG");
            noOfChild.setText("No of child : "+cursor.getString(cursor.getColumnIndex("no_of_child")));
//            motherId.setText("Mother id : "+cursor.getString(cursor.getColumnIndex("mother_id")));
//            fatherId.setText("Father id : "+cursor.getString(cursor.getColumnIndex("father_id")));
            String breedStr = cursor.getString(cursor.getColumnIndex("breed"));
            breed.setText("Breed : "+breedStr);

            String breedingStatus = cursor.getString(cursor.getColumnIndex("breeding_status"));
            String milkingStatus = cursor.getString(cursor.getColumnIndex("status"));
            status.setText("Milking Status : "+milkingStatus);
            txtBreedingStatus.setText("Breeding status : "+breedingStatus);

            Cattle cattle = new Cattle();
            cattle.setUcin(ucin);
            cattle.setCattleType(cattleType);
            cattle.setBreed(breedStr);
            cattle.setBreedingStatus(breedingStatus);
            cattle.setStatus(milkingStatus);
            cattle.setAge(ageInt);
            String lastHeatDate = cursor.getString(cursor.getColumnIndex("last_heat_on"));
            try {
                cattle.setLastHeatDate(new SimpleDateFormat("dd/MM/yyyy").parse(lastHeatDate));
            } catch (ParseException e) {
            }


            c_status = cursor.getString(cursor.getColumnIndex("status"));
            cattle_id = cursor.getString(cursor.getColumnIndex("cuin"));

            if(cattleType.equals("Buffalo Bull") || cattleType.equals("Bull") || breedingStatus.equals("Immature")) {
                if (cattleType.equals("Buffalo Bull") || cattleType.equals("Bull")) {
                    txtBreedingStatus.setVisibility(View.GONE);
                    status.setVisibility(View.GONE);
                }
                noOfChild.setVisibility(View.GONE);
                nextHeat.setVisibility(View.GONE);
                status.setVisibility(View.GONE);
                btnInseminationDetails.setVisibility(View.GONE);
                btnMilkProduction.setVisibility(View.GONE);
                btnHeatDetails.setVisibility(View.GONE);
            }else{
                nextHeat.setText("Next heat on : "+new SimpleDateFormat("dd/MM/yyyy").format(HeatPrediction.nextHeatCalculate(this,cattle)));
            }




             cursor.moveToNext();
        }
        Button heatDetailsBtn = (Button)findViewById(R.id.heat_details);
        if(c_status.equals("Immature"))
        {
            heatDetailsBtn.setVisibility(View.INVISIBLE);
        }
        else
        {
            heatDetailsBtn.setVisibility(View.VISIBLE);
        }
    }
    public void insemination(View v)
    {
        Intent i = new Intent(this,Insemination.class);
        i.putExtra("cattleId",cattle_id);
        startActivity(i);
    }
    public void milkProduction(View v)
    {
        Intent i = new Intent(this,MilkProduction.class);
        startActivity(i);
    }
    public void heatDetails(View v)
    {
        Intent i = new Intent(this,HeatDetails.class);
        i.putExtra("cattleId",cattle_id);
        startActivity(i);
    }
}
