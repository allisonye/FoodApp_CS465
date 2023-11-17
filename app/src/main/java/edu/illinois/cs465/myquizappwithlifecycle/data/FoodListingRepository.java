package edu.illinois.cs465.myquizappwithlifecycle.data;

import android.app.Application;
import android.os.AsyncTask;
//import android.os.AsyncResponse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.util.List;
import java.util.stream.Collectors;

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

    public void deleteAll() {
        new DeleteAllAsyncTask(dao).execute();
    }

    // below method is to read all the courses.
    public LiveData<List<FoodListing>> getAll() {
        return allFoodListings;
    }

    public LiveData<FoodListing> getById(int id) {
       return dao.getById(id);
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

    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private FoodListingDao dao;

        private DeleteAllAsyncTask(FoodListingDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // below line is use to delete
            // our course modal in dao.
            dao.deleteAll();
            return null;
        }
    }

//    private static class GetByIdAsyncTask extends AsyncTask<Integer, Void, FoodListing> {
//        private FoodListingDao dao;
//
//        private GetByIdAsyncTask(FoodListingDao dao) {
//            this.dao = dao;
//        }
//
//        @Override
//        protected FoodListing doInBackground(Integer... ids) {
//            return dao.getById(ids[0]);
//        }
//    }
//    private static class GetByIdAsyncTask extends AsyncTask<Integer, Void, FoodListing> {
//        private FoodListingDao dao;
//
//        // you may separate this or combined to caller class.
//        public interface AsyncResponse {
//            void processFinish(FoodListing output);
//        }
//
//        public AsyncResponse delegate = null;
//
//        public GetByIdAsyncTask(AsyncResponse delegate){
//            this.delegate = delegate;
//        }
//
//        @Override
//        protected void onPostExecute(FoodListing foodListing) {
//            delegate.processFinish(foodListing);
//        }
//
//        @Override
//        protected FoodListing doInBackground(Integer... ids) {
//            return dao.getById(ids[0]);
//        }
//    }
}
