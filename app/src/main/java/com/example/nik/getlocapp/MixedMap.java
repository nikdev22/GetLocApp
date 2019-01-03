package com.example.nik.getlocapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MixedMap extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Marker currentLocation;
    private static final int User_Location = 99;

    double latitude, longitude;
    double latitudi,longitudi;

    private int ProximityRadius = 10000;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mixed_map);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            checkUserPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent intent = getIntent();





    }
    public void displayRestaurant(double latitude,double longitude){
        String restaurant = "restaurant";
        NearByRestaurant nearByRestaurant = new NearByRestaurant();
        mMap.clear();
        String url = getUrl(latitude,longitude,restaurant);
        Object transferData[ ] = new Object[2];
        transferData[0] = mMap;
        transferData[1] = url;
        nearByRestaurant.execute(transferData);
    }


  private String getUrl(double latitude,double longitude, String restaurant){
        StringBuilder googleURL = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googleURL.append("location=" +latitude + ","+longitude);
        googleURL.append("&radius=10000&type=restaurant&sensor=true&key=YOUR_API_KEY");


         Log.d("latitudy",String.valueOf(latitude));
         Log.d("londitudy",String.valueOf(longitude));
         Log.d("lat",String.valueOf(latitude));


      Log.d("URL",googleURL.toString());
       return googleURL.toString();
     // return urli;
  }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

        // Add a marker in Sydney and move the camera
       // LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        //displayRestaurant();
    }

    public boolean checkUserPermission(){
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){
                      ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},User_Location);
                 }
                 else{
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},User_Location);

                }
                return false;
        }
        else{
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case User_Location:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                        if(googleApiClient == null){
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }
                else{
                    Toast.makeText(this,"Please allow google map to access your location",Toast.LENGTH_LONG).show();
                }
                return;

        }
    }

    protected synchronized void buildGoogleApiClient(){
        googleApiClient = new GoogleApiClient.Builder(this).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).
                addApi(LocationServices.API).build();
        googleApiClient.connect();
    }


    @Override
    public void onLocationChanged(Location location) {

        if(currentLocation != null){
            currentLocation.remove();
        }
        LatLng newLocation = new LatLng(location.getLatitude(),location.getLongitude());
        latitude = location.getLatitude();
        longitudi = location.getLongitude();
        latitudi = location.getLatitude();
        displayRestaurant(latitudi,longitudi);
        Log.d("longitudi",String.valueOf(location.getLongitude()));
        Log.d("latitudi",String.valueOf(location.getLatitude()));
        longitude = location.getLongitude();
        mMap.addMarker(new MarkerOptions().position(newLocation).title("My Current location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(newLocation));

        if(googleApiClient != null){
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient,this);

        }

    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {
     locationRequest = new LocationRequest();
     locationRequest.setInterval(1000);
     locationRequest.setFastestInterval(1000);
     locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
     if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
         LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest,this);

     }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
