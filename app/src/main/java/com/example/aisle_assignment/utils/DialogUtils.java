package com.example.aisle_assignment.utils;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.aisle_assignment.R;

public class DialogUtils {
    public static ProgressDialog dialog;
    public static void showDialog(Context context){
        dialog = new ProgressDialog(context,R.style.AppCompatAlertDialogStyle);
        dialog.setMessage("Please wait...");
        dialog.show();
    }

    public static void dialogDismiss(){
        dialog.dismiss();
    }

}
