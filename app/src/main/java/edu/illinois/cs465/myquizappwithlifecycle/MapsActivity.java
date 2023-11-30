package edu.illinois.cs465.myquizappwithlifecycle;

import static java.security.AccessController.getContext;
import static edu.illinois.cs465.myquizappwithlifecycle.R.*;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
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
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import com.google.android.gms.maps.model.Marker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import edu.illinois.cs465.myquizappwithlifecycle.data.AppDatabase;
import edu.illinois.cs465.myquizappwithlifecycle.data.AppExecutors;
import edu.illinois.cs465.myquizappwithlifecycle.data.FoodListing;
import edu.illinois.cs465.myquizappwithlifecycle.data.FoodListingDao;
import edu.illinois.cs465.myquizappwithlifecycle.data.ViewModal;
import edu.illinois.cs465.myquizappwithlifecycle.databinding.ActivityMapsBinding;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, Serializable {
    private static String DEBUG = "DEBUG";
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private FusedLocationProviderClient fusedLocationClient;
    private ViewModal viewmodal;
    List<Marker> markers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(DEBUG,"MapsActivity onCreate()");
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(id.map);
        mapFragment.getMapAsync(this);

        Button buttonDistance = findViewById(id.buttonDistance);
        Button buttonRestrictions = findViewById(id.buttonRestrictions);
        FloatingActionButton information = findViewById(id.information);
        SeekBar distanceSlider = findViewById(id.distanceSlider);

        ImageView accountCircleImage = findViewById(id.account_circle_image);
        accountCircleImage.setOnClickListener(v -> showBottomDialog());

        information.setOnClickListener(v -> {showLegendPopup();});

        buttonDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (distanceSlider.getVisibility() == View.GONE) {
                    distanceSlider.setVisibility(View.VISIBLE);
                } else {
                    distanceSlider.setVisibility(View.GONE);
                }
            }
        });

        distanceSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Handle the zoom level based on slider progress
                mMap.moveCamera(CameraUpdateFactory.zoomTo(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        buttonRestrictions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRestrictionsDialog();
            }
        });

//        accountCircleImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Intent to navigate to MainActivity
//                Intent intent = new Intent(MapsActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });

        // initialize viewmodal, gmaps markers list
        viewmodal = new ViewModelProvider(this).get(ViewModal.class);
        markers = new ArrayList<Marker>();

        // TODO: delete this when ready
        // clears map, initializes two food listings at CIF and Illini Union

       //   viewmodal.deleteAllFoodListings();
/*
        FoodListing fl1 = new FoodListing();
        fl1.food_name = "Pizza @ CIF";
        fl1.description = "yo this is bomb";
        fl1.latitude = 40.11260764797458;
        fl1.longitude = -88.22836335177905;
        fl1.status = "LOW";
        ArrayList<String> temp_diets = new ArrayList<>();
        temp_diets.add("Vegetarian");
        temp_diets.add("Vegan");
        fl1.dietary_restrictions =temp_diets;
        viewmodal.insertFoodListing(fl1);

        FoodListing fl2 = new FoodListing();
        fl2.food_name = "FREE Samosas!!";
        fl2.description = "these are fire, get you some";
        fl2.latitude = 40.11398325552492;
        fl2.longitude = -88.22495883787813;
        fl2.status = "AVAILABLE";
        ArrayList<String> temp_diets1 = new ArrayList<>();
        temp_diets1.add("Vegetarian");
        temp_diets1.add("Vegan");
        temp_diets1.add("Gluten-Free");
        temp_diets1.add("Dairy-free");
        fl2.dietary_restrictions =temp_diets1;
        viewmodal.insertFoodListing(fl2);*/


//        FoodListing fl3 = new FoodListing();
//        fl2.food_id = 6;
//        fl2.food_name = "Sandwiches @ Illini Union";
//        fl2.description = "yo this is goooodddddd asfffffffff";
//        fl2.latitude = 40.10934133355023;
//        fl2.longitude = -88.22725468192122;
//        fl2.status = "LOW";
//        ArrayList<String> temp_diets2 = new ArrayList<>();
//        temp_diets2.add("gluten-free");
//        temp_diets2.add("vegan");
//        fl2.dietary_restrictions =temp_diets2;
//        viewmodal.insertFoodListing(fl2);


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
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }


//        LatLng siebelCenterDesign = new LatLng(40.1027, -88.2328); // Coordinates for Siebel Center for Design
//        Marker siebelMarker = mMap.addMarker(new MarkerOptions().position(siebelCenterDesign).title("Siebel Center for Design"));

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
                    m.setTag(foodListing);
                    markers.add(m);
                }
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                FoodListing foodListing = (FoodListing) marker.getTag();
                if (foodListing != null){
                showPopup(foodListing);
                return true;
                }
                return false;
            }
        });
    }

    private void showPopup(FoodListing foodListing) {
        Dialog dialogLayout = FoodPopUpActivity.showFoodInfoPopup(this,foodListing);
        Button seeMoreButton = (Button)dialogLayout.findViewById(id.see_more_button);
        seeMoreButton.setOnClickListener(b -> {
            Intent intent = new Intent(this, FoodInfoActivity.class);
            intent.putExtra("foodName", foodListing.food_name);
            intent.putExtra("rso_name", foodListing.rso_name);
            intent.putExtra("description", foodListing.description);
            intent.putExtra("diet",foodListing.dietary_restrictions);
            intent.putExtra("status",foodListing.status);
            startActivity(intent);
        });
    }

    private void showLegendPopup( ) {
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(layout.legend, null);
        ImageView iv1 = (ImageView) dialogLayout.findViewById(id.green_circle);
        iv1.setImageResource(drawable.circle_status);
        iv1.setColorFilter(Color.rgb(0, 0, 255), PorterDuff.Mode.SRC_IN);

        ImageView iv2 = (ImageView) dialogLayout.findViewById(id.yellow_screen);
        iv2.setImageResource(drawable.half_circle);
        iv2.setColorFilter(Color.rgb(0, 0, 255), PorterDuff.Mode.SRC_IN);
        Dialog dialog = new Dialog(MapsActivity.this);
        dialog.setContentView(dialogLayout);
        dialog.show();
       Log.d("STATUS", "HERE");
    }

    private void getCurrentLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 5));
                }
            }
        });
    }


    private void showRestrictionsDialog() {

        String[] items = {"Gluten-free", "Vegetarian", "Lactose-free", "Vegan"};
        boolean[] checkedItems = {false, false, false, false};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Restrictions");

        builder.setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                checkedItems[which] = isChecked;
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();



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

    private void showBottomDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(layout.bottomsheet_layout);

        LinearLayout rso1 = dialog.findViewById(id.rso1);
        LinearLayout student1 = dialog.findViewById(id.student1);
        ImageView cancelButton = dialog.findViewById(id.cancelButton);

        rso1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Toast.makeText(MapsActivity.this, "Switching to RSO 1 screen", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MapsActivity.this, RSOActivity.class);
                startActivity(intent);
            }
        });

        student1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Toast.makeText(MapsActivity.this, "Already in Student 1 screen", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MapsActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = com.google.android.material.R.style.MaterialAlertDialog_Material3_Animation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
}