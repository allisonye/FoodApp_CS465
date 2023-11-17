package edu.illinois.cs465.myquizappwithlifecycle.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.sql.Date;
import java.util.List;

@Dao
public abstract class FoodListingDao {
    @Insert
    abstract void insert(FoodListing foodListing);

    void insertWithTimestamp(FoodListing foodListing) {
        foodListing.createdAt = new Date(System.currentTimeMillis());
        insert(foodListing);
    }

    @Update
    abstract void update(FoodListing foodListing);

    @Delete
    abstract void delete(FoodListing foodListing);

    @Query("DELETE FROM FoodListing")
    abstract void deleteAll();

    @Query("SELECT * FROM FoodListing")
    abstract LiveData<List<FoodListing>> getAll();

    @Query("SELECT * FROM FoodListing WHERE food_id=:id")
    abstract LiveData<FoodListing> getById(int id);
}
