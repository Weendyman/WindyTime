package com.example.windtime3;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Place.class},version = 3)
public abstract class PlaceDatabase extends RoomDatabase {
    public abstract  DaoPlace daoPlace();
}
