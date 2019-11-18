package com.example.chms;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.BitSet;

public class AddCattle extends AppCompatActivity {
    private Button btnCaptureImage;

    private EditText txtUcin,txtcattleName,txtPolicy,txtAge,txtWeight,txtNoOfChild,txtFatherId,txtMotherId;
    private Spinner spnBreed,spnStatus;
    private Bitmap bmpCapturedImage;
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

        txtUcin = findViewById(R.id.ucin);
        txtcattleName = findViewById(R.id.cattle_name);
        txtPolicy = findViewById(R.id.policy);
        txtAge = findViewById(R.id.age);
        txtWeight = findViewById(R.id.weight);
        txtNoOfChild = findViewById(R.id.no_of_child);
        txtFatherId = findViewById(R.id.father);
        txtMotherId = findViewById(R.id.mother);
        spnStatus = findViewById(R.id.status);
        spnBreed = findViewById(R.id.breed);


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

        String motherId = txtMotherId.getText().toString();
        String fatherId = txtFatherId.getText().toString();
        String policy = txtPolicy.getText().toString();
        String noOfChild = txtNoOfChild.getText().toString();
        if(motherId.isEmpty())
            motherId = "0";
        if(fatherId.isEmpty())
            fatherId = "0";
        if(policy.isEmpty())
            policy = "0";
        if(noOfChild.isEmpty())
            noOfChild = "0";

        ContentValues values = new ContentValues();
        values.put("cuin",Integer.parseInt(txtUcin.getText().toString()));
        values.put("cattle_name",txtcattleName.getText().toString());
        values.put("cattle_policy",Integer.parseInt(policy));
        values.put("no_of_child",Integer.parseInt(noOfChild));
        values.put("mother_id",Integer.parseInt(motherId));
        values.put("father_id",Integer.parseInt(fatherId));
        values.put("age",Integer.parseInt(txtAge.getText().toString()));
        values.put("weight",Double.parseDouble(txtWeight.getText().toString()));
        values.put("breed",spnBreed.getSelectedItem().toString());
        values.put("status",spnStatus.getSelectedItem().toString());

        String imageFileName = getImageOutputFile(txtUcin.getText().toString());
        values.put("cattle_image",imageFileName);
        CHMSDatabase dbHelper = new CHMSDatabase(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long count = db.insert("cattle_profile",null,values);
        if(count > 0){
            Toast.makeText(this, "Cattle Details Added Successfull", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,CattleList.class);
            startActivity(intent);
        }else{
            Toast.makeText(this, "Already Exist", Toast.LENGTH_SHORT).show();
        }
        db.close();

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(imageFileName);
            bmpCapturedImage.compress(Bitmap.CompressFormat.JPEG,100,out);
            bmpCapturedImage.recycle();
            bmpCapturedImage = null;
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
        }

    }

    private String getImageOutputFile(String ucin) {
        String rootDuirectory = Environment.getExternalStorageDirectory().getAbsolutePath();
        File imageFilePath = new File(rootDuirectory+"/chms");
        if (!imageFilePath.exists())
        {
            imageFilePath.mkdirs();
        }
        File imageFile = new File(imageFilePath,"Img-"+ucin+".jpg");
        return imageFile.getAbsolutePath();
    }

    public void capturePhoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,1001);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1001 && resultCode == RESULT_OK)
        {
            bmpCapturedImage = (Bitmap) data.getExtras().get("data");
            Toast.makeText(this, "Image Captured", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Failed captureImage", Toast.LENGTH_SHORT).show();
        }
    }
}
