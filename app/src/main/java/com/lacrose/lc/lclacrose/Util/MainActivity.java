package com.lacrose.lc.lclacrose.Util;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.annotation.UiThread;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.lacrose.lc.lclacrose.R;

import java.sql.Ref;

public class MainActivity extends AppCompatActivity {

    public ProgressDialog progressDialog;
    public static String TAG="####################TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @UiThread
    public void showProgress(String text) {
        progressDialog = ProgressDialog.show(this, "", text, true, false);
    }

    @UiThread
    public void dismissProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
    public void showAlert(String title, String msg){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.dialog_confirm),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public void errorAndRequestFocustoEditText(EditText edit_text){
        edit_text.setError(getString(R.string.empty_field_error));
        edit_text.requestFocus();
    }

    public long getDateWithoutHoursAndMinutes(long date){
        return date/ (1000 * 60 * 60 * 24);
    }
}
