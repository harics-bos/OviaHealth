package com.oviahealth.assignment.Movie.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.oviahealth.assignment.oviaapplication.R;

public class UIUtil {

    public static AlertDialog createInfoDialog(String title, String message, Context context){
        AlertDialog infoDialog = new AlertDialog.Builder(context).create();
        infoDialog.setTitle(title);
        infoDialog.setMessage(message);
        DialogInterface.OnClickListener dismissListener = null;
        infoDialog.setButton(DialogInterface.BUTTON_POSITIVE, StringUtil.diaplayableString(R.string.alert_dismiss_button), dismissListener);

        return infoDialog;
    }
}
