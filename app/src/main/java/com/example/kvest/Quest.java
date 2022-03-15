package com.example.kvest;

import com.geo.GeoPoint;

public class Quest {
    private String name;
    private String description;
    private GeoPoint geoPoint;

    public Quest(String newName, String newDescription, GeoPoint geoPoint)
    {
        this.name = newName;
        this.description = newDescription;
        this.geoPoint = geoPoint;
    }

    public String getName(){
        return name;
    }

    public String getDescription()
    {
        return description;
    }
    public double getLatitude(){
        return geoPoint.latitude;
    }
    public double getLongitude()
    {
        return geoPoint.longitude;
    }
}
