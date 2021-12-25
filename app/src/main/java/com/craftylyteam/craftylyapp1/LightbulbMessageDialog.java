package com.craftylyteam.craftylyapp1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class LightbulbMessageDialog extends AppCompatDialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Just a reminder!")
                .setMessage("Creating masterpeices takes time. " +
                        "Don't stress, take a deep breath, and remember to stay hydrated (:")
                .setIcon(R.drawable.ic_craftyly_bulb_icon);
        builder.setPositiveButton("Got it!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        don't do anything when clicked
                    }
                });
        return builder.create();
    }
}
