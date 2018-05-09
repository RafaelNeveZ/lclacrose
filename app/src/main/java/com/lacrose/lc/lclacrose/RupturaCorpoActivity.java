package com.lacrose.lc.lclacrose;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.lacrose.lc.lclacrose.Model.CP;
import com.lacrose.lc.lclacrose.Model.CorpoLotes;
import com.lacrose.lc.lclacrose.Model.Prismas;
import com.lacrose.lc.lclacrose.Util.FireBaseUtil;
import com.lacrose.lc.lclacrose.Util.MainActivity;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.Calendar;


public class RupturaCorpoActivity extends MainActivity {
    public static String CODE;
    public static String idade;
    public static CorpoLotes atualLote;
    public TextView code_ET;
    private final Context context = this;
    Spinner spinner_type;
    EditText edit_carga,edit_res;;
    FirebaseDatabase database;
    public static long Hoje;
    private FirebaseAuth Auth;
    public static boolean jaPerguntei;
    Calendar today= Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corpo_rulptura);
        database = FireBaseUtil.getDatabase();
        if(!jaPerguntei){
            isThisForNow();
        }
        code_ET = (TextView) findViewById(R.id.code_edit_text);
        code_ET.setText(CODE);
        edit_res = (EditText) findViewById(R.id.res_edit_text);
        spinner_type=(Spinner) findViewById(R.id.type_spinner);
        edit_carga = (EditText) findViewById(R.id.carga_edit_text);
        edit_carga.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!edit_carga.getText().toString().isEmpty() && (atualLote.getDimenssion().get("altura") != null)
                        && (atualLote.getDimenssion().get("largura") != null)){
                    DecimalFormat formatador = new DecimalFormat("0,0");
                    Double res = Double.parseDouble(edit_carga.getText().toString())/(atualLote.getDimenssion().get("largura")*atualLote.getDimenssion().get("altura"));
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
            int dateSomada = Integer.valueOf(idade);
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
            CP newCP = new CP();
            newCP.setCodigo(code_ET.getText().toString());
            newCP.setCarga(Double.parseDouble(edit_carga.getText().toString()));
            newCP.setAltura(Double.parseDouble(atualLote.getDimenssion().get("altura").toString()));
            newCP.setLargura(Double.parseDouble(atualLote.getDimenssion().get("largura").toString()));
            newCP.setTipo(String.valueOf(spinner_type.getSelectedItem()));
            edit_res.setEnabled(true);
            newCP.setResistencia(Double.parseDouble(edit_res.getText().toString()));
            edit_res.setEnabled(false);
            newCP.setCreatedBy(Auth.getUid());
            newCP.setDataCreate(today.getTime().getTime());
            newCP.setCentro_de_custo(HomeActivity.Work.getCentro_de_custo());
            newCP.setObraId(HomeActivity.WorkId);
            newCP.setLoteId(atualLote.getId());
            RupturaCorpoListActivity.CorposList.add(newCP);
            dismissProgress();
            Intent intent = new Intent(RupturaCorpoActivity.this, Continue ? ScanActivity.class : RupturaCorpoListActivity.class);
            startActivity(intent);
            finish();
        }else{
            dismissProgress();
        }
    }

    private boolean validateFields() {

        if(edit_carga.getText().toString().isEmpty()){
            errorAndRequestFocustoEditText(edit_carga);
            return false;
        }

        if(String.valueOf(spinner_type.getSelectedItem()).equals(getString(R.string.type_prompt)) ){
            Toast.makeText(context,getString(R.string.type_prompt),Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
    @Override
    public void onBackPressed() {
        exit();
    }
    public void exit(){
        if(RupturaCorpoListActivity.CorposList.size()>0){
            Intent intent = new Intent(context, RupturaCorpoListActivity.class);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(context, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
