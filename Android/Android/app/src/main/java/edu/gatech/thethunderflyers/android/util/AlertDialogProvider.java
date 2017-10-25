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
        return new AlertDialog.Builder(c)
                .setMessage("An unexpected error occurred. Please try again later.")
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create();
    }

    /**
     * Provides an AlertDialog representing the case when the API returns an APIMessage
     * indicating failure.
     * @param c the Context from the calling Activity
     * @param message the message extracted from the APIMessage sent by the API
     * @return an AlertDialog with message
     */
    public static AlertDialog getNotSuccessDialog(Context c, String message) {
        return new AlertDialog.Builder(c)
                .setMessage(message)
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create();
    }
}
