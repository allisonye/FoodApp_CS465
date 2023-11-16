package edu.illinois.cs465.myquizappwithlifecycle.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;

@Entity
public class FoodListing {
    @PrimaryKey(autoGenerate = true)
    public int food_id;

    public String food_name;
    public double latitude;
    public double longitude;
}
