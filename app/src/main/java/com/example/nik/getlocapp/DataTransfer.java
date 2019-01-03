package com.example.nik.getlocapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataTransfer {


    private HashMap<String,String> getPlace(JSONObject googlePlaceJSON){
        HashMap<String,String> googlePlaceMap = new HashMap<>();
        String NameOfPlace = "-NA-";
        String vicinty = "-NA-";
        String latitude = "";
        String longitude = "";
        String reference = "";

        try {
            if(!googlePlaceJSON.isNull("name")){
                NameOfPlace = googlePlaceJSON.getString("name");

            }
            if(!googlePlaceJSON.isNull("vicinity")){
                vicinty = googlePlaceJSON.getString("vicinity");
            }
            latitude = googlePlaceJSON.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude = googlePlaceJSON.getJSONObject("geometry").getJSONObject("location").getString("lng");
            reference = googlePlaceJSON.getString("reference");


            googlePlaceMap.put("place_name", NameOfPlace);
            googlePlaceMap.put("vicinity", vicinty);
            googlePlaceMap.put("latitude", latitude);
            googlePlaceMap.put("longitude", longitude);
            googlePlaceMap.put("reference", reference);



        } catch (JSONException e) {
            e.printStackTrace();
        }
    return googlePlaceMap;

    }





    private List<HashMap<String,String>> getAllNearByPlaces(JSONArray jsonArray){
        int counter = jsonArray.length();
        List<HashMap<String,String>> nearByPlacesList = new ArrayList<>();
        HashMap<String,String> nearByPlaceMap = null;
        for(int i = 0 ; i < counter; i++){
           try {
               nearByPlaceMap = getPlace((JSONObject) jsonArray.get(i));
               nearByPlacesList.add(nearByPlaceMap);
           }
           catch (JSONException e){
               e.printStackTrace();
           }
        }
        return nearByPlacesList;
    }



    public List<HashMap<String,String>> parse(String jSONData){
        JSONArray jsonArray = null;
        JSONObject jsonObject ;
        try {
            jsonObject = new JSONObject(jSONData);
            jsonArray = jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getAllNearByPlaces(jsonArray);

    }





}
