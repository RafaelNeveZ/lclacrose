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
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.lacrose.lc.lclacrose.Adapter.CodigoCorpoAdapter;
import com.lacrose.lc.lclacrose.Model.BlocoLotes;
import com.lacrose.lc.lclacrose.Model.PavimentoLotes;
import com.lacrose.lc.lclacrose.Util.FireBaseUtil;
import com.lacrose.lc.lclacrose.Util.MainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static android.Manifest.permission.CAMERA;

public class PavimentoMoldActivity extends MainActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    private final Context context = this;
    CollectionReference lote_ref;
    CodigoCorpoAdapter codigoCorpoAdapter;
    FirebaseFirestore database;
    CheckBox check_dimenssion,check_nota,check_dataFab, check_fpk,check_lote,check_fab,check_date,check_idade;
    Spinner spinner_dimenssion;
    TextView tv_code;
    RelativeLayout relative_hora,relative_largura,relative_altura,relative_comprimento;
    EditText edit_nota,edit_lote, edit_fpk,edit_fab,edit_more,edit_idade,edit_quantidade,edit_hora,edit_altura,edit_largra,edit_comprimeto;
    Button button_date, button_datefab,button_hora;
    Calendar refCalendar,tempCalendar,finalCalendar,fabCalendar,horaCalendar;
    PavimentoLotes newLote;
    ArrayList<String> dimen;
    public boolean isFab=true;
    long date, fabDate,hora;
    private FirebaseAuth Auth;
    public static  ListView lista_corpos;
    List<String>corpos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pavimento_mold);
        refCalendar = Calendar.getInstance();
        tempCalendar = Calendar.getInstance();
        horaCalendar = Calendar.getInstance();
        finalCalendar = Calendar.getInstance();
        fabCalendar = Calendar.getInstance();
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
        dimen.add(getString(R.string.d60_100_200));
        dimen.add(getString(R.string.d80_100_200));
        dimen.add(getString(R.string.d100_100_200));
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
    }

    private void getLoteNumber() {

        database.collection(getString(R.string.lote_tag))
                .whereEqualTo(getString(R.string.obraid),HomeActivity.WorkId)
                .whereEqualTo(getString(R.string.tipo),getString(R.string.pavimento_minusculo))
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
      //  check_dimenssion = (CheckBox) findViewById(R.id.check_dimenssion);
        check_dataFab = (CheckBox) findViewById(R.id.checkfab_date);
     //   check_idade = (CheckBox) findViewById(R.id.check_idade);
        check_lote = (CheckBox) findViewById(R.id.check_lote);
        check_nota = (CheckBox) findViewById(R.id.check_nota_fiscal);
        check_fab = (CheckBox) findViewById(R.id.check_fab);
        check_fpk = (CheckBox) findViewById(R.id.check_fpk);
     //   check_date = (CheckBox) findViewById(R.id.check_date);

        relative_comprimento = (RelativeLayout) findViewById(R.id.relative_comprimento);
        relative_altura = (RelativeLayout) findViewById(R.id.relative_altura);
        relative_largura = (RelativeLayout) findViewById(R.id.relative_largura);
        relative_hora = (RelativeLayout) findViewById(R.id.relative_hora);
        if(HomeActivity.Work.getIs24().equals("Sim")){
            relative_hora.setVisibility(View.VISIBLE);
        }

        //SPINNER
        spinner_dimenssion=(Spinner) findViewById(R.id.dimenssion_spinner);

        //EDITTEXT
        edit_comprimeto = (EditText) findViewById(R.id.comprimento_edit_text);
        edit_altura = (EditText) findViewById(R.id.altura_edit_text);
        edit_largra = (EditText) findViewById(R.id.largura_edit_text);
        edit_idade = (EditText) findViewById(R.id.idade_edit_text);
        edit_lote = (EditText) findViewById(R.id.lote_edit_text);
        edit_nota = (EditText) findViewById(R.id.nota_edit_text);
        edit_fab = (EditText) findViewById(R.id.fab_edit_text);
        edit_fpk = (EditText) findViewById(R.id.fpk_edit_text);
        edit_quantidade = (EditText) findViewById(R.id.quantidade_edit_text);
        edit_more = (EditText) findViewById(R.id.more_edit_text);


        //BUTTOM
        button_date = (Button) findViewById(R.id.date_buttom);
        button_datefab = (Button) findViewById(R.id.datefab_buttom);
        button_hora = (Button) findViewById(R.id.hora_buttom);

        //TEXTVIEW
        tv_code = (TextView) findViewById(R.id.code_edit_text);

        lista_corpos = (ListView) findViewById(R.id.corpo_list);
        lista_corpos.setDivider(null);

        corpos = new ArrayList<>();
        codigoCorpoAdapter = new CodigoCorpoAdapter(this, R.layout.item_codigo_corpos, corpos);
        lista_corpos.setAdapter(codigoCorpoAdapter);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        tempCalendar.set(year,month,dayOfMonth);
  /*      if(tempCalendar.getTime().getTime()>= refCalendar.getTime().getTime()){*/
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

       /* }else{
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
            newLote = new PavimentoLotes();
            HashMap<String, Double> dimenssionHash = new HashMap<>();
            if(!String.valueOf(spinner_dimenssion.getSelectedItem()).equals(getString(R.string.dimenssion_prompt))) {
                if (String.valueOf(spinner_dimenssion.getSelectedItem()).equals(getString(R.string.d60_100_200))) {
                    dimenssionHash.put(getString(R.string.largura), 60.0);
                    dimenssionHash.put(getString(R.string.altura), 100.0);
                    dimenssionHash.put(getString(R.string.comprimento), 200.0);
                    newLote.setDimenssion(dimenssionHash);
                } else if (String.valueOf(spinner_dimenssion.getSelectedItem()).equals(getString(R.string.d80_100_200))) {
                    dimenssionHash.put(getString(R.string.largura), 80.0);
                    dimenssionHash.put(getString(R.string.altura), 100.0);
                    dimenssionHash.put(getString(R.string.comprimento), 200.0);
                    newLote.setDimenssion(dimenssionHash);
                } else if (String.valueOf(spinner_dimenssion.getSelectedItem()).equals(getString(R.string.d100_100_200))) {
                    dimenssionHash.put(getString(R.string.largura), 100.0);
                    dimenssionHash.put(getString(R.string.altura), 100.0);
                    dimenssionHash.put(getString(R.string.comprimento), 200.0);
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



            if(!button_date.getText().toString().equals(getString(R.string.date_default)))
                newLote.setData(date);

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
            newLote.setTipo(getString(R.string.pavimento_minusculo));
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
                                        Intent i = new Intent(PavimentoMoldActivity.this, PavimentoMoldActivity.class);
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
        if(edit_quantidade.getText().toString().isEmpty() ){
            errorAndRequestFocustoEditText(edit_quantidade);
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
                Intent intent = new Intent(PavimentoMoldActivity.this, ScanAddCorpoActivity.class);
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
            setListViewHeightBasedOnChildren(lista_corpos);
        }
        if (resultCode == 8) {
            Toast.makeText(this,getString(R.string.cancelada),Toast.LENGTH_SHORT);
        }
    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        Log.e("Listview Size ", "" + listView.getCount());
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {

            return;
        }

        int totalHeight =100;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();;
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();


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
                        Intent intent = new Intent(PavimentoMoldActivity.this, ScanAddCorpoActivity.class);
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
        new AlertDialog.Builder(PavimentoMoldActivity.this)
                .setMessage(message)
                .setPositiveButton(R.string.ok, okListener)
                .setNegativeButton(R.string.cancel, null)
                .create()
                .show();
    }


}
