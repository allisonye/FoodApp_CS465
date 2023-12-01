package edu.illinois.cs465.myquizappwithlifecycle;

import android.app.Dialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import edu.illinois.cs465.myquizappwithlifecycle.data.FoodListing;

public class FoodPopUpActivity  extends AppCompatActivity implements Serializable {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static Dialog showFoodInfoPopup(Context context, FoodListing foodListing) {
        Dialog dialog = new Dialog(context);
        View dialogLayout = LayoutInflater.from(context).inflate(R.layout.food_popup, null);
        dialog.setContentView(dialogLayout);

        TextView tv = dialogLayout.findViewById(R.id.title_popUp);
        tv.setText(foodListing.food_name);

        TextView rsoView = dialogLayout.findViewById(R.id.rso_name);
        rsoView.setText(foodListing.rso_name);

        Chip status = dialogLayout.findViewById(R.id.chip3);
        status.setText(foodListing.status);

        ChipGroup chipGroup = dialogLayout.findViewById(R.id.chipGroupDietPopUp);
        ArrayList<String> diets = foodListing.dietary_restrictions;
        if (diets != null) {
            for (String diet : diets) {
                addReadOnlyChipToGroup(context, chipGroup, diet);
            }
        }

        // format expiration time
        TextView expiryTimeView = dialogLayout.findViewById(R.id.expiry_time);
        Timestamp expiryTime = new Timestamp(foodListing.createdAt.getTime() + 30*60000);
        int hour = expiryTime.getHours();
        int minute = expiryTime.getMinutes();
        String ampm = "am";
        if (hour > 12) {
            hour -= 12;
            ampm = "pm";
        }
        if (hour == 0) {
            hour = 12;
        }
        expiryTimeView.setText("Available until " + hour + ":" + minute + ampm);

        // convert latlng to location name to be displayed
        // https://www.geeksforgeeks.org/reverse-geocoding-in-android/
        Geocoder mGeocoder = new Geocoder(context, Locale.getDefault());
        String addressString = "";
        try {
            List<Address> addressList = mGeocoder.getFromLocation(foodListing.latitude, foodListing.longitude, 1);
            if (addressList != null && !addressList.isEmpty()) {
                Address address = addressList.get(0);
                addressString = address.getAddressLine(0);
                // the option that's supposed to be better but is absolutely not:
                // addressString = address.getFeatureName();
                Log.d("DEBUG", "addressString is " + addressString);
            }
        } catch (IOException e) {
            Toast.makeText(context,"Unable connect to Geocoder",Toast.LENGTH_LONG).show();
        }
        TextView locationTextView = dialogLayout.findViewById(R.id.locationName);
        locationTextView.setText(addressString);

        dialog.show();
        return dialog;
    }

    private static void addReadOnlyChipToGroup(Context context, ChipGroup chipGroup, String text) {
        Chip chip = new Chip(context);
        chip.setText(text);
        chip.setClickable(false);
        chip.setFocusable(false);
        chip.setCheckable(false);

        chip.setLayoutParams(new ChipGroup.LayoutParams(ChipGroup.LayoutParams.WRAP_CONTENT, ChipGroup.LayoutParams.WRAP_CONTENT));
        chip.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        chipGroup.setChipSpacingVertical(0);
        chipGroup.addView(chip);
    }
}
