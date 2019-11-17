package com.example.chms;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.BitSet;

public class AddCattle extends AppCompatActivity {
    private Button btnCaptureImage;

    private Bitmap capturedimage;
    String[] breed = {"Select breed",
            "Gir",
            "Surti",
            "Jaffrabadi",
            "Mahesana"
    };
    String[] status = {"Select status",
            "Milking",
            "Non-Milking",
            "Pregnant",
            "Non-well",
            "Under observation"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cattle);


        btnCaptureImage = findViewById(R.id.capture_image);

        ArrayAdapter<String> breedAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,breed);
        Spinner breedSpinner = (Spinner)findViewById(R.id.breed);
        breedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        breedSpinner.setAdapter(breedAdapter);

        ArrayAdapter<String> statusAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,status);
        Spinner statusSpinner = (Spinner)findViewById(R.id.status);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(statusAdapter);
    }
    public void addCattle(View v)
    {
        Intent i = new Intent(this,CattleList.class);
        startActivity(i);
    }

    public void capturePhoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        capturedimage = (Bitmap) data.getExtras().get("data");

        Toast.makeText(this, "Image Captured", Toast.LENGTH_SHORT).show();
    }
}
