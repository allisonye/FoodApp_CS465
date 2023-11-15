package edu.illinois.cs465.myquizappwithlifecycle;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class FoodInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.food_preview);

        findViewById(R.id.back_button).setOnClickListener(v -> {
            finish();
        });
    }
}
