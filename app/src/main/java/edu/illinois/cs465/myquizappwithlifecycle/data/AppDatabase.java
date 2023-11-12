package edu.illinois.cs465.myquizappwithlifecycle.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {FoodListing.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FoodListingDao foodListingDao();
}
