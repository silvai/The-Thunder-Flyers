package edu.gatech.thethunderflyers.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
    }

    /**
     * Handles submit button click by verifying whether or not the submitted credentials match an
     * actual user. If so, logs the user in by transitioning to MainActivity; otherwise shows an
     * error.
     * @param v the callback parameter
     */
    public void submit(View v) {
        String user = username.getText().toString().toLowerCase();
        String pass = password.getText().toString();

        boolean foundUserName = false;
        boolean foundPassword = false;

        for (User u : RegisterActivity.users) {
            if (u.getUsername().equals(user)) {
                foundUserName = true;
                if (u.getPassword().equals(pass)) {
                    foundPassword = true;
                    break;
                }
            }
        }

        if (foundUserName && foundPassword) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (foundUserName) {
            Toast.makeText(this, "Wrong password!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "User does not exist!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Handles cancel button click.
     * @param v the callback parameter
     */
    public void cancel(View v) {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }
}
