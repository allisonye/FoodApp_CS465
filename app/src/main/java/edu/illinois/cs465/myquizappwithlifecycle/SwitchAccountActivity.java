package edu.illinois.cs465.myquizappwithlifecycle;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;

import edu.illinois.cs465.myquizappwithlifecycle.databinding.LandingScreenBinding;

public class SwitchAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LandingScreenBinding binding = LandingScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.accountCircleImage.setOnClickListener(v -> showBottomDialog());
    }

    private void showBottomDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheet_layout);

        LinearLayout rso1 = dialog.findViewById(R.id.rso1);
        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);

        rso1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Toast.makeText(SwitchAccountActivity.this, "Switching to RSO 1 screen", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SwitchAccountActivity.this, RSOActivity.class);
                startActivity(intent);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = com.google.android.material.R.style.MaterialAlertDialog_Material3_Animation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
}
