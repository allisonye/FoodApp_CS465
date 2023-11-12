package edu.illinois.cs465.myquizappwithlifecycle.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FoodListingDao {
    @Insert
    void insertAll(FoodListing ... foodListings);

    @Delete
    void delete(FoodListing foodListing);

    @Query("SELECT * FROM FoodListing")
    List<FoodListing> getAll();
}
