package com.example.windtime3;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "places")
public class Place {

    @PrimaryKey
    public long id;

    String name;
    Double lat;
    Double lng;


    public Place(long id, String name, double lat, double lng) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }

    @Override
   public String toString() {
        return name;
    }

}
