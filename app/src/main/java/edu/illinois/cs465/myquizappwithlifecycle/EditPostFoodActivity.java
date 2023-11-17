package edu.illinois.cs465.myquizappwithlifecycle;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

public class EditPostFoodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rso_food_card); // Use the edit_post_food.xml layout

        //SOHYUN I changed this button to R.id.vert_icon_button from R.id.imageBtn which does not exist
        ImageButton imageBtn = findViewById(R.id.vert_icon_button);
        if (imageBtn != null) {
            setupPopupMenu(imageBtn);
        }
    }

    private void setupPopupMenu(ImageButton imageBtn) {
        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(EditPostFoodActivity.this, imageBtn);
                popup.getMenuInflater().inflate(R.menu.menu_edit_delete, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.action_edit) {
                            setContentView(R.layout.edit_post_food);
                            return true;
                        } else if (id == R.id.action_delete) {
                            showDeleteConfirmationDialog();
                            return true;
                        }
                        return false;
                    }

                });
                popup.show();
            }
        });
    }

    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Event")
                .setMessage("Are you sure you want to delete this event? This will remove your event from the map.")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteEvent();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void deleteEvent() {
        Toast.makeText(this, "Event deleted", Toast.LENGTH_SHORT).show();
        // can also close the activity after deletion
         finish();
    }

}
