package com.lacrose.lc.lclacrose;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.lacrose.lc.lclacrose.Model.Corpos;
import com.lacrose.lc.lclacrose.Model.CorpoLotes;
import com.lacrose.lc.lclacrose.Util.FireBaseUtil;
import com.lacrose.lc.lclacrose.Util.MainActivity;


public class RupturaCorpoActivity extends MainActivity {
    public static String CODE;
    public static CorpoLotes atualLote;
    public TextView code_ET;
    private final Context context = this;
    Spinner spinner_type;
    EditText edit_carga;
    FirebaseDatabase database;
    public static long Hoje;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corpo_rulptura);
        database = FireBaseUtil.getDatabase();
        isThisForNow();
        code_ET = (TextView) findViewById(R.id.code_edit_text);
        code_ET.setText(CODE);
        spinner_type=(Spinner) findViewById(R.id.type_spinner);
        edit_carga = (EditText) findViewById(R.id.carga_edit_text);

    }

    private void isThisForNow() {
/*        int idade = atualLote.getIdade();
        Calendar idadeCalendar = Calendar.getInstance();
        idadeCalendar.setTime(new Date(Hoje));
        idadeCalendar.add(Calendar.DATE, +idade);*/
        long idade = atualLote.getIdade();
        long criacao = getDateWithoutHoursAndMinutes((long)atualLote.getDataCreate());
        long tempoHoje = getDateWithoutHoursAndMinutes(Hoje);
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
            Corpos newCorpo = new Corpos();
            newCorpo.setCodigo(code_ET.getText().toString());
            newCorpo.setCarga(Float.parseFloat(edit_carga.getText().toString()));
            newCorpo.setTipo(String.valueOf(spinner_type.getSelectedItem()));
            newCorpo.setDataCreate(ServerValue.TIMESTAMP);
            RupturaCorpoListActivity.CorposList.add(newCorpo);
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

}
