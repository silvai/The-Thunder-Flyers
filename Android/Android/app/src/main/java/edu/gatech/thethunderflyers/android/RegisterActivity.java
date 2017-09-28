package edu.gatech.thethunderflyers.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    private Button cancel;
    private EditText firstName;
    private EditText lastName;
    private EditText username;
    private EditText password;
    private EditText confirmPass;
    private Spinner userOrAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        cancel = (Button) findViewById(R.id.cancelButton);
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        confirmPass = (EditText) findViewById(R.id.confirmPass);
        userOrAdmin = (Spinner) findViewById(R.id.userOrAdmin);
    }
    public void cancel(View view) {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }

    public void submitReg(View view) {
        String firstN = firstName.getText().toString();
        String lastN = lastName.getText().toString();
        String user = username.getText().toString();
        String pass = password.getText().toString();
        String conPass = confirmPass.getText().toString();
        if (firstN == null) {
            Toast.makeText(this, "First name is empty!", Toast.LENGTH_SHORT).show();
        }
        if (lastN == null) {
            Toast.makeText(this, "Last name is empty!", Toast.LENGTH_SHORT).show();
        }
        if (user == null) {
            Toast.makeText(this, "Username is empty!", Toast.LENGTH_SHORT).show();
        }
        if (pass == null) {
            Toast.makeText(this, "Password is empty!", Toast.LENGTH_SHORT).show();
        }
        if (conPass == null) {
            Toast.makeText(this, "Confirm your password!", Toast.LENGTH_SHORT).show();
        }
    }
}
