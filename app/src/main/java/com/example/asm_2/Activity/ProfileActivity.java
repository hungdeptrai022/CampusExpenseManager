package com.example.asm_2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.asm_2.R;

public class ProfileActivity extends AppCompatActivity {
    private TextView textViewUsername, textViewPhone, textViewEmail;
    private Button btnEditProfile;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textViewUsername = findViewById(R.id.username_value);
        textViewEmail    = findViewById(R.id.email_value);
        textViewPhone = findViewById(R.id.phone_value);
        btnEditProfile = findViewById(R.id.buttonEdit);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String email = intent.getStringExtra("email");
        String phone = intent.getStringExtra("phone");

        textViewUsername.setText(username);
        textViewEmail.setText(email);
        textViewPhone.setText(phone);

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                editIntent.putExtra("username", username);
                editIntent.putExtra("email", email);
                editIntent.putExtra("phone", phone);

                startActivity(editIntent);
            }
        });

    }
}
