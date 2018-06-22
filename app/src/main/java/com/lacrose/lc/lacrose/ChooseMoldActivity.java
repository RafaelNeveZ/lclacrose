package com.lacrose.lc.lacrose;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lacrose.lc.lacrose.Model.Obras;

public class ChooseMoldActivity extends AppCompatActivity {
    private Obras obra = HomeActivity.Work;
    private Button pav,prisma,bloco,cp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_mold);
        pav = (Button) findViewById(R.id.pav);
        prisma = (Button) findViewById(R.id.prisma);
        bloco = (Button) findViewById(R.id.bloco);
        cp = (Button) findViewById(R.id.cp);

        if(!obra.getTipoLotes().contains(getString(R.string.blocoTipo))){
            pav.setEnabled(false);
        }
        if(!obra.getTipoLotes().contains(getString(R.string.prismaTipo))){
            prisma.setEnabled(false);
        }
        if(!obra.getTipoLotes().contains(getString(R.string.blocoTIpo))){
            bloco.setEnabled(false);
        }
        if(!obra.getTipoLotes().contains(getString(R.string.cpTipo))){
            cp.setEnabled(false);
        }

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
