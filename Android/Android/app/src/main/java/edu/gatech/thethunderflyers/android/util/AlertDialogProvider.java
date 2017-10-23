package edu.gatech.thethunderflyers.android.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class AlertDialogProvider {
    public static AlertDialog getExceptionDialog(Context c) {
        return new AlertDialog.Builder(c)
                .setMessage("An unexpected error occurred. Please try again later.")
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create();
    }

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
