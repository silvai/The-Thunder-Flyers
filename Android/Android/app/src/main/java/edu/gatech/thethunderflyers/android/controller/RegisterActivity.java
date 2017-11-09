package edu.gatech.thethunderflyers.android.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import edu.gatech.thethunderflyers.android.R;
import edu.gatech.thethunderflyers.android.model.APIMessage;
import edu.gatech.thethunderflyers.android.model.Model;
import edu.gatech.thethunderflyers.android.model.UserMode;
import edu.gatech.thethunderflyers.android.util.AsyncHandler;
import edu.gatech.thethunderflyers.android.util.ExceptionAlertDialog;
import edu.gatech.thethunderflyers.android.util.Navigator;
import edu.gatech.thethunderflyers.android.util.NotSuccessAlertDialog;
import edu.gatech.thethunderflyers.android.util.Validator;

import static edu.gatech.thethunderflyers.android.util.APIClient.API_CLIENT;

/**
 * Activity to register a new account
 */
public class RegisterActivity extends AppCompatActivity implements AsyncHandler<APIMessage> {
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
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        username = findViewById(R.id.regUserName);
        password = findViewById(R.id.password);
        confirmPass = findViewById(R.id.confirmPass);
        userOrAdmin = findViewById(R.id.userOrAdmin);
        ArrayAdapter<UserMode> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, UserMode.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userOrAdmin.setAdapter(adapter);
    }

    /**
     * Handles cancel button click.
     * @param view the callback parameter
     */
    public void cancel(View view) {
        Navigator.goToWelcomeActivity(this);
    }

    /**
     * Handles submit button click by performing some verification and either showing an error
     * message or creating a new user.
     * @param view the callback parameter
     */
    public void submitReg(View view) {
        Validator validator = new Validator(firstName, lastName, username, password, confirmPass);
        boolean isValid = validator.validate();
        isValid = validator.checkPassword(password, confirmPass) && isValid;
        if (isValid) {
            Editable firstNEditable = firstName.getText();
            Editable lastNEditable = lastName.getText();
            Editable userEditable = username.getText();
            Editable passEditable = password.getText();

            String firstN = firstNEditable.toString();
            String lastN = lastNEditable.toString();
            String user = userEditable.toString();
            user = user.toLowerCase();
            String pass = passEditable.toString();
            UserMode um = (UserMode) userOrAdmin.getSelectedItem();

            API_CLIENT.register(Model.getUser(firstN, lastN, user, pass, um),
                    new WeakReference<>(this));
        } else {
            Toast t = Toast.makeText(this, "One or more fields is invalid!", Toast.LENGTH_SHORT);
            t.show();
        }
    }

    @Override
    public void handleResponse(APIMessage response, Exception ex) {
        if (ex != null) {
            ExceptionAlertDialog ead = new ExceptionAlertDialog(ex, this);
            ead.show();
        } else if (response.isSuccess()) {
            String message = response.getMessage();
            NotSuccessAlertDialog nsad = new NotSuccessAlertDialog(message, this);
            nsad.show();
        } else {
            Toast t = Toast.makeText(this, "Successfully registered user!", Toast.LENGTH_SHORT);
            t.show();
            Navigator.goToLoginActivity(this);
        }
    }
}
