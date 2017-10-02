package edu.gatech.thethunderflyers.android.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.gatech.thethunderflyers.android.R;
import edu.gatech.thethunderflyers.android.model.User;
import edu.gatech.thethunderflyers.android.model.UserMode;
import edu.gatech.thethunderflyers.android.util.FormValidator;

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
        ArrayAdapter<UserMode> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, UserMode.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userOrAdmin.setAdapter(adapter);

        FormValidator fn = new FormValidator(firstName, "First name");
        FormValidator ln = new FormValidator(lastName, "Last name");
        FormValidator un = new FormValidator(username, "Username");
        FormValidator pa = new FormValidator(password, "Password");
        FormValidator cp = new FormValidator(confirmPass, "Confirm password");

        firstName.addTextChangedListener(fn);
        firstName.setOnFocusChangeListener(fn);
        lastName.addTextChangedListener(ln);
        lastName.setOnFocusChangeListener(ln);
        username.addTextChangedListener(un);
        username.setOnFocusChangeListener(un);
        password.addTextChangedListener(pa);
        password.setOnFocusChangeListener(pa);
        confirmPass.addTextChangedListener(cp);
        confirmPass.setOnFocusChangeListener(cp);
    }

    public void cancel(View view) {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }

    public void submitReg(View view) {
        String firstN = firstName.getText().toString();
        String lastN = lastName.getText().toString();
        String user = username.getText().toString().toLowerCase();
        String pass = password.getText().toString();
        String conPass = confirmPass.getText().toString();
        UserMode um = (UserMode) userOrAdmin.getSelectedItem();

        if (!pass.equals(conPass)) {
            confirmPass.setError("Passwords don't match!");
        }

        boolean isValid = firstName.getError() == null && lastName.getError() == null
                && username.getError() == null && password.getError() == null
                && confirmPass.getError() == null && !TextUtils.isEmpty(firstN)
                && !TextUtils.isEmpty(lastN) && !TextUtils.isEmpty(user) && !TextUtils.isEmpty(pass)
                && !TextUtils.isEmpty(conPass);

        if (isValid) {
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
                    Toast.makeText(this,"Account registered successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    //we should add something to notify user that they're registered
                    //and go back to the welcome screen
                    //or maybe we can automatically log them in after they register
                }
            }
        } else {
            Toast.makeText(this, "One or more fields is invalid!", Toast.LENGTH_SHORT).show();
        }
    }


}
