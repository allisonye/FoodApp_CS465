package edu.illinois.cs465.myquizappwithlifecycle;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.io.Serializable;

public class FoodPopUpActivity  extends AppCompatActivity implements Serializable {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView tv = (TextView)findViewById(R.id.title_popUp);
        String food_name = (String) getIntent().getSerializableExtra("foodName");
        tv.setText(food_name);
    }
}
