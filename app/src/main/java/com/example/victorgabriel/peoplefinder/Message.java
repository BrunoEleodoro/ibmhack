package com.example.victorgabriel.peoplefinder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by bruno on 19/08/17.
 */

public class Message {
    public ProgressDialog progress(Activity activity, String msg)
    {
        ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setIcon(R.drawable.icon_dois);
        dialog.setTitle("Aviso");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(msg);
        dialog.show();
        return dialog;
    }

    public void showMessage(Activity activity,String msg)
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setTitle("Aviso");
        dialog.setIcon(R.drawable.icon_dois);
        dialog.setMessage(msg);
        dialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.show();
    }
}
