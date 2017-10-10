package com.lacrose.lc.lclacrose;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class RulpturaActivity extends AppCompatActivity {
    public static String CODE;
    public TextView code_ET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rulptura);
        code_ET = (TextView) findViewById(R.id.code_edit_text);
        code_ET.setText(CODE);
    }

    public void saveFinish(View view) {
    }

    public void saveContinue(View view) {
    }
}
