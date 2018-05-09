package com.lacrose.lc.lclacrose;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.lacrose.lc.lclacrose.Adapter.CodigoCorpoAdapter;
import com.lacrose.lc.lclacrose.Model.PrismaLotes;
import com.lacrose.lc.lclacrose.Util.FireBaseUtil;
import com.lacrose.lc.lclacrose.Util.MainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static android.Manifest.permission.CAMERA;

public class PrismaMoldActivity extends MainActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    private final Context context = this;
    CollectionReference lote_ref;
    FirebaseFirestore database;
    CodigoCorpoAdapter codigoCorpoAdapter;
    RelativeLayout dateGraute_relative;
    RelativeLayout relative_hora,relative_largura,relative_altura,relative_comprimento;
    CheckBox check_dimenssion,check_nota,check_dataFab, check_fpk,check_lote,check_fab,check_func,check_date,check_idade;
    Spinner spinner_dimenssion, spinner_tipo;
    TextView tv_code;
    EditText edit_nota,edit_lote, edit_fpk,edit_fab,edit_more,edit_idade,edit_quantidade,edit_hora,edit_altura,edit_largra,edit_comprimeto;
    Button button_date, button_datefab, button_dateGraute,button_hora;
    Calendar refCalendar,tempCalendar,finalCalendar,fabCalendar,assentCalendar, grauteCalendar,horaCalendar;
    PrismaLotes newLote;
    ArrayList<String> dimen, tipo;
    public int whatDate=0;
    long date, fabDate,assentDate, grauteDate,hora;
    private Button button_dateAssent;
    private CheckBox check_dateAssent;
    boolean foiOco =false;
    private CheckBox check_dateGraute;
    private CheckBox check_tipo;
    private CheckBox check_local;
    private Switch switch_func;
    private EditText edit_local;
    private TextView tv_switch;
    private FirebaseAuth Auth;
    ListView lista_corpos;
    List<String>corpos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prisma_mold);
        refCalendar = Calendar.getInstance();
        tempCalendar = Calendar.getInstance();
        finalCalendar = Calendar.getInstance();
        horaCalendar = Calendar.getInstance();
        assentCalendar = Calendar.getInstance();
        fabCalendar = Calendar.getInstance();
        grauteCalendar = Calendar.getInstance();
        database = FireBaseUtil.getFireDatabase();
        Auth = FirebaseAuth.getInstance();
        initiateViews();
        showProgress(getString(R.string.getting_lote_number));
        getLoteNumber();
        regrasNegocios();
    }
    public void regrasNegocios(){
        Intent intent = getIntent();
        String dataFabLong = intent.getStringExtra("dataFabLong");
        String dataFabText = intent.getStringExtra("dataFabText");
        String dataLong = intent.getStringExtra("dataLong");
        String dataText = intent.getStringExtra("dataText");
        String fabricante = intent.getStringExtra("fabricante");
        String fpk = intent.getStringExtra("fpk");
        if(dataFabLong !=null && dataFabText !=null){
            log(dataFabText);
            if(dataFabLong.equals("NI")){
                check_dataFab.setChecked(true);
            }else{
                fabDate = Long.parseLong(dataFabLong);

                button_datefab.setText(dataFabText);
            }
        }
        if(dataLong !=null && dataText !=null){
            date = Long.parseLong(dataLong);
            button_date.setText(dataText);
        }
        if(fabricante !=null){
            if(fabricante.equals("NI")){
                check_fab.setChecked(true);
            }else{
                edit_fab.setText(fabricante);
            }
        }
        if(fpk !=null){
            if(fpk.equals("NI")){
                check_fpk.setChecked(true);
            }else{
                edit_fpk.setText(fpk);
            }
        }
        dimen = new ArrayList<>();
        dimen.add(getString(R.string.dimenssion_prompt));
        dimen.add(getString(R.string.d120_390_390));
        dimen.add(getString(R.string.d140_390_390));
        dimen.add(getString(R.string.outras_dim));
        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_dimenssion.setAdapter(spinnerAdapter);
        spinnerAdapter.addAll(dimen);
        spinnerAdapter.notifyDataSetChanged();
        spinner_dimenssion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(String.valueOf(spinner_dimenssion.getSelectedItem()).equals(getString(R.string.outras_dim))){
                    relative_altura.setVisibility(View.VISIBLE);
                    relative_comprimento.setVisibility(View.VISIBLE);
                    relative_largura.setVisibility(View.VISIBLE);
                }else{
                    relative_altura.setVisibility(View.GONE);
                    relative_comprimento.setVisibility(View.GONE);
                    relative_largura.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        tipo = new ArrayList<>();
        tipo.add(getString(R.string.tipo_prompt));
        tipo.add(getString(R.string.oco));
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
                    case 1:
                        dateGraute_relative.setVisibility(View.VISIBLE);
                        foiOco =true;
                        break;
                    case 2:
                        dateGraute_relative.setVisibility(View.GONE);
                        foiOco =false;
                        break;
                    case 3:
                        dateGraute_relative.setVisibility(View.GONE);
                        foiOco =false;
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

        database.collection(getString(R.string.lote_tag))
                .whereEqualTo(getString(R.string.obraid),HomeActivity.WorkId)
                .whereEqualTo(getString(R.string.tipo),getString(R.string.prisma_minusculo))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            int last = task.getResult().size();
                            tv_code.setText("" + last);

                            /*if (last < 10)
                                tv_code.setText("000" + last);
                            else if (last < 100)
                                tv_code.setText("00" + last);
                            else if (last < 1000)
                                tv_code.setText("0" + last);
                            else if (last < 1000)
                                tv_code.setText("" + last);*/

                            dismissProgress();
                        } else {
                            dismissProgress();
                            /*int last =  0;*/
                            tv_code.setText(0);
                            /*tv_code.setText(last+"000");*/

                        }
                    }
                });
    }


    public void initiateViews(){


        //CHECKBOX
       // check_dimenssion = (CheckBox) findViewById(R.id.check_dimenssion);
        check_dataFab = (CheckBox) findViewById(R.id.checkfab_date);
       // check_idade = (CheckBox) findViewById(R.id.check_idade);
        check_lote = (CheckBox) findViewById(R.id.check_lote);
        check_nota = (CheckBox) findViewById(R.id.check_nota_fiscal);
        check_fab = (CheckBox) findViewById(R.id.check_fab);
        check_fpk = (CheckBox) findViewById(R.id.check_fpk);
       // check_tipo = (CheckBox) findViewById(R.id.check_tipo);
       // check_dateGraute = (CheckBox) findViewById(R.id.check_dateGraute);
        check_local = (CheckBox) findViewById(R.id.check_local);
        check_func = (CheckBox) findViewById(R.id.check_func);
      //  check_date = (CheckBox) findViewById(R.id.check_date);
        check_dateAssent = (CheckBox) findViewById(R.id.checkAssent_date);

        //RELATIVE LAYOUT
        dateGraute_relative = (RelativeLayout) findViewById(R.id.dateGraute_relate);
        relative_comprimento = (RelativeLayout) findViewById(R.id.relative_comprimento);
        relative_altura = (RelativeLayout) findViewById(R.id.relative_altura);
        relative_largura = (RelativeLayout) findViewById(R.id.relative_largura);
        relative_hora = (RelativeLayout) findViewById(R.id.relative_hora);
        if(HomeActivity.Work.getIs24().equals("Sim")){
            relative_hora.setVisibility(View.VISIBLE);
        }

        //SPINNER
        spinner_dimenssion=(Spinner) findViewById(R.id.dimenssion_spinner);
        spinner_tipo=(Spinner) findViewById(R.id.tipo_spinner);

        //EDITTEXT
        edit_comprimeto = (EditText) findViewById(R.id.comprimento_edit_text);
        edit_altura = (EditText) findViewById(R.id.altura_edit_text);
        edit_largra = (EditText) findViewById(R.id.largura_edit_text);
        edit_idade = (EditText) findViewById(R.id.idade_edit_text);
        edit_lote = (EditText) findViewById(R.id.lote_edit_text);
        edit_nota = (EditText) findViewById(R.id.nota_edit_text);
        edit_fab = (EditText) findViewById(R.id.fab_edit_text);
        edit_fpk = (EditText) findViewById(R.id.fpk_edit_text);
        edit_more = (EditText) findViewById(R.id.more_edit_text);
        edit_local = (EditText) findViewById(R.id.local_edit_text);
        edit_quantidade = (EditText) findViewById(R.id.quantidade_edit_text);

        //BUTTOM
        button_date = (Button) findViewById(R.id.date_buttom);
        button_datefab = (Button) findViewById(R.id.datefab_buttom);
        button_dateAssent = (Button) findViewById(R.id.dateAssent_buttom);
        button_dateGraute = (Button) findViewById(R.id.dateGraute_buttom);
        button_hora = (Button) findViewById(R.id.hora_buttom);

        //TEXTVIEW
        tv_code = (TextView) findViewById(R.id.code_edit_text);
        tv_switch = (TextView) findViewById(R.id.func_choose_text_view);
        //Switch
        switch_func = (Switch) findViewById(R.id.swit_func);


        lista_corpos = (ListView) findViewById(R.id.corpo_list);
        lista_corpos.setDivider(null);

        corpos = new ArrayList<>();
        codigoCorpoAdapter = new CodigoCorpoAdapter(this, R.layout.item_codigo_corpos, corpos);
        lista_corpos.setAdapter(codigoCorpoAdapter);

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
                    fabDate = tempCalendar.getTime().getTime();
                    break;
                case 1:
                    button_dateGraute.setText(fmtOut.format(tempCalendar.getTime()));
                    grauteCalendar = tempCalendar;
                    grauteDate = tempCalendar.getTime().getTime();
                    break;
                case 2:
                    button_dateAssent.setText(fmtOut.format(tempCalendar.getTime()));
                    assentCalendar = tempCalendar;
                    assentDate = tempCalendar.getTime().getTime();
                    break;
                case 3:
                    button_date.setText(fmtOut.format(tempCalendar.getTime()));
                    finalCalendar = tempCalendar;
                    date = tempCalendar.getTime().getTime();
                    break;
            }
     /*   }else{
            tempCalendar = Calendar.getInstance();
            showAlert(getString(R.string.dialog_date_error_title),getString(R.string.date_before_error));
        }*/
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        tempCalendar.set(Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DATE),hourOfDay,minute);

        button_hora.setText(((hourOfDay<10)?"0"+hourOfDay:hourOfDay)+":"+((minute<10)?"0"+minute:minute));
        horaCalendar = tempCalendar;
        hora = tempCalendar.getTime().getTime();
    }

    public void horaChange(View view) {
        TimePickerDialog tpd = new TimePickerDialog(context, this, Calendar.getInstance().get(Calendar.HOUR), Calendar.getInstance().get(Calendar.MINUTE), true);
        tpd.show();
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
            final CollectionReference ref_lote = database.collection(getString(R.string.lote_tag));
            newLote = new PrismaLotes();
            HashMap<String, Double> dimenssionHash = new HashMap<>();
            if(!String.valueOf(spinner_dimenssion.getSelectedItem()).equals(getString(R.string.dimenssion_prompt))) {
                if (String.valueOf(spinner_dimenssion.getSelectedItem()).equals(getString(R.string.d120_390_390))) {
                    dimenssionHash.put(getString(R.string.largura), 120.0);
                    dimenssionHash.put(getString(R.string.altura), 390.0);
                    dimenssionHash.put(getString(R.string.comprimento), 390.0);
                    newLote.setDimenssion(dimenssionHash);
                } else if (String.valueOf(spinner_dimenssion.getSelectedItem()).equals(getString(R.string.d140_390_390))) {
                    dimenssionHash.put(getString(R.string.largura), 140.0);
                    dimenssionHash.put(getString(R.string.altura), 390.0);
                    dimenssionHash.put(getString(R.string.comprimento), 390.0);
                    newLote.setDimenssion(dimenssionHash);
                } else if(String.valueOf(spinner_dimenssion.getSelectedItem()).equals(getString(R.string.outras_dim))){
                    dimenssionHash.put(getString(R.string.largura), Double.parseDouble(edit_largra.getText().toString()));
                    dimenssionHash.put(getString(R.string.altura), Double.parseDouble(edit_altura.getText().toString()));
                    dimenssionHash.put(getString(R.string.comprimento), Double.parseDouble(edit_comprimeto.getText().toString()));
                    newLote.setDimenssion(dimenssionHash);
                }
            }



            if(!button_datefab.getText().toString().equals(getString(R.string.date_default)) && !check_dataFab.isChecked())
                newLote.setDataFab(fabDate);
            if(!edit_lote.getText().toString().isEmpty() && !check_lote.isChecked())
                newLote.setLote(edit_lote.getText().toString());
            if(!edit_nota.getText().toString().isEmpty() && !check_nota.isChecked())
                newLote.setNotaFiscal(edit_nota.getText().toString());
            if(!edit_fab.getText().toString().isEmpty() && !check_fab.isChecked())
                newLote.setFabricante(edit_fab.getText().toString());
            if(!edit_fpk.getText().toString().isEmpty() && !check_fpk.isChecked())
                newLote.setFpk(Double.parseDouble(edit_fpk.getText().toString()));

            newLote.setFuncEstrutural(getString(R.string.nao_informado));
            if(switch_func.isChecked() && !check_func.isChecked() )
                newLote.setFuncEstrutural("Sim");
            else if(!switch_func.isChecked() && !check_func.isChecked())
                newLote.setFuncEstrutural("Não");

            if(!button_date.getText().toString().equals(getString(R.string.date_default)))
                newLote.setData(date);
            if(!button_dateAssent.getText().toString().equals(getString(R.string.date_default)) && !check_dateAssent.isChecked())
                newLote.setDataAss(assentDate);



            if(!String.valueOf(spinner_tipo.getSelectedItem()).equals(getString(R.string.tipo_prompt)))
                newLote.setTipo_de_prisma(String.valueOf(spinner_tipo.getSelectedItem()));

            if(!button_dateGraute.getText().toString().equals(getString(R.string.date_default) )&& foiOco)
                newLote.setDataGraute(grauteDate);

            if(!edit_local.getText().toString().isEmpty() && !check_local.isChecked())
                newLote.setLocal(edit_local.getText().toString());

            if(!edit_idade.getText().toString().isEmpty() ) {
                newLote.setIdade(Double.parseDouble(edit_idade.getText().toString()));
            }
            if(!button_hora.getText().toString().equals(getString(R.string.hora_default)) && (relative_hora.getVisibility() != View.GONE)){
                newLote.setHora(hora);

            }
            if(!edit_quantidade.getText().toString().isEmpty())
                newLote.setQuantidade(Double.parseDouble(edit_quantidade.getText().toString()));

            newLote.setCodigo(tv_code.getText().toString());

            List<HashMap<String, String>> corpoHashList = new ArrayList<>();

            for (int i = 0; i<corpos.size();i++){
                HashMap<String, String> corpoHash = new HashMap<>();
                corpoHash.put("codigo",corpos.get(i));
                corpoHashList.add(corpoHash);
            }
            newLote.setCorpos(corpoHashList);


            if(!edit_more.getText().toString().isEmpty() )
                newLote.setObs(edit_more.getText().toString());


            newLote.setIsValid(true);
            newLote.setTipo(getString(R.string.prisma_minusculo));
            long todau = Calendar.getInstance().getTime().getTime();
            newLote.setDataCreate(todau);
            newLote.setCreatedBy(Auth.getCurrentUser().getUid());
            newLote.setCentro_de_custo(HomeActivity.Work.getCentro_de_custo());
            newLote.setObraId(HomeActivity.WorkId);

            ref_lote.add(newLote)
                    .addOnCompleteListener(this,new OnCompleteListener(){
                        @Override
                        public void onComplete(@NonNull Task task) {
                            dismissProgress();
                            if(task.isSuccessful()) {
                                final Dialog dialog = new Dialog(context);
                                dialog.setContentView(R.layout.dialog_cp_repeat);
                                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                                lp.copyFrom(dialog.getWindow().getAttributes());
                                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                                dialog.show();
                                dialog.getWindow().setAttributes(lp);
                                final CheckBox dataFab = (CheckBox)dialog.findViewById(R.id.check_dateFab);
                                final CheckBox data = (CheckBox)dialog.findViewById(R.id.check_date);
                                final CheckBox fpk = (CheckBox)dialog.findViewById(R.id.check_fpk);
                                fpk.setVisibility(View.VISIBLE);
                                final CheckBox fab = (CheckBox)dialog.findViewById(R.id.check_fab);
                                fab.setVisibility(View.VISIBLE);
                                Button btCancel = (Button) dialog.findViewById(R.id.button_no);
                                Button btConfrimar = (Button) dialog.findViewById(R.id.button_yes);
                                btConfrimar.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent i = new Intent(PrismaMoldActivity.this, PrismaMoldActivity.class);
                                        if (dataFab.isChecked()) {
                                            if (check_dataFab.isChecked()) {
                                                i.putExtra("dataFabLong", "NI");
                                                i.putExtra("dataFabText", "NI");
                                            } else {
                                                i.putExtra("dataFabLong", fabDate+"");
                                                i.putExtra("dataFabText", button_datefab.getText().toString());
                                            }
                                        }

                                        if (data.isChecked()) {
                                            log(button_date.getText().toString());
                                            i.putExtra("dataLong", date+"");
                                            i.putExtra("dataText", button_date.getText().toString());
                                        }
                                        if (fpk.isChecked()){
                                            if (check_fpk.isChecked()) {
                                                i.putExtra("fpk","NI");
                                            } else {
                                                i.putExtra("fpk",edit_fpk.getText().toString());
                                            }
                                        }
                                        if (fab.isChecked()){
                                            if (check_fab.isChecked()) {
                                                i.putExtra("fabricante","NI");
                                            } else {
                                                i.putExtra("fabricante",edit_fab.getText().toString());
                                            }
                                        }

                                        startActivity(i);
                                        finish();
                                        dialog.dismiss();
                                    }
                                });
                                btCancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                        finish();
                                    }
                                });
                                Toast.makeText(context,getString(R.string.lote_create_sucess),Toast.LENGTH_SHORT).show();
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

        if(String.valueOf(spinner_dimenssion.getSelectedItem()).equals(getString(R.string.dimenssion_prompt))){
            Toast.makeText(context,getString(R.string.dimenssion_prompt),Toast.LENGTH_SHORT).show();
            return false;
        }
        if(String.valueOf(spinner_dimenssion.getSelectedItem()).equals(getString(R.string.outras_dim))){
            if(edit_altura.getText().toString().isEmpty()){
                errorAndRequestFocustoEditText(edit_altura);
                return false;
            }
            if(edit_comprimeto.getText().toString().isEmpty()){
                errorAndRequestFocustoEditText(edit_comprimeto);
                return false;
            }
            if(edit_largra.getText().toString().isEmpty()){
                errorAndRequestFocustoEditText(edit_largra);
                return false;
            }

        }


        if(button_datefab.getText().equals(getString(R.string.date_default)) && !check_dataFab.isChecked()){
            Toast.makeText(context,getString(R.string.date_fab_notput_error),Toast.LENGTH_SHORT).show();
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
        if(button_date.getText().equals(getString(R.string.date_default))){
            Toast.makeText(context,getString(R.string.date_notput_error),Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!button_date.getText().equals(getString(R.string.date_default))
                && !button_datefab.getText().equals(getString(R.string.date_default)) && !check_dataFab.isChecked()) {
            if(date < fabDate){
                showAlert(getString(R.string.dialog_date_error_title), getString(R.string.dialog_date_error));
                return false;
            }
        }
        if(button_dateAssent.getText().equals(getString(R.string.date_default)) && !check_dateAssent.isChecked()){
            Toast.makeText(context,getString(R.string.date_notput_error),Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!button_dateAssent.getText().equals(getString(R.string.date_default)) && !check_dateAssent.isChecked()
                && !button_datefab.getText().equals(getString(R.string.date_default)) && !check_dataFab.isChecked()) {
            if(assentDate < fabDate) {
                showAlert(getString(R.string.dialog_date_error_title), getString(R.string.dialog_date_Assent_error_title));
                return false;
            }
        }
        if(String.valueOf(spinner_tipo.getSelectedItem()).equals(getString(R.string.tipo_prompt))){
            Toast.makeText(context,getString(R.string.tipo_prompt),Toast.LENGTH_SHORT).show();
            return false;
        }

        if(button_dateGraute.getText().equals(getString(R.string.date_default)) && foiOco){
            Toast.makeText(context,getString(R.string.date_notput_error),Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!button_dateGraute.getText().equals(getString(R.string.date_default))
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
        if(edit_idade.getText().toString().isEmpty()){
            errorAndRequestFocustoEditText(edit_idade);
            return false;
        }
        if(relative_hora.getVisibility() != View.GONE){
            if(button_hora.getText().equals(getString(R.string.hora_default))){
                Toast.makeText(context,getString(R.string.hora_notput_error),Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if(edit_quantidade.getText().toString().isEmpty() ){
            errorAndRequestFocustoEditText(edit_quantidade);
            return false;
        }
        if(corpos.size() !=  Integer.parseInt(edit_quantidade.getText().toString())){
            Toast.makeText(context,getString(R.string.corpos_erros),Toast.LENGTH_SHORT).show();
            return false;
        }









        return true;
    }

    public void addCorpo(View view) {
        ScanAddCorpoActivity.vimdeCP = false;
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
            if (checkPermission()) {
                Intent intent = new Intent(PrismaMoldActivity.this, ScanAddCorpoActivity.class);
                startActivityForResult(intent,10);
            } else {
                requestPermission();
            }
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == 10) {
            String result=data.getStringExtra("result");
            corpos.add(result);
            codigoCorpoAdapter.notifyDataSetChanged();
        }
        if (resultCode == 8) {
            Toast.makeText(this,getString(R.string.cancelada),Toast.LENGTH_SHORT);
        }
    }
    //PERMISSIONS===============================================================
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        return result == PackageManager.PERMISSION_GRANTED;
    }
    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, PERMISSION_REQUEST_CODE);
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],@NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted) {
                        Intent intent = new Intent(PrismaMoldActivity.this, ScanAddCorpoActivity.class);
                        startActivityForResult(intent,10);
                    }else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                showMessageOKCancel(getString(R.string.dialog_permission),
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{CAMERA},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }
                break;
        }
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(PrismaMoldActivity.this)
                .setMessage(message)
                .setPositiveButton(R.string.ok, okListener)
                .setNegativeButton(R.string.cancel, null)
                .create()
                .show();
    }


}