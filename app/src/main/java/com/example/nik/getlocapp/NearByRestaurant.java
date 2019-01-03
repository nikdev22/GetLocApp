package com.example.nik.getlocapp;

import android.os.AsyncTask;
import android.provider.ContactsContract;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class NearByRestaurant extends AsyncTask<Object,String,String> {
    private String googlePlaceData,url;
    private GoogleMap mMap;

    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap) objects[0];
        url = (String) objects[1];
        ExtractUrl extractUrl = new ExtractUrl();
        try {
            googlePlaceData = extractUrl.ReadUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return googlePlaceData;
    }

    @Override
    protected void onPostExecute(String s) {
        List<HashMap<String,String>> nearByPlaceList = null;
        DataTransfer dataTransfer = new DataTransfer();
        nearByPlaceList = dataTransfer.parse(s);
        DisplayNearbyPlaces(nearByPlaceList);
    }

    private  void DisplayNearbyPlaces(List<HashMap<String,String>> nearByPlacesList){
        for(int i = 0 ; i < nearByPlacesList.size(); i++){
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String,String> googleNearByPlace = nearByPlacesList.get(i);
            String nameOfPlace = googleNearByPlace.get("place_name");
            String vicinityOfPlace = googleNearByPlace.get("vicinity");
            Double latitudeOfPlace = Double.parseDouble(googleNearByPlace.get("latitude"));
            Double longitudeOfPlace = Double.parseDouble(googleNearByPlace.get("longitude"));

            LatLng latLng = new LatLng(latitudeOfPlace,longitudeOfPlace);
            markerOptions.position(latLng);
            markerOptions.title(nameOfPlace+ ":" + vicinityOfPlace);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(13));

        }
    }

}
