package com.example.asm_2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.asm_2.R;

public class DashboardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        CardView overviewCard = findViewById(R.id.overview_card);
        CardView recordCard = findViewById(R.id.record_card);
        CardView budgetCard = findViewById(R.id.budget_card);
        CardView profileCard = findViewById(R.id.profile_card);

        overviewCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, OverviewActivity.class);
                startActivity(intent);
            }
        });
    }
}
