package com.example.aisle_assignment.utils;

import android.app.ProgressDialog;
import android.content.Context;

public class DialogUtils {
    public static ProgressDialog dialog;
    public static void showDialog(Context context){
        dialog = ProgressDialog.show(context, "",
                "Please wait...", true);
    }

    public static void dialogDismiss(){
        dialog.dismiss();
    }

}
