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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.lacrose.lc.lclacrose.Adapter.CodigoCorpoAdapter;
import com.lacrose.lc.lclacrose.Adapter.CodigoCorpoCPAdapter;
import com.lacrose.lc.lclacrose.Model.BlocoLotes;
import com.lacrose.lc.lclacrose.Model.Concreteira;
import com.lacrose.lc.lclacrose.Model.CorpoLotes;
import com.lacrose.lc.lclacrose.Util.FireBaseUtil;
import com.lacrose.lc.lclacrose.Util.MainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static android.Manifest.permission.CAMERA;

public class CorpoMoldActivity extends MainActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{


    private final Context context = this;
    CollectionReference lote_ref;
    FirebaseFirestore database;
    CheckBox check_material,check_dimenssion,check_dataFab, check_concre,check_nota,check_volume,check_fck,check_slump,check_slump_flow,check_date,check_local, check_idade;
    Spinner spinner_material,spinner_dimenssion, spinner_concre, spinner_slump,spinner_alvenaria;
    EditText edit_nota,edit_volume,edit_fck,edit_slump,edit_slump_flow,edit_local,edit_more,edit_idade,edit_quantidade,edit_hora,edit_altura,edit_largra,edit_comprimeto;
    Button button_date, button_datefab,button_hora;
    TextView tv_code,tv_slump,tv_slump_flow;
    Calendar refCalendar,tempCalendar,finalCalendar,fabCalendar,horaCalendar;
    CorpoLotes newLote;
    ArrayList<String> concre_dim, argamassa_dim, graute_dim, defaultlist_dim, slump_list, concreteiras_list,alvenarialist,materialsList;
    RelativeLayout relative_slump,relative_alvenaria;
    RelativeLayout relative_hora,relative_largura,relative_altura,relative_comprimento,relative_nb_slump;
    ListView lista_corpos;
    List<String>concreteirasIDs;
    List<HashMap<String,String>>corpos;
    CodigoCorpoCPAdapter codigoCorpoAdapter;
    public boolean isFab=true,temAlvenaria=false;
    long date, fabDate,hora;
    int positionOfConcreteiraEscolhida=-1;

