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
    TextView c_id,c_name,c_policy;
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
        c_title = (CollapsingToolbarLayout)findViewById(R.id.cattle_profile_title);
        c_image = (ImageView)findViewById(R.id.cattle_image);
        BitmapFactory.Options bitmapFactory = new BitmapFactory.Options();
        bitmapFactory.inSampleSize = 10;
        Bitmap bitmap;
        cursor.moveToFirst();
        while(cursor.isAfterLast()==false) {
            bitmap = BitmapFactory.decodeFile(cursor.getString(cursor.getColumnIndex("cattle_image")));
            c_image.setImageBitmap(bitmap);
            c_title.setTitle("cattle id : "+cursor.getString(cursor.getColumnIndex("cuin")));
            c_id.setText("cattle id : "+cursor.getString(cursor.getColumnIndex("cuin")));
            c_name.setText("cattle name : "+cursor.getString(cursor.getColumnIndex("cattle_name")));
            c_policy.setText("cattle policy : "+cursor.getString(cursor.getColumnIndex("cattle_policy")));

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
