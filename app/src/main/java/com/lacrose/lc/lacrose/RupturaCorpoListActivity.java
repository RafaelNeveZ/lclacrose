package com.lacrose.lc.lacrose;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.lacrose.lc.lacrose.Adapter.RupturaCorpoAdapter;
import com.lacrose.lc.lacrose.Model.CP;
import com.lacrose.lc.lacrose.Model.CorpoLotes;
import com.lacrose.lc.lacrose.Model.Corpos;
import com.lacrose.lc.lacrose.Util.FireBaseUtil;
import com.lacrose.lc.lacrose.Util.MainActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class RupturaCorpoListActivity extends MainActivity {
    public static  List<CP> CorposList;
    private final Context context=this;
    public static Corpos corpo;
    public static CP antigoCorpos;
    CollectionReference corpo_ref;
    FirebaseFirestore database;
    Button save;
    int ListSize = 0;
    private static RupturaCorpoAdapter corposAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruptura_list);
        database = FireBaseUtil.getFireDatabase();
        ListView rupturaListView = (ListView) findViewById(R.id.ruptura_list);
        save = (Button) findViewById(R.id.save_bt);
        rupturaListView.setDivider(null);
        corposAdapter = new RupturaCorpoAdapter(this, R.layout.item_ruptura_corpo, CorposList);
        rupturaListView.setAdapter(corposAdapter);
        corposAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                if(CorposList.size()>0){
                    save.setEnabled(true);
                }else{
                    save.setEnabled(false);
                }

            }
        });
    }

    public void saveRuptura(View view) {
        if(RupturaCorpoActivity.atualLote.getQuantidade()>CorposList.size()){
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.dialog_two_choice);
            dialog.setTitle(getString(R.string.dialog_cancel_ruptura));
            dialog.show();
            TextView title = (TextView) dialog.findViewById(R.id.dialog_title);
            title.setText(getString(R.string.dialog_quant_errada));
            Button btCancel = (Button) dialog.findViewById(R.id.button_no);
            Button btYes = (Button) dialog.findViewById(R.id.button_yes);
            btCancel.setText(getString(R.string.no));
            btYes.setText(getString(R.string.yes));
            btYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    gravar();
                }
            });
            btCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }else{
            gravar();
        }


    }
    public void gravar() {
        showProgress(getString(R.string.saving));
        Corpos newCorpo = new Corpos();
        newCorpo.setCentro_de_custo(HomeActivity.Work.getCentro_de_custo());
        newCorpo.setCreatedBy(FirebaseAuth.getInstance().getUid());
        newCorpo.setDataCreate(Calendar.getInstance().getTime().getTime());
        newCorpo.setIsValid(true);
        newCorpo.setObraId(HomeActivity.WorkId);
        newCorpo.setLoteId(RupturaCorpoActivity.atualLote.getId());
        newCorpo.setAlertas(testAvisos());
        newCorpo.setTipo(getString(R.string.cp_minusculo));
        if (corpo.getDataCreate() == null) {
            corpo_ref = database.collection(getString(R.string.corpos_tag));
            corpo_ref.add(newCorpo).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                @Override
                public void onComplete(@NonNull Task<DocumentReference> task) {
                    if (task.isSuccessful()) {
                        CollectionReference corpoCriadoRef = database.collection(getString(R.string.corpos_tag) + "/" + task.getResult().getId() + "/" + getString(R.string.rompimentos_tag));
                        for (CP cps : CorposList) {
                            ListSize++;
                            corpoCriadoRef.add(cps).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    if (task.isSuccessful()) {
                                        if (ListSize >= CorposList.size()) {
                                            gravarLote();
                                            dismissProgress();
                                            Toast.makeText(context, getString(R.string.rupturas_create_sucess), Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(RupturaCorpoListActivity.this, HomeActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            dismissProgress();
                                        }
                                    } else {
                                        dismissProgress();
                                    }
                                }
                            });
                        }
                    } else {
                        showAlert(getString(R.string.erro), getString(R.string.erro_salve));
                    }
                }
            });

        }else{
            log(getString(R.string.corpos_tag) + "/" + corpo.getId());
            database.collection(getString(R.string.corpos_tag)).document(corpo.getId())
                    .update("alertas",newCorpo.getAlertas(),
                            "centro_de_custo",newCorpo.getCentro_de_custo(),
                            "createdBy",newCorpo.getCreatedBy(),
                            "dataCreate",newCorpo.getDataCreate(),
                            "isValid",true,
                            "loteId",newCorpo.getLoteId(),
                            "obraId",newCorpo.getObraId(),
                            "tipo",newCorpo.getTipo())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task1) {
                            database.collection(getString(R.string.corpos_tag) + "/" + corpo.getId() + "/" + getString(R.string.rompimentos_tag)).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task2) {
                                    for (DocumentChange change2 :
                                            task2.getResult().getDocumentChanges()){
                                        database.document(getString(R.string.corpos_tag) + "/" + corpo.getId() + "/" + getString(R.string.rompimentos_tag)+"/"+change2.getDocument().getId()).delete();

                                    }

                                    for (CP cp:
                                            CorposList) {
                                        ListSize++;
                                        database.collection(getString(R.string.corpos_tag) + "/" + corpo.getId() + "/" + getString(R.string.rompimentos_tag))
                                                .add(cp).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                if (task.isSuccessful()) {
                                                    if (ListSize >= CorposList.size()) {
                                                        gravarLote();
                                                        dismissProgress();
                                                        Toast.makeText(context, getString(R.string.rupturas_create_sucess), Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(RupturaCorpoListActivity.this, HomeActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    } else {
                                                        dismissProgress();
                                                    }
                                                } else {
                                                    dismissProgress();
                                                }
                                            }
                                        });
                                    }

                                }
                            });
                        }
                    });

        }
    }
    public void gravarLote(){
        database.collection(getString(R.string.lote_tag)).document(RupturaCorpoActivity.atualLote.getId())
                .update("rompido",true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.corpos_list_menu, menu);
        return true;
    }

    public List<String> testAvisos(){
        List<String> alertas = new ArrayList<>();
        Double media = 0.0;
        Double maior = -999999.0;
        Double menor = 99999.0;
        Double res = 0.0;
        List<Double> vectorRes = new ArrayList<>();
        Double variancia = 0.0;
        Double devPad = 0.0;
        Integer n =CorposList.size();
        Integer i = 0;
        Double phi = 0.0;
        Double phiMinimo = 0.0;
        Double fck = 0.0;
        CorpoLotes lote = RupturaCorpoActivity.atualLote;
        Double fck_est = 0.0;

        for (CP cp : CorposList) {
            media = cp.getResistencia() + media;
            if (cp.resistencia < menor) {
                menor = cp.resistencia;
            }
            if (cp.resistencia > maior) {
                maior = cp.resistencia;
            }


        }
        media = media / n;

        for (CP cp : CorposList) {
            res = ((cp.getResistencia() - media) * (cp.getResistencia() - media)) + res;
            vectorRes.add(cp.getResistencia());
        }
        variancia = (res / (n - 1));
        devPad = Math.sqrt(variancia);

        if (devPad > 0.5) {
            alertas.add("A5: Desvio padrão maior que 0,5.");
        }

        if (n < 6) {
            n = 6;
        }

        if (CorposList.size() % 2 > 0) {
            i = (CorposList.size() - 1) / 2;
        } else {
            i = CorposList.size() / 2;
        }

        switch (n) {
            case 2:
                phi = 0.75;
                break;
            case 3:
                phi = 0.8;
                break;
            case 4:
                phi = 0.84;

                break;
            case 5:
                phi = 0.87;

                break;
            case 6:
                phi = 0.89;

                break;
            case 7:
                phi = 0.91;

                break;
            case 8:
                phi = 0.93;

                break;
            case 9:
                phi = 0.945;

                break;
            case 10:
                phi = 0.96;

                break;
            case 11:
                phi = 0.97;

                break;
            case 12:
                phi = 0.98;

                break;
            case 13:
                phi = 0.99;

                break;
            case 14:
                phi = 1.0;

                break;
            case 15:
                phi = 1.01;

                break;
        }

        if (n >= 16) {
            phi = 1.02;
        }

        if (n < 6) {
            alertas.add("A4: Número de amostras insuficiente para formar o lote.");
            fck = phi * menor;
        } else if (n >= 6 && n < 20) {
            switch (i) {
                case 3:
                    fck = ((vectorRes.get(0) + vectorRes.get(1)) * 2.0 / (i - 1.0)) - vectorRes.get(2);
                    break;
                case 4:
                    fck = ((vectorRes.get(0) + vectorRes.get(2)) * 2 / (i - 1)) - vectorRes.get(3);

                    break;
                case 5:
                    fck = ((vectorRes.get(0) + vectorRes.get(3)) * 2 / (i - 1)) - vectorRes.get(4);

                    break;
                case 6:
                    fck = ((vectorRes.get(0) + vectorRes.get(4)) * 2 / (i - 1)) - vectorRes.get(5);

                    break;
                case 7:
                    fck = ((vectorRes.get(0) + vectorRes.get(5)) * 2 / (i - 1)) - vectorRes.get(6);

                    break;
                case 8:
                    fck = ((vectorRes.get(0) + vectorRes.get(6)) * 2 / (i - 1)) - vectorRes.get(7);

                    break;
                case 9:
                    fck = ((vectorRes.get(0) + vectorRes.get(7)) * 2 / (i - 1)) - vectorRes.get(8);
                    break;
            }
        }else if (n >= 20) {
            fck = media - (1.65 * devPad);
        }

        fck_est = fck;
        if(lote.fck !=null) {
            if (fck_est < lote.fck) {
                alertas.add("A2: O fck, est foi menor que o fck de projeto.");
            }
            if (fck_est > (lote.fck + (lote.fck * 40 / 100))) {
                alertas.add("A3: O fck, est foi maior que 40% que o fck de projeto.");
            }
        }
        return alertas;
    }


    public void adcionar(MenuItem item) {
        Intent intent = new Intent(context, ScanActivity.class);
        startActivity(intent);
        finish();
    }

    public static void voltei(){
        corposAdapter.notifyDataSetChanged();

    }

    @Override
    public void onBackPressed() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_two_choice);
        dialog.setTitle(getString(R.string.dialog_cancel_ruptura));
        dialog.show();
        TextView title = (TextView) dialog.findViewById(R.id.dialog_title);
        title.setText(getString(R.string.dialog_cancel_ruptura));
        Button btCancel = (Button) dialog.findViewById(R.id.button_no);
        Button btYes = (Button) dialog.findViewById(R.id.button_yes);
        btYes.setText(getString(R.string.yes));
        btYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