    private FirebaseAuth Auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corpo_mold);
        refCalendar = Calendar.getInstance();
        tempCalendar = Calendar.getInstance();
        fabCalendar = Calendar.getInstance();
        finalCalendar = Calendar.getInstance();
        horaCalendar = Calendar.getInstance();
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
        final String Concreteira = intent.getStringExtra("Concreteira");
        String dataLong = intent.getStringExtra("dataLong");
        String dataText = intent.getStringExtra("dataText");
        String local = intent.getStringExtra("local");
        String fck = intent.getStringExtra("fck");
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
        if(local !=null){
            if(local.equals("NI")){
                check_local.setChecked(true);
            }else{
                edit_local.setText(local);
            }
        }
        if(fck !=null){
            if(fck.equals("NI")){
                check_fck.setChecked(true);
            }else{
                edit_fck.setText(fck);
            }
        }
        defaultlist_dim = new ArrayList<>();
        defaultlist_dim.add(getString(R.string.select_material));
        concre_dim = new ArrayList<>();
        concre_dim.add(getString(R.string.dimenssion_prompt));
        concre_dim.add(getString(R.string.d100_200));
        concre_dim.add(getString(R.string.outras_dim));
        argamassa_dim = new ArrayList<>();
        argamassa_dim.add(getString(R.string.dimenssion_prompt));
        argamassa_dim.add(getString(R.string.d40_40));
        argamassa_dim.add(getString(R.string.d50_100));
        argamassa_dim.add(getString(R.string.outras_dim));
        graute_dim = new ArrayList<>();
        graute_dim.add(getString(R.string.dimenssion_prompt));
        graute_dim.add(getString(R.string.d50_100));
        graute_dim.add(getString(R.string.d100_200));
        graute_dim.add(getString(R.string.outras_dim));

        alvenarialist = new ArrayList<>();
        alvenarialist.add(getString(R.string.alvenaria_prompt));
        alvenarialist.add(getString(R.string.sim));
        alvenarialist.add(getString(R.string.nao));
        final ArrayAdapter<String> spinnerAdapterAlvenaria = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        spinnerAdapterAlvenaria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_alvenaria.setAdapter(spinnerAdapterAlvenaria);
        spinnerAdapterAlvenaria.addAll(alvenarialist);
        spinnerAdapterAlvenaria.notifyDataSetChanged();

        slump_list = new ArrayList<>();
        slump_list.add(getString(R.string.slump_prompt));
        slump_list.add(getString(R.string.slump));
        slump_list.add(getString(R.string.slump_flow));
        final ArrayAdapter<String> spinnerAdapterSlump = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        spinnerAdapterSlump.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_slump.setAdapter(spinnerAdapterSlump);
        spinnerAdapterSlump.addAll(slump_list);
        spinnerAdapterSlump.notifyDataSetChanged();

        concreteiras_list = new ArrayList<>();
        concreteirasIDs = new ArrayList<>();
        concreteirasIDs.add(getString(R.string.construc_prompt));
        concreteiras_list.add(getString(R.string.construc_prompt));
        int count = 0;
        for (final String concrecId:
             HomeActivity.Work.getConcreteiras()) {
            database.document(getString(R.string.concrec_tag)+"/"+concrecId)
                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Log.d(TAG,documentSnapshot.getId().toString());
                    final Concreteira concreteira = documentSnapshot.toObject(Concreteira.class);
                    if(concreteira.getIsValid()){
                        concreteiras_list.add(concreteira.getNome()+" - "+concreteira.getSigla());
                        if(Concreteira !=null && !Concreteira.equals("NI")) {


                            if(Concreteira.equals(concreteira.getNome()+" - "+concreteira.getSigla())){
                                log(Concreteira);
                                positionOfConcreteiraEscolhida = concreteiras_list.size()-1;



                            }
                        }

                        log(concreteira.getNome()+" - "+concreteira.getSigla());
                        concreteirasIDs.add(documentSnapshot.getId().toString());
                    }
                    if(concreteiras_list.size() == HomeActivity.Work.getConcreteiras().size()+1){
                        concreteiras_list.add(getString(R.string.feito_obra));
                        final ArrayAdapter<String> spinnerAdapterCon = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item);
                        spinnerAdapterCon.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_concre.setAdapter(spinnerAdapterCon);
                        spinnerAdapterCon.addAll(concreteiras_list);
                        spinnerAdapterCon.notifyDataSetChanged();
                        if(Concreteira !=null) {
                            if (Concreteira.equals("NI")) {
                                check_concre.setChecked(true);
                            } else if (Concreteira.equals(getString(R.string.feito_obra))) {
                                spinner_concre.setSelection(concreteiras_list.size()-1);
                                }else{
                                spinner_concre.setSelection(positionOfConcreteiraEscolhida);
                            }
                        }
                    }

                }
            });

        }

        spinner_concre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                positionOfConcreteiraEscolhida = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        materialsList = new ArrayList<>();
        materialsList.add(getString(R.string.material_prompt));
        for (String material:
                HomeActivity.Work.getMateriais()) {
            materialsList.add(material);

        }


        final ArrayAdapter<String> spinnerAdapterDim = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        spinnerAdapterDim.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_dimenssion.setAdapter(spinnerAdapterDim);
        spinnerAdapterDim.addAll(defaultlist_dim);
        spinnerAdapterDim.notifyDataSetChanged();

        final ArrayAdapter<String> spinnerAdapterMat = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        spinnerAdapterMat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_material.setAdapter(spinnerAdapterMat);
        spinnerAdapterMat.addAll(materialsList);
        spinnerAdapterMat.notifyDataSetChanged();


        spinner_material.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (spinnerAdapterMat.getItem(position)) {
                    case "Concreto":
                        relative_alvenaria.setVisibility(View.GONE);
                        relative_slump.setVisibility(View.VISIBLE);
                        relative_nb_slump.setVisibility(View.VISIBLE);
                        spinnerAdapterDim.clear();
                        spinnerAdapterDim.addAll(concre_dim);
                        spinnerAdapterDim.notifyDataSetChanged();
                        break;
                    case "Graute":
                        if(temAlvenaria){
                            relative_alvenaria.setVisibility(View.VISIBLE);
                        }
                        relative_slump.setVisibility(View.GONE);
                        relative_nb_slump.setVisibility(View.GONE);
                        spinnerAdapterDim.clear();
                        spinnerAdapterDim.addAll(graute_dim);
                        spinnerAdapterDim.notifyDataSetChanged();
                    break;
                    case "Argamassa":
                        if(temAlvenaria){
                            relative_alvenaria.setVisibility(View.VISIBLE);
                        }
                        relative_slump.setVisibility(View.GONE);
                        relative_nb_slump.setVisibility(View.GONE);
                        spinnerAdapterDim.clear();
                        spinnerAdapterDim.addAll(argamassa_dim);
                        spinnerAdapterDim.notifyDataSetChanged();
                        break;
                    case "Selecione o material":
                        relative_alvenaria.setVisibility(View.GONE);
                        relative_nb_slump.setVisibility(View.GONE);
                        relative_slump.setVisibility(View.GONE);
                        spinnerAdapterDim.clear();
                        spinnerAdapterDim.addAll(defaultlist_dim);
                        spinnerAdapterDim.notifyDataSetChanged();
                        break;

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinner_dimenssion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                if(String.valueOf(spinner_dimenssion.getSelectedItem()).equals(getString(R.string.outras_dim))){
                    relative_altura.setVisibility(View.VISIBLE);
                    relative_largura.setVisibility(View.VISIBLE);
                }else{
                    relative_altura.setVisibility(View.GONE);
                    relative_largura.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    private void getLoteNumber() {
        final List<BlocoLotes> loteList = new ArrayList<>();
        database.collection(getString(R.string.lote_tag))
                .whereEqualTo(getString(R.string.obraid),HomeActivity.WorkId)
                .whereEqualTo(getString(R.string.tipo),getString(R.string.cp_minusculo))
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
      //  check_material = (CheckBox) findViewById(R.id.check_material);
     //   check_dimenssion = (CheckBox) findViewById(R.id.check_dimenssion);
        check_concre = (CheckBox) findViewById(R.id.check_contruc);
        check_nota = (CheckBox) findViewById(R.id.check_nota_fiscal);
        check_volume = (CheckBox) findViewById(R.id.check_volume);
        check_fck = (CheckBox) findViewById(R.id.check_fck);
        check_dataFab = (CheckBox) findViewById(R.id.checkfab_date);

        //   check_slump = (CheckBox) findViewById(R.id.check_slump);
    //    check_slump_flow = (CheckBox) findViewById(R.id.check_slump_flow);
    //    check_date = (CheckBox) findViewById(R.id.check_date);
        check_local = (CheckBox) findViewById(R.id.check_local);
     //   check_idade = (CheckBox) findViewById(R.id.check_idade);

        //SPINNER
        spinner_material=(Spinner) findViewById(R.id.material_spinner);
        spinner_dimenssion=(Spinner) findViewById(R.id.dimenssion_spinner);
        spinner_concre =(Spinner) findViewById(R.id.constru_spinner);
        spinner_alvenaria=(Spinner) findViewById(R.id.alvenaria_spinner);
        spinner_slump=(Spinner) findViewById(R.id.slump_spinner);


        //EDITTEXT
        edit_altura = (EditText) findViewById(R.id.altura_edit_text);
        edit_largra = (EditText) findViewById(R.id.largura_edit_text);
        edit_nota = (EditText) findViewById(R.id.nota_edit_text);
        edit_volume = (EditText) findViewById(R.id.volume_edit_text);
        edit_fck = (EditText) findViewById(R.id.fck_edit_text);
        edit_slump = (EditText) findViewById(R.id.slump_nb_edit_text);
   //     edit_slump_flow = (EditText) findViewById(R.id.slump_flow_edit_text);
        edit_local = (EditText) findViewById(R.id.local_edit_text);
        edit_more = (EditText) findViewById(R.id.more_edit_text);
        edit_quantidade = (EditText) findViewById(R.id.quantidade_edit_text);

        //BUTTOM
        button_date = (Button) findViewById(R.id.date_buttom);
        button_datefab = (Button) findViewById(R.id.datefab_buttom);
        button_hora = (Button) findViewById(R.id.hora_buttom);
        //TEXTVIEW

        tv_code = (TextView) findViewById(R.id.code_edit_text);
 //       tv_slump = (TextView) findViewById(R.id.slump_text_view);
//        tv_slump_flow = (TextView) findViewById(R.id.slump_flow_text_view);

        //Relative
           relative_slump = (RelativeLayout) findViewById(R.id.relative_slump);
        relative_altura = (RelativeLayout) findViewById(R.id.relative_altura);
        relative_largura = (RelativeLayout) findViewById(R.id.relative_largura);
        relative_hora = (RelativeLayout) findViewById(R.id.relative_hora);
        relative_alvenaria = (RelativeLayout) findViewById(R.id.relative_alvenaria);
        if(HomeActivity.Work.getIs24().equals("Sim")){
            relative_hora.setVisibility(View.VISIBLE);
        }
        relative_nb_slump = (RelativeLayout) findViewById(R.id.slump_nb_relative);
        if(!HomeActivity.Work.getAlvenaria().equals(getString(R.string.nao_possui))){

            temAlvenaria = true;
        }
        lista_corpos = (ListView) findViewById(R.id.corpo_list);
        lista_corpos.setDivider(null);
        corpos = new ArrayList<>();
        codigoCorpoAdapter = new CodigoCorpoCPAdapter(this, R.layout.item_codigo_corpos, corpos);
        lista_corpos.setAdapter(codigoCorpoAdapter);
  //      slumpflow = (RelativeLayout) findViewById(R.id.slump_flow_relative);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        tempCalendar.set(year,month,dayOfMonth);

        SimpleDateFormat fmtOut = new SimpleDateFormat("dd/MM/yyyy");
        if(isFab) {
            button_datefab.setText(fmtOut.format(tempCalendar.getTime()));
            fabCalendar = tempCalendar;
            fabDate = tempCalendar.getTime().getTime();
        }else {
            button_date.setText(fmtOut.format(tempCalendar.getTime()));
            finalCalendar = tempCalendar;
            date = tempCalendar.getTime().getTime();
        }

    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        tempCalendar.set(Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DATE),hourOfDay,minute);

        button_hora.setText(((hourOfDay<10)?"0"+hourOfDay:hourOfDay)+":"+((minute<10)?"0"+minute:minute));
        horaCalendar = tempCalendar;
        hora = tempCalendar.getTime().getTime();

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
            final CollectionReference ref_lote = database.collection(getString(R.string.lote_tag));
            newLote = new CorpoLotes();

            if(!String.valueOf(spinner_material.getSelectedItem()).equals(getString(R.string.material_prompt)))
            newLote.setMaterial(String.valueOf(spinner_material.getSelectedItem()));


            HashMap<String, Double> dimenssionHash = new HashMap<>();
            if(!String.valueOf(spinner_dimenssion.getSelectedItem()).equals(getString(R.string.dimenssion_prompt))) {
                if (String.valueOf(spinner_dimenssion.getSelectedItem()).equals(getString(R.string.d100_200))) {
                    dimenssionHash.put(getString(R.string.largura), 100.0);
                    dimenssionHash.put(getString(R.string.altura), 200.0);
                    newLote.setDimenssion(dimenssionHash);
                } else if (String.valueOf(spinner_dimenssion.getSelectedItem()).equals(getString(R.string.d50_100))) {
                    dimenssionHash.put(getString(R.string.largura), 50.0);
                    dimenssionHash.put(getString(R.string.altura), 100.0);
                    newLote.setDimenssion(dimenssionHash);
                } else if (String.valueOf(spinner_dimenssion.getSelectedItem()).equals(getString(R.string.d40_40))) {
                    dimenssionHash.put(getString(R.string.largura), 40.0);
                    dimenssionHash.put(getString(R.string.altura), 40.0);
                    newLote.setDimenssion(dimenssionHash);
                } else if(String.valueOf(spinner_dimenssion.getSelectedItem()).equals(getString(R.string.outras_dim))){
                    dimenssionHash.put(getString(R.string.largura), Double.parseDouble(edit_largra.getText().toString()));
                    dimenssionHash.put(getString(R.string.altura), Double.parseDouble(edit_altura.getText().toString()));
                    newLote.setDimenssion(dimenssionHash);
                }
            }


            if(!button_datefab.getText().toString().equals(getString(R.string.date_default)) && !check_dataFab.isChecked())
                newLote.setDataFab(fabDate);

            if(!String.valueOf(spinner_concre.getSelectedItem()).equals(getString(R.string.construc_prompt))&&!check_concre.isChecked()
                    && positionOfConcreteiraEscolhida !=-1){
                if(String.valueOf(spinner_concre.getSelectedItem()).equals(getString(R.string.feito_obra))){
                    newLote.setConcreteira(getString(R.string.feito_obra));
                }else{
                    newLote.setConcreteira(concreteirasIDs.get(positionOfConcreteiraEscolhida));
                }

            }
            if(!edit_volume.getText().toString().isEmpty() && !check_volume.isChecked())
                newLote.setVolume(Double.parseDouble(edit_volume.getText().toString()));

            if(!edit_nota.getText().toString().isEmpty() && !check_nota.isChecked())
                newLote.setNotaFiscal(edit_nota.getText().toString());

            if(!edit_fck.getText().toString().isEmpty() && !check_fck.isChecked())
                newLote.setFck(Double.parseDouble(edit_fck.getText().toString()));

            if(!String.valueOf(spinner_alvenaria.getSelectedItem()).equals(getString(R.string.alvenaria_prompt))&&temAlvenaria){
               newLote.setAlvenaria(String.valueOf(spinner_alvenaria.getSelectedItem()));
            }


            if (String.valueOf(spinner_material.getSelectedItem()).equals(getString(R.string.concreto)) &&
                    !String.valueOf(spinner_slump.getSelectedItem()).equals(getString(R.string.slump_prompt))
                    ) {
                newLote.setSlump(String.valueOf(spinner_slump.getSelectedItem()));

            }

            if(!edit_slump.getText().toString().isEmpty() && relative_nb_slump.getVisibility() != View.GONE)
                newLote.setSlumpNB(Double.parseDouble(edit_slump.getText().toString()));

            if(!edit_local.getText().toString().isEmpty() && !check_local.isChecked())
                newLote.setLocal_concretado(edit_local.getText().toString());


            if(!button_date.getText().toString().equals(getString(R.string.date_default)))
                newLote.setData(date);

            if(!edit_quantidade.getText().toString().isEmpty())
                newLote.setQuantidade(Double.parseDouble(edit_quantidade.getText().toString()));

            newLote.setCodigo(tv_code.getText().toString());



            if(!button_hora.getText().toString().equals(getString(R.string.hora_default)) && (relative_hora.getVisibility() != View.GONE)){
                newLote.setHora(hora);

            }

            List<HashMap<String, String>> corpoHashList = new ArrayList<>();

            for (int i = 0; i<corpos.size();i++){
                HashMap<String, String> corpoHash = new HashMap<>();
                corpoHash.put("codigo",corpos.get(i).get("codigo"));
                corpoHash.put("idade",corpos.get(i).get("idade"));
                corpoHashList.add(corpoHash);
            }
            newLote.setCorpos(corpoHashList);


            if(!edit_more.getText().toString().isEmpty() )
                newLote.setObs(edit_more.getText().toString());


            newLote.setIsValid(true);
            newLote.setTipo(getString(R.string.cp_minusculo));
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
                                final CheckBox Concre = (CheckBox)dialog.findViewById(R.id.check_conc);
                                Concre.setVisibility(View.VISIBLE);
                                final CheckBox fck = (CheckBox)dialog.findViewById(R.id.check_fck);
                                fck.setVisibility(View.VISIBLE);
                                final CheckBox local = (CheckBox)dialog.findViewById(R.id.check_local);
                                local.setVisibility(View.VISIBLE);
                                Button btCancel = (Button) dialog.findViewById(R.id.button_no);
                                Button btConfrimar = (Button) dialog.findViewById(R.id.button_yes);
                                btConfrimar.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent i = new Intent(CorpoMoldActivity.this, CorpoMoldActivity.class);
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
                                        if (Concre.isChecked()) {
                                            if (check_concre.isChecked()) {
                                                i.putExtra("Concreteira", "NI");

                                            } else {
                                                i.putExtra("Concreteira", String.valueOf(spinner_concre.getSelectedItem()));

                                            }
                                        }
                                        if (fck.isChecked()){
                                            if (check_fck.isChecked()) {
                                                i.putExtra("fck","NI");
                                            } else {
                                                i.putExtra("fck",edit_fck.getText().toString());
                                            }
                                        }
                                        if (local.isChecked()){
                                            if (check_local.isChecked()) {
                                                i.putExtra("local","NI");
                                            } else {
                                                i.putExtra("local",edit_local.getText().toString());
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

        if(String.valueOf(spinner_material.getSelectedItem()).equals(getString(R.string.material_prompt))){
            showAlert(getString(R.string.erro),getString(R.string.material_prompt));
            return false;
        }
        if(String.valueOf(spinner_dimenssion.getSelectedItem()).equals(getString(R.string.dimenssion_prompt))){
            showAlert(getString(R.string.erro),getString(R.string.dimenssion_prompt));
            return false;
        }

        if(String.valueOf(spinner_dimenssion.getSelectedItem()).equals(getString(R.string.outras_dim))){
            if(edit_altura.getText().toString().isEmpty()){
                errorAndRequestFocustoEditText(edit_altura);
                return false;
            }
            if(edit_largra.getText().toString().isEmpty()){
                errorAndRequestFocustoEditText(edit_largra);
                return false;
            }
        }
        if(button_datefab.getText().equals(getString(R.string.date_default)) && !check_dataFab.isChecked()){
            showAlert(getString(R.string.erro),getString(R.string.date_fab_notput_error));
            return false;
        }
        if(String.valueOf(spinner_concre.getSelectedItem()).equals(getString(R.string.construc_prompt))&& !check_concre.isChecked()){
            showAlert(getString(R.string.erro),getString(R.string.construc_prompt));
            return false;
        }
        if(edit_volume.getText().toString().isEmpty() && !check_volume.isChecked()){
            errorAndRequestFocustoEditText(edit_volume);
            return false;
        }
        if(edit_nota.getText().toString().isEmpty() && !check_nota.isChecked()){
            errorAndRequestFocustoEditText(edit_nota);
            return false;
        }
        if(edit_fck.getText().toString().isEmpty() && !check_fck.isChecked()){
            errorAndRequestFocustoEditText(edit_fck);
            return false;
        }
        if(String.valueOf(spinner_material.getSelectedItem()).equals(getString(R.string.concreto))&&
                String.valueOf(spinner_slump.getSelectedItem()).equals(getString(R.string.slump_prompt))){
            showAlert(getString(R.string.erro),getString(R.string.slump_erro));
            return false;
        }
        if(edit_slump.getText().toString().isEmpty() && relative_nb_slump.getVisibility() !=View.GONE){
            errorAndRequestFocustoEditText(edit_slump);
            return false;
        }
        if(edit_local.getText().toString().isEmpty() && !check_local.isChecked()){
            errorAndRequestFocustoEditText(edit_local);
            return false;
        }
        if(button_date.getText().equals(getString(R.string.date_default)) ){
            showAlert(getString(R.string.erro),getString(R.string.date_re_notput_error));
            return false;
        }
        if(!button_date.getText().equals(getString(R.string.date_default))
                && !button_datefab.getText().equals(getString(R.string.date_default)) && !check_dataFab.isChecked()) {
            if(date < fabDate){
                showAlert(getString(R.string.dialog_date_error_title), getString(R.string.dialog_date_error));
                return false;
            }


        }
        if(edit_quantidade.getText().toString().isEmpty() ){
            errorAndRequestFocustoEditText(edit_quantidade);
            return false;
        }
        if(String.valueOf(spinner_alvenaria.getSelectedItem()).equals(getString(R.string.alvenaria_prompt))&&temAlvenaria){
            showAlert(getString(R.string.erro),getString(R.string.alvenaria_erro));
            return false;
        }

        if(corpos.size() !=  Integer.parseInt(edit_quantidade.getText().toString())){
            showAlert(getString(R.string.erro),getString(R.string.corpos_erros));
            return false;
        }
        int contIdades = 0;
        List<String> vetorIdades = new ArrayList();
        for (HashMap<String,String>corpo:
             corpos) {
            if(!vetorIdades.contains(corpo.get("idade"))){
                contIdades++;
            }
            vetorIdades.add(corpo.get("idade"));

        }

        if(!vetorIdades.contains("28")){
            showAlert(getString(R.string.erro),getString(R.string.erro_28));
            return false;
        }
        if(contIdades>3){
            showAlert(getString(R.string.erro),getString(R.string.erro_3));
            return false;
        }


        return true;
    }
    public void addCorpo(View view) {
        ScanAddCorpoActivity.vimdeCP = true;
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
            if (checkPermission()) {
                Intent intent = new Intent(CorpoMoldActivity.this, ScanAddCorpoActivity.class);
                startActivityForResult(intent,10);
            } else {
                requestPermission();
            }
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {

        if (resultCode == 10) {



            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.dialog_idade_choice);
            dialog.setTitle(getString(R.string.dialog_idade_title));
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            dialog.show();
            dialog.getWindow().setAttributes(lp);
            final EditText edit_add_idade = (EditText)dialog.findViewById(R.id.edit_add_idade);
            Button btCancel = (Button) dialog.findViewById(R.id.button_no);
            Button btConfrimar = (Button) dialog.findViewById(R.id.button_yes);
            btConfrimar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String result=data.getStringExtra("result");
                    HashMap<String,String> cp = new HashMap<>();
                    cp.put("codigo",result);
                    cp.put("idade",edit_add_idade.getText().toString());
                    corpos.add(cp);
                    codigoCorpoAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            });
            btCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

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
                        Intent intent = new Intent(CorpoMoldActivity.this, ScanAddCorpoActivity.class);
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
        new AlertDialog.Builder(CorpoMoldActivity.this)
                .setMessage(message)
                .setPositiveButton(R.string.ok, okListener)
                .setNegativeButton(R.string.cancel, null)
                .create()
                .show();
    }
}
