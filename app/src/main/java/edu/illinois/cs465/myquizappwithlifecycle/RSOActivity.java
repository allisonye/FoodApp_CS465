package edu.illinois.cs465.myquizappwithlifecycle;

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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class RSOActivity extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.rso_food_card);
            findViewById(R.id.food_card_status_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Code to display the menu
                    showStatusMenu(v, R.menu.overflow_menu);
                }
            });
            findViewById(R.id.vert_icon_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Code to display the menu
                    showVertMenu(v, R.menu.vert_menu);
                }
            });
        }

    private void showDeleteConfirmationPopup() {
        AlertDialog dialog = new MaterialAlertDialogBuilder(this)
                .setTitle(getResources().getString(R.string.delete_confirmation_title))
                .setMessage(getResources().getString(R.string.delete_confirmation_message))
                .setNegativeButton(getResources().getString(R.string.cancel_delete), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        // Respond to negative button press
                    }
                })
                .setPositiveButton(getResources().getString(R.string.confirm_delete), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        // Respond to positive button press
                    }
                }).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.overflow_menu, menu);
        return true;
    }


    private void showVertMenu(View v, @MenuRes int menuRes) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.getMenuInflater().inflate(menuRes, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.option_vert_menu_delete) {
                    showDeleteConfirmationPopup();
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


    private void showStatusMenu(View v, @MenuRes int menuRes) {
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
                    if (item.getItemId() == R.id.option_1) {
                        icon.setColorFilter(statusAvailableColor, PorterDuff.Mode.SRC_IN);
                    } else if (item.getItemId() == R.id.option_2) {
                        icon.setColorFilter(statusLowColor, PorterDuff.Mode.SRC_IN);
                    }
                }
            }
        }

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Handle menu item selection
                return true;
            }
        });
        popup.show();
    }
}
