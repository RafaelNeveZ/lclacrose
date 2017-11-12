package com.lacrose.lc.lclacrose;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
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
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.lacrose.lc.lclacrose.Model.PrismaLotes;
import com.lacrose.lc.lclacrose.Util.FireBaseUtil;
import com.lacrose.lc.lclacrose.Util.MainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class PrismaMoldActivity extends MainActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    private final Context context = this;
    DatabaseReference lote_ref;
    FirebaseDatabase database;
    RelativeLayout dateGraute_relative;
    CheckBox check_dimenssion,check_nota,check_dataFab, check_fpk,check_lote,check_fab,check_func,check_date,check_idade;
    Spinner spinner_dimenssion, spinner_tipo;
    TextView tv_code;
    EditText edit_nota,edit_lote, edit_fpk,edit_fab,edit_more,edit_idade;
    Button button_date, button_datefab, button_dateGraute;
    Calendar refCalendar,tempCalendar,finalCalendar,fabCalendar,assentCalendar, grauteCalendar;
    PrismaLotes newLote;
    ArrayList<String> dimen, tipo;
    public int whatDate=0;
    long date, fabDate,assentDate, grauteDate;
    private Button button_dateAssent;
    private CheckBox check_dateAssent;
    boolean foiCheio=false;
    private CheckBox check_dateGraute;
    private CheckBox check_tipo;
    private CheckBox check_local;
    private Switch switch_func;
    private EditText edit_local;
    private TextView tv_switch;
    private FirebaseAuth Auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prisma_mold);
        refCalendar = Calendar.getInstance();
        tempCalendar = Calendar.getInstance();
        finalCalendar = Calendar.getInstance();
        assentCalendar = Calendar.getInstance();
        fabCalendar = Calendar.getInstance();
        grauteCalendar = Calendar.getInstance();
        database = FireBaseUtil.getDatabase();
        Auth = FirebaseAuth.getInstance();
        initiateViews();
        showProgress(getString(R.string.getting_lote_number));
        getLoteNumber();
        regrasNegocios();
    }
    public void regrasNegocios(){
        dimen = new ArrayList<>();
        dimen.add(getString(R.string.dimenssion_prompt));
        dimen.add(getString(R.string.d120_390_390));
        dimen.add(getString(R.string.d140_390_390));
        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_dimenssion.setAdapter(spinnerAdapter);
        spinnerAdapter.addAll(dimen);
        spinnerAdapter.notifyDataSetChanged();

        tipo = new ArrayList<>();
        tipo.add(getString(R.string.tipo_prompt));
        tipo.add(getString(R.string.vazio));
        tipo.add(getString(R.string.metade));
        tipo.add(getString(R.string.cheio));
        final ArrayAdapter<String> spinnerTipoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        spinnerTipoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_tipo.setAdapter(spinnerTipoAdapter);
        spinnerTipoAdapter.addAll(tipo);
        spinnerTipoAdapter.notifyDataSetChanged();
        spinner_tipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        dateGraute_relative.setVisibility(View.GONE);
                        foiCheio=false;
                        break;
                    case 1:
                        dateGraute_relative.setVisibility(View.GONE);
                        foiCheio=false;
                        break;
                    case 2:
                        dateGraute_relative.setVisibility(View.GONE);
                        foiCheio=false;
                        break;
                    case 3:
                        dateGraute_relative.setVisibility(View.VISIBLE);
                        foiCheio=true;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

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
        final List<PrismaLotes> loteList = new ArrayList<>();
        lote_ref = database.getReference(getString(R.string.work_tag)).child(HomeActivity.WorkId+"").child(getString(R.string.lote_prisma_tag));
        lote_ref.keepSynced(true);
        lote_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for(DataSnapshot d : dataSnapshot.getChildren()) {
                        PrismaLotes prismaLotes = d.getValue(PrismaLotes.class);
                        prismaLotes.setId(d.getKey());
                        loteList.add(prismaLotes);
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
        //RELATIVE LAYOUT
        dateGraute_relative = (RelativeLayout) findViewById(R.id.dateGraute_relate);

        //CHECKBOX
        check_dimenssion = (CheckBox) findViewById(R.id.check_dimenssion);
        check_dataFab = (CheckBox) findViewById(R.id.checkfab_date);
        check_idade = (CheckBox) findViewById(R.id.check_idade);
        check_lote = (CheckBox) findViewById(R.id.check_lote);
        check_nota = (CheckBox) findViewById(R.id.check_nota_fiscal);
        check_fab = (CheckBox) findViewById(R.id.check_fab);
        check_fpk = (CheckBox) findViewById(R.id.check_fpk);
        check_tipo = (CheckBox) findViewById(R.id.check_tipo);
        check_dateGraute = (CheckBox) findViewById(R.id.check_dateGraute);
        check_local = (CheckBox) findViewById(R.id.check_local);
        check_func = (CheckBox) findViewById(R.id.check_func);
        check_date = (CheckBox) findViewById(R.id.check_date);
        check_dateAssent = (CheckBox) findViewById(R.id.checkAssent_date);



        //SPINNER
        spinner_dimenssion=(Spinner) findViewById(R.id.dimenssion_spinner);
        spinner_tipo=(Spinner) findViewById(R.id.tipo_spinner);

        //EDITTEXT
        edit_idade = (EditText) findViewById(R.id.idade_edit_text);
        edit_lote = (EditText) findViewById(R.id.lote_edit_text);
        edit_nota = (EditText) findViewById(R.id.nota_edit_text);
        edit_fab = (EditText) findViewById(R.id.fab_edit_text);
        edit_fpk = (EditText) findViewById(R.id.fpk_edit_text);
        edit_more = (EditText) findViewById(R.id.more_edit_text);
        edit_local = (EditText) findViewById(R.id.local_edit_text);


        //BUTTOM
        button_date = (Button) findViewById(R.id.date_buttom);
        button_datefab = (Button) findViewById(R.id.datefab_buttom);
        button_dateAssent = (Button) findViewById(R.id.dateAssent_buttom);
        button_dateGraute = (Button) findViewById(R.id.dateGraute_buttom);

        //TEXTVIEW
        tv_code = (TextView) findViewById(R.id.code_edit_text);
        tv_switch = (TextView) findViewById(R.id.func_choose_text_view);
        //Switch
        switch_func = (Switch) findViewById(R.id.swit_func);


    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        tempCalendar.set(year,month,dayOfMonth);
      /*  if(tempCalendar.getTime().getTime()>= refCalendar.getTime().getTime()){*/
            SimpleDateFormat fmtOut = new SimpleDateFormat("dd/MM/yyyy");
            switch (whatDate){
                case 0:
                    button_datefab.setText(fmtOut.format(tempCalendar.getTime()));
                    fabCalendar = tempCalendar;
                    fabDate = getDateWithoutHoursAndMinutes(tempCalendar.getTime().getTime());
                    break;
                case 1:
                    button_dateGraute.setText(fmtOut.format(tempCalendar.getTime()));
                    grauteCalendar = tempCalendar;
                    grauteDate = getDateWithoutHoursAndMinutes(tempCalendar.getTime().getTime());
                    break;
                case 2:
                    button_dateAssent.setText(fmtOut.format(tempCalendar.getTime()));
                    assentCalendar = tempCalendar;
                    assentDate = getDateWithoutHoursAndMinutes(tempCalendar.getTime().getTime());
                    break;
                case 3:
                    button_date.setText(fmtOut.format(tempCalendar.getTime()));
                    finalCalendar = tempCalendar;
                    date = getDateWithoutHoursAndMinutes(tempCalendar.getTime().getTime());
                    break;
            }
     /*   }else{
            tempCalendar = Calendar.getInstance();
            showAlert(getString(R.string.dialog_date_error_title),getString(R.string.date_before_error));
        }*/
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
    }


    public void dataFabChamge(View view) {
        whatDate = 0;
        DatePickerDialog dpd = new DatePickerDialog(context, this, tempCalendar.get(Calendar.YEAR), tempCalendar.get(Calendar.MONTH), tempCalendar.get(Calendar.DAY_OF_MONTH));
        dpd.show();
    }
    public void  dateGrauteChamge(View view) {
        whatDate = 1;
        DatePickerDialog dpd = new DatePickerDialog(context, this, tempCalendar.get(Calendar.YEAR), tempCalendar.get(Calendar.MONTH), tempCalendar.get(Calendar.DAY_OF_MONTH));
        dpd.show();
    }
    public void dataAssentChamge(View view) {
        whatDate = 2;
        DatePickerDialog dpd = new DatePickerDialog(context, this, tempCalendar.get(Calendar.YEAR), tempCalendar.get(Calendar.MONTH), tempCalendar.get(Calendar.DAY_OF_MONTH));
        dpd.show();
    }
    public void dataChamge(View view) {
        whatDate = 3;
        DatePickerDialog dpd = new DatePickerDialog(context, this, tempCalendar.get(Calendar.YEAR), tempCalendar.get(Calendar.MONTH), tempCalendar.get(Calendar.DAY_OF_MONTH));
        dpd.show();
    }


    public void loteCreate(View view) {
        showProgress(getString(R.string.create_lote));
        if(validateFields()){
            DatabaseReference ref_lote = database.getReference(getString(R.string.work_tag)).child(HomeActivity.WorkId+"").child(getString(R.string.lote_prisma_tag));
            newLote = new PrismaLotes();
            newLote.setCodigo(Integer.parseInt(tv_code.getText().toString()));

            if(!edit_idade.getText().toString().isEmpty() && !check_idade.isChecked()) {
                Calendar temp = Calendar.getInstance();
                temp.add(Calendar.DATE, +Integer.parseInt(edit_idade.getText().toString()));
                newLote.setIdade(getDateWithoutHoursAndMinutes(temp.getTime().getTime()));
            }

            if(!edit_nota.getText().toString().isEmpty() && !check_nota.isChecked())
                newLote.setNotaFiscal(Long.parseLong(edit_nota.getText().toString()));

            if(!edit_lote.getText().toString().isEmpty() && !check_lote.isChecked())
                newLote.setLote(edit_lote.getText().toString());

            if(!edit_fpk.getText().toString().isEmpty() && !check_fpk.isChecked())
                newLote.setFPK(Integer.parseInt(edit_fpk.getText().toString()));

            if(!String.valueOf(spinner_tipo.getSelectedItem()).equals(getString(R.string.tipo_prompt)))
                newLote.setTipo(spinner_tipo.getSelectedItem().toString());

            if(!button_date.getText().toString().equals(getString(R.string.date_default)) && !check_date.isChecked())
                newLote.setData(date);

            if(!button_datefab.getText().toString().equals(getString(R.string.date_default)) && !check_dataFab.isChecked())
                newLote.setDataFab(fabDate);

            if(!button_dateGraute.getText().toString().equals(getString(R.string.date_default)) && !check_dateGraute.isChecked() && foiCheio)
                newLote.setDataGraute(grauteDate);

            if(!edit_local.getText().toString().isEmpty() && !check_local.isChecked())
                newLote.setLocal(edit_local.getText().toString());

            if(!button_dateAssent.getText().toString().equals(getString(R.string.date_default)) && !check_dateAssent.isChecked())
                newLote.setDataAssent(assentDate);

            if(!edit_fpk.getText().toString().isEmpty() && !check_fpk.isChecked())
                newLote.setFabricante(edit_fab.getText().toString());

            if(switch_func.isChecked())
                newLote.setFuncEstrutural(true);
            else
                newLote.setFuncEstrutural(false);

            if(!edit_more.getText().toString().isEmpty())
                newLote.setObs(edit_more.getText().toString());
            

            HashMap<String, Integer> dimenssionHash = new HashMap<>();
            if(!String.valueOf(spinner_dimenssion.getSelectedItem()).equals(getString(R.string.dimenssion_prompt))) {
                if (String.valueOf(spinner_dimenssion.getSelectedItem()).equals(getString(R.string.d120_390_390))) {
                    dimenssionHash.put(getString(R.string.largura), 120);
                    dimenssionHash.put(getString(R.string.altura), 390);
                    dimenssionHash.put(getString(R.string.comprimento), 390);
                    newLote.setDimenssoes(dimenssionHash);
                }else if (String.valueOf(spinner_dimenssion.getSelectedItem()).equals(getString(R.string.d140_390_390))) {
                    dimenssionHash.put(getString(R.string.largura), 140);
                    dimenssionHash.put(getString(R.string.altura), 390);
                    dimenssionHash.put(getString(R.string.comprimento), 390);
                    newLote.setDimenssoes(dimenssionHash);

                }
            }

            newLote.setCreatedBy(Auth.getCurrentUser().getUid(),true);

            newLote.setDataCreate(ServerValue.TIMESTAMP);
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
            errorAndRequestFocustoEditText(edit_idade);
            return false;
        }

        if(edit_lote.getText().toString().isEmpty() && !check_lote.isChecked()){
            errorAndRequestFocustoEditText(edit_lote);
            return false;
        }

        if(edit_nota.getText().toString().isEmpty() && !check_nota.isChecked()){
            errorAndRequestFocustoEditText(edit_nota);
            return false;
        }

        if(edit_fab.getText().toString().isEmpty() && !check_fab.isChecked()){
            errorAndRequestFocustoEditText(edit_fab);
            return false;
        }

        if(edit_fpk.getText().toString().isEmpty() && !check_fpk.isChecked()){
            errorAndRequestFocustoEditText(edit_fpk);
            return false;
        }

        if(String.valueOf(spinner_tipo.getSelectedItem()).equals(getString(R.string.tipo_prompt)) && !check_tipo.isChecked()){
            Toast.makeText(context,getString(R.string.tipo_prompt),Toast.LENGTH_SHORT).show();
            return false;
        }

        if(button_dateGraute.getText().equals(getString(R.string.date_default)) && !check_dateGraute.isChecked() && foiCheio){
            Toast.makeText(context,getString(R.string.date_notput_error),Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!button_dateGraute.getText().equals(getString(R.string.date_default)) && !check_dateGraute.isChecked()
                && !button_datefab.getText().equals(getString(R.string.date_default)) && !check_dataFab.isChecked()) {
            if (grauteDate < fabDate) {
                showAlert(getString(R.string.dialog_date_error_title), getString(R.string.dialog_date_Grauur_error_title));
                return false;
            }
        }

        if(edit_local.getText().toString().isEmpty() && !check_local.isChecked()){
            errorAndRequestFocustoEditText(edit_local);
            return false;
        }

        if(button_date.getText().equals(getString(R.string.date_default)) && !check_date.isChecked()){
            Toast.makeText(context,getString(R.string.date_notput_error),Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!button_date.getText().equals(getString(R.string.date_default)) && !check_date.isChecked()
                && !button_datefab.getText().equals(getString(R.string.date_default)) && !check_dataFab.isChecked() && date < fabDate) {
            showAlert(getString(R.string.dialog_date_error_title), getString(R.string.dialog_date_error));
            return false;

        }

        if(button_dateAssent.getText().equals(getString(R.string.date_default)) && !check_dateAssent.isChecked()){
            Toast.makeText(context,getString(R.string.date_notput_error),Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!button_dateAssent.getText().equals(getString(R.string.date_default)) && !check_dateAssent.isChecked()
                && !button_datefab.getText().equals(getString(R.string.date_default)) && !check_dataFab.isChecked() && assentDate < fabDate) {
                showAlert(getString(R.string.dialog_date_error_title), getString(R.string.dialog_date_Assent_error_title));
                return false;
        }
        return true;
    }




}