package com.example.chms;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class OwnerRegistration extends AppCompatActivity {
    private double latitude, longitude;
    private EditText txtLatitude, txtLongitude;
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

    }
    public void getStartedBtn(View v)
    {
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
