package com.lacrose.lc.lclacrose;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lacrose.lc.lclacrose.Model.Corpos;
import com.lacrose.lc.lclacrose.Util.MainActivity;

public class RupturaActivity extends MainActivity {
    public static String CODE, LOTE_ID;
    public TextView code_ET;
    private final Context context = this;
    Spinner spinner_type;
    EditText edit_carga;
    TextView edit_codigo;
    DatabaseReference corpo_ref;
    FirebaseDatabase database;
    Corpos newCorpo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rulptura);
        code_ET = (TextView) findViewById(R.id.code_edit_text);
        code_ET.setText(CODE);
        spinner_type=(Spinner) findViewById(R.id.type_spinner);
        edit_carga = (EditText) findViewById(R.id.carga_edit_text);
        database = FirebaseDatabase.getInstance();

    }
//tesa
    public void saveFinish(View view) {
        showProgress(getString(R.string.create_body));
        if(validateFields()){
            corpo_ref = database.getReference(getString(R.string.work_tag)).child(MoldActivity.WorkId+"").child(getString(R.string.lote_tag)).child(LOTE_ID);
            newCorpo = new Corpos();
            newCorpo.setCodigo(code_ET.getText().toString());
            newCorpo.setCarga(Float.parseFloat(edit_carga.getText().toString()));
            newCorpo.setTipo(String.valueOf(spinner_type.getSelectedItem()));
            corpo_ref.child(getString(R.string.corpos)).push().setValue(newCorpo).addOnCompleteListener(this,new OnCompleteListener(){
                @Override
                public void onComplete(@NonNull Task task) {
                    dismissProgress();
                    Intent intent = new Intent(RupturaActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }else{
            dismissProgress();
        }
    }

    public void saveContinue(View view) {
        showProgress(getString(R.string.create_body));
        if(validateFields()){
            corpo_ref = database.getReference(getString(R.string.work_tag)).child(MoldActivity.WorkId+"").child(getString(R.string.lote_tag)).child(LOTE_ID);
            newCorpo = new Corpos();
            newCorpo.setCodigo(code_ET.getText().toString());
            newCorpo.setCarga(Float.parseFloat(edit_carga.getText().toString()));
            newCorpo.setTipo(String.valueOf(spinner_type.getSelectedItem()));
            corpo_ref.child(getString(R.string.corpos)).push().setValue(newCorpo).addOnCompleteListener(this,new OnCompleteListener(){
                @Override
                public void onComplete(@NonNull Task task) {
                    dismissProgress();
                    Intent intent = new Intent(RupturaActivity.this, ScanActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

        }else{
            dismissProgress();
        }
    }

    private boolean validateFields() {

        if(edit_carga.getText().toString().isEmpty()){
            edit_carga.setError(getString(R.string.empty_field_error));
            return false;
        }

        if(String.valueOf(spinner_type.getSelectedItem()).equals(getString(R.string.type_prompt)) ){
            Toast.makeText(context,getString(R.string.type_prompt),Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}
