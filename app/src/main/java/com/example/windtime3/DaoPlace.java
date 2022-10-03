package com.example.windtime3;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.ArrayList;
import java.util.List;


@Dao
public interface DaoPlace {
 @Query("SELECT * FROM places")
 List<Place> getAll();



 @Insert(onConflict = REPLACE)
 void insert(Place place);

 @Delete
    void delete(Place place);



}
