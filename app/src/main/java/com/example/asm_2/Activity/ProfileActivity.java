package com.example.asm_2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.asm_2.R;

public class ProfileActivity extends AppCompatActivity {
    private TextView textViewUsername, textViewPhone, textViewEmail;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textViewUsername = findViewById(R.id.username_value);
        textViewEmail    = findViewById(R.id.email_value);
        textViewPhone = findViewById(R.id.phone_value);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String email = intent.getStringExtra("email");
        String phone = intent.getStringExtra("phone");

        textViewUsername.setText(username);
        textViewEmail.setText(email);
        textViewPhone.setText(phone);

    }
}
