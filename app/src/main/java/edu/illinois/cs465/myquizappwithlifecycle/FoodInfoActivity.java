package edu.illinois.cs465.myquizappwithlifecycle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

public class FoodInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.food_preview);
        TextView title = findViewById(R.id.post_title);
        title.setText((String)intent.getSerializableExtra("foodName"));
        TextView status = findViewById(R.id.status_food);
        status.setText((String)intent.getSerializableExtra("status"));
        TextView description = findViewById(R.id.description);
        description.setText((String)intent.getSerializableExtra("description"));
        TextView rso_name = findViewById(R.id.rso_name_text);
        rso_name.setText((String)intent.getSerializableExtra("rso_name"));
        ChipGroup chipGroup = findViewById(R.id.chipGroupDiet);
        ArrayList<String> diets = (ArrayList<String>)intent.getSerializableExtra("diet");
        if(diets != null) {
            for (String diet : diets) {
                addReadOnlyChipToGroup(this, chipGroup, diet);
            }
        }

        findViewById(R.id.back_button).setOnClickListener(v -> {
            finish();
        });

    }

    private void addReadOnlyChipToGroup(Context context, ChipGroup chipGroup, String text) {
        Chip chip = new Chip(context);
        chip.setText(text);
        chip.setClickable(false);
        chip.setFocusable(false);
        chip.setCheckable(false);

        chip.setLayoutParams(new ChipGroup.LayoutParams(ChipGroup.LayoutParams.WRAP_CONTENT, ChipGroup.LayoutParams.WRAP_CONTENT));
        chip.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        chipGroup.addView(chip);
    }
}
