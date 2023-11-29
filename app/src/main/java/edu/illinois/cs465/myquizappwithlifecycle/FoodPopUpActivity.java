package edu.illinois.cs465.myquizappwithlifecycle;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.Chip;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;

import edu.illinois.cs465.myquizappwithlifecycle.data.FoodListing;

public class FoodPopUpActivity  extends AppCompatActivity implements Serializable {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static Dialog showFoodInfoPopup(Context context, FoodListing foodListing) {
        Dialog dialog = new Dialog(context);
        View dialogLayout = LayoutInflater.from(context).inflate(R.layout.food_popup, null);
        dialog.setContentView(dialogLayout);
        TextView tv = (TextView) dialog.findViewById(R.id.title_popUp);
        tv.setText(foodListing.food_name);
        TextView rsoView = (TextView) dialog.findViewById(R.id.rso_name);
        rsoView.setText(foodListing.rso_name);
        Chip status = (Chip) dialog.findViewById(R.id.chip3);
        status.setText(foodListing.status);

        Chip c1 = (Chip) dialog.findViewById(R.id.chip1);
        Chip c2 = (Chip) dialog.findViewById(R.id.chip2);
        ArrayList<String> diets =  foodListing.dietary_restrictions;
        if(diets == null || diets.size()==0){
            c1.setText("Vegetarian");
            c2.setText("Dairy-Free");
        }
        if(diets.size()==2) {
            c1.setText(diets.get(0));
            c2.setText(diets.get(1));
        }
        else if(diets.size()==1){
            c1.setText(diets.get(0));
            c2.setText("Dairy-Free");
        }

        dialog.show();
        return dialog;
    }

}
