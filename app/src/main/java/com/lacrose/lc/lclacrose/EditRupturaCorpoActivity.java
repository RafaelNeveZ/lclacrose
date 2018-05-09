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
import com.lacrose.lc.lclacrose.Util.FireBaseUtil;
import com.lacrose.lc.lclacrose.Util.MainActivity;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class EditRupturaCorpoActivity extends MainActivity {
    public static String CODE;
    public static String idade;
    public static CP editCorpo;

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
        setContentView(R.layout.activity_edit_corpo_rulptura);
        database = FireBaseUtil.getDatabase();
        code_ET = (TextView) findViewById(R.id.code_edit_text);
        code_ET.setText(editCorpo.getCodigo());
        edit_res = (EditText) findViewById(R.id.res_edit_text);
        spinner_type=(Spinner) findViewById(R.id.type_spinner);
        edit_carga = (EditText) findViewById(R.id.carga_edit_text);
        edit_carga.setText(editCorpo.getCarga()+"");
        spinner_type.setSelection(getIndex(spinner_type, editCorpo.getType()));
        edit_res = (EditText) findViewById(R.id.res_edit_text);
        edit_res.setText(editCorpo.getResistencia()+"");
        edit_carga.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!edit_carga.getText().toString().isEmpty() && editCorpo.largura != null
                        && (editCorpo.altura != null)){
                    DecimalFormat formatador = new DecimalFormat("0,0");
                    Double res = Double.parseDouble(edit_carga.getText().toString())/(editCorpo.altura*editCorpo.largura);
                    edit_res.setText(res.toString());
                }

            }
        });

        Auth = FirebaseAuth.getInstance();

    }

    private int getIndex(Spinner spinner, String myString){

        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).equals(myString)){
                index = i;
            }
        }
        return index;
    }

    public void deletar(View view) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_two_choice);
        TextView tv = (TextView) dialog.findViewById(R.id.dialog_title);
        tv.setText(getString(R.string.delete_rompimento_title));
        dialog.show();
        Button nao = (Button) dialog.findViewById(R.id.button_no);
        Button sim = (Button) dialog.findViewById(R.id.button_yes);
        sim.setText(getString(R.string.delete_bt));
        nao.setText(getString(R.string.no));
        sim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RupturaCorpoListActivity.CorposList.remove(editCorpo);
                RupturaCorpoListActivity.voltei();
                finish();
            }
        });
        nao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }


    public void saveEdit(View view) {
    saveResults(false);
    }

    public void cancel(View view) {
        ScanActivity.primeiraVez = false;
        onBackPressed();
    }

    public void saveResults(final boolean Continue){
        showProgress(getString(R.string.create_body));
        if(validateFields()){
            CP newCP = new CP();
            newCP = editCorpo;
            newCP.setCodigo(code_ET.getText().toString());
            newCP.setCarga(Double.parseDouble(edit_carga.getText().toString()));
            newCP.setAltura(editCorpo.getAltura());
            newCP.setLargura(editCorpo.getLargura());
            newCP.setType(String.valueOf(spinner_type.getSelectedItem()));
            newCP.setResistencia(Double.parseDouble(edit_res.getText().toString()));
            RupturaCorpoListActivity.CorposList.remove(editCorpo);
            RupturaCorpoListActivity.CorposList.add(newCP);
            RupturaCorpoListActivity.voltei();
            dismissProgress();
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
            showAlert(getString(R.string.aviso),getString(R.string.tipo_prompt));
            return false;
        }

        return true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
