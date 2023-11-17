package edu.illinois.cs465.myquizappwithlifecycle;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import edu.illinois.cs465.myquizappwithlifecycle.data.FoodListing;
import edu.illinois.cs465.myquizappwithlifecycle.data.ViewModal;

public class FoodPostActivity extends AppCompatActivity {
    private EditText foodName;
    private EditText description;
    private ViewModal viewmodal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_food);

        viewmodal = new ViewModelProvider(this).get(ViewModal.class);



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

                //get checked values from chipGroup for the dietary restriction
                ChipGroup chipGroup = findViewById(R.id.chipGroupDiet);
                List<Integer> ids = chipGroup.getCheckedChipIds();
                ArrayList<String> dietary_res = new ArrayList<>();
                for(Integer id:ids){
                    Chip chip = chipGroup.findViewById(id);
                    dietary_res.add(chip.getText().toString());
                    Log.d("DEBUG", "chip_name: " + chip.getText().toString());
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
                fl1.latitude = 40.11260764797458;
                fl1.longitude = -88.22836335177905;
                viewmodal.insertFoodListing(fl1);
                showNotification();
                Log.d("DEBUG", "IM HRERE");
            }
        });
        findViewById(R.id.direction_button).setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

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

        Intent notifyIntent = new Intent(this, MainActivity.class);
        // Set the Activity to start in a new, empty task.
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // Create the PendingIntent.
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                this, 0, notifyIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
                .setSmallIcon(R.drawable.account_circle_24px) // Make sure this icon exists
                .setContentTitle("Free Food Alert!")
                .setContentText("Pizza Available at Siebel CS NOW!")
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
