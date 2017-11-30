package edu.gatech.thethunderflyers.android.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * ExceptionAlertDialog: subclass of AlertDialog to represent the situation when an API call results
 * in an exception.
 */
public class ExceptionAlertDialog extends AlertDialog {
    private ExceptionAlertDialog(Context context) {
        super(context);
    }

    /**
     * Basic ExceptionAlertDialog constructor
     * @param e the Exception that was thrown during the API call
     * @param context the Context of the Activity which created the object
     */
    public ExceptionAlertDialog(Exception e, Context context) {
        this(context);
        this.setMessage("An unexpected error occurred (" + e.getMessage() + "). Please try again" +
                " later.");
        this.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
    }
}
