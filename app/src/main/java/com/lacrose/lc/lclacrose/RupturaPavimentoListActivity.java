package com.lacrose.lc.lclacrose;

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
import com.lacrose.lc.lclacrose.Adapter.RupturaPavimentoAdapter;
import com.lacrose.lc.lclacrose.Model.Corpos;
import com.lacrose.lc.lclacrose.Model.PavimentoLotes;
import com.lacrose.lc.lclacrose.Model.Pavimentos;
import com.lacrose.lc.lclacrose.Util.FireBaseUtil;
import com.lacrose.lc.lclacrose.Util.MainActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class RupturaPavimentoListActivity extends MainActivity {
    public static  List<Pavimentos> pavimentoList;
    private final Context context=this;
    public static Pavimentos antigoCorpos;
    CollectionReference corpo_ref;
    public static Corpos corpo;
    FirebaseFirestore database;
    int ListSize = 0;
    private static RupturaPavimentoAdapter pavimentoAdapter;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruptura_list);
        database = FireBaseUtil.getFireDatabase();
        ListView rupturaListView = (ListView) findViewById(R.id.ruptura_list);
        rupturaListView.setDivider(null);
        pavimentoAdapter = new RupturaPavimentoAdapter(this, R.layout.item_ruptura_pavimento, pavimentoList);
        rupturaListView.setAdapter(pavimentoAdapter);
        pavimentoAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                if(pavimentoList.size()>0){
                    save.setEnabled(true);
                }else{
                    save.setEnabled(false);
                }

            }
        });
    }

    public void saveRuptura(View view) {
        if(RupturaPavimentoActivity.atualLote.getQuantidade()>pavimentoList.size()){
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
        newCorpo.setLoteId(RupturaPavimentoActivity.atualLote.getId());
        newCorpo.setAlertas(testAvisos());
        newCorpo.setTipo(getString(R.string.pavimento_minusculo));
        if (newCorpo.getDataCreate() == null) {
            corpo_ref = database.collection(getString(R.string.corpos_tag));
            corpo_ref.add(newCorpo).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                @Override
                public void onComplete(@NonNull Task<DocumentReference> task) {
                    if (task.isSuccessful()) {
                        CollectionReference corpoCriadoRef = database.collection(getString(R.string.corpos_tag) + "/" + task.getResult().getId() + "/" + getString(R.string.rompimentos_tag));
                        for (Pavimentos pavimentos : pavimentoList) {
                            ListSize++;
                            corpoCriadoRef.add(pavimentos).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    if (task.isSuccessful()) {
                                        if (ListSize >= pavimentoList.size()) {
                                            dismissProgress();
                                            Toast.makeText(context, getString(R.string.rupturas_create_sucess), Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(RupturaPavimentoListActivity.this, HomeActivity.class);
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

                                    for (Pavimentos pavimentos:
                                            pavimentoList) {
                                        ListSize++;
                                        database.collection(getString(R.string.corpos_tag) + "/" + corpo.getId() + "/" + getString(R.string.rompimentos_tag))
                                                .add(pavimentos).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                if (task.isSuccessful()) {
                                                    if (ListSize >= pavimentoList.size()) {
                                                        dismissProgress();
                                                        Toast.makeText(context, getString(R.string.rupturas_create_sucess), Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(RupturaPavimentoListActivity.this, HomeActivity.class);
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
    public List<String> testAvisos(){
        List<String> alertas = new ArrayList<>();
        Double media = 0.0;
        Double maior = -999999.0;
        Double menor = 99999.0;
        Double res = 0.0;
        List<Double> vectorRes = new ArrayList<>();
        Double variancia = 0.0;
        Double devPad = 0.0;
        Integer n = pavimentoList.size();
        Integer i = 0;
        Double phi = 0.0;
        Double phiMinimo = 0.0;
        Double fpk = 0.0;
        PavimentoLotes lote = RupturaPavimentoActivity.atualLote;
        Double largura_do_lote = RupturaPavimentoActivity.atualLote.getDimenssion().get(getString(R.string.largura));
        Double altura_do_lote = RupturaPavimentoActivity.atualLote.getDimenssion().get(getString(R.string.altura));
        Double comprimento_do_lote = RupturaPavimentoActivity.atualLote.getDimenssion().get(getString(R.string.comprimento));
        boolean jaTeveAltura = false;
        boolean jaTeveLargura = false;
        boolean jaTeveComprimento = false;
        boolean jaTeveIndice = false;
        Double fpk_est = 0.0;

        for (Pavimentos pavimentos : pavimentoList) {
            media = pavimentos.getResistencia() + media;
            if (pavimentos.resistencia < menor) {
                menor = pavimentos.resistencia;
            }
            if (pavimentos.resistencia > maior) {
                maior = pavimentos.resistencia;
            }
            if ((((pavimentos.largura > largura_do_lote && ((pavimentos.largura - largura_do_lote) >= 3)) || (pavimentos.largura < largura_do_lote && ((largura_do_lote - pavimentos.largura) >= 3))) && !jaTeveLargura)) {
                alertas.add("A1: Largura divergiu pelo menos 3mm da largura nominal.");
                jaTeveLargura = true;

            }

            if ((((pavimentos.altura > altura_do_lote) && ((pavimentos.altura - altura_do_lote) >= 3)) || (pavimentos.altura < altura_do_lote && ((altura_do_lote - pavimentos.altura) >= 3))) && !jaTeveAltura) {
                alertas.add("A1: Altura divergiu pelo menos 3mm da altura nominal.");
                jaTeveAltura = true;


            }
            if ((((pavimentos.comprimento > comprimento_do_lote) && ((pavimentos.comprimento - comprimento_do_lote) >= 3)) || (pavimentos.comprimento < comprimento_do_lote && ((comprimento_do_lote - pavimentos.comprimento) >= 3))) && !jaTeveComprimento) {
                alertas.add("A1: Comprimento divergiu pelo menos 3mm da comprimento nominal.");
                jaTeveComprimento = true;

            }
            Double ind =pavimentos.comprimento / pavimentos.altura;

            if (ind > 4 && !jaTeveIndice) {
                jaTeveIndice = true;
                alertas.add("A5: Índice de forma maior que 4.");
            }


        }
        media = media / n;

        for (Pavimentos pavimentos : pavimentoList) {
            res = ((pavimentos.getResistencia() - media) * (pavimentos.getResistencia() - media)) + res;
            vectorRes.add(pavimentos.getResistencia());
        }
        variancia = (res / (n - 1));
        devPad = Math.sqrt(variancia);

        if (devPad > 0.5) {
            alertas.add("A4: Desvio padrão maior que 0,5.");
        }

        if (n < 6) {
            n = 6;
        }

        if (pavimentoList.size() % 2 > 0) {
            i = (pavimentoList.size() - 1) / 2;
        } else {
            i = pavimentoList.size() / 2;
        }


        switch (n) {
            case 6:
                phi = 0.92;

                break;
            case 7:
                phi = 0.906;

                break;
            case 8:
                phi = 0.896;

                break;
            case 9:
                phi = 0.889;

            case 12:
                phi = 0.883;

                break;
            case 14:
                phi = 0.876;

                break;
            case 16:
                phi = 0.866;

                break;
            case 18:
                phi = 0.863;

                break;
            case 20:
                phi = 0.861;

                break;
            case 22:
                phi = 0.859;

                break;
            case 24:
                phi = 0.858;

                break;
            case 26:
                phi = 0.856;

                break;
            case 27:
                phi = 0.8555;

                break;
            case 28:
                phi = 0.855;

                break;
            case 29:
                phi = 0.8545;

                break;
            case 30:
                phi = 0.854;

                break;
            case 31:
                phi = 0.848;

                break;
            case 32:
                phi = 0.842;

                break;
        }

            if(i>=3) {
                switch (i) {
                    case 3:
                        fpk = ((vectorRes.get(0) + vectorRes.get(1)) * 2.0 / (i - 1.0)) - vectorRes.get(2);
                        break;
                    case 4:
                        fpk = ((vectorRes.get(0) + vectorRes.get(2)) * 2 / (i - 1)) - vectorRes.get(3);

                        break;
                    case 5:
                        fpk = ((vectorRes.get(0) + vectorRes.get(3)) * 2 / (i - 1)) - vectorRes.get(4);

                        break;
                    case 6:
                        fpk = ((vectorRes.get(0) + vectorRes.get(4)) * 2 / (i - 1)) - vectorRes.get(5);

                        break;
                    case 7:
                        fpk = ((vectorRes.get(0) + vectorRes.get(5)) * 2 / (i - 1)) - vectorRes.get(6);

                        break;
                    case 8:
                        fpk = ((vectorRes.get(0) + vectorRes.get(6)) * 2 / (i - 1)) - vectorRes.get(7);

                        break;
                    case 9:
                        fpk = ((vectorRes.get(0) + vectorRes.get(7)) * 2 / (i - 1)) - vectorRes.get(8);
                        break;
                }
            }else{
                alertas.add("A3: Número de amostras insuficiente para formar o lote.");
            }

        fpk_est = (media - (devPad * phi));

        if(lote.fpk !=null) {
            if (fpk_est < lote.fpk) {
                alertas.add("A2: O fpk, est foi menor que o fbk de projeto.");
            }
           /* if (fpk_est > (lote.fpk + (lote.fpk * 40 / 100))) {
                alertas.add("A3: O fpk, est foi maior que 40% que o fbk de projeto.");
            }*/
        }
        return alertas;
    }
    public static void voltei(){

        pavimentoAdapter.notifyDataSetChanged();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.corpos_list_menu, menu);
        return true;
    }

    public void adcionar(MenuItem item) {
        Intent intent = new Intent(context, ScanActivity.class);
        startActivity(intent);
        finish();
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
