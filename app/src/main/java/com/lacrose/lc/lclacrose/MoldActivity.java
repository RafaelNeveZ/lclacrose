package com.lacrose.lc.lclacrose;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.lacrose.lc.lclacrose.Model.Lotes;
import com.lacrose.lc.lclacrose.Model.Obras;
import com.lacrose.lc.lclacrose.Util.MainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MoldActivity extends MainActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    private static final String TAG = "ERRO";
    public static  String WorkId;
    public static Obras thisObra;
    private FirebaseAuth Auth;
    private final Context context = this;
    DatabaseReference lote_ref, ref_lote;
    FirebaseDatabase database;
    CheckBox check_material,check_dimenssion,check_construc,check_nota,check_volume,check_fck,check_slump,check_slump_flow,check_date,check_local;
    Spinner spinner_material,spinner_dimenssion,spinner_contruc;
    EditText edit_nota,edit_volume,edit_fck,edit_slump,edit_slump_flow,edit_local;
    Button button_date;
    TextView tv_code,tv_slump,tv_slump_flow;
    Calendar refCalendar,tempCalendar,finalCalendar;
    Lotes newLote;
    ArrayList<String> cimento,argamassa,graute,defaultlist;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mold);
        refCalendar = Calendar.getInstance();
        tempCalendar = Calendar.getInstance();
        finalCalendar = Calendar.getInstance();
        Auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        initiateViews();
        showProgress(getString(R.string.getting_lote_number));
        getLoteNumber();
        regrasNegocios();
    }
    public void regrasNegocios(){
        defaultlist = new ArrayList<>();
        defaultlist.add(getString(R.string.dimenssion_prompt));
        defaultlist.add(getString(R.string.d40_40));
        defaultlist.add(getString(R.string.d50_100));
        defaultlist.add(getString(R.string.d100_200));
        cimento = new ArrayList<>();
        cimento.add(getString(R.string.dimenssion_prompt));
        cimento.add(getString(R.string.d100_200));
        argamassa = new ArrayList<>();
        argamassa.add(getString(R.string.dimenssion_prompt));
        argamassa.add(getString(R.string.d40_40));
        argamassa.add(getString(R.string.d50_100));
        graute = new ArrayList<>();
        graute.add(getString(R.string.dimenssion_prompt));
        graute.add(getString(R.string.d50_100));
        graute.add(getString(R.string.d100_200));

        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_dimenssion.setAdapter(spinnerAdapter);
        spinnerAdapter.addAll(defaultlist);
        spinnerAdapter.notifyDataSetChanged();

        spinner_material.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        spinnerAdapter.clear();
                        spinnerAdapter.addAll(defaultlist);
                        spinnerAdapter.notifyDataSetChanged();
                        break;
                    case 1:
                        spinnerAdapter.clear();
                        spinnerAdapter.addAll(argamassa);
                        spinnerAdapter.notifyDataSetChanged();
                    break;
                    case 2:
                        spinnerAdapter.clear();
                        spinnerAdapter.addAll(cimento);
                        spinnerAdapter.notifyDataSetChanged();
                        break;
                    case 3:
                        spinnerAdapter.clear();
                        spinnerAdapter.addAll(graute);
                        spinnerAdapter.notifyDataSetChanged();
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getLoteNumber() {
        final List<Lotes> loteList = new ArrayList<>();
        lote_ref = database.getReference(getString(R.string.work_tag)).child(WorkId+"").child(getString(R.string.lote_tag));
        lote_ref.keepSynced(true);
        lote_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for(DataSnapshot d : dataSnapshot.getChildren()) {
                        Lotes lotes = d.getValue(Lotes.class);
                        lotes.setId(d.getKey());
                        loteList.add(lotes);
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
                    Log.e(TAG,"TENHO");
                }else{
                    dismissProgress();
                    Log.e(TAG,"NAO TENHO");
                    int last =  0;
                    tv_code.setText(last+"");
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
        tv_code = (TextView) findViewById(R.id.code_edit_text);
        tv_slump = (TextView) findViewById(R.id.slump_text_view);
        tv_slump_flow = (TextView) findViewById(R.id.slump_flow_text_view);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        tempCalendar.set(year,month,dayOfMonth);
        if(tempCalendar.getTime().getTime()>= refCalendar.getTime().getTime()){
            SimpleDateFormat fmtOut = new SimpleDateFormat("dd/MM/yyyy");
            button_date.setText(fmtOut.format(tempCalendar.getTime()));
            finalCalendar = tempCalendar;
        }else{
            tempCalendar = Calendar.getInstance();
            Toast.makeText(context,getString(R.string.date_before_error),Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
    }

    public void dataChamge(View view) {
        DatePickerDialog dpd = new DatePickerDialog(context, this, tempCalendar.get(Calendar.YEAR), tempCalendar.get(Calendar.MONTH), tempCalendar.get(Calendar.DAY_OF_MONTH));
        dpd.show();
    }



    public void loteCreate(View view) {
        showProgress(getString(R.string.create_lote));
        if(validateFields()){
            //TODO VERIFICAR SE PODE COLOCAR OU NAO E MSG DE ERRO
            ref_lote = database.getReference(getString(R.string.work_tag)).child(WorkId+"").child(getString(R.string.lote_tag));
            newLote = new Lotes();
            newLote.setCodigo(Integer.parseInt(tv_code.getText().toString()));
            newLote.setMaterial(String.valueOf(spinner_material.getSelectedItem()));
            newLote.setDimenssoes_nominais(String.valueOf(spinner_dimenssion.getSelectedItem()));
            newLote.setConcreteira(String.valueOf(spinner_contruc.getSelectedItem()));
            newLote.setNotaFiscal(Integer.parseInt(edit_nota.getText().toString()));
            newLote.setVolume_do_caminhão(Float.parseFloat(edit_volume.getText().toString()));
            newLote.setFCK(Integer.parseInt(edit_nota.getText().toString()));
            newLote.setSlump(Integer.parseInt(edit_nota.getText().toString()));
            newLote.setSlumFlow(Float.parseFloat(edit_volume.getText().toString()));
            newLote.setData(finalCalendar.getTime());
            newLote.setLocal_concretado(edit_local.getText().toString());
            ref_lote.push().setValue(newLote).addOnCompleteListener(this,new OnCompleteListener(){
                @Override
                public void onComplete(@NonNull Task task) {
                    dismissProgress();
                    finish();
                }
            });

        }else{
            dismissProgress();
        }
    }

    private boolean validateFields() {

        if(String.valueOf(spinner_material.getSelectedItem()).equals(getString(R.string.material_prompt)) && !check_material.isChecked()){
            Toast.makeText(context,getString(R.string.material_prompt),Toast.LENGTH_SHORT).show();
            return false;
        }
        if(String.valueOf(spinner_dimenssion.getSelectedItem()).equals(getString(R.string.dimenssion_prompt)) && !check_dimenssion.isChecked()){
            Toast.makeText(context,getString(R.string.dimenssion_prompt),Toast.LENGTH_SHORT).show();
            return false;
        }
        if(String.valueOf(spinner_contruc.getSelectedItem()).equals(getString(R.string.construc_prompt)) && !check_construc.isChecked()){
            Toast.makeText(context,getString(R.string.construc_prompt),Toast.LENGTH_SHORT).show();
            return false;
        }

        if(edit_nota.getText().toString().isEmpty() && !check_nota.isChecked()){
            edit_nota.setError(getString(R.string.empty_field_error));
            return false;
        }
        if(edit_volume.getText().toString().isEmpty() && !check_volume.isChecked()){
            edit_volume.setError(getString(R.string.empty_field_error));
            return false;
        }
        if(edit_fck.getText().toString().isEmpty() && !check_fck.isChecked()){
            edit_fck.setError(getString(R.string.empty_field_error));
            return false;
        }
        if(edit_slump.getText().toString().isEmpty() && !check_slump.isChecked() && String.valueOf(spinner_material.getSelectedItem()).equals(getString(R.string.concreto))){
            edit_slump.setError(getString(R.string.empty_field_error));
            return false;
        }
        if(edit_slump_flow.getText().toString().isEmpty() && !check_slump_flow.isChecked() && String.valueOf(spinner_material.getSelectedItem()).equals(getString(R.string.concreto))){
            edit_slump_flow.setError(getString(R.string.empty_field_error));
            return false;
        }
        if(button_date.getText().toString().isEmpty() && !check_date.isChecked()){
            Toast.makeText(context,getString(R.string.date_notput_error),Toast.LENGTH_SHORT).show();
            return false;
        }

        if(edit_local.getText().toString().isEmpty() && !check_local.isChecked()){
            edit_local.setError(getString(R.string.empty_field_error));
            return false;
        }

        return true;
    }

    public void editTextError(final EditText edittext){
        edittext.setError(getString(R.string.empty_field_error));
    }
    
}
