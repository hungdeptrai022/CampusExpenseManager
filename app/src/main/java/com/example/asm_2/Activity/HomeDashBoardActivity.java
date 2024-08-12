package com.example.asm_2.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.asm_2.R;

public class HomeDashBoardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_dashboard);
        Button btnNotiFake = findViewById(R.id.btnReturnFromHome);
        btnNotiFake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(HomeDashBoardActivity.this)
                        .setTitle("Warning")
                        .setMessage("You have exceeded your budget limit on Eating!.")
                        .setPositiveButton("OK", null)
                        .show();
            }
        });
    }
}
