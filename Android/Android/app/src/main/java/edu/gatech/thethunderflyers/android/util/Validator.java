package edu.gatech.thethunderflyers.android.util;

import android.text.TextUtils;
import android.widget.EditText;

public class Validator {
    public static boolean validate(EditText... editText) {
        boolean isValid = true;
        for (EditText et : editText) {
            if (TextUtils.isEmpty(et.getText().toString())) {
                isValid = false;
                et.setError("This field cannot be empty!");
            }
        }
        return isValid;
    }

    public static boolean checkPassword(EditText password, EditText repeat) {
        if (!repeat.getText().toString().equals(password.getText().toString())) {
            repeat.setError("Passwords don't match!");
            return false;
        } else {
            return true;
        }
    }
}
