package com.lacrose.lc.lclacrose;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.lacrose.lc.lclacrose.Model.PrismaLotes;
import com.lacrose.lc.lclacrose.Model.Prismas;
import com.lacrose.lc.lclacrose.Util.FireBaseUtil;
import com.lacrose.lc.lclacrose.Util.MainActivity;



public class RupturaPrismaActivity extends MainActivity {
    public static String CODE;
    public static PrismaLotes atualLote;
    public TextView code_ET;
    private final Context context = this;
    EditText edit_carga, edit_altura,edit_largura,edit_comprimento;
    FirebaseDatabase database;
    public static long Hoje;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prisma_rulptura);
        database = FireBaseUtil.getDatabase();
        isThisForNow();
        code_ET = (TextView) findViewById(R.id.code_edit_text);
        code_ET.setText(CODE);
        edit_largura = (EditText) findViewById(R.id.largura_edit_text);
        edit_altura = (EditText) findViewById(R.id.altura_edit_text);
        edit_comprimento = (EditText) findViewById(R.id.comprimento_edit_text);
        edit_carga = (EditText) findViewById(R.id.carga_edit_text);
    }

    private void isThisForNow() {
        long idade = atualLote.getIdade();
        Log.e(TAG,"idade "+idade);
        //Descobrir com que data comparar
        long criacao = atualLote.getDataFab();
        Log.e(TAG,"criacao "+criacao);
        long tempoHoje = getDateWithoutHoursAndMinutes(Hoje);
        Log.e(TAG,"hoje "+Hoje);
        if(tempoHoje - criacao < idade - criacao) {
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.dialog_two_choice);
            dialog.setTitle(getString(R.string.dialog_before_age));
            dialog.show();
            TextView title = (TextView) dialog.findViewById(R.id.dialog_title);
            title.setText(getString(R.string.dialog_before_age));
            Button btCancel = (Button) dialog.findViewById(R.id.button_no);
            Button btYes = (Button) dialog.findViewById(R.id.button_yes);
            btYes.setText(getString(R.string.yes));
            btYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            btCancel.setText(getString(R.string.no));
            btCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }


    public void saveFinish(View view) {
    saveResults(false);
    }

    public void saveContinue(View view) {
        saveResults(true);
    }

    public void saveResults(final boolean Continue){
        showProgress(getString(R.string.create_body));
        if(validateFields()){
            Prismas newPrisma = new Prismas();
            newPrisma .setCodigo(code_ET.getText().toString());
            newPrisma .setLargura(Float.parseFloat(edit_largura.getText().toString()));
            newPrisma .setAltura(Float.parseFloat(edit_altura.getText().toString()));
            newPrisma .setComprimento(Float.parseFloat(edit_comprimento.getText().toString()));
            newPrisma .setCarga(Float.parseFloat(edit_carga.getText().toString()));
            newPrisma .setDataCreate(ServerValue.TIMESTAMP);
            RupturaPrismasListActivity.prismasList.add(newPrisma);
            dismissProgress();
            Intent intent = new Intent(RupturaPrismaActivity.this, Continue ? ScanActivity.class : RupturaPrismasListActivity.class);
            startActivity(intent);
            finish();

        }else{
            dismissProgress();
        }
    }

    private boolean validateFields() {

        if(edit_largura.getText().toString().isEmpty()){
            errorAndRequestFocustoEditText(edit_largura);
            return false;
        }
        if(edit_altura.getText().toString().isEmpty()){
            errorAndRequestFocustoEditText(edit_altura);
            return false;
        }
        if(edit_comprimento.getText().toString().isEmpty()){
            errorAndRequestFocustoEditText(edit_comprimento);
            return false;
        }
        if(edit_carga.getText().toString().isEmpty()){
            errorAndRequestFocustoEditText(edit_carga);
            return false;
        }
        return true;
    }

}
