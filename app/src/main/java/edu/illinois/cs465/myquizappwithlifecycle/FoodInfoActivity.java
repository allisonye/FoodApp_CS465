package edu.illinois.cs465.myquizappwithlifecycle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.Chip;

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
        Chip c = findViewById(R.id.chip_diet);
        Chip c2 = findViewById(R.id.chip_diet2);
        ArrayList<String> diets =  (ArrayList<String>)intent.getSerializableExtra("diet");
        if(diets == null || diets.size()==0){
            c.setText("Vegetarian");
            c2.setText("Dairy-Free");
        }
        if(diets.size()==2) {
            c.setText(diets.get(0));
            c2.setText(diets.get(1));
        }
        else if(diets.size()==1){
            c.setText(diets.get(0));
            c2.setText("Dairy-Free");
        }


        findViewById(R.id.back_button).setOnClickListener(v -> {
            finish();
        });

    }
}
