package edu.illinois.cs465.myquizappwithlifecycle;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class FoodPostActivity extends AppCompatActivity {
    private TextInputLayout foodName;
    private TextInputLayout description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_food);
    }
}
