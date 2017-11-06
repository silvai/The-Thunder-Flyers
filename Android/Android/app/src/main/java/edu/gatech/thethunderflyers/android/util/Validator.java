package edu.gatech.thethunderflyers.android.util;

import android.text.Editable;
import android.text.TextUtils;
import android.widget.EditText;

/**
 * This is the validator class to confirm whether or not
 * the fields are valid.
 */
public class Validator {
    /**
     * this method is to see if any of the editTexts are null
     * @param editText the editText parameters
     * @return returns if they're valid or not
     */
    public static boolean validate(EditText... editText) {
        boolean isValid = true;
        for (EditText et : editText) {
            Editable e = et.getText();
            if (TextUtils.isEmpty(e.toString())) {
                isValid = false;
                et.setError("This field cannot be empty!");
            }
        }
        return isValid;
    }

    /**
     * this method is to check if the passwords match
     * @param password the first editText for password
     * @param repeat the second editText for password
     * @return returns whether or not they match
     */
    public static boolean checkPassword(EditText password, EditText repeat) {
        Editable re = repeat.getText();
        String res = re.toString();
        Editable pe = password.getText();
        String pes = pe.toString();
        if (!res.equals(pes)) {
            repeat.setError("Passwords don't match!");
            return false;
        } else {
            return true;
        }
    }
}
