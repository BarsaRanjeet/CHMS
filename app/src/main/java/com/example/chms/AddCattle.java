package com.example.chms;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddCattle extends AppCompatActivity {
    private Button btnCaptureImage;

    final Calendar myCalendar = Calendar.getInstance();
    private EditText txtUcin,txtcattleName,txtPolicy,txtAge,txtWeight,txtNoOfChild,txtFatherId,txtMotherId,txtBirthDate;
    private Spinner spnBreed,spnStatus,spnCattletype,spnBreedingStatus;
    private Bitmap bmpCapturedImage;
    private String c_year;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cattle);


        EditText datePicker= (EditText) findViewById(R.id.birth_date);
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
                new DatePickerDialog(AddCattle.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddCattle.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        txtUcin = findViewById(R.id.ucin);
        txtcattleName = findViewById(R.id.cattle_name);
        txtPolicy = findViewById(R.id.policy);
        txtAge = findViewById(R.id.age);
        txtWeight = findViewById(R.id.weight);
        txtNoOfChild = findViewById(R.id.no_of_child);
        txtFatherId = findViewById(R.id.father);
        txtMotherId = findViewById(R.id.mother);
        txtBirthDate = findViewById(R.id.birth_date);
        //txtLastHeatDate = findViewById(R.id.last_heat_date);
        spnStatus = findViewById(R.id.status);
        spnBreed = findViewById(R.id.breed);
        //spnGender = findViewById(R.id.cattle_gender);


        btnCaptureImage = findViewById(R.id.capture_image);
        btnCaptureImage = findViewById(R.id.capture_image);
        spnCattletype  = (Spinner)findViewById(R.id.cattle_type);
        spnBreed = findViewById(R.id.breed);
        spnBreedingStatus = findViewById(R.id.breeding_status);

        String [] cattleTypes = getResources().getStringArray(R.array.cattle_types);
        ArrayAdapter<String> spnCattletypeAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,cattleTypes);
       spnCattletypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCattletype.setAdapter(spnCattletypeAdapter);
        spnCattletype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedType = spnCattletype.getSelectedItem().toString();

                if(selectedType.equals("Buffalo Bull") || selectedType.equals("Bull"))
                {
                    spnBreedingStatus.setVisibility(View.GONE);
                    spnStatus.setVisibility(View.GONE);
                }else{
                    spnBreedingStatus.setVisibility(View.VISIBLE);
                    spnStatus.setVisibility(View.VISIBLE);
                }
                String breedList[] = null;
                if(selectedType.equals("Cow") || selectedType.equals("Bull")){
                    breedList = getResources().getStringArray(R.array.cow_breed);
                } else if (selectedType.equals("Buffalo") || selectedType.equals("Buffalo Bull")) {
                    breedList = getResources().getStringArray(R.array.buffalo_breed);
                }
                String[] breeds = null;
                if(breedList != null){

                    breeds = new String[breedList.length+1];
                    breeds[0] = "Select Breed";
                    for(int i=0;i< breedList.length;i++){
                        breeds[i+1] = breedList[i];
                    }
                }else{
                    breeds = new String[1];
                    breeds[0] = "Select Breed";
                }
                ArrayAdapter<String> breedAdapter = new ArrayAdapter<String>(AddCattle.this,android.R.layout.simple_spinner_dropdown_item,breeds);
                breedAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                spnBreed.setAdapter(breedAdapter);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



      //  ArrayAdapter<String> statusAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,status);
        //Spinner statusSpinner = (Spinner)findViewById(R.id.status);
        //statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //statusSpinner.setAdapter(statusAdapter);
    }

    private void updateDate()
    {
        EditText edittext= (EditText) findViewById(R.id.birth_date);
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        edittext.setText(sdf.format(myCalendar.getTime()));
        String birth = sdf.format(myCalendar.getTime());
        int day = Integer.parseInt(birth.substring(0,2));
        int month = Integer.parseInt(birth.substring(3,5));
        int year = Integer.parseInt(birth.substring(6));
        Calendar calendar = Calendar.getInstance();
        int c_year = calendar.get(Calendar.YEAR);
        int c_day = calendar.get(Calendar.DATE);
        int c_month = calendar.get(Calendar.DATE) + 1;
        int age_year;
        age_year = (c_year - year)-1;
        if(c_month >= month)
        {
            if(c_day>=day)
                age_year += 1;
        }
        EditText age = (EditText)findViewById(R.id.age);
        age.setText(""+age_year);
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
        values.put("cattle_type",spnCattletype.getSelectedItem().toString());
        values.put("date_of_birth",txtBirthDate.getText().toString());
        values.put("breed",spnBreed.getSelectedItem().toString());
        values.put("status",spnStatus.getSelectedItem().toString());
        values.put("breeding_status",spnBreedingStatus.getSelectedItem().toString());
        values.put("last_heat_on","");
        String imageFileName = getImageOutputFile(txtUcin.getText().toString());
        values.put("cattle_image",getImageOutputFile(txtUcin.getText().toString()));
        CHMSDatabase dbHelper = new CHMSDatabase(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long count = db.insert("cattle_profile",null,values);
        if(count > 0){
            Toast.makeText(this, "Cattle Details Added Successfull", Toast.LENGTH_SHORT).show();
            if(spnCattletype.getSelectedItem().toString().equals("Buffalo Bull") || spnCattletype.getSelectedItem().toString().equals("Bull")  || spnBreedingStatus.getSelectedItem().toString().equals("Immature")){
                Intent intent = new Intent(this,CattleList.class);
                startActivity(intent);
            }else {

                Intent intent = new Intent(this,CreateHeatProfile.class);
                Cattle cattle = new Cattle();

                cattle.setUcin(Integer.parseInt(txtUcin.getText().toString()));
                cattle.setAge(Integer.parseInt(txtAge.getText().toString()));
                cattle.setBreed(spnBreed.getSelectedItem().toString().trim());
                cattle.setCattleType(spnCattletype.getSelectedItem().toString());
                cattle.setStatus(spnStatus.getSelectedItem().toString());
                cattle.setBreedingStatus(spnBreedingStatus.getSelectedItem().toString());

                intent.putExtra("cattle",cattle);
                startActivity(intent);
            }
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
