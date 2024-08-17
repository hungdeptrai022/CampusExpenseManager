package com.example.asm_2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.asm_2.Activity.DatabaseSQLite.UserDatabase;
import com.example.asm_2.R;

public class EditProfileActivity extends AppCompatActivity {
    private EditText editTextUsername, editTextPhone, editTextEmail;
    private Button buttonUpdate;
    public UserDatabase userDatabase;
    private String originalEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextEmail = findViewById(R.id.editTextEmail);
        buttonUpdate = findViewById(R.id.buttonUpdate);

        userDatabase = new UserDatabase(this);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String phone = intent.getStringExtra("phone");
        originalEmail = intent.getStringExtra("email");

        editTextUsername.setText(username);
        editTextPhone.setText(phone);
        editTextEmail.setText(originalEmail);

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newUsername = editTextUsername.getText().toString().trim();
                String newPhone = editTextPhone.getText().toString().trim();
                String newEmail = editTextEmail.getText().toString().trim();

                if(!newUsername.isEmpty() && !newPhone.isEmpty() && !newEmail.isEmpty()){
                    userDatabase.updateUser(originalEmail, newUsername, newPhone, newEmail);
                    Toast.makeText(EditProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
