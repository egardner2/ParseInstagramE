package com.insta.parseinstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    private static String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ParseUser currentUser = ParseUser.getCurrentUser();

        if(currentUser != null) {
            goMainActivity();
        }

        final EditText etUsername = findViewById(R.id.etUsername);
        final EditText etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnCreate = findViewById(R.id.btnCreate);

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                login(username, password);

            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                goCreateActivity();
            }
        });
    }

    private void goCreateActivity() {

        Log.d(TAG, "Navigating to Create Activity");

        Intent i = new Intent(this, CreateAccount.class);
        startActivity(i);
    }

    private void login(String username, String password) {

        ParseUser.logInInBackground(username, password, new LogInCallback() {

            @Override
            public void done(ParseUser user, ParseException e) {

                if(e != null) {

                    Log.d(TAG, "Encountered error during login");
                    e.printStackTrace();
                    return;
                }

                Log.d(TAG, "Login Successful");
                goMainActivity();
            }
        });
    }

    private void goMainActivity() {

        Log.d(TAG, "Navigating to Main Activity");

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
