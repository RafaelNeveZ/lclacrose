package com.lacrose.lc.lclacrose;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MoldActivity extends AppCompatActivity {

    CheckBox check_material,check_dimenssion,check_construc,check_nota,check_volume,check_fck,check_slump,check_slump_flow,check_date,check_local;
    Spinner spinner_material,spinner_dimenssion,spinner_contruc;
    EditText edit_nota,edit_volume,edit_fck,edit_slump,edit_slump_flow,edit_local;
    Button button_date;
    TextView tv_code,tv_slump,tv_slump_flow;
    private final Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mold);
        initiateViews();
    }

    public void initiateViews(){
        //CHECKBOX
        check_material = (CheckBox) findViewById(R.id.check_material);
        check_dimenssion = (CheckBox) findViewById(R.id.check_dimenssion);
        check_construc = (CheckBox) findViewById(R.id.check_contruc);
        check_nota = (CheckBox) findViewById(R.id.check_nota_fiscal);
        check_volume = (CheckBox) findViewById(R.id.check_volume);
        check_fck = (CheckBox) findViewById(R.id.check_fck);
        check_slump = (CheckBox) findViewById(R.id.check_slump);
        check_slump_flow = (CheckBox) findViewById(R.id.check_slump_flow);
        check_date = (CheckBox) findViewById(R.id.check_date);
        check_local = (CheckBox) findViewById(R.id.check_local);

        //SPINNER
        spinner_material=(Spinner) findViewById(R.id.material_spinner);
        spinner_dimenssion=(Spinner) findViewById(R.id.dimenssion_spinner);
        spinner_contruc=(Spinner) findViewById(R.id.constru_spinner);

        //EDITTEXT
        edit_nota = (EditText) findViewById(R.id.nota_edit_text);
        edit_volume = (EditText) findViewById(R.id.volume_edit_text);
        edit_fck = (EditText) findViewById(R.id.fck_edit_text);
        edit_slump = (EditText) findViewById(R.id.slump_edit_text);
        edit_slump_flow = (EditText) findViewById(R.id.slump_flow_edit_text);
        edit_local = (EditText) findViewById(R.id.local_edit_text);

        //BUTTOM
        button_date = (Button) findViewById(R.id.date_buttom);

        //TEXTVIEW
        tv_code = (TextView) findViewById(R.id.code_text_view);
        tv_slump = (TextView) findViewById(R.id.slump_text_view);
        tv_slump_flow = (TextView) findViewById(R.id.slump_flow_text_view);

    }

    public void dataChamge(View view) {
    }


    public void loteCreate(View view) {
        validateFields();
    }

    private boolean validateFields() {

        if(String.valueOf(spinner_material.getSelectedItem()).equals(R.string.material_prompt) && !check_material.isChecked()){
            Toast.makeText(context,getString(R.string.material_prompt),Toast.LENGTH_SHORT).show();
            return false;
        }

        if(edit_nota.getText().toString().isEmpty() && !check_nota.isChecked()){
            editTextError(edit_nota);
            return false;
        }
        if(edit_volume.getText().toString().isEmpty() && !check_volume.isChecked()){
            editTextError(edit_volume);
            return false;
        }
        if(edit_fck.getText().toString().isEmpty() && !check_fck.isChecked()){
            editTextError(edit_fck);
            return false;
        }
        if(edit_slump.getText().toString().isEmpty() && !check_slump.isChecked() && String.valueOf(spinner_material.getSelectedItem()).equals(getString(R.string.concreto))){
            editTextError(edit_slump);
            return false;
        }
        if(edit_slump_flow.getText().toString().isEmpty() && !check_slump_flow.isChecked() && String.valueOf(spinner_material.getSelectedItem()).equals(getString(R.string.concreto))){
            editTextError(edit_slump_flow);
            return false;
        }
        if(edit_local.getText().toString().isEmpty() && !check_local.isChecked()){
            editTextError(edit_local);
            return false;
        }

        return true;
    }
    public void editTextError(final EditText edittext){
        edittext.setError(getString(R.string.empty_field_error));
    }
}
