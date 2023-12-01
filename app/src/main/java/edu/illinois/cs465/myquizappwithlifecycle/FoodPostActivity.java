package edu.illinois.cs465.myquizappwithlifecycle;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import edu.illinois.cs465.myquizappwithlifecycle.BuildConfig;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import edu.illinois.cs465.myquizappwithlifecycle.data.FoodListing;
import edu.illinois.cs465.myquizappwithlifecycle.data.ViewModal;
import edu.illinois.cs465.myquizappwithlifecycle.rso_recycler_view.FoodCardAdapter;

public class FoodPostActivity extends AppCompatActivity {
    private EditText foodName;
    private EditText description;
    private EditText rsoName;
    private ViewModal viewmodal;
    private ChipGroup dietaryRestrictionsChipGroup;
    private ChipGroup statusChipGroup;
    private boolean isEditMode = false;
    private int editingFoodId = -1; // ID of the FoodListing being edited
    private double latitude = 0.0;
    private double longitude = 0.0;

    private FoodCardAdapter adapter;

private String apiKey;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AutocompleteActivity.RESULT_OK) {
            if (data != null) {
                Log.d("DEBUG","succeed");
            }
        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            if (data != null) {
                Log.d("DEBUG", "error"+Autocomplete.getStatusFromIntent(data));
            }
        } else if (resultCode == RESULT_CANCELED) {
            Log.d("DEBUG", "cancelled");
        }
    }

    private int postedItemPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_food);
        viewmodal = new ViewModelProvider(this).get(ViewModal.class);

        foodName = findViewById(R.id.textField1);
        description = findViewById(R.id.textField2);
        rsoName = findViewById(R.id.textField3);
        dietaryRestrictionsChipGroup = findViewById(R.id.chipGroupDiet);
        statusChipGroup = findViewById(R.id.chipGroup);



        Places.initializeWithNewPlacesApiEnabled(getApplicationContext(), BuildConfig.MAPS_API_KEY);

        findViewById(R.id.submit_post_back).setOnClickListener(v -> {
            finish();
        });

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        Log.d("DEBUG", "autocomplete: "+autocompleteFragment);
        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                 latitude = place.getLatLng().latitude;
                 longitude = place.getLatLng().longitude;
                 Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
            }
            @Override
            public void onError(@NonNull Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        // Check for incoming FoodListing data
        if (getIntent().hasExtra("food_id")) {
            String foodNameValue = getIntent().getStringExtra("food_name");
            String descriptionValue = getIntent().getStringExtra("description");
            String rsoNameValue = getIntent().getStringExtra("rso_name");
            ArrayList<String> dietaryRestrictions = getIntent().getStringArrayListExtra("dietary_restrictions");
            String status = getIntent().getStringExtra("status");

            foodName.setText(foodNameValue);
            description.setText(descriptionValue);
            rsoName.setText(rsoNameValue);
            setDietaryRestrictions(dietaryRestrictions);
            setStatusChip(status);
        }

        // insert or update db on submit
        findViewById(R.id.direction_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //make a FoodListing object
                FoodListing fl1 = new FoodListing();

                //Get food name
                foodName = (EditText)findViewById(R.id.textField1);
                fl1.food_name = foodName.getText().toString();

                //Get description of the food
                description = (EditText)findViewById(R.id.textField2);
                fl1.description = description.getText().toString();

                //Get rso name
                rsoName = (EditText)findViewById(R.id.textField3);
                fl1.rso_name = rsoName.getText().toString();

                //get checked values from chipGroup for the dietary restriction
                ChipGroup chipGroup = findViewById(R.id.chipGroupDiet);
                List<Integer> ids = chipGroup.getCheckedChipIds();
                ArrayList<String> dietary_res = new ArrayList<>();
                for(Integer id:ids){
                    Chip chip = chipGroup.findViewById(id);
                    dietary_res.add(chip.getText().toString());
//                    Log.d("DEBUG", "chip_name: " + chip.getText().toString());
                }
                fl1.dietary_restrictions = dietary_res;

                //get status
                ChipGroup chipGroup2 = findViewById(R.id.chipGroup);
                Integer id2 = chipGroup2.getCheckedChipId();
                Chip chip_status = chipGroup2.findViewById(id2);
                String status = chip_status.getText().toString();
                Log.d("DEBUG", "status: " + status);
                if(status.equals("Available")){
                    fl1.status = "AVAILABLE";
                }
                else if(status.equals("Running Low")){
                    fl1.status = "LOW";
                };

//                to do: get from gmap values
                fl1.latitude = latitude;
                fl1.longitude = longitude;

//                fl1.latitude = 40.10934133355023;
//                fl1.longitude = -88.22725468192122;

                if (getIntent().hasExtra("food_id")) {
                    Log.d("DEBUG", "UPDATING FOOD LISTING WITH ID " + getIntent().getIntExtra("food_id", 0));
                    fl1.food_id = getIntent().getIntExtra("food_id", 0);
                    viewmodal.updateFoodListing(fl1);
                } else {
                    Log.d("DEBUG", "INSERTING FOOD LISTING");
                    viewmodal.insertFoodListing(fl1);
                }
                showNotification();
                Log.d("DEBUG", "IM HRERE");

                postedItemPosition = getIntent().getIntExtra("food_id", -1);

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        viewmodal.deleteFoodListing(fl1); // Delete from database
                    }
                }, 1800000); //Deletes the post after 30 min = 1,800,000 miliseconds

                finish();
            }
        });
    }

    private void setDietaryRestrictions(ArrayList<String> restrictions) {
        if (restrictions != null) {
            for (String restriction : restrictions) {
                int chipId = getDietaryRestrictionChipId(restriction);
                Chip chip = findViewById(chipId);
                if (chip != null) {
                    chip.setChecked(true);
                }
            }
        }
    }

    private int getDietaryRestrictionChipId(String restriction) {
        // Map restriction strings to chip IDs
        switch (restriction) {
            case "Gluten-free": return R.id.chip1;
            case "Dairy-free": return R.id.chip2;
            case "Vegetarian": return R.id.chip3;
            case "Nut-free": return R.id.chip4;
            case "Shellfish-free": return R.id.chip5;
            case "Vegan": return R.id.chip6;
            default: return -1;
        }
    }

    private void setStatusChip(String status) {
        if (status != null) {
            int chipId = status.equals("AVAILABLE") ? R.id.chip_available : R.id.chip_runningLow;
            statusChipGroup.check(chipId);
        }
    }


    private void showNotification() {
        // Create the notification channel for Oreo and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Free Food Alert!";
            String description = "Pizza Available at Siebel CS NOW!";
            int importance = NotificationManager.IMPORTANCE_DEFAULT; // Check this importance
            NotificationChannel channel = new NotificationChannel("channel_id", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        Intent notifyIntent = new Intent(this, RSOActivity.class);

        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                this, 0, notifyIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
                .setSmallIcon(R.drawable.account_circle_24px) // Make sure this icon exists
                .setContentTitle("Alert: New Food Post by " + rsoName.getText().toString())
                .setContentText(foodName.getText().toString() + " is available! (click to learn more)")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(notifyPendingIntent)
                .setAutoCancel(true); // Auto-cancel the notification after it's tapped

        // Post the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // Handle missing permissions
            return;
        }

        notificationManager.notify(1, builder.build());
    }





}
