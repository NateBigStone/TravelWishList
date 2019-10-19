package com.nathan.travelwishlist;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Place.class}, version = 1, exportSchema = false)
public abstract class PlaceDatabase extends RoomDatabase {

    private static volatile PlaceDatabase INSTANCE;

    public abstract TravelDAO travelDAO();

    static PlaceDatabase getDatabase(final Context context) {

        if(INSTANCE == null) {
            synchronized (PlaceDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), PlaceDatabase.class, "Place").build();
                }
            }
        }
        return INSTANCE;
    }


}
