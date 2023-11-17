// Code with edit/delete button with Bryan's original code
//package edu.illinois.cs465.myquizappwithlifecycle;
//
//import android.annotation.SuppressLint;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.graphics.PorterDuff;
//import android.graphics.drawable.Drawable;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//
//import androidx.annotation.MenuRes;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.view.menu.MenuBuilder;
//import androidx.appcompat.widget.PopupMenu;
//import androidx.core.content.ContextCompat;
//import androidx.lifecycle.Observer;
//import androidx.lifecycle.ViewModelProvider;
//import androidx.lifecycle.ViewModelProviders;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.android.material.dialog.MaterialAlertDialogBuilder;
//
//import java.util.List;
//
//import edu.illinois.cs465.myquizappwithlifecycle.data.FoodListing;
//import edu.illinois.cs465.myquizappwithlifecycle.data.ViewModal;
//import edu.illinois.cs465.myquizappwithlifecycle.databinding.ActivityMainBinding;
//import edu.illinois.cs465.myquizappwithlifecycle.rso_recycler_view.FoodCardAdapter;
//
//public class RSOActivity extends AppCompatActivity {
//
//        private ViewModal foodListingModal;
//        private ActivityMainBinding binding;
//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            binding = ActivityMainBinding.inflate(getLayoutInflater());
//            setContentView(R.layout.rso_list_food);
//
//            RecyclerView recyclerView = findViewById(R.id.recycler_view);
//            recyclerView.setLayoutManager(new LinearLayoutManager(this));
//            recyclerView.setHasFixedSize(true);
//
//            final FoodCardAdapter adapter = new FoodCardAdapter();
//            recyclerView.setAdapter(adapter);
//
//            foodListingModal = new ViewModelProvider(this).get(ViewModal.class);
//            foodListingModal.deleteAllFoodListings();
//            FoodListing fl1 = new FoodListing();
//            fl1.food_name = "CIF2";
//            fl1.latitude = 40.11260764797458;
//            fl1.longitude = -88.22836335177905;
//            fl1.status = "AVAILABLE";
//            foodListingModal.insertFoodListing(fl1);
//
//            FoodListing fl2 = new FoodListing();
//            fl2.food_name = "CIF3";
//            fl2.latitude = 40.11260764797458;
//            fl2.longitude = -88.22836335177905;
//            fl2.status = "LOW";
//            foodListingModal.insertFoodListing(fl2);
//
//
//
//            foodListingModal.getAllFoodListings().observe(this, new Observer<List<FoodListing>>() {
//                public void onChanged(@Nullable List<FoodListing> foodListings) {
//                    adapter.setFoodListings(foodListings);
//                }
//            });
//
//            adapter.setOnClickListener(new FoodCardAdapter.OnClickListener() {
//                @Override
//                public void onClick(View view, int position, FoodListing foodListing) {
//                    if (view.getId() == R.id.food_card_status_button) {
//                        showStatusMenu(view, R.menu.overflow_menu);
//                    }
//                    else if (view.getId() == R.id.vert_icon_button) {
//                        showVertMenu(view, R.menu.vert_menu);
//                    }
//                }
//            });
//
////            findViewById(R.id.food_card_status_button).setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    showStatusMenu(v, R.menu.overflow_menu);
////                }
////            });
////
////            findViewById(R.id.vert_icon_button).setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    showVertMenu(v, R.menu.vert_menu);
////                }
////            });
//
//            findViewById(R.id.rso_profile_icon).setOnClickListener(v -> {
//                Intent intent = new Intent(this, MainActivity.class);
//                startActivity(intent);
//            });
//
//            findViewById(R.id.add_food_button).setOnClickListener(v -> {
//                Intent intent = new Intent(this, FoodPostActivity.class);
//                startActivity(intent);
//            });
//        }
//
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
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.overflow_menu, menu);
//        return true;
//    }
//
//
//    private void showVertMenu(View v, @MenuRes int menuRes) {
//        PopupMenu popup = new PopupMenu(this, v);
//        popup.getMenuInflater().inflate(menuRes, popup.getMenu());
//        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                if (item.getItemId() == R.id.option_vert_menu_delete) {
//                    showDeleteConfirmationPopup();
//                    return true;
//                }
//                // Handle other menu item clicks if necessary
//                return false;
//            }
//        });
//        popup.setOnDismissListener(new PopupMenu.OnDismissListener() {
//            @Override
//            public void onDismiss(PopupMenu menu) {
//                // Respond to popup being dismissed.
//            }
//        });
//
//        // Show the popup menu.
//        popup.show();
//    }
//
//
//    @SuppressLint("RestrictedApi")
//    private void showStatusMenu(View v, @MenuRes int menuRes) {
//        PopupMenu popup = new PopupMenu(this, v);
//        popup.getMenuInflater().inflate(menuRes, popup.getMenu());
//
//        // Define your colors
//        int statusAvailableColor = ContextCompat.getColor(this, R.color.status_available_color);
//        int statusLowColor = ContextCompat.getColor(this, R.color.status_low_color);
//
//        if (popup.getMenu() instanceof MenuBuilder) {
//            MenuBuilder menuBuilder = (MenuBuilder) popup.getMenu();
//            menuBuilder.setOptionalIconsVisible(true);
//
//            for (MenuItem item : menuBuilder.getVisibleItems()) {
//                Drawable icon = item.getIcon();
//                if (icon != null) {
//                    // Set the tint based on some condition or item ID
//                    if (item.getItemId() == R.id.status_option_1) {
//                        icon.setColorFilter(statusAvailableColor, PorterDuff.Mode.SRC_IN);
//                    } else if (item.getItemId() == R.id.status_option_2) {
//                        icon.setColorFilter(statusLowColor, PorterDuff.Mode.SRC_IN);
//                    }
//                }
//            }
//        }
//
//        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                // if (item.getItemId() == R.id.status_option_1) {
//                //     // click AVAILABLE
//                //     foodListingModal.getFoodListingById(??????).observe(this, new Observer<FoodListing>() {
//                //         @Override
//                //         public void onChanged(FoodListing foodListing) {
//                //             if (foodListing != null) {
//                //                 // Log.d(DEBUG, "GET BY ID    " + foodListing.food_name);
//                //                 foodListing.status = "AVAILABLE";
//                //                 foodListingModal.updateFoodListing();
//                //             }
//                //         }
//                //     });
//                // } else if (item.getItemId() == R.id.status_option_2) {
//                //     // click LOW
//                //     foodListingModal.getFoodListingById(??????).observe(this, new Observer<FoodListing>() {
//                //         @Override
//                //         public void onChanged(FoodListing foodListing) {
//                //             if (foodListing != null) {
//                //                 // Log.d(DEBUG, "GET BY ID    " + foodListing.food_name);
//                //                 foodListing.status = "LOW";
//                //                 foodListingModal.updateFoodListing();
//                //             }
//                //         }
//                //     });
//                // }
//                // Handle other menu item clicks if necessary
//                return false;
//            }
//        });
//        popup.show();
//    }
//
//}
//

