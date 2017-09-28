package edu.gatech.thethunderflyers.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {
    private Button cancel;
    private EditText firstName;
    private EditText lastName;
    private EditText username;
    private EditText password;
    private EditText confirmPass;
    private Spinner userOrAdmin;

    public static ArrayList<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        cancel = (Button) findViewById(R.id.cancelButton);
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        username = (EditText) findViewById(R.id.regUserName);
        password = (EditText) findViewById(R.id.password);
        confirmPass = (EditText) findViewById(R.id.confirmPass);
        userOrAdmin = (Spinner) findViewById(R.id.userOrAdmin);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, UserMode.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userOrAdmin.setAdapter(adapter);
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
        UserMode um = (UserMode) userOrAdmin.getSelectedItem();

        if (firstN.equals("")) {
            Toast.makeText(this, "First name is empty!", Toast.LENGTH_SHORT).show();
        }
        if (lastN.equals("")) {
            Toast.makeText(this, "Last name is empty!", Toast.LENGTH_SHORT).show();
        }
        if (user.equals("")) {
            Toast.makeText(this, "Username is empty!", Toast.LENGTH_SHORT).show();
        }
        if (pass.equals("")) {
            Toast.makeText(this, "Password is empty!", Toast.LENGTH_SHORT).show();
        }
        if (conPass.equals("")) {
            Toast.makeText(this, "Confirm your password!", Toast.LENGTH_SHORT).show();
        }
        if (!pass.equals(conPass)) {
            Toast.makeText(this, "Passwords don't match!", Toast.LENGTH_SHORT).show();
        }

        User u = new User(firstN, lastN, user, pass, um);
        if (users.contains(u)) {
            Toast.makeText(this, "User already exists!", Toast.LENGTH_SHORT).show();
        } else {
            boolean userNameSame = false;
            for (User us : RegisterActivity.users) {
                if (us.getUsername().equals(user)) {
                    userNameSame = true;
                    break;
                }
            }
            if (userNameSame) {
                Toast.makeText(this, "Username already taken!", Toast.LENGTH_SHORT).show();
            } else {
                users.add(u);
            }
        }
    }

    public enum UserMode {
        USER ("user"), ADMIN ("admin");

        private String representation;

        UserMode (String representation) {
            this.representation = representation;
        }
    }
}
