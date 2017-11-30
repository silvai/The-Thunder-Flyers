package edu.gatech.thethunderflyers.android.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * NotSuccessAlertDialog: subclass of AlertDialog to represent the situation when an API call
 * returns a failure status with no exception.
 */
public class NotSuccessAlertDialog extends AlertDialog {
    private NotSuccessAlertDialog(Context context) {
        super(context);
    }

    /**
     * Basic constructor for NotSuccessAlertDialog.
     * @param message the message returned by the API call
     * @param context the Context of the Activity which created the object
     */
    public NotSuccessAlertDialog(CharSequence message, Context context) {
        this(context);
        this.setMessage(message);
        this.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
    }
}
