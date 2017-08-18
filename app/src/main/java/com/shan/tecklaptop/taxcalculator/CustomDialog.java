package com.shan.tecklaptop.taxcalculator;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

/**
 * Created by E-Teck Laptop on 8/14/2017.
 */

public class CustomDialog {

    Dialog dialog;
    Context mcontext;

    public CustomDialog(Context context){
        mcontext = context;
    }

    public void ShowDialog(){
        dialog = new Dialog(mcontext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(false);
        if(!(dialog.isShowing())) {
            dialog.show();
        }

    }

    public void HideDialog(){

        if(dialog.isShowing()) {
            dialog.dismiss();
            dialog.cancel();
        }
    }


}

