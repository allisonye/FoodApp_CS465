package edu.illinois.cs465.myquizappwithlifecycle;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class AddPostActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rso_base_screen);

        findViewById(R.id.bottomAppBar).setOnClickListener(v -> {
            Intent intent = new Intent(this, FoodPostActivity.class);
            startActivity(intent);
        });
    }
}
