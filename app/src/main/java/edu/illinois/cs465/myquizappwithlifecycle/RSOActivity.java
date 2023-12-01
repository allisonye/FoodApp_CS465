package edu.illinois.cs465.myquizappwithlifecycle;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;

import android.util.Log;

import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.MenuRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import edu.illinois.cs465.myquizappwithlifecycle.data.FoodListing;
import edu.illinois.cs465.myquizappwithlifecycle.data.ViewModal;
import edu.illinois.cs465.myquizappwithlifecycle.databinding.ActivityMainBinding;
import edu.illinois.cs465.myquizappwithlifecycle.rso_recycler_view.FoodCardAdapter;

public class RSOActivity extends AppCompatActivity {

    private ViewModal foodListingModal;
    private ActivityMainBinding binding;
    private FoodCardAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.rso_list_food);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new FoodCardAdapter(recyclerView, this);
        recyclerView.setAdapter(adapter);

        foodListingModal = new ViewModelProvider(this).get(ViewModal.class);
//        foodListingModal.deleteAllFoodListings();
//        FoodListing fl1 = new FoodListing();
//        fl1.food_name = "CIF2";
//        fl1.latitude = 40.11260764797458;
//        fl1.longitude = -88.22836335177905;
//        fl1.status = "AVAILABLE";
//        foodListingModal.insertFoodListing(fl1);
//
//        FoodListing fl2 = new FoodListing();
//        fl2.food_name = "CIF3";
//        fl2.latitude = 40.11260764797458;
//        fl2.longitude = -88.22836335177905;
//        fl2.status = "LOW";
//        foodListingModal.insertFoodListing(fl2);
        foodListingModal.getAllFoodListings();

        foodListingModal.getAllFoodListings().observe(this, new Observer<List<FoodListing>>() {
            public void onChanged(@Nullable List<FoodListing> foodListings) {
                adapter.setFoodListings(foodListings);
            }
        });

        adapter.setOnClickListener(new FoodCardAdapter.OnClickListener() {
            @Override
            public void onClick(View view, int position, FoodListing foodListing) {
                if (view.getId() == R.id.food_card_status_button) {
                    showStatusMenu(view, R.menu.overflow_menu, position);
                }
                else if (view.getId() == R.id.vert_icon_button) {
                    showVertMenu(view, R.menu.vert_menu, position);
                }
            }
        });

//            findViewById(R.id.food_card_status_button).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    showStatusMenu(v, R.menu.overflow_menu);
//                }
//            });
//
//            findViewById(R.id.vert_icon_button).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    showVertMenu(v, R.menu.vert_menu);
//                }
//            });

//        findViewById(R.id.rso_profile_icon).setOnClickListener(v -> {
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
//        });

        ImageView accountCircleImage = findViewById(R.id.rso_profile_icon);
        accountCircleImage.setOnClickListener(v -> showBottomDialog());

        findViewById(R.id.add_food_button).setOnClickListener(v -> {
            Intent intent = new Intent(this, FoodPostActivity.class);
            startActivity(intent);
        });

    }

