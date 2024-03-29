package com.example.shivam_wission;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.shivam_wission.viewmodels.AddUserViewModel;
import com.google.gson.JsonObject;

public class AddUserActivity extends AppCompatActivity {

    private EditText etName, etEmail, etPhone;
    private Button btnSubmit;
    private AddUserViewModel mAddUserViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        etName = findViewById(R.id.et_add_name);
        etEmail = findViewById(R.id.et_add_email);
        etPhone = findViewById(R.id.et_add_phone);

        btnSubmit = findViewById(R.id.btn_add_submit);


        mAddUserViewModel = ViewModelProviders.of(this).get(AddUserViewModel.class);
        mAddUserViewModel.init();


        Intent intent = getIntent();
        if (intent.hasExtra("EXTRA_ID")) {
            setTitle("View User");
            btnSubmit.setVisibility(View.GONE);
            etName.setEnabled(false);
            etPhone.setEnabled(false);
            etEmail.setEnabled(false);
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            if (intent.hasExtra("name")) {
                etName.setText(intent.getStringExtra("name"));
            }
            if (intent.hasExtra("email")) {
                etEmail.setText(intent.getStringExtra("email"));
            }
            if (intent.hasExtra("phone")) {
                etPhone.setText(intent.getStringExtra("phone"));
            }
        } else {
            setTitle("Add User");
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(false);


        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name, email, phone;
                name = etName.getText().toString().trim();
                email = etEmail.getText().toString().trim();
                phone = etPhone.getText().toString().trim();

                Log.d("USER ACTIVITY", "onClick: " + name + email + phone);

                JsonObject j = new JsonObject();
                j.addProperty("name", name);
                j.addProperty("email", email);
                j.addProperty("phone", phone);


                if (checkText(name, email, phone)) {
                    mAddUserViewModel.addNewUser(j);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }

            }
        });

    }

    private boolean checkText(String name, String email, String phone) {
        if (name.isEmpty()) {
            etName.setError("Name is required");
            etName.requestFocus();
            return false;
        }
        if (phone.isEmpty()) {
            etPhone.setError("Phone is required");
            etPhone.requestFocus();
            return false;
        }

        if (!Patterns.PHONE.matcher(phone).matches()) {
            etPhone.setError("Enter valid Phone");
            etPhone.requestFocus();
            return false;
        }


        if (email.isEmpty()) {
            etEmail.setError("Email is required");
            etEmail.requestFocus();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Enter a valid email");
            etEmail.requestFocus();
            return false;
        }

        return true;


    }
}
