package edu.illinois.cs465.myquizappwithlifecycle;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import edu.illinois.cs465.myquizappwithlifecycle.databinding.FoodPopupBinding;
import edu.illinois.cs465.myquizappwithlifecycle.databinding.LandingScreenBinding;
import edu.illinois.cs465.myquizappwithlifecycle.databinding.RsoBaseScreenBinding;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static String DEBUG = "DEBUG";
    private static final String KEY = "KEY";
    private static final String VALUE = "We passed the bundle of data";
    private Button falseButton;
    private Button trueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.d(DEBUG, "onCreate()");
        setContentView(R.layout.landing_screen);

//        RsoBaseScreenBinding binding = RsoBaseScreenBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());

        if (savedInstanceState != null) {
            String value = new String(savedInstanceState.getString(KEY));
            if (value != null) {
                Log.d(DEBUG, value);
            }
        }

        findViewById(R.id.deleted_post_button).setOnClickListener(v -> {
            showDeletedEventPopup();
        });

        findViewById(R.id.food_popup).setOnClickListener(v -> {
            showFoodInfoPopup();
        });

        RsoBaseScreenBinding binding = RsoBaseScreenBinding.inflate(getLayoutInflater());
        binding.floatingActionButton.setOnClickListener(v -> showBottomDialog());

//        findViewById(R.id.floating_action_button).setOnClickListener(v -> {
//            showBottomDialog();
//        });
//        falseButton = (Button) findViewById(R.id.false_button);
//        trueButton = (Button) findViewById(R.id.true_button);

//        falseButton.setOnClickListener(this);
//        trueButton.setOnClickListener(this);

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
//    bottomAppBar.setNavigationOnClickListener {
//            // Handle navigation icon press
//    }
//
//    bottomAppBar.setOnMenuItemClickListener { menuItem ->
//            when (menuItem.itemId) {
//                R.id.search -> {
//                    // Handle search icon press
//                    true
//                }
//                R.id.more -> {
//                    // Handle more item (inside overflow menu) press
//                    true
//                }
//                else -> false
//            }
//        }

    private void showDeletedEventPopup() {
        AlertDialog dialog = new MaterialAlertDialogBuilder(MainActivity.this)
            .setMessage("Post has been deleted by host")
            .setPositiveButton("Ok", null)
            .show();
    }

    private void showFoodInfoPopup() {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.food_popup);
        dialog.show();
    }

    private void showBottomDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheet_layout);

        LinearLayout user1 = dialog.findViewById(R.id.user1);
        LinearLayout user2 = dialog.findViewById(R.id.user2);
        LinearLayout user3 = dialog.findViewById(R.id.user3);
        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);

        user1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Switching to user 1", Toast.LENGTH_SHORT).show();
            }
        });

        user2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Switching to user 2", Toast.LENGTH_SHORT).show();
            }
        });

        user3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Switching to user 3", Toast.LENGTH_SHORT).show();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = com.google.android.material.R.style.MaterialAlertDialog_Material3_Animation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
}

