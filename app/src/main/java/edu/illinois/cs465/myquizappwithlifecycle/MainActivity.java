package edu.illinois.cs465.myquizappwithlifecycle;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;
import android.content.Intent;


import androidx.annotation.NonNull;
import androidx.annotation.MenuRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

import edu.illinois.cs465.myquizappwithlifecycle.data.FoodListing;
import edu.illinois.cs465.myquizappwithlifecycle.data.ViewModal;
import edu.illinois.cs465.myquizappwithlifecycle.databinding.FoodPopupBinding;
import edu.illinois.cs465.myquizappwithlifecycle.databinding.LandingScreenBinding;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static String DEBUG = "DEBUG";
    private static final String KEY = "KEY";
    private static final String VALUE = "We passed the bundle of data";
    private ViewModal viewmodal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
//        startActivity(intent);

        Log.d(DEBUG, "MainActivity onCreate()");
        super.onCreate(savedInstanceState);
        viewmodal = new ViewModelProvider(this).get(ViewModal.class);

        // initialize some data
        viewmodal.deleteAllFoodListings();

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
        temp_diets1.add("Dairy-free");
        fl2.dietary_restrictions =temp_diets1;
        viewmodal.insertFoodListing(fl2);

        // redirect to MapsActivity
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);

//        setContentView(R.layout.landing_screen);
//        setContentView(R.layout.activity_maps);

//        RsoBaseScreenBinding binding = RsoBaseScreenBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        if (savedInstanceState != null) {
//            String value = new String(savedInstanceState.getString(KEY));
//            if (value != null) {
//                Log.d(DEBUG, value);
//            }
//        }
//
//        findViewById(R.id.deleted_post_button).setOnClickListener(v -> {
//            showDeletedEventPopup();
//        });
//
//        findViewById(R.id.food_popup).setOnClickListener(v -> {
//            // Inflater to be able to grab button in dialog
//            LayoutInflater inflater = getLayoutInflater();
//            View dialogLayout = inflater.inflate(R.layout.food_popup, null);
////        RsoBaseScreenBinding binding = RsoBaseScreenBinding.inflate(getLayoutInflater());
////        binding.floatingActionButton.setOnClickListener(view -> showBottomDialog());
//
////        findViewById(R.id.floating_action_button).setOnClickListener(v -> {
////            showBottomDialog();
//        });
//
////////////////////////// TO MAPS
////        ImageView accountCircleImage = findViewById(R.id.account_circle_image);
////
////        accountCircleImage.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                // Intent to navigate to MainActivity
////                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
////                startActivity(intent);
////            }
////        });
///////////////////////
////        });
////
////        findViewById(R.id.food_popup).setOnClickListener(v -> {
////            // Inflater to be able to grab button in dialog
////            LayoutInflater inflater = getLayoutInflater();
////            View dialogLayout = inflater.inflate(R.layout.food_popup, null);
////
//////        RsoBaseScreenBinding binding = RsoBaseScreenBinding.inflate(getLayoutInflater());
//////        binding.floatingActionButton.setOnClickListener(v -> showBottomDialog());
////
//////        findViewById(R.id.floating_action_button).setOnClickListener(v -> {
//////            showBottomDialog();
//////        });
//////        falseButton = (Button) findViewById(R.id.false_button);
//////        trueButton = (Button) findViewById(R.id.true_button);
////
//////        falseButton.setOnClickListener(this);
//////        trueButton.setOnClickListener(this);
////            showFoodInfoPopup(dialogLayout);
////
////            Button seeMoreButton = (Button)dialogLayout.findViewById(R.id.see_more_button);
////            seeMoreButton.setOnClickListener(b -> {
////                Intent intent = new Intent(this, FoodInfoActivity.class);
////                startActivity(intent);
////            });
////
////        });
////
////        findViewById(R.id.post_food).setOnClickListener(v -> {
////            Intent intent = new Intent(this, FoodPostActivity.class);
////            startActivity(intent);
////        });
////
////        findViewById(R.id.RSO_View).setOnClickListener(v -> {
////            Intent intent = new Intent(this, RSOActivity.class);
////            startActivity(intent);
////        });
//
//        ImageView accountCircleImage = findViewById(R.id.account_circle_image);
//        accountCircleImage.setOnClickListener(v -> showBottomDialog());
////        accountCircleImage.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                showBottomDialog
////            }
////        });
    }

    protected void onSaveInstanceState(Bundle savedInstance) {
        super.onSaveInstanceState(savedInstance);
        Log.d(DEBUG, "onSavedInstanceState()");
        savedInstance.putString(KEY, VALUE);
    }

    protected void onStart() {
        super.onStart();
        Log.d(DEBUG, "OnStart()");
    }

    protected void onResume() {
        super.onResume();
        Log.d(DEBUG, "OnResume()");
    }

    protected void onRestart() {
        super.onRestart();
        Log.d(DEBUG, "OnRestart()");
    }

    protected void onPause() {
        super.onPause();
        Log.d(DEBUG, "OnPause()");
    }

    protected void onStop() {
        super.onStop();
        Log.d(DEBUG, "OnStop()");
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.d(DEBUG, "OnDestroy()");
    }


    public void onClick(View v) {
        if (v.getId() == R.id.false_button) {
            Toast.makeText(this, "CORRECT", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.true_button) {
            Toast.makeText(this, "INCORRECT", Toast.LENGTH_SHORT).show();
        }
    }
//
//    private void createNotificationChannel() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = "Free Food Alert!";
//            String description = "Pizza Available at Siebel CS NOW!";
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel("channel_id", name, importance);
//            channel.setDescription(description);
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
//    }
//
//    private void showNotification() {
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
//                .setSmallIcon(R.drawable.account_circle_24px) // replace with your own icon
//                .setContentTitle("Free Food Alert!")
//                .setContentText("Pizza Available at Siebel CS NOW!")
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        notificationManager.notify(1, builder.build());
//    }
//
////    bottomAppBar.setNavigationOnClickListener {
////            // Handle navigation icon press
////    }
////
////    bottomAppBar.setOnMenuItemClickListener { menuItem ->
////            when (menuItem.itemId) {
////                R.id.search -> {
////                    // Handle search icon press
////                    true
////                }
////                R.id.more -> {
////                    // Handle more item (inside overflow menu) press
////                    true
////                }
////                else -> false
////            }
////        }
//
//    private void showDeletedEventPopup() {
//        AlertDialog dialog = new MaterialAlertDialogBuilder(MainActivity.this)
//                .setMessage("Post has been deleted by host")
//                .setPositiveButton("Ok", null)
//                .show();
//    }
//
//    private void showFoodInfoPopup(View dialog_layout) {
//        Dialog dialog = new Dialog(MainActivity.this);
//        dialog.setContentView(dialog_layout);
//        dialog.show();
//    }
//    private void showBottomDialog() {
//        final Dialog dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.bottomsheet_layout);
//
//        LinearLayout rso1 = dialog.findViewById(R.id.rso1);
//        LinearLayout student1 = dialog.findViewById(R.id.student1);
//        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);
//
//        rso1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//                Toast.makeText(MainActivity.this, "Switching to RSO 1 screen", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(MainActivity.this, RSOActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        student1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//                Toast.makeText(MainActivity.this, "Switching to Student 1 screen", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
//                startActivity(intent);
//            }
//        });
//        cancelButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.getWindow().getAttributes().windowAnimations = com.google.android.material.R.style.MaterialAlertDialog_Material3_Animation;
//        dialog.getWindow().setGravity(Gravity.BOTTOM);
//    }
}