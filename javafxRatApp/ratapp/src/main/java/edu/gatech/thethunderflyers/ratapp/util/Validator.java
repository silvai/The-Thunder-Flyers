package edu.gatech.thethunderflyers.ratapp.util;


import javafx.scene.control.TextField;

/**
 * This is the validator class to confirm whether or not
 * the fields are valid.
 */
public class Validator {

    private TextField[] texts;

    public Validator(TextField... texts) {
        this.texts = texts;
    }
    /**
     * this method is to see if any of the editTexts are null
     * @return returns if they're valid or not
     */
    public boolean validate() {
        for (TextField temp: texts) {
            if (temp == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * this method is to check if the passwords match
     * @return returns whether or not they match
     */
    public boolean checkPassword() {
        return false;
    }
}
