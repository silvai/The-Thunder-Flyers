package edu.gatech.thethunderflyers.android.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import edu.gatech.thethunderflyers.android.R;
import edu.gatech.thethunderflyers.android.model.APIMessage;
import edu.gatech.thethunderflyers.android.model.Model;
import edu.gatech.thethunderflyers.android.model.UserMode;
import edu.gatech.thethunderflyers.android.util.APIClient;
import edu.gatech.thethunderflyers.android.util.AlertDialogProvider;
import edu.gatech.thethunderflyers.android.util.AsyncHandler;
import edu.gatech.thethunderflyers.android.util.Navigator;
import edu.gatech.thethunderflyers.android.util.Validator;

public class RegisterActivity extends AppCompatActivity implements AsyncHandler<APIMessage> {
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
        username = (EditText) findViewById(R.id.regUserName);
        password = (EditText) findViewById(R.id.password);
        confirmPass = (EditText) findViewById(R.id.confirmPass);
        userOrAdmin = (Spinner) findViewById(R.id.userOrAdmin);
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
        boolean isValid = Validator.validate(firstName, lastName, username, password, confirmPass);
        isValid = Validator.checkPassword(password, confirmPass) && isValid;
        if (isValid) {
            String firstN = firstName.getText().toString();
            String lastN = lastName.getText().toString();
            String user = username.getText().toString().toLowerCase();
            String pass = password.getText().toString();
            UserMode um = (UserMode) userOrAdmin.getSelectedItem();

            APIClient.getInstance().register(Model.getUser(firstN, lastN, user, pass, um),
                    new WeakReference<>(this));
        } else {
            Toast.makeText(this, "One or more fields is invalid!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void handleResponse(APIMessage response, Exception ex) {
        if (ex != null) {
            AlertDialogProvider.getExceptionDialog(this).show();
        } else if (!response.isSuccess()) {
            AlertDialogProvider.getNotSuccessDialog(this, response.getMessage()).show();
        } else {
            Toast.makeText(this, "Successfully registered user!", Toast.LENGTH_SHORT).show();
            Navigator.goToLoginActivity(this);
        }
    }
}
