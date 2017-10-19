package com.lacrose.lc.lclacrose;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ChooseMoldActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_mold);
    }

    public void btPavimento(View view) {
        Intent intent = new Intent(this, PavimentoMoldActivity.class);
        startActivity(intent);
    }

    public void btPrisma(View view) {
        Intent intent = new Intent(this, PrismaMoldActivity.class);
        startActivity(intent);
    }

    public void btBloco(View view) {
        Intent intent = new Intent(this, BlocoMoldActivity.class);
        startActivity(intent);
    }

    public void btCorpo(View view) {
        Intent intent = new Intent(this, CorpoMoldActivity.class);
        startActivity(intent);
    }
}
