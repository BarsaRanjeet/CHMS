package com.example.chms;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class OwnerRegistration extends AppCompatActivity {
    private double latitude, longitude;
    private EditText txtLatitude, txtLongitude,txtName,txtAddress,txtContact,txtAdhar,txtPincode;
    private LocationManager mLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_registration);
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60,
                20, mLocationListener);
        txtLatitude = findViewById(R.id.lat);
        txtLongitude = findViewById(R.id.lon);
        txtName = findViewById(R.id.name);
        txtAddress = findViewById(R.id.address);
        txtAdhar = findViewById(R.id.aadhar);
        txtContact = findViewById(R.id.contact);
        txtPincode = findViewById(R.id.pincode);
    }
    public void getStartedBtn(View v)
    {
        CHMSDatabase dbHelper = new CHMSDatabase(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", String.valueOf(txtName.getText()));
        values.put("address", String.valueOf(txtAddress.getText()));
        values.put("contact_no", String.valueOf(txtContact.getText()));
        values.put("adhar_no", String.valueOf(txtAdhar.getText()));
        values.put("pincode", String.valueOf(txtAdhar.getText()));

        long count = db.insert("owner_profile",null,values);
        if(count > 0)
        {
            Toast.makeText(this, "Failed to insert", Toast.LENGTH_SHORT).show();
        }
        Intent i = new Intent(this,CattleList.class);
        startActivity(i);

    }
    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            txtLatitude.setText(latitude+"");
            txtLongitude.setText(longitude+"");
            Toast.makeText(OwnerRegistration.this, ""+latitude+" "+longitude, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
}
