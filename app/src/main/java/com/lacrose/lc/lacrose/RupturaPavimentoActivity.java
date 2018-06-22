package com.lacrose.lc.lacrose;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


import com.lacrose.lc.lacrose.Model.PavimentoLotes;
import com.lacrose.lc.lacrose.Model.Pavimentos;
import com.lacrose.lc.lacrose.Util.FireBaseUtil;
import com.lacrose.lc.lacrose.Util.MainActivity;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.Calendar;

public class RupturaPavimentoActivity extends MainActivity {
    public static String CODE;
    public static PavimentoLotes atualLote;
    public TextView code_ET;
    private final Context context = this;
    EditText edit_carga, edit_altura,edit_largura,edit_comprimento,edit_res;
    FirebaseDatabase database;
    public static long Hoje;
    private FirebaseAuth Auth;
    public static boolean jaPerguntei;
    Calendar today= Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pavimento_rulptura);
        database = FireBaseUtil.getDatabase();
        isThisForNow();
        code_ET = (TextView) findViewById(R.id.code_edit_text);
        code_ET.setText(CODE);
        edit_res = (EditText) findViewById(R.id.res_edit_text);
        edit_largura = (EditText) findViewById(R.id.largura_edit_text);
        edit_altura = (EditText) findViewById(R.id.altura_edit_text);
        edit_comprimento = (EditText) findViewById(R.id.comprimento_edit_text);
        edit_altura.setText(atualLote.getDimenssion().get("altura")+"");
        edit_largura.setText(atualLote.getDimenssion().get("largura")+"");
        edit_comprimento.setText(atualLote.getDimenssion().get("comprimento")+"");
        edit_carga = (EditText) findViewById(R.id.carga_edit_text);
        edit_comprimento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!edit_carga.getText().toString().isEmpty() && !edit_largura.getText().toString().isEmpty()
                        && !edit_comprimento.getText().toString().isEmpty() && !edit_altura.getText().toString().isEmpty()){
                    DecimalFormat formatador = new DecimalFormat("0,0");
                    Double res = Double.parseDouble(edit_carga.getText().toString())/(Double.parseDouble(edit_largura.getText().toString())*Double.parseDouble(edit_comprimento.getText().toString()));
                    edit_res.setText(res.toString());
                }
            }
        });
        edit_largura.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!edit_carga.getText().toString().isEmpty() && !edit_largura.getText().toString().isEmpty()
                        && !edit_comprimento.getText().toString().isEmpty() && !edit_altura.getText().toString().isEmpty()){
                    DecimalFormat formatador = new DecimalFormat("0,0");
                    Double res = Double.parseDouble(edit_carga.getText().toString())/(Double.parseDouble(edit_largura.getText().toString())*Double.parseDouble(edit_comprimento.getText().toString()));
                    edit_res.setText(res.toString());
                }
            }
        });
        edit_carga.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!edit_carga.getText().toString().isEmpty() && !edit_largura.getText().toString().isEmpty()
                        && !edit_comprimento.getText().toString().isEmpty() && !edit_altura.getText().toString().isEmpty()){
                    DecimalFormat formatador = new DecimalFormat("0,0");
                    Double res = Double.parseDouble(edit_carga.getText().toString())/(Double.parseDouble(edit_largura.getText().toString())*Double.parseDouble(edit_comprimento.getText().toString()));
                    edit_res.setText(res.toString());
                }

            }
        });
        Auth = FirebaseAuth.getInstance();
    }

    private void isThisForNow() {
        jaPerguntei = true;
        if(atualLote.getDataFab() !=null) {


            Date dataMoldagem = new Date(atualLote.getDataFab());
            Date dataRompimentoEsperada = new Date(atualLote.getDataFab());
            int dateSomada = Integer.valueOf(atualLote.getIdade().intValue());

            dataRompimentoEsperada.setDate(dataMoldagem.getDate() + dateSomada);
            Date diaSemHora = new Date(today.getTime().getYear(),today.getTime().getMonth(), today.getTime().getDate());
            Date rompSemHora = new Date(dataRompimentoEsperada.getYear(), dataRompimentoEsperada.getMonth(), dataRompimentoEsperada.getDate());
            if (rompSemHora.getTime() > diaSemHora.getTime()) {
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
                        exit();
                    }
                });
            }
        }
    }

    public void saveFinish(View view) {
    saveResults(false);
    }

    public void saveContinue(View view) {
        ScanActivity.primeiraVez = false;
        saveResults(true);
    }

    public void saveResults(final boolean Continue){
        showProgress(getString(R.string.create_body));
        if(validateFields()){
            Pavimentos newPavimento = new Pavimentos();
            newPavimento.setCodigo(code_ET.getText().toString());
            newPavimento.setCarga(Double.parseDouble(edit_carga.getText().toString()));
            newPavimento.setAltura(Double.parseDouble(edit_altura.getText().toString()));
            newPavimento.setLargura(Double.parseDouble(edit_largura.getText().toString()));
            newPavimento.setComprimento(Double.parseDouble(edit_comprimento.getText().toString()));
            edit_res.setEnabled(true);
            newPavimento.setResistencia(Double.parseDouble(edit_res.getText().toString()));
            edit_res.setEnabled(false);
            newPavimento.setCreatedBy(Auth.getUid());
            newPavimento.setDataCreate(today.getTime().getTime());
            newPavimento.setCentro_de_custo(HomeActivity.Work.getCentro_de_custo());
            newPavimento.setObraId(HomeActivity.WorkId);
            newPavimento.setLoteId(atualLote.getId());
            RupturaPavimentoListActivity.pavimentoList.add(newPavimento);
            dismissProgress();
            Intent intent = new Intent(RupturaPavimentoActivity.this, Continue ? ScanActivity.class : RupturaPavimentoListActivity.class);
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
        if(edit_res.getText().toString().isEmpty()){
            errorAndRequestFocustoEditText(edit_res);
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        exit();
    }

    public void exit(){
        if(RupturaPavimentoListActivity.pavimentoList.size()>0){
            Intent intent = new Intent(context, RupturaPavimentoListActivity.class);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(context, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
