package com.example.nik.getlocapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class CurrentLocation extends AppCompatActivity {
    private FusedLocationProviderClient fusedLocationProviderClient;
    TextView longitudeTextView;
    TextView latitudeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_location);
        Intent intent = getIntent();
        longitudeTextView = findViewById(R.id.longitudeTextView);
        latitudeTextView = findViewById(R.id.latitudeTextView);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                              String userLongitude = String.valueOf(location.getLongitude());
                              String userLatitude = String.valueOf(location.getLatitude());
                              longitudeTextView.setText(userLongitude);
                              latitudeTextView.setText(userLatitude);
                              //if(location!= null){
                                //  Toast.makeText(CurrentLocation.this, "Location is not available",Toast.LENGTH_LONG).show();
                              //}
                        }
                    });
            return;
        }else{
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            String userLongitude = String.valueOf(location.getLongitude());
                            String userLatitude = String.valueOf(location.getLatitude());
                            longitudeTextView.setText("Longitude: " +userLongitude);
                            latitudeTextView.setText("Latitude:  "+userLatitude);
                            //if(location!= null){
                              //  Toast.makeText(CurrentLocation.this, "Location is not available",Toast.LENGTH_LONG).show();
                            //}
                        }
                    });
        }

    }
}
