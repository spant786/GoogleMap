package com.example.googlemapproject;

public class Place {
    private double lat;
    private double lon;
    private String name;

    public Place(double lat, double lon, String name) {
        this.lat = lat;
        this.lon = lon;
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getName() {
        return name;
    }
}