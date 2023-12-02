package edu.illinois.cs465.myquizappwithlifecycle;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FoodInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.food_preview);

        TextView title = findViewById(R.id.post_title);
        title.setText((String)intent.getSerializableExtra("foodName"));

        TextView status = findViewById(R.id.status_food);
        status.setText((String)intent.getSerializableExtra("status"));

        TextView description = findViewById(R.id.description);
        description.setText((String)intent.getSerializableExtra("description"));

        TextView rso_name = findViewById(R.id.rso_name_text);
        rso_name.setText((String)intent.getSerializableExtra("rso_name"));

        ChipGroup chipGroup = findViewById(R.id.chipGroupDiet);
        ArrayList<String> diets = (ArrayList<String>)intent.getSerializableExtra("diet");
        if(diets != null) {
            for (String diet : diets) {
                addReadOnlyChipToGroup(this, chipGroup, diet);
            }
        }

        // format expiration time
        TextView expiryTimeView = findViewById(R.id.expiry_time);
        Timestamp expiryTime = new Timestamp(((Date) intent.getSerializableExtra("created_at")).getTime() + 30*60000);
        int hour = expiryTime.getHours();
        int minute = expiryTime.getMinutes();
        String ampm = "am";
        if (hour >= 12) {
            hour -= 12;
            ampm = "pm";
        }
        if (hour == 0) {
            hour = 12;
        }
        String minuteStr = minute < 10 ? "0" + Integer.toString(minute) : Integer.toString(minute);
        expiryTimeView.setText("Available until " + hour + ":" + minuteStr + ampm);

        // convert latlng to location name to be displayed
        // https://www.geeksforgeeks.org/reverse-geocoding-in-android/
        Geocoder mGeocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        String addressString = "";
        try {
            List<Address> addressList = mGeocoder.getFromLocation(
                    (double) intent.getSerializableExtra("latitude"),
                    (double) intent.getSerializableExtra("longitude"),
                    1);
            if (addressList != null && !addressList.isEmpty()) {
                Address address = addressList.get(0);
                addressString = address.getAddressLine(0);
                // the option that's supposed to be better but is absolutely not:
                // addressString = address.getFeatureName();
                Log.d("DEBUG", "addressString is " + addressString);
            }
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(),"Unable connect to Geocoder",Toast.LENGTH_LONG).show();
        }
        TextView locationTextView = findViewById(R.id.locationName);
        locationTextView.setText(addressString);

        findViewById(R.id.direction_button).setOnClickListener(v -> {
            showNavigateToMapsPopUp();
        });

        findViewById(R.id.back_button).setOnClickListener(v -> {
            if (isTaskRoot())
            {
                Intent mapsIntent = new Intent(this, MapsActivity.class);
                startActivity(mapsIntent);
                finish();
            }
            finish();
        });

    }

    private void addReadOnlyChipToGroup(Context context, ChipGroup chipGroup, String text) {
        Chip chip = new Chip(context);
        chip.setText(text);
        chip.setClickable(false);
        chip.setFocusable(false);
        chip.setCheckable(false);

        chip.setLayoutParams(new ChipGroup.LayoutParams(ChipGroup.LayoutParams.WRAP_CONTENT, ChipGroup.LayoutParams.WRAP_CONTENT));
        chip.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        chipGroup.addView(chip);
    }

    private void showNavigateToMapsPopUp() {
        AlertDialog dialog = new MaterialAlertDialogBuilder(FoodInfoActivity.this)
                .setMessage("Open in Google Maps?")
                .setPositiveButton("OPEN", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        redirectToGoogleMaps();
                    }
                })
                .setNegativeButton("CANCEL", null)
                .show();
    }

    private void redirectToGoogleMaps() {
        double destinationLatitude = (double) getIntent().getSerializableExtra("latitude");
        double destinationLongitude = (double) getIntent().getSerializableExtra("longitude");

        Uri gmmIntentUri = Uri.parse("geo:" + destinationLatitude + "," + destinationLongitude + "?q=" + destinationLatitude + "," + destinationLongitude);

        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

        mapIntent.setPackage("com.google.android.apps.maps");

        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            Toast.makeText(this, "Google Maps is not installed on your device.", Toast.LENGTH_SHORT).show();
        }
    }
}
