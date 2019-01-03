package com.example.nik.getlocapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
     ImageView googleMapImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        googleMapImage = findViewById(R.id.googleMapImageID);
    }

    public void displayGoogleMap(View view){
        Intent intent = new Intent(this,MapsActivity.class);
        startActivity(intent);
    }
    public void displayNews(View view){
        Intent intent = new Intent(this,NewsApp.class);
        startActivity(intent);
    }
    public void displayCurrentLocation(View view){
        Intent intent = new Intent(this,CurrentLocation.class);
        startActivity(intent);
    }
    public void displayMixedContent(View View){
        Intent intent = new Intent(this,MixedMap.class);
        startActivity(intent);
    }


    public void displayNearByRestaurant(View view){
        Intent intent = new Intent(this,RestaurantMap.class);
        startActivity(intent);
    }





}