//////////////////////////
// The below only handles delete button

package edu.illinois.cs465.myquizappwithlifecycle;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

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

        adapter = new FoodCardAdapter();
        recyclerView.setAdapter(adapter);

        foodListingModal = new ViewModelProvider(this).get(ViewModal.class);
        foodListingModal.deleteAllFoodListings();
        FoodListing fl1 = new FoodListing();
        fl1.food_name = "CIF2";
        fl1.latitude = 40.11260764797458;
        fl1.longitude = -88.22836335177905;
        fl1.status = "AVAILABLE";
        foodListingModal.insertFoodListing(fl1);

        FoodListing fl2 = new FoodListing();
        fl2.food_name = "CIF3";
        fl2.latitude = 40.11260764797458;
        fl2.longitude = -88.22836335177905;
        fl2.status = "LOW";
        foodListingModal.insertFoodListing(fl2);



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

        findViewById(R.id.rso_profile_icon).setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

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
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.option_vert_menu_delete) {
                    showDeleteConfirmationPopup(position);
                    return true;
                }
                // Handle other menu item clicks if necessary
                return false;
            }
        });
        popup.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                // Respond to popup being dismissed.
            }
        });

        // Show the popup menu.
        popup.show();
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

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // if (item.getItemId() == R.id.status_option_1) {
                //     // click AVAILABLE
                //     foodListingModal.getFoodListingById(??????).observe(this, new Observer<FoodListing>() {
                //         @Override
                //         public void onChanged(FoodListing foodListing) {
                //             if (foodListing != null) {
                //                 // Log.d(DEBUG, "GET BY ID    " + foodListing.food_name);
                //                 foodListing.status = "AVAILABLE";
                //                 foodListingModal.updateFoodListing();
                //             }
                //         }
                //     });
                // } else if (item.getItemId() == R.id.status_option_2) {
                //     // click LOW
                //     foodListingModal.getFoodListingById(??????).observe(this, new Observer<FoodListing>() {
                //         @Override
                //         public void onChanged(FoodListing foodListing) {
                //             if (foodListing != null) {
                //                 // Log.d(DEBUG, "GET BY ID    " + foodListing.food_name);
                //                 foodListing.status = "LOW";
                //                 foodListingModal.updateFoodListing();
                //             }
                //         }
                //     });
                // }
                // Handle other menu item clicks if necessary
                return false;
            }
        });
        popup.show();
    }

}