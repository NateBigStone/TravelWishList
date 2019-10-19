package com.nathan.travelwishlist;

import android.database.Observable;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TravelDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Place... wr);

    @Update
    void update(Place... wr);

    @Query("SELECT * FROM Place WHERE name = :name LIMIT 1")
    LiveData<Place> getRecordForName(String name);

    @Query("SELECT * FROM Place")
    LiveData<List<Place>> getAllRecords();

    @Query("SELECT COUNT(name) FROM Place")
    LiveData<Integer> getRowCount();

}