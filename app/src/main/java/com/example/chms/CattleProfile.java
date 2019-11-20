package com.example.chms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.CollapsingToolbarLayout;

public class CattleProfile extends AppCompatActivity {
    TextView c_id,c_name,c_policy,age,weight,noOfChild,motherId,fatherId,breed,status,nextHeat;
    ImageView c_image;
    CollapsingToolbarLayout c_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cattle_profile);

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
        motherId = (TextView)findViewById(R.id.mother_id);
        fatherId = (TextView)findViewById(R.id.father_id);
        breed = (TextView)findViewById(R.id.cattle_breed);
        status = (TextView)findViewById(R.id.cattle_status);
        nextHeat = (TextView)findViewById(R.id.cattle_next_heat);
        c_title = (CollapsingToolbarLayout)findViewById(R.id.cattle_profile_title);
        c_image = (ImageView)findViewById(R.id.cattle_image);
        BitmapFactory.Options bitmapFactory = new BitmapFactory.Options();
        bitmapFactory.inSampleSize = 10;
        Bitmap bitmap;
        cursor.moveToFirst();
        while(cursor.isAfterLast()==false) {
            bitmap = BitmapFactory.decodeFile(cursor.getString(cursor.getColumnIndex("cattle_image")));
            c_image.setImageBitmap(bitmap);
            c_title.setTitle("Cattle id : "+cursor.getString(cursor.getColumnIndex("cuin")));
            c_id.setText("Cattle id : "+cursor.getString(cursor.getColumnIndex("cuin")));
            c_name.setText("Cattle name : "+cursor.getString(cursor.getColumnIndex("cattle_name")));
            c_policy.setText("Cattle policy : "+cursor.getString(cursor.getColumnIndex("cattle_policy")));
            age.setText("cattle age : "+cursor.getString(cursor.getColumnIndex("age")));
            weight.setText("Cattle weight : "+cursor.getString(cursor.getColumnIndex("weight")));
            noOfChild.setText("No of child : "+cursor.getString(cursor.getColumnIndex("no_of_child")));
            motherId.setText("Mother id : "+cursor.getString(cursor.getColumnIndex("mother_id")));
            fatherId.setText("Father id : "+cursor.getString(cursor.getColumnIndex("father_id")));
            breed.setText("Cattle breed : "+cursor.getString(cursor.getColumnIndex("breed")));
            status.setText("Cattle status : "+cursor.getString(cursor.getColumnIndex("status")));
            nextHeat.setText("Next heat on : "+cursor.getString(cursor.getColumnIndex("last_heat_on")));

             cursor.moveToNext();
        }
    }
    public void insemination(View v)
    {
        Intent i = new Intent(this,Insemination.class);
        startActivity(i);
    }
    public void milkProduction(View v)
    {
        Intent i = new Intent(this,MilkProduction.class);
        startActivity(i);
    }
}
