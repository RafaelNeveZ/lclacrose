package com.lacrose.lc.lclacrose;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lacrose.lc.lclacrose.Model.BlocoLotes;
import com.lacrose.lc.lclacrose.Model.CorpoLotes;
import com.lacrose.lc.lclacrose.Model.Obras;
import com.lacrose.lc.lclacrose.Util.MainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class BlocoMoldActivity extends MainActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{



    public static Obras thisObra;
    private FirebaseAuth Auth;
    private final Context context = this;
    DatabaseReference lote_ref, ref_lote;
    FirebaseDatabase database;
    CheckBox check_dimenssion,check_nota,check_dataFab, check_fbk,check_lote,check_fab,check_func,check_date,check_idade;
    Spinner spinner_dimenssion;
    TextView tv_code, tv_switch;
    Switch switch_func;
    EditText edit_nota,edit_lote, edit_fbk,edit_fab,edit_more,edit_idade;
    Button button_date, button_datefab;
    Calendar refCalendar,tempCalendar,finalCalendar,fabCalendar;
    BlocoLotes newLote;
    ArrayList<String> dimen;
    public boolean isFab=true;


    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloco_mold);
        refCalendar = Calendar.getInstance();
        tempCalendar = Calendar.getInstance();
        finalCalendar = Calendar.getInstance();
        fabCalendar = Calendar.getInstance();
        Auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        initiateViews();
        showProgress(getString(R.string.getting_lote_number));
        getLoteNumber();
        regrasNegocios();
    }
    public void regrasNegocios(){
        dimen = new ArrayList<>();
        dimen.add(getString(R.string.dimenssion_prompt));
        dimen.add(getString(R.string.d120_190_390));
        dimen.add(getString(R.string.d140_190_390));
        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_dimenssion.setAdapter(spinnerAdapter);
        spinnerAdapter.addAll(dimen);
        spinnerAdapter.notifyDataSetChanged();
        switch_func.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    tv_switch.setText(getString(R.string.yes_uper));
                else if(!isChecked)
                    tv_switch.setText(getString(R.string.no_uper));
            }
        });

    }

    private void getLoteNumber() {
        final List<CorpoLotes> loteList = new ArrayList<>();
        lote_ref = database.getReference(getString(R.string.work_tag)).child(HomeActivity.WorkId+"").child(getString(R.string.lote_bloco_tag));
        lote_ref.keepSynced(true);
        lote_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for(DataSnapshot d : dataSnapshot.getChildren()) {
                        CorpoLotes corpoLotes = d.getValue(CorpoLotes.class);
                        corpoLotes.setId(d.getKey());
                        loteList.add(corpoLotes);
                    }
                    int last =  loteList.get(loteList.size()-1).getCodigo()+1;
                    if(last<10)
                        tv_code.setText("000"+last);
                    else  if(last<100)
                        tv_code.setText("00"+last);
                    else  if(last<1000)
                        tv_code.setText("0"+last);
                    else  if(last<1000)
                        tv_code.setText(""+last);
                    dismissProgress();
                }else{
                    dismissProgress();
                    int last =  0;
                    tv_code.setText(last+"000");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                dismissProgress();
            }
        });
    }


    public void initiateViews(){
        //CHECKBOX
        check_dimenssion = (CheckBox) findViewById(R.id.check_dimenssion);
        check_nota = (CheckBox) findViewById(R.id.check_nota_fiscal);
        check_dataFab = (CheckBox) findViewById(R.id.checkfab_date);
        check_idade = (CheckBox) findViewById(R.id.check_idade);
        check_lote = (CheckBox) findViewById(R.id.check_lote);
        check_fbk = (CheckBox) findViewById(R.id.check_fbk);
        check_date = (CheckBox) findViewById(R.id.check_date);
        check_fab = (CheckBox) findViewById(R.id.check_fab);
        check_func = (CheckBox) findViewById(R.id.check_func);

        //SPINNER
        spinner_dimenssion=(Spinner) findViewById(R.id.dimenssion_spinner);

        //EDITTEXT
        edit_nota = (EditText) findViewById(R.id.nota_edit_text);
        edit_idade = (EditText) findViewById(R.id.idade_edit_text);
        edit_lote = (EditText) findViewById(R.id.lote_edit_text);
        edit_fbk = (EditText) findViewById(R.id.fbk_edit_text);
        edit_fab = (EditText) findViewById(R.id.fab_edit_text);
        edit_more = (EditText) findViewById(R.id.more_edit_text);


        //BUTTOM
        button_date = (Button) findViewById(R.id.date_buttom);
        button_datefab = (Button) findViewById(R.id.datefab_buttom);

        //TEXTVIEW
        tv_code = (TextView) findViewById(R.id.code_edit_text);
        tv_switch = (TextView) findViewById(R.id.func_choose_text_view);
        //Switch
        switch_func = (Switch) findViewById(R.id.swit_func);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        tempCalendar.set(year,month,dayOfMonth);
        if(tempCalendar.getTime().getTime()>= refCalendar.getTime().getTime()){
            SimpleDateFormat fmtOut = new SimpleDateFormat("dd/MM/yyyy");
            if(isFab) {
                button_datefab.setText(fmtOut.format(tempCalendar.getTime()));
                fabCalendar = tempCalendar;
            }else {
                button_date.setText(fmtOut.format(tempCalendar.getTime()));
                finalCalendar = tempCalendar;
            }

        }else{
            tempCalendar = Calendar.getInstance();
            Toast.makeText(context,getString(R.string.date_before_error),Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
    }

    public void dataChamge(View view) {
        isFab=false;
        DatePickerDialog dpd = new DatePickerDialog(context, this, tempCalendar.get(Calendar.YEAR), tempCalendar.get(Calendar.MONTH), tempCalendar.get(Calendar.DAY_OF_MONTH));
        dpd.show();
    }

    public void dataFabChamge(View view) {
        isFab=true;
        DatePickerDialog dpd = new DatePickerDialog(context, this, tempCalendar.get(Calendar.YEAR), tempCalendar.get(Calendar.MONTH), tempCalendar.get(Calendar.DAY_OF_MONTH));
        dpd.show();
    }

    public void loteCreate(View view) {
        showProgress(getString(R.string.create_lote));
        if(validateFields()){
            DatabaseReference ref_lote = database.getReference(getString(R.string.work_tag)).child(HomeActivity.WorkId+"").child(getString(R.string.lote_bloco_tag));
            newLote = new BlocoLotes();
            newLote.setCodigo(Integer.parseInt(tv_code.getText().toString()));

            if(!edit_idade.getText().toString().isEmpty()) {
                Calendar temp = Calendar.getInstance();
                temp.add(Calendar.DATE, +Integer.parseInt(edit_idade.getText().toString()));
                newLote.setIdade(temp.getTime().getTime());
            }

            if(!edit_nota.getText().toString().isEmpty())
                newLote.setNotaFiscal(Long.parseLong(edit_nota.getText().toString()));

            if(!edit_lote.getText().toString().isEmpty())
                newLote.setLote(edit_lote.getText().toString());

            if(!edit_fbk.getText().toString().isEmpty())
                newLote.setFBK(Integer.parseInt(edit_fbk.getText().toString()));

            newLote.setDatafab(fabCalendar.getTime().getTime());
            newLote.setData(finalCalendar.getTime().getTime());

            if(switch_func.isChecked())
                newLote.setFunc_estrutural(true);
            else
                newLote.setFunc_estrutural(false);

            if(!edit_fab.getText().toString().isEmpty())
                newLote.setFabricante(edit_fab.getText().toString());

            if(!edit_more.getText().toString().isEmpty())
                newLote.setMore(edit_more.getText().toString());

            HashMap<String, Integer> dimenssionHash = new HashMap<>();

            if(String.valueOf(spinner_dimenssion.getSelectedItem()).equals(getString(R.string.d120_190_390))){
                dimenssionHash.put(getString(R.string.largura),120);
                dimenssionHash.put(getString(R.string.altura),190);
                dimenssionHash.put(getString(R.string.comprimento),390);
                newLote.setDimenssoes(dimenssionHash);
            }else if(String.valueOf(spinner_dimenssion.getSelectedItem()).equals(getString(R.string.d140_190_390))){
                dimenssionHash.put(getString(R.string.largura),140);
                dimenssionHash.put(getString(R.string.altura),190);
                dimenssionHash.put(getString(R.string.comprimento),390);
                newLote.setDimenssoes(dimenssionHash);
            }

            ref_lote.push().setValue(newLote).addOnCompleteListener(this,new OnCompleteListener(){
                @Override
                public void onComplete(@NonNull Task task) {
                    dismissProgress();
                    if(task.isSuccessful()) {
                        Toast.makeText(context,getString(R.string.lote_create_sucess),Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(context,getString(R.string.server_error),Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }else{
            dismissProgress();
        }
    }

    private boolean validateFields() {

        if(String.valueOf(spinner_dimenssion.getSelectedItem()).equals(getString(R.string.dimenssion_prompt)) && !check_dimenssion.isChecked()){
            Toast.makeText(context,getString(R.string.dimenssion_prompt),Toast.LENGTH_SHORT).show();
            return false;
        }

        if(button_datefab.getText().equals(getString(R.string.date_default)) && !check_dataFab.isChecked()){
            Toast.makeText(context,getString(R.string.date_fab_notput_error),Toast.LENGTH_SHORT).show();
            return false;
        }

        if(edit_idade.getText().toString().isEmpty() && !check_idade.isChecked()){
            edit_idade.setError(getString(R.string.empty_field_error));
            return false;
        }

        if(edit_lote.getText().toString().isEmpty() && !check_lote.isChecked()){
            edit_lote.setError(getString(R.string.empty_field_error));
            return false;
        }

        if(edit_nota.getText().toString().isEmpty() && !check_nota.isChecked()){
            edit_nota.setError(getString(R.string.empty_field_error));
            return false;
        }

        if(edit_fab.getText().toString().isEmpty() && !check_fab.isChecked()){
            edit_fab.setError(getString(R.string.empty_field_error));
            return false;
        }

        if(edit_fbk.getText().toString().isEmpty() && !check_fbk.isChecked()){
            edit_fbk.setError(getString(R.string.empty_field_error));
            return false;
        }

        if(button_date.getText().equals(getString(R.string.date_default)) && !check_date.isChecked()){
            Toast.makeText(context,getString(R.string.date_notput_error),Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }


}