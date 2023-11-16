package edu.illinois.cs465.myquizappwithlifecycle.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewModal extends AndroidViewModel {
    private FoodListingRepository foodListingRepository;
    private LiveData<List<FoodListing>> allFoodListings;

    public ViewModal(@NonNull Application application) {
        super(application);
        foodListingRepository = new FoodListingRepository(application);
        allFoodListings = foodListingRepository.getAll();
    }

    public void insertFoodListing(FoodListing foodListing) {
        foodListingRepository.insert(foodListing);
    }

    public void updateFoodListing(FoodListing foodListing) {
        foodListingRepository.update(foodListing);
    }

    public void deleteFoodListing(FoodListing foodListing) {
        foodListingRepository.delete(foodListing);
    }

    public void deleteAllFoodListings() {
        foodListingRepository.deleteAll();
    }

    public LiveData<List<FoodListing>> getAllFoodListings() {
        return allFoodListings;
    }

    public LiveData<FoodListing> getFoodListingById(int id) {
        return foodListingRepository.getById(id);
    }
}
