package edu.illinois.cs465.myquizappwithlifecycle.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewModal {
    // creating a new variable for course repository.
    private FoodListingRepository foodListingRepository;

    // below line is to create a variable for live
    // data where all the courses are present.
    private LiveData<List<FoodListing>> allFoodListings;

    // constructor for our view modal.
    public ViewModal(@NonNull Application application) {
        super(); // super(application)
        foodListingRepository = new FoodListingRepository(application);
        allFoodListings = foodListingRepository.getAllCourses();
    }

    // below method is use to insert the data to our repository.
    public void insert(FoodListing foodListing) {
        foodListingRepository.insert(foodListing);
    }

    // below line is to update data in our repository.
    public void update(FoodListing foodListing) {
        foodListingRepository.update(foodListing);
    }

    // below line is to delete the data in our repository.
    public void delete(FoodListing foodListing) {
        foodListingRepository.delete(foodListing);
    }

    // below method is to get all the courses in our list.
    public LiveData<List<FoodListing>> getAllFoodListings() {
        return allFoodListings;
    }
}
