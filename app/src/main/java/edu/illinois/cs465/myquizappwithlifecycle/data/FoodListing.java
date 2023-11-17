package edu.illinois.cs465.myquizappwithlifecycle.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity
public class FoodListing {
    @PrimaryKey(autoGenerate = true)
    public int food_id;
    public String food_name;
    public double latitude;
    public double longitude;
    public String description;
    public ArrayList<String> dietary_restrictions;
    public String status; // "AVAILABLE", "LOW"
    public String rso_name;


}
