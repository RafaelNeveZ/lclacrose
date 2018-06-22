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
import com.lacrose.lc.lacrose.Adapter.RupturaPrismaAdapter;
import com.lacrose.lc.lacrose.Model.Corpos;
import com.lacrose.lc.lacrose.Model.PrismaLotes;
import com.lacrose.lc.lacrose.Model.Prismas;
import com.lacrose.lc.lacrose.Util.FireBaseUtil;
import com.lacrose.lc.lacrose.Util.MainActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class RupturaPrismasListActivity extends MainActivity {
    public static  List<Prismas> prismasList;
    private final Context context=this;
    public static Prismas antigoCorpos;
    CollectionReference corpo_ref;
    public static Corpos corpo;
    FirebaseFirestore database;
    int ListSize = 0;
    private static RupturaPrismaAdapter prismasAdapter;
    Button save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruptura_list);
        database = FireBaseUtil.getFireDatabase();
        ListView rupturaListView = (ListView) findViewById(R.id.ruptura_list);
        rupturaListView.setDivider(null);
        save = (Button) findViewById(R.id.save_bt);
        prismasAdapter = new RupturaPrismaAdapter(this, R.layout.item_ruptura_prisma, prismasList);
        rupturaListView.setAdapter(prismasAdapter);
        prismasAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                if(prismasList.size()>0){
                    save.setEnabled(true);
                }else{
                    save.setEnabled(false);
                }

            }
        });
    }

    public void saveRuptura(View view) {
        if(RupturaPrismaActivity.atualLote.getQuantidade()>prismasList.size()){
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
        newCorpo.setLoteId(RupturaPrismaActivity.atualLote.getId());
        newCorpo.setAlertas(testAvisos());
        newCorpo.setTipo(getString(R.string.prisma_minusculo));
        if (corpo.getDataCreate() == null) {
            corpo_ref = database.collection(getString(R.string.corpos_tag));
            corpo_ref.add(newCorpo).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                @Override
                public void onComplete(@NonNull Task<DocumentReference> task) {
                    if (task.isSuccessful()) {
                        CollectionReference corpoCriadoRef = database.collection(getString(R.string.corpos_tag) + "/" + task.getResult().getId() + "/" + getString(R.string.rompimentos_tag));
                        for (Prismas prisma : prismasList) {
                            ListSize++;
                            corpoCriadoRef.add(prisma).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    if (task.isSuccessful()) {
                                        if (ListSize >= prismasList.size()) {
                                            gravarLote();
                                            dismissProgress();
                                            Toast.makeText(context, getString(R.string.rupturas_create_sucess), Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(RupturaPrismasListActivity.this, HomeActivity.class);
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

                                    for (Prismas prisma:
                                            prismasList) {
                                        ListSize++;
                                        database.collection(getString(R.string.corpos_tag) + "/" + corpo.getId() + "/" + getString(R.string.rompimentos_tag))
                                                .add(prisma).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                if (task.isSuccessful()) {
                                                    if (ListSize >= prismasList.size()) {
                                                        gravarLote();
                                                        dismissProgress();
                                                        Toast.makeText(context, getString(R.string.rupturas_create_sucess), Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(RupturaPrismasListActivity.this, HomeActivity.class);
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
        database.collection(getString(R.string.lote_tag)).document(RupturaPrismaActivity.atualLote.getId())
                .update("rompido",true);
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
        Integer n = prismasList.size();
        Integer i = 0;
        Double phi = 0.0;
        Double phiMinimo = 0.0;
        Double fpk = 0.0;
        PrismaLotes lote = RupturaPrismaActivity.atualLote;
        Double largura_do_lote = RupturaPrismaActivity.atualLote.getDimenssion().get(getString(R.string.largura));
        Double altura_do_lote = RupturaPrismaActivity.atualLote.getDimenssion().get(getString(R.string.altura));
        Double comprimento_do_lote = RupturaPrismaActivity.atualLote.getDimenssion().get(getString(R.string.comprimento));
        boolean jaTeveAltura = false;
        boolean jaTeveLargura = false;
        boolean jaTeveComprimento = false;

        Double fpk_est = 0.0;

        for (Prismas prismas : prismasList) {
            media = prismas.getResistencia() + media;
            if (prismas.resistencia < menor) {
                menor = prismas.resistencia;
            }
            if (prismas.resistencia > maior) {
                maior = prismas.resistencia;
            }
            if ((((prismas.largura > largura_do_lote && ((prismas.largura - largura_do_lote) >= 5)) || (prismas.largura < largura_do_lote && ((largura_do_lote - prismas.largura) >= 5))) && !jaTeveLargura)) {
                alertas.add("A1: Largura divergiu pelo menos 5mm da largura nominal.");
                jaTeveLargura = true;

            }

            if ((((prismas.altura > altura_do_lote) && ((prismas.altura - altura_do_lote) >= 6)) || (prismas.altura < altura_do_lote && ((altura_do_lote - prismas.altura) >= 6))) && !jaTeveAltura) {
                alertas.add("A1: Altura divergiu pelo menos 6mm da altura nominal.");
                jaTeveAltura = true;


            }
            if ((((prismas.comprimento > comprimento_do_lote) && ((prismas.comprimento - comprimento_do_lote) >= 6)) || (prismas.comprimento < comprimento_do_lote && ((comprimento_do_lote - prismas.comprimento) >= 6))) && !jaTeveComprimento) {
                alertas.add("A1: Comprimento divergiu pelo menos 6mm da comprimento nominal.");
                jaTeveComprimento = true;

            }


        }
        media = media / n;

        for (Prismas prisma : prismasList) {
            res = ((prisma.getResistencia() - media) * (prisma.getResistencia() - media)) + res;
            vectorRes.add(prisma.getResistencia());
        }
        variancia = (res / (n - 1));
        devPad = Math.sqrt(variancia);

        if (devPad > 0.5) {
            alertas.add("A4: Desvio padrão maior que 0,5.");
        }

        if (n < 6) {
            n = 6;
        }

        if (prismasList.size() % 2 > 0) {
            i = (prismasList.size() - 1) / 2;
        } else {
            i = prismasList.size() / 2;
        }

        if(i>=3) {
            switch (n) {
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
                    phi = 0.94;

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
                case 16:
                    phi = 1.02;

                    break;
                case 17:
                    phi = 1.02;

                    break;
                case 18:
                    phi = 1.04;

                    break;
                case 19:
                    phi = 1.04;

                    break;
                }
            }else{
                alertas.add("A3: Número de amostras insuficiente para formar o lote.");
            }

        if (n < 6) {
            fpk = phi * menor;
        } else if (n >= 6 && n < 20) {
            Double fek1=0.0;

            switch (i) {
                case 3:
                    fek1 = ((vectorRes.get(0) + vectorRes.get(1)) * 2.0 / (i - 1.0)) - vectorRes.get(2);
                    break;
                case 4:
                    fek1 = ((vectorRes.get(0) + vectorRes.get(2)) * 2 / (i - 1)) - vectorRes.get(3);

                    break;
                case 5:
                    fek1 = ((vectorRes.get(0) + vectorRes.get(3)) * 2 / (i - 1)) - vectorRes.get(4);

                    break;
                case 6:
                    fek1 = ((vectorRes.get(0) + vectorRes.get(4)) * 2 / (i - 1)) - vectorRes.get(5);

                    break;
                case 7:
                    fek1 = ((vectorRes.get(0) + vectorRes.get(5)) * 2 / (i - 1)) - vectorRes.get(6);

                    break;
                case 8:
                    fek1 = ((vectorRes.get(0) + vectorRes.get(6)) * 2 / (i - 1)) - vectorRes.get(7);

                    break;
                case 9:
                    fek1 = ((vectorRes.get(0) + vectorRes.get(7)) * 2 / (i - 1)) - vectorRes.get(8);
                    break;
            }
            Double fek2 = phi * menor;
            Double fek3=0.0;
            if (fek1 >= fek2) {
                fek3 = fek1;
            } else {
                fek3 = fek2;
            }
            Double fek4 = 0.85 * media;
            if (fek3 >= fek4) {
                fpk = fek4;
            } else {
                fpk = fek3;
            }

        } else if (n >= 20) {
            fpk = media - (1.65 * devPad);
        }

        fpk_est = fpk;

        if(lote.fpk !=null) {
            if (fpk_est < lote.fpk) {
                alertas.add("A2: O fpk, est foi menor que o fbk de projeto.");
            }
            if (fpk_est > (lote.fpk + (lote.fpk * 40 / 100))) {
                //alertas.add("A3: O fpk, est foi maior que 40% que o fbk de projeto.");
            }
        }
        return alertas;
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

    public static void voltei(){
        prismasAdapter.notifyDataSetChanged();

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
