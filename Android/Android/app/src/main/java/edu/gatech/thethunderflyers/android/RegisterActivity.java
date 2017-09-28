package edu.gatech.thethunderflyers.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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

    }

    public enum UserMode {
        USER ("user"), ADMIN ("admin");

        private String representation;

        UserMode (String representation) {
            this.representation = representation;
        }
    }
}
