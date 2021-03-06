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
import com.lacrose.lc.lacrose.Model.Prismas;
import com.lacrose.lc.lacrose.Util.FireBaseUtil;
import com.lacrose.lc.lacrose.Util.MainActivity;

import java.text.DecimalFormat;
import java.util.Calendar;


public class EditRupturaPrismaActivity extends MainActivity {
    public static String CODE;
    public static Prismas editPrisma;
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
        setContentView(R.layout.activity_edit_prisma_rulptura);
        database = FireBaseUtil.getDatabase();
        code_ET = (TextView) findViewById(R.id.code_edit_text);
        code_ET.setText(editPrisma.getCodigo());

        edit_res = (EditText) findViewById(R.id.res_edit_text);
        edit_res.setText(editPrisma.getResistencia()+"");
        edit_largura = (EditText) findViewById(R.id.largura_edit_text);
        edit_altura = (EditText) findViewById(R.id.altura_edit_text);
        edit_comprimento = (EditText) findViewById(R.id.comprimento_edit_text);
        edit_altura.setText(editPrisma.getAltura().toString());
        edit_largura.setText(editPrisma.getLargura().toString());
        edit_comprimento.setText(editPrisma.getComprimento().toString());
        edit_carga = (EditText) findViewById(R.id.carga_edit_text);
        edit_carga.setText(editPrisma.getCarga()+"");
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
                RupturaPrismasListActivity.prismasList.remove(editPrisma);
                RupturaPrismasListActivity.voltei();
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
        showProgress(getString(R.string.create_body));
        if(validateFields()){
            Prismas newPrisma = new Prismas();
            newPrisma = editPrisma;
            newPrisma.setCodigo(code_ET.getText().toString());
            newPrisma.setCarga(Double.parseDouble(edit_carga.getText().toString()));
            newPrisma.setAltura(Double.parseDouble(edit_altura.getText().toString()));
            newPrisma.setLargura(Double.parseDouble(edit_largura.getText().toString()));
            newPrisma.setComprimento(Double.parseDouble(edit_comprimento.getText().toString()));
            newPrisma.setResistencia(Double.parseDouble(edit_res.getText().toString()));
            RupturaPrismasListActivity.prismasList.remove(editPrisma);
            RupturaPrismasListActivity.prismasList.add(newPrisma);
            RupturaPrismasListActivity.voltei();
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
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



}