//    private void showDeleteConfirmationPopup() {
//        AlertDialog dialog = new MaterialAlertDialogBuilder(this)
//                .setTitle(getResources().getString(R.string.delete_confirmation_title))
//                .setMessage(getResources().getString(R.string.delete_confirmation_message))
//                .setNegativeButton(getResources().getString(R.string.cancel_delete), new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int which) {
//                        // Respond to negative button press
//                    }
//                })
//                .setPositiveButton(getResources().getString(R.string.confirm_delete), new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int which) {
//                        //respond to delete
//                    }
//                }).show();
//    }

    private void showDeleteConfirmationPopup(int position) {
        new MaterialAlertDialogBuilder(this)
                .setTitle(getResources().getString(R.string.delete_confirmation_title))
                .setMessage(getResources().getString(R.string.delete_confirmation_message))
                .setNegativeButton(getResources().getString(R.string.cancel_delete), null)
                .setPositiveButton(getResources().getString(R.string.confirm_delete), (dialogInterface, which) -> {
                    adapter.removeFoodListing(position);
                })
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.overflow_menu, menu);
        return true;
    }


    private void showVertMenu(View v, @MenuRes int menuRes, int position) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.getMenuInflater().inflate(menuRes, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.option_vert_menu_delete) {
                showSnackbarWithAction(v, position);
                return true;
            }
            else if (item.getItemId() == R.id.option_vert_menu_edit){
                FoodListing foodListing = adapter.getFoodListingAt(position);
                startFoodPostActivityWithFoodListing(foodListing);
                return true;
            }
            // Handle other menu item clicks if necessary
            return false;
        });

        // Show the popup menu.
        popup.show();
    }

    private void showSnackbarWithAction(View view, final int position) {
        final FoodListing deletedFoodListing = adapter.getFoodListingAt(position);
        adapter.removeFoodListing(position);

        Snackbar snackbar = Snackbar.make(findViewById(R.id.add_food_button), "Item deleted", Snackbar.LENGTH_LONG);
        snackbar.setAction("UNDO", v -> {
            Log.d("Snackbar", "Undo clicked");
            adapter.restoreFoodListing(deletedFoodListing, position);
        });
        snackbar.addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                if (    event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT ||
                        event == Snackbar.Callback.DISMISS_EVENT_CONSECUTIVE ||
                        event == Snackbar.Callback.DISMISS_EVENT_SWIPE) {
                    foodListingModal.deleteFoodListing(deletedFoodListing);
                }
            }
        });
        snackbar.setAnchorView(findViewById(R.id.add_food_button));
        snackbar.show();
    }

    @SuppressLint("RestrictedApi")
    private void showStatusMenu(View v, @MenuRes int menuRes, int position) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.getMenuInflater().inflate(menuRes, popup.getMenu());

        // Define your colors
        int statusAvailableColor = ContextCompat.getColor(this, R.color.status_available_color);
        int statusLowColor = ContextCompat.getColor(this, R.color.status_low_color);

        if (popup.getMenu() instanceof MenuBuilder) {
            MenuBuilder menuBuilder = (MenuBuilder) popup.getMenu();
            menuBuilder.setOptionalIconsVisible(true);

            for (MenuItem item : menuBuilder.getVisibleItems()) {
                Drawable icon = item.getIcon();
                if (icon != null) {
                    // Set the tint based on some condition or item ID
                    if (item.getItemId() == R.id.status_option_1) {
                        icon.setColorFilter(statusAvailableColor, PorterDuff.Mode.SRC_IN);
                    } else if (item.getItemId() == R.id.status_option_2) {
                        icon.setColorFilter(statusLowColor, PorterDuff.Mode.SRC_IN);
                    }
                }
            }
        }

        popup.setOnMenuItemClickListener(item -> {
            FoodListing foodListing = adapter.getFoodListingAt(position);
            int id = item.getItemId();
            if (id == R.id.status_option_1) {
                adapter.changeStatusAndUpdateColor(position, "AVAILABLE");
                foodListing.status = "AVAILABLE";
            } else if (id == R.id.status_option_2) {
                adapter.changeStatusAndUpdateColor(position, "LOW");
                foodListing.status = "LOW";
            }
            foodListingModal.updateFoodListing(foodListing);
            return true;
        });

        popup.show();
    }


    private void startFoodPostActivityWithFoodListing(FoodListing foodListing) {
        Intent intent = new Intent(this, FoodPostActivity.class);
        intent.putExtra("food_id", foodListing.food_id);
        intent.putExtra("food_name", foodListing.food_name);
        intent.putExtra("rso_name", foodListing.rso_name);
        intent.putExtra("latitude", foodListing.latitude);
        intent.putExtra("longitude", foodListing.longitude);
        intent.putExtra("description", foodListing.description);
        intent.putStringArrayListExtra("dietary_restrictions", foodListing.dietary_restrictions);
        intent.putExtra("status", foodListing.status);
        intent.putExtra("rso_name", foodListing.rso_name);

        // For Date, you need to convert it to a long or string as Intent doesn't support Date directly
        if (foodListing.createdAt != null) {
            intent.putExtra("createdAt", foodListing.createdAt.getTime());
        }

        startActivity(intent);
    }
    private void showBottomDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheet_layout);

        LinearLayout rso1 = dialog.findViewById(R.id.rso1);
        LinearLayout student1 = dialog.findViewById(R.id.student1);
        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);

        rso1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Toast.makeText(RSOActivity.this, "Already in RSO 1 screen", Toast.LENGTH_SHORT).show();
            }
        });

        student1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Toast.makeText(RSOActivity.this, "Switching to Student 1 screen", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RSOActivity.this, MapsActivity.class);
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

