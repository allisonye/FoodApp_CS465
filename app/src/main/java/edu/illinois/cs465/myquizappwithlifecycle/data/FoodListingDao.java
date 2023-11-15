package edu.illinois.cs465.myquizappwithlifecycle.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FoodListingDao {
    @Insert
    void insert(FoodListing foodListing);

    @Update
    void update(FoodListing foodListing);

    @Delete
    void delete(FoodListing foodListing);

    @Query("SELECT * FROM FoodListing")
    LiveData<List<FoodListing>> getAll();
}
