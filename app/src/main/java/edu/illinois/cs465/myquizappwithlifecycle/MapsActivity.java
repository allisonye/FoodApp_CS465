package edu.illinois.cs465.myquizappwithlifecycle;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import edu.illinois.cs465.myquizappwithlifecycle.data.AppDatabase;
import edu.illinois.cs465.myquizappwithlifecycle.data.AppExecutors;
import edu.illinois.cs465.myquizappwithlifecycle.data.FoodListing;
import edu.illinois.cs465.myquizappwithlifecycle.data.FoodListingDao;
import edu.illinois.cs465.myquizappwithlifecycle.data.ViewModal;
import edu.illinois.cs465.myquizappwithlifecycle.databinding.ActivityMapsBinding;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static String DEBUG = "DEBUG";
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private ViewModal viewmodal;
    List<Marker> markers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(DEBUG,"onCreate()");
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // initialize viewmodal, gmaps markers list
        viewmodal = new ViewModelProvider(this).get(ViewModal.class);
        markers = new ArrayList<Marker>();

        // TODO: delete this when ready
        // clears map, initializes two food listings at CIF and Illini Union
        viewmodal.deleteAllFoodListings();
        FoodListing fl1 = new FoodListing();
        fl1.food_id = 5;
        fl1.food_name = "Pizza @ CIF";
        fl1.latitude = 40.11260764797458;
        fl1.longitude = -88.22836335177905;
        fl1.status = "AVAILABLE";
        viewmodal.insertFoodListing(fl1);
        FoodListing fl2 = new FoodListing();
        fl2.food_id = 6;
        fl2.food_name = "Sandwiches @ Illini Union";
        fl2.latitude = 40.10934133355023;
        fl2.longitude = -88.22725468192122;
        fl2.status = "LOW";
        viewmodal.insertFoodListing(fl2);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(DEBUG,"onMapReady()");
        mMap = googleMap;

        // Add a marker at Siebel and ECE and move the camera
//        LatLng siebel = new LatLng(40.113876587817316, -88.22487301073227);
//        Marker m1 = mMap.addMarker(new MarkerOptions().position(siebel).title("Siebel"));
//        LatLng ece = new LatLng(40.11504431688674, -88.22802319553404);
//        mMap.addMarker(new MarkerOptions().position(ece).title("ECE"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(siebel));

        viewmodal.getAllFoodListings().observe(this, new Observer<List<FoodListing>>() {
            @Override
            public void onChanged(List<FoodListing> foodListings) {
                // clear existing markers from map
                for (Marker m : markers) {
                    m.remove();
                }
                markers.clear();
                // add markers back to map
                for (FoodListing foodListing : foodListings) {
                    BitmapDescriptor icon = foodListing.status.equals("LOW")
                            ? BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)
                            : BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
                    Marker m = mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(foodListing.latitude, foodListing.longitude))
                            .title(foodListing.food_name)
                            .icon(icon)
                    );
                    markers.add(m);
                    Log.d(DEBUG, "ID " + foodListing.food_id);
                }
            }
        });

        // copy this code + viewmodal init code in whatever activities need it
        viewmodal.getFoodListingById(5).observe(this, new Observer<FoodListing>() {
            @Override
            public void onChanged(FoodListing foodListing) {
                if (foodListing != null) {
                    Log.d(DEBUG, "GET BY ID    " + foodListing.food_name);
                }
            }
        });
    }
}