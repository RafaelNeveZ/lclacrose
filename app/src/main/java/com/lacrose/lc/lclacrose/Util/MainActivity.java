package com.lacrose.lc.lclacrose.Util;

import android.app.ProgressDialog;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lacrose.lc.lclacrose.R;

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
}
