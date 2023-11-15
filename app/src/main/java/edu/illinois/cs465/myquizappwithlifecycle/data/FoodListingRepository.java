package edu.illinois.cs465.myquizappwithlifecycle.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class FoodListingRepository {
    private FoodListingDao dao;
    private LiveData<List<FoodListing>> allFoodListings;

    public FoodListingRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        dao = database.foodListingDao();
        allFoodListings = dao.getAll();
    }

    // creating a method to insert the data to our database.
    public void insert(FoodListing foodListing) {
        new InsertFoodListingAsyncTask(dao).execute(foodListing);
    }

    // creating a method to update data in database.
    public void update(FoodListing foodListing) {
        new UpdateFoodListingAsyncTask(dao).execute(foodListing);
    }

    // creating a method to delete the data in our database.
    public void delete(FoodListing foodListing) {
        new DeleteFoodListingAsyncTask(dao).execute(foodListing);
    }

    // below method is to read all the courses.
    public LiveData<List<FoodListing>> getAllCourses() {
        return allFoodListings;
    }

    // we are creating a async task method to insert new course.
    private static class InsertFoodListingAsyncTask extends AsyncTask<FoodListing, Void, Void> {
        private FoodListingDao dao;

        private InsertFoodListingAsyncTask(FoodListingDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(FoodListing... foodListings) {
            // below line is use to insert our modal in dao.
            dao.insert(foodListings[0]);
            return null;
        }
    }

    // we are creating a async task method to update our course.
    private static class UpdateFoodListingAsyncTask extends AsyncTask<FoodListing, Void, Void> {
        private FoodListingDao dao;

        private UpdateFoodListingAsyncTask(FoodListingDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(FoodListing... foodListings) {
            // below line is use to update
            // our modal in dao.
            dao.update(foodListings[0]);
            return null;
        }
    }

    // we are creating a async task method to delete course.
    private static class DeleteFoodListingAsyncTask extends AsyncTask<FoodListing, Void, Void> {
        private FoodListingDao dao;

        private DeleteFoodListingAsyncTask(FoodListingDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(FoodListing... foodListings) {
            // below line is use to delete
            // our course modal in dao.
            dao.delete(foodListings[0]);
            return null;
        }
    }
}
