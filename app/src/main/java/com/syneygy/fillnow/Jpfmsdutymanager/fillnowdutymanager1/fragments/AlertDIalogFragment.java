package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.interfaces.Click;

public class AlertDIalogFragment extends DialogFragment {

    String k;
    Context context;
    Click click;

    public AlertDIalogFragment(String k, Context context, Click click) {
        this.k = k;
        this.context = context;
        this.click = click;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(k)
                .setPositiveButton("Start Fueling", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    click.ClickK(true);

                    dialog.dismiss();
                    }

                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                      //  click.ClickK(false);
                        dialog.dismiss();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            FragmentTransaction ft = manager.beginTransaction();
            ft.add(this, tag);
            ft.commit();
        } catch (IllegalStateException e) {
            Log.d("ABSDIALOGFRAG", "Exception", e);
        }
    }
}
