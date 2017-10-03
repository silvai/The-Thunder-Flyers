package edu.gatech.thethunderflyers.android.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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

import com.google.gson.Gson;

import java.util.ArrayList;

import edu.gatech.thethunderflyers.android.R;
import edu.gatech.thethunderflyers.android.model.APIMessage;
import edu.gatech.thethunderflyers.android.model.User;
import edu.gatech.thethunderflyers.android.model.UserMode;
import edu.gatech.thethunderflyers.android.util.AsyncHandler;
import edu.gatech.thethunderflyers.android.util.FormValidator;
import edu.gatech.thethunderflyers.android.util.RegisterTask;

public class RegisterActivity extends AppCompatActivity implements AsyncHandler<APIMessage> {
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
            new RegisterTask(this).execute(new Gson().toJson(u));
        } else {
            Toast.makeText(this, "One or more fields is invalid!", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void handleResponse(APIMessage response, Exception ex) {
        if (ex != null) {
            AlertDialog ad = new AlertDialog.Builder(this)
                    .setMessage("An unexpected error occurred. Please try again later.")
                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).create();
            ad.show();
        } else if (!response.isSuccess()) {
            AlertDialog ad = new AlertDialog.Builder(this)
                    .setMessage(response.getMessage())
                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).create();
            ad.show();
        } else {
            Toast.makeText(this, "Successfully registered user!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
}
