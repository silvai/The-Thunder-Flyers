package edu.gatech.thethunderflyers.android.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class AlertDialogProvider {
    /**
     * Provides an AlertDialog representing the case when an HTTP AsyncTask throws an
     * exception.
     * @param c the Context from the calling Activity
     * @return an AlertDialog with an appropriate message
     */
    public static AlertDialog getExceptionDialog(Context c) {
        AlertDialog.Builder adb = new AlertDialog.Builder(c);
        adb.setMessage("An unexpected error occurred. Please try again later.");
        adb.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        return adb.create();
    }

    /**
     * Provides an AlertDialog representing the case when the API returns an APIMessage
     * indicating failure.
     * @param c the Context from the calling Activity
     * @param message the message extracted from the APIMessage sent by the API
     * @return an AlertDialog with message
     */
    public static AlertDialog getNotSuccessDialog(Context c, CharSequence message) {
        AlertDialog.Builder adb = new AlertDialog.Builder(c);
        adb.setMessage(message);
        adb.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        return adb.create();
    }
}
