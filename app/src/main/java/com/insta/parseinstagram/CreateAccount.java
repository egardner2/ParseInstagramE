package com.insta.parseinstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class CreateAccount extends AppCompatActivity {

    public static final String TAG = "CreateAccount";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        final EditText newUsername = findViewById(R.id.newUsername);
        final EditText newPassword = findViewById(R.id.etPassword);
        final EditText newMail = findViewById(R.id.EMail);
        Button btnNew = findViewById(R.id.btnNew);

        btnNew.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String username = newUsername.getText().toString();
                String password = newPassword.getText().toString();
                String mail = newMail.getText().toString();

                // Create the ParseUser
                ParseUser user = new ParseUser();

                // Set core properties
                user.setUsername(username);
                user.setPassword(password);
                user.setEmail(mail);

                // Invoke signUpInBackground
                user.signUpInBackground(new SignUpCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            goLoginActivity();
                        } else {
                            Log.d(TAG, "Error creating account " + e);
                        }
                    }
                });
            }
        });

    }

    private void goLoginActivity() {

        Log.d(TAG, "Navigating to Login Activity");

        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }
}
