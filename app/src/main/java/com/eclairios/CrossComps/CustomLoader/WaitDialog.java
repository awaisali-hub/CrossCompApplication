package com.eclairios.CrossComps.CustomLoader;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.eclairios.CrossComps.R;

public class WaitDialog extends Dialog {
    private static WaitDialog theWaitDialog = null;
    private TextView mTxtMessage;
    public WaitDialog(Context context) {
        super(context);
    }
    public static void showDialog(Context context) {
        if (theWaitDialog != null) {
            try {
                theWaitDialog.dismiss();
            } catch (IllegalArgumentException i) {
                i.printStackTrace();
            }
        }
        theWaitDialog = new WaitDialog(context);
        theWaitDialog.setCancelable(false);
        theWaitDialog.show();
    }
    public static void hideDialog() {
        if (theWaitDialog == null) {
            return;
        }
        try {
            theWaitDialog.dismiss();
        }catch (IllegalArgumentException i)
        {
            i.printStackTrace();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.wait_dialog);
        mTxtMessage = this.findViewById(R.id.txt_message);
    }
    public void setMessage(int resId) {
        mTxtMessage.setText(resId);
    }
    public void setMessage(String message) {
        mTxtMessage.setText(message);
    }
}
