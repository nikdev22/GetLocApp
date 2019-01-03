package com.example.nik.getlocapp;

public class NearByPlaces {
    private String name;
    private double rating;
    private String vicinity;
    private double lat;
    private double lon;
    private String openStatus;

    public NearByPlaces(String name, float rating, String vicinity, double lat, double lon,String openStatus) {
        this.name = name;
        this.rating = rating;
        this.vicinity = vicinity;
        this.lat = lat;
        this.lon = lon;
        this.openStatus = openStatus;

    }

    public String getOpenStatus() {
        return openStatus;
    }

    public void setOpenStatus(String openStatus) {
        this.openStatus = openStatus;
    }

    public NearByPlaces() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
