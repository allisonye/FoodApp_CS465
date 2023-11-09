package edu.illinois.cs465.myquizappwithlifecycle;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static String DEBUG = "DEBUG";
    private static final String KEY = "KEY";
    private static final String VALUE = "We passed the bundle of data";
    private Button falseButton;
    private Button trueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(DEBUG, "onCreate()");
        setContentView(R.layout.post_food);
        if (savedInstanceState != null) {
            String value = new String(savedInstanceState.getString(KEY));
            Log.d(DEBUG, value);
        }

//        falseButton = (Button) findViewById(R.id.false_button);
//        trueButton = (Button) findViewById(R.id.true_button);

//        falseButton.setOnClickListener(this);
//        trueButton.setOnClickListener(this);
    }

    protected void onSaveInstanceState(Bundle savedInstance) {
        super.onSaveInstanceState(savedInstance);
        Log.d(DEBUG, "onSavedInstanceState()");
        savedInstance.putString(KEY, VALUE);
    }

    protected void onStart() {
        super.onStart();
        Log.d(DEBUG, "OnStart()");
    }
    protected void onResume() {
        super.onResume();
        Log.d(DEBUG, "OnResume()");
    }
    protected void onRestart() {
        super.onRestart();
        Log.d(DEBUG, "OnRestart()");
    }
    protected void onPause() {
        super.onPause();
        Log.d(DEBUG, "OnPause()");
    }
    protected void onStop() {
        super.onStop();
        Log.d(DEBUG, "OnStop()");
    }
    protected void onDestroy() {
        super.onDestroy();
        Log.d(DEBUG, "OnDestroy()");
    }


    public void onClick(View v) {
        if (v.getId() == R.id.false_button) {
            Toast.makeText(this, "CORRECT", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.true_button) {
            Toast.makeText(this, "INCORRECT", Toast.LENGTH_SHORT).show();
        }
    }
//    bottomAppBar.setNavigationOnClickListener {
//            // Handle navigation icon press
//    }
//
//    bottomAppBar.setOnMenuItemClickListener { menuItem ->
//            when (menuItem.itemId) {
//                R.id.search -> {
//                    // Handle search icon press
//                    true
//                }
//                R.id.more -> {
//                    // Handle more item (inside overflow menu) press
//                    true
//                }
//                else -> false
//            }
//        }
}
