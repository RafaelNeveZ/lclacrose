package com.lacrose.lc.lacrose;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.lacrose.lc.lacrose.Model.Blocos;
import com.lacrose.lc.lacrose.Util.FireBaseUtil;
import com.lacrose.lc.lacrose.Util.MainActivity;

import java.text.DecimalFormat;
import java.util.Calendar;


public class EditRupturaBlocoActivity extends MainActivity {
    public static String CODE;
    public static Blocos editBloco;
    public TextView code_ET;
    private final Context context = this;
    EditText edit_carga, edit_altura,edit_largura,edit_comprimento,edit_espc_long,edit_espc_trans,edit_res;
    FirebaseDatabase database;
    public static boolean jaPerguntei;
    private FirebaseAuth Auth;
    Calendar today= Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bloco_rulptura);
        database = FireBaseUtil.getDatabase();
        code_ET = (TextView) findViewById(R.id.code_edit_text);
        code_ET.setText(editBloco.getCodigo());

        edit_largura = (EditText) findViewById(R.id.largura_edit_text);
        edit_largura.setText(editBloco.getLargura()+"");
        edit_altura = (EditText) findViewById(R.id.altura_edit_text);
        edit_altura.setText(editBloco.getAltura()+"");
        edit_comprimento = (EditText) findViewById(R.id.comprimento_edit_text);
        edit_comprimento.setText(editBloco.getComprimento()+"");
        edit_carga = (EditText) findViewById(R.id.carga_edit_text);
        edit_carga.setText(0+"");
        edit_carga.setText(editBloco.getCarga()+"");

        edit_espc_long = (EditText) findViewById(R.id.esp_long_edit_text);
        edit_espc_long.setText(editBloco.getEsspesura_Long()+"");

        edit_espc_trans = (EditText) findViewById(R.id.esp_trans_edit_text);
        edit_espc_trans.setText(editBloco.getEsspesura_Tranv()+"");

        edit_res = (EditText) findViewById(R.id.res_edit_text);
        edit_res.setText(editBloco.getResistencia()+"");

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
                    edit_res.setText(formatador.format(res));
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
                    edit_res.setText(formatador.format(res));
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
                    edit_res.setText(formatador.format(res));
                }

            }
        });
        Auth = FirebaseAuth.getInstance();

    }




    public void saveEdit(View view) {
    saveResults(false);
    }

    public void cancel(View view) {
        ScanActivity.primeiraVez = false;
        onBackPressed();
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
                RupturaBlocoListActivity.BlocosList.remove(editBloco);
                RupturaBlocoListActivity.voltei();
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

    public void saveResults(final boolean Continue){
        showProgress(getString(R.string.edit_body));
        if(validateFields()){
            Blocos newBloco = new Blocos();
            newBloco = editBloco;
            newBloco.setCodigo(code_ET.getText().toString());
            newBloco.setCarga(Double.parseDouble(edit_carga.getText().toString()));
            newBloco.setAltura(Double.parseDouble(edit_altura.getText().toString()));
            newBloco.setLargura(Double.parseDouble(edit_largura.getText().toString()));
            newBloco.setComprimento(Double.parseDouble(edit_comprimento.getText().toString()));
            newBloco.setResistencia(Double.parseDouble(edit_res.getText().toString()));
            newBloco.setEsspesura_Long(Double.parseDouble(edit_espc_long.getText().toString()));
            newBloco.setEsspesura_Tranv(Double.parseDouble(edit_espc_trans.getText().toString()));
            RupturaBlocoListActivity.BlocosList.remove(editBloco);
            RupturaBlocoListActivity.BlocosList.add(newBloco);
            RupturaBlocoListActivity.voltei();
            dismissProgress();
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
        if(edit_espc_long.getText().toString().isEmpty()){
            errorAndRequestFocustoEditText(edit_espc_long);
            return false;
        }
        if(edit_espc_trans.getText().toString().isEmpty()){
            errorAndRequestFocustoEditText(edit_espc_trans);
            return false;
        }

        return true;
    }
    @Override
    public void onBackPressed() {
        /*final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_two_choice);
        dialog.setTitle(getString(R.string.dialog_cancel_ruptura));
        dialog.show();
        TextView title = (TextView) dialog.findViewById(R.id.dialog_title);
        title.setText(getString(R.string.dialog_cancel_ruptura));
        Button btCancel = (Button) dialog.findViewById(R.id.button_no);
        Button btYes = (Button) dialog.findViewById(R.id.button_yes);
        btYes.setText(getString(R.string.yes));
        btYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();
            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });*/
        super.onBackPressed();
    }



}
